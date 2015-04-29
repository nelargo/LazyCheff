package com.madgoatstd.lazycheff.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.madgoatstd.lazycheff.R;
import java.util.Collections;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private View container;
    private ClickListener clickListener;
    List<Recipe> data = Collections.emptyList();

    public RecipeAdapter(Context context, List<Recipe> data) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;

        if(viewType == 0) {
            container = inflater.inflate(R.layout.header_view, parent, false);
            holder = new firstViewHolder(container);
        }
        else if(viewType == 1){
            container = inflater.inflate(R.layout.time_difficult_row, parent, false);
            holder = new secondViewHolder(container);

        }else if(viewType == 2 || viewType == 3){
            container = inflater.inflate(R.layout.simple_row, parent, false);
            holder = new thirdViewHolder(container);

        }else{
            container = inflater.inflate(R.layout.simple_row, parent, false);
            holder = new fourthViewHolder(container);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

       switch(position){
           case 0:
               firstViewHolder holder1 = (firstViewHolder) holder;
               holder1.title.setText("Huevos con Tocino");
               break;
           case 1:
               secondViewHolder holder2 = (secondViewHolder) holder;
               holder2.time.setText("10 minutos");
               holder2.difficult.setText("1/3");
               break;
           case 2:
               thirdViewHolder holder3 = (thirdViewHolder) holder;
               holder3.title.setText("Utensilios");
               break;
           case 3:
               thirdViewHolder holder4 = (thirdViewHolder)holder;
               holder4.title.setText("Ingredientes");
               break;
       }

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /** HEADER **/
    class firstViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;
        public firstViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            image = (ImageView)itemView.findViewById(R.id.thumbnail);
        }
    }

    /** TIEMPO Y DIFICULTAD **/
    class secondViewHolder extends RecyclerView.ViewHolder {
        TextView time, difficult;
        public secondViewHolder(View itemView) {
            super(itemView);
            time =(TextView)itemView.findViewById(R.id.tiempo);
            difficult =(TextView)itemView.findViewById(R.id.dificultad);

        }


    }

    /** UTENCILIOS E INGREDIENTES **/
    class thirdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView image, image2;
        public thirdViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.listTitle);
            image = (ImageView)itemView.findViewById(R.id.listIcon);
            image2 = (ImageView)itemView.findViewById(R.id.listAdd);
            image2.setVisibility(View.GONE);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(clickListener != null)
                clickListener.itemClicked(view, getAdapterPosition());
        }

    }

    /** PASOS */

    class fourthViewHolder extends RecyclerView.ViewHolder{
        public fourthViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }

}
