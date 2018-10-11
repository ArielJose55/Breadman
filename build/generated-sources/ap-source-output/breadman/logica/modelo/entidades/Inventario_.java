package breadman.logica.modelo.entidades;

import breadman.logica.modelo.entidades.InventarioPK;
import breadman.logica.modelo.entidades.Lote;
import breadman.logica.modelo.entidades.Producto;
import breadman.logica.modelo.entidades.Venta;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-22T01:34:18")
@StaticMetamodel(Inventario.class)
public class Inventario_ { 

    public static volatile SingularAttribute<Inventario, InventarioPK> inventarioPK;
    public static volatile ListAttribute<Inventario, Venta> ventaList;
    public static volatile ListAttribute<Inventario, Lote> loteList;
    public static volatile SingularAttribute<Inventario, Integer> cantidad;
    public static volatile SingularAttribute<Inventario, Producto> producto;
    public static volatile SingularAttribute<Inventario, Integer> cantidadMin;
    public static volatile SingularAttribute<Inventario, Integer> cantidadMax;

}