import enums.Action;
import enums.Strategy;
import exception.InvalidInputTypeException;

import java.util.*;

public class RockPaperScissor {

    public static void main(String[] args) {
       begin();
    }

    private static void begin(){
            Scanner scanner = new Scanner(System.in);

            // Get the game settings from the user
            int totalRounds = getTotalNumberOfRounds(scanner);
            Strategy StrategyP1 = getPlayerStrategy(scanner, 1);
            Strategy StrategyP2 = getPlayerStrategy(scanner, 2);

            // Play the game
            startGame(scanner,StrategyP1,StrategyP2,totalRounds);
    }

    public static int getTotalNumberOfRounds(Scanner scanner) {
        System.out.println("\n");
        return getValidIntegerInput(scanner, "How many games do you want to play? ");
    }

    /**
     This function chooses the player's strategy from the given list of 5, for the game of rock-paper-scissors using input from the user.
     The user must make a legitimate selection from a menu of available strategies before the system will proceed.
     It continues to ask the user for a valid input and shows an error message if an incorrect input is given. It provides a Strategy enum value as a return for the chosen strategy.
     */
    public static Strategy getPlayerStrategy(Scanner scanner, int playerNum) {
        while (true) {
            System.out.println("\n");
            System.out.println("Select the Strategy for Player " + playerNum + ":");
            System.out.println("1. Random");
            System.out.println("2. Always rock");
            System.out.println("3. Always paper");
            System.out.println("4. Always scissors");
            System.out.println("5. Alternate rock-paper");
            System.out.print("Enter the Strategy: ");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        return Strategy.RANDOM;
                    case 2:
                        return Strategy.ALWAYS_ROCK;
                    case 3:
                        return Strategy.ALWAYS_PAPER;
                    case 4:
                        return Strategy.ALWAYS_SCISSORS;
                    case 5:
                        return Strategy.ALTERNATE_ROCK_PAPER;
                    default:
                        System.out.println("\n");
                        System.out.print("Invalid Strategy choice. Please choose from the below listed Strategy");
                }
            } catch (InputMismatchException e) {
                scanner.close();
                throw new InvalidInputTypeException("Invalid input: expected a number");

            }
        }
    }

    /**
     This function starts a game between two players using the given strategies and total number of rounds.
     It calculates the results of each round based on the players' moves and updates the wins and ties accordingly.
     */
    private static void startGame(Scanner scanner,Strategy StrategyP1, Strategy StrategyP2, int totalRounds) {
        int WinsP1 =0 , WinsP2 = 0, ties = 0;

        // Calculate the initial moves for both players
        Action ActionP1 = getPlayerMove(StrategyP1, totalRounds);
        Action ActionP2 = getPlayerMove(StrategyP2, totalRounds);

        for (int i = 0; i < totalRounds; i++) {
            int result = getResult(ActionP1, ActionP2);
            WinsP1 += (result == 1) ? 1 : 0;
            WinsP2 += (result == -1) ? 1 : 0;
            ties += (result == 0) ? 1 : 0;
            // Calculate the moves for the next round
            ActionP1 = getPlayerMove(StrategyP1, i + 1);
            ActionP2 = getPlayerMove(StrategyP2, i + 1);
        }
        displayGameResult(WinsP1, WinsP2, ties, scanner);
    }

    /**
     This function will check whether the given input is valid or not
     */
    public static int getValidIntegerInput(Scanner scanner, String inputMessage) {
        while (true) {
            try {
                System.out.print(inputMessage);
                int input = scanner.nextInt();
                if (input > 0) {
                    return input;
                } else {
                    System.out.println("\n");
                    System.out.println("You have to play at least one game! Please enter a valid number.");
                }
            } catch (InputMismatchException e) {
                scanner.close();
                throw new InvalidInputTypeException("Invalid input: expected a number");
            }
        }
    }

    /**
     This function displays the final result!
     */
    private static void displayGameResult(int WinsP1, int WinsP2, int ties, Scanner scanner){
        System.out.println("\n");
        System.out.println("Game over!");
        System.out.println("No. of games won by Player 1: " + WinsP1);
        System.out.println("No. of games won by Player 2: " + WinsP2);
        System.out.println("No. of Ties: " + ties);
        String message = (WinsP1 > WinsP2) ? "Congratulations Player 1 !!" :
                (WinsP1 < WinsP2) ? "Congratulations Player 2 !!" :
                        "Game tied!! Congratulations both!!!!";
        System.out.print(message);


        // Get the game settings from the user
        Scanner sc = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("Do you want to play again? (y/n)");
        String playAgain = sc.nextLine().toLowerCase();
        if (playAgain.equalsIgnoreCase("y")) {
            begin();
        }
        System.out.println("Thanks for playing!");
        scanner.close();
    }

    /**
     This function is returning the action based on the strategy
     */
    public static Action getPlayerMove(Strategy strategy, int round) {
        Random random = new Random();
        switch(strategy) {
            case RANDOM:
                return Action.values()[random.nextInt(3)];
            case ALWAYS_ROCK:
                return Action.ROCK;
            case ALWAYS_PAPER:
                return Action.PAPER;
            case ALWAYS_SCISSORS:
                return Action.SCISSORS;
            case ALTERNATE_ROCK_PAPER:
                return round % 2 != 0 ? Action.ROCK : Action.PAPER;
            default:
                throw new IllegalArgumentException("Invalid Strategy");
        }
    }

    /**
     This function states the gameplay
     0 -> Tie
     1 -> player 1 wins
     -1 -> player 2 wins
     */
    public static int getResult(Action actionP1, Action actionP2) {
        if (actionP1 == actionP2) {
            return 0; // Tie
        }
        switch (actionP1) {
            case ROCK:
                return (actionP2 == Action.SCISSORS) ? 1 : -1;
            case PAPER:
                return (actionP2 == Action.ROCK) ? 1 : -1;
            case SCISSORS:
                return (actionP2 == Action.PAPER) ? 1 : -1;
            default:
                return 0;
        }
    }

}
