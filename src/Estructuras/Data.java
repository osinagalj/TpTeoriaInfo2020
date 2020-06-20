package Estructuras;

public class Data<E,Y,J,K> {

    private E contenido1;
    private Y contenido2;
    private J sizeX;
    private K sizeY;

    public E getContenido1() {
        return contenido1;
    }

    public void setContenido1(E contenido1) {
        this.contenido1 = contenido1;
    }

    public Y getContenido2() {
        return contenido2;
    }

    public void setContenido2(Y contenido2) {
        this.contenido2 = contenido2;
    }

    public J getSizeX() {
        return sizeX;
    }

    public void setSizeX(J sizeX) {
        this.sizeX = sizeX;
    }

    public K getSizeY() {
        return sizeY;
    }

    public void setSizeY(K sizeY) {
        this.sizeY = sizeY;
    }
}
