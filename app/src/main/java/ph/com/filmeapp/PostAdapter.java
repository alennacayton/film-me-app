package ph.com.filmeapp;

import android.content.Context;
import android.net.Uri;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    //Context context;

    //ArrayList<>
    public Context cxt;
    private ArrayList<Post> postArrayList;



    DatabaseReference commentRef;

    private FirebaseAuth mAuth;
    private String userId;
    private FirebaseUser user;





    public PostAdapter(ArrayList<Post> postArrayList ,Context mContext){
        this.cxt = mContext;
        this.postArrayList = postArrayList;
    }




    @NonNull
    @NotNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Inflating item_post.xml
/*
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_post, parent, false);

        PostAdapter.PostViewHolder postViewHolder = new PostAdapter.PostViewHolder(view);

        // return custom ViewHolder
        return postViewHolder;
        */

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PostViewHolder holder, int position) {


        Post currentItem = postArrayList.get(position);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(cxt, LinearLayoutManager.VERTICAL, false);
        holder.commentRecyclerView.setLayoutManager(layoutManager);
    //    holder.commentRecyclerView.setHasFixedSize(true);


       // holder.tvGenre.setText(currentItem.getGenre());


        Picasso.get().load(currentItem.getImage()).into(holder.ivPoster);
        holder.tvDesc.setText(currentItem.getDescription());
        holder.tvName.setText(currentItem.getName());
        holder.tvTitle.setText(currentItem.getTitle());
        holder.tvRating.setText(currentItem.getRating());




        holder.ivSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment = holder.etComment.getText().toString();
                String uid = getuid();
                //get user id avatar

                
            }
        });




        ArrayList<Comment> arrayList = new ArrayList<>();



        if(postArrayList.get(position).getTitle().equals("Kimi No Nawa")){

            arrayList.add(new Comment(R.drawable.user, "Bob Mondello", "It's both gorgeous enough and emotionally engaging enough to be worth seeking out." ));
        }


        if(postArrayList.get(position).getTitle().equals("Kimi No Nawa")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Alix Turner", "Japanese animated fantasy; humorous, romantic, captivating and utterly beautiful. " ));
        }


        if(postArrayList.get(position).getTitle().equals("5 Centimeters per Second")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Ronnie Scheib", "Just a perfect piece of work" ));
        }


        if(postArrayList.get(position).getTitle().equals("Whisper of the Heart")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Charles Solomon", "If there's a Ghibli film that is under-appreciated, it's this one." ));
        }

        if(postArrayList.get(position).getTitle().equals("Whisper of the Heart")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Felix Vasquez Jr.", "Will translate more towards girls than boys in the long run… " ));
        }

        if(postArrayList.get(position).getTitle().equals("Whisper of the Heart")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Jeffrey M. Anderson", "Enchanting." ));
        }

        if(postArrayList.get(position).getTitle().equals("Guardians of the Galaxy")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Lisa Wright", "The big screen version of this lesser-known Marvel comic is a hilarious curveball." ));
        }


        if(postArrayList.get(position).getTitle().equals("E.T.")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Danielle Solzman", "E.T. the Extra-Terrestrial is one of the greatest films ever made." ));
        }

        if(postArrayList.get(position).getTitle().equals("E.T.")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Mike Massie", "A family-friendly, heartwarming science-fiction adventure, full of iconic sequences." ));
        }







        if(postArrayList.get(position).getTitle().equals("Star Wars")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Evan Williams", "The special effects, of course, are incomparably good and the digital photography achieves a dazzling clarity." ));
        }

        if(postArrayList.get(position).getTitle().equals("Star Wars")){

            arrayList.add(new Comment(R.drawable.ic_icon, "David Stratton", "I think it is visually exciting. I think there are wonderful things in it but I just missed that innocence of the early films." ));
        }







        CommentAdapter commentAdapter = new CommentAdapter(arrayList,holder.commentRecyclerView.getContext());
        holder.commentRecyclerView.setAdapter(commentAdapter);
    }


    /*

    private void addComment(PostViewHolder holder, String postKey, DatabaseReference commentRef, String uid, String comment)
    {
        HashMap hm = new HashMap();
        hm.put("name", )

    }


    */

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



    private void initFirebase() {
        this.mAuth = FirebaseAuth.getInstance();
        this.user = this.mAuth.getCurrentUser();

        this.commentRef = FirebaseDatabase.getInstance().getReference("comments");
        this.userId = this.user.getUid();
    }



    private String getuid() {
        return this.user.getUid();
    }






}
