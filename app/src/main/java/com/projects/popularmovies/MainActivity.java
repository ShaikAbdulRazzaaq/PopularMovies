package com.projects.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView gridRecyclerView;
    private ProgressBar progressBar;
    private static String API_KEY;
    private List<Result>moviesData;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        API_KEY=getResources().getString(R.string.api_key);
        gridRecyclerView=findViewById(R.id.rv_moviesList);
        progressBar=findViewById(R.id.progress);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        gridRecyclerView.setLayoutManager(gridLayoutManager);
        progressBar.setVisibility(View.VISIBLE);
       popularMovies();
    }

    private void popularMovies() {
        if (UtilsClass.getInstance().isNetworkAvailable(this)) {
            if (retrofit == null) {
                retrofit = RetrofitSingleton.getRetrofitInstance();
            }
            GetDataFromMovieDB getDataFromMovieDB = retrofit.create(GetDataFromMovieDB.class);
            Call<MainModelClass> call = getDataFromMovieDB.getPopularMovies(API_KEY, getResources().getString(R.string.LANGUAGE), 1);
            Log.i("Popular movies api", getDataFromMovieDB.getPopularMovies(API_KEY, getResources().getString(R.string.LANGUAGE), 1).request().url().toString());
            call.enqueue(new Callback<MainModelClass>() {
                @Override
                public void onResponse(Call<MainModelClass> call, Response<MainModelClass> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        moviesData = Objects.requireNonNull(response.body()).getResults();
                        Log.i(TAG, "onResponse:  "+response.message()+ response.body().getTotalResults() );
                        if (moviesData != null) {
                            generateMovieList(moviesData);
                            Log.i(TAG, "Number of popular movies received: " + moviesData.size());
                        }
                    }
                }
                @Override
                public void onFailure(Call<MainModelClass> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e(TAG, "Network Status: Not available");
        }
    }
    private void topRatedMovies(){
        if (UtilsClass.getInstance().isNetworkAvailable(this)) {
            if (retrofit == null) {
                retrofit = RetrofitSingleton.getRetrofitInstance();
            }
            GetDataFromMovieDB getDataFromMovieDB = retrofit.create(GetDataFromMovieDB.class);
            Call<MainModelClass> call = getDataFromMovieDB.getTopRatedMovies(API_KEY, getResources().getString(R.string.LANGUAGE), 1);
            Log.i("Popular movies api", getDataFromMovieDB.getTopRatedMovies(API_KEY, getResources().getString(R.string.LANGUAGE), 1).request().url().toString());
            call.enqueue(new Callback<MainModelClass>() {
                @Override
                public void onResponse(Call<MainModelClass> call, Response<MainModelClass> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        moviesData = Objects.requireNonNull(response.body()).getResults();
                        Log.i(TAG, "onResponse:  "+response.message()+ response.body().getTotalResults() );
                        if (moviesData != null) {
                            generateMovieList(moviesData);
                            Log.i(TAG, "Number of popular movies received: " + moviesData.size());
                        }
                    }
                }
                @Override
                public void onFailure(Call<MainModelClass> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e(TAG, "Network Status: Not available");
        }
    }

    private void generateMovieList(List<Result> moviesData) {
        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(this,moviesData, thumbnailIndex -> {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("Movies",moviesData.get(thumbnailIndex));
            Log.i(TAG, "adapterSet: "+thumbnailIndex );
            startActivity(intent);
        });
        Log.e(TAG, "adapterSet:  size of the list is "+moviesData.size() );
        gridRecyclerView.setHasFixedSize(true);
        gridRecyclerView.setAdapter(recyclerAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.topRatedSortSetting):
                Log.i(TAG, "onOptionsItemSelected: Top Rated Button Clicked");
                progressBar.setVisibility(View.VISIBLE);
                topRatedMovies();
                setTitle("Top Rated Movies");
                break;
            case (R.id.mostPopularSortSetting):
                Log.e(TAG, "onOptionsItemSelected: most Popular Button Clicked");
                progressBar.setVisibility(View.VISIBLE);
                popularMovies();
                setTitle("Popular Movies");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
