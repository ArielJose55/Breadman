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
import breadman.logica.modelo.entidades.Categoria;
import breadman.logica.modelo.entidades.Ingrediente;
import java.util.ArrayList;
import java.util.List;
import breadman.logica.modelo.entidades.Inventario;
import breadman.logica.modelo.entidades.OrdenProduccion;
import breadman.logica.modelo.entidades.Producto;
import breadman.logica.modelo.persistencia.exceptions.IllegalOrphanException;
import breadman.logica.modelo.persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel Arnedo
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getIngredienteList() == null) {
            producto.setIngredienteList(new ArrayList<Ingrediente>());
        }
        if (producto.getInventarioList() == null) {
            producto.setInventarioList(new ArrayList<Inventario>());
        }
        if (producto.getOrdenProduccionList() == null) {
            producto.setOrdenProduccionList(new ArrayList<OrdenProduccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria idCategoria = producto.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getCodigoCategoria());
                producto.setIdCategoria(idCategoria);
            }
            List<Ingrediente> attachedIngredienteList = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListIngredienteToAttach : producto.getIngredienteList()) {
                ingredienteListIngredienteToAttach = em.getReference(ingredienteListIngredienteToAttach.getClass(), ingredienteListIngredienteToAttach.getCodigoIngrediente());
                attachedIngredienteList.add(ingredienteListIngredienteToAttach);
            }
            producto.setIngredienteList(attachedIngredienteList);
            List<Inventario> attachedInventarioList = new ArrayList<Inventario>();
            for (Inventario inventarioListInventarioToAttach : producto.getInventarioList()) {
                inventarioListInventarioToAttach = em.getReference(inventarioListInventarioToAttach.getClass(), inventarioListInventarioToAttach.getInventarioPK());
                attachedInventarioList.add(inventarioListInventarioToAttach);
            }
            producto.setInventarioList(attachedInventarioList);
            List<OrdenProduccion> attachedOrdenProduccionList = new ArrayList<OrdenProduccion>();
            for (OrdenProduccion ordenProduccionListOrdenProduccionToAttach : producto.getOrdenProduccionList()) {
                ordenProduccionListOrdenProduccionToAttach = em.getReference(ordenProduccionListOrdenProduccionToAttach.getClass(), ordenProduccionListOrdenProduccionToAttach.getCodigoOrdenProduccion());
                attachedOrdenProduccionList.add(ordenProduccionListOrdenProduccionToAttach);
            }
            producto.setOrdenProduccionList(attachedOrdenProduccionList);
            em.persist(producto);
            if (idCategoria != null) {
                idCategoria.getProductoList().add(producto);
                idCategoria = em.merge(idCategoria);
            }
            for (Ingrediente ingredienteListIngrediente : producto.getIngredienteList()) {
                Producto oldIdProductoOfIngredienteListIngrediente = ingredienteListIngrediente.getIdProducto();
                ingredienteListIngrediente.setIdProducto(producto);
                ingredienteListIngrediente = em.merge(ingredienteListIngrediente);
                if (oldIdProductoOfIngredienteListIngrediente != null) {
                    oldIdProductoOfIngredienteListIngrediente.getIngredienteList().remove(ingredienteListIngrediente);
                    oldIdProductoOfIngredienteListIngrediente = em.merge(oldIdProductoOfIngredienteListIngrediente);
                }
            }
            for (Inventario inventarioListInventario : producto.getInventarioList()) {
                Producto oldProductoOfInventarioListInventario = inventarioListInventario.getProducto();
                inventarioListInventario.setProducto(producto);
                inventarioListInventario = em.merge(inventarioListInventario);
                if (oldProductoOfInventarioListInventario != null) {
                    oldProductoOfInventarioListInventario.getInventarioList().remove(inventarioListInventario);
                    oldProductoOfInventarioListInventario = em.merge(oldProductoOfInventarioListInventario);
                }
            }
            for (OrdenProduccion ordenProduccionListOrdenProduccion : producto.getOrdenProduccionList()) {
                Producto oldIdProductoOfOrdenProduccionListOrdenProduccion = ordenProduccionListOrdenProduccion.getIdProducto();
                ordenProduccionListOrdenProduccion.setIdProducto(producto);
                ordenProduccionListOrdenProduccion = em.merge(ordenProduccionListOrdenProduccion);
                if (oldIdProductoOfOrdenProduccionListOrdenProduccion != null) {
                    oldIdProductoOfOrdenProduccionListOrdenProduccion.getOrdenProduccionList().remove(ordenProduccionListOrdenProduccion);
                    oldIdProductoOfOrdenProduccionListOrdenProduccion = em.merge(oldIdProductoOfOrdenProduccionListOrdenProduccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getCodigoProducto());
            Categoria idCategoriaOld = persistentProducto.getIdCategoria();
            Categoria idCategoriaNew = producto.getIdCategoria();
            List<Ingrediente> ingredienteListOld = persistentProducto.getIngredienteList();
            List<Ingrediente> ingredienteListNew = producto.getIngredienteList();
            List<Inventario> inventarioListOld = persistentProducto.getInventarioList();
            List<Inventario> inventarioListNew = producto.getInventarioList();
            List<OrdenProduccion> ordenProduccionListOld = persistentProducto.getOrdenProduccionList();
            List<OrdenProduccion> ordenProduccionListNew = producto.getOrdenProduccionList();
            List<String> illegalOrphanMessages = null;
            for (Ingrediente ingredienteListOldIngrediente : ingredienteListOld) {
                if (!ingredienteListNew.contains(ingredienteListOldIngrediente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ingrediente " + ingredienteListOldIngrediente + " since its idProducto field is not nullable.");
                }
            }
            for (Inventario inventarioListOldInventario : inventarioListOld) {
                if (!inventarioListNew.contains(inventarioListOldInventario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inventario " + inventarioListOldInventario + " since its producto field is not nullable.");
                }
            }
            for (OrdenProduccion ordenProduccionListOldOrdenProduccion : ordenProduccionListOld) {
                if (!ordenProduccionListNew.contains(ordenProduccionListOldOrdenProduccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrdenProduccion " + ordenProduccionListOldOrdenProduccion + " since its idProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getCodigoCategoria());
                producto.setIdCategoria(idCategoriaNew);
            }
            List<Ingrediente> attachedIngredienteListNew = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListNewIngredienteToAttach : ingredienteListNew) {
                ingredienteListNewIngredienteToAttach = em.getReference(ingredienteListNewIngredienteToAttach.getClass(), ingredienteListNewIngredienteToAttach.getCodigoIngrediente());
                attachedIngredienteListNew.add(ingredienteListNewIngredienteToAttach);
            }
            ingredienteListNew = attachedIngredienteListNew;
            producto.setIngredienteList(ingredienteListNew);
            List<Inventario> attachedInventarioListNew = new ArrayList<Inventario>();
            for (Inventario inventarioListNewInventarioToAttach : inventarioListNew) {
                inventarioListNewInventarioToAttach = em.getReference(inventarioListNewInventarioToAttach.getClass(), inventarioListNewInventarioToAttach.getInventarioPK());
                attachedInventarioListNew.add(inventarioListNewInventarioToAttach);
            }
            inventarioListNew = attachedInventarioListNew;
            producto.setInventarioList(inventarioListNew);
            List<OrdenProduccion> attachedOrdenProduccionListNew = new ArrayList<OrdenProduccion>();
            for (OrdenProduccion ordenProduccionListNewOrdenProduccionToAttach : ordenProduccionListNew) {
                ordenProduccionListNewOrdenProduccionToAttach = em.getReference(ordenProduccionListNewOrdenProduccionToAttach.getClass(), ordenProduccionListNewOrdenProduccionToAttach.getCodigoOrdenProduccion());
                attachedOrdenProduccionListNew.add(ordenProduccionListNewOrdenProduccionToAttach);
            }
            ordenProduccionListNew = attachedOrdenProduccionListNew;
            producto.setOrdenProduccionList(ordenProduccionListNew);
            producto = em.merge(producto);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getProductoList().remove(producto);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getProductoList().add(producto);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            for (Ingrediente ingredienteListNewIngrediente : ingredienteListNew) {
                if (!ingredienteListOld.contains(ingredienteListNewIngrediente)) {
                    Producto oldIdProductoOfIngredienteListNewIngrediente = ingredienteListNewIngrediente.getIdProducto();
                    ingredienteListNewIngrediente.setIdProducto(producto);
                    ingredienteListNewIngrediente = em.merge(ingredienteListNewIngrediente);
                    if (oldIdProductoOfIngredienteListNewIngrediente != null && !oldIdProductoOfIngredienteListNewIngrediente.equals(producto)) {
                        oldIdProductoOfIngredienteListNewIngrediente.getIngredienteList().remove(ingredienteListNewIngrediente);
                        oldIdProductoOfIngredienteListNewIngrediente = em.merge(oldIdProductoOfIngredienteListNewIngrediente);
                    }
                }
            }
            for (Inventario inventarioListNewInventario : inventarioListNew) {
                if (!inventarioListOld.contains(inventarioListNewInventario)) {
                    Producto oldProductoOfInventarioListNewInventario = inventarioListNewInventario.getProducto();
                    inventarioListNewInventario.setProducto(producto);
                    inventarioListNewInventario = em.merge(inventarioListNewInventario);
                    if (oldProductoOfInventarioListNewInventario != null && !oldProductoOfInventarioListNewInventario.equals(producto)) {
                        oldProductoOfInventarioListNewInventario.getInventarioList().remove(inventarioListNewInventario);
                        oldProductoOfInventarioListNewInventario = em.merge(oldProductoOfInventarioListNewInventario);
                    }
                }
            }
            for (OrdenProduccion ordenProduccionListNewOrdenProduccion : ordenProduccionListNew) {
                if (!ordenProduccionListOld.contains(ordenProduccionListNewOrdenProduccion)) {
                    Producto oldIdProductoOfOrdenProduccionListNewOrdenProduccion = ordenProduccionListNewOrdenProduccion.getIdProducto();
                    ordenProduccionListNewOrdenProduccion.setIdProducto(producto);
                    ordenProduccionListNewOrdenProduccion = em.merge(ordenProduccionListNewOrdenProduccion);
                    if (oldIdProductoOfOrdenProduccionListNewOrdenProduccion != null && !oldIdProductoOfOrdenProduccionListNewOrdenProduccion.equals(producto)) {
                        oldIdProductoOfOrdenProduccionListNewOrdenProduccion.getOrdenProduccionList().remove(ordenProduccionListNewOrdenProduccion);
                        oldIdProductoOfOrdenProduccionListNewOrdenProduccion = em.merge(oldIdProductoOfOrdenProduccionListNewOrdenProduccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getCodigoProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getCodigoProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ingrediente> ingredienteListOrphanCheck = producto.getIngredienteList();
            for (Ingrediente ingredienteListOrphanCheckIngrediente : ingredienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Ingrediente " + ingredienteListOrphanCheckIngrediente + " in its ingredienteList field has a non-nullable idProducto field.");
            }
            List<Inventario> inventarioListOrphanCheck = producto.getInventarioList();
            for (Inventario inventarioListOrphanCheckInventario : inventarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Inventario " + inventarioListOrphanCheckInventario + " in its inventarioList field has a non-nullable producto field.");
            }
            List<OrdenProduccion> ordenProduccionListOrphanCheck = producto.getOrdenProduccionList();
            for (OrdenProduccion ordenProduccionListOrphanCheckOrdenProduccion : ordenProduccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the OrdenProduccion " + ordenProduccionListOrphanCheckOrdenProduccion + " in its ordenProduccionList field has a non-nullable idProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categoria idCategoria = producto.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getProductoList().remove(producto);
                idCategoria = em.merge(idCategoria);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
