package Client.view.gamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLabel extends JPanel {
    public JLabel nameLabel ;
    public JButton time ;
    public int remainTime;
    public JLabel scoreLabel ;
    public int score;
    public boolean continueRun;
    public String name;

    public GameLabel(String name) {
        nameLabel = new JLabel();
        time = new JButton();
        scoreLabel = new JLabel();
        this.name=name;
        nameLabel.setText("Player :"+name);
        remainTime = 25;
        score=0;
        time.setText("Remain time :"+String.valueOf(remainTime));
        scoreLabel.setText("Score :"+ String.valueOf(score));
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setBackground(Color.orange);
        this.add(nameLabel);
        this.add(time);
        this.add(scoreLabel);
    }

    @Override
    public void repaint() {
        nameLabel = new JLabel();
        time = new JButton();
        scoreLabel = new JLabel();
        this.removeAll();
        nameLabel.setText("Player :"+name);
        if(remainTime<25){
            time.setBackground(Color.GREEN);
        }else{
            time.setBackground(Color.red);
        }
        time.setText("Remain time :"+String.valueOf(remainTime));
        scoreLabel.setText("Score :"+ String.valueOf(score));
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setBackground(Color.orange);
        this.add(nameLabel);
        this.add(time);
        this.add(scoreLabel);
    }

    public void start() {
        remainTime = 25;
        continueRun=true;
        time.setBackground(Color.green);
    }

    public void passTime(){
        if (remainTime > 0) {
            remainTime -= 1;
            time.setText("Remain time :" +String.valueOf(remainTime));
            scoreLabel.setText("Score :"+ String.valueOf(score));
            time.setBackground(Color.GREEN);
        }else {
            stop();
        }
    }

    public void stop() {
        continueRun=false;
        time.setBackground(Color.red);
        remainTime=25;
        time.setText("Remain time :" +String.valueOf(remainTime));
        scoreLabel.setText("Score :"+ String.valueOf(score));
    }

    public  void setScore(int a){
        score=a;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public boolean isContinueRun() {
        return continueRun;
    }

    public void Decode(String s) {
        String[] A=s.split("\\[");
        nameLabel.setText("Player :"+A[0]);
        remainTime=Integer.valueOf(A[2]);
        time.setText("Remain time :"+A[2]);
        if(A[1].equals("true")){
            time.setBackground(Color.GREEN);
        }else{
            time.setBackground(Color.RED);
        }
        score=Integer.valueOf(A[3]);
        scoreLabel.setText("Score :"+ String.valueOf(score));
    }
}
