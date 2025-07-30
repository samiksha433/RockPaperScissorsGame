import java.util.Scanner;
import java.util.Random;

public class RockPaperScissorsGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String[] options = {"rock", "paper", "scissors"};

        System.out.println("🎮 Welcome to Rock Paper Scissors Game!");
        System.out.print("👤 Enter your name: ");
        String playerName = scanner.nextLine().trim();

        boolean playAgain = true;

        while (playAgain) {
            int userScore = 0;
            int computerScore = 0;
            final int totalRounds = 5;

            System.out.println("\n🎯 Starting the game: Best of " + totalRounds + " rounds");
            System.out.println("---------------------------------------------------");

            for (int round = 1; round <= totalRounds; round++) {
                System.out.println("\n🔁 Round " + round + " of " + totalRounds);
                String userChoice = "";

                while (true) {
                    System.out.print("👉 " + playerName + ", enter your move (rock/paper/scissors): ");
                    userChoice = scanner.nextLine().trim().toLowerCase();

                    if (userChoice.equals("rock") || userChoice.equals("paper") || userChoice.equals("scissors")) {
                        break;
                    } else {
                        System.out.println("⚠️ Invalid input. Please enter only rock, paper, or scissors.");
                    }
                }

                String computerChoice = options[random.nextInt(3)];
                System.out.println("🤖 Computer chose: " + computerChoice);

                if (userChoice.equals(computerChoice)) {
                    System.out.println("🤝 It's a tie this round.");
                } else if (
                    (userChoice.equals("rock") && computerChoice.equals("scissors")) ||
                    (userChoice.equals("paper") && computerChoice.equals("rock")) ||
                    (userChoice.equals("scissors") && computerChoice.equals("paper"))
                ) {
                    System.out.println("✅ " + playerName + " wins this round!");
                    userScore++;
                } else {
                    System.out.println("❌ Computer wins this round.");
                    computerScore++;
                }

                System.out.println("📊 Scoreboard => " + playerName + ": " + userScore + " | Computer: " + computerScore);
            }

            System.out.println("\n🏁 Final Result After " + totalRounds + " Rounds:");
            System.out.println("---------------------------------------------------");
            System.out.println("📌 Final Score => " + playerName + ": " + userScore + " | Computer: " + computerScore);

            if (userScore > computerScore) {
                System.out.println("🏆 Congratulations " + playerName + ", you are the overall winner!");
            } else if (userScore < computerScore) {
                System.out.println("😔 Computer wins the game. Better luck next time!");
            } else {
                System.out.println("🤝 The game ended in a draw!");
            }

            // Ask for restart
            System.out.print("\n🔁 Do you want to play again? (yes/no): ");
            String again = scanner.nextLine().trim().toLowerCase();

            if (!again.equals("yes")) {
                playAgain = false;
                System.out.println("\n👋 Thanks for playing, " + playerName + "! See you next time!");
                System.out.println("===================================================");
            } else {
                System.out.println("\n🔄 Restarting game...");
                System.out.println("===================================================");
            }
        }

        scanner.close();
    }
}
