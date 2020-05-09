import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main (String [ ] args) {

        try {


            BufferedImage imgOriginal = ImageIO.read(new File("src\\img\\Will_5.bmp"));//media 189  "src\\img\\Will(Original).bmp"
            BufferedImage will_1 = ImageIO.read(new File("src\\img\\Will_1.bmp")); //media 192
            BufferedImage will_2 = ImageIO.read(new File("src\\img\\Will_2.bmp")); //media 189
            BufferedImage will_3 = ImageIO.read(new File("src\\img\\Will_3.bmp")); //media 185
            BufferedImage will_4 = ImageIO.read(new File("src\\img\\Will_4.bmp")); //media 191
            BufferedImage will_5 = ImageIO.read(new File("src\\img\\Will_5.bmp")); //media 187
            BufferedImage will_ej2 = ImageIO.read(new File("src\\img\\Will_ej2.bmp"));
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

            float covarianza = ejercicio1.calcularCovarianzaAB(imgOriginal,will_1,mediaOriginal,mediaWill1);
            System.out.println("Cov(A,B) : " + covarianza);
            float covarianza2 = ejercicio1.calcularCovarianzaAB(imgOriginal,will_2,mediaOriginal,mediaWill2);
            System.out.println("Cov(A,B) : " + covarianza);
            float covarianza3 = ejercicio1.calcularCovarianzaAB(imgOriginal,will_3,mediaOriginal,mediaWill3);
            System.out.println("Cov(A,B) : " + covarianza);
            float covarianza4 = ejercicio1.calcularCovarianzaAB(imgOriginal,will_4,mediaOriginal,mediaWill4);
            System.out.println("Cov(A,B) : " + covarianza);
            float covarianza5 = ejercicio1.calcularCovarianzaAB(imgOriginal,will_5,mediaOriginal,mediaWill5);
            System.out.println("Cov(A,B) : " + covarianza);

            //correlacion entre original y will1
            double r = ejercicio1.calcularFactorCorrelacionPearson(covarianza,DesvioOriginal,DesvioWill1);
            System.out.println("indice de correlacion : " + r);
            //correlacion entre original y will2
            double r2 = ejercicio1.calcularFactorCorrelacionPearson(covarianza2,DesvioOriginal,DesvioWill2);
            System.out.println("indice de correlacion2 : " + r2);
            //correlacion entre original y will3
            double r3 = ejercicio1.calcularFactorCorrelacionPearson(covarianza3,DesvioOriginal,DesvioWill3);
            System.out.println("indice de correlacion3 : " + r3);
            //correlacion entre original y will2
            double r4 = ejercicio1.calcularFactorCorrelacionPearson(covarianza4,DesvioOriginal,DesvioWill4);
            System.out.println("indice de correlacion4 : " + r4);
            //correlacion entre original y will2
            double r5 = ejercicio1.calcularFactorCorrelacionPearson(covarianza5,DesvioOriginal,DesvioWill5);
            System.out.println("indice de correlacion5 : " + r5);


        //Ejercicio 2
            int[] distribucionImagenOrignal = new int[256];
            ejercicio1.obtenerDistribuciones(imgOriginal,distribucionImagenOrignal);
            int[] distribucionWill5 = new int[256];
            ejercicio1.obtenerDistribuciones(will_5,distribucionWill5);
            int[] distribucionWill_ej2 = new int[256];
            ejercicio1.obtenerDistribuciones(will_ej2,distribucionWill_ej2);

            System.out.println("frecuencia will original ");
            for(int i= 0; i< 256 ; i++){
                System.out.print(distribucionImagenOrignal[i]+ " ");
            }
            System.out.println();
            System.out.println("frecuencia will 5 ");
            for(int i= 0; i< 256 ; i++){
                System.out.print(distribucionWill5[i]+ " ");
            }
            System.out.println();
            System.out.println("frecuencia will ej 2 ");
            for(int i= 0; i< 256 ; i++){
                System.out.print(distribucionWill_ej2[i]+ " ");
            }
            System.out.println();
            System.out.println();

            //MEDIAS Y DESVIOS
           // la original y will 5 tienen el mismo desvio y media y misma distribucion
            float Ej2_mediaWill_5 = ejercicio1.calcularMedia(will_5);
            double Ej2_DesvioWill5 = ejercicio1.calcularDesvioEstandar(will_5,Ej2_mediaWill_5);
            System.out.println("media will 5: "+ Ej2_mediaWill_5);
            System.out.println("desvio will 5: "+ Ej2_DesvioWill5);

            System.out.println();
            float Ej2_mediaWill_original = ejercicio1.calcularMedia(imgOriginal);
            double Ej2_DesvioWill_original = ejercicio1.calcularDesvioEstandar(imgOriginal,Ej2_mediaWill_original);
            System.out.println("media will original: "+ Ej2_mediaWill_original);
            System.out.println("desvio will original: "+ Ej2_DesvioWill_original);

            System.out.println();
            float Ej2_mediaWill_ej2 = ejercicio1.calcularMedia(will_ej2);
            double Ej2_DesvioWill_ej2 = ejercicio1.calcularDesvioEstandar(will_ej2,Ej2_mediaWill_ej2);
            System.out.println("media will ej2: "+ Ej2_mediaWill_ej2);
            System.out.println("desvio will ej2: "+ Ej2_DesvioWill_ej2);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
