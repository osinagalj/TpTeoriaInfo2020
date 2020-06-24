import Ejercicios.Ejercicio_1;
import Ejercicios.Ejercicio_2;
import Estructuras.Arbol;
import Estructuras.ByteEncodingHelper;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import Ejercicios.*;
//import javafx.util.Pair;
import Estructuras.Pair;
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

            int h =  imgOriginal.getHeight();
            int w =  imgOriginal.getWidth();
            int n = h*w;



//--------------------------------------------------------------------------------------------------------//
//----------------------------          EJERCICIO 1             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//
        /*
            Ejercicio_1 ejercicio1 = new Ejercicio_1();

            Vector<Pair> pares = new Vector<Pair>();

            //calculo los indices de correlacion correspondientes
            ejercicio1.addCorrelacion(imgOriginal,will_1,h,w,n,pares);
            ejercicio1.addCorrelacion(imgOriginal,will_2,h,w,n,pares);
            ejercicio1.addCorrelacion(imgOriginal,will_3,h,w,n,pares);
            ejercicio1.addCorrelacion(imgOriginal,will_4,h,w,n,pares);
            ejercicio1.addCorrelacion(imgOriginal,will_5,h,w,n,pares);

            System.out.println("indices de correlacion: ");
            for(int i = 0; i< pares.size();i++){
                System.out.println("Original y Will_" + (i+1) + ": " + pares.get(i).getCorrelacion());
            }

            //Ordeno las imagenes por indice de correlacion
            Collections.sort(pares);

            //Muestro en la salida el orden de las imagenes de mayor a menor segun su indice de correlacion con la imagen original
            for(int i = 0; i< pares.size();i++){
                Pair par = pares.get(i);
                ejercicio1.deepCopy(par.getImg(),"src\\Salidas\\Ejercicio1\\orden"+(i+1)+".bmp");
            }
*/
//--------------------------------------------------------------------------------------------------------//
//----------------------------          EJERCICIO 2             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//

            Ejercicio_2 ejercicio2 = new Ejercicio_2();

            //Distribuciones
            int[] distribucionImagenOrignal = new int[256];
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
            System.out.println("\nmedia Original: " +mediaYDesvioOriginal[0]);
            System.out.println("mediaWill_1: " +mediaYDesvioWill_1[0]);
            System.out.println("media will ej 2: " +mediaYDesvioWill_ej2[0]);
            //desvios
            System.out.println("desvio Original: " +mediaYDesvioOriginal[1]);
            System.out.println("desvioWill_1: " +mediaYDesvioWill_1[1]);
            System.out.println("desvioWill_ej2: " +mediaYDesvioWill_ej2[1]);

            System.out.println("distribucion del original");
            for(int i = 0; i<256; i++ ){
                if(distribucionImagenOrignal[i] != 0){
                    System.out.println("x : "+ i +" fi: "+distribucionImagenOrignal[i]+ " P("+ i +") = "+  (double)distribucionImagenOrignal[i]/n) ;
                }
            }
            System.out.println(" distribucionWill_1  ");

            for(int i = 0; i<256; i++ ){
                if(distribucionWill_1[i] != 0){
                    System.out.println("x : "+ i +" fi: "+distribucionWill_1[i]+ " P("+ i +") = "+  (double)distribucionWill_1[i]/n) ;
                }
            }
            System.out.println(" distribucionWill_ej2 ");

            for(int i = 0; i<256; i++ ){
                if(distribucionWill_ej2[i] != 0){
                    System.out.println("x : "+ i +" fi: "+distribucionWill_ej2[i]+ " P("+ i +") = "+  (double)distribucionWill_ej2[i]/n) ;
                }
            }

/*
            //CREANDO LOS HISTOGRAMAS
            ejercicio2.generarHistograma(distribucionWill_ej2,"Will ej 2");
            ejercicio2.generarHistograma(distribucionWill_1,"Will 1");
            ejercicio2.generarHistograma(distribucionImagenOrignal,"Will Original");
*/

///--------------------------------------------------------------------------------------------------------//
//----------------------------          EJERCICIO 3             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//
/*
            Ejercicio_3 ejercicio_3 = new Ejercicio_3();
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

            //Paths  "src\\Salidas\\Ejercicio1\\
            String pathWill_Original_comprimido = "src\\Salidas\\Ejercicio3\\a\\willOriginalComprimido.bin";
            String pathWill_Original_descomprimido = "src\\Salidas\\Ejercicio3\\a\\willOriginalDescomprimido.bmp";

            String pathWill_1_comprimido = "src\\Salidas\\Ejercicio3\\b\\will1_comprimido.bin";
            String pathWill_1_descomprimido = "src\\Salidas\\Ejercicio3\\b\\will1_descomprimido.bmp";

            String pathWill_ej2_comprimido = "src\\Salidas\\Ejercicio3\\c\\will_ej2_comprimido.bin";
            String pathWill_ej2_descomprimido = "src\\Salidas\\Ejercicio3\\c\\will_ej2_descomprimido.bmp";

            String pathWill_ej2_comprimido_Original = "src\\Salidas\\Ejercicio3\\d\\will_ej2_comprimido_Original.bin";
            String pathWill_ej2_descomprimido_Original = "src\\Salidas\\Ejercicio3\\d\\will_ej2_descomprimido_Original.bmp";


            //COMPRIMIR IMAGEN ORIGINAL
            //a
            //Comprimo la imagen
            Integer profundidad = ejercicio_3.getProfundidadBit(imgOriginal);
            ejercicio_3.comprimirImagen(pathWill_Original_comprimido,imgOriginal,secuencias_original,distribucionImagenOrignal);
            //Descomprimo la imagen
            BufferedImage img = ejercicio_3.descomprimirImagen(ByteEncodingHelper.DecodeSequence(pathWill_Original_comprimido),profundidad);
            ejercicio_3.savePNG( img, pathWill_Original_descomprimido );

            //b
            Integer profundidad2 = ejercicio_3.getProfundidadBit(will_1);
            ejercicio_3.comprimirImagen(pathWill_1_comprimido,will_1,secuencias_original,distribucionImagenOrignal);
            //Descomprimo la imagen
            BufferedImage img_descomprimida_will_1 = ejercicio_3.descomprimirImagen(ByteEncodingHelper.DecodeSequence(pathWill_1_comprimido),profundidad2);
            ejercicio_3.savePNG( img_descomprimida_will_1, pathWill_1_descomprimido );

            //c
            Integer profundidad3 = ejercicio_3.getProfundidadBit(will_ej2);
            ejercicio_3.comprimirImagen(pathWill_ej2_comprimido_Original,will_ej2,secuencias_original,distribucionImagenOrignal);
            //Descomprimo la imagen
            BufferedImage img_descomprimida_will_ej2_Orignal = ejercicio_3.descomprimirImagen(ByteEncodingHelper.DecodeSequence(pathWill_ej2_comprimido_Original),profundidad3);
            ejercicio_3.savePNG( img_descomprimida_will_ej2_Orignal, pathWill_ej2_descomprimido_Original );

            //d
            ejercicio_3.comprimirImagen(pathWill_ej2_comprimido,will_ej2,secuencias_will_ej2,distribucionWill_ej2);
            //Descomprimo la imagen
            BufferedImage img_descomprimida_will_ej2 = ejercicio_3.descomprimirImagen(ByteEncodingHelper.DecodeSequence(pathWill_ej2_comprimido),profundidad3);
            ejercicio_3.savePNG( img_descomprimida_will_ej2, pathWill_ej2_descomprimido );

            //e
            File Original = new File("src\\img\\Will(Original).bmp");
            //long tamanoOriginal=

            File Will_original_comprimido = new File(pathWill_Original_comprimido);
            File Will_1_comprimido = new File(pathWill_1_comprimido);
            File will_ej2ComprimidoConOriginal =  new File(pathWill_ej2_comprimido_Original);
            File will_ej2Comprimido = new File(pathWill_ej2_comprimido);

            long tamanoOriginal= Original.length();
            long tamanoOriginalComprimido= Will_original_comprimido.length();

            System.out.println("\nTasa de compresion a)");
            System.out.println("Tamaño Original = "+ tamanoOriginal + "   Tamaño Comprimido = " + tamanoOriginalComprimido + "   N = " +((double)Original.length()/(double)Will_original_comprimido.length()));

            System.out.println("Tasa de compresion b)");
            System.out.println("Tamaño Original = "+ tamanoOriginal + "   Tamaño Comprimido = " + tamanoOriginalComprimido + "   N = " +((double)Original.length()/(double)Will_1_comprimido.length()));


            System.out.println("Tasa de compresion c)");
            System.out.println("Tamaño Original = "+ tamanoOriginal + "   Tamaño Comprimido = " + tamanoOriginalComprimido + "   N = " +((double)Original.length()/(double)will_ej2ComprimidoConOriginal.length()));

            System.out.println("Tasa de compresion d)");
            System.out.println("Tamaño Original = "+ tamanoOriginal + "   Tamaño Comprimido = " + tamanoOriginalComprimido + "   N = " +((double)Original.length()/(double)will_ej2Comprimido.length()));
*/

//--------------------------------------------------------------------------------------------------------//
//----------------------------          EJERCICIO 4             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//

            Ejercicio_4 ejercicio_4 = new Ejercicio_4();

            //Siempre usamos "imgOriginal", ya que es la que se pasa por cada canal

            //Cargando las Imagenes de canales
            BufferedImage imgCanal2 = ImageIO.read(new File("src\\img\\Will_Canal2.bmp"));
            BufferedImage imgCanal8 = ImageIO.read(new File("src\\img\\Will_Canal8.bmp"));
            BufferedImage imgCanal10 = ImageIO.read(new File("src\\img\\Will_canal10.bmp"));

            String pathCanal2 = "src\\Salidas\\Ejercicio4\\a\\MatrizTransicionC2.csv";
            String pathFaCanal2 = "src\\Salidas\\Ejercicio4\\a\\MatrizAcumuladaC2.csv";
            String pathCanal8 = "src\\Salidas\\Ejercicio4\\a\\MatrizTransicionC8.csv";
            String pathCanal10 = "src\\Salidas\\Ejercicio4\\a\\MatrizTransicionC10.csv";
            String pathFaCanal8 = "src\\Salidas\\Ejercicio4\\a\\MatrizAcumuladaC8.csv";
            String pathFaCanal10 = "src\\Salidas\\Ejercicio4\\a\\MatrizAcumuladaC10.csv";
            String pathOriginal = "src\\Salidas\\Ejercicio4\\a\\MatrizAcumuladaC10.csv";
            String pathFaOriginal = "src\\Salidas\\Ejercicio4\\a\\MatrizAcumuladaC10.csv";

            double[] marginalC2 = new double[256];
            double[] marginalC8 = new double[256];
            double[] marginalC10 = new double[256];

            double[][] matrizCanal2 = ejercicio_4.calcularMatriz(imgOriginal,imgCanal2,marginalC2);
            double[][] matrizCanal8 = ejercicio_4.calcularMatriz(imgOriginal,imgCanal8,marginalC8);
            double[][] matrizCanal10 = ejercicio_4.calcularMatriz(imgOriginal,imgCanal10,marginalC10);

            ejercicio_4.crearCSV(matrizCanal2,marginalC2,pathCanal2);
            ejercicio_4.crearCSV(matrizCanal8,marginalC2,pathCanal8);
            ejercicio_4.crearCSV(matrizCanal10,marginalC2,pathCanal10);

            System.out.println("IMPRIMIENDO MATRIZ ");
            ejercicio_4.mostrarMatriz2(matrizCanal2);


            //b
            double[][] matrizCanal2Fa  = ejercicio_4.calcularMatrizAcumulada(matrizCanal2);
            double[][] matrizCanal8Fa  = ejercicio_4.calcularMatrizAcumulada(matrizCanal8);
            double[][] matrizCanal10Fa  = ejercicio_4.calcularMatrizAcumulada(matrizCanal10);

            System.out.println("IMPRIMIENDO MATRIZ ACUMULADA ");
            ejercicio_4.mostrarMatriz2(matrizCanal2Fa);
            System.out.println("termino ");
            /*
            Ruido analitico:
                Canal 2: 3.643027393902264
                Canal 8: 3.790633817046591
                Canal 10: 2.4658405430026074
            Ruido simulacion:
                Canal 2: 3.6211454222442567
                Canal 8: 3.797488008381553
                Canal 10: 2.5783626805467987
             */
            double[] originalFa = ejercicio_4.getProbabilidadesAcumuladas(distribucionImagenOrignal,n); //Acumuladas del original
            double[] historial = ejercicio_4.simulacionComputacional(originalFa,matrizCanal2Fa,9000,3.643027393902264);
            ejercicio_4.generarGraficoLineas(historial,3.643027393902264);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}