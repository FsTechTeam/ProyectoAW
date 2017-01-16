/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import entidades.DetalleDeLote;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.TblLoteProductos;
import entidades.TblProducto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lestermeneses
 */
public class DetalleDeLoteJpaController implements Serializable {

    public DetalleDeLoteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleDeLote detalleDeLote) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblLoteProductos tblLoteProductosid = detalleDeLote.getTblLoteProductosid();
            if (tblLoteProductosid != null) {
                tblLoteProductosid = em.getReference(tblLoteProductosid.getClass(), tblLoteProductosid.getId());
                detalleDeLote.setTblLoteProductosid(tblLoteProductosid);
            }
            TblProducto tblProductoid = detalleDeLote.getTblProductoid();
            if (tblProductoid != null) {
                tblProductoid = em.getReference(tblProductoid.getClass(), tblProductoid.getId());
                detalleDeLote.setTblProductoid(tblProductoid);
            }
            em.persist(detalleDeLote);
            if (tblLoteProductosid != null) {
                tblLoteProductosid.getDetalleDeLoteCollection().add(detalleDeLote);
                tblLoteProductosid = em.merge(tblLoteProductosid);
            }
            if (tblProductoid != null) {
                tblProductoid.getDetalleDeLoteCollection().add(detalleDeLote);
                tblProductoid = em.merge(tblProductoid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleDeLote detalleDeLote) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleDeLote persistentDetalleDeLote = em.find(DetalleDeLote.class, detalleDeLote.getId());
            TblLoteProductos tblLoteProductosidOld = persistentDetalleDeLote.getTblLoteProductosid();
            TblLoteProductos tblLoteProductosidNew = detalleDeLote.getTblLoteProductosid();
            TblProducto tblProductoidOld = persistentDetalleDeLote.getTblProductoid();
            TblProducto tblProductoidNew = detalleDeLote.getTblProductoid();
            if (tblLoteProductosidNew != null) {
                tblLoteProductosidNew = em.getReference(tblLoteProductosidNew.getClass(), tblLoteProductosidNew.getId());
                detalleDeLote.setTblLoteProductosid(tblLoteProductosidNew);
            }
            if (tblProductoidNew != null) {
                tblProductoidNew = em.getReference(tblProductoidNew.getClass(), tblProductoidNew.getId());
                detalleDeLote.setTblProductoid(tblProductoidNew);
            }
            detalleDeLote = em.merge(detalleDeLote);
            if (tblLoteProductosidOld != null && !tblLoteProductosidOld.equals(tblLoteProductosidNew)) {
                tblLoteProductosidOld.getDetalleDeLoteCollection().remove(detalleDeLote);
                tblLoteProductosidOld = em.merge(tblLoteProductosidOld);
            }
            if (tblLoteProductosidNew != null && !tblLoteProductosidNew.equals(tblLoteProductosidOld)) {
                tblLoteProductosidNew.getDetalleDeLoteCollection().add(detalleDeLote);
                tblLoteProductosidNew = em.merge(tblLoteProductosidNew);
            }
            if (tblProductoidOld != null && !tblProductoidOld.equals(tblProductoidNew)) {
                tblProductoidOld.getDetalleDeLoteCollection().remove(detalleDeLote);
                tblProductoidOld = em.merge(tblProductoidOld);
            }
            if (tblProductoidNew != null && !tblProductoidNew.equals(tblProductoidOld)) {
                tblProductoidNew.getDetalleDeLoteCollection().add(detalleDeLote);
                tblProductoidNew = em.merge(tblProductoidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleDeLote.getId();
                if (findDetalleDeLote(id) == null) {
                    throw new NonexistentEntityException("The detalleDeLote with id " + id + " no longer exists.");
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
            DetalleDeLote detalleDeLote;
            try {
                detalleDeLote = em.getReference(DetalleDeLote.class, id);
                detalleDeLote.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleDeLote with id " + id + " no longer exists.", enfe);
            }
            TblLoteProductos tblLoteProductosid = detalleDeLote.getTblLoteProductosid();
            if (tblLoteProductosid != null) {
                tblLoteProductosid.getDetalleDeLoteCollection().remove(detalleDeLote);
                tblLoteProductosid = em.merge(tblLoteProductosid);
            }
            TblProducto tblProductoid = detalleDeLote.getTblProductoid();
            if (tblProductoid != null) {
                tblProductoid.getDetalleDeLoteCollection().remove(detalleDeLote);
                tblProductoid = em.merge(tblProductoid);
            }
            em.remove(detalleDeLote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleDeLote> findDetalleDeLoteEntities() {
        return findDetalleDeLoteEntities(true, -1, -1);
    }

    public List<DetalleDeLote> findDetalleDeLoteEntities(int maxResults, int firstResult) {
        return findDetalleDeLoteEntities(false, maxResults, firstResult);
    }

    private List<DetalleDeLote> findDetalleDeLoteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleDeLote.class));
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

    public DetalleDeLote findDetalleDeLote(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleDeLote.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleDeLoteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleDeLote> rt = cq.from(DetalleDeLote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
