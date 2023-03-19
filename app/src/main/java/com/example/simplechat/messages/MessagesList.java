package com.example.simplechat.messages;

public class MessagesList {

    private String name, mobile, lastMessage, profilePic, chatKey;

    private int unseenMessages;

    public MessagesList(String name, String mobile, String lastMessage, String profilePic, int unseenMessages, String chatKey) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.unseenMessages = unseenMessages;
        this.profilePic = profilePic;
        this.chatKey = chatKey;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getChatKey() {
        return chatKey;
    }
}
