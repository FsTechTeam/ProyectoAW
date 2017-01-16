/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import entidades.TblCategoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.TblLinea;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lestermeneses
 */
public class TblCategoriaJpaController implements Serializable {

    public TblCategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblCategoria tblCategoria) {
        if (tblCategoria.getTblLineaCollection() == null) {
            tblCategoria.setTblLineaCollection(new ArrayList<TblLinea>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TblLinea> attachedTblLineaCollection = new ArrayList<TblLinea>();
            for (TblLinea tblLineaCollectionTblLineaToAttach : tblCategoria.getTblLineaCollection()) {
                tblLineaCollectionTblLineaToAttach = em.getReference(tblLineaCollectionTblLineaToAttach.getClass(), tblLineaCollectionTblLineaToAttach.getId());
                attachedTblLineaCollection.add(tblLineaCollectionTblLineaToAttach);
            }
            tblCategoria.setTblLineaCollection(attachedTblLineaCollection);
            em.persist(tblCategoria);
            for (TblLinea tblLineaCollectionTblLinea : tblCategoria.getTblLineaCollection()) {
                TblCategoria oldTblCategoriaidOfTblLineaCollectionTblLinea = tblLineaCollectionTblLinea.getTblCategoriaid();
                tblLineaCollectionTblLinea.setTblCategoriaid(tblCategoria);
                tblLineaCollectionTblLinea = em.merge(tblLineaCollectionTblLinea);
                if (oldTblCategoriaidOfTblLineaCollectionTblLinea != null) {
                    oldTblCategoriaidOfTblLineaCollectionTblLinea.getTblLineaCollection().remove(tblLineaCollectionTblLinea);
                    oldTblCategoriaidOfTblLineaCollectionTblLinea = em.merge(oldTblCategoriaidOfTblLineaCollectionTblLinea);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblCategoria tblCategoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblCategoria persistentTblCategoria = em.find(TblCategoria.class, tblCategoria.getId());
            Collection<TblLinea> tblLineaCollectionOld = persistentTblCategoria.getTblLineaCollection();
            Collection<TblLinea> tblLineaCollectionNew = tblCategoria.getTblLineaCollection();
            List<String> illegalOrphanMessages = null;
            for (TblLinea tblLineaCollectionOldTblLinea : tblLineaCollectionOld) {
                if (!tblLineaCollectionNew.contains(tblLineaCollectionOldTblLinea)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblLinea " + tblLineaCollectionOldTblLinea + " since its tblCategoriaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TblLinea> attachedTblLineaCollectionNew = new ArrayList<TblLinea>();
            for (TblLinea tblLineaCollectionNewTblLineaToAttach : tblLineaCollectionNew) {
                tblLineaCollectionNewTblLineaToAttach = em.getReference(tblLineaCollectionNewTblLineaToAttach.getClass(), tblLineaCollectionNewTblLineaToAttach.getId());
                attachedTblLineaCollectionNew.add(tblLineaCollectionNewTblLineaToAttach);
            }
            tblLineaCollectionNew = attachedTblLineaCollectionNew;
            tblCategoria.setTblLineaCollection(tblLineaCollectionNew);
            tblCategoria = em.merge(tblCategoria);
            for (TblLinea tblLineaCollectionNewTblLinea : tblLineaCollectionNew) {
                if (!tblLineaCollectionOld.contains(tblLineaCollectionNewTblLinea)) {
                    TblCategoria oldTblCategoriaidOfTblLineaCollectionNewTblLinea = tblLineaCollectionNewTblLinea.getTblCategoriaid();
                    tblLineaCollectionNewTblLinea.setTblCategoriaid(tblCategoria);
                    tblLineaCollectionNewTblLinea = em.merge(tblLineaCollectionNewTblLinea);
                    if (oldTblCategoriaidOfTblLineaCollectionNewTblLinea != null && !oldTblCategoriaidOfTblLineaCollectionNewTblLinea.equals(tblCategoria)) {
                        oldTblCategoriaidOfTblLineaCollectionNewTblLinea.getTblLineaCollection().remove(tblLineaCollectionNewTblLinea);
                        oldTblCategoriaidOfTblLineaCollectionNewTblLinea = em.merge(oldTblCategoriaidOfTblLineaCollectionNewTblLinea);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblCategoria.getId();
                if (findTblCategoria(id) == null) {
                    throw new NonexistentEntityException("The tblCategoria with id " + id + " no longer exists.");
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
            TblCategoria tblCategoria;
            try {
                tblCategoria = em.getReference(TblCategoria.class, id);
                tblCategoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblCategoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TblLinea> tblLineaCollectionOrphanCheck = tblCategoria.getTblLineaCollection();
            for (TblLinea tblLineaCollectionOrphanCheckTblLinea : tblLineaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblCategoria (" + tblCategoria + ") cannot be destroyed since the TblLinea " + tblLineaCollectionOrphanCheckTblLinea + " in its tblLineaCollection field has a non-nullable tblCategoriaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tblCategoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TblCategoria> findTblCategoriaEntities() {
        return findTblCategoriaEntities(true, -1, -1);
    }

    public List<TblCategoria> findTblCategoriaEntities(int maxResults, int firstResult) {
        return findTblCategoriaEntities(false, maxResults, firstResult);
    }

    private List<TblCategoria> findTblCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblCategoria.class));
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

    public TblCategoria findTblCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblCategoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblCategoria> rt = cq.from(TblCategoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
