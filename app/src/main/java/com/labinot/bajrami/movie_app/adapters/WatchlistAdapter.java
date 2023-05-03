package com.labinot.bajrami.movie_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.labinot.bajrami.movie_app.R;
import com.labinot.bajrami.movie_app.databinding.ItemContainerTvShowBinding;
import com.labinot.bajrami.movie_app.listeners.WatchlistListener;
import com.labinot.bajrami.movie_app.models.TVShow;

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.ViewHolder> {

    private List<TVShow> mList;
    private LayoutInflater layoutInflater;
    private WatchlistListener watchlistListener;

    public WatchlistAdapter(List<TVShow> mList, WatchlistListener watchlistListener) {
        this.mList = mList;
        this.watchlistListener = watchlistListener;
    }

    @NonNull
    @Override
    public WatchlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        ItemContainerTvShowBinding tvShowBinding = DataBindingUtil.inflate(
                layoutInflater,R.layout.item_container_tv_show,parent,false
        );

        return new ViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistAdapter.ViewHolder holder, int position) {

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
                    watchlistListener.onTVShowClicked(tvShow);
                }
            });
            itemContainerTvShowBinding.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchlistListener.removeTVShowFromWatchlist(tvShow,getAdapterPosition());
                }
            });
            itemContainerTvShowBinding.imageDelete.setVisibility(View.VISIBLE);

        }
    }

}
