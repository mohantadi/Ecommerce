package com.example.ecommerce;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;

public class Order {
    public static boolean placeOrder(Customer customer,Product product){
        String groupOrderId="select max(grouporderid)+1 id from orders";
        dbconnection dbconnection=new dbconnection();
        try{
            ResultSet rs=dbconnection.getQueryTable(groupOrderId);
            if(rs.next()){
                String placeOrder="Insert into orders(grouporderid,customerid,productid) Values("+rs.getInt("id")+","+customer.getId()+" ,"+product.getId()+")";
                return dbconnection.updateDatabase(placeOrder)!=0;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return  false;

    }

    public static int placeMultipleOrder(Customer customer, ObservableList<Product>productList){
        String groupOrderId="select max(grouporderid)+1 id from orders";
        dbconnection dbconnection=new dbconnection();
        try{
            ResultSet rs=dbconnection.getQueryTable(groupOrderId);
            int count=0;
            if(rs.next()){
                for(Product product:productList){
                    UserInterface ui=new UserInterface();
                    ui.itemsInOrder.add(product);
                    String placeOrder="Insert into orders(grouporderid,customerid,productid) Values("+rs.getInt("id")+","+customer.getId()+" ,"+product.getId()+")";
                    count+=dbconnection.updateDatabase(placeOrder);

                }
                return count;  }

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;

    }
    private static TableView<Product> orderTable;
    public static VBox createTable(ObservableList<Product>data)
    {
        //column
        TableColumn id=new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name=new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price=new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        //dummy data
        orderTable =new TableView<>();
        orderTable.setItems(data);
        orderTable.getColumns().addAll(id,name,price);
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox vBox=new VBox();
        // gicve padding in whole vbox;
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(orderTable);
        return vBox;

    }
    public static VBox getOrderProducts(ObservableList<Product>data){
        return createTable(data);
    }


}