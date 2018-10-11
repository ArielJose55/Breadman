package breadman.logica.modelo.entidades;

import breadman.logica.modelo.entidades.Ingrediente;
import breadman.logica.modelo.entidades.OrdenDeCompraInsumo;
import breadman.logica.modelo.entidades.SolicitudDeCompra;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-22T01:34:18")
@StaticMetamodel(Insumo.class)
public class Insumo_ { 

    public static volatile SingularAttribute<Insumo, String> unidad;
    public static volatile ListAttribute<Insumo, OrdenDeCompraInsumo> ordenDeCompraInsumoList;
    public static volatile SingularAttribute<Insumo, Integer> codigoInsumo;
    public static volatile SingularAttribute<Insumo, Float> cantidadAlmacenada;
    public static volatile ListAttribute<Insumo, SolicitudDeCompra> solicitudDeCompraList;
    public static volatile ListAttribute<Insumo, Ingrediente> ingredienteList;
    public static volatile SingularAttribute<Insumo, String> nombre;

}