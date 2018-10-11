/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadman.logica.modelo.util.graficas;

import breadman.logica.modelo.entidades.Panadero;
import breadman.logica.modelo.entidades.Proveedor;
import breadman.logica.modelo.entidades.Vendedor;
import breadman.vista.SwingAttribute;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.CenterTextMode;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleInsets;


/**
 *
 * @author Ariel Arnedo
 */
public class FactoryChart {
    
    public static org.jfree.chart.JFreeChart createPanelSerieTimeChart(TipoGrafica tipoGrafica){
        
        switch(tipoGrafica){
            case VENTAS_POR_TIEMPO_AND_VENDEDOR:{
                org.jfree.chart.JFreeChart chart =
                        org.jfree.chart.ChartFactory
                                .createTimeSeriesChart("Ventas por vendedor",
                                        null, "Cantidad de Ventas", ChartDataModel.createSeriesTimeDataSet(tipoGrafica), true, true, false);
                return addAtributeSerieTime(chart);
            }
            case VENTAS_POR_TIEMPO:{
                org.jfree.chart.JFreeChart chart =
                        org.jfree.chart.ChartFactory
                                .createTimeSeriesChart("Flujo de Ventas",
                                        null, "Ventas en $", ChartDataModel.createSeriesTimeDataSet(tipoGrafica), true, true, false);
                return addAtributeSerieTime(chart);
            }
            
        }
        return null;
    }
    
    public static org.jfree.chart.JFreeChart createPanelSerieTimeChart(Object object, TipoGrafica tipoGrafica){
        
        switch(tipoGrafica){
            case PRODUCCION_DIARIA_POR_PANADERO:{
                org.jfree.chart.JFreeChart chart =
                        org.jfree.chart.ChartFactory
                                .createTimeSeriesChart("Días de la semana con mas producciones por panadero",
                                        null, "Ventas en $", ChartDataModel.createSeriesTimeDataSet(object,tipoGrafica), true, true, false);
                return addAtributeSerieTime(chart);
            }
        }
        
        return null;
    }
    
    public static org.jfree.chart.JFreeChart createPanelChartBar(TipoGrafica tipoGrafica){
        
        switch(tipoGrafica){
            case VENTAS_POR_DIA:{
               org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createBarChart3D("Cantidad de Ventas por Día", "Día de la semana", "Ventas", ChartDataModel.createBarDataSet(tipoGrafica),
                    org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);
               
               return addAttributeChartBar(chart);
            }
            case PRODUCTOS_MAS_VENDIDOS:{
                org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createBarChart3D("Productos más Vendidos", "Producto", "Ventas", ChartDataModel.createBarDataSet(tipoGrafica),
                    org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);
                
               return addAttributeChartBar(chart);
            }
            case VENTAS_POR_VENDEDOR:{
                org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createBarChart3D("Ventas por Vendedor", "Vendedor", "Ventas", ChartDataModel.createBarDataSet(tipoGrafica),
                    org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);
                
                return addAttributeChartBar(chart);
            }
            
            case ORDEN_AND_LOTE_POR_PANADERO:{
                org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createBarChart3D("Ordenes de produccion y lotes producidos por Panadero"
                        , "Panadero", "Cantidad", ChartDataModel.createBarDataSet(tipoGrafica),
                            org.jfree.chart.plot.PlotOrientation.VERTICAL, true, true, false);
                
                return addAttributeChartBar(chart);
            }
            case ORDENES_PRODUCCION_ESTADO:{
                org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createBarChart3D("Ordenes de produccion vencidas, pendientes y actuales por panadero"
                        , "Panadero", "Cantidad de ordenes", ChartDataModel.createBarDataSet(tipoGrafica),
                            org.jfree.chart.plot.PlotOrientation.VERTICAL, true, true, false);
                
                return addAttributeChartBar(chart);
            }
            
            case COMPRA_INSUMO_POR_DIA:{
                org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createBarChart3D("Días de la semana en donde se compran más insumos"
                        , "Día de la semana", "Cantidad de insumos comprados", ChartDataModel.createBarDataSet(tipoGrafica),
                            org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);
                
                return addAttributeChartBar(chart);
            }
        }
        return null;
    }
    
    
    public static org.jfree.chart.JFreeChart createPanelChartPie(TipoGrafica tipoGrafica){
         
        switch(tipoGrafica){
            case PRODUCTOS_MAS_VENDIDOS:{
                 org.jfree.chart.JFreeChart chart = 
                         org.jfree.chart.ChartFactory.createPieChart3D("Productos Mas Vendidos", ChartDataModel.createPieDataSet(tipoGrafica), false, true, false);
                return addAtributeChartPie(chart);
            }
            case PRODUCTOS_MAS_PRODUCIDOS:{
                 org.jfree.chart.JFreeChart chart = 
                         org.jfree.chart.ChartFactory.createPieChart3D("Productos Mas Producidos", ChartDataModel.createPieDataSet(tipoGrafica), false, true, false);
                return addAtributeChartPie(chart);
            }
            case MAYOR_PROVEEDOR:{
                 org.jfree.chart.JFreeChart chart = 
                         org.jfree.chart.ChartFactory.createPieChart3D("Mayores Proveedores", ChartDataModel.createPieDataSet(tipoGrafica), false, true, false);
                return addAtributeChartPie(chart);
            }
            case CAPACIDAD_DE_INVENTARIO:{
                
                RingPlot localRingPlot = new RingPlot(ChartDataModel.createPieDataSet(tipoGrafica));
                localRingPlot.setCenterTextMode(CenterTextMode.VALUE);
                localRingPlot.setCenterTextFont(new Font("SansSerif", 1, 24));
                localRingPlot.setCenterTextColor(Color.LIGHT_GRAY);
                localRingPlot.setCenterTextFormatter(new DecimalFormat("0.0%"));
                JFreeChart localJFreeChart = new JFreeChart("Capacidad actual del inventario", JFreeChart.DEFAULT_TITLE_FONT, localRingPlot, true);
                localJFreeChart.setBackgroundPaint(java.awt.Color.WHITE);
                TextTitle localTextTitle = localJFreeChart.getTitle();
                localTextTitle.setHorizontalAlignment(HorizontalAlignment.LEFT);
                localTextTitle.setPaint(new Color(80, 200, 200));
                localTextTitle.setFont(new Font("Arial", 1, 26));
                localRingPlot.setBackgroundPaint(null);
                localRingPlot.setOutlineVisible(true);
                localRingPlot.setOutlinePaint(new java.awt.Color(102, 204, 255));
                localRingPlot.setLabelGenerator(null);
                localRingPlot.setSectionPaint("Capacidad Actual", Color.ORANGE);
                localRingPlot.setSectionPaint("Capacidad Faltante", new Color(100, 100, 100));
                localRingPlot.setSectionDepth(0.05D);
                localRingPlot.setSectionOutlinesVisible(true);
                localRingPlot.setShadowPaint(null);
                return localJFreeChart;
            }
        }
        return null;
    }
    
     public static org.jfree.chart.JFreeChart createPanelChartBar(Object object,TipoGrafica tipoGrafica){
        switch(tipoGrafica){
            case ORDENES_DIARIAS_POR_PROVEEDOR:{
                
                org.jfree.chart.JFreeChart chart = 
                        org.jfree.chart.ChartFactory.createBarChart3D("Cantidad de compras por día del proveedor: "+((Proveedor)object).getNombre(),
                            "Día de la semana", "Compras de insumos",
                                ChartDataModel.createBarDataSet(object,tipoGrafica),
                                    org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);
               
               return addAttributeChartBar(chart);
            }
            case VENTAS_DIARIAS_POR_VENDEROR:{
                org.jfree.chart.JFreeChart chart = 
                        org.jfree.chart.ChartFactory.createBarChart3D("Cantidad de compras por día del vendedor: "+((Vendedor)object).getUsuario().getNombre(),
                            "Día de la semana", "Ventas",
                                ChartDataModel.createBarDataSet(object,tipoGrafica),
                                    org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);
               
               return addAttributeChartBar(chart);
            }
            
            case PRODUCCION_DIARIA_POR_PANADERO:{
                org.jfree.chart.JFreeChart chart = 
                        org.jfree.chart.ChartFactory.createBarChart("Días que mas lotes produce: "+((Panadero)object).getUsuario().getNombre(),
                            "Día de la semana", "Lotes producidos",
                                ChartDataModel.createBarDataSet(object,tipoGrafica),
                                    org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);
               
               return addAttributeChartBar(chart);
            }
            case PRODUCCION_PRODUCTO_DIARIA_POR_PANADERO:{
                org.jfree.chart.JFreeChart chart = 
                        org.jfree.chart.ChartFactory.createBarChart("Productos que más produce: "+((Panadero)object).getUsuario().getNombre(),
                            "Día de la semana", "Producto",
                                ChartDataModel.createBarDataSet(object,tipoGrafica),
                                    org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);
               
               return addAttributeChartBar(chart);
            }
        }
        return null;
     }
    
    
//    -------------------------------------------------------------------
    private static JFreeChart addAtributeChartPie(JFreeChart freeChart){
        org.jfree.chart.title.TextTitle title = freeChart.getTitle();
        title.setPaint(new java.awt.Color(102, 102, 102));
        org.jfree.chart.plot.PiePlot plot = (org.jfree.chart.plot.PiePlot) freeChart.getPlot();
        plot.setBackgroundPaint(java.awt.Color.WHITE);
        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);
        plot.setBackgroundAlpha(.5f);
        return freeChart;
    }
    
    private static JFreeChart addAtributeSerieTime(JFreeChart freeChart){
        org.jfree.chart.title.TextTitle title = freeChart.getTitle();
        title.setPaint(new java.awt.Color(102, 102, 102));
        org.jfree.chart.plot.XYPlot plot = (org.jfree.chart.plot.XYPlot) freeChart.getPlot();
        plot.setBackgroundPaint(java.awt.Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setBackgroundAlpha(.5f);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        PeriodAxis localPeriodAxis = new PeriodAxis("");
       
        PeriodAxisLabelInfo[] arrayOfPeriodAxisLabelInfo = new PeriodAxisLabelInfo[3];
        arrayOfPeriodAxisLabelInfo[0] = new PeriodAxisLabelInfo(Day.class, new SimpleDateFormat("d"));
        arrayOfPeriodAxisLabelInfo[1] = new PeriodAxisLabelInfo(Day.class, new SimpleDateFormat("E"), new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D), new Font("Arial", 1, 10), SwingAttribute.COLOR_FOREGROUND, false, new BasicStroke(0.0F), Color.lightGray);
        arrayOfPeriodAxisLabelInfo[2] = new PeriodAxisLabelInfo(Day.class, new SimpleDateFormat("Y"));
////        arrayOfPeriodAxisLabelInfo[3] = new PeriodAxisLabelInfo(Year.class, new SimpleDateFormat("YY"));
        localPeriodAxis.setLabelInfo(arrayOfPeriodAxisLabelInfo);
        plot.setDomainAxis(localPeriodAxis);
        XYItemRenderer localXYItemRenderer = plot.getRenderer();
        if ((localXYItemRenderer instanceof XYLineAndShapeRenderer)){
            XYLineAndShapeRenderer localXYLineAndShapeRenderer = (XYLineAndShapeRenderer)localXYItemRenderer;
            localXYLineAndShapeRenderer.setBaseShapesVisible(false);
            localXYLineAndShapeRenderer.setDrawSeriesLineAsPath(true);
            localXYLineAndShapeRenderer.setAutoPopulateSeriesStroke(false);
            localXYLineAndShapeRenderer.setBaseStroke(new BasicStroke(3.0F, 1, 2), false);
        }
        return freeChart;
    }
    
    private static JFreeChart addAttributeChartBar(JFreeChart freeChart){
       freeChart.getTitle().setPaint(new java.awt.Color(102, 102, 102));
       org.jfree.chart.plot.CategoryPlot plot = freeChart.getCategoryPlot();
       plot.setBackgroundPaint(java.awt.Color.WHITE);
       plot.setRangeGridlinePaint(freeChart.getTitle().getPaint());
       //plot.getRangeAxis().setRange(0, 25);
       plot.setBackgroundAlpha(.5f);
       plot.getRenderer().setBaseSeriesVisibleInLegend(true);
       return freeChart;
    }
}
