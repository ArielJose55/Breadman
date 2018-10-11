/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.vista;

public interface SwingAttribute {
    public java.awt.Font FONT_MAIN = new java.awt.Font("Arial", 1, 17);
    public java.awt.Font FONT_LABEL_PRIMARY = new java.awt.Font("Arial", 1, 13);
    public java.awt.Font FONT_LABEL_SECUNDARY = new java.awt.Font("Arial", 0, 13);
    public java.awt.Color COLOR_BACKGROUND = java.awt.Color.WHITE;
    public java.awt.Color COLOR_FOREGROUND = new java.awt.Color(102, 102, 102);
    public java.awt.Color COLOR_FOREGROUND_PANEL_WEST = new java.awt.Color(204,211,230);
    public java.awt.Color COLOR_FOREGROUND_LABEL = new java.awt.Color(80, 80, 80);
    public javax.swing.border.Border BORDER_MENU_BUTTON = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 255), 2);
    public javax.swing.border.Border BORDER_MAIN = javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(152, 204, 255), new java.awt.Color(152, 204, 255));
    public javax.swing.border.Border BORDER_SECUNDARY = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 255), 1);
}

