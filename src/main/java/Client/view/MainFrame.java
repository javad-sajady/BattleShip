package Client.view;

import Client.ClientSideConnection;

import javax.swing.*;

public class MainFrame extends JFrame {
    private  ClientSideConnection csc;
    private MainPanel mainPanel;

    public MainFrame(ClientSideConnection csc) {
        super(ViewConstant.NAME);
        mainPanel=new MainPanel(csc,this);
        this.setLayout(null);
        this.setSize(ViewConstant.HEIGHT, ViewConstant.WIDTH);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.setVisible(true);
    }

    @Override
    public void repaint() {
        this.revalidate();
        super.repaint();
    }

}
