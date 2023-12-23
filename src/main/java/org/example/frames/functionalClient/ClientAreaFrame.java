package org.example.frames.functionalClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.frames.help.AddProductFrame;
import org.example.models.ProductTableModel.ProductTableData;
import org.example.models.client.Client;
import org.example.models.client.ClientService;
import org.example.models.product.Product;
import org.example.models.product.ProductService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientAreaFrame extends JFrame{
    private final JTable ordersTable;
    private final ProductTableData productTableModel;
    private final JScrollPane tableScrollPane;
    private final ProductService productService;
    private final ClientService clientService;
    private final JButton buttonAddOrder;
    private final JButton buttonDeleteOrder;
    private String name;
    private String password;
    private String currentCell;

    public ClientAreaFrame(String name, String password){
        this.clientService = new ClientService(name, password);
        this.productTableModel = new ProductTableData();
        this.ordersTable = new JTable(productTableModel);
        this.tableScrollPane = new JScrollPane(ordersTable);
        this.productService = new ProductService();
        this.buttonAddOrder = new JButton("Добавить");
        this.buttonDeleteOrder = new JButton("Удалить");
        this.name = name;
        this.password = password;
        init();
    }
    public void init(){
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        Client client = clientService.getClient(name);
        Product[] productArray = productService.getAllProductsByClient(client.getClient_id());
        List<Product> productList = new ArrayList<>(Arrays.stream(productArray).toList());

        productTableModel.addDate(productList);

        ordersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Получаем выбранное значение ячейки
                int selectedRow = ordersTable.getSelectedRow();
                //int selectedColumn = ordersTable.getSelectedColumn();
                try {
                    currentCell = ordersTable.getValueAt(selectedRow, 0).toString();
                }catch (Exception exc){
                    System.out.println(exc.getMessage());
                }
            }
        });
        buttonDeleteOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Product product : productTableModel.getDate()){
                    if(product.getName().equals(currentCell)){
                        try {
                            productService.deleteProduct(client.getClient_id(), product.getProduct_id());
                        } catch (JsonProcessingException ex) {
                            throw new RuntimeException(ex);
                        }
                        dispose();
                        new ClientAreaFrame(name, password).setVisible(true);
                    }
                }
            }
        });
        buttonAddOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddProductFrame(name,password).setVisible(true);
                dispose();
            }
        });


        JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
        buttonsPanel.add(buttonAddOrder);
        buttonsPanel.add(buttonDeleteOrder);



        add(tableScrollPane, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
        add(buttonsPanel, new GridBagConstraints(0,1,1,0,1,0,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
    }
}
