package com.madgoatstd.lazycheff.database;

public class Receta {
    private int id;
    private String nombre;
    private String dificultad;
    private String tiempo;
    private String indicaciones;
    private String tipo;
    private String imagen;

    public Receta(int id, String nombre, String dificultad, String tiempo, String indicaciones, String tipo, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.tiempo = tiempo;
        this.indicaciones = indicaciones;
        this.tipo = tipo;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
