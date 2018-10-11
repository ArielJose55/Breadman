/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.vista.admin.paneles.panadero;

import breadman.logica.modelo.entidades.Panadero;
import breadman.logica.modelo.util.TipoDato;
import breadman.logica.modelo.util.ComboBoxModelo;
import breadman.logica.modelo.util.FactoryCellListRenderer;
import breadman.logica.modelo.util.graficas.FactoryChart;
import breadman.logica.modelo.util.graficas.TipoGrafica;
import breadman.vista.MainPanel;
import breadman.vista.SwingAttribute;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author Ariel Arnedo
 */
public class PanelAdminPanadero extends MainPanel {
    
    /**
     * Creates new form PanelAdminPanadero
     */
    public PanelAdminPanadero() {
        initComponents();
        updatePanels();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelPanderoTable = new javax.swing.JPanel();
        comboListPanadero = new javax.swing.JComboBox<>();
        panelChartLoteDia = new javax.swing.JPanel();
        panelChartlProductoDia = new javax.swing.JPanel();
        panelPanaderoChart = new javax.swing.JPanel();
        panelChartOrdenAndSolicitud = new javax.swing.JPanel();
        panelEstadoOrdenes = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        panelPanderoTable.setBorder(SwingAttribute.BORDER_SECUNDARY);
        panelPanderoTable.setPreferredSize(new java.awt.Dimension(0, 0));
        panelPanderoTable.setLayout(new java.awt.GridBagLayout());

        comboListPanadero.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        comboListPanadero.setModel(new ComboBoxModelo(TipoDato.PANADEROS));
        comboListPanadero.setSelectedIndex(0);
        comboListPanadero.setPreferredSize(new java.awt.Dimension(0, 0));
        comboListPanadero.setRenderer(FactoryCellListRenderer.createListCellRenderer());
        comboListPanadero.setVerifyInputWhenFocusTarget(false);
        comboListPanadero.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboListPanaderoItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
        panelPanderoTable.add(comboListPanadero, gridBagConstraints);

        panelChartLoteDia.setPreferredSize(new java.awt.Dimension(0, 0));
        panelChartLoteDia.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panelPanderoTable.add(panelChartLoteDia, gridBagConstraints);

        panelChartlProductoDia.setPreferredSize(new java.awt.Dimension(0, 0));
        panelChartlProductoDia.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panelPanderoTable.add(panelChartlProductoDia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 5);
        add(panelPanderoTable, gridBagConstraints);

        panelPanaderoChart.setBorder(SwingAttribute.BORDER_SECUNDARY);
        panelPanaderoChart.setPreferredSize(new java.awt.Dimension(0, 0));
        panelPanaderoChart.setLayout(new java.awt.GridBagLayout());

        panelChartOrdenAndSolicitud.setPreferredSize(new java.awt.Dimension(0, 0));
        panelChartOrdenAndSolicitud.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        panelPanaderoChart.add(panelChartOrdenAndSolicitud, gridBagConstraints);

        panelEstadoOrdenes.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        panelPanaderoChart.add(panelEstadoOrdenes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 10);
        add(panelPanaderoChart, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void comboListPanaderoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboListPanaderoItemStateChanged
        if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED){
            Panadero panadero = (Panadero) comboListPanadero.getSelectedItem();
            panelChartLoteDia.add(new ChartPanel(FactoryChart.createPanelChartBar(panadero,TipoGrafica.PRODUCCION_DIARIA_POR_PANADERO)));
            panelChartlProductoDia.add(new ChartPanel(FactoryChart.createPanelChartBar(panadero,TipoGrafica.PRODUCCION_PRODUCTO_DIARIA_POR_PANADERO)));
        }
    }//GEN-LAST:event_comboListPanaderoItemStateChanged

    private void updatePanels(){
        panelChartOrdenAndSolicitud.add(new ChartPanel(FactoryChart.createPanelChartBar(TipoGrafica.ORDEN_AND_LOTE_POR_PANADERO)));
        panelEstadoOrdenes.add(new ChartPanel(FactoryChart.createPanelChartBar(TipoGrafica.ORDENES_PRODUCCION_ESTADO)));
        
        if(comboListPanadero.getModel().getSize() != 0){
            ComboBoxModelo comboBoxModelo = (ComboBoxModelo) comboListPanadero.getModel();
            Panadero panadero = (Panadero) comboBoxModelo.getElementAt(0);
            panelChartLoteDia.add(new ChartPanel(FactoryChart.createPanelChartBar(panadero,TipoGrafica.PRODUCCION_DIARIA_POR_PANADERO)));
            panelChartlProductoDia.add(new ChartPanel(FactoryChart.createPanelChartBar(panadero,TipoGrafica.PRODUCCION_PRODUCTO_DIARIA_POR_PANADERO)));
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboListPanadero;
    private javax.swing.JPanel panelChartLoteDia;
    private javax.swing.JPanel panelChartOrdenAndSolicitud;
    private javax.swing.JPanel panelChartlProductoDia;
    private javax.swing.JPanel panelEstadoOrdenes;
    private javax.swing.JPanel panelPanaderoChart;
    private javax.swing.JPanel panelPanderoTable;
    // End of variables declaration//GEN-END:variables
}
