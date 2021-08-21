package ph.com.filmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.sql.DatabaseMetaData;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvname;
    private TextView tvusername;
    private TextView tvdescription;
    private TextView tvbirthday;
    private Button btedit;
    private ProgressBar pbProfile;
    private RecyclerView rvPosts;


    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }


                return false;
            }
        });

        this.initComponents();
        this.initFirebase();
    }

    private void initComponents(){
        this.tvname = findViewById(R.id.tv_profile_name);
        this.tvusername = findViewById(R.id.tv_profile_username);
        this.tvdescription = findViewById(R.id.tv_profile_description);
        this.tvbirthday = findViewById(R.id.tv_profile_birthday);
        this.btedit = findViewById(R.id.btn_profile_edit);
        this.pbProfile = findViewById(R.id.pb_profile);

    }

    private void initFirebase(){
        this.mAuth = FirebaseAuth.getInstance();
        this.user = this.mAuth.getCurrentUser();
        this.userId = this.user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Collections.users.name());

        this.pbProfile.setVisibility(View.VISIBLE);
        reference.child(this.userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                tvname.setText(name);
                String username = snapshot.child("username").getValue().toString();
                tvusername.setText(username);

                pbProfile.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pbProfile.setVisibility(View.GONE);

            }
        });

    }
}