package ph.com.filmeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public Context cxt;
    private ArrayList<Post> postArrayList;

    private FirebaseAuth mAuth;
    private String userId;
    private FirebaseUser user;
    String mCurrName, profileImageUrlV;


    public PostAdapter(ArrayList<Post> postArrayList ,Context mContext){
        this.cxt = mContext;
        this.postArrayList = postArrayList;


    }


    @NonNull
    @NotNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);


    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull PostViewHolder holder, int position) {


        Post currentItem = postArrayList.get(position);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(cxt, LinearLayoutManager.VERTICAL, false);
        holder.commentRecyclerView.setLayoutManager(layoutManager);


        Picasso.get().load(currentItem.getImage()).into(holder.ivPoster);
        holder.tvDesc.setText(currentItem.getDescription());
        holder.tvName.setText("by " + currentItem.getName());
        holder.tvTitle.setText(currentItem.getTitle());
        holder.tvRating.setText("Rating : " + currentItem.getRating());

       // Toast.makeText(cxt, currentItem.getTitle(), Toast.LENGTH_LONG).show();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(Collections.users.name());

        userRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    mCurrName = snapshot.child("name").getValue().toString();
                    profileImageUrlV = snapshot.child("avatar").getValue().toString();

                    // Toast.makeText(cxt, mCurrName, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        holder.ivSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment = holder.etComment.getText().toString();
                String postKey = currentItem.getPostId().toString();


              //  Toast.makeText(cxt, currentItem.getTitle(), Toast.LENGTH_SHORT).show();

                if (comment.isEmpty())
                {
                    Toast.makeText(cxt, "Please enter a comment!", Toast.LENGTH_SHORT).show();
                }
                else{


                    HashMap hm = new HashMap();
                    hm.put("comment", comment);
                    hm.put("postId", postKey);
                    hm.put("name", mCurrName);
                    hm.put("avatarId",profileImageUrlV);

                   // Toast.makeText(cxt, mCurrName, Toast.LENGTH_LONG).show();



                    FirebaseDatabase.getInstance().getReference().child("comments").push()
                            .setValue(hm)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(cxt, "Succesfully Added Comment", Toast.LENGTH_LONG).show();
                                    notifyDataSetChanged();
                                    holder.etComment.setText(null);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(cxt, "Could not insert comment", Toast.LENGTH_LONG).show();
                                }
                            });



                  //  addComment(holder, postKey, commentRef, user.getUid(), comment);
                }
            }
        });


        ArrayList<Comment> arrayList = new ArrayList<>();





        CommentAdapter commentAdapter = new CommentAdapter(arrayList,holder.commentRecyclerView.getContext());
        holder.commentRecyclerView.setAdapter(commentAdapter);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("comments");


        Query query = database.orderByChild("postId").equalTo(currentItem.getPostId().toString());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Comment comment = dataSnapshot.getValue(Comment.class);
                    arrayList.add(comment);
                }

                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




        //  commentAdapter.notifyDataSetChanged();

    }



    @Override
    public int getItemCount() {
        return postArrayList.size();
    }




    // Post ViewHolder
    public static class PostViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivPoster;
        public TextView tvTitle;
        public TextView tvDesc;
        public TextView tvRating;
        public TextView tvName;
        public RecyclerView commentRecyclerView;
        private ImageView ivSendComment;
        private EditText etComment;


        public PostViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            this.ivPoster = itemView.findViewById(R.id.iv_img_mr);
            this.tvTitle = itemView.findViewById(R.id.tv_title_mr);
            this.tvDesc = itemView.findViewById(R.id.tv_desc_mr);
            this.tvRating = itemView.findViewById(R.id.tv_rating_mr);
            this.tvName = itemView.findViewById(R.id.tv_name_mr);
            this.commentRecyclerView = itemView.findViewById(R.id.rv_feedbacks_mr);

            this.ivSendComment = itemView.findViewById(R.id.iv_send_comment);
            this.etComment = itemView.findViewById(R.id.pv_comment_mr);


        }
    }






    private String getuid() {
        return this.user.getUid();
    }






}
