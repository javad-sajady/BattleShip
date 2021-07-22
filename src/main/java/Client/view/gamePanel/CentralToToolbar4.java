package Client.view.gamePanel;


import Client.ClientSideConnection;
import Client.listtener.StringListener;

import java.util.Timer;
import java.util.TimerTask;

public class CentralToToolbar4 implements StringListener {
    private GamePage gamePage;
    private ClientSideConnection csc;
    public GameBoard myMap;
    public GameBoard rivalMap;
    public GameLabel myLabel;
    public GameLabel rivalLabel;
    boolean endGame;

    public CentralToToolbar4(GamePage gamePage, ClientSideConnection csc) {
        this.gamePage = gamePage;
        this.csc = csc;
    }

    public void setInf(GamePaint gamePaint) {
        this.myLabel = gamePaint.myLabel;
        this.myMap = gamePaint.myMap;
        this.rivalLabel = gamePaint.rivalLabel;
        this.rivalMap = gamePaint.rivalMap;

    }


    public void Begin() {
        csc.sendEvent("Begin");
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
            } else {
                return false;
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
    public void stringEventOccurred(String string) {
        csc.sendEvent("Click[" + string);
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
