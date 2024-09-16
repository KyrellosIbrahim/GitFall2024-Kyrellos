import java.util.ArrayList;

public class Deck {
    int deckID;
    ArrayList<Card> CardList;

    public Deck() {
        this.CardList = new ArrayList<>();
        this.deckID = generateDeckID();
    }

    public void addCard(Card card) {
        CardList.add(card);
    }

    public int getTotalEnergyCost() {
        int totalEnergyCost = 0;
        for(Card c : CardList) {
            totalEnergyCost += c.getEnergy();
        }
        return totalEnergyCost;
    }

    private int generateDeckID() {
        return (int) (Math.random() * 1000000000);
    }
}

class Card {
    String name;
    int energy;

    public Card(String name, int energy) {
        this.energy = energy;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }
}
