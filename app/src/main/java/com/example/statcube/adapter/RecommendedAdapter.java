package com.example.statcube.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statcube.CourseDetailActivity;
import com.example.statcube.R;
import com.example.statcube.model.Course;

import java.util.ArrayList;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    private Context ctx;
    private ArrayList<Course> courses;
    private ArrayList<String> authors;

    public RecommendedAdapter(Context ctx, ArrayList<Course> courses, ArrayList<String> authors) {
        this.ctx = ctx;
        this.courses = courses;
        this.authors = authors;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    @NonNull
    @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recommended_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(courses.get(position).getCourseTitle());
        holder.tvAuthor.setText(authors.get(position));

        if(position%3 == 0) holder.cvRecommended.setCardBackgroundColor(Color.parseColor("#27647B"));
        else if(position%3 == 1) holder.cvRecommended.setCardBackgroundColor(Color.parseColor("#849FAD"));
        else holder.cvRecommended.setCardBackgroundColor(Color.parseColor("#CA3542"));

        holder.cvRecommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, CourseDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("course", courses.get(position));
                intent.putExtras(bundle);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvAuthor;
        CardView cvRecommended;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            cvRecommended = itemView.findViewById(R.id.cv_recommended_item);
        }
    }
}
