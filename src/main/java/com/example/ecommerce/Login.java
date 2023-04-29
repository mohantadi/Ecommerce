package com.example.ecommerce;

import java.sql.ResultSet;

public class Login {

    public static Customer customerLogin(String userName, String password) {
        String loginQuery=" select *from customer where name='"+userName+"' and password='"+password+"'";

        dbconnection conn = new dbconnection();
        ResultSet rs = conn.getQueryTable(loginQuery);
        try {
            if(rs.next()){
                return  new Customer(rs.getInt("id"),rs.getString("name"),
                        rs.getString("email"),rs.getString("mobile"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args){
        Login login=new Login();
        Customer customer=
                login.customerLogin("mohan","8919") ;
        System.out.println("Welcome:"+customer.getName());
    }
}