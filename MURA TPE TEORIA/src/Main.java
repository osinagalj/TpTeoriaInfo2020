
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.FileOutputStream;
import java.util.List;

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
            int[] distribucionImagenOrignal = new int[256]; //Arreglar extensiones
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

            /*
            //CREANDO LOS HISTOGRAMAS
            calculator.generarHistograma(distribucionWill_ej2,"Will ej 2");
            calculator.generarHistograma(distribucionWill_1,"Will 1");
            calculator.generarHistograma(distribucionImagenOrignal,"Will Original");
*/




//--------------------------------------------------------------------------------------------------------//
//----------------------------          EJERCICIO 3             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//


            Vector<Arbol> hojas_WillOriginal = new Vector<Arbol>();
            Vector<Arbol> hojas_Willej2 = new Vector<Arbol>();

            //Obtengo las hojas ordenadas para armar los arboles
            calculator.ordenarHojas(distribucionImagenOrignal,hojas_WillOriginal,n);
            calculator.ordenarHojas(distribucionWill_ej2,hojas_Willej2,n);

            //Armo los arboles
            Arbol arbol_WillOriginal = calculator.getArbolHuffman(hojas_WillOriginal);
            Arbol arbol_Willej2 = calculator.getArbolHuffman(hojas_WillOriginal);

            //Obtengo las secuencias
            HashMap<Integer,String> secuencias_original = new HashMap<Integer,String>();
            calculator.obtenerSecuencias(secuencias_original,arbol_WillOriginal,"");

            HashMap<Integer,String> secuencias_will_ej2 = new HashMap<Integer,String>();
            calculator.obtenerSecuencias(secuencias_will_ej2,arbol_Willej2,"");

            /*System.out.println("Las secuencias de huffman: ");
            Iterator it = secuencias_original.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
            }*/


            //Paths
            String pathWill_Original_comprimido = "src\\bin\\willOriginalComprimido.bin";
            String pathWill_Original_descomprimido = "src\\bin\\willOriginalDescomprimido.bmp";

            String pathWill_1_comprimido = "src\\bin\\will1_comprimido.bin";
            String pathWill_1_descomprimido = "src\\bin\\will1_descomprimido.bmp";

            String pathWill_ej2_comprimido = "src\\bin\\will_ej2_comprimido.bin";
            String pathWill_ej2_descomprimido = "src\\bin\\will_ej2_descomprimido.bmp";

            String pathWill_ej2_comprimido_Original = "src\\bin\\will_ej2_comprimido_Original.bin";
            String pathWill_ej2_descomprimido_Original = "src\\bin\\will_ej2_descomprimido_Original.bmp";


            //COMPRIMIR IMAGEN ORIGINAL
            //a
            //Comprimo la imagen
            calculator.comprimirImagen(pathWill_Original_comprimido,imgOriginal,secuencias_original, distribucionImagenOrignal);
            //Descomprimo la imagen
            BufferedImage img = calculator.map(w, h,ByteEncodingHelper.DecodeSequence(pathWill_Original_comprimido));
            calculator.savePNG( img, pathWill_Original_descomprimido );

            //b
            calculator.comprimirImagen(pathWill_1_comprimido,will_1,secuencias_original, distribucionWill_1);
            //Descomprimo la imagen
            BufferedImage img_descomprimida_will_1 = calculator.map(w, h,ByteEncodingHelper.DecodeSequence(pathWill_1_comprimido));
            calculator.savePNG( img_descomprimida_will_1, pathWill_1_descomprimido );

            //c
            calculator.comprimirImagen(pathWill_ej2_comprimido_Original,will_ej2,secuencias_original,distribucionImagenOrignal);
            //Descomprimo la imagen
            BufferedImage img_descomprimida_will_ej2_Orignal = calculator.map(w, h,ByteEncodingHelper.DecodeSequence(pathWill_ej2_comprimido_Original));
            System.out.println("sale");
            calculator.savePNG( img_descomprimida_will_ej2_Orignal, pathWill_ej2_descomprimido_Original );

            //d
            calculator.comprimirImagen(pathWill_ej2_comprimido,will_ej2,secuencias_will_ej2, distribucionWill_ej2);
            //Descomprimo la imagen
            BufferedImage img_descomprimida_will_ej2 = calculator.map(w, h,ByteEncodingHelper.DecodeSequence(pathWill_ej2_comprimido));
            calculator.savePNG( img_descomprimida_will_ej2, pathWill_ej2_descomprimido );

            //e
            //calculator.obtenerTasas();


            //----------------------------------------------------------------------------------
            //Falta hacer que al lee el archivo omita las primeras lineas en donde estan los ceros extra,las distribuciones y la longitud



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
