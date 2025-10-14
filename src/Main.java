import DAO.Conexao;
import DAO.ProdutoDAO;
import Modelo.Produto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProdutoDAO produtoDAO = new ProdutoDAO();

        Scanner scanopcao = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("----------------------------------------");
            System.out.println("------------------MENU------------------");
            System.out.println("----------------------------------------");
            System.out.println("1-inserir produtos----------------------");
            System.out.println("2-atualizar produtos--------------------");
            System.out.println("3-excluir produtos----------------------");
            System.out.println("4-exibir produtos-----------------------");
            System.out.println("5-sair----------------------------------");
            System.out.println("----------------------------------------");
            System.out.println("opção: ");
            opcao =scanopcao.nextInt();

            switch (opcao){
                case 1:
                    Produto produto = new Produto();
                    System.out.println("Informe os dados do produto");
                    scanopcao.nextLine();
                    System.out.println("Nome: ");
                    produto.setNome(scanopcao.nextLine());
                    System.out.println("Quantidade: ");
                    produto.setQuantidade(scanopcao.nextInt());
                    System.out.println("Preço: ");
                    produto.setPreco(scanopcao.nextDouble());
                    produtoDAO.inserir(produto);
                    break;
                case 2:
                    System.out.println("Informe o ID do produto: ");
                    int codigo = scanopcao.nextInt();

                    /*
                    fazer a validação da existencia do produto
                    chamar um metodo buscarID(id)
                    segunda versao
                    retornaria todos os dados do objeto para nao termos valores nulos
                    */


                    Produto produtoAtualizar = new Produto();

                    System.out.println("Informe os novos dados do produto");
                    scanopcao.nextLine();
                    System.out.println("Nome: ");
                    produtoAtualizar.setNome(scanopcao.nextLine());
                    System.out.println("Quantidade: ");
                    produtoAtualizar.setQuantidade(scanopcao.nextInt());
                    System.out.println("Preço: ");
                    produtoAtualizar.setPreco(scanopcao.nextDouble());

                    produtoAtualizar.setId(codigo);
                    produtoDAO.atualizar(produtoAtualizar);
                    break;
                case 3:
                    System.out.println("Escolha o ID do produto a qual quer excluir");
                    System.out.println("ID: ");
                    int codigoExcluir = scanopcao.nextInt();
                    scanopcao.nextLine();
                    System.out.println("Voce realmente deseja exluir este produto?");
                    System.out.println("Sim ou não(s - n): ");
                    String confirmar = scanopcao.nextLine();
                    if (confirmar.equals("s")){
                        produtoDAO.excluir(codigoExcluir);
                    } else if (confirmar.equals("n")){
                        System.out.println("Ação cancelada");
                    } else {
                        System.out.println("Erro Fatal uau!");
                    }
                    break;
                case 4:
                    for (Produto p : produtoDAO.listarTodos()) {
                        System.out.print("\n*****************************");
                        System.out.print("\nID: " + p.getId());
                        System.out.print("\nProduto: " + p.getNome());
                        System.out.print("\nQuantidade: " + p.getQuantidade());
                        System.out.print("\nPreço R$ " + p.getPreco());
                        System.out.print("\nTotal R$ " + p.getQuantidade() * p.getPreco());
                    }
                    break;
                case 5:
                    System.out.println("saindo...");
                    break;
                default:
                    System.out.println("Erro fatal!!");
                    break;
            }
        }while (opcao !=5);


        //Produto produto = new Produto("Arroz ABE 5 kg", 2, 35.98);
        //produtoDAO.inserir(produto);

        //Produto produtoAtualizar = new Produto("Leite Integral", 12, 3.98);
        //produtoAtualizar.setId(1);
        //produtoDAO.atualizar(produtoAtualizar);

       //produtoDAO.excluir(4);
    }
}