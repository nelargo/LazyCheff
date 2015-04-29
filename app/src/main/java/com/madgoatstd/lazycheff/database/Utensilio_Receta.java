package com.madgoatstd.lazycheff.database;

/**
 * Created by Carlos on 29/04/2015.
 */
public class Utensilio_Receta {
    private int id;
    private int id_receta;
    private int id_utensilio;

    public Utensilio_Receta(int id, int id_receta, int id_utensilio) {
        this.id = id;
        this.id_receta = id_receta;
        this.id_utensilio = id_utensilio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_receta() {
        return id_receta;
    }

    public void setId_receta(int id_receta) {
        this.id_receta = id_receta;
    }

    public int getId_utensilio() {
        return id_utensilio;
    }

    public void setId_utensilio(int id_utensilio) {
        this.id_utensilio = id_utensilio;
    }
}
