package com.example.hantera_studenter_v4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends ArrayAdapter<Student> {

    private int layout;
    private List<Student> students;
    private Context context;

    public StudentListAdapter(Context context, int layout, List<Student> students) {
        super(context, layout, students);

        this.layout = layout;
        this.students = students;
        this.context = context;
    }

    public void update(ArrayList<Student> students){
        this.students = students;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(layout, parent, false);

            ImageView image = view.findViewById(R.id.list_item_image);
            TextView text = view.findViewById(R.id.list_item_text);
            Button button = view.findViewById(R.id.list_item_button);

            ListItem item = new ListItem(image, text, button);
            set(item, position);
            view.setTag(item);
        }else{
            ListItem main = (ListItem) view.getTag();
            set(main, position);
        }


        return view;
    }

    public void set(ListItem li, int position){
        Student student = students.get(position);

        li.getText().setText(student.getName());

        li.getText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudentView.class);

                int studentId = students.get(position).getId();

                intent.putExtra("id" , studentId);
                context.startActivity(intent);
            }
        });

        li.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.instance.remove(student.getId());
                update(Database.instance.getStudents());
            }
        });
    }
}
