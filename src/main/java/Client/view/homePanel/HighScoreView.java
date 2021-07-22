package Client.view.homePanel;

import Client.ClientSideConnection;
import Client.logic.Account;
import Client.view.ViewConstant;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class HighScoreView extends JPanel {
    private JTextField textField = new JTextField(10);
    private JButton newGame = new JButton();
    private List<Account> accountList;
    public CentralToToolbar2 centerToToolbar;
    public ClientSideConnection csc;


    public HighScoreView(CentralToToolbar2 centerToToolbar, ClientSideConnection csc) {
        this.csc = csc;
        this.setBackground(Color.orange);
        this.centerToToolbar = centerToToolbar;
        this.setLayout(new BorderLayout());
        paint();
    }

    public HighScoreView(HighScoreView highScoreView) {
        this.centerToToolbar = highScoreView.getCenterToToolbar();
        this.csc = highScoreView.getCsc();
        this.setBackground(Color.orange);
        paint();
    }

    public ClientSideConnection getCsc() {
        return csc;
    }

    public CentralToToolbar2 getCenterToToolbar() {
        return centerToToolbar;
    }

    public void paint() {
        Border innerBorder = BorderFactory.createTitledBorder("High score");
        Border outerBoarder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        this.setBorder(BorderFactory.createCompoundBorder(outerBoarder, innerBorder));

//      send request for get list:
        csc.sendEvent("HighScore");
        String s=csc.receiveEvent();
        accountList = stringToList(s);

        accountList.sort(new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {
                return -(o1.getWin() - o1.getLost() - o2.getWin() + o2.getLost());
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        for (Account account : accountList) {
            JButton button =new JButton(account.toString());
            button.setSize(new Dimension(ViewConstant.WIDTH_KEY,500));
            if(account.isOnline()){
                button.setBackground(Color.green);
            }else {
                button.setBackground(Color.white);
            }
            panel.add(button);
        }
        JScrollPane scrollbar = new JScrollPane(panel);
        this.add(scrollbar, BorderLayout.CENTER);
    }

    public List<Account> stringToList(String s) {
        List<Account> accounts = new LinkedList<>();
        String[] splited = s.split("]");
        for (String t : splited) {
            String[] A = t.split("\\[");
            Account account = new Account("", "");
            account.SetInf(A[0], "", Integer.valueOf(A[1]), Integer.valueOf(A[2]));
            account.setOnline(Boolean.valueOf(A[3]));
            accounts.add(account);
        }
        return accounts;
    }

}
