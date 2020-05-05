package com.projects.popularmovies;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    private TextView TitleDetailActivity;
    private TextView releaseDate;
    private TextView rating;
    private TextView overview;
    private TextView language;
    private ImageView thumbnailDetailActivity;
    private static final String PosterUrl = "https://image.tmdb.org/t/p/w500";
    private static final String TAG = "DetailsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle("Details");
        TitleDetailActivity = findViewById(R.id.TitleDetailActivity);
        releaseDate = findViewById(R.id.releaseDate);
        rating = findViewById(R.id.rating);
        overview = findViewById(R.id.overview);
        language = findViewById(R.id.language);
        thumbnailDetailActivity = findViewById(R.id.thumbnailDetailActivity);
        Bundle b;
        b = getIntent().getExtras();
        if (b != null) {
            Result result = b.getParcelable("Movies");
            assert result != null;
            TitleDetailActivity.setText(result.getTitle());
            overview.setText(result.getOverview());
            rating.setText(new StringBuilder("Rating: ").append(result.getVoteAverage()));
            releaseDate.setText(new StringBuilder("Release Date: ").append(result.getReleaseDate()));
            language.setText(new StringBuilder("Language: ").append(result.getOriginalLanguage()));
            Picasso.get()
                    .load(PosterUrl + result.getBackdropPath())
                    .into(thumbnailDetailActivity);
            Log.e(TAG, "onCreate: " + PosterUrl + result.getBackdropPath());
        }
    }
}
