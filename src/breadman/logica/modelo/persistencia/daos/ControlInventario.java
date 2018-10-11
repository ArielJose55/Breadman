/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.persistencia.daos;

import breadman.logica.modelo.entidades.Inventario;
import breadman.logica.modelo.entidades.Producto;
import breadman.logica.modelo.entidades.Producto_;
import breadman.logica.modelo.persistencia.InventarioJpaController;
import breadman.logica.modelo.util.graficas.ChartDataModel;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.Persistence;

/**
 *
 * @author Ariel Arnedo
 */
public class ControlInventario {
    
    private final InventarioJpaController canController;

    public ControlInventario() {
        this.canController = new InventarioJpaController(Persistence.createEntityManagerFactory("BreadmanPU"));
    }
    
    
    public List<Inventario> listarTodas(){
        return canController.findInventarioEntities();
    }
    
    public Inventario getInventario(Producto producto){
        List<Inventario> list = listarTodas();
        
        for(Inventario inventario : list){
            if(Objects.equals(inventario.getProducto().getCodigoProducto(), producto.getCodigoProducto())){
                return inventario;
            }
        }
        return null;
    }
    
    public List<Inventario> productosMasVendidos(){
        List<Inventario> list = listarTodas();
        
        ChartDataModel.DataProductSell [] cantidaVentas = new ChartDataModel.DataProductSell[list.size()];
        int i = 0;
        for(Inventario inventario : list){
            cantidaVentas[i++] = new ChartDataModel.DataProductSell(inventario);
        }
        Arrays.sort(cantidaVentas);
        list.clear();
        for (ChartDataModel.DataProductSell cantidaVenta : cantidaVentas) {
            list.add(cantidaVenta.getInventario());
        }
        
        return list;
    }
    
    public List<Inventario> productosMasProduccidos(){
        List<Inventario> list = listarTodas();
        
        ChartDataModel.DataProductProduction [] cantidaLotes = new ChartDataModel.DataProductProduction[list.size()];
        int i = 0;
        for(Inventario inventario : list){
            cantidaLotes[i++] = new ChartDataModel.DataProductProduction(inventario);
        }
        Arrays.sort(cantidaLotes);
        list.clear();
        for (ChartDataModel.DataProductProduction cantidaVenta : cantidaLotes) {
            list.add(cantidaVenta.getInventario());
        }
        
        return list;
    }
    
    public Double getCapacidadDeInventario(){
        List<Inventario> list = listarTodas();
        double capacidadTotal = 0;
        double capacidadActual = 0;
         for(Inventario inventario : list){
             capacidadTotal += inventario.getCantidadMax();
             capacidadActual += inventario.getCantidad();
         }
         
         return new Double(capacidadActual / capacidadTotal);
    }
    
    
}
