/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
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
@Table(name = "tblHistorialPuntos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblHistorialPuntos.findAll", query = "SELECT t FROM TblHistorialPuntos t"),
    @NamedQuery(name = "TblHistorialPuntos.findById", query = "SELECT t FROM TblHistorialPuntos t WHERE t.id = :id"),
    @NamedQuery(name = "TblHistorialPuntos.findByFecha", query = "SELECT t FROM TblHistorialPuntos t WHERE t.fecha = :fecha")})
public class TblHistorialPuntos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    private String fecha;
    @JoinColumn(name = "tblCliente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblCliente tblClienteid;

    public TblHistorialPuntos() {
    }

    public TblHistorialPuntos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public TblCliente getTblClienteid() {
        return tblClienteid;
    }

    public void setTblClienteid(TblCliente tblClienteid) {
        this.tblClienteid = tblClienteid;
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
        if (!(object instanceof TblHistorialPuntos)) {
            return false;
        }
        TblHistorialPuntos other = (TblHistorialPuntos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TblHistorialPuntos[ id=" + id + " ]";
    }
    
}
