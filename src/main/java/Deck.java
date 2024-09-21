import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    int deckID;
    ArrayList<Card> CardList;
    HashMap<Integer, Integer> costCount;

    /**
     * Constructor for Deck class
     * Generates a random deck ID
     */
    public Deck() {
        this.CardList = new ArrayList<>();
        this.costCount = new HashMap<>();
        this.deckID = generateDeckID();
    }

    /**
     * Adds a card to the deck and updates the costCount
     * @param card Card that will be added to deck
     */
    public void addCard(Card card) {
        CardList.add(card);
        costCount.put(card.getEnergy(), costCount.getOrDefault(card.getEnergy(), 0) + 1);
    }

    /**
     * Returns the total energy cost of the deck
     * @return total energy cost of the deck
     */
    public int getTotalEnergyCost() {
        int totalEnergyCost = 0;
        for(Card c : CardList) {
            totalEnergyCost += c.getEnergy();
        }
        return totalEnergyCost;
    }

    /**
     * Generates a random deck ID
     * @return random 9 digit deck ID
     */
    private int generateDeckID() {
        return (int) (Math.random() * 1000000000);
    }

    /**
     * Returns the deck ID
     * @return deck ID
     */
    public int getDeckId() {
        return deckID;
    }

    public HashMap<Integer, Integer> getCostCount() {
        return costCount;
    }


}


class Card {
    String name;
    int energy;

    /**
     * Constructor for Card class
     */
    public Card(String name, int energy) {
        this.energy = energy;
        this.name = name;
    }

    /**
     * Returns the energy cost of the card
     * @return energy cost of the card
     */
    public int getEnergy() {
        return energy;
    }
}
