package utils;

public class ViewUtil {
    public static void linha(int tam) {
        System.out.print('-');
        for (int i = 0; i < (tam - 1); i++) {
            System.out.print('-');
        }
    }

    public static void pularLinha() {
        System.out.println("\n");
    }

    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Erro ao limpar o terminal: " + e.getMessage());
        }
    }
}
