/*public class Pseudo {

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

        Ordenar nodos con probabilidades de menor a mayor

        while (Haya mas de una probabilidad){              
            Crear arbol(las dos probabilidades mas bajas);
            retirarProbabilidades;                      
            añadimos el nuevo nodo     
        }

        recorrerArbol(arbol);     	//Guardamos pares "entrada" -> "secuencia"
        obtenerSecuencias(Todos los pixeles de la imagen);            
    }

    public archivo comprimir(secuencia){
        Escribir la longitud en bytes;
        Escribir entradas con frecuencias en bytes;
	Transformar secuencia de char a secuencia de bytes; //Dado por la catedra
        Escribir la secuencia;

        Guardar todo en un archivo;
    }

    public void descomprimir(archivo){
        Convertir la lista de bytes en una secuencia de char
        armarArbol(simbolos y frecuencias);      
  
        for(Secuencia){       
            if(0)                           //Si viene un 0 avanza en el arbol por izq
                arbol = arbol.izq;
            else                            //Si no, por derecha
                arbol = arbol.der;
            if(arbol = hoja) {              
                añadir color en la imagen;
                arbol = Raiz;               //Volvemos a la raiz
            }
        }
    }

}
*/