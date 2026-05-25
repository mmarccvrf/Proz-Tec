package controller;

import dao.ProdutoDao;
import model.ProdutoModel;

import java.util.List;

/**
 * Controlador para a entidade Produto.
 * Gerencia a validação de regras de negócio e intermedia a View e o DAO.
 */
public class ProdutoController {

    private final ProdutoDao produtoDao;

    public ProdutoController() {
        this.produtoDao = new ProdutoDao();
    }

    /**
     * Valida e cadastra um novo produto.
     */
    public String cadastrarProduto(Integer id, String nome, Integer quant, Integer valor, Integer fornecedor) {
        // Validações básicas
        if (id == null) {
            return "[ERRO] O ID do produto é obrigatório.";
        }

        if (id <= 0) {
            return "[ERRO] O ID do produto deve ser maior que zero.";
        }

        if (nome == null || nome.trim().isEmpty()) {
            return "[ERRO] O nome do produto é obrigatório.";
        }

        if (quant == null || quant < 0) {
            return "[ERRO] A quantidade do produto deve ser maior ou igual a zero.";
        }

        if (valor == null || valor < 0) {
            return "[ERRO] O valor do produto deve ser maior ou igual a zero.";
        }

        // Verificar se já existe cadastrado
        if (produtoDao.findById(id) != null) {
            return "[ERRO] Já existe um produto cadastrado com o ID: " + id;
        }

        // Criar modelo
        ProdutoModel novoProduto = new ProdutoModel(id, nome.trim(), quant, valor, fornecedor);

        // Salvar via DAO
        boolean sucesso = produtoDao.save(novoProduto);
        if (sucesso) {
            return "[SUCESSO] Produto cadastrado com sucesso!";
        } else {
            return "[ERRO] Falha interna ao salvar produto no banco de dados.";
        }
    }

    /**
     * Retorna a lista de todos os produtos.
     */
    public List<ProdutoModel> listarTodos() {
        return produtoDao.findAll();
    }

    /**
     * Busca um produto pelo ID.
     */
    public ProdutoModel buscarPorId(Integer id) {
        if (id == null) {
            return null;
        }
        return produtoDao.findById(id);
    }

    /**
     * Valida e atualiza os dados de um produto existente.
     */
    public String atualizarProduto(Integer id, String nome, Integer quant, Integer valor, Integer fornecedor) {
        if (id == null) {
            return "[ERRO] ID do produto inválido para atualização.";
        }

        ProdutoModel produtoExistente = produtoDao.findById(id);
        if (produtoExistente == null) {
            return "[ERRO] Nenhum produto encontrado com o ID informado.";
        }

        // Validar e atualizar apenas campos preenchidos/válidos
        if (nome != null && !nome.trim().isEmpty()) {
            produtoExistente.setNome(nome.trim());
        }

        if (quant != null) {
            if (quant < 0) {
                return "[ERRO] A quantidade não pode ser negativa.";
            }
            produtoExistente.setQuant(quant);
        }

        if (valor != null) {
            if (valor < 0) {
                return "[ERRO] O valor não pode ser negativo.";
            }
            produtoExistente.setValor(valor);
        }

        // Atualizar fornecedor (pode ser nulo se for excluído/limpo)
        produtoExistente.setFornecedor(fornecedor);

        // Executar atualização
        boolean sucesso = produtoDao.update(produtoExistente);
        if (sucesso) {
            return "[SUCESSO] Cadastro do produto atualizado com sucesso!";
        } else {
            return "[ERRO] Falha interna ao atualizar produto no banco de dados.";
        }
    }

    /**
     * Exclui um produto com base no ID.
     */
    public String excluirProduto(Integer id) {
        if (id == null) {
            return "[ERRO] ID inválido.";
        }

        ProdutoModel produtoExistente = produtoDao.findById(id);
        if (produtoExistente == null) {
            return "[ERRO] Produto não encontrado.";
        }

        boolean sucesso = produtoDao.delete(id);
        if (sucesso) {
            return "[SUCESSO] Produto com ID " + id + " excluído com sucesso!";
        } else {
            return "[ERRO] Falha ao excluir produto. Verifique se ele está vinculado a outras tabelas (ex: Venda).";
        }
    }
}
