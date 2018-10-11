package breadman.logica.modelo.entidades;

import breadman.logica.modelo.entidades.Categoria;
import breadman.logica.modelo.entidades.Ingrediente;
import breadman.logica.modelo.entidades.Inventario;
import breadman.logica.modelo.entidades.OrdenProduccion;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-22T01:34:18")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile ListAttribute<Producto, OrdenProduccion> ordenProduccionList;
    public static volatile SingularAttribute<Producto, Float> precioDeVenta;
    public static volatile ListAttribute<Producto, Ingrediente> ingredienteList;
    public static volatile ListAttribute<Producto, Inventario> inventarioList;
    public static volatile SingularAttribute<Producto, Integer> codigoProducto;
    public static volatile SingularAttribute<Producto, Categoria> idCategoria;
    public static volatile SingularAttribute<Producto, String> nombre;

}