package com.labinot.bajrami.movie_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.labinot.bajrami.movie_app.R;
import com.labinot.bajrami.movie_app.databinding.ItemContainerEpisodesBinding;
import com.labinot.bajrami.movie_app.models.Episode;

import java.util.List;


public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ViewHolder> {

    private List<Episode> episodes;
    private LayoutInflater layoutInflater;

    public EpisodesAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        ItemContainerEpisodesBinding containerEpisodesBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_episodes,parent,false
        );

        return new ViewHolder(containerEpisodesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesAdapter.ViewHolder holder, int position) {

        holder.bindEpisodes(episodes.get(position));

    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerEpisodesBinding itemContainerEpisodesBinding;

        public ViewHolder(@NonNull ItemContainerEpisodesBinding itemContainerEpisodesBinding) {
            super(itemContainerEpisodesBinding.getRoot());
            this.itemContainerEpisodesBinding = itemContainerEpisodesBinding;
        }


        public void bindEpisodes(Episode episode){

            String title = "S";
            String season = episode.getSeason();

            if(season.length() == 1)
                season = "0".concat(season);

            String episodeNumber = episode.getEpisode();
            if(episodeNumber.length() == 1)
                episodeNumber = "0".concat(episodeNumber);

            episodeNumber = "E".concat(episodeNumber);
            title = title.concat(season).concat(episodeNumber);

            itemContainerEpisodesBinding.setTitle(title);
            itemContainerEpisodesBinding.setName(episode.getName());
            itemContainerEpisodesBinding.setAirDate(episode.getAirDate());

        }
     }
}
