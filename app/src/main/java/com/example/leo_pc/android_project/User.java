package com.example.leo_pc.android_project;

/**
 * Created by LEO-PC on 5/7/2018.
 */

public class User {
    private String UID;
    private String nickname;
    private String profile;

    public User (String UID, String email) {
        this.UID = UID;
        this.nickname = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
