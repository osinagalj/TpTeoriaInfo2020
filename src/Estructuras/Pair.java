package Estructuras;

import java.awt.image.BufferedImage;
    //Estrucutra auxialiar para guardar en orden las imagenes con respecto a sus correlaciones (Ej 1)

public class Pair implements Comparable<Pair>{
    private BufferedImage img;
    private double correlacion;

    public Pair(double d,BufferedImage img){
        this.correlacion = d;
        this.img = img;
    }

    public int compareTo(Pair a){
        // compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than
        // other and 0 if they are supposed to be equal
        if(this.getCorrelacion() > a.getCorrelacion()){
            return -1;
        }else{
            if(this.getCorrelacion() < a.getCorrelacion()){
                return 1;
            }else{
                return 0;
            }
        }
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public double getCorrelacion() {
        return correlacion;
    }

    public void setCorrelacion(double correlacion) {
        this.correlacion = correlacion;
    }
}
