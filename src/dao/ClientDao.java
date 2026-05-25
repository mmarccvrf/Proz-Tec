package dao;

import database.DbConnection;
import interfaces.DaoInterface;
import model.ClienteModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para realizar operações CRUD na tabela 'cliente'.
 */
public class ClientDao implements DaoInterface {

    private ClienteModel cliente;

    // Construtores
    public ClientDao() {}

    public ClientDao(ClienteModel cliente) {
        this.cliente = cliente;
    }

    // Getters/Setters para o cliente associado
    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    // ==========================================
    // Métodos da DaoInterface (Baseados no estado)
    // ==========================================

    @Override
    public void save() {
        if (this.cliente != null) {
            save(this.cliente);
        } else {
            System.err.println("[ERRO] Nenhum cliente associado para salvar.");
        }
    }

    @Override
    public void update() {
        if (this.cliente != null) {
            update(this.cliente);
        } else {
            System.err.println("[ERRO] Nenhum cliente associado para atualizar.");
        }
    }

    @Override
    public void delete() {
        if (this.cliente != null) {
            delete(this.cliente.getCpf());
        } else {
            System.err.println("[ERRO] Nenhum cliente associado para excluir.");
        }
    }

    // ==========================================
    // Métodos CRUD Diretos (Banco de Dados)
    // ==========================================

    /**
     * Insere um novo cliente no banco de dados.
     * @param cli O cliente a ser salvo
     * @return true se inserido com sucesso, false caso contrário
     */
    public boolean save(ClienteModel cli) {
        String sql = "INSERT INTO cliente (cpf, nome, genero, telefone, email, senha, fk_venda_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return false;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cli.getCpf());
            stmt.setString(2, cli.getNome());
            stmt.setString(3, cli.getGenero());
            stmt.setString(4, cli.getTelefone());
            stmt.setString(5, cli.getEmail());
            stmt.setString(6, cli.getSenha());

            if (cli.getFkVendaId() != null) {
                stmt.setInt(7, cli.getFkVendaId());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao salvar cliente: " + e.getMessage());
            return false;
        } finally {
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
    }

    /**
     * Atualiza as informações de um cliente existente no banco de dados.
     * @param cli O cliente a ser atualizado
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean update(ClienteModel cli) {
        String sql = "UPDATE cliente SET nome = ?, genero = ?, telefone = ?, email = ?, senha = ?, fk_venda_id = ? WHERE cpf = ?";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return false;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cli.getNome());
            stmt.setString(2, cli.getGenero());
            stmt.setString(3, cli.getTelefone());
            stmt.setString(4, cli.getEmail());
            stmt.setString(5, cli.getSenha());

            if (cli.getFkVendaId() != null) {
                stmt.setInt(6, cli.getFkVendaId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setString(7, cli.getCpf());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao atualizar cliente: " + e.getMessage());
            return false;
        } finally {
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
    }

    /**
     * Exclui um cliente do banco de dados com base no CPF.
     * @param cpf O CPF do cliente a ser deletado
     * @return true se deletado com sucesso, false caso contrário
     */
    public boolean delete(String cpf) {
        String sql = "DELETE FROM cliente WHERE cpf = ?";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return false;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao excluir cliente: " + e.getMessage());
            return false;
        } finally {
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
    }

    /**
     * Busca um cliente pelo seu CPF.
     * @param cpf O CPF a ser pesquisado
     * @return ClienteModel preenchido, ou null caso não encontrado
     */
    public ClienteModel findByCpf(String cpf) {
        String sql = "SELECT cpf, nome, genero, telefone, email, senha, fk_venda_id FROM cliente WHERE cpf = ?";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return null;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();

            if (rs.next()) {
                ClienteModel cli = new ClienteModel();
                cli.setCpf(rs.getString("cpf"));
                cli.setNome(rs.getString("nome"));
                cli.setGenero(rs.getString("genero"));
                cli.setTelefone(rs.getString("telefone"));
                cli.setEmail(rs.getString("email"));
                cli.setSenha(rs.getString("senha"));
                
                int fkVenda = rs.getInt("fk_venda_id");
                if (!rs.wasNull()) {
                    cli.setFkVendaId(fkVenda);
                } else {
                    cli.setFkVendaId(null);
                }
                
                return cli;
            }
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao buscar cliente por CPF: " + e.getMessage());
        } finally {
            DbConnection.closeResultSet(rs);
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
        return null;
    }

    /**
     * Retorna a lista de todos os clientes cadastrados.
     * @return Lista contendo todos os clientes cadastrados
     */
    public List<ClienteModel> findAll() {
        List<ClienteModel> lista = new ArrayList<>();
        String sql = "SELECT cpf, nome, genero, telefone, email, senha, fk_venda_id FROM cliente";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return lista;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ClienteModel cli = new ClienteModel();
                cli.setCpf(rs.getString("cpf"));
                cli.setNome(rs.getString("nome"));
                cli.setGenero(rs.getString("genero"));
                cli.setTelefone(rs.getString("telefone"));
                cli.setEmail(rs.getString("email"));
                cli.setSenha(rs.getString("senha"));
                
                int fkVenda = rs.getInt("fk_venda_id");
                if (!rs.wasNull()) {
                    cli.setFkVendaId(fkVenda);
                } else {
                    cli.setFkVendaId(null);
                }
                
                lista.add(cli);
            }
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao listar clientes: " + e.getMessage());
        } finally {
            DbConnection.closeResultSet(rs);
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
        return lista;
    }
}
