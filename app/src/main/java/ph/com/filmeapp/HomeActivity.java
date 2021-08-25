package ph.com.filmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

public class HomeActivity extends AppCompatActivity {


    private CardView cvComedy;
    private CardView cvDrama;
    private CardView cvRomance;
    private CardView cvHorror;
    private CardView cvAction;
    private CardView cvScifi;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        // Initializing Components

        initComponents();


        // Setting On Click Listeners

        cvComedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MovieRecomActivity.class);
                intent.putExtra("item", "Comedy");
                startActivity(intent);
                finish();
            }
        });

        cvDrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MovieRecomActivity.class);
                intent.putExtra("item", "Drama");
                startActivity(intent);
                finish();
            }
        });

        cvRomance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MovieRecomActivity.class);
                intent.putExtra("item", "Romance");
                startActivity(intent);
                finish();
            }
        });

        cvHorror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MovieRecomActivity.class);
                intent.putExtra("item", "Thriller");
                startActivity(intent);
                finish();
            }
        });
        cvAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MovieRecomActivity.class);
                intent.putExtra("item", "Action");
                startActivity(intent);
                finish();
            }
        });

        cvScifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MovieRecomActivity.class);
                intent.putExtra("item", "Sci-Fi");
                startActivity(intent);
                finish();
            }
        });



    }




    private void initComponents(){
        this.cvComedy = findViewById(R.id.cv_comedy_hp);
        this.cvDrama = findViewById(R.id.cv_drama_hp);
        this.cvRomance = findViewById(R.id.cv_romance_hp);
        this.cvHorror = findViewById(R.id.cv_horror_hp);
        this.cvAction = findViewById(R.id.cv_action_hp);
        this.cvScifi = findViewById(R.id.cv_scifi_hp);

    }



}