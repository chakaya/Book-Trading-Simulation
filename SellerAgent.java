import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SellerAgent extends Thread {
    private Map<String, Float> catalogue = new HashMap<>();
    private String agentName;

    public SellerAgent(String agentName) {
        this.agentName = agentName;
        this.setName("SellerAgent-" + agentName);
    }

    public synchronized void addBookToCatalogue(String title, float price) {
        catalogue.put(title, price);
    }

    public synchronized Optional<Float> makeOffer(String bookTitle) {
        return Optional.ofNullable(catalogue.get(bookTitle));
    }

    public synchronized void processPurchaseOrder(Book book) {
        catalogue.remove(book.getTitle());
        System.out.println(this.getName() + " sold " + book.getTitle() + " for " + book.getPrice());

        // Close the market if the catalogue is empty
        if (catalogue.isEmpty()) {
            MarketStatus.marketOpen = false;
        }
    }

    public String getAgentName() {
        return agentName;
    }

    @Override
    public void run() {
        while (!catalogue.isEmpty() && MarketStatus.marketOpen) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(this.getName() + " interrupted and terminating.");
                return; // Exit the run method when interrupted
            }
        }
        System.out.println(this.getName() + " is terminating.");
    }
}
