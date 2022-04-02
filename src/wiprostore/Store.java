package wiprostore;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Store {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Product> products = new ArrayList<>(10) {{
            add(new Product(1, "Leite", 10, 4.57F));
            add(new Product(2, "Cereal", 10, 3.02F));
            add(new Product(3, "Arroz", 10, 9.43F));
            add(new Product(4, "Atum", 10, 3.55F));
            add(new Product(5, "Feijão", 10, 6.55F));
            add(new Product(6, "Azeite", 10, 4.55F));
            add(new Product(7, "Oleo", 10, 7.33F));
            add(new Product(8, "Sabão", 10, 1.99F));
            add(new Product(9, "Sal ", 10, 3.82F));
            add(new Product(10, "Açucar", 10, 4.29F));
        }};
        System.out.printf("\t\t\t\t\t WIPRO STORE\n=============================================================\n\n");
        System.out.printf("CÓDIGO\t\tPRODUTO\t\tQNTD. PRODUTOS \t\tPREÇO UNIT\n");
        for (Product product : products) {
            System.out.printf("%d\t\t\t%s\t\t\t%d\t\t\t\t\t%.2f\n", product.getId(), product.getDescription(), product.getQuantity(), product.getPrice());
        }
        System.out.printf("\n=============================================================\n");
        System.out.printf("Olá!");
        boolean continuarCompra = true;
        do {
            System.out.printf("Digite o código do produto desejado: ");
            int cod = sc.nextInt();
            if (products.get(cod - 1).getQuantity() <= 0) {
                System.out.printf("\nProduto indisponível no estoque!\n Deseja continuar a compra? (S/N)");
                switch (sc.next()){
                    case "S":
                        break;
                    case "N":
                        continuarCompra = false;

                        break;
                    default:
                        System.out.printf("\nOpção inválida!\n");
                }
            }
            System.out.printf("Insira a quantidade desejada do produto: ");
            int qtd = sc.nextInt();
            if (products.get(cod - 1).getQuantity() <= 0)
                System.out.printf("\nQuantidade indisponível em estoque, digite novamente...\n");

        } while (continuarCompra == true);
    }

    static class Product {
        int id, quantity;
        String description;
        float price;

        Product(int id, String description, int quantity, float price) {
            this.id = id;
            this.description = description;
            this.quantity = quantity;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public int getQuantity() {
            return quantity;
        }

        public float getPrice() {
            return price;
        }
    }
}
