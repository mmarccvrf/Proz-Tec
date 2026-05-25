package controller;

import dao.ClientDao;
import model.ClienteModel;

import java.util.List;

/**
 * Controlador para a entidade Cliente.
 * Gerencia a validação de regras de negócio e intermedia a View e o DAO.
 */
public class ClienteController {

    private final ClientDao clientDao;

    public ClienteController() {
        this.clientDao = new ClientDao();
    }

    /**
     * Valida e cadastra um novo cliente.
     */
    public String cadastrarCliente(String cpf, String nome, String genero, String telefone, String email, String senha, Integer fkVendaId) {
        // Validações básicas
        if (cpf == null || cpf.trim().isEmpty()) {
            return "[ERRO] O CPF é obrigatório.";
        }
        
        // Limpar formatação do CPF (remover pontos, traços, etc.)
        String cpfLimpo = cpf.replaceAll("\\D", "");
        if (cpfLimpo.length() != 11) {
            return "[ERRO] O CPF deve conter exatamente 11 dígitos numéricos.";
        }

        if (nome == null || nome.trim().isEmpty()) {
            return "[ERRO] O nome é obrigatório.";
        }

        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return "[ERRO] E-mail inválido ou não informado.";
        }

        if (senha == null || senha.trim().length() < 4) {
            return "[ERRO] A senha deve conter pelo menos 4 caracteres.";
        }

        // Verificar se já existe cadastrado
        if (clientDao.findByCpf(cpfLimpo) != null) {
            return "[ERRO] Já existe um cliente cadastrado com o CPF: " + cpfLimpo;
        }

        // Criar modelo
        ClienteModel novoCliente = new ClienteModel(cpfLimpo, nome.trim(), genero, telefone, email.trim(), senha, fkVendaId);
        
        // Salvar via DAO
        boolean sucesso = clientDao.save(novoCliente);
        if (sucesso) {
            return "[SUCESSO] Cliente cadastrado com sucesso!";
        } else {
            return "[ERRO] Falha interna ao salvar cliente no banco de dados.";
        }
    }

    /**
     * Retorna a lista de todos os clientes.
     */
    public List<ClienteModel> listarTodos() {
        return clientDao.findAll();
    }

    /**
     * Busca um cliente pelo CPF.
     */
    public ClienteModel buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return null;
        }
        String cpfLimpo = cpf.replaceAll("\\D", "");
        return clientDao.findByCpf(cpfLimpo);
    }

    /**
     * Valida e atualiza os dados de um cliente existente.
     */
    public String atualizarCliente(String cpf, String nome, String genero, String telefone, String email, String senha, Integer fkVendaId) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return "[ERRO] CPF inválido para atualização.";
        }
        
        String cpfLimpo = cpf.replaceAll("\\D", "");
        ClienteModel clienteExistente = clientDao.findByCpf(cpfLimpo);
        if (clienteExistente == null) {
            return "[ERRO] Nenhum cliente encontrado com o CPF informado.";
        }

        // Validar e atualizar apenas campos preenchidos
        if (nome != null && !nome.trim().isEmpty()) {
            clienteExistente.setNome(nome.trim());
        }
        if (genero != null && !genero.trim().isEmpty()) {
            clienteExistente.setGenero(genero);
        }
        if (telefone != null && !telefone.trim().isEmpty()) {
            clienteExistente.setTelefone(telefone);
        }
        if (email != null && !email.trim().isEmpty()) {
            if (!email.contains("@")) {
                return "[ERRO] E-mail informado é inválido.";
            }
            clienteExistente.setEmail(email.trim());
        }
        if (senha != null && !senha.trim().isEmpty()) {
            if (senha.trim().length() < 4) {
                return "[ERRO] A nova senha deve ter no mínimo 4 caracteres.";
            }
            clienteExistente.setSenha(senha);
        }
        
        // fkVendaId pode ser atualizado opcionalmente
        if (fkVendaId != null) {
            clienteExistente.setFkVendaId(fkVendaId);
        }

        // Executar atualização
        boolean sucesso = clientDao.update(clienteExistente);
        if (sucesso) {
            return "[SUCESSO] Cadastro do cliente atualizado com sucesso!";
        } else {
            return "[ERRO] Falha interna ao atualizar cliente no banco de dados.";
        }
    }

    /**
     * Exclui um cliente com base no CPF.
     */
    public String excluirCliente(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return "[ERRO] CPF inválido.";
        }
        
        String cpfLimpo = cpf.replaceAll("\\D", "");
        ClienteModel clienteExistente = clientDao.findByCpf(cpfLimpo);
        if (clienteExistente == null) {
            return "[ERRO] Cliente não encontrado.";
        }

        boolean sucesso = clientDao.delete(cpfLimpo);
        if (sucesso) {
            return "[SUCESSO] Cliente com CPF " + cpfLimpo + " excluído com sucesso!";
        } else {
            return "[ERRO] Falha ao excluir cliente. Verifique se ele está vinculado a outras tabelas (ex: Venda).";
        }
    }
}
