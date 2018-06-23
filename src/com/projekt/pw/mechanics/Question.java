package com.projekt.pw.mechanics;

public class Question {

    private int questionID;
    private String content;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String correct;

    public Question(int questionID, String content, String answerA, String answerB, String answerC, String answerD, String correct) {
        this.questionID = questionID;
        this.content = content;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correct = correct;
    }

    public boolean equalsAnswers(String answer){
        if(correct.toUpperCase().equals(answer.toUpperCase()))
            return true;
        else return false;
    }

    @Override
    public String toString() {
        return "Pytanie{" +
                "idPytania=" + questionID +
                ", trescPytania='" + content + '\'' +
                ", odpowiedzA='" + answerA + '\'' +
                ", odpowiedzB='" + answerB + '\'' +
                ", odpowiedzC='" + answerC + '\'' +
                ", odpowiedzD='" + answerD + '\'' +
                ", prawidlowa='" + correct + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public String getCorrect() {
        return correct;
    }
}
