package Client.view.homePanel;

import Client.ClientSideConnection;
import Client.logic.Game;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StreamView extends JPanel implements ActionListener {
    public List<Game> gameList;
    public HashMap<JButton, Integer> gameBut;
    public CentralToToolbar2 centralToToolbar2;
    public ClientSideConnection csc;

    public StreamView(CentralToToolbar2 centralToToolbar2, ClientSideConnection csc) {
        this.csc = csc;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.orange);
        this.centralToToolbar2 = centralToToolbar2;
        paint();
    }

    public StreamView(StreamView streamView) {
        this.setBackground(Color.orange);
        this.centralToToolbar2 = streamView.getCentralToToolbar2();
        this.gameBut=streamView.gameBut;
        this.csc=streamView.csc;
        paint();
    }

    public CentralToToolbar2 getCentralToToolbar2() {
        return centralToToolbar2;
    }


    private void paint() {
        Border innerBorder = BorderFactory.createTitledBorder("Stream");
        Border outerBoarder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        this.setBorder(BorderFactory.createCompoundBorder(outerBoarder, innerBorder));

        gameList = new LinkedList<>();
        csc.sendEvent("Stream");
        String s = csc.receiveEvent();
        if (!s.equals("")) {
            String[] A = s.split("\\[");
            JPanel panel = new JPanel();
            gameBut = new HashMap<>();
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            for (String a : A) {
                String[] B = a.split("\\*");
                JButton button = new JButton();
                button.setText(B[0]);
                gameBut.put(button, Integer.valueOf(B[1]));
                panel.add(button);
                button.addActionListener(this);
            }
            JScrollPane scrollbar = new JScrollPane(panel);
            this.add(scrollbar, BorderLayout.CENTER);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button : gameBut.keySet()) {
            if ((JButton) e.getSource() == button) {
                int a = gameBut.get(button);
                centralToToolbar2.TimerBrake();
                centralToToolbar2.getCentralPanel().mainPanel.GamePage2(csc);
                centralToToolbar2.getCentralPanel().mainPanel.GameBegin2(a);
            }
        }
    }
}