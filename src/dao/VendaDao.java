package dao;

import database.DbConnection;
import interfaces.DaoInterface;
import model.VendaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para realizar operações de INSERT e SELECT na tabela 'venda'.
 */
public class VendaDao implements DaoInterface {

    private VendaModel venda;

    // Construtores
    public VendaDao() {}

    public VendaDao(VendaModel venda) {
        this.venda = venda;
    }

    // Getters e Setters
    public VendaModel getVenda() {
        return venda;
    }

    public void setVenda(VendaModel venda) {
        this.venda = venda;
    }

    // ==========================================
    // Métodos da DaoInterface
    // ==========================================

    @Override
    public void save() {
        if (this.venda != null) {
            save(this.venda);
        } else {
            System.err.println("[ERRO] Nenhuma venda associada para salvar.");
        }
    }

    @Override
    public void update() {
        System.err.println("[INFO] Operação de UPDATE não suportada/requerida para Vendas.");
    }

    @Override
    public void delete() {
        System.err.println("[INFO] Operação de DELETE não suportada/requerida para Vendas.");
    }

    // ==========================================
    // Métodos de Banco de Dados
    // ==========================================

    /**
     * Insere uma nova venda no banco de dados.
     * @param v A venda a ser inserida
     * @return true se salva com sucesso, false caso contrário
     */
    public boolean save(VendaModel v) {
        String sql = "INSERT INTO venda (id, fornecedor, cliente, valor, produto, fk_produto_id) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return false;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, v.getId());
            stmt.setString(2, v.getFornecedor());
            stmt.setString(3, v.getCliente());
            stmt.setInt(4, v.getValor());
            
            if (v.getProduto() != null) {
                stmt.setInt(5, v.getProduto());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            if (v.getFkProdutoId() != null) {
                stmt.setInt(6, v.getFkProdutoId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao salvar venda: " + e.getMessage());
            return false;
        } finally {
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
    }

    /**
     * Busca uma venda por seu ID.
     */
    public VendaModel findById(Integer id) {
        String sql = "SELECT id, fornecedor, cliente, valor, produto, fk_produto_id FROM venda WHERE id = ?";
        Connection conn = DbConnection.getConnection();
        if (conn == null) return null;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                VendaModel v = new VendaModel();
                v.setId(rs.getInt("id"));
                v.setFornecedor(rs.getString("fornecedor"));
                v.setCliente(rs.getString("cliente"));
                v.setValor(rs.getInt("valor"));
                v.setProduto(rs.getInt("produto"));
                v.setFkProdutoId(rs.getInt("fk_produto_id"));
                return v;
            }
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao buscar venda por ID: " + e.getMessage());
        } finally {
            DbConnection.closeResultSet(rs);
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
        return null;
    }

    /**
     * Retorna a lista de todas as vendas cadastradas, fazendo JOIN com
     * cliente, fornecedor e produto para obter seus respectivos nomes.
     * @return Lista contendo vendas com nomes preenchidos
     */
    public List<VendaModel> findAllWithNames() {
        List<VendaModel> lista = new ArrayList<>();
        String sql = "SELECT v.id, v.fornecedor, v.cliente, v.valor, v.produto, v.fk_produto_id, " +
                     "c.nome AS cliente_nome, " +
                     "f.nome AS fornecedor_nome, " +
                     "p.nome AS produto_nome " +
                     "FROM venda v " +
                     "LEFT JOIN cliente c ON v.cliente = c.cpf " +
                     "LEFT JOIN fornecedor f ON v.fornecedor = f.cnpj " +
                     "LEFT JOIN produto p ON v.fk_produto_id = p.id";

        Connection conn = DbConnection.getConnection();
        if (conn == null) return lista;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                VendaModel v = new VendaModel();
                v.setId(rs.getInt("id"));
                v.setFornecedor(rs.getString("fornecedor"));
                v.setCliente(rs.getString("cliente"));
                v.setValor(rs.getInt("valor"));
                v.setProduto(rs.getInt("produto"));
                v.setFkProdutoId(rs.getInt("fk_produto_id"));

                // Atributos de JOIN
                v.setClienteNome(rs.getString("cliente_nome"));
                v.setFornecedorNome(rs.getString("fornecedor_nome"));
                v.setProdutoNome(rs.getString("produto_nome"));

                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao listar vendas com nomes: " + e.getMessage());
        } finally {
            DbConnection.closeResultSet(rs);
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
        }
        return lista;
    }
}
