import java.util.Optional;

public class BuyerAgent extends Thread {
    private String targetBookTitle;
    private BrokerAgent broker;
    private Optional<Float> bestOffer;
    private SellerAgent bestSeller;
    private static final int MAX_ATTEMPTS = 5;
    private int attempts = 0;

    public BuyerAgent(String targetBookTitle, BrokerAgent broker) {
        this.targetBookTitle = targetBookTitle;
        this.broker = broker;
        this.bestOffer = Optional.empty();
        this.bestSeller = null;
        this.setName("BuyerAgent-" + targetBookTitle);
    }

    @Override
    public void run() {
        while (!interrupted() && attempts < MAX_ATTEMPTS) {
            requestOffers();

            if (bestOffer.isPresent()) {
                placeOrder(bestSeller, bestOffer.get());
                break;
            }

            attempts++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
        if (attempts >= MAX_ATTEMPTS) {
            System.out.println(this.getName() + " gave up after " + MAX_ATTEMPTS + " attempts.");
        }
    }

    private void requestOffers() {
        Optional<BrokerAgent.Offer> offer = broker.requestOffers(targetBookTitle);
        offer.ifPresent(o -> {
            bestOffer = Optional.of(o.getPrice());
            bestSeller = o.getSeller();
        });
    }

    private void placeOrder(SellerAgent seller, float price) {
        seller.processPurchaseOrder(new Book(targetBookTitle, price));
        System.out.println(this.getName() + " purchased " + targetBookTitle + " for " + price + " from " + seller.getAgentName());
    }
}
