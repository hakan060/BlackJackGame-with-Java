
import java.util.Random;

public class Deck {

    // 1 Pack = 52 cards
    // We have 2 pack in the Deck
    // cards in the deck --- arraylist or linkedlist could also be used
    private Cards[] cards;
    private int numberOfCards;
    private int onePackNumber = 52;

    // constructor for shuffled
    public Deck() {

        this(1, true);
    }

    // Shuffle control
    public Deck(int numPacks, boolean shuffle) {

        this.numberOfCards = numPacks * this.onePackNumber;
        this.cards = new Cards[this.numberOfCards];

        int c = 0;

        // for each deck
        for (int d = 0; d < numPacks; d++) {

            // for each suit
            for (int s = 0; s < 4; s++) {

                // for each number
                for (int n = 1; n <= 13; n++) {

                    this.cards[c] = new Cards(Suits.values()[s], n);
                    c++;
                }
            }
        }
        //shuffle the Deck
        if (shuffle) {

            this.shuffle();
        }
        int l;
        for (l = 0; l < cards.length; l++) {
            System.out.println(l + 1 + ". " + cards[l]);
        }

    }

    public void shuffle() {

        Random random = new Random();

        Cards temp;

        // swap
        int j;
        for (int i = 0; i < this.numberOfCards; i++) {

            j = random.nextInt(this.numberOfCards);

            temp = this.cards[i];
            this.cards[i] = this.cards[j];
            this.cards[j] = temp;
        }
    }

    // dealing for the next card
    public Cards agreeNextCard() {

        Cards topCard = this.cards[0];

        for (int c = 1; c < this.numberOfCards; c++) {

            this.cards[c - 1] = this.cards[c];
        }

        this.cards[this.numberOfCards - 1] = null;
        this.numberOfCards--;

        return topCard;
    }

    public Cards showPickedCardtoUser(String choosenCardNo) {
        System.out.println("*************************************************************");
        System.out.println(cards[Integer.parseInt(choosenCardNo)]);
        System.out.println("*************************************************************");
        return cards[Integer.parseInt(choosenCardNo)];

    }
}
