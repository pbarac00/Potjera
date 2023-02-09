package com.intelektualcicii.potjera;

import java.util.ArrayList;

public class User {

    public String fullName, email;
    public ArrayList<String> gamesList=new ArrayList<>();

    public User(){}

    public User(String fullName, String email){
        this.fullName=fullName;
        this.email=email;
    }



    public void addResultOnEndOfGameList(String result){
        gamesList.add(result);
    }
}
