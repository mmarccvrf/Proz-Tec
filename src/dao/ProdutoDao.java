package dao;

import database.DbConnection;
import interfaces.DaoInterface;
import model.ProdutoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para realizar operações CRUD na tabela 'produto'.
 */
public class ProdutoDao implements DaoInterface {

    private ProdutoModel produto;

    // Construtores
    public ProdutoDao() {}

    public ProdutoDao(ProdutoModel produto) {
        this.produto = produto;
    }

    // Getters/Setters para o produto associado
    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }

    // ==========================================
    // Métodos da DaoInterface (Baseados no estado)
    // ==========================================

    @Override
    public void save() {
        if (this.produto != null) {
            save(this.produto);
        } else {
            System.err.println("[ERRO] Nenhum produto associado para salvar.");
        }
    }

    @Override
    public void update() {
        if (this.produto != null) {
            update(this.produto);
        } else {
            System.err.println("[ERRO] Nenhum produto associado para atualizar.");
        }
    }

    @Override
    public void delete() {
        if (this.produto != null) {
            delete(this.produto.getId());
        } else {
            System.err.println("[ERRO] Nenhum produto associado para excluir.");
        }
    }

    // ==========================================
    // Métodos CRUD Diretos (Banco de Dados)
    // ==========================================

    /**
     * Insere um novo produto no banco de dados.
     * @param prod O produto a ser salvo
     * @return true se inserido com sucesso, false caso contrário
     */
    public boolean save(ProdutoModel prod) {
        String sql = "INSERT INTO produto (id, nome, quant, valor, fornecedor) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return false;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, prod.getId());
            stmt.setString(2, prod.getNome());
            stmt.setInt(3, prod.getQuant());
            stmt.setInt(4, prod.getValor());

            if (prod.getFornecedor() != null) {
                stmt.setInt(5, prod.getFornecedor());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao salvar produto: " + e.getMessage());
            return false;
        } finally {
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
    }

    /**
     * Atualiza as informações de um produto existente no banco de dados.
     * @param prod O produto a ser atualizado
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean update(ProdutoModel prod) {
        String sql = "UPDATE produto SET nome = ?, quant = ?, valor = ?, fornecedor = ? WHERE id = ?";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return false;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, prod.getNome());
            stmt.setInt(2, prod.getQuant());
            stmt.setInt(3, prod.getValor());

            if (prod.getFornecedor() != null) {
                stmt.setInt(4, prod.getFornecedor());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setInt(5, prod.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao atualizar produto: " + e.getMessage());
            return false;
        } finally {
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
    }

    /**
     * Exclui um produto do banco de dados com base no ID.
     * @param id O ID do produto a ser deletado
     * @return true se deletado com sucesso, false caso contrário
     */
    public boolean delete(Integer id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return false;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao excluir produto: " + e.getMessage());
            return false;
        } finally {
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
    }

    /**
     * Busca um produto pelo seu ID.
     * @param id O ID a ser pesquisado
     * @return ProdutoModel preenchido, ou null caso não encontrado
     */
    public ProdutoModel findById(Integer id) {
        String sql = "SELECT id, nome, quant, valor, fornecedor FROM produto WHERE id = ?";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return null;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                ProdutoModel prod = new ProdutoModel();
                prod.setId(rs.getInt("id"));
                prod.setNome(rs.getString("nome"));
                prod.setQuant(rs.getInt("quant"));
                prod.setValor(rs.getInt("valor"));

                int forn = rs.getInt("fornecedor");
                if (!rs.wasNull()) {
                    prod.setFornecedor(forn);
                } else {
                    prod.setFornecedor(null);
                }

                return prod;
            }
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao buscar produto por ID: " + e.getMessage());
        } finally {
            DbConnection.closeResultSet(rs);
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
        return null;
    }

    /**
     * Retorna a lista de todos os produtos cadastrados.
     * @return Lista contendo todos os produtos cadastrados
     */
    public List<ProdutoModel> findAll() {
        List<ProdutoModel> lista = new ArrayList<>();
        String sql = "SELECT id, nome, quant, valor, fornecedor FROM produto";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return lista;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProdutoModel prod = new ProdutoModel();
                prod.setId(rs.getInt("id"));
                prod.setNome(rs.getString("nome"));
                prod.setQuant(rs.getInt("quant"));
                prod.setValor(rs.getInt("valor"));

                int forn = rs.getInt("fornecedor");
                if (!rs.wasNull()) {
                    prod.setFornecedor(forn);
                } else {
                    prod.setFornecedor(null);
                }

                lista.add(prod);
            }
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao listar produtos: " + e.getMessage());
        } finally {
            DbConnection.closeResultSet(rs);
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
        return lista;
    }
}
