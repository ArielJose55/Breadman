/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "vendedores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vendedor.findAll", query = "SELECT v FROM Vendedor v")
    , @NamedQuery(name = "Vendedor.findByCodigoVendedor", query = "SELECT v FROM Vendedor v WHERE v.vendedorPK.codigoVendedor = :codigoVendedor")
    , @NamedQuery(name = "Vendedor.findByCedula", query = "SELECT v FROM Vendedor v WHERE v.vendedorPK.cedula = :cedula")})
public class Vendedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VendedorPK vendedorPK;
    @JoinColumn(name = "cedula", referencedColumnName = "cedula", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vendedor")
    private List<Venta> ventaList;

    public Vendedor() {
    }

    public Vendedor(VendedorPK vendedorPK) {
        this.vendedorPK = vendedorPK;
    }

    public Vendedor(int codigoVendedor, int cedula) {
        this.vendedorPK = new VendedorPK(codigoVendedor, cedula);
    }

    public VendedorPK getVendedorPK() {
        return vendedorPK;
    }

    public void setVendedorPK(VendedorPK vendedorPK) {
        this.vendedorPK = vendedorPK;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vendedorPK != null ? vendedorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendedor)) {
            return false;
        }
        Vendedor other = (Vendedor) object;
        if ((this.vendedorPK == null && other.vendedorPK != null) || (this.vendedorPK != null && !this.vendedorPK.equals(other.vendedorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return usuario.getNombre();
    }
    
}
