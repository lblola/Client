package org.example.frames.functionalAdmin;

import org.example.frames.functionalClient.ClientAreaFrame;
import org.example.models.admin.AdminService;
import org.example.models.client.ClientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class AdminAuthFrame extends JFrame {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton buttonAuth;

    public AdminAuthFrame() throws HeadlessException {
        this.nameField = new JTextField();
        this.passwordField = new JPasswordField();
        this.buttonAuth = new JButton("Войти");
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
                AdminService adminService = new AdminService(nameField.getText(), passwordField.getText());
                try {
                    boolean isAuth = adminService.getResultAuth();
                    if(isAuth){
                        new AdminAreaFrame().setVisible(true);
                        dispose();
                    }
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JPanel jLabelPanel = new JPanel();
        jLabelPanel.setLayout(new GridLayout(2, 1));
        jLabelPanel.add(nameField);
        jLabelPanel.add(passwordField);

        add(jLabelPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,1));
        buttonPanel.add(buttonAuth);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }
}
