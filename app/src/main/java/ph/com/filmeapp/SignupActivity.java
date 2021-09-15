package ph.com.filmeapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SignupActivity extends AppCompatActivity {

    private TextView tvLogin;
    private EditText etName;
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etDescription;
    private Button btnSignup;
    private ProgressBar pbSignup;
    Uri uri;
    FirebaseStorage storage;
    String imageAvatar;


    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    // private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.initFirebase();
        this.initComponents();


    }

    private void initFirebase() {
        this.mAuth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();

        storage = FirebaseStorage.getInstance();
    }

    private void initComponents() {
        this.tvLogin = findViewById(R.id.tv_signup_login);
        this.etName = findViewById(R.id.pt_name_su);
        this.etUsername = findViewById(R.id.pt_us_su);
        this.etEmail = findViewById(R.id.pt_email);
        this.etPassword = findViewById(R.id.pt_pw_su);
        this.btnSignup = findViewById(R.id.btn_signup_su);
        this.pbSignup = findViewById(R.id.pb_signup);
        this.etDescription = findViewById(R.id.et_su_description);


        imageAvatar = "https://firebasestorage.googleapis.com/v0/b/feed-me-app-6d735.appspot.com/o/woman-avatar-profile-vector-21372067.jpg?alt=media&token=486395a7-6de1-4d4a-808e-a270c9da7a36";


        this.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent i = new Intent (SignupActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        this.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {


                String email = etEmail.getText().toString().trim();
                String password =  etPassword.getText().toString().trim();
                String name =  etName.getText().toString().trim();
                String username =  etUsername.getText().toString().trim();
                String description =  etDescription.getText().toString().trim();
                String avatar = imageAvatar;





                //initFirebase();
                if(!checkEmpty(email, password, name,username, imageAvatar)) {
                    // do something
                    //add user to db

                    User user = new User(email, password, name, username, description, imageAvatar);
                    storeUser(user);
                }
            }
        });
    }
/*
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null){

                        ivAvatar.setImageURI(result);
                        uri = result;
                    }
                }
            });

*/


    private boolean checkEmpty (String email, String password, String name, String username, String avatar) {
        boolean isEmpty = false;
        if(email.isEmpty()) {
            this.etEmail.setError("Required");
            this.etEmail.requestFocus();
            isEmpty = true;
        } else if (password.isEmpty()) {
            this.etPassword.setError("Required");
            this.etPassword.requestFocus();
            isEmpty = true;
        }  else if (name.isEmpty()) {
            this.etName.setError("Required");
            this.etName.requestFocus();
            isEmpty = true;
        } else if (username.isEmpty()) {
            this.etUsername.setError("Required");
            this.etUsername.requestFocus();
            isEmpty = true;
        }

        /*else if (avatar == null) {
            Toast.makeText(SignupActivity.this, "Please Upload Avatar", Toast.LENGTH_SHORT).show();
            this.ivAvatar.requestFocus();
            isEmpty = true;
        }
*/
        return isEmpty;
    }

    public void storeUser (User user) {
        this.pbSignup.setVisibility(View.VISIBLE);
        this.mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            database.getReference(Collections.users.name()).child(mAuth.getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void> () {

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                signupSuccessful();
                                            } else {
                                                signupUnsuccessful();
                                            }
                                        }
                                    });
                        } else {
                            signupUnsuccessful();
                        }
                    }
                });
    }

    private void signupUnsuccessful () {
        this.pbSignup.setVisibility(View.GONE);
        Toast.makeText(this, "Signup Unsuccessful", Toast.LENGTH_SHORT).show();
    }

    private void signupSuccessful() {
        this.pbSignup.setVisibility(View.GONE);
        Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(SignupActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }


}