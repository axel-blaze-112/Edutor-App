package com.example.miniproj2.Models;

public class forumChatMessages
{
    String message,sender,timestamp,type;

    public forumChatMessages()
    {
    }

    public forumChatMessages(String message, String sender, String timestamp, String type) {
        this.message = message;
        this.sender = sender;
        this.timestamp = timestamp;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timestamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
