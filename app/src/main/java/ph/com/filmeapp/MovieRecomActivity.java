package ph.com.filmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MovieRecomActivity extends AppCompatActivity {

    private RecyclerView postRecyclerView;
    private RecyclerView.Adapter postAdapter;
    ArrayList<Post> postArrayList = new ArrayList<>();
    private RecyclerView.LayoutManager postLayoutManager;


    private TextView tvGenreHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_recom);

        // Initialize and Assign Variable

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

        // Set Home Selected

        bottomNavigationView.setSelectedItemId(R.id.nb_home);

        // Perform ItemSelectedListener

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {

                switch(menuItem.getItemId()){

                    case R.id.nb_home:
                        return true;
                    case R.id.nb_add:
                        startActivity(new Intent(getApplicationContext(), AddRecomActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nb_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nb_logout:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nb_bookmarks:
                        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }


                return false;
            }
        });


        this.tvGenreHome = findViewById(R.id.tv_genre_home);

        if(getIntent()!=null){
            this.tvGenreHome.setText(getIntent().getStringExtra("item"));
        }


        if(getIntent().getStringExtra("item").equals("Romance"))
        {
            postArrayList.add(new Post(
                    R.drawable.sample,
                    "Kimi No Nawa",
                    "Beautifully animated (we're talking Studio Ghibli standards here), Your Name captures that sensation of waking up from a dream you wish lasted longer, or misplacing a name that's still warm on your tongue, but has just departed.",
                    "Rating : 4",
                    "Kristen Yoonsoo Kim","romance"));
            postArrayList.add(new Post(
                    R.drawable.centi,
                    "5 Centimeters per Second",
                    "This movie blew me away - it was amazing. I can't tell you anything more other than it revolves around love, life and time. This film is stunning you simply must watch it! Don't be put off if you're not interested by the end of the first episode because by the end of the third episode everything comes together in one amazingly beautiful package. If ever a film deserved 10/10 this was it. If you have ever felt love you will adore this movie because it captures that feeling flawlessly.",
                    "Rating : 5",
                    "Jeff Shannon","romance"));

            postArrayList.add(new Post(
                    R.drawable.whisper_poster,
                    "Whisper of the Heart",
                    "Shizuku is a fantastic heroine and for all my creatively inclined people, if you haven't seen this movie yet, it'll remind you of the work and honing of craft needed to be great at anything you love. ",
                    "Rating : 5",
                    "Princess Weekes","romance"));



        }


        if(getIntent().getStringExtra("item").equals("Sci-Fi"))
        {

            postArrayList.add(new Post(
                    R.drawable.star,
                    "Star Wars",
                    "The technical wizardry, the special effects, the ear-buzzing sound, the mind-numbing action and those magnificent light-sabres are all there.",
                    "Rating : 5",
                    "Namrata Joshi","drama"));
            postArrayList.add(new Post(
                    R.drawable.guardians,
                    "Guardians of the Galaxy",
                    "For much of its 122 minutes, Gunn's grab-bag of quirky characters, genre ribbing, and incongruent '70s pop tunes goes over like gangbusters. The trouble is, it's only irreverent to a point. ",
                    "Rating : 3",
                    "Jason Bailey","drama"));

            postArrayList.add(new Post(
                    R.drawable.et,
                    "E.T.",
                    "This is a real movie, with all those elements that have proved sure-fire through history; Laughter, tears, involvement, thrills, wonderment. Steven Spielberg also adds a message: Human beings and spacelings should learn to co-exist.",
                    "Rating : 5",
                    "Bob Thomas","drama"));


        }


        // romance : kimi no nawa, 5 centimeters per seocnd , Whisper of the Heart

        // sci-fi: star wars, guardians ofthe galaxy, et



        this.postRecyclerView = findViewById(R.id.rv_posts_home);


        this.postLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false );
        this.postRecyclerView.setLayoutManager(postLayoutManager);

        this.postAdapter = new PostAdapter(postArrayList, MovieRecomActivity.this);
        this.postRecyclerView.setAdapter(postAdapter);
        this.postAdapter.notifyDataSetChanged();






/*
        postRecyclerView = findViewById(R.id.rv_posts_home);
        //postRecyclerView .setHasFixedSize(true);
        postLayoutManager = new LinearLayoutManager(this);
        postAdapter = new PostAdapter(postArrayList, MovieRecomActivity.this);
        postRecyclerView.setLayoutManager(postLayoutManager);
        postRecyclerView.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();
*/







    }
}