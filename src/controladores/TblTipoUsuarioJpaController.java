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
import entidades.TblUsuarios;
import java.util.ArrayList;
import java.util.Collection;
import entidades.TblBitacora;
import entidades.TblTipoUsuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lestermeneses
 */
public class TblTipoUsuarioJpaController implements Serializable {

    public TblTipoUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblTipoUsuario tblTipoUsuario) {
        if (tblTipoUsuario.getTblUsuariosCollection() == null) {
            tblTipoUsuario.setTblUsuariosCollection(new ArrayList<TblUsuarios>());
        }
        if (tblTipoUsuario.getTblBitacoraCollection() == null) {
            tblTipoUsuario.setTblBitacoraCollection(new ArrayList<TblBitacora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TblUsuarios> attachedTblUsuariosCollection = new ArrayList<TblUsuarios>();
            for (TblUsuarios tblUsuariosCollectionTblUsuariosToAttach : tblTipoUsuario.getTblUsuariosCollection()) {
                tblUsuariosCollectionTblUsuariosToAttach = em.getReference(tblUsuariosCollectionTblUsuariosToAttach.getClass(), tblUsuariosCollectionTblUsuariosToAttach.getId());
                attachedTblUsuariosCollection.add(tblUsuariosCollectionTblUsuariosToAttach);
            }
            tblTipoUsuario.setTblUsuariosCollection(attachedTblUsuariosCollection);
            Collection<TblBitacora> attachedTblBitacoraCollection = new ArrayList<TblBitacora>();
            for (TblBitacora tblBitacoraCollectionTblBitacoraToAttach : tblTipoUsuario.getTblBitacoraCollection()) {
                tblBitacoraCollectionTblBitacoraToAttach = em.getReference(tblBitacoraCollectionTblBitacoraToAttach.getClass(), tblBitacoraCollectionTblBitacoraToAttach.getId());
                attachedTblBitacoraCollection.add(tblBitacoraCollectionTblBitacoraToAttach);
            }
            tblTipoUsuario.setTblBitacoraCollection(attachedTblBitacoraCollection);
            em.persist(tblTipoUsuario);
            for (TblUsuarios tblUsuariosCollectionTblUsuarios : tblTipoUsuario.getTblUsuariosCollection()) {
                TblTipoUsuario oldTblTipoUsuarioidOfTblUsuariosCollectionTblUsuarios = tblUsuariosCollectionTblUsuarios.getTblTipoUsuarioid();
                tblUsuariosCollectionTblUsuarios.setTblTipoUsuarioid(tblTipoUsuario);
                tblUsuariosCollectionTblUsuarios = em.merge(tblUsuariosCollectionTblUsuarios);
                if (oldTblTipoUsuarioidOfTblUsuariosCollectionTblUsuarios != null) {
                    oldTblTipoUsuarioidOfTblUsuariosCollectionTblUsuarios.getTblUsuariosCollection().remove(tblUsuariosCollectionTblUsuarios);
                    oldTblTipoUsuarioidOfTblUsuariosCollectionTblUsuarios = em.merge(oldTblTipoUsuarioidOfTblUsuariosCollectionTblUsuarios);
                }
            }
            for (TblBitacora tblBitacoraCollectionTblBitacora : tblTipoUsuario.getTblBitacoraCollection()) {
                TblTipoUsuario oldTblTipoUsuarioidOfTblBitacoraCollectionTblBitacora = tblBitacoraCollectionTblBitacora.getTblTipoUsuarioid();
                tblBitacoraCollectionTblBitacora.setTblTipoUsuarioid(tblTipoUsuario);
                tblBitacoraCollectionTblBitacora = em.merge(tblBitacoraCollectionTblBitacora);
                if (oldTblTipoUsuarioidOfTblBitacoraCollectionTblBitacora != null) {
                    oldTblTipoUsuarioidOfTblBitacoraCollectionTblBitacora.getTblBitacoraCollection().remove(tblBitacoraCollectionTblBitacora);
                    oldTblTipoUsuarioidOfTblBitacoraCollectionTblBitacora = em.merge(oldTblTipoUsuarioidOfTblBitacoraCollectionTblBitacora);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblTipoUsuario tblTipoUsuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblTipoUsuario persistentTblTipoUsuario = em.find(TblTipoUsuario.class, tblTipoUsuario.getId());
            Collection<TblUsuarios> tblUsuariosCollectionOld = persistentTblTipoUsuario.getTblUsuariosCollection();
            Collection<TblUsuarios> tblUsuariosCollectionNew = tblTipoUsuario.getTblUsuariosCollection();
            Collection<TblBitacora> tblBitacoraCollectionOld = persistentTblTipoUsuario.getTblBitacoraCollection();
            Collection<TblBitacora> tblBitacoraCollectionNew = tblTipoUsuario.getTblBitacoraCollection();
            List<String> illegalOrphanMessages = null;
            for (TblUsuarios tblUsuariosCollectionOldTblUsuarios : tblUsuariosCollectionOld) {
                if (!tblUsuariosCollectionNew.contains(tblUsuariosCollectionOldTblUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblUsuarios " + tblUsuariosCollectionOldTblUsuarios + " since its tblTipoUsuarioid field is not nullable.");
                }
            }
            for (TblBitacora tblBitacoraCollectionOldTblBitacora : tblBitacoraCollectionOld) {
                if (!tblBitacoraCollectionNew.contains(tblBitacoraCollectionOldTblBitacora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblBitacora " + tblBitacoraCollectionOldTblBitacora + " since its tblTipoUsuarioid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TblUsuarios> attachedTblUsuariosCollectionNew = new ArrayList<TblUsuarios>();
            for (TblUsuarios tblUsuariosCollectionNewTblUsuariosToAttach : tblUsuariosCollectionNew) {
                tblUsuariosCollectionNewTblUsuariosToAttach = em.getReference(tblUsuariosCollectionNewTblUsuariosToAttach.getClass(), tblUsuariosCollectionNewTblUsuariosToAttach.getId());
                attachedTblUsuariosCollectionNew.add(tblUsuariosCollectionNewTblUsuariosToAttach);
            }
            tblUsuariosCollectionNew = attachedTblUsuariosCollectionNew;
            tblTipoUsuario.setTblUsuariosCollection(tblUsuariosCollectionNew);
            Collection<TblBitacora> attachedTblBitacoraCollectionNew = new ArrayList<TblBitacora>();
            for (TblBitacora tblBitacoraCollectionNewTblBitacoraToAttach : tblBitacoraCollectionNew) {
                tblBitacoraCollectionNewTblBitacoraToAttach = em.getReference(tblBitacoraCollectionNewTblBitacoraToAttach.getClass(), tblBitacoraCollectionNewTblBitacoraToAttach.getId());
                attachedTblBitacoraCollectionNew.add(tblBitacoraCollectionNewTblBitacoraToAttach);
            }
            tblBitacoraCollectionNew = attachedTblBitacoraCollectionNew;
            tblTipoUsuario.setTblBitacoraCollection(tblBitacoraCollectionNew);
            tblTipoUsuario = em.merge(tblTipoUsuario);
            for (TblUsuarios tblUsuariosCollectionNewTblUsuarios : tblUsuariosCollectionNew) {
                if (!tblUsuariosCollectionOld.contains(tblUsuariosCollectionNewTblUsuarios)) {
                    TblTipoUsuario oldTblTipoUsuarioidOfTblUsuariosCollectionNewTblUsuarios = tblUsuariosCollectionNewTblUsuarios.getTblTipoUsuarioid();
                    tblUsuariosCollectionNewTblUsuarios.setTblTipoUsuarioid(tblTipoUsuario);
                    tblUsuariosCollectionNewTblUsuarios = em.merge(tblUsuariosCollectionNewTblUsuarios);
                    if (oldTblTipoUsuarioidOfTblUsuariosCollectionNewTblUsuarios != null && !oldTblTipoUsuarioidOfTblUsuariosCollectionNewTblUsuarios.equals(tblTipoUsuario)) {
                        oldTblTipoUsuarioidOfTblUsuariosCollectionNewTblUsuarios.getTblUsuariosCollection().remove(tblUsuariosCollectionNewTblUsuarios);
                        oldTblTipoUsuarioidOfTblUsuariosCollectionNewTblUsuarios = em.merge(oldTblTipoUsuarioidOfTblUsuariosCollectionNewTblUsuarios);
                    }
                }
            }
            for (TblBitacora tblBitacoraCollectionNewTblBitacora : tblBitacoraCollectionNew) {
                if (!tblBitacoraCollectionOld.contains(tblBitacoraCollectionNewTblBitacora)) {
                    TblTipoUsuario oldTblTipoUsuarioidOfTblBitacoraCollectionNewTblBitacora = tblBitacoraCollectionNewTblBitacora.getTblTipoUsuarioid();
                    tblBitacoraCollectionNewTblBitacora.setTblTipoUsuarioid(tblTipoUsuario);
                    tblBitacoraCollectionNewTblBitacora = em.merge(tblBitacoraCollectionNewTblBitacora);
                    if (oldTblTipoUsuarioidOfTblBitacoraCollectionNewTblBitacora != null && !oldTblTipoUsuarioidOfTblBitacoraCollectionNewTblBitacora.equals(tblTipoUsuario)) {
                        oldTblTipoUsuarioidOfTblBitacoraCollectionNewTblBitacora.getTblBitacoraCollection().remove(tblBitacoraCollectionNewTblBitacora);
                        oldTblTipoUsuarioidOfTblBitacoraCollectionNewTblBitacora = em.merge(oldTblTipoUsuarioidOfTblBitacoraCollectionNewTblBitacora);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblTipoUsuario.getId();
                if (findTblTipoUsuario(id) == null) {
                    throw new NonexistentEntityException("The tblTipoUsuario with id " + id + " no longer exists.");
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
            TblTipoUsuario tblTipoUsuario;
            try {
                tblTipoUsuario = em.getReference(TblTipoUsuario.class, id);
                tblTipoUsuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblTipoUsuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TblUsuarios> tblUsuariosCollectionOrphanCheck = tblTipoUsuario.getTblUsuariosCollection();
            for (TblUsuarios tblUsuariosCollectionOrphanCheckTblUsuarios : tblUsuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblTipoUsuario (" + tblTipoUsuario + ") cannot be destroyed since the TblUsuarios " + tblUsuariosCollectionOrphanCheckTblUsuarios + " in its tblUsuariosCollection field has a non-nullable tblTipoUsuarioid field.");
            }
            Collection<TblBitacora> tblBitacoraCollectionOrphanCheck = tblTipoUsuario.getTblBitacoraCollection();
            for (TblBitacora tblBitacoraCollectionOrphanCheckTblBitacora : tblBitacoraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblTipoUsuario (" + tblTipoUsuario + ") cannot be destroyed since the TblBitacora " + tblBitacoraCollectionOrphanCheckTblBitacora + " in its tblBitacoraCollection field has a non-nullable tblTipoUsuarioid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tblTipoUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TblTipoUsuario> findTblTipoUsuarioEntities() {
        return findTblTipoUsuarioEntities(true, -1, -1);
    }

    public List<TblTipoUsuario> findTblTipoUsuarioEntities(int maxResults, int firstResult) {
        return findTblTipoUsuarioEntities(false, maxResults, firstResult);
    }

    private List<TblTipoUsuario> findTblTipoUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblTipoUsuario.class));
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

    public TblTipoUsuario findTblTipoUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblTipoUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblTipoUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblTipoUsuario> rt = cq.from(TblTipoUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
