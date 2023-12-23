package org.example.models.ProductTableModel;

import org.example.models.product.Product;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductTableData extends AbstractTableModel {
    private final int countColumn = 2;
    private final List<Product> dataArrayList = new ArrayList<>();

    public ProductTableData(){
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
        Product row = dataArrayList.get(rowIndex);
        if(columnIndex == 0){
            return row.getName();
        }else{
            return row.getPrice();
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        String columName = "";
        switch (columnIndex){
            case 0 -> columName = "Наименование";
            case 1 -> columName = "Стоимость";
        }
        return columName;
    }

    /*@Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0; // Позволяет редактирование только ячеек 1 столбца
    }*/
    public void addDate(List<Product> rows){
        dataArrayList.addAll(rows);
    }

    public List<Product> getDate(){
        return dataArrayList;
    }
}
