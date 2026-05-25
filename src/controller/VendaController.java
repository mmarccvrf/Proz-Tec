package controller;

import dao.ClientDao;
import dao.FornecedorDao;
import dao.ProdutoDao;
import dao.VendaDao;
import model.ClienteModel;
import model.FornecedorModel;
import model.ProdutoModel;
import model.VendaModel;

import java.util.List;

/**
 * Controlador para a entidade Venda.
 * Gerencia validação de regras de negócio e garante integridade relacional.
 */
public class VendaController {

    private final VendaDao vendaDao;
    private final ClientDao clientDao;
    private final FornecedorDao fornecedorDao;
    private final ProdutoDao produtoDao;

    public VendaController() {
        this.vendaDao = new VendaDao();
        this.clientDao = new ClientDao();
        this.fornecedorDao = new FornecedorDao();
        this.produtoDao = new ProdutoDao();
    }

    /**
     * Valida os relacionamentos e registra uma nova venda.
     * @param id ID da venda
     * @param clienteCpf CPF do cliente associado
     * @param fornecedorCnpj CNPJ do fornecedor associado
     * @param produtoId ID do produto associado
     * @return Mensagem de sucesso ou erro
     */
    public String cadastrarVenda(Integer id, String clienteCpf, String fornecedorCnpj, Integer produtoId) {
        // 1. Validações básicas do ID da Venda
        if (id == null) {
            return "[ERRO] O ID da venda é obrigatório.";
        }
        if (id <= 0) {
            return "[ERRO] O ID da venda deve ser maior que zero.";
        }

        // Verificar se já existe uma venda com esse ID
        if (vendaDao.findById(id) != null) {
            return "[ERRO] Já existe uma venda cadastrada com o ID: " + id;
        }

        // 2. Buscar e validar o Cliente
        if (clienteCpf == null || clienteCpf.trim().isEmpty()) {
            return "[ERRO] O CPF do cliente é obrigatório.";
        }
        String clienteCpfLimpo = clienteCpf.replaceAll("\\D", "");
        ClienteModel cliente = clientDao.findByCpf(clienteCpfLimpo);
        if (cliente == null) {
            return "[ERRO] Cliente com CPF " + clienteCpfLimpo + " não encontrado.";
        }

        // 3. Buscar e validar o Fornecedor
        if (fornecedorCnpj == null || fornecedorCnpj.trim().isEmpty()) {
            return "[ERRO] O CNPJ do fornecedor é obrigatório.";
        }
        String fornecedorCnpjLimpo = fornecedorCnpj.replaceAll("\\D", "");
        FornecedorModel fornecedor = fornecedorDao.findByCnpj(fornecedorCnpjLimpo);
        if (fornecedor == null) {
            return "[ERRO] Fornecedor com CNPJ " + fornecedorCnpjLimpo + " não encontrado.";
        }

        // 4. Buscar e validar o Produto
        if (produtoId == null) {
            return "[ERRO] O ID do produto é obrigatório.";
        }
        ProdutoModel produto = produtoDao.findById(produtoId);
        if (produto == null) {
            return "[ERRO] Produto com ID " + produtoId + " não encontrado.";
        }

        // 5. Obter valor do produto e criar modelo da venda
        Integer valorVenda = produto.getValor();

        VendaModel novaVenda = new VendaModel(
                id,
                fornecedorCnpjLimpo,
                clienteCpfLimpo,
                valorVenda,
                produtoId, // campo 'produto'
                produtoId  // campo 'fk_produto_id'
        );

        // 6. Persistir venda no banco via DAO
        boolean sucesso = vendaDao.save(novaVenda);
        if (sucesso) {
            return "[SUCESSO] Venda registrada com sucesso! Valor total: R$ " + valorVenda;
        } else {
            return "[ERRO] Falha interna ao registrar venda no banco de dados.";
        }
    }

    /**
     * Retorna a lista de todas as vendas com os respectivos nomes preenchidos.
     */
    public List<VendaModel> listarTodas() {
        return vendaDao.findAllWithNames();
    }
}
