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
import entidades.TblCliente;
import entidades.TblUsuarios;
import entidades.DetalleFactura;
import entidades.TblFactura;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lestermeneses
 */
public class TblFacturaJpaController implements Serializable {

    public TblFacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblFactura tblFactura) {
        if (tblFactura.getDetalleFacturaCollection() == null) {
            tblFactura.setDetalleFacturaCollection(new ArrayList<DetalleFactura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblCliente tblClienteid = tblFactura.getTblClienteid();
            if (tblClienteid != null) {
                tblClienteid = em.getReference(tblClienteid.getClass(), tblClienteid.getId());
                tblFactura.setTblClienteid(tblClienteid);
            }
            TblUsuarios tblUsuariosid = tblFactura.getTblUsuariosid();
            if (tblUsuariosid != null) {
                tblUsuariosid = em.getReference(tblUsuariosid.getClass(), tblUsuariosid.getId());
                tblFactura.setTblUsuariosid(tblUsuariosid);
            }
            Collection<DetalleFactura> attachedDetalleFacturaCollection = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionDetalleFacturaToAttach : tblFactura.getDetalleFacturaCollection()) {
                detalleFacturaCollectionDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionDetalleFacturaToAttach.getClass(), detalleFacturaCollectionDetalleFacturaToAttach.getId());
                attachedDetalleFacturaCollection.add(detalleFacturaCollectionDetalleFacturaToAttach);
            }
            tblFactura.setDetalleFacturaCollection(attachedDetalleFacturaCollection);
            em.persist(tblFactura);
            if (tblClienteid != null) {
                tblClienteid.getTblFacturaCollection().add(tblFactura);
                tblClienteid = em.merge(tblClienteid);
            }
            if (tblUsuariosid != null) {
                tblUsuariosid.getTblFacturaCollection().add(tblFactura);
                tblUsuariosid = em.merge(tblUsuariosid);
            }
            for (DetalleFactura detalleFacturaCollectionDetalleFactura : tblFactura.getDetalleFacturaCollection()) {
                TblFactura oldTblFacturaidOfDetalleFacturaCollectionDetalleFactura = detalleFacturaCollectionDetalleFactura.getTblFacturaid();
                detalleFacturaCollectionDetalleFactura.setTblFacturaid(tblFactura);
                detalleFacturaCollectionDetalleFactura = em.merge(detalleFacturaCollectionDetalleFactura);
                if (oldTblFacturaidOfDetalleFacturaCollectionDetalleFactura != null) {
                    oldTblFacturaidOfDetalleFacturaCollectionDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionDetalleFactura);
                    oldTblFacturaidOfDetalleFacturaCollectionDetalleFactura = em.merge(oldTblFacturaidOfDetalleFacturaCollectionDetalleFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblFactura tblFactura) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblFactura persistentTblFactura = em.find(TblFactura.class, tblFactura.getId());
            TblCliente tblClienteidOld = persistentTblFactura.getTblClienteid();
            TblCliente tblClienteidNew = tblFactura.getTblClienteid();
            TblUsuarios tblUsuariosidOld = persistentTblFactura.getTblUsuariosid();
            TblUsuarios tblUsuariosidNew = tblFactura.getTblUsuariosid();
            Collection<DetalleFactura> detalleFacturaCollectionOld = persistentTblFactura.getDetalleFacturaCollection();
            Collection<DetalleFactura> detalleFacturaCollectionNew = tblFactura.getDetalleFacturaCollection();
            List<String> illegalOrphanMessages = null;
            for (DetalleFactura detalleFacturaCollectionOldDetalleFactura : detalleFacturaCollectionOld) {
                if (!detalleFacturaCollectionNew.contains(detalleFacturaCollectionOldDetalleFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleFactura " + detalleFacturaCollectionOldDetalleFactura + " since its tblFacturaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tblClienteidNew != null) {
                tblClienteidNew = em.getReference(tblClienteidNew.getClass(), tblClienteidNew.getId());
                tblFactura.setTblClienteid(tblClienteidNew);
            }
            if (tblUsuariosidNew != null) {
                tblUsuariosidNew = em.getReference(tblUsuariosidNew.getClass(), tblUsuariosidNew.getId());
                tblFactura.setTblUsuariosid(tblUsuariosidNew);
            }
            Collection<DetalleFactura> attachedDetalleFacturaCollectionNew = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionNewDetalleFacturaToAttach : detalleFacturaCollectionNew) {
                detalleFacturaCollectionNewDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionNewDetalleFacturaToAttach.getClass(), detalleFacturaCollectionNewDetalleFacturaToAttach.getId());
                attachedDetalleFacturaCollectionNew.add(detalleFacturaCollectionNewDetalleFacturaToAttach);
            }
            detalleFacturaCollectionNew = attachedDetalleFacturaCollectionNew;
            tblFactura.setDetalleFacturaCollection(detalleFacturaCollectionNew);
            tblFactura = em.merge(tblFactura);
            if (tblClienteidOld != null && !tblClienteidOld.equals(tblClienteidNew)) {
                tblClienteidOld.getTblFacturaCollection().remove(tblFactura);
                tblClienteidOld = em.merge(tblClienteidOld);
            }
            if (tblClienteidNew != null && !tblClienteidNew.equals(tblClienteidOld)) {
                tblClienteidNew.getTblFacturaCollection().add(tblFactura);
                tblClienteidNew = em.merge(tblClienteidNew);
            }
            if (tblUsuariosidOld != null && !tblUsuariosidOld.equals(tblUsuariosidNew)) {
                tblUsuariosidOld.getTblFacturaCollection().remove(tblFactura);
                tblUsuariosidOld = em.merge(tblUsuariosidOld);
            }
            if (tblUsuariosidNew != null && !tblUsuariosidNew.equals(tblUsuariosidOld)) {
                tblUsuariosidNew.getTblFacturaCollection().add(tblFactura);
                tblUsuariosidNew = em.merge(tblUsuariosidNew);
            }
            for (DetalleFactura detalleFacturaCollectionNewDetalleFactura : detalleFacturaCollectionNew) {
                if (!detalleFacturaCollectionOld.contains(detalleFacturaCollectionNewDetalleFactura)) {
                    TblFactura oldTblFacturaidOfDetalleFacturaCollectionNewDetalleFactura = detalleFacturaCollectionNewDetalleFactura.getTblFacturaid();
                    detalleFacturaCollectionNewDetalleFactura.setTblFacturaid(tblFactura);
                    detalleFacturaCollectionNewDetalleFactura = em.merge(detalleFacturaCollectionNewDetalleFactura);
                    if (oldTblFacturaidOfDetalleFacturaCollectionNewDetalleFactura != null && !oldTblFacturaidOfDetalleFacturaCollectionNewDetalleFactura.equals(tblFactura)) {
                        oldTblFacturaidOfDetalleFacturaCollectionNewDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionNewDetalleFactura);
                        oldTblFacturaidOfDetalleFacturaCollectionNewDetalleFactura = em.merge(oldTblFacturaidOfDetalleFacturaCollectionNewDetalleFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblFactura.getId();
                if (findTblFactura(id) == null) {
                    throw new NonexistentEntityException("The tblFactura with id " + id + " no longer exists.");
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
            TblFactura tblFactura;
            try {
                tblFactura = em.getReference(TblFactura.class, id);
                tblFactura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblFactura with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetalleFactura> detalleFacturaCollectionOrphanCheck = tblFactura.getDetalleFacturaCollection();
            for (DetalleFactura detalleFacturaCollectionOrphanCheckDetalleFactura : detalleFacturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblFactura (" + tblFactura + ") cannot be destroyed since the DetalleFactura " + detalleFacturaCollectionOrphanCheckDetalleFactura + " in its detalleFacturaCollection field has a non-nullable tblFacturaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TblCliente tblClienteid = tblFactura.getTblClienteid();
            if (tblClienteid != null) {
                tblClienteid.getTblFacturaCollection().remove(tblFactura);
                tblClienteid = em.merge(tblClienteid);
            }
            TblUsuarios tblUsuariosid = tblFactura.getTblUsuariosid();
            if (tblUsuariosid != null) {
                tblUsuariosid.getTblFacturaCollection().remove(tblFactura);
                tblUsuariosid = em.merge(tblUsuariosid);
            }
            em.remove(tblFactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TblFactura> findTblFacturaEntities() {
        return findTblFacturaEntities(true, -1, -1);
    }

    public List<TblFactura> findTblFacturaEntities(int maxResults, int firstResult) {
        return findTblFacturaEntities(false, maxResults, firstResult);
    }

    private List<TblFactura> findTblFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblFactura.class));
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

    public TblFactura findTblFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblFactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblFactura> rt = cq.from(TblFactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
