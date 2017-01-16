/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lestermeneses
 */
@Entity
@Table(name = "detalleDeLote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleDeLote.findAll", query = "SELECT d FROM DetalleDeLote d"),
    @NamedQuery(name = "DetalleDeLote.findById", query = "SELECT d FROM DetalleDeLote d WHERE d.id = :id")})
public class DetalleDeLote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "tblLoteProductos_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblLoteProductos tblLoteProductosid;
    @JoinColumn(name = "tblProducto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblProducto tblProductoid;

    public DetalleDeLote() {
    }

    public DetalleDeLote(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TblLoteProductos getTblLoteProductosid() {
        return tblLoteProductosid;
    }

    public void setTblLoteProductosid(TblLoteProductos tblLoteProductosid) {
        this.tblLoteProductosid = tblLoteProductosid;
    }

    public TblProducto getTblProductoid() {
        return tblProductoid;
    }

    public void setTblProductoid(TblProducto tblProductoid) {
        this.tblProductoid = tblProductoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleDeLote)) {
            return false;
        }
        DetalleDeLote other = (DetalleDeLote) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DetalleDeLote[ id=" + id + " ]";
    }
    
}
