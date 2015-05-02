package com.madgoatstd.lazycheff.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.madgoatstd.lazycheff.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private View container;
    private ClickListener clickListener;
    List<String> data;

    public RecipeAdapter(Context context, List<String> data) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        this.data = data;

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(1000)
                .cacheOnDisk(true) // default
                .build();

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.defaultDisplayImageOptions(options);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;

        if (viewType == 0) {
            container = inflater.inflate(R.layout.header_view, parent, false);
            holder = new firstViewHolder(container);
        } else if (viewType == 1) {
            container = inflater.inflate(R.layout.time_difficult_row, parent, false);
            holder = new secondViewHolder(container);

        } else if (viewType == 2 || viewType == 3) {
            container = inflater.inflate(R.layout.simple_row, parent, false);
            holder = new thirdViewHolder(container, viewType);

        } else {
            container = inflater.inflate(R.layout.indication_row, parent, false);
            holder = new fourthViewHolder(container);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String linea = data.get(position);
        switch (position) {
            case 0:
                String[] nombretipo = linea.split(";");
                final firstViewHolder holder1 = (firstViewHolder) holder;
                holder1.title.setText(nombretipo[0]);
                ImageLoader.getInstance().loadImage(nombretipo[2], new SimpleImageLoadingListener(){
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        holder1.image.setImageBitmap(loadedImage);
                    }
                });
                break;
            case 1:
                String[] tiempodificultad = linea.split(";");
                secondViewHolder holder2 = (secondViewHolder) holder;
                holder2.time.setText(tiempodificultad[0] + " minutos");
                int d = Integer.valueOf(tiempodificultad[1]);
                if (d == 1)
                    holder2.difficult.setText("FÁCIL");
                if (d == 2)
                    holder2.difficult.setText("MEDIA");
                if (d == 3)
                    holder2.difficult.setText("DIFICIL");

                break;
            case 2:
                thirdViewHolder holder3 = (thirdViewHolder) holder;
                holder3.title.setText(linea);
                break;
            case 3:
                thirdViewHolder holder4 = (thirdViewHolder) holder;
                holder4.title.setText(linea);
                break;
            case 4:
                fourthViewHolder holder5 = (fourthViewHolder) holder;
                holder5.indication.setText(linea);
                break;
            default:
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

    /**
     * HEADER *
     */
    class firstViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public firstViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }

    /**
     * TIEMPO Y DIFICULTAD *
     */
    class secondViewHolder extends RecyclerView.ViewHolder {
        TextView time, difficult;

        public secondViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.tiempo);
            difficult = (TextView) itemView.findViewById(R.id.dificultad);

        }


    }

    /**
     * UTENCILIOS E INGREDIENTES *
     */
    class thirdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView image, image2;
        int type;

        public thirdViewHolder(View itemView, int type) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listTitle);
            image = (ImageView) itemView.findViewById(R.id.listIcon);
            image2 = (ImageView) itemView.findViewById(R.id.listAdd);
            image2.setVisibility(View.GONE);
            this.type = type;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.itemClicked(view, getAdapterPosition(), type);
        }

    }

    /**
     * PASOS
     */

    class fourthViewHolder extends RecyclerView.ViewHolder {
        TextView indication;
        public fourthViewHolder(View itemView) {
            super(itemView);
            indication = (TextView)itemView.findViewById(R.id.indication_text);
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position, int type);
    }

}
