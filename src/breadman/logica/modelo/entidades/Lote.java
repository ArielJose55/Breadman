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
@Table(name = "lotes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lote.findAll", query = "SELECT l FROM Lote l")
    , @NamedQuery(name = "Lote.findByCodigoLote", query = "SELECT l FROM Lote l WHERE l.codigoLote = :codigoLote")
    , @NamedQuery(name = "Lote.findByCantidadProducida", query = "SELECT l FROM Lote l WHERE l.cantidadProducida = :cantidadProducida")
    , @NamedQuery(name = "Lote.findByFechaDeProduccion", query = "SELECT l FROM Lote l WHERE l.fechaDeProduccion = :fechaDeProduccion")
    , @NamedQuery(name = "Lote.findByFechaDeVencimiento", query = "SELECT l FROM Lote l WHERE l.fechaDeVencimiento = :fechaDeVencimiento")})
public class Lote implements Serializable {

    @JoinColumn(name = "id_codigo_orden_produccion", referencedColumnName = "codigo_orden_produccion")
    @ManyToOne(optional = false)
    private OrdenProduccion idCodigoOrdenProduccion;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_lote")
    private Integer codigoLote;
    @Basic(optional = false)
    @Column(name = "cantidad_producida")
    private int cantidadProducida;
    @Basic(optional = false)
    @Column(name = "fecha_de_produccion")
    @Temporal(TemporalType.DATE)
    private Date fechaDeProduccion;
    @Column(name = "fecha_de_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaDeVencimiento;
    @JoinColumns({
        @JoinColumn(name = "id_inventario", referencedColumnName = "codigo_inventario")
        , @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")})
    @ManyToOne(optional = false)
    private Inventario inventario;
    @JoinColumns({
        @JoinColumn(name = "id_panadero", referencedColumnName = "codigo_panadero")
        , @JoinColumn(name = "cedula", referencedColumnName = "cedula")})
    @ManyToOne(optional = false)
    private Panadero panadero;

    public Lote() {
    }

    public Lote(Integer codigoLote) {
        this.codigoLote = codigoLote;
    }

    public Lote(Integer codigoLote, int cantidadProducida, Date fechaDeProduccion) {
        this.codigoLote = codigoLote;
        this.cantidadProducida = cantidadProducida;
        this.fechaDeProduccion = fechaDeProduccion;
    }

    public Integer getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(Integer codigoLote) {
        this.codigoLote = codigoLote;
    }

    public int getCantidadProducida() {
        return cantidadProducida;
    }

    public void setCantidadProducida(int cantidadProducida) {
        this.cantidadProducida = cantidadProducida;
    }

    public Date getFechaDeProduccion() {
        return fechaDeProduccion;
    }

    public void setFechaDeProduccion(Date fechaDeProduccion) {
        this.fechaDeProduccion = fechaDeProduccion;
    }

    public Date getFechaDeVencimiento() {
        return fechaDeVencimiento;
    }

    public void setFechaDeVencimiento(Date fechaDeVencimiento) {
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Panadero getPanadero() {
        return panadero;
    }

    public void setPanadero(Panadero panadero) {
        this.panadero = panadero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoLote != null ? codigoLote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lote)) {
            return false;
        }
        Lote other = (Lote) object;
        if ((this.codigoLote == null && other.codigoLote != null) || (this.codigoLote != null && !this.codigoLote.equals(other.codigoLote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "breadman.logica.modelo.entidades.Lote[ codigoLote=" + codigoLote + " ]";
    }

    public OrdenProduccion getIdCodigoOrdenProduccion() {
        return idCodigoOrdenProduccion;
    }

    public void setIdCodigoOrdenProduccion(OrdenProduccion idCodigoOrdenProduccion) {
        this.idCodigoOrdenProduccion = idCodigoOrdenProduccion;
    }
    
}
