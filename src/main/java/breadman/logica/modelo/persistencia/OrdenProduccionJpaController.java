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
import breadman.logica.modelo.entidades.Panadero;
import breadman.logica.modelo.entidades.Producto;
import breadman.logica.modelo.entidades.Lote;
import breadman.logica.modelo.entidades.OrdenProduccion;
import breadman.logica.modelo.persistencia.exceptions.IllegalOrphanException;
import breadman.logica.modelo.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel Arnedo
 */
public class OrdenProduccionJpaController implements Serializable {

    public OrdenProduccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrdenProduccion ordenProduccion) {
        if (ordenProduccion.getLoteList() == null) {
            ordenProduccion.setLoteList(new ArrayList<Lote>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Panadero panadero = ordenProduccion.getPanadero();
            if (panadero != null) {
                panadero = em.getReference(panadero.getClass(), panadero.getPanaderoPK());
                ordenProduccion.setPanadero(panadero);
            }
            Producto idProducto = ordenProduccion.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getCodigoProducto());
                ordenProduccion.setIdProducto(idProducto);
            }
            List<Lote> attachedLoteList = new ArrayList<Lote>();
            for (Lote loteListLoteToAttach : ordenProduccion.getLoteList()) {
                loteListLoteToAttach = em.getReference(loteListLoteToAttach.getClass(), loteListLoteToAttach.getCodigoLote());
                attachedLoteList.add(loteListLoteToAttach);
            }
            ordenProduccion.setLoteList(attachedLoteList);
            em.persist(ordenProduccion);
            if (panadero != null) {
                panadero.getOrdenProduccionList().add(ordenProduccion);
                panadero = em.merge(panadero);
            }
            if (idProducto != null) {
                idProducto.getOrdenProduccionList().add(ordenProduccion);
                idProducto = em.merge(idProducto);
            }
            for (Lote loteListLote : ordenProduccion.getLoteList()) {
                OrdenProduccion oldIdCodigoOrdenProduccionOfLoteListLote = loteListLote.getIdCodigoOrdenProduccion();
                loteListLote.setIdCodigoOrdenProduccion(ordenProduccion);
                loteListLote = em.merge(loteListLote);
                if (oldIdCodigoOrdenProduccionOfLoteListLote != null) {
                    oldIdCodigoOrdenProduccionOfLoteListLote.getLoteList().remove(loteListLote);
                    oldIdCodigoOrdenProduccionOfLoteListLote = em.merge(oldIdCodigoOrdenProduccionOfLoteListLote);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrdenProduccion ordenProduccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenProduccion persistentOrdenProduccion = em.find(OrdenProduccion.class, ordenProduccion.getCodigoOrdenProduccion());
            Panadero panaderoOld = persistentOrdenProduccion.getPanadero();
            Panadero panaderoNew = ordenProduccion.getPanadero();
            Producto idProductoOld = persistentOrdenProduccion.getIdProducto();
            Producto idProductoNew = ordenProduccion.getIdProducto();
            List<Lote> loteListOld = persistentOrdenProduccion.getLoteList();
            List<Lote> loteListNew = ordenProduccion.getLoteList();
            List<String> illegalOrphanMessages = null;
            for (Lote loteListOldLote : loteListOld) {
                if (!loteListNew.contains(loteListOldLote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lote " + loteListOldLote + " since its idCodigoOrdenProduccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (panaderoNew != null) {
                panaderoNew = em.getReference(panaderoNew.getClass(), panaderoNew.getPanaderoPK());
                ordenProduccion.setPanadero(panaderoNew);
            }
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getCodigoProducto());
                ordenProduccion.setIdProducto(idProductoNew);
            }
            List<Lote> attachedLoteListNew = new ArrayList<Lote>();
            for (Lote loteListNewLoteToAttach : loteListNew) {
                loteListNewLoteToAttach = em.getReference(loteListNewLoteToAttach.getClass(), loteListNewLoteToAttach.getCodigoLote());
                attachedLoteListNew.add(loteListNewLoteToAttach);
            }
            loteListNew = attachedLoteListNew;
            ordenProduccion.setLoteList(loteListNew);
            ordenProduccion = em.merge(ordenProduccion);
            if (panaderoOld != null && !panaderoOld.equals(panaderoNew)) {
                panaderoOld.getOrdenProduccionList().remove(ordenProduccion);
                panaderoOld = em.merge(panaderoOld);
            }
            if (panaderoNew != null && !panaderoNew.equals(panaderoOld)) {
                panaderoNew.getOrdenProduccionList().add(ordenProduccion);
                panaderoNew = em.merge(panaderoNew);
            }
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getOrdenProduccionList().remove(ordenProduccion);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getOrdenProduccionList().add(ordenProduccion);
                idProductoNew = em.merge(idProductoNew);
            }
            for (Lote loteListNewLote : loteListNew) {
                if (!loteListOld.contains(loteListNewLote)) {
                    OrdenProduccion oldIdCodigoOrdenProduccionOfLoteListNewLote = loteListNewLote.getIdCodigoOrdenProduccion();
                    loteListNewLote.setIdCodigoOrdenProduccion(ordenProduccion);
                    loteListNewLote = em.merge(loteListNewLote);
                    if (oldIdCodigoOrdenProduccionOfLoteListNewLote != null && !oldIdCodigoOrdenProduccionOfLoteListNewLote.equals(ordenProduccion)) {
                        oldIdCodigoOrdenProduccionOfLoteListNewLote.getLoteList().remove(loteListNewLote);
                        oldIdCodigoOrdenProduccionOfLoteListNewLote = em.merge(oldIdCodigoOrdenProduccionOfLoteListNewLote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordenProduccion.getCodigoOrdenProduccion();
                if (findOrdenProduccion(id) == null) {
                    throw new NonexistentEntityException("The ordenProduccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenProduccion ordenProduccion;
            try {
                ordenProduccion = em.getReference(OrdenProduccion.class, id);
                ordenProduccion.getCodigoOrdenProduccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordenProduccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Lote> loteListOrphanCheck = ordenProduccion.getLoteList();
            for (Lote loteListOrphanCheckLote : loteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This OrdenProduccion (" + ordenProduccion + ") cannot be destroyed since the Lote " + loteListOrphanCheckLote + " in its loteList field has a non-nullable idCodigoOrdenProduccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Panadero panadero = ordenProduccion.getPanadero();
            if (panadero != null) {
                panadero.getOrdenProduccionList().remove(ordenProduccion);
                panadero = em.merge(panadero);
            }
            Producto idProducto = ordenProduccion.getIdProducto();
            if (idProducto != null) {
                idProducto.getOrdenProduccionList().remove(ordenProduccion);
                idProducto = em.merge(idProducto);
            }
            em.remove(ordenProduccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrdenProduccion> findOrdenProduccionEntities() {
        return findOrdenProduccionEntities(true, -1, -1);
    }

    public List<OrdenProduccion> findOrdenProduccionEntities(int maxResults, int firstResult) {
        return findOrdenProduccionEntities(false, maxResults, firstResult);
    }

    private List<OrdenProduccion> findOrdenProduccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrdenProduccion.class));
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

    public OrdenProduccion findOrdenProduccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrdenProduccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenProduccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrdenProduccion> rt = cq.from(OrdenProduccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
