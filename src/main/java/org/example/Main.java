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

        // -------------------------
        // BATTLE LOOP
        // -------------------------

        while (playerHP > 0 && computerHP > 0) {

            System.out.println("\n----------------------------");
            System.out.println(playerFighter + " HP: " + playerHP);
            System.out.println(computerFighter + " HP: " + computerHP);
            System.out.println("----------------------------");

            String playerAttack = "";
            int playerDamage = 0;

            // -------------------------
            // PLAYER ATTACK MENU
            // -------------------------

            System.out.println("\nChoose your attack:");

            if (playerFighter.equals("rock")) {
                System.out.println("1. Pebble Toss");
                System.out.println("2. Rock Smash");
                System.out.println("3. Boulder Crush");

            } else if (playerFighter.equals("paper")) {
                System.out.println("1. Paper Cut");
                System.out.println("2. Paper Plane");
                System.out.println("3. Origami Strike");

            } else if (playerFighter.equals("scissors")) {
                System.out.println("1. Snip");
                System.out.println("2. Double Cut");
                System.out.println("3. Blade Rush");

            } else if (playerFighter.equals("lizard")) {
                System.out.println("1. Bite");
                System.out.println("2. Tail Swipe");
                System.out.println("3. Poison Spit");

            } else if (playerFighter.equals("spock")) {
                System.out.println("1. Vulcan Chop");
                System.out.println("2. Logic Blast");
                System.out.println("3. Mind Meld");
            }

            System.out.print("Attack: ");

            int attackChoice = scanner.nextInt();
            scanner.nextLine();

            while (attackChoice < 1 || attackChoice > 3) {
                System.out.print(
                        "Invalid attack. Choose 1, 2, or 3: "
                );

                attackChoice = scanner.nextInt();
                scanner.nextLine();
            }

            // -------------------------
            // PLAYER ATTACK
            // -------------------------

            if (playerFighter.equals("rock")) {

                if (attackChoice == 1) {
                    playerAttack = "Pebble Toss";
                    playerDamage = 10;
                } else if (attackChoice == 2) {
                    playerAttack = "Rock Smash";
                    playerDamage = 20;
                } else {
                    playerAttack = "Boulder Crush";
                    playerDamage = 30;
                }

            } else if (playerFighter.equals("paper")) {

                if (attackChoice == 1) {
                    playerAttack = "Paper Cut";
                    playerDamage = 10;
                } else if (attackChoice == 2) {
                    playerAttack = "Paper Plane";
                    playerDamage = 20;
                } else {
                    playerAttack = "Origami Strike";
                    playerDamage = 30;
                }

            } else if (playerFighter.equals("scissors")) {

                if (attackChoice == 1) {
                    playerAttack = "Snip";
                    playerDamage = 10;
                } else if (attackChoice == 2) {
                    playerAttack = "Double Cut";
                    playerDamage = 20;
                } else {
                    playerAttack = "Blade Rush";
                    playerDamage = 30;
                }

            } else if (playerFighter.equals("lizard")) {

                if (attackChoice == 1) {
                    playerAttack = "Bite";
                    playerDamage = 10;
                } else if (attackChoice == 2) {
                    playerAttack = "Tail Swipe";
                    playerDamage = 20;
                } else {
                    playerAttack = "Poison Spit";
                    playerDamage = 30;
                }

            } else if (playerFighter.equals("spock")) {

                if (attackChoice == 1) {
                    playerAttack = "Vulcan Chop";
                    playerDamage = 10;
                } else if (attackChoice == 2) {
                    playerAttack = "Logic Blast";
                    playerDamage = 20;
                } else {
                    playerAttack = "Mind Meld";
                    playerDamage = 30;
                }
            }

            // -------------------------
            // COMPUTER ATTACK
            // -------------------------

            int computerAttackChoice = random.nextInt(3) + 1;

            String computerAttack = "";
            int computerDamage = 0;

            if (computerFighter.equals("rock")) {

                if (computerAttackChoice == 1) {
                    computerAttack = "Pebble Toss";
                    computerDamage = 10;
                } else if (computerAttackChoice == 2) {
                    computerAttack = "Rock Smash";
                    computerDamage = 20;
                } else {
                    computerAttack = "Boulder Crush";
                    computerDamage = 30;
                }

            } else if (computerFighter.equals("paper")) {

                if (computerAttackChoice == 1) {
                    computerAttack = "Paper Cut";
                    computerDamage = 10;
                } else if (computerAttackChoice == 2) {
                    computerAttack = "Paper Plane";
                    computerDamage = 20;
                } else {
                    computerAttack = "Origami Strike";
                    computerDamage = 30;
                }

            } else if (computerFighter.equals("scissors")) {

                if (computerAttackChoice == 1) {
                    computerAttack = "Snip";
                    computerDamage = 10;
                } else if (computerAttackChoice == 2) {
                    computerAttack = "Double Cut";
                    computerDamage = 20;
                } else {
                    computerAttack = "Blade Rush";
                    computerDamage = 30;
                }

            } else if (computerFighter.equals("lizard")) {

                if (computerAttackChoice == 1) {
                    computerAttack = "Bite";
                    computerDamage = 10;
                } else if (computerAttackChoice == 2) {
                    computerAttack = "Tail Swipe";
                    computerDamage = 20;
                } else {
                    computerAttack = "Poison Spit";
                    computerDamage = 30;
                }

            } else if (computerFighter.equals("spock")) {

                if (computerAttackChoice == 1) {
                    computerAttack = "Vulcan Chop";
                    computerDamage = 10;
                } else if (computerAttackChoice == 2) {
                    computerAttack = "Logic Blast";
                    computerDamage = 20;
                } else {
                    computerAttack = "Mind Meld";
                    computerDamage = 30;
                }
            }

            // -------------------------
            // TYPE ADVANTAGES
            // -------------------------

            boolean playerHasAdvantage =
                    (playerFighter.equals("rock") &&
                            (computerFighter.equals("scissors")
                                    || computerFighter.equals("lizard"))) ||

                            (playerFighter.equals("paper") &&
                                    (computerFighter.equals("rock")
                                            || computerFighter.equals("spock"))) ||

                            (playerFighter.equals("scissors") &&
                                    (computerFighter.equals("paper")
                                            || computerFighter.equals("lizard"))) ||

                            (playerFighter.equals("lizard") &&
                                    (computerFighter.equals("paper")
                                            || computerFighter.equals("spock"))) ||

                            (playerFighter.equals("spock") &&
                                    (computerFighter.equals("rock")
                                            || computerFighter.equals("scissors")));

            boolean computerHasAdvantage =
                    (computerFighter.equals("rock") &&
                            (playerFighter.equals("scissors")
                                    || playerFighter.equals("lizard"))) ||

                            (computerFighter.equals("paper") &&
                                    (playerFighter.equals("rock")
                                            || playerFighter.equals("spock"))) ||

                            (computerFighter.equals("scissors") &&
                                    (playerFighter.equals("paper")
                                            || playerFighter.equals("lizard"))) ||

                            (computerFighter.equals("lizard") &&
                                    (playerFighter.equals("paper")
                                            || playerFighter.equals("spock"))) ||

                            (computerFighter.equals("spock") &&
                                    (playerFighter.equals("rock")
                                            || playerFighter.equals("scissors")));

            // -------------------------
            // PLAYER TURN
            // -------------------------

            System.out.println("\nYou used " + playerAttack + "!");

            if (playerHasAdvantage) {
                System.out.println("It's super effective!");
                playerDamage += 10;
            }

            computerHP -= playerDamage;

            if (computerHP < 0) {
                computerHP = 0;
            }

            System.out.println(
                    computerFighter + " took "
                            + playerDamage + " damage!"
            );

            // Stop battle if computer fainted
            if (computerHP <= 0) {
                break;
            }

            // -------------------------
            // COMPUTER TURN
            // -------------------------

            System.out.println(
                    "\nOpponent used " + computerAttack + "!"
            );

            if (computerHasAdvantage) {
                System.out.println("It's super effective!");
                computerDamage += 10;
            }

            playerHP -= computerDamage;

            if (playerHP < 0) {
                playerHP = 0;
            }

            System.out.println(
                    playerFighter + " took "
                            + computerDamage + " damage!"
            );
        }

        // -------------------------
        // RESULTS
        // -------------------------

        System.out.println("\n============================");

        if (computerHP <= 0) {

            System.out.println(
                    computerFighter + " fainted!"
            );

            System.out.println("You won the battle!");

        } else {

            System.out.println(
                    playerFighter + " fainted!"
            );

            System.out.println("You lost the battle!");
        }

        System.out.println("============================");

    }
}