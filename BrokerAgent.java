import java.util.List;
import java.util.Optional;

public class BrokerAgent {
    private List<SellerAgent> sellers;

    public BrokerAgent(List<SellerAgent> sellers) {
        this.sellers = sellers;
    }

    public Optional<Offer> requestOffers(String bookTitle) {
        Offer bestOffer = null;

        for (SellerAgent seller : sellers) {
            Optional<Float> offerPrice = seller.makeOffer(bookTitle);
            if (offerPrice.isPresent()) {
                if (bestOffer == null || offerPrice.get() < bestOffer.getPrice()) {
                    bestOffer = new Offer(seller, offerPrice.get());
                }
            }
        }

        return Optional.ofNullable(bestOffer);
    }

    public static class Offer {
        private SellerAgent seller;
        private float price;

        public Offer(SellerAgent seller, float price) {
            this.seller = seller;
            this.price = price;
        }

        public SellerAgent getSeller() {
            return seller;
        }

        public float getPrice() {
            return price;
        }
    }
}
