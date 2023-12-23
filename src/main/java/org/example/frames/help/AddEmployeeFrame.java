package org.example.frames.help;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.frames.functionalAdmin.AdminAreaFrame;
import org.example.frames.functionalClient.ClientAreaFrame;
import org.example.models.ProductTableModel.ProductTableData;
import org.example.models.client.Client;
import org.example.models.client.ClientService;
import org.example.models.employee.Employee;
import org.example.models.employee.EmployeeService;
import org.example.models.employeeTableData.EmployeeTableData;
import org.example.models.product.Product;
import org.example.models.product.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEmployeeFrame extends JFrame {
    private final JLabel labelName;
    private final JLabel labelSalary;
    private final JTextField textFieldName;
    private final JTextField textFieldSalary;
    private Employee[] employees;
    private String currentCell;
    private EmployeeService employeeService;
    private JButton buttonAdd;
    private String name;
    private String password;

    public AddEmployeeFrame(){
        this.labelName = new JLabel("Имя: ");
        this.labelSalary = new JLabel("Зарплата: ");
        this.textFieldName = new JTextField();
        this.textFieldSalary = new JTextField();
        this.buttonAdd = new JButton("Добавить");
        this.employeeService = new EmployeeService();

        init();
    }

    public void init(){
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int salary = Integer.parseInt(textFieldSalary.getText());
                    employeeService.addEmployee(new Employee(textFieldName.getText(),salary));
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
                new AdminAreaFrame().setVisible(true);
            }
        });

        JPanel panelName = new JPanel(new GridLayout(1,2));
        panelName.add(labelName);
        panelName.add(textFieldName);

        JPanel panelSalary = new JPanel(new GridLayout(1,2));
        panelSalary.add(labelSalary);
        panelSalary.add(textFieldSalary);


        add(panelName, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
        add(panelSalary, new GridBagConstraints(0,1,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
        add(buttonAdd, new GridBagConstraints(0,2,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));

        pack();
    }
}
