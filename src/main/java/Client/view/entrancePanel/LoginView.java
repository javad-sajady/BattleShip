package Client.view.entrancePanel;

import Client.ClientSideConnection;
import Client.listtener.loginCreate.LoginFormListener;
import Client.view.event.LoginFormEvent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginView extends JPanel implements ActionListener {
    JTextField usernameField = new JTextField(10);
    JTextField passwordField = new JTextField(10);
    JButton loginBtn = new JButton("login");
    public boolean nameBool;
    public boolean passBool;
    public CentralToToolbar centerToToolbarStringListener;
    public  ClientSideConnection csc;

    public LoginView(CentralToToolbar centerToToolbarStringListener, ClientSideConnection csc) {
        this.csc=csc;
        this.setBackground(Color.orange);
        this.centerToToolbarStringListener=centerToToolbarStringListener;
        paint();
        loginBtn.addActionListener(this);
    }

    public LoginView(LoginView loginView){
        this.centerToToolbarStringListener=loginView.getCenterToToolbarStringListener();
        this.nameBool=loginView.isNameBool();
        this.passBool=loginView.isPassBool();
        this.usernameField.setText(loginView.getNameField());
        this.passwordField.setText(loginView.getPasswordField());
        this.csc=loginView.getCsc();
        this.setBackground(Color.orange);
        paint();
        loginBtn.addActionListener(this);
    }

    public boolean isNameBool() {
        return nameBool;
    }

    public boolean isPassBool() {
        return passBool;
    }

    public CentralToToolbar getCenterToToolbarStringListener() {
        return centerToToolbarStringListener;
    }

    public  void paint(){
        Border innerBorder = BorderFactory.createTitledBorder("login");
        Border outerBoarder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        this.setBorder(BorderFactory.createCompoundBorder(outerBoarder, innerBorder));

        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;
        gc.weighty = 0.1;

        /////////////// 1
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(0, 0, 0, 7);
        gc.anchor = GridBagConstraints.LINE_END;
        this.add(new JLabel("username: "), gc);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.insets = new Insets(0, 0, 0, 7);
        gc.anchor = GridBagConstraints.LINE_START;
        this.add(usernameField, gc);

        if(nameBool){
            gc.gridx = 1;
            gc.gridy = 1;
            gc.insets = new Insets(0, 0, 0, 5);
            gc.anchor = GridBagConstraints.LINE_END;
            JLabel label = new JLabel("no account in this account name");
            label.setBackground(Color.red);
            this.add(label, gc);
        }

        /////////////// 2
        gc.gridx = 0;
        gc.gridy = 2;
        gc.insets = new Insets(0, 0, 0, 7);
        gc.anchor = GridBagConstraints.LINE_END;
        this.add(new JLabel("password: "), gc);

        gc.gridx = 1;
        gc.gridy = 2;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        this.add(passwordField, gc);

        if (passBool) {
            gc.gridx = 1;
            gc.gridy = 3;
            gc.insets = new Insets(0, 0, 0, 3);
            gc.anchor = GridBagConstraints.LINE_END;
            JLabel label = new JLabel("passwords is not correct");
            label.setBackground(Color.red);
            this.add(label, gc);
        }

        /////////////// 3
        gc.weightx = 1;
        gc.weighty = 2;

        gc.gridx = 1;
        gc.gridy = 4;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(loginBtn, gc);
    }

    public String getNameField() {
        return usernameField.getText();
    }

    public String getPasswordField() {
        return passwordField.getText();
    }

    public ClientSideConnection getCsc() {
        return csc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LoginFormEvent loginFormEvent =
                new LoginFormEvent(this,
                        getNameField(),
                        getPasswordField());

        try {
            LoginFormListener  loginFormListener = new LoginFormListener(getCsc());
            loginFormListener.eventOccurred(loginFormEvent);
            nameBool = !loginFormListener.isNameBoolean();
            passBool = !loginFormListener.isPassBoolean();
        } catch (IOException ioe) {
            System.out.println("IOException in action performanve in login view "+ ioe);
        }

        centerToToolbarStringListener.logPaint(this);
    }
}
