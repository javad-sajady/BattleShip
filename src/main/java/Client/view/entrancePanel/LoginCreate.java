package Client.view.entrancePanel;

import Client.ClientSideConnection;
import Client.listtener.StringListener;
import Client.view.CentralPanel;
import Client.view.MainPanel;

import javax.swing.*;
import java.awt.*;

public class LoginCreate extends JPanel {
    private ToolsBar toolsBar;
    private CentralPanel centralPanel;
    private CentralToToolbar centerToToolbar;
    private ClientSideConnection csc;
    private MainPanel mainPanel;

    public LoginCreate(ToolsBar toolsBar, CentralPanel centerPanel) {
        this.toolsBar = toolsBar;
        this.centralPanel = centerPanel;
    }

    public LoginCreate(ClientSideConnection csc, MainPanel mainPanel) {
        this.csc=csc;
        this.mainPanel =mainPanel;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.ORANGE);
        centralPanel = new CentralPanel(this.mainPanel);
        this.add(centralPanel, BorderLayout.CENTER);

        toolsBar = new ToolsBar();
        this.add(toolsBar, BorderLayout.NORTH);

        centerToToolbar=new CentralToToolbar(centralPanel,csc);
        toolsBar.addListener((StringListener) centerToToolbar );
    }

}
