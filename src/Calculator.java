
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
   public void inserTarOrdenado(Vector<Arbol> hojas, Arbol hoja) {
        int i = 0;
       // for(int j = 0; )
       //el nivel mas alto tiene que ir primero, si tengo un 8(lvl 2) , 8(lvl 0)  entonces el 8 (lvl 3) va primero
       while ( i < hojas.size()) {
           if ( hoja.getProbabilidad() <= hojas.elementAt(i).getProbabilidad()) {
                   if(hoja.getProbabilidad() == hojas.elementAt(i).getProbabilidad()){
                       while((i<hojas.size()) && (hoja.getNivel() <= hojas.elementAt(i).getNivel())  ){
                           i++;
                       }

                   }

                   hojas.add(i, hoja);
                   return ;
           }
           i++;
       }

       hojas.add(hoja);
       return;
   }
   public Arbol getIndexMenorProb(Vector<Arbol> hojas){
        int menor = hojas.size()+1;
        double probMenor = 1;
        for(int i = 0;i<hojas.size();i++){
            if(probMenor > hojas.get(i).getProbabilidad()){
                probMenor = hojas.get(i).getProbabilidad();
                menor = i;
            }
        }
        Arbol a = hojas.remove(menor);

        return a;
   }
    public void inserTarOrdenado2(Vector<Arbol> hojas, Arbol hoja) {
        int i = 0;

            while((i<hojas.size()) && (hoja.getNivel() < hojas.elementAt(i).getNivel()) ){ // tiene que ir <=
                i++;
                if((hoja.getNivel() == hojas.elementAt(i).getNivel())){
                    while ((i<hojas.size()) && (hoja.getNivel() == hojas.elementAt(i).getNivel()) && (hojas.elementAt(i).getProbabilidad() <= hoja.getProbabilidad())){
                        i++;
                    }

                    hojas.add(i, hoja);
                    return;
                }

            }
            hojas.add(i, hoja);
             return ;

            //mientras sea de nivel menor

        }


    public Arbol getPadre2(Vector<Arbol> hojas){

        //Hijo derecho   = Hijo Arriba (mas prob, si tienen igual prob entonces el que MENOS nivel tenga)
        //Hijo Izquierdo = Hijo abajo (menos prob o si son iguales el q tenga mas nivel)
        System.out.println("Mostrando la h");
        while (hojas.size() > 1){

            for(int j = 0;j<hojas.size();j++){
                System.out.print((int) (hojas.get(j).getProbabilidad()*41) + "(" + hojas.get(j).getNivel() +") ");
            }
            System.out.println();



            Arbol izq = getIndexMenorProb(hojas);
            Arbol der = getIndexMenorProb(hojas);
            if(izq.getNivel() != der.getNivel()){
                if (izq.getProbabilidad() < der.getProbabilidad()){
                    Arbol aux = der;
                    der = izq;
                    izq = aux;
                }
            }


            Arbol huffman = new Arbol(izq,der);


            System.out.println("hijo izquierdo actual :" +  (int)(huffman.getHijoIzquierdo().getProbabilidad()*41)+ "("+ huffman.getHijoIzquierdo().getNivel() + ")" );
            System.out.println("hijo derecho actual:" +  (int)(huffman.getHijoDerecho().getProbabilidad()*41) + "("+ huffman.getHijoDerecho().getNivel()+ ")" );

            System.out.println();
            huffman.setColor(huffman.getHijoDerecho().getColor()+huffman.getHijoIzquierdo().getColor());
            huffman.setNivel(huffman.getNivel()+1);
            this.inserTarOrdenado2(hojas, huffman);
        }
        System.out.println("Termino el padre");
        return hojas.get(0);
    }
   public Arbol getPadre(Vector<Arbol> hojas){

        //Hijo derecho   = Hijo Arriba (mas prob, si tienen igual prob entonces el que MENOS nivel tenga)
        //Hijo Izquierdo = Hijo abajo (menos prob o si son iguales el q tenga mas nivel)
        while (hojas.size() > 1){
            for(int i = 0; i <hojas.size();i++){
                System.out.println(" prob : "+ hojas.get(i).getProbabilidad()*41+ "  nivel: "+hojas.get(i).getNivel() );
            }

            System.out.println("nodo 0 :" + hojas.get(0).getProbabilidad()*41);

            Arbol huffman = new Arbol();
            huffman.setHijoDerecho(hojas.get(1));
            huffman.setHijoIzquierdo(hojas.get(0));
            huffman.setProbabilidad(hojas.get(1).getProbabilidad() + hojas.get(0).getProbabilidad());
            huffman.setColor(hojas.get(1).getColor() + hojas.get(0).getColor());
            huffman.addCadena(" (" + hojas.get(0).getColor() + "-" + hojas.get(1).getColor() + ")" );
            System.out.println("hijo izquierdo actual :" +  huffman.getHijoIzquierdo().getProbabilidad()*41+ " nivel :"+ huffman.getHijoIzquierdo().getNivel() + " color: " + huffman.getHijoIzquierdo().getColor());
            System.out.println("hijo derecho actual:" +  huffman.getHijoDerecho().getProbabilidad()*41 + " nivel :"+ huffman.getHijoDerecho().getNivel()+ " color: " + huffman.getHijoDerecho().getColor() );
            System.out.println(" Cadena actual: " + huffman.getCadena());
            hojas.remove(0);
            hojas.remove(0);
            huffman.setNivel(huffman.getNivel()+1);
            this.inserTarOrdenado(hojas, huffman);
        }
       System.out.println("Termino el padre");
       return hojas.get(0);
   }
    public void obtenerSecuencias(HashMap<Integer,String> h, Arbol arbolito, String secuencia){
        if((arbolito.getHijoDerecho() == null) && (arbolito.getHijoIzquierdo() == null)){

            h.put(arbolito.getColor(),secuencia);
        }else{

            if(arbolito.getHijoDerecho() != null){
                    obtenerSecuencias(h,arbolito.getHijoDerecho(),secuencia+"0");
            }
            if(arbolito.getHijoIzquierdo() != null){
                obtenerSecuencias(h,arbolito.getHijoIzquierdo(),secuencia+"1");
            }

        }
    }
    public void imprimirArbolDer(Arbol a,int nivel){
        if(a!= null){
            System.out.println(a.getProbabilidad()*41+ " color : " +a.getColor() + " nivel: "+ a.getNivel());
            imprimirArbolDer(a.getHijoDerecho(),nivel+1);
        }
    }
    public void imprimirArbolIzq(Arbol a,int nivel){
        if(a!= null){
            System.out.println(a.getProbabilidad()*41+ " color : " +a.getColor()+ " nivel: "+ a.getNivel());
            imprimirArbolIzq(a.getHijoIzquierdo(),nivel+1);
        }
    }


}

