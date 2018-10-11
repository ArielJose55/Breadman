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
import javax.persistence.JoinColumns;
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
@Table(name = "ventas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v")
    , @NamedQuery(name = "Venta.findByCodigoVenta", query = "SELECT v FROM Venta v WHERE v.codigoVenta = :codigoVenta")
    , @NamedQuery(name = "Venta.findByCantidadComprada", query = "SELECT v FROM Venta v WHERE v.cantidadComprada = :cantidadComprada")
    , @NamedQuery(name = "Venta.findByValorCompra", query = "SELECT v FROM Venta v WHERE v.valorCompra = :valorCompra")
    , @NamedQuery(name = "Venta.findByFechaDeCompra", query = "SELECT v FROM Venta v WHERE v.fechaDeCompra = :fechaDeCompra")})
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_venta")
    private Integer codigoVenta;
    @Basic(optional = false)
    @Column(name = "cantidad_comprada")
    private float cantidadComprada;
    @Basic(optional = false)
    @Column(name = "valor_compra")
    private float valorCompra;
    @Basic(optional = false)
    @Column(name = "fecha_de_compra")
    @Temporal(TemporalType.DATE)
    private Date fechaDeCompra;
    @JoinColumns({
        @JoinColumn(name = "id_inventario", referencedColumnName = "codigo_inventario")
        , @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")})
    @ManyToOne(optional = false)
    private Inventario inventario;
    @JoinColumns({
        @JoinColumn(name = "id_vendedor", referencedColumnName = "codigo_vendedor")
        , @JoinColumn(name = "cedula", referencedColumnName = "cedula")})
    @ManyToOne(optional = false)
    private Vendedor vendedor;

    public Venta() {
        this.cantidadComprada = 0;
    }

    public Venta(Integer codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public Venta(Integer codigoVenta, float cantidadComprada, float valorCompra, Date fechaDeCompra) {
        this.codigoVenta = codigoVenta;
        this.cantidadComprada = cantidadComprada;
        this.valorCompra = valorCompra;
        this.fechaDeCompra = fechaDeCompra;
    }

    public Integer getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(Integer codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public float getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(float cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }

    public float getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(float valorCompra) {
        this.valorCompra = valorCompra;
    }

    public Date getFechaDeCompra() {
        return fechaDeCompra;
    }

    public void setFechaDeCompra(Date fechaDeCompra) {
        this.fechaDeCompra = fechaDeCompra;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoVenta != null ? codigoVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.codigoVenta == null && other.codigoVenta != null) || (this.codigoVenta != null && !this.codigoVenta.equals(other.codigoVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.valorCompra+"  "+this.fechaDeCompra;
    }
    
}
