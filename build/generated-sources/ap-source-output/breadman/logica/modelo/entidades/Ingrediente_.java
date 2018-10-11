package breadman.logica.modelo.entidades;

import breadman.logica.modelo.entidades.Insumo;
import breadman.logica.modelo.entidades.Producto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-22T01:34:18")
@StaticMetamodel(Ingrediente.class)
public class Ingrediente_ { 

    public static volatile SingularAttribute<Ingrediente, Insumo> idInsumo;
    public static volatile SingularAttribute<Ingrediente, Float> cantidadRequerida;
    public static volatile SingularAttribute<Ingrediente, Integer> codigoIngrediente;
    public static volatile SingularAttribute<Ingrediente, Producto> idProducto;

}