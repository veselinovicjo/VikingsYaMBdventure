package com.vikinzi.vikingsyambdventure.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.vikinzi.vikingsyambdventure.R;

public class LoadingScreen extends AppCompatActivity {

    private FirebaseAuth auth;
    private ProgressBar prog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        prog = (ProgressBar) findViewById(R.id.progresBar);

        //Nadogradi da proveri da li postoji kao korisnik
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoadingScreen.this, MainActivity.class));
        }
        else {
            Intent gotoLogIn  = new Intent(LoadingScreen.this, LoginActivity.class);
            startActivity(gotoLogIn);
        }
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException ex) {
            android.util.Log.d("YourApplicationName", ex.toString());
        }
        finish();
    }
}
