# Documentação do Sistema de Gestão para Oficina Mecânica

## 1. Introdução

Este documento apresenta a especificação e detalhes do sistema de gestão desenvolvido para uma oficina mecânica. O sistema permite o cadastro e gerenciamento de clientes, ordens de serviço, e peças, facilitando o controle interno da oficina.

---

## 2. Escopo do Sistema

O sistema contempla as seguintes funcionalidades principais:

- Gerenciar dados cadastrais de clientes e veículos.
- Facilitar o controle de informações essenciais para a oficina.
- Prover uma base para futuras expansões como orçamentos, prazos de entrega e geração de garantias.

---

## 3. Requisitos Funcionais

- O sistema deve permitir a inclusão, alteração, exclusão e consulta de clientes.
- O sistema deve permitir o registro e acompanhamento de ordens de serviço.
- O sistema deve controlar o estoque de peças disponíveis.
- O sistema deve gerar relatórios de ordens de serviço e estoque.

---

## 4. Requisitos Funcionais

- Implementação do gerenciamento completo de orçamentos, incluindo cadastro detalhado de peças e mão de obra.
- Definição de prazos para entrega dos serviços.
- Geração automática de garantias com validade de 4 meses.

---

## 5. Tecnologias Utilizadas.

- Linguagem: Java (NetBeans IDE 21)
- Banco de Dados: MySQL (MySQL Workbench 8.0 CE)
- Servidor Web e Banco: XAMPP (Apache + MySQL)
- Controle de Versão: Git / GitHub



## 6. Diagrama de Entidade-Relacionamento (DER)

```EED - Diagrama
+-------------+          1        N         +------------+
|   Cliente   |---------------------------->|  Veículo   |
+-------------+                             +------------+
| id (PK)     |                             | id (PK)    |
| nome        |                             | cliente_id (FK) |
| telefone    |                             | modelo     |
| email       |                             | cor        |
+-------------+                             | marca      |
                                            | placa      |
                                            +------------+
```

![image](https://github.com/user-attachments/assets/603ab59c-ad63-4eaa-b200-b2d36da8be17)

---

## 7. Código - Java

### 7.1 Código Java - Classe Oficina.java
```java
import java.awt.Image;
import javax.swing.*;
import java.sql.ResultSet;

// public class Oficina extends JFrame {
    private JTextField idField, nomeField, telefoneField, emailField;
    private JTextField modeloField, corField, marcaField, placaField;
    private JButton incluirBtn, editarBtn, consultarBtn, excluirBtn, limparBtn;
    private ClienteDAO clienteDAO = new ClienteDAO();

    public Oficina() {
        setTitle("Gestão de Clientes");
        setLayout(null);
        setSize(520, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon logoOriginal = new ImageIcon(getClass().getResource("/Imagens/logo.oficina.png"));
        Image logoRedimensionada = logoOriginal.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(logoRedimensionada);

        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds(520 - 150 - 10, 10, 150, 150);
        add(logoLabel);

        JLabel idLabel = new JLabel("ID:");
        JLabel nomeLabel = new JLabel("Nome:");
        JLabel telefoneLabel = new JLabel("Telefone:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel modeloLabel = new JLabel("Modelo:");
        JLabel corLabel = new JLabel("Cor:");
        JLabel marcaLabel = new JLabel("Marca:");
        JLabel placaLabel = new JLabel("Placa:");

        JLabel footerLabel = new JLabel("Formulário de Cadastro Clientes - Oficina Prado");
        footerLabel.setBounds(10, 500, 400, 20);

        idField = new JTextField();
        nomeField = new JTextField();
        telefoneField = new JTextField();
        emailField = new JTextField();
        modeloField = new JTextField();
        corField = new JTextField();
        marcaField = new JTextField();
        placaField = new JTextField();

        incluirBtn = new JButton("Incluir");
        editarBtn = new JButton("Editar");
        consultarBtn = new JButton("Consultar");
        excluirBtn = new JButton("Excluir");
        limparBtn = new JButton("Limpar");

        // Posicionamento dos elementos
        idLabel.setBounds(20, 20, 100, 20);
        idField.setBounds(120, 20, 200, 20);
        nomeLabel.setBounds(20, 50, 100, 20);
        nomeField.setBounds(120, 50, 200, 20);
        telefoneLabel.setBounds(20, 80, 100, 20);
        telefoneField.setBounds(120, 80, 200, 20);
        emailLabel.setBounds(20, 110, 100, 20);
        emailField.setBounds(120, 110, 200, 20);
        modeloLabel.setBounds(20, 140, 100, 20);
        modeloField.setBounds(120, 140, 200, 20);
        corLabel.setBounds(20, 170, 100, 20);
        corField.setBounds(120, 170, 200, 20);
        marcaLabel.setBounds(20, 200, 100, 20);
        marcaField.setBounds(120, 200, 200, 20);
        placaLabel.setBounds(20, 230, 100, 20);
        placaField.setBounds(120, 230, 200, 20);

        incluirBtn.setBounds(20, 270, 80, 30);
        editarBtn.setBounds(110, 270, 80, 30);
        consultarBtn.setBounds(200, 270, 90, 30);
        excluirBtn.setBounds(300, 270, 80, 30);
        limparBtn.setBounds(400, 270, 80, 30);

        add(idLabel); add(idField);
        add(nomeLabel); add(nomeField);
        add(telefoneLabel); add(telefoneField);
        add(emailLabel); add(emailField);
        add(modeloLabel); add(modeloField);
        add(corLabel); add(corField);
        add(marcaLabel); add(marcaField);
        add(placaLabel); add(placaField);

        add(incluirBtn); add(editarBtn); add(consultarBtn); add(excluirBtn); add(limparBtn);
        add(footerLabel);

        incluirBtn.addActionListener(e -> incluir());
        editarBtn.addActionListener(e -> editarCliente());
        consultarBtn.addActionListener(e -> consultarCliente());
        excluirBtn.addActionListener(e -> excluirCliente());
        limparBtn.addActionListener(e -> limparCampos());

        setVisible(true);
    }

    private void incluir() {
        try {
            clienteDAO.incluir(
                nomeField.getText(),
                telefoneField.getText(),
                emailField.getText(),
                modeloField.getText(),
                corField.getText(),
                marcaField.getText(),
                placaField.getText()
            );
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao incluir cliente: " + e.getMessage());
        }
    }

    private void editarCliente() {
        try {
            int id = Integer.parseInt(idField.getText());
            clienteDAO.editar(id, nomeField.getText(), telefoneField.getText(), emailField.getText());
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    private void consultarCliente() {
        try {
            int id = Integer.parseInt(idField.getText());
            ResultSet rs = clienteDAO.consultar(id);
            if (rs != null && rs.next()) {
                nomeField.setText(rs.getString("nome"));
                telefoneField.setText(rs.getString("telefone"));
                emailField.setText(rs.getString("email"));
                modeloField.setText(rs.getString("modelo"));
                corField.setText(rs.getString("cor"));
                marcaField.setText(rs.getString("marca"));
                placaField.setText(rs.getString("placa"));
                rs.getStatement().getConnection().close();
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar cliente: " + e.getMessage());
        }
    }

    private void excluirCliente() {
        try {
            int id = Integer.parseInt(idField.getText());
            clienteDAO.excluir(id);
            JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso.");
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir cliente: " + e.getMessage());
        }
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        telefoneField.setText("");
        emailField.setText("");
        modeloField.setText("");
        corField.setText("");
        marcaField.setText("");
        placaField.setText("");
    }

    public static void main(String[] args) {
        new Oficina();
    }
}

```

### 7.2 Código Java - Classe ClienteDAO.java
```java
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

```
### 7.3 Código Java - Classe Conexao.java
```java
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


```

### Estrutura do Programa dentro da IDE NetBeans.

![image](https://github.com/user-attachments/assets/f5971660-5c76-4436-9008-9c17df0b9792)

## 8. Código - SQL

### 8.1 Query em SQL - Criar Banco Oficina
```SQL

CREATE DATABASE oficina;
USE oficina;

```
### 8.2 Query em SQL - Criar Tabela Clientes.
```SQL

CREATE TABLE clientes (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8;
```
### 8.3 Query em SQL - Criar Tabela Veícuylos.
```SQL

CREATE TABLE veiculos (
    id INT NOT NULL AUTO_INCREMENT,
    cliente_id INT,
    modelo VARCHAR(50),
    cor VARCHAR(30),
    marca VARCHAR(50),
    placa VARCHAR(10) UNIQUE,
    PRIMARY KEY (id),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) DEFAULT CHARSET = utf8;

```

### 8.4 Query em SQL - Consultas editar .
```SQL

USE oficina;

-- Query para inserção de dados na tabela clientes.
INSERT INTO clientes (nome, telefone, email) VALUES
('Carlos Silva', '11999999999', 'carlos.silva@gmail.com'),
('Ana Pereira', '21988888888', 'ana.pereira@hotmail.com'),
('João Souza', '31977777777', 'joao.souza@yahoo.com.br'),
('Fernanda Lima', '41966666666', 'fernanda.lima@gmail.com'),
('Ricardo Alves', '51955555555', 'ricardo.alves@gmail.com');

-- Query  para inserção de veículos relacionados aos clientes
INSERT INTO veiculos (cliente_id, modelo, cor, marca, placa) VALUES
(1, 'Onix', 'Prata', 'Chevrolet', 'ABC1A23'),
(2, 'Civic', 'Preto', 'Honda', 'DEF2B34'),
(3, 'Gol', 'Branco', 'Volkswagen', 'GHI3C45'),
(4, 'Corolla', 'Cinza', 'Toyota', 'JKL4D56'),
(5, 'HB20', 'Vermelho', 'Hyundai', 'MNO5E67');

-- Query para atualização dos telefones dos clientes.
UPDATE clientes
SET telefone = CASE id
    WHEN 1 THEN '11973872463'
    WHEN 2 THEN '1122789846'
    WHEN 3 THEN '21973971468'
    WHEN 4 THEN '2122356410'
    WHEN 5 THEN '47982146879'
END
WHERE id IN (1, 2, 3, 4, 5);


-- Query para consulta e atualização das tabelas.

-- Consulta combinando clientes com seus veículos
-- Essa query exibe dados de veículos junto com os dados dos respectivos clientes
SELECT 
    v.id AS veiculo_id,
    v.modelo,
    v.cor,
    v.marca,
    v.placa,
    c.id AS cliente_id,
    c.nome,
    c.telefone,
    c.email
FROM veiculos v
JOIN clientes c ON v.cliente_id = c.id;

-- Verificação de registros com exemplo: ID 7

-- Útil para saber se há cliente ou veículo com esse ID antes de excluir
SELECT * FROM clientes WHERE id = 7;
SELECT * FROM veiculos WHERE id = 7 OR cliente_id = 7;

-- Exclusão do cliente com ID 7 (se existir)

-- Isso também remove automaticamente o veículo vinculado por ON DELETE CASCADE
DELETE FROM clientes WHERE id = 7;

-- Exclusão direta do veículo com ID 7 (caso queira apenas apagar o veículo).alter
-- DELETE FROM veiculos WHERE id = 7;

```
### 8.5 Estrutura do Programa dentro da IDE MySQL Workbench.

![image](https://github.com/user-attachments/assets/de68b660-ccc0-431d-98be-66dcd59b992f)


### 8.6 Visualização do EER Driagrama do Bando de Dados Odicina.
![image](https://github.com/user-attachments/assets/0bc60421-f607-4c58-aa29-9fe41d38d438)


