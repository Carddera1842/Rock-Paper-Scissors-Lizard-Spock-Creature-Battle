package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String[] options = {"rock",
                            "paper",
                            "scissors",
                            "lizard",
                            "spock"};

        int playerHP = 100;
        int computerHP = 100;

        System.out.println("Welcome to RPSLS Battle!");

        System.out.println("===========================");
        System.out.println("        BATTLE TIME!");
        System.out.println("===========================");

        // -------------------------
        // CHOOSE FIGHTER
        // -------------------------

        System.out.println("\nChoose your fighter!");
        System.out.println("1. Rock");
        System.out.println("2. Paper");
        System.out.println("3. Scissors");
        System.out.println("4. Lizard");
        System.out.println("5. Spock");

        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        while (choice < 1 || choice > 5) {
            System.out.print(
                    "Invalid choice. Choose 1, 2, 3, 4, or 5: "
            );

            choice = scanner.nextInt();
            scanner.nextLine();
        }

        String playerFighter = options[choice - 1];

        // Computer randomly chooses a fighter
        int computerChoice = random.nextInt(5);
        String computerFighter = options[computerChoice];

        System.out.println("\nYou chose " + playerFighter + "!");
        System.out.println(
                "Your opponent chose " + computerFighter + "!"
        );

        System.out.println("\nBattle Start!");


        // System.out.println("Thanks for playing! Final Score -> You: " + userScore);
        //scanner.close();
    }
}