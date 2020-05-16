package com.vikinzi.vikingsyambdventure.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vikinzi.vikingsyambdventure.R;

import java.util.List;

public class MiniYambOpponent extends AppCompatActivity implements View.OnClickListener
{
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

    ImageView profile;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference parRef, myRef, oppRef, Tabela;
    Integer[] niz = new Integer[52];
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_yamb_opponent);
        initLayout();

        Intent i = getIntent();
        id = i.getStringExtra("opponent");
        
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        parRef = database.getReference("users");
        oppRef = parRef.child(id);
        myRef = parRef.child(auth.getUid());
        Tabela = oppRef.child("tabela");

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myRef.child("pic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                switch (dataSnapshot.getValue(int.class)){
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
        
        Tabela.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //List<Integer> temp = (List<Integer>) dataSnapshot.getValue();
                //temp.toArray(niz);
                int i = 0;
                for (DataSnapshot tableElementSnapshot: dataSnapshot.getChildren()) {
                    niz[i] = tableElementSnapshot.getValue(Integer.class);
                    i++;
                }
                populateTable(niz);
                yamb_sumtotal.setText(String.valueOf(oneToSixSum() + MaxMinSum() + TheLastOne()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        
        profile = (ImageView) findViewById(R.id.profile);
    }

    @Override
    public void onClick(View v)
    {
        Intent i;
        switch (v.getId()) {

            case R.id.profile:
                finish();
                break;
        }
    }

    public void populateTable(Integer[] tabel){
        String[] niz = new String[52];
        for(int i = 0; i < 52; i++){
            if(tabel[i]== -1)
                niz[i] = "";
            else
                niz[i] = String.valueOf(tabel[i]);
        }

        down_1.setText(String.valueOf(niz[0]));
        down_2.setText(String.valueOf(niz[1]));
        down_3.setText(String.valueOf(niz[2]));
        down_4.setText(String.valueOf(niz[3]));
        down_5.setText(String.valueOf(niz[4]));
        down_6.setText(String.valueOf(niz[5]));
        down_max.setText(String.valueOf(niz[6]));
        down_min.setText(String.valueOf(niz[7]));
        down_straight.setText(String.valueOf(niz[8]));
        down_three.setText(String.valueOf(niz[9]));
        down_full.setText(String.valueOf(niz[10]));
        down_poker.setText(String.valueOf(niz[11])); 
        down_yamb.setText(String.valueOf(niz[12]));

        up_1.setText(String.valueOf(niz[13]));
        up_2.setText(String.valueOf(niz[14]));
        up_3.setText(String.valueOf(niz[15]));
        up_4.setText(String.valueOf(niz[16]));
        up_5.setText(String.valueOf(niz[17]));
        up_6.setText(String.valueOf(niz[18]));
        up_max.setText(String.valueOf(niz[19]));
        up_min.setText(String.valueOf(niz[20]));
        up_straight.setText(String.valueOf(niz[21]));
        up_three.setText(String.valueOf(niz[22]));
        up_full.setText(String.valueOf(niz[23]));
        up_poker.setText(String.valueOf(niz[24]));
        up_yamb.setText(String.valueOf(niz[25]));

        free_1.setText(String.valueOf(niz[26]));
        free_2.setText(String.valueOf(niz[27]));
        free_3.setText(String.valueOf(niz[28]));
        free_4.setText(String.valueOf(niz[29]));
        free_5.setText(String.valueOf(niz[30]));
        free_6.setText(String.valueOf(niz[31]));
        free_max.setText(String.valueOf(niz[32]));
        free_min.setText(String.valueOf(niz[33]));
        free_straight.setText(String.valueOf(niz[34]));
        free_three.setText(String.valueOf(niz[35]));
        free_full.setText(String.valueOf(niz[36]));
        free_poker.setText(String.valueOf(niz[37]));
        free_yamb.setText(String.valueOf(niz[38]));

        hand_1.setText(String.valueOf(niz[39]));
        hand_2.setText(String.valueOf(niz[40]));
        hand_3.setText(String.valueOf(niz[41]));
        hand_4.setText(String.valueOf(niz[42]));
        hand_5.setText(String.valueOf(niz[43]));
        hand_6.setText(String.valueOf(niz[44]));
        hand_max.setText(String.valueOf(niz[45]));
        hand_min.setText(String.valueOf(niz[46]));
        hand_straight.setText(String.valueOf(niz[47]));
        hand_three.setText(String.valueOf(niz[48]));
        hand_full.setText(String.valueOf(niz[49]));
        hand_poker.setText(String.valueOf(niz[50]));
        hand_yamb.setText(String.valueOf(niz[51]));
    }
    public int oneToSixSum() {
        int sum = 0;
        int sum1 = 0;
        int sum2 = 0;
        int sum4 = 0;
        int sum3 = 0;
        for (int i = 0; i < 6; i++) {
            if (niz[i] == -1)
                sum1++;
            sum1 += niz[i];
            if (niz[i + 13] == -1)
                sum2++;
            sum2 += niz[i + 13];
            if (niz[i + 26] == -1)
                sum3++;
            sum3 += niz[i + 26];
            if (niz[i + 39] == -1)
                sum4++;
            sum4 += niz[i + 39];
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
        if (niz[7] != -1)
            sum1 = (niz[6] - niz[7]) * niz[0];
        if (niz[13] != -1)
            sum2 = (niz[19] - niz[20]) * niz[13];
        if (niz[26] != -1 && niz[32] != -1 && niz[33] != -1)
            sum3 = (niz[32] - niz[33]) * niz[26];
        if (niz[39] != -1 && niz[45] != -1 && niz[46] != -1)
            sum4 = (niz[45] - niz[46]) * niz[39];
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
            if (niz[i] == -1)
                sum1++;
            sum1 += niz[i];
            if (niz[i + 13] == -1)
                sum2++;
            sum2 += niz[i + 13];
            if (niz[i + 26] == -1)
                sum3++;
            sum3 += niz[i + 26];
            if (niz[i + 39] == -1)
                sum4++;
            sum4 += niz[i + 39];
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


}
