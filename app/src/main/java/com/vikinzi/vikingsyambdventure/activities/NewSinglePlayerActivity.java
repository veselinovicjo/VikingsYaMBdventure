package com.vikinzi.vikingsyambdventure.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vikinzi.vikingsyambdventure.R;

import static android.view.View.GONE;

public class NewSinglePlayerActivity extends AppCompatActivity
{
    ConstraintLayout viewHelp;
    TextView close;
    TextView eraser, coins, help1, help2, help3;
    TextView txt_single;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_single_player);
        Typeface enchantedLandFont = Typeface.createFromAsset(getAssets(), "fonts/enchanted_land.ttf");

        eraser = (TextView) findViewById(R.id.txt_eraser);
        eraser.setTypeface(enchantedLandFont);
        coins = (TextView) findViewById(R.id.txt_coins);
        coins.setTypeface(enchantedLandFont);
        help1 = (TextView) findViewById(R.id.txt_help1);
        help1.setTypeface(enchantedLandFont);
        help2 = (TextView) findViewById(R.id.txt_help2);
        help2.setTypeface(enchantedLandFont);
        help3 = (TextView) findViewById(R.id.txt_help3);
        help3.setTypeface(enchantedLandFont);

        txt_single  = (TextView) findViewById(R.id.txt_single);
        txt_single.setTypeface(enchantedLandFont);

//        //Card view verzija - opens only in first entering the game
        SharedPreferences sharedPreferences = this.getSharedPreferences("APP_DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        viewHelp = (ConstraintLayout)findViewById(R.id.viewHelp);

        if(!sharedPreferences.getBoolean("FirstTime", false))
        {
            viewHelp.setVisibility(View.VISIBLE);
            editor.putBoolean("FirstTime", true);
        }
        editor.apply();

        TextView close = (TextView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                viewHelp.setVisibility(GONE);
            }
        });


        TextView miniSingle = (TextView) findViewById(R.id.miniyamb);
        miniSingle.setTypeface(enchantedLandFont);
        miniSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMini = new Intent(NewSinglePlayerActivity.this, MiniSingle.class);
                startActivity(goToMini);
            }
        });

        TextView maxiSingle = (TextView) findViewById(R.id.maxiyamb);
        maxiSingle.setTypeface(enchantedLandFont);
        maxiSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMaxi = new Intent(NewSinglePlayerActivity.this, MaxiSingle.class);
                startActivity(goToMaxi);
            }
        });

    }

}
