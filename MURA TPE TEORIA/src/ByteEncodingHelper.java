import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
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

    public static int traducirBytes(Byte[] bytes, int cantidad){
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

    public static Cosas<char[],Arbol,Integer,Integer> DecodeSequence(String inputFilepath) {
        Cosas<char[],Arbol,Integer,Integer> cosas = new Cosas<char[],Arbol,Integer,Integer>();
        try {

            Calculator calculator = new Calculator();

            //Leo todos los bytes
            byte[] inputSequence = Files.readAllBytes(new File(inputFilepath).toPath());

            Byte[] auxiliar = new Byte[3];

            for (int x = 0; x < 3; x++)
                auxiliar[x] = inputSequence[x];
            int longitud = traducirBytes(auxiliar,3);  //3 bytes

            char[] secuenciaChar = new char[longitud];


            auxiliar[0] = inputSequence[3];
            int cantFrecuencias = traducirBytes(auxiliar,1);

            for (int x = 0; x < 3; x++)
                auxiliar[x] = inputSequence[4+x];
            int X = traducirBytes(auxiliar,3);

            for (int x = 0; x < 3; x++)
                auxiliar[x] = inputSequence[7+x];
            int Y = traducirBytes(auxiliar,3);


            Vector<Arbol> frecuencias = new Vector<Arbol>();

            for(int x = 0; x<cantFrecuencias; x++ ){
                auxiliar[0] = inputSequence[10+x*4];              //Primero el color
                int color = traducirBytes(auxiliar,1);
                for (int y = 0; y < 3; y++)                       //Y luego los 3 de su distribucion
                    auxiliar[y] = inputSequence[11+x*4+y];
                double probabilidad = (double) traducirBytes(auxiliar,3)/(X*Y);
                Arbol aux = new Arbol(color,probabilidad);
                calculator.inserTarOrdenado(frecuencias,aux);
            }
            Arbol raiz = calculator.getArbolHuffman(frecuencias);

            int i = 10+4*cantFrecuencias;
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
                        cosas.setContenido1(secuenciaChar);
                        cosas.setSizeX(X);
                        cosas.setSizeY(Y);
                        cosas.setContenido2(raiz);
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
