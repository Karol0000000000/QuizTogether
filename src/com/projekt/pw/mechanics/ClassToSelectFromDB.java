package com.projekt.pw.mechanics;

import java.sql.SQLException;

public class ClassToSelectFromDB extends Query implements Runnable{

    private Question question;
    private String sql;
    private int ifDBMustWait;
    private Object monitorDB;
    private boolean threadFinish;

    public ClassToSelectFromDB(Object monitorDB){
        this.monitorDB=monitorDB;
        ifDBMustWait = 1;
        threadFinish = true;
    }

    @Override
    protected void process() throws SQLException {

        while(resultset.next()){
            question = new Question(resultset.getInt("id_pytania")
                    ,resultset.getString("tresc_pytania")
                    ,resultset.getString("odpowiedz_a")
                    ,resultset.getString("odpowiedz_b")
                    ,resultset.getString("odpowiedz_c")
                    ,resultset.getString("odpowiedz_d")
                    ,resultset.getString("prawidlowa"));
        }

    }

    @Override
    public void run() {
        while(!threadFinish) {
            synchronized (monitorDB) {

                if (ifDBMustWait == 1) {
                    try {
                        monitorDB.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(threadFinish){
                    return;
                }

                try {
                    super.setSql(sql);
                    connectToDataBase();
                    executeQuery();
                    this.process();
                    close();
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
                ifDBMustWait = 1;
                monitorDB.notify();
            }
        }
    }

    @Override
    public void setSql(String sql) {
        this.sql = sql;
    }

    public Question getQuestion() {
        return question;
    }

    public int getIfDBMustWait() {
        return ifDBMustWait;
    }

    public void setIfDBMustWait(int ifDBMustWait) {
        this.ifDBMustWait = ifDBMustWait;
    }

    public void setThreadFinish(boolean threadFinish) {
        this.threadFinish = threadFinish;
    }
}
