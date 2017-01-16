/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.TblCliente;
import entidades.TblHistorialPuntos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lestermeneses
 */
public class TblHistorialPuntosJpaController implements Serializable {

    public TblHistorialPuntosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblHistorialPuntos tblHistorialPuntos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblCliente tblClienteid = tblHistorialPuntos.getTblClienteid();
            if (tblClienteid != null) {
                tblClienteid = em.getReference(tblClienteid.getClass(), tblClienteid.getId());
                tblHistorialPuntos.setTblClienteid(tblClienteid);
            }
            em.persist(tblHistorialPuntos);
            if (tblClienteid != null) {
                tblClienteid.getTblHistorialPuntosCollection().add(tblHistorialPuntos);
                tblClienteid = em.merge(tblClienteid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblHistorialPuntos tblHistorialPuntos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblHistorialPuntos persistentTblHistorialPuntos = em.find(TblHistorialPuntos.class, tblHistorialPuntos.getId());
            TblCliente tblClienteidOld = persistentTblHistorialPuntos.getTblClienteid();
            TblCliente tblClienteidNew = tblHistorialPuntos.getTblClienteid();
            if (tblClienteidNew != null) {
                tblClienteidNew = em.getReference(tblClienteidNew.getClass(), tblClienteidNew.getId());
                tblHistorialPuntos.setTblClienteid(tblClienteidNew);
            }
            tblHistorialPuntos = em.merge(tblHistorialPuntos);
            if (tblClienteidOld != null && !tblClienteidOld.equals(tblClienteidNew)) {
                tblClienteidOld.getTblHistorialPuntosCollection().remove(tblHistorialPuntos);
                tblClienteidOld = em.merge(tblClienteidOld);
            }
            if (tblClienteidNew != null && !tblClienteidNew.equals(tblClienteidOld)) {
                tblClienteidNew.getTblHistorialPuntosCollection().add(tblHistorialPuntos);
                tblClienteidNew = em.merge(tblClienteidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblHistorialPuntos.getId();
                if (findTblHistorialPuntos(id) == null) {
                    throw new NonexistentEntityException("The tblHistorialPuntos with id " + id + " no longer exists.");
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
            TblHistorialPuntos tblHistorialPuntos;
            try {
                tblHistorialPuntos = em.getReference(TblHistorialPuntos.class, id);
                tblHistorialPuntos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblHistorialPuntos with id " + id + " no longer exists.", enfe);
            }
            TblCliente tblClienteid = tblHistorialPuntos.getTblClienteid();
            if (tblClienteid != null) {
                tblClienteid.getTblHistorialPuntosCollection().remove(tblHistorialPuntos);
                tblClienteid = em.merge(tblClienteid);
            }
            em.remove(tblHistorialPuntos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TblHistorialPuntos> findTblHistorialPuntosEntities() {
        return findTblHistorialPuntosEntities(true, -1, -1);
    }

    public List<TblHistorialPuntos> findTblHistorialPuntosEntities(int maxResults, int firstResult) {
        return findTblHistorialPuntosEntities(false, maxResults, firstResult);
    }

    private List<TblHistorialPuntos> findTblHistorialPuntosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblHistorialPuntos.class));
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

    public TblHistorialPuntos findTblHistorialPuntos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblHistorialPuntos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblHistorialPuntosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblHistorialPuntos> rt = cq.from(TblHistorialPuntos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
