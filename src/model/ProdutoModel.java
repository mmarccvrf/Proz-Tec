package model;

/**
 * Modelo que representa a entidade Produto do banco de dados.
 */
public class ProdutoModel {
    private Integer id;
    private String nome;
    private Integer quant;
    private Integer valor;
    private Integer fornecedor; // Pode ser nulo

    // Construtor padrão
    public ProdutoModel() {}

    // Construtor completo
    public ProdutoModel(Integer id, String nome, Integer quant, Integer valor, Integer fornecedor) {
        this.id = id;
        this.nome = nome;
        this.quant = quant;
        this.valor = valor;
        this.fornecedor = fornecedor;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuant() {
        return quant;
    }

    public void setQuant(Integer quant) {
        this.quant = quant;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Integer fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "ProdutoModel{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", quant=" + quant +
                ", valor=" + valor +
                ", fornecedor=" + fornecedor +
                '}';
    }
}
