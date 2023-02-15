
public class Cards {

    private Suits cardSuit;
    private int cardNumber;
    private String[] num = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};

    // card constructor
    public Cards(Suits stype, int snum) {

        this.cardSuit = stype;

        // card control
        if (snum >= 1 && snum <= 13) {
            this.cardNumber = snum;
        } else {

            System.out.println(snum + " is not a valid card number\n");
            System.exit(1);
        }
    }

    // get method
    public int getCardNumber() {

        return this.cardNumber;
    }
    // toString method

    public String toString() {

        return this.num[this.cardNumber - 1] + " of " + this.cardSuit.toString();
    }

}
