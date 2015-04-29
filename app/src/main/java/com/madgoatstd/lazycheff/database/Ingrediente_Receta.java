package com.madgoatstd.lazycheff.database;

/**
 * Created by Carlos on 29/04/2015.
 */
public class Ingrediente_Receta {
    private int id;
    private int id_ingrediente;
    private int id_receta;
    private String cantidad;

    public Ingrediente_Receta(int id, int id_ingrediente, int id_receta, String cantidad) {
        this.id = id;
        this.id_ingrediente = id_ingrediente;
        this.id_receta = id_receta;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_ingrediente() {
        return id_ingrediente;
    }

    public void setId_ingrediente(int id_ingrediente) {
        this.id_ingrediente = id_ingrediente;
    }

    public int getId_receta() {
        return id_receta;
    }

    public void setId_receta(int id_receta) {
        this.id_receta = id_receta;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
