package Client.view.entrancePanel;

import Client.ClientSideConnection;
import Client.listtener.loginCreate.RegistrationFormListener;
import Client.view.event.RegistrationFormEvent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RegistrationView extends JPanel implements ActionListener {
    private JTextField nameField = new JTextField(10);
    private JTextField emailField = new JTextField(10);
    private JTextField password1Field = new JTextField(10);
    private JTextField password2Field = new JTextField(10);
    private JButton registerBtn = new JButton("register");
    public boolean nameBool;
    public boolean passBool;
    public CentralToToolbar centralToToolbar;
    public ClientSideConnection csc;

    public RegistrationView(CentralToToolbar centralToToolbar,ClientSideConnection csc) {
        this.csc=csc;
        this.setBackground(Color.orange);
        this.centralToToolbar = centralToToolbar;
        this.setLayout(new GridBagLayout());
        print();
        registerBtn.addActionListener(this);
    }

    public RegistrationView(RegistrationView registrationView) {
        this.centralToToolbar = registrationView.getCentralToToolbar();
        this.nameField.setText(registrationView.getNameField());
        this.csc=registrationView.getCsc();
        this.password1Field.setText(registrationView.getPassword1Field());
        this.password2Field.setText(registrationView.getPassword2Field());
        this.nameBool = registrationView.isNameBool();
        this.passBool = registrationView.isPassBool();
        this.setBackground(Color.orange);
        this.setLayout(new GridBagLayout());
        print();
        registerBtn.addActionListener(this);
    }

    public void print() {
        Border innerBorder = BorderFactory.createTitledBorder("Registration form");
        Border outerBoarder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        this.setBorder(BorderFactory.createCompoundBorder(outerBoarder, innerBorder));

        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;
        gc.weighty = 0.1;

        /////////////// 1
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(0, 0, 0, 5);
        gc.anchor = GridBagConstraints.LINE_END;
        this.add(new JLabel("name: "), gc);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        this.add(nameField, gc);

        if (nameBool) {
            gc.gridx = 1;
            gc.gridy = 1;
            gc.insets = new Insets(0, 0, 0, 5);
            gc.anchor = GridBagConstraints.LINE_END;
            JLabel label = new JLabel("existed name account");
            label.setBackground(Color.red);
            this.add(label, gc);
        }

        /////////////// 2
        gc.gridx = 0;
        gc.gridy = 2;
        gc.insets = new Insets(0, 0, 0, 5);
        gc.anchor = GridBagConstraints.LINE_END;
        this.add(new JLabel("password:"), gc);

        gc.gridx = 1;
        gc.gridy = 2;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        this.add(password1Field, gc);

        /////////////// 3
        gc.gridx = 0;
        gc.gridy = 3;
        gc.insets = new Insets(0, 0, 0, 5);
        gc.anchor = GridBagConstraints.LINE_END;
        this.add(new JLabel("password: "), gc);

        gc.gridx = 1;
        gc.gridy = 3;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        this.add(password2Field, gc);

        if (passBool) {
            gc.gridx = 1;
            gc.gridy = 4;
            gc.insets = new Insets(0, 0, 0, 3);
            gc.anchor = GridBagConstraints.LINE_END;
            JLabel label = new JLabel("passwords are not the same");
            label.setBackground(Color.red);
            this.add(label, gc);
        }

        /////////////// 4
        gc.weightx = 1;
        gc.weighty = 2;

        gc.gridx = 1;
        gc.gridy = 5;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(registerBtn, gc);
    }

    public CentralToToolbar getCentralToToolbar() {
        return centralToToolbar;
    }

    public String getNameField() {
        return nameField.getText();
    }

    public String getPassword1Field() {
        return password1Field.getText();
    }

    public String getPassword2Field() {
        return password2Field.getText();
    }

    public boolean isNameBool() {
        return nameBool;
    }

    public boolean isPassBool() {
        return passBool;
    }

    public ClientSideConnection getCsc() {
        return csc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (registerBtn == (JButton) e.getSource()) {
            RegistrationFormEvent registrationFormEvent =
                    new RegistrationFormEvent(this,
                            getNameField(),
                            getPassword1Field(),
                            getPassword2Field());

            try {
                RegistrationFormListener registrationFormListener = new RegistrationFormListener(csc);
                registrationFormListener.eventOccurred(registrationFormEvent);
                nameBool = !registrationFormListener.isNameBoolean();
                passBool = !registrationFormListener.isPassBoolean();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            centralToToolbar.regPaint(this);
        }
    }

}
