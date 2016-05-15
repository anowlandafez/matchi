package com.example.matchi.matchi;

public class User {
    private String facebookName;
    private String facebookID;


    public User (String facebookName, String facebookID){
        this.facebookName = facebookName;
        this.facebookID = facebookID;
    }

    public String getId(){
        return facebookID;
    }

    public String getName(){
        return facebookName;
    }



}
