// NOTE: type "chcp 65001" into the terminal to enable unicode display

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class BlackjackGame {
    
    private Scanner scanner;
    private Currency bank;
    private int wagerAmount;
    private boolean hasSurrendered;

    public BlackjackGame(Scanner scanner, Currency currency) {
        this.scanner = scanner;
        this.bank = currency;
    }

    public void start() throws InterruptedException {

        // Constructing objects
        BlackjackPlayer player = new BlackjackPlayer(bank);
        BlackjackDealer dealer = new BlackjackDealer();
        Deck deck = new Deck();

        boolean isQuit = false;

        // ascii art title
        clearConsole();
        animatedText("\n" + Ansi.CYAN + """                 
 /$$$$$$$  /$$                     /$$                               /$$      
| $$__  $$| $$                    | $$                              | $$      
| $$  \\ $$| $$  /$$$$$$   /$$$$$$$| $$   /$$ /$$  /$$$$$$   /$$$$$$$| $$   /$$
| $$$$$$$ | $$ |____  $$ /$$_____/| $$  /$$/|__/ |____  $$ /$$_____/| $$  /$$/
| $$__  $$| $$  /$$$$$$$| $$      | $$$$$$/  /$$  /$$$$$$$| $$      | $$$$$$/ 
| $$  \\ $$| $$ /$$__  $$| $$      | $$_  $$ | $$ /$$__  $$| $$      | $$_  $$ 
| $$$$$$$/| $$|  $$$$$$$|  $$$$$$$| $$ \\  $$| $$|  $$$$$$$|  $$$$$$$| $$ \\  $$
|_______/ |__/ \\_______/ \\_______/|__/  \\__/| $$ \\_______/  \\_______/|__/ \\__/
                                       /$$  | $$                              
                                      |  $$$$$$/                              
                                       \\______/                               
                """ + Ansi.RESET, 2);
        
        // Betting
        while (bank.getBalance() >= 10 && isQuit == false) {
            hasSurrendered = false;

            if (deck.cardsLeft() <= 12) {
                System.out.println(Ansi.CYAN + "The dealer reshuffles the deck..." + Ansi.RESET);
                deck.resetDeck();
            } 
            
            player.resetHands();
            dealer.resetHands();
            
            while (true) {
                System.out.print("How much do you want to bet? (Bank: " + Ansi.BOLD + "$" + bank.getBalance() + Ansi.RESET + "): ");
                try {
                    wagerAmount = Integer.parseInt(scanner.nextLine().trim());
                    player.placeBet(wagerAmount);
                    animatedText(Ansi.LIME_GREEN + Ansi.BOLD + "\nYou have successfully bet $" + wagerAmount + Ansi.RESET, 35);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(Ansi.BRIGHT_RED + "Please enter a valid integer." + Ansi.RESET);
                } catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }

            System.out.println();

            player.hit(deck);
            player.hit(deck);

            dealer.hit(deck);
            dealer.hit(deck);


            // Special Move
            if (bank.getBalance() >= 50) {
                System.out.println(Ansi.BOLD + "What special move do you want to use? \n(A) " + Ansi.RESET + "Ace -> Spend $50 for a guaranteed ace \n" + Ansi.BOLD + "(X) " + Ansi.RESET + "Exit -> No powerup\n");
                String moveInput = readChoice(scanner, new HashSet<>(Arrays.asList("a", "x")), Ansi.BOLD + "Enter" + Ansi.RESET + " A, X: ");
                if (moveInput.equals("a")) {
                    bank.withdraw(50);
                    player.getHand().guaranteedAce();
                } 
            }
            System.out.println();
            animatedText(". . .", 120);

            clearConsole();
            player.displayHand(player.getHand());
            dealer.displayHand();

            
            // Insurance
            int totalPayout = 0;
            int insuranceCost = 0;
            if (dealer.getHand().getCards().get(1).getRank() == Rank.ACE) {
                System.out.println(Ansi.ITALIC + "\nDealer's upcard is an Ace. Offering insurance..." + Ansi.RESET);
                Thread.sleep(2000);
                String insuranceInput = readChoice(scanner, new HashSet<>(Arrays.asList("y", "n")), "Do you want to take insurance? ($" + (int) (wagerAmount / 2.0) + ")" + Ansi.BOLD + " (Y/N): " + Ansi.RESET);
                if (insuranceInput.equals("y")) {
                    int insuranceBet = (int) (wagerAmount / 2.0);
                    if (bank.getBalance() >= insuranceBet) {
                        bank.withdraw(insuranceBet);
                        insuranceCost = insuranceBet;
                        if (dealer.getHand().isBlackjack()) {
                            animatedText(Ansi.LIME_GREEN + "\nDealer has a blackjack! You win the insurance bet." + Ansi.RESET, 30);
                            int payout = insuranceBet * 2;
                            bank.deposit(payout);
                            Thread.sleep(1000);
                            totalPayout += payout;
                        } else {
                            animatedText(Ansi.RED + Ansi.BOLD + "\nDealer does not have a blackjack. You lose the insurance bet." + Ansi.RESET, 30);
                            totalPayout -= insuranceBet;
                        }
                    } else {
                        System.out.println("You do not have enough money for the insurance bet.");
                    }
                    Thread.sleep(1000);
                }
            } else {
                dealer.peek(); // Check for dealer blackjack
            }

            clearConsole();
            
            // Player's turn
            if (!hasSurrendered && !dealer.getHand().isBlackjack()) {
                clearConsole();
                playHand(player.getHand(), deck, player, dealer, false);
            }

                if (player.hasSplit()) {
                    clearConsole();
                    Hand splitHand = player.getHands().get(1);
                    playHand(splitHand, deck, player, dealer, true);
                }

                // Dealer's turn
                if (!player.allHandsBust() && !player.getHand().isBlackjack() && !hasSurrendered) {
                    dealer.revealHoleCard();
                    player.displayHand(player.getHand());
                    dealer.displayHand();
                    Thread.sleep(1200);

                    if (dealer.getHandValue() < dealer.getThreshold()) dealer.playTurn(deck, player); 

                } else {
                    player.displayHand(player.getHand());
                    dealer.displayHand();
                    if (player.getHand().isBust()) animatedText(Ansi.UNDERLINE + Ansi.BOLD + Ansi.RED + "\nBUST!\n" + Ansi.RESET, 100);
                    Thread.sleep(1000);
                }
            
            
            // Outcome and Payout
            if (hasSurrendered) System.out.println(); // Better formatting for surrender case
            totalPayout += handleOutcome(player.getHand(), dealer, player, bank, "first hand", wagerAmount);
            if (player.hasSplit()) {
                Hand splitHand = player.getHands().get(1);
                totalPayout += handleOutcome(splitHand, dealer, player, bank, "second hand", player.getSplitBets().get(0));
            }
            
            // Round end
            int totalWager = wagerAmount + insuranceCost;
            animatedText(Ansi.BOLD + "\nWager:   " + Ansi.RESET + "$" + totalWager, 40);
            animatedText(Ansi.BOLD + "Profit: " + Ansi.RESET + (totalPayout > 0 ? Ansi.LIME_GREEN + "+$": Ansi.BRIGHT_RED + "-$") + Math.abs(totalPayout - totalWager) + Ansi.RESET, 25);
            animatedText(Ansi.BOLD + "Balance: " + Ansi.RESET + "$" + bank.getBalance(), 40);
            Thread.sleep(450);
            System.out.println("\n==============================\nPress " + Ansi.BOLD + "ENTER " + Ansi.RESET + "to start next round... (Q to quit)" + Ansi.RESET + "\n==============================");
            String endScreenInput = scanner.nextLine();

            if (endScreenInput.toLowerCase().equals("q")) isQuit = true;
            clearConsole();
        }
        return;
    }

    private void playHand(Hand hand, Deck deck, BlackjackPlayer player, BlackjackDealer dealer, boolean showBanner) throws InterruptedException {
        boolean isStand = false;
        while (!(hand.isBust() || hand.isBlackjack()) && !isStand && !dealer.getHand().isBlackjack()) {

            clearConsole();
            if (showBanner) System.out.println("\n=============== Now playing second hand... ==============\n" + Ansi.RESET);
            player.displayHand(hand);
            dealer.displayHand();
            
            HashSet<String> validOptions = new HashSet<>() {
                {
                    add("s");
                    add("h");
                }
            };

            System.out.println(Ansi.BOLD + "\nChoose your action:\n(H) " + Ansi.RESET + "Hit -> Take another card \n" + Ansi.BOLD + "(S) " + Ansi.RESET + "Stand -> Keep your current total");
            if ((player.canDoubleDown() && !player.hasSplit()) || (player.hasSplit() && player.getHands().size() == 1)) {
                System.out.println(Ansi.BOLD + "(D) " + Ansi.RESET + "Double Down -> Double your bet, take one card, then stand");
                validOptions.add("d");
            }
            if ((player.canSplit() && player.enoughMoney()) && !player.hasSplit()) { // Prevent multiple splits (does not align with time constraints; improvement for future)
                System.out.println(Ansi.BOLD + "(X) " + Ansi.RESET + "Split -> Split your hand into two, then place an additional bet on the second hand");
                validOptions.add("x");
            }

            // Additional surrender option to mirror real blackjack rules, although not required for success criteria
            if (hand.getCards().size() == 2 && !player.hasSplit()) {
                System.out.println(Ansi.BOLD + "(R) " + Ansi.RESET + "Surrender -> Forfeit half your bet and end the round");
                validOptions.add("r");
            }
            System.out.println();

            String selection = readChoice(scanner, validOptions, Ansi.BOLD + "Enter " + Ansi.RESET + String.join(", ", validOptions).toUpperCase() + ": ");
            System.out.println();

            // Selection logic
            switch (selection) {
                case "h":
                    player.hit(deck, hand);
                    break;
                case "s":
                    isStand = true;
                    break;
                case "d":
                    player.doubleDown();
                    wagerAmount *= 2;
                    player.hit(deck, hand);
                    isStand = true;
                    break;
                case "x":
                    player.split(deck);
                    wagerAmount *= 2;
                    break;
                case "r":
                    animatedText(Ansi.ITALIC + Ansi.BOLD + "\nYou surrendered and forfeit half your bet." + Ansi.RESET, 50);
                    isStand = true;
                    hasSurrendered = true;
                    break;
            }
            clearConsole();
        }
    }

    private int handleOutcome(Hand hand, BlackjackDealer dealer, BlackjackPlayer player, Currency bank, String handName, int betAmount) throws InterruptedException {
        int payout = 0;
        if (player.hasSplit()) {
            System.out.println(Ansi.BOLD + "\nResults for " + handName + ":" + Ansi.RESET);
        }
        StringBuilder endMessage = new StringBuilder("==============================\n" + Ansi.BOLD + "Result: " + Ansi.RESET);
        if (hasSurrendered) {
            endMessage.append("YOU " + Ansi.BOLD + Ansi.YELLOW + "SURRENDERED. " + Ansi.RESET + "(-_-) zzz");
            payout = (int) (betAmount / 2.0);

        } else if (hand.isBlackjack()) {
            endMessage.replace(0, endMessage.length(), Ansi.BOLD + "\n╔═══════════════════════════╗\n" + "║     🎉  BLACKJACK!!  🎉    ║\n" + "╚═══════════════════════════╝\n" + Ansi.RESET);
            payout = betAmount + (int) (betAmount / 2 * 3); // Blackjack pays 3 to 2

        } else if ((hand.getValue() > dealer.getHandValue() && hand.getValue() <= 21) || dealer.getHand().isBust() == true) {
            endMessage.append("YOU " + Ansi.BOLD + Ansi.LIME_GREEN + "WIN! " + Ansi.RESET + "\\m/ (>.<) \\m/");
            payout = betAmount * 2;

        } else if (hand.getValue() == dealer.getHandValue()) {
            endMessage.append(Ansi.ITALIC + "PUSH... (Draw) " + Ansi.RESET);
            payout = betAmount;

        } else {
            endMessage.append("YOU " + Ansi.BOLD + Ansi.RED + "LOST! " + Ansi.RESET + "t(-_-t)");
        }
        System.out.println(endMessage.toString() + "\n==============================");
        bank.deposit(payout);
        Thread.sleep(800);
        return payout;
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void animatedText(String word, int delay) throws InterruptedException {
        for (int i = 0; i < word.length(); i++) {
            System.out.print(word.toCharArray()[i]);
            Thread.sleep(delay);
        }
        System.out.println();
    }

    public static String readChoice(Scanner scanner, HashSet<String> validOptions, String prompt) {
        String selection;
        while (true) {
            System.out.print(Ansi.BOLD + prompt + Ansi.RESET);
            selection = scanner.nextLine().trim().toLowerCase();
            if (validOptions.contains(selection)) return selection;
            System.out.println(Ansi.BRIGHT_RED + "Please enter a valid option.\n" + Ansi.RESET);
        }
    }
}