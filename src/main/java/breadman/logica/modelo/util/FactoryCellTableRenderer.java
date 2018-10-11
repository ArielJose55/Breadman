/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package breadman.logica.modelo.util;

import breadman.vista.SwingAttribute;

/**
 *
 * @author Klac
 */
class FactoryCellTableRenderer {
    
    
    static javax.swing.table.DefaultTableCellRenderer createTableCellRenderer(int columna,TipoTabla typeTable){
        switch(typeTable){
            case TABLE_VIEW_ORDER_PRODUCTION:{
                return new RowRendererOfClassOne(columna);
            }
            case TABLE_VIEW_PRODUCT_SELL:{
                 return new RowRendererOfClassTwo(columna);
            }
            case TABLE_VIEW_PRODUCT_MORE:{
                return new RowRendererOfClassThree(columna);
            }
            case TABLE_ORDER_SALES_INSUMO:{
                return new RowRendererOfClassThree(columna);
            }
            case TABLE_INSUMO:{
                return new RowRendererOfClassFour(columna);
            }
            default:
                throw new RuntimeException("No corresponde a ningun tipo de tabla");
        }
    }
    
    
    private static class RowRendererOfClassOne extends javax.swing.table.DefaultTableCellRenderer{
    
    private final int columna;

        RowRendererOfClassOne(int columna) {
            this.columna = columna;
        }
        
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
            setBackground(java.awt.Color.WHITE);

            if(columna < 0){
                setPreferredSize(new java.awt.Dimension(12, 20));
                setFont(new java.awt.Font("Arial", 1, 15));
                setForeground(new java.awt.Color(120,120,120));
                setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                return this;
            }
          
            if(column >= 1 && column <= 3 && column != 1)
            setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            
            if(isSelected){
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }else{
                setForeground(new java.awt.Color(130,130,130));
                
            }
            setFont(new java.awt.Font("Arial", 0, 13));
            return this;
        }
    }
    
    private static class RowRendererOfClassTwo extends javax.swing.table.DefaultTableCellRenderer{
    
    private final int columna;

        RowRendererOfClassTwo(int columna) {
            this.columna = columna;
        }
        
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
            setBackground(java.awt.Color.WHITE);

            if(columna < 0){
                setPreferredSize(new java.awt.Dimension(12, 20));
                setFont(new java.awt.Font("Arial", 1, 15));
                setForeground(new java.awt.Color(120,120,120));
                return this;
            }
          
            if(column >= 1 && column <= 3)
            setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            
            if(isSelected){
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }else{
                setForeground(new java.awt.Color(130,130,130));
                
            }
            setFont(new java.awt.Font("Arial", 0, 13));
            return this;
        }
    }

    private static class RowRendererOfClassThree extends javax.swing.table.DefaultTableCellRenderer{
    
        private final int columna;

        RowRendererOfClassThree(int columna) {
            this.columna = columna;
        }
        
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            setBorder(SwingAttribute.BORDER_SECUNDARY);
            setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
            setBackground(java.awt.Color.WHITE);
            setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            if(columna < 0){
                setPreferredSize(new java.awt.Dimension(12, 40));
                setFont(new java.awt.Font("Arial", 1, 17));
                setForeground(new java.awt.Color(120,120,120));
                return this;
            }
          
            if(isSelected){
                setBackground(new java.awt.Color(228,240,255));
                setForeground(table.getSelectionForeground());
            }else{
                setForeground(new java.awt.Color(130,130,130));
                
            }
            
            setFont(new java.awt.Font("Arial", 0, 17));
            
            return this;
        }
    }
    
     private static class RowRendererOfClassFour extends javax.swing.table.DefaultTableCellRenderer{
    
        private final int columna;

        RowRendererOfClassFour(int columna) {
            this.columna = columna;
        }
        
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            setBorder(SwingAttribute.BORDER_SECUNDARY);
            setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
            setBackground(java.awt.Color.WHITE);
            
            if(columna < 0){
                setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                setPreferredSize(new java.awt.Dimension(12, 40));
                setFont(new java.awt.Font("Arial", 1, 17));
                setForeground(new java.awt.Color(120,120,120));
                return this;
            }
          
            if(columna == 1)
                setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            else
                setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            
            if(isSelected){
                setBackground(new java.awt.Color(228,240,255));
                setForeground(table.getSelectionForeground());
            }else{
                setForeground(new java.awt.Color(130,130,130));
                
            }
            
            setFont(new java.awt.Font("Arial", 0, 17));
            
            return this;
        }
    }
}
