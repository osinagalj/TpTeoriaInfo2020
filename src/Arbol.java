public class Arbol {

    private Arbol hijoIzquierdo = null;
    private Arbol hijoDerecho = null;
    private boolean visitado = false;


    private float probabilidad;
    private int color;

    public Arbol(){}
    public Arbol( int color, float probabilidad){
        this.color = color;
        this.probabilidad = probabilidad;
    }
    public Arbol( Arbol hijoIzquierdo,Arbol hijoDerecho, int color, float probabilidad){
        this.hijoDerecho = hijoDerecho;
        this.hijoIzquierdo = hijoIzquierdo;
        this.color = color;
        this.probabilidad = probabilidad;
    }
    public Arbol( Arbol hijoIzquierdo,Arbol hijoDerecho, float probabilidad){
        this.hijoDerecho = hijoDerecho;
        this.hijoIzquierdo = hijoIzquierdo;

        this.probabilidad = probabilidad;
    }



    public float getProbabilidad(){
        return this.probabilidad;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Arbol getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(Arbol hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public Arbol getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(Arbol hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public void setProbabilidad(float probabilidad) {
        this.probabilidad = probabilidad;
    }


}
