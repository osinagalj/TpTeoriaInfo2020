
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
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

public class Calculator {

    public Calculator(){}

    public int getGris(BufferedImage imgOriginal,int x,int y){
        int rgb = imgOriginal.getRGB(x, y);
        Color color = new Color(rgb, true);
        //double gris = (double)(r+g+b)/3; //con este se saca la escala de grises, pero como las imagenenes vienen en escala de grises, para que sea mas eficiente podemos tomar uno de los 3, ya uqe los 3 son iguales siempre. Esto lo podemos hacer porque sabemos de antemano que no nos van a venir imagenes de color
        return color.getRed();
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

    public void inserTarOrdenado(Vector<Arbol>  hojas, Arbol hoja) {

        int i = 0; //Para recorrer el vector

        while (i < hojas.size()) {
            if (hoja.getProbabilidad() < hojas.get(i).getProbabilidad()) { //Si es mas chico va de una
                hojas.add(i, hoja);
                return;
            } else {
                if ((hoja.getProbabilidad() == hojas.get(i).getProbabilidad()) && (hoja.getNivel() >= hojas.get(i).getNivel())) { //Si son iguales ordeno por nivel
                    hojas.add(i, hoja);
                    return;           //Si son iguales primero quiero que este el de mayor nivel
                }
                i++; //Para que avance
            }
        }
        hojas.add(hoja); //Si llego aca es porque nunca se incerto => probabilidad mas grande, va al final
    }

    public Arbol getArbolHuffman(Vector<Arbol> hojas){
        //Hijo derecho   = mayor probabilidad
        //Hijo Izquierdo = menor probabilidad
        while (hojas.size() > 1){
            Arbol izq = hojas.get(0);
            Arbol der = hojas.get(1);
            hojas.remove(0);
            hojas.remove(0);

            Arbol huffman = new Arbol(izq,der);
            this.inserTarOrdenado(hojas, huffman);
        }
        return hojas.get(0);
    }

    public static void printMap(Map mp) { //Muestra el contenido del HashMap
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }

    public void obtenerSecuencias(HashMap<Integer,String> h, Arbol arbolito, String secuencia){
        if(arbolito.getHijoDerecho() == null) { //Con que uno sea null ya sabemos que es hoja
            h.put(arbolito.getColor(),secuencia);
        }else{                                  //Si no es hoja, recorremos para ambos lados
            obtenerSecuencias(h,arbolito.getHijoDerecho(),secuencia+"0");
            obtenerSecuencias(h,arbolito.getHijoIzquierdo(),secuencia+"1");
        }
    }

    public void ordenarHojas(int[] distribucion, Vector<Arbol> hojas, int n){
        for(int i=0; i<distribucion.length;i++){
            if(distribucion[i] != 0) {
                Arbol hoja = new Arbol(i, (double)distribucion[i]/n); //Cuando arreglemos extension la posicion no va a representar el color
                hojas.add(hoja);
            }
        }
        Collections.sort(hojas);
    }

    public char[] codificarSecuencia(BufferedImage img,HashMap<Integer,String> secuencias){ //Chequeado

        ArrayList<Character> secuenciaArray = new ArrayList<Character>();

        Integer color;
        String aux;

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                color = (int)getGris(img,x,y);
                //System.out.println(color); //Capta bien el color
                aux = secuencias.get(color);
                //System.out.println(aux);  //Capta bien las secuencias
                for (int k = 0; k < aux.length(); k++){
                    secuenciaArray.add(aux.charAt(k));
                }
            }
        }

        char[] secuenciaChar = new char[secuenciaArray.size()];
        for (int x = 0; x < secuenciaChar.length; x++){
            secuenciaChar[x] = secuenciaArray.get(x);
        }

        return secuenciaChar;
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
        jf.setBackgroundPaint(new Color(42, 112, 182));
        jf.getPlot().setBackgroundPaint( new Color(42, 112, 182));

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

    public int[] getColor(char[] in,Arbol nodo,int index){  //Mura
        int[] rtrn = new int[2]; //En 0 el index, en 1 el color
        Arbol aux = nodo;

        while(aux.getHijoDerecho() != null){ //Con uno alcanza, por construccion
            if((in[index]) == '0'){
                aux = aux.getHijoDerecho();
            }else{  // == '1'
                aux = aux.getHijoIzquierdo();
            }
            index++;
        }

        rtrn[0] = index;
        rtrn[1] = aux.getColor();
        return rtrn;
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

    public  BufferedImage map(Cosas<char[],Arbol,Integer,Integer> cosas){
        int sizeX = cosas.getSizeX();
        int sizeY = cosas.getSizeY();

        final BufferedImage res = new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_INT_RGB );
        int index = 0;

        for(int x = 0; x < sizeX; x++)           //Recorremos la imagen
            for(int y = 0; y < sizeY; y++){
                int[] color = getColor(cosas.getContenido1(), cosas.getContenido2(), index);   //Obtenemos el color de esa posicion
                index = color[0];
                res.setRGB(x, y, (color[1]*256*256 + color[1] * 256 + color[1]));
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
            char[] colorChar = new char[1];
            char[] distribucionChar = new char[24];

            for(int i = 0;i<distribuciones.length;i++){
                if(distribuciones[i]!=0){
                    colorChar = convertirNumeroChar(i,1); //1 byte para almacenar el color (0 a 255)
                    List<Byte> colorByte = ByteEncodingHelper.EncodeSequence(colorChar);  //Codificado a byte
                    byte[] colorBits = this.ConvertByteListToPrimitives(colorByte); //Binario

                    distribucionChar = convertirNumeroChar(distribuciones[i],3);
                    List<Byte> distribucionByte = ByteEncodingHelper.EncodeSequence(distribucionChar);
                    byte[] distribucionBits = this.ConvertByteListToPrimitives(distribucionByte);

                    fos.write(colorBits);           //Escribo 1 bit con el color
                    fos.write(distribucionBits);    //Escribo 3 bits con la distribucion
                }
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public char[] convertirNumeroChar(int entrada,int n){   //Convierte un numero a n bytes
        int aux = entrada;
        char[] rta = new char[8*n];
        for(int x = 0; x < rta.length; x++){
            if (aux >= Math.pow(2,rta.length-1-x)){
                rta[x] = '1';
                aux = (int) (aux - Math.pow(2,23-x));
            } else{ rta[x] = '0';}
        }
        return rta;
    }

    public void comprimirImagen(String path,BufferedImage img,HashMap<Integer,String> secuencias, int[] distribucion){

        //SECUENCIA
        char[] secuenciaChar = codificarSecuencia(img, secuencias) ; //Secuencia de chars
        List<Byte> secuenciaByte = ByteEncodingHelper.EncodeSequence(secuenciaChar);  //Codificado a byte
        byte[] secuenciaBits = this.ConvertByteListToPrimitives(secuenciaByte); //Binario

        //LONGITUD SECUENCIA
        char[] longitudChar = convertirNumeroChar(secuenciaChar.length,3);
        List<Byte> longitudByte = ByteEncodingHelper.EncodeSequence(longitudChar);
        byte[] longitudBit = this.ConvertByteListToPrimitives(longitudByte); //Añade un byte de mas en 0

        //CANTIDAD FRECUENCIAS
        int cantidadFrecuencias = 0;
        for(int x = 0; x < distribucion.length; x++){
            if (distribucion[x] != 0)
                cantidadFrecuencias++;
        }
        char[] cantidadFrecuenciasChar = convertirNumeroChar(cantidadFrecuencias,1); //1 byte, como mucho 255 frecuencias
        List<Byte> cantidadFrecuenciasByte = ByteEncodingHelper.EncodeSequence(cantidadFrecuenciasChar);
        byte[] cantidadFrecuenciasBits = this.ConvertByteListToPrimitives(cantidadFrecuenciasByte);

        //tamanio X
        char[] XChar = convertirNumeroChar(img.getWidth(),3);
        List<Byte> XByte = ByteEncodingHelper.EncodeSequence(XChar);
        byte[] XBit = this.ConvertByteListToPrimitives(XByte);

        //tamanio Y
        char[] YChar = convertirNumeroChar(img.getHeight(),3);
        List<Byte> YByte = ByteEncodingHelper.EncodeSequence(YChar);
        byte[] YBit = this.ConvertByteListToPrimitives(YByte);

        try{
            FileOutputStream fos = new FileOutputStream(path);

            fos.write(longitudBit);                         //Longitud de secuencia         3 Bytes   0,1,2
            fos.write(cantidadFrecuenciasBits);             //Cantidad de Frecuencias       1 Byte    3

            fos.write(XBit);                                //Anchura                       3 Bytes   4,5,6
            fos.write(YBit);                                //Altura                        3 Bytes   7,8,9

            insertarFrecuencias(fos,distribucion);          //Todas las frecuencias:  Color 1 Byte    10,14
            //                 Distribucion 3 Bytes   (11,12,13),(15,16,17)

            fos.write(secuenciaBits);                       //Bytes restantes               n Bytes   18...

            fos.close();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


//EJERCICIO 4

    public void mostrarMatriz(double[][] matriz){
        DecimalFormat formato = new DecimalFormat("#.000");
        for(int x = 0; x < matriz.length; x++){
            System.out.print("| ");
            for(int y = 0; y < matriz[x].length; y++){
                System.out.print(formato.format(matriz[x][y]) + " ");
            }
            System.out.println("|");
        }
    }

    public double[][] calcularMatriz(BufferedImage entrada,BufferedImage salida){
        double[][] rtrn = new double[17][16]; //de 0 a 16 los colores, el 17 es para llevar las cuentas

        //Inicializamos en 0
        for (int x=0; x < rtrn.length; x++) {
            for (int y=0; y < rtrn[x].length; y++) {
                rtrn[x][y] = 0;
            }
        }

        //Sabemos que tienen mismas dimensiones
        //System.out.println("Dimensiones entrada w: " + entrada.getWidth() + " h: " + entrada.getHeight());
        //System.out.println("Dimensiones entrada w: " + salida.getWidth() + " h: " + salida.getHeight());

        for (int x=0; x < entrada.getWidth(); x++) {
            for (int y=0; y < entrada.getHeight(); y++) {      //Cada posicion de pixel [x][y]
                int in = getGris(entrada,x,y);   //get color entrada en x,y
                int out = getGris(salida,x,y);   //get color salida en x,y

                in = in/17;
                out = out/17;

                rtrn[out][in]++;  //sumamos uno en la fila colorEntrada/17
                //columna colorSalida/17
                rtrn[16][in]++;
            }
        }

        for (int x=0; x < 16; x++) {
            for (int y=0; y < 16; y++) {  //Para cada espacio de la matriz
                rtrn[x][y] = (rtrn[x][y])/(rtrn[16][y]); //divido por el total de esa entrada
            }
        }
        return rtrn;
    }




}