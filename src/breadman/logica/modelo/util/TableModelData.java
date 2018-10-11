/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.util;

import breadman.logica.modelo.core.State;

/**
 *
 * @author Ariel Arnedo
 */
public class TableModelData extends ModelData{

    public TableModelData(Class clase) {
        super(clase);
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(clase.getSimpleName()){
            case "DataRegisterSell": {
                switch(columnIndex){
                    case 0: return String.class;
                    case 1: return Integer.class;
                    case 2 | 3: return Float.class;
                    default: return String.class;
                }
            }
            case "DataOrderProduction": {
                switch(columnIndex){
                    case 0: return String.class;
                    case 1: return Integer.class;
                    case 2: return java.sql.Date.class;
                    default: return State.class;
                }
            }
            case "DataProductInventory": {
                switch(columnIndex){
                    case 0: return String.class;
                    case 1: return Float.class;
                    default: return Integer.class;
                }
            }
            case "OrdenDeCompraInsumo":{
                switch(columnIndex){
                    case 0: return String.class;
                    case 1: return Float.class;
                    default: return String.class;
                }
            }
            case "DataSalesMoreProduct":{
                switch(columnIndex){
                    case 0: return Integer.class;
                    case 1: return String.class;
                    default: return String.class;
                }
            }
            case "Insumo":{
                if(columnIndex == 1)
                    return Float.class;
                return String.class;
                   
            }
            default: return String.class;
        }
    }
       
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    @Override
    public int getRowCount() {
        return ( list == null || list.isEmpty()) ? 0 : list.size();
    }

    @Override
    public int getColumnCount() {
        switch(clase.getSimpleName()){
            case "DataRegisterSell": return 4;
            case "DataOrderProduction": return 4;
            case "DataProductInventory": return 7;
            case "OrdenDeCompraInsumo": return 4;
            case "DataSalesMoreProduct": return 3;
            case "Insumo": return 4;
            default: return 0;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(clase.getSimpleName()){
            case "DataRegisterSell": return getValueClassProduct(rowIndex, columnIndex);
            case "DataOrderProduction": return getValueClassOrderProduction(rowIndex, columnIndex);
            case "DataProductInventory": return getValueClassProductInventory(rowIndex, columnIndex);
            case "OrdenDeCompraInsumo": return getValueClassOrdenCompraInsumo(rowIndex, columnIndex);
            case "DataSalesMoreProduct": return getValueClassVendedorSales(rowIndex, columnIndex);
            case "Insumo": return getValueClassInsumo(rowIndex, columnIndex);
            default: return null;
        }
    }
}
