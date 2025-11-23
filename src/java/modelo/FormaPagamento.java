package modelo;

public class FormaPagamento {

    private int idFormaPagamento;
    private String nome;
    private String descricao;
    private int status;
    private int tipoPagamento;

    public FormaPagamento() {
    }

    public FormaPagamento(int idFormaPagamento, String nome) {
        this.idFormaPagamento = idFormaPagamento;
        this.nome = nome;
    }

    public int getIdFormaPagamento() {
        return idFormaPagamento;
    }

    public void setIdFormaPagamento(int idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(int tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
