import java.sql.*;

public class ClienteDAO {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/oficina?useSSL=false&serverTimezone=UTC";
        String user = "root"; 
        String password = "NovaSenha123!";
        return DriverManager.getConnection(url, user, password);
    }

    public void incluir(String nome, String telefone, String email,
                        String modelo, String cor, String marca, String placa) throws SQLException {
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);

            String sqlCliente = "INSERT INTO clientes (nome, telefone, email) VALUES (?, ?, ?)";
            PreparedStatement psCliente = con.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
            psCliente.setString(1, nome);
            psCliente.setString(2, telefone);
            psCliente.setString(3, email);
            psCliente.executeUpdate();

            ResultSet rs = psCliente.getGeneratedKeys();
            if (rs.next()) {
                int clienteId = rs.getInt(1);

                String sqlVeiculo = "INSERT INTO veiculos (cliente_id, modelo, cor, marca, placa) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psVeiculo = con.prepareStatement(sqlVeiculo);
                psVeiculo.setInt(1, clienteId);
                psVeiculo.setString(2, modelo);
                psVeiculo.setString(3, cor);
                psVeiculo.setString(4, marca);
                psVeiculo.setString(5, placa);
                psVeiculo.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public ResultSet consultar(int id) throws SQLException {
        Connection con = getConnection();
        String sql = "SELECT c.*, v.modelo, v.cor, v.marca, v.placa " +
                     "FROM clientes c LEFT JOIN veiculos v ON c.id = v.cliente_id " +
                     "WHERE c.id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    public void editar(int id, String nome, String telefone, String email) throws SQLException {
        Connection con = getConnection();
        String sql = "UPDATE clientes SET nome = ?, telefone = ?, email = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nome);
        ps.setString(2, telefone);
        ps.setString(3, email);
        ps.setInt(4, id);
        ps.executeUpdate();
        con.close();
    }

    public void excluir(int id) throws SQLException {
        Connection con = getConnection();
        String sql = "DELETE FROM clientes WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
    }
}
