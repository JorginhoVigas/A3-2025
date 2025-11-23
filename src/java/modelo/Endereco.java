package modelo;

public class Endereco {

    private int idEndereco;
    private String estado;
    private String cidade;
    private String cep;
    private String bairro;
    private String logradouro;
    private String uf;
    private String numero;
    private String complemento;

    public Endereco() {
    }

    public Endereco(int idEndereco, String estado, String cidade, String cep, String bairro, String logradouro, String uf, String numero, String complemento) {
        this.idEndereco = idEndereco;
        this.estado = estado;
        this.cidade = cidade;
        this.cep = cep;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.uf = uf;
        this.numero = numero;
        this.complemento = complemento;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public String getEstado() {
        return estado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getCep() {
        return cep;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getUf() {
        return uf;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        return "Endereco{" + "idEndereco=" + idEndereco + ", estado=" + estado + ", cidade=" + cidade + ", cep=" + cep + ", bairro=" + bairro + ", logradouro=" + logradouro + ", uf=" + uf + ", numero=" + numero + ", complemento=" + complemento + '}';
    }

}
