package org.example.models.employeeTableData;

import org.example.models.employee.Employee;
import org.example.models.product.Product;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTableData extends AbstractTableModel {
    private final int countColumn = 2;
    private final List<Employee> dataArrayList = new ArrayList<>();

    public EmployeeTableData(){
    }

    @Override
    public int getRowCount() {
        return dataArrayList.size();
    }

    @Override
    public int getColumnCount() {
        return countColumn;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee row = dataArrayList.get(rowIndex);
        if(columnIndex == 0){
            return row.getName();
        }else{
            return row.getSalary();
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        String columName = "";
        switch (columnIndex){
            case 0 -> columName = "Имя";
            case 1 -> columName = "Зарплата";
        }
        return columName;
    }

    public void addDate(List<Employee> rows){
        dataArrayList.addAll(rows);
    }

    public List<Employee> getDate(){
        return dataArrayList;
    }
}
