/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.entidades;

import breadman.logica.modelo.core.State;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ariel Arnedo
 */
@Entity
@Table(name = "ordenes_producciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenProduccion.findAll", query = "SELECT o FROM OrdenProduccion o")
    , @NamedQuery(name = "OrdenProduccion.findByCodigoOrdenProduccion", query = "SELECT o FROM OrdenProduccion o WHERE o.codigoOrdenProduccion = :codigoOrdenProduccion")
    , @NamedQuery(name = "OrdenProduccion.findByCantidad", query = "SELECT o FROM OrdenProduccion o WHERE o.cantidad = :cantidad")
    , @NamedQuery(name = "OrdenProduccion.findByFechaPrevista", query = "SELECT o FROM OrdenProduccion o WHERE o.fechaPrevista = :fechaPrevista")
    , @NamedQuery(name = "OrdenProduccion.findByEstado", query = "SELECT o FROM OrdenProduccion o WHERE o.estado = :estado")})
public class OrdenProduccion implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCodigoOrdenProduccion")
    private List<Lote> loteList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_orden_produccion")
    private Integer codigoOrdenProduccion;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "fecha_prevista")
    @Temporal(TemporalType.DATE)
    private Date fechaPrevista;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @JoinColumns({
        @JoinColumn(name = "id_panadero", referencedColumnName = "codigo_panadero")
        , @JoinColumn(name = "cedula", referencedColumnName = "cedula")})
    @ManyToOne(optional = false)
    private Panadero panadero;
    @JoinColumn(name = "id_producto", referencedColumnName = "codigo_producto")
    @ManyToOne(optional = false)
    private Producto idProducto;

    public OrdenProduccion() {
    }

    public OrdenProduccion(Integer codigoOrdenProduccion) {
        this.codigoOrdenProduccion = codigoOrdenProduccion;
    }

    public OrdenProduccion(Integer codigoOrdenProduccion, int cantidad, Date fechaPrevista, int estado) {
        this.codigoOrdenProduccion = codigoOrdenProduccion;
        this.cantidad = cantidad;
        this.fechaPrevista = fechaPrevista;
        this.estado = estado;
    }

    public Integer getCodigoOrdenProduccion() {
        return codigoOrdenProduccion;
    }

    public void setCodigoOrdenProduccion(Integer codigoOrdenProduccion) {
        this.codigoOrdenProduccion = codigoOrdenProduccion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaPrevista() {
        return fechaPrevista;
    }

    public void setFechaPrevista(Date fechaPrevista) {
        this.fechaPrevista = fechaPrevista;
    }

    public State getEstado() {
        return getState(estado);
    }

    public void setState(State state){
        setEstado(state);
    }
    
    private State getState(int estado){
        switch(estado){
            case 1: return State.PENDIENTE;
            case 2: return State.VENCIDA;
            case 4: return State.PRODUCIDA;
            default: return State.ACTUAL;
        }
    }
    
    private void setEstado(State estado) {
        switch(estado){
            case PENDIENTE:{
                this.estado = 1;
                break;
            }
            case VENCIDA:{
                this.estado = 2;
                break;
            }
            case PRODUCIDA:{
                this.estado = 4;
                break;
            }
            default: this.estado = 3;
        }
    }

    public Panadero getPanadero() {
        return panadero;
    }

    public void setPanadero(Panadero panadero) {
        this.panadero = panadero;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoOrdenProduccion != null ? codigoOrdenProduccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenProduccion)) {
            return false;
        }
        OrdenProduccion other = (OrdenProduccion) object;
        if ((this.codigoOrdenProduccion == null && other.codigoOrdenProduccion != null) || (this.codigoOrdenProduccion != null && !this.codigoOrdenProduccion.equals(other.codigoOrdenProduccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cantidad: "+cantidad+" "+idProducto.getNombre();
    }

    @XmlTransient
    public List<Lote> getLoteList() {
        return loteList;
    }

    public void setLoteList(List<Lote> loteList) {
        this.loteList = loteList;
    }
    
}
