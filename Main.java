import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SellerAgent seller1 = new SellerAgent("Seller1");
        SellerAgent seller2 = new SellerAgent("Seller2");
        List<SellerAgent> sellers = Arrays.asList(seller1, seller2);

        BrokerAgent broker = new BrokerAgent(sellers);

        initializeSellers(sellers);
        seller1.start();
        seller2.start();

        BuyerAgent[] buyers = new BuyerAgent[args.length];
        for (int i = 0; i < args.length; i++) {
            buyers[i] = new BuyerAgent(args[i], broker);
            buyers[i].start();
        }

        new Thread(() -> {
            boolean allBuyersDone;
            do {
                allBuyersDone = Arrays.stream(buyers)
                                    .allMatch(thread -> thread.getState() == Thread.State.TERMINATED);
		System.out.println("Checking if all buyers are done: " + allBuyersDone); 

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } while (!allBuyersDone);

            MarketStatus.marketOpen = false;
	    System.out.println("Market is now closed.");
        }).start();
    }

    private static void initializeSellers(List<SellerAgent> sellers) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Initialize Seller Catalogues");

        for (SellerAgent seller : sellers) {
            System.out.println("Enter books for " + seller.getAgentName() + " (format: title,price; title,price; ...):");
            String input = scanner.nextLine();
            String[] books = input.split(";");

            for (String book : books) {
                String[] details = book.trim().split(",");
                if (details.length == 2) {
                    String title = details[0].trim();
                    float price = Float.parseFloat(details[1].trim());
                    seller.addBookToCatalogue(title, price);
                }
            }
        }
    }
}
