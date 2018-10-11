/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.persistencia.daos;

import breadman.logica.modelo.entidades.OrdenDeCompraInsumo;
import breadman.logica.modelo.entidades.Proveedor;
import breadman.logica.modelo.entidades.Venta;
import breadman.logica.modelo.persistencia.ProveedorJpaController;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Ariel Arnedo
 */
public class ControlProveedor {
    
    private final ProveedorJpaController canController;

    public ControlProveedor() {
        this.canController = new ProveedorJpaController(Persistence.createEntityManagerFactory("BreadmanPU"));
    }
    
    public List<Proveedor> listarTodas(){
        return canController.findProveedorEntities();
    }
    
    public Float getCantidadSuministrada(List<OrdenDeCompraInsumo> listOrden){
        Float acum = 0.0F;
        acum = listOrden.stream().map((ordenDeCompraInsumo) -> ordenDeCompraInsumo.getCantidadDeCompra()).reduce(acum, (accumulator, _item) -> accumulator + _item);
        return acum;
    }
    
    public List<List<OrdenDeCompraInsumo>> getOrdenesPorDia(Proveedor proveedor){
        List<List<OrdenDeCompraInsumo>> ordenesPorDia = fillDay();
        List<OrdenDeCompraInsumo> list = proveedor.getOrdenDeCompraInsumoList();
        
        for(OrdenDeCompraInsumo ordenDeCompraInsumo : list){
            int dia = getDiaDeCompraInsumo(ordenDeCompraInsumo);
            ordenesPorDia.get(dia).add(ordenDeCompraInsumo);
        }
        return ordenesPorDia;
    }
    
    
    private List<List<OrdenDeCompraInsumo>> fillDay(){
        List<List<OrdenDeCompraInsumo>> ventasPorDia = new ArrayList<>(7);
        for(int i = 0; i < 7 ; i++){
            ventasPorDia.add(i,new ArrayList<>());
        }
        return ventasPorDia;
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
    
    private Integer getDiaDeCompraInsumo(OrdenDeCompraInsumo ordenDeCompraInsumo){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(ordenDeCompraInsumo.getFechaDeCompra());
        return calendar.get(GregorianCalendar.DAY_OF_WEEK) - 1;
    }
    
    
    public static String getProveedoresOfInsumo(List<OrdenDeCompraInsumo> list){
        if(list.isEmpty())
            return "No hay Proveedores para este Insumo";
        
        List<String> proveedores = new ArrayList<>();
        for (OrdenDeCompraInsumo ordenDeCompraInsumo : list) {
            if(!proveedores.contains(ordenDeCompraInsumo.getNitProveedor().getNombre()))
                proveedores.add(ordenDeCompraInsumo.getNitProveedor().getNombre());
        }
        
        if(proveedores.size() == 1)
            return proveedores.get(0);
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0 ; i < proveedores.size() ; i++) {
            sb.append(proveedores.get(i));
            if(i != proveedores.size() - 1)
                sb.append(", ");
        }
         return sb.toString();
    }
}
