import java.util.Scanner;
import java.util.Vector;

public class Codigos {

    public Codigos(){

    }


    public void LZW(String entrada, String simbolos){

        Vector<String> diccionario = new Vector<String>();

        for(int x = 0 ; x < simbolos.length(); x++){
            diccionario.add(String.valueOf(simbolos.charAt(x)));
        }

        int fin = 0;
        int primero = 0;

        while (fin != entrada.length()){
            

            fin++;
        }
    }

    public double log2 (double x){
        return (Math.log10(x)/Math.log10(2));
    }

    public void RuidoyPerdidaMatrizYdadoX(double[][] matriz){

        double ruidoPerdida = 0;
        for(int y = 0; y < matriz.length-1; y++){             //Primero por columnas
            double aux = 0;
            for(int x = 0 ; x < matriz[y].length-1; x++){         //De arriba abajo, izq a derecha
                if (matriz[x][y] != 0)
                    aux = aux + (matriz[x][y] * log2(matriz[x][y]));
            }
            ruidoPerdida = ruidoPerdida + (aux * matriz[matriz[y].length-1][y]);
        }
        System.out.println("El ruido H(Y/X) es de: " + (-ruidoPerdida));


        ruidoPerdida = 0;
        for(int x = 0; x < matriz.length-1; x++){             //Primero por filas
            double aux = 0;
            for(int y = 0 ; y < matriz[x].length-1; y++){        //De izq a derecha, arriba abajo
                if (matriz[x][y] != 0)
                    aux = aux + (matriz[x][y] * log2(matriz[x][y]));
            }
            ruidoPerdida = ruidoPerdida + (aux * matriz[x][matriz[x].length-1]);
        }
        System.out.println("La perdida H(X/Y) es de: " + (-ruidoPerdida));

    }

    public double[][] generarMatrizYdadoX(int x, int y){ //Chequeado
        x = x +1;
        y = y +1; //Guardar las marginales
        double[][] rtrn = new double[x][y];

        for(int j = 0; j < y-1; j++){  //recorro primero por columnas
            for(int k = 0 ; k < x-1; k++){        //desps por filas
                Scanner sc = new Scanner(System.in);
                System.out.println("inserte probabilidad de y" + (j+1) + ", dado x" + (k+1) +":");
                rtrn[k][j] = sc.nextDouble();
            }
        }

        for(int k = 0; k < x-1 ; k++){
            Scanner sc = new Scanner(System.in);
            System.out.println("ingrese marginal de x" + (k+1));
            rtrn[k][y-1]= sc.nextDouble();
        }

        for(int k = 0; k < y-1 ; k++){
            double aux = 0;
            for(int j = 0; j < x-1 ; j++) {
                aux = aux + rtrn[j][k]*rtrn[y-1][j];
            }
            rtrn[x-1][k] = aux;
        }
        return rtrn;
    }


    public static void main(String[] args) {
        Codigos task = new Codigos();

        //Descomentar para generar una matriz condicional Y/X, y obtener su ruido y perdida
        //Requiere la tabla y las probabilidades marginales de x

        int x = 2; //Cantidad filas     (Cuantos para abajo)     ¡Sin contar marginales ni nada raro!
        int y = 2; //Cantidad columnas  (Cuantos para la derecha)

        task.RuidoyPerdidaMatrizYdadoX(task.generarMatrizYdadoX(x,y));
    }
}
