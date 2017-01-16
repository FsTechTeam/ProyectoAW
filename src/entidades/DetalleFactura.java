/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "detalleFactura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleFactura.findAll", query = "SELECT d FROM DetalleFactura d"),
    @NamedQuery(name = "DetalleFactura.findByDescuentoProducto", query = "SELECT d FROM DetalleFactura d WHERE d.descuentoProducto = :descuentoProducto"),
    @NamedQuery(name = "DetalleFactura.findBySubTotal", query = "SELECT d FROM DetalleFactura d WHERE d.subTotal = :subTotal"),
    @NamedQuery(name = "DetalleFactura.findByCantidad", query = "SELECT d FROM DetalleFactura d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleFactura.findById", query = "SELECT d FROM DetalleFactura d WHERE d.id = :id")})
public class DetalleFactura implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "descuentoProducto")
    private BigDecimal descuentoProducto;
    @Column(name = "subTotal")
    private Float subTotal;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "tblFactura_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblFactura tblFacturaid;
    @JoinColumn(name = "tblProducto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblProducto tblProductoid;

    public DetalleFactura() {
    }

    public DetalleFactura(Integer id) {
        this.id = id;
    }

    public BigDecimal getDescuentoProducto() {
        return descuentoProducto;
    }

    public void setDescuentoProducto(BigDecimal descuentoProducto) {
        this.descuentoProducto = descuentoProducto;
    }

    public Float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Float subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TblFactura getTblFacturaid() {
        return tblFacturaid;
    }

    public void setTblFacturaid(TblFactura tblFacturaid) {
        this.tblFacturaid = tblFacturaid;
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
        if (!(object instanceof DetalleFactura)) {
            return false;
        }
        DetalleFactura other = (DetalleFactura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DetalleFactura[ id=" + id + " ]";
    }
    
}
