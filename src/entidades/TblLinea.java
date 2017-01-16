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
@Table(name = "tblLinea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblLinea.findAll", query = "SELECT t FROM TblLinea t"),
    @NamedQuery(name = "TblLinea.findById", query = "SELECT t FROM TblLinea t WHERE t.id = :id"),
    @NamedQuery(name = "TblLinea.findByActivo", query = "SELECT t FROM TblLinea t WHERE t.activo = :activo")})
public class TblLinea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "tblCategoria_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblCategoria tblCategoriaid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblLineaid")
    private Collection<TblProducto> tblProductoCollection;

    public TblLinea() {
    }

    public TblLinea(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public TblCategoria getTblCategoriaid() {
        return tblCategoriaid;
    }

    public void setTblCategoriaid(TblCategoria tblCategoriaid) {
        this.tblCategoriaid = tblCategoriaid;
    }

    @XmlTransient
    public Collection<TblProducto> getTblProductoCollection() {
        return tblProductoCollection;
    }

    public void setTblProductoCollection(Collection<TblProducto> tblProductoCollection) {
        this.tblProductoCollection = tblProductoCollection;
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
        if (!(object instanceof TblLinea)) {
            return false;
        }
        TblLinea other = (TblLinea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TblLinea[ id=" + id + " ]";
    }
    
}
