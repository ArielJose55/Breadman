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
import breadman.logica.modelo.entidades.Insumo;
import breadman.logica.modelo.entidades.OrdenDeCompraInsumo;
import breadman.logica.modelo.entidades.Proveedor;
import breadman.logica.modelo.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel Arnedo
 */
public class OrdenDeCompraInsumoJpaController implements Serializable {

    public OrdenDeCompraInsumoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrdenDeCompraInsumo ordenDeCompraInsumo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Insumo idInsumo = ordenDeCompraInsumo.getIdInsumo();
            if (idInsumo != null) {
                idInsumo = em.getReference(idInsumo.getClass(), idInsumo.getCodigoInsumo());
                ordenDeCompraInsumo.setIdInsumo(idInsumo);
            }
            Proveedor nitProveedor = ordenDeCompraInsumo.getNitProveedor();
            if (nitProveedor != null) {
                nitProveedor = em.getReference(nitProveedor.getClass(), nitProveedor.getCodigoProveedor());
                ordenDeCompraInsumo.setNitProveedor(nitProveedor);
            }
            em.persist(ordenDeCompraInsumo);
            if (idInsumo != null) {
                idInsumo.getOrdenDeCompraInsumoList().add(ordenDeCompraInsumo);
                idInsumo = em.merge(idInsumo);
            }
            if (nitProveedor != null) {
                nitProveedor.getOrdenDeCompraInsumoList().add(ordenDeCompraInsumo);
                nitProveedor = em.merge(nitProveedor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrdenDeCompraInsumo ordenDeCompraInsumo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenDeCompraInsumo persistentOrdenDeCompraInsumo = em.find(OrdenDeCompraInsumo.class, ordenDeCompraInsumo.getCodigoCompraInsumo());
            Insumo idInsumoOld = persistentOrdenDeCompraInsumo.getIdInsumo();
            Insumo idInsumoNew = ordenDeCompraInsumo.getIdInsumo();
            Proveedor nitProveedorOld = persistentOrdenDeCompraInsumo.getNitProveedor();
            Proveedor nitProveedorNew = ordenDeCompraInsumo.getNitProveedor();
            if (idInsumoNew != null) {
                idInsumoNew = em.getReference(idInsumoNew.getClass(), idInsumoNew.getCodigoInsumo());
                ordenDeCompraInsumo.setIdInsumo(idInsumoNew);
            }
            if (nitProveedorNew != null) {
                nitProveedorNew = em.getReference(nitProveedorNew.getClass(), nitProveedorNew.getCodigoProveedor());
                ordenDeCompraInsumo.setNitProveedor(nitProveedorNew);
            }
            ordenDeCompraInsumo = em.merge(ordenDeCompraInsumo);
            if (idInsumoOld != null && !idInsumoOld.equals(idInsumoNew)) {
                idInsumoOld.getOrdenDeCompraInsumoList().remove(ordenDeCompraInsumo);
                idInsumoOld = em.merge(idInsumoOld);
            }
            if (idInsumoNew != null && !idInsumoNew.equals(idInsumoOld)) {
                idInsumoNew.getOrdenDeCompraInsumoList().add(ordenDeCompraInsumo);
                idInsumoNew = em.merge(idInsumoNew);
            }
            if (nitProveedorOld != null && !nitProveedorOld.equals(nitProveedorNew)) {
                nitProveedorOld.getOrdenDeCompraInsumoList().remove(ordenDeCompraInsumo);
                nitProveedorOld = em.merge(nitProveedorOld);
            }
            if (nitProveedorNew != null && !nitProveedorNew.equals(nitProveedorOld)) {
                nitProveedorNew.getOrdenDeCompraInsumoList().add(ordenDeCompraInsumo);
                nitProveedorNew = em.merge(nitProveedorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordenDeCompraInsumo.getCodigoCompraInsumo();
                if (findOrdenDeCompraInsumo(id) == null) {
                    throw new NonexistentEntityException("The ordenDeCompraInsumo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenDeCompraInsumo ordenDeCompraInsumo;
            try {
                ordenDeCompraInsumo = em.getReference(OrdenDeCompraInsumo.class, id);
                ordenDeCompraInsumo.getCodigoCompraInsumo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordenDeCompraInsumo with id " + id + " no longer exists.", enfe);
            }
            Insumo idInsumo = ordenDeCompraInsumo.getIdInsumo();
            if (idInsumo != null) {
                idInsumo.getOrdenDeCompraInsumoList().remove(ordenDeCompraInsumo);
                idInsumo = em.merge(idInsumo);
            }
            Proveedor nitProveedor = ordenDeCompraInsumo.getNitProveedor();
            if (nitProveedor != null) {
                nitProveedor.getOrdenDeCompraInsumoList().remove(ordenDeCompraInsumo);
                nitProveedor = em.merge(nitProveedor);
            }
            em.remove(ordenDeCompraInsumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrdenDeCompraInsumo> findOrdenDeCompraInsumoEntities() {
        return findOrdenDeCompraInsumoEntities(true, -1, -1);
    }

    public List<OrdenDeCompraInsumo> findOrdenDeCompraInsumoEntities(int maxResults, int firstResult) {
        return findOrdenDeCompraInsumoEntities(false, maxResults, firstResult);
    }

    private List<OrdenDeCompraInsumo> findOrdenDeCompraInsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrdenDeCompraInsumo.class));
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

    public OrdenDeCompraInsumo findOrdenDeCompraInsumo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrdenDeCompraInsumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenDeCompraInsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrdenDeCompraInsumo> rt = cq.from(OrdenDeCompraInsumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
