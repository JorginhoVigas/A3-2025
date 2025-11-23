package modelo;

public class Cliente {

    private int idCliente;
    private String nome;
    private String telefone;
    private int tipoDocumento;
    private String cpfCnpj;
    private int status;

    public Cliente() {
    }

    public Cliente(int idCliente, String nome, String telefone, int tipoDocumento, String cpfCnpj, int status) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.telefone = telefone;
        this.tipoDocumento = tipoDocumento;
        this.cpfCnpj = cpfCnpj;
        this.status = status;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(int tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cliente{"
                + "idCliente=" + idCliente
                + ", nome=" + nome
                + ", telefone=" + telefone
                + ", tipoDocumento=" + tipoDocumento
                + ", cpfCnpj=" + cpfCnpj
                + ", status=" + status + '}';
    }
}
