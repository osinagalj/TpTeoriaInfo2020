import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Ejercicio1 {

    public Ejercicio1(){}
    public float calcularMedia( BufferedImage imgOriginal){
        float sumaTonalidades = 0;

        for (int x = 0; x < imgOriginal.getWidth(); x++) {
            for (int y = 0; y < imgOriginal.getHeight(); y++) {
                float gris = getGris(imgOriginal,x,y);
                sumaTonalidades= sumaTonalidades+gris;
            }
        }
        return sumaTonalidades/(imgOriginal.getWidth()*imgOriginal.getHeight());
    }

    public double calcularDesvioEstandar(BufferedImage imgOriginal, float media){
        double desvioEstandar=0;
        int n = imgOriginal.getWidth()*imgOriginal.getHeight(); //2227000

        for (int x = 0; x < imgOriginal.getWidth(); x++) {
            for (int y = 0; y < imgOriginal.getHeight(); y++) {
                float gris = getGris(imgOriginal,x,y);
                desvioEstandar+=Math.pow((gris - media), 2);
               /* System.out.println("gris: " + gris);
                System.out.println("media: " + media);
                System.out.println("desvioEstandar: " + desvioEstandar);
*/
            }
        }
       // System.out.println("varianza: " + desvioEstandar);
        return Math.sqrt((desvioEstandar/(n)));
    }

    public int getGris(BufferedImage imgOriginal,int x,int y){
        int rgb = imgOriginal.getRGB(x, y);
        Color color = new Color(rgb, true);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        int gris = (r+g+b)/3; // con este se saca la escala de grises
        return gris;
    }
    public float calcularCovarianzaAB(BufferedImage imgA,BufferedImage imgB,float media1, float media2){
        float suma = 0;
        for (int x = 0; x < imgA.getWidth(); x++) {
            for (int y = 0; y < imgA.getHeight(); y++) {
                            int grisA = getGris(imgA,x,y);
                            int grisB = getGris(imgB,x,y);
                            suma = suma + ((grisA-media1)*(grisB-media2));
            }
        }
        return suma/(imgA.getWidth()*imgA.getHeight());
    }
   public double calcularFactorCorrelacionPearson(float covarianza,double desvioX,double desvioY){
        return covarianza/(desvioX*desvioY);
   }
   public void obtenerDistribuciones(BufferedImage img, int[] myIntArray ){
       for (int x = 0; x < img.getWidth(); x++) {
           for (int y = 0; y < img.getHeight(); y++) {
               int grisA = getGris(img,x,y);
               myIntArray[grisA] = myIntArray[grisA] + 1;
           }
       }
   }
}

