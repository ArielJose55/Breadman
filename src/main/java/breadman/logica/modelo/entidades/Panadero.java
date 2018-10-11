/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author Ariel Arnedo
 */
@Entity
@Table(name = "panaderos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Panadero.findAll", query = "SELECT p FROM Panadero p")
    , @NamedQuery(name = "Panadero.findByCodigoPanadero", query = "SELECT p FROM Panadero p WHERE p.panaderoPK.codigoPanadero = :codigoPanadero")
    , @NamedQuery(name = "Panadero.findByCedula", query = "SELECT p FROM Panadero p WHERE p.panaderoPK.cedula = :cedula")})
public class Panadero implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PanaderoPK panaderoPK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "panadero")
    private List<OrdenProduccion> ordenProduccionList;
    @JoinColumn(name = "cedula", referencedColumnName = "cedula", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "panadero")
    private List<Lote> loteList;

    public Panadero() {
    }

    public Panadero(PanaderoPK panaderoPK) {
        this.panaderoPK = panaderoPK;
    }

    public Panadero(int codigoPanadero, int cedula) {
        this.panaderoPK = new PanaderoPK(codigoPanadero, cedula);
    }

    public PanaderoPK getPanaderoPK() {
        return panaderoPK;
    }

    public void setPanaderoPK(PanaderoPK panaderoPK) {
        this.panaderoPK = panaderoPK;
    }

    @XmlTransient
    public List<OrdenProduccion> getOrdenProduccionList() {
        return ordenProduccionList;
    }

    public void setOrdenProduccionList(List<OrdenProduccion> ordenProduccionList) {
        this.ordenProduccionList = ordenProduccionList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public List<Lote> getLoteList() {
        return loteList;
    }

    public void setLoteList(List<Lote> loteList) {
        this.loteList = loteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (panaderoPK != null ? panaderoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Panadero)) {
            return false;
        }
        Panadero other = (Panadero) object;
        if ((this.panaderoPK == null && other.panaderoPK != null) || (this.panaderoPK != null && !this.panaderoPK.equals(other.panaderoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return usuario.getNombre();
    }
    
}
