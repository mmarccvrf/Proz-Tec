package model;

/**
 * Modelo que representa a entidade Cliente do banco de dados.
 */
public class ClienteModel {
    private String cpf;
    private String nome;
    private String genero;
    private String telefone;
    private String email;
    private String senha;
    private Integer fkVendaId; // Pode ser nulo

    // Construtor padrão
    public ClienteModel() {}

    // Construtor completo
    public ClienteModel(String cpf, String nome, String genero, String telefone, String email, String senha, Integer fkVendaId) {
        this.cpf = cpf;
        this.nome = nome;
        this.genero = genero;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.fkVendaId = fkVendaId;
    }

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    // Métodos preexistentes mantidos para compatibilidade
    public char login() {
        return 'a';
    }

    public char buscarPedidos() {
        return 'a';
    }

    @Override
    public String toString() {
        return "ClienteModel{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", genero='" + genero + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", fkVendaId=" + fkVendaId +
                '}';
    }
}
