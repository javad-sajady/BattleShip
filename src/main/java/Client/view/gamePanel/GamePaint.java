package Client.view.gamePanel;

import Client.ClientSideConnection;
import Client.listtener.StringListener;
import Client.view.MainPanel;
import Client.view.ViewConstant;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GamePaint extends JPanel implements StringListener {
    public ClientSideConnection csc;
    public MainPanel mainPanel;
    public GameBoard myMap;
    public GameBoard rivalMap;
    public GameLabel myLabel;
    public GameLabel rivalLabel;
    public CentralToToolbar4 centralToToolbar4;

    public GamePaint(ClientSideConnection csc, MainPanel mainPanel, CentralToToolbar4 centralToToolbar4) {
        this.csc = csc;
        this.mainPanel = mainPanel;
        this.setLayout(new GridLayout(2, 2));
        this.centralToToolbar4 = this.centralToToolbar4;
        this.setBackground(Color.ORANGE);

        myLabel = new GameLabel(csc.getAccount().getName());
        rivalLabel = new GameLabel(csc.getRival());

        myMap = new GameBoard(mainPanel, csc);
        myMap.setBlock(ViewConstant.Block / 2);
        myMap.setClick(false);

        rivalMap = new GameBoard(mainPanel, csc);
        rivalMap.setBlock(ViewConstant.Block / 2);
        rivalMap.setClick(true);

        Paint();
    }

    public GamePaint(GamePaint gamePaint) {
        this.setLayout(new GridLayout(2, 2));
        this.setBackground(Color.ORANGE);
        csc=gamePaint.csc;
        mainPanel =gamePaint.mainPanel;
        myMap=gamePaint.myMap;
        rivalMap= gamePaint.rivalMap;;
        myLabel= gamePaint.myLabel;
        rivalLabel= gamePaint.rivalLabel;
        centralToToolbar4 = gamePaint.centralToToolbar4;
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

    @Override
    public void stringEventOccurred(String registration) {

    }
}
