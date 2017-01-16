/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import entidades.DetalleFactura;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.TblFactura;
import entidades.TblProducto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lestermeneses
 */
public class DetalleFacturaJpaController implements Serializable {

    public DetalleFacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleFactura detalleFactura) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblFactura tblFacturaid = detalleFactura.getTblFacturaid();
            if (tblFacturaid != null) {
                tblFacturaid = em.getReference(tblFacturaid.getClass(), tblFacturaid.getId());
                detalleFactura.setTblFacturaid(tblFacturaid);
            }
            TblProducto tblProductoid = detalleFactura.getTblProductoid();
            if (tblProductoid != null) {
                tblProductoid = em.getReference(tblProductoid.getClass(), tblProductoid.getId());
                detalleFactura.setTblProductoid(tblProductoid);
            }
            em.persist(detalleFactura);
            if (tblFacturaid != null) {
                tblFacturaid.getDetalleFacturaCollection().add(detalleFactura);
                tblFacturaid = em.merge(tblFacturaid);
            }
            if (tblProductoid != null) {
                tblProductoid.getDetalleFacturaCollection().add(detalleFactura);
                tblProductoid = em.merge(tblProductoid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleFactura detalleFactura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleFactura persistentDetalleFactura = em.find(DetalleFactura.class, detalleFactura.getId());
            TblFactura tblFacturaidOld = persistentDetalleFactura.getTblFacturaid();
            TblFactura tblFacturaidNew = detalleFactura.getTblFacturaid();
            TblProducto tblProductoidOld = persistentDetalleFactura.getTblProductoid();
            TblProducto tblProductoidNew = detalleFactura.getTblProductoid();
            if (tblFacturaidNew != null) {
                tblFacturaidNew = em.getReference(tblFacturaidNew.getClass(), tblFacturaidNew.getId());
                detalleFactura.setTblFacturaid(tblFacturaidNew);
            }
            if (tblProductoidNew != null) {
                tblProductoidNew = em.getReference(tblProductoidNew.getClass(), tblProductoidNew.getId());
                detalleFactura.setTblProductoid(tblProductoidNew);
            }
            detalleFactura = em.merge(detalleFactura);
            if (tblFacturaidOld != null && !tblFacturaidOld.equals(tblFacturaidNew)) {
                tblFacturaidOld.getDetalleFacturaCollection().remove(detalleFactura);
                tblFacturaidOld = em.merge(tblFacturaidOld);
            }
            if (tblFacturaidNew != null && !tblFacturaidNew.equals(tblFacturaidOld)) {
                tblFacturaidNew.getDetalleFacturaCollection().add(detalleFactura);
                tblFacturaidNew = em.merge(tblFacturaidNew);
            }
            if (tblProductoidOld != null && !tblProductoidOld.equals(tblProductoidNew)) {
                tblProductoidOld.getDetalleFacturaCollection().remove(detalleFactura);
                tblProductoidOld = em.merge(tblProductoidOld);
            }
            if (tblProductoidNew != null && !tblProductoidNew.equals(tblProductoidOld)) {
                tblProductoidNew.getDetalleFacturaCollection().add(detalleFactura);
                tblProductoidNew = em.merge(tblProductoidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleFactura.getId();
                if (findDetalleFactura(id) == null) {
                    throw new NonexistentEntityException("The detalleFactura with id " + id + " no longer exists.");
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
            DetalleFactura detalleFactura;
            try {
                detalleFactura = em.getReference(DetalleFactura.class, id);
                detalleFactura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleFactura with id " + id + " no longer exists.", enfe);
            }
            TblFactura tblFacturaid = detalleFactura.getTblFacturaid();
            if (tblFacturaid != null) {
                tblFacturaid.getDetalleFacturaCollection().remove(detalleFactura);
                tblFacturaid = em.merge(tblFacturaid);
            }
            TblProducto tblProductoid = detalleFactura.getTblProductoid();
            if (tblProductoid != null) {
                tblProductoid.getDetalleFacturaCollection().remove(detalleFactura);
                tblProductoid = em.merge(tblProductoid);
            }
            em.remove(detalleFactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleFactura> findDetalleFacturaEntities() {
        return findDetalleFacturaEntities(true, -1, -1);
    }

    public List<DetalleFactura> findDetalleFacturaEntities(int maxResults, int firstResult) {
        return findDetalleFacturaEntities(false, maxResults, firstResult);
    }

    private List<DetalleFactura> findDetalleFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleFactura.class));
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

    public DetalleFactura findDetalleFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleFactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleFactura> rt = cq.from(DetalleFactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
