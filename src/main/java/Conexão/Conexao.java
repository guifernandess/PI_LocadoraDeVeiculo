/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexão;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author guilherme.rocha
 */
public class Conexao {
    private static Connection obterConexao() throws ClassNotFoundException, SQLException {
        // Driver MySql
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/produtobd", "root", "");
        return conn;
    }
}
