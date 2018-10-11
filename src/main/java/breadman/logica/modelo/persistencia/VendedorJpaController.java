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
import breadman.logica.modelo.entidades.Vendedor;
import breadman.logica.modelo.entidades.VendedorPK;
import breadman.logica.modelo.entidades.Venta;
import breadman.logica.modelo.persistencia.exceptions.IllegalOrphanException;
import breadman.logica.modelo.persistencia.exceptions.NonexistentEntityException;
import breadman.logica.modelo.persistencia.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel Arnedo
 */
public class VendedorJpaController implements Serializable {

    public VendedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vendedor vendedor) throws PreexistingEntityException, Exception {
        if (vendedor.getVendedorPK() == null) {
            vendedor.setVendedorPK(new VendedorPK());
        }
        if (vendedor.getVentaList() == null) {
            vendedor.setVentaList(new ArrayList<Venta>());
        }
        vendedor.getVendedorPK().setCedula(vendedor.getUsuario().getCedula());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = vendedor.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getCedula());
                vendedor.setUsuario(usuario);
            }
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : vendedor.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getCodigoVenta());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            vendedor.setVentaList(attachedVentaList);
            em.persist(vendedor);
            if (usuario != null) {
                usuario.getVendedorList().add(vendedor);
                usuario = em.merge(usuario);
            }
            for (Venta ventaListVenta : vendedor.getVentaList()) {
                Vendedor oldVendedorOfVentaListVenta = ventaListVenta.getVendedor();
                ventaListVenta.setVendedor(vendedor);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldVendedorOfVentaListVenta != null) {
                    oldVendedorOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldVendedorOfVentaListVenta = em.merge(oldVendedorOfVentaListVenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVendedor(vendedor.getVendedorPK()) != null) {
                throw new PreexistingEntityException("Vendedor " + vendedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vendedor vendedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        vendedor.getVendedorPK().setCedula(vendedor.getUsuario().getCedula());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendedor persistentVendedor = em.find(Vendedor.class, vendedor.getVendedorPK());
            Usuario usuarioOld = persistentVendedor.getUsuario();
            Usuario usuarioNew = vendedor.getUsuario();
            List<Venta> ventaListOld = persistentVendedor.getVentaList();
            List<Venta> ventaListNew = vendedor.getVentaList();
            List<String> illegalOrphanMessages = null;
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Venta " + ventaListOldVenta + " since its vendedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getCedula());
                vendedor.setUsuario(usuarioNew);
            }
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getCodigoVenta());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            vendedor.setVentaList(ventaListNew);
            vendedor = em.merge(vendedor);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getVendedorList().remove(vendedor);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getVendedorList().add(vendedor);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Vendedor oldVendedorOfVentaListNewVenta = ventaListNewVenta.getVendedor();
                    ventaListNewVenta.setVendedor(vendedor);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldVendedorOfVentaListNewVenta != null && !oldVendedorOfVentaListNewVenta.equals(vendedor)) {
                        oldVendedorOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldVendedorOfVentaListNewVenta = em.merge(oldVendedorOfVentaListNewVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                VendedorPK id = vendedor.getVendedorPK();
                if (findVendedor(id) == null) {
                    throw new NonexistentEntityException("The vendedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(VendedorPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendedor vendedor;
            try {
                vendedor = em.getReference(Vendedor.class, id);
                vendedor.getVendedorPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vendedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Venta> ventaListOrphanCheck = vendedor.getVentaList();
            for (Venta ventaListOrphanCheckVenta : ventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vendedor (" + vendedor + ") cannot be destroyed since the Venta " + ventaListOrphanCheckVenta + " in its ventaList field has a non-nullable vendedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario = vendedor.getUsuario();
            if (usuario != null) {
                usuario.getVendedorList().remove(vendedor);
                usuario = em.merge(usuario);
            }
            em.remove(vendedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vendedor> findVendedorEntities() {
        return findVendedorEntities(true, -1, -1);
    }

    public List<Vendedor> findVendedorEntities(int maxResults, int firstResult) {
        return findVendedorEntities(false, maxResults, firstResult);
    }

    private List<Vendedor> findVendedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vendedor.class));
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

    public Vendedor findVendedor(VendedorPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vendedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vendedor> rt = cq.from(Vendedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
