package Ejercicios;

import java.awt.*;
import java.awt.image.BufferedImage;

public class getColor {  //Nos devuelve un integer con el color para cierto pixel en la posicion x y
    public getColor(){};
    public static int getGris(BufferedImage imgOriginal, int x, int y){
        // obtiene el color de un pixel en la posicion (x,y) de una BufferedImage.
        // Al trabajar unicamente con escalas de griess solo se utiliza el color RED de los 3 colores (R-G-B), ya que los 3 son iguales.
        int rgb = imgOriginal.getRGB(x, y);
        Color color = new Color(rgb, true);
        return color.getRed();
    }
}
