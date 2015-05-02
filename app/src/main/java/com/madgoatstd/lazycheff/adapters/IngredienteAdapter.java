package com.madgoatstd.lazycheff.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.madgoatstd.lazycheff.R;
import com.madgoatstd.lazycheff.database.Ingrediente;
import com.madgoatstd.lazycheff.database.IngredienteDataSource;
import com.madgoatstd.lazycheff.database.Ingrediente_Receta;
import com.madgoatstd.lazycheff.database.Utensilio;
import com.madgoatstd.lazycheff.database.UtensilioDataSource;
import com.madgoatstd.lazycheff.database.Utensilio_Receta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IngredienteAdapter extends RecyclerView.Adapter<IngredienteAdapter.MyViewHolder> {
    private static int TYPE_INGREDIENTS = 0;
    private static int TYPE_CART = 1;
    private Context mContext;
    private LayoutInflater inflater;
    private View container;
    List<Ingrediente_Receta> ur = Collections.emptyList();
    List<Ingrediente> data = Collections.emptyList();
    int lastPosition = -1;

    public List<Ingrediente> getAllItems(){
        return data;
    }

    public IngredienteAdapter(Context context, List<Ingrediente_Receta> data) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        this.data = getIngredientes(data);
        ur = data;
    }

    private List<Ingrediente> getIngredientes(List<Ingrediente_Receta> a){
        List<Ingrediente> result = new ArrayList<>();
        IngredienteDataSource i = new IngredienteDataSource(mContext);
        i.open();
        for(Ingrediente_Receta u : a) {
            result.add(i.getIngrediente(u.getId_ingrediente()));
        }
        i.close();
        return result;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        container = inflater.inflate(R.layout.simple_row_ingredient, parent, false);
        MyViewHolder holder = new MyViewHolder(container);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Ingrediente current = data.get(position);
        holder.title.setText(current.getNombre());
        holder.catidad.setText(ur.get(position).getCantidad());
        setAnimation(holder.itemView, position);
    }

    public void setAnimation(View toAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            toAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView catidad;
        View container;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.container = itemView;
            title = (TextView) itemView.findViewById(R.id.listTitle);
            catidad = (TextView) itemView.findViewById(R.id.cantidad);
        }


    }

}
