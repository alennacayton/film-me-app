package ph.com.filmeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    //Context context;

    //ArrayList<>
    public Context cxt;
    private ArrayList<Post> postArrayList;






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
        holder.commentRecyclerView.setHasFixedSize(true);


        holder.tvGenre.setText(currentItem.getGenre());
        holder.ivPoster.setImageResource(currentItem.getImageId());
        holder.tvDesc.setText(currentItem.getDesc());
        holder.tvName.setText(currentItem.getName());
        holder.tvTitle.setText(currentItem.getTitle());
        holder.tvRating.setText(currentItem.getRating());

        ArrayList<Comment> arrayList = new ArrayList<>();

        if(postArrayList.get(position).getGenre().equals("romance")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Alenna", "Noice movie" ));
        }


        if(postArrayList.get(position).getGenre().equals("action")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Cheska", "WAOO" ));
        }

        if(postArrayList.get(position).getGenre().equals("comedy")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Candy", "gud" ));
        }

        if(postArrayList.get(position).getGenre().equals("horror")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Alenna Cayton", "Noice movie" ));
        }

        if(postArrayList.get(position).getGenre().equals("drama")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Alenna Cayton", "Noice movie" ));
        }
        if(postArrayList.get(position).getGenre().equals("drama")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Kim Taehyung", "So so good! I was so stunned mygad! The animation is so powerful!" ));
        }
        if(postArrayList.get(position).getGenre().equals("drama")){

            arrayList.add(new Comment(R.drawable.ic_icon, "Park Jimin", "Umiyak ako ng sobra. Ginusto ko na bigla magkajowa kasi naghost ako ng bestfriend ko na crush huhuhuhh! relate!" ));
        }


        CommentAdapter commentAdapter = new CommentAdapter(arrayList,holder.commentRecyclerView.getContext());
        holder.commentRecyclerView.setAdapter(commentAdapter);
        //holder.commentRecyclerView.setLayoutFrozen(true);
    }


    @Override
    public int getItemCount() {
        return postArrayList.size();
    }




    private int imageId;
    private String title;
    private String desc;
    private String rating;
    private String name;
    private String genre;



    // Post ViewHolder
    public static class PostViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivPoster;
        public TextView tvTitle;
        public TextView tvDesc;
        public TextView tvRating;
        public TextView tvName;
        public TextView tvGenre;
        public RecyclerView commentRecyclerView;



        public PostViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            this.ivPoster = itemView.findViewById(R.id.iv_img_mr);
            this.tvTitle = itemView.findViewById(R.id.tv_title_mr);
            this.tvDesc = itemView.findViewById(R.id.tv_desc_mr);
            this.tvRating = itemView.findViewById(R.id.tv_rating_mr);
            this.tvName = itemView.findViewById(R.id.tv_name_mr);
            this.tvGenre = itemView.findViewById(R.id.tv_genre_mr);
            this.commentRecyclerView = itemView.findViewById(R.id.rv_feedbacks_mr);


        }
    }


}
