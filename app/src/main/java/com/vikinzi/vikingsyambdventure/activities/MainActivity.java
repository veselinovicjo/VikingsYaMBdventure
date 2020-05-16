package com.vikinzi.vikingsyambdventure.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.ValueEventListener;
import com.vikinzi.vikingsyambdventure.R;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener
{

    private int pic = 0;

    @Override
    public void onClick(View v)
    {
        Intent i;
        switch (v.getId()) {

            case R.id.singleplayer:
                i = new Intent(MainActivity.this, NewSinglePlayerActivity.class);
                startActivity(i);
                break;

            case R.id.gamerules:
                i = new Intent(MainActivity.this, GameRulesActivity.class);
                startActivity(i);
                break;

//            case R.id.ranglist:
//                i = new Intent(MainActivity.this, RangListActivity.class);
//                startActivity(i);
//                break;

            case R.id.properties:
                i = new Intent(MainActivity.this, PropertiesActivity.class);
                startActivity(i);
                break;

            case R.id.newgame:

                i = new Intent(MainActivity.this, NewGameActivity.class);
                startActivity(i);
                break;

            case R.id.profile:

                i = new Intent(MainActivity.this, ProfileActivity.class);
                i.putExtra("Background", 0);
                startActivity(i);
                break;
        }
    }
    ImageButton sp, gr, rl, p, ng, prof;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
     //   requestWindowFeature(Window.FEATURE_NO_TITLE);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("users");
        DatabaseReference ChildRef = Ref.child(auth.getUid());
        DatabaseReference profPic = ChildRef.child("pic");



        ImageButton sp = (ImageButton) findViewById(R.id.singleplayer);
        sp.setOnClickListener(this);
        ImageButton gr = (ImageButton) findViewById(R.id.gamerules);
        gr.setOnClickListener(this);
//        ImageButton rl = (ImageButton) findViewById(R.id.ranglist);
//        rl.setOnClickListener(this);
        ImageButton p = (ImageButton) findViewById(R.id.properties);
        p.setOnClickListener(this);
        ImageButton ng = (ImageButton) findViewById(R.id.newgame);
        ng.setOnClickListener(this);
        final ImageButton prof = (ImageButton) findViewById(R.id.profile);
        prof.setOnClickListener(this);

        profPic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pic = dataSnapshot.getValue(int.class);
                switch(pic){
                    case 0:
                        prof.setBackgroundResource(R.drawable.avatar_ridji);
                        break;
                    case 1:
                        prof.setBackgroundResource(R.drawable.avatar_plavi);
                        break;
                    case 2:
                        prof.setBackgroundResource(R.drawable.avatar_zeleni);
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                auth.signOut();
            }
        });




        // Read from the database
        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String value = dataSnapshot.getValue(String.class);
                Toast baza = Toast.makeText(this, "Value is: " + value, Toast.LENGTH_LONG);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/


//        GlideApp.with(this)
//                .load(R.drawable.avatar_muski_braon)
//                .centerCrop()
//                .transition(DrawableTransitionOptions.withCrossFade()) //Optional
//                .skipMemoryCache(true)  //No memory cache
//                .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .into(prof);
    }


}





