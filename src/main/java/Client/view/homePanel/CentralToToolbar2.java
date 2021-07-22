package Client.view.homePanel;

import Client.ClientSideConnection;
import Client.listtener.StringListener;
import Client.view.CentralPanel;

import java.io.IOException;
import java.util.Timer;

public class CentralToToolbar2 implements StringListener {
    private CentralPanel centralPanel;
    private HighScoreView highScoreView;
    private PersonalView personalView;
    private StreamView streamView;
    private Boolean firstTime;
    private ClientSideConnection csc;
    private Timer timer;

    public CentralToToolbar2(CentralPanel centralPanel, ClientSideConnection csc) {
        this.centralPanel = centralPanel;
        timer = new Timer();
        this.csc = csc;
        highScoreView = new HighScoreView(this, csc);
        personalView = new PersonalView(this, csc);
        streamView = new StreamView(this, csc);
    }

    public void stringEventOccurred(String string) {
        if (string.equals("exit")) {
            csc.sendEvent("Exit");
            timer.cancel();
            try {
                csc.getSocket().close();
            } catch (IOException e) {
                System.out.println("IOException in centralToToolbar2 in string EventOccurred " + e);
            }
            System.exit(0);
        }
        if (string.equals("highScore")) {
            timer.cancel();
            firstTime = true;
            highPaint(highScoreView);
        }
        if (string.equals("personal")) {
            timer.cancel();
            firstTime = true;
            PersonalPaint(personalView);
        }
        if (string.equals("stream")) {
            timer.cancel();
            firstTime = true;
            streamPaint(streamView);
        }
    }

    private void streamPaint(StreamView streamView) {
        timer = new Timer();
        timer.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        centralPanel.setVisible(false);
                        centralPanel.removeAll();
                        centralPanel.add(new StreamView(streamView));
                        centralPanel.setVisible(true);
                        firstTime = false;
                    }
                },
                1000, 5000
        );
    }


    void PersonalPaint(PersonalView personalView) {
        centralPanel.setVisible(false);
        centralPanel.removeAll();
        centralPanel.add(new PersonalView(personalView));
        centralPanel.setVisible(true);
        firstTime = false;
    }

    private void highPaint(HighScoreView highScoreView) {
        timer = new Timer();
        timer.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        centralPanel.setVisible(false);
                        centralPanel.removeAll();
                        centralPanel.add(new HighScoreView(highScoreView));
                        centralPanel.setVisible(true);
                        firstTime = false;
                    }
                },
                1000, 5000
        );
    }

    public void TimerBrake(){
        timer.cancel();
    }

    public CentralPanel getCentralPanel() {
        return centralPanel;
    }

    @Override
    public String toString() {
        return "CenterToToolbarStringListener2{" +
                "centerPanel=" + centralPanel + "\n" +
                ", highScoreView=" + highScoreView + "\n" +
                ", personalView=" + personalView + "\n" +
                ", streamView=" + streamView + "\n" +
                ", firstTime=" + firstTime + "\n" +
                '}';
    }
}
