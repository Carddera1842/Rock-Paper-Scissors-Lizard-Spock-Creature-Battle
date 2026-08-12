package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String[] options = {"rock", "paper", "scissors"};

        int userScore = 0;
        int computerScore = 0;
        int ties = 0;
        int rounds = 0;

        System.out.println("Welcome to Rock, Paper, Scissors!");

        while (true) {
            System.out.print("\nEnter your move (rock, paper, or scissors): ");
            String userMove = scanner.nextLine().toLowerCase().trim();

            while (!userMove.equals("rock") && !userMove.equals("paper") && !userMove.equals("scissors")) {
                System.out.print("Invalid move! Please enter rock, paper, or scissors!");
                userMove = scanner.nextLine().toLowerCase().trim();
            }

            int compIndex = random.nextInt(3);
            String computerMove = options[compIndex];

            System.out.println("Computer chose: " + computerMove);

            if (userMove.equals(computerMove)) {
                System.out.println("It's a tie!");
                ties++;
            } else if (
                    (userMove.equals("rock") && computerMove.equals("scissors")) ||
                            (userMove.equals("scissors") && computerMove.equals("paper")) ||
                            (userMove.equals("paper") && computerMove.equals("rock"))
            ) {
                System.out.println("You win!");
                userScore++;
            } else {
                System.out.println("You lose!");
                computerScore++;
            }
            rounds++;

            System.out.print("Play again? (yes/no): ");
            String playAgain = scanner.nextLine().toLowerCase().trim();

            if (!playAgain.equals("yes")) {
                break;
            }
        }
        System.out.println("Thanks for playing! Final Score -> You: " + userScore);
        scanner.close();
    }
}