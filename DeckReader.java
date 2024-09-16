import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DeckReader {

    public Deck readDeck(String fileName) throws IOException{
        Deck deck = new Deck();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while((line = reader.readLine()) != null) {
            String[] split = line.split(":"); //separates between card name and energy cost

            if(split.length==2) {
                String cardName = split[0].trim();

                try {
                    int energy = Integer.parseInt(split[1].trim());

                    if(energy >= 0 && energy <= 6 && !cardName.isEmpty()) {
                        deck.addCard(new Card(cardName, energy));
                    }
                    else { //for invalid card name
                        System.out.println("Invalid card: " + line);
                    }
                }
                catch (NumberFormatException e) { //for invalid energy cost
                    System.out.println("Invalid card: " + line);
                }
            }
        }
        reader.close();
        return deck;
    }
}
