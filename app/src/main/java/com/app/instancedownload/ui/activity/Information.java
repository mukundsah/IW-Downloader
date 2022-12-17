package com.app.instancedownload.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.instancedownload.R;
import com.app.instancedownload.util.Method;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

public class Information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Method method = new Method(Information.this);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_information);
        toolbar.setTitle(getResources().getString(R.string.guidance));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MaterialTextView textViewInfoOne = findViewById(R.id.textView_two_information);
        MaterialTextView textViewInfoTwo = findViewById(R.id.textView_three_information);
        ImageView imageView = findViewById(R.id.imageView_information);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textViewInfoOne.setText(getResources().getString(R.string.intro_one_ten));
            textViewInfoTwo.setText(getResources().getString(R.string.intro_two_ten));
        } else {
            textViewInfoOne.setText(getResources().getString(R.string.intro_one));
            textViewInfoTwo.setText(getResources().getString(R.string.intro_two));
        }

        Glide.with(Information.this).load(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder).into(imageView);

        imageView.setOnClickListener(v -> method.alertBox(getResources().getString(R.string.video_message)));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
