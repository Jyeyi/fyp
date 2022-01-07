package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fyp.databinding.ToolbarViewBinding;

public class user_add_ticket extends AppCompatActivity {

    private ToolbarViewBinding toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_ticket);

        setToolbar();
    }

    private void setToolbar() {
        toolbar.tvTitle.setText("Add Ticket");
        toolbar.ivRight.setImageResource(R.drawable.ic_trash);
//        toolbar.rlRight.setVisibility(View.VISIBLE);
    }
}