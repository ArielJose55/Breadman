/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.entidades;

import java.io.Serializable;
import java.util.List;
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
 * @author Ariel Arnedo
 */
@Entity
@Table(name = "insumos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Insumo.findAll", query = "SELECT i FROM Insumo i")
    , @NamedQuery(name = "Insumo.findByCodigoInsumo", query = "SELECT i FROM Insumo i WHERE i.codigoInsumo = :codigoInsumo")
    , @NamedQuery(name = "Insumo.findByNombre", query = "SELECT i FROM Insumo i WHERE i.nombre = :nombre")
    , @NamedQuery(name = "Insumo.findByCantidadAlmacenada", query = "SELECT i FROM Insumo i WHERE i.cantidadAlmacenada = :cantidadAlmacenada")
    , @NamedQuery(name = "Insumo.findByUnidad", query = "SELECT i FROM Insumo i WHERE i.unidad = :unidad")})
public class Insumo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_insumo")
    private Integer codigoInsumo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "cantidad_almacenada")
    private float cantidadAlmacenada;
    @Basic(optional = false)
    @Column(name = "unidad")
    private String unidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInsumo")
    private List<Ingrediente> ingredienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInsumo")
    private List<SolicitudDeCompra> solicitudDeCompraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInsumo")
    private List<OrdenDeCompraInsumo> ordenDeCompraInsumoList;

    public Insumo() {
    }

    public Insumo(Integer codigoInsumo) {
        this.codigoInsumo = codigoInsumo;
    }

    public Insumo(Integer codigoInsumo, String nombre, float cantidadAlmacenada, String unidad) {
        this.codigoInsumo = codigoInsumo;
        this.nombre = nombre;
        this.cantidadAlmacenada = cantidadAlmacenada;
        this.unidad = unidad;
    }

    public Integer getCodigoInsumo() {
        return codigoInsumo;
    }

    public void setCodigoInsumo(Integer codigoInsumo) {
        this.codigoInsumo = codigoInsumo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getCantidadAlmacenada() {
        return cantidadAlmacenada;
    }

    public void setCantidadAlmacenada(float cantidadAlmacenada) {
        this.cantidadAlmacenada = cantidadAlmacenada;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    @XmlTransient
    public List<Ingrediente> getIngredienteList() {
        return ingredienteList;
    }

    public void setIngredienteList(List<Ingrediente> ingredienteList) {
        this.ingredienteList = ingredienteList;
    }

    @XmlTransient
    public List<SolicitudDeCompra> getSolicitudDeCompraList() {
        return solicitudDeCompraList;
    }

    public void setSolicitudDeCompraList(List<SolicitudDeCompra> solicitudDeCompraList) {
        this.solicitudDeCompraList = solicitudDeCompraList;
    }

    @XmlTransient
    public List<OrdenDeCompraInsumo> getOrdenDeCompraInsumoList() {
        return ordenDeCompraInsumoList;
    }

    public void setOrdenDeCompraInsumoList(List<OrdenDeCompraInsumo> ordenDeCompraInsumoList) {
        this.ordenDeCompraInsumoList = ordenDeCompraInsumoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoInsumo != null ? codigoInsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Insumo)) {
            return false;
        }
        Insumo other = (Insumo) object;
        if ((this.codigoInsumo == null && other.codigoInsumo != null) || (this.codigoInsumo != null && !this.codigoInsumo.equals(other.codigoInsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "breadman.logica.modelo.entidades.Insumo[ codigoInsumo=" + codigoInsumo + " ]";
    }
    
}
