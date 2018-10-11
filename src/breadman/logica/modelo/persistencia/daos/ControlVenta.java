/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.persistencia.daos;

import breadman.logica.modelo.entidades.Inventario;
import breadman.logica.modelo.entidades.Producto;
import breadman.logica.modelo.entidades.Producto_;
import breadman.logica.modelo.entidades.Vendedor;
import breadman.logica.modelo.entidades.Venta;
import breadman.logica.modelo.persistencia.VentaJpaController;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Persistence;
import sun.util.calendar.Gregorian;

/**
 *
 * @author Ariel Arnedo
 */
public class ControlVenta {
    
    private final VentaJpaController canController;

    public ControlVenta() {
        this.canController = new VentaJpaController(Persistence.createEntityManagerFactory("BreadmanPU"));
    }
    
    public boolean registrarVentas(List<Venta> ventas){
        ventas.forEach((venta) -> {
            canController.create(venta);
        });
        return true;
    }
    
    
    public List<Venta> listarTodas(){
        return canController.findVentaEntities();
    }
    
    public List<List<Venta>> getVentasPorDia(){
        List<List<Venta>> ventasPorDia = fillDay();
        List<Venta> list = listarTodas();
        
        list.forEach((venta) -> {
            int dia = getDiaDeVenta(venta);
            ventasPorDia.get(dia).add(venta);
        });
        
        return ventasPorDia;
    }
    
    public List<List<Venta>> getVentasPorVendedor(Vendedor vendedor){
        List<List<Venta>> ventasPorDia = fillDay();
        List<Venta> list = vendedor.getVentaList();
        for(Venta venta : list){
            int dia = getDiaDeVenta(venta);
            ventasPorDia.get(dia).add(venta);
        }
        
        return ventasPorDia;
    }
    
    private Integer getDiaDeVenta(Venta venta){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(venta.getFechaDeCompra());
        return calendar.get(GregorianCalendar.DAY_OF_WEEK) - 1;
    }
    
    public static Integer getVentas(List<Venta> list){
        Integer acum = 0;
        for (Venta venta : list) {
            acum += (int)venta.getCantidadComprada();
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
    
    private List<List<Venta>> fillDay(){
        List<List<Venta>> ventasPorDia = new ArrayList<>(7);
        for(int i = 0; i < 7 ; i++){
            ventasPorDia.add(i,new ArrayList<>());
        }
        return ventasPorDia;
    }
}
