/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.vista;

import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Ariel Arnedo
 */
public class MainPanel extends javax.swing.JPanel{

    private javax.swing.JPanel panelDeTrabajo;
    
    public MainPanel() {
        super();
    }

    public MainPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public MainPanel(LayoutManager layout) {
        super(layout);
    }

    public MainPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }
    
     protected javax.swing.JButton createButton(){
        javax.swing.JButton boton = new javax.swing.JButton();
        boton.setFocusPainted(false);
        boton.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        boton.setBorder(SwingAttribute.BORDER_MAIN);
        boton.setForeground(SwingAttribute.COLOR_FOREGROUND);
        boton.setFont(SwingAttribute.FONT_MAIN);
        return boton;
    }
    
    protected javax.swing.JButton createButtonSecondary(){
        javax.swing.JButton boton = new javax.swing.JButton();
        boton.setFocusPainted(false);
        boton.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        boton.setBorder(SwingAttribute.BORDER_MAIN);
        boton.setForeground(SwingAttribute.COLOR_FOREGROUND);
        boton.setFont(new java.awt.Font("Arial", 0, 15));
        return boton;
    }
    
    protected javax.swing.JButton createButtonMenu(){
        javax.swing.JButton boton = createButtonSecondary();
        boton.setBorder(SwingAttribute.BORDER_MENU_BUTTON);
        return boton;
    }
    
    protected javax.swing.JTextField createTextField(){
        javax.swing.JTextField textField = new JTextField();
        textField.setBorder(SwingAttribute.BORDER_MAIN);
        textField.setFont(new java.awt.Font("Arial", 1, 13));
        return textField;
    }
    
    protected javax.swing.JTextField createTextFieldNumeric(){
        javax.swing.JTextField textField = createTextField();
        textField.setDocument(new PlainDocumentCustom());
        return textField;
    }
    
    
    protected javax.swing.JLabel createLabelPrimary(){
        javax.swing.JLabel label = new JLabel();
        label.setForeground(SwingAttribute.COLOR_FOREGROUND_LABEL);
        label.setFont(SwingAttribute.FONT_LABEL_PRIMARY);
        
        return label;        
    }
    
     protected javax.swing.JLabel createLabel(){
        javax.swing.JLabel label = new JLabel();
        label.setFont(SwingAttribute.FONT_LABEL_PRIMARY);
        return label;        
    }
    
    protected javax.swing.JLabel createLabelSecundary(){
        javax.swing.JLabel label = createLabelPrimary();
        label.setFont(SwingAttribute.FONT_LABEL_SECUNDARY);
        return label;        
    }
    
    protected boolean isFieldEmply(javax.swing.JTextField textField){
        return textField.getText() == null || textField.getText().compareTo("") == 0;
    }
    
    public void setPanelWorking(javax.swing.JPanel panelWork){
        this.panelDeTrabajo = panelWork;
    }
    
    public javax.swing.JPanel getPanelWorking(){
        return panelDeTrabajo;
    }
    
    public void changePanel(javax.swing.JPanel panel){
        panelDeTrabajo.removeAll();
        panelDeTrabajo.add(panel, java.awt.BorderLayout.CENTER);
        panelDeTrabajo.updateUI();
    }
    
    private class PlainDocumentCustom extends javax.swing.text.PlainDocument{

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            char[] fuente = str.toCharArray();
            char[] resultado = new char[fuente.length];
            int j = 0;

            for(int i = 0; i < fuente.length; i++){
                if(fuente[i] >= '0' && fuente[i] <= '9'){
                    resultado[j++] = fuente[i];
                }else{
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }
            super.insertString(offs, new String(resultado, 0, j), a);
        }
        
    }
}
