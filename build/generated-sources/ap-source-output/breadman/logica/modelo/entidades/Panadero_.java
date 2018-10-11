package breadman.logica.modelo.entidades;

import breadman.logica.modelo.entidades.Lote;
import breadman.logica.modelo.entidades.OrdenProduccion;
import breadman.logica.modelo.entidades.PanaderoPK;
import breadman.logica.modelo.entidades.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-22T01:34:18")
@StaticMetamodel(Panadero.class)
public class Panadero_ { 

    public static volatile ListAttribute<Panadero, OrdenProduccion> ordenProduccionList;
    public static volatile SingularAttribute<Panadero, Usuario> usuario;
    public static volatile ListAttribute<Panadero, Lote> loteList;
    public static volatile SingularAttribute<Panadero, PanaderoPK> panaderoPK;

}