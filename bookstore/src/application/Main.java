package application;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String currentUserType = "";

    public static void main(String[] args) {
        boolean sair = false;
        
        while (!sair) {
            exibirMenuPrincipal();
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    fazerLogin();
                    break;
                case 2:
                    if (currentUserType.equals("cliente")) {
                        exibirDadosCadastrais();
                    } else {
                        System.out.println("Ação disponível apenas para clientes.");
                    }
                    break;
                case 3:
                    if (currentUserType.equals("cliente")) {
                        exibirPedidos();
                    } else {
                        System.out.println("Ação disponível apenas para clientes.");
                    }
                    break;
                case 4:
                    if (currentUserType.equals("vendedor") || currentUserType.equals("gerente")) {
                        verificarEstoque();
                    } else {
                        System.out.println("Ação disponível apenas para vendedores e gerentes.");
                    }
                    break;
                case 5:
                    if (currentUserType.equals("vendedor") || currentUserType.equals("gerente")) {
                        exibirListaClientes();
                    } else {
                        System.out.println("Ação disponível apenas para vendedores e gerentes.");
                    }
                    break;
                case 6:
                    if (currentUserType.equals("vendedor") || currentUserType.equals("gerente")) {
                        gerarRelatorioVendas();
                    } else {
                        System.out.println("Ação disponível apenas para vendedores e gerentes.");
                    }
                    break;
                case 7:
                        buscarLivro();
                    break;
                case 8:
                    sair = true;
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("----- Menu Principal -----");
        System.out.println("1. Fazer login");
        System.out.println("2. Exibir dados cadastrais");
        System.out.println("3. Exibir pedidos");
        System.out.println("4. Verificar estoque");
        System.out.println("5. Exibir lista de clientes");
        System.out.println("6. Gerar relatório de vendas");
        System.out.println("7. Buscar livro");
        System.out.println("8. Sair");
        System.out.print("Selecione uma opção: ");
    }

    private static void fazerLogin() {
        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        // Lógica para verificar o login
        // ...

        // Definir o tipo de usuário logado (cliente, vendedor, gerente)
        currentUserType = "cliente"; // Exemplo: definindo como cliente
    }

    private static void exibirDadosCadastrais() {
        // Lógica para exibir os dados cadastrais do cliente
        // ...
        System.out.println("Exibindo dados cadastrais do cliente...");
    }

    private static void exibirPedidos() {
        // Lógica para exibir os pedidos do cliente
        // ...
        System.out.println("Exibindo pedidos do cliente...");
    }

    private static void verificarEstoque() {
        // Lógica para verificar o estoque
        // ...
        System.out.println("Verificando estoque...");
    }

    private static void exibirListaClientes() {
        // Lógica para exibir a lista de clientes
        // ...
        System.out.println("Exibindo lista de clientes...");
    }

    private static void gerarRelatorioVendas() {
        // Lógica para gerar o relatório de vendas
        // ...
        System.out.println("Gerando relatório de vendas...");
    }

    private static void buscarLivro() {
        System.out.println("----- Buscar Livro -----");
        System.out.println("1. Por cidade");
        System.out.println("2. Por categoria");
        System.out.println("3. Por faixa de preço");
        System.out.println("4. Por nome");
        System.out.print("Selecione uma opção de busca: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1:
                System.out.print("Digite o nome da cidade: ");
                String cidade = scanner.nextLine();
                // Lógica para buscar livro por cidade
                // ...
                System.out.println("Buscando livro por cidade: " + cidade);
                break;
            case 2:
                System.out.print("Digite a categoria: ");
                String categoria = scanner.nextLine();
                // Lógica para buscar livro por categoria
                // ...
                System.out.println("Buscando livro por categoria: " + categoria);
                break;
            case 3:
                System.out.print("Digite a faixa de preço mínima: ");
                double precoMinimo = scanner.nextDouble();
                System.out.print("Digite a faixa de preço máxima: ");
                double precoMaximo = scanner.nextDouble();
                // Lógica para buscar livro por faixa de preço
                // ...
                System.out.println("Buscando livro por faixa de preço: " + precoMinimo + " - " + precoMaximo);
                break;
            case 4:
                System.out.print("Digite o nome do livro: ");
                String nomeLivro = scanner.nextLine();
                // Lógica para buscar livro por nome
                // ...
                System.out.println("Buscando livro por nome: " + nomeLivro);
                break;
            default:
                System.out.println("Opção inválida. Por favor, tente novamente.");
        }
    }
}
