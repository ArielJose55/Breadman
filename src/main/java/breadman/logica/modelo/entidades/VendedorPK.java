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
public class VendedorPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_vendedor")
    private int codigoVendedor;
    @Basic(optional = false)
    @Column(name = "cedula")
    private int cedula;

    public VendedorPK() {
    }

    public VendedorPK(int codigoVendedor, int cedula) {
        this.codigoVendedor = codigoVendedor;
        this.cedula = cedula;
    }

    public int getCodigoVendedor() {
        return codigoVendedor;
    }

    public void setCodigoVendedor(int codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoVendedor;
        hash += (int) cedula;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VendedorPK)) {
            return false;
        }
        VendedorPK other = (VendedorPK) object;
        if (this.codigoVendedor != other.codigoVendedor) {
            return false;
        }
        if (this.cedula != other.cedula) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "breadman.logica.modelo.entidades.VendedorPK[ codigoVendedor=" + codigoVendedor + ", cedula=" + cedula + " ]";
    }
    
}
