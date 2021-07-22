package Client.view.homePanel;

import Client.listtener.StringListener;
import Client.view.ViewConstant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ToolsBar2 extends JPanel implements ActionListener {
    private JButton ExitBtn;
    private JButton HighScoreBtn;
    private JButton PersonalBtn;
    private JButton StreamBtn;
    private LinkedList<StringListener> stringListeners ;

    public ToolsBar2() {
        stringListeners=new LinkedList<>();
        this.setBackground(Color.GRAY);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        ExitBtn = new JButton("Exit");
        ExitBtn.setPreferredSize(new Dimension(ViewConstant.HEIGHT_KEY, ViewConstant.WIDTH_KEY));
        this.add(ExitBtn);
        ExitBtn.addActionListener(this);

        HighScoreBtn = new JButton("High Score");
        HighScoreBtn.setPreferredSize(new Dimension(ViewConstant.HEIGHT_KEY, ViewConstant.WIDTH_KEY));
        this.add(HighScoreBtn);
        HighScoreBtn.addActionListener(this);

        PersonalBtn = new JButton("Personal/newGame");
        PersonalBtn.setPreferredSize(new Dimension(ViewConstant.HEIGHT_KEY, ViewConstant.WIDTH_KEY));
        this.add(PersonalBtn);
        PersonalBtn.addActionListener(this);

        StreamBtn = new JButton("Stream");
        StreamBtn.setPreferredSize(new Dimension(ViewConstant.HEIGHT_KEY, ViewConstant.WIDTH_KEY));
        this.add(StreamBtn);
        StreamBtn.addActionListener(this);
    }

    public void addListener(StringListener stringListener) {
        stringListeners.add(stringListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (StringListener stringListener : stringListeners) {
            if (ExitBtn == (JButton)e.getSource()){
                stringListener.stringEventOccurred("exit");
            }
            if (PersonalBtn == (JButton)e.getSource()) {
                stringListener.stringEventOccurred("personal");
            }
            if (HighScoreBtn == (JButton)e.getSource()) {
                stringListener.stringEventOccurred("highScore");
            }
            if (StreamBtn == (JButton)e.getSource()) {
                stringListener.stringEventOccurred("stream");
            }
        }
    }
}
