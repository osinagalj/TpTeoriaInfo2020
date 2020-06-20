import Ejercicios.Ejercicio_1;
import Ejercicios.Ejercicio_2;
import Estructuras.Arbol;
import Estructuras.ByteEncodingHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import Ejercicios.*;
public class Main {

    public static File statText = new File("src\\Consola\\Consola.txt");

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

            int h =  imgOriginal.getHeight();
            int w =  imgOriginal.getWidth();
            int n = h*w;

            //Consola

           FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer wr = new BufferedWriter(osw);



//--------------------------------------------------------------------------------------------------------//
//----------------------------          EJERCICIO 1             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//
            Ejercicio_1 ejercicio1 = new Ejercicio_1();

            Vector<Double> v1 = new Vector<Double>();
            double[] v = new double[5];
            wr.write("indices de correlacion: "+ "\n");
            System.out.println("indices de correlacion: ");
            v[0] = ejercicio1.correlacion(imgOriginal,will_1,h,w,n);
            v1.add(ejercicio1.correlacion(imgOriginal,will_1,h,w,n));

            v[1] = ejercicio1.correlacion(imgOriginal,will_2,h,w,n);
            v1.add(ejercicio1.correlacion(imgOriginal,will_2,h,w,n));

            v[2] = ejercicio1.correlacion(imgOriginal,will_3,h,w,n);
            v1.add(ejercicio1.correlacion(imgOriginal,will_3,h,w,n));

            v[3] = ejercicio1.correlacion(imgOriginal,will_4,h,w,n);
            v1.add(ejercicio1.correlacion(imgOriginal,will_4,h,w,n));

            v[4] = ejercicio1.correlacion(imgOriginal,will_5,h,w,n);
            v1.add(ejercicio1.correlacion(imgOriginal,will_5,h,w,n));


            Collections.sort(v1);

            for(int x = 5; x > 0; x--){
                for (int y = 0; y < 5; y++){
                    if (v1.get(x-1) == v[y]){ //
                        wr.write("Original y Will_" + (y+1) + ": " + v1.get(x-1) + "\n" );
                        System.out.println("Original y Will_" + (y+1) + ": " + v1.get(x-1));
                    }
                }
            }
            wr.write("\n");
            System.out.println("\n");

//--------------------------------------------------------------------------------------------------------//
//----------------------------          EJERCICIO 2             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//
            Ejercicio_2 ejercicio2 = new Ejercicio_2();
            //Distribuciones
            int[] distribucionImagenOrignal = new int[256]; //Arreglar extensiones
            int[] distribucionWill_1 = new int[256];
            int[] distribucionWill_ej2 = new int[256];
            //Medias y Desvios
            double[] mediaYDesvioOriginal = new double[2];
            double[] mediaYDesvioWill_1 = new double[2];
            double[] mediaYDesvioWill_ej2 = new double[2];
            //Obteniendo
            ejercicio2.media_desvio(imgOriginal,distribucionImagenOrignal,n,mediaYDesvioOriginal);
            ejercicio2.media_desvio(will_1,distribucionWill_1,n,mediaYDesvioWill_1);
            ejercicio2.media_desvio(will_ej2,distribucionWill_ej2,n,mediaYDesvioWill_ej2);

            //medias
            System.out.println("media Original: " +mediaYDesvioOriginal[0]);        wr.write("media Original: " +mediaYDesvioOriginal[0]+"\n");
            System.out.println("mediaWill_1: " +mediaYDesvioWill_1[0]);             wr.write("mediaWill_1: " +mediaYDesvioWill_1[0]+"\n");
            System.out.println("media will ej 2: " +mediaYDesvioWill_ej2[0]);       wr.write("media will ej 2: " +mediaYDesvioWill_ej2[0]+"\n");
            //desvios
            System.out.println("desvio Original: " +mediaYDesvioOriginal[1]);       wr.write("desvio Original: " +mediaYDesvioOriginal[1]+"\n");
            System.out.println("desvioWill_1: " +mediaYDesvioWill_1[1]);            wr.write("desvioWill_1: " +mediaYDesvioWill_1[1]+"\n");
            System.out.println("desvioWill_ej2: " +mediaYDesvioWill_ej2[1]);       wr.write("desvioWill_ej2: "+"\n");

            System.out.println("distribucion del original");                            wr.write("distribucion del original /n"+"\n");
            for(int i = 0; i<256; i++ ){
                if(distribucionImagenOrignal[i] != 0){
                    wr.write("x : "+ i +" fi: "+distribucionImagenOrignal[i]+ " P("+ i +") = "+  (double)distribucionImagenOrignal[i]/n+ "\n");
                    System.out.println("x : "+ i +" fi: "+distribucionImagenOrignal[i]+ " P("+ i +") = "+  (double)distribucionImagenOrignal[i]/n) ;
                }
            }
            System.out.println(" distribucionWill_1  ");
            wr.write("distribucionWill_1 "+"\n");
            for(int i = 0; i<256; i++ ){
                if(distribucionWill_1[i] != 0){
                    wr.write("x : "+ i +" fi: "+distribucionWill_1[i]+ " P("+ i +") = "+  (double)distribucionWill_1[i]/n+"\n");
                    System.out.println("x : "+ i +" fi: "+distribucionWill_1[i]+ " P("+ i +") = "+  (double)distribucionWill_1[i]/n) ;
                }
            }
            System.out.println(" distribucionWill_ej2 ");
            wr.write("distribucionWill_ej2  "+"\n");
            for(int i = 0; i<256; i++ ){
                if(distribucionWill_ej2[i] != 0){
                    wr.write("x : "+ i +" fi: "+distribucionWill_ej2[i]+ " P("+ i +") = "+  (double)distribucionWill_ej2[i]/n+"\n");
                    System.out.println("x : "+ i +" fi: "+distribucionWill_ej2[i]+ " P("+ i +") = "+  (double)distribucionWill_ej2[i]/n) ;
                }
            }


            //CREANDO LOS HISTOGRAMAS
            ejercicio2.generarHistograma(distribucionWill_ej2,"Will ej 2");
            ejercicio2.generarHistograma(distribucionWill_1,"Will 1");
            ejercicio2.generarHistograma(distribucionImagenOrignal,"Will Original");


///--------------------------------------------------------------------------------------------------------//
//----------------------------          EJERCICIO 3             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//

            Ejercicio_3 ejercicio_3 = new Ejercicio_3(wr);
            Vector<Arbol> hojas_WillOriginal = new Vector<Arbol>();
            Vector<Arbol> hojas_Willej2 = new Vector<Arbol>();

            //Obtengo las hojas ordenadas para armar los arboles
            ejercicio_3.ordenarHojas(distribucionImagenOrignal,hojas_WillOriginal,n);
            ejercicio_3.ordenarHojas(distribucionWill_ej2,hojas_Willej2,n);

            //Armo los arboles
            Arbol arbol_WillOriginal = ejercicio_3.getArbolHuffman(hojas_WillOriginal);
            Arbol arbol_Willej2 = ejercicio_3.getArbolHuffman(hojas_Willej2);

            //Obtengo las secuencias
            HashMap<Integer,String> secuencias_original = new HashMap<Integer, String>();
            ejercicio_3.obtenerSecuencias(secuencias_original,arbol_WillOriginal,"");

            HashMap<Integer,String> secuencias_will_ej2 = new HashMap<Integer,String>();
            ejercicio_3.obtenerSecuencias(secuencias_will_ej2,arbol_Willej2,"");

/*
            System.out.println("\n" + "Secuencias Original:");
            calculator.printMap(secuencias_original);
            System.out.println("\n" + "Secuencias Will_ej2:");
            calculator.printMap(secuencias_will_ej2);
            System.out.println();
*/

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
            System.out.println("FRENCUANCIAS DEL HASMAP 1");
            ejercicio_3.printMap(secuencias_original);
            Integer profundidad = ejercicio_3.getProfundidadBit(imgOriginal);
            System.out.println("profundiad = " + profundidad);
            ejercicio_3.comprimirImagen(pathWill_Original_comprimido,imgOriginal,secuencias_original,distribucionImagenOrignal);
            //Descomprimo la imagen
            BufferedImage img = ejercicio_3.map(ByteEncodingHelper.DecodeSequence(pathWill_Original_comprimido));
            ejercicio_3.savePNG( img, pathWill_Original_descomprimido );

            //b
            Integer profundidad2 = ejercicio_3.getProfundidadBit(will_1);
            System.out.println("profundiad2 = " + profundidad2);
            ejercicio_3.comprimirImagen(pathWill_1_comprimido,will_1,secuencias_original,distribucionImagenOrignal);
            //Descomprimo la imagen
            BufferedImage img_descomprimida_will_1 = ejercicio_3.map(ByteEncodingHelper.DecodeSequence(pathWill_1_comprimido));
            ejercicio_3.savePNG( img_descomprimida_will_1, pathWill_1_descomprimido );

            //c
            Integer profundidad3 = ejercicio_3.getProfundidadBit(will_ej2);
            System.out.println("profundiad3 = " + profundidad3);
            ejercicio_3.comprimirImagen(pathWill_ej2_comprimido_Original,will_ej2,secuencias_original,distribucionImagenOrignal);
            //Descomprimo la imagen
            BufferedImage img_descomprimida_will_ej2_Orignal = ejercicio_3.map(ByteEncodingHelper.DecodeSequence(pathWill_ej2_comprimido_Original));
            ejercicio_3.savePNG( img_descomprimida_will_ej2_Orignal, pathWill_ej2_descomprimido_Original );

            //d
            ejercicio_3.comprimirImagen(pathWill_ej2_comprimido,will_ej2,secuencias_will_ej2,distribucionWill_ej2);
            //Descomprimo la imagen
            BufferedImage img_descomprimida_will_ej2 = ejercicio_3.map(ByteEncodingHelper.DecodeSequence(pathWill_ej2_comprimido));
            ejercicio_3.savePNG( img_descomprimida_will_ej2, pathWill_ej2_descomprimido );



//--------------------------------------------------------------------------------------------------------//
//----------------------------          EJERCICIO 4             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//

            Ejercicio_4 ejercicio_4 = new Ejercicio_4();
            //Siempre usamos "imgOriginal", ya que es la que se pasa por cada canal

            //Cargando las Imagenes de canales
            BufferedImage imgCanal2 = ImageIO.read(new File("src\\img\\Will_Canal2.bmp"));
            BufferedImage imgCanal8 = ImageIO.read(new File("src\\img\\Will_Canal8.bmp"));
            BufferedImage imgCanal10 = ImageIO.read(new File("src\\img\\Will_canal10.bmp"));

            double[][] matrizCanal2 = ejercicio_4.calcularMatriz(imgOriginal,imgCanal2);
           // double[][] matrizCanal8 = ejercicio_4.calcularMatriz(imgOriginal,imgCanal8);
           // double[][] matrizCanal10 = ejercicio_4.calcularMatriz(imgOriginal,imgCanal10);

            ejercicio_4.mostrarMatriz(matrizCanal2);
           // ejercicio_4.mostrarMatriz(ejercicio_4.calcularMatriz(imgOriginal,imgCanal8));
           // ejercicio_4.mostrarMatriz(ejercicio_4.calcularMatriz(imgOriginal,imgCanal10));


            // Cierro el archivo en donde guardo lo imprimido por consola
            wr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}