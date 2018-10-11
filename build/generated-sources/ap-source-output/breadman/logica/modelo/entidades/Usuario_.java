package breadman.logica.modelo.entidades;

import breadman.logica.modelo.entidades.Panadero;
import breadman.logica.modelo.entidades.Vendedor;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-22T01:34:18")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile ListAttribute<Usuario, Panadero> panaderoList;
    public static volatile SingularAttribute<Usuario, String> password;
    public static volatile SingularAttribute<Usuario, Integer> role;
    public static volatile ListAttribute<Usuario, Vendedor> vendedorList;
    public static volatile SingularAttribute<Usuario, Integer> cedula;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile SingularAttribute<Usuario, String> email;
    public static volatile SingularAttribute<Usuario, String> username;

}