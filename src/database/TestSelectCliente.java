package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe de teste para validar a conexão com o banco de dados MySQL
 * realizando uma consulta (SELECT) na tabela 'cliente'.
 */
public class TestSelectCliente {

    public static void main(String[] args) {
        System.out.println("Iniciando teste de consulta ao banco de dados...");

        // Obter conexão
        Connection conn = DbConnection.getConnection();
        if (conn == null) {
            System.err.println("[ERRO] Não foi possível estabelecer conexão. Verifique o arquivo .env e o status do MySQL.");
            return;
        }

        System.out.println("Conexão estabelecida com sucesso!");
        System.out.println("Executando consulta na tabela 'cliente'...\n");

        String sql = "SELECT cpf, nome, email, genero, telefone FROM cliente";
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            boolean encontrouClientes = false;

            System.out.println("----------------------------------------------------------------------------------");
            System.out.printf("%-15s | %-25s | %-25s | %-10s\n", "CPF", "NOME", "EMAIL", "GÊNERO");
            System.out.println("----------------------------------------------------------------------------------");

            while (rs.next()) {
                encontrouClientes = true;
                String cpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String genero = rs.getString("genero");

                System.out.printf("%-15s | %-25s | %-25s | %-10s\n", cpf, nome, email, genero);
            }
            System.out.println("----------------------------------------------------------------------------------");

            if (!encontrouClientes) {
                System.out.println("Aviso: A tabela 'cliente' está vazia. Certifique-se de rodar as inserções de seed data.");
            }

        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao executar a consulta SELECT:");
            System.err.println("Detalhes: " + e.getMessage());
        } finally {
            // Fechamento seguro dos recursos
            DbConnection.closeResultSet(rs);
            DbConnection.closeStatement(stmt);
            DbConnection.closeConnection(conn);
            System.out.println("\nConexão fechada e recursos liberados com segurança.");
        }
    }
}
