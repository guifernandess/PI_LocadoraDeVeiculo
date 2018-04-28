package piii_pog;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class Crud_Cliente {

    private static Connection obterConexao() throws ClassNotFoundException, SQLException {
        // Driver MySql
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/produtobd", "root", "");
        return conn;
    }

    public void adicionar(Cliente c) throws ClassNotFoundException, SQLException {
        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO CLIENTE (nome, CPF, DtNascimento)"
                        + "VALUES (?,?,?) ")) {
            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getCPF());
            stmt.setDate(3, (Date) c.getDtNascimento());

            int status = stmt.executeUpdate();
            System.out.println("Status: " + status);

            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void atualizar(Cliente c) throws ClassNotFoundException, SQLException {
        try {
            Connection conn = obterConexao();
            PreparedStatement stmt = conn.prepareStatement("UPDATE CLIENTE SET "
                    + "nome = ?, CPF = ?, DtNascimento = ?"
                    + " WHERE ID = ? ");
            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getCPF());
            stmt.setDate(3, (Date) c.getDtNascimento());
            stmt.setLong(6, c.getId());
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void excluir(int id) throws ClassNotFoundException, SQLException {
        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(
                        "DELETE FROM CLIENTE WHERE id = ? ")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public List<Cliente> procurar(String valor) throws SQLException, Exception {

        String sql = "SELECT * FROM PRODUTOBD.PRODUTO WHERE UPPER(nome) LIKE UPPER(?) ";
        //Lista de clientes de resultado
        List<Cliente> listaClientes = null;
        //Conexão para abertura e fechamento
        //Statement para obtenção através da conexão, execução de
        //comandos SQL e fechamentos
        PreparedStatement preparedStatement = null;
        //Armazenará os resultados do banco de dados
        ResultSet result = null;
        Connection conn = obterConexao();
        try {
            //Abre uma conexão com o banco de dados

            //Cria um statement para execução de instruções SQL
            preparedStatement = conn.prepareStatement(sql);
            //Configura os parâmetros do "PreparedStatement"
            preparedStatement.setString(1, "%" + valor + "%");

            //Executa a consulta SQL no banco de dados
            result = preparedStatement.executeQuery();

            //Itera por cada item do resultado
            while (result.next()) {
                //Se a lista não foi inicializada, a inicializa
                if (listaClientes == null) {
                    listaClientes = new ArrayList<Cliente>();
                }
                //Cria uma instância de Cliente e popula com os valores do BD
                Cliente p = new Cliente();

                p.setId(result.getLong("id"));
                p.setNome(result.getString("nome"));
                p.setCPF(result.getInt("CPF"));
                p.setDtNascimento(result.getDate("DtNascimento"));

                //Adiciona a instância na lista
                listaClientes.add(p);
            }
        } finally {
            //Se o result ainda estiver aberto, realiza seu fechamento
            if (result != null && !result.isClosed()) {
                result.close();
            }
            //Se o statement ainda estiver aberto, realiza seu fechamento
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            //Se a conexão ainda estiver aberta, realiza seu fechamento
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        //Retorna a lista de clientes do banco de dados
        return listaClientes;
    }

    public static Cliente obter(Integer id)
            throws SQLException, Exception {
        //Compõe uma String de consulta que considera apenas o cliente
        //com o ID informado e que esteja ativo ("enabled" com "true")
        String sql = "SELECT * FROM PRODUTOBD.PRODUTO WHERE (id=?)";

        //Statement para obtenção através da conexão, execução de
        //comandos SQL e fechamentos
        PreparedStatement preparedStatement = null;
        //Armazenará os resultados do banco de dados
        ResultSet result = null;
        Connection conn = obterConexao();
        try {
            //Abre uma conexão com o banco de dados

            //Cria um statement para execução de instruções SQL
            preparedStatement = conn.prepareStatement(sql);
            //Configura os parâmetros do "PreparedStatement"
            preparedStatement.setInt(1, id);

            //Executa a consulta SQL no banco de dados
            result = preparedStatement.executeQuery();

            //Verifica se há pelo menos um resultado
            if (result.next()) {
                //Cria uma instância de Cliente e popula com os valores do BD
                Cliente p = new Cliente();

                p.setId(result.getLong("id"));
                p.setNome(result.getString("nome"));
                p.setCPF(result.getInt("CPF"));
                p.setDtNascimento(result.getDate("DtNascimento"));

                //Retorna o resultado
                return p;
            }
        } finally {
            //Se o result ainda estiver aberto, realiza seu fechamento
            if (result != null && !result.isClosed()) {
                result.close();
            }
            //Se o statement ainda estiver aberto, realiza seu fechamento
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            //Se a conexão ainda estiver aberta, realiza seu fechamento
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }

        //Se chegamos aqui, o "return" anterior não foi executado porque
        //a pesquisa não teve resultados
        //Neste caso, não há um elemento a retornar, então retornamos "null"
        return null;
    }
}
