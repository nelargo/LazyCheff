package com.madgoatstd.lazycheff;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.madgoatstd.lazycheff.adapters.Ingredient;
import com.madgoatstd.lazycheff.adapters.SimpleAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity implements SimpleAdapter.ClickListener{
    Context mContext;
    private Toolbar toolbar;
    private RecyclerView recyclerView, listCart;
    private SimpleAdapter simpleAdapterIng, simpleAdapterCart;
    private List<Ingredient> cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.ingredientsList);
        listCart = (RecyclerView) findViewById(R.id.ingredientsCart);
        simpleAdapterIng = new SimpleAdapter(this, getData());
        simpleAdapterIng.setClickListener(this);
        recyclerView.setAdapter(simpleAdapterIng);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        cart = new ArrayList<>();
        simpleAdapterCart = new SimpleAdapter(this, cart);
        simpleAdapterCart.setClickListener(this);
        listCart.setAdapter(simpleAdapterCart);
        listCart.setLayoutManager(new LinearLayoutManager(this));


    }

    public static List<Ingredient> getData(){
        /* Obtener lista de ingredientes */
        List<Ingredient> data = new ArrayList<>();
        String[] titles = {"Tomate","Pollo","Queso","Carne de Cerdo","Tocino","Huevo","Papa","Carne de Vacuno","Acelga","Pepino","Manzana","Pera","Plátano","Manjar"};

        for(int i = 0; i<titles.length; i++){
            Ingredient current= new Ingredient();
            current.name = titles[i];
            data.add(current);
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {

            new IntentIntegrator(this).initiateScan();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemClicked(View view, int position) {
        Ingredient toMove = simpleAdapterIng.getItem(position);
        if(simpleAdapterIng.removeItem(toMove,position)) {
            simpleAdapterCart.addItem(toMove);
            RelativeLayout asd = (RelativeLayout) findViewById(R.id.downList);

        }
    }
}
