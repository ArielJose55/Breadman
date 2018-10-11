/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.entidades;

import breadman.logica.modelo.core.State;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "solicitudes_de_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudDeCompra.findAll", query = "SELECT s FROM SolicitudDeCompra s")
    , @NamedQuery(name = "SolicitudDeCompra.findByCodigoSolicitud", query = "SELECT s FROM SolicitudDeCompra s WHERE s.codigoSolicitud = :codigoSolicitud")
    , @NamedQuery(name = "SolicitudDeCompra.findByCantidadSolicitada", query = "SELECT s FROM SolicitudDeCompra s WHERE s.cantidadSolicitada = :cantidadSolicitada")
    , @NamedQuery(name = "SolicitudDeCompra.findByFechaMaximaDeCompra", query = "SELECT s FROM SolicitudDeCompra s WHERE s.fechaMaximaDeCompra = :fechaMaximaDeCompra")
    , @NamedQuery(name = "SolicitudDeCompra.findByEstado", query = "SELECT s FROM SolicitudDeCompra s WHERE s.estado = :estado")})
public class SolicitudDeCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo_solicitud")
    private Integer codigoSolicitud;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad_solicitada")
    private Float cantidadSolicitada;
    @Column(name = "fecha_maxima_de_compra")
    @Temporal(TemporalType.DATE)
    private Date fechaMaximaDeCompra;
    @Column(name = "estado")
    private Integer estado;
    @JoinColumn(name = "id_insumo", referencedColumnName = "codigo_insumo")
    @ManyToOne(optional = false)
    private Insumo idInsumo;

    public SolicitudDeCompra() {
    }

    public SolicitudDeCompra(Integer codigoSolicitud) {
        this.codigoSolicitud = codigoSolicitud;
    }

    public Integer getCodigoSolicitud() {
        return codigoSolicitud;
    }

    public void setCodigoSolicitud(Integer codigoSolicitud) {
        this.codigoSolicitud = codigoSolicitud;
    }

    public Float getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Float cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Date getFechaMaximaDeCompra() {
        return fechaMaximaDeCompra;
    }

    public void setFechaMaximaDeCompra(Date fechaMaximaDeCompra) {
        this.fechaMaximaDeCompra = fechaMaximaDeCompra;
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
            default: this.estado = 3;
        }
    }

    public Insumo getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Insumo idInsumo) {
        this.idInsumo = idInsumo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoSolicitud != null ? codigoSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudDeCompra)) {
            return false;
        }
        SolicitudDeCompra other = (SolicitudDeCompra) object;
        if ((this.codigoSolicitud == null && other.codigoSolicitud != null) || (this.codigoSolicitud != null && !this.codigoSolicitud.equals(other.codigoSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "breadman.logica.modelo.entidades.SolicitudDeCompra[ codigoSolicitud=" + codigoSolicitud + " ]";
    }
    
}
