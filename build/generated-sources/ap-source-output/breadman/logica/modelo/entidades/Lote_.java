package breadman.logica.modelo.entidades;

import breadman.logica.modelo.entidades.Inventario;
import breadman.logica.modelo.entidades.OrdenProduccion;
import breadman.logica.modelo.entidades.Panadero;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-22T01:34:18")
@StaticMetamodel(Lote.class)
public class Lote_ { 

    public static volatile SingularAttribute<Lote, Integer> codigoLote;
    public static volatile SingularAttribute<Lote, Integer> cantidadProducida;
    public static volatile SingularAttribute<Lote, Panadero> panadero;
    public static volatile SingularAttribute<Lote, Date> fechaDeVencimiento;
    public static volatile SingularAttribute<Lote, Date> fechaDeProduccion;
    public static volatile SingularAttribute<Lote, Inventario> inventario;
    public static volatile SingularAttribute<Lote, OrdenProduccion> idCodigoOrdenProduccion;

}