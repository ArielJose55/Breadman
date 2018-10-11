package breadman.logica.modelo.entidades;

import breadman.logica.modelo.entidades.Inventario;
import breadman.logica.modelo.entidades.Vendedor;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-22T01:34:18")
@StaticMetamodel(Venta.class)
public class Venta_ { 

    public static volatile SingularAttribute<Venta, Vendedor> vendedor;
    public static volatile SingularAttribute<Venta, Integer> codigoVenta;
    public static volatile SingularAttribute<Venta, Date> fechaDeCompra;
    public static volatile SingularAttribute<Venta, Float> cantidadComprada;
    public static volatile SingularAttribute<Venta, Float> valorCompra;
    public static volatile SingularAttribute<Venta, Inventario> inventario;

}