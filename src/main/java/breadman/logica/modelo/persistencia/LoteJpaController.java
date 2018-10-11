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
import breadman.logica.modelo.entidades.Lote;
import breadman.logica.modelo.entidades.Panadero;
import breadman.logica.modelo.entidades.OrdenProduccion;
import breadman.logica.modelo.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel Arnedo
 */
public class LoteJpaController implements Serializable {

    public LoteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lote lote) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventario inventario = lote.getInventario();
            if (inventario != null) {
                inventario = em.getReference(inventario.getClass(), inventario.getInventarioPK());
                lote.setInventario(inventario);
            }
            Panadero panadero = lote.getPanadero();
            if (panadero != null) {
                panadero = em.getReference(panadero.getClass(), panadero.getPanaderoPK());
                lote.setPanadero(panadero);
            }
            OrdenProduccion idCodigoOrdenProduccion = lote.getIdCodigoOrdenProduccion();
            if (idCodigoOrdenProduccion != null) {
                idCodigoOrdenProduccion = em.getReference(idCodigoOrdenProduccion.getClass(), idCodigoOrdenProduccion.getCodigoOrdenProduccion());
                lote.setIdCodigoOrdenProduccion(idCodigoOrdenProduccion);
            }
            em.persist(lote);
            if (inventario != null) {
                inventario.getLoteList().add(lote);
                inventario = em.merge(inventario);
            }
            if (panadero != null) {
                panadero.getLoteList().add(lote);
                panadero = em.merge(panadero);
            }
            if (idCodigoOrdenProduccion != null) {
                idCodigoOrdenProduccion.getLoteList().add(lote);
                idCodigoOrdenProduccion = em.merge(idCodigoOrdenProduccion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lote lote) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lote persistentLote = em.find(Lote.class, lote.getCodigoLote());
            Inventario inventarioOld = persistentLote.getInventario();
            Inventario inventarioNew = lote.getInventario();
            Panadero panaderoOld = persistentLote.getPanadero();
            Panadero panaderoNew = lote.getPanadero();
            OrdenProduccion idCodigoOrdenProduccionOld = persistentLote.getIdCodigoOrdenProduccion();
            OrdenProduccion idCodigoOrdenProduccionNew = lote.getIdCodigoOrdenProduccion();
            if (inventarioNew != null) {
                inventarioNew = em.getReference(inventarioNew.getClass(), inventarioNew.getInventarioPK());
                lote.setInventario(inventarioNew);
            }
            if (panaderoNew != null) {
                panaderoNew = em.getReference(panaderoNew.getClass(), panaderoNew.getPanaderoPK());
                lote.setPanadero(panaderoNew);
            }
            if (idCodigoOrdenProduccionNew != null) {
                idCodigoOrdenProduccionNew = em.getReference(idCodigoOrdenProduccionNew.getClass(), idCodigoOrdenProduccionNew.getCodigoOrdenProduccion());
                lote.setIdCodigoOrdenProduccion(idCodigoOrdenProduccionNew);
            }
            lote = em.merge(lote);
            if (inventarioOld != null && !inventarioOld.equals(inventarioNew)) {
                inventarioOld.getLoteList().remove(lote);
                inventarioOld = em.merge(inventarioOld);
            }
            if (inventarioNew != null && !inventarioNew.equals(inventarioOld)) {
                inventarioNew.getLoteList().add(lote);
                inventarioNew = em.merge(inventarioNew);
            }
            if (panaderoOld != null && !panaderoOld.equals(panaderoNew)) {
                panaderoOld.getLoteList().remove(lote);
                panaderoOld = em.merge(panaderoOld);
            }
            if (panaderoNew != null && !panaderoNew.equals(panaderoOld)) {
                panaderoNew.getLoteList().add(lote);
                panaderoNew = em.merge(panaderoNew);
            }
            if (idCodigoOrdenProduccionOld != null && !idCodigoOrdenProduccionOld.equals(idCodigoOrdenProduccionNew)) {
                idCodigoOrdenProduccionOld.getLoteList().remove(lote);
                idCodigoOrdenProduccionOld = em.merge(idCodigoOrdenProduccionOld);
            }
            if (idCodigoOrdenProduccionNew != null && !idCodigoOrdenProduccionNew.equals(idCodigoOrdenProduccionOld)) {
                idCodigoOrdenProduccionNew.getLoteList().add(lote);
                idCodigoOrdenProduccionNew = em.merge(idCodigoOrdenProduccionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lote.getCodigoLote();
                if (findLote(id) == null) {
                    throw new NonexistentEntityException("The lote with id " + id + " no longer exists.");
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
            Lote lote;
            try {
                lote = em.getReference(Lote.class, id);
                lote.getCodigoLote();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lote with id " + id + " no longer exists.", enfe);
            }
            Inventario inventario = lote.getInventario();
            if (inventario != null) {
                inventario.getLoteList().remove(lote);
                inventario = em.merge(inventario);
            }
            Panadero panadero = lote.getPanadero();
            if (panadero != null) {
                panadero.getLoteList().remove(lote);
                panadero = em.merge(panadero);
            }
            OrdenProduccion idCodigoOrdenProduccion = lote.getIdCodigoOrdenProduccion();
            if (idCodigoOrdenProduccion != null) {
                idCodigoOrdenProduccion.getLoteList().remove(lote);
                idCodigoOrdenProduccion = em.merge(idCodigoOrdenProduccion);
            }
            em.remove(lote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lote> findLoteEntities() {
        return findLoteEntities(true, -1, -1);
    }

    public List<Lote> findLoteEntities(int maxResults, int firstResult) {
        return findLoteEntities(false, maxResults, firstResult);
    }

    private List<Lote> findLoteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lote.class));
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

    public Lote findLote(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lote.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lote> rt = cq.from(Lote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
