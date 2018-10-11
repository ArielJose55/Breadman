/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.persistencia.daos;

import breadman.logica.modelo.core.ControlUserModel;
import breadman.logica.modelo.core.State;
import breadman.logica.modelo.entidades.Categoria;
import breadman.logica.modelo.entidades.OrdenProduccion;
import breadman.logica.modelo.persistencia.CategoriaJpaController;
import breadman.logica.modelo.persistencia.OrdenProduccionJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Ariel Arnedo
 */
public class ControlOrdenProduccion {
    
    private final OrdenProduccionJpaController canController;

    public ControlOrdenProduccion() {
        this.canController = new OrdenProduccionJpaController(Persistence.createEntityManagerFactory("BreadmanPU"));
    }
    
    
    public  List<OrdenProduccion> listarTodas(){
        return canController.findOrdenProduccionEntities();
    }
    
    public List<OrdenProduccion> listarOrdenesDeProduccionPendientes(){
        List<OrdenProduccion> list = listarTodas();
        List<OrdenProduccion> listOrder = new ArrayList<>();
        
        list.stream().filter((ordenProduccion) -> (ordenProduccion.getEstado().compareTo(State.ACTUAL) ==  0 || ordenProduccion.getEstado().compareTo(State.PENDIENTE) == 0 &&
                ordenProduccion.getPanadero().getUsuario().getCedula().compareTo(ControlUserModel.getInstance().getUsuario().getCedula()) == 0))
                .forEachOrdered((ordenProduccion) -> {
            listOrder.add(ordenProduccion);
        });
        return listOrder;
    }
    
    public List<OrdenProduccion> listarOrdenesDeProduccionActuales(){
        List<OrdenProduccion> list = listarTodas();
        List<OrdenProduccion> listOrder = new ArrayList<>();
        
        list.stream().filter((ordenProduccion) -> (ordenProduccion.getEstado().compareTo(State.ACTUAL) ==  0  &&
                ordenProduccion.getPanadero().getUsuario().getCedula().compareTo(101011) == 0))
                .forEachOrdered((ordenProduccion) -> {
            listOrder.add(ordenProduccion);
        });
        return listOrder;
    }
    
    public OrdenProduccion getProduccionMasReciente(){
        List<OrdenProduccion> ordenes = listarOrdenesDeProduccionActuales();

        OrdenProduccion masReciente = null;
        
        for(int i = 0 ; i < ordenes.size() ; i++){

            masReciente = ordenes.get(i);
            
            for(int  j = i + 1 ; j < ordenes.size() ; j++){
                if(ordenes.get(i).getFechaPrevista().before(ordenes.get(j).getFechaPrevista())){
                    masReciente = ordenes.get(j);
                    i = j;
                }
            }
        }
        return masReciente;
    }
    
    public int cantidadOrdenesActuales(){
        
        int acum = 0;
        acum = listarOrdenesDeProduccionPendientes().stream().filter((ordenProduccion) -> 
                (ordenProduccion.getEstado().compareTo(State.ACTUAL) == 0)).map((_item) -> 1).reduce(acum, Integer::sum);
        return acum;
    }
    
    public int cantidadOrdenesPendientes(){
        int acum = 0;
        acum = listarOrdenesDeProduccionPendientes().stream().filter((ordenProduccion) -> 
                (ordenProduccion.getEstado().compareTo(State.PENDIENTE) == 0)).map((_item) -> 1).reduce(acum, Integer::sum);
        return acum;
    }
}
