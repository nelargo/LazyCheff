package com.madgoatstd.lazycheff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.madgoatstd.lazycheff.adapters.Ingredient;
import com.madgoatstd.lazycheff.adapters.SimpleAdapter;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements SimpleAdapter.ClickListener {
    private static int TYPE_INGREDIENTS = 0;
    private static int TYPE_CART = 1;
    Context mContext;
    private Toolbar toolbar;
    private RecyclerView recyclerView, listCart;
    private LinearLayout footer;
    private RelativeLayout footerbar;
    private SimpleAdapter simpleAdapterIng, simpleAdapterCart;
    private List<Ingredient> cart;
    private TextView footerText2, footerText1;
    FloatingActionButton actionButton;
    FloatingActionButton.LayoutParams normal,elevated;
    ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher1);

        // in Activity Context
        icon = new ImageView(this); // Create an icon
        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.fab_primary)
                .build();
        actionButton.setVisibility(View.GONE);

        recyclerView = (RecyclerView) findViewById(R.id.ingredientsList);
        listCart = (RecyclerView) findViewById(R.id.ingredientsCart);
        simpleAdapterIng = new SimpleAdapter(this, getData(), TYPE_INGREDIENTS);
        simpleAdapterIng.setClickListener(this);
        recyclerView.setAdapter(simpleAdapterIng);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cart = new ArrayList<>();
        simpleAdapterCart = new SimpleAdapter(this, cart, TYPE_CART);
        simpleAdapterCart.setClickListener(this);
        listCart.setAdapter(simpleAdapterCart);
        listCart.setLayoutManager(new LinearLayoutManager(this));

        footerText1 = (TextView) findViewById(R.id.footerbartext1);
        footerText2 = (TextView) findViewById(R.id.footerbartext2);
        footer = (LinearLayout) findViewById(R.id.footer);
        footerbar = (RelativeLayout) findViewById(R.id.footerbar);

        footerbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listCart.getVisibility() == View.VISIBLE) {
                    YoYo.with(Techniques.SlideOutDown)
                            .duration(600)
                            .playOn(listCart);

                    listCart.setVisibility(View.GONE);
                    footerText2.setVisibility(View.VISIBLE);
                    footerText1.setVisibility(View.GONE);
                    normal = (FloatingActionButton.LayoutParams)actionButton.getLayoutParams();
                    normal.setMargins(0,0,16,16);
                    actionButton.setLayoutParams(normal);
                } else {
                    YoYo.with(Techniques.Bounce)
                            .duration(1000)
                            .playOn(listCart);
                    YoYo.with(Techniques.Bounce)
                            .duration(1000)
                            .playOn(footerbar);
                    listCart.setVisibility(View.VISIBLE);
                    footerText1.setVisibility(View.VISIBLE);
                    footerText2.setVisibility(View.GONE);
                    normal = (FloatingActionButton.LayoutParams)actionButton.getLayoutParams();
                    normal.setMargins(0,0,dpToPx(16),dpToPx(216));
                    actionButton.setLayoutParams(normal);
                }

            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(mContext)
                        .title("Opciones de Búsqueda")
                        .customView(R.layout.dialog_options,true)
                        .positiveText("Buscar")
                        .negativeText("Volver")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                Toast.makeText(mContext, "Buscando", Toast.LENGTH_SHORT).show();
                                startActivityForResult(new Intent(mContext,ResultsActivity.class),0);
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                                Toast.makeText(mContext, "Buscando", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();

            }
        });

    }

    public static List<Ingredient> getData() {
        /* Obtener lista de ingredientes */
        List<Ingredient> data = new ArrayList<>();
        String[] titles = {"Tomate", "Pollo", "Queso", "Carne de Cerdo", "Tocino", "Huevo", "Papa", "Carne de Vacuno", "Acelga", "Pepino", "Manzana", "Pera", "Plátano", "Manjar"};

        for (int i = 0; i < titles.length; i++) {
            Ingredient current = new Ingredient();
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
        int id = item.getItemId();
        if (id == R.id.action_filter) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemClicked(View view, int position, int type) {
        if (type == TYPE_INGREDIENTS) {
            Ingredient toMove = simpleAdapterIng.getItem(position);
            if (simpleAdapterIng.removeItem(toMove, position)) {
                simpleAdapterCart.addItem(toMove);

            }
        }
        if (type == TYPE_CART) {
            Ingredient toMove = simpleAdapterCart.getItem(position);
            if (simpleAdapterCart.removeItem(toMove, position)) {
                simpleAdapterIng.addItem(toMove);
            }
        }

        YoYo.with(Techniques.RubberBand)
                .duration(800)
                .playOn(footerText2);
        footerText2.setText("Nº de Ingredientes: " + cart.size());

        if (cart.size() > 0 && footer.getVisibility() == View.GONE) {
            YoYo.with(Techniques.BounceInUp)
                    .duration(600)
                    .playOn(footer);
            footer.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.BounceInUp)
                    .duration(800)
                    .playOn(actionButton);
            actionButton.setVisibility(View.VISIBLE);
            normal = (FloatingActionButton.LayoutParams)actionButton.getLayoutParams();
            normal.setMargins(0,0,dpToPx(16),dpToPx(16));
            actionButton.setLayoutParams(normal);
        } else if(cart.size() == 0 && footer.getVisibility() == View.VISIBLE){
            YoYo.with(Techniques.SlideOutDown)
                    .duration(600)
                    .playOn(footer);
            YoYo.with(Techniques.SlideOutDown)
                    .duration(800)
                    .playOn(actionButton);

            listCart.setVisibility(View.GONE);
            footerText1.setVisibility(View.GONE);
            footerText2.setVisibility(View.VISIBLE);
            footer.setVisibility(View.GONE);
            actionButton.setVisibility(View.GONE);
        }
    }

    public int dpToPx(int dp){
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int px = Math.round(dp*(displayMetrics.xdpi/DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result
            Log.d("ZXINGSASD", "" + scanResult.toString());
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i("MainActivity", "onPause");
    }
    @Override
    public void onDestroy(){
        super.onPause();
        Log.i("MainActivity", "onDestroy");
    }

    @Override
    public void onResume(){
        super.onPause();
        Log.i("MainActivity", "onResume");
    }
}
