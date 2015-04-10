package com.madgoatstd.lazycheff.adapters;

import java.util.List;

public class Recipe {
    public String name;
    public int difficult=0;
    public int timeP=0;
    public int timeC=0;
    public String howToDo;
    public int image;
    public String imageDir;
    public List<IngredientRecipe> ingredients;

    protected static final String DIFFICULT_PREFIX = "Dificultad: ";
    protected static final String DIFFICULT_POSTFIX = "/3";
    protected static final String TIME_PREFIX = "Tiempo: ";
    protected static final String TIME_POSTFIX = " minutos";

}


