import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Currency currency = new Currency();
        Scanner scanner = new Scanner(System.in);
        
        boolean isQuit = false;

        clearConsole();
        animatedText(Ansi.BOLD + Ansi.CYAN + "Welcome to the Casino!\n" + Ansi.RESET, 20);
        System.out.println(Ansi.YELLOW + Ansi.BOLD + "(!) " + Ansi.ITALIC + "Type \"chcp 65001\" into the console to enable unicode display\n" + Ansi.RESET);

        BlackjackGame Blackjack = new BlackjackGame(scanner, currency);

        while (!isQuit) {
            String selection;
            while (true) {
                System.out.println(Ansi.BOLD + "\nWhat do you want to do?" + Ansi.RESET);
                System.out.println(Ansi.BOLD + "(1) " + Ansi.RESET + "Play Blackjack");
                System.out.println(Ansi.BOLD + "(2) " + Ansi.RESET + "Quit\n");

                System.out.print(Ansi.BOLD + "Enter" + Ansi.RESET + " 1, 2: ");
                selection = scanner.nextLine().trim();

                if (selection.equals("1") || selection.equals("2")) break;
                System.out.println(Ansi.BRIGHT_RED + "\nPlease enter a valid option.\n" + Ansi.RESET);
            }

            switch (Integer.parseInt(selection)) {
                case 1:
                    Blackjack.start();
                    break;
                case 2:
                    isQuit = true;
                    break;
            }
        }
        animatedText(Ansi.CYAN + "\nFinal Currency balance: " + Ansi.BOLD + "$" + currency.getBalance() + Ansi.RESET + Ansi.BOLD + Ansi.ITALIC + "\nThanks for playing!" + Ansi.RESET, 30);
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
}
