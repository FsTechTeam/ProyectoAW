/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.DetalleDeLote;
import entidades.TblLoteProductos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lestermeneses
 */
public class TblLoteProductosJpaController implements Serializable {

    public TblLoteProductosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblLoteProductos tblLoteProductos) {
        if (tblLoteProductos.getDetalleDeLoteCollection() == null) {
            tblLoteProductos.setDetalleDeLoteCollection(new ArrayList<DetalleDeLote>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<DetalleDeLote> attachedDetalleDeLoteCollection = new ArrayList<DetalleDeLote>();
            for (DetalleDeLote detalleDeLoteCollectionDetalleDeLoteToAttach : tblLoteProductos.getDetalleDeLoteCollection()) {
                detalleDeLoteCollectionDetalleDeLoteToAttach = em.getReference(detalleDeLoteCollectionDetalleDeLoteToAttach.getClass(), detalleDeLoteCollectionDetalleDeLoteToAttach.getId());
                attachedDetalleDeLoteCollection.add(detalleDeLoteCollectionDetalleDeLoteToAttach);
            }
            tblLoteProductos.setDetalleDeLoteCollection(attachedDetalleDeLoteCollection);
            em.persist(tblLoteProductos);
            for (DetalleDeLote detalleDeLoteCollectionDetalleDeLote : tblLoteProductos.getDetalleDeLoteCollection()) {
                TblLoteProductos oldTblLoteProductosidOfDetalleDeLoteCollectionDetalleDeLote = detalleDeLoteCollectionDetalleDeLote.getTblLoteProductosid();
                detalleDeLoteCollectionDetalleDeLote.setTblLoteProductosid(tblLoteProductos);
                detalleDeLoteCollectionDetalleDeLote = em.merge(detalleDeLoteCollectionDetalleDeLote);
                if (oldTblLoteProductosidOfDetalleDeLoteCollectionDetalleDeLote != null) {
                    oldTblLoteProductosidOfDetalleDeLoteCollectionDetalleDeLote.getDetalleDeLoteCollection().remove(detalleDeLoteCollectionDetalleDeLote);
                    oldTblLoteProductosidOfDetalleDeLoteCollectionDetalleDeLote = em.merge(oldTblLoteProductosidOfDetalleDeLoteCollectionDetalleDeLote);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblLoteProductos tblLoteProductos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblLoteProductos persistentTblLoteProductos = em.find(TblLoteProductos.class, tblLoteProductos.getId());
            Collection<DetalleDeLote> detalleDeLoteCollectionOld = persistentTblLoteProductos.getDetalleDeLoteCollection();
            Collection<DetalleDeLote> detalleDeLoteCollectionNew = tblLoteProductos.getDetalleDeLoteCollection();
            List<String> illegalOrphanMessages = null;
            for (DetalleDeLote detalleDeLoteCollectionOldDetalleDeLote : detalleDeLoteCollectionOld) {
                if (!detalleDeLoteCollectionNew.contains(detalleDeLoteCollectionOldDetalleDeLote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleDeLote " + detalleDeLoteCollectionOldDetalleDeLote + " since its tblLoteProductosid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<DetalleDeLote> attachedDetalleDeLoteCollectionNew = new ArrayList<DetalleDeLote>();
            for (DetalleDeLote detalleDeLoteCollectionNewDetalleDeLoteToAttach : detalleDeLoteCollectionNew) {
                detalleDeLoteCollectionNewDetalleDeLoteToAttach = em.getReference(detalleDeLoteCollectionNewDetalleDeLoteToAttach.getClass(), detalleDeLoteCollectionNewDetalleDeLoteToAttach.getId());
                attachedDetalleDeLoteCollectionNew.add(detalleDeLoteCollectionNewDetalleDeLoteToAttach);
            }
            detalleDeLoteCollectionNew = attachedDetalleDeLoteCollectionNew;
            tblLoteProductos.setDetalleDeLoteCollection(detalleDeLoteCollectionNew);
            tblLoteProductos = em.merge(tblLoteProductos);
            for (DetalleDeLote detalleDeLoteCollectionNewDetalleDeLote : detalleDeLoteCollectionNew) {
                if (!detalleDeLoteCollectionOld.contains(detalleDeLoteCollectionNewDetalleDeLote)) {
                    TblLoteProductos oldTblLoteProductosidOfDetalleDeLoteCollectionNewDetalleDeLote = detalleDeLoteCollectionNewDetalleDeLote.getTblLoteProductosid();
                    detalleDeLoteCollectionNewDetalleDeLote.setTblLoteProductosid(tblLoteProductos);
                    detalleDeLoteCollectionNewDetalleDeLote = em.merge(detalleDeLoteCollectionNewDetalleDeLote);
                    if (oldTblLoteProductosidOfDetalleDeLoteCollectionNewDetalleDeLote != null && !oldTblLoteProductosidOfDetalleDeLoteCollectionNewDetalleDeLote.equals(tblLoteProductos)) {
                        oldTblLoteProductosidOfDetalleDeLoteCollectionNewDetalleDeLote.getDetalleDeLoteCollection().remove(detalleDeLoteCollectionNewDetalleDeLote);
                        oldTblLoteProductosidOfDetalleDeLoteCollectionNewDetalleDeLote = em.merge(oldTblLoteProductosidOfDetalleDeLoteCollectionNewDetalleDeLote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblLoteProductos.getId();
                if (findTblLoteProductos(id) == null) {
                    throw new NonexistentEntityException("The tblLoteProductos with id " + id + " no longer exists.");
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
            TblLoteProductos tblLoteProductos;
            try {
                tblLoteProductos = em.getReference(TblLoteProductos.class, id);
                tblLoteProductos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblLoteProductos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetalleDeLote> detalleDeLoteCollectionOrphanCheck = tblLoteProductos.getDetalleDeLoteCollection();
            for (DetalleDeLote detalleDeLoteCollectionOrphanCheckDetalleDeLote : detalleDeLoteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblLoteProductos (" + tblLoteProductos + ") cannot be destroyed since the DetalleDeLote " + detalleDeLoteCollectionOrphanCheckDetalleDeLote + " in its detalleDeLoteCollection field has a non-nullable tblLoteProductosid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tblLoteProductos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TblLoteProductos> findTblLoteProductosEntities() {
        return findTblLoteProductosEntities(true, -1, -1);
    }

    public List<TblLoteProductos> findTblLoteProductosEntities(int maxResults, int firstResult) {
        return findTblLoteProductosEntities(false, maxResults, firstResult);
    }

    private List<TblLoteProductos> findTblLoteProductosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblLoteProductos.class));
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

    public TblLoteProductos findTblLoteProductos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblLoteProductos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblLoteProductosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblLoteProductos> rt = cq.from(TblLoteProductos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
