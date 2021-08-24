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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MovieRecomActivity extends AppCompatActivity {

    private RecyclerView postRecyclerView;
    private RecyclerView.Adapter postAdapter;
    ArrayList<Post> postArrayList = new ArrayList<>();
    private RecyclerView.LayoutManager postLayoutManager;




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
                }


                return false;
            }
        });

/*
        postRecyclerView = findViewById(R.id.rv_posts_home);


        postLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false );
        postRecyclerView.setLayoutManager(postLayoutManager);



        // Initialize Data

        DataHelper data = new DataHelper();
        postArrayList = data.initializeData();



      //  postRecyclerView.setHasFixedSize(true);
        postAdapter = new PostAdapter(postArrayList, MovieRecomActivity.this);
        postRecyclerView.setAdapter(postAdapter);
       postAdapter.notifyDataSetChanged();


        //set the Categories for each array list set in the `ParentViewHolder`

        DataHelper data = new DataHelper();
        postArrayList = data.initializeData();


                */




       postArrayList.add(new Post(
                R.drawable.sample,
                "Kimi No Nawa",
                "Lorem ipsum dolor  eni ut alrum",
                "5",
                "Alenna Cayton","romance"));
        postArrayList.add(new Post(
                R.drawable.pulpfiction,
                "Pulp Fiction",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
                "3",
                "Cheska Mendoza","action"));
        postArrayList.add(new Post(
                R.drawable.starwars,
                "Star Wars",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
                "5",
                "Cheska Roque","drama"));
        postArrayList.add(new Post(
                R.drawable.wandavision,
                "Wanda Vision",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
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