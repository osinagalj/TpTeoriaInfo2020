public class Pseudo {

    public double CoeficienteCorrelacion(imagen A, imagen B){

        double Sumatoria, Suma_A, Suma_B, SumaCuadrada_A, SumaCuadrada_B = 0;

        for (CadaPixel){
            Suma_A = Suma_A + ColorPixel_A; //Llevamos la suma de los colores total
            Suma_B = Suma_B + ColorPixel_B;
            Sumatoria = Sumatoria + ColorPixel_A * ColorPixel_B;
            SumaCuadrada_A = SumaCuadrada_A + ColorPixel_A^2;
            SumaCuadrada_B = SumaCuadrada_B + ColorPixel_B^2;
        }
        double covarianza = (Cant_Pixeles *  Sumatoria) -  (Suma_A * Suma_B);
        double desvA = Cant_Pixeles*SumaCuadrada_A -  suma_A^2;
        double desvB = Cant_Pixeles*SumaCuadrada_B -  suma_B^2;

        return (covarianza) / raiz( desvA * desvB );
    }

    public double[] mediaYdesvio(imagen foto){
        double suma, sumaCuadrado = 0;

        for (cadaPixel) {
                distribuciones[colorPixel] += 1;
                suma+= colorPixel;
                sumaCuadrado += colorPixel^2;
        }
        double media = suma/cantPixeles;
        double desvio = raiz(sumaCuadrado/cantPixeles -  (suma/cantPixeles)^2);
        double rta[];
        rta[0] = media;
        rta[1] = desvio;
        return rta;
    }

    public arbol arbolDeHuffman(probabilidades, imagen){
        probabilidades[] = ordenar(probabilidades[]);    //Ordenamos las probabilidades de menor a mayor
        while (probabilidades[].size > 1){              //Mientras tengamos minimos 2 simbolos
            new arbol(probabilidad[0],probabilidad[1]);
            retirarProbabilidades;                      //Creamos un nodo con ambas probabilidades sumadas
            instertarArbol(probabilidades[], arbol)     //lo incertamos en las probabilidades
        }
        recorrerArbol(Hash(Simbolo,Secuencia));     //Recorremos generando secuencias para las hojas
        obtenerSecuencia(Imagen);                   //Recorremos cada pixel, codificando una secuencia
    }

    public archivo comprimir(secuencia){
        bytes1[] = codificarAByte(secuencia.Longitud);          //Escribo la longitud
        bytes2[] = codificarAByte(secuencia.cerosAdicionales);  //Escribo la cantidad de 0
        bytes3[] = codificarAByte(secuencia);                   //Escribo la secuencia
        archivo = write(bytes1[], bytes2[], bytes3[]);          //Guardo t0do en el archivo
    }

    public void descomprimir(archivo){
        Bytes[] = decodificarSecuencia(archivo);    //Convertimos la lista de bytes en una secuencia de char
        arbol = armarArbol(header(Bytes[]));        //Replicamos el arbol de Huffman
        for(secuencia(Bytes[])){        //Recorremos toda la lista restante
            if(0)                           //Si viene un 0 avanza en el arbol por izq
                arbol = arbol.izq;
            else                            //Si no, por derecha
                arbol = arbol.der;
            if(arbol = hoja) {              //Si una es hoja
                añadirImagen(arbol);            //Añadimos el color a la imagen
                arbol = Raiz;                   //Volvemos a la raiz
            }
        }
    }

}
