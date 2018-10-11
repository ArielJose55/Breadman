/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman;

import breadman.vista.admin.FrameAdmin;
import breadman.vista.login.DialogLogin;
import breadman.vista.produccion.FrameProduccion;
import breadman.vista.venta.FrameVenta;


/**
 *
 * @author Ariel Arnedo
 */
public class Breadman {

    
    
    public static void main(String[] args) {
        initSkin();
        new DialogLogin().setVisible(true);
    }
    
    public static void throwThread(final Class ventana){
        java.awt.EventQueue.invokeLater(() -> {
            switch (ventana.getSimpleName()) {
                case "FrameAdmin":
                    try {
                        FrameAdmin mainFrame = (FrameAdmin) ventana.newInstance();
                        mainFrame.setVisible(true);
                    } catch (InstantiationException | IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(Breadman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }   break;
                case "FrameProduccion":
                    try {
                        FrameProduccion mainFrame = (FrameProduccion) ventana.newInstance();
                        mainFrame.setVisible(true);
                    } catch (InstantiationException | IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(Breadman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }   break;
                case "FrameVenta":
                    try {
                        FrameVenta mainFrame = (FrameVenta) ventana.newInstance();
                        mainFrame.setVisible(true);
                    } catch (InstantiationException | IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(Breadman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }   break;
                default:
                    break;
            }
        });
    }
    
    private static void initSkin(){
        javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
      
        javax.swing.JDialog.setDefaultLookAndFeelDecorated(true);
        
        org.jvnet.substance.SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.BusinessBlueSteelSkin"); // 
        org.jvnet.substance.SubstanceLookAndFeel.setCurrentTheme("org.jvnet.substance.theme.SubstanceSteelBlueTheme"); // Tema 
       
       
        org.jvnet.substance.SubstanceLookAndFeel.setCurrentBorderPainter("org.jvnet.substance.border.NullBorderPainter");
        org.jvnet.substance.SubstanceLookAndFeel.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceNoneWatermark");
    }
}
