package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Gerenciador de conexões com o banco de dados MySQL.
 */
public class DbConnection {

    private static final Map<String, String> env = loadEnv();

    private static final String URL = getEnv("DB_URL", "jdbc:mysql://localhost:3306/sis_proz_tec?useTimezone=true&serverTimezone=America/Sao_Paulo&useSSL=false");
    private static final String USER = getEnv("DB_USER", "root");
    private static final String PASSWORD = getEnv("DB_PASSWORD", "");

    /**
     * Carrega as variáveis do arquivo .env se ele existir.
     */
    private static Map<String, String> loadEnv() {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(".env"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Ignora linhas vazias ou comentários
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    // Remove aspas simples ou duplas ao redor do valor, se houver
                    if ((value.startsWith("\"") && value.endsWith("\"")) || 
                        (value.startsWith("'") && value.endsWith("'"))) {
                        value = value.substring(1, value.length() - 1);
                    }
                    map.put(key, value);
                }
            }
        } catch (IOException e) {
            // Arquivo .env opcional: sem erro crítico se não existir, usará o fallback/sistema
        }
        return map;
    }

    /**
     * Obtém o valor de uma variável de ambiente do arquivo .env ou do sistema.
     */
    private static String getEnv(String key, String defaultValue) {
        String value = env.get(key);
        if (value == null) {
            value = System.getenv(key);
        }
        return value != null ? value : defaultValue;
    }

    /**
     * Estabelece e retorna uma conexão com o banco de dados.
     * @return Connection ou null se falhar
     */
    public static Connection getConnection() {
        try {
            // Garante que o driver JDBC do MySQL esteja carregado
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("[ERRO] Driver JDBC do MySQL não encontrado no classpath!");
            System.err.println("Certifique-se de adicionar o driver mysql-connector-j-x.x.x.jar ao seu projeto.");
            System.err.println("Detalhes: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao se conectar com o banco de dados MySQL.");
            System.err.println("Detalhes: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fecha a conexão com segurança.
     * @param conn Conexão a ser fechada
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("[ERRO] Falha ao fechar Connection: " + e.getMessage());
            }
        }
    }

    /**
     * Fecha o Statement com segurança.
     * @param stmt Statement a ser fechado
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("[ERRO] Falha ao fechar Statement: " + e.getMessage());
            }
        }
    }

    /**
     * Fecha o ResultSet com segurança.
     * @param rs ResultSet a ser fechado
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("[ERRO] Falha ao fechar ResultSet: " + e.getMessage());
            }
        }
    }

    /**
     * Método principal para teste rápido de conexão.
     */
    public static void main(String[] args) {
        System.out.println("Tentando conectar ao banco de dados MySQL...");
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("\n=================================================");
            System.out.println("SUCESSO: Conectado ao MySQL com sucesso!");
            System.out.println("Banco de dados: sis_proz_tec");
            System.out.println("=================================================\n");
            closeConnection(conn);
        } else {
            System.out.println("\n=================================================");
            System.out.println("FALHA: Não foi possível estabelecer conexão.");
            System.out.println("Consulte os erros acima para diagnosticar o problema.");
            System.out.println("=================================================\n");
        }
    }
}
