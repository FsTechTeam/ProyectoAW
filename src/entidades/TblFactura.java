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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tblFactura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblFactura.findAll", query = "SELECT t FROM TblFactura t"),
    @NamedQuery(name = "TblFactura.findById", query = "SELECT t FROM TblFactura t WHERE t.id = :id"),
    @NamedQuery(name = "TblFactura.findByFecha", query = "SELECT t FROM TblFactura t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TblFactura.findByHora", query = "SELECT t FROM TblFactura t WHERE t.hora = :hora"),
    @NamedQuery(name = "TblFactura.findByPuntosTotales", query = "SELECT t FROM TblFactura t WHERE t.puntosTotales = :puntosTotales"),
    @NamedQuery(name = "TblFactura.findByTotalFactura", query = "SELECT t FROM TblFactura t WHERE t.totalFactura = :totalFactura"),
    @NamedQuery(name = "TblFactura.findByActivo", query = "SELECT t FROM TblFactura t WHERE t.activo = :activo")})
public class TblFactura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hora;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "puntosTotales")
    private Float puntosTotales;
    @Column(name = "totalFactura")
    private Float totalFactura;
    @Column(name = "activo")
    private Integer activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblFacturaid")
    private Collection<DetalleFactura> detalleFacturaCollection;
    @JoinColumn(name = "tblCliente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblCliente tblClienteid;
    @JoinColumn(name = "tblUsuarios_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblUsuarios tblUsuariosid;

    public TblFactura() {
    }

    public TblFactura(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Float getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(Float puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    public Float getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Float totalFactura) {
        this.totalFactura = totalFactura;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    @XmlTransient
    public Collection<DetalleFactura> getDetalleFacturaCollection() {
        return detalleFacturaCollection;
    }

    public void setDetalleFacturaCollection(Collection<DetalleFactura> detalleFacturaCollection) {
        this.detalleFacturaCollection = detalleFacturaCollection;
    }

    public TblCliente getTblClienteid() {
        return tblClienteid;
    }

    public void setTblClienteid(TblCliente tblClienteid) {
        this.tblClienteid = tblClienteid;
    }

    public TblUsuarios getTblUsuariosid() {
        return tblUsuariosid;
    }

    public void setTblUsuariosid(TblUsuarios tblUsuariosid) {
        this.tblUsuariosid = tblUsuariosid;
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
        if (!(object instanceof TblFactura)) {
            return false;
        }
        TblFactura other = (TblFactura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TblFactura[ id=" + id + " ]";
    }
    
}
