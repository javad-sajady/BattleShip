package Client.view.streamPanel;


import Client.ClientSideConnection;
import Client.listtener.StringListener;
import Client.view.gamePanel.GameBoard;
import Client.view.gamePanel.GameLabel;
import Client.view.gamePanel.GamePage;
import Client.view.gamePanel.GamePaint;

import java.util.Timer;
import java.util.TimerTask;

public class CentralToToolbar5 {
    private GamePage2 gamePage;
    private ClientSideConnection csc;
    public GameBoard2 myMap;
    public GameBoard2 rivalMap;
    public GameLabel myLabel;
    public GameLabel rivalLabel;
    boolean endGame;
    public int number;

    public CentralToToolbar5(GamePage2 gamePage2, ClientSideConnection csc) {
        this.gamePage = gamePage2;
        this.csc = csc;
    }

    public void setInf(GamePaint2 gamePaint) {
        this.myLabel = gamePaint.myLabel;
        this.myMap = gamePaint.myMap;
        this.rivalLabel = gamePaint.rivalLabel;
        this.rivalMap = gamePaint.rivalMap;
    }


    public void Begin(int a) {
        number=a;
        csc.sendEvent("StreamBegin["+number);
        Timer timer = new Timer();
        final boolean[] b = {true};
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                b[0] = Turn(timer);
                if (!b[0]) {
                    timer.cancel();
                    csc.receiveEvent();
                    gamePage.mainpanel.HomePage(csc);
                }
            }
        }, 0, 1000);
    }

    public boolean Turn(Timer timer) {
        csc.sendEvent("Board");
        String s = csc.receiveEvent();
        if (s != null) {
            if (!s.equals("EndGame")) {
                Decode(s);
                gamePage.revalidate();
                gamePage.Paint(gamePage.gamePaint);
                return true;
            }
        }
        return false;
    }


    private void Decode(String s) {
        if (!s.equals("")) {
            String[] A = s.split("]");
            myLabel.Decode(A[0]);
            rivalLabel.Decode(A[1]);
            myMap.Decode(A[2]);
            rivalMap.Decode(A[3]);
        }
    }

    @Override
    public String toString() {
        return "CentralToToolbar4{" +
                "gamePage=" + gamePage +
                ", csc=" + csc +
                ", myMap=" + myMap +
                ", rivalMap=" + rivalMap +
                ", myLabel=" + myLabel +
                ", rivalLabel=" + rivalLabel +
                ", endGame=" + endGame +
                '}';
    }
}
