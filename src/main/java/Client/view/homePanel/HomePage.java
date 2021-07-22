package Client.view.homePanel;

import Client.ClientSideConnection;
import Client.listtener.StringListener;
import Client.view.CentralPanel;
import Client.view.MainPanel;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {
    private ToolsBar2 toolsBar;
    private CentralPanel centralPanel;
    private CentralToToolbar2 centralToToolbar2;
    private ClientSideConnection csc;
    private MainPanel mainPanel;


    public HomePage(ClientSideConnection csc, MainPanel mainPanel) {
        this.csc=csc;
        this.mainPanel =mainPanel;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.ORANGE);
        centralPanel = new CentralPanel(mainPanel);
        this.add(centralPanel, BorderLayout.CENTER);

        toolsBar = new ToolsBar2();
        this.add(toolsBar, BorderLayout.NORTH);

        centralToToolbar2 =new CentralToToolbar2(centralPanel,csc);
        toolsBar.addListener((StringListener) centralToToolbar2);
    }
}
