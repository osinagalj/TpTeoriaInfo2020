import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

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

            HashMap distribucionesOrdenadas = calculator.ordenarFI(distribucionImagenOrignal);
            Vector<Arbol> hojas = new Vector<Arbol>();
            //Cargando hojas
            /*
            for(int i=0; i<distribucionImagenOrignal.length;i++){
                if(distribucionImagenOrignal[i] != 0) {
                    Arbol hoja = new Arbol(i,(float) distribucionImagenOrignal[i]/n);
                    calculator.inserTarOrdenado(hojas,hoja);
                }
            }

*/


            hojas.add(0,new Arbol(0,(double)1/41));
            hojas.add(1,new Arbol(1,(double)1/41));
            hojas.add(2,new Arbol(2,(double)1/41));
            hojas.add(3,new Arbol(3,(double)1/41));
            hojas.add(4,new Arbol(4,(double)1/41));
            hojas.add(5,new Arbol(5,(double)1/41));
            hojas.add(6,new Arbol(6,(double)2/41));
            hojas.add(7,new Arbol(7,(double)2/41));
            hojas.add(8,new Arbol(8,(double)2/41));
            hojas.add(9,new Arbol(9,(double)2/41));//0,048
            hojas.add(10,new Arbol(10,(double)2/41));
            hojas.add(11,new Arbol(11,(double)2/41));
            hojas.add(12,new Arbol(12,(double)3/41));
            hojas.add(13,new Arbol(13,(double)3/41));
            hojas.add(14,new Arbol(14,(double)3/41));
            hojas.add(15,new Arbol(15,(double)6/41));//0,14
            hojas.add(16,new Arbol(16,(double)8/41)); //0,19

/*

            hojas.add(0,new Arbol(1,(double)4/41,2));
            hojas.add(1,new Arbol(2,(double)4/41,2));
            hojas.add(2,new Arbol(3,(double)4/41,1));
            hojas.add(3,new Arbol(4,(double)2/41,0));

            calculator.inserTarOrdenado2(hojas,new Arbol(5,(double)4/41,1));
            System.out.println("Arbolito xd");
            for(int i = 0; i <hojas.size();i++){
                System.out.println("color: "+ hojas.get(i).getColor()+ " nivel: "+hojas.get(i).getNivel() );
            }
*/

           // 0  1  2  3  4  5  6  7  8  9  10  11  12  13  14  15
           //41 es la suma de fi

            Arbol a = calculator.getPadre2(hojas);
            HashMap<Integer,String> resultados = new HashMap<Integer,String>();
            calculator.obtenerSecuencias(resultados,a,"");

            System.out.println("Las secuencias de huffman: ");
            Iterator it = resultados.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }

            System.out.println("IMPRIMIENDO ARBOL izq");
            calculator.imprimirArbolIzq(a,0);
            System.out.println("IMPRIMIENDO ARBOL der");
            calculator.imprimirArbolDer(a,0);
            System.out.println("hijo derecho: "+ a.getHijoDerecho().getProbabilidad());
/*
                    111111
                    111110
                    111101
                    111100

                    111011
                    111010
                    11100

                    0111
                    0110
                    0101
                    0100
                    1011
                    1010
                    1001
                    1000
                    110
                    00
*/

/*
            //Recorrer el mapa
            Iterator it = resultados2.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }
*/
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
