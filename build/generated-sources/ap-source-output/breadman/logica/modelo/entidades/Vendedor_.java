package breadman.logica.modelo.entidades;

import breadman.logica.modelo.entidades.Usuario;
import breadman.logica.modelo.entidades.VendedorPK;
import breadman.logica.modelo.entidades.Venta;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-22T01:34:18")
@StaticMetamodel(Vendedor.class)
public class Vendedor_ { 

    public static volatile ListAttribute<Vendedor, Venta> ventaList;
    public static volatile SingularAttribute<Vendedor, Usuario> usuario;
    public static volatile SingularAttribute<Vendedor, VendedorPK> vendedorPK;

}