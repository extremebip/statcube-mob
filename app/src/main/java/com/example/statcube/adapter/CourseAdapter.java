package com.example.statcube.adapter;

import android.content.Context;
import android.content.Intent;
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

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Course> courses;

    public CourseAdapter(Context context) { this.context = context; }
    public void setCourses(ArrayList<Course> courses) { this.courses = courses; }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        holder.tvCourseTitle.setText(courses.get(position).getCourseTitle());
        holder.tvCourseDescription.setText(courses.get(position).getCourseDescription());

        holder.cvCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("course", courses.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return courses.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCourseTitle, tvCourseDescription;
        CardView cvCourse;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseTitle = itemView.findViewById(R.id.tv_course_title);
            tvCourseDescription = itemView.findViewById(R.id.tv_course_description);
            cvCourse = itemView.findViewById(R.id.cv_course);
        }
    }
}
