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
import breadman.logica.modelo.entidades.Ingrediente;
import breadman.logica.modelo.entidades.Insumo;
import java.util.ArrayList;
import java.util.List;
import breadman.logica.modelo.entidades.SolicitudDeCompra;
import breadman.logica.modelo.entidades.OrdenDeCompraInsumo;
import breadman.logica.modelo.persistencia.exceptions.IllegalOrphanException;
import breadman.logica.modelo.persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel Arnedo
 */
public class InsumoJpaController implements Serializable {

    public InsumoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Insumo insumo) {
        if (insumo.getIngredienteList() == null) {
            insumo.setIngredienteList(new ArrayList<Ingrediente>());
        }
        if (insumo.getSolicitudDeCompraList() == null) {
            insumo.setSolicitudDeCompraList(new ArrayList<SolicitudDeCompra>());
        }
        if (insumo.getOrdenDeCompraInsumoList() == null) {
            insumo.setOrdenDeCompraInsumoList(new ArrayList<OrdenDeCompraInsumo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ingrediente> attachedIngredienteList = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListIngredienteToAttach : insumo.getIngredienteList()) {
                ingredienteListIngredienteToAttach = em.getReference(ingredienteListIngredienteToAttach.getClass(), ingredienteListIngredienteToAttach.getCodigoIngrediente());
                attachedIngredienteList.add(ingredienteListIngredienteToAttach);
            }
            insumo.setIngredienteList(attachedIngredienteList);
            List<SolicitudDeCompra> attachedSolicitudDeCompraList = new ArrayList<SolicitudDeCompra>();
            for (SolicitudDeCompra solicitudDeCompraListSolicitudDeCompraToAttach : insumo.getSolicitudDeCompraList()) {
                solicitudDeCompraListSolicitudDeCompraToAttach = em.getReference(solicitudDeCompraListSolicitudDeCompraToAttach.getClass(), solicitudDeCompraListSolicitudDeCompraToAttach.getCodigoSolicitud());
                attachedSolicitudDeCompraList.add(solicitudDeCompraListSolicitudDeCompraToAttach);
            }
            insumo.setSolicitudDeCompraList(attachedSolicitudDeCompraList);
            List<OrdenDeCompraInsumo> attachedOrdenDeCompraInsumoList = new ArrayList<OrdenDeCompraInsumo>();
            for (OrdenDeCompraInsumo ordenDeCompraInsumoListOrdenDeCompraInsumoToAttach : insumo.getOrdenDeCompraInsumoList()) {
                ordenDeCompraInsumoListOrdenDeCompraInsumoToAttach = em.getReference(ordenDeCompraInsumoListOrdenDeCompraInsumoToAttach.getClass(), ordenDeCompraInsumoListOrdenDeCompraInsumoToAttach.getCodigoCompraInsumo());
                attachedOrdenDeCompraInsumoList.add(ordenDeCompraInsumoListOrdenDeCompraInsumoToAttach);
            }
            insumo.setOrdenDeCompraInsumoList(attachedOrdenDeCompraInsumoList);
            em.persist(insumo);
            for (Ingrediente ingredienteListIngrediente : insumo.getIngredienteList()) {
                Insumo oldIdInsumoOfIngredienteListIngrediente = ingredienteListIngrediente.getIdInsumo();
                ingredienteListIngrediente.setIdInsumo(insumo);
                ingredienteListIngrediente = em.merge(ingredienteListIngrediente);
                if (oldIdInsumoOfIngredienteListIngrediente != null) {
                    oldIdInsumoOfIngredienteListIngrediente.getIngredienteList().remove(ingredienteListIngrediente);
                    oldIdInsumoOfIngredienteListIngrediente = em.merge(oldIdInsumoOfIngredienteListIngrediente);
                }
            }
            for (SolicitudDeCompra solicitudDeCompraListSolicitudDeCompra : insumo.getSolicitudDeCompraList()) {
                Insumo oldIdInsumoOfSolicitudDeCompraListSolicitudDeCompra = solicitudDeCompraListSolicitudDeCompra.getIdInsumo();
                solicitudDeCompraListSolicitudDeCompra.setIdInsumo(insumo);
                solicitudDeCompraListSolicitudDeCompra = em.merge(solicitudDeCompraListSolicitudDeCompra);
                if (oldIdInsumoOfSolicitudDeCompraListSolicitudDeCompra != null) {
                    oldIdInsumoOfSolicitudDeCompraListSolicitudDeCompra.getSolicitudDeCompraList().remove(solicitudDeCompraListSolicitudDeCompra);
                    oldIdInsumoOfSolicitudDeCompraListSolicitudDeCompra = em.merge(oldIdInsumoOfSolicitudDeCompraListSolicitudDeCompra);
                }
            }
            for (OrdenDeCompraInsumo ordenDeCompraInsumoListOrdenDeCompraInsumo : insumo.getOrdenDeCompraInsumoList()) {
                Insumo oldIdInsumoOfOrdenDeCompraInsumoListOrdenDeCompraInsumo = ordenDeCompraInsumoListOrdenDeCompraInsumo.getIdInsumo();
                ordenDeCompraInsumoListOrdenDeCompraInsumo.setIdInsumo(insumo);
                ordenDeCompraInsumoListOrdenDeCompraInsumo = em.merge(ordenDeCompraInsumoListOrdenDeCompraInsumo);
                if (oldIdInsumoOfOrdenDeCompraInsumoListOrdenDeCompraInsumo != null) {
                    oldIdInsumoOfOrdenDeCompraInsumoListOrdenDeCompraInsumo.getOrdenDeCompraInsumoList().remove(ordenDeCompraInsumoListOrdenDeCompraInsumo);
                    oldIdInsumoOfOrdenDeCompraInsumoListOrdenDeCompraInsumo = em.merge(oldIdInsumoOfOrdenDeCompraInsumoListOrdenDeCompraInsumo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Insumo insumo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Insumo persistentInsumo = em.find(Insumo.class, insumo.getCodigoInsumo());
            List<Ingrediente> ingredienteListOld = persistentInsumo.getIngredienteList();
            List<Ingrediente> ingredienteListNew = insumo.getIngredienteList();
            List<SolicitudDeCompra> solicitudDeCompraListOld = persistentInsumo.getSolicitudDeCompraList();
            List<SolicitudDeCompra> solicitudDeCompraListNew = insumo.getSolicitudDeCompraList();
            List<OrdenDeCompraInsumo> ordenDeCompraInsumoListOld = persistentInsumo.getOrdenDeCompraInsumoList();
            List<OrdenDeCompraInsumo> ordenDeCompraInsumoListNew = insumo.getOrdenDeCompraInsumoList();
            List<String> illegalOrphanMessages = null;
            for (Ingrediente ingredienteListOldIngrediente : ingredienteListOld) {
                if (!ingredienteListNew.contains(ingredienteListOldIngrediente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ingrediente " + ingredienteListOldIngrediente + " since its idInsumo field is not nullable.");
                }
            }
            for (SolicitudDeCompra solicitudDeCompraListOldSolicitudDeCompra : solicitudDeCompraListOld) {
                if (!solicitudDeCompraListNew.contains(solicitudDeCompraListOldSolicitudDeCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudDeCompra " + solicitudDeCompraListOldSolicitudDeCompra + " since its idInsumo field is not nullable.");
                }
            }
            for (OrdenDeCompraInsumo ordenDeCompraInsumoListOldOrdenDeCompraInsumo : ordenDeCompraInsumoListOld) {
                if (!ordenDeCompraInsumoListNew.contains(ordenDeCompraInsumoListOldOrdenDeCompraInsumo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrdenDeCompraInsumo " + ordenDeCompraInsumoListOldOrdenDeCompraInsumo + " since its idInsumo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ingrediente> attachedIngredienteListNew = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListNewIngredienteToAttach : ingredienteListNew) {
                ingredienteListNewIngredienteToAttach = em.getReference(ingredienteListNewIngredienteToAttach.getClass(), ingredienteListNewIngredienteToAttach.getCodigoIngrediente());
                attachedIngredienteListNew.add(ingredienteListNewIngredienteToAttach);
            }
            ingredienteListNew = attachedIngredienteListNew;
            insumo.setIngredienteList(ingredienteListNew);
            List<SolicitudDeCompra> attachedSolicitudDeCompraListNew = new ArrayList<SolicitudDeCompra>();
            for (SolicitudDeCompra solicitudDeCompraListNewSolicitudDeCompraToAttach : solicitudDeCompraListNew) {
                solicitudDeCompraListNewSolicitudDeCompraToAttach = em.getReference(solicitudDeCompraListNewSolicitudDeCompraToAttach.getClass(), solicitudDeCompraListNewSolicitudDeCompraToAttach.getCodigoSolicitud());
                attachedSolicitudDeCompraListNew.add(solicitudDeCompraListNewSolicitudDeCompraToAttach);
            }
            solicitudDeCompraListNew = attachedSolicitudDeCompraListNew;
            insumo.setSolicitudDeCompraList(solicitudDeCompraListNew);
            List<OrdenDeCompraInsumo> attachedOrdenDeCompraInsumoListNew = new ArrayList<OrdenDeCompraInsumo>();
            for (OrdenDeCompraInsumo ordenDeCompraInsumoListNewOrdenDeCompraInsumoToAttach : ordenDeCompraInsumoListNew) {
                ordenDeCompraInsumoListNewOrdenDeCompraInsumoToAttach = em.getReference(ordenDeCompraInsumoListNewOrdenDeCompraInsumoToAttach.getClass(), ordenDeCompraInsumoListNewOrdenDeCompraInsumoToAttach.getCodigoCompraInsumo());
                attachedOrdenDeCompraInsumoListNew.add(ordenDeCompraInsumoListNewOrdenDeCompraInsumoToAttach);
            }
            ordenDeCompraInsumoListNew = attachedOrdenDeCompraInsumoListNew;
            insumo.setOrdenDeCompraInsumoList(ordenDeCompraInsumoListNew);
            insumo = em.merge(insumo);
            for (Ingrediente ingredienteListNewIngrediente : ingredienteListNew) {
                if (!ingredienteListOld.contains(ingredienteListNewIngrediente)) {
                    Insumo oldIdInsumoOfIngredienteListNewIngrediente = ingredienteListNewIngrediente.getIdInsumo();
                    ingredienteListNewIngrediente.setIdInsumo(insumo);
                    ingredienteListNewIngrediente = em.merge(ingredienteListNewIngrediente);
                    if (oldIdInsumoOfIngredienteListNewIngrediente != null && !oldIdInsumoOfIngredienteListNewIngrediente.equals(insumo)) {
                        oldIdInsumoOfIngredienteListNewIngrediente.getIngredienteList().remove(ingredienteListNewIngrediente);
                        oldIdInsumoOfIngredienteListNewIngrediente = em.merge(oldIdInsumoOfIngredienteListNewIngrediente);
                    }
                }
            }
            for (SolicitudDeCompra solicitudDeCompraListNewSolicitudDeCompra : solicitudDeCompraListNew) {
                if (!solicitudDeCompraListOld.contains(solicitudDeCompraListNewSolicitudDeCompra)) {
                    Insumo oldIdInsumoOfSolicitudDeCompraListNewSolicitudDeCompra = solicitudDeCompraListNewSolicitudDeCompra.getIdInsumo();
                    solicitudDeCompraListNewSolicitudDeCompra.setIdInsumo(insumo);
                    solicitudDeCompraListNewSolicitudDeCompra = em.merge(solicitudDeCompraListNewSolicitudDeCompra);
                    if (oldIdInsumoOfSolicitudDeCompraListNewSolicitudDeCompra != null && !oldIdInsumoOfSolicitudDeCompraListNewSolicitudDeCompra.equals(insumo)) {
                        oldIdInsumoOfSolicitudDeCompraListNewSolicitudDeCompra.getSolicitudDeCompraList().remove(solicitudDeCompraListNewSolicitudDeCompra);
                        oldIdInsumoOfSolicitudDeCompraListNewSolicitudDeCompra = em.merge(oldIdInsumoOfSolicitudDeCompraListNewSolicitudDeCompra);
                    }
                }
            }
            for (OrdenDeCompraInsumo ordenDeCompraInsumoListNewOrdenDeCompraInsumo : ordenDeCompraInsumoListNew) {
                if (!ordenDeCompraInsumoListOld.contains(ordenDeCompraInsumoListNewOrdenDeCompraInsumo)) {
                    Insumo oldIdInsumoOfOrdenDeCompraInsumoListNewOrdenDeCompraInsumo = ordenDeCompraInsumoListNewOrdenDeCompraInsumo.getIdInsumo();
                    ordenDeCompraInsumoListNewOrdenDeCompraInsumo.setIdInsumo(insumo);
                    ordenDeCompraInsumoListNewOrdenDeCompraInsumo = em.merge(ordenDeCompraInsumoListNewOrdenDeCompraInsumo);
                    if (oldIdInsumoOfOrdenDeCompraInsumoListNewOrdenDeCompraInsumo != null && !oldIdInsumoOfOrdenDeCompraInsumoListNewOrdenDeCompraInsumo.equals(insumo)) {
                        oldIdInsumoOfOrdenDeCompraInsumoListNewOrdenDeCompraInsumo.getOrdenDeCompraInsumoList().remove(ordenDeCompraInsumoListNewOrdenDeCompraInsumo);
                        oldIdInsumoOfOrdenDeCompraInsumoListNewOrdenDeCompraInsumo = em.merge(oldIdInsumoOfOrdenDeCompraInsumoListNewOrdenDeCompraInsumo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = insumo.getCodigoInsumo();
                if (findInsumo(id) == null) {
                    throw new NonexistentEntityException("The insumo with id " + id + " no longer exists.");
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
            Insumo insumo;
            try {
                insumo = em.getReference(Insumo.class, id);
                insumo.getCodigoInsumo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The insumo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ingrediente> ingredienteListOrphanCheck = insumo.getIngredienteList();
            for (Ingrediente ingredienteListOrphanCheckIngrediente : ingredienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Insumo (" + insumo + ") cannot be destroyed since the Ingrediente " + ingredienteListOrphanCheckIngrediente + " in its ingredienteList field has a non-nullable idInsumo field.");
            }
            List<SolicitudDeCompra> solicitudDeCompraListOrphanCheck = insumo.getSolicitudDeCompraList();
            for (SolicitudDeCompra solicitudDeCompraListOrphanCheckSolicitudDeCompra : solicitudDeCompraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Insumo (" + insumo + ") cannot be destroyed since the SolicitudDeCompra " + solicitudDeCompraListOrphanCheckSolicitudDeCompra + " in its solicitudDeCompraList field has a non-nullable idInsumo field.");
            }
            List<OrdenDeCompraInsumo> ordenDeCompraInsumoListOrphanCheck = insumo.getOrdenDeCompraInsumoList();
            for (OrdenDeCompraInsumo ordenDeCompraInsumoListOrphanCheckOrdenDeCompraInsumo : ordenDeCompraInsumoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Insumo (" + insumo + ") cannot be destroyed since the OrdenDeCompraInsumo " + ordenDeCompraInsumoListOrphanCheckOrdenDeCompraInsumo + " in its ordenDeCompraInsumoList field has a non-nullable idInsumo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(insumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Insumo> findInsumoEntities() {
        return findInsumoEntities(true, -1, -1);
    }

    public List<Insumo> findInsumoEntities(int maxResults, int firstResult) {
        return findInsumoEntities(false, maxResults, firstResult);
    }

    private List<Insumo> findInsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Insumo.class));
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

    public Insumo findInsumo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Insumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getInsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Insumo> rt = cq.from(Insumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
