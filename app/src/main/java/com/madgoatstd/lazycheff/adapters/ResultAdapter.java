package com.madgoatstd.lazycheff.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.madgoatstd.lazycheff.R;

import java.util.Collections;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private View container;
    List<Recipe> data = Collections.emptyList();
    int lastPosition = -1;
    private int type;

    public ResultAdapter(Context context, List<Recipe> data) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        this.data = data;
    }

    public Recipe getItem(int position) {
        return data.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        container = inflater.inflate(R.layout.result_row, parent, false);
        MyViewHolder holder = new MyViewHolder(container);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Recipe current = data.get(position);
        holder.title.setText(current.name);
        holder.difficult.setText(current.DIFFICULT_PREFIX+current.difficult+current.DIFFICULT_POSTFIX);
        holder.time.setText(current.TIME_PREFIX+current.time+current.TIME_POSTFIX);
        holder.icon.setImageResource(R.mipmap.ic_launcher);
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
        TextView title, difficult, time;
        ImageView icon;


        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            difficult = (TextView) itemView.findViewById(R.id.difficult);
            time = (TextView) itemView.findViewById(R.id.time);
            icon = (ImageView) itemView.findViewById(R.id.image);

        }
    }
}
