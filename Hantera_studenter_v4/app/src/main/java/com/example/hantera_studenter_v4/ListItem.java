package com.example.hantera_studenter_v4;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItem {
    private ImageView image;
    private TextView text;
    private Button button;

    public ListItem(ImageView iv, TextView tv, Button b){
        image = iv;
        text = tv;
        button = b;
    }

    public ImageView getImage(){
        return image;
    }

    public TextView getText(){
        return text;
    }

    public Button getButton(){
        return button;
    }
}
