package com.example.statcube.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statcube.CourseDetailActivity;
import com.example.statcube.R;
import com.example.statcube.SubscribeActivity;
import com.example.statcube.TopicDetailActivity;
import com.example.statcube.model.Topic;
import com.example.statcube.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Topic> topics;
    private User user = null;

    public TopicAdapter(Context context) { this.context = context; }

    public void setTopics(ArrayList<Topic> topics) { this.topics = topics; }

    public void setUser(User user) { this.user = user; }

    @NonNull
    @Override
    public TopicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topic_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicAdapter.ViewHolder holder, int position) {
        holder.tvTopicTitle.setText(topics.get(position).getTopicTitle());
        holder.tvTopicContent.setText(topics.get(position).getTopicContent());
        Picasso.with(context).load(topics.get(position).getTopicThumbnail()).into(holder.ivTopicThumbnail);
        holder.cvTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null){
                    Intent intent = new Intent(context, SubscribeActivity.class);
                    context.startActivity(intent);
                }
                else{
                    Intent intent = new Intent(context, TopicDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("topic", topics.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() { return topics.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopicTitle, tvTopicContent;
        ImageView ivTopicThumbnail;
        CardView cvTopic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTopicTitle = itemView.findViewById(R.id.tv_topic_title);
            tvTopicContent = itemView.findViewById(R.id.tv_topic_content);
            ivTopicThumbnail = itemView.findViewById(R.id.iv_topic_thumbnail);
            cvTopic = itemView.findViewById(R.id.cv_topic);
        }
    }
}
