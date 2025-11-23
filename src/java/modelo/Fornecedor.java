package modelo;

import java.sql.Date;

public class Fornecedor {

    private int idFornecedor;
    private int status;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String inscricaoEstadual;
    private Date dataCadastro;
    private String telefone;
    private int enderecoId;

    public Fornecedor() {
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(int enderecoId) {
        this.enderecoId = enderecoId;
    }

    @Override
    public String toString() {
        return "Fornecedor{" + "idFornecedor=" + idFornecedor + ", razaoSocial=" + razaoSocial + ", nomeFantasia=" + nomeFantasia + '}';
    }
}
