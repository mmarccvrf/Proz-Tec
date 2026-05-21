package view;

import utils.ViewUtil;
import utils.ScannerUtil;

public class View {
    public void viewMain() {
        menuLogin();
    }

    private void menuLogin() {
        String email;
        String password;

        ViewUtil.clearConsole();

        ViewUtil.linha(10);
        System.out.println("\nLOGIN");
        ViewUtil.linha(10);

        System.out.println("\nDigite seu email: ");
        email = ScannerUtil.getString();

        System.out.println("\nDigite sua senha: ");
        password = ScannerUtil.getString();

        System.out.print(email + " " + password);
    }
}
