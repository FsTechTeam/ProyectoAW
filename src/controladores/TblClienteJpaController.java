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
import java.util.ArrayList;
import java.util.Collection;
import entidades.TblFactura;
import entidades.TblHistorialPuntos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lestermeneses
 */
public class TblClienteJpaController implements Serializable {

    public TblClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblCliente tblCliente) {
        if (tblCliente.getTblClienteCollection() == null) {
            tblCliente.setTblClienteCollection(new ArrayList<TblCliente>());
        }
        if (tblCliente.getTblFacturaCollection() == null) {
            tblCliente.setTblFacturaCollection(new ArrayList<TblFactura>());
        }
        if (tblCliente.getTblHistorialPuntosCollection() == null) {
            tblCliente.setTblHistorialPuntosCollection(new ArrayList<TblHistorialPuntos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblCliente tblClienteid = tblCliente.getTblClienteid();
            if (tblClienteid != null) {
                tblClienteid = em.getReference(tblClienteid.getClass(), tblClienteid.getId());
                tblCliente.setTblClienteid(tblClienteid);
            }
            Collection<TblCliente> attachedTblClienteCollection = new ArrayList<TblCliente>();
            for (TblCliente tblClienteCollectionTblClienteToAttach : tblCliente.getTblClienteCollection()) {
                tblClienteCollectionTblClienteToAttach = em.getReference(tblClienteCollectionTblClienteToAttach.getClass(), tblClienteCollectionTblClienteToAttach.getId());
                attachedTblClienteCollection.add(tblClienteCollectionTblClienteToAttach);
            }
            tblCliente.setTblClienteCollection(attachedTblClienteCollection);
            Collection<TblFactura> attachedTblFacturaCollection = new ArrayList<TblFactura>();
            for (TblFactura tblFacturaCollectionTblFacturaToAttach : tblCliente.getTblFacturaCollection()) {
                tblFacturaCollectionTblFacturaToAttach = em.getReference(tblFacturaCollectionTblFacturaToAttach.getClass(), tblFacturaCollectionTblFacturaToAttach.getId());
                attachedTblFacturaCollection.add(tblFacturaCollectionTblFacturaToAttach);
            }
            tblCliente.setTblFacturaCollection(attachedTblFacturaCollection);
            Collection<TblHistorialPuntos> attachedTblHistorialPuntosCollection = new ArrayList<TblHistorialPuntos>();
            for (TblHistorialPuntos tblHistorialPuntosCollectionTblHistorialPuntosToAttach : tblCliente.getTblHistorialPuntosCollection()) {
                tblHistorialPuntosCollectionTblHistorialPuntosToAttach = em.getReference(tblHistorialPuntosCollectionTblHistorialPuntosToAttach.getClass(), tblHistorialPuntosCollectionTblHistorialPuntosToAttach.getId());
                attachedTblHistorialPuntosCollection.add(tblHistorialPuntosCollectionTblHistorialPuntosToAttach);
            }
            tblCliente.setTblHistorialPuntosCollection(attachedTblHistorialPuntosCollection);
            em.persist(tblCliente);
            if (tblClienteid != null) {
                tblClienteid.getTblClienteCollection().add(tblCliente);
                tblClienteid = em.merge(tblClienteid);
            }
            for (TblCliente tblClienteCollectionTblCliente : tblCliente.getTblClienteCollection()) {
                TblCliente oldTblClienteidOfTblClienteCollectionTblCliente = tblClienteCollectionTblCliente.getTblClienteid();
                tblClienteCollectionTblCliente.setTblClienteid(tblCliente);
                tblClienteCollectionTblCliente = em.merge(tblClienteCollectionTblCliente);
                if (oldTblClienteidOfTblClienteCollectionTblCliente != null) {
                    oldTblClienteidOfTblClienteCollectionTblCliente.getTblClienteCollection().remove(tblClienteCollectionTblCliente);
                    oldTblClienteidOfTblClienteCollectionTblCliente = em.merge(oldTblClienteidOfTblClienteCollectionTblCliente);
                }
            }
            for (TblFactura tblFacturaCollectionTblFactura : tblCliente.getTblFacturaCollection()) {
                TblCliente oldTblClienteidOfTblFacturaCollectionTblFactura = tblFacturaCollectionTblFactura.getTblClienteid();
                tblFacturaCollectionTblFactura.setTblClienteid(tblCliente);
                tblFacturaCollectionTblFactura = em.merge(tblFacturaCollectionTblFactura);
                if (oldTblClienteidOfTblFacturaCollectionTblFactura != null) {
                    oldTblClienteidOfTblFacturaCollectionTblFactura.getTblFacturaCollection().remove(tblFacturaCollectionTblFactura);
                    oldTblClienteidOfTblFacturaCollectionTblFactura = em.merge(oldTblClienteidOfTblFacturaCollectionTblFactura);
                }
            }
            for (TblHistorialPuntos tblHistorialPuntosCollectionTblHistorialPuntos : tblCliente.getTblHistorialPuntosCollection()) {
                TblCliente oldTblClienteidOfTblHistorialPuntosCollectionTblHistorialPuntos = tblHistorialPuntosCollectionTblHistorialPuntos.getTblClienteid();
                tblHistorialPuntosCollectionTblHistorialPuntos.setTblClienteid(tblCliente);
                tblHistorialPuntosCollectionTblHistorialPuntos = em.merge(tblHistorialPuntosCollectionTblHistorialPuntos);
                if (oldTblClienteidOfTblHistorialPuntosCollectionTblHistorialPuntos != null) {
                    oldTblClienteidOfTblHistorialPuntosCollectionTblHistorialPuntos.getTblHistorialPuntosCollection().remove(tblHistorialPuntosCollectionTblHistorialPuntos);
                    oldTblClienteidOfTblHistorialPuntosCollectionTblHistorialPuntos = em.merge(oldTblClienteidOfTblHistorialPuntosCollectionTblHistorialPuntos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblCliente tblCliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblCliente persistentTblCliente = em.find(TblCliente.class, tblCliente.getId());
            TblCliente tblClienteidOld = persistentTblCliente.getTblClienteid();
            TblCliente tblClienteidNew = tblCliente.getTblClienteid();
            Collection<TblCliente> tblClienteCollectionOld = persistentTblCliente.getTblClienteCollection();
            Collection<TblCliente> tblClienteCollectionNew = tblCliente.getTblClienteCollection();
            Collection<TblFactura> tblFacturaCollectionOld = persistentTblCliente.getTblFacturaCollection();
            Collection<TblFactura> tblFacturaCollectionNew = tblCliente.getTblFacturaCollection();
            Collection<TblHistorialPuntos> tblHistorialPuntosCollectionOld = persistentTblCliente.getTblHistorialPuntosCollection();
            Collection<TblHistorialPuntos> tblHistorialPuntosCollectionNew = tblCliente.getTblHistorialPuntosCollection();
            List<String> illegalOrphanMessages = null;
            for (TblCliente tblClienteCollectionOldTblCliente : tblClienteCollectionOld) {
                if (!tblClienteCollectionNew.contains(tblClienteCollectionOldTblCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblCliente " + tblClienteCollectionOldTblCliente + " since its tblClienteid field is not nullable.");
                }
            }
            for (TblFactura tblFacturaCollectionOldTblFactura : tblFacturaCollectionOld) {
                if (!tblFacturaCollectionNew.contains(tblFacturaCollectionOldTblFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblFactura " + tblFacturaCollectionOldTblFactura + " since its tblClienteid field is not nullable.");
                }
            }
            for (TblHistorialPuntos tblHistorialPuntosCollectionOldTblHistorialPuntos : tblHistorialPuntosCollectionOld) {
                if (!tblHistorialPuntosCollectionNew.contains(tblHistorialPuntosCollectionOldTblHistorialPuntos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblHistorialPuntos " + tblHistorialPuntosCollectionOldTblHistorialPuntos + " since its tblClienteid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tblClienteidNew != null) {
                tblClienteidNew = em.getReference(tblClienteidNew.getClass(), tblClienteidNew.getId());
                tblCliente.setTblClienteid(tblClienteidNew);
            }
            Collection<TblCliente> attachedTblClienteCollectionNew = new ArrayList<TblCliente>();
            for (TblCliente tblClienteCollectionNewTblClienteToAttach : tblClienteCollectionNew) {
                tblClienteCollectionNewTblClienteToAttach = em.getReference(tblClienteCollectionNewTblClienteToAttach.getClass(), tblClienteCollectionNewTblClienteToAttach.getId());
                attachedTblClienteCollectionNew.add(tblClienteCollectionNewTblClienteToAttach);
            }
            tblClienteCollectionNew = attachedTblClienteCollectionNew;
            tblCliente.setTblClienteCollection(tblClienteCollectionNew);
            Collection<TblFactura> attachedTblFacturaCollectionNew = new ArrayList<TblFactura>();
            for (TblFactura tblFacturaCollectionNewTblFacturaToAttach : tblFacturaCollectionNew) {
                tblFacturaCollectionNewTblFacturaToAttach = em.getReference(tblFacturaCollectionNewTblFacturaToAttach.getClass(), tblFacturaCollectionNewTblFacturaToAttach.getId());
                attachedTblFacturaCollectionNew.add(tblFacturaCollectionNewTblFacturaToAttach);
            }
            tblFacturaCollectionNew = attachedTblFacturaCollectionNew;
            tblCliente.setTblFacturaCollection(tblFacturaCollectionNew);
            Collection<TblHistorialPuntos> attachedTblHistorialPuntosCollectionNew = new ArrayList<TblHistorialPuntos>();
            for (TblHistorialPuntos tblHistorialPuntosCollectionNewTblHistorialPuntosToAttach : tblHistorialPuntosCollectionNew) {
                tblHistorialPuntosCollectionNewTblHistorialPuntosToAttach = em.getReference(tblHistorialPuntosCollectionNewTblHistorialPuntosToAttach.getClass(), tblHistorialPuntosCollectionNewTblHistorialPuntosToAttach.getId());
                attachedTblHistorialPuntosCollectionNew.add(tblHistorialPuntosCollectionNewTblHistorialPuntosToAttach);
            }
            tblHistorialPuntosCollectionNew = attachedTblHistorialPuntosCollectionNew;
            tblCliente.setTblHistorialPuntosCollection(tblHistorialPuntosCollectionNew);
            tblCliente = em.merge(tblCliente);
            if (tblClienteidOld != null && !tblClienteidOld.equals(tblClienteidNew)) {
                tblClienteidOld.getTblClienteCollection().remove(tblCliente);
                tblClienteidOld = em.merge(tblClienteidOld);
            }
            if (tblClienteidNew != null && !tblClienteidNew.equals(tblClienteidOld)) {
                tblClienteidNew.getTblClienteCollection().add(tblCliente);
                tblClienteidNew = em.merge(tblClienteidNew);
            }
            for (TblCliente tblClienteCollectionNewTblCliente : tblClienteCollectionNew) {
                if (!tblClienteCollectionOld.contains(tblClienteCollectionNewTblCliente)) {
                    TblCliente oldTblClienteidOfTblClienteCollectionNewTblCliente = tblClienteCollectionNewTblCliente.getTblClienteid();
                    tblClienteCollectionNewTblCliente.setTblClienteid(tblCliente);
                    tblClienteCollectionNewTblCliente = em.merge(tblClienteCollectionNewTblCliente);
                    if (oldTblClienteidOfTblClienteCollectionNewTblCliente != null && !oldTblClienteidOfTblClienteCollectionNewTblCliente.equals(tblCliente)) {
                        oldTblClienteidOfTblClienteCollectionNewTblCliente.getTblClienteCollection().remove(tblClienteCollectionNewTblCliente);
                        oldTblClienteidOfTblClienteCollectionNewTblCliente = em.merge(oldTblClienteidOfTblClienteCollectionNewTblCliente);
                    }
                }
            }
            for (TblFactura tblFacturaCollectionNewTblFactura : tblFacturaCollectionNew) {
                if (!tblFacturaCollectionOld.contains(tblFacturaCollectionNewTblFactura)) {
                    TblCliente oldTblClienteidOfTblFacturaCollectionNewTblFactura = tblFacturaCollectionNewTblFactura.getTblClienteid();
                    tblFacturaCollectionNewTblFactura.setTblClienteid(tblCliente);
                    tblFacturaCollectionNewTblFactura = em.merge(tblFacturaCollectionNewTblFactura);
                    if (oldTblClienteidOfTblFacturaCollectionNewTblFactura != null && !oldTblClienteidOfTblFacturaCollectionNewTblFactura.equals(tblCliente)) {
                        oldTblClienteidOfTblFacturaCollectionNewTblFactura.getTblFacturaCollection().remove(tblFacturaCollectionNewTblFactura);
                        oldTblClienteidOfTblFacturaCollectionNewTblFactura = em.merge(oldTblClienteidOfTblFacturaCollectionNewTblFactura);
                    }
                }
            }
            for (TblHistorialPuntos tblHistorialPuntosCollectionNewTblHistorialPuntos : tblHistorialPuntosCollectionNew) {
                if (!tblHistorialPuntosCollectionOld.contains(tblHistorialPuntosCollectionNewTblHistorialPuntos)) {
                    TblCliente oldTblClienteidOfTblHistorialPuntosCollectionNewTblHistorialPuntos = tblHistorialPuntosCollectionNewTblHistorialPuntos.getTblClienteid();
                    tblHistorialPuntosCollectionNewTblHistorialPuntos.setTblClienteid(tblCliente);
                    tblHistorialPuntosCollectionNewTblHistorialPuntos = em.merge(tblHistorialPuntosCollectionNewTblHistorialPuntos);
                    if (oldTblClienteidOfTblHistorialPuntosCollectionNewTblHistorialPuntos != null && !oldTblClienteidOfTblHistorialPuntosCollectionNewTblHistorialPuntos.equals(tblCliente)) {
                        oldTblClienteidOfTblHistorialPuntosCollectionNewTblHistorialPuntos.getTblHistorialPuntosCollection().remove(tblHistorialPuntosCollectionNewTblHistorialPuntos);
                        oldTblClienteidOfTblHistorialPuntosCollectionNewTblHistorialPuntos = em.merge(oldTblClienteidOfTblHistorialPuntosCollectionNewTblHistorialPuntos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblCliente.getId();
                if (findTblCliente(id) == null) {
                    throw new NonexistentEntityException("The tblCliente with id " + id + " no longer exists.");
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
            TblCliente tblCliente;
            try {
                tblCliente = em.getReference(TblCliente.class, id);
                tblCliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblCliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TblCliente> tblClienteCollectionOrphanCheck = tblCliente.getTblClienteCollection();
            for (TblCliente tblClienteCollectionOrphanCheckTblCliente : tblClienteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblCliente (" + tblCliente + ") cannot be destroyed since the TblCliente " + tblClienteCollectionOrphanCheckTblCliente + " in its tblClienteCollection field has a non-nullable tblClienteid field.");
            }
            Collection<TblFactura> tblFacturaCollectionOrphanCheck = tblCliente.getTblFacturaCollection();
            for (TblFactura tblFacturaCollectionOrphanCheckTblFactura : tblFacturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblCliente (" + tblCliente + ") cannot be destroyed since the TblFactura " + tblFacturaCollectionOrphanCheckTblFactura + " in its tblFacturaCollection field has a non-nullable tblClienteid field.");
            }
            Collection<TblHistorialPuntos> tblHistorialPuntosCollectionOrphanCheck = tblCliente.getTblHistorialPuntosCollection();
            for (TblHistorialPuntos tblHistorialPuntosCollectionOrphanCheckTblHistorialPuntos : tblHistorialPuntosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblCliente (" + tblCliente + ") cannot be destroyed since the TblHistorialPuntos " + tblHistorialPuntosCollectionOrphanCheckTblHistorialPuntos + " in its tblHistorialPuntosCollection field has a non-nullable tblClienteid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TblCliente tblClienteid = tblCliente.getTblClienteid();
            if (tblClienteid != null) {
                tblClienteid.getTblClienteCollection().remove(tblCliente);
                tblClienteid = em.merge(tblClienteid);
            }
            em.remove(tblCliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TblCliente> findTblClienteEntities() {
        return findTblClienteEntities(true, -1, -1);
    }

    public List<TblCliente> findTblClienteEntities(int maxResults, int firstResult) {
        return findTblClienteEntities(false, maxResults, firstResult);
    }

    private List<TblCliente> findTblClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblCliente.class));
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

    public TblCliente findTblCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblCliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblCliente> rt = cq.from(TblCliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
