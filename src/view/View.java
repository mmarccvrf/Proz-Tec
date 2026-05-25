package view;

import controller.ClienteController;
import controller.FornecedorController;
import controller.ProdutoController;
import model.ClienteModel;
import model.FornecedorModel;
import model.ProdutoModel;
import utils.ScannerUtil;
import utils.ViewUtil;

import java.util.List;

/**
 * Interface em Linha de Comando (CLI) para o sistema Proz-Tec.
 */
public class View {

    private final ClienteController clienteController;
    private final FornecedorController fornecedorController;
    private final ProdutoController produtoController;

    public View() {
        this.clienteController = new ClienteController();
        this.fornecedorController = new FornecedorController();
        this.produtoController = new ProdutoController();
    }

    /**
     * Ponto de entrada da View chamado pela classe Main.
     */
    public void viewMain() {
        boolean rodando = true;
        while (rodando) {
            exibirCabecalhoPrincipal();
            System.out.println("1. [CRUD] Gerenciar Clientes");
            System.out.println("2. [CRUD] Gerenciar Fornecedores");
            System.out.println("3. [CRUD] Gerenciar Produtos");
            System.out.println("4. Realizar Login de Exemplo (Original)");
            System.out.println("0. Sair do Sistema");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            String opcao = ScannerUtil.getString().trim();

            switch (opcao) {
                case "1":
                    menuClientes();
                    break;
                case "2":
                    menuFornecedores();
                    break;
                case "3":
                    menuProdutos();
                    break;
                case "4":
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

    // ==========================================
    // Submenu de Clientes
    // ==========================================
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
    // Submenu de Fornecedores
    // ==========================================
    private void menuFornecedores() {
        boolean noSubmenu = true;
        while (noSubmenu) {
            ViewUtil.clearConsole();
            ViewUtil.linha(50);
            System.out.println("\n     SISTEMA PROZ-TEC - GERENCIAR FORNECEDORES     ");
            ViewUtil.linha(50);
            System.out.println();
            System.out.println("1. Cadastrar Novo Fornecedor");
            System.out.println("2. Listar Todos os Fornecedores");
            System.out.println("3. Buscar Fornecedor por CNPJ");
            System.out.println("4. Atualizar Cadastro de Fornecedor");
            System.out.println("5. Excluir Fornecedor");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            String opcao = ScannerUtil.getString().trim();

            switch (opcao) {
                case "1":
                    cadastrarFornecedor();
                    break;
                case "2":
                    listarFornecedores();
                    break;
                case "3":
                    buscarFornecedor();
                    break;
                case "4":
                    atualizarFornecedor();
                    break;
                case "5":
                    excluirFornecedor();
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
    // Submenu de Produtos
    // ==========================================
    private void menuProdutos() {
        boolean noSubmenu = true;
        while (noSubmenu) {
            ViewUtil.clearConsole();
            ViewUtil.linha(50);
            System.out.println("\n       SISTEMA PROZ-TEC - GERENCIAR PRODUTOS       ");
            ViewUtil.linha(50);
            System.out.println();
            System.out.println("1. Cadastrar Novo Produto");
            System.out.println("2. Listar Todos os Produtos");
            System.out.println("3. Buscar Produto por ID");
            System.out.println("4. Atualizar Cadastro de Produto");
            System.out.println("5. Excluir Produto");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            String opcao = ScannerUtil.getString().trim();

            switch (opcao) {
                case "1":
                    cadastrarProduto();
                    break;
                case "2":
                    listarProdutos();
                    break;
                case "3":
                    buscarProduto();
                    break;
                case "4":
                    atualizarProduto();
                    break;
                case "5":
                    excluirProduto();
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
    // Fluxos CRUD Cliente
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
        String resultado = clienteController.cadastrarCliente(cpf, nome, genero, telefone, email, senha, fkVendaId);
        System.out.println(resultado);
        pausar();
    }

    private void listarClientes() {
        ViewUtil.clearConsole();
        ViewUtil.linha(90);
        System.out.println("\n                                 LISTA DE CLIENTES CADASTRADOS                            ");
        ViewUtil.linha(90);
        System.out.println();

        List<ClienteModel> clientes = clienteController.listarTodos();

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
        ClienteModel c = clienteController.buscarPorCpf(cpf);

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

        ClienteModel c = clienteController.buscarPorCpf(cpf);
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
        String resultado = clienteController.atualizarCliente(c.getCpf(), nome, genero, telefone, email, senha, fkVendaId);
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

        ClienteModel c = clienteController.buscarPorCpf(cpf);
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
            String resultado = clienteController.excluirCliente(c.getCpf());
            System.out.println(resultado);
        } else {
            System.out.println("\nOperação cancelada de forma segura.");
        }
        pausar();
    }


    // ==========================================
    // Fluxos CRUD Fornecedor
    // ==========================================

    private void cadastrarFornecedor() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n             CADASTRAR NOVO FORNECEDOR            ");
        ViewUtil.linha(50);
        System.out.println();

        System.out.print("Digite o CNPJ (apenas números, 14 dígitos): ");
        String cnpj = ScannerUtil.getString();

        System.out.print("Digite o Nome do Fornecedor: ");
        String nome = ScannerUtil.getString();

        System.out.print("Digite a Área de Atuação: ");
        String atuacao = ScannerUtil.getString();

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
                System.out.println("[AVISO] ID de Venda inválido. O fornecedor será cadastrado sem vínculo de venda.");
            }
        }

        System.out.print("Digite o CPF do Cliente Vinculado (ou Enter para nenhum): ");
        String clienteCpf = ScannerUtil.getString();
        if (clienteCpf.trim().isEmpty()) {
            clienteCpf = null;
        }

        System.out.println("\nEnviando dados para o controlador...");
        String resultado = fornecedorController.cadastrarFornecedor(cnpj, nome, atuacao, telefone, email, senha, fkVendaId, clienteCpf);
        System.out.println(resultado);
        pausar();
    }

    private void listarFornecedores() {
        ViewUtil.clearConsole();
        ViewUtil.linha(100);
        System.out.println("\n                               LISTA DE FORNECEDORES CADASTRADOS                          ");
        ViewUtil.linha(100);
        System.out.println();

        List<FornecedorModel> fornecedores = fornecedorController.listarTodos();

        if (fornecedores.isEmpty()) {
            System.out.println("Nenhum fornecedor cadastrado no banco de dados.");
        } else {
            System.out.printf("%-18s | %-22s | %-22s | %-16s | %-12s\n", "CNPJ", "NOME", "EMAIL", "ATUAÇÃO", "TELEFONE");
            ViewUtil.linha(100);
            System.out.println();
            for (FornecedorModel f : fornecedores) {
                System.out.printf("%-18s | %-22s | %-22s | %-16s | %-12s\n",
                        formatarCnpj(f.getCnpj()),
                        truncar(f.getNome(), 22),
                        truncar(f.getEmail(), 22),
                        truncar(f.getAtuacao(), 16),
                        f.getTelefone() != null ? f.getTelefone() : "-"
                );
            }
            ViewUtil.linha(100);
            System.out.println();
            System.out.println("Total de fornecedores: " + fornecedores.size());
        }
        pausar();
    }

    private void buscarFornecedor() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n             BUSCAR FORNECEDOR POR CNPJ           ");
        ViewUtil.linha(50);
        System.out.println();

        System.out.print("Digite o CNPJ a ser pesquisado: ");
        String cnpj = ScannerUtil.getString();

        System.out.println("\nBuscando...");
        FornecedorModel f = fornecedorController.buscarPorCnpj(cnpj);

        if (f == null) {
            System.out.println("[AVISO] Fornecedor não encontrado com o CNPJ informado.");
        } else {
            exibirFichaFornecedor(f);
        }
        pausar();
    }

    private void atualizarFornecedor() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n           ATUALIZAR CADASTRO DE FORNECEDOR       ");
        ViewUtil.linha(50);
        System.out.println();

        System.out.print("Digite o CNPJ do fornecedor que deseja atualizar: ");
        String cnpj = ScannerUtil.getString();

        FornecedorModel f = fornecedorController.buscarPorCnpj(cnpj);
        if (f == null) {
            System.out.println("[ERRO] Fornecedor não encontrado.");
            pausar();
            return;
        }

        System.out.println("\n--- Dados atuais encontrados. Pressione Enter para manter o valor atual ---");

        System.out.print("Nome [" + f.getNome() + "]: ");
        String nome = ScannerUtil.getString();
        if (nome.trim().isEmpty()) nome = f.getNome();

        System.out.print("Atuação [" + f.getAtuacao() + "]: ");
        String atuacao = ScannerUtil.getString();
        if (atuacao.trim().isEmpty()) atuacao = f.getAtuacao();

        System.out.print("Telefone [" + f.getTelefone() + "]: ");
        String telefone = ScannerUtil.getString();
        if (telefone.trim().isEmpty()) telefone = f.getTelefone();

        System.out.print("E-mail [" + f.getEmail() + "]: ");
        String email = ScannerUtil.getString();
        if (email.trim().isEmpty()) email = f.getEmail();

        System.out.print("Senha [" + f.getSenha() + "]: ");
        String senha = ScannerUtil.getString();
        if (senha.trim().isEmpty()) senha = f.getSenha();

        String vendaAtualLabel = f.getFkVendaId() != null ? String.valueOf(f.getFkVendaId()) : "nenhum";
        System.out.print("ID da Venda [" + vendaAtualLabel + "] (digite 'remover' para limpar ou Enter para manter): ");
        String vendaInput = ScannerUtil.getString().trim();
        Integer fkVendaId = f.getFkVendaId();

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

        String clienteCpfAtualLabel = f.getFkClienteCpf() != null ? formatarCpf(f.getFkClienteCpf()) : "nenhum";
        System.out.print("CPF do Cliente Vinculado [" + clienteCpfAtualLabel + "] (digite 'remover' para limpar ou Enter para manter): ");
        String clienteCpfInput = ScannerUtil.getString().trim();
        String fkClienteCpf = f.getFkClienteCpf();

        if (!clienteCpfInput.isEmpty()) {
            if (clienteCpfInput.equalsIgnoreCase("remover")) {
                fkClienteCpf = "remover"; // Flag para controller limpar
            } else {
                fkClienteCpf = clienteCpfInput;
            }
        }

        System.out.println("\nEnviando atualizações...");
        String resultado = fornecedorController.atualizarFornecedor(f.getCnpj(), nome, atuacao, telefone, email, senha, fkVendaId, fkClienteCpf);
        System.out.println(resultado);
        pausar();
    }

    private void excluirFornecedor() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n                EXCLUIR FORNECEDOR                ");
        ViewUtil.linha(50);
        System.out.println();

        System.out.print("Digite o CNPJ do fornecedor a ser excluído: ");
        String cnpj = ScannerUtil.getString();

        FornecedorModel f = fornecedorController.buscarPorCnpj(cnpj);
        if (f == null) {
            System.out.println("[ERRO] Fornecedor não encontrado.");
            pausar();
            return;
        }

        exibirFichaFornecedor(f);

        System.out.print("AVISO: Tem certeza de que deseja EXCLUIR permanentemente este fornecedor? (S/N): ");
        String confirmacao = ScannerUtil.getString().trim();

        if (confirmacao.equalsIgnoreCase("S")) {
            System.out.println("\nExcluindo do banco de dados...");
            String resultado = fornecedorController.excluirFornecedor(f.getCnpj());
            System.out.println(resultado);
        } else {
            System.out.println("\nOperação cancelada de forma segura.");
        }
        pausar();
    }

    // ==========================================
    // Fluxos CRUD Produto
    // ==========================================

    private void cadastrarProduto() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n              CADASTRAR NOVO PRODUTO              ");
        ViewUtil.linha(50);
        System.out.println();

        System.out.print("Digite o ID do Produto (apenas números): ");
        String idInput = ScannerUtil.getString().trim();
        Integer id = null;
        try {
            id = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] ID inválido. Deve ser um número inteiro.");
            pausar();
            return;
        }

        System.out.print("Digite o Nome do Produto: ");
        String nome = ScannerUtil.getString();

        System.out.print("Digite a Quantidade em Estoque: ");
        String quantInput = ScannerUtil.getString().trim();
        Integer quant = null;
        try {
            quant = Integer.parseInt(quantInput);
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] Quantidade inválida. Deve ser um número inteiro.");
            pausar();
            return;
        }

        System.out.print("Digite o Valor do Produto (inteiro): ");
        String valorInput = ScannerUtil.getString().trim();
        Integer valor = null;
        try {
            valor = Integer.parseInt(valorInput);
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] Valor inválido. Deve ser um número inteiro.");
            pausar();
            return;
        }

        System.out.print("Digite o ID do Fornecedor (ou Enter para nenhum): ");
        String fornInput = ScannerUtil.getString().trim();
        Integer fornecedor = null;
        if (!fornInput.isEmpty()) {
            try {
                fornecedor = Integer.parseInt(fornInput);
            } catch (NumberFormatException e) {
                System.out.println("[AVISO] ID do Fornecedor inválido. O produto será salvo sem fornecedor vinculado.");
            }
        }

        System.out.println("\nEnviando dados para o controlador...");
        String resultado = produtoController.cadastrarProduto(id, nome, quant, valor, fornecedor);
        System.out.println(resultado);
        pausar();
    }

    private void listarProdutos() {
        ViewUtil.clearConsole();
        ViewUtil.linha(80);
        System.out.println("\n                               LISTA DE PRODUTOS CADASTRADOS                            ");
        ViewUtil.linha(80);
        System.out.println();

        List<ProdutoModel> produtos = produtoController.listarTodos();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado no banco de dados.");
        } else {
            System.out.printf("%-10s | %-30s | %-12s | %-12s | %-15s\n", "ID", "NOME", "QUANTIDADE", "VALOR", "FORNECEDOR ID");
            ViewUtil.linha(80);
            System.out.println();
            for (ProdutoModel p : produtos) {
                System.out.printf("%-10d | %-30s | %-12d | %-12d | %-15s\n",
                        p.getId(),
                        truncar(p.getNome(), 30),
                        p.getQuant(),
                        p.getValor(),
                        p.getFornecedor() != null ? String.valueOf(p.getFornecedor()) : "-"
                );
            }
            ViewUtil.linha(80);
            System.out.println();
            System.out.println("Total de produtos: " + produtos.size());
        }
        pausar();
    }

    private void buscarProduto() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n               BUSCAR PRODUTO POR ID              ");
        ViewUtil.linha(50);
        System.out.println();

        System.out.print("Digite o ID do produto a ser pesquisado: ");
        String idInput = ScannerUtil.getString().trim();
        Integer id = null;
        try {
            id = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] ID inválido.");
            pausar();
            return;
        }

        System.out.println("\nBuscando...");
        ProdutoModel p = produtoController.buscarPorId(id);

        if (p == null) {
            System.out.println("[AVISO] Produto não encontrado com o ID informado.");
        } else {
            exibirFichaProduto(p);
        }
        pausar();
    }

    private void atualizarProduto() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n             ATUALIZAR CADASTRO DE PRODUTO        ");
        ViewUtil.linha(50);
        System.out.println();

        System.out.print("Digite o ID do produto que deseja atualizar: ");
        String idInput = ScannerUtil.getString().trim();
        Integer id = null;
        try {
            id = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] ID inválido.");
            pausar();
            return;
        }

        ProdutoModel p = produtoController.buscarPorId(id);
        if (p == null) {
            System.out.println("[ERRO] Produto não encontrado.");
            pausar();
            return;
        }

        System.out.println("\n--- Dados atuais encontrados. Pressione Enter para manter o valor atual ---");

        System.out.print("Nome [" + p.getNome() + "]: ");
        String nome = ScannerUtil.getString();
        if (nome.trim().isEmpty()) nome = p.getNome();

        System.out.print("Quantidade [" + p.getQuant() + "]: ");
        String quantInput = ScannerUtil.getString().trim();
        Integer quant = p.getQuant();
        if (!quantInput.isEmpty()) {
            try {
                quant = Integer.parseInt(quantInput);
            } catch (NumberFormatException e) {
                System.out.println("[AVISO] Valor inválido. Mantendo a quantidade atual.");
            }
        }

        System.out.print("Valor [" + p.getValor() + "]: ");
        String valorInput = ScannerUtil.getString().trim();
        Integer valor = p.getValor();
        if (!valorInput.isEmpty()) {
            try {
                valor = Integer.parseInt(valorInput);
            } catch (NumberFormatException e) {
                System.out.println("[AVISO] Valor inválido. Mantendo o valor atual.");
            }
        }

        String fornAtualLabel = p.getFornecedor() != null ? String.valueOf(p.getFornecedor()) : "nenhum";
        System.out.print("ID do Fornecedor [" + fornAtualLabel + "] (digite 'remover' para limpar ou Enter para manter): ");
        String fornInput = ScannerUtil.getString().trim();
        Integer fornecedor = p.getFornecedor();

        if (!fornInput.isEmpty()) {
            if (fornInput.equalsIgnoreCase("remover")) {
                fornecedor = null;
            } else {
                try {
                    fornecedor = Integer.parseInt(fornInput);
                } catch (NumberFormatException e) {
                    System.out.println("[AVISO] ID inválido. Mantendo o fornecedor atual.");
                }
            }
        }

        System.out.println("\nEnviando atualizações...");
        String resultado = produtoController.atualizarProduto(p.getId(), nome, quant, valor, fornecedor);
        System.out.println(resultado);
        pausar();
    }

    private void excluirProduto() {
        ViewUtil.clearConsole();
        ViewUtil.linha(50);
        System.out.println("\n                  EXCLUIR PRODUTO                 ");
        ViewUtil.linha(50);
        System.out.println();

        System.out.print("Digite o ID do produto a ser excluído: ");
        String idInput = ScannerUtil.getString().trim();
        Integer id = null;
        try {
            id = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println("[ERRO] ID inválido.");
            pausar();
            return;
        }

        ProdutoModel p = produtoController.buscarPorId(id);
        if (p == null) {
            System.out.println("[ERRO] Produto não encontrado.");
            pausar();
            return;
        }

        exibirFichaProduto(p);

        System.out.print("AVISO: Tem certeza de que deseja EXCLUIR permanentemente este produto? (S/N): ");
        String confirmacao = ScannerUtil.getString().trim();

        if (confirmacao.equalsIgnoreCase("S")) {
            System.out.println("\nExcluindo do banco de dados...");
            String resultado = produtoController.excluirProduto(p.getId());
            System.out.println(resultado);
        } else {
            System.out.println("\nOperação cancelada de forma segura.");
        }
        pausar();
    }

    private void exibirFichaProduto(ProdutoModel p) {
        ViewUtil.linha(40);
        System.out.println("\n          DADOS DO PRODUTO ENCONTRADO         ");
        ViewUtil.linha(40);
        System.out.println();
        System.out.println("ID:            " + p.getId());
        System.out.println("Nome:          " + p.getNome());
        System.out.println("Quantidade:    " + p.getQuant());
        System.out.println("Valor:         " + p.getValor());
        System.out.println("Fornecedor ID: " + (p.getFornecedor() != null ? p.getFornecedor() : "Nenhum vinculado"));
        ViewUtil.linha(40);
        System.out.println();
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

    private void exibirFichaFornecedor(FornecedorModel f) {
        ViewUtil.linha(40);
        System.out.println("\n         DADOS DO FORNECEDOR FOUND        ");
        ViewUtil.linha(40);
        System.out.println();
        System.out.println("CNPJ:       " + formatarCnpj(f.getCnpj()));
        System.out.println("Nome:       " + f.getNome());
        System.out.println("Atuação:    " + f.getAtuacao());
        System.out.println("Telefone:   " + (f.getTelefone() != null ? f.getTelefone() : "Não informado"));
        System.out.println("E-mail:     " + f.getEmail());
        System.out.println("Venda ID:   " + (f.getFkVendaId() != null ? f.getFkVendaId() : "Nenhuma vinculada"));
        System.out.println("Cliente CPF:" + (f.getFkClienteCpf() != null ? formatarCpf(f.getFkClienteCpf()) : "Nenhum vinculado"));
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

    private String formatarCnpj(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) return cnpj;
        return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12);
    }

    private String truncar(String texto, int tamanhoMax) {
        if (texto == null) return "";
        if (texto.length() <= tamanhoMax) return texto;
        return texto.substring(0, tamanhoMax - 3) + "...";
    }
}
