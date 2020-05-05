package com.projects.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.thumbnailViewHolder> {
    private final Context context;
    private static final String imageUrl = "https://image.tmdb.org/t/p/w185";
    private final List<Result> movieData;
    private final ThumbnailClicked thumbnailClicked;

    interface ThumbnailClicked {
        void onThumbnailClicked(int thumbnailIndex);
    }

    RecyclerAdapter(Context context, List<Result> movieData, ThumbnailClicked thumbnailClicked) {
        this.thumbnailClicked = thumbnailClicked;
        this.movieData = movieData;
        this.context = context;
    }


    @NonNull
    @Override
    public thumbnailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new thumbnailViewHolder(LayoutInflater.from(context).inflate(R.layout.thumbnail_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull thumbnailViewHolder holder, int position) {
        Result result = movieData.get(position);
        Picasso.get()
                .load(imageUrl + result.getPosterPath())
                .into(holder.imageView);
        holder.textView.setText(result.getTitle());
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    public class thumbnailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;
        final Context context;
        final TextView textView;

        thumbnailViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnail);
            context = itemView.getContext();
            textView = itemView.findViewById(R.id.nameOfTheMovie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            thumbnailClicked.onThumbnailClicked(index);
        }
    }
}