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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.madgoatstd.lazycheff.adapters.Ingredient;
import com.madgoatstd.lazycheff.adapters.SimpleAdapter;
import com.madgoatstd.lazycheff.database.Ingrediente;
import com.madgoatstd.lazycheff.database.IngredienteDataSource;
import com.madgoatstd.lazycheff.webservice.SoapRequest;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements SimpleAdapter.ClickListener {
    private static final String TAG = "MainActivity";
    private static int TYPE_INGREDIENTS = 0;
    private static int TYPE_CART = 1;
    Context mContext;
    private Toolbar toolbar;
    private RecyclerView recyclerView, listCart;
    private LinearLayout footer;
    private RelativeLayout footerbar;
    private SimpleAdapter simpleAdapterIng, simpleAdapterCart;
    private List<Ingrediente> cart;
    private TextView footerText2, footerText1;
    FloatingActionButton actionButton;
    FloatingActionButton.LayoutParams normal;
    ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SoapRequest(MainActivity.this).consumirWebService();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        cart = new ArrayList<>();
        simpleAdapterCart = new SimpleAdapter(this, cart, TYPE_CART);
        simpleAdapterCart.setClickListener(this);
        listCart.setAdapter(simpleAdapterCart);
        listCart.setLayoutManager(new LinearLayoutManager(mContext));

        footerText1 = (TextView) findViewById(R.id.footerbartext1);
        footerText2 = (TextView) findViewById(R.id.footerbartext2);
        footer = (LinearLayout) findViewById(R.id.footer);
        footerbar = (RelativeLayout) findViewById(R.id.footerbar);

        footerbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listCart.getVisibility() == View.VISIBLE) {
                    YoYo.with(Techniques.Bounce)
                            .duration(500)
                            .playOn(actionButton);
                    YoYo.with(Techniques.Bounce)
                            .duration(500)
                            .playOn(footer);
                    listCart.setVisibility(View.GONE);
                    footerText2.setVisibility(View.VISIBLE);
                    footerText1.setVisibility(View.GONE);

                    normal = (FloatingActionButton.LayoutParams)actionButton.getLayoutParams();
                    normal.setMargins(0,0,16,16);
                    actionButton.setLayoutParams(normal);
                    //actionButton.setBackgroundResource(R.drawable.fab_primary);
                    //footerbar.setBackgroundColor(getResources().getColor(R.color.primary));
                } else {
                    YoYo.with(Techniques.Bounce)
                            .duration(500)
                            .playOn(footer);
                    YoYo.with(Techniques.Bounce)
                            .duration(500)
                            .playOn(actionButton);

                    //actionButton.setBackgroundResource(R.drawable.fab_secundary);
                    //footerbar.setBackgroundColor(getResources().getColor(R.color.accentColor));
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
                        .customView(R.layout.dialog_options, true)
                        .positiveText("Buscar")
                        .negativeText("Volver")
                        .autoDismiss(false)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                View v = dialog.getCustomView();
                                CheckBox only = (CheckBox) v.findViewById(R.id.dialog_checkBox);
                                EditText time = (EditText) v.findViewById(R.id.dialog_editText);
                                RatingBar difficult = (RatingBar) v.findViewById(R.id.dialog_ratingBar);
                                Boolean solo = only.isChecked();
                                String tiempo = time.getText().toString();
                                int dificultad = (int) difficult.getRating();

                                if (tiempo == null || tiempo.compareTo("") == 0) {
                                    YoYo.with(Techniques.Tada).duration(500).playOn(time);
                                    //Toast.makeText(mContext, "Ingrese el tiempo máximo. Ingrese 0 (cero) para mostrar todas.",Toast.LENGTH_LONG ).show();
                                    return;
                                }

                                if (dificultad == 0) {
                                    YoYo.with(Techniques.Tada).duration(500).playOn(difficult);
                                    //Toast.makeText(mContext, "Seleccione una dificultad",Toast.LENGTH_LONG ).show();
                                    return;
                                }


                                ArrayList<Integer> ids = new ArrayList<>();
                                for(Ingrediente a : ((SimpleAdapter) listCart.getAdapter()).getAllItems()){
                                    ids.add(a.getId());
                                }

                                Bundle bundle = new Bundle();
                                bundle.putIntegerArrayList("INGREDIENTES", ids);
                                Intent i = new Intent(mContext, ResultsActivity.class);
                                i.putExtra("SOLO", solo);
                                i.putExtra("TIEMPO", tiempo);
                                i.putExtra("DIFICULTAD", dificultad);
                                i.putExtra("INGREDIENTES", bundle);

                                startActivity(i);
                                dialog.dismiss();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });

    }

    public List<Ingrediente> getData() {
        /* Obtener lista de ingredientes */
        IngredienteDataSource ingredienteDataSource = new IngredienteDataSource(this.mContext);
        ingredienteDataSource.open();
        List<Ingrediente> data = ingredienteDataSource.getAllIngredientes();
        ingredienteDataSource.close();
        /*String[] titles = {"Tomate", "Pollo", "Queso", "Carne de Cerdo", "Tocino", "Huevo", "Papa", "Carne de Vacuno", "Acelga", "Pepino", "Manzana", "Pera", "Plátano", "Manjar"};

        for (int i = 0; i < titles.length; i++) {
            Ingredient current = new Ingredient();
            current.name = titles[i];
            data.add(current);
        }*/
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
            Ingrediente toMove = simpleAdapterIng.getItem(position);
            if (simpleAdapterIng.removeItem(toMove, position)) {
                simpleAdapterCart.addItem(toMove);

            }
        }
        if (type == TYPE_CART) {
            Ingrediente toMove = simpleAdapterCart.getItem(position);
            if (simpleAdapterCart.removeItem(toMove, position)) {
                simpleAdapterIng.addItem(toMove);
            }
        }

        YoYo.with(Techniques.RubberBand)
                .duration(800)
                .playOn(footerText2);
        footerText2.setText("Nº de Ingredientes: " + cart.size());

        if (cart.size() > 0 && footer.getVisibility() == View.GONE) {
            YoYo.with(Techniques.SlideInUp)
                    .duration(500)
                    .playOn(footer);
            footer.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.BounceInUp)
                    .duration(500)
                    .playOn(actionButton);
            actionButton.setVisibility(View.VISIBLE);
            normal = (FloatingActionButton.LayoutParams)actionButton.getLayoutParams();
            normal.setMargins(0,0,dpToPx(16),dpToPx(16));
            actionButton.setLayoutParams(normal);
        } else if(cart.size() == 0 && footer.getVisibility() == View.VISIBLE){
            YoYo.with(Techniques.SlideOutDown)
                    .duration(500)
                    .playOn(footer);
            YoYo.with(Techniques.SlideOutDown)
                    .duration(500)
                    .playOn(actionButton);

            listCart.setVisibility(View.GONE);
            footerText1.setVisibility(View.GONE);
            footerText2.setVisibility(View.VISIBLE);
            footer.setVisibility(View.GONE);
            actionButton.setVisibility(View.GONE);

            actionButton.setBackgroundResource(R.drawable.fab_primary);
            footerbar.setBackgroundColor(getResources().getColor(R.color.primary));
        }
    }

    public int dpToPx(int dp){
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int px = Math.round(dp*(displayMetrics.xdpi/DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

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
