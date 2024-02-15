package com.example.espark_1;

import static com.example.espark_1.SECTION_P.selected_slot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;

public class NewsActivity extends AppCompatActivity {
    LinearLayout parentLayout;
    String jsonString;
    AppCompatImageView exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        parentLayout = findViewById(R.id.parentlayout);

        Intent intent = getIntent();

        // Retrieve extras from the intent
        if (intent != null) {
            jsonString = intent.getStringExtra("news");
        }

        exit = findViewById(R.id.image_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

        Gson gson = new Gson();

        JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);

        String[] urls = new String[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            urls[i] = jsonObject.get("url").getAsString();
        }

        for (String url : urls) {
            LinearLayout roundedContainer = createRoundedContainer(url);
            // Add margin to the dynamically created container
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.container_height)
            );
            layoutParams.setMargins(40, 40, 40, 60);
            roundedContainer.setLayoutParams(layoutParams);
            parentLayout.addView(roundedContainer);
        }
    }
    private LinearLayout createRoundedContainer(String text) {
        LinearLayout roundedContainer = new LinearLayout(this);
        roundedContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.container_height)
        ));
        roundedContainer.setOrientation(LinearLayout.VERTICAL);
        roundedContainer.setBackgroundResource(R.drawable.container);
        roundedContainer.setElevation(getResources().getDimension(R.dimen.elevation_value));


        LinearLayout linearLayout1 = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.setMargins(0, 10, 0, 0);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        linearLayout1.setLayoutParams(layoutParams);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);


        TextView txt_name = new TextView(this);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams1.setMargins(20, 0, 0, 0);
        layoutParams1.gravity = Gravity.CENTER_VERTICAL;
        txt_name.setText(text);
        txt_name.setTextColor(Color.parseColor("#000000"));
        txt_name.setTypeface(null, Typeface.BOLD);
        txt_name.setLayoutParams(layoutParams1);
        txt_name.setTextSize(18);

        Linkify.addLinks(txt_name, Linkify.WEB_URLS);

        linearLayout1.addView(txt_name);

        roundedContainer.addView(linearLayout1);

        return roundedContainer;
    }
    //disable back button
    @Override
    public void onBackPressed(){}
}