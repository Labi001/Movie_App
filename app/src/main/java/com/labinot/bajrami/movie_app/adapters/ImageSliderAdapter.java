package com.labinot.bajrami.movie_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.labinot.bajrami.movie_app.R;
import com.labinot.bajrami.movie_app.databinding.ItemContainerSliderImageBinding;
import com.labinot.bajrami.movie_app.databinding.ItemContainerTvShowBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {

    private String[] sliderImage;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(String[] sliderImage) {
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public ImageSliderAdapter.ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        ItemContainerSliderImageBinding sliderImageBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_container_slider_image,parent,false);


        return new ImageSliderViewHolder(sliderImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderAdapter.ImageSliderViewHolder holder, int position) {

        holder.bindSliderImage(sliderImage[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }

    static class ImageSliderViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerSliderImageBinding itemContainerSliderImageBinding;

        public ImageSliderViewHolder(@NonNull ItemContainerSliderImageBinding itemContainerSliderImageBinding ) {
            super(itemContainerSliderImageBinding.getRoot());
            this.itemContainerSliderImageBinding = itemContainerSliderImageBinding;
        }

        public void bindSliderImage(String imageUrl){

              itemContainerSliderImageBinding.setImageURL(imageUrl);
        }

    }
}
