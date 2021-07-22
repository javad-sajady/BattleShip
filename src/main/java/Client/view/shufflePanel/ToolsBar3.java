package Client.view.shufflePanel;

import Client.listtener.StringListener;
import Client.view.ViewConstant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ToolsBar3 extends JPanel implements ActionListener {
    private JButton readyBut = new JButton("Ready");
    private JButton shuffleBut = new JButton("Shuffle:3");
    private JLabel timeLabel = new JLabel();
    private Timer timer;
    private int Life;
    private int remainTime;
    private LinkedList<StringListener> stringListeners;

    public ToolsBar3() {
        stringListeners = new LinkedList<>();
        this.setBackground(Color.GRAY);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        Life = 3;
        remainTime = 25;

        shuffleBut.setPreferredSize(new Dimension(ViewConstant.HEIGHT_KEY,ViewConstant.WIDTH_KEY));
        this.add(shuffleBut);
        shuffleBut.addActionListener(this);

        readyBut.setPreferredSize(new Dimension(ViewConstant.HEIGHT_KEY,ViewConstant.WIDTH_KEY));
        this.add(readyBut);
        readyBut.addActionListener(this);

        timeLabel.setText("time :" +30);
        this.add(timeLabel);

        timer = new Timer(1000, this);
        timer.start();
    }

    public void TimeRepaint() {
        this.setVisible(false);
        this.removeAll();
        timeLabel.setText("time :" + remainTime);
        if (Life > 0) {
            this.add(shuffleBut);
        }
        this.add(readyBut);
        this.add(timeLabel);
        this.setVisible(true);
    }

    public void addListener(StringListener stringListener) {
        stringListeners.add(stringListener);
    }

    public void setTimer() {
        remainTime += 10;
        Life -= 1;
        shuffleBut.setText("Shuffle:" + Life);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!String.valueOf(e.getSource().getClass()).equals("class javax.swing.Timer")) {
            for (StringListener stringListener : stringListeners) {
                if ((JButton) e.getSource() == readyBut) {
                    stringListener.stringEventOccurred("ready");
                }
                if ((JButton) e.getSource() == shuffleBut) {
                    stringListener.stringEventOccurred("shuffle");
                }
            }
        } else {
            if ((Timer) e.getSource() == timer) {
                remainTime -= 1;
                TimeRepaint();
            }
            if (remainTime == 0) {
                for (StringListener stringListener : stringListeners) {
                    stringListener.stringEventOccurred("ready");
                }
            }
        }
    }
}