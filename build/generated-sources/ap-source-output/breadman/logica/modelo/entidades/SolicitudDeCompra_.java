package breadman.logica.modelo.entidades;

import breadman.logica.modelo.entidades.Insumo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-22T01:34:18")
@StaticMetamodel(SolicitudDeCompra.class)
public class SolicitudDeCompra_ { 

    public static volatile SingularAttribute<SolicitudDeCompra, Integer> estado;
    public static volatile SingularAttribute<SolicitudDeCompra, Insumo> idInsumo;
    public static volatile SingularAttribute<SolicitudDeCompra, Date> fechaMaximaDeCompra;
    public static volatile SingularAttribute<SolicitudDeCompra, Float> cantidadSolicitada;
    public static volatile SingularAttribute<SolicitudDeCompra, Integer> codigoSolicitud;

}