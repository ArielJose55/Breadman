/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package breadman.logica.modelo.util;

/**
 *
 * @author Klac
 */
public class FactoryColumnModel {
    
    public static javax.swing.table.TableColumnModel createModelColumnTableProducts(){
        
        javax.swing.table.DefaultTableColumnModel tablaColumn = new  javax.swing.table.DefaultTableColumnModel();
        tablaColumn.setColumnMargin(0);
        int ancho[] ={50,30,50,30};
        String cabeceras[] = {"Producto","Cantidad","Precio Unitario","Total"};
        for(int i = 0; i < 4 ; i++){
                javax.swing.table.TableColumn columna = new javax.swing.table.TableColumn(i,ancho[i]);
                columna.setHeaderValue(cabeceras[i]);
                javax.swing.table.DefaultTableCellRenderer rowRenderer = FactoryCellTableRenderer.createTableCellRenderer(-1, TipoTabla.TABLE_VIEW_PRODUCT_SELL);
                columna.setHeaderRenderer(rowRenderer);
                rowRenderer = FactoryCellTableRenderer.createTableCellRenderer(i, TipoTabla.TABLE_VIEW_PRODUCT_SELL);
                columna.setCellRenderer(rowRenderer);
                tablaColumn.addColumn(columna);
        }
        return tablaColumn;
    }
    
    public static javax.swing.table.TableColumnModel createModelColumnTableOrdenCompraInsumo(){
        
        javax.swing.table.DefaultTableColumnModel tablaColumn = new  javax.swing.table.DefaultTableColumnModel();
        tablaColumn.setColumnMargin(0);
        int ancho[] ={50,30,50,30};
        String cabeceras[] = {"Insumo","Cantidad comprada","Unidad de medición","Fecha de compra"};
        for(int i = 0; i < 4 ; i++){
                javax.swing.table.TableColumn columna = new javax.swing.table.TableColumn(i,ancho[i]);
                columna.setHeaderValue(cabeceras[i]);
                javax.swing.table.DefaultTableCellRenderer rowRenderer = FactoryCellTableRenderer.createTableCellRenderer(-1, TipoTabla.TABLE_ORDER_SALES_INSUMO);
                columna.setHeaderRenderer(rowRenderer);
                rowRenderer = FactoryCellTableRenderer.createTableCellRenderer(i, TipoTabla.TABLE_ORDER_SALES_INSUMO);
                columna.setCellRenderer(rowRenderer);
                tablaColumn.addColumn(columna);
        }
        return tablaColumn;
    }
    
    public static javax.swing.table.TableColumnModel createModelColumnTableOrdenProduction(){
        
        javax.swing.table.DefaultTableColumnModel tablaColumn = new  javax.swing.table.DefaultTableColumnModel();
        tablaColumn.setColumnMargin(0);
        int ancho[] ={50,30,50,30};
        String cabeceras[] = {"Producto","Cantidad","Fecha Prevista","Estado"};
        for(int i = 0; i < 4 ; i++){
                javax.swing.table.TableColumn columna = new javax.swing.table.TableColumn(i,ancho[i]);
                columna.setHeaderValue(cabeceras[i]);
                javax.swing.table.DefaultTableCellRenderer rowRenderer = FactoryCellTableRenderer.createTableCellRenderer(-1, TipoTabla.TABLE_VIEW_ORDER_PRODUCTION);
                columna.setHeaderRenderer(rowRenderer);
                rowRenderer = FactoryCellTableRenderer.createTableCellRenderer(i, TipoTabla.TABLE_VIEW_ORDER_PRODUCTION);
                columna.setCellRenderer(rowRenderer);
                tablaColumn.addColumn(columna);
        }
        return tablaColumn;
    }
    
    public static javax.swing.table.TableColumnModel createModelColumnTableProductInventory(){
        
        javax.swing.table.DefaultTableColumnModel tablaColumn = new  javax.swing.table.DefaultTableColumnModel();
        tablaColumn.setColumnMargin(0);
        int ancho[] ={20,10,10,10,10,60,60};
        String cabeceras[] = {"Producto","Cantidad","Precio","Maxima","Minima","Lotes Producidos","Numero de Ventas"};
        for(int i = 0; i < cabeceras.length ; i++){
                javax.swing.table.TableColumn columna = new javax.swing.table.TableColumn(i,ancho[i]);
                columna.setHeaderValue(cabeceras[i]);
                javax.swing.table.DefaultTableCellRenderer rowRenderer = FactoryCellTableRenderer.createTableCellRenderer(-1, TipoTabla.TABLE_VIEW_PRODUCT_MORE);
                columna.setHeaderRenderer(rowRenderer);
                rowRenderer = FactoryCellTableRenderer.createTableCellRenderer(i, TipoTabla.TABLE_VIEW_PRODUCT_MORE);
                columna.setCellRenderer(rowRenderer);
                tablaColumn.addColumn(columna);
        }
        return tablaColumn;
    }
    
    public static javax.swing.table.TableColumnModel createModelColumnTableSales(){
        
        javax.swing.table.DefaultTableColumnModel tablaColumn = new  javax.swing.table.DefaultTableColumnModel();
        tablaColumn.setColumnMargin(0);
        int ancho[] ={10,30,30};
        String cabeceras[] = {"Cedula","Vendedor","Produto que más vendío"};
        for(int i = 0; i < cabeceras.length ; i++){
                javax.swing.table.TableColumn columna = new javax.swing.table.TableColumn(i,ancho[i]);
                columna.setHeaderValue(cabeceras[i]);
                columna.setPreferredWidth(ancho[i]);
                javax.swing.table.DefaultTableCellRenderer rowRenderer = FactoryCellTableRenderer.createTableCellRenderer(-1, TipoTabla.TABLE_VIEW_ORDER_PRODUCTION);
                columna.setHeaderRenderer(rowRenderer);
                rowRenderer = FactoryCellTableRenderer.createTableCellRenderer(i, TipoTabla.TABLE_VIEW_ORDER_PRODUCTION);
                columna.setCellRenderer(rowRenderer);
                tablaColumn.addColumn(columna);
        }
        return tablaColumn;
    }
    
    public static javax.swing.table.TableColumnModel createModelColumnTableInsumo(){
        
        javax.swing.table.DefaultTableColumnModel tablaColumn = new  javax.swing.table.DefaultTableColumnModel();
        tablaColumn.setColumnMargin(0);
        int ancho[] ={10,30,30, 30};
        String cabeceras[] = {"Insumo","Cantidad Almacenada","Unidad de Medida","Proveedor"};
        for(int i = 0; i < cabeceras.length ; i++){
                javax.swing.table.TableColumn columna = new javax.swing.table.TableColumn(i,ancho[i]);
                columna.setHeaderValue(cabeceras[i]);
                columna.setPreferredWidth(ancho[i]);
                javax.swing.table.DefaultTableCellRenderer rowRenderer = FactoryCellTableRenderer.createTableCellRenderer(-1, TipoTabla.TABLE_INSUMO);
                columna.setHeaderRenderer(rowRenderer);
                rowRenderer = FactoryCellTableRenderer.createTableCellRenderer(i, TipoTabla.TABLE_INSUMO);
                columna.setCellRenderer(rowRenderer);
                tablaColumn.addColumn(columna);
        }
        return tablaColumn;
    }
}
