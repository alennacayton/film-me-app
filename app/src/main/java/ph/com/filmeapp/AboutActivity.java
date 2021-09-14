package ph.com.filmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Initialize and Assign Variable

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));


        // Set Home Selected

        bottomNavigationView.setSelectedItemId(R.id.nb_bookmarks);

        // Perform ItemSelectedListener

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {

                switch(menuItem.getItemId()){

                    case R.id.nb_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
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
                        return true;
                }


                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //status to add
    }

    @Override
    protected void onPause() {
        super.onPause();
        //status to add
    }
}