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
@Table(name = "tblCategoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCategoria.findAll", query = "SELECT t FROM TblCategoria t"),
    @NamedQuery(name = "TblCategoria.findById", query = "SELECT t FROM TblCategoria t WHERE t.id = :id"),
    @NamedQuery(name = "TblCategoria.findByCategoria", query = "SELECT t FROM TblCategoria t WHERE t.categoria = :categoria"),
    @NamedQuery(name = "TblCategoria.findByActivo", query = "SELECT t FROM TblCategoria t WHERE t.activo = :activo")})
public class TblCategoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblCategoriaid")
    private Collection<TblLinea> tblLineaCollection;

    public TblCategoria() {
    }

    public TblCategoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public Collection<TblLinea> getTblLineaCollection() {
        return tblLineaCollection;
    }

    public void setTblLineaCollection(Collection<TblLinea> tblLineaCollection) {
        this.tblLineaCollection = tblLineaCollection;
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
        if (!(object instanceof TblCategoria)) {
            return false;
        }
        TblCategoria other = (TblCategoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TblCategoria[ id=" + id + " ]";
    }
    
}
