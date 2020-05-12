
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;


public class Calculator {

    public Calculator(){}

    public double getGris(BufferedImage imgOriginal,int x,int y){
        int rgb = imgOriginal.getRGB(x, y);
        Color color = new Color(rgb, true);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        double gris = (double)(r+g+b)/3; // con este se saca la escala de grises
        return gris;
    }

   public double ejercicio1(BufferedImage imgA,BufferedImage imgB, int h, int w,int n){
       double Xi,Yi,sumatoriaXiYi = 0, sumaXi= 0,sumaYi = 0,sumaXiCuadrado=0,sumaYiCuadrado=0;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                Xi =  getGris(imgA, x, y);
                Yi = getGris(imgB, x, y);
                sumatoriaXiYi +=  Xi * Yi;
                sumaXi +=  Xi;
                sumaYi +=  Yi;
                sumaXiCuadrado +=  Xi * Xi;
                sumaYiCuadrado +=  Yi* Yi;
            }
        }
       double covarianza = (n *  sumatoriaXiYi) -  (sumaXi * sumaYi);
       double desvA = n*sumaXiCuadrado -  Math.pow(sumaXi, 2);
       double desvB = n*sumaYiCuadrado -  Math.pow(sumaYi, 2);

       return (covarianza) / Math.sqrt(desvA*desvB);
   }

   public void ejercicio2(BufferedImage img, int[] distribuciones, int n,double [] parametros){
       double suma = 0;
       double sumaXiCuadrado = 0;
       int Xi;
       for (int x = 0; x < img.getWidth(); x++) {
           for (int y = 0; y < img.getHeight(); y++) {
               Xi= (int)getGris(img,x,y);
               distribuciones[Xi] += 1;
               suma+= Xi;
               sumaXiCuadrado += Math.pow(Xi,2);
           }
       }
       double media = suma/n;
       double desvio = Math.sqrt(sumaXiCuadrado/n -  Math.pow(suma/n, 2));
       parametros[0] = media;
       parametros[1] = desvio;
   }
   //EJERCICIO 3
   public HashMap<Integer,Integer> ordenarFI(int[] myIntArray){
       HashMap<Integer,Integer> frecuenciasOrdenadas = new HashMap<Integer, Integer>();
       for(int i=0; i<myIntArray.length;i++){
           if(myIntArray[i] != 0) {
               frecuenciasOrdenadas.put(myIntArray[i],i);
           }
       }
       return frecuenciasOrdenadas;
   }
   //testeado el insertar
   public Vector<Arbol> inserTarOrdenado(Vector<Arbol> hojas, Arbol hoja) {

       for (int i = 0; i < hojas.size(); i++) {
           if ( hoja.getProbabilidad() <= hojas.elementAt(i).getProbabilidad()) {
               hojas.add(i, hoja);
               return hojas;
           }
       }
       hojas.add(hoja);
       return hojas;
   }
   public Arbol getPadre(Vector<Arbol> hojas){

        while (hojas.size() > 1){
            System.out.println("Arreglo");
            for(int i = 0; i< hojas.size();i++){
                System.out.print(hojas.get(i).getProbabilidad() +  "  ");
            }
            double nuevoProb = hojas.get(0).getProbabilidad() + hojas.get(1).getProbabilidad();
            Arbol huffman = new Arbol(hojas.get(0),hojas.get(1),-1,nuevoProb);
            hojas.remove(0);
            hojas.remove(0);
            this.inserTarOrdenado(hojas, huffman);

        }
       System.out.println("Termino el padre");
       return hojas.get(0);
   }
    public void obtenerSecuencias(HashMap<Integer,String> h, Arbol arbolito, String secuencia){
        if((arbolito.getHijoDerecho() == null) && (arbolito.getHijoIzquierdo() == null)){
            System.out.println("ENCONTRO UN COLOR DE MIERDA color: " + arbolito.getColor());
            System.out.println("secuencia: " + secuencia);
            h.put(arbolito.getColor(),secuencia);
        }else{
            System.out.println("secuencia actual: " + secuencia);
            System.out.println("prob actual: " + arbolito.getProbabilidad());
            if(arbolito.getHijoDerecho() != null){
                if(arbolito.getHijoDerecho().getProbabilidad()>arbolito.getHijoIzquierdo().getProbabilidad()){
                    obtenerSecuencias(h,arbolito.getHijoDerecho(),secuencia+"1");
                }else {
                    //el derecho es menor o igual
                        obtenerSecuencias(h, arbolito.getHijoDerecho(), secuencia + "0");
                }
            }
            if(arbolito.getHijoIzquierdo() != null){
                if(arbolito.getHijoDerecho().getProbabilidad()<arbolito.getHijoIzquierdo().getProbabilidad()){
                    obtenerSecuencias(h,arbolito.getHijoIzquierdo(),secuencia+"1");
                }else{
                    //el izquierdo es menor o igual
                    obtenerSecuencias(h,arbolito.getHijoIzquierdo(),secuencia+"1");

            }}
        }
    }
    public void imprimirArbol(Arbol a,int nivel){
        if(a!= null){
            if((a.getHijoDerecho() == null) && (a.getHijoIzquierdo() == null)) {
                System.out.print("Hoja Color: "+a.getColor() +"     ");
            }
            System.out.println(a.getProbabilidad() + "   nivel: " + nivel);
            imprimirArbol(a.getHijoIzquierdo(),nivel+1);
            imprimirArbol(a.getHijoDerecho(),nivel+1);
        }
    }


}

