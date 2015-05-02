package com.madgoatstd.lazycheff;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.madgoatstd.lazycheff.adapters.IngredienteAdapter;
import com.madgoatstd.lazycheff.adapters.Recipe;
import com.madgoatstd.lazycheff.adapters.RecipeAdapter;
import com.madgoatstd.lazycheff.adapters.ResultAdapter;
import com.madgoatstd.lazycheff.adapters.UtensilioAdapter;
import com.madgoatstd.lazycheff.database.Ingrediente;
import com.madgoatstd.lazycheff.database.IngredienteRecetaDataSource;
import com.madgoatstd.lazycheff.database.Ingrediente_Receta;
import com.madgoatstd.lazycheff.database.Receta;
import com.madgoatstd.lazycheff.database.RecipeDataSource;
import com.madgoatstd.lazycheff.database.SQLiteHelper;
import com.madgoatstd.lazycheff.database.UtensilioRecetaDataSource;
import com.madgoatstd.lazycheff.database.Utensilio_Receta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RecipeActivity extends ActionBarActivity implements RecipeAdapter.ClickListener{

    private Context mContext;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mContext = this;
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recipeview);
        recipeAdapter = new RecipeAdapter(this, getData());
        recipeAdapter.setClickListener(this);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    public List<String> getData(){
        List<String> data = new ArrayList<>();
        Bundle extra = getIntent().getExtras().getBundle("RECETA");
        Receta receta = new Receta(extra.getInt("ID"),
                extra.getString("NOMBRE"),
                extra.getString("DIFICULTAD"),
                extra.getString("TIEMPO"),
                extra.getString("INDICACIONES"),
                extra.getString("TIPO"),
                extra.getString("IMAGEN"));

        Log.i("PICHULA", extra.getString("IMAGEN"));

        data.add(receta.getNombre()+";"+receta.getTipo()+";"+receta.getImagen());
        data.add(receta.getTiempo()+";"+receta.getDificultad());
        data.add("Utensilios");
        data.add("Ingredientes");
        data.add(receta.getIndicaciones());


        return data;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Utensilio_Receta> getTools(){
        UtensilioRecetaDataSource source = new UtensilioRecetaDataSource(mContext);
        source.open();
        List<Utensilio_Receta> ur = source.getUtensilio_Receta(getIntent().getExtras().getBundle("RECETA").getInt("ID"));
        source.close();
        return ur;
    }
    private List<Ingrediente_Receta> getIngredients(){
        IngredienteRecetaDataSource ingredienteSource = new IngredienteRecetaDataSource(mContext);
        ingredienteSource.open();
        List<Ingrediente_Receta> ir = ingredienteSource.getIngrediente_Receta(getIntent().getExtras().getBundle("RECETA").getInt("ID"));
        ingredienteSource.close();
        return ir;
    }

    @Override
    public void itemClicked(View view, int position, int type) {
        YoYo.with(Techniques.Tada)
                .duration(600)
                .playOn(view);
        MaterialDialog.Builder builder;
        View v = getLayoutInflater().inflate(R.layout.dialog_list,null);
        RecyclerView rv = (RecyclerView)v.findViewById(R.id.listado);
        String titulo = "";
        if(position == 2){
            UtensilioAdapter adapter = new UtensilioAdapter(mContext, getTools());
            titulo = "Utensilios Necesarios";
            rv.setAdapter(adapter);

        }
        if(position == 3){
            IngredienteAdapter adapter = new IngredienteAdapter(mContext, getIngredients());
            titulo = "Ingredientes Necesarios";
            rv.setAdapter(adapter);
        }
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        builder = new MaterialDialog.Builder(mContext)
                .positiveText("Cerrar")
                .customView(v, false)
                .title(titulo)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                    }
                })
                .autoDismiss(false);
        builder.show();



    }
}
