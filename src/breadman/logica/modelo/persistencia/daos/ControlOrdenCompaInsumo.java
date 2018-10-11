/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.persistencia.daos;

import breadman.logica.modelo.entidades.OrdenDeCompraInsumo;
import breadman.logica.modelo.entidades.Venta;
import breadman.logica.modelo.persistencia.OrdenDeCompraInsumoJpaController;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Ariel Arnedo
 */
public class ControlOrdenCompaInsumo {
    
    private final OrdenDeCompraInsumoJpaController canController;

    public ControlOrdenCompaInsumo() {
        this.canController = new OrdenDeCompraInsumoJpaController(Persistence.createEntityManagerFactory("BreadmanPU"));
    }
    
    public List<OrdenDeCompraInsumo> listarTodas(){
        return canController.findOrdenDeCompraInsumoEntities();
    }
    
    public List<List<OrdenDeCompraInsumo>> getComprasInsumoPorDia(){
        List<List<OrdenDeCompraInsumo>> compraInsumoPordia = fillDay();
        List<OrdenDeCompraInsumo> list = listarTodas();
        
        list.forEach((ordenDeCompraInsumo) -> {
            int dia = getDiaCompraInsumo(ordenDeCompraInsumo);
            compraInsumoPordia.get(dia).add(ordenDeCompraInsumo);
        });
        
        return compraInsumoPordia;
    }
    
    private Integer getDiaCompraInsumo(OrdenDeCompraInsumo ordenCompraInsumo){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(ordenCompraInsumo.getFechaDeCompra());
        return calendar.get(GregorianCalendar.DAY_OF_WEEK) - 1;
    }
    
    public static Integer getCantidadComprarPorDia(List<OrdenDeCompraInsumo> list){
        Integer acum = 0;
        for (OrdenDeCompraInsumo venta : list) {
            acum += (int)venta.getCantidadDeCompra();
        }
        return acum;
    }
    
    
    
    public static String getDay(Integer integer){
        switch(integer){
            case 0: return "Domingo";
            case 1: return "Lunes";
            case 2: return "Martes";
            case 3: return "Miercoles";
            case 4: return "Jueves";
            case 5: return "Viernes";
            case 6: return "Sabado";
            default: return "Null";
        }
    }
    
    private List<List<OrdenDeCompraInsumo>> fillDay(){
        List<List<OrdenDeCompraInsumo>> ventasPorDia = new ArrayList<>(7);
        for(int i = 0; i < 7 ; i++){
            ventasPorDia.add(i,new ArrayList<>());
        }
        return ventasPorDia;
    }
    
}
