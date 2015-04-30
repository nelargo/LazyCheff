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

import java.util.Collections;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.MyViewHolder> {
    private static int TYPE_INGREDIENTS = 0;
    private static int TYPE_CART = 1;
    private Context mContext;
    private LayoutInflater inflater;
    private View container;
    private ClickListener clickListener;
    List<Ingrediente> data = Collections.emptyList();
    int lastPosition = -1;
    private int type;

    public SimpleAdapter(Context context, List<Ingrediente> data, int type) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        this.data = data;
        this.type = type;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public boolean removeItem(Ingrediente toRemove, int position) {
        if (data.remove(toRemove)) {
            notifyItemRemoved(position);
            return true;
        }

        return false;
    }

    public Ingrediente getItem(int position) {
        return data.get(position);
    }

    public void addItem(Ingrediente ingredient) {
        data.add(0, ingredient);
        notifyItemInserted(0);


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        container = inflater.inflate(R.layout.simple_row, parent, false);
        MyViewHolder holder = new MyViewHolder(container, this.type);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Ingrediente current = data.get(position);
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


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView licon, ricon;
        View container;
        int type;

        public MyViewHolder(View itemView, int type) {
            super(itemView);
            this.type = type;
            this.container = itemView;
            title = (TextView) itemView.findViewById(R.id.listTitle);
            licon = (ImageView) itemView.findViewById(R.id.listIcon);
            ricon = (ImageView) itemView.findViewById(R.id.listAdd);
            if(type == TYPE_INGREDIENTS){
                ricon.setImageResource(R.drawable.add_icon_v1);
            }
            if(type == TYPE_CART){
                ricon.setImageResource(R.drawable.remove_icon_v1);
            }
            ricon.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.listAdd) {
                if (clickListener != null) {
                    clickListener.itemClicked(view, getAdapterPosition(), this.type);
                }
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position, int type);
    }
}
