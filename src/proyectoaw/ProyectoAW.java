/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoaw;

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
        
        
    }
    
}
