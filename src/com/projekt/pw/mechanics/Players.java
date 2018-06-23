package com.projekt.pw.mechanics;

import com.projekt.pw.gui.GameController;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Players implements Runnable{

    private GameController gameController;
    private volatile Boolean[] whoseTurn;
    private int numberOfQuestion;
    private int numberOfPlayer;
    private Map<Integer, String> answers;
    private long startTime;
    private double timeOfAnswer;
    private ClassToSelectFromDB classToSelectFromDB;
    private Question question;
    private double pointsForQuestion;
    private CommonData commonData;
    private List<Integer> randNumbers;
    private boolean threadFinish;
    private Object monitorDB;
    private String playerAnswer;
    private boolean playerAnswerIsGood;
    private Object monitorPlayer;

    public Players(GameController graController, Boolean[] b, int nrGracza, Map m, ClassToSelectFromDB selectFromDB,
                   CommonData wspolnePunkty, Object monitorDB, Object monitorPlayer) {
        this.monitorDB = monitorDB;
        this.gameController = graController;
        this.whoseTurn =b;
        this.numberOfPlayer =nrGracza;
        this.answers = m;
        this.classToSelectFromDB = selectFromDB;
        this.pointsForQuestion = 0;
        this.commonData = wspolnePunkty;
        this.numberOfQuestion =0;
        this.randNumbers = rand();
        this.threadFinish = true;
        this.monitorPlayer = monitorPlayer;
    }

    @Override
    public void run() {

        while(true) {
            synchronized(monitorPlayer) {
                while (!whoseTurn[numberOfPlayer]) {
                    try {
                        monitorPlayer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (threadFinish) {
                        return;
                    }
                }
            }
            if(numberOfQuestion >0) {//odpowiedz
                timeOfAnswer = (System.currentTimeMillis() - startTime)/1000.0;
                playerAnswer = answers.get(numberOfQuestion);
                if(question.equalsAnswers(playerAnswer)){
                    playerAnswerIsGood = true;
                    if(numberOfPlayer ==0) {
                        gameController.setVisibleGoodAnswer1(true);
                        pointsForQuestion = 15-timeOfAnswer;
                        manageCommonPoints();
                        incrementNumberOfQuestionsInCommonData();
                        setTimeAndManageQuestion1();
                        pointToGoodAndBadAnswer();
                    }else if(numberOfPlayer ==1){
                        gameController.setVisibleGoodAnswer2(true);
                        pointsForQuestion = 15-timeOfAnswer;
                        manageCommonPoints();
                        incrementNumberOfQuestionsInCommonData();
                        setTimeAndManageQuestion2();
                        pointToGoodAndBadAnswer();
                    }
                }else{
                    playerAnswerIsGood = false;
                    if(numberOfPlayer ==0){
                        gameController.setVisibleBadAnswer1(true);
                        incrementNumberOfQuestionsInCommonData();
                        setTimeAndManageQuestion1();
                        pointToGoodAndBadAnswer();
                    }else if(numberOfPlayer ==1){
                        gameController.setVisibleBadAnswer2(true);
                        incrementNumberOfQuestionsInCommonData();
                        setTimeAndManageQuestion2();
                        pointToGoodAndBadAnswer();
                    }
                }
            }

            if(threadFinish){
                return;
            }

            if(numberOfQuestion ==0){
                if(numberOfPlayer ==0){
                    countDown3Seconds1();
                }else if(numberOfPlayer ==1){
                    countDown3Seconds2();
                }
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    numberOfQuestion++;
                    if (numberOfPlayer == 0) {
                        selectQuery();
                        try {
                            setQuestionForPlayer1();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        startTime = System.currentTimeMillis();
                    } else if (numberOfPlayer == 1) {
                        selectQuery();
                        try {
                            setQuestionForPlayer2();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        startTime = System.currentTimeMillis();
                    }

                }
            });
            whoseTurn[numberOfPlayer]=false;
        }
    }

    public void pointToGoodAndBadAnswer(){

        int ileRazy = 0;
        String []odp;
        String []styles = {"#11d36f", "#f20909"};
        if(playerAnswerIsGood){
            odp = new String[1];
            ileRazy = 1;
            odp[0] = question.getCorrect().toLowerCase();
        }else{
            odp = new String[2];
            ileRazy = 2;
            odp[0] = question.getCorrect().toLowerCase();
            odp[1] = playerAnswer.toLowerCase();
        }
        if(numberOfPlayer == 0){
            for(int i= 0; i<ileRazy; i++) {
                if (odp[i].equals("a")) {
                    gameController.getLabelAGr1().setStyle("-fx-background-color: "+styles[i]+";");
                } else if (odp[i].equals("b")) {
                    gameController.getLabelBGr1().setStyle("-fx-background-color: "+styles[i]+";");
                } else if (odp[i].equals("c")) {
                    gameController.getLabelCGr1().setStyle("-fx-background-color: "+styles[i]+";");
                } else if (odp[i].equals("d")) {
                    gameController.getLabelDGr1().setStyle("-fx-background-color: "+styles[i]+";");
                }
            }
            if(threadFinish)return;
            countDown3Seconds1();
            manageInfoAboutSummaryAnswer1();
            gameController.getLabelAGr1().setStyle(null);
            gameController.getLabelBGr1().setStyle(null);
            gameController.getLabelCGr1().setStyle(null);
            gameController.getLabelDGr1().setStyle(null);
        }else if(numberOfPlayer == 1){
            for(int i= 0; i<ileRazy; i++) {
                if (odp[i].equals("a")) {
                    gameController.getLabelAGr2().setStyle("-fx-background-color: "+styles[i]+";");
                } else if (odp[i].equals("b")) {
                    gameController.getLabelBGr2().setStyle("-fx-background-color: "+styles[i]+";");
                } else if (odp[i].equals("c")) {
                    gameController.getLabelCGr2().setStyle("-fx-background-color: "+styles[i]+";");
                } else if (odp[i].equals("d")) {
                    gameController.getLabelDGr2().setStyle("-fx-background-color: "+styles[i]+";");
                }
            }
            if(threadFinish)return;
            countDown3Seconds2();
            manageInfoAboutSummaryAnswer2();
            gameController.getLabelAGr2().setStyle(null);
            gameController.getLabelBGr2().setStyle(null);
            gameController.getLabelCGr2().setStyle(null);
            gameController.getLabelDGr2().setStyle(null);
        }
    }

    public List<Integer> rand(){

        Random random = new Random();
        List<Integer> tempRandomNumbers = new ArrayList<Integer>();
        boolean b;
        int k;

        for(int j=0;j<10;j++){

            b=false;

            while(!b) {
                b=true;
                k = random.nextInt(36)+1;
                for(int p : tempRandomNumbers){
                    if(k == p){
                        b = false;
                    }
                }
                if(b == true){
                    tempRandomNumbers.add(k);
                }

            }
        }
        return tempRandomNumbers;
    }

    private void incrementNumberOfQuestionsInCommonData() {
        synchronized(commonData) {
            commonData.incrementNumberOfCommonQuestions();
        }
    }

    private void manageCommonPoints(){
        double pointsToSet;
        synchronized(commonData){
            commonData.addToCommonPoints(pointsForQuestion);
            pointsToSet = commonData.getCommonPoints();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameController.setTotalPoints(pointsToSet);
            }
        });
    }

    private void countDown3Seconds2() {

        for(int i = 3; i>0; i--) {
            final int j = i;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gameController.setLabelQuestionGracz2(String.valueOf(j));
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void countDown3Seconds1() {

        for(int i=3; i>0; i--) {
            final int j = i;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gameController.setLabelQuestionGracz1(String.valueOf(j));
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void manageInfoAboutSummaryAnswer1(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameController.setVisibleBadAnswer1(false);
                gameController.setVisibleGoodAnswer1(false);
                gameController.setVisibleTime1(false);
            }
        });
    }

    private void manageInfoAboutSummaryAnswer2(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameController.setVisibleBadAnswer2(false);
                gameController.setVisibleGoodAnswer2(false);
                gameController.setVisibleTime2(false);
            }
        });
    }

    private void setTimeAndManageQuestion1() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameController.setTime1(timeOfAnswer);
                gameController.setVisibleTime1(true);
            }
        });
        if(numberOfQuestion == 10 && commonData.getNumberOfCommonQuestions() == 20){
            gameController.setDisableVBox1();
            afterUsedAllQuestion();
        }else if(numberOfQuestion == 10){
            gameController.setDisableVBox1();
            threadFinish = true;
            return;
        }
    }

    private void setTimeAndManageQuestion2() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameController.setTime2(timeOfAnswer);
                gameController.setVisibleTime2(true);
            }
        });
        if(numberOfQuestion == 10 && commonData.getNumberOfCommonQuestions() == 20){
            gameController.setDisableVBox2();
            afterUsedAllQuestion();
        }else if(numberOfQuestion == 10){
            gameController.setDisableVBox2();
            threadFinish = true;
            return;
        }
    }

    private void afterUsedAllQuestion() {
        threadFinish = true;
        pointToGoodAndBadAnswer();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameController.displaySummary();
            }
        });
    }

    public void setQuestionForPlayer1() throws InterruptedException {
        gameController.setLabelHowMuchQuestionGracz1(String.valueOf(numberOfQuestion));
        gameController.setLabelQuestionGracz1(question.getContent());
        gameController.setLabelAPlayer1(question.getAnswerA());
        gameController.setLabelBPlayer1(question.getAnswerB());
        gameController.setLabelCPlayer1(question.getAnswerC());
        gameController.setLabelDPlayer1(question.getAnswerD());
    }

    public void setQuestionForPlayer2() throws InterruptedException {
        gameController.setLabelHowMuchQuestionGracz2(String.valueOf(numberOfQuestion));
        gameController.setLabelQuestionGracz2(question.getContent());
        gameController.setLabelAPlayer2(question.getAnswerA());
        gameController.setLabelBPlayer2(question.getAnswerB());
        gameController.setLabelCPlayer2(question.getAnswerC());
        gameController.setLabelDPlayer2(question.getAnswerD());
    }

    public void selectQuery(){


        if(threadFinish){
            return;
        }

        int numer = randNumbers.get(numberOfQuestion - 1);

        synchronized(monitorDB) {

            classToSelectFromDB.setIfDBMustWait(0);
            classToSelectFromDB.setSql("select * from pytanie where id_pytania=" + numer);
            monitorDB.notify();

            while(classToSelectFromDB.getIfDBMustWait() == 0){
                try {
                    monitorDB.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            question = classToSelectFromDB.getQuestion();
        }

    }

    public void setThreadFinish(boolean threadFinish) {
        this.threadFinish = threadFinish;
    }
}