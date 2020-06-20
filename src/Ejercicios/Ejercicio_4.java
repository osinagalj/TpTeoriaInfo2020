package Ejercicios;

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import static Ejercicios.getColor.getGris;

public class Ejercicio_4 {

    public void mostrarMatriz(double[][] matriz){
        DecimalFormat formato = new DecimalFormat("#.000");
        for(int x = 0; x < matriz.length; x++){
            System.out.print("| ");
            for(int y = 0; y < matriz[x].length; y++){
                System.out.print(formato.format(matriz[x][y]) + " ");
            }
            System.out.println("|");
        }
    }

    public double[][] calcularMatriz(BufferedImage entrada, BufferedImage salida){
        double[][] rtrn = new double[17][16]; //de 0 a 16 los colores, el 17 es para llevar las cuentas

        //Inicializamos en 0
        for (int x=0; x < rtrn.length; x++) {
            for (int y=0; y < rtrn[x].length; y++) {
                rtrn[x][y] = 0;
            }
        }

        //Sabemos que tienen mismas dimensiones
        //System.out.println("Dimensiones entrada w: " + entrada.getWidth() + " h: " + entrada.getHeight());
        //System.out.println("Dimensiones entrada w: " + salida.getWidth() + " h: " + salida.getHeight());

        for (int x=0; x < entrada.getWidth(); x++) {
            for (int y=0; y < entrada.getHeight(); y++) {      //Cada posicion de pixel [x][y]
                int in = getGris(entrada,x,y);   //get color entrada en x,y
                int out = getGris(salida,x,y);   //get color salida en x,y

                in = in/17;
                out = out/17;

                rtrn[out][in]++;  //sumamos uno en la fila colorEntrada/17
                //columna colorSalida/17
                rtrn[16][in]++;
            }
        }

        for (int x=0; x < 16; x++) {
            for (int y=0; y < 16; y++) {  //Para cada espacio de la matriz
                rtrn[x][y] = (rtrn[x][y])/(rtrn[16][y]); //divido por el total de esa entrada
            }
        }
        return rtrn;
    }
}
