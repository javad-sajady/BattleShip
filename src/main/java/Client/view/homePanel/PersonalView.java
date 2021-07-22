package Client.view.homePanel;

import Client.ClientSideConnection;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonalView extends JPanel implements ActionListener {
    public JButton findBut = new JButton("New Game");
    public JLabel label=new JLabel();
    public CentralToToolbar2 centralToToolbar;
    public ClientSideConnection csc;
    public  boolean find;

    public PersonalView(CentralToToolbar2 centralToToolbar2, ClientSideConnection csc) {
        this.csc=csc;
        this.setBackground(Color.orange);
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.centralToToolbar =centralToToolbar2;
        paint();
    }

    public PersonalView(PersonalView personalView) {
        this.setBackground(Color.orange);
        this.csc= personalView.getCsc();
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.centralToToolbar =personalView.getCentralToToolbar();
        paint();
    }

    public ClientSideConnection getCsc() {
        return csc;
    }

    public CentralToToolbar2 getCentralToToolbar() {
        return centralToToolbar;
    }

    public void  paint(){
        Border innerBorder = BorderFactory.createTitledBorder("Personal information and new game");
        Border outerBoarder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        this.setBorder(BorderFactory.createCompoundBorder(outerBoarder, innerBorder));
        label.setText(csc.getAccount().toString());
        this.add(label);
        if(!find) {
            this.add(findBut);
            findBut.addActionListener(this);
        }else {
            JLabel label1=new JLabel();
            label1.setText("we start to find");
            this.add(label1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if((JButton) e.getSource() ==findBut){
            csc.sendEvent("Game");
            find=true;
            centralToToolbar.PersonalPaint(this);
            String s=csc.receiveEvent();
            if(s.equals("Wait")){
                s=csc.receiveEvent();
            }
            csc.setRival(s);
            centralToToolbar.getCentralPanel().mainPanel.Shuffle(csc);
        }
    }
}
