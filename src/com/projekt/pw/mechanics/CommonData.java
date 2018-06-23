package com.projekt.pw.mechanics;

public class CommonData {

    private double commonPoints;
    private int numberOfCommonQuestions;

    public CommonData(){
        this.commonPoints = 0;
        this.numberOfCommonQuestions = 0;
    }

    public void incrementNumberOfCommonQuestions(){
        numberOfCommonQuestions++;
    }

    public void addToCommonPoints(double points) {
        if(points > 0.0) {
            this.commonPoints += points;
        }
    }

    public double getCommonPoints() {
        return commonPoints;
    }

    public int getNumberOfCommonQuestions(){
        return numberOfCommonQuestions;
    }
}
