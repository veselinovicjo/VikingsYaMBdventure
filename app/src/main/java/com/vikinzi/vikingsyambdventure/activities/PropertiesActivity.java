package com.vikinzi.vikingsyambdventure.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vikinzi.vikingsyambdventure.GlideApp;
import com.vikinzi.vikingsyambdventure.R;


public class PropertiesActivity extends AppCompatActivity implements
        View.OnClickListener
{

    ImageView sound;
    private MediaPlayer mp;
    private ImageButton account;
    boolean soundON = true;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);

        sharedPreferences = getApplicationContext().getSharedPreferences("Sound", MODE_PRIVATE);
        initLayout();
        initListeners();
        loadSavedPreferences();
    }

    private void initLayout()
    {
        sound = (ImageView) findViewById(R.id.sound) ;
        account = (ImageButton) findViewById(R.id.account);

    }

    private void initListeners()
    {
        sound.setOnClickListener(this);
        account.setOnClickListener(this);
    }

    private void loadSavedPreferences()
    {
        soundON = sharedPreferences.getBoolean("soundON", true);
        if(soundON)
        {
            setSoundOn();
        }
        else
        {
            setSoundOff();
        }
    }

    private void setSoundOff()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("soundON", false);
        GlideApp.with(PropertiesActivity.this).load(R.drawable.btn_sound_off).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(sound);
        editor.apply();
    }
    private void setSoundOn()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("soundON", true);
        editor.apply();
        GlideApp.with(PropertiesActivity.this).load(R.drawable.btn_sound_on).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(sound);
    }

    @Override
    public void onClick(View v) {
            Intent i;
        switch (v.getId()) {

            case R.id.sound:
                soundON =sharedPreferences.getBoolean("soundON", true);
                if(soundON)
                {
                    setSoundOff();
                }
                else
                {
                    setSoundOn();
                }

                break;
            case R.id.account:
                i = new Intent(PropertiesActivity.this, AccountActivity.class);
                startActivity(i);
                break;
        }
    }

}
