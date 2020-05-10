
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;


public class Ejercicio1 {

    public Ejercicio1(){}
    public float calcularMedia( BufferedImage imgOriginal){
        float sumaTonalidades = 0;

        for (int x = 0; x < imgOriginal.getWidth(); x++) {
            for (int y = 0; y < imgOriginal.getHeight(); y++) {
                float gris = getGris(imgOriginal,x,y);
                sumaTonalidades= sumaTonalidades+gris;
            }
        }
        return sumaTonalidades/(imgOriginal.getWidth()*imgOriginal.getHeight());
    }

    public double calcularDesvioEstandar(BufferedImage imgOriginal, float media){
        double desvioEstandar=0;
        int n = imgOriginal.getWidth()*imgOriginal.getHeight(); //2227000

        for (int x = 0; x < imgOriginal.getWidth(); x++) {
            for (int y = 0; y < imgOriginal.getHeight(); y++) {
                float gris = getGris(imgOriginal,x,y);
                desvioEstandar+=Math.pow((gris - media), 2);
            }
        }
        return Math.sqrt((desvioEstandar/(n)));
    }

    public int getGris(BufferedImage imgOriginal,int x,int y){
        int rgb = imgOriginal.getRGB(x, y);
        Color color = new Color(rgb, true);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        int gris = (r+g+b)/3; // con este se saca la escala de grises
        return gris;
    }
    public float calcularCovarianzaAB(BufferedImage imgA,BufferedImage imgB,float media1, float media2){
        float suma = 0;
        for (int x = 0; x < imgA.getWidth(); x++) {
            for (int y = 0; y < imgA.getHeight(); y++) {
                            int grisA = getGris(imgA,x,y);
                            int grisB = getGris(imgB,x,y);
                            suma = suma + ((grisA-media1)*(grisB-media2));
            }
        }
        return suma/(imgA.getWidth()*imgA.getHeight());
    }
   public double calcularFactorCorrelacionPearson(float covarianza,double desvioX,double desvioY){
        return covarianza/(desvioX*desvioY);
   }

   public void obtenerDistribuciones(BufferedImage img, int[] myIntArray ){
       for (int x = 0; x < img.getWidth(); x++) {
           for (int y = 0; y < img.getHeight(); y++) {
               int grisA = getGris(img,x,y);
               myIntArray[grisA] = myIntArray[grisA] + 1;
           }
       }
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
   public Vector<Arbol> inserTarOrdenado(Vector<Arbol> hojas, Arbol hoja) {

       for (int i = 0; i < hojas.size(); i++) {
           if (hojas.elementAt(i).getProbabilidad() > hoja.getProbabilidad()) {
               hojas.add(i, hoja);
               return hojas;
           }
       }
       hojas.add(hoja);
       return hojas;
   }
   public void obtenerSecuencias(HashMap<Integer,String> h, Arbol arbolito, String secuencia){
       if((arbolito.getHijoDerecho() == null) && (arbolito.getHijoIzquierdo() == null)){
           h.put(arbolito.getColor(),secuencia);
       }else{
           if(arbolito.getHijoDerecho() != null){
               secuencia+= "0";
               obtenerSecuencias(h,arbolito.getHijoDerecho(),secuencia);
           }
           if(arbolito.getHijoIzquierdo() != null){
               secuencia+= "1";
               obtenerSecuencias(h,arbolito.getHijoIzquierdo(),secuencia);
           }
       }


   }

   public Arbol getPadre(Vector<Arbol> hojas){
       System.out.println("color: "+ hojas.get(0).getColor());
        while (hojas.size() > 1){
            float nuevoProb = hojas.get(0).getProbabilidad() + hojas.get(1).getProbabilidad();
            Arbol huffman = new Arbol(hojas.get(0),hojas.get(1),nuevoProb);
            hojas.remove(1);
            hojas.remove(0);
            this.inserTarOrdenado(hojas, huffman);

        }
       return hojas.get(0);
   }


}

