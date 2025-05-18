import java.awt.Image;
import javax.swing.*;
import java.sql.ResultSet;

public class Oficina extends JFrame {
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
