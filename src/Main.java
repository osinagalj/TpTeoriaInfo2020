import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main (String [ ] args) {

        try {
            BufferedImage imgOriginal = ImageIO.read(new File("src\\img\\Will_5.bmp"));//media 189  "src\\img\\Will(Original).bmp"
            BufferedImage will_1 = ImageIO.read(new File("src\\img\\Will_1.bmp")); //media 192
            BufferedImage will_2 = ImageIO.read(new File("src\\img\\Will_2.bmp")); //media 189
            BufferedImage will_3 = ImageIO.read(new File("src\\img\\Will_3.bmp")); //media 185
            BufferedImage will_4 = ImageIO.read(new File("src\\img\\Will_4.bmp")); //media 191
            BufferedImage will_5 = ImageIO.read(new File("src\\img\\Will_5.bmp")); //media 187
            // todas tienen un desvio de mierda igual a 1

            Ejercicio1 ejercicio1 = new Ejercicio1();
            float mediaOriginal = ejercicio1.calcularMedia(imgOriginal);
            float mediaWill1 = ejercicio1.calcularMedia(will_1);
            float mediaWill2 = ejercicio1.calcularMedia(will_2);
            float mediaWill3 = ejercicio1.calcularMedia(will_3);
            float mediaWill4 = ejercicio1.calcularMedia(will_4);
            float mediaWill5 = ejercicio1.calcularMedia(will_5);

            double DesvioOriginal = ejercicio1.calcularDesvioEstandar(imgOriginal,mediaOriginal);
            double DesvioWill1 = ejercicio1.calcularDesvioEstandar(will_1,mediaWill1);
            double DesvioWill2 = ejercicio1.calcularDesvioEstandar(will_2,mediaWill2);
            double DesvioWill3 = ejercicio1.calcularDesvioEstandar(will_3,mediaWill3);
            double DesvioWill4 = ejercicio1.calcularDesvioEstandar(will_4,mediaWill4);
            double DesvioWill5 = ejercicio1.calcularDesvioEstandar(will_5,mediaWill5);

            System.out.println("Media origi : " + mediaOriginal);
            System.out.println("Media Will1 : " + mediaWill1);
            System.out.println("Media Will2 : " + mediaWill2);
            System.out.println("Media Will3 : " + mediaWill3);
            System.out.println("Media Will4 : " + mediaWill4);
            System.out.println("Media Will5 : " + mediaWill5);

            System.out.println("Desvio original : " + DesvioOriginal);
            System.out.println("Desvio Will1 : " + DesvioWill1);
            System.out.println("Desvio Will2 : " + DesvioWill2);
            System.out.println("Desvio Will3 : " + DesvioWill3);
            System.out.println("Desvio Will4 : " + DesvioWill4);
            System.out.println("Desvio Will5 : " + DesvioWill5);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
