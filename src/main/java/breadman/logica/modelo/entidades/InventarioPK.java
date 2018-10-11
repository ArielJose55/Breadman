/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Ariel Arnedo
 */
@Embeddable
public class InventarioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_inventario")
    private int codigoInventario;
    @Basic(optional = false)
    @Column(name = "id_producto")
    private int idProducto;

    public InventarioPK() {
    }

    public InventarioPK(int codigoInventario, int idProducto) {
        this.codigoInventario = codigoInventario;
        this.idProducto = idProducto;
    }

    public int getCodigoInventario() {
        return codigoInventario;
    }

    public void setCodigoInventario(int codigoInventario) {
        this.codigoInventario = codigoInventario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoInventario;
        hash += (int) idProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InventarioPK)) {
            return false;
        }
        InventarioPK other = (InventarioPK) object;
        if (this.codigoInventario != other.codigoInventario) {
            return false;
        }
        if (this.idProducto != other.idProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "breadman.logica.modelo.entidades.InventarioPK[ codigoInventario=" + codigoInventario + ", idProducto=" + idProducto + " ]";
    }
    
}
