package com.example.matchi.matchi;

public class User {
    private String first_name;
    private String last_name;
    private String id;


    public User (){
    }

    public void setFName(String fname){
        first_name = fname;
    }

    public void setLName(String lname){
        last_name = lname;
    }

    public void setId(String fbid){
        id = fbid;
    }

    public String getId(){
        return id;
    }

    public String getFName(){
        return first_name;
    }

    public String getLName(){
        return last_name;
    }

}
