

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ByteEncodingHelper {
    private static int bufferLength = 8;

    public static int EncodeSequence(char[] sequence,List<Byte> result) {
        //List<Byte> result = new ArrayList<Byte>();
        int cerosExtra = 0;
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
        if (bufferPos != bufferLength){
            while(bufferPos < bufferLength){
                buffer = (byte) (buffer << 1);
                cerosExtra++;
                bufferPos++;
            }
            result.add(buffer);
        }
        return cerosExtra;
    }

    public static Par<char[],Arbol> DecodeSequence(String inputFilepath) {
        Par<char[],Arbol> cosas = new Par<char[],Arbol>();
        try {

            //Leo tod0 en:

            //CAMBIAR A LA MODALIDAD CON BUFFERS, ESTO SE ROMPE
            String[] secuencias = Files.readAllLines(new File(inputFilepath).toPath()).toArray(new String[0]);


            int ceros = Integer.parseInt(secuencias[0]);      //ESTOS DOS ESTAN SIEMPRE
            int sequenceLength = Integer.parseInt(secuencias[1]);

            Vector<Integer> color = new Vector<Integer>();
            Vector<Integer> frecuencia = new Vector<Integer>();

            int i = 2;
            while (secuencias[i] != "corta") {
                color.add(Integer.parseInt(secuencias[i]));        //Tiene el color
                frecuencia.add(Integer.parseInt(secuencias[i+1]));  //Tiene la frecuencia del color
                //añadir a algo para mandar a hacer arbol
                i = i+2;
            }

            //Crear arbol de huffman

            //Se hace esto
            byte[] inputSequence = Files.readAllBytes(new File(inputFilepath).toPath());

            int globalIndex = 0;
            byte mask = (byte) (1 << (bufferLength - 1)); // mask: 10000000
            int bufferPos = 0;

            while (globalIndex < sequenceLength-ceros)
            {
                byte buffer = inputSequence[i];
                while (bufferPos < bufferLength) {

                    if ((buffer & mask) == mask) {  // 10000000
                        cosas.contenido1[globalIndex] = '1';
                    } else {
                        cosas.contenido1[globalIndex] = '0';
                    }

                    buffer = (byte) (buffer << 1);
                    bufferPos++;
                    globalIndex++;

                    if (globalIndex == sequenceLength) {
                        break;
                    }
                }

                i++;
                bufferPos = 0;
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return cosas;
    }
}
