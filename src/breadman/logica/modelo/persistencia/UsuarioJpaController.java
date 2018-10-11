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
import breadman.logica.modelo.entidades.Vendedor;
import java.util.ArrayList;
import java.util.List;
import breadman.logica.modelo.entidades.Panadero;
import breadman.logica.modelo.entidades.Usuario;
import breadman.logica.modelo.persistencia.exceptions.IllegalOrphanException;
import breadman.logica.modelo.persistencia.exceptions.NonexistentEntityException;
import breadman.logica.modelo.persistencia.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel Arnedo
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getVendedorList() == null) {
            usuario.setVendedorList(new ArrayList<Vendedor>());
        }
        if (usuario.getPanaderoList() == null) {
            usuario.setPanaderoList(new ArrayList<Panadero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Vendedor> attachedVendedorList = new ArrayList<Vendedor>();
            for (Vendedor vendedorListVendedorToAttach : usuario.getVendedorList()) {
                vendedorListVendedorToAttach = em.getReference(vendedorListVendedorToAttach.getClass(), vendedorListVendedorToAttach.getVendedorPK());
                attachedVendedorList.add(vendedorListVendedorToAttach);
            }
            usuario.setVendedorList(attachedVendedorList);
            List<Panadero> attachedPanaderoList = new ArrayList<Panadero>();
            for (Panadero panaderoListPanaderoToAttach : usuario.getPanaderoList()) {
                panaderoListPanaderoToAttach = em.getReference(panaderoListPanaderoToAttach.getClass(), panaderoListPanaderoToAttach.getPanaderoPK());
                attachedPanaderoList.add(panaderoListPanaderoToAttach);
            }
            usuario.setPanaderoList(attachedPanaderoList);
            em.persist(usuario);
            for (Vendedor vendedorListVendedor : usuario.getVendedorList()) {
                Usuario oldUsuarioOfVendedorListVendedor = vendedorListVendedor.getUsuario();
                vendedorListVendedor.setUsuario(usuario);
                vendedorListVendedor = em.merge(vendedorListVendedor);
                if (oldUsuarioOfVendedorListVendedor != null) {
                    oldUsuarioOfVendedorListVendedor.getVendedorList().remove(vendedorListVendedor);
                    oldUsuarioOfVendedorListVendedor = em.merge(oldUsuarioOfVendedorListVendedor);
                }
            }
            for (Panadero panaderoListPanadero : usuario.getPanaderoList()) {
                Usuario oldUsuarioOfPanaderoListPanadero = panaderoListPanadero.getUsuario();
                panaderoListPanadero.setUsuario(usuario);
                panaderoListPanadero = em.merge(panaderoListPanadero);
                if (oldUsuarioOfPanaderoListPanadero != null) {
                    oldUsuarioOfPanaderoListPanadero.getPanaderoList().remove(panaderoListPanadero);
                    oldUsuarioOfPanaderoListPanadero = em.merge(oldUsuarioOfPanaderoListPanadero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getCedula()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getCedula());
            List<Vendedor> vendedorListOld = persistentUsuario.getVendedorList();
            List<Vendedor> vendedorListNew = usuario.getVendedorList();
            List<Panadero> panaderoListOld = persistentUsuario.getPanaderoList();
            List<Panadero> panaderoListNew = usuario.getPanaderoList();
            List<String> illegalOrphanMessages = null;
            for (Vendedor vendedorListOldVendedor : vendedorListOld) {
                if (!vendedorListNew.contains(vendedorListOldVendedor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vendedor " + vendedorListOldVendedor + " since its usuario field is not nullable.");
                }
            }
            for (Panadero panaderoListOldPanadero : panaderoListOld) {
                if (!panaderoListNew.contains(panaderoListOldPanadero)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Panadero " + panaderoListOldPanadero + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Vendedor> attachedVendedorListNew = new ArrayList<Vendedor>();
            for (Vendedor vendedorListNewVendedorToAttach : vendedorListNew) {
                vendedorListNewVendedorToAttach = em.getReference(vendedorListNewVendedorToAttach.getClass(), vendedorListNewVendedorToAttach.getVendedorPK());
                attachedVendedorListNew.add(vendedorListNewVendedorToAttach);
            }
            vendedorListNew = attachedVendedorListNew;
            usuario.setVendedorList(vendedorListNew);
            List<Panadero> attachedPanaderoListNew = new ArrayList<Panadero>();
            for (Panadero panaderoListNewPanaderoToAttach : panaderoListNew) {
                panaderoListNewPanaderoToAttach = em.getReference(panaderoListNewPanaderoToAttach.getClass(), panaderoListNewPanaderoToAttach.getPanaderoPK());
                attachedPanaderoListNew.add(panaderoListNewPanaderoToAttach);
            }
            panaderoListNew = attachedPanaderoListNew;
            usuario.setPanaderoList(panaderoListNew);
            usuario = em.merge(usuario);
            for (Vendedor vendedorListNewVendedor : vendedorListNew) {
                if (!vendedorListOld.contains(vendedorListNewVendedor)) {
                    Usuario oldUsuarioOfVendedorListNewVendedor = vendedorListNewVendedor.getUsuario();
                    vendedorListNewVendedor.setUsuario(usuario);
                    vendedorListNewVendedor = em.merge(vendedorListNewVendedor);
                    if (oldUsuarioOfVendedorListNewVendedor != null && !oldUsuarioOfVendedorListNewVendedor.equals(usuario)) {
                        oldUsuarioOfVendedorListNewVendedor.getVendedorList().remove(vendedorListNewVendedor);
                        oldUsuarioOfVendedorListNewVendedor = em.merge(oldUsuarioOfVendedorListNewVendedor);
                    }
                }
            }
            for (Panadero panaderoListNewPanadero : panaderoListNew) {
                if (!panaderoListOld.contains(panaderoListNewPanadero)) {
                    Usuario oldUsuarioOfPanaderoListNewPanadero = panaderoListNewPanadero.getUsuario();
                    panaderoListNewPanadero.setUsuario(usuario);
                    panaderoListNewPanadero = em.merge(panaderoListNewPanadero);
                    if (oldUsuarioOfPanaderoListNewPanadero != null && !oldUsuarioOfPanaderoListNewPanadero.equals(usuario)) {
                        oldUsuarioOfPanaderoListNewPanadero.getPanaderoList().remove(panaderoListNewPanadero);
                        oldUsuarioOfPanaderoListNewPanadero = em.merge(oldUsuarioOfPanaderoListNewPanadero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getCedula();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Vendedor> vendedorListOrphanCheck = usuario.getVendedorList();
            for (Vendedor vendedorListOrphanCheckVendedor : vendedorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Vendedor " + vendedorListOrphanCheckVendedor + " in its vendedorList field has a non-nullable usuario field.");
            }
            List<Panadero> panaderoListOrphanCheck = usuario.getPanaderoList();
            for (Panadero panaderoListOrphanCheckPanadero : panaderoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Panadero " + panaderoListOrphanCheckPanadero + " in its panaderoList field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
