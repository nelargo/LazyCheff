package com.madgoatstd.lazycheff;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.madgoatstd.lazycheff.adapters.Recipe;
import com.madgoatstd.lazycheff.adapters.ResultAdapter;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ResultsActivity extends ActionBarActivity {
    Context mContext;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ResultAdapter adapter;
    FloatingActionButton actionButton;
    ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // in Activity Context
        icon = new ImageView(this); // Create an icon
        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.fab_primary)
                .build();

        recyclerView = (RecyclerView) findViewById(R.id.ingredientsList);
        adapter = new ResultAdapter(this, getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static List<Recipe> getData() {
        /* Obtener lista de ingredientes */
        List<Recipe> data = new ArrayList<>();
        String[] titles = {"Tomate", "Pollo", "Queso", "Carne de Cerdo", "Tocino", "Huevo", "Papa", "Carne de Vacuno", "Acelga", "Pepino", "Manzana", "Pera", "Plátano", "Manjar"};

        for (int i = 0; i < titles.length; i++) {
            Recipe current = new Recipe();
            current.name = titles[i];
            data.add(current);
        }
        return data;
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
        if (id == R.id.action_filter) {
            return true;
        }
        /*if(id == android.R.id.home){
            onBackPressed();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }


}
