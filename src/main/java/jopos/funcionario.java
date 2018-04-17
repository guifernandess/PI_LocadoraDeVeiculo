/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jopos;

import Conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Matheus Maia
 */
public class funcionario extends pessoa{
    
    private String cargoAcesso;
    private String login;
    private String senha;
    
   /* public void verificarLogin (String login, String senha) throws SQLException, Exception {
        Conexao connm = new Conexao();
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        Connection conn = connm.obterConexao();
        try{
                String sql = "SELECT * FROM usuario where user_nome like '"
                + loginTxt.getText()+"' and user_senha like '"+ senha +"'";
                rs= ConexaoBD.getConexao().executeQuery(sql);// exexuta a varialvel sql acima


                   if(rs.first())// conecta e busca o primeiro registro igual e compara se for certo entra senao da erro no CATCH
                   {
                    JOptionPane.showMessageDialog(null,"Login Efetuado com Sucesso!");
                     controla = rs.getInt("TIPO_ACESSO"); 
    
                   }}}*/
}
