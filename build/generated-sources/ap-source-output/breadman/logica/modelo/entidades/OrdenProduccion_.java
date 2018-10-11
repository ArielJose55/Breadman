package breadman.logica.modelo.entidades;

import breadman.logica.modelo.entidades.Lote;
import breadman.logica.modelo.entidades.Panadero;
import breadman.logica.modelo.entidades.Producto;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-22T01:34:18")
@StaticMetamodel(OrdenProduccion.class)
public class OrdenProduccion_ { 

    public static volatile SingularAttribute<OrdenProduccion, Integer> estado;
    public static volatile SingularAttribute<OrdenProduccion, Integer> codigoOrdenProduccion;
    public static volatile SingularAttribute<OrdenProduccion, Date> fechaPrevista;
    public static volatile SingularAttribute<OrdenProduccion, Panadero> panadero;
    public static volatile ListAttribute<OrdenProduccion, Lote> loteList;
    public static volatile SingularAttribute<OrdenProduccion, Integer> cantidad;
    public static volatile SingularAttribute<OrdenProduccion, Producto> idProducto;

}