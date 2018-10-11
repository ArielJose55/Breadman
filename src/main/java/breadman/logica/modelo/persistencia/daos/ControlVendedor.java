/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.persistencia.daos;

import breadman.logica.modelo.entidades.Producto;
import breadman.logica.modelo.entidades.Vendedor;
import breadman.logica.modelo.entidades.Venta;
import breadman.logica.modelo.persistencia.ProductoJpaController;
import breadman.logica.modelo.persistencia.VendedorJpaController;
import breadman.logica.modelo.util.TableModelData;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Ariel Arnedo
 */
public class ControlVendedor {
    
    private final VendedorJpaController canController;

    public ControlVendedor() {
        this.canController = new VendedorJpaController(Persistence.createEntityManagerFactory("BreadmanPU"));
    }
    
    public List<Vendedor> listarTodas(){
        return canController.findVendedorEntities();
    }
    
    public static List<Venta> agruparVentasPorDia(List<Venta> ventas){
        List<Venta> ventasAgrupadas = new ArrayList<>();
        
        for(int i = 0; i < ventas.size() ; i++){
            Venta venta = new Venta();
            venta.setCantidadComprada(ventas.get(i).getCantidadComprada());
            venta.setFechaDeCompra(ventas.get(i).getFechaDeCompra());
            venta.setValorCompra(ventas.get(i).getValorCompra());
            for(int j = i + 1 ; j < ventas.size() ; j++){
                if(ventas.get(i).getFechaDeCompra().compareTo(ventas.get(j).getFechaDeCompra()) == 0){
                    venta.setCantidadComprada(venta.getCantidadComprada() + ventas.get(j).getCantidadComprada());
                    venta.setValorCompra(venta.getValorCompra() + ventas.get(j).getValorCompra());
                    i = j;
                }
            }
            ventasAgrupadas.add(venta);
        }
        return ventasAgrupadas;
    }
    
    private static boolean isInArray(List<Venta> list, Venta venta){
        for(Venta ventaArray : list){
            if(ventaArray.getFechaDeCompra().compareTo(venta.getFechaDeCompra()) == 0){
                return true;
            }
        }
        return false;
    }
    
    public static List<Venta> agruparVentasPorDia(){
        List<Venta> ventas = new ControlVenta().listarTodas();
        List<Venta> ventasAgrupadas = new ArrayList<>();
        
        for(int i = 0; i < ventas.size() ; i++){
            
            Venta venta = new Venta();
            venta.setFechaDeCompra(ventas.get(i).getFechaDeCompra());
            venta.setValorCompra(ventas.get(i).getValorCompra());
            
            for(int j = i + 1 ; j < ventas.size() ; j++){
                if(ventas.get(i).getFechaDeCompra().compareTo(ventas.get(j).getFechaDeCompra()) == 0){
                     venta.setValorCompra(venta.getValorCompra() + ventas.get(j).getValorCompra());
                }
            }
            if(!isInArray(ventasAgrupadas, venta))
                ventasAgrupadas.add(venta);
        }
        return ventasAgrupadas;
    }
    
    public Producto getProductoMasVendidoPorVendedor(Vendedor vendedor){
        List<Venta> ventas = vendedor.getVentaList();
        
        Producto producto = null;
        int cantidadMax = 0, cantidadActual = 0;
        
        for (int i = 0; i < ventas.size() ; i++) {
            
            cantidadActual = (int)ventas.get(i).getCantidadComprada();
            //System.out.print(ventas.get(i).getInventario().getProducto()+"\t"+ventas.get(i).getCantidadComprada()+"\n");
            for(int j = i + 1; j < ventas.size() ; j++){
                if(ventas.get(i).getInventario().getProducto().equals(ventas.get(j).getInventario().getProducto())){
                    cantidadActual += (int)ventas.get(j).getCantidadComprada();
                }
                
            }
            if(cantidadActual >=
                    cantidadMax){
                producto = ventas.get(i).getInventario().getProducto();
                cantidadMax = cantidadActual;
            }
        }
        
        
        return producto;
    }
    
    public List<TableModelData.DataSalesMoreProduct> getMayorVentaPorVendedor(){
        List<TableModelData.DataSalesMoreProduct> ventasPorVendedor = new ArrayList<>();
        List<Vendedor> vendedores = listarTodas();
        
        vendedores.forEach((vendedore) -> {
            ventasPorVendedor.add(new TableModelData.DataSalesMoreProduct(vendedore, getProductoMasVendidoPorVendedor(vendedore)));
        });
        return ventasPorVendedor;
    }
}
