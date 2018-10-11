/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.util;

import breadman.logica.modelo.entidades.Insumo;
import breadman.logica.modelo.entidades.Inventario;
import breadman.logica.modelo.entidades.Lote;
import breadman.logica.modelo.entidades.OrdenDeCompraInsumo;
import breadman.logica.modelo.entidades.OrdenProduccion;
import breadman.logica.modelo.entidades.Producto;
import breadman.logica.modelo.entidades.Vendedor;
import breadman.logica.modelo.persistencia.daos.ControlProveedor;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Ariel Arnedo
 */
public abstract class ModelData extends javax.swing.table.AbstractTableModel{

    protected final java.util.List<Object> list;
    protected final Class clase;
    
    public ModelData(Class clase) {
        this.clase = clase;
        list = new ArrayList<>();
    }
    
    public boolean add(Object element){
        int last = list.size();
        boolean added = list.add(element);
        fireTableRowsInserted(last, list.size());
        return added;
    }
    
    public boolean add(List<Object> list){
        int last = this.list.size();
        boolean added = this.list.addAll(list);
        fireTableRowsInserted(last, this.list.size());
        return added;
    }
    
    public boolean delete(Object element){
        return list.contains(element) && list.remove(element);
    }
    
    public void clearData(){
        int last = list.size();
        list.clear();
        fireTableRowsDeleted(0, last);
    }
    
    public boolean add(List list, Class origen){

        if(list.isEmpty())
            return false;

        List<Object> lista = new ArrayList<>();
        switch(origen.getSimpleName()){
            case "OrdenProduccion":{
                list.stream().map((object) -> (OrdenProduccion) object).forEachOrdered((Object ordenProduccion) -> {
                    lista.add(new DataOrderProduction((OrdenProduccion) ordenProduccion));
                });
                return add(lista);
            }
            case "Inventario":{
                list.stream().map((object) -> (Inventario) object).forEachOrdered((Object inventario) -> {
                    lista.add(new DataProductInventory((Inventario) inventario));
                });
                return add(lista);
            }
            
            case "OrdenDeCompraInsumo":{
                
                for(Object object : list){
                    OrdenDeCompraInsumo ordenDeCompraInsumo = (OrdenDeCompraInsumo)object;
                    lista.add(ordenDeCompraInsumo);
                }
                return add(lista);
            }
            case "DataSalesMoreProduct":{
                for(Object object : list){
                    TableModelData.DataSalesMoreProduct data = (TableModelData.DataSalesMoreProduct) object;
                    lista.add(data);
                }
                return add(lista);
            }
            
            case "Insumo":{
                for(Object object : list){
                    lista.add((Insumo)object);
                }
                 return add(lista);
            }
        }
        return false;
    }
    
    public Object get(int row){
        if(row < 0 || row >= list.size())
            return null;
        
        return list.get(row);
    }

    public List getData(){
        return list;
    }
    
    public float getCantPrimary(){
        float cantidad = 0F;
        switch(clase.getSimpleName()){
            case "DataRegisterSell": {
                cantidad = list.stream().map((object) ->
                        (DataRegisterSell) object).map((dataRegisterSell) -> 
                                ((float) dataRegisterSell.cantidad * (float) dataRegisterSell.producto.getPrecioDeVenta())).reduce(cantidad, (accumulator, _item) -> accumulator + _item);
                return cantidad;
            }
            default: return 0;
        }
    }
    
    @Override
    public abstract int getRowCount();

    @Override
    public abstract int getColumnCount();

    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);
    
    protected Object getValueClassVendedorSales(int rowIndex, int columnIndex){
        DataSalesMoreProduct data = (DataSalesMoreProduct) list.get(rowIndex);
        switch(columnIndex){
            case 0: return data.getVendedor().getUsuario().getCedula();
            case 1: return data.getVendedor().getUsuario().getNombre();
            case 2: return data.getProducto() == null ? "No registra ventas" : data.getProducto().getNombre();
            default: return null;
        }
    }
    
    protected Object getValueClassOrdenCompraInsumo(int rowIndex, int columnIndex){
        OrdenDeCompraInsumo ordenDeCompraInsumo = (OrdenDeCompraInsumo) list.get(rowIndex);
        switch(columnIndex){
            case 0: return ordenDeCompraInsumo.getIdInsumo().getNombre();
            case 1: return ordenDeCompraInsumo.getCantidadDeCompra();
            case 2: return ordenDeCompraInsumo.getIdInsumo().getUnidad();
            case 3: return formatDate(ordenDeCompraInsumo.getFechaDeCompra());
            default: return null;
        }
    }
    
    protected Object getValueClassInsumo(int rowIndex, int columnIndex){
        Insumo insumo = (Insumo) list.get(rowIndex);
        switch(columnIndex){
            case 0: return insumo.getNombre();
            case 1: return insumo.getCantidadAlmacenada();
            case 2: return insumo.getUnidad();
            case 3: return ControlProveedor.getProveedoresOfInsumo(insumo.getOrdenDeCompraInsumoList());
            default: return null;
        }
    }
    
    private String formatDate(Date date){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(GregorianCalendar.DAY_OF_MONTH)); sb.append("/");
        sb.append(calendar.get(GregorianCalendar.MONTH)); sb.append("/");
        sb.append(calendar.get(GregorianCalendar.YEAR));
        return sb.toString();
    }
    
    
    
    protected Object getValueClassProduct(int row, int column){
        DataRegisterSell data = (DataRegisterSell) list.get(row);
        switch(column){
            case 0: return data.producto.getNombre();
            case 1: return data.cantidad;
            case 2: return data.producto.getPrecioDeVenta();
            case 3: return (float) data.cantidad * (float) data.producto.getPrecioDeVenta();
            default: return null;
        }
    }
    
    protected Object getValueClassOrderProduction(int row, int column){
        DataOrderProduction data = (DataOrderProduction) list.get(row);
        switch(column){
            case 0: return data.getOrdenProduccion().getIdProducto().getNombre();
            case 1: return data.getOrdenProduccion().getCantidad();
            case 2: return data.getOrdenProduccion().getFechaPrevista();
            case 3: return data.getOrdenProduccion().getEstado();
            default: return null;
        }
    }
    
    protected Object getValueClassProductInventory(int row, int column){
        DataProductInventory data = (DataProductInventory) list.get(row);
        switch(column){
            case 0: return data.getInventario().getProducto().getNombre();
            case 1: return data.getInventario().getCantidad();
            case 2: return data.getInventario().getProducto().getPrecioDeVenta();
            case 3: return data.getInventario().getCantidadMax();
            case 4: return data.getInventario().getCantidadMin();
            case 5: return data.getInventario().getLoteList().size();
            case 6: return data.getInventario().getVentaList().size();
            default: return null;
        }
    } 
    
    
    
//    --------------------------------------------------------------------------------------------
  
    public static class DataSalesMoreProduct{
        private Vendedor vendedor;
        private Producto producto;

        public DataSalesMoreProduct(Vendedor vendedor, Producto producto) {
            this.vendedor = vendedor;
            this.producto = producto;
        }

        public Vendedor getVendedor() {
            return vendedor;
        }

        public void setVendedor(Vendedor vendedor) {
            this.vendedor = vendedor;
        }

        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
        }
        
        
    }
    
    public static class DataOrderProduction{
        private OrdenProduccion ordenProduccion;

        public DataOrderProduction(OrdenProduccion ordenProduccion) {
            this.ordenProduccion = ordenProduccion;
        }

        public OrdenProduccion getOrdenProduccion() {
            return ordenProduccion;
        }

        public void setOrdenProduccion(OrdenProduccion ordenProduccion) {
            this.ordenProduccion = ordenProduccion;
        }

        @Override
        public String toString() {
            return  ordenProduccion.getIdProducto().getNombre();
        }
        
        
        
    }
    
    public static class DataRegisterSell{
        
        private Producto producto;
        private Integer cantidad;

        public DataRegisterSell(Producto producto, Integer cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }

        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        @Override
        public String toString() {
            return producto.getNombre();
        }
        
        
    }
    
    public static class DataProductInventory{
        
        private Inventario inventario;

        public DataProductInventory(Inventario inventario) {
            this.inventario = inventario;
        }

        public Inventario getInventario() {
            return inventario;
        }

        public void setInventario(Inventario inventario) {
            this.inventario = inventario;
        }
        
    }
    
    public static class DataBaker{
        
        private OrdenProduccion ordenDeProduccion;
        private Lote loteProducido;

        public DataBaker() {
            ordenDeProduccion = new OrdenProduccion();
            loteProducido = new Lote();
        }

        public DataBaker(OrdenProduccion ordenDeProduccion, Lote loteProducido) {
            this.ordenDeProduccion = ordenDeProduccion;
            this.loteProducido = loteProducido;
        }

        public OrdenProduccion getOrdenDeProduccion() {
            return ordenDeProduccion;
        }

        public void setOrdenDeProduccion(OrdenProduccion ordenDeProduccion) {
            this.ordenDeProduccion = ordenDeProduccion;
        }

        public Lote getLoteProducido() {
            return loteProducido;
        }

        public void setLoteProducido(Lote loteProducido) {
            this.loteProducido = loteProducido;
        }

        public void updateCantidadAProducir(int cantidad){
            ordenDeProduccion.setCantidad(ordenDeProduccion.getCantidad() + cantidad);
        }
        
        public void updateCantidadProducida(int cantidad){
            loteProducido.setCantidadProducida(loteProducido.getCantidadProducida() + cantidad);
        }
        
    }
}
