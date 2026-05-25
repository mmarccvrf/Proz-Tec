package dao;

import database.DbConnection;
import interfaces.DaoInterface;
import model.FornecedorModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para realizar operações CRUD na tabela 'fornecedor'.
 */
public class FornecedorDao implements DaoInterface {

    private FornecedorModel fornecedor;

    // Construtores
    public FornecedorDao() {}

    public FornecedorDao(FornecedorModel fornecedor) {
        this.fornecedor = fornecedor;
    }

    // Getters/Setters para o fornecedor associado
    public FornecedorModel getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorModel fornecedor) {
        this.fornecedor = fornecedor;
    }

    // ==========================================
    // Métodos da DaoInterface (Baseados no estado)
    // ==========================================

    @Override
    public void save() {
        if (this.fornecedor != null) {
            save(this.fornecedor);
        } else {
            System.err.println("[ERRO] Nenhum fornecedor associado para salvar.");
        }
    }

    @Override
    public void update() {
        if (this.fornecedor != null) {
            update(this.fornecedor);
        } else {
            System.err.println("[ERRO] Nenhum fornecedor associado para atualizar.");
        }
    }

    @Override
    public void delete() {
        if (this.fornecedor != null) {
            delete(this.fornecedor.getCnpj());
        } else {
            System.err.println("[ERRO] Nenhum fornecedor associado para excluir.");
        }
    }

    // ==========================================
    // Métodos CRUD Diretos (Banco de Dados)
    // ==========================================

    /**
     * Insere um novo fornecedor no banco de dados.
     */
    public boolean save(FornecedorModel f) {
        String sql = "INSERT INTO fornecedor (cnpj, nome, atuacao, telefone, email, senha, fk_venda_id, fk_cliente_cpf) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return false;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, f.getCnpj());
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getAtuacao());
            stmt.setString(4, f.getTelefone());
            stmt.setString(5, f.getEmail());
            stmt.setString(6, f.getSenha());

            if (f.getFkVendaId() != null) {
                stmt.setInt(7, f.getFkVendaId());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }

            if (f.getFkClienteCpf() != null) {
                stmt.setString(8, f.getFkClienteCpf());
            } else {
                stmt.setNull(8, Types.VARCHAR);
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao salvar fornecedor: " + e.getMessage());
            return false;
        } finally {
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
    }

    /**
     * Atualiza as informações de um fornecedor existente.
     */
    public boolean update(FornecedorModel f) {
        String sql = "UPDATE fornecedor SET nome = ?, atuacao = ?, telefone = ?, email = ?, senha = ?, fk_venda_id = ?, fk_cliente_cpf = ? WHERE cnpj = ?";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return false;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getAtuacao());
            stmt.setString(3, f.getTelefone());
            stmt.setString(4, f.getEmail());
            stmt.setString(5, f.getSenha());

            if (f.getFkVendaId() != null) {
                stmt.setInt(6, f.getFkVendaId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            if (f.getFkClienteCpf() != null) {
                stmt.setString(7, f.getFkClienteCpf());
            } else {
                stmt.setNull(7, Types.VARCHAR);
            }

            stmt.setString(8, f.getCnpj());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao atualizar fornecedor: " + e.getMessage());
            return false;
        } finally {
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
    }

    /**
     * Exclui um fornecedor pelo CNPJ.
     */
    public boolean delete(String cnpj) {
        String sql = "DELETE FROM fornecedor WHERE cnpj = ?";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return false;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cnpj);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao excluir fornecedor: " + e.getMessage());
            return false;
        } finally {
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
    }

    /**
     * Busca um fornecedor pelo seu CNPJ.
     */
    public FornecedorModel findByCnpj(String cnpj) {
        String sql = "SELECT cnpj, nome, atuacao, telefone, email, senha, fk_venda_id, fk_cliente_cpf FROM fornecedor WHERE cnpj = ?";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return null;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cnpj);
            rs = stmt.executeQuery();

            if (rs.next()) {
                FornecedorModel f = new FornecedorModel();
                f.setCnpj(rs.getString("cnpj"));
                f.setNome(rs.getString("nome"));
                f.setAtuacao(rs.getString("atuacao"));
                f.setTelefone(rs.getString("telefone"));
                f.setEmail(rs.getString("email"));
                f.setSenha(rs.getString("senha"));

                int fkVenda = rs.getInt("fk_venda_id");
                if (!rs.wasNull()) {
                    f.setFkVendaId(fkVenda);
                } else {
                    f.setFkVendaId(null);
                }

                String fkCliente = rs.getString("fk_cliente_cpf");
                if (!rs.wasNull()) {
                    f.setFkClienteCpf(fkCliente);
                } else {
                    f.setFkClienteCpf(null);
                }

                return f;
            }
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao buscar fornecedor por CNPJ: " + e.getMessage());
        } finally {
            DbConnection.closeResultSet(rs);
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
        return null;
    }

    /**
     * Retorna a lista de todos os fornecedores cadastrados.
     */
    public List<FornecedorModel> findAll() {
        List<FornecedorModel> lista = new ArrayList<>();
        String sql = "SELECT cnpj, nome, atuacao, telefone, email, senha, fk_venda_id, fk_cliente_cpf FROM fornecedor";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return lista;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                FornecedorModel f = new FornecedorModel();
                f.setCnpj(rs.getString("cnpj"));
                f.setNome(rs.getString("nome"));
                f.setAtuacao(rs.getString("atuacao"));
                f.setTelefone(rs.getString("telefone"));
                f.setEmail(rs.getString("email"));
                f.setSenha(rs.getString("senha"));

                int fkVenda = rs.getInt("fk_venda_id");
                if (!rs.wasNull()) {
                    f.setFkVendaId(fkVenda);
                } else {
                    f.setFkVendaId(null);
                }

                String fkCliente = rs.getString("fk_cliente_cpf");
                if (!rs.wasNull()) {
                    f.setFkClienteCpf(fkCliente);
                } else {
                    f.setFkClienteCpf(null);
                }

                lista.add(f);
            }
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao listar fornecedores: " + e.getMessage());
        } finally {
            DbConnection.closeResultSet(rs);
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
        return lista;
    }
}
