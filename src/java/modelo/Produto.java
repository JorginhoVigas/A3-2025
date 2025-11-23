package modelo;

import java.sql.Date;

public class Produto {

    private int idProduto;
    private String descricao;
    private double custo;
    private int fornecedorId;
    private String codigoBarras;
    private String marca;
    private String unidadeMedida;
    private Date dataAquisicao;
    private int quantidadeEstoque;
    private int estoqueMinimo;
    private double valorVenda;
    private int status;
    private int categoriaId;

    public Produto() {
    }

    public Produto(int idProduto, String descricao, double custo, int fornecedorId, String codigoBarras,
            String marca, String unidadeMedida, Date dataAquisicao, int quantidadeEstoque,
            int estoqueMinimo, double valorVenda, int status, int categoriaId) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.custo = custo;
        this.fornecedorId = fornecedorId;
        this.codigoBarras = codigoBarras;
        this.marca = marca;
        this.unidadeMedida = unidadeMedida;
        this.dataAquisicao = dataAquisicao;
        this.quantidadeEstoque = quantidadeEstoque;
        this.estoqueMinimo = estoqueMinimo;
        this.valorVenda = valorVenda;
        this.status = status;
        this.categoriaId = categoriaId;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public int getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Date getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(Date dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public String toString() {
        return "Produto{"
                + "idProduto=" + idProduto
                + ", descricao='" + descricao + '\''
                + ", custo=" + custo
                + ", fornecedorId=" + fornecedorId
                + ", codigoBarras='" + codigoBarras + '\''
                + ", marca='" + marca + '\''
                + ", unidadeMedida='" + unidadeMedida + '\''
                + ", dataAquisicao=" + dataAquisicao
                + ", quantidadeEstoque=" + quantidadeEstoque
                + ", estoqueMinimo=" + estoqueMinimo
                + ", valorVenda=" + valorVenda
                + ", status=" + status
                + ", categoriaId=" + categoriaId
                + '}';
    }
}
