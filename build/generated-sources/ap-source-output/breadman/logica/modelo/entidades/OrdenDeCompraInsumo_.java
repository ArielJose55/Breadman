package breadman.logica.modelo.entidades;

import breadman.logica.modelo.entidades.Insumo;
import breadman.logica.modelo.entidades.Proveedor;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-22T01:34:18")
@StaticMetamodel(OrdenDeCompraInsumo.class)
public class OrdenDeCompraInsumo_ { 

    public static volatile SingularAttribute<OrdenDeCompraInsumo, Float> cantidadDeCompra;
    public static volatile SingularAttribute<OrdenDeCompraInsumo, Insumo> idInsumo;
    public static volatile SingularAttribute<OrdenDeCompraInsumo, Date> fechaDeCompra;
    public static volatile SingularAttribute<OrdenDeCompraInsumo, Proveedor> nitProveedor;
    public static volatile SingularAttribute<OrdenDeCompraInsumo, Integer> codigoCompraInsumo;

}