

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.util.List;

public class ByteEncodingHelper {
    private static int bufferLength = 8;
    // Importante, este código funciona cuando la longitud de la secuencia es multiplo de 8.
    // Es parte del TPE modificar el código para que acepte secuencias de cualqueir tamaño
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
        if((sequence.length == i) &&  (bufferPos != bufferLength)){
            while(bufferPos < bufferLength){
                buffer = (byte) (buffer << 1);
                cerosExtra++;
                bufferPos++;
            }
            result.add(buffer);
        }
        return cerosExtra;
    }

    public static char[] DecodeSequence(String inputFilepath, int sequenceLength) {
        char[] restoredSequence = new char[sequenceLength];

        try {
            byte[] inputSequence = Files.readAllBytes(new File(inputFilepath).toPath());

            int globalIndex = 0;
            byte mask = (byte) (1 << (bufferLength - 1)); // mask: 10000000
            int bufferPos = 0;

            int i = 0; // indice en la lista de bytes (secuencia codificada)
            while (globalIndex < sequenceLength)
            {
                byte buffer = inputSequence[i];
                while (bufferPos < bufferLength) {

                    if ((buffer & mask) == mask) {  // 10000000
                        restoredSequence[globalIndex] = '1';
                    } else {
                        restoredSequence[globalIndex] = '0';
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

        return restoredSequence;
    }
}
