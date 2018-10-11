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
import breadman.logica.modelo.entidades.OrdenDeCompraInsumo;
import breadman.logica.modelo.entidades.Proveedor;
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
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) {
        if (proveedor.getOrdenDeCompraInsumoList() == null) {
            proveedor.setOrdenDeCompraInsumoList(new ArrayList<OrdenDeCompraInsumo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<OrdenDeCompraInsumo> attachedOrdenDeCompraInsumoList = new ArrayList<OrdenDeCompraInsumo>();
            for (OrdenDeCompraInsumo ordenDeCompraInsumoListOrdenDeCompraInsumoToAttach : proveedor.getOrdenDeCompraInsumoList()) {
                ordenDeCompraInsumoListOrdenDeCompraInsumoToAttach = em.getReference(ordenDeCompraInsumoListOrdenDeCompraInsumoToAttach.getClass(), ordenDeCompraInsumoListOrdenDeCompraInsumoToAttach.getCodigoCompraInsumo());
                attachedOrdenDeCompraInsumoList.add(ordenDeCompraInsumoListOrdenDeCompraInsumoToAttach);
            }
            proveedor.setOrdenDeCompraInsumoList(attachedOrdenDeCompraInsumoList);
            em.persist(proveedor);
            for (OrdenDeCompraInsumo ordenDeCompraInsumoListOrdenDeCompraInsumo : proveedor.getOrdenDeCompraInsumoList()) {
                Proveedor oldNitProveedorOfOrdenDeCompraInsumoListOrdenDeCompraInsumo = ordenDeCompraInsumoListOrdenDeCompraInsumo.getNitProveedor();
                ordenDeCompraInsumoListOrdenDeCompraInsumo.setNitProveedor(proveedor);
                ordenDeCompraInsumoListOrdenDeCompraInsumo = em.merge(ordenDeCompraInsumoListOrdenDeCompraInsumo);
                if (oldNitProveedorOfOrdenDeCompraInsumoListOrdenDeCompraInsumo != null) {
                    oldNitProveedorOfOrdenDeCompraInsumoListOrdenDeCompraInsumo.getOrdenDeCompraInsumoList().remove(ordenDeCompraInsumoListOrdenDeCompraInsumo);
                    oldNitProveedorOfOrdenDeCompraInsumoListOrdenDeCompraInsumo = em.merge(oldNitProveedorOfOrdenDeCompraInsumoListOrdenDeCompraInsumo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getCodigoProveedor());
            List<OrdenDeCompraInsumo> ordenDeCompraInsumoListOld = persistentProveedor.getOrdenDeCompraInsumoList();
            List<OrdenDeCompraInsumo> ordenDeCompraInsumoListNew = proveedor.getOrdenDeCompraInsumoList();
            List<String> illegalOrphanMessages = null;
            for (OrdenDeCompraInsumo ordenDeCompraInsumoListOldOrdenDeCompraInsumo : ordenDeCompraInsumoListOld) {
                if (!ordenDeCompraInsumoListNew.contains(ordenDeCompraInsumoListOldOrdenDeCompraInsumo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrdenDeCompraInsumo " + ordenDeCompraInsumoListOldOrdenDeCompraInsumo + " since its nitProveedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<OrdenDeCompraInsumo> attachedOrdenDeCompraInsumoListNew = new ArrayList<OrdenDeCompraInsumo>();
            for (OrdenDeCompraInsumo ordenDeCompraInsumoListNewOrdenDeCompraInsumoToAttach : ordenDeCompraInsumoListNew) {
                ordenDeCompraInsumoListNewOrdenDeCompraInsumoToAttach = em.getReference(ordenDeCompraInsumoListNewOrdenDeCompraInsumoToAttach.getClass(), ordenDeCompraInsumoListNewOrdenDeCompraInsumoToAttach.getCodigoCompraInsumo());
                attachedOrdenDeCompraInsumoListNew.add(ordenDeCompraInsumoListNewOrdenDeCompraInsumoToAttach);
            }
            ordenDeCompraInsumoListNew = attachedOrdenDeCompraInsumoListNew;
            proveedor.setOrdenDeCompraInsumoList(ordenDeCompraInsumoListNew);
            proveedor = em.merge(proveedor);
            for (OrdenDeCompraInsumo ordenDeCompraInsumoListNewOrdenDeCompraInsumo : ordenDeCompraInsumoListNew) {
                if (!ordenDeCompraInsumoListOld.contains(ordenDeCompraInsumoListNewOrdenDeCompraInsumo)) {
                    Proveedor oldNitProveedorOfOrdenDeCompraInsumoListNewOrdenDeCompraInsumo = ordenDeCompraInsumoListNewOrdenDeCompraInsumo.getNitProveedor();
                    ordenDeCompraInsumoListNewOrdenDeCompraInsumo.setNitProveedor(proveedor);
                    ordenDeCompraInsumoListNewOrdenDeCompraInsumo = em.merge(ordenDeCompraInsumoListNewOrdenDeCompraInsumo);
                    if (oldNitProveedorOfOrdenDeCompraInsumoListNewOrdenDeCompraInsumo != null && !oldNitProveedorOfOrdenDeCompraInsumoListNewOrdenDeCompraInsumo.equals(proveedor)) {
                        oldNitProveedorOfOrdenDeCompraInsumoListNewOrdenDeCompraInsumo.getOrdenDeCompraInsumoList().remove(ordenDeCompraInsumoListNewOrdenDeCompraInsumo);
                        oldNitProveedorOfOrdenDeCompraInsumoListNewOrdenDeCompraInsumo = em.merge(oldNitProveedorOfOrdenDeCompraInsumoListNewOrdenDeCompraInsumo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedor.getCodigoProveedor();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
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
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getCodigoProveedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<OrdenDeCompraInsumo> ordenDeCompraInsumoListOrphanCheck = proveedor.getOrdenDeCompraInsumoList();
            for (OrdenDeCompraInsumo ordenDeCompraInsumoListOrphanCheckOrdenDeCompraInsumo : ordenDeCompraInsumoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the OrdenDeCompraInsumo " + ordenDeCompraInsumoListOrphanCheckOrdenDeCompraInsumo + " in its ordenDeCompraInsumoList field has a non-nullable nitProveedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
