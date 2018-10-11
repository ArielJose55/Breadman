/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.entidades;

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
 * @author Ariel Arnedo
 */
@Entity
@Table(name = "ingredientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingrediente.findAll", query = "SELECT i FROM Ingrediente i")
    , @NamedQuery(name = "Ingrediente.findByCodigoIngrediente", query = "SELECT i FROM Ingrediente i WHERE i.codigoIngrediente = :codigoIngrediente")
    , @NamedQuery(name = "Ingrediente.findByCantidadRequerida", query = "SELECT i FROM Ingrediente i WHERE i.cantidadRequerida = :cantidadRequerida")})
public class Ingrediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_ingrediente")
    private Integer codigoIngrediente;
    @Basic(optional = false)
    @Column(name = "cantidad_requerida")
    private float cantidadRequerida;
    @JoinColumn(name = "id_insumo", referencedColumnName = "codigo_insumo")
    @ManyToOne(optional = false)
    private Insumo idInsumo;
    @JoinColumn(name = "id_producto", referencedColumnName = "codigo_producto")
    @ManyToOne(optional = false)
    private Producto idProducto;

    public Ingrediente() {
    }

    public Ingrediente(Integer codigoIngrediente) {
        this.codigoIngrediente = codigoIngrediente;
    }

    public Ingrediente(Integer codigoIngrediente, float cantidadRequerida) {
        this.codigoIngrediente = codigoIngrediente;
        this.cantidadRequerida = cantidadRequerida;
    }

    public Integer getCodigoIngrediente() {
        return codigoIngrediente;
    }

    public void setCodigoIngrediente(Integer codigoIngrediente) {
        this.codigoIngrediente = codigoIngrediente;
    }

    public float getCantidadRequerida() {
        return cantidadRequerida;
    }

    public void setCantidadRequerida(float cantidadRequerida) {
        this.cantidadRequerida = cantidadRequerida;
    }

    public Insumo getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Insumo idInsumo) {
        this.idInsumo = idInsumo;
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
        hash += (codigoIngrediente != null ? codigoIngrediente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingrediente)) {
            return false;
        }
        Ingrediente other = (Ingrediente) object;
        if ((this.codigoIngrediente == null && other.codigoIngrediente != null) || (this.codigoIngrediente != null && !this.codigoIngrediente.equals(other.codigoIngrediente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "breadman.logica.modelo.entidades.Ingrediente[ codigoIngrediente=" + codigoIngrediente + " ]";
    }
    
}
