package ph.com.filmeapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.Object;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class AddRecomActivity extends AppCompatActivity {


    private ImageButton ibAddPhoto;
    private EditText etDesc;
    private EditText etTitle;
    private Button btnAddPost;

    private RadioGroup rgRating;
    private RadioGroup rgGenre;

    private RadioButton rbRating;
    private RadioButton rbGenre;

    // firebase

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private String userId;
    private FirebaseUser user;


    public static String mCurrName;
    FirebaseStorage storage;
    Uri imageUri;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recom);

        // Initialize and Assign Variable

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setItemIconTintList(null);

        bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

        // Set Home Selected

        bottomNavigationView.setSelectedItemId(R.id.nb_add);

        // Perform ItemSelectedListener

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
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
                    case R.id.nb_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nb_add:
                        return true;
                }


                return false;
            }
        });



        initComponents();
        initFirebase();


        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // uploadImage();


                String title = etTitle.getText().toString();
                String description = etDesc.getText().toString();

                int radioIdGenre = rgGenre.getCheckedRadioButtonId();
                int radioIdRating = rgRating.getCheckedRadioButtonId();

                rbGenre = findViewById(radioIdGenre);
                rbRating = findViewById(radioIdRating);

                String genre = rbGenre.getText().toString();
                String rating = rbRating.getText().toString();


                String uid = getuid();

                checkEmpty(title,description,genre,rating);

                if(imageUri != null)
                {
                    StorageReference reference = storage.getReference().child("post_images" + UUID.randomUUID().toString());


                    reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {

                            if(task.isSuccessful()){


                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {


                                        Map<String, Object> map = new HashMap<>();
                                        map.put("image", uri.toString());
                                        map.put("uid", uid);
                                        map.put("name", mCurrName);
                                        map.put("title", title);
                                        map.put("description", description);
                                        map.put("genre", genre);
                                        map.put("rating", rating);

//
//                                        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//
//                                                String posts = snapshot.getKey();
//                                                String ref = FirebaseDatabase.getInstance().getReference() + posts;
//                                                Toast.makeText(getApplicationContext(), ref, Toast.LENGTH_LONG).show();
//                                                for(DataSnapshot ds: snapshot.getChildren())
//                                                {
//                                                    int index = (int) (ds.getChildrenCount()) + 1;
//                                                    Toast.makeText(getApplicationContext(), String.valueOf(ds.getChildrenCount()), Toast.LENGTH_LONG).show();
//
//                                                }
//
//
//
//
//
//                                            }
//
//                                            @Override
//                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                                            }
//                                        });

                                        String key = FirebaseDatabase.getInstance().getReference().child("posts").push().getKey();


                                        map.put("postID", key);

                                        Toast.makeText(getApplicationContext(), key, Toast.LENGTH_LONG).show();
//                                        FirebaseDatabase.getInstance().getReference().child("posts").push()
                                        FirebaseDatabase.getInstance().getReference().child("posts").child(key)
                                                .setValue(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                        Toast.makeText(getApplicationContext(), "Succesfully Added", Toast.LENGTH_LONG).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull @NotNull Exception e) {
                                                        Toast.makeText(getApplicationContext(), "Could not insert data", Toast.LENGTH_LONG).show();
                                                    }
                                                });



                                    }
                                });



                            } else{

                                //checkEmpty(title,description,genre,rating);
                                Toast.makeText(AddRecomActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });



                }


                Intent intent = new Intent(AddRecomActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }

        });


        ibAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGetContent.launch("image/*");
            }
        });





    }

    private boolean checkEmpty (String title, String description, String genre, String rating) {
        boolean isEmpty = false;
        if(title.isEmpty()) {
            this.etTitle.setError("Required");
            this.etTitle.requestFocus();
            isEmpty = true;
        } else if (description.isEmpty()) {
            this.etDesc.setError("Required");
            this.etDesc.requestFocus();
            isEmpty = true;
        }  else if (genre.isEmpty()) {
            this.rbGenre.setError("Required");
            this.rbGenre.requestFocus();
            isEmpty = true;
        } else if (rating.isEmpty()) {
            this.rbRating.setError("Required");
            this.rbRating.requestFocus();
            isEmpty = true;
        }

        return isEmpty;
    }
    @Override
    protected void onStart(){

        super.onStart();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(Collections.users.name());

        userRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                        mCurrName = snapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }




    private void uploadImage()
    {
        if(imageUri != null)
        {
            StorageReference reference = storage.getReference().child("images/*" + UUID.randomUUID().toString());


            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {

                    if(task.isSuccessful()){

                        Toast.makeText(AddRecomActivity.this, "Image Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                    } else{

                        Toast.makeText(AddRecomActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            });


        }

    }



    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null){

                        ibAddPhoto.setImageURI(result);
                        imageUri = result;
                    }
                }
            });







    private void initFirebase() {
        this.mAuth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
        this.user = this.mAuth.getCurrentUser();
        this.userId = this.user.getUid();

        storage = FirebaseStorage.getInstance();
    }

    private void initComponents(){

       ibAddPhoto = findViewById(R.id.ib_upload_ar);
       etDesc = findViewById(R.id.pt_desc_ar);
       etTitle = findViewById(R.id.pt_title_ar);
       btnAddPost = findViewById(R.id.btn_addpost_ar);

       rgGenre = findViewById(R.id.rg_genre);
       rgRating = findViewById(R.id.rg_rating);


    }



    private String getuid() {
        return this.user.getUid();
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