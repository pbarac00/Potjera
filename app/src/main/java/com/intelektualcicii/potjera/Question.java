package com.intelektualcicii.potjera;

//nisan siguran triba li ova klasa
public class Question {

    public String questionId, questionText, questionCorrectAnsw,
            questionWrongAns1,getQuestionWrongAns2,getQuestionWrongAns3;


    public Question() {
    }

    public Question(String questionId, String questionText, String questionCorrectAnsw, String questionWrongAns1, String getQuestionWrongAns2, String getQuestionWrongAns3) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionCorrectAnsw = questionCorrectAnsw;
        this.questionWrongAns1 = questionWrongAns1;
        this.getQuestionWrongAns2 = getQuestionWrongAns2;
        this.getQuestionWrongAns3 = getQuestionWrongAns3;
    }
}
