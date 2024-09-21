import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.HashMap;
import java.util.Map;

public class DeckReader {
    //first integer is the energy cost, second integer is the number of cards with that energy cost
    private HashMap<Integer, Integer> costCount = new HashMap<>();

    /**
     * Reads the deck from the input file
     * @param fileName name of the input file
     * @return deck object with the cards from the input file
     * @throws IOException if error occurs while reading the file
     */
    public Deck readDeck(String fileName) throws IOException, InvalidDeckException {
        Deck deck = new Deck();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        int numberOfLines = 0; //must not exceed 1000 lines in the deck
        int invalidCards = 0; //must not exceed 10 invalid cards in the input file

        try {
            while((line = reader.readLine()) != null) {
                numberOfLines++;
                if(numberOfLines > 1000) {
                    reader.close();
                    throw new InvalidDeckException("Deck is too large", deck.getDeckId());
                }
                String[] split = line.split(":"); //separates between card name and energy cost

                if(split.length==2) {
                    String cardName = split[0].trim();

                    try {
                        int energy = Integer.parseInt(split[1].trim());

                        if(ValidCards.isValidCard(cardName, energy)) {
                            deck.addCard(new Card(cardName, energy));
                            //count the number of occurrences of each card for the histogram
                            costCount.put(energy, costCount.getOrDefault(energy, 0) + 1);
                        }
                        else { //for invalid card name
                            System.out.println("Invalid card: " + line);
                            invalidCards++;
                            if(invalidCards > 10) {
                                reader.close();
                                throw new InvalidDeckException("Too many invalid cards", deck.getDeckId());
                            }
                        }
                    }
                    catch (NumberFormatException e) { //for invalid energy cost
                        System.out.println("Invalid card: " + line);
                        invalidCards++;
                        if(invalidCards > 10) {
                            reader.close();
                            throw new InvalidDeckException("Too many invalid cards", deck.getDeckId());
                        }
                    }
                }
            }
        }
        finally {
            reader.close();
        }
        return deck;
    }

    /**
     * Generates a histogram of the energy cost of the cards in the deck
     * @param costCount Occurrences of each energy cost
     * @return histogram of the energy cost of the cards in the deck
     */
    public static JFreeChart generateHistogram(HashMap<Integer, Integer> costCount) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Map.Entry<Integer, Integer> entry : costCount.entrySet()) {
            dataset.addValue(entry.getValue(), "Number of Cards", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Energy Cost Histogram",
                "Energy Cost",
                "Number of Cards",
                dataset
        );
        return chart;
    }

    /**
     * returns the hashmap of the energy cost
     * @return hashmap of the energy cost
     */
    public HashMap<Integer, Integer> getCostCount() {
        return costCount;
    }
}

class ValidCards { //to check actual cards
    private static final HashMap<String, Integer> validCards = new HashMap<>();

    //put all the cards into a map for validation
    //got the card list from https://maybelatergames.co.uk/tools/slaythespire/
    static {
        validCards.put("Strike", 1);
        validCards.put("Defend", 1);
        validCards.put("Bash", 2);
        validCards.put("Anger", 0);
        validCards.put("Body Slam", 1);
        validCards.put("Clash", 0);
        validCards.put("Cleave", 1);
        validCards.put("Clothesline", 2);
        validCards.put("Headbutt", 1);
        validCards.put("Heavy Blade", 2);
        validCards.put("Iron wave", 1);
        validCards.put("Perfected Strike", 2);
        validCards.put("Pommel Strike", 1);
        validCards.put("Sword Boomerang", 1);
        validCards.put("Thunderclap", 1);
        validCards.put("Twin Strike", 1);
        validCards.put("Wild Strike", 1);
        validCards.put("Blood for Blood", 4);
        validCards.put("Carnage", 2);
        validCards.put("Dropkick", 1);
        validCards.put("Hemokinesis", 1);
        validCards.put("Pummel", 1);
        validCards.put("Rampage", 1);
        validCards.put("Reckless Charge", 0);
        validCards.put("Searing Blow", 2);
        validCards.put("Sever Soul", 2);
        validCards.put("Uppercut", 2);
        validCards.put("Whirlwind", 0);
        validCards.put("Bludgeon", 3);
        validCards.put("Feed", 1);
        validCards.put("Fiend Fire", 2);
        validCards.put("Immolate", 2);
        validCards.put("Reaper", 2);
        validCards.put("Armaments", 1);
        validCards.put("Barricade", 3);
        validCards.put("Battle Trance", 0);
        validCards.put("Berserk", 0);
        validCards.put("Blood For Blood", 4);
        validCards.put("Bloodletting",0);
        validCards.put("Brutality", 0);
        validCards.put("Burning Pact", 1);
        validCards.put("Combust", 1);
        validCards.put("Corruption", 3);
        validCards.put("Dark Embrace", 2);
        validCards.put("Demon Form", 3);
        validCards.put("Disarm", 1);
        validCards.put("Double Tap", 1);
        validCards.put("Dual Wield", 1);
        validCards.put("Entrench", 2);
        validCards.put("Evolve", 1);
        validCards.put("Exhume", 1);
        validCards.put("Feel No Pain", 1);
        validCards.put("Fire Breathing", 1);
        validCards.put("Flame Barrier", 2);
        validCards.put("Flex", 0);
        validCards.put("Ghostly Armor", 1);
        validCards.put("Havoc", 1);
        validCards.put("Inflame", 2);
        validCards.put("Impervious", 2);
        validCards.put("Infernal Blade", 1);
        validCards.put("Intimidate", 0);
        validCards.put("Iron Wave", 1);
        validCards.put("Juggernaut", 2);
        validCards.put("Limit Break", 1);
        validCards.put("Metallicize", 1);
        validCards.put("Offering", 0);
        validCards.put("Power Through", 1);
        validCards.put("Rage", 0);
        validCards.put("Rupture", 1);
        validCards.put("Second Wind", 1);
        validCards.put("Seeing Red", 1);
        validCards.put("Sentinel", 1);
        validCards.put("Shrug it Off", 1);
        validCards.put("Shockwave", 2);
        validCards.put("Spot Weakness", 1);
        validCards.put("True Grit", 1);
        validCards.put("Warcry", 0);

    }

    /**
     * Checks if the card is valid
     * @param cardName name of the card to be tested
     * @param cost energy cost of the card to be tested
     * @return true if the card is valid, false otherwise
     */
    public static boolean isValidCard(String cardName, int cost) {
        return validCards.containsKey(cardName) && validCards.get(cardName) == cost;
    }
}


class InvalidDeckException extends Exception {
    private int deckId;

    public InvalidDeckException(String message, int deckId) {
        super(message);
        this.deckId = deckId;
    }

    public int getDeckId() {
        return deckId;
    }
}