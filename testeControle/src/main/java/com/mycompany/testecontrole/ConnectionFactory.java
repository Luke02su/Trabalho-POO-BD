package com.mycompany.testecontrole;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    public Connection getConnection() {
    
    try {
        return  DriverManager.getConnection("jdbc:mysql://localhost:3306/controle_equipamentos_ti", "auxiliar01_ti", "Nac@2000"); 
    } catch (SQLException e) {
        throw new RuntimeException (e);
        }
    }
}
