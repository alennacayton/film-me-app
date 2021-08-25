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
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvname;
    private TextView tvusername;
    private TextView tvdescription;
    private Button btedit;
    private ProgressBar pbProfile;
    private RecyclerView rvPosts;


// postRecyclerView >> rvPosts

    private RecyclerView.Adapter postAdapter;
    ArrayList<Post> postArrayList = new ArrayList<>();
    private RecyclerView.LayoutManager postLayoutManager;







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


        // Set Profile Selected

        bottomNavigationView.setSelectedItemId(R.id.nb_profile);

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



        this.postLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false );
        this.rvPosts.setLayoutManager(postLayoutManager);

        this.postAdapter = new PostAdapter(postArrayList, ProfileActivity.this);
        this.rvPosts.setAdapter(postAdapter);
        this.postAdapter.notifyDataSetChanged();







    }

    private void initComponents(){
        this.tvname = findViewById(R.id.tv_profile_name);
        this.tvusername = findViewById(R.id.tv_profile_username);
        this.tvdescription = findViewById(R.id.tv_profile_description);
        this.btedit = findViewById(R.id.btn_profile_edit);
        this.pbProfile = findViewById(R.id.pb_profile);
        this.btedit = findViewById(R.id.btn_profile_edit);
        this.rvPosts = findViewById(R.id.rv_profile);

        this.btedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent i = new Intent (ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
                finish();
            }
        });
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




                String description = snapshot.child("description").getValue().toString();
                tvdescription.setText(description);

                if (description != "Description"){
                    getDescription().setVisibility(View.VISIBLE);
                }

                pbProfile.setVisibility(View.GONE);



                pbProfile.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private TextView getDescription() {
        return this.tvdescription;
    }
}