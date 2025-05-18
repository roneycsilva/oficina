import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/oficina?useSSL=false&serverTimezone=UTC";  
    private static final String USUARIO = "root";                       
    private static final String SENHA = "NovaSenha123!";                    

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
