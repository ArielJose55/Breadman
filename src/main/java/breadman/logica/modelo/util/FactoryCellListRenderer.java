/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.util;

public class FactoryCellListRenderer {
    
    public static javax.swing.DefaultListCellRenderer createListCellRenderer(){
        return new RowCellListRendererTwo();
    }
    
    public static javax.swing.DefaultListCellRenderer createListCellRendererComboBox(){
        return new RowCellListRendererOne();
    }
    
    private static class RowCellListRendererOne extends javax.swing.DefaultListCellRenderer{

        @Override
        public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(130,130,130)));
            setFont(new java.awt.Font("Arial", 0, 18));
            setBackground(java.awt.Color.WHITE);
            setPreferredSize(new java.awt.Dimension(35, 35));
            if(isSelected){
                setFont(new java.awt.Font("Arial", 1, 18));
                setBackground(new java.awt.Color(204,210,255));
                setForeground(list.getSelectionForeground());
            }else{
                setForeground(new java.awt.Color(130,130,130));
                setFont(new java.awt.Font("Arial", 0, 18));
                
            }
            return this;
        }
        
    }
    
    private static class RowCellListRendererTwo extends javax.swing.DefaultListCellRenderer{

        @Override
        public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setBorder(javax.swing.BorderFactory.createEmptyBorder());
            setFont(new java.awt.Font("Arial", 0, 18));
            setBackground(java.awt.Color.WHITE);
            setPreferredSize(new java.awt.Dimension(35, 35));
            if(isSelected){
                setFont(new java.awt.Font("Arial", 1, 18));
                setBackground(new java.awt.Color(204,210,255));
                setForeground(list.getSelectionForeground());
            }else{
                setForeground(new java.awt.Color(130,130,130));
                setFont(new java.awt.Font("Arial", 0, 18));
                
            }
            return this;
        }
        
    }
}
