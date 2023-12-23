package org.example.frames.functionalClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.models.client.Client;
import org.example.models.client.ClientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientRegistrationFrame extends JFrame {
    private JTextField nameField;
    private JTextField addressField;
    private JPasswordField passwordField;
    private JButton buttonRegister;

    public ClientRegistrationFrame(){
        nameField = new JTextField("Имя");
        addressField = new JTextField("Адрес");
        passwordField = new JPasswordField();
        buttonRegister = new JButton("Регистрация");
        init();
    }

    public void init(){
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String address = addressField.getText();
                String password = passwordField.getText();

                ClientService clientService = new ClientService();
                try {
                    clientService.createClient(new Client(name, address, password));
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });

        JPanel textFieldsPanel = new JPanel();
        textFieldsPanel.setLayout(new GridLayout(3,1));
        textFieldsPanel.add(nameField);
        textFieldsPanel.add(addressField);
        textFieldsPanel.add(passwordField);

        add(textFieldsPanel, BorderLayout.CENTER);
        add(buttonRegister, BorderLayout.SOUTH);
        pack();
    }
}
