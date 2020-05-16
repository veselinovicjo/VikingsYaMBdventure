package com.vikinzi.vikingsyambdventure.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.vikinzi.vikingsyambdventure.R;

public class FindMiniActivity extends AppCompatActivity {

    public static final String TAG = AppCompatActivity.class.getSimpleName();
    private final String NONE = "none";

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mMatchmaker = database.getReference("minimatchmaker");
    DatabaseReference mGamesReference = database.getReference("minigames");
    DatabaseReference parRef = database.getReference("users");
    DatabaseReference myRef = parRef.child(auth.getUid());
    DatabaseReference opponentRef;
    TextView my_nick, opponent_nick, status;
    ImageView my_profile, opponent_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mini);
        my_nick = (TextView) findViewById(R.id.my_nick);
        opponent_nick = (TextView) findViewById(R.id.opponent_nick);
        status = (TextView) findViewById(R.id.status);
        my_profile = (ImageView) findViewById(R.id.my_profile);
        opponent_profile = (ImageView) findViewById(R.id.opponent_profile);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                my_nick.setText(dataSnapshot.child("nickname").getValue(String.class));
                switch (dataSnapshot.child("pic").getValue(int.class)) {
                    case 0:
                        my_profile.setImageResource(R.drawable.avatar_ridji);
                        break;
                    case 1:
                        my_profile.setImageResource(R.drawable.avatar_plavi);
                        break;
                    case 2:
                        my_profile.setImageResource(R.drawable.avatar_zeleni);
                }
                opponent_nick.setText("?");
                opponent_profile.setImageResource(R.drawable.avatar_upitnik);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        findMatch();
    }

    /**
     * This function is the callback of the "Find Match" button.   This function reads the current
     * value of the matchmaker storage location to determine if it thinks that we're the first arriver
     * or the second.
     */
    public void findMatch() {
        mMatchmaker.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String matchmaker = dataSnapshot.getValue(String.class);
                Log.d(TAG, "mMatchmaker: " + matchmaker);

                if (matchmaker.equals(NONE)) {
                    findMatchFirstArriver();
                } else {
                    findMatchSecondArriver(matchmaker);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * The first arriver needs to create the game, add themselves to it, and then atomically
     * (i.e., using a transaction) verify that no one else has posted a game yet and post the game.
     * If it fails to post the game, it destroys the game.
     */
    private void findMatchFirstArriver() {
        String matchmaker;
        final DatabaseReference dbReference = mGamesReference.push();
        dbReference.child("prvi").setValue(auth.getUid());
        matchmaker = dbReference.getKey();
        final String newMatchmaker = matchmaker;

        mMatchmaker.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if(mutableData.getValue()==null) {
                    mutableData.setValue(NONE);
                }
                if (mutableData.getValue(String.class).equals(NONE)) {
                    mutableData.setValue(newMatchmaker);
                    return Transaction.success(mutableData);
                }
                // someone beat us to posting a game, so fail and retry later
                return Transaction.abort();
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean commit, DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(),
                        commit ? "transaction success" : "transaction failed",
                        Toast.LENGTH_SHORT).show();
                if (!commit) {
                    // we failed to post the game, so destroy the game so we don't leave trash.
                    dbReference.removeValue();
                }
                else{
                    dbReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getChildrenCount()==2){
                                String opponent = dataSnapshot.child("drugi").getValue(String.class);
                                Intent startGame = new Intent(FindMiniActivity.this, MiniYamb.class);
                                startGame.putExtra("opponent", opponent);
                                opponentRef = parRef.child(opponent);
                                opponentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        opponent_nick.setText(dataSnapshot.child("nickname").getValue(String.class));
                                        switch (dataSnapshot.child("pic").getValue(int.class)) {
                                            case 0:
                                                opponent_profile.setImageResource(R.drawable.avatar_ridji);
                                                break;
                                            case 1:
                                                opponent_profile.setImageResource(R.drawable.avatar_plavi);
                                                break;
                                            case 2:
                                                opponent_profile.setImageResource(R.drawable.avatar_zeleni);
                                        }
                                        status.setText("Starting Match");
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                startActivity(startGame);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    /**
     * The second arriver needs atomically (i.e., with a transcation) verify that the game is
     * still available to join and then remove the game from the matchmaker.  It then adds
     * itself to the game, so that player0 gets a notification that the game was joined.
     * @param matchmaker
     */
    private void findMatchSecondArriver(final String matchmaker) {
        mMatchmaker.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if(mutableData.getValue()==null){
                    mutableData.setValue(matchmaker);
                }
                if (mutableData.getValue(String.class).equals(matchmaker)) {
                    mutableData.setValue(NONE);
                    return Transaction.success(mutableData);
                }
                // someone beat us to joining this game, so fail and retry later
                return Transaction.abort();
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean committed,
                                   DataSnapshot dataSnapshot) {
                if (committed) {
                    DatabaseReference gameReference = mGamesReference.child(matchmaker);
                    gameReference.child("drugi").setValue(auth.getUid());
                    mMatchmaker.setValue(NONE);
                    gameReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String opponent = dataSnapshot.child("prvi").getValue(String.class); //getValue(String.class);
                            Intent startGame = new Intent(FindMiniActivity.this, MiniYamb.class);
                            startGame.putExtra("opponent", opponent);
                            opponentRef = parRef.child(opponent);
                            opponentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    opponent_nick.setText(dataSnapshot.child("nickname").getValue(String.class));
                                    switch (dataSnapshot.child("pic").getValue(int.class)) {
                                        case 0:
                                            opponent_profile.setImageResource(R.drawable.avatar_ridji);
                                            break;
                                        case 1:
                                            opponent_profile.setImageResource(R.drawable.avatar_plavi);
                                            break;
                                        case 2:
                                            opponent_profile.setImageResource(R.drawable.avatar_zeleni);
                                    }
                                    status.setText("Starting Match");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            startActivity(startGame);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }
}