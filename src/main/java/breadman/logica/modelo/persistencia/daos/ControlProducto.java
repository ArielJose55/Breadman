/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.persistencia.daos;

import breadman.logica.modelo.entidades.Producto;
import breadman.logica.modelo.persistencia.CategoriaJpaController;
import breadman.logica.modelo.persistencia.ProductoJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Ariel Arnedo
 */
public class ControlProducto {

    private final ProductoJpaController canController;

    public ControlProducto() {
        this.canController = new ProductoJpaController(Persistence.createEntityManagerFactory("BreadmanPU"));
    }
    
    public List<Producto> listarTodas(){
        return canController.findProductoEntities();
    }
    
}
