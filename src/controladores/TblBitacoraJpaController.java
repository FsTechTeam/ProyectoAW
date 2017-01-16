/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import entidades.TblBitacora;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.TblProducto;
import entidades.TblTipoUsuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lestermeneses
 */
public class TblBitacoraJpaController implements Serializable {

    public TblBitacoraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblBitacora tblBitacora) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblProducto tblProductoid = tblBitacora.getTblProductoid();
            if (tblProductoid != null) {
                tblProductoid = em.getReference(tblProductoid.getClass(), tblProductoid.getId());
                tblBitacora.setTblProductoid(tblProductoid);
            }
            TblTipoUsuario tblTipoUsuarioid = tblBitacora.getTblTipoUsuarioid();
            if (tblTipoUsuarioid != null) {
                tblTipoUsuarioid = em.getReference(tblTipoUsuarioid.getClass(), tblTipoUsuarioid.getId());
                tblBitacora.setTblTipoUsuarioid(tblTipoUsuarioid);
            }
            em.persist(tblBitacora);
            if (tblProductoid != null) {
                tblProductoid.getTblBitacoraCollection().add(tblBitacora);
                tblProductoid = em.merge(tblProductoid);
            }
            if (tblTipoUsuarioid != null) {
                tblTipoUsuarioid.getTblBitacoraCollection().add(tblBitacora);
                tblTipoUsuarioid = em.merge(tblTipoUsuarioid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblBitacora tblBitacora) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblBitacora persistentTblBitacora = em.find(TblBitacora.class, tblBitacora.getId());
            TblProducto tblProductoidOld = persistentTblBitacora.getTblProductoid();
            TblProducto tblProductoidNew = tblBitacora.getTblProductoid();
            TblTipoUsuario tblTipoUsuarioidOld = persistentTblBitacora.getTblTipoUsuarioid();
            TblTipoUsuario tblTipoUsuarioidNew = tblBitacora.getTblTipoUsuarioid();
            if (tblProductoidNew != null) {
                tblProductoidNew = em.getReference(tblProductoidNew.getClass(), tblProductoidNew.getId());
                tblBitacora.setTblProductoid(tblProductoidNew);
            }
            if (tblTipoUsuarioidNew != null) {
                tblTipoUsuarioidNew = em.getReference(tblTipoUsuarioidNew.getClass(), tblTipoUsuarioidNew.getId());
                tblBitacora.setTblTipoUsuarioid(tblTipoUsuarioidNew);
            }
            tblBitacora = em.merge(tblBitacora);
            if (tblProductoidOld != null && !tblProductoidOld.equals(tblProductoidNew)) {
                tblProductoidOld.getTblBitacoraCollection().remove(tblBitacora);
                tblProductoidOld = em.merge(tblProductoidOld);
            }
            if (tblProductoidNew != null && !tblProductoidNew.equals(tblProductoidOld)) {
                tblProductoidNew.getTblBitacoraCollection().add(tblBitacora);
                tblProductoidNew = em.merge(tblProductoidNew);
            }
            if (tblTipoUsuarioidOld != null && !tblTipoUsuarioidOld.equals(tblTipoUsuarioidNew)) {
                tblTipoUsuarioidOld.getTblBitacoraCollection().remove(tblBitacora);
                tblTipoUsuarioidOld = em.merge(tblTipoUsuarioidOld);
            }
            if (tblTipoUsuarioidNew != null && !tblTipoUsuarioidNew.equals(tblTipoUsuarioidOld)) {
                tblTipoUsuarioidNew.getTblBitacoraCollection().add(tblBitacora);
                tblTipoUsuarioidNew = em.merge(tblTipoUsuarioidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblBitacora.getId();
                if (findTblBitacora(id) == null) {
                    throw new NonexistentEntityException("The tblBitacora with id " + id + " no longer exists.");
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
            TblBitacora tblBitacora;
            try {
                tblBitacora = em.getReference(TblBitacora.class, id);
                tblBitacora.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblBitacora with id " + id + " no longer exists.", enfe);
            }
            TblProducto tblProductoid = tblBitacora.getTblProductoid();
            if (tblProductoid != null) {
                tblProductoid.getTblBitacoraCollection().remove(tblBitacora);
                tblProductoid = em.merge(tblProductoid);
            }
            TblTipoUsuario tblTipoUsuarioid = tblBitacora.getTblTipoUsuarioid();
            if (tblTipoUsuarioid != null) {
                tblTipoUsuarioid.getTblBitacoraCollection().remove(tblBitacora);
                tblTipoUsuarioid = em.merge(tblTipoUsuarioid);
            }
            em.remove(tblBitacora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TblBitacora> findTblBitacoraEntities() {
        return findTblBitacoraEntities(true, -1, -1);
    }

    public List<TblBitacora> findTblBitacoraEntities(int maxResults, int firstResult) {
        return findTblBitacoraEntities(false, maxResults, firstResult);
    }

    private List<TblBitacora> findTblBitacoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblBitacora.class));
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

    public TblBitacora findTblBitacora(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblBitacora.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblBitacoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblBitacora> rt = cq.from(TblBitacora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
