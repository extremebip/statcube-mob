package com.example.statcube.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statcube.model.Course;
import com.example.statcube.R;

import java.util.ArrayList;

public class AllCoursesAdapter extends RecyclerView.Adapter<AllCoursesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Course> courses;

    public AllCoursesAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public AllCoursesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_courses_item, parent, false);
        return new AllCoursesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCoursesAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(courses.get(position).getCourseTitle());
        holder.tvDescription.setText(courses.get(0).getCourseDescription());

        holder.cvAllCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kalo coursenya di klik
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvDescription;
        CardView cvAllCourses;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            cvAllCourses = itemView.findViewById(R.id.cv_all_courses);
        }
    }
}
