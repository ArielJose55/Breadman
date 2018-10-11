/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.persistencia.daos;

import breadman.logica.modelo.entidades.Insumo;
import breadman.logica.modelo.persistencia.InsumoJpaController;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Ariel Arnedo
 */
public class ControlInsumo {
    
    private final InsumoJpaController canController;

    public ControlInsumo() {
        this.canController = new InsumoJpaController(Persistence.createEntityManagerFactory("BreadmanPU"));
    }
    
    
    public  List<Insumo> listarTodas(){
        return canController.findInsumoEntities();
    }
}
