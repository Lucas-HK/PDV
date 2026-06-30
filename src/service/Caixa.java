package service;

import model.ItemVenda;
import model.Venda;

public class Caixa {

    public double calcularTroco(
            double valorPago,
            double total) {

        if (valorPago < total) {
            throw new IllegalArgumentException("Valor pago é insuficiente.");
        }
        return valorPago - total;
    }
    public void finalizarVenda(Venda venda, double valorPago) {
        double total = venda.calcularTotal();

        double troco = calcularTroco(valorPago, total);

        for (ItemVenda item : venda.getItens()) {
            int quantidadeVendida = item.getQuantidade();

            if (item.getProduto().getEstoque() < quantidadeVendida) {
                throw new IllegalStateException("Estoque insuficiente para o produto: " + item.getProduto().getNome());
            }

            item.getProduto().baixarEstoque(quantidadeVendida);
        }
    }
}
