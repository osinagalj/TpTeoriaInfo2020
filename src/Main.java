import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main (String [ ] args) {

        try {
            //Cargando las Imagenes
            BufferedImage imgOriginal = ImageIO.read(new File("src\\img\\Will(Original).bmp"));//media 189  "src\\img\\Will(Original).bmp"
            BufferedImage will_1 = ImageIO.read(new File("src\\img\\Will_1.bmp")); //media 192
            BufferedImage will_2 = ImageIO.read(new File("src\\img\\Will_2.bmp")); //media 189
            BufferedImage will_3 = ImageIO.read(new File("src\\img\\Will_3.bmp")); //media 185
            BufferedImage will_4 = ImageIO.read(new File("src\\img\\Will_4.bmp")); //media 191
            BufferedImage will_5 = ImageIO.read(new File("src\\img\\Will_5.bmp")); //media 187
            BufferedImage will_ej2 = ImageIO.read(new File("src\\img\\Will_ej2.bmp"));
            //Creando la calculadora
            Calculator calculator = new Calculator();

            int h =  imgOriginal.getHeight();
            int w =  imgOriginal.getWidth();
            int n = h*w;

//--------------------------------------------------------------------------------------------------------//
//----------------------------          EJERCICIO 1             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//

            System.out.println("indices de correlacion ");
            double r = calculator.ejercicio1(imgOriginal,will_1,h,w,n);
            System.out.println("Original y Will_1 : " + r);
            double r2 = calculator.ejercicio1(imgOriginal,will_2,h,w,n);
            System.out.println("Original y Will_2  : " + r2);
            double r3 = calculator.ejercicio1(imgOriginal,will_3,h,w,n);
            System.out.println("Original y Will_3  " + r3);
            double r4 = calculator.ejercicio1(imgOriginal,will_4,h,w,n);
            System.out.println("Original y Will_4 : " + r4);
            double r5 = calculator.ejercicio1(imgOriginal,will_5,h,w,n);
            System.out.println("Original y Will_5  : " + r5);



//--------------------------------------------------------------------------------------------------------//
//----------------------------          EJERCICIO 2             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//

            //Distribuciones
            int[] distribucionImagenOrignal = new int[256];
            int[] distribucionWill_1 = new int[256];
            int[] distribucionWill_ej2 = new int[256];
            //Medias y Desvios
            double[] mediaYDesvioOriginal = new double[2];
            double[] mediaYDesvioWill_1 = new double[2];
            double[] mediaYDesvioWill_ej2 = new double[2];
            //Obteniendo
            calculator.ejercicio2(imgOriginal,distribucionImagenOrignal,n,mediaYDesvioOriginal);
            calculator.ejercicio2(will_1,distribucionWill_1,n,mediaYDesvioWill_1);
            calculator.ejercicio2(will_ej2,distribucionWill_ej2,n,mediaYDesvioWill_ej2);

            //medias
            System.out.println("media Original: " +mediaYDesvioOriginal[0]);
            System.out.println("mediaWill_1: " +mediaYDesvioWill_1[0]);
            System.out.println("media will ej 2: " +mediaYDesvioWill_ej2[0]);
            //desvios
            System.out.println("desvio Original: " +mediaYDesvioOriginal[1]);
            System.out.println("desvioWill_1: " +mediaYDesvioWill_1[1]);
            System.out.println("desvioWill_ej2: " +mediaYDesvioWill_ej2[1]);

            System.out.println("distribucion del original /n");
            for(int i = 0; i<256; i++ ){
                if(distribucionImagenOrignal[i] != 0){
                    System.out.println("x : "+ i +" fi: "+distribucionImagenOrignal[i]+ " P("+ i +") = "+  (double)distribucionImagenOrignal[i]/n) ;
                }
            }
            System.out.println(" distribucionWill_1  /n");
            for(int i = 0; i<256; i++ ){
                if(distribucionWill_1[i] != 0){
                    System.out.println("x : "+ i +" fi: "+distribucionWill_1[i]+ " P("+ i +") = "+  (double)distribucionWill_1[i]/n) ;
                }
            }
            System.out.println(" distribucionWill_ej2  /n");
            for(int i = 0; i<256; i++ ){
                if(distribucionWill_ej2[i] != 0){
                    System.out.println("x : "+ i +" fi: "+distribucionWill_ej2[i]+ " P("+ i +") = "+  (double)distribucionWill_ej2[i]/n) ;
                }
            }


//--------------------------------------------------------------------------------------------------------//
//----------------------------          EJERCICIO 3             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//


            Vector<Arbol> hojas_WillOriginal = new Vector<Arbol>();

            //Obtengo las hojas ordenadas para armar el arbol
            calculator.ordenarHojas(distribucionImagenOrignal,hojas_WillOriginal,n);
            //Armo el arbol
            Arbol arbol_WillOriginal = calculator.getArbolHuffman(hojas_WillOriginal);
            //Obtengo las secuencias
            HashMap<Integer,String> secuencias = new HashMap<Integer,String>();
            calculator.obtenerSecuencias(secuencias,arbol_WillOriginal,"");


            Vector<Arbol> hojas_Willej2 = new Vector<Arbol>();
            calculator.ordenarHojas(distribucionWill_ej2,hojas_Willej2,n);
            Arbol arbol_Willej2 = calculator.getArbolHuffman(hojas_WillOriginal);
            HashMap<Integer,String> secuencias_will_ej2 = new HashMap<Integer,String>();
            calculator.obtenerSecuencias(secuencias_will_ej2,arbol_Willej2,"");

            //a
            calculator.comprimirImagen(imgOriginal,secuencias,"");
            //b
            calculator.comprimirImagen(will_1,secuencias,"");
            //c
            calculator.comprimirImagen(will_ej2,secuencias,"");
            //d
            calculator.comprimirImagen(will_ej2,secuencias_will_ej2,"");
            //e
            //calculator.obtenerTasas();


            //Imprimo las secuencias de willOriginal
            System.out.println("Las secuencias de huffman: ");
            Iterator it = secuencias.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }







        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
