package view;

import controller.VendaController;
import java.awt.*;
import javax.swing.*;

public class TelaLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private VendaController controller;

    public TelaLogin(VendaController controller) {
        this.controller = controller;

        //LOGIN -> admin
        //SENHA -> 2705

        setTitle("Autenticação - PDV Caixa");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Espaçamento entre os campos
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Acesso ao Sistema", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelPrincipal.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelPrincipal.add(new JLabel("Usuário:"), gbc);

        txtUsuario = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        painelPrincipal.add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelPrincipal.add(new JLabel("Senha:"), gbc);

        txtSenha = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        painelPrincipal.add(txtSenha, gbc);

        JButton btnEntrar = new JButton("Entrar no Caixa");
        btnEntrar.setBackground(new Color(84, 232, 199, 255)); // Mesmo azul bonito do PDV
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 12));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        painelPrincipal.add(btnEntrar, gbc);

        add(painelPrincipal);

        btnEntrar.addActionListener(e -> {
            String usuario = txtUsuario.getText().trim();
            String senha = new String(txtSenha.getPassword()).trim();

            if (usuario.equals("admin") && senha.equals("2705")) {
                JOptionPane.showMessageDialog(this, "Login efetuado com sucesso! Bem-vindo.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                this.dispose();

                TelaPDV telaPDV = new TelaPDV(controller);
                telaPDV.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou Senha incorretos!", "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
                txtSenha.setText("");
                txtSenha.requestFocus();
            }
        });
    }
}