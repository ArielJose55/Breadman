/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.vista;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public abstract class MainDialog extends javax.swing.JDialog {
    
    protected MainDialog(){
        super(new javax.swing.JFrame(), true);
        setResizable(false);
        getContentPane().setBackground(SwingAttribute.COLOR_BACKGROUND);
    }
    
    protected MainDialog(String title){
        super(new javax.swing.JFrame(),title, true);
        setResizable(false);
        getContentPane().setBackground(SwingAttribute.COLOR_BACKGROUND);
    }
    
    protected MainDialog(boolean modal){
        super(new javax.swing.JFrame(), modal);
        getContentPane().setBackground(SwingAttribute.COLOR_BACKGROUND);
    }
    
    protected MainDialog(boolean modal, boolean redimencionable){
        super(new javax.swing.JFrame(), modal);
        setResizable(redimencionable);
        getContentPane().setBackground(SwingAttribute.COLOR_BACKGROUND);
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
    
    protected javax.swing.JTextField createTextField(){
        javax.swing.JTextField textField = new JTextField();
        textField.setBorder(SwingAttribute.BORDER_MAIN);
        textField.setFont(new java.awt.Font("Arial", 0, 13));
        return textField;
    }
    
    protected javax.swing.JTextField createTextFieldNumeric(){
        javax.swing.JTextField textField = createTextField();
        textField.setDocument(new PlainDocumentCustom());
        return textField;
    }
    
    protected javax.swing.JLabel createLabelSecundary(){
        javax.swing.JLabel label = new JLabel();
        label.setForeground(SwingAttribute.COLOR_FOREGROUND_LABEL);
        label.setFont(SwingAttribute.FONT_LABEL_SECUNDARY);
        return label;        
    }
    
    protected boolean isFieldEmply(javax.swing.JTextField textField){
        return textField.getText() == null || textField.getText().compareTo("") == 0;
    }
    
    protected void doCloseAndExit(){
        System.exit(0);
    }
    
    protected void doClose(){
        this.setVisible(false);
        this.dispose();
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
