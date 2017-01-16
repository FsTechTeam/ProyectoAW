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
import entidades.TblTipoUsuario;
import entidades.TblFactura;
import entidades.TblUsuarios;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lestermeneses
 */
public class TblUsuariosJpaController implements Serializable {

    public TblUsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblUsuarios tblUsuarios) {
        if (tblUsuarios.getTblFacturaCollection() == null) {
            tblUsuarios.setTblFacturaCollection(new ArrayList<TblFactura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblTipoUsuario tblTipoUsuarioid = tblUsuarios.getTblTipoUsuarioid();
            if (tblTipoUsuarioid != null) {
                tblTipoUsuarioid = em.getReference(tblTipoUsuarioid.getClass(), tblTipoUsuarioid.getId());
                tblUsuarios.setTblTipoUsuarioid(tblTipoUsuarioid);
            }
            Collection<TblFactura> attachedTblFacturaCollection = new ArrayList<TblFactura>();
            for (TblFactura tblFacturaCollectionTblFacturaToAttach : tblUsuarios.getTblFacturaCollection()) {
                tblFacturaCollectionTblFacturaToAttach = em.getReference(tblFacturaCollectionTblFacturaToAttach.getClass(), tblFacturaCollectionTblFacturaToAttach.getId());
                attachedTblFacturaCollection.add(tblFacturaCollectionTblFacturaToAttach);
            }
            tblUsuarios.setTblFacturaCollection(attachedTblFacturaCollection);
            em.persist(tblUsuarios);
            if (tblTipoUsuarioid != null) {
                tblTipoUsuarioid.getTblUsuariosCollection().add(tblUsuarios);
                tblTipoUsuarioid = em.merge(tblTipoUsuarioid);
            }
            for (TblFactura tblFacturaCollectionTblFactura : tblUsuarios.getTblFacturaCollection()) {
                TblUsuarios oldTblUsuariosidOfTblFacturaCollectionTblFactura = tblFacturaCollectionTblFactura.getTblUsuariosid();
                tblFacturaCollectionTblFactura.setTblUsuariosid(tblUsuarios);
                tblFacturaCollectionTblFactura = em.merge(tblFacturaCollectionTblFactura);
                if (oldTblUsuariosidOfTblFacturaCollectionTblFactura != null) {
                    oldTblUsuariosidOfTblFacturaCollectionTblFactura.getTblFacturaCollection().remove(tblFacturaCollectionTblFactura);
                    oldTblUsuariosidOfTblFacturaCollectionTblFactura = em.merge(oldTblUsuariosidOfTblFacturaCollectionTblFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblUsuarios tblUsuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblUsuarios persistentTblUsuarios = em.find(TblUsuarios.class, tblUsuarios.getId());
            TblTipoUsuario tblTipoUsuarioidOld = persistentTblUsuarios.getTblTipoUsuarioid();
            TblTipoUsuario tblTipoUsuarioidNew = tblUsuarios.getTblTipoUsuarioid();
            Collection<TblFactura> tblFacturaCollectionOld = persistentTblUsuarios.getTblFacturaCollection();
            Collection<TblFactura> tblFacturaCollectionNew = tblUsuarios.getTblFacturaCollection();
            List<String> illegalOrphanMessages = null;
            for (TblFactura tblFacturaCollectionOldTblFactura : tblFacturaCollectionOld) {
                if (!tblFacturaCollectionNew.contains(tblFacturaCollectionOldTblFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblFactura " + tblFacturaCollectionOldTblFactura + " since its tblUsuariosid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tblTipoUsuarioidNew != null) {
                tblTipoUsuarioidNew = em.getReference(tblTipoUsuarioidNew.getClass(), tblTipoUsuarioidNew.getId());
                tblUsuarios.setTblTipoUsuarioid(tblTipoUsuarioidNew);
            }
            Collection<TblFactura> attachedTblFacturaCollectionNew = new ArrayList<TblFactura>();
            for (TblFactura tblFacturaCollectionNewTblFacturaToAttach : tblFacturaCollectionNew) {
                tblFacturaCollectionNewTblFacturaToAttach = em.getReference(tblFacturaCollectionNewTblFacturaToAttach.getClass(), tblFacturaCollectionNewTblFacturaToAttach.getId());
                attachedTblFacturaCollectionNew.add(tblFacturaCollectionNewTblFacturaToAttach);
            }
            tblFacturaCollectionNew = attachedTblFacturaCollectionNew;
            tblUsuarios.setTblFacturaCollection(tblFacturaCollectionNew);
            tblUsuarios = em.merge(tblUsuarios);
            if (tblTipoUsuarioidOld != null && !tblTipoUsuarioidOld.equals(tblTipoUsuarioidNew)) {
                tblTipoUsuarioidOld.getTblUsuariosCollection().remove(tblUsuarios);
                tblTipoUsuarioidOld = em.merge(tblTipoUsuarioidOld);
            }
            if (tblTipoUsuarioidNew != null && !tblTipoUsuarioidNew.equals(tblTipoUsuarioidOld)) {
                tblTipoUsuarioidNew.getTblUsuariosCollection().add(tblUsuarios);
                tblTipoUsuarioidNew = em.merge(tblTipoUsuarioidNew);
            }
            for (TblFactura tblFacturaCollectionNewTblFactura : tblFacturaCollectionNew) {
                if (!tblFacturaCollectionOld.contains(tblFacturaCollectionNewTblFactura)) {
                    TblUsuarios oldTblUsuariosidOfTblFacturaCollectionNewTblFactura = tblFacturaCollectionNewTblFactura.getTblUsuariosid();
                    tblFacturaCollectionNewTblFactura.setTblUsuariosid(tblUsuarios);
                    tblFacturaCollectionNewTblFactura = em.merge(tblFacturaCollectionNewTblFactura);
                    if (oldTblUsuariosidOfTblFacturaCollectionNewTblFactura != null && !oldTblUsuariosidOfTblFacturaCollectionNewTblFactura.equals(tblUsuarios)) {
                        oldTblUsuariosidOfTblFacturaCollectionNewTblFactura.getTblFacturaCollection().remove(tblFacturaCollectionNewTblFactura);
                        oldTblUsuariosidOfTblFacturaCollectionNewTblFactura = em.merge(oldTblUsuariosidOfTblFacturaCollectionNewTblFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblUsuarios.getId();
                if (findTblUsuarios(id) == null) {
                    throw new NonexistentEntityException("The tblUsuarios with id " + id + " no longer exists.");
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
            TblUsuarios tblUsuarios;
            try {
                tblUsuarios = em.getReference(TblUsuarios.class, id);
                tblUsuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblUsuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TblFactura> tblFacturaCollectionOrphanCheck = tblUsuarios.getTblFacturaCollection();
            for (TblFactura tblFacturaCollectionOrphanCheckTblFactura : tblFacturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblUsuarios (" + tblUsuarios + ") cannot be destroyed since the TblFactura " + tblFacturaCollectionOrphanCheckTblFactura + " in its tblFacturaCollection field has a non-nullable tblUsuariosid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TblTipoUsuario tblTipoUsuarioid = tblUsuarios.getTblTipoUsuarioid();
            if (tblTipoUsuarioid != null) {
                tblTipoUsuarioid.getTblUsuariosCollection().remove(tblUsuarios);
                tblTipoUsuarioid = em.merge(tblTipoUsuarioid);
            }
            em.remove(tblUsuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TblUsuarios> findTblUsuariosEntities() {
        return findTblUsuariosEntities(true, -1, -1);
    }

    public List<TblUsuarios> findTblUsuariosEntities(int maxResults, int firstResult) {
        return findTblUsuariosEntities(false, maxResults, firstResult);
    }

    private List<TblUsuarios> findTblUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblUsuarios.class));
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

    public TblUsuarios findTblUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblUsuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblUsuarios> rt = cq.from(TblUsuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
