package Client.view.shufflePanel;

import Client.ClientSideConnection;
import Client.listtener.StringListener;
import Client.view.MainPanel;
import Client.view.ViewConstant;

import javax.swing.*;
import java.awt.*;

public class ShufflePage extends JPanel {
    private ToolsBar3 toolsBar;
    private ShipPaint centralPanel;
    private CentralToToolbar3 centerToToolbar;
    private ClientSideConnection csc;
    private MainPanel mainPanel;

    public ShufflePage(ClientSideConnection csc, MainPanel mainPanel) {
        this.csc=csc;
        this.mainPanel =mainPanel;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.ORANGE);
        centralPanel = new ShipPaint(this.mainPanel,csc);
        centralPanel.setShip("MakeShuffle");
        centralPanel.setBlock(ViewConstant.Block);
        this.add(centralPanel, BorderLayout.CENTER);

        toolsBar = new ToolsBar3();
        this.add(toolsBar, BorderLayout.NORTH);

        centerToToolbar=new CentralToToolbar3(centralPanel,csc);
        centerToToolbar.setInf(toolsBar,centralPanel);
        toolsBar.addListener((StringListener) centerToToolbar );
    }
}
