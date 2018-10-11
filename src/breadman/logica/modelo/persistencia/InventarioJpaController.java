/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.persistencia;

import breadman.logica.modelo.entidades.Inventario;
import breadman.logica.modelo.entidades.InventarioPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import breadman.logica.modelo.entidades.Producto;
import breadman.logica.modelo.entidades.Lote;
import java.util.ArrayList;
import java.util.List;
import breadman.logica.modelo.entidades.Venta;
import breadman.logica.modelo.persistencia.exceptions.IllegalOrphanException;
import breadman.logica.modelo.persistencia.exceptions.NonexistentEntityException;
import breadman.logica.modelo.persistencia.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel Arnedo
 */
public class InventarioJpaController implements Serializable {

    public InventarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inventario inventario) throws PreexistingEntityException, Exception {
        if (inventario.getInventarioPK() == null) {
            inventario.setInventarioPK(new InventarioPK());
        }
        if (inventario.getLoteList() == null) {
            inventario.setLoteList(new ArrayList<Lote>());
        }
        if (inventario.getVentaList() == null) {
            inventario.setVentaList(new ArrayList<Venta>());
        }
        inventario.getInventarioPK().setIdProducto(inventario.getProducto().getCodigoProducto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto = inventario.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getCodigoProducto());
                inventario.setProducto(producto);
            }
            List<Lote> attachedLoteList = new ArrayList<Lote>();
            for (Lote loteListLoteToAttach : inventario.getLoteList()) {
                loteListLoteToAttach = em.getReference(loteListLoteToAttach.getClass(), loteListLoteToAttach.getCodigoLote());
                attachedLoteList.add(loteListLoteToAttach);
            }
            inventario.setLoteList(attachedLoteList);
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : inventario.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getCodigoVenta());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            inventario.setVentaList(attachedVentaList);
            em.persist(inventario);
            if (producto != null) {
                producto.getInventarioList().add(inventario);
                producto = em.merge(producto);
            }
            for (Lote loteListLote : inventario.getLoteList()) {
                Inventario oldInventarioOfLoteListLote = loteListLote.getInventario();
                loteListLote.setInventario(inventario);
                loteListLote = em.merge(loteListLote);
                if (oldInventarioOfLoteListLote != null) {
                    oldInventarioOfLoteListLote.getLoteList().remove(loteListLote);
                    oldInventarioOfLoteListLote = em.merge(oldInventarioOfLoteListLote);
                }
            }
            for (Venta ventaListVenta : inventario.getVentaList()) {
                Inventario oldInventarioOfVentaListVenta = ventaListVenta.getInventario();
                ventaListVenta.setInventario(inventario);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldInventarioOfVentaListVenta != null) {
                    oldInventarioOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldInventarioOfVentaListVenta = em.merge(oldInventarioOfVentaListVenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInventario(inventario.getInventarioPK()) != null) {
                throw new PreexistingEntityException("Inventario " + inventario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inventario inventario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        inventario.getInventarioPK().setIdProducto(inventario.getProducto().getCodigoProducto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventario persistentInventario = em.find(Inventario.class, inventario.getInventarioPK());
            Producto productoOld = persistentInventario.getProducto();
            Producto productoNew = inventario.getProducto();
            List<Lote> loteListOld = persistentInventario.getLoteList();
            List<Lote> loteListNew = inventario.getLoteList();
            List<Venta> ventaListOld = persistentInventario.getVentaList();
            List<Venta> ventaListNew = inventario.getVentaList();
            List<String> illegalOrphanMessages = null;
            for (Lote loteListOldLote : loteListOld) {
                if (!loteListNew.contains(loteListOldLote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lote " + loteListOldLote + " since its inventario field is not nullable.");
                }
            }
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Venta " + ventaListOldVenta + " since its inventario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getCodigoProducto());
                inventario.setProducto(productoNew);
            }
            List<Lote> attachedLoteListNew = new ArrayList<Lote>();
            for (Lote loteListNewLoteToAttach : loteListNew) {
                loteListNewLoteToAttach = em.getReference(loteListNewLoteToAttach.getClass(), loteListNewLoteToAttach.getCodigoLote());
                attachedLoteListNew.add(loteListNewLoteToAttach);
            }
            loteListNew = attachedLoteListNew;
            inventario.setLoteList(loteListNew);
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getCodigoVenta());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            inventario.setVentaList(ventaListNew);
            inventario = em.merge(inventario);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getInventarioList().remove(inventario);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getInventarioList().add(inventario);
                productoNew = em.merge(productoNew);
            }
            for (Lote loteListNewLote : loteListNew) {
                if (!loteListOld.contains(loteListNewLote)) {
                    Inventario oldInventarioOfLoteListNewLote = loteListNewLote.getInventario();
                    loteListNewLote.setInventario(inventario);
                    loteListNewLote = em.merge(loteListNewLote);
                    if (oldInventarioOfLoteListNewLote != null && !oldInventarioOfLoteListNewLote.equals(inventario)) {
                        oldInventarioOfLoteListNewLote.getLoteList().remove(loteListNewLote);
                        oldInventarioOfLoteListNewLote = em.merge(oldInventarioOfLoteListNewLote);
                    }
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Inventario oldInventarioOfVentaListNewVenta = ventaListNewVenta.getInventario();
                    ventaListNewVenta.setInventario(inventario);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldInventarioOfVentaListNewVenta != null && !oldInventarioOfVentaListNewVenta.equals(inventario)) {
                        oldInventarioOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldInventarioOfVentaListNewVenta = em.merge(oldInventarioOfVentaListNewVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                InventarioPK id = inventario.getInventarioPK();
                if (findInventario(id) == null) {
                    throw new NonexistentEntityException("The inventario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(InventarioPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventario inventario;
            try {
                inventario = em.getReference(Inventario.class, id);
                inventario.getInventarioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inventario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Lote> loteListOrphanCheck = inventario.getLoteList();
            for (Lote loteListOrphanCheckLote : loteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inventario (" + inventario + ") cannot be destroyed since the Lote " + loteListOrphanCheckLote + " in its loteList field has a non-nullable inventario field.");
            }
            List<Venta> ventaListOrphanCheck = inventario.getVentaList();
            for (Venta ventaListOrphanCheckVenta : ventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inventario (" + inventario + ") cannot be destroyed since the Venta " + ventaListOrphanCheckVenta + " in its ventaList field has a non-nullable inventario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Producto producto = inventario.getProducto();
            if (producto != null) {
                producto.getInventarioList().remove(inventario);
                producto = em.merge(producto);
            }
            em.remove(inventario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inventario> findInventarioEntities() {
        return findInventarioEntities(true, -1, -1);
    }

    public List<Inventario> findInventarioEntities(int maxResults, int firstResult) {
        return findInventarioEntities(false, maxResults, firstResult);
    }

    private List<Inventario> findInventarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inventario.class));
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

    public Inventario findInventario(InventarioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inventario.class, id);
        } finally {
            em.close();
        }
    }

    public int getInventarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inventario> rt = cq.from(Inventario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
