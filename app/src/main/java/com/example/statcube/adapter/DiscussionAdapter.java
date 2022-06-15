package com.example.statcube.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statcube.DiscussionDetailActivity;
import com.example.statcube.R;
import com.example.statcube.model.Discussion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Discussion> discussions;
    private ArrayList<String> usersName;

    public DiscussionAdapter(Context context) { this.context = context; }

    public void setDiscussions(ArrayList<Discussion> discussions) { this.discussions = discussions; }

    public void setUsersName(ArrayList<String> usersName) { this.usersName = usersName; }

    @NonNull
    @Override
    public DiscussionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.discussions_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscussionAdapter.ViewHolder holder, int position) {
        holder.tvDiscussionTitle.setText(discussions.get(position).getDiscussionTitle());
        holder.tvDiscussionUsername.setText(usersName.get(position));

        SimpleDateFormat formatOutput = new SimpleDateFormat("dd MMM yyyy");
        holder.tvDiscussionDate.setText(formatOutput.format(discussions.get(position).getDiscussionDate()));

        holder.cvDiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DiscussionDetailActivity.class);
                intent.putExtra("DiscussionID", discussions.get(position).getDiscussionID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() { return discussions.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDiscussionTitle, tvDiscussionUsername, tvDiscussionDate;
        CardView cvDiscussion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDiscussionTitle = itemView.findViewById(R.id.tv_discussion_title);
            tvDiscussionUsername = itemView.findViewById(R.id.tv_discussion_username);
            tvDiscussionDate = itemView.findViewById(R.id.tv_discussion_date);
            cvDiscussion = itemView.findViewById(R.id.cv_discussion);
        }
    }
}
