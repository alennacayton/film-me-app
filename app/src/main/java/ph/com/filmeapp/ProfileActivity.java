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
import android.widget.ImageView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView ivpic;
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




    DatabaseReference database;


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
                    case R.id.nb_bookmarks:
                        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nb_logout:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }


                return false;
            }
        });

        this.initComponents();
        this.initFirebase();



        database = FirebaseDatabase.getInstance().getReference("posts");


        Query query = database.orderByChild("uid").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Post post = dataSnapshot.getValue(Post.class);
                    postArrayList.add(post);
                }

                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });








        this.postLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false );
        this.rvPosts.setLayoutManager(postLayoutManager);

        this.postAdapter = new PostAdapter(postArrayList, ProfileActivity.this);
        this.rvPosts.setAdapter(postAdapter);
        this.postAdapter.notifyDataSetChanged();







    }

    private void initComponents(){
        this.ivpic = findViewById(R.id.iv_profile_picture);
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
                //Picasso.get().load(currentItem.getImage()).into(ivpic);
                String name = snapshot.child("name").getValue().toString();
                tvname.setText(name);
                String username = snapshot.child("username").getValue().toString();
                tvusername.setText(username);


                String avatar = snapshot.child("avatar").getValue().toString();
                Picasso.get().load(avatar).into(ivpic);

                String description = snapshot.child("description").getValue().toString();
                tvdescription.setText(description);

                if (description != "Description"){
                    getDescription().setVisibility(View.VISIBLE);
                }

                //if (avatar != )

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