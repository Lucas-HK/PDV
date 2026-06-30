package model;

public class Produto {

    private int codigo;
    private String nome;
    private double preco;
    private int estoque;

    public Produto(int codigo, String nome,
                   double preco, int estoque) {

        if (preco < 0) {
            throw new IllegalArgumentException("O preço do produto não pode ser negativo.");
        }
        if (estoque < 0) {
            throw new IllegalArgumentException("O estoque inicial não pode ser negativo.");
        }

        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }


    public int getEstoque() {
        return estoque;
    }

    public void baixarEstoque(int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade para baixar deve ser maior que zero.");
        }
        if (quantidade > this.estoque) {
            throw new IllegalStateException("Estoque insuficiente! O produto -" + nome + "- só tem " + estoque + " unidades.");
        }

        estoque -= quantidade;
    }

    public void setNome(String nome) { this.nome = nome; }

    public void setPreco(double preco) { this.preco = preco; }

    public void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.estoque += quantidade;
    }
}
