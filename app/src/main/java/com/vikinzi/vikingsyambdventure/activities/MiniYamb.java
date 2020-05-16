package com.vikinzi.vikingsyambdventure.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vikinzi.vikingsyambdventure.GlideApp;
import com.vikinzi.vikingsyambdventure.R;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Random;

import static android.graphics.Color.RED;


public class MiniYamb extends AppCompatActivity  implements
        View.OnClickListener {
    //TextView obj for 1. column - down
    TextView down_1;
    TextView down_2;
    TextView down_3;
    TextView down_4;
    TextView down_5;
    TextView down_6;
    TextView down_max;
    TextView down_min;
    TextView down_straight;
    TextView down_three;
    TextView down_full;
    TextView down_poker;
    TextView down_yamb;

    //TextView obj for 2. column - up
    TextView up_1;
    TextView up_2;
    TextView up_3;
    TextView up_4;
    TextView up_5;
    TextView up_6;
    TextView up_max;
    TextView up_min;
    TextView up_straight;
    TextView up_three;
    TextView up_full;
    TextView up_poker;
    TextView up_yamb;

    //TextView obj for 3. column - free
    TextView free_1;
    TextView free_2;
    TextView free_3;
    TextView free_4;
    TextView free_5;
    TextView free_6;
    TextView free_max;
    TextView free_min;
    TextView free_straight;
    TextView free_three;
    TextView free_full;
    TextView free_poker;
    TextView free_yamb;

    //TextView obj for 4. column - hand
    TextView hand_1;
    TextView hand_2;
    TextView hand_3;
    TextView hand_4;
    TextView hand_5;
    TextView hand_6;
    TextView hand_max;
    TextView hand_min;
    TextView hand_straight;
    TextView hand_three;
    TextView hand_full;
    TextView hand_poker;
    TextView hand_yamb;

    //sum
    TextView down_sum1;
    TextView down_sum2;
    TextView down_sum3;
    TextView up_sum1;
    TextView up_sum2;
    TextView up_sum3;
    TextView free_sum1;
    TextView free_sum2;
    TextView free_sum3;
    TextView hand_sum1;
    TextView hand_sum2;
    TextView hand_sum3;
    TextView yamb_sum1;
    TextView yamb_sum2;
    TextView yamb_sum3;
    TextView yamb_sumtotal;
    TextView txt_coins;

    ImageView dice1;
    ImageView dice2;
    ImageView dice3;
    ImageView dice4;
    ImageView dice5;
    ImageView dice6;
    ImageView play;
    ImageView profile;

    ImageView eraser, help1, help2, help3;

    TextView time;


    MediaPlayer mp_dice, mp_erase, mp_select, mp_write, mp_coins;
    boolean soundON = true;
    SharedPreferences sharedPreferences;



    //Arrays for TextViews values of all columns
    int[] arrayDicesValue,  arrayDicesValue1;
    Integer[] arrayCeo, oppArrayCeo;
    boolean[] arrayDicesState, arrayDicesState1;
    int numOfThrows = 0;
    int value = 0;
    TextView back;
    int bacanja = 0;
    int maxMin = 0;
    int coins = 0;
    Dialog popupDialog;
    int lastHand = 0;
    String opponentUID = "";

    int timeTotal = 0;
    int min = 0;
    int sec = 0;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference parRef, myRef, oppRef, coinsRef, profileRef, tabelaRef;


    Typeface enchantedLandFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_yamb);


        initLayout();
        initData();
        initListeners();

        enchantedLandFont = Typeface.createFromAsset(getAssets(), "fonts/enchanted_land.ttf");

        new CountDownTimer(1200000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeTotal = Integer.parseInt("" + millisUntilFinished / 1000);
                min = timeTotal/60;
                sec = timeTotal%60;
                if(min <= 5)
                    time.setTextColor(RED);
                time.setText((String.valueOf(min)) +" "+":"+" "+ (String.valueOf(sec)));
            }
            public void onFinish() {
                for (int k = 0; k < 52; k++)
                    if(arrayCeo[k] == -1)
                        arrayCeo[k] = 0;
                yamb_sumtotal.setText(String.valueOf(oneToSixSum() + MaxMinSum() + TheLastOne()));
                End();

            }

        }.start();

    }

    private void initLayout() {
        down_1 = (TextView) findViewById(R.id.down_1);
        down_2 = (TextView) findViewById(R.id.down_2);
        down_3 = (TextView) findViewById(R.id.down_3);
        down_4 = (TextView) findViewById(R.id.down_4);
        down_5 = (TextView) findViewById(R.id.down_5);
        down_6 = (TextView) findViewById(R.id.down_6);
        down_max = (TextView) findViewById(R.id.down_max);
        down_min = (TextView) findViewById(R.id.down_min);
        down_straight = (TextView) findViewById(R.id.down_straight);
        down_three = (TextView) findViewById(R.id.down_three);
        down_full = (TextView) findViewById(R.id.down_full);
        down_poker = (TextView) findViewById(R.id.down_poker);
        down_yamb = (TextView) findViewById(R.id.down_yamb);

        up_1 = (TextView) findViewById(R.id.up_1);
        up_2 = (TextView) findViewById(R.id.up_2);
        up_3 = (TextView) findViewById(R.id.up_3);
        up_4 = (TextView) findViewById(R.id.up_4);
        up_5 = (TextView) findViewById(R.id.up_5);
        up_6 = (TextView) findViewById(R.id.up_6);
        up_max = (TextView) findViewById(R.id.up_max);
        up_min = (TextView) findViewById(R.id.up_min);
        up_straight = (TextView) findViewById(R.id.up_straight);
        up_three = (TextView) findViewById(R.id.up_three);
        up_full = (TextView) findViewById(R.id.up_full);
        up_poker = (TextView) findViewById(R.id.up_poker);
        up_yamb = (TextView) findViewById(R.id.up_yamb);

        free_1 = (TextView) findViewById(R.id.free_1);
        free_2 = (TextView) findViewById(R.id.free_2);
        free_3 = (TextView) findViewById(R.id.free_3);
        free_4 = (TextView) findViewById(R.id.free_4);
        free_5 = (TextView) findViewById(R.id.free_5);
        free_6 = (TextView) findViewById(R.id.free_6);
        free_max = (TextView) findViewById(R.id.free_max);
        free_min = (TextView) findViewById(R.id.free_min);
        free_straight = (TextView) findViewById(R.id.free_straight);
        free_three = (TextView) findViewById(R.id.free_three);
        free_full = (TextView) findViewById(R.id.free_full);
        free_poker = (TextView) findViewById(R.id.free_poker);
        free_yamb = (TextView) findViewById(R.id.free_yamb);

        hand_1 = (TextView) findViewById(R.id.hand_1);
        hand_2 = (TextView) findViewById(R.id.hand_2);
        hand_3 = (TextView) findViewById(R.id.hand_3);
        hand_4 = (TextView) findViewById(R.id.hand_4);
        hand_5 = (TextView) findViewById(R.id.hand_5);
        hand_6 = (TextView) findViewById(R.id.hand_6);
        hand_max = (TextView) findViewById(R.id.hand_max);
        hand_min = (TextView) findViewById(R.id.hand_min);
        hand_straight = (TextView) findViewById(R.id.hand_straight);
        hand_three = (TextView) findViewById(R.id.hand_three);
        hand_full = (TextView) findViewById(R.id.hand_full);
        hand_poker = (TextView) findViewById(R.id.hand_poker);
        hand_yamb = (TextView) findViewById(R.id.hand_yamb);

        down_sum1 = (TextView) findViewById(R.id.down_sum1);
        down_sum2 = (TextView) findViewById(R.id.down_sum2);
        down_sum3 = (TextView) findViewById(R.id.down_sum3);
        up_sum1 = (TextView) findViewById(R.id.up_sum1);
        up_sum2 = (TextView) findViewById(R.id.up_sum2);
        up_sum3 = (TextView) findViewById(R.id.up_sum3);
        free_sum1 = (TextView) findViewById(R.id.free_sum1);
        free_sum2 = (TextView) findViewById(R.id.free_sum2);
        free_sum3 = (TextView) findViewById(R.id.free_sum3);
        hand_sum1 = (TextView) findViewById(R.id.hand_sum1);
        hand_sum2 = (TextView) findViewById(R.id.hand_sum2);
        hand_sum3 = (TextView) findViewById(R.id.hand_sum3);
        yamb_sum1 = (TextView) findViewById(R.id.yamb_sum1);
        yamb_sum2 = (TextView) findViewById(R.id.yamb_sum2);
        yamb_sum3 = (TextView) findViewById(R.id.yamb_sum3);
        yamb_sumtotal = (TextView) findViewById(R.id.yamb_sumtotal);

        dice1 = (ImageView) findViewById(R.id.dice1);
        dice2 = (ImageView) findViewById(R.id.dice2);
        dice3 = (ImageView) findViewById(R.id.dice3);
        dice4 = (ImageView) findViewById(R.id.dice4);
        dice5 = (ImageView) findViewById(R.id.dice5);
        dice6 = (ImageView) findViewById(R.id.dice6);

        play = (ImageView) findViewById(R.id.play);
        profile = (ImageView) findViewById(R.id.profile);

        eraser = (ImageView) findViewById(R.id.yamb_eraser);
        help1 = (ImageView) findViewById(R.id.yamb_help1);
        help2 = (ImageView) findViewById(R.id.yamb_help2);
        help3 = (ImageView) findViewById(R.id.yamb_help3);

        txt_coins = (TextView) findViewById(R.id.txt_coins);

        time = (TextView) findViewById(R.id.time);

    }

    private void initListeners() {
        down_1.setOnClickListener(this);
        down_2.setOnClickListener(this);
        down_3.setOnClickListener(this);
        down_4.setOnClickListener(this);
        down_5.setOnClickListener(this);
        down_6.setOnClickListener(this);
        down_max.setOnClickListener(this);
        down_min.setOnClickListener(this);
        down_straight.setOnClickListener(this);
        down_three.setOnClickListener(this);
        down_full.setOnClickListener(this);
        down_poker.setOnClickListener(this);
        down_yamb.setOnClickListener(this);

        up_1.setOnClickListener(this);
        up_2.setOnClickListener(this);
        up_3.setOnClickListener(this);
        up_4.setOnClickListener(this);
        up_5.setOnClickListener(this);
        up_6.setOnClickListener(this);
        up_max.setOnClickListener(this);
        up_min.setOnClickListener(this);
        up_straight.setOnClickListener(this);
        up_three.setOnClickListener(this);
        up_full.setOnClickListener(this);
        up_poker.setOnClickListener(this);
        up_yamb.setOnClickListener(this);

        free_1.setOnClickListener(this);
        free_2.setOnClickListener(this);
        free_3.setOnClickListener(this);
        free_4.setOnClickListener(this);
        free_5.setOnClickListener(this);
        free_6.setOnClickListener(this);
        free_max.setOnClickListener(this);
        free_min.setOnClickListener(this);
        free_straight.setOnClickListener(this);
        free_three.setOnClickListener(this);
        free_full.setOnClickListener(this);
        free_poker.setOnClickListener(this);
        free_yamb.setOnClickListener(this);

        hand_1.setOnClickListener(this);
        hand_2.setOnClickListener(this);
        hand_3.setOnClickListener(this);
        hand_4.setOnClickListener(this);
        hand_5.setOnClickListener(this);
        hand_6.setOnClickListener(this);
        hand_max.setOnClickListener(this);
        hand_min.setOnClickListener(this);
        hand_straight.setOnClickListener(this);
        hand_three.setOnClickListener(this);
        hand_full.setOnClickListener(this);
        hand_poker.setOnClickListener(this);
        hand_yamb.setOnClickListener(this);

        dice1.setOnClickListener(this);
        dice2.setOnClickListener(this);
        dice3.setOnClickListener(this);
        dice4.setOnClickListener(this);
        dice5.setOnClickListener(this);
        dice6.setOnClickListener(this);

        profile.setOnClickListener(this);
        play.setOnClickListener(this);

        eraser.setOnClickListener(this);

        help1.setOnClickListener(this);
        help2.setOnClickListener(this);
        help3.setOnClickListener(this);

        coinsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coins = dataSnapshot.getValue(int.class);
                txt_coins.setText(String.valueOf(coins));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                switch(dataSnapshot.getValue(int.class)){
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        oppRef.child("tabela").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot tableElementSnapshot: dataSnapshot.getChildren()) {
                    oppArrayCeo[i] = tableElementSnapshot.getValue(Integer.class);
                    i++;
                    End();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initData() {

        opponentUID = getIntent().getStringExtra("opponent");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        parRef = database.getReference("users");

        myRef = parRef.child(auth.getUid());
        oppRef = parRef.child(opponentUID);

        coinsRef = myRef.child("coins");
        tabelaRef = myRef.child("tabela");
        profileRef = oppRef.child("pic");

        arrayCeo = new Integer[52];
        for (int i = 0; i < 52; i++) {
            arrayCeo[i] = -1;
            oppArrayCeo[i] = -1;
        }
        tabelaRef.setValue(Arrays.asList(arrayCeo));

        arrayDicesValue = new int[6];
        arrayDicesState = new boolean[6];
        arrayDicesValue1 = new int[6];
        arrayDicesState1 = new boolean[6];
        for (int i = 0; i < 6; i++) {
            arrayDicesState[i] = true;
            arrayDicesValue[i] = 0;
            arrayDicesState1[i] = true;
            arrayDicesValue1[i] = 0;
        }

        played(0);
        eraser.setClickable(false);

        mp_dice = MediaPlayer.create(this, R.raw.rolldices);
        mp_erase = MediaPlayer.create(this, R.raw.eraser);
        mp_select = MediaPlayer.create(this, R.raw.diceselect);
        mp_write = MediaPlayer.create(this, R.raw.write);
        mp_coins = MediaPlayer.create(this, R.raw.coins);

        sharedPreferences = getApplicationContext().getSharedPreferences("Sound", MODE_PRIVATE);
        soundON = sharedPreferences.getBoolean("soundON", true);


    }

    public void SVE(int i, TextView textView) {
        if (i == 0 || i == 25) {
            arrayCeo[i] = write(i);
            textView.setText(String.valueOf(arrayCeo[i]));
            back = textView;
            played(i);
            yamb_sumtotal.setText(String.valueOf(oneToSixSum() + MaxMinSum() + TheLastOne()));
            tabelaRef.setValue(Arrays.asList(arrayCeo));
            if(soundON)
                mp_write.start();
        } else if (i < 13 && i > 0 && arrayCeo[i - 1] != -1) {
            arrayCeo[i] = write(i);
            textView.setText(String.valueOf(arrayCeo[i]));
            back = textView;
            played(i);
            yamb_sumtotal.setText(String.valueOf(oneToSixSum() + MaxMinSum() + TheLastOne()));
            tabelaRef.setValue(Arrays.asList(arrayCeo));
            if(soundON)
                mp_write.start();
        } else if (i > 12 && i < 25 && arrayCeo[i + 1] != -1) {
            arrayCeo[i] = write(i);
            textView.setText(String.valueOf(arrayCeo[i]));
            back = textView;
            played(i);
            yamb_sumtotal.setText(String.valueOf(oneToSixSum() + MaxMinSum() + TheLastOne()));
            tabelaRef.setValue(Arrays.asList(arrayCeo));
            if(soundON)
                mp_write.start();
        } else if ((i > 38 && i < 52) && numOfThrows == 1) {
            arrayCeo[i] = write(i);
            textView.setText(String.valueOf(arrayCeo[i]));
            back = textView;
            played(i);
            yamb_sumtotal.setText(String.valueOf(oneToSixSum() + MaxMinSum() + TheLastOne()));
            tabelaRef.setValue(Arrays.asList(arrayCeo));
            if(soundON)
                mp_write.start();

        } else if (i < 39 && i > 25) {
            arrayCeo[i] = write(i);
            textView.setText(String.valueOf(arrayCeo[i]));
            back = textView;
            played(i);
            yamb_sumtotal.setText(String.valueOf(oneToSixSum() + MaxMinSum() + TheLastOne()));
            tabelaRef.setValue(Arrays.asList(arrayCeo));
            if(soundON)
                mp_write.start();
        }
        if (yamb_sumtotal.getText().toString().equals("0"))
            yamb_sumtotal.setText("");

        End();

    }

    public void played(int i) {
        value = i;
        for (int j = 0; j < 6; j++) {
            arrayDicesState1[j] = arrayDicesState[j];
            arrayDicesValue1[j] = arrayDicesValue[j];
            arrayDicesState[j] = true;
            arrayDicesValue[j] = 0;
        }
        bacanja = numOfThrows;
        numOfThrows = 0;
        GlideApp.with(MiniYamb.this).load(R.drawable.unknown).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(dice1);
        GlideApp.with(MiniYamb.this).load(R.drawable.unknown).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(dice2);
        GlideApp.with(MiniYamb.this).load(R.drawable.unknown).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(dice3);
        GlideApp.with(MiniYamb.this).load(R.drawable.unknown).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(dice4);
        GlideApp.with(MiniYamb.this).load(R.drawable.unknown).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(dice5);
        GlideApp.with(MiniYamb.this).load(R.drawable.unknown).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(dice6);
        GlideApp.with(MiniYamb.this).load(R.drawable.play).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(play);
        play.setClickable(true);
        eraser.setClickable(true);
        lastHand = 0;
        for (int k = 0; k < 39; k++)
            if(arrayCeo[k] == -1)
                lastHand++;
    }

    public void random(ImageView imageView, int i) {
        Random rand = new Random();
        int n = rand.nextInt(6) + 1;

        try {
            if (arrayDicesState[i] && imageView != null) {
                switch (n) {
                    case 1: {
                        GlideApp.with(MiniYamb.this).load(R.drawable.orange1).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageView);
                    }
                    break;
                    case 2: {
                        GlideApp.with(MiniYamb.this).load(R.drawable.orange2).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageView);
                    }
                    break;
                    case 3: {
                        GlideApp.with(MiniYamb.this).load(R.drawable.orange3).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageView);
                    }
                    break;
                    case 4: {
                        GlideApp.with(MiniYamb.this).load(R.drawable.orange4).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageView);
                    }
                    break;
                    case 5: {
                        GlideApp.with(MiniYamb.this).load(R.drawable.orange5).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageView);
                    }
                    break;
                    case 6: {
                        GlideApp.with(MiniYamb.this).load(R.drawable.orange6).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageView);
                    }
                    break;
                }
                arrayDicesValue[i] = n;
            }
        } catch (OutOfMemoryError | Exception e) {
            //
        }
    }

    public void swapDice(ImageView imageView, int i) {
        try {
            if (!arrayDicesState[i]) {
                if(soundON)
                    mp_select.start();
                arrayDicesState[i] = true;
                switch (arrayDicesValue[i]) {
                    case 1:
                        GlideApp.with(MiniYamb.this).load(R.drawable.orange1).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageView);
                        break;
                    case 2:
                        GlideApp.with(MiniYamb.this).load(R.drawable.orange2).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageView);
                        break;
                    case 3:
                        GlideApp.with(MiniYamb.this).load(R.drawable.orange3).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageView);
                        break;
                    case 4:
                        GlideApp.with(MiniYamb.this).load(R.drawable.orange4).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageView);
                        break;
                    case 5:
                        GlideApp.with(MiniYamb.this).load(R.drawable.orange5).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageView);
                        break;
                    case 6:
                        GlideApp.with(MiniYamb.this).load(R.drawable.orange6).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(imageView);
                        break;
                }
            } else {
                int sel = 0;
                for (int j = 0; j < 6; j++)
                    if (!arrayDicesState[j])
                        sel++;
                if (sel <= 4) {
                    if(soundON)
                        mp_select.start();
                    arrayDicesState[i] = false;
                    switch (arrayDicesValue[i]) {

                        case 1:
                            GlideApp.with(MiniYamb.this).load(R.drawable.red1).diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(imageView);
                            break;
                        case 2:
                            GlideApp.with(MiniYamb.this).load(R.drawable.red2).diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(imageView);
                            break;
                        case 3:
                            GlideApp.with(MiniYamb.this).load(R.drawable.red3).diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(imageView);
                            break;
                        case 4:
                            GlideApp.with(MiniYamb.this).load(R.drawable.red4).diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(imageView);
                            break;
                        case 5:
                            GlideApp.with(MiniYamb.this).load(R.drawable.red5).diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(imageView);
                            break;
                        case 6:
                            GlideApp.with(MiniYamb.this).load(R.drawable.red6).diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(imageView);
                            break;
                    }
                }
            }
        } catch (OutOfMemoryError | Exception e) {
            //
        }
    }

    public void swapPlay(ImageView imageView) {
        numOfThrows++;
        try {
            if (numOfThrows == 1) {
                if(lastHand == 0)
                    play.setClickable(false);
                eraser.setClickable(false);
                GlideApp.with(MiniYamb.this).load(R.drawable.play1).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true).into(imageView);
            }
            if (numOfThrows == 2) {
                GlideApp.with(MiniYamb.this).load(R.drawable.play2).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true).into(imageView);
            }
            if (numOfThrows == 3) {
                GlideApp.with(MiniYamb.this).load(R.drawable.play3).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true).into(imageView);
                play.setClickable(false);

            }

            if (arrayDicesState[0])
                random(dice1, 0);
            if (arrayDicesState[1])
                random(dice2, 1);
            if (arrayDicesState[2])
                random(dice3, 2);
            if (arrayDicesState[3])
                random(dice4, 3);
            if (arrayDicesState[4])
                random(dice5, 4);
            if (arrayDicesState[5])
                random(dice6, 5);
        } catch (OutOfMemoryError | Exception e) {
            //
        }
    }

    public int write(int i) {
        int sum = 0;
        if ((i >= 0 && i <= 5) || (i >= 13 && i <= 18) || (i >= 26 && i <= 31) || (i >= 39 && i <= 44)) {
            for (int j = 0; j < 6; j++) {
                if (!arrayDicesState[j]) {
                    if (arrayDicesValue[j] == i + 1)
                        sum += (i + 1);
                    else if (arrayDicesValue[j] == i - 12)
                        sum += (i - 12);
                    else if (arrayDicesValue[j] == i - 25)
                        sum += (i - 25);
                    else if (arrayDicesValue[j] == i - 38)
                        sum += (i - 38);

                }
            }

        } else if (i == 6 || i == 7 || i == 19 || i == 20 || i == 32 || i == 33 || i == 45 || i == 46) {
            for (int j = 0; j < 6; j++) {
                if (!arrayDicesState[j])
                    sum += arrayDicesValue[j];
            }
        } else if (i == 8 || i == 21 || i == 34 || i == 47) {
            int[] kenta = {2, 3, 4, 5};
            int l = 0;
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 4; k++) {
                    if (!arrayDicesState[j] && arrayDicesValue[j] == kenta[k]) {
                        kenta[k] += 5;
                        l++;
                    }
                }
            }
            if (l == 4) {
                for (int j = 0; j < 6; j++)
                    if (!arrayDicesState[j] && (arrayDicesValue[j] == 1 || arrayDicesValue[j] == 6)) {
                        if (numOfThrows == 1)
                            sum = 66;
                        else if (numOfThrows == 2)
                            sum = 56;
                        else if (numOfThrows == 3)
                            sum = 46;
                    }
            }
        } else if ((i >= 9 && i <= 12) || (i >= 22 && i <= 25) || (i >= 35 && i <= 38) || (i >= 48 && i <= 51)) {
            int x = 0;
            int n = 0;
            int sf = 0;
            if (i == 9 || i == 10 || i == 22 || i == 23 || i == 35 || i == 36 || i == 48 || i == 49) {
                x = 3;
                n = 20;
            } else if (i == 11 || i == 24 || i == 37 || i == 50) {
                x = 4;
                n = 40;
            } else if (i == 12 || i == 25 || i == 38 || i == 51) {
                x = 5;
                n = 50;
            }
            for (int m = 0; m < 6; m++) {
                int count;
                for (int k = 1; k < 7; k++) {
                    if (!arrayDicesState[m] && k == arrayDicesValue[m])
                        continue;
                    count = 0;
                    for (int j = 0; j < 6; j++) {
                        if (!arrayDicesState[j] && arrayDicesValue[j] == k)
                            count++;
                    }
                    if (count >= x) {
                        sum = k * x + n;
                        sf = k;
                    }
                }
                if ((i == 10 || i == 23 || i == 36 || i == 49) && sum != 0) {
                    int sum1 = 0;
                    for (int a = 0; a < 6; a++) {
                        int count1;
                        for (int k = 1; k < 7; k++) {
                            if (!arrayDicesState[a] && k == arrayDicesValue[a] && k != sf)
                                continue;
                            count1 = 0;
                            for (int j = 0; j < 6; j++) {
                                if (!arrayDicesState[j] && arrayDicesValue[j] == k && k != sf)
                                    count1++;
                            }
                            if (count1 >= 2)
                                sum1 = sf * x + 2 * k + 30;
                            else if (sum1 == 0)
                                sum = 0;
                            else sum = sum1;
                        }
                    }
                }
            }
            if (x == 5 && sum != 0) {
                coins += 200;
                txt_coins.setText(String.valueOf(coins));
            }
        }
        return sum;
    }

    public int oneToSixSum() {
        int sum = 0;
        int sum1 = 0;
        int sum2 = 0;
        int sum4 = 0;
        int sum3 = 0;
        for (int i = 0; i < 6; i++) {
            if (arrayCeo[i] == -1)
                sum1++;
            sum1 += arrayCeo[i];
            if (arrayCeo[i + 13] == -1)
                sum2++;
            sum2 += arrayCeo[i + 13];
            if (arrayCeo[i + 26] == -1)
                sum3++;
            sum3 += arrayCeo[i + 26];
            if (arrayCeo[i + 39] == -1)
                sum4++;
            sum4 += arrayCeo[i + 39];
        }
        if (sum1 >= 60)
            sum1 += 30;
        if (sum2 >= 60)
            sum2 += 30;
        if (sum3 >= 60)
            sum3 += 30;
        if (sum4 >= 60)
            sum4 += 30;
        sum = sum1 + sum2 + sum3 + sum4;
        if (sum1 != 0)
            down_sum1.setText(String.valueOf(sum1));
        else
            down_sum1.setText("");
        if (sum2 != 0)
            up_sum1.setText(String.valueOf(sum2));
        else
            up_sum1.setText("");
        if (sum3 != 0)
            free_sum1.setText(String.valueOf(sum3));
        else
            free_sum1.setText("");
        if (sum4 != 0)
            hand_sum1.setText(String.valueOf(sum4));
        else
            hand_sum1.setText("");
        if (sum != 0)
            yamb_sum1.setText(String.valueOf(sum));
        else
            yamb_sum1.setText("");
        return sum;
    }

    public int MaxMinSum() {
        int sum = 0;
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        int sum4 = 0;
        if (arrayCeo[7] != -1)
            sum1 = (arrayCeo[6] - arrayCeo[7]) * arrayCeo[0];
        if (arrayCeo[13] != -1)
            sum2 = (arrayCeo[19] - arrayCeo[20]) * arrayCeo[13];
        if (arrayCeo[26] != -1 && arrayCeo[32] != -1 && arrayCeo[33] != -1)
            sum3 = (arrayCeo[32] - arrayCeo[33]) * arrayCeo[26];
        if (arrayCeo[39] != -1 && arrayCeo[45] != -1 && arrayCeo[46] != -1)
            sum4 = (arrayCeo[45] - arrayCeo[46]) * arrayCeo[39];
        sum = sum1 + sum2 + sum3 + sum4;
        if (sum1 != 0)
            down_sum2.setText(String.valueOf(sum1));
        else
            down_sum2.setText("");
        if (sum2 != 0)
            up_sum2.setText(String.valueOf(sum2));
        else
            up_sum2.setText("");
        if (sum3 != 0)
            free_sum2.setText(String.valueOf(sum3));
        else
            free_sum2.setText("");
        if (sum4 != 0)
            hand_sum2.setText(String.valueOf(sum4));
        else
            hand_sum2.setText("");
        if (sum != 0)
            yamb_sum2.setText(String.valueOf(sum));
        else
            yamb_sum2.setText("");
        return sum;
    }

    public int TheLastOne() {
        int sum = 0;
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        int sum4 = 0;
        for (int i = 8; i < 13; i++) {
            if (arrayCeo[i] == -1)
                sum1++;
            sum1 += arrayCeo[i];
            if (arrayCeo[i + 13] == -1)
                sum2++;
            sum2 += arrayCeo[i + 13];
            if (arrayCeo[i + 26] == -1)
                sum3++;
            sum3 += arrayCeo[i + 26];
            if (arrayCeo[i + 39] == -1)
                sum4++;
            sum4 += arrayCeo[i + 39];
        }
        sum = sum1 + sum2 + sum3 + sum4;
        if (sum1 != 0)
            down_sum3.setText(String.valueOf(sum1));
        else
            down_sum3.setText("");
        if (sum2 != 0)
            up_sum3.setText(String.valueOf(sum2));
        else
            up_sum3.setText("");
        if (sum3 != 0)
            free_sum3.setText(String.valueOf(sum3));
        else
            free_sum3.setText("");
        if (sum4 != 0)
            hand_sum3.setText(String.valueOf(sum4));
        else
            hand_sum3.setText("");
        if (sum != 0)
            yamb_sum3.setText(String.valueOf(sum));
        else
            yamb_sum3.setText("");
        return sum;
    }

    public void BackDices(ImageView imageView, int i) {

        switch (arrayDicesValue1[i]) {
            case 1:
                if (arrayDicesState1[i])
                    GlideApp.with(MiniYamb.this).load(R.drawable.orange1).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageView);
                else
                    GlideApp.with(MiniYamb.this).load(R.drawable.red1).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageView);
                break;
            case 2:
                if (arrayDicesState1[i])
                    GlideApp.with(MiniYamb.this).load(R.drawable.orange2).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageView);
                else
                    GlideApp.with(MiniYamb.this).load(R.drawable.red2).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageView);
                break;
            case 3:
                if (arrayDicesState1[i])
                    GlideApp.with(MiniYamb.this).load(R.drawable.orange3).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageView);
                else
                    GlideApp.with(MiniYamb.this).load(R.drawable.red3).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageView);
                break;
            case 4:
                if (arrayDicesState1[i])
                    GlideApp.with(MiniYamb.this).load(R.drawable.orange4).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageView);
                else
                    GlideApp.with(MiniYamb.this).load(R.drawable.red4).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageView);
                break;
            case 5:
                if (arrayDicesState1[i])
                    GlideApp.with(MiniYamb.this).load(R.drawable.orange5).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageView);
                else
                    GlideApp.with(MiniYamb.this).load(R.drawable.red5).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageView);
                break;
            case 6:
                if (arrayDicesState1[i])
                    GlideApp.with(MiniYamb.this).load(R.drawable.orange6).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageView);
                else
                    GlideApp.with(MiniYamb.this).load(R.drawable.red6).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(imageView);
                break;
        }
        eraser.setClickable(false);
    }

    public void BackValue() {
        if ((value == 12 || value == 25 || value == 38 || value == 51) && arrayCeo[value] != 0) {
            coins -= 200;
            txt_coins.setText(String.valueOf(coins));
        }

        arrayCeo[value] = -1;
        back.setText("");
        numOfThrows = bacanja;
        for (int j = 0; j < 6; j++) {
            arrayDicesState[j] = arrayDicesState1[j];
            arrayDicesValue[j] = arrayDicesValue1[j];
        }
        if (bacanja == 1) {
            eraser.setClickable(false);
            GlideApp.with(MiniYamb.this).load(R.drawable.play1).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(play);
        }
        if (bacanja == 2) {
            GlideApp.with(MiniYamb.this).load(R.drawable.play2).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(play);
        }
        if (bacanja == 3) {
            GlideApp.with(MiniYamb.this).load(R.drawable.play3).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(play);
            play.setClickable(false);
        }
        yamb_sumtotal.setText(String.valueOf(oneToSixSum() + MaxMinSum() + TheLastOne()));
        if (yamb_sumtotal.getText().toString().equals("0"))
            yamb_sumtotal.setText("");
        if(lastHand == 0)
            play.setClickable(false);

    }

    public void Help(ImageView imageView) {
        if (imageView == help1 && numOfThrows == 3 && coins >= 100) {
            if (arrayCeo[39] == -1) {
                arrayCeo[39] = 5;
                hand_1.setText(String.valueOf(5));
                back = hand_1;
                played(39);
                coins -= 100;
                txt_coins.setText(String.valueOf(coins));
                coinsRef.setValue(coins);
                eraser.setClickable(false);
                tabelaRef.setValue(Arrays.asList(arrayCeo));
                if(soundON)
                    mp_coins.start();
            } else if (arrayCeo[0] == -1) {
                arrayCeo[0] = 5;
                down_1.setText(String.valueOf(5));
                back = down_1;
                played(0);
                coins -= 100;
                txt_coins.setText(String.valueOf(coins));
                coinsRef.setValue(coins);
                eraser.setClickable(false);
                tabelaRef.setValue(Arrays.asList(arrayCeo));
                if(soundON)
                    mp_coins.start();
            } else {
                Toast.makeText(this, "No one can help you anymore", Toast.LENGTH_SHORT).show();
                if (soundON)
                    mp_coins.start();
            }
        } else if (imageView == help2 && numOfThrows == 3 && coins >= 150) {
            if (arrayCeo[46] == -1) {
                arrayCeo[46] = 5;
                hand_min.setText(String.valueOf(5));
                back = hand_min;
                played(46);
                coins -= 150;
                txt_coins.setText(String.valueOf(coins));
                coinsRef.setValue(coins);
                eraser.setClickable(false);
                tabelaRef.setValue(Arrays.asList(arrayCeo));
                if(soundON)
                    mp_coins.start();
            } else if (arrayCeo[45] == -1) {
                arrayCeo[45] = 30;
                hand_max.setText(String.valueOf(30));
                back = hand_max;
                played(45);
                coins -= 150;
                txt_coins.setText(String.valueOf(coins));
                coinsRef.setValue(coins);
                eraser.setClickable(false);
                tabelaRef.setValue(Arrays.asList(arrayCeo));
                if(soundON)
                    mp_coins.start();
            } else {
                Toast.makeText(this, "No one can help you anymore", Toast.LENGTH_SHORT).show();
                if (soundON)
                    mp_coins.start();
            }
        } else if (imageView == help3 && numOfThrows == 3 && coins >= 200) {
            if (arrayCeo[51] == -1) {
                arrayCeo[51] = 80;
                hand_yamb.setText(String.valueOf(80));
                back = hand_yamb;
                played(51);
                coins -= 200;
                txt_coins.setText(String.valueOf(coins));
                coinsRef.setValue(coins);
                eraser.setClickable(false);
                tabelaRef.setValue(Arrays.asList(arrayCeo));
                if(soundON)
                    mp_coins.start();
            } else if (arrayCeo[25] == -1) {
                arrayCeo[25] = 80;
                up_yamb.setText(String.valueOf(80));
                back = up_yamb;
                played(25);
                coins -= 200;
                txt_coins.setText(String.valueOf(coins));
                coinsRef.setValue(coins);
                eraser.setClickable(false);
                tabelaRef.setValue(Arrays.asList(arrayCeo));
                if(soundON)
                    mp_coins.start();
            } else {
                Toast.makeText(this, "No one can help you anymore", Toast.LENGTH_SHORT).show();
                if (soundON)
                    mp_coins.start();
            }
            if(coins < 100 && imageView == help1)
                Toast.makeText(this, "Not enough coins", Toast.LENGTH_SHORT).show();
            if(coins < 150 && imageView == help2)
                Toast.makeText(this, "Not enough coins", Toast.LENGTH_SHORT).show();
            if(coins < 200 && imageView == help3)
                Toast.makeText(this, "Not enough coins", Toast.LENGTH_SHORT).show();
        }
        yamb_sumtotal.setText(String.valueOf(oneToSixSum() + MaxMinSum() + TheLastOne()));
        if (yamb_sumtotal.getText().toString().equals("0"))
            yamb_sumtotal.setText("");
        End();
    }

    public void End() {
        int i, oppPoints = 0;
        for (i = 0; i < 52; i++) {
            if (arrayCeo[i] == -1)
                break;
            oppPoints += oppArrayCeo[i];
        }
        if (i == 52 && oppEnd()) {
            popupDialog = new Dialog(this);
            popupDialog.setContentView(R.layout.popup_endgame);
            ImageView profile_win = (ImageView) popupDialog.findViewById(R.id.profile_win);
            final TextView nick_win = (TextView) popupDialog.findViewById(R.id.nick_win);
            TextView points_win = (TextView) popupDialog.findViewById(R.id.points_win);
            ImageView profile_lose = (ImageView) popupDialog.findViewById(R.id.profile_lose);
            TextView nick_lose = (TextView) popupDialog.findViewById(R.id.nick_lose);
            TextView points_lose = (TextView) popupDialog.findViewById(R.id.points_lose);
            if(oneToSixSum() + MaxMinSum() + TheLastOne() > oppPoints) {
                points_win.setText(yamb_sumtotal.toString());//TODO finish here
            }


            popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupDialog.show();

        }
    }

    public boolean oppEnd(){
        int i;
        for (i = 0; i < 52; i++)
            if(oppArrayCeo[i] == -1)
                break;
        if(i != 52)
            return false;
        else
            return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            popupDialog = new Dialog(this);
            popupDialog.setContentView(R.layout.popup_exitgame);
            popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupDialog.show();
            ImageView btn_yes;
            ImageView btn_no;
            TextView txt_goback;
            txt_goback = (TextView) popupDialog.findViewById(R.id.txt_goback);
            txt_goback.setTypeface(enchantedLandFont);
            btn_no = (ImageView) popupDialog.findViewById(R.id.btn_no);
            btn_yes = (ImageView) popupDialog.findViewById(R.id.btn_yes);
            btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//
                    onBackPressed();

                }
            });
            btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupDialog.dismiss();
                }
            });
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        for (int j = 0; j < 6; j++)
            if (!arrayDicesState[j])
                maxMin++;
        if (numOfThrows != 0) {
            switch (v.getId()) {
                // 2. column - down
                case R.id.down_1:
                    if (arrayCeo[0] == -1)
                        SVE(0, down_1);
                    break;
                case R.id.down_2:
                    if (arrayCeo[1] == -1)
                        SVE(1, down_2);
                    break;
                case R.id.down_3:
                    if (arrayCeo[2] == -1)
                        SVE(2, down_3);
                    break;
                case R.id.down_4:
                    if (arrayCeo[3] == -1)
                        SVE(3, down_4);
                    break;
                case R.id.down_5:
                    if (arrayCeo[4] == -1)
                        SVE(4, down_5);
                    break;
                case R.id.down_6:
                    if (arrayCeo[5] == -1)
                        SVE(5, down_6);
                    break;

                case R.id.down_straight:
                    if (arrayCeo[8] == -1)
                        SVE(8, down_straight);
                    break;
                case R.id.down_three:
                    if (arrayCeo[9] == -1)
                        SVE(9, down_three);
                    break;
                case R.id.down_full:
                    if (arrayCeo[10] == -1)
                        SVE(10, down_full);
                    break;
                case R.id.down_poker:
                    if (arrayCeo[11] == -1)
                        SVE(11, down_poker);
                    break;
                case R.id.down_yamb:
                    if (arrayCeo[12] == -1)
                        SVE(12, down_yamb);
                    break;

                // 3. column - up
                case R.id.up_1:
                    if (arrayCeo[13] == -1)
                        SVE(13, up_1);
                    break;

                case R.id.up_2:
                    if (arrayCeo[14] == -1)
                        SVE(14, up_2);
                    break;

                case R.id.up_3:
                    if (arrayCeo[15] == -1)
                        SVE(15, up_3);
                    break;

                case R.id.up_4:
                    if (arrayCeo[16] == -1)
                        SVE(16, up_4);
                    break;

                case R.id.up_5:
                    if (arrayCeo[17] == -1)
                        SVE(17, up_5);
                    break;

                case R.id.up_6:
                    if (arrayCeo[18] == -1)
                        SVE(18, up_6);
                    break;

                case R.id.up_straight:
                    if (arrayCeo[21] == -1)
                        SVE(21, up_straight);
                    break;

                case R.id.up_three:
                    if (arrayCeo[22] == -1)
                        SVE(22, up_three);
                    break;

                case R.id.up_full:
                    if (arrayCeo[23] == -1)
                        SVE(23, up_full);
                    break;

                case R.id.up_poker:
                    if (arrayCeo[24] == -1)
                        SVE(24, up_poker);
                    break;

                case R.id.up_yamb:
                    if (arrayCeo[25] == -1)
                        SVE(25, up_yamb);
                    break;

                // 3. column - free
                case R.id.free_1:
                    if (arrayCeo[26] == -1)
                        SVE(26, free_1);
                    break;

                case R.id.free_2:
                    if (arrayCeo[27] == -1)
                        SVE(27, free_2);
                    break;

                case R.id.free_3:
                    if (arrayCeo[28] == -1)
                        SVE(28, free_3);
                    break;

                case R.id.free_4:
                    if (arrayCeo[29] == -1)
                        SVE(29, free_4);
                    break;

                case R.id.free_5:
                    if (arrayCeo[30] == -1)
                        SVE(30, free_5);
                    break;

                case R.id.free_6:
                    if (arrayCeo[31] == -1)
                        SVE(31, free_6);
                    break;

                case R.id.free_straight:
                    if (arrayCeo[34] == -1)
                        SVE(34, free_straight);
                    break;

                case R.id.free_three:
                    if (arrayCeo[35] == -1)
                        SVE(35, free_three);
                    break;

                case R.id.free_full:
                    if (arrayCeo[36] == -1)
                        SVE(36, free_full);
                    break;

                case R.id.free_poker:
                    if (arrayCeo[37] == -1)
                        SVE(37, free_poker);
                    break;

                case R.id.free_yamb:
                    if (arrayCeo[38] == -1)
                        SVE(38, free_yamb);
                    break;

                // 4. column - hand
                case R.id.hand_1:
                    if (arrayCeo[39] == -1)
                        SVE(39, hand_1);
                    break;

                case R.id.hand_2:
                    if (arrayCeo[40] == -1)
                        SVE(40, hand_2);
                    break;

                case R.id.hand_3:
                    if (arrayCeo[41] == -1)
                        SVE(41, hand_3);
                    break;

                case R.id.hand_4:
                    if (arrayCeo[42] == -1)
                        SVE(42, hand_4);
                    break;

                case R.id.hand_5:
                    if (arrayCeo[43] == -1)
                        SVE(43, hand_5);
                    break;

                case R.id.hand_6:
                    if (arrayCeo[44] == -1)
                        SVE(44, hand_6);
                    break;

                case R.id.hand_straight:
                    if (arrayCeo[47] == -1)
                        SVE(47, hand_straight);
                    break;

                case R.id.hand_three:
                    if (arrayCeo[48] == -1)
                        SVE(48, hand_three);
                    break;

                case R.id.hand_full:
                    if (arrayCeo[49] == -1)
                        SVE(49, hand_full);
                    break;

                case R.id.hand_poker:
                    if (arrayCeo[50] == -1)
                        SVE(50, hand_poker);
                    break;

                case R.id.hand_yamb:
                    if (arrayCeo[51] == -1)
                        SVE(51, hand_yamb);
                    break;

                // Dices
                case R.id.dice1:
                    swapDice(dice1, 0);
                    break;

                case R.id.dice2:
                    swapDice(dice2, 1);
                    break;

                case R.id.dice3:
                    swapDice(dice3, 2);
                    break;

                case R.id.dice4:
                    swapDice(dice4, 3);
                    break;

                case R.id.dice5:
                    swapDice(dice5, 4);
                    break;

                case R.id.dice6:
                    swapDice(dice6, 5);
                    break;

                case R.id.down_max:
                    if (arrayCeo[6] == -1 && arrayCeo[5] != -1)
                        if (maxMin == 5)
                            SVE(6, down_max);
                        else
                            Toast.makeText(this, "You must select 5 dice", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.down_min:
                    if (arrayCeo[7] == -1 && arrayCeo[6] != -1)
                        if (maxMin == 5)
                            SVE(7, down_min);
                        else
                            Toast.makeText(this, "You must select 5 dice", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.up_max:
                    if (arrayCeo[19] == -1 && arrayCeo[20] != -1)
                        if (maxMin == 5)
                            SVE(19, up_max);
                        else
                            Toast.makeText(this, "You must select 5 dice", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.up_min:
                    if (arrayCeo[20] == -1 && arrayCeo[21] != -1)
                        if (maxMin == 5)
                            SVE(20, up_min);
                        else
                            Toast.makeText(this, "You must select 5 dice", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.free_max:
                    if (arrayCeo[32] == -1)
                        if (maxMin == 5)
                            SVE(32, free_max);
                        else
                            Toast.makeText(this, "You must select 5 dice", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.free_min:
                    if (arrayCeo[33] == -1)
                        if (maxMin == 5)
                            SVE(33, free_min);
                        else
                            Toast.makeText(this, "You must select 5 dice", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.hand_max:
                    if (arrayCeo[45] == -1 && numOfThrows == 1)
                        if (maxMin == 5)
                            SVE(45, hand_max);
                        else
                            Toast.makeText(this, "You must select 5 dice", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.hand_min:
                    if (arrayCeo[46] == -1 && numOfThrows == 1)
                        if (maxMin == 5)
                            SVE(46, hand_min);
                        else
                            Toast.makeText(this, "You must select 5 dice", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        maxMin = 0;

        switch (v.getId()) {
            case R.id.play:
                if (soundON)
                    mp_dice.start();
                swapPlay(play);
                break;

            case R.id.yamb_help1:
                Help(help1);
                break;

            case R.id.yamb_help2:
                Help(help2);
                break;

            case R.id.yamb_help3:
                Help(help3);
                break;
            case R.id.yamb_eraser:
                if (soundON)
                    mp_erase.start();

                BackDices(dice1, 0);
                BackDices(dice2, 1);
                BackDices(dice3, 2);
                BackDices(dice4, 3);
                BackDices(dice5, 4);
                BackDices(dice6, 5);
                BackValue();
                break;
                
            case R.id.profile:
                Intent goToOpp = new Intent(MiniYamb.this, MiniYambOpponent.class);
                goToOpp.putExtra("opponent", opponentUID);
                startActivity(goToOpp);
                break;

        }
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mp_dice != null)
            mp_dice.release();
        if (mp_erase != null)
            mp_erase.release();
        if (mp_select != null)
            mp_select.release();
        if (mp_write != null)
            mp_write.release();
        if (mp_coins != null)
            mp_coins.release();
    }
}