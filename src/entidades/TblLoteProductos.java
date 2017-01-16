/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lestermeneses
 */
@Entity
@Table(name = "tblLoteProductos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblLoteProductos.findAll", query = "SELECT t FROM TblLoteProductos t"),
    @NamedQuery(name = "TblLoteProductos.findById", query = "SELECT t FROM TblLoteProductos t WHERE t.id = :id"),
    @NamedQuery(name = "TblLoteProductos.findByNumeroFactura", query = "SELECT t FROM TblLoteProductos t WHERE t.numeroFactura = :numeroFactura"),
    @NamedQuery(name = "TblLoteProductos.findByFecha", query = "SELECT t FROM TblLoteProductos t WHERE t.fecha = :fecha")})
public class TblLoteProductos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "numeroFactura")
    private String numeroFactura;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblLoteProductosid")
    private Collection<DetalleDeLote> detalleDeLoteCollection;

    public TblLoteProductos() {
    }

    public TblLoteProductos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public Collection<DetalleDeLote> getDetalleDeLoteCollection() {
        return detalleDeLoteCollection;
    }

    public void setDetalleDeLoteCollection(Collection<DetalleDeLote> detalleDeLoteCollection) {
        this.detalleDeLoteCollection = detalleDeLoteCollection;
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
        if (!(object instanceof TblLoteProductos)) {
            return false;
        }
        TblLoteProductos other = (TblLoteProductos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TblLoteProductos[ id=" + id + " ]";
    }
    
}
