package ph.com.filmeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {


    Context cxt;
    //ArrayList<>
    public ArrayList<Comment> commentArrayList;



    public CommentAdapter(ArrayList<Comment> commentArrayList, Context mContext){
        this.cxt = mContext;
        this.commentArrayList = commentArrayList;
    }



    @NonNull
    @NotNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Inflating item_feedback.xml
/*
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_feedback, parent, false);

        CommentViewHolder commentViewHolder = new CommentViewHolder(view);

        // return custom ViewHolder
        return commentViewHolder;

        */

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new CommentViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentViewHolder holder, int position) {

        Comment currentItem = commentArrayList.get(position);

        holder.ivAvatar.setImageResource(currentItem.getAvatarId());
        holder.tvName.setText(currentItem.getName());
        holder.tvDesc.setText(currentItem.getDesc());

    }

    @Override
    public int getItemCount() {
        return this.commentArrayList.size();
    }



    // Comment ViewHolder
    public static class CommentViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivAvatar;
        public TextView tvName;
        public TextView tvDesc;


        public CommentViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            this.ivAvatar = itemView.findViewById(R.id.iv_avatar_fb);
            this.tvName = itemView.findViewById(R.id.tv_name_fb);
            this.tvDesc = itemView.findViewById(R.id.tv_comment_fb);


        }


    }




}
