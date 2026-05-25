package model;

/**
 * Modelo que representa a entidade Fornecedor do banco de dados.
 */
public class FornecedorModel {
    private String cnpj;
    private String nome;
    private String atuacao;
    private String telefone;
    private String email;
    private String senha;
    private Integer fkVendaId;      // Pode ser nulo
    private String fkClienteCpf;   // Pode ser nulo

    // Construtor padrão
    public FornecedorModel() {}

    // Construtor completo
    public FornecedorModel(String cnpj, String nome, String atuacao, String telefone, String email, String senha, Integer fkVendaId, String fkClienteCpf) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.atuacao = atuacao;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.fkVendaId = fkVendaId;
        this.fkClienteCpf = fkClienteCpf;
    }

    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAtuacao() {
        return atuacao;
    }

    public void setAtuacao(String atuacao) {
        this.atuacao = atuacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getFkVendaId() {
        return fkVendaId;
    }

    public void setFkVendaId(Integer fkVendaId) {
        this.fkVendaId = fkVendaId;
    }

    public String getFkClienteCpf() {
        return fkClienteCpf;
    }

    public void setFkClienteCpf(String fkClienteCpf) {
        this.fkClienteCpf = fkClienteCpf;
    }

    @Override
    public String toString() {
        return "FornecedorModel{" +
                "cnpj='" + cnpj + '\'' +
                ", nome='" + nome + '\'' +
                ", atuacao='" + atuacao + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", fkVendaId=" + fkVendaId +
                ", fkClienteCpf='" + fkClienteCpf + '\'' +
                '}';
    }
}
