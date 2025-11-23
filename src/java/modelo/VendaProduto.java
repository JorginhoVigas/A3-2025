package modelo;

public class VendaProduto {

    private int idVendaProduto;
    private Venda venda;
    private Produto produto;
    private int quantidade;
    private double valorUnitario;
    private double valorTotal; // Alterado de subtotal para valorTotal para combinar com o Servlet

    public VendaProduto() {
    }

    public int getIdVendaProduto() {
        return idVendaProduto;
    }

    public void setIdVendaProduto(int idVendaProduto) {
        this.idVendaProduto = idVendaProduto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    // --- AQUI ESTAVA O ERRO: Renomeado de get/setSubtotal para get/setValorTotal ---
    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    // -----------------------------------------------------------------------------

    @Override
    public String toString() {
        return "VendaProduto{" + "idVendaProduto=" + idVendaProduto + ", venda=" + venda + ", produto=" + produto + ", quantidade=" + quantidade + ", valorUnitario=" + valorUnitario + ", valorTotal=" + valorTotal + '}';
    }

}
