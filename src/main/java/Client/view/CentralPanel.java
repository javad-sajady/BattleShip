package Client.view;

import Client.view.MainPanel;

import javax.swing.*;
import java.awt.*;

public class CentralPanel extends JPanel {
    public MainPanel mainPanel;

    public CentralPanel(MainPanel mainPanel) {
        this.mainPanel =mainPanel;
        this.setBackground(Color.orange);
        this.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
    }

    public CentralPanel() {
    }
}
