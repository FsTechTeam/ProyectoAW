/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lestermeneses
 */
@Entity
@Table(name = "tblProducto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblProducto.findAll", query = "SELECT t FROM TblProducto t"),
    @NamedQuery(name = "TblProducto.findById", query = "SELECT t FROM TblProducto t WHERE t.id = :id"),
    @NamedQuery(name = "TblProducto.findByPagProducto", query = "SELECT t FROM TblProducto t WHERE t.pagProducto = :pagProducto"),
    @NamedQuery(name = "TblProducto.findByPresentacion", query = "SELECT t FROM TblProducto t WHERE t.presentacion = :presentacion"),
    @NamedQuery(name = "TblProducto.findByDescripcion", query = "SELECT t FROM TblProducto t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TblProducto.findByPuntos", query = "SELECT t FROM TblProducto t WHERE t.puntos = :puntos"),
    @NamedQuery(name = "TblProducto.findByPrecioPublico", query = "SELECT t FROM TblProducto t WHERE t.precioPublico = :precioPublico"),
    @NamedQuery(name = "TblProducto.findByExistencias", query = "SELECT t FROM TblProducto t WHERE t.existencias = :existencias"),
    @NamedQuery(name = "TblProducto.findByActivo", query = "SELECT t FROM TblProducto t WHERE t.activo = :activo")})
public class TblProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "pagProducto")
    private Integer pagProducto;
    @Column(name = "presentacion")
    private String presentacion;
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "puntos")
    private BigDecimal puntos;
    @Column(name = "precioPublico")
    private BigDecimal precioPublico;
    @Column(name = "existencias")
    private Integer existencias;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblProductoid")
    private Collection<DetalleFactura> detalleFacturaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblProductoid")
    private Collection<DetalleDeLote> detalleDeLoteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblProductoid")
    private Collection<TblBitacora> tblBitacoraCollection;
    @JoinColumn(name = "tblLinea_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblLinea tblLineaid;

    public TblProducto() {
    }

    public TblProducto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPagProducto() {
        return pagProducto;
    }

    public void setPagProducto(Integer pagProducto) {
        this.pagProducto = pagProducto;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPuntos() {
        return puntos;
    }

    public void setPuntos(BigDecimal puntos) {
        this.puntos = puntos;
    }

    public BigDecimal getPrecioPublico() {
        return precioPublico;
    }

    public void setPrecioPublico(BigDecimal precioPublico) {
        this.precioPublico = precioPublico;
    }

    public Integer getExistencias() {
        return existencias;
    }

    public void setExistencias(Integer existencias) {
        this.existencias = existencias;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public Collection<DetalleFactura> getDetalleFacturaCollection() {
        return detalleFacturaCollection;
    }

    public void setDetalleFacturaCollection(Collection<DetalleFactura> detalleFacturaCollection) {
        this.detalleFacturaCollection = detalleFacturaCollection;
    }

    @XmlTransient
    public Collection<DetalleDeLote> getDetalleDeLoteCollection() {
        return detalleDeLoteCollection;
    }

    public void setDetalleDeLoteCollection(Collection<DetalleDeLote> detalleDeLoteCollection) {
        this.detalleDeLoteCollection = detalleDeLoteCollection;
    }

    @XmlTransient
    public Collection<TblBitacora> getTblBitacoraCollection() {
        return tblBitacoraCollection;
    }

    public void setTblBitacoraCollection(Collection<TblBitacora> tblBitacoraCollection) {
        this.tblBitacoraCollection = tblBitacoraCollection;
    }

    public TblLinea getTblLineaid() {
        return tblLineaid;
    }

    public void setTblLineaid(TblLinea tblLineaid) {
        this.tblLineaid = tblLineaid;
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
        if (!(object instanceof TblProducto)) {
            return false;
        }
        TblProducto other = (TblProducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TblProducto[ id=" + id + " ]";
    }
    
}
