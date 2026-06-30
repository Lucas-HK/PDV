package controller;

import java.util.ArrayList;
import java.util.List;

import model.ItemVenda;
import model.Produto;
import model.Venda;
import service.Caixa;

public class VendaController {

    private List<Produto> bancoDeProdutos;
    private Venda vendaAtual;
    private Caixa caixaService;

    public VendaController() {
        this.caixaService = new Caixa();
        this.bancoDeProdutos = new ArrayList<>();
        this.vendaAtual = new Venda();

        inicializarProdutosFicticios();
    }

    private List<model.Cliente> bancoDeClientes = new java.util.ArrayList<>();

    private List<model.Venda> historicoVendas = new java.util.ArrayList<>();

    private void inicializarProdutosFicticios() {
        bancoDeProdutos.add(new Produto(1, "Refrigerante", 7.50, 20));
        bancoDeProdutos.add(new Produto(2, "Chocolate", 4.00, 35));
        bancoDeProdutos.add(new Produto(3, "Água", 2.50, 50));
    }

//pesquisa

    public Produto pesquisarPorCodigo(int codigo) {
        for (Produto p : bancoDeProdutos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        throw new IllegalArgumentException("Produto com o código " + codigo + " não foi encontrado.");
    }

    public Produto buscarProduto(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            throw new IllegalArgumentException("Digite o código ou o nome do produto.");
        }

        String termoLimpo = termo.trim();

        try {
            int codigo = Integer.parseInt(termoLimpo);
            for (Produto p : bancoDeProdutos) {
                if (p.getCodigo() == codigo) {
                    return p;
                }
            }
        } catch (NumberFormatException e) {

        }

        for (Produto p : bancoDeProdutos) {

            if (p.getNome().toLowerCase().contains(termoLimpo.toLowerCase())) {
                return p;
            }
        }

        throw new IllegalArgumentException("Nenhum produto encontrado com o código ou nome: " + termoLimpo);
    }

    //Adicionar produto ao carrinho/venda

    public void adicionarProdutoPorTermo(String termo, int quantidade) {
        Produto produto = buscarProduto(termo);
        ItemVenda item = new ItemVenda(produto, quantidade);
        vendaAtual.adicionarItem(item);
    }

    public double finalizarVendaAtual(double valorPago) {

        caixaService.finalizarVenda(vendaAtual, valorPago);

        double troco = caixaService.calcularTroco(valorPago, vendaAtual.calcularTotal());

        historicoVendas.add(vendaAtual);

        return troco;
    }

    public List<model.Venda> getHistoricoVendas() {
        return historicoVendas;
    }

    public boolean salvarOuAtualizarProduto(int codigo, String nome, double preco, int quantidade) {
        try {
            Produto produtoExistente = pesquisarPorCodigo(codigo);

            produtoExistente.adicionarEstoque(quantidade);
            produtoExistente.setNome(nome);
            produtoExistente.setPreco(preco);

            return true;

        } catch (IllegalArgumentException e) {
            Produto novo = new Produto(codigo, nome, preco, quantidade);
            bancoDeProdutos.add(novo);

            return false;
        }
    }

    //nova venda
    public void iniciarNovaVenda() {
        this.vendaAtual = new Venda();
    }

    public Venda getVendaAtual() {
        return vendaAtual;
    }

    public List<Produto> getBancoDeProdutos() {
        return bancoDeProdutos;
    }

    public boolean salvarCliente(String cpf, String nome, String telefone) {
        for (model.Cliente c : bancoDeClientes) {
            if (c.getCpf().equals(cpf)) {
                c.setNome(nome);
                c.setTelefone(telefone);
                return true;
            }
        }
        bancoDeClientes.add(new model.Cliente(cpf, nome, telefone));
        return false;
    }

    public List<model.Cliente> getBancoDeClientes() {
        return bancoDeClientes;
    }

    public model.Cliente removerCliente(String cpf) {
        for (model.Cliente c : bancoDeClientes) {
            if (c.getCpf().equals(cpf)) {
                bancoDeClientes.remove(c);
                return c;
            }
        }
        return null;
    }

    public void associarClienteAVenda(model.Cliente cliente) {
        if (vendaAtual != null) {
            vendaAtual.setCliente(cliente);
        }
    }
}