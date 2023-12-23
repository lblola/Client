package org.example.frames;

import org.example.frames.functionalAdmin.AdminAuthFrame;
import org.example.frames.functionalClient.ClientAuthFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JButton buttonClientAuth;
    private JButton buttonAdminAuth;
    private JLabel text;

    public MainFrame(){
        this.buttonClientAuth = new JButton("Клиент");
        this.buttonAdminAuth = new JButton("Админ");
        this.text = new JLabel("Авторизоваться как: ");

        init();

    }

    public void init(){
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        buttonClientAuth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientAuthFrame().setVisible(true);
                dispose();
            }
        });

        buttonAdminAuth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminAuthFrame().setVisible(true);
                dispose();
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(buttonClientAuth);
        buttonPanel.add(buttonAdminAuth);

        add(text, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }
}
