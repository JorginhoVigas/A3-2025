package modelo;

import java.sql.Timestamp;

public class Venda {

    private int idVenda;         
    private Usuario usuario;    
    private Cliente cliente;  
    private int status;
    private double valorTotal; 
    private double desconto;
    private String observacao;
    private Timestamp dataVenda;

    public Venda() {
    }

    public Venda(int idVenda, Usuario usuario, Cliente cliente, int status, double valorTotal, double desconto, String observacao, Timestamp dataVenda) {
        this.idVenda = idVenda;
        this.usuario = usuario;
        this.cliente = cliente;
        this.status = status;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
        this.observacao = observacao;
        this.dataVenda = dataVenda;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Timestamp getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Timestamp dataVenda) {
        this.dataVenda = dataVenda;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "idVenda=" + idVenda +
                ", usuario=" + (usuario != null ? usuario.getIdUsuario() + " - " + usuario.getNome() : "null") +
                ", cliente=" + (cliente != null ? cliente.getIdCliente() + " - " + cliente.getNome() : "null") +
                ", status=" + status +
                ", valorTotal=" + valorTotal +
                ", desconto=" + desconto +
                ", observacao='" + observacao + '\'' +
                ", dataVenda=" + dataVenda +
                '}';
    }
}
