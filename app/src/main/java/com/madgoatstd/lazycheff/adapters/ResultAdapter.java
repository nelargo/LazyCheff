package com.madgoatstd.lazycheff.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.madgoatstd.lazycheff.R;
import com.madgoatstd.lazycheff.database.Receta;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private View container;
    private ClickListener clickListener;
    List<Receta> data = Collections.emptyList();
    int lastPosition = -1;
    private int type;
    ImageLoader imageLoader;

    public ResultAdapter(Context context, List<Receta> data) {
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

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public Receta getItem(int position) {
        return data.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        container = inflater.inflate(R.layout.result_row, parent, false);
        MyViewHolder holder = new MyViewHolder(container);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Receta current = data.get(position);
        int dificultad = Integer.valueOf(current.getDificultad());
        if(dificultad == 1)
            holder.difficult.setText("Dificultad: FACIL");
        if(dificultad == 2)
            holder.difficult.setText("Dificultad: MEDIA");
        if(dificultad == 3)
            holder.difficult.setText("Dificultad: DIFICIL");

        holder.title.setText(current.getNombre());
        holder.time.setText("Tiempo: "+current.getTiempo()+" Minutos");
        holder.type.setText(current.getTipo());
        ImageLoader.getInstance().loadImage(current.getImagen(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                Log.i("IMAGELOADER","onLoadingStarted: "+ s);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                Log.i("IMAGELOADER", "onLoadingFailed: "+s);
                holder.icon.setImageResource(R.drawable.no_thumbnail);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.icon.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                Log.i("IMAGELOADER", "onCancel: "+s);
                holder.icon.setImageResource(R.drawable.no_thumbnail);
            }
        });
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


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title, difficult, time, type;
        ImageView icon;


        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            type = (TextView) itemView.findViewById(R.id.type);
            difficult = (TextView) itemView.findViewById(R.id.difficult);
            time = (TextView) itemView.findViewById(R.id.time);
            icon = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.itemClicked(view, getAdapterPosition(), data);
                }

        }
    }
    public interface ClickListener {
        public void itemClicked(View view, int position, List<Receta> actual);
    }
}
