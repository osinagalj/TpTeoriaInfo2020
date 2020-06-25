package Ejercicios;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import Estructuras.Pair;
import static Ejercicios.getColor.getGris;

public class Ejercicio_1 {

    public Ejercicio_1(){}

    public void addCorrelacion(BufferedImage original,BufferedImage img ,int h, int w, int n, Vector<Pair> pares ){
        double correlacion_Original_Will1 = correlacion(original,img,h,w,n);
        pares.add(new Pair(correlacion_Original_Will1,img));
    }

    public double correlacion(BufferedImage imgA,BufferedImage imgB, int h, int w,int n){
        //Calculamos el factor de correlacion entre la imgA y la imgB
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

    public BufferedImage deepCopy(BufferedImage bi,String saveAs) throws IOException {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        BufferedImage cImg = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        File saveImage = new File(saveAs);
        ImageIO.write(cImg, "jpg", saveImage);
        return cImg;
    }
}
