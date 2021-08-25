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
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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



       postArrayList.add(new Post(
                R.drawable.sample,
                "Kimi No Nawa",
                "Lorem ipsum dolor  eni ut alrum",
                "5",
                "Alenna Cayton","romance"));
        postArrayList.add(new Post(
                R.drawable.pulpfiction,
                "Pulp Fiction",
                "Lorem ipsum dolor sit amet,  elit,ialiqua. Ut enim ad minim veniamea commodo consequat. Duis autre eu fugiat nullallit anim id est laborum",
                "3",
                "Cheska Mendoza","action"));
        postArrayList.add(new Post(
                R.drawable.starwars,
                "Star Wars",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque rhoncus eu risus aliquet venenatis. Mauris ipsum mi, convallis eu vestibulum in, venenatis sit amet risus. Duis aliquet mattis enim ac ornare. Nunc blandit nibh lectus, vitae mattis elit blandit ac. Aenean in semper libero, eget finibus lectus. Proin ullamcorper odio sed ex dapibus blandit. Proin gravida cursus felis, sed porttitor purus sollicitudin a. Duis auctor rutrum dictum. In sagittis, sapien vitae posuere vestibulum, augue neque viverra nibh, a cursus eros magna quis elit.",
                "5",
                "Cheska Roque","drama"));
        postArrayList.add(new Post(
                R.drawable.wandavision,
                "Wanda Vision",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, truccaecat ct mollit anim id est laborum",
                "1",
                "Candy Candy","comedy"));





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