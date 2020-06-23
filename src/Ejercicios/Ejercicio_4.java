package Ejercicios;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import static Ejercicios.getColor.getGris;

public class Ejercicio_4 {

//-------------------------------------------------------------------------------------------------------//
//--------------------------------------    A     -------------------------------------------------------//
//-------------------------------------------------------------------------------------------------------//
    public double[][] calcularMatriz(BufferedImage entrada, BufferedImage salida, String Path,String path2,double[] marginal ){
        double[][] rtrn = new double[256][256];

        //Inicializamos en 0
        for (int x=0; x < rtrn.length; x++) {
            for (int y=0; y < rtrn[x].length; y++) {
                rtrn[x][y] = 0;
            }
        }

        //Sumamos 1 cada vez que aparece aparece x un x/y
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
/*
    [17:56, 19/6/2020] Mati Guerrero: Yo hice una simulación de entrada con la distribución de la matriz original
[17:56, 19/6/2020] Mati Guerrero: Y la salida dada la entrada con la matriz condiconal entre las dos imágenes (4 a) pero acumulada
[17:56, 19/6/2020] Mati Guerrero: Y voy sumando éxito cada vez que sale una entrada
[17:56, 19/6/2020] Mati Guerrero: Y una salida
[17:56, 19/6/2020] Mati Guerrero: Y generando la matriz de transiciónes por muestreo
[17:56, 19/6/2020] Mati Guerrero: Y a eso le aplicas la fórmula del ruido
*/
    public double[][] copyMatriz(double[][] matriz){
        double[][] rtrn = new double[256][256];
        for(int x = 0; x < matriz.length; x++){
            for(int y = 0; y < matriz[x].length; y++){
                rtrn[x][y] =  matriz[x][y] ;
            }
        }
        return rtrn;

    }
    public double[][] calcularMatrizAcumulada(double[][] matriz){

        double[][] Fa = copyMatriz(matriz);
        for(int x = 0; x < matriz.length; x++){
            double sumnaAcumulada = 0;
            for(int y = 0; y < matriz[0].length; y++){
                if(matriz[y][x] != 0) {
                    sumnaAcumulada = sumnaAcumulada + matriz[y][x];
                }
                matriz[y][x] = sumnaAcumulada;
            }
        }

        return Fa;
    }




//-------------------------------------------------------------------------------------------------------//
//--------------------------------------    Print     ---------------------------------------------------//
//-------------------------------------------------------------------------------------------------------//

    public void mostrarMatriz(double[][] matriz){
        for(int x = 0; x < matriz.length; x++){
            for(int y = 0; y < matriz[x].length; y++){
                if(matriz[x][y] != 0) {
                    System.out.println("fila: " + x + " columna: " + y + "  " + "value: " + matriz[x][y]);
                }
            }
        }
    }





}
