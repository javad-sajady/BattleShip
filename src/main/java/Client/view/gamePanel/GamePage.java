package Client.view.gamePanel;

import Client.ClientSideConnection;
import Client.listtener.StringListener;
import Client.view.MainPanel;

import javax.swing.*;
import java.awt.*;

public class GamePage extends JPanel {
    public ClientSideConnection csc;
    public MainPanel mainpanel;
    //    public GamePaint gamePaint;
    public GamePaint gamePaint;
//    public CentralToToolbar4 centralToToolbar4;
    public CentralToToolbar4 centralToToolbar4;

    public GamePage(ClientSideConnection csc, MainPanel mainPanel) {
        this.csc = csc;
        this.mainpanel = mainPanel;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.ORANGE);
        this.setLayout(new BorderLayout());

        centralToToolbar4 =new CentralToToolbar4(this,csc);
        gamePaint = new GamePaint(csc, mainPanel, centralToToolbar4);
        centralToToolbar4.setInf(gamePaint);
        gamePaint.rivalMap.addListener((StringListener) centralToToolbar4);
        this.add(gamePaint);
    }

    public void Paint(GamePaint gamePaint) {
        this.setVisible(false);
        this.removeAll();
        this.add(new GamePaint(gamePaint));
        this.revalidate();
        this.setVisible(true);
    }


}
