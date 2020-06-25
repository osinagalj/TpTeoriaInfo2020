package Estructuras;

import Ejercicios.Ejercicio_3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ByteEncodingHelper {
    private static int bufferLength = 8;

    public static List<Byte> EncodeSequence(char[] sequence) {
        List<Byte> result = new ArrayList<Byte>();
        byte buffer = 0;
        int bufferPos = 0;
        int i = 0;
        while (i < sequence.length) {
            // La operación de corrimiento pone un '0'
            buffer = (byte) (buffer << 1);
            bufferPos++;
            if (sequence[i] == '1') {
                buffer = (byte) (buffer | 1);  // 0 0 0 0 0 0 0 1
            }

            if (bufferPos == bufferLength) {
                result.add(buffer);
                buffer = 0;
                bufferPos = 0;
            }

            i++;
        }
        if (bufferPos != 0){
            while(bufferPos < bufferLength){
                buffer = (byte) (buffer << 1);
                bufferPos++;
            }
            result.add(buffer);
        }
        return result;
    }

    public static int traducirBytes(byte[] bytes, int cantidad){
        //Traduce a un numero una secuencia de cierta cantidad de bytes
        int rta = 0;
        byte mask = (byte) (1 << (bufferLength - 1)); // mask: 10000000
        byte aux;
        for (int x = 0 ; x < cantidad; x++){
            aux = bytes[cantidad-1-x];
            int bufferPos = 0;
            while (bufferPos < bufferLength) {
                if ((aux & mask) == mask) {  // 10000000
                    rta = rta + (int) (Math.pow(2,(7-bufferPos+(8*x))));
                }
                aux = (byte) (aux << 1);
                bufferPos++;
            }
        }
        return rta;
    }

    public static Data<char[], Arbol,Integer,Integer,Integer> DecodeSequence(String inputFilepath) {
        //decodifica el string a un conjunto de cosas necesarias para la reconstruccion de la imagen
        Data<char[], Arbol,Integer,Integer,Integer> data = new Data<char[], Arbol,Integer,Integer,Integer>();
        try {
            Ejercicio_3 ejercicio3 = new Ejercicio_3();
            //Leo todos los bytes
            byte[] inputSequence = Files.readAllBytes(new File(inputFilepath).toPath());
            byte[] auxiliar = new byte[3];

            auxiliar[0] = inputSequence[0];                     //Extramos la profundidad
            int profundidad = traducirBytes(auxiliar,1);

            for (int x = 1; x < 4; x++)
                auxiliar[x-1] = inputSequence[x];
            int longitud = traducirBytes(auxiliar,3);  //La longitud de la secuencia

            char[] secuenciaChar = new char[longitud];

            auxiliar[0] = inputSequence[4];                     //la cantidad de frecuencias
            int cantFrecuencias = traducirBytes(auxiliar,1);

            for (int x = 0; x < 3; x++)
                auxiliar[x] = inputSequence[5+x];
            int X = traducirBytes(auxiliar,3);          //Anchura

            for (int x = 0; x < 3; x++)
                auxiliar[x] = inputSequence[8+x];               //Altura
            int Y = traducirBytes(auxiliar,3);

            Vector<Arbol> frecuencias = new Vector<Arbol>();        //Frecuencias color-distribucion

            for(int x = 0; x<cantFrecuencias; x++ ){
                auxiliar[0] = inputSequence[11+x*4];              //Primero el color
                int color = traducirBytes(auxiliar,1);
                for (int y = 0; y < 3; y++)                       //Y luego los 3 de su distribucion
                    auxiliar[y] = inputSequence[12+x*4+y];
                double probabilidad = (double) traducirBytes(auxiliar,3)/(X*Y);
                Arbol aux = new Arbol(color,probabilidad);
                ejercicio3.inserTarOrdenado(frecuencias,aux);
            }

            Arbol raiz = ejercicio3.getArbolHuffman(frecuencias);  //Generamos el arbol de Huffman

            int i = 11+4*cantFrecuencias;                   //Decodificamos el resto de la secuencia
            int globalIndex = 0;
            byte mask = (byte) (1 << (bufferLength - 1)); // mask: 10000000
            int bufferPos = 0;
            byte buffer;

            while (globalIndex < longitud) {

                buffer = inputSequence[i];
                while ((bufferPos < bufferLength)&&(globalIndex < longitud)) { //Si el buffer no termina, pero la longitud si, se rompe

                    if ((buffer & mask) == mask) {  // 10000000
                        secuenciaChar[globalIndex] = '1';
                    } else {
                        secuenciaChar[globalIndex] = '0';
                    }

                    buffer = (byte) (buffer << 1);
                    bufferPos++;
                    globalIndex++;

                    if (globalIndex == longitud) {
                        data.setContenido1(secuenciaChar);
                        data.setSizeX(X);
                        data.setSizeY(Y);
                        data.setContenido2(raiz);
                        data.setProfundidad(profundidad);
                    }
                }

                i++;
                bufferPos = 0;
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return data;
    }
}