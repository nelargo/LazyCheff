package com.madgoatstd.lazycheff.adapters;

public class Recipe {
    public String name;
    public int difficult;
    public int time;
    public String howToDo;
    public int image;

    protected static final String DIFFICULT_PREFIX = "Dificultad: ";
    protected static final String DIFFICULT_POSTFIX = "/5";
    protected static final String TIME_PREFIX = "Tiempo: ";
    protected static final String TIME_POSTFIX = " minutos";
}
