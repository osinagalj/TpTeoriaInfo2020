package Ejercicios;

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import static Ejercicios.getColor.getGris;

public class Ejercicio_4 {

    public void mostrarMatriz(double[][] matriz){
        for(int x = 0; x < matriz.length; x++){
            for(int y = 0; y < matriz[x].length; y++){
                if(matriz[y][x] != 0) {
                    System.out.println("x: " + x + " y: " + y + "  " + "value: " + matriz[y][x]);
                }
            }
        }
    }

    public double[][] calcularMatriz(BufferedImage entrada, BufferedImage salida){
        double[][] rtrn = new double[256][256];
        double[] marginal = new double[256];
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






}
