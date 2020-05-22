

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.List;

public class Calculator {

    public Calculator(){}

    public double getGris(BufferedImage imgOriginal,int x,int y){
        int rgb = imgOriginal.getRGB(x, y);
        Color color = new Color(rgb, true);
        return color.getRed(); // usamos el red
        //double gris = (double)(r+g+b)/3; // con este se saca la escala de grises, pero como las imagenenes vienen en escala de grises, para que sea mas eficiente podemos tomar uno de los 3, ya uqe los 3 son iguales siempre. Esto lo podemos hacer porque sabemos de antemano que no nos van a venir imagenes de color
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


               Iterator it2 = frecuenciasOrdenadas.entrySet().iterator();
               while (it2.hasNext()) {
                   Map.Entry pair = (Map.Entry)it2.next();
                   double x = (double) pair.getKey();
                   if(x >= myIntArray[i]){
                       frecuenciasOrdenadas.put(myIntArray[i],i);
                   }

                   it2.remove();
               }

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
        double probMenor = 1.0;
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
        while((i<hojas.size()) && (hoja.getNivel() <= hojas.elementAt(i).getNivel()) ){ // tiene que ir <=
                i++;
            if((hoja.getNivel() == hojas.elementAt(i).getNivel())) {
                while ((i < hojas.size()) && (hoja.getNivel() == hojas.elementAt(i).getNivel()) && (hojas.elementAt(i).getProbabilidad() <= hoja.getProbabilidad())) {
                    i++;
                }
                hojas.add(i, hoja);
                return;
            }
        }
        hojas.add(i, hoja);
        return ;
    }

    public Arbol getArbolHuffman(Vector<Arbol> hojas){
        //Hijo derecho   = Hijo Arriba (mas prob, si tienen igual prob entonces el que MENOS nivel tenga)
        //Hijo Izquierdo = Hijo abajo (menos prob o si son iguales el q tenga mas nivel)
        while (hojas.size() > 1){
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
            huffman.setNivel(huffman.getNivel()+1);
            this.inserTarOrdenado2(hojas, huffman);
        }
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

            System.out.println("hijo izquierdo actual :" +  huffman.getHijoIzquierdo().getProbabilidad()*41+ " nivel :"+ huffman.getHijoIzquierdo().getNivel() + " color: " + huffman.getHijoIzquierdo().getColor());
            System.out.println("hijo derecho actual:" +  huffman.getHijoDerecho().getProbabilidad()*41 + " nivel :"+ huffman.getHijoDerecho().getNivel()+ " color: " + huffman.getHijoDerecho().getColor() );

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

   public void ordenarHojas(int[] distribucion, Vector<Arbol> hojas, int n){
       for(int i=0; i<distribucion.length;i++){
           if(distribucion[i] != 0) {
               Arbol hoja = new Arbol(i, (double)distribucion[i]/n);
               hojas.add(hoja);
           }
       }
       Collections.sort(hojas);
   }


   public char[] pasarAArreglo(ArrayList<Character> secuencia,char[] arr){
        //preguntar si podemos meter toda la secuencia de huffman en un string y despues pasarlo a un arreglo de char, porque en el peor de los casos ocupa la mitad del total de un string, ocupa 2227000 * 6 y un string tiene el doble
       for(int i = 0;i<secuencia.size();i++){
            arr[i] = secuencia.get(i);
        }
       return arr;
   }
   public ArrayList<Character> comprimirImagen(BufferedImage img,HashMap<Integer,String> codigos){
       ArrayList<Character> secuencia = new ArrayList<Character>();
        int simbolo;
       Iterator it2;
       String s = "";
        for (int x = 0; x < img.getWidth(); x++) { //
           for (int y = 0; y < img.getHeight(); y++) { //
               simbolo = (int)getGris(img,x,y);

               it2 = codigos.entrySet().iterator();
               while (it2.hasNext()) {
                   Map.Entry pair = (Map.Entry)it2.next();
                   if((int)pair.getKey() == simbolo){
                       s = (String)pair.getValue();
                       //System.out.println(s);
                   }
               }
               for(int z=0;z<s.length();z++){
                   secuencia.add(s.charAt(z));
               }

           }
       }
   return secuencia;
   }

   public int getSimbolo(Arbol huffman,String secuencia,int pos){
       //pos=0
       if((huffman.getHijoDerecho() == null) && (huffman.getHijoIzquierdo() == null)){
           return huffman.getColor();
       }else{
           if(secuencia.length()> pos){
               String s = "1";
               if(secuencia.charAt(pos) == s.charAt(0)){
                   return getSimbolo(huffman.getHijoIzquierdo(),secuencia,pos+1);
               }else{
                   return getSimbolo(huffman.getHijoDerecho(),secuencia,pos+1);
               }
           }else{
               return -1;
           }
       }
   }
    public ArrayList<Character> descomprimirImagen(char[] entrada,Arbol huffman){
        ArrayList<Character> secuencia = new ArrayList<Character>();

        return secuencia;
    }


   public void generarHistograma(int[]distribuciones,String titulo) throws IOException {

       DefaultCategoryDataset ds = new DefaultCategoryDataset();
       CategoryAxis categoryAxis = new CategoryAxis("Tonalidades");
       categoryAxis.setCategoryMargin(.1);
       ValueAxis valueAxis = new NumberAxis("Frecuencias");
       StackedBarRenderer renderer = new StackedBarRenderer();
       renderer.setBarPainter(new StandardBarPainter());
       renderer.setDrawBarOutline(false);
       renderer.setShadowVisible(false);
       renderer.setBaseItemLabelsVisible(true);
       renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
       CategoryPlot plot = new CategoryPlot( ds,
               categoryAxis,
               valueAxis,
               renderer);
       plot.setOrientation(PlotOrientation.VERTICAL);

       JFreeChart jf = new JFreeChart( titulo,
               JFreeChart.DEFAULT_TITLE_FONT,
               plot,
               true);

       int j = 0;
       for(int i = 0; i<256; i++ ){
           if(distribuciones[i] != 0){
               ds.addValue(distribuciones[i],String.valueOf(i),String.valueOf(i));
               plot.getRenderer().setSeriesPaint(j, new Color(i, i, i));
               j++;
           }
       }
       jf.setBackgroundPaint(new Color(182, 235, 176));
       jf.getPlot().setBackgroundPaint( new Color(73, 144, 66));

       ChartFrame f = new ChartFrame("Histograma",jf);
       f.setSize(1200,600);
       f.setLocationRelativeTo(null);
       ChartUtilities.saveChartAsPNG(new File("src\\Histogramas\\"+titulo+".png"), jf, 600, 300 );
   }

















    public void printSequence(char[] sequence) {
        for (int i = 0; i < sequence.length; i++) {
            System.out.print(sequence[i]);
        }
        System.out.println();
    }

    public byte[] ConvertByteListToPrimitives(List<Byte> input) {
        byte[] ret = new byte[input.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = input.get(i);
        }

        return ret;
    }

   public int getColor(char[] in,Arbol huffman,Vector<Integer> v_pos){
       // System.out.println("pos: "+v_pos.get(0));
       //System.out.println("IN: "+in.length);
        if(v_pos.get(0)<in.length){
            int pos = v_pos.get(0);
            char c = in[pos];
            pos++;
            v_pos.remove(0);
            String s ="";
            s=s+c;
            //System.out.println("SECUENCIA :" + s);
            int x = getSimbolo(huffman,s,0);
            while(pos<in.length && x == -1){
                char c2 = in[pos];
                pos++;
                s=s+c2;
                x=getSimbolo(huffman,s,0);
                //System.out.println("SECUENCIA :" + s);
            }
            v_pos.add(0,pos);
            if(pos>in.length){
                return -1;
            }
            return x;
        }else{
            //System.out.println("SE ROMPE ACA");
            return -1;

        }

    }
    public  BufferedImage map(int sizeX, int sizeY , char[]in, Arbol huffman,int ceros){
        final BufferedImage res = new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_INT_RGB );
        Vector<Integer> v_pos = new Vector<Integer>();
        v_pos.add(0,0);
        int x = 0;
        int y = 0;
        while(v_pos.get(0) <= (in.length-ceros)){
            int color = this.getColor(in,huffman,v_pos);
            if(color != -1){
                Color c = new Color(color,color,color);
                res.setRGB(x, y, c.getRGB() );
            }
            y++;
            if(y >= sizeY){
                //System.out.println("width:" + sizeX);
                //System.out.println("valor de x:" + x);
                x++;
                y=0;
            }
        }
        return res;
    }

    public  void savePNG( final BufferedImage bi, final String path ){
        try {
            RenderedImage rendImage = bi;
            ImageIO.write(rendImage, "bmp", new File(path));
            //ImageIO.write(rendImage, "PNG", new File(path));
            //ImageIO.write(rendImage, "jpeg", new File(path));
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    public void insertarFrecuencias(FileOutputStream fos, int[] distribuciones){
        try{
            for(int i = 0;i<distribuciones.length;i++){
                if(distribuciones[i] != 0){
                    String s = i+"["+distribuciones[i]+"]"+"\n";
                    fos.write(s.getBytes(Charset.forName("UTF-8")));
                }
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }



    }
    public void comprimirImagen(String path,BufferedImage img,HashMap<Integer,String> secuencias,int[]distribuciones){

        ArrayList<Character> secuencia = new ArrayList<Character>();
        secuencia = this.comprimirImagen(img,secuencias);
        System.out.println(" tamaño secuencia:" + secuencia.size());
        char[] originalSequence = new char[secuencia.size()];
        this.pasarAArreglo(secuencia,originalSequence);
        // Secuencia de 0s y 1s a codificar a nivel bit
        //calculator.printSequence(originalSequence);
        List<Byte> result = new ArrayList<Byte>();
        int ceros = ByteEncodingHelper.EncodeSequence(originalSequence,result);
        byte[] byteArray = this.ConvertByteListToPrimitives(result);
        // Guardar la codificación en un archivo binario
        try{
            String cerosExtra = "ceros extra: "+ ceros +"\n";
            String longitud = "Longitud: " + originalSequence.length + "\n";
            FileOutputStream fos = new FileOutputStream(path);

            //Para escribir esto primero hay que hacer que el decodificador las pueda saltear
            ///fos.write(longitud.getBytes(Charset.forName("UTF-8")));
          // fos.write(cerosExtra.getBytes(Charset.forName("UTF-8")));
           // this.insertarFrecuencias(fos,distribuciones);

            fos.write(byteArray);
            fos.close();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }



    //en la cabecera hay que poner la cantidad de simbolos
    //vamos a tener que completar con 0 , pero el problema es al decodificarlo saber que 0
    //hay que poner un dato que indique cuantos bits hay que utlizar del ultimo byte, porque el resto son 0s
    //todas las cabeceras van a ser distintas para los alumnos, osea otro no puede decodificar mis imagenes con su algoritmo

}

