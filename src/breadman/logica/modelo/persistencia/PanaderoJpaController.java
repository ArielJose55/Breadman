/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import breadman.logica.modelo.entidades.Usuario;
import breadman.logica.modelo.entidades.OrdenProduccion;
import java.util.ArrayList;
import java.util.List;
import breadman.logica.modelo.entidades.Lote;
import breadman.logica.modelo.entidades.Panadero;
import breadman.logica.modelo.entidades.PanaderoPK;
import breadman.logica.modelo.persistencia.exceptions.IllegalOrphanException;
import breadman.logica.modelo.persistencia.exceptions.NonexistentEntityException;
import breadman.logica.modelo.persistencia.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel Arnedo
 */
public class PanaderoJpaController implements Serializable {

    public PanaderoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Panadero panadero) throws PreexistingEntityException, Exception {
        if (panadero.getPanaderoPK() == null) {
            panadero.setPanaderoPK(new PanaderoPK());
        }
        if (panadero.getOrdenProduccionList() == null) {
            panadero.setOrdenProduccionList(new ArrayList<OrdenProduccion>());
        }
        if (panadero.getLoteList() == null) {
            panadero.setLoteList(new ArrayList<Lote>());
        }
        panadero.getPanaderoPK().setCedula(panadero.getUsuario().getCedula());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = panadero.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getCedula());
                panadero.setUsuario(usuario);
            }
            List<OrdenProduccion> attachedOrdenProduccionList = new ArrayList<OrdenProduccion>();
            for (OrdenProduccion ordenProduccionListOrdenProduccionToAttach : panadero.getOrdenProduccionList()) {
                ordenProduccionListOrdenProduccionToAttach = em.getReference(ordenProduccionListOrdenProduccionToAttach.getClass(), ordenProduccionListOrdenProduccionToAttach.getCodigoOrdenProduccion());
                attachedOrdenProduccionList.add(ordenProduccionListOrdenProduccionToAttach);
            }
            panadero.setOrdenProduccionList(attachedOrdenProduccionList);
            List<Lote> attachedLoteList = new ArrayList<Lote>();
            for (Lote loteListLoteToAttach : panadero.getLoteList()) {
                loteListLoteToAttach = em.getReference(loteListLoteToAttach.getClass(), loteListLoteToAttach.getCodigoLote());
                attachedLoteList.add(loteListLoteToAttach);
            }
            panadero.setLoteList(attachedLoteList);
            em.persist(panadero);
            if (usuario != null) {
                usuario.getPanaderoList().add(panadero);
                usuario = em.merge(usuario);
            }
            for (OrdenProduccion ordenProduccionListOrdenProduccion : panadero.getOrdenProduccionList()) {
                Panadero oldPanaderoOfOrdenProduccionListOrdenProduccion = ordenProduccionListOrdenProduccion.getPanadero();
                ordenProduccionListOrdenProduccion.setPanadero(panadero);
                ordenProduccionListOrdenProduccion = em.merge(ordenProduccionListOrdenProduccion);
                if (oldPanaderoOfOrdenProduccionListOrdenProduccion != null) {
                    oldPanaderoOfOrdenProduccionListOrdenProduccion.getOrdenProduccionList().remove(ordenProduccionListOrdenProduccion);
                    oldPanaderoOfOrdenProduccionListOrdenProduccion = em.merge(oldPanaderoOfOrdenProduccionListOrdenProduccion);
                }
            }
            for (Lote loteListLote : panadero.getLoteList()) {
                Panadero oldPanaderoOfLoteListLote = loteListLote.getPanadero();
                loteListLote.setPanadero(panadero);
                loteListLote = em.merge(loteListLote);
                if (oldPanaderoOfLoteListLote != null) {
                    oldPanaderoOfLoteListLote.getLoteList().remove(loteListLote);
                    oldPanaderoOfLoteListLote = em.merge(oldPanaderoOfLoteListLote);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPanadero(panadero.getPanaderoPK()) != null) {
                throw new PreexistingEntityException("Panadero " + panadero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Panadero panadero) throws IllegalOrphanException, NonexistentEntityException, Exception {
        panadero.getPanaderoPK().setCedula(panadero.getUsuario().getCedula());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Panadero persistentPanadero = em.find(Panadero.class, panadero.getPanaderoPK());
            Usuario usuarioOld = persistentPanadero.getUsuario();
            Usuario usuarioNew = panadero.getUsuario();
            List<OrdenProduccion> ordenProduccionListOld = persistentPanadero.getOrdenProduccionList();
            List<OrdenProduccion> ordenProduccionListNew = panadero.getOrdenProduccionList();
            List<Lote> loteListOld = persistentPanadero.getLoteList();
            List<Lote> loteListNew = panadero.getLoteList();
            List<String> illegalOrphanMessages = null;
            for (OrdenProduccion ordenProduccionListOldOrdenProduccion : ordenProduccionListOld) {
                if (!ordenProduccionListNew.contains(ordenProduccionListOldOrdenProduccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrdenProduccion " + ordenProduccionListOldOrdenProduccion + " since its panadero field is not nullable.");
                }
            }
            for (Lote loteListOldLote : loteListOld) {
                if (!loteListNew.contains(loteListOldLote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lote " + loteListOldLote + " since its panadero field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getCedula());
                panadero.setUsuario(usuarioNew);
            }
            List<OrdenProduccion> attachedOrdenProduccionListNew = new ArrayList<OrdenProduccion>();
            for (OrdenProduccion ordenProduccionListNewOrdenProduccionToAttach : ordenProduccionListNew) {
                ordenProduccionListNewOrdenProduccionToAttach = em.getReference(ordenProduccionListNewOrdenProduccionToAttach.getClass(), ordenProduccionListNewOrdenProduccionToAttach.getCodigoOrdenProduccion());
                attachedOrdenProduccionListNew.add(ordenProduccionListNewOrdenProduccionToAttach);
            }
            ordenProduccionListNew = attachedOrdenProduccionListNew;
            panadero.setOrdenProduccionList(ordenProduccionListNew);
            List<Lote> attachedLoteListNew = new ArrayList<Lote>();
            for (Lote loteListNewLoteToAttach : loteListNew) {
                loteListNewLoteToAttach = em.getReference(loteListNewLoteToAttach.getClass(), loteListNewLoteToAttach.getCodigoLote());
                attachedLoteListNew.add(loteListNewLoteToAttach);
            }
            loteListNew = attachedLoteListNew;
            panadero.setLoteList(loteListNew);
            panadero = em.merge(panadero);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getPanaderoList().remove(panadero);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getPanaderoList().add(panadero);
                usuarioNew = em.merge(usuarioNew);
            }
            for (OrdenProduccion ordenProduccionListNewOrdenProduccion : ordenProduccionListNew) {
                if (!ordenProduccionListOld.contains(ordenProduccionListNewOrdenProduccion)) {
                    Panadero oldPanaderoOfOrdenProduccionListNewOrdenProduccion = ordenProduccionListNewOrdenProduccion.getPanadero();
                    ordenProduccionListNewOrdenProduccion.setPanadero(panadero);
                    ordenProduccionListNewOrdenProduccion = em.merge(ordenProduccionListNewOrdenProduccion);
                    if (oldPanaderoOfOrdenProduccionListNewOrdenProduccion != null && !oldPanaderoOfOrdenProduccionListNewOrdenProduccion.equals(panadero)) {
                        oldPanaderoOfOrdenProduccionListNewOrdenProduccion.getOrdenProduccionList().remove(ordenProduccionListNewOrdenProduccion);
                        oldPanaderoOfOrdenProduccionListNewOrdenProduccion = em.merge(oldPanaderoOfOrdenProduccionListNewOrdenProduccion);
                    }
                }
            }
            for (Lote loteListNewLote : loteListNew) {
                if (!loteListOld.contains(loteListNewLote)) {
                    Panadero oldPanaderoOfLoteListNewLote = loteListNewLote.getPanadero();
                    loteListNewLote.setPanadero(panadero);
                    loteListNewLote = em.merge(loteListNewLote);
                    if (oldPanaderoOfLoteListNewLote != null && !oldPanaderoOfLoteListNewLote.equals(panadero)) {
                        oldPanaderoOfLoteListNewLote.getLoteList().remove(loteListNewLote);
                        oldPanaderoOfLoteListNewLote = em.merge(oldPanaderoOfLoteListNewLote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PanaderoPK id = panadero.getPanaderoPK();
                if (findPanadero(id) == null) {
                    throw new NonexistentEntityException("The panadero with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PanaderoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Panadero panadero;
            try {
                panadero = em.getReference(Panadero.class, id);
                panadero.getPanaderoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The panadero with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<OrdenProduccion> ordenProduccionListOrphanCheck = panadero.getOrdenProduccionList();
            for (OrdenProduccion ordenProduccionListOrphanCheckOrdenProduccion : ordenProduccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Panadero (" + panadero + ") cannot be destroyed since the OrdenProduccion " + ordenProduccionListOrphanCheckOrdenProduccion + " in its ordenProduccionList field has a non-nullable panadero field.");
            }
            List<Lote> loteListOrphanCheck = panadero.getLoteList();
            for (Lote loteListOrphanCheckLote : loteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Panadero (" + panadero + ") cannot be destroyed since the Lote " + loteListOrphanCheckLote + " in its loteList field has a non-nullable panadero field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario = panadero.getUsuario();
            if (usuario != null) {
                usuario.getPanaderoList().remove(panadero);
                usuario = em.merge(usuario);
            }
            em.remove(panadero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Panadero> findPanaderoEntities() {
        return findPanaderoEntities(true, -1, -1);
    }

    public List<Panadero> findPanaderoEntities(int maxResults, int firstResult) {
        return findPanaderoEntities(false, maxResults, firstResult);
    }

    private List<Panadero> findPanaderoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Panadero.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Panadero findPanadero(PanaderoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Panadero.class, id);
        } finally {
            em.close();
        }
    }

    public int getPanaderoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Panadero> rt = cq.from(Panadero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
