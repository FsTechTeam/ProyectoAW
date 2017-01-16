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
@Table(name = "tblTipoUsuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblTipoUsuario.findAll", query = "SELECT t FROM TblTipoUsuario t"),
    @NamedQuery(name = "TblTipoUsuario.findById", query = "SELECT t FROM TblTipoUsuario t WHERE t.id = :id"),
    @NamedQuery(name = "TblTipoUsuario.findByNombreUsuario", query = "SELECT t FROM TblTipoUsuario t WHERE t.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "TblTipoUsuario.findByPassword", query = "SELECT t FROM TblTipoUsuario t WHERE t.password = :password"),
    @NamedQuery(name = "TblTipoUsuario.findByTipo", query = "SELECT t FROM TblTipoUsuario t WHERE t.tipo = :tipo")})
public class TblTipoUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombreUsuario")
    private String nombreUsuario;
    @Column(name = "password")
    private String password;
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblTipoUsuarioid")
    private Collection<TblUsuarios> tblUsuariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblTipoUsuarioid")
    private Collection<TblBitacora> tblBitacoraCollection;

    public TblTipoUsuario() {
    }

    public TblTipoUsuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<TblUsuarios> getTblUsuariosCollection() {
        return tblUsuariosCollection;
    }

    public void setTblUsuariosCollection(Collection<TblUsuarios> tblUsuariosCollection) {
        this.tblUsuariosCollection = tblUsuariosCollection;
    }

    @XmlTransient
    public Collection<TblBitacora> getTblBitacoraCollection() {
        return tblBitacoraCollection;
    }

    public void setTblBitacoraCollection(Collection<TblBitacora> tblBitacoraCollection) {
        this.tblBitacoraCollection = tblBitacoraCollection;
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
        if (!(object instanceof TblTipoUsuario)) {
            return false;
        }
        TblTipoUsuario other = (TblTipoUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TblTipoUsuario[ id=" + id + " ]";
    }
    
}
