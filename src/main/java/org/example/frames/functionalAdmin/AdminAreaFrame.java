package org.example.frames.functionalAdmin;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.frames.functionalClient.ClientAreaFrame;
import org.example.frames.help.AddEmployeeFrame;
import org.example.frames.help.PatchEmployeeFrame;
import org.example.models.employee.Employee;
import org.example.models.employee.EmployeeService;
import org.example.models.employeeTableData.EmployeeTableData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminAreaFrame extends JFrame {
    private final JTable employeesTable;
    private final EmployeeTableData employeeTableModel;
    private final JScrollPane tableScrollPane;
    private final EmployeeService employeeService;
    private final JButton buttonAddEmployee;
    private final JButton buttonDeleteEmployee;
    private final JButton buttonPatchEmployee;
    private String currentCell;

    public AdminAreaFrame(){
        this.employeeTableModel = new EmployeeTableData();
        this.employeesTable = new JTable(employeeTableModel);
        this.tableScrollPane = new JScrollPane(employeesTable);
        this.employeeService = new EmployeeService();
        this.buttonAddEmployee = new JButton("Добавить");
        this.buttonDeleteEmployee = new JButton("Удалить");
        this.buttonPatchEmployee = new JButton("Редактировать");

        init();
    }

    public void init(){
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        Employee[] employeeArray = employeeService.getAllEmployees();
        List<Employee> employeeList = new ArrayList<>(Arrays.stream(employeeArray).toList());

        employeeTableModel.addDate(employeeList);

        employeesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Получаем выбранное значение ячейки
                int selectedRow = employeesTable.getSelectedRow();
                try {
                    currentCell = employeesTable.getValueAt(selectedRow, 0).toString();
                }catch (Exception exc){
                    System.out.println(exc.getMessage());
                }
            }
        });

        JPanel buttonsPanel = new JPanel(new GridLayout(1,3));
        buttonsPanel.add(buttonAddEmployee);
        buttonsPanel.add(buttonPatchEmployee);
        buttonsPanel.add(buttonDeleteEmployee);

        buttonAddEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEmployeeFrame().setVisible(true);
                dispose();
            }
        });
        buttonDeleteEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(currentCell);
                for(Employee employee : employeeTableModel.getDate()){
                    if(employee.getName().equals(currentCell)){
                        try {
                            employeeService.deleteEmployee(employee.getName());
                        } catch (JsonProcessingException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                dispose();
                new AdminAreaFrame().setVisible(true);
            }
        });
        buttonPatchEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Employee employee : employeeTableModel.getDate()){
                    if(employee.getName().equals(currentCell)){
                        new PatchEmployeeFrame(employee).setVisible(true);
                    }
                }
                dispose();
            }
        });
        add(tableScrollPane, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
        add(buttonsPanel, new GridBagConstraints(0,1,1,0,1,0,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
    }
}
