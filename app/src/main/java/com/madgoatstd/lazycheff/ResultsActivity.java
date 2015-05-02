package com.madgoatstd.lazycheff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.madgoatstd.lazycheff.adapters.ResultAdapter;
import com.madgoatstd.lazycheff.database.IngredienteRecetaDataSource;
import com.madgoatstd.lazycheff.database.Ingrediente_Receta;
import com.madgoatstd.lazycheff.database.Receta;
import com.madgoatstd.lazycheff.database.RecipeDataSource;
import com.nineoldandroids.animation.Animator;

import java.util.ArrayList;
import java.util.List;


public class ResultsActivity extends ActionBarActivity implements ResultAdapter.ClickListener {

    private static final String TAG = "ResultActivity";
    Context mContext;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ResultAdapter adapter;
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        extras = getIntent().getExtras();

        // in Activity Context
        /*icon = new ImageView(this); // Create an icon
        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.fab_primary)
                .build();*/

        recyclerView = (RecyclerView) findViewById(R.id.ingredientsList);
        adapter = new ResultAdapter(this, getData());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }

    public List<Receta> getData() {
        /* Obtener lista de ingredientes */

        Boolean isOnly = extras.getBoolean("SOLO");
        int dificultad = extras.getInt("DIFICULTAD");
        String tiempo = extras.getString("TIEMPO");
        Bundle bundle = extras.getBundle("INGREDIENTES");
        List<Integer> ids = bundle.getIntegerArrayList("INGREDIENTES");

        RecipeDataSource dataSource = new RecipeDataSource(mContext);
        IngredienteRecetaDataSource ingredienteSource = new IngredienteRecetaDataSource(mContext);
        ingredienteSource.open();
        dataSource.open();

        List<Receta> data = dataSource.getAllRecetas();
        List<Ingrediente_Receta> ingredientes_receta = ingredienteSource.getAllIngrediente_Recetas();

        dataSource.close();
        ingredienteSource.close();

        if(data!= null)
            data = filterByDifficult(data,dificultad);
        if(data!= null)
            data = filterByTime(data, Integer.valueOf(tiempo));
        if(data!= null)
            data = filterByIngredientes(data, ingredientes_receta,ids,isOnly);

        return data;
    }

    private List<Receta> filterByIngredientes(List<Receta> recetas,List<Ingrediente_Receta> ingredientes_receta,List<Integer> ingredientes, Boolean isOnly){
        if(recetas.size()>0){
            List<Receta> filtrado = new ArrayList<>();
            for (Receta r : recetas) {
                List<Integer> ids = getIngredientsInReceta(r.getId(),ingredientes_receta);

                /*if(aContainsAllOfB(ids, ingredientes)){
                    if(isOnly && ids.size()==ingredientes.size())
                        filtrado.add(r);
                    if(!isOnly)
                        filtrado.add(r);
                }*/
                if(isOnly && aContainsAllOfB(ids, ingredientes))
                    filtrado.add(r);
                if(!isOnly && aContainsAnyofB(ids, ingredientes))
                    filtrado.add(r);
            }
            return filtrado;
        }
        return null;
    }

    private Boolean aContainsAllOfB(List<Integer> a,List<Integer> b){

        for(int i= 0; i < b.size();i++){
            if(!a.contains(b.get(i))){
                return false;
            }
        }
        return true;
    }

    private Boolean aContainsAnyofB(List<Integer> a,List<Integer> b){

        for(int i= 0; i < b.size();i++){
            if(a.contains(b.get(i))){
                return true;
            }
        }
        return false;
    }

    private List<Integer> getIngredientsInReceta(int id_receta, List<Ingrediente_Receta> ingredientes_recetas ){
        List<Integer> ids = new ArrayList<>();
        for(Ingrediente_Receta ir:ingredientes_recetas){
            if(ir.getId_receta() == id_receta)
                ids.add(ir.getId_ingrediente());
        }
        return ids;
    }

    private List<Receta> filterByTime(List<Receta> recetas,int t){
        if(recetas.size()>0){
            List<Receta> filtrado = new ArrayList<>();

            for (Receta r : recetas) {
                if (Integer.valueOf(r.getTiempo())<= t) {
                    filtrado.add(r);
                }
            }

            return filtrado;
        }
        return null;
    }
    private List<Receta> filterByDifficult(List<Receta> recetas,int d){
        if(recetas.size()>0){
            List<Receta> filtrado = new ArrayList<>();

            for (Receta r : recetas) {
                if (Integer.valueOf(r.getDificultad())<= d) {
                    filtrado.add(r);
                }
            }
            return filtrado;
        }
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }


    @Override
    public void itemClicked(View view, final int position, final List<Receta> actual) {
        YoYo.with(Techniques.Tada)
                .duration(600).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Receta r = actual.get(position);
                Bundle extras = new Bundle();
                extras.putInt("ID",r.getId());
                extras.putString("NOMBRE", r.getNombre());
                extras.putString("DIFICULTAD", r.getDificultad());
                extras.putString("TIEMPO", r.getTiempo());
                extras.putString("INDICACIONES", r.getIndicaciones());
                extras.putString("IMAGEN", r.getImagen());
                extras.putString("TIPO",r.getTipo());

                Intent p = new Intent(mContext, RecipeActivity.class);
                p.putExtra("RECETA", extras);
                startActivity(p);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        })
                .playOn(view);


    }
}
