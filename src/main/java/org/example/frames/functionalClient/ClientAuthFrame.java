package org.example.frames.functionalClient;

import org.example.models.client.ClientService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class ClientAuthFrame extends JFrame {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton buttonAuth;
    private JButton buttonRegister;

    public ClientAuthFrame() throws HeadlessException {
        this.nameField = new JTextField();
        this.passwordField = new JPasswordField();
        this.buttonAuth = new JButton("Войти");
        this.buttonRegister = new JButton("Регистрация");
        init();
    }

    public void init(){
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buttonAuth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientService clientService = new ClientService(nameField.getText(), passwordField.getText());
                try {
                    boolean isAuth = clientService.getResultAuth();
                    if(isAuth){
                        new ClientAreaFrame(nameField.getText(), passwordField.getText()).setVisible(true);
                        dispose();
                    }
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientRegistrationFrame().setVisible(true);
            }
        });



        JPanel jLabelPanel = new JPanel();
        jLabelPanel.setLayout(new GridLayout(2, 1));
        jLabelPanel.add(nameField);
        jLabelPanel.add(passwordField);

        add(jLabelPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        buttonPanel.add(buttonAuth);
        buttonPanel.add(buttonRegister);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }


}
