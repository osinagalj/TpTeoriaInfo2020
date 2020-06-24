package Ejercicios;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Ejercicios.getColor.getGris;
public class Ejercicio_2 {
    public Ejercicio_2(){
    }


    public void media_desvio(BufferedImage img, int[] distribuciones, int n, double [] parametros){
        double suma = 0;
        double sumaXiCuadrado = 0;
        int Xi;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                Xi= (int)getGris(img,x,y);
                distribuciones[Xi] ++;
                suma+= Xi;
                sumaXiCuadrado += Math.pow(Xi,2);
            }
        }
        double media = suma/n;
        double desvio = Math.sqrt(sumaXiCuadrado/n -  Math.pow(suma/n, 2));
        parametros[0] = media;
        parametros[1] = desvio;
    }

    public void generarHistograma(int[]distribuciones,String titulo) throws IOException {

        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        CategoryAxis categoryAxis = new CategoryAxis("Tonalidades");
        categoryAxis.setCategoryMargin(.1);
        ValueAxis valueAxis = new NumberAxis("Frecuencias");
        StackedBarRenderer renderer = new StackedBarRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        CategoryPlot plot = new CategoryPlot( ds,
                categoryAxis,
                valueAxis,
                renderer);
        plot.setOrientation(PlotOrientation.VERTICAL);

        JFreeChart jf = new JFreeChart( titulo,
                JFreeChart.DEFAULT_TITLE_FONT,
                plot,
                true);

        int j = 0;
        for(int i = 0; i<256; i++ ){
            if(distribuciones[i] != 0){
                ds.addValue(distribuciones[i],String.valueOf(i),String.valueOf(i));
                plot.getRenderer().setSeriesPaint(j, new Color(i, i, i));
                j++;
            }
        }
        jf.setBackgroundPaint(new Color(42, 112, 182));
        jf.getPlot().setBackgroundPaint( new Color(42, 112, 182));

        ChartFrame f = new ChartFrame("Histograma",jf);
        f.setSize(1200,600);
        f.setLocationRelativeTo(null);
        //Histogram
        ChartUtilities.saveChartAsPNG(new File("src\\Salidas\\Ejercicio2\\Histograma-"+titulo+".png"), jf, 600, 300 );
    }
}
