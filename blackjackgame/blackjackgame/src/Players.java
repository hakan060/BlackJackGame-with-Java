
public class Players {

    private String playerName;
    private Cards[] playerHand = new Cards[10];
    private int cardNoInHand;

    // players constructor
    public Players(String name) {

        this.playerName = name;
        this.empty();
    }
    // control empty or not
    public void empty() {

        for (int p = 0; p < 10; p++) {

            this.playerHand[p] = null;
        }

        this.cardNoInHand = 0;

    }
    // add method to player

    public boolean addCardToPlayer(Cards card) {

        if (this.cardNoInHand == 11) {

            System.out.println(this.playerName + "' cannot add more cards");
            System.exit(1);
        }

        this.playerHand[this.cardNoInHand] = card;
        this.cardNoInHand++;

        return (this.getPlayersHand() <= 21);

    }
    // get method from player 

    public int getPlayersHand() {

        int sumOfHand = 0;
        int cardNum;
        int numAces = 0;

        //System.out.println("getPlayersHand : "+ this.numCardsInHand);
        for (int c = 0; c < this.cardNoInHand; c++) {

            cardNum = this.playerHand[c].getCardNumber();
           

            if (cardNum == 1) { // Ace

                numAces++;
                sumOfHand += 11;
            } else if (cardNum >= 10) {

                sumOfHand += 10;
            } else {

                sumOfHand += cardNum;
            }
        }

        while (sumOfHand > 21 && numAces > 0) {

            sumOfHand -= 10;
            numAces--;
        }

        return sumOfHand;
    }

    // print method for cards in hand
    public void displayCardsInHand(boolean showFirstCard) {

        System.out.println(this.playerName + "'s cards in hand");
        for (int c = 0; c < this.cardNoInHand; c++) {

            if (!showFirstCard && c == 0) {

                System.out.println("HIDDEN: ");
            } else {

                //System.out.println(c);
                System.out.println(this.playerHand[c]);
            }

        }
    }

    public boolean split() {

        if (this.cardNoInHand == 2 && (this.playerHand[0].getCardNumber() == this.playerHand[1].getCardNumber())) {

            return true;
        } else {

            return false;
        }
    }
}
