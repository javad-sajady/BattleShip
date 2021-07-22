package Client.view.streamPanel;

import Client.ClientSideConnection;
import Client.listtener.StringListener;
import Client.view.MainPanel;
import Client.view.ViewConstant;
import Client.view.gamePanel.CentralToToolbar4;
import Client.view.gamePanel.GameBoard;
import Client.view.gamePanel.GameLabel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GamePaint2 extends JPanel {
    public ClientSideConnection csc;
    public MainPanel mainPanel;
    public GameBoard2 myMap;
    public GameBoard2 rivalMap;
    public GameLabel myLabel;
    public GameLabel rivalLabel;
    public CentralToToolbar5 centralToToolbar5;

    public GamePaint2(ClientSideConnection csc, MainPanel mainPanel, CentralToToolbar5 centralToToolbar5) {
        this.csc = csc;
        this.mainPanel = mainPanel;
        this.setLayout(new GridLayout(2, 2));
        this.centralToToolbar5 = centralToToolbar5;
        this.setBackground(Color.ORANGE);


        myLabel = new GameLabel(csc.getAccount().getName());
        rivalLabel = new GameLabel(csc.getRival());

        myMap = new GameBoard2(mainPanel, csc);
        myMap.setBlock(ViewConstant.Block / 2);

        rivalMap = new GameBoard2(mainPanel, csc);
        rivalMap.setBlock(ViewConstant.Block / 2);

        Paint();
    }

    public GamePaint2(GamePaint2 gamePaint) {
        this.setLayout(new GridLayout(2, 2));
        this.setBackground(Color.ORANGE);
        csc=gamePaint.csc;
        mainPanel =gamePaint.mainPanel;
        myMap=gamePaint.myMap;
        rivalMap= gamePaint.rivalMap;;
        myLabel= gamePaint.myLabel;
        rivalLabel= gamePaint.rivalLabel;
        centralToToolbar5 = gamePaint.centralToToolbar5;
        Paint();
    }

    protected void Paint() {
        Border innerBorder = BorderFactory.createTitledBorder("Personal information and new game");
        Border outerBoarder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        this.setBorder(BorderFactory.createCompoundBorder(outerBoarder, innerBorder));

        this.add(myLabel);
        this.add(rivalLabel);
        this.add(myMap);
        this.add(rivalMap);
    }
}
