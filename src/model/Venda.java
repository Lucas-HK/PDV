package model;

import java.util.ArrayList;
import java.util.Collections; //-Collections => manipula e protege as listas-
import java.util.List;

public class Venda {

    private ArrayList<ItemVenda> itens;
    private double desconto;

    private Cliente cliente;

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Venda() {
        this.itens = new ArrayList<>();
        this.desconto = 0.0;
    }

    public void adicionarItem(ItemVenda item) {
        if (item == null) {
            throw new IllegalArgumentException("Item inválido.");
        }
        itens.add(item);
    }

    public double calcularSubtotal() {

        double subtotal = 0;

        for (ItemVenda item : itens) {
            subtotal += item.getSubtotal();
        }

        return subtotal;
    }

    public void aplicarDesconto(double valorDesconto) {
        if (valorDesconto < 0 || valorDesconto > 100) {
            throw new IllegalArgumentException("Desconto inválido.");
        }
        this.desconto = valorDesconto;
    }

    public double getValorDescontado(){
        return calcularSubtotal() * (desconto / 100);
    }

    // O valor final que o cliente realmente vai pagar (Total = Subtotal - Desconto)
    public double calcularTotal() {
        return calcularSubtotal() - getValorDescontado();
    }

    public double getDesconto() {
        return desconto;
    }

    public List<ItemVenda> getItens() {
        return Collections.unmodifiableList(itens); //-impede a auteração da lista
    }
}


