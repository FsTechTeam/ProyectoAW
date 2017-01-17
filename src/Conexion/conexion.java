/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Dhaby Xiloj <dhabyx@gmail.com>
 */
public class conexion {
    private static final conexion objetoUnico = new conexion();
    
    protected EntityManagerFactory emf;
     
    protected conexion(){ }
    
    public static conexion getInstancia(){
        return objetoUnico;
    }
    
    public EntityManagerFactory getEMF() {
        if (emf == null)
            setEmf();
        return emf;
    }
    
    public void setEmf() {
        if (emf == null)
           emf = Persistence.createEntityManagerFactory("ProyectoAWPU");
    }
    
    public void cerrarEMF() {
        if (this.emf != null)
            emf.close();
    }
}
