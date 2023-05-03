package com.labinot.bajrami.movie_app.activities;

import static com.labinot.bajrami.movie_app.utils.Constants.ID;
import static com.labinot.bajrami.movie_app.utils.SnackBar.showMessageSnackBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.labinot.bajrami.movie_app.R;
import com.labinot.bajrami.movie_app.adapters.EpisodesAdapter;
import com.labinot.bajrami.movie_app.adapters.ImageSliderAdapter;
import com.labinot.bajrami.movie_app.databinding.ActivityTvshowDetailBinding;
import com.labinot.bajrami.movie_app.databinding.LayoutEpisodesBottomSheetBinding;
import com.labinot.bajrami.movie_app.models.TVShow;
import com.labinot.bajrami.movie_app.utils.Constants;
import com.labinot.bajrami.movie_app.viewmodels.TVSHowDetailViewModel;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.internal.Util;

public class TVShowDetailActivity extends AppCompatActivity {

    private ActivityTvshowDetailBinding activityTvshowDetailBinding;
    private TVSHowDetailViewModel tvShowDetailViewModel;
    private BottomSheetDialog episodesBottomSheetDialog;
    private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;
    private TVShow tvShow;
    private Boolean isTVShowAvailableInWatchList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvshowDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_tvshow_detail);

        doInitialization();

    }

    private void doInitialization() {

        tvShowDetailViewModel = new ViewModelProvider(this).get(TVSHowDetailViewModel.class);
        activityTvshowDetailBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        tvShow = (TVShow) getIntent().getSerializableExtra("tvShow");
        checkTvShowInWatchList();
        getTvShowDetails();

    }

    private void checkTvShowInWatchList(){

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(tvShowDetailViewModel.getTVShowFromWatchList(String.valueOf(tvShow.getId()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShow -> {

                    isTVShowAvailableInWatchList = true;
                    activityTvshowDetailBinding.imgWatchList.setImageResource(R.drawable.ic_check);
                    compositeDisposable.dispose();

                }));

    }

    private void getTvShowDetails() {

        activityTvshowDetailBinding.setIsLoading(true);
        String tvShowId = String.valueOf(tvShow.getId());
        tvShowDetailViewModel.getTVShowDetails(tvShowId).observe(this,tvShowDetailsResponse -> {
             activityTvshowDetailBinding.setIsLoading(false);
                    if(tvShowDetailsResponse.getTvShowDetails() != null){

                        if(tvShowDetailsResponse.getTvShowDetails().getPictures() != null)
                            loadImageSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());

                        activityTvshowDetailBinding.setTvShowImageURL(

                                tvShowDetailsResponse.getTvShowDetails().getImagePath()
                        );

                        activityTvshowDetailBinding.imageTVShow.setVisibility(View.VISIBLE);
                        activityTvshowDetailBinding.setDescription(

                                String.valueOf(
                                        HtmlCompat.fromHtml(

                                                tvShowDetailsResponse.getTvShowDetails().getDescription(),
                                                HtmlCompat.FROM_HTML_MODE_LEGACY
                                        )
                                )

                        );
                        activityTvshowDetailBinding.txtDescription.setVisibility(View.VISIBLE);
                        activityTvshowDetailBinding.txtReadMore.setVisibility(View.VISIBLE);
                        activityTvshowDetailBinding.txtReadMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(activityTvshowDetailBinding.txtReadMore.getText().toString().equals(getString(R.string.read_more))){
                                    activityTvshowDetailBinding.txtDescription.setMaxLines(Integer.MAX_VALUE);
                                    activityTvshowDetailBinding.txtDescription.setEllipsize(null);
                                    activityTvshowDetailBinding.txtReadMore.setText(R.string.read_less);
                                }else{

                                    activityTvshowDetailBinding.txtDescription.setMaxLines(4);
                                    activityTvshowDetailBinding.txtDescription.setEllipsize(TextUtils.TruncateAt.END);
                                    activityTvshowDetailBinding.txtReadMore.setText(R.string.read_more);

                                };
                            }
                        });
                        activityTvshowDetailBinding.setRating(

                                String.format(
                                        Locale.getDefault(),
                                        "%.2f",
                                        Double.parseDouble(tvShowDetailsResponse.getTvShowDetails().getRating())

                                )

                        );
                        if(tvShowDetailsResponse.getTvShowDetails().getGenres() != null)
                            activityTvshowDetailBinding.setGenre(tvShowDetailsResponse.getTvShowDetails().getGenres()[0]);
                        else
                            activityTvshowDetailBinding.setGenre("N/A");

                        activityTvshowDetailBinding.setRunTime(tvShowDetailsResponse.getTvShowDetails().getRuntime() + " Min");
                        activityTvshowDetailBinding.viewDivide1.setVisibility(View.VISIBLE);
                        activityTvshowDetailBinding.viewDivider2.setVisibility(View.VISIBLE);
                        activityTvshowDetailBinding.layoutMisc.setVisibility(View.VISIBLE);
                        activityTvshowDetailBinding.buttonWebsite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(tvShowDetailsResponse.getTvShowDetails().getUrl()));
                                startActivity(intent);
                            }
                        });
                        activityTvshowDetailBinding.buttonWebsite.setVisibility(View.VISIBLE);
                        activityTvshowDetailBinding.buttonEpisodes.setVisibility(View.VISIBLE);
                        activityTvshowDetailBinding.buttonEpisodes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(episodesBottomSheetDialog == null){

                                    episodesBottomSheetDialog = new BottomSheetDialog(TVShowDetailActivity.this);
                                    layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(

                                            LayoutInflater.from(TVShowDetailActivity.this),
                                            R.layout.layout_episodes_bottom_sheet,
                                            findViewById(R.id.episodesContainer),
                                            false
                                    );

                                    episodesBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot());
                                    layoutEpisodesBottomSheetBinding.episodesRecyclerView.setAdapter(

                                            new EpisodesAdapter(tvShowDetailsResponse.getTvShowDetails().getEpisodes())
                                    );
                                    layoutEpisodesBottomSheetBinding.textTitle.setText(
                                            String.format("Episodes | %s", tvShow.getName())
                                    );
                                    layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            episodesBottomSheetDialog.dismiss();
                                        }
                                    });

                                }

                                FrameLayout frameLayout = episodesBottomSheetDialog.findViewById(
                                        com.google.android.material.R.id.design_bottom_sheet
                                );
                                if(frameLayout != null){
                                    BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                                    bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                }
                                 episodesBottomSheetDialog.show();
                            }
                        });

                        activityTvshowDetailBinding.imgWatchList.setOnClickListener(view -> {

                            CompositeDisposable compositeDisposable = new CompositeDisposable();

                            if(isTVShowAvailableInWatchList){

                                compositeDisposable.add(tvShowDetailViewModel.removeTvShowFromWatchList(tvShow)
                                        .subscribeOn(Schedulers.computation())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() ->{

                                            isTVShowAvailableInWatchList = false;
                                            Constants.IS_WATCHLIST_UPDATE = true;
                                            activityTvshowDetailBinding.imgWatchList.setImageResource(R.drawable.ic_eyes);
                                            showMessageSnackBar(TVShowDetailActivity.this,findViewById(R.id.mLayout),findViewById(R.id.imgWatchList),"Removed from WatchList !");
                                            compositeDisposable.dispose();
                                        }));

                            }else{

                                compositeDisposable.add(tvShowDetailViewModel.addToWatchList(tvShow)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> {
                                            Constants.IS_WATCHLIST_UPDATE = true;
                                            activityTvshowDetailBinding.imgWatchList.setImageResource(R.drawable.ic_check);
                                            showMessageSnackBar(TVShowDetailActivity.this,findViewById(R.id.mLayout),findViewById(R.id.imgWatchList),"Added to WatchList !");
                                            compositeDisposable.dispose();

                                        })
                                );

                            }
                        });




                       activityTvshowDetailBinding.imgWatchList.setVisibility(View.VISIBLE);
                        loadBasicTVShowDetails();

                    }
        }
        );
    }

    private void loadImageSlider(String[] sliderImages){

        activityTvshowDetailBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTvshowDetailBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        activityTvshowDetailBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTvshowDetailBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setUpSliderIndicator(sliderImages.length);
        activityTvshowDetailBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setUpSliderIndicator(int count){

        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(

                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);

        for(int i = 0; i < indicators.length; i++){

            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slider_indicator_inactive
            ));

            indicators[i].setLayoutParams(layoutParams);
            activityTvshowDetailBinding.layoutSliderIndicator.addView(indicators[i]);
        }
        activityTvshowDetailBinding.layoutSliderIndicator.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);

    }

    private void setCurrentSliderIndicator(int position){

        int childCount = activityTvshowDetailBinding.layoutSliderIndicator.getChildCount();

        for(int i = 0; i < childCount; i++){

            ImageView imageView = (ImageView) activityTvshowDetailBinding.layoutSliderIndicator.getChildAt(i);

            if(i == position){

                imageView.setImageDrawable(

                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active)

                );
            }else{

                imageView.setImageDrawable(

                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive)

                );

            }

        }

    }

    private void loadBasicTVShowDetails(){

        activityTvshowDetailBinding.setTvShowName(tvShow.getName());
        activityTvshowDetailBinding.setStatus(tvShow.getStatus());
        activityTvshowDetailBinding.setStartedDate(tvShow.getStartDate());
        activityTvshowDetailBinding.setNetworkCountry(tvShow.getNetwork() +
                " (" + tvShow.getCountry() + ")");

        activityTvshowDetailBinding.textName.setVisibility(View.VISIBLE);
        activityTvshowDetailBinding.txtNetworkCountry.setVisibility(View.VISIBLE);
        activityTvshowDetailBinding.txtStarted.setVisibility(View.VISIBLE);
        activityTvshowDetailBinding.txtStatus.setVisibility(View.VISIBLE);

    }


}