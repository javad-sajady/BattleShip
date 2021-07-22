package Client.view.streamPanel;

import Client.ClientSideConnection;
import Client.listtener.StringListener;
import Client.view.MainPanel;
import Client.view.gamePanel.CentralToToolbar4;
import Client.view.gamePanel.GamePaint;

import javax.swing.*;
import java.awt.*;

public class GamePage2 extends JPanel {
    public ClientSideConnection csc;
    public MainPanel mainpanel;
    //    public GamePaint gamePaint;
    public GamePaint2 gamePaint;
    //    public CentralToToolbar4 centralToToolbar4;
    public CentralToToolbar5 centralToToolbar5;

    public GamePage2(ClientSideConnection csc, MainPanel mainPanel) {
        this.csc = csc;
        this.mainpanel = mainPanel;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.ORANGE);
        this.setLayout(new BorderLayout());

        centralToToolbar5 =new CentralToToolbar5(this,csc);
        gamePaint = new GamePaint2(csc, mainPanel, centralToToolbar5);
        centralToToolbar5.setInf(gamePaint);
        this.add(gamePaint);
    }

    public void Paint(GamePaint2 gamePaint) {
        this.setVisible(false);
        this.removeAll();
        this.add(new GamePaint2(gamePaint));
        this.revalidate();
        this.setVisible(true);
    }


}
