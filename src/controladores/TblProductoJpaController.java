/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.TblLinea;
import entidades.DetalleFactura;
import java.util.ArrayList;
import java.util.Collection;
import entidades.DetalleDeLote;
import entidades.TblBitacora;
import entidades.TblProducto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lestermeneses
 */
public class TblProductoJpaController implements Serializable {

    public TblProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblProducto tblProducto) throws PreexistingEntityException, Exception {
        if (tblProducto.getDetalleFacturaCollection() == null) {
            tblProducto.setDetalleFacturaCollection(new ArrayList<DetalleFactura>());
        }
        if (tblProducto.getDetalleDeLoteCollection() == null) {
            tblProducto.setDetalleDeLoteCollection(new ArrayList<DetalleDeLote>());
        }
        if (tblProducto.getTblBitacoraCollection() == null) {
            tblProducto.setTblBitacoraCollection(new ArrayList<TblBitacora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblLinea tblLineaid = tblProducto.getTblLineaid();
            if (tblLineaid != null) {
                tblLineaid = em.getReference(tblLineaid.getClass(), tblLineaid.getId());
                tblProducto.setTblLineaid(tblLineaid);
            }
            Collection<DetalleFactura> attachedDetalleFacturaCollection = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionDetalleFacturaToAttach : tblProducto.getDetalleFacturaCollection()) {
                detalleFacturaCollectionDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionDetalleFacturaToAttach.getClass(), detalleFacturaCollectionDetalleFacturaToAttach.getId());
                attachedDetalleFacturaCollection.add(detalleFacturaCollectionDetalleFacturaToAttach);
            }
            tblProducto.setDetalleFacturaCollection(attachedDetalleFacturaCollection);
            Collection<DetalleDeLote> attachedDetalleDeLoteCollection = new ArrayList<DetalleDeLote>();
            for (DetalleDeLote detalleDeLoteCollectionDetalleDeLoteToAttach : tblProducto.getDetalleDeLoteCollection()) {
                detalleDeLoteCollectionDetalleDeLoteToAttach = em.getReference(detalleDeLoteCollectionDetalleDeLoteToAttach.getClass(), detalleDeLoteCollectionDetalleDeLoteToAttach.getId());
                attachedDetalleDeLoteCollection.add(detalleDeLoteCollectionDetalleDeLoteToAttach);
            }
            tblProducto.setDetalleDeLoteCollection(attachedDetalleDeLoteCollection);
            Collection<TblBitacora> attachedTblBitacoraCollection = new ArrayList<TblBitacora>();
            for (TblBitacora tblBitacoraCollectionTblBitacoraToAttach : tblProducto.getTblBitacoraCollection()) {
                tblBitacoraCollectionTblBitacoraToAttach = em.getReference(tblBitacoraCollectionTblBitacoraToAttach.getClass(), tblBitacoraCollectionTblBitacoraToAttach.getId());
                attachedTblBitacoraCollection.add(tblBitacoraCollectionTblBitacoraToAttach);
            }
            tblProducto.setTblBitacoraCollection(attachedTblBitacoraCollection);
            em.persist(tblProducto);
            if (tblLineaid != null) {
                tblLineaid.getTblProductoCollection().add(tblProducto);
                tblLineaid = em.merge(tblLineaid);
            }
            for (DetalleFactura detalleFacturaCollectionDetalleFactura : tblProducto.getDetalleFacturaCollection()) {
                TblProducto oldTblProductoidOfDetalleFacturaCollectionDetalleFactura = detalleFacturaCollectionDetalleFactura.getTblProductoid();
                detalleFacturaCollectionDetalleFactura.setTblProductoid(tblProducto);
                detalleFacturaCollectionDetalleFactura = em.merge(detalleFacturaCollectionDetalleFactura);
                if (oldTblProductoidOfDetalleFacturaCollectionDetalleFactura != null) {
                    oldTblProductoidOfDetalleFacturaCollectionDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionDetalleFactura);
                    oldTblProductoidOfDetalleFacturaCollectionDetalleFactura = em.merge(oldTblProductoidOfDetalleFacturaCollectionDetalleFactura);
                }
            }
            for (DetalleDeLote detalleDeLoteCollectionDetalleDeLote : tblProducto.getDetalleDeLoteCollection()) {
                TblProducto oldTblProductoidOfDetalleDeLoteCollectionDetalleDeLote = detalleDeLoteCollectionDetalleDeLote.getTblProductoid();
                detalleDeLoteCollectionDetalleDeLote.setTblProductoid(tblProducto);
                detalleDeLoteCollectionDetalleDeLote = em.merge(detalleDeLoteCollectionDetalleDeLote);
                if (oldTblProductoidOfDetalleDeLoteCollectionDetalleDeLote != null) {
                    oldTblProductoidOfDetalleDeLoteCollectionDetalleDeLote.getDetalleDeLoteCollection().remove(detalleDeLoteCollectionDetalleDeLote);
                    oldTblProductoidOfDetalleDeLoteCollectionDetalleDeLote = em.merge(oldTblProductoidOfDetalleDeLoteCollectionDetalleDeLote);
                }
            }
            for (TblBitacora tblBitacoraCollectionTblBitacora : tblProducto.getTblBitacoraCollection()) {
                TblProducto oldTblProductoidOfTblBitacoraCollectionTblBitacora = tblBitacoraCollectionTblBitacora.getTblProductoid();
                tblBitacoraCollectionTblBitacora.setTblProductoid(tblProducto);
                tblBitacoraCollectionTblBitacora = em.merge(tblBitacoraCollectionTblBitacora);
                if (oldTblProductoidOfTblBitacoraCollectionTblBitacora != null) {
                    oldTblProductoidOfTblBitacoraCollectionTblBitacora.getTblBitacoraCollection().remove(tblBitacoraCollectionTblBitacora);
                    oldTblProductoidOfTblBitacoraCollectionTblBitacora = em.merge(oldTblProductoidOfTblBitacoraCollectionTblBitacora);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTblProducto(tblProducto.getId()) != null) {
                throw new PreexistingEntityException("TblProducto " + tblProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblProducto tblProducto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblProducto persistentTblProducto = em.find(TblProducto.class, tblProducto.getId());
            TblLinea tblLineaidOld = persistentTblProducto.getTblLineaid();
            TblLinea tblLineaidNew = tblProducto.getTblLineaid();
            Collection<DetalleFactura> detalleFacturaCollectionOld = persistentTblProducto.getDetalleFacturaCollection();
            Collection<DetalleFactura> detalleFacturaCollectionNew = tblProducto.getDetalleFacturaCollection();
            Collection<DetalleDeLote> detalleDeLoteCollectionOld = persistentTblProducto.getDetalleDeLoteCollection();
            Collection<DetalleDeLote> detalleDeLoteCollectionNew = tblProducto.getDetalleDeLoteCollection();
            Collection<TblBitacora> tblBitacoraCollectionOld = persistentTblProducto.getTblBitacoraCollection();
            Collection<TblBitacora> tblBitacoraCollectionNew = tblProducto.getTblBitacoraCollection();
            List<String> illegalOrphanMessages = null;
            for (DetalleFactura detalleFacturaCollectionOldDetalleFactura : detalleFacturaCollectionOld) {
                if (!detalleFacturaCollectionNew.contains(detalleFacturaCollectionOldDetalleFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleFactura " + detalleFacturaCollectionOldDetalleFactura + " since its tblProductoid field is not nullable.");
                }
            }
            for (DetalleDeLote detalleDeLoteCollectionOldDetalleDeLote : detalleDeLoteCollectionOld) {
                if (!detalleDeLoteCollectionNew.contains(detalleDeLoteCollectionOldDetalleDeLote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleDeLote " + detalleDeLoteCollectionOldDetalleDeLote + " since its tblProductoid field is not nullable.");
                }
            }
            for (TblBitacora tblBitacoraCollectionOldTblBitacora : tblBitacoraCollectionOld) {
                if (!tblBitacoraCollectionNew.contains(tblBitacoraCollectionOldTblBitacora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblBitacora " + tblBitacoraCollectionOldTblBitacora + " since its tblProductoid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tblLineaidNew != null) {
                tblLineaidNew = em.getReference(tblLineaidNew.getClass(), tblLineaidNew.getId());
                tblProducto.setTblLineaid(tblLineaidNew);
            }
            Collection<DetalleFactura> attachedDetalleFacturaCollectionNew = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionNewDetalleFacturaToAttach : detalleFacturaCollectionNew) {
                detalleFacturaCollectionNewDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionNewDetalleFacturaToAttach.getClass(), detalleFacturaCollectionNewDetalleFacturaToAttach.getId());
                attachedDetalleFacturaCollectionNew.add(detalleFacturaCollectionNewDetalleFacturaToAttach);
            }
            detalleFacturaCollectionNew = attachedDetalleFacturaCollectionNew;
            tblProducto.setDetalleFacturaCollection(detalleFacturaCollectionNew);
            Collection<DetalleDeLote> attachedDetalleDeLoteCollectionNew = new ArrayList<DetalleDeLote>();
            for (DetalleDeLote detalleDeLoteCollectionNewDetalleDeLoteToAttach : detalleDeLoteCollectionNew) {
                detalleDeLoteCollectionNewDetalleDeLoteToAttach = em.getReference(detalleDeLoteCollectionNewDetalleDeLoteToAttach.getClass(), detalleDeLoteCollectionNewDetalleDeLoteToAttach.getId());
                attachedDetalleDeLoteCollectionNew.add(detalleDeLoteCollectionNewDetalleDeLoteToAttach);
            }
            detalleDeLoteCollectionNew = attachedDetalleDeLoteCollectionNew;
            tblProducto.setDetalleDeLoteCollection(detalleDeLoteCollectionNew);
            Collection<TblBitacora> attachedTblBitacoraCollectionNew = new ArrayList<TblBitacora>();
            for (TblBitacora tblBitacoraCollectionNewTblBitacoraToAttach : tblBitacoraCollectionNew) {
                tblBitacoraCollectionNewTblBitacoraToAttach = em.getReference(tblBitacoraCollectionNewTblBitacoraToAttach.getClass(), tblBitacoraCollectionNewTblBitacoraToAttach.getId());
                attachedTblBitacoraCollectionNew.add(tblBitacoraCollectionNewTblBitacoraToAttach);
            }
            tblBitacoraCollectionNew = attachedTblBitacoraCollectionNew;
            tblProducto.setTblBitacoraCollection(tblBitacoraCollectionNew);
            tblProducto = em.merge(tblProducto);
            if (tblLineaidOld != null && !tblLineaidOld.equals(tblLineaidNew)) {
                tblLineaidOld.getTblProductoCollection().remove(tblProducto);
                tblLineaidOld = em.merge(tblLineaidOld);
            }
            if (tblLineaidNew != null && !tblLineaidNew.equals(tblLineaidOld)) {
                tblLineaidNew.getTblProductoCollection().add(tblProducto);
                tblLineaidNew = em.merge(tblLineaidNew);
            }
            for (DetalleFactura detalleFacturaCollectionNewDetalleFactura : detalleFacturaCollectionNew) {
                if (!detalleFacturaCollectionOld.contains(detalleFacturaCollectionNewDetalleFactura)) {
                    TblProducto oldTblProductoidOfDetalleFacturaCollectionNewDetalleFactura = detalleFacturaCollectionNewDetalleFactura.getTblProductoid();
                    detalleFacturaCollectionNewDetalleFactura.setTblProductoid(tblProducto);
                    detalleFacturaCollectionNewDetalleFactura = em.merge(detalleFacturaCollectionNewDetalleFactura);
                    if (oldTblProductoidOfDetalleFacturaCollectionNewDetalleFactura != null && !oldTblProductoidOfDetalleFacturaCollectionNewDetalleFactura.equals(tblProducto)) {
                        oldTblProductoidOfDetalleFacturaCollectionNewDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionNewDetalleFactura);
                        oldTblProductoidOfDetalleFacturaCollectionNewDetalleFactura = em.merge(oldTblProductoidOfDetalleFacturaCollectionNewDetalleFactura);
                    }
                }
            }
            for (DetalleDeLote detalleDeLoteCollectionNewDetalleDeLote : detalleDeLoteCollectionNew) {
                if (!detalleDeLoteCollectionOld.contains(detalleDeLoteCollectionNewDetalleDeLote)) {
                    TblProducto oldTblProductoidOfDetalleDeLoteCollectionNewDetalleDeLote = detalleDeLoteCollectionNewDetalleDeLote.getTblProductoid();
                    detalleDeLoteCollectionNewDetalleDeLote.setTblProductoid(tblProducto);
                    detalleDeLoteCollectionNewDetalleDeLote = em.merge(detalleDeLoteCollectionNewDetalleDeLote);
                    if (oldTblProductoidOfDetalleDeLoteCollectionNewDetalleDeLote != null && !oldTblProductoidOfDetalleDeLoteCollectionNewDetalleDeLote.equals(tblProducto)) {
                        oldTblProductoidOfDetalleDeLoteCollectionNewDetalleDeLote.getDetalleDeLoteCollection().remove(detalleDeLoteCollectionNewDetalleDeLote);
                        oldTblProductoidOfDetalleDeLoteCollectionNewDetalleDeLote = em.merge(oldTblProductoidOfDetalleDeLoteCollectionNewDetalleDeLote);
                    }
                }
            }
            for (TblBitacora tblBitacoraCollectionNewTblBitacora : tblBitacoraCollectionNew) {
                if (!tblBitacoraCollectionOld.contains(tblBitacoraCollectionNewTblBitacora)) {
                    TblProducto oldTblProductoidOfTblBitacoraCollectionNewTblBitacora = tblBitacoraCollectionNewTblBitacora.getTblProductoid();
                    tblBitacoraCollectionNewTblBitacora.setTblProductoid(tblProducto);
                    tblBitacoraCollectionNewTblBitacora = em.merge(tblBitacoraCollectionNewTblBitacora);
                    if (oldTblProductoidOfTblBitacoraCollectionNewTblBitacora != null && !oldTblProductoidOfTblBitacoraCollectionNewTblBitacora.equals(tblProducto)) {
                        oldTblProductoidOfTblBitacoraCollectionNewTblBitacora.getTblBitacoraCollection().remove(tblBitacoraCollectionNewTblBitacora);
                        oldTblProductoidOfTblBitacoraCollectionNewTblBitacora = em.merge(oldTblProductoidOfTblBitacoraCollectionNewTblBitacora);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tblProducto.getId();
                if (findTblProducto(id) == null) {
                    throw new NonexistentEntityException("The tblProducto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblProducto tblProducto;
            try {
                tblProducto = em.getReference(TblProducto.class, id);
                tblProducto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblProducto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetalleFactura> detalleFacturaCollectionOrphanCheck = tblProducto.getDetalleFacturaCollection();
            for (DetalleFactura detalleFacturaCollectionOrphanCheckDetalleFactura : detalleFacturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblProducto (" + tblProducto + ") cannot be destroyed since the DetalleFactura " + detalleFacturaCollectionOrphanCheckDetalleFactura + " in its detalleFacturaCollection field has a non-nullable tblProductoid field.");
            }
            Collection<DetalleDeLote> detalleDeLoteCollectionOrphanCheck = tblProducto.getDetalleDeLoteCollection();
            for (DetalleDeLote detalleDeLoteCollectionOrphanCheckDetalleDeLote : detalleDeLoteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblProducto (" + tblProducto + ") cannot be destroyed since the DetalleDeLote " + detalleDeLoteCollectionOrphanCheckDetalleDeLote + " in its detalleDeLoteCollection field has a non-nullable tblProductoid field.");
            }
            Collection<TblBitacora> tblBitacoraCollectionOrphanCheck = tblProducto.getTblBitacoraCollection();
            for (TblBitacora tblBitacoraCollectionOrphanCheckTblBitacora : tblBitacoraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblProducto (" + tblProducto + ") cannot be destroyed since the TblBitacora " + tblBitacoraCollectionOrphanCheckTblBitacora + " in its tblBitacoraCollection field has a non-nullable tblProductoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TblLinea tblLineaid = tblProducto.getTblLineaid();
            if (tblLineaid != null) {
                tblLineaid.getTblProductoCollection().remove(tblProducto);
                tblLineaid = em.merge(tblLineaid);
            }
            em.remove(tblProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TblProducto> findTblProductoEntities() {
        return findTblProductoEntities(true, -1, -1);
    }

    public List<TblProducto> findTblProductoEntities(int maxResults, int firstResult) {
        return findTblProductoEntities(false, maxResults, firstResult);
    }

    private List<TblProducto> findTblProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblProducto.class));
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

    public TblProducto findTblProducto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblProducto> rt = cq.from(TblProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
