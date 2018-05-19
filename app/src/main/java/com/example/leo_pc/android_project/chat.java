package com.example.leo_pc.android_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class chat extends AppCompatActivity {
    private Context context;
    private RecyclerView mMessageRecycler;
    private FirebaseAuth auth;
    private MymessageRecyclerViewAdapter mMessageAdapter;
    private List<Message> messageList = new ArrayList<Message>();
    private FirebaseUser user;
    private String TAG = "Message";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                messageList.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    messageList.add(child.getValue(Message.class));
                }
                Log.d(TAG, "Value is: " + messageList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MymessageRecyclerViewAdapter(this, messageList);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);

        mMessageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                linearLayoutManager.smoothScrollToPosition(mMessageRecycler, null, mMessageAdapter.getItemCount());
            }
        });

        mMessageRecycler.setLayoutManager(linearLayoutManager);

        mMessageRecycler.setAdapter(mMessageAdapter);




        final Button sendMessage = findViewById(R.id.button_chatbox_send);
        final EditText messageText = findViewById(R.id.edittext_chatbox);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.d("chat", "user=" + user.getEmail());
                Message newMessage = new Message(messageText.getText().toString(), user, new Date());
                FirebaseDatabase.getInstance()
                        .getReference("message")
                        .push()
                        .setValue(newMessage);
                messageList.add(newMessage);
                messageText.setText("", EditText.BufferType.EDITABLE);
                mMessageAdapter = new MymessageRecyclerViewAdapter(context, messageList);
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                linearLayoutManager.setStackFromEnd(true);
                mMessageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onItemRangeInserted(int positionStart, int itemCount) {
                        linearLayoutManager.smoothScrollToPosition(mMessageRecycler, null, mMessageAdapter.getItemCount());
                    }
                });
                mMessageRecycler.setLayoutManager(linearLayoutManager);
            }
        });
    }
}
