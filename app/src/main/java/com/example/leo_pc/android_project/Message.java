package com.example.leo_pc.android_project;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

/**
 * Created by LEO-PC on 5/7/2018.
 */

public class Message {
    /*
    * Need to add User to messages
    */
    private String message;
    private User sender;
    private Date createdAt;

    public Message(String message, FirebaseUser user, Date createdAt){
        this.message = message;
        this.sender = new User(user.getUid(), user.getEmail());
        this.createdAt = createdAt;
    }

    public Message(){
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
