/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.persistencia.daos;

import breadman.logica.modelo.entidades.Lote;
import breadman.logica.modelo.persistencia.LoteJpaController;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Ariel Arnedo
 */
public class ControlLote {
    
    private final LoteJpaController canController;

    public ControlLote() {
        this.canController = new LoteJpaController(Persistence.createEntityManagerFactory("BreadmanPU"));
    }
    
    public List<Lote> listarTodas(){
        return canController.findLoteEntities();
    }
    
    public void addLote(Lote lote){
        canController.create(lote);
    }
}
