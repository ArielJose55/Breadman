/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.persistencia.daos;

import breadman.logica.modelo.entidades.Lote;
import breadman.logica.modelo.entidades.OrdenProduccion;
import breadman.logica.modelo.entidades.Panadero;
import breadman.logica.modelo.entidades.Producto;
import breadman.logica.modelo.entidades.Producto_;
import breadman.logica.modelo.persistencia.OrdenProduccionJpaController;
import breadman.logica.modelo.persistencia.PanaderoJpaController;
import breadman.logica.modelo.util.ModelData;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Ariel Arnedo
 */
public class ControlPanadero {
    
    private final PanaderoJpaController canController;

    public ControlPanadero() {
        this.canController = new PanaderoJpaController(Persistence.createEntityManagerFactory("BreadmanPU"));
    }
    
    
    public  List<Panadero> listarTodas(){
        return canController.findPanaderoEntities();
    }
    
    public ModelData.DataBaker getProduccionAndLote(Panadero panadero){
        ModelData.DataBaker data = new ModelData.DataBaker();
        
        for (Lote lote : panadero.getLoteList()) {
            data.updateCantidadProducida(lote.getCantidadProducida());
            data.updateCantidadAProducir(lote.getIdCodigoOrdenProduccion().getCantidad());
        }
        return data;
    }
    
    public List<List<Lote>> getVentasPorPanadero(Panadero panadero){
        List<List<Lote>> ventasPorDia = fillDay();
        List<Lote> list = panadero.getLoteList();
        for(Lote lote : list){
            int dia = getDiaDeVenta(lote);
            ventasPorDia.get(dia).add(lote);
        }
        
        return ventasPorDia;
    }
    
    public List<List<Producto>>  getProductosMasProduccidosPorPanadero(Panadero panadero){
        List<List<Producto>> listProductos = new ArrayList<>();
        List<Lote> lotes = panadero.getLoteList();
        for(int i = 0 ; i < lotes.size() ; i++){
            List<Producto> productos = new ArrayList<>();
            
            Producto producto = lotes.get(i).getInventario().getProducto();

            productos.add(producto);
            for(int j = i + 1 ; j < lotes.size() ; j++){
                if(lotes.get(i).getInventario().getProducto().equals(lotes.get(j).getInventario().getProducto())){
                    productos.add(producto);
                }
            }

            if(!isInArray(listProductos, producto)){
                listProductos.add(productos);
            }
//            System.out.println("");
        }
        return listProductos;
    }
    
    private Integer getDiaDeVenta(Lote lote){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(lote.getFechaDeProduccion());
        return calendar.get(GregorianCalendar.DAY_OF_WEEK) - 1;
    }
    
    public static Integer getVentas(List<Lote> list){
        Integer acum = 0;
        for (Lote lote : list) {
            acum += (int)lote.getCantidadProducida();
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
    
    private List<List<Lote>> fillDay(){
        List<List<Lote>> ventasPorDia = new ArrayList<>(7);
        for(int i = 0; i < 7 ; i++){
            ventasPorDia.add(i,new ArrayList<>());
        }
        return ventasPorDia;
    }
    
    private static boolean isInArray(List<List<Producto>> list, Producto producto){

        for(List<Producto> arrayProductos : list){
            for(Producto arrayProducto : arrayProductos){
                if(arrayProducto.equals(producto)){
                    return true;
                }
            }
        }
        return false;
    }
}
