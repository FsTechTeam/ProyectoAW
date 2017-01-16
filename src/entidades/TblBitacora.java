/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lestermeneses
 */
@Entity
@Table(name = "tblBitacora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblBitacora.findAll", query = "SELECT t FROM TblBitacora t"),
    @NamedQuery(name = "TblBitacora.findByFecha", query = "SELECT t FROM TblBitacora t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TblBitacora.findById", query = "SELECT t FROM TblBitacora t WHERE t.id = :id")})
public class TblBitacora implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "tblProducto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblProducto tblProductoid;
    @JoinColumn(name = "tblTipoUsuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblTipoUsuario tblTipoUsuarioid;

    public TblBitacora() {
    }

    public TblBitacora(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TblProducto getTblProductoid() {
        return tblProductoid;
    }

    public void setTblProductoid(TblProducto tblProductoid) {
        this.tblProductoid = tblProductoid;
    }

    public TblTipoUsuario getTblTipoUsuarioid() {
        return tblTipoUsuarioid;
    }

    public void setTblTipoUsuarioid(TblTipoUsuario tblTipoUsuarioid) {
        this.tblTipoUsuarioid = tblTipoUsuarioid;
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
        if (!(object instanceof TblBitacora)) {
            return false;
        }
        TblBitacora other = (TblBitacora) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TblBitacora[ id=" + id + " ]";
    }
    
}
