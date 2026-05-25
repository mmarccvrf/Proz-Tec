package view;

import controller.ClienteController;
import model.ClienteModel;
import utils.ScannerUtil;
import utils.ViewUtil;

import java.util.List;

/**
 * Interface em Linha de Comando (CLI) para o sistema Proz-Tec.
 */
public class View {

    private final ClienteController controller;

    public View() {
        this.controller = new ClienteController();
    }

    /**
     * Ponto de entrada da View chamado pela classe Main.
     */
    public void viewMain() {
        boolean rodando = true;
        while (rodando) {
            exibirCabecalhoPrincipal();
            System.out.println("1. [CRUD] Gerenciar Clientes");
            System.out.println("2. Realizar Login de Exemplo (Original)");
            System.out.println("0. Sair do Sistema");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            String opcao = ScannerUtil.getString().trim();

            switch (opcao) {
                case "1":
                    menuClientes();
                    break;
                case "2":
                    menuLogin();
                    break;
                case "0":
                    System.out.println("\nEncerrando o sistema... Obrigado por utilizar Proz-Tec!");
                    rodando = false;
                    break;
                default:
                    System.out.println("\n[AVISO] Opção inválida! Tente novamente.");
                    pausar();
            }
        }
    }

    /**
     * Submenu para o CRUD de Clientes
     */
    private void menuClientes() {
        boolean noSubmenu = true;
        while (noSubmenu) {
            ViewUtil.clearConsole();
            ViewUtil.linha(50);
            System.out.println("\n       SISTEMA PROZ-TEC - GERENCIAR CLIENTES       ");
            ViewUtil.linha(50);
            System.out.println();
            System.out.println("1. Cadastrar Novo Cliente");
            System.out.println("2. Listar Todos os Clientes");
            System.out.println("3. Buscar Cliente por CPF");
            System.out.println("4. Atualizar Cadastro de Cliente");
            System.out.println("5. Excluir Cliente");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            String opcao = ScannerUtil.getString().trim();

            switch (opcao) {
                case "1":
                    cadastrarCliente();
                    break;
                case "2":
                    listarClientes();
                    break;
                case "3":
                    buscarCliente();
                    break;
                case "4":
                    atualizarCliente();
                    break;
                case "5":
                    excluirCliente();
                    break;
                case "0":
                    noSubmenu = false;
                    break;
                default:
                    System.out.println("\n[AVISO] Opção inválida! Tente novamente.");
                    pausar();
            }
        }
    }

    // ==========================================
    // Fluxos CRUD Individuais
    // ==========================================

    private void cadastrarCliente() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n              CADASTRAR NOVO CLIENTE              ");
        ViewUtil.linha(50);
        System.out.println();

        System.out.print("Digite o CPF (apenas números, 11 dígitos): ");
        String cpf = ScannerUtil.getString();

        System.out.print("Digite o Nome completo: ");
        String nome = ScannerUtil.getString();

        System.out.print("Digite o Gênero (Masculino/Feminino/Outro): ");
        String genero = ScannerUtil.getString();

        System.out.print("Digite o Telefone: ");
        String telefone = ScannerUtil.getString();

        System.out.print("Digite o E-mail: ");
        String email = ScannerUtil.getString();

        System.out.print("Digite a Senha de acesso: ");
        String senha = ScannerUtil.getString();

        System.out.print("Digite o ID da Venda (ou Enter para nenhum): ");
        String vendaInput = ScannerUtil.getString();
        Integer fkVendaId = null;
        if (!vendaInput.trim().isEmpty()) {
            try {
                fkVendaId = Integer.parseInt(vendaInput.trim());
            } catch (NumberFormatException e) {
                System.out.println("[AVISO] ID da Venda inválido. O cliente será salvo sem vínculo de venda.");
            }
        }

        System.out.println("\nEnviando dados para o controlador...");
        String resultado = controller.cadastrarCliente(cpf, nome, genero, telefone, email, senha, fkVendaId);
        System.out.println(resultado);
        pausar();
    }

    private void listarClientes() {
        ViewUtil.clearConsole();
        ViewUtil.linha(90);
        System.out.println("\n                                 LISTA DE CLIENTES CADASTRADOS                            ");
        ViewUtil.linha(90);
        System.out.println();

        List<ClienteModel> clientes = controller.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado no banco de dados.");
        } else {
            System.out.printf("%-15s | %-25s | %-25s | %-12s | %-12s\n", "CPF", "NOME", "EMAIL", "GÊNERO", "TELEFONE");
            ViewUtil.linha(90);
            System.out.println();
            for (ClienteModel c : clientes) {
                System.out.printf("%-15s | %-25s | %-25s | %-12s | %-12s\n",
                        formatarCpf(c.getCpf()),
                        truncar(c.getNome(), 25),
                        truncar(c.getEmail(), 25),
                        c.getGenero() != null ? c.getGenero() : "-",
                        c.getTelefone() != null ? c.getTelefone() : "-"
                );
            }
            ViewUtil.linha(90);
            System.out.println();
            System.out.println("Total de clientes: " + clientes.size());
        }
        pausar();
    }

    private void buscarCliente() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n               BUSCAR CLIENTE POR CPF             ");
        ViewUtil.linha(50);
        System.out.println();

        System.out.print("Digite o CPF a ser pesquisado: ");
        String cpf = ScannerUtil.getString();

        System.out.println("\nBuscando...");
        ClienteModel c = controller.buscarPorCpf(cpf);

        if (c == null) {
            System.out.println("[AVISO] Cliente não encontrado com o CPF informado.");
        } else {
            exibirFichaCliente(c);
        }
        pausar();
    }

    private void atualizarCliente() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n             ATUALIZAR CADASTRO DE CLIENTE        ");
        ViewUtil.linha(50);
        System.out.println();

        System.out.print("Digite o CPF do cliente que deseja atualizar: ");
        String cpf = ScannerUtil.getString();

        ClienteModel c = controller.buscarPorCpf(cpf);
        if (c == null) {
            System.out.println("[ERRO] Cliente não encontrado.");
            pausar();
            return;
        }

        System.out.println("\n--- Dados atuais encontrados. Pressione Enter para manter o valor atual ---");

        System.out.print("Nome [" + c.getNome() + "]: ");
        String nome = ScannerUtil.getString();
        if (nome.trim().isEmpty()) nome = c.getNome();

        System.out.print("Gênero [" + c.getGenero() + "]: ");
        String genero = ScannerUtil.getString();
        if (genero.trim().isEmpty()) genero = c.getGenero();

        System.out.print("Telefone [" + c.getTelefone() + "]: ");
        String telefone = ScannerUtil.getString();
        if (telefone.trim().isEmpty()) telefone = c.getTelefone();

        System.out.print("E-mail [" + c.getEmail() + "]: ");
        String email = ScannerUtil.getString();
        if (email.trim().isEmpty()) email = c.getEmail();

        System.out.print("Senha [" + c.getSenha() + "]: ");
        String senha = ScannerUtil.getString();
        if (senha.trim().isEmpty()) senha = c.getSenha();

        String vendaAtualLabel = c.getFkVendaId() != null ? String.valueOf(c.getFkVendaId()) : "nenhum";
        System.out.print("ID da Venda [" + vendaAtualLabel + "] (digite 'remover' para limpar ou Enter para manter): ");
        String vendaInput = ScannerUtil.getString().trim();
        Integer fkVendaId = c.getFkVendaId();

        if (!vendaInput.isEmpty()) {
            if (vendaInput.equalsIgnoreCase("remover")) {
                fkVendaId = null;
            } else {
                try {
                    fkVendaId = Integer.parseInt(vendaInput);
                } catch (NumberFormatException e) {
                    System.out.println("[AVISO] ID inválido. Mantendo o ID atual.");
                }
            }
        }

        System.out.println("\nEnviando atualizações...");
        String resultado = controller.atualizarCliente(c.getCpf(), nome, genero, telefone, email, senha, fkVendaId);
        System.out.println(resultado);
        pausar();
    }

    private void excluirCliente() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n                  EXCLUIR CLIENTE                 ");
        ViewUtil.linha(50);
        System.out.println();

        System.out.print("Digite o CPF do cliente a ser excluído: ");
        String cpf = ScannerUtil.getString();

        ClienteModel c = controller.buscarPorCpf(cpf);
        if (c == null) {
            System.out.println("[ERRO] Cliente não encontrado.");
            pausar();
            return;
        }

        exibirFichaCliente(c);

        System.out.print("AVISO: Tem certeza de que deseja EXCLUIR permanentemente este cliente? (S/N): ");
        String confirmacao = ScannerUtil.getString().trim();

        if (confirmacao.equalsIgnoreCase("S")) {
            System.out.println("\nExcluindo do banco de dados...");
            String resultado = controller.excluirCliente(c.getCpf());
            System.out.println(resultado);
        } else {
            System.out.println("\nOperação cancelada de forma segura.");
        }
        pausar();
    }

    // ==========================================
    // Métodos Auxiliares e Mock Original
    // ==========================================

    private void menuLogin() {
        ViewUtil.clearConsole();
        ViewUtil.linha(30);
        System.out.println("\n           MOCK LOGIN         ");
        ViewUtil.linha(30);
        System.out.println();

        System.out.print("Digite seu e-mail: ");
        String email = ScannerUtil.getString();

        System.out.print("Digite sua senha: ");
        String password = ScannerUtil.getString();

        System.out.println("\nDados capturados na View original:");
        System.out.println("E-mail: " + email);
        System.out.println("Senha: " + password);
        pausar();
    }

    private void exibirCabecalhoPrincipal() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n            SISTEMA DE GESTÃO PROZ-TEC            ");
        ViewUtil.linha(50);
        System.out.println();
    }

    private void exibirFichaCliente(ClienteModel c) {
        ViewUtil.linha(40);
        System.out.println("\n          DADOS DO CLIENTE FOUND          ");
        ViewUtil.linha(40);
        System.out.println();
        System.out.println("CPF:      " + formatarCpf(c.getCpf()));
        System.out.println("Nome:     " + c.getNome());
        System.out.println("Gênero:   " + (c.getGenero() != null ? c.getGenero() : "Não informado"));
        System.out.println("Telefone: " + (c.getTelefone() != null ? c.getTelefone() : "Não informado"));
        System.out.println("E-mail:   " + c.getEmail());
        System.out.println("Venda ID: " + (c.getFkVendaId() != null ? c.getFkVendaId() : "Nenhuma vinculada"));
        ViewUtil.linha(40);
        System.out.println();
    }

    private void pausar() {
        System.out.println("\nPressione Enter para continuar...");
        ScannerUtil.getString();
    }

    private String formatarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) return cpf;
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
    }

    private String truncar(String texto, int tamanhoMax) {
        if (texto == null) return "";
        if (texto.length() <= tamanhoMax) return texto;
        return texto.substring(0, tamanhoMax - 3) + "...";
    }
}
