package wiprostore;

import java.util.*;

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
        HashMap<Integer, Integer> cart = new HashMap<>();
        boolean continuarCompra = true;
        do {
            Integer cod = 0, qtd = 0;
            printReport(products, ReportTypeEnum.INVENTORY, 0f);
            System.out.print("Digite o código do produto desejado: ");
            cod = validateInteger(sc);
            if (cod == null || cod < 1 || cod > 10 || products.get(cod - 1).getQuantity() <= 0) {
                System.out.print("\nCódigo inválido!\n");
                continue;
            }
            System.out.print("Insira a quantidade desejada do produto: ");
            qtd = validateInteger(sc);
            if (products.get(cod - 1).getQuantity() <= 0 || qtd == null || qtd < 1) {
                System.out.print("\nQuantidade invalida!\n");
            } else {
                try {
                    products.get(cod - 1).removeQuantity(qtd);
                    cart.put(cod, qtd);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                System.out.printf("\n %d un. de %s adicionado com sucesso no carrinho !!\n", qtd, products.get(cod - 1).getDescription());
                System.out.println("\n Deseja continuar a compra? (S/N)");
                switch (sc.next().toUpperCase()) {
                    case "S":
                        break;
                    case "N":
                        continuarCompra = false;
                        break;
                    default:
                        System.out.print("\nOpção inválida!\n");
                        continuarCompra = true;
                        break;
                }
            }
        } while (continuarCompra);
        List<Product> cartProductList = new ArrayList<>() {{
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                var product = products.get(entry.getKey() - 1);
                product.setQuantity(entry.getValue());
                this.add(product);
            }
        }};
        boolean continuePurchase = true;
        do {
            printReport(cartProductList, ReportTypeEnum.PREVIEW_CART, 0f);
            var paymentOption = validateInteger(sc);
            if (paymentOption == null || paymentOption < 1 || paymentOption > 4) {
                System.out.print("\nMétodo de pagamento invalido!\n");
                continue;
            }
            switch (paymentOption) {
                case 1:
                    printReport(cartProductList, ReportTypeEnum.FINAL_TOTAL, 0.2f);
                    continuePurchase = false;
                    break;
                case 2:
                    printReport(cartProductList, ReportTypeEnum.FINAL_TOTAL, 0.15f);
                    continuePurchase = false;
                    break;
                case 3:
                    printReport(cartProductList, ReportTypeEnum.FINAL_TOTAL, 0f);
                    continuePurchase = false;
                    break;
                case 4:
                    printReport(cartProductList, ReportTypeEnum.FINAL_TOTAL, -0.1f);
                    continuePurchase = false;
                    break;
            }
        } while (continuePurchase);
    }

    static void printReport(List<Product> products, ReportTypeEnum reportType, float discount) {
        double total = 0.0;
        switch (reportType) {
            case INVENTORY:
                System.out.print("\t\t\t\t\t WIPRO STORE\n=============================================================\n\n");
                System.out.print("CÓDIGO\t\tPRODUTO\t\tQNTD. PRODUTOS \t\tPREÇO UNIT\n");
                System.out.print("\n=============================================================\n");
                for (Product product : products)
                    if (product.getQuantity() > 0)
                        System.out.printf("%d\t\t\t%s\t\t\t%d\t\t\t\t\t%.2f\n", product.getId(), product.getDescription(), product.getQuantity(), product.getPrice());
                break;
            case PREVIEW_CART:
                System.out.print("\nITENS NO CARRINHO:\nNome\t\tQtde. no carrinho\tPreço unit.(R$)\t\t\tPreço total (R$)\n");
                for (Product product : products)
                    if (product.getQuantity() > 0) {
                        total += (product.getPrice() * product.getQuantity());
                        System.out.printf("%s\t\t%d\t\t\t\t\t%.2f\t\t\t\t\t%.2f\n", product.getDescription(), product.getQuantity(), product.getPrice(), (product.getPrice() * product.getQuantity()));
                    }
                System.out.printf("O valor total da compra com Imposto de 9%%: R$: %.2f%n", (total + (total * 0.09)));
                System.out.print(
                        "Opções de Pagamento: \n\n[1]À vista em dinheiro ou cartçao MASTERCARD. recebe 20% de desconto." +
                                "\n[2]À vista no cartão de crédito, recebe 15% de desconto." +
                                "\n[3]Em duas vezes, preço normal de etiqueta sem juros." +
                                "\n[4]Em três vezes, preço normal de etiqueta mais juros de 10%." +
                                "\n\nQual seria a forma de pagamento? ");
                break;
            case FINAL_TOTAL:
                System.out.print("\nWipro STORE\nRua dos Bôbos, nº0 - Mercadinho -LTDA\nCNPJ: 123456789/0001-01\n\n\n\t\t\t\t\t\t" +
                        "NOTA FISCAL\n=============================================================\n\n" +
                        "PRODUTO\t\tQNTD. PRODUTOS \t\tPREÇO UNIT.\nPREÇO TOTAL\n\n");
                for (Product product : products)
                    if (product.getQuantity() > 0) {
                        total += (product.getPrice() * product.getQuantity());
                        System.out.printf("%s\t\t%d\t\t\t\t\tR$%.2f\t\t\t\t\tR$%.2f\n", product.getDescription(), product.getQuantity(), product.getPrice(), (product.getPrice() * product.getQuantity()));
                    }
                System.out.print("\n=============================================================\n\n\n\n");
                System.out.printf("DESCONTO NA COMPRA: R$%.2f" +
                                "\nVALOR TOTAL A SER PAGO: R$%.2f" +
                                "\nVALOR TRIBUTÁRIO: R$%.2f %n%n", ((discount == 0 ? 0 : total * discount)),
                        ((discount == 0 ? total : total + (total * (-1 * discount)))),
                        (total * 0.09));
        }
    }

    static Integer validateInteger(Scanner sc) {
        try {
            return Integer.valueOf(sc.next());
        } catch (Exception e) {
            return null;
        }
    }

    public enum ReportTypeEnum {
        INVENTORY,
        PREVIEW_CART,
        FINAL_TOTAL
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

        public void removeQuantity(int quantity) {
            if (this.quantity < quantity) {
                throw new IllegalArgumentException("Quantidade indisponivel no estoque, tente novamente...");
            }
            this.quantity = this.quantity - quantity;
        }

        public int getId() {
            return id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getDescription() {
            return description;
        }

        public float getPrice() {
            return price;
        }
    }
}
