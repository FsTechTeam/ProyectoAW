/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lestermeneses
 */
@Entity
@Table(name = "tblCliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCliente.findAll", query = "SELECT t FROM TblCliente t"),
    @NamedQuery(name = "TblCliente.findById", query = "SELECT t FROM TblCliente t WHERE t.id = :id"),
    @NamedQuery(name = "TblCliente.findByNombre", query = "SELECT t FROM TblCliente t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblCliente.findByApellido", query = "SELECT t FROM TblCliente t WHERE t.apellido = :apellido"),
    @NamedQuery(name = "TblCliente.findByTelefono", query = "SELECT t FROM TblCliente t WHERE t.telefono = :telefono"),
    @NamedQuery(name = "TblCliente.findByLugar", query = "SELECT t FROM TblCliente t WHERE t.lugar = :lugar"),
    @NamedQuery(name = "TblCliente.findByDireccion", query = "SELECT t FROM TblCliente t WHERE t.direccion = :direccion"),
    @NamedQuery(name = "TblCliente.findByCodigoAsociado", query = "SELECT t FROM TblCliente t WHERE t.codigoAsociado = :codigoAsociado"),
    @NamedQuery(name = "TblCliente.findByPuntosIndividuales", query = "SELECT t FROM TblCliente t WHERE t.puntosIndividuales = :puntosIndividuales"),
    @NamedQuery(name = "TblCliente.findByActivo", query = "SELECT t FROM TblCliente t WHERE t.activo = :activo"),
    @NamedQuery(name = "TblCliente.findByPin", query = "SELECT t FROM TblCliente t WHERE t.pin = :pin")})
public class TblCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "lugar")
    private String lugar;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "codigoAsociado")
    private String codigoAsociado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "puntosIndividuales")
    private Float puntosIndividuales;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "pin")
    private String pin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblClienteid")
    private Collection<TblCliente> tblClienteCollection;
    @JoinColumn(name = "tblCliente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblCliente tblClienteid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblClienteid")
    private Collection<TblFactura> tblFacturaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblClienteid")
    private Collection<TblHistorialPuntos> tblHistorialPuntosCollection;

    public TblCliente() {
    }

    public TblCliente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoAsociado() {
        return codigoAsociado;
    }

    public void setCodigoAsociado(String codigoAsociado) {
        this.codigoAsociado = codigoAsociado;
    }

    public Float getPuntosIndividuales() {
        return puntosIndividuales;
    }

    public void setPuntosIndividuales(Float puntosIndividuales) {
        this.puntosIndividuales = puntosIndividuales;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @XmlTransient
    public Collection<TblCliente> getTblClienteCollection() {
        return tblClienteCollection;
    }

    public void setTblClienteCollection(Collection<TblCliente> tblClienteCollection) {
        this.tblClienteCollection = tblClienteCollection;
    }

    public TblCliente getTblClienteid() {
        return tblClienteid;
    }

    public void setTblClienteid(TblCliente tblClienteid) {
        this.tblClienteid = tblClienteid;
    }

    @XmlTransient
    public Collection<TblFactura> getTblFacturaCollection() {
        return tblFacturaCollection;
    }

    public void setTblFacturaCollection(Collection<TblFactura> tblFacturaCollection) {
        this.tblFacturaCollection = tblFacturaCollection;
    }

    @XmlTransient
    public Collection<TblHistorialPuntos> getTblHistorialPuntosCollection() {
        return tblHistorialPuntosCollection;
    }

    public void setTblHistorialPuntosCollection(Collection<TblHistorialPuntos> tblHistorialPuntosCollection) {
        this.tblHistorialPuntosCollection = tblHistorialPuntosCollection;
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
        if (!(object instanceof TblCliente)) {
            return false;
        }
        TblCliente other = (TblCliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TblCliente[ id=" + id + " ]";
    }
    
}
