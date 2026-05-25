package model;

/**
 * Modelo que representa a entidade Venda do banco de dados,
 * com campos auxiliares para nomes obtidos via JOIN.
 */
public class VendaModel {
    private Integer id;
    private String fornecedor; // CNPJ do Fornecedor
    private String cliente;    // CPF do Cliente
    private Integer valor;
    private Integer produto;   // ID do Produto
    private Integer fkProdutoId; // FK do Produto

    // Campos auxiliares para exibição de relatórios (JOINs)
    private String clienteNome;
    private String fornecedorNome;
    private String produtoNome;

    // Construtores
    public VendaModel() {}

    public VendaModel(Integer id, String fornecedor, String cliente, Integer valor, Integer produto, Integer fkProdutoId) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.cliente = cliente;
        this.valor = valor;
        this.produto = produto;
        this.fkProdutoId = fkProdutoId;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getProduto() {
        return produto;
    }

    public void setProduto(Integer produto) {
        this.produto = produto;
    }

    public Integer getFkProdutoId() {
        return fkProdutoId;
    }

    public void setFkProdutoId(Integer fkProdutoId) {
        this.fkProdutoId = fkProdutoId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getFornecedorNome() {
        return fornecedorNome;
    }

    public void setFornecedorNome(String fornecedorNome) {
        this.fornecedorNome = fornecedorNome;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    @Override
    public String toString() {
        return "VendaModel{" +
                "id=" + id +
                ", fornecedor='" + fornecedor + '\'' +
                ", cliente='" + cliente + '\'' +
                ", valor=" + valor +
                ", produto=" + produto +
                ", fkProdutoId=" + fkProdutoId +
                ", clienteNome='" + clienteNome + '\'' +
                ", fornecedorNome='" + fornecedorNome + '\'' +
                ", produtoNome='" + produtoNome + '\'' +
                '}';
    }
}
