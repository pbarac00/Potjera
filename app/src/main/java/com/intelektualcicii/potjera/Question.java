package com.intelektualcicii.potjera;


public class Question {

    public String questionText, correctAnsw,
            answ1,answ2,answ3,answ4;


    public Question() {
    }

    public Question( String questionText, String correctAnsw,
                    String answ1, String answ2, String answ3, String answ4) {

        this.questionText = questionText;
        this.correctAnsw = correctAnsw;
        this.answ1 = answ1;
        this.answ2 = answ2;
        this.answ3 = answ3;
        this.answ4 = answ4;
    }


}
