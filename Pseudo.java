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

    public void arboldeHuffman(){

    }

    public void comprimir(){

    }

    public void descomprimir(){

    }
}
