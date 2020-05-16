package com.vikinzi.vikingsyambdventure.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vikinzi.vikingsyambdventure.R;
import com.vikinzi.vikingsyambdventure.models.User;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference parRef, Ref;

    private TextView txtCoins, txtTotal, txtWins, txtRang;
    private TextView coins, total_games, wins, rang;
    private EditText txtNick, txtAge;
    private RadioGroup radioSex;
    private ImageButton savedata, profile;

    User user;
    int pic = 0;


    private Dialog popupDialog;
    private CarouselPicker carouselPicker1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ConstraintLayout pozadina = (ConstraintLayout) findViewById(R.id.pozadina);

        Intent intent = getIntent();
        int background = intent.getIntExtra("Background", 0);
        switch(background){
            case 0:
                pozadina.setBackgroundResource(R.drawable.bcgimg);
                break;
            case 1:
                pozadina.setBackgroundResource(R.drawable.background);
                break;
        }

        Typeface enchantedLandFont = Typeface.createFromAsset(getAssets(), "fonts/enchanted_land.ttf");

        popupDialog = new Dialog(this);
        radioSex = (RadioGroup) findViewById(R.id.radio_sex);
        txtAge = (EditText) findViewById(R.id.txtAge);
        txtNick = (EditText) findViewById(R.id.txtNick);
        profile = (ImageButton) findViewById(R.id.profile);
        savedata = (ImageButton) findViewById(R.id.save);

        coins = (TextView) findViewById(R.id.coins);
        coins.setTypeface(enchantedLandFont);
        total_games = (TextView) findViewById(R.id.total_games);
        total_games.setTypeface(enchantedLandFont);
        wins = (TextView) findViewById(R.id.wins);
        wins.setTypeface(enchantedLandFont);
        rang = (TextView) findViewById(R.id.rang);
        rang.setTypeface(enchantedLandFont);

        txtCoins = (TextView) findViewById(R.id.coins_num);
        txtCoins.setTypeface(enchantedLandFont);

        txtTotal = (TextView) findViewById(R.id.total_games_num);
        txtTotal.setTypeface(enchantedLandFont);

        txtWins = (TextView) findViewById(R.id.wins_num);
        txtWins.setTypeface(enchantedLandFont);

        txtRang = (TextView) findViewById(R.id.rang_num);
        txtRang.setTypeface(enchantedLandFont);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        parRef = database.getReference("users");
        Ref = parRef.child(auth.getUid());

        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = new User(dataSnapshot.getValue(User.class));
                populateProfile(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                RadioButton selected = (RadioButton) findViewById( radioSex.getCheckedRadioButtonId() );
                boolean sex = selected.getText().toString().equals("Female");
                int age = Integer.parseInt( txtAge.getText().toString().trim());
                String nickname = txtNick.getText().toString().trim();
                int coins = Integer.parseInt(txtCoins.getText().toString());
                int total = Integer.parseInt(txtTotal.getText().toString());
                int wins = Integer.parseInt(txtWins.getText().toString());
                int rang = Integer.parseInt(txtRang.getText().toString());


                User user = new User( age, sex, nickname, pic, coins, total, wins, rang);
                Ref.setValue(user);
                finish();
            }
        });

    }

    public void ShowPopup(View v) {
        TextView txtclose;
        popupDialog.setContentView(R.layout.popup_profile_pic);
        txtclose = (TextView) popupDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.dismiss();
            }
        });
        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupDialog.show();

        carouselPicker1 = (CarouselPicker) popupDialog.findViewById(R.id.carouselPicker);
        List<CarouselPicker.PickerItem> itemsImages = new ArrayList<>();
        itemsImages.add(new CarouselPicker.DrawableItem(R.drawable.avatar_ridji));
        itemsImages.add(new CarouselPicker.DrawableItem(R.drawable.avatar_plavi));
        itemsImages.add(new CarouselPicker.DrawableItem(R.drawable.avatar_zeleni));
        CarouselPicker.CarouselViewAdapter imageAdapter = new CarouselPicker.CarouselViewAdapter(this,itemsImages,0);
        carouselPicker1.setAdapter(imageAdapter);
        carouselPicker1.setCurrentItem(user.getPic());
        carouselPicker1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        profile.setBackgroundResource(R.drawable.avatar_ridji);
                        pic = 0;
                        break;
                    case 1:
                        profile.setBackgroundResource(R.drawable.avatar_plavi);
                        pic = 1;
                        break;
                    case 2:
                        profile.setBackgroundResource(R.drawable.avatar_zeleni);
                        pic = 2;
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        profile.setBackgroundResource(R.drawable.avatar_ridji);
                        pic = 0;
                        break;
                    case 1:
                        profile.setBackgroundResource(R.drawable.avatar_plavi);
                        pic = 1;
                        break;
                    case 2:
                        profile.setBackgroundResource(R.drawable.avatar_zeleni);
                        pic = 2;
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void populateProfile(User u){
        txtAge.setText(u.getAge() + "");
        txtNick.setText(u.getNickname());
        radioSex.check( u.getSex() ? R.id.radioFemale : R.id.radioMale );
        txtCoins.setText(u.getCoins() + "");
        txtTotal.setText(u.getTotal() + "");
        txtWins.setText(u.getWins() + "");
        txtRang.setText(u.getRang() + "");
        switch (u.getPic()){
            case 0:
                profile.setBackgroundResource(R.drawable.avatar_ridji);
                break;
            case 1:
                profile.setBackgroundResource(R.drawable.avatar_plavi);
                break;
            case 2:
                profile.setBackgroundResource(R.drawable.avatar_zeleni);
                break;
        }
    }

}

