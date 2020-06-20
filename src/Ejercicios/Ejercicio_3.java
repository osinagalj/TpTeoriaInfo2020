package Ejercicios;

import javax.imageio.ImageIO;
import java.awt.*;
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
import Estructuras.Cosas;
import Estructuras.ByteEncodingHelper;


public class Ejercicio_3 {

    Writer wr ;
    public Ejercicio_3(Writer wr){
        this.wr = wr;
    }
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
                    return;           //Si son iguales primero quiero que este el de mayor nivel
                }
                i++; //Para que avance
            }
        }
        hojas.add(hoja); //Si llego aca es porque nunca se incerto => probabilidad mas grande, va al final
    }

    public Arbol getArbolHuffman(Vector<Arbol> hojas){
        //Hijo derecho   = mayor probabilidad
        //Hijo Izquierdo = menor probabilidad
        while (hojas.size() > 1){
            Arbol izq = hojas.get(0);
            Arbol der = hojas.get(1);
            hojas.remove(0);
            hojas.remove(0);

            Arbol huffman = new Arbol(izq,der);
            this.inserTarOrdenado(hojas, huffman);
        }
        return hojas.get(0);
    }

    public static void printMap(Map mp) { //Muestra el contenido del HashMap
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();


            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }

    public void obtenerSecuencias(HashMap<Integer,String> h, Arbol arbolito, String secuencia){
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

    public char[] codificarSecuencia(BufferedImage img, HashMap<Integer,String> secuencias){ //Chequeado

        ArrayList<Character> secuenciaArray = new ArrayList<Character>();

        Integer color;
        String aux;

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                color = (int)getGris(img,x,y);
                //System.out.println(color); //Capta bien el color
                aux = secuencias.get(color);
                //System.out.println(aux);  //Capta bien las secuencias
                for (int k = 0; k < aux.length(); k++){
                    secuenciaArray.add(aux.charAt(k));
                }
            }
        }

        char[] secuenciaChar = new char[secuenciaArray.size()];
        for (int x = 0; x < secuenciaChar.length; x++){
            secuenciaChar[x] = secuenciaArray.get(x);
        }

        return secuenciaChar;
    }

    public int getSimbolo(Arbol huffman, String secuencia, int pos){
        //pos=0
        if((huffman.getHijoDerecho() == null) && (huffman.getHijoIzquierdo() == null)){
            return huffman.getColor();
        }else{
            if(secuencia.length()> pos){
                String s = "1";
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
public void comprimirImagen(String path,BufferedImage img,HashMap<Integer,String> secuencias, int[] distribucion){

    //SECUENCIA
    char[] secuenciaChar = codificarSecuencia(img, secuencias) ; //Secuencia de chars
    List<Byte> secuenciaByte = ByteEncodingHelper.EncodeSequence(secuenciaChar);  //Codificado a byte
    byte[] secuenciaBits = this.ConvertByteListToPrimitives(secuenciaByte); //Binario

    //LONGITUD SECUENCIA
    char[] longitudChar = convertirNumeroChar(secuenciaChar.length,3);
    List<Byte> longitudByte = ByteEncodingHelper.EncodeSequence(longitudChar);
    byte[] longitudBit = this.ConvertByteListToPrimitives(longitudByte); //Añade un byte de mas en 0

    //CANTIDAD FRECUENCIAS
    int cantidadFrecuencias = 0;
    for(int x = 0; x < distribucion.length; x++){
        if (distribucion[x] != 0)
            cantidadFrecuencias++;
    }
    char[] cantidadFrecuenciasChar = convertirNumeroChar(cantidadFrecuencias,1); //1 byte, como mucho 255 frecuencias
    List<Byte> cantidadFrecuenciasByte = ByteEncodingHelper.EncodeSequence(cantidadFrecuenciasChar);
    byte[] cantidadFrecuenciasBits = this.ConvertByteListToPrimitives(cantidadFrecuenciasByte);

    //tamanio X
    char[] XChar = convertirNumeroChar(img.getWidth(),3);
    List<Byte> XByte = ByteEncodingHelper.EncodeSequence(XChar);
    byte[] XBit = this.ConvertByteListToPrimitives(XByte);

    //tamanio Y
    char[] YChar = convertirNumeroChar(img.getHeight(),3);
    List<Byte> YByte = ByteEncodingHelper.EncodeSequence(YChar);
    byte[] YBit = this.ConvertByteListToPrimitives(YByte);

    try{
        FileOutputStream fos = new FileOutputStream(path);

        fos.write(longitudBit);                         //Longitud de secuencia         3 Bytes   0,1,2
        fos.write(cantidadFrecuenciasBits);             //Cantidad de Frecuencias       1 Byte    3

        fos.write(XBit);                                //Anchura                       3 Bytes   4,5,6
        fos.write(YBit);                                //Altura                        3 Bytes   7,8,9

        insertarFrecuencias(fos,distribucion);          //Todas las frecuencias:  Color 1 Byte    10,14
        //                 Distribucion 3 Bytes   (11,12,13),(15,16,17)

        fos.write(secuenciaBits);                       //Bytes restantes               n Bytes   18...

        fos.close();
    }catch (IOException e) {
        System.out.println(e.getMessage());
    }
}

    public byte[] ConvertByteListToPrimitives(List<Byte> input) {
        byte[] ret = new byte[input.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = input.get(i);
        }

        return ret;
    }
    public void insertarFrecuencias(FileOutputStream fos, int[] distribuciones){
        try{
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
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
///--------------------------------------------------------------------------------------------------------//
//----------------------------          DESCOMPRIMIR             ------------------------------------------//
//--------------------------------------------------------------------------------------------------------//

    public BufferedImage createImage(int sizeX,int sizeY,Integer profundidad){
        if(profundidad.equals(16)){
            return new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_USHORT_565_RGB ,createGreyscaleModel(profundidad) );
        }else{
            if(profundidad.equals(8)){
                return new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_BYTE_INDEXED,createGreyscaleModel(profundidad));
            }else{
               return new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_BYTE_BINARY,createGreyscaleModel(profundidad));
            }
        }
    }
    public  BufferedImage map(Cosas<char[], Arbol,Integer,Integer> cosas, Integer profundidad){
        int sizeX = cosas.getSizeX();
        int sizeY = cosas.getSizeY();


        final BufferedImage res = createImage(sizeX,sizeY,profundidad); ; //=new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_INT_RGB);

/*
        if(profundidad.equals(16)){
            res = new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_USHORT_565_RGB ,createGreyscaleModel(profundidad) );
        }else{
            if(profundidad.equals(8)){
                res = new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_BYTE_INDEXED,createGreyscaleModel(profundidad));
            }else{
                res = new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_BYTE_BINARY,createGreyscaleModel(profundidad));
            }
        }
*/

        int index = 0;

        for(int x = 0; x < sizeX; x++)           //Recorremos la imagen
            for(int y = 0; y < sizeY; y++){
                int[] color = getColor(cosas.getContenido1(), cosas.getContenido2(), index);   //Obtenemos el color de esa posicion

                index = color[0];
                Color c = new Color(color[1],color[1],color[1]);
                res.setRGB(x, y, c.getRGB() );
                //res.setRGB(x, y, (color[1]*256*256 + color[1] * 256 + color[1]));
            }
        return res;
    }
    public int[] getColor(char[] in, Arbol nodo, int index){  //Mura
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
        try {
            RenderedImage rendImage = bi;
            ImageIO.write(rendImage, "bmp", new File(path));
            //ImageIO.write(rendImage, "PNG", new File(path));
            //ImageIO.write(rendImage, "jpeg", new File(path));
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }



    public char[] convertirNumeroChar(int entrada,int n){   //Convierte un numero a n bytes
        int aux = entrada;
        char[] rta = new char[8*n];
        for(int x = 0; x < rta.length; x++){
            if (aux >= Math.pow(2,rta.length-1-x)){
                rta[x] = '1';
                aux = (int) (aux - Math.pow(2,23-x));
            } else{ rta[x] = '0';}
        }
        return rta;
    }


    public Integer getProfundidadBit(BufferedImage img){
        //img es un BufferedImage
        ColorModel color = img.getColorModel();
        return color.getPixelSize();
    }
    private static final IndexColorModel createGreyscaleModel(int bitDepth) {

        // Only support these bitDepth(1, 2, 4) for now: Set the size.
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

}