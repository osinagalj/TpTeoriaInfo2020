package Ejercicios;

import java.awt.*;
import java.awt.image.BufferedImage;
import static Ejercicios.getColor.getGris;

public class Ejercicio_1 {

    public Ejercicio_1(){}

    public double correlacion(BufferedImage imgA,BufferedImage imgB, int h, int w,int n){
        double Xi,Yi,sumatoriaXiYi = 0, sumaXi= 0,sumaYi = 0,sumaXiCuadrado=0,sumaYiCuadrado=0;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                Xi =  getGris(imgA, x, y);
                Yi = getGris(imgB, x, y);
                sumatoriaXiYi +=  Xi * Yi;
                sumaXi +=  Xi;
                sumaYi +=  Yi;
                sumaXiCuadrado +=  Xi * Xi;
                sumaYiCuadrado +=  Yi* Yi;
            }
        }
        double covarianza = (n *  sumatoriaXiYi) -  (sumaXi * sumaYi);
        double desvA = n*sumaXiCuadrado -  Math.pow(sumaXi, 2);
        double desvB = n*sumaYiCuadrado -  Math.pow(sumaYi, 2);

        return (covarianza) / Math.sqrt(desvA*desvB);
    }
}
