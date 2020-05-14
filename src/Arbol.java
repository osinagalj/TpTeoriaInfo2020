public class Arbol {

    private Arbol hijoIzquierdo = null;
    private Arbol hijoDerecho = null;
    private boolean visitado = false;
    private String cadena = "";

    private double probabilidad;
    private int color = -1;

    public void addCadena(String s){
        cadena = cadena + " " + s;
    }
    public String getCadena(){
        return cadena;
    }

    private int nivel = 0;

    public Arbol(){

    }
    public Arbol( int color, double probabilidad){
        this.color = color;
        this.probabilidad = probabilidad;
    }
    public Arbol( int color, double probabilidad,int nivel){
        this.color = color;
        this.probabilidad = probabilidad;
        this.nivel = nivel;

    }
    public Arbol( Arbol hijoIzquierdo,Arbol hijoDerecho, int color, double probabilidad){
        this.hijoDerecho = hijoDerecho;
        this.hijoIzquierdo = hijoIzquierdo;
        this.color = color;
        this.probabilidad = probabilidad;

        if(hijoDerecho.getNivel()> hijoIzquierdo.getNivel()){
            this.setNivel(hijoDerecho.getNivel());
        }else{
            this.setNivel(hijoIzquierdo.getNivel());
        }
    }
    public Arbol( Arbol hijoIzquierdo,Arbol hijoDerecho, double probabilidad){
        this.hijoDerecho = hijoDerecho;
        this.hijoIzquierdo = hijoIzquierdo;

        this.probabilidad = probabilidad;
        if(hijoDerecho.getNivel()> hijoIzquierdo.getNivel()){
            this.setNivel(hijoDerecho.getNivel());
        }else{
            this.setNivel(hijoIzquierdo.getNivel());
        }

    }
    public Arbol( Arbol hijoIzquierdo,Arbol hijoDerecho){
        this.hijoDerecho = hijoDerecho;
        this.hijoIzquierdo = hijoIzquierdo;

        this.probabilidad = hijoIzquierdo.getProbabilidad() + hijoDerecho.getProbabilidad();
        if(hijoDerecho.getNivel()> hijoIzquierdo.getNivel()){
            this.setNivel(hijoDerecho.getNivel());
        }else{
            this.setNivel(hijoIzquierdo.getNivel());
        }

    }


    public double getProbabilidad(){
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

    public void setProbabilidad(double probabilidad) {
        this.probabilidad = probabilidad;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
