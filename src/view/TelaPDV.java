package view;

import controller.VendaController;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.ItemVenda;
import model.Produto;

public class TelaPDV extends JFrame {

    private VendaController controller;
    private JTextField txtBuscaProduto, txtQuantidade, txtValorPago;
    private JLabel lblSubtotal, lblTotal, lblTroco;
    private JTable tabelaItens;
    private DefaultTableModel modeloTabela;
    private JTextArea txtAreaEstoque;
    private JLabel lblClienteAtual;

    public TelaPDV(VendaController controller) {
        this.controller = controller;
        setTitle("Sistema de Frente de Loja - PDV");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        inicializarComponentes();
        atualizarPainelEstoque();
    }

    private void inicializarComponentes() {
        //estoque (esquerda)
        JPanel painelEstoque = new JPanel(new BorderLayout(5 ,5));
        painelEstoque.setBorder(BorderFactory.createTitledBorder("Produtos no Estoque"));
        txtAreaEstoque = new JTextArea(8, 25);
        txtAreaEstoque.setEditable(false);
        txtAreaEstoque.setFont(new Font("Monospaced", Font.PLAIN, 13));
        painelEstoque.add(new JScrollPane(txtAreaEstoque), BorderLayout.CENTER);
        add(painelEstoque, BorderLayout.WEST);

        JButton btnCadastrarProduto = new JButton("Cadastrar/Atualizar Produto");

        btnCadastrarProduto.setBackground(new Color(0, 36, 225, 255));
        btnCadastrarProduto.setForeground(Color.WHITE);

        painelEstoque.add(btnCadastrarProduto, BorderLayout.SOUTH);
        add(painelEstoque, BorderLayout.WEST);

        //Painel central
        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));

        JPanel painelInputs = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelInputs.setBorder(BorderFactory.createTitledBorder("Pesquisa e Venda"));

        painelInputs.add(new JLabel("Cód ou Nome:"));
        txtBuscaProduto = new JTextField(11);
        painelInputs.add(txtBuscaProduto);

        painelInputs.add(new JLabel("Qtd:"));
        txtQuantidade = new JTextField(3);
        txtQuantidade.setText("1");
        painelInputs.add(txtQuantidade);

        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnAdicionar = new JButton("Adicionar Item");
        JButton btnSelecionarCliente = new JButton("Selecionar Cliente");

        btnPesquisar.setBackground(new Color(200, 220, 255));
        btnAdicionar.setBackground(new Color(200, 255, 200));
        btnSelecionarCliente.setBackground(new Color(250, 218, 96));

        painelInputs.add(btnPesquisar);
        painelInputs.add(btnAdicionar);
        painelInputs.add(btnSelecionarCliente);

        JPanel painelTopCentro = new JPanel(new BorderLayout(5, 5));
        painelTopCentro.add(painelInputs, BorderLayout.NORTH);

        lblClienteAtual = new JLabel("Cliente da Venda: Consumidor Geral");
        lblClienteAtual.setFont(new Font("Arial", Font.BOLD, 12));
        lblClienteAtual.setForeground(new Color(44, 62, 80));
        lblClienteAtual.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));
        painelTopCentro.add(lblClienteAtual, BorderLayout.SOUTH);

        painelCentral.add(painelTopCentro, BorderLayout.NORTH);

        //tabela
        String[] colunas = {"Código", "Produto", "Preço Unit.", "Qtd", "Subtotal"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaItens = new JTable(modeloTabela);
        tabelaItens.setRowHeight(25);
        painelCentral.add(new JScrollPane(tabelaItens), BorderLayout.CENTER);

        JPanel painelInferiorCentro = new JPanel(new GridLayout(1, 2, 10, 10));
        painelInferiorCentro.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JButton btnCadastrarCliente = new JButton("Cadastrar / Atualizar Cliente");
        btnCadastrarCliente.setBackground(new Color(230, 126, 34));
        btnCadastrarCliente.setForeground(Color.WHITE);
        btnCadastrarCliente.setFont(new Font("Arial", Font.BOLD, 12));

        JButton btnRemoverCliente = new JButton("Remover Cliente do Sistema");
        btnRemoverCliente.setBackground(new Color(192, 57, 43));
        btnRemoverCliente.setForeground(Color.WHITE);
        btnRemoverCliente.setFont(new Font("Arial", Font.BOLD, 12));

        painelInferiorCentro.add(btnCadastrarCliente);
        painelInferiorCentro.add(btnRemoverCliente);
        painelCentral.add(painelInferiorCentro, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);

        //painel da direita(financeiro e açoes)
        JPanel painelDireito = new JPanel(new BorderLayout(10, 10));
        painelDireito.setPreferredSize(new Dimension(250, 0));

        //financeiro
        JPanel painelValores = new JPanel(new GridLayout(6, 2, 5, 10));
        painelValores.setBorder(BorderFactory.createTitledBorder("Financeiro"));

        painelValores.add(new JLabel("Subtotal:"));
        lblSubtotal = new JLabel("R$ 0,00", SwingConstants.RIGHT);
        painelValores.add(lblSubtotal);

        painelValores.add(new JLabel("Desconto (%):"));
        JTextField txtDesconto = new JTextField("0.0");
        painelValores.add(txtDesconto);

        JButton btnAplicarDesconto = new JButton("Aplicar %");
        painelValores.add(btnAplicarDesconto);
        painelValores.add(new JLabel(""));

        painelValores.add(new JLabel("TOTAL:"));
        lblTotal = new JLabel("R$ 0,00", SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        painelValores.add(lblTotal);

        painelValores.add(new JLabel("Valor Pago:"));
        txtValorPago = new JTextField();
        painelValores.add(txtValorPago);

        painelValores.add(new JLabel("Troco:"));
        lblTroco = new JLabel("R$ 0,00", SwingConstants.RIGHT);
        painelValores.add(lblTroco);

        //ações
        JPanel painelAcoes = new JPanel(new GridLayout(4, 1, 5, 10)); // Mudei para 4 linhas
        painelAcoes.setBorder(BorderFactory.createTitledBorder("Ações"));

        JButton btnNovaVenda = new JButton("Nova Venda");
        JButton btnFinalizar = new JButton("Finalizar Venda");
        JButton btnCancelar = new JButton("Cancelar Venda");
        JButton btnHistorico = new JButton("Histórico de Vendas");

        btnFinalizar.setBackground(new Color(46, 204, 113));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 13));

        btnNovaVenda.setBackground(new Color(52, 152, 219));
        btnNovaVenda.setForeground(Color.WHITE);

        btnCancelar.setBackground(new Color(231, 76, 60));
        btnCancelar.setForeground(Color.WHITE);

        btnHistorico.setBackground(new Color(127, 140, 141));
        btnHistorico.setForeground(Color.WHITE);

        painelAcoes.add(btnFinalizar);
        painelAcoes.add(btnNovaVenda);
        painelAcoes.add(btnHistorico);
        painelAcoes.add(btnCancelar);

        painelDireito.add(painelValores, BorderLayout.NORTH);
        painelDireito.add(painelAcoes, BorderLayout.CENTER);
        add(painelDireito, BorderLayout.EAST);

        // ========================================================================================================================================================================================================

        //Pesquisar
        btnPesquisar.addActionListener(e -> {
            try {
                String termo = txtBuscaProduto.getText();
                Produto p = controller.buscarProduto(termo);
                JOptionPane.showMessageDialog(this, "Produto: " + p.getNome() + "\nPreço: R$ " + p.getPreco() + "\nEstoque: " + p.getEstoque());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        //add carrinho
        btnAdicionar.addActionListener(e -> {
            try {
                String termo = txtBuscaProduto.getText();
                int qtd = Integer.parseInt(txtQuantidade.getText());
                controller.adicionarProdutoPorTermo(termo, qtd);
                atualizarCarrinho();
                txtBuscaProduto.setText("");
                txtBuscaProduto.requestFocus();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        //butao cadastrar
        btnCadastrarProduto.addActionListener(e -> {
            JTextField fCod = new JTextField();
            JTextField fNome = new JTextField();
            JTextField fPreco = new JTextField();
            JTextField fQtd = new JTextField();
            Object[] msg = {
                    "Código:", fCod,
                    "Nome do Produto:", fNome,
                    "Preço (R$):", fPreco,
                    "Estoque Inicial:", fQtd
            };

            int op = JOptionPane.showConfirmDialog(this, msg, "Novo Produto", JOptionPane.OK_CANCEL_OPTION);
            if (op == JOptionPane.OK_OPTION) {
                try {

                    String strCod = fCod.getText().trim();
                    String strNome = fNome.getText().trim();
                    String strPreco = fPreco.getText().trim();
                    String strQtd = fQtd.getText().trim();

                    if (strCod.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "O campo 'Código' não pode ficar vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (strNome.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "O campo 'Nome do Produto' não pode ficar vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (strPreco.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "O campo 'Preço' não pode ficar vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (strQtd.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "O campo 'Estoque Inicial' não pode ficar vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int cod = Integer.parseInt(strCod);
                    String nome = strNome;
                    double preco = Double.parseDouble(strPreco);
                    int qtd = Integer.parseInt(strQtd);

                    boolean foiAtualizado = controller.salvarOuAtualizarProduto(cod, nome, preco, qtd);

                    atualizarPainelEstoque();

                    model.Produto prodModificado = controller.pesquisarPorCodigo(cod);

                    String tituloPopup;
                    String textoIntroducao;

                    if (foiAtualizado) {
                        tituloPopup = "Produto Atualizado";
                        textoIntroducao = "O produto já existia e foi atualizado com sucesso!\n\n";
                    } else {
                        tituloPopup = "Produto Cadastrado";
                        textoIntroducao = "Novo produto adicionado ao sistema!\n\n";
                    }

                    String resumo = String.format(
                            textoIntroducao +
                                    "Código: %d\n" +
                                    "Nome: %s\n" +
                                    "Preço Unitário: R$ %.2f\n" +
                                    "Estoque Atual Total: %d unidades",
                            prodModificado.getCodigo(),
                            prodModificado.getNome(),
                            prodModificado.getPreco(),
                            prodModificado.getEstoque()
                    );

                    JOptionPane.showMessageDialog(this, resumo, tituloPopup, JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Erro: Use apenas números inteiros para Código/Estoque e pontos para o Preço.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Finalizar Venda
        btnFinalizar.addActionListener(e -> {
            try {
                String textoPago = txtValorPago.getText().trim();
                if (textoPago.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Insira o valor pago.");
                    return;
                }
                double pago = Double.parseDouble(textoPago);

                double subtotal = controller.getVendaAtual().calcularSubtotal();
                double desc = controller.getVendaAtual().getDesconto();
                double total = controller.getVendaAtual().calcularTotal();


                String dadosClienteCupom = "Consumidor Geral";
                if (controller.getVendaAtual().getCliente() != null) {
                    model.Cliente c = controller.getVendaAtual().getCliente();
                    dadosClienteCupom = String.format("%s\nCPF: %s", c.getNome(), c.getCpf());
                }

                double troco = controller.finalizarVendaAtual(pago);
                lblTroco.setText(String.format("R$ %.2f", troco));


                String recibo = String.format(
                        "Venda Finalizada!\n" +
                                "Cliente: %s\n" +
                                "----------------------------------------\n" +
                                "Subtotal: R$ %.2f\n" +
                                "Desconto: %.1f%%\n" +
                                "Total: R$ %.2f\n" +
                                "Valor Pago: R$ %.2f\n" +
                                "Troco: R$ %.2f",
                        dadosClienteCupom, subtotal, desc, total, pago, troco
                );
                JOptionPane.showMessageDialog(this, recibo, "Cupom Não Fiscal", JOptionPane.INFORMATION_MESSAGE);

                atualizarPainelEstoque();
                controller.iniciarNovaVenda();
                modeloTabela.setRowCount(0);
                txtValorPago.setText("");
                txtDesconto.setText("0.0");
                lblSubtotal.setText("R$ 0,00");
                lblTotal.setText("R$ 0,00");

                lblClienteAtual.setText("Cliente da Venda: Consumidor Geral");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        //botão histotico
        btnHistorico.addActionListener(e -> {
            JDialog janelaHistorico = new JDialog(this, "Histórico de Vendas", true);
            janelaHistorico.setSize(600, 350);
            janelaHistorico.setLocationRelativeTo(this);
            janelaHistorico.setLayout(new BorderLayout());

            String[] colunasHist = {"Cliente", "CPF Cliente", "Qtd Itens", "Total"};
            DefaultTableModel modeloHist = new DefaultTableModel(colunasHist, 0);
            JTable tabelaHist = new JTable(modeloHist);

            for (model.Venda v : controller.getHistoricoVendas()) {

                String nomeCliente = "Consumidor Geral";
                String cpfCliente = "---";

                if (v.getCliente() != null) {
                    nomeCliente = v.getCliente().getNome();
                    cpfCliente = v.getCliente().getCpf();
                }

                modeloHist.addRow(new Object[]{
                        nomeCliente,
                        cpfCliente,
                        v.getItens().size() + " item(ns)",
                        String.format("R$ %.2f", v.calcularTotal())
                });
            }
            janelaHistorico.add(new JScrollPane(tabelaHist), BorderLayout.CENTER);
            janelaHistorico.setVisible(true);
        });

        // Aplicar Desconto
        btnAplicarDesconto.addActionListener(e -> {
            try {
                double perc = Double.parseDouble(txtDesconto.getText());
                controller.getVendaAtual().aplicarDesconto(perc);
                atualizarValoresFinanceiros();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        ActionListener limparTudo = e -> {
            controller.iniciarNovaVenda();
            modeloTabela.setRowCount(0);
            txtValorPago.setText("");
            txtDesconto.setText("0.0");
            txtBuscaProduto.setText("");

            lblSubtotal.setText("R$ 0,00");
            lblTotal.setText("R$ 0,00");
            lblTroco.setText("R$ 0,00");
            lblClienteAtual.setText("Cliente da Venda: Consumidor Geral");

            JOptionPane.showMessageDialog(this, "Carrinho limpo! Pronto para uma nova venda.", "Status", JOptionPane.INFORMATION_MESSAGE);
        };

        btnNovaVenda.addActionListener(limparTudo);
        btnCancelar.addActionListener(limparTudo);

        //cadastrar cliente
        btnCadastrarCliente.addActionListener(e -> {
            JTextField fCpf = new JTextField();
            JTextField fNome = new JTextField();
            JTextField fTelefone = new JTextField();
            Object[] msg = {"CPF (Apenas 11 números):", fCpf, "Nome Completo:", fNome, "Telefone:", fTelefone};

            int op = JOptionPane.showConfirmDialog(this, msg, "Novo Cliente", JOptionPane.OK_CANCEL_OPTION);
            if (op == JOptionPane.OK_OPTION) {
                try {
                    String cpf = fCpf.getText().trim();
                    String nome = fNome.getText().trim();
                    String telefone = fTelefone.getText().trim();

                    if (cpf.isEmpty() || nome.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "CPF e Nome são obrigatórios!", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (!cpf.matches("\\d+")) {
                        JOptionPane.showMessageDialog(this, "O CPF não aceita letras, digite só números!", "Erro no CPF", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (cpf.length() != 11) {
                        JOptionPane.showMessageDialog(this, "O CPF precisa ter obrigatoriamente 11 dígitos!", "Erro no CPF", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    boolean atualizado = controller.salvarCliente(cpf, nome, telefone);
                    String status = atualizado ? "Dados atualizados!" : "Cadastrado com sucesso!";
                    JOptionPane.showMessageDialog(this, "Cliente: " + nome + "\nStatus: " + status);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        });

        //selecionar cliente
        btnSelecionarCliente.addActionListener(e -> {
            java.util.List<model.Cliente> clientes = controller.getBancoDeClientes();
            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado, por favor cadastre alguém aí.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] nomesClientes = new String[clientes.size()];
            for (int i = 0; i < clientes.size(); i++) {
                nomesClientes[i] = clientes.get(i).getNome() + " (CPF: " + clientes.get(i).getCpf() + ")";
            }

            String escolha = (String) JOptionPane.showInputDialog(this, "Escolha o cliente para esta venda:", "Selecionar Cliente", JOptionPane.QUESTION_MESSAGE, null, nomesClientes, nomesClientes[0]);

            if (escolha != null) {
                int index = java.util.Arrays.asList(nomesClientes).indexOf(escolha);
                model.Cliente selecionado = clientes.get(index);
                controller.associarClienteAVenda(selecionado);


                lblClienteAtual.setText(String.format("Cliente da Venda: %s (CPF: %s)", selecionado.getNome(), selecionado.getCpf()));

                JOptionPane.showMessageDialog(this, "Cliente " + selecionado.getNome() + " associado à venda com sucesso!");
            }
        });

        btnRemoverCliente.addActionListener(e -> {
            String cpfParaRemover = JOptionPane.showInputDialog(this, "Digite o CPF do cliente que deseja remover (Apenas números):");
            if (cpfParaRemover != null) {
                cpfParaRemover = cpfParaRemover.trim();

                model.Cliente clienteRemovido = controller.removerCliente(cpfParaRemover);

                if (clienteRemovido != null) {
                    String mensagemSucesso = String.format(
                            "Cliente removido com sucesso do sistema!\n\n" +
                                    "Nome: %s\n" +
                                    "CPF: %s\n" +
                                    "Telefone: %s",
                            clienteRemovido.getNome(),
                            clienteRemovido.getCpf(),
                            clienteRemovido.getTelefone()
                    );

                    JOptionPane.showMessageDialog(this, mensagemSucesso, "Cliente Excluído", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "CPF não encontrado no sistema.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

    }

    private void atualizarCarrinho() {
        modeloTabela.setRowCount(0);
        for (ItemVenda item : controller.getVendaAtual().getItens()) {
            modeloTabela.addRow(new Object[]{
                    item.getProduto().getCodigo(), item.getProduto().getNome(),
                    String.format("R$ %.2f", item.getProduto().getPreco()),
                    item.getQuantidade(), String.format("R$ %.2f", item.getSubtotal())
            });
        }
        atualizarValoresFinanceiros();
    }

    private void atualizarValoresFinanceiros() {
        lblSubtotal.setText(String.format("R$ %.2f", controller.getVendaAtual().calcularSubtotal()));
        lblTotal.setText(String.format("R$ %.2f", controller.getVendaAtual().calcularTotal()));
    }

    private void atualizarPainelEstoque() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-4s | %-12s | %-8s | %-3s\n", "ID", "Produto", "Preço", "Est"));
        sb.append("--------------------------------------\n");
        for (Produto p : controller.getBancoDeProdutos()) {
            sb.append(String.format("%-4d | %-12s | %-8.2f | %-3d\n",
                    p.getCodigo(), p.getNome(), p.getPreco(), p.getEstoque()));
        }
        txtAreaEstoque.setText(sb.toString());
    }
}