package com.intelektualcicii.potjera;


public class Question {

    public String questionText, correctAnsw,
            answ1,answ2,answ3,answ4;


    public Question() {
    }


    public void setAnsw1(String answ1) {
        this.answ1 = answ1;
    }

    public void setAnsw2(String answ2) {
        this.answ2 = answ2;
    }

    public void setAnsw3(String answ3) {
        this.answ3 = answ3;
    }

    public void setAnsw4(String answ4) {
        this.answ4 = answ4;
    }

    public Question(String questionText, String correctAnsw,
                    String answ1, String answ2, String answ3, String answ4) {

        this.questionText = questionText;
        this.correctAnsw = correctAnsw;
        this.answ1 = answ1;
        this.answ2 = answ2;
        this.answ3 = answ3;
        this.answ4 = answ4;
    }


}
