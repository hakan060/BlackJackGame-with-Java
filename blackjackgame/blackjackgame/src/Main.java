

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    private Deck newDeck;
    private String playerName;
    private float money;
    private float yourBet;
    private boolean youIsDone;
    private boolean dealerIsDone;
    private Players dealer;
    private Players you;
    private Scanner scan = new Scanner(System.in);

    // GameMain constructor
    Main(String pName) {

        this.money = 10;
        this.newDeck = new Deck(2, true);
        boolean gameOver = false;
        this.playerName = pName;

        System.out.println("Welcome, " + this.playerName + "! You have got 10 Turkish Liras!\n");

        // Players init
        you = new Players(this.playerName);
        dealer = new Players("Dealer: ");

        // Game Starts
        while (this.money > 0 && !gameOver) {

            System.out.println("\n" + this.playerName + ", Do you want to DEAL or END the game [D or E]??");
            String gameInit = scan.next();

            if (gameInit.compareToIgnoreCase("D") == 0) {

                this.dealTheGame();

            } else {

                gameOver = true;
            }
        }

        System.out.println(this.playerName + " END GAME");

        // Play again
        System.out.println(this.playerName + " PLAY AGAIN? [Y or N]");
        String Y = scan.next();
        if (Y.compareToIgnoreCase("Y") == 0) {

            new Main(this.playerName);
        }

        //close scan
        scan.close();

    }

    // Deal the game
    private void dealTheGame() {

        boolean blackjack = false;
        this.yourBet = 0;
        System.out.println("BALANCE: " + this.money + " TL");
        String msg = "Enter your bet:";

        while (this.yourBet <= 0) {

            try {

                System.out.println("\n" + msg);
                this.yourBet = scan.nextFloat();
            } catch (InputMismatchException e) {

                scan.nextLine();
            } finally {

                msg = "Please enter integer! ";
            }
        }

        // make a choice to the user
        System.out.println("To make randomly choose (1), to make card choose (2)");
        String userPick = scan.next();

        // make randomly choose
        if (userPick.equals("1")) {
            if ((this.yourBet >= 1) && (this.yourBet % 1 == 0) && (this.money - this.yourBet >= 0)) {

                this.money = this.money - this.yourBet;

                // players start with empty hands
                you.empty();
                dealer.empty();

                this.youIsDone = false;
                this.dealerIsDone = false;

                // Distributing initial cards to players
                you.addCardToPlayer(newDeck.agreeNextCard());
                dealer.addCardToPlayer(newDeck.agreeNextCard());
                you.addCardToPlayer(newDeck.agreeNextCard());
                dealer.addCardToPlayer(newDeck.agreeNextCard());

                // Cards Dealt
                System.out.println("--- CARDS DETAIL --- ");
                dealer.displayCardsInHand(false);
                you.displayCardsInHand(true);

                System.out.println("Your Score:" + you.getPlayersHand());
                System.out.println("Bet:" + this.yourBet + " TL");
                System.out.println("Balance: " + this.money + " TL");

                // checking state on initial card -- if BlackJack
                blackjack = this.controlBlackJack();

                while (!this.youIsDone || !this.dealerIsDone) {

                    if (!this.youIsDone) {

                        this.yourTurn(userPick);

                    } else if (!this.dealerIsDone) {

                        this.dealersTurn();
                    }

                    System.out.println();
                }

                if (!blackjack) {

                    this.awardWinner();
                }
            } else {

                System.out.println("Your Balance: " + this.money + "TL");
            }
        } // make card choose
        else if (userPick.equals("2")) {

            if ((this.yourBet >= 1) && (this.yourBet % 1 == 0) && (this.money - this.yourBet >= 0)) {

                this.money = this.money - this.yourBet;

                // player starts empty
                you.empty();
                dealer.empty();

                this.youIsDone = false;
                this.dealerIsDone = false;

                // initial cards
                you.addCardToPlayer(newDeck.agreeNextCard());
                dealer.addCardToPlayer(newDeck.agreeNextCard());
                you.addCardToPlayer(newDeck.agreeNextCard());
                dealer.addCardToPlayer(newDeck.agreeNextCard());

                // Cards Detail
                System.out.println("--- CARDS DETAIL ---");
                dealer.displayCardsInHand(false);
                you.displayCardsInHand(true);

                System.out.println("Your Score:" + you.getPlayersHand());
                System.out.println("Bet:" + this.yourBet + " TL");
                System.out.println("Balance: " + this.money + " TL");

                // check state of card
                blackjack = this.controlBlackJack();

                while (!this.youIsDone || !this.dealerIsDone) {

                    if (!this.youIsDone) {

                        this.yourTurn(userPick);

                    } else if (!this.dealerIsDone) {

                        this.dealersTurn();
                    }

                    System.out.println();
                }

                if (!blackjack) {

                    this.awardWinner();
                }
            } else {

                System.out.println("Your Balance: " + this.money + " TL");
            }

        } else {
            System.out.println("Please enter 1 or 2");
        }
    }

    // Blackjack control
    private boolean controlBlackJack() {

        boolean blackJack = false;

        if (you.getPlayersHand() == 21) {

            this.youIsDone = true;
            this.dealerIsDone = true;

            if (you.getPlayersHand() > dealer.getPlayersHand() || dealer.getPlayersHand() > 21) {

                System.out.println("YOU WON!");

                dealer.displayCardsInHand(true);

                System.out.println("Dealer's Score: " + dealer.getPlayersHand());
                System.out.println("Your Bet was :" + this.yourBet + " TL");
                System.out.println("Your Balance was: " + this.money + " TL");
                System.out.println("You win[3:2]: " + (3 * this.yourBet) / 2);

                this.money = this.money + (3 * this.yourBet) / 2 + this.yourBet;
                System.out.println("Your Current Balance:" + this.money + " TL");

                blackJack = true;
            } else {

                dealer.displayCardsInHand(true);

                System.out.println("Dealer's Score:" + dealer.getPlayersHand());
                blackJack = false;
            }
        } else if (dealer.getPlayersHand() == 21) {

            dealer.displayCardsInHand(true);
            System.out.println("Dealer's Score: " + dealer.getPlayersHand());

            System.out.println("DEALER IS BLACKJACK, YOU LOST");

            this.dealerIsDone = true;
            blackJack = false;
        }

        return blackJack;
    }

    // Player's play here
    private void yourTurn(String userPick) {

        String answer;

        if (this.money >= this.yourBet) {

            System.out.print("Hit or Stay [H or S]");
        } else {

            System.out.print("Hit or Stay? [Enter H or S]: ");
        }

        answer = scan.next();
        System.out.println();

        if (answer.compareToIgnoreCase("H") == 0) {

            this.hit(userPick);

        } else {
            this.stay();
            //System.out.println("please enter H or S");
        }
    }

    // Player's Hit
    private void hit(String userPick) {
        if (userPick.equals("1")) {
            System.out.println("You Choose to Hit.");
            youIsDone = !you.addCardToPlayer(newDeck.agreeNextCard());
            you.displayCardsInHand(true);
            System.out.println("Your Score: " + you.getPlayersHand());
            System.out.println("Bet:" + this.yourBet + " TL");
            System.out.println("Balance: " + this.money + " TL");

            if (you.getPlayersHand() > 21) {

                System.out.println("YOU BUSTED!");

                dealer.displayCardsInHand(true);
                System.out.println("Dealer's Score:" + dealer.getPlayersHand());
                youIsDone = true;
                dealerIsDone = true;
            }
        } else if (userPick.equals("2")) {
            System.out.println("You Choose to Hit.");
            System.out.println("You can choose the card number");

            String choosenCardNo = scan.next();
            scan.nextLine();

            //System.out.println(choosenCardNo);
            youIsDone = you.addCardToPlayer(newDeck.showPickedCardtoUser(choosenCardNo));
            you.displayCardsInHand(true);
            System.out.println("Your Score: " + you.getPlayersHand());
            System.out.println("Bet: " + this.yourBet + " TL");
            System.out.println("Balance: " + this.money + " TL");

            if (you.getPlayersHand() > 21) {

                System.out.println("YOU BUSTED! ");

                dealer.displayCardsInHand(true);
                System.out.println("Dealer's Score: " + dealer.getPlayersHand());
                youIsDone = true;
                dealerIsDone = true;
            }
        }

    }

    // Player's Stay
    private void stay() {

        System.out.println("You Choose to Stay, Dealer's turn");
        youIsDone = true;
    }

    // Dealer's Play here
    private void dealersTurn() {

        if (dealer.getPlayersHand() < 17) {

            dealer.displayCardsInHand(true);
            System.out.println("Dealer's Score: " + dealer.getPlayersHand());
            System.out.println("Dealer Hits ");
            dealerIsDone = !dealer.addCardToPlayer(newDeck.agreeNextCard());

            if (dealer.getPlayersHand() > 21) {

                dealer.displayCardsInHand(true);
                System.out.println("Dealer's Score: " + dealer.getPlayersHand());

                System.out.println("DEALER BUSTED! ");

                dealerIsDone = true;
            }
        } else {

            dealer.displayCardsInHand(true);
            System.out.println("Dealer's Score: " + dealer.getPlayersHand());
            System.out.println("Dealer Stays");
            dealerIsDone = true;
        }
    }

    // Decide who won
    private void awardWinner() {

        int youSum = you.getPlayersHand();
        int dealerSum = dealer.getPlayersHand();

        if (youSum > dealerSum && youSum <= 21 || dealerSum > 21) {

            System.out.println("YOU WON! ");

            System.out.println("Your Bet was : " + this.yourBet + " TL");
            System.out.println("Your Balance was: " + this.money + " TL");
            System.out.println("You win[1:1] : " + this.yourBet + " TL");

            this.money = this.money + this.yourBet + this.yourBet;
            System.out.println("Your Current Balance: " + money + " TL");

        } else if (youSum == dealerSum) {

            System.out.println("PUSH   ");

            this.money = this.money + this.yourBet;
            System.out.println("Your Current Balance:" + this.money + " TL");
        } else {

            System.out.println("YOU LOST ");

            System.out.println("You lose[1:1]: " + this.yourBet + " TL");
            System.out.println("Your Current Balance: " + this.money + " TL");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String playerName;

        System.out.println("************WELCOME TO AYBU CASINO********");

        System.out.println("Enter Your Name:\n");
        playerName = scanner.nextLine();

        new Main(playerName);

        scanner.close();
    }

}
