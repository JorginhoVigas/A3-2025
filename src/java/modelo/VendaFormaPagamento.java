package modelo;

public class VendaFormaPagamento {

    private int idVendaFormaPagamento;
    private Venda venda;
    private FormaPagamento formaPagamento;
    private double valorPago;
    private int numeroParcelas;

    public VendaFormaPagamento() {
    }

    public VendaFormaPagamento(int idVendaFormaPagamento, Venda venda, FormaPagamento formaPagamento, double valorPago, int numeroParcelas) {
        this.idVendaFormaPagamento = idVendaFormaPagamento;
        this.venda = venda;
        this.formaPagamento = formaPagamento;
        this.valorPago = valorPago;
        this.numeroParcelas = numeroParcelas;
    }

    public int getIdVendaFormaPagamento() {
        return idVendaFormaPagamento;
    }

    public void setIdVendaFormaPagamento(int idVendaFormaPagamento) {
        this.idVendaFormaPagamento = idVendaFormaPagamento;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(int numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    @Override
    public String toString() {
        return "VendaFormaPagamento{" + "idVendaFormaPagamento=" + idVendaFormaPagamento + ", venda=" + venda + ", formaPagamento=" + formaPagamento + ", valorPago=" + valorPago + ", numeroParcelas=" + numeroParcelas + '}';
    }
}
