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
public class PanaderoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_panadero")
    private int codigoPanadero;
    @Basic(optional = false)
    @Column(name = "cedula")
    private int cedula;

    public PanaderoPK() {
    }

    public PanaderoPK(int codigoPanadero, int cedula) {
        this.codigoPanadero = codigoPanadero;
        this.cedula = cedula;
    }

    public int getCodigoPanadero() {
        return codigoPanadero;
    }

    public void setCodigoPanadero(int codigoPanadero) {
        this.codigoPanadero = codigoPanadero;
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
        hash += (int) codigoPanadero;
        hash += (int) cedula;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PanaderoPK)) {
            return false;
        }
        PanaderoPK other = (PanaderoPK) object;
        if (this.codigoPanadero != other.codigoPanadero) {
            return false;
        }
        if (this.cedula != other.cedula) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "breadman.logica.modelo.entidades.PanaderoPK[ codigoPanadero=" + codigoPanadero + ", cedula=" + cedula + " ]";
    }
    
}
