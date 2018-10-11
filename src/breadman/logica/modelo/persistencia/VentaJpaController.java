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
import breadman.logica.modelo.entidades.Inventario;
import breadman.logica.modelo.entidades.Vendedor;
import breadman.logica.modelo.entidades.Venta;
import breadman.logica.modelo.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel Arnedo
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta venta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventario inventario = venta.getInventario();
            if (inventario != null) {
                inventario = em.getReference(inventario.getClass(), inventario.getInventarioPK());
                venta.setInventario(inventario);
            }
            Vendedor vendedor = venta.getVendedor();
            if (vendedor != null) {
                vendedor = em.getReference(vendedor.getClass(), vendedor.getVendedorPK());
                venta.setVendedor(vendedor);
            }
            em.persist(venta);
            if (inventario != null) {
                inventario.getVentaList().add(venta);
                inventario = em.merge(inventario);
            }
            if (vendedor != null) {
                vendedor.getVentaList().add(venta);
                vendedor = em.merge(vendedor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta venta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta persistentVenta = em.find(Venta.class, venta.getCodigoVenta());
            Inventario inventarioOld = persistentVenta.getInventario();
            Inventario inventarioNew = venta.getInventario();
            Vendedor vendedorOld = persistentVenta.getVendedor();
            Vendedor vendedorNew = venta.getVendedor();
            if (inventarioNew != null) {
                inventarioNew = em.getReference(inventarioNew.getClass(), inventarioNew.getInventarioPK());
                venta.setInventario(inventarioNew);
            }
            if (vendedorNew != null) {
                vendedorNew = em.getReference(vendedorNew.getClass(), vendedorNew.getVendedorPK());
                venta.setVendedor(vendedorNew);
            }
            venta = em.merge(venta);
            if (inventarioOld != null && !inventarioOld.equals(inventarioNew)) {
                inventarioOld.getVentaList().remove(venta);
                inventarioOld = em.merge(inventarioOld);
            }
            if (inventarioNew != null && !inventarioNew.equals(inventarioOld)) {
                inventarioNew.getVentaList().add(venta);
                inventarioNew = em.merge(inventarioNew);
            }
            if (vendedorOld != null && !vendedorOld.equals(vendedorNew)) {
                vendedorOld.getVentaList().remove(venta);
                vendedorOld = em.merge(vendedorOld);
            }
            if (vendedorNew != null && !vendedorNew.equals(vendedorOld)) {
                vendedorNew.getVentaList().add(venta);
                vendedorNew = em.merge(vendedorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venta.getCodigoVenta();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
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
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getCodigoVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            Inventario inventario = venta.getInventario();
            if (inventario != null) {
                inventario.getVentaList().remove(venta);
                inventario = em.merge(inventario);
            }
            Vendedor vendedor = venta.getVendedor();
            if (vendedor != null) {
                vendedor.getVentaList().remove(venta);
                vendedor = em.merge(vendedor);
            }
            em.remove(venta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
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

    public Venta findVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
