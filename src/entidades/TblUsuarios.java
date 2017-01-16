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
@Table(name = "tblUsuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblUsuarios.findAll", query = "SELECT t FROM TblUsuarios t"),
    @NamedQuery(name = "TblUsuarios.findById", query = "SELECT t FROM TblUsuarios t WHERE t.id = :id"),
    @NamedQuery(name = "TblUsuarios.findByNombre", query = "SELECT t FROM TblUsuarios t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblUsuarios.findByApellido", query = "SELECT t FROM TblUsuarios t WHERE t.apellido = :apellido"),
    @NamedQuery(name = "TblUsuarios.findByDireccion", query = "SELECT t FROM TblUsuarios t WHERE t.direccion = :direccion"),
    @NamedQuery(name = "TblUsuarios.findByTelefono", query = "SELECT t FROM TblUsuarios t WHERE t.telefono = :telefono"),
    @NamedQuery(name = "TblUsuarios.findByActivo", query = "SELECT t FROM TblUsuarios t WHERE t.activo = :activo")})
public class TblUsuarios implements Serializable {
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
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "activo")
    private String activo;
    @JoinColumn(name = "tblTipoUsuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblTipoUsuario tblTipoUsuarioid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblUsuariosid")
    private Collection<TblFactura> tblFacturaCollection;

    public TblUsuarios() {
    }

    public TblUsuarios(Integer id) {
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public TblTipoUsuario getTblTipoUsuarioid() {
        return tblTipoUsuarioid;
    }

    public void setTblTipoUsuarioid(TblTipoUsuario tblTipoUsuarioid) {
        this.tblTipoUsuarioid = tblTipoUsuarioid;
    }

    @XmlTransient
    public Collection<TblFactura> getTblFacturaCollection() {
        return tblFacturaCollection;
    }

    public void setTblFacturaCollection(Collection<TblFactura> tblFacturaCollection) {
        this.tblFacturaCollection = tblFacturaCollection;
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
        if (!(object instanceof TblUsuarios)) {
            return false;
        }
        TblUsuarios other = (TblUsuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TblUsuarios[ id=" + id + " ]";
    }
    
}
