package controller;

import dao.ClientDao;
import dao.FornecedorDao;
import model.ClienteModel;
import model.FornecedorModel;

import java.util.List;

/**
 * Controlador para a entidade Fornecedor.
 * Gerencia validação de regras de negócio e integridade de chaves estrangeiras.
 */
public class FornecedorController {

    private final FornecedorDao fornecedorDao;
    private final ClientDao clientDao; // Para verificar existência de cliente

    public FornecedorController() {
        this.fornecedorDao = new FornecedorDao();
        this.clientDao = new ClientDao();
    }

    /**
     * Valida e cadastra um novo fornecedor.
     */
    public String cadastrarFornecedor(String cnpj, String nome, String atuacao, String telefone, String email, String senha, Integer fkVendaId, String fkClienteCpf) {
        // Validações básicas
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return "[ERRO] O CNPJ é obrigatório.";
        }

        // Limpar formatação do CNPJ (manter apenas números)
        String cnpjLimpo = cnpj.replaceAll("\\D", "");
        if (cnpjLimpo.length() != 14) {
            return "[ERRO] O CNPJ deve conter exatamente 14 dígitos numéricos.";
        }

        if (nome == null || nome.trim().isEmpty()) {
            return "[ERRO] O nome é obrigatório.";
        }

        if (atuacao == null || atuacao.trim().isEmpty()) {
            return "[ERRO] A área de atuação é obrigatória.";
        }

        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return "[ERRO] E-mail inválido ou não informado.";
        }

        if (senha == null || senha.trim().length() < 4) {
            return "[ERRO] A senha deve conter pelo menos 4 caracteres.";
        }

        // Verificar se CNPJ já existe cadastrado
        if (fornecedorDao.findByCnpj(cnpjLimpo) != null) {
            return "[ERRO] Já existe um fornecedor cadastrado com o CNPJ: " + cnpjLimpo;
        }

        // Validação da Chave Estrangeira do Cliente se informada
        String clienteCpfLimpo = null;
        if (fkClienteCpf != null && !fkClienteCpf.trim().isEmpty()) {
            clienteCpfLimpo = fkClienteCpf.replaceAll("\\D", "");
            ClienteModel cliente = clientDao.findByCpf(clienteCpfLimpo);
            if (cliente == null) {
                return "[ERRO] Não foi possível vincular. O CPF de cliente " + clienteCpfLimpo + " não está cadastrado.";
            }
        }

        // Criar modelo
        FornecedorModel novoFornecedor = new FornecedorModel(
                cnpjLimpo,
                nome.trim(),
                atuacao.trim(),
                telefone,
                email.trim(),
                senha,
                fkVendaId,
                clienteCpfLimpo
        );

        // Salvar via DAO
        boolean sucesso = fornecedorDao.save(novoFornecedor);
        if (sucesso) {
            return "[SUCESSO] Fornecedor cadastrado com sucesso!";
        } else {
            return "[ERRO] Falha interna ao salvar fornecedor no banco de dados.";
        }
    }

    /**
     * Retorna a lista de todos os fornecedores.
     */
    public List<FornecedorModel> listarTodos() {
        return fornecedorDao.findAll();
    }

    /**
     * Busca um fornecedor por CNPJ.
     */
    public FornecedorModel buscarPorCnpj(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return null;
        }
        String cnpjLimpo = cnpj.replaceAll("\\D", "");
        return fornecedorDao.findByCnpj(cnpjLimpo);
    }

    /**
     * Valida e atualiza os dados de um fornecedor existente.
     */
    public String atualizarFornecedor(String cnpj, String nome, String atuacao, String telefone, String email, String senha, Integer fkVendaId, String fkClienteCpf) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return "[ERRO] CNPJ inválido para atualização.";
        }

        String cnpjLimpo = cnpj.replaceAll("\\D", "");
        FornecedorModel fornecedorExistente = fornecedorDao.findByCnpj(cnpjLimpo);
        if (fornecedorExistente == null) {
            return "[ERRO] Nenhum fornecedor encontrado com o CNPJ informado.";
        }

        // Validar e atualizar atributos gerais
        if (nome != null && !nome.trim().isEmpty()) {
            fornecedorExistente.setNome(nome.trim());
        }
        if (atuacao != null && !atuacao.trim().isEmpty()) {
            fornecedorExistente.setAtuacao(atuacao.trim());
        }
        if (telefone != null && !telefone.trim().isEmpty()) {
            fornecedorExistente.setTelefone(telefone);
        }
        if (email != null && !email.trim().isEmpty()) {
            if (!email.contains("@")) {
                return "[ERRO] E-mail informado é inválido.";
            }
            fornecedorExistente.setEmail(email.trim());
        }
        if (senha != null && !senha.trim().isEmpty()) {
            if (senha.trim().length() < 4) {
                return "[ERRO] A nova senha deve ter no mínimo 4 caracteres.";
            }
            fornecedorExistente.setSenha(senha);
        }

        // fkVendaId pode ser atualizado (aceita nulo se editado com 'remover')
        fornecedorExistente.setFkVendaId(fkVendaId);

        // Validação e atualização de fkClienteCpf
        if (fkClienteCpf != null) {
            if (fkClienteCpf.trim().isEmpty() || fkClienteCpf.equalsIgnoreCase("remover")) {
                fornecedorExistente.setFkClienteCpf(null);
            } else {
                String clienteCpfLimpo = fkClienteCpf.replaceAll("\\D", "");
                ClienteModel cliente = clientDao.findByCpf(clienteCpfLimpo);
                if (cliente == null) {
                    return "[ERRO] Não foi possível vincular. O CPF de cliente " + clienteCpfLimpo + " não está cadastrado.";
                }
                fornecedorExistente.setFkClienteCpf(clienteCpfLimpo);
            }
        }

        // Executar atualização no banco
        boolean sucesso = fornecedorDao.update(fornecedorExistente);
        if (sucesso) {
            return "[SUCESSO] Cadastro do fornecedor atualizado com sucesso!";
        } else {
            return "[ERRO] Falha interna ao atualizar fornecedor no banco de dados.";
        }
    }

    /**
     * Exclui um fornecedor por CNPJ.
     */
    public String excluirFornecedor(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return "[ERRO] CNPJ inválido.";
        }

        String cnpjLimpo = cnpj.replaceAll("\\D", "");
        FornecedorModel fornecedorExistente = fornecedorDao.findByCnpj(cnpjLimpo);
        if (fornecedorExistente == null) {
            return "[ERRO] Fornecedor não encontrado.";
        }

        boolean sucesso = fornecedorDao.delete(cnpjLimpo);
        if (sucesso) {
            return "[SUCESSO] Fornecedor com CNPJ " + cnpjLimpo + " excluído com sucesso!";
        } else {
            return "[ERRO] Falha ao excluir fornecedor. Verifique se ele está vinculado a outras tabelas no banco de dados.";
        }
    }
}
