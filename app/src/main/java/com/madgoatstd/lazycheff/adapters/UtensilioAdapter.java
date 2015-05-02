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

public class UtensilioAdapter extends RecyclerView.Adapter<UtensilioAdapter.MyViewHolder> {
    private static int TYPE_INGREDIENTS = 0;
    private static int TYPE_CART = 1;
    private Context mContext;
    private LayoutInflater inflater;
    private View container;
    //List<Utensilio_Receta> data = Collections.emptyList();
    List<Utensilio> data = Collections.emptyList();
    int lastPosition = -1;

    public List<Utensilio> getAllItems(){
        return data;
    }

    public UtensilioAdapter(Context context, List<Utensilio_Receta> data) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        this.data = getUtensilios(data);
    }

    private List<Utensilio> getUtensilios(List<Utensilio_Receta> a){
        List<Utensilio> result = new ArrayList<>();
        UtensilioDataSource i = new UtensilioDataSource(mContext);
        i.open();
        for(Utensilio_Receta u : a) {
            result.add(i.getUtensilio(u.getId_utensilio()));
        }
        i.close();
        return result;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        container = inflater.inflate(R.layout.simple_row, parent, false);
        MyViewHolder holder = new MyViewHolder(container);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Utensilio current = data.get(position);
        holder.title.setText(current.getNombre());
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
        ImageView licon, ricon;
        View container;
        int type;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.type = type;
            this.container = itemView;
            title = (TextView) itemView.findViewById(R.id.listTitle);
            licon = (ImageView) itemView.findViewById(R.id.listIcon);
            ricon = (ImageView) itemView.findViewById(R.id.listAdd);
            licon.setVisibility(View.GONE);
            ricon.setVisibility(View.GONE);
        }


    }

}
