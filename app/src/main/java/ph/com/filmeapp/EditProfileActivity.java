package ph.com.filmeapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {
    private EditText etname;
    private EditText etusername;
    private EditText etdescription;
    private EditText etpassword;
    private EditText etemail;
    private Button btupdate;
    private Button btcancel;
    private ImageButton ibAvatar;
    private ProgressBar pbUpdate;


    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private String userId;


    FirebaseStorage storage;
    Uri imageUri;



    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Collections.users.name());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        this.initComponents();
        this.initFirebase();
    }


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null){

                        ibAvatar.setImageURI(result);
                        imageUri = result;
                    }
                }
            });

    private void initComponents(){
        this.etname = findViewById(R.id.et_name);
        this.etusername = findViewById(R.id.et_username);
        this.etdescription = findViewById(R.id.et_description);
        this.etpassword = findViewById(R.id.et_password);
        this.btupdate = findViewById(R.id.bt_update);
        this.pbUpdate = findViewById(R.id.pb_update);
        this.btcancel = findViewById(R.id.bt_cancel);
        this.etemail = findViewById(R.id.et_email);
        this.ibAvatar = findViewById(R.id.et_avatar);

        this.initFirebase();




        btupdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v ) {
                String name = etname.getText().toString();
                String username = etusername.getText().toString();
                String description = etdescription.getText().toString();
                String password = etpassword.getText().toString();
                String email = etemail.getText().toString();



                if(imageUri != null)
                {
                    StorageReference storageReference = storage.getReference().child("user_images" + UUID.randomUUID().toString());

                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {

                            if(task.isSuccessful()){


                                storageReference .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {


                                        HashMap hashMap = new HashMap();
                                        hashMap.put("avatar", uri.toString());
                                        hashMap.put("name", name);
                                        hashMap.put("username", username);
                                        hashMap.put("description", description);
                                        hashMap.put("password", password);
                                        hashMap.put("email", email);


                                        etname.setText(name);


                                        reference.child(getuid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                            @Override
                                            public void onSuccess(Object o) {
                                                pbUpdate.setVisibility(View.GONE);
                                            }
                                        });


                                    }
                                });



                            } else{

                                Toast.makeText(EditProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });


                }


                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();

            }
        });




        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });


        ibAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

    }

    private void initFirebase(){
        this.mAuth = FirebaseAuth.getInstance();
        this.user = this.mAuth.getCurrentUser();
        this.userId = this.user.getUid();
        storage = FirebaseStorage.getInstance();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Collections.users.name());

        this.pbUpdate.setVisibility(View.VISIBLE);
        reference.child(this.userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String avatar = snapshot.child("avatar").getValue().toString();
                Picasso.get().load(avatar).into(ibAvatar);
                String name = snapshot.child("name").getValue().toString();
                etname.setText(name);
                String username = snapshot.child("username").getValue().toString();
                etusername.setText(username);
                String description = snapshot.child("description").getValue().toString();
                etdescription.setText(description);
                String password = snapshot.child("password").getValue().toString();
                etpassword.setText(password);
                String email = snapshot.child("email").getValue().toString();
                etemail.setText(email);


                pbUpdate.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private String getuid() {
        return this.user.getUid();
    }



}