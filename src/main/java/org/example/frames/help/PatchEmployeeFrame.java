package org.example.frames.help;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.frames.functionalAdmin.AdminAreaFrame;
import org.example.models.employee.Employee;
import org.example.models.employee.EmployeeService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatchEmployeeFrame extends JFrame {
    private final JTextField textFieldName;
    private final JTextField textFieldSalary;
    private final JButton buttonPatch;
    private final EmployeeService employeeService;
    private Employee employee;

    public PatchEmployeeFrame(Employee employee){
        this.textFieldName = new JTextField(employee.getName());
        this.textFieldSalary = new JTextField(String.valueOf(employee.getSalary()));
        this.buttonPatch = new JButton("Редактировать");
        this.employeeService = new EmployeeService();
        this.employee = employee;

        init();
    }

    public void init(){
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        buttonPatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int salary = Integer.parseInt(textFieldSalary.getText());
                    employeeService.patchEmployee(employee.getEmployee_id(), new Employee(textFieldName.getText(),salary));
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
                new AdminAreaFrame().setVisible(true);
            }
        });

        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.add(textFieldName);
        panel.add(textFieldSalary);


        add(panel, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
        add(buttonPatch, new GridBagConstraints(0,2,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));

        pack();
    }
}
