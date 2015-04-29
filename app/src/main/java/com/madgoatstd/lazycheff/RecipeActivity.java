package com.madgoatstd.lazycheff;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.madgoatstd.lazycheff.adapters.Recipe;
import com.madgoatstd.lazycheff.adapters.RecipeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RecipeActivity extends ActionBarActivity implements RecipeAdapter.ClickListener{

    private Context mContext;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;

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

    public List<Recipe> getData(){
         /* Obtener lista de ingredientes */
        List<Recipe> data = new ArrayList<>();
        String[] titles = {"Tomate", "Pollo", "Queso", "Carne de Cerdo"};

        for (int i = 0; i < titles.length; i++) {
            Recipe current = new Recipe();
            current.name = titles[i];
            data.add(current);
        }
        return data;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<String> getTools(){
        List<String> data = new ArrayList<>();
        String[] a = {"Sartén", "Espátula"};
        for(int i = 0; i<a.length;i++)
            data.add(a[i]);
        return data;
    }
    private List<String> getIngredients(){
        List<String> data = new ArrayList<>();
        String[] a = {"Huevo", "Tocino"};
        for(int i = 0; i<a.length;i++)
            data.add(a[i]);
        return data;
    }

    @Override
    public void itemClicked(View view, int position) {
        YoYo.with(Techniques.Tada).duration(600).playOn(view);
        MaterialDialog.Builder builder;
        if(position == 2){
            builder = new MaterialDialog.Builder(mContext)
                    .positiveText("Cerrar")
                    .items(R.array.utensilios)
                    .title("Utensilios Necesarios")
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                            dialog.dismiss();
                        }
                    })
                    .autoDismiss(false)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                            return;
                        }
                    });
            builder.show();
        }
        if(position == 3){
            builder = new MaterialDialog.Builder(mContext)
                    .autoDismiss(false)
                .positiveText("Cerrar")
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                            dialog.dismiss();
                        }
                    })
                .items(R.array.ingredients)
                .title("Ingredientes Necesarios")
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        return;
                    }
                });
            builder.show();
        }


    }
}
