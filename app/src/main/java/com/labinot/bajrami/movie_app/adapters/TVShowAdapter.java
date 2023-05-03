package com.labinot.bajrami.movie_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.labinot.bajrami.movie_app.R;
import com.labinot.bajrami.movie_app.databinding.ItemContainerTvShowBinding;
import com.labinot.bajrami.movie_app.listeners.TVShowListeners;
import com.labinot.bajrami.movie_app.models.TVShow;

import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.ViewHolder> {

    private List<TVShow> mList;
    private LayoutInflater layoutInflater;
    private TVShowListeners tvShowListeners;

    public TVShowAdapter(List<TVShow> mList,TVShowListeners tvShowListeners) {
        this.mList = mList;
        this.tvShowListeners = tvShowListeners;
    }

    @NonNull
    @Override
    public TVShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        ItemContainerTvShowBinding tvShowBinding = DataBindingUtil.inflate(
                layoutInflater,R.layout.item_container_tv_show,parent,false
        );

        return new ViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowAdapter.ViewHolder holder, int position) {

        holder.bindTvShow(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerTvShowBinding itemContainerTvShowBinding;
        public ViewHolder(@NonNull ItemContainerTvShowBinding itemContainerTvShowBinding) {
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding = itemContainerTvShowBinding;
        }

        public void bindTvShow(TVShow tvShow){

            itemContainerTvShowBinding.setTvShow(tvShow);
            itemContainerTvShowBinding.executePendingBindings();
            itemContainerTvShowBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvShowListeners.onTVShowClicked(tvShow);
                }
            });

        }
    }

}
