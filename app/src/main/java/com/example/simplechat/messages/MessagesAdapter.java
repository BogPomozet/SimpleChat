package com.example.simplechat.messages;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplechat.R;
import com.example.simplechat.chat.Chat;
import com.example.simplechat.chat.ChatAdapter;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter <MessagesAdapter.MyViewHolder>{

    private final List<MessagesList> messagesLists;
    private final Context context;

    public MessagesAdapter(List<MessagesList> messagesLists, Context context) {
        this.messagesLists = messagesLists;
        this.context = context;
    }

    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MessagesList listA = messagesLists.get(position);

        if(!listA.getProfilePic().isEmpty()){
            Picasso.get().load(listA.getProfilePic()).into(holder.profilePic);
        }
        holder.uname.setText(listA.getName());
        holder.lastMessage.setText(listA.getLastMessage());

        if (listA.getUnseenMessages() == 0){
            holder.unseenMessages.setVisibility(View.GONE);
        }else {
            holder.unseenMessages.setVisibility(View.VISIBLE);
        }

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Chat.class);
                intent.putExtra("name",listA.getName());
                intent.putExtra("profile_pic",listA.getName());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messagesLists.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView profilePic;
        private TextView uname;
        private TextView lastMessage;
        private TextView unseenMessages;
        private LinearLayout rootLayout;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            profilePic = itemView.findViewById(R.id.profilePic);
            uname = itemView.findViewById(R.id.uname);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            unseenMessages = itemView.findViewById(R.id.unseenMessages);
            rootLayout = itemView.findViewById(R.id.rootLayout);
        }
    }
}