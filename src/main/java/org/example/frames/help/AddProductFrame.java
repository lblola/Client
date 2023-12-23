package org.example.frames.help;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.frames.functionalClient.ClientAreaFrame;
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
import java.util.List;

public class AddProductFrame extends JFrame {
    private final JTable productTable;
    private Product[] products;
    private final ProductTableData productTableModel;
    private final JScrollPane tableScrollPane;
    private String currentCell;
    private ProductService productService;
    private JButton buttonAdd;
    private String name;
    private String password;
    public AddProductFrame(String name, String password){
        this.buttonAdd = new JButton("Добавить");
        this.productService = new ProductService();
        this.productTableModel = new ProductTableData();
        this.productTable = new JTable(productTableModel);
        this.tableScrollPane = new JScrollPane(productTable);
        this.name = name;
        this.password = password;

        init();
    }

    public void init(){
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        products = productService.getAllProducts();
        productTableModel.addDate(List.of(products));

        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Получаем выбранное значение ячейки
                int selectedRow = productTable.getSelectedRow();
                //int selectedColumn = ordersTable.getSelectedColumn();
                try {
                    currentCell = productTable.getValueAt(selectedRow, 0).toString();
                }catch (Exception exc){
                    System.out.println(exc.getMessage());
                }
            }
        });

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientService clientService = new ClientService(name, password);
                Client client = clientService.getClient(name);

                for (Product product : productTableModel.getDate()) {
                    if (product.getName().equals(currentCell)) {
                        try {
                            productService.addProduct(client.getClient_id(), product.getProduct_id());
                            dispose();
                            new ClientAreaFrame(name, password).setVisible(true);
                        } catch (JsonProcessingException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

            }
        });
        JPanel panel = new JPanel(new GridLayout(1,1));
        panel.add(buttonAdd);

        add(tableScrollPane, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
        add(panel, new GridBagConstraints(0,1,1,0,1,0,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
    }
}
