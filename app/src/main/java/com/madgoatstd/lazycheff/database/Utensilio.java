package com.madgoatstd.lazycheff.database;

/**
 * Created by Carlos on 29/04/2015.
 */
public class Utensilio {
    private int id;
    private String nombre;

    public Utensilio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
