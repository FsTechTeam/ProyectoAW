/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoaw;

import Conexion.conexion;
import app.pPrincipal;
import controladores.TblCategoriaJpaController;
import entidades.TblCategoria;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lestermeneses
 */
public class ProyectoAW {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoAWPU");
        EntityManager em = emf.createEntityManager();
        emf.close();

//        EntityManagerFactory emf = conexion.getInstancia().getEMF();
//        EntityManager em = emf.createEntityManager();
//        TblCategoriaJpaController service = new TblCategoriaJpaController(emf);
//        TblCategoria ncategoria = new TblCategoria();
//        em.getTransaction().begin();
//        ncategoria.setCategoria("Nuevo");
//        ncategoria.setActivo(true);
//        try{
//            service.create(ncategoria);
//            em.getTransaction().commit();
//            System.out.println("Exito");
//        }catch(Exception e){
//            System.out.println(e);
//            em.getTransaction().rollback();
//        }
//        em.close();
//        emf.close();
//        
        pPrincipal principal = new pPrincipal();
        principal.setVisible(true);

        
    }
    
}
