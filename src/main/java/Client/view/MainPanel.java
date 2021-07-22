package Client.view;

import Client.ClientSideConnection;
import javax.swing.*;
import java.awt.*;

import Client.view.entrancePanel.LoginCreate;
import Client.view.gamePanel.GamePage;
import Client.view.homePanel.HomePage;
import Client.view.shufflePanel.ShufflePage;
import Client.view.streamPanel.GamePage2;

public class MainPanel extends JPanel {
    public ClientSideConnection csc;
    public MainFrame mainFrame;
    public HomePage home;
    public ShufflePage shuffle;
    public GamePage game;
    public GamePage2 game2;


    public MainPanel(ClientSideConnection csc,MainFrame mainFrame) {
        this.mainFrame=mainFrame;
        this.csc=csc;
        this.setSize(ViewConstant.HEIGHT, ViewConstant.WIDTH);
        this.setBackground(Color.ORANGE);
        this.setLayout(new BorderLayout());
        this.add(new LoginCreate(csc,this));

    }

    public void HomePage(ClientSideConnection csc){
        home=new HomePage(csc,this);
        this.setVisible(false);
        this.removeAll();
        this.add(home);
        this.setVisible(true);
    }

    public void Shuffle(ClientSideConnection csc){
        shuffle =new ShufflePage(csc,this);
        this.setVisible(false);
        this.removeAll();
        this.add(shuffle);
        this.setVisible(true);
    }

    public void GamePage(ClientSideConnection csc) {
        game =new GamePage(csc,this);
        this.setVisible(false);
        this.removeAll();
        this.add(game);
        this.setVisible(true);
    }

    public void GameBegin(){
        game.centralToToolbar4.Begin();
    }

    public void GamePage2(ClientSideConnection csc) {
        game2 =new GamePage2(csc,this);
        this.setVisible(false);
        this.removeAll();
        this.add(game2);
        this.setVisible(true);
    }

    public void GameBegin2(int a) {
        game2.centralToToolbar5.Begin(a);
    }
}
