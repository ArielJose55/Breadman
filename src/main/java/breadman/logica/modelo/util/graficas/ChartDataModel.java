/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.util.graficas;

import breadman.logica.modelo.core.State;
import breadman.logica.modelo.entidades.Inventario;
import breadman.logica.modelo.entidades.Lote;
import breadman.logica.modelo.entidades.OrdenDeCompraInsumo;
import breadman.logica.modelo.entidades.OrdenProduccion;
import breadman.logica.modelo.entidades.Panadero;
import breadman.logica.modelo.entidades.Producto;
import breadman.logica.modelo.entidades.Proveedor;
import breadman.logica.modelo.entidades.Vendedor;
import breadman.logica.modelo.entidades.Venta;
import breadman.logica.modelo.persistencia.daos.ControlInventario;
import breadman.logica.modelo.persistencia.daos.ControlOrdenCompaInsumo;
import breadman.logica.modelo.persistencia.daos.ControlPanadero;
import breadman.logica.modelo.persistencia.daos.ControlProducto;
import breadman.logica.modelo.persistencia.daos.ControlProveedor;
import breadman.logica.modelo.persistencia.daos.ControlVendedor;
import breadman.logica.modelo.persistencia.daos.ControlVenta;
import breadman.logica.modelo.util.ModelData;
import java.util.List;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Ariel Arnedo
 */
public class ChartDataModel {
    
    public static XYDataset createSeriesTimeDataSet(TipoGrafica tipoGrafica){
        
        
        switch(tipoGrafica){
            case VENTAS_POR_TIEMPO_AND_VENDEDOR:{
                TimeSeriesCollection dataset = new TimeSeriesCollection();
                List<Vendedor> vendedores = new ControlVendedor().listarTodas();
                
                for (Vendedor vendedor : vendedores) {
                    TimeSeries serieTime = new TimeSeries(vendedor.toString());
                    
                    for(Venta venta : ControlVendedor.agruparVentasPorDia(vendedor.getVentaList())){
                        System.out.println(venta);
                        serieTime.add(new Day(venta.getFechaDeCompra()), venta.getCantidadComprada());
                    }

                    dataset.addSeries(serieTime);
                }
                return dataset;
            }
            case VENTAS_POR_TIEMPO:{
                TimeSeriesCollection dataset = new TimeSeriesCollection();
                TimeSeries serieTime = new TimeSeries("Flujo de ventas");
                
                for(Venta venta : ControlVendedor.agruparVentasPorDia()){
                    TimeSeriesDataItem timeSeriesDataItem = new TimeSeriesDataItem(new Day(venta.getFechaDeCompra()), venta.getValorCompra());
                    serieTime.add(timeSeriesDataItem);
                }
                dataset.addSeries(serieTime);
                
                return dataset;
            }
        }
        
        return null;
    }
    
    public static XYDataset createSeriesTimeDataSet(Object object, TipoGrafica tipoGrafica){
        
        switch(tipoGrafica){
            case PRODUCCION_DIARIA_POR_PANADERO:{
                
            }
        }
        return null;
    }
    public static DefaultPieDataset createPieDataSet(TipoGrafica tipoGrafica){
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        switch(tipoGrafica){
            case PRODUCTOS_MAS_VENDIDOS:{
                List<Inventario> list = new ControlInventario().productosMasVendidos();
                list.stream().map((object) -> (Inventario) object).forEachOrdered((inventario) -> {
                    dataset.setValue(((Inventario)inventario).getProducto().getNombre(), ((Inventario)inventario).getVentaList().size());
                });
                return dataset;
            }
            case PRODUCTOS_MAS_PRODUCIDOS:{
                List<Inventario> list = new ControlInventario().productosMasVendidos();
                list.stream().map((object) -> (Inventario) object).forEachOrdered((inventario) -> {
                    dataset.setValue(((Inventario)inventario).getProducto().getNombre(), ((Inventario)inventario).getLoteList().size());
                });
                return dataset;
            }
            case MAYOR_PROVEEDOR:{
                List<Proveedor> list = new ControlProveedor().listarTodas();
                list.forEach((proveedor) -> {
                    dataset.setValue(proveedor.getNombre(), proveedor.getOrdenDeCompraInsumoList().size());
                });
                return dataset;
            }
            case CAPACIDAD_DE_INVENTARIO:{
                Double capacidad = new ControlInventario().getCapacidadDeInventario();
                dataset.setValue("Capacidad Actual", capacidad);
                dataset.setValue("Capacidad Faltante", 1.0 - capacidad);
                return dataset;
            }
        }
        return null;
    }
    
    public static DefaultCategoryDataset createBarDataSet(TipoGrafica tipoGrafica){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        switch(tipoGrafica){
            case PRODUCTOS_MAS_VENDIDOS:{
                List<Producto> listProductos = new ControlProducto().listarTodas();
                List<Inventario> listInventario = new ControlInventario().listarTodas();
                int i = 0;
                for(Producto producto : listProductos){
                    boolean tiene_inventario = false;
                        for(Inventario inventario : listInventario){
                        if(producto.equals(inventario.getProducto())){
                            dataset.addValue(ControlVenta.getVentas(inventario.getVentaList()), new Integer(i++), producto.getNombre());
                            tiene_inventario = true;
                        }
                    }
                    if(!tiene_inventario)
                        dataset.addValue(new Integer(0), new Integer(i++), producto.getNombre());
                }
                
                return dataset;
            }
            case PRODUCTOS_MAS_PRODUCIDOS:{
                
                return dataset;
            }
            case VENTAS_POR_DIA:{
                
                List<List<Venta>> list = new ControlVenta().getVentasPorDia();
                int i = 0;
                for(List<Venta> ventas : list){
                    dataset.addValue(ControlVenta.getVentas(ventas), new Integer(i+1), ControlVenta.getDay(i++));
                }
                return dataset;
            }
            
            case COMPRA_INSUMO_POR_DIA:{
                
                List<List<OrdenDeCompraInsumo>> list = new ControlOrdenCompaInsumo().getComprasInsumoPorDia();
                int i = 0;
                for(List<OrdenDeCompraInsumo> compras : list){
                    dataset.addValue(ControlOrdenCompaInsumo.getCantidadComprarPorDia(compras), new Integer(i+1), ControlVenta.getDay(i++));
                }
                return dataset;
            }
            
            case VENTAS_POR_VENDEDOR:{
               List<Vendedor> list = new ControlVendedor().listarTodas();
               
               int i = 0;
               list.forEach((vendedor) -> {
                   dataset.addValue(ControlVenta.getVentas(vendedor.getVentaList()), new Integer(i+1), vendedor.getUsuario().getNombre());
               });
               return dataset;
            }
            
            case ORDEN_AND_LOTE_POR_PANADERO:{
                List<Panadero> panaderos = new ControlPanadero().listarTodas();
                
                for (Panadero panadero : panaderos) {
                    ModelData.DataBaker data = new ControlPanadero().getProduccionAndLote(panadero);
                    dataset.addValue(data.getLoteProducido().getCantidadProducida(), "Cantidad de Lotes Produccidos", panadero.getUsuario().getNombre());
                    dataset.addValue(data.getOrdenDeProduccion().getCantidad(), "Cantidad de Lotes a Producir", panadero.getUsuario().getNombre());
                }
                return dataset;
            }     
            
            case ORDENES_PRODUCCION_ESTADO:{
                List<Panadero> panaderos = new ControlPanadero().listarTodas();
                
                for (Panadero panadero : panaderos) {
                    int pendientes = 0, vendidas = 0, actuales = 0, producidas = 0;
                    for(OrdenProduccion ordenProduccion : panadero.getOrdenProduccionList()){
                        if(ordenProduccion.getEstado().compareTo(State.PENDIENTE) == 0)
                            pendientes++;
                        else if(ordenProduccion.getEstado().compareTo(State.VENCIDA) == 0)
                            vendidas++;
                        else if(ordenProduccion.getEstado().compareTo(State.PRODUCIDA) == 0)
                            producidas++;
                        else
                            actuales++;
                    }
                    dataset.addValue(producidas, "Ordenes de Produccion Producidas", panadero.getUsuario().getNombre());
                    dataset.addValue(vendidas, "Ordenes de Produccion Vencidas", panadero.getUsuario().getNombre());
                    dataset.addValue(pendientes, "Ordenes de Produccion Pendientes", panadero.getUsuario().getNombre());
                    dataset.addValue(actuales, "Ordenes de Produccion Actuales", panadero.getUsuario().getNombre());
                }
                return dataset;
            }   
        }
        return null;
    }
    
     public static DefaultCategoryDataset createBarDataSet(Object object,TipoGrafica tipoGrafica){
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
         switch(tipoGrafica){
             case ORDENES_DIARIAS_POR_PROVEEDOR:{
                Proveedor proveedor = (Proveedor) object;
                 
                List<List<OrdenDeCompraInsumo>> list = new ControlProveedor().getOrdenesPorDia(proveedor);
                 
                int i = 0;
                for(List<OrdenDeCompraInsumo> ordenDeCompraInsumo : list){
                    dataset.addValue(ordenDeCompraInsumo.size(), new Integer(i+1), ControlProveedor.getDay(i++));
                }
                
                return dataset;
             }
             case VENTAS_DIARIAS_POR_VENDEROR:{
                 
                 Vendedor vendedor = (Vendedor) object;
                 List<List<Venta>> list = new ControlVenta().getVentasPorVendedor(vendedor);
                 
                int i = 0;
                for(List<Venta> ventas : list){
                    dataset.addValue(ventas.size(), new Integer(i+1), ControlProveedor.getDay(i++));
                }
                
                return dataset;
             }
             
             case PRODUCCION_DIARIA_POR_PANADERO:{
                 Panadero vendedor = (Panadero) object;
                 
                List<List<Lote>> list = new ControlPanadero().getVentasPorPanadero(vendedor);
                int i = 0;
                for(List<Lote> lotes : list){
                    dataset.addValue(lotes.size(), "Producción", ControlProveedor.getDay(i++));
                }
                return dataset;
             }
             case PRODUCCION_PRODUCTO_DIARIA_POR_PANADERO:{
                 Panadero vendedor = (Panadero) object;
                 
                 List<List<Producto>> list = new ControlPanadero().getProductosMasProduccidosPorPanadero(vendedor);
                 int i = 0;
                 for(List<Producto> productos : list){
                    dataset.addValue(productos.size(), new Integer(i++), productos.get(0).getNombre());
                }
                return dataset;
             }
         }
         return null;
     }
    
    public static class DataProductSell implements Comparable<ChartDataModel.DataProductSell>{
        
        private final Inventario inventario;

        public DataProductSell(Inventario inventario) {
            this.inventario = inventario;
        }
        
        public String getNameProduct(){
            return inventario.getProducto().getNombre();
        }
        
        public Integer getSales(){
            return inventario.getVentaList().size();
        }
        
        public Integer getProductions(){
            return inventario.getLoteList().size();
        }

        public Inventario getInventario() {
            return inventario;
        }

        @Override
        public int compareTo(DataProductSell o) {
            return o.getSales() - getSales();
        }

        @Override
        public String toString() {
            return getNameProduct();
        }
        
    }
    
    public static class DataProductProduction implements Comparable<ChartDataModel.DataProductProduction>{
        
        private final Inventario inventario;

        public DataProductProduction(Inventario inventario) {
            this.inventario = inventario;
        }
        
        public String getNameProduct(){
            return inventario.getProducto().getNombre();
        }
        
        
        public Integer getProductions(){
            return inventario.getLoteList().size();
        
        }
        
        public Inventario getInventario() {
            return inventario;
        }

        @Override
        public int compareTo(DataProductProduction o) {
            return o.getProductions() - getProductions();
        }

        
    }
}
