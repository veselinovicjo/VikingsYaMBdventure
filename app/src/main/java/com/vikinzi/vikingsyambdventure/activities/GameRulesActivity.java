package com.vikinzi.vikingsyambdventure.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Typeface;
import android.widget.TextView;

import com.vikinzi.vikingsyambdventure.R;


public class GameRulesActivity extends AppCompatActivity {

    TextView gamerules, gamerules1, gamerules2, gamerules3, gamerules4,gamerules5, gamerules6, gamerules7, gamerules8, gamerules9;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_rules);
        gamerules = (TextView)findViewById(R.id.gamerules);
        gamerules1 = (TextView)findViewById(R.id.gamerules1);
        gamerules2 = (TextView)findViewById(R.id.gamerules2);
        gamerules3 = (TextView)findViewById(R.id.gamerules3);
        gamerules4 = (TextView)findViewById(R.id.gamerules4);
        gamerules5 = (TextView)findViewById(R.id.gamerules5);
        gamerules6 = (TextView)findViewById(R.id.gamerules6);
        gamerules7 = (TextView)findViewById(R.id.gamerules7);
        gamerules8 = (TextView)findViewById(R.id.gamerules8);
        gamerules9 = (TextView)findViewById(R.id.gamerules9);


        Typeface vikingsFont= Typeface.createFromAsset(getAssets(), "fonts/enchanted_land.ttf");
        gamerules.setTypeface(vikingsFont);
        gamerules1.setTypeface(vikingsFont);
        gamerules2.setTypeface(vikingsFont);
        gamerules3.setTypeface(vikingsFont);
        gamerules4.setTypeface(vikingsFont);
        gamerules5.setTypeface(vikingsFont);
        gamerules6.setTypeface(vikingsFont);
        gamerules7.setTypeface(vikingsFont);
        gamerules8.setTypeface(vikingsFont);
        gamerules9.setTypeface(vikingsFont);

    }
}
