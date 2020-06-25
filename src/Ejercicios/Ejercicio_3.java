package Ejercicios;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.List;

import static Ejercicios.getColor.getGris;

import Estructuras.Arbol;
import Estructuras.Data;
import Estructuras.ByteEncodingHelper;


public class Ejercicio_3 {



    public Ejercicio_3(){}

///--------------------------------------------------------------------------------------------------------//
//----------------------------          ARBOL DE HUFFMAN         ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//
    public void inserTarOrdenado(Vector<Arbol> hojas, Arbol hoja) {
        int i = 0; //Para recorrer el vector

        while (i < hojas.size()) {
            if (hoja.getProbabilidad() < hojas.get(i).getProbabilidad()) { //Si es mas chico va de una
                hojas.add(i, hoja);
                return;
            } else {
                if ((hoja.getProbabilidad() == hojas.get(i).getProbabilidad()) && (hoja.getNivel() >= hojas.get(i).getNivel())) { //Si son iguales ordeno por nivel
                    hojas.add(i, hoja);
                    return;           //Si son iguales primero quiero que este el de mayor nivel, para que tenga siempre la misma forma
                }
                i++; //Para que avance
            }
        }
        hojas.add(hoja); //Si llega aca es porque nunca se inserto => probabilidad mas grande, va al final
    }

    public Arbol getArbolHuffman(Vector<Arbol> hojas){
        //Hijo derecho   = mayor probabilidad
        //Hijo Izquierdo = menor probabilidad
        while (hojas.size() > 1){ //Sacamos dos del vector, y creamos uno nuevo, hasta que tenga 1 solo nodo
            Arbol izq = hojas.get(0);
            Arbol der = hojas.get(1);
            hojas.remove(0);
            hojas.remove(0);

            Arbol huffman = new Arbol(izq,der);
            this.inserTarOrdenado(hojas, huffman);
        }
        return hojas.get(0);
    }

    public void obtenerSecuencias(HashMap<Integer,String> h, Arbol arbolito, String secuencia){
        //Recorremos el arbol obteniendo las secuencias para cada simbolo de entrada

        if(arbolito.getHijoDerecho() == null) { //Con que uno sea null ya sabemos que es hoja
            h.put(arbolito.getColor(),secuencia);
        }else{                                  //Si no es hoja, recorremos para ambos lados
            obtenerSecuencias(h,arbolito.getHijoDerecho(),secuencia+"0");
            obtenerSecuencias(h,arbolito.getHijoIzquierdo(),secuencia+"1");
        }
    }

    public void ordenarHojas(int[] distribucion, Vector<Arbol> hojas, int n){
        for(int i=0; i<distribucion.length;i++){
            if(distribucion[i] != 0) {
                Arbol hoja = new Arbol(i, (double)distribucion[i]/n); //Cuando arreglemos extension la posicion no va a representar el color
                hojas.add(hoja);
            }
        }
        Collections.sort(hojas);
    }

    public char[] codificarSecuencia(BufferedImage img, HashMap<Integer,String> secuencias){
        //A partir de una imagen y el hash asociado a cada pixel-secuencia nos devuelve una cadena de char de 0 o 1
        ArrayList<Character> secuenciaArray = new ArrayList<Character>();
        Integer color;
        String aux;

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {  //Para cada pixel
                color = (int)getGris(img,x,y);           //Obtenemos el color
                aux = secuencias.get(color);            //obtenemos el integer que lo representa
                for (int k = 0; k < aux.length(); k++){  //partimos el string como una secuencia de char
                    secuenciaArray.add(aux.charAt(k));
                }
            }
        }

        char[] secuenciaChar = new char[secuenciaArray.size()]; //Ahora que sabemos la cantidad de char que generamos lo pasamos a un arreglo
        for (int x = 0; x < secuenciaChar.length; x++){
            secuenciaChar[x] = secuenciaArray.get(x);
        }

        return secuenciaChar;
    }

    public int getSimbolo(Arbol huffman, String secuencia, int pos){
        //recorremos el arbol buscando el simbolo en base a una secuencia

        if((huffman.getHijoDerecho() == null) && (huffman.getHijoIzquierdo() == null)){
            return huffman.getColor();  //Si es hoja lo encontraste
        }else{                          //Si no debes buscarlo en sus hijos
            if(secuencia.length()> pos){
                String s = "1";                 //esto es para igualar abajo, podria ser un char, pero nos tiro un error y quedo asi
                if(secuencia.charAt(pos) == s.charAt(0)){
                    return getSimbolo(huffman.getHijoIzquierdo(),secuencia,pos+1);
                }else{
                    return getSimbolo(huffman.getHijoDerecho(),secuencia,pos+1);
                }
            }else{
                return -1;
            }
        }
    }



///--------------------------------------------------------------------------------------------------------//
//----------------------------          COMPRIMIR             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//

    public void comprimirImagen(String path,BufferedImage img,HashMap<Integer,String> secuencias, int[] distribucion) throws IOException {

        FileOutputStream fos = new FileOutputStream(path);

        //Secuencia de la imagen
        char[] secuenciaChar = codificarSecuencia(img, secuencias) ;
        List<Byte> secuenciaByte = ByteEncodingHelper.EncodeSequence(secuenciaChar);  //Codificado a byte
        byte[] secuenciaBits = this.ConvertByteListToPrimitives(secuenciaByte); //Binario

        //WRITE IN FILE
        writeInFile(getProfundidadBit(img),1,fos);                      //Profundidad             1 Byte    0
        writeInFile(secuenciaChar.length,3,fos);                        //Longitud de secuencia         3 Bytes   1,2,3
        writeInFile(getFrecuenciasUtilizadas(distribucion),1,fos);      //Cantidad de Frecuencias       1 Byte    4
        writeInFile(img.getWidth(),3,fos);                              //Anchura                       3 Bytes   5,6,7
        writeInFile(img.getHeight(),3,fos);                             //Altura                        3 Bytes   8,9,10
        insertarFrecuencias(fos,distribucion,getFrecuenciasUtilizadas(distribucion)); //Frecuencias           1 Byte Color 11; 15
                                                                              //                              3 Byte frecuencia  12,13,14; 16,17,18
        fos.write(secuenciaBits);                                            //Bytes restantes               n Bytes   19... 11+cant*4

        fos.close();
    }

    public void writeInFile(Object o, int cant, FileOutputStream fos) throws IOException {
        int numero  = (int) o;
        char[] longitudChar  = convertirNumeroChar(numero,cant);
        List<Byte> longitudByte = ByteEncodingHelper.EncodeSequence(longitudChar);
        byte[] longitudBit = this.ConvertByteListToPrimitives(longitudByte);
        fos.write(longitudBit);
        //Escribe en fos una cantidad de bytes = "cant"
    }

    public int getFrecuenciasUtilizadas(int[] distribucion){ //obitenene la cantidad de frecuencias que trae la imagen
        int cantidadFrecuencias = 0;
        for(int x = 0; x < distribucion.length; x++){
            if (distribucion[x] != 0)
                cantidadFrecuencias++;
        }
        return cantidadFrecuencias;
    }
    public Integer getProfundidadBit(BufferedImage img){ //Devuelve la profundidad de la imagen
        ColorModel color = img.getColorModel();
        return color.getPixelSize();
    }

    public byte[] ConvertByteListToPrimitives(List<Byte> input) {
        byte[] ret = new byte[input.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = input.get(i);
        }

        return ret;
    }

    public char[] convertirNumeroChar(int entrada,int n){   //Convierte un numero entrada a n bytes
        int aux = entrada;
        char[] rta = new char[8*n];
        for(int x = 0; x < rta.length; x++){
            if (aux >= Math.pow(2,rta.length-1-x)){
                rta[x] = '1';
                aux = (int) (aux - Math.pow(2,rta.length-1-x));
            } else{ rta[x] = '0';}
        }
        return rta;
    }

    public void insertarFrecuencias(FileOutputStream fos, int[] distribuciones,int cantidad) throws IOException {
        //Inserta los colores con su probabilidada de ocurrencia
        char[] colorChar = new char[1];
        char[] distribucionChar = new char[24];

        for(int i = 0;i<distribuciones.length;i++){
            if(distribuciones[i]!=0){
                colorChar = convertirNumeroChar(i,1); //1 byte para almacenar el color (0 a 255)

                List<Byte> colorByte = ByteEncodingHelper.EncodeSequence(colorChar);  //Codificado a byte
                byte[] colorBits = this.ConvertByteListToPrimitives(colorByte); //Binario

                distribucionChar = convertirNumeroChar(distribuciones[i],3);
                List<Byte> distribucionByte = ByteEncodingHelper.EncodeSequence(distribucionChar);
                byte[] distribucionBits = this.ConvertByteListToPrimitives(distribucionByte);

                fos.write(colorBits);           //Escribo 1 bit con el color
                fos.write(distribucionBits);    //Escribo 3 bits con la distribucion
                }
            }
    }

///--------------------------------------------------------------------------------------------------------//
//----------------------------          DESCOMPRIMIR             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//

    public  BufferedImage descomprimirImagen(Data<char[], Arbol,Integer,Integer,Integer> data){
        int sizeX = data.getSizeX();
        int sizeY = data.getSizeY();

        final BufferedImage res = createImage(sizeX,sizeY, data.getProfundidad());
        int index = 0;          //Creamos la imagen

        for(int x = 0; x < sizeX; x++)           //La recorremos
            for(int y = 0; y < sizeY; y++){
                int[] color = getColor(data.getContenido1(), data.getContenido2(), index);   //Obtenemos el color de esa posicion
                index = color[0];
                res.setRGB(x, y, (color[1]*256*256 + color[1] * 256 + color[1]));
            }
        return res;
    }

    public BufferedImage createImage(int sizeX,int sizeY,Integer profundidad) {

        switch (profundidad) {
            case 16:
                return new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_USHORT_565_RGB, createGreyscaleModel(profundidad));
            case 8:
                return new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_BYTE_INDEXED, createGreyscaleModel(profundidad));
            default:
                return new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_BYTE_BINARY, createGreyscaleModel(profundidad));
        }
    }

    public int[] getColor(char[] in, Arbol nodo, int index){  //Conseguimos el color en el arbol
        int[] rtrn = new int[2]; //En 0 el index, en 1 el color
        Arbol aux = nodo;

        while(aux.getHijoDerecho() != null){ //Con uno alcanza, por construccion
            if((in[index]) == '0'){
                aux = aux.getHijoDerecho();
            }else{  // == '1'
                aux = aux.getHijoIzquierdo();
            }
            index++;
        }

        rtrn[0] = index;
        rtrn[1] = aux.getColor();
        return rtrn;
    }

    public  void savePNG( final BufferedImage bi, final String path ){
        //Guardamos la imagen en formato bmp
        try {
            RenderedImage rendImage = bi;
            ImageIO.write(rendImage, "bmp", new File(path));
            //ImageIO.write(rendImage, "PNG", new File(path));
            //ImageIO.write(rendImage, "jpeg", new File(path));
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    private static final IndexColorModel createGreyscaleModel(int bitDepth) {

        int size = 0;
        if (bitDepth == 1 || bitDepth == 2 || bitDepth == 4 || bitDepth ==8 || bitDepth==16) {
            size = (int) Math.pow(2, bitDepth);
        } else {
            //logger error
            return null;
        }

        // generate the rgb and set the greyscale color.
        byte[] r = new byte[size];
        byte[] g = new byte[size];
        byte[] b = new byte[size];

        // The size should be larger or equal to 2, so we firstly set the start and end pixel color.
        r[0] = g[0] = b[0] = 0;
        r[size-1] = g[size-1] = b[size-1] = (byte)255;

        for (int i=1; i<size-1; i++) {
            r[i] = g[i] = b[i] = (byte)((255/(size-1))*i);
        }
        return new IndexColorModel(bitDepth, size, r, g, b);
    }



///--------------------------------------------------------------------------------------------------------//
//----------------------------          PRINT             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//
    public void printSequence(char[] sequence) {
        for (int i = 0; i < sequence.length; i++) {
            System.out.print(sequence[i]);
        }
        System.out.println();
    }
    public static void printMap(Map mp) { //Muestra el contenido del HashMap
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();


            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }

}