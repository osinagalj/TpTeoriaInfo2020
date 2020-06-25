package Ejercicios;

import Estructuras.Arbol;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;


import static Ejercicios.getColor.getGris;


public class Ejercicio_4 {

    private double epsilon = 0.00000001d;

//-------------------------------------------------------------------------------------------------------//
//--------------------------------------    A     -------------------------------------------------------//
//-------------------------------------------------------------------------------------------------------//

    public void inicializarMarginal(double[] marginal){
        for(int x = 0; x < marginal.length; x++)
            marginal[x] = 0;
    }

public double[][] calcularMatriz(BufferedImage entrada, BufferedImage salida,double[] marginal){
        //Calculamos la matriz condicional
    double[][] rtrn = new double[256][256];

    //Inicializamos en 0
    for (int x=0; x < rtrn.length; x++) {
        for (int y=0; y < rtrn[x].length; y++) {
            rtrn[x][y] = 0;
        }
    }

    //Sumamos 1 cada vez que aparece aparece un x/y
    for (int x=0; x < entrada.getWidth(); x++) {
        for (int y=0; y < entrada.getHeight(); y++) {
            int in = getGris(entrada,x,y);   //get color entrada en x,y
            int out = getGris(salida,x,y);   //get color salida en x,y
            rtrn[out][in]++;
            marginal[in]++;
        }
    }

    for (int x = 0; x < 256; x++) {
        for (int y = 0; y < 256; y++) {  //Para cada espacio de la matriz
            if(marginal[y]!=0){
                rtrn[x][y] = (rtrn[x][y])/(marginal[y]); //divido por el total de esa entrada
            }
        }
    }
    return rtrn;
}

    public void crearCSV(double[][] matriz,double[] marginal,String path) throws FileNotFoundException {
        //Crea el .CSV para las matrices obtenidas
        PrintWriter pw = new PrintWriter(new File(path));
        StringBuilder sb = new StringBuilder();
        sb.append("Y/X,");
        // Primero cargamos todos los colores en la primer fila
        for(int x = 0; x < matriz.length; x++){
            if(marginal[x] != 0) {
                sb.append(x);
                sb.append(',');
            }
        }
        for(int x = 0; x < matriz.length; x++){
            if(marginal[x] != 0) {
                sb.append('\n');
                sb.append(x);                                       //color en Y
                sb.append(',');
                for (int y = 0; y < matriz[x].length; y++) {
                    if(marginal[y] != 0) {
                        double number = matriz[x][y];
                        sb.append((double)Math.round(number * 1000d) / 1000d); // redondeamos a 3 decimales
                        sb.append(',');                                        // separado por coma cada valor para poder abrir en un excel
                    }
                }
            }
        }
        pw.write(sb.toString());
        pw.close();
    }
//-------------------------------------------------------------------------------------------------------//
//--------------------------------------    B     -------------------------------------------------------//
//-------------------------------------------------------------------------------------------------------//

    public double[][] copyMatriz(double[][] matriz){
        //copia una matriz dada por parametro y retorna la copia
        double[][] rtrn = new double[matriz.length][matriz[0].length];
        for(int x = 0; x < matriz.length; x++){
            for(int y = 0; y < matriz[x].length; y++){
                rtrn[x][y] =  matriz[x][y] ;
            }
        }
        return rtrn;

    }
    public double[][] calcularMatrizAcumulada(double[][] matriz){
        //Calula la matriz acumulada por columnas
        double[][] Fa = copyMatriz(matriz);
        double sumnaAcumulada = 0;
        for(int x = 0; x < matriz.length; x++){

            for(int y = 0; y < matriz[0].length; y++){
                if(matriz[y][x] != 0) {
                    sumnaAcumulada = sumnaAcumulada + matriz[y][x];
                }
                Fa[y][x] = sumnaAcumulada;
            }
            sumnaAcumulada = 0;
        }

        return Fa;
    }

    private boolean Converge(double probAnterior, double probActual) {
        //retorna true si la dif de probabilidades es menor a un epsion
        return (Math.abs(probActual-probAnterior) < this.epsilon);
    }

    private int SimboloEmitido(double[] OriginalFa) {
        //simula la extraccion de un simbolo de entrada usando las probabilidades acumuladas
        double prob = (double) Math.random();
        for (int i = 0; i < OriginalFa.length; i++) {
            if (prob < OriginalFa[i]) {
                return i;
            }
        }
        return -1;
    }

    public int SimboloSalida(double[][] CanalFa,int S_emitido) {
        //simula la extraccion de un simbolo de salida Y dada una entrada X (S_Emitido)
        double prob = (double) Math.random();
        for (int i = 0; i < CanalFa.length; i++) {
           // System.out.println("prob en x = " + i + " y =" +S_emitido + "value = "+ CanalFa[i][S_emitido]);
            if (prob < CanalFa[i][S_emitido]) {
                return i;
            }
        }
        return -1;
    }

    public double getLog_2(double probabilidad){
        // calcula logaritmo en base 2 de un valor pasado por parametro
        return (-1* Math.log10(probabilidad)/Math.log10(2));
    }

    public double[] getProbabilidadesAcumuladas(int[] original,int n){
        // obtiene el vector de probabilidades acumuladas de un vector, a cada celda le suma el valor acumulado del anterior
        double[] originalFa = new double[original.length];
        double acumulada = 0;
        for(int i = 0; i< original.length ; i ++){  // se podria hacer sin la variable acumulada, utilizando los indices
            acumulada = acumulada + (double)original[i]/n;
            originalFa[i] = acumulada;
        }
        return originalFa;
    }
    public double getRuidoColumna(double[][] matriz, int i){
        //obtiene la entropia de una columna
        double ruidoColumna = 0;
        for(int y = 0; y < matriz.length; y++){
            double numeber = matriz[y][i];
            if(numeber != 0) {
                double log= getLog_2(numeber);
                ruidoColumna = ruidoColumna + log*numeber;
            }
        }
        return ruidoColumna;
    }


    public double getRuidoAnalitico(double[][] matriz,int[] distribucion,int n){
        // calcula el ruido de una matriz, primero obteniendo la entropia de cada columna con una funcion y luego a esa entropia la multiplica por la probabilidad del simbolo asociada
        double ruido = 0;
        for(int x = 0; x < distribucion.length; x++){
            if(distribucion[x] != 0){
                double ruidoColumna = getRuidoColumna(matriz,x);
                ruido = ruido + (ruidoColumna * ((double)distribucion[x]/n));
            }
        }
        return ruido;
    }
    public double actualizarColumnaRuido(double[][] M_prob,int S_emitido, int ocurrenciaSimbolo){
        //calcula el ruido de la columna afectada por la emision

        double ruidoColumna = 0;
        for(int y = 0; y < M_prob.length; y++){
            double numeber = M_prob[y][S_emitido]/ocurrenciaSimbolo;
            if(numeber != 0) {
                double log= getLog_2(numeber);
                ruidoColumna = ruidoColumna + log*numeber;
            }
        }
        return(ruidoColumna);

    }

    public double getRuido(int[] ocurrenciaSimbolos, double[] ruidoColumnas, int muestras){
        //hace esta formula Sum(p(x) * p(y/x) ) los dos son de simulacion
        double ruido = 0;
        for(int i = 0; i<ocurrenciaSimbolos.length;i++){
            ruido = ruido + ((double)ocurrenciaSimbolos[i]/muestras) * ruidoColumnas[i];
        }
        return ruido;
    }

    public double[] simulacionComputacional(double[] OriginalFa,double[][] CanalFa,int MIN_MUESTRAS,double[] historialRuido) {
        //Realizamos una simulacion Montecarlo para generar el ruido de los canales por muestreo computacional.
        //inicializacion de variables
        int emisiones = 0;
        double ruido_actual= 0;
        double ruido_viejo = -1;
        int[] ocurrenciaSimbolos = new int[256]; //simulacion
        double[][] M_prob_muestro = new double[256][256]; //simulacion
        double[] ruidoColumnas = new double[256];
        double[] historialError = new double[MIN_MUESTRAS];

        //iteraciones
        while (!this.Converge(ruido_actual, ruido_viejo) || emisiones < MIN_MUESTRAS) {
            //Simulacion
            int S_emitido = this.SimboloEmitido(OriginalFa);
            int S_salida = this.SimboloSalida(CanalFa,S_emitido);
            //Actualizacion
            emisiones++;
            ruido_viejo = ruido_actual;
            ocurrenciaSimbolos[S_emitido]++;
            M_prob_muestro[S_salida][S_emitido]++;
            ruidoColumnas[S_emitido]= actualizarColumnaRuido(M_prob_muestro,S_emitido,ocurrenciaSimbolos[S_emitido]);
            //actualizo el ruido
            ruido_actual = getRuido(ocurrenciaSimbolos,ruidoColumnas,emisiones);
            //Historial de ruidos
            if(emisiones < (MIN_MUESTRAS)){
                historialRuido[emisiones] = ruido_actual;//guardo el ruido
                historialError[emisiones] = ruido_actual-ruido_viejo;
            }
        }

        return historialError;
    }



//Generar el grafico
    private XYDataset xyDataset(double[] historialRuido, double ruidoAnalitico, boolean analitico) {
        //se declaran las series y se llenan los datos
        XYSeries error_analitico = new XYSeries("Error Analitico");
        XYSeries error_Muestrepo = new XYSeries("Error por Muestreo");
        XYSeriesCollection xyseriescollection =  new XYSeriesCollection();
        if(analitico){
            error_analitico.add( 0, ruidoAnalitico);
            error_analitico.add( historialRuido.length, ruidoAnalitico);  //Constante
            xyseriescollection.addSeries( error_analitico );
        }
        //Ruido por muestreoComputacional
        for(int i = 0; i<historialRuido.length;i++){
            error_Muestrepo.add( i, historialRuido[i]);
        }
        xyseriescollection.addSeries( error_Muestrepo );

        return xyseriescollection;
    }

    public void generarGraficoLineas(double[] historialRuido, double ruidoAnalitico,String titulo,boolean analitico,String columnay) throws IOException {
        JFrame frame = new JFrame("Ejercicio 4b");
        frame.setSize(500, 370);

        XYDataset xydataset = xyDataset(historialRuido,ruidoAnalitico,analitico);
        JFreeChart jfreechart = ChartFactory.createXYLineChart(
                titulo ,
                "Muestras", columnay,
                xydataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        //personalización del grafico
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        jfreechart.setBackgroundPaint(new Color(238, 238, 238) );
        xyplot.setBackgroundPaint( new Color(238, 238, 238) );
        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        xylineandshaperenderer.setBaseLinesVisible(true);
        xylineandshaperenderer.setBaseItemLabelsVisible(true);

        ChartPanel chartPanel = new ChartPanel(jfreechart, false);
        frame.setContentPane(chartPanel);
        //frame.setVisible(true);
        String pathGraficoCanal2 = "src"+File.separator+"Salidas"+File.separator+"Ejercicio4"+File.separator+"b"+File.separator+ titulo+".png";

        ChartUtilities.saveChartAsPNG(new File(pathGraficoCanal2), jfreechart, 600, 300 );
    }

//-------------------------------------------------------------------------------------------------------//
//--------------------------------------    Print     ---------------------------------------------------//
//-------------------------------------------------------------------------------------------------------//

    public void mostrarMatriz(double[][] matriz){
        for(int x = 0; x < matriz.length; x++){
            for(int y = 0; y < matriz[x].length; y++){
                if(matriz[x][y] != 0) {
                    //System.out.println("fila: " + x + " columna: " + y + "  " + "value: " + matriz[x][y]);
                }
            }
        }
    }
    public void mostrarMatriz2(double[][] matriz){
        NumberFormat formatter = new DecimalFormat("#0.00000");
        //System.out.println(formatter.format(4.0));

        for(int x = 0; x < matriz.length; x++){
            boolean entro = false;
            for(int y = 0; y < matriz[0].length; y++){
                if(matriz[x][y] != 0) {
                    System.out.print(formatter.format(matriz[x][y])+"| ");
                    entro = true;
                }
            }
            if(entro){
                System.out.println();
            }

        }
    }





}
