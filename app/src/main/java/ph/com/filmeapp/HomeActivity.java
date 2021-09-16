package ph.com.filmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {


    private CardView cvComedy;
    private CardView cvDrama;
    private CardView cvRomance;
    private CardView cvHorror;
    private CardView cvAction;
    private CardView cvScifi;


    public ImageView ivPoster;
    public TextView tvTitle;
    public TextView tvDesc;
    public TextView tvRating;
    public TextView tvName;

    ArrayList<Post> postArrayList;
    DatabaseReference databaseReference;



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
                        FirebaseAuth.getInstance().signOut();
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

/*

        mAdUserDatabse = FirebaseDatabase.getInstance().getReference().child("posts");
        mAdUserDatabse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int ads = (int) dataSnapshot.getChildrenCount();
                int rand = new Random().nextInt(ads);
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    for (DataSnapshot datas : data.getChildren()) {
                        for (int i = 0; i < rand; i++) {

                            postArrayList = new ArrayList<Post>();

                            Post value = datas.getValue(Post.class);
                            Post temp = new Post();

                            String title = value.getTitle();
                            String description = value.getDescription();
                            String genre = value.getGenre();
                            String image = value.getImage();
                            String name = value.getName();
                            String rating = value.getRating();
                            String uid = value.getUid();
                            String postId = value.getPostId();



                            temp.setTitle(title);
                            temp.setDescription(description);
                            temp.setGenre(genre);
                            temp.setImage(image);
                            temp.setName(name);
                            temp.setRating(rating);
                            temp.setUid(uid);
                            temp.setPostId(postId);


                            postArrayList.add(temp);



                            tvTitle.setText(title);
                            tvDesc.setText(description);
                            tvRating.setText(rating);
                            tvName.setText(name);
                            Picasso.get().load(image).into(ivPoster);

                            // fire.setType(type);
                            //  fire.setCategories(categories);
                            //  fire.setCity(city);
                            //   postArrayList.add(fire);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }


        });

*/
     /*   DatabaseReference database = FirebaseDatabase.getInstance().getReference("posts");


        FirebaseDatabase mthemetuneref = FirebaseDatabase.getInstance();
        DatabaseReference ref = mthemetuneref.getReference().child("posts");
*/
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("posts");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long childrenCount = dataSnapshot.getChildrenCount();
                int count = (int) childrenCount;
                int randomNumber = new Random().nextInt(count);

                int i=0;
                String themeTune; //Your random themeTune will be stored here
                Post randomPost = new Post();

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    if(i == randomNumber) {
                        randomPost = snap.getValue(Post.class);
                        break;
                    }
                    i++;
                }

                String title = randomPost.getTitle();
                String description = randomPost.getDescription();
                String genre = randomPost.getGenre();
                String image = randomPost.getImage();
                String name = randomPost.getName();
                String rating = randomPost.getRating();
                String uid = randomPost.getUid();
                String postId = randomPost.getPostId();

                tvTitle.setText(title);
                tvDesc.setText(description);
                tvRating.setText("Rating : " + rating);
                tvName.setText("by " + name);
                Picasso.get().load(image).into(ivPoster);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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


        this.ivPoster = findViewById(R.id.iv_img_hp);
        this.tvTitle = findViewById(R.id.tv_title_hp);
        this.tvDesc = findViewById(R.id.tv_desc_hp);
        this.tvRating = findViewById(R.id.tv_rating_hp);
        this.tvName = findViewById(R.id.tv_name_hp);


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