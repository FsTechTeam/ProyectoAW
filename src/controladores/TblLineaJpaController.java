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
import entidades.TblCategoria;
import entidades.TblLinea;
import entidades.TblProducto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lestermeneses
 */
public class TblLineaJpaController implements Serializable {

    public TblLineaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblLinea tblLinea) {
        if (tblLinea.getTblProductoCollection() == null) {
            tblLinea.setTblProductoCollection(new ArrayList<TblProducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblCategoria tblCategoriaid = tblLinea.getTblCategoriaid();
            if (tblCategoriaid != null) {
                tblCategoriaid = em.getReference(tblCategoriaid.getClass(), tblCategoriaid.getId());
                tblLinea.setTblCategoriaid(tblCategoriaid);
            }
            Collection<TblProducto> attachedTblProductoCollection = new ArrayList<TblProducto>();
            for (TblProducto tblProductoCollectionTblProductoToAttach : tblLinea.getTblProductoCollection()) {
                tblProductoCollectionTblProductoToAttach = em.getReference(tblProductoCollectionTblProductoToAttach.getClass(), tblProductoCollectionTblProductoToAttach.getId());
                attachedTblProductoCollection.add(tblProductoCollectionTblProductoToAttach);
            }
            tblLinea.setTblProductoCollection(attachedTblProductoCollection);
            em.persist(tblLinea);
            if (tblCategoriaid != null) {
                tblCategoriaid.getTblLineaCollection().add(tblLinea);
                tblCategoriaid = em.merge(tblCategoriaid);
            }
            for (TblProducto tblProductoCollectionTblProducto : tblLinea.getTblProductoCollection()) {
                TblLinea oldTblLineaidOfTblProductoCollectionTblProducto = tblProductoCollectionTblProducto.getTblLineaid();
                tblProductoCollectionTblProducto.setTblLineaid(tblLinea);
                tblProductoCollectionTblProducto = em.merge(tblProductoCollectionTblProducto);
                if (oldTblLineaidOfTblProductoCollectionTblProducto != null) {
                    oldTblLineaidOfTblProductoCollectionTblProducto.getTblProductoCollection().remove(tblProductoCollectionTblProducto);
                    oldTblLineaidOfTblProductoCollectionTblProducto = em.merge(oldTblLineaidOfTblProductoCollectionTblProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblLinea tblLinea) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblLinea persistentTblLinea = em.find(TblLinea.class, tblLinea.getId());
            TblCategoria tblCategoriaidOld = persistentTblLinea.getTblCategoriaid();
            TblCategoria tblCategoriaidNew = tblLinea.getTblCategoriaid();
            Collection<TblProducto> tblProductoCollectionOld = persistentTblLinea.getTblProductoCollection();
            Collection<TblProducto> tblProductoCollectionNew = tblLinea.getTblProductoCollection();
            List<String> illegalOrphanMessages = null;
            for (TblProducto tblProductoCollectionOldTblProducto : tblProductoCollectionOld) {
                if (!tblProductoCollectionNew.contains(tblProductoCollectionOldTblProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblProducto " + tblProductoCollectionOldTblProducto + " since its tblLineaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tblCategoriaidNew != null) {
                tblCategoriaidNew = em.getReference(tblCategoriaidNew.getClass(), tblCategoriaidNew.getId());
                tblLinea.setTblCategoriaid(tblCategoriaidNew);
            }
            Collection<TblProducto> attachedTblProductoCollectionNew = new ArrayList<TblProducto>();
            for (TblProducto tblProductoCollectionNewTblProductoToAttach : tblProductoCollectionNew) {
                tblProductoCollectionNewTblProductoToAttach = em.getReference(tblProductoCollectionNewTblProductoToAttach.getClass(), tblProductoCollectionNewTblProductoToAttach.getId());
                attachedTblProductoCollectionNew.add(tblProductoCollectionNewTblProductoToAttach);
            }
            tblProductoCollectionNew = attachedTblProductoCollectionNew;
            tblLinea.setTblProductoCollection(tblProductoCollectionNew);
            tblLinea = em.merge(tblLinea);
            if (tblCategoriaidOld != null && !tblCategoriaidOld.equals(tblCategoriaidNew)) {
                tblCategoriaidOld.getTblLineaCollection().remove(tblLinea);
                tblCategoriaidOld = em.merge(tblCategoriaidOld);
            }
            if (tblCategoriaidNew != null && !tblCategoriaidNew.equals(tblCategoriaidOld)) {
                tblCategoriaidNew.getTblLineaCollection().add(tblLinea);
                tblCategoriaidNew = em.merge(tblCategoriaidNew);
            }
            for (TblProducto tblProductoCollectionNewTblProducto : tblProductoCollectionNew) {
                if (!tblProductoCollectionOld.contains(tblProductoCollectionNewTblProducto)) {
                    TblLinea oldTblLineaidOfTblProductoCollectionNewTblProducto = tblProductoCollectionNewTblProducto.getTblLineaid();
                    tblProductoCollectionNewTblProducto.setTblLineaid(tblLinea);
                    tblProductoCollectionNewTblProducto = em.merge(tblProductoCollectionNewTblProducto);
                    if (oldTblLineaidOfTblProductoCollectionNewTblProducto != null && !oldTblLineaidOfTblProductoCollectionNewTblProducto.equals(tblLinea)) {
                        oldTblLineaidOfTblProductoCollectionNewTblProducto.getTblProductoCollection().remove(tblProductoCollectionNewTblProducto);
                        oldTblLineaidOfTblProductoCollectionNewTblProducto = em.merge(oldTblLineaidOfTblProductoCollectionNewTblProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblLinea.getId();
                if (findTblLinea(id) == null) {
                    throw new NonexistentEntityException("The tblLinea with id " + id + " no longer exists.");
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
            TblLinea tblLinea;
            try {
                tblLinea = em.getReference(TblLinea.class, id);
                tblLinea.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblLinea with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TblProducto> tblProductoCollectionOrphanCheck = tblLinea.getTblProductoCollection();
            for (TblProducto tblProductoCollectionOrphanCheckTblProducto : tblProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblLinea (" + tblLinea + ") cannot be destroyed since the TblProducto " + tblProductoCollectionOrphanCheckTblProducto + " in its tblProductoCollection field has a non-nullable tblLineaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TblCategoria tblCategoriaid = tblLinea.getTblCategoriaid();
            if (tblCategoriaid != null) {
                tblCategoriaid.getTblLineaCollection().remove(tblLinea);
                tblCategoriaid = em.merge(tblCategoriaid);
            }
            em.remove(tblLinea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TblLinea> findTblLineaEntities() {
        return findTblLineaEntities(true, -1, -1);
    }

    public List<TblLinea> findTblLineaEntities(int maxResults, int firstResult) {
        return findTblLineaEntities(false, maxResults, firstResult);
    }

    private List<TblLinea> findTblLineaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblLinea.class));
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

    public TblLinea findTblLinea(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblLinea.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblLineaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblLinea> rt = cq.from(TblLinea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
