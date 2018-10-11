/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ariel Arnedo
 */
@Entity
@Table(name = "orden_de_compra_insumos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenDeCompraInsumo.findAll", query = "SELECT o FROM OrdenDeCompraInsumo o")
    , @NamedQuery(name = "OrdenDeCompraInsumo.findByCodigoCompraInsumo", query = "SELECT o FROM OrdenDeCompraInsumo o WHERE o.codigoCompraInsumo = :codigoCompraInsumo")
    , @NamedQuery(name = "OrdenDeCompraInsumo.findByCantidadDeCompra", query = "SELECT o FROM OrdenDeCompraInsumo o WHERE o.cantidadDeCompra = :cantidadDeCompra")
    , @NamedQuery(name = "OrdenDeCompraInsumo.findByFechaDeCompra", query = "SELECT o FROM OrdenDeCompraInsumo o WHERE o.fechaDeCompra = :fechaDeCompra")})
public class OrdenDeCompraInsumo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_compra_insumo")
    private Integer codigoCompraInsumo;
    @Basic(optional = false)
    @Column(name = "cantidad_de_compra")
    private float cantidadDeCompra;
    @Basic(optional = false)
    @Column(name = "fecha_de_compra")
    @Temporal(TemporalType.DATE)
    private Date fechaDeCompra;
    @JoinColumn(name = "id_insumo", referencedColumnName = "codigo_insumo")
    @ManyToOne(optional = false)
    private Insumo idInsumo;
    @JoinColumn(name = "nit_proveedor", referencedColumnName = "codigo_proveedor")
    @ManyToOne(optional = false)
    private Proveedor nitProveedor;

    public OrdenDeCompraInsumo() {
    }

    public OrdenDeCompraInsumo(Integer codigoCompraInsumo) {
        this.codigoCompraInsumo = codigoCompraInsumo;
    }

    public OrdenDeCompraInsumo(Integer codigoCompraInsumo, float cantidadDeCompra, Date fechaDeCompra) {
        this.codigoCompraInsumo = codigoCompraInsumo;
        this.cantidadDeCompra = cantidadDeCompra;
        this.fechaDeCompra = fechaDeCompra;
    }

    public Integer getCodigoCompraInsumo() {
        return codigoCompraInsumo;
    }

    public void setCodigoCompraInsumo(Integer codigoCompraInsumo) {
        this.codigoCompraInsumo = codigoCompraInsumo;
    }

    public float getCantidadDeCompra() {
        return cantidadDeCompra;
    }

    public void setCantidadDeCompra(float cantidadDeCompra) {
        this.cantidadDeCompra = cantidadDeCompra;
    }

    public Date getFechaDeCompra() {
        return fechaDeCompra;
    }

    public void setFechaDeCompra(Date fechaDeCompra) {
        this.fechaDeCompra = fechaDeCompra;
    }

    public Insumo getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Insumo idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Proveedor getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(Proveedor nitProveedor) {
        this.nitProveedor = nitProveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCompraInsumo != null ? codigoCompraInsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenDeCompraInsumo)) {
            return false;
        }
        OrdenDeCompraInsumo other = (OrdenDeCompraInsumo) object;
        if ((this.codigoCompraInsumo == null && other.codigoCompraInsumo != null) || (this.codigoCompraInsumo != null && !this.codigoCompraInsumo.equals(other.codigoCompraInsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "breadman.logica.modelo.entidades.OrdenDeCompraInsumo[ codigoCompraInsumo=" + codigoCompraInsumo + " ]";
    }
    
}
