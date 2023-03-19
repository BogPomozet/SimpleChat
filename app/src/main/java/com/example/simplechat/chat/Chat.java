package com.example.simplechat.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simplechat.MemoryData;
import com.example.simplechat.R;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://simplechat-52db7-default-rtdb.firebaseio.com");
    private RecyclerView chattingRecyclerView;
    String chatKey;
    String getUserMobile = "";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView nameTV = findViewById(R.id.uname);
        final EditText messageEdtTxt = findViewById(R.id.messageEditText);
        final CircleImageView profilePic = findViewById(R.id.profilePic);
        final ImageView sendBtn = findViewById(R.id.sendBtn);

        chattingRecyclerView =findViewById(R.id.chattingRecyclerView);

        //get data from messages adapter class
        final String getName = getIntent().getStringExtra("name");
        final String getProfilePic = getIntent().getStringExtra("profilePic");
        chatKey = getIntent().getStringExtra("chat_key");
        final String getMobile = getIntent().getStringExtra("mobile");

        //getting mobile from memory
        getUserMobile = MemoryData.getData(Chat.this);

        nameTV.setText(getName);
        Picasso.get().load(getProfilePic).into(profilePic);

        chattingRecyclerView.setHasFixedSize(true);
        chattingRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));

        if (chatKey.isEmpty()) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //generate chat key
                    chatKey = "1";
                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String getTxtMessage = messageEdtTxt.getText().toString();

                //get current timestamp
                final String timeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);

                MemoryData.saveLastMessage(timeStamp, chatKey, Chat.this);

                databaseReference.child("chat").child(chatKey).child("user1").setValue(getUserMobile);
                databaseReference.child("chat").child(chatKey).child("user2").setValue(getUserMobile);
                databaseReference.child("chat").child(chatKey).child("messages").child(timeStamp).child("msg").setValue(getTxtMessage);
                databaseReference.child("chat").child(chatKey).child("messages").child(timeStamp).child("mobile").setValue(getUserMobile);

                //clear edit txt
                messageEdtTxt.setText("");
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}