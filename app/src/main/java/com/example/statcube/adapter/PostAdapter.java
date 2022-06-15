package com.example.statcube.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statcube.R;
import com.example.statcube.model.Post;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Post> posts;

    public PostAdapter(Context context) { this.context = context; }

    public void setPosts(ArrayList<Post> posts) { this.posts = posts; }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.tvPostUsername.setText(post.getAuthor());
        holder.tvPostDate.setText(post.getPostDateFormatted());

        String postContent = post.getPostContent();
        if (post.isDeleted()) {
            postContent = "This post has been deleted by the admin";
            holder.tvPostContent.setTypeface(null, Typeface.ITALIC);
        }
        holder.tvPostContent.setText(postContent);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPostUsername, tvPostDate, tvPostContent;
        CardView cvPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvPost = itemView.findViewById(R.id.cv_post);
            tvPostUsername = itemView.findViewById(R.id.tv_post_username);
            tvPostDate = itemView.findViewById(R.id.tv_post_date);
            tvPostContent = itemView.findViewById(R.id.tv_post_content);
        }
    }
}
