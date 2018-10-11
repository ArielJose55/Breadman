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
import breadman.logica.modelo.entidades.SolicitudDeCompra;
import breadman.logica.modelo.persistencia.exceptions.NonexistentEntityException;
import breadman.logica.modelo.persistencia.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel Arnedo
 */
public class SolicitudDeCompraJpaController implements Serializable {

    public SolicitudDeCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SolicitudDeCompra solicitudDeCompra) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Insumo idInsumo = solicitudDeCompra.getIdInsumo();
            if (idInsumo != null) {
                idInsumo = em.getReference(idInsumo.getClass(), idInsumo.getCodigoInsumo());
                solicitudDeCompra.setIdInsumo(idInsumo);
            }
            em.persist(solicitudDeCompra);
            if (idInsumo != null) {
                idInsumo.getSolicitudDeCompraList().add(solicitudDeCompra);
                idInsumo = em.merge(idInsumo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSolicitudDeCompra(solicitudDeCompra.getCodigoSolicitud()) != null) {
                throw new PreexistingEntityException("SolicitudDeCompra " + solicitudDeCompra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SolicitudDeCompra solicitudDeCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SolicitudDeCompra persistentSolicitudDeCompra = em.find(SolicitudDeCompra.class, solicitudDeCompra.getCodigoSolicitud());
            Insumo idInsumoOld = persistentSolicitudDeCompra.getIdInsumo();
            Insumo idInsumoNew = solicitudDeCompra.getIdInsumo();
            if (idInsumoNew != null) {
                idInsumoNew = em.getReference(idInsumoNew.getClass(), idInsumoNew.getCodigoInsumo());
                solicitudDeCompra.setIdInsumo(idInsumoNew);
            }
            solicitudDeCompra = em.merge(solicitudDeCompra);
            if (idInsumoOld != null && !idInsumoOld.equals(idInsumoNew)) {
                idInsumoOld.getSolicitudDeCompraList().remove(solicitudDeCompra);
                idInsumoOld = em.merge(idInsumoOld);
            }
            if (idInsumoNew != null && !idInsumoNew.equals(idInsumoOld)) {
                idInsumoNew.getSolicitudDeCompraList().add(solicitudDeCompra);
                idInsumoNew = em.merge(idInsumoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solicitudDeCompra.getCodigoSolicitud();
                if (findSolicitudDeCompra(id) == null) {
                    throw new NonexistentEntityException("The solicitudDeCompra with id " + id + " no longer exists.");
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
            SolicitudDeCompra solicitudDeCompra;
            try {
                solicitudDeCompra = em.getReference(SolicitudDeCompra.class, id);
                solicitudDeCompra.getCodigoSolicitud();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitudDeCompra with id " + id + " no longer exists.", enfe);
            }
            Insumo idInsumo = solicitudDeCompra.getIdInsumo();
            if (idInsumo != null) {
                idInsumo.getSolicitudDeCompraList().remove(solicitudDeCompra);
                idInsumo = em.merge(idInsumo);
            }
            em.remove(solicitudDeCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SolicitudDeCompra> findSolicitudDeCompraEntities() {
        return findSolicitudDeCompraEntities(true, -1, -1);
    }

    public List<SolicitudDeCompra> findSolicitudDeCompraEntities(int maxResults, int firstResult) {
        return findSolicitudDeCompraEntities(false, maxResults, firstResult);
    }

    private List<SolicitudDeCompra> findSolicitudDeCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SolicitudDeCompra.class));
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

    public SolicitudDeCompra findSolicitudDeCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SolicitudDeCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudDeCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SolicitudDeCompra> rt = cq.from(SolicitudDeCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
