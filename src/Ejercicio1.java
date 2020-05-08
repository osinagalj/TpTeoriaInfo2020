import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Ejercicio1 {

    public Ejercicio1(){}
    public float calcularMedia( BufferedImage imgOriginal){
        int sumaTonalidades = 0;

        for (int x = 0; x < imgOriginal.getWidth(); x++) {
            for (int y = 0; y < imgOriginal.getHeight(); y++) {
                int gris = getGris(imgOriginal,x,y);
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
        System.out.println("varianza: " + desvioEstandar);
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
}

