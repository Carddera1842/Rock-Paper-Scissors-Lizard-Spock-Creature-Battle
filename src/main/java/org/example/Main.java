package org.example;

import org.example.model.Creature;
import org.example.repository.CreatureRepository;
import org.example.model.Move;
import org.example.repository.MoveRepository;
import org.example.repository.TypeAdvantageRepository;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // --------------------------------
        // REPOSITORIES
        // --------------------------------

        CreatureRepository repository = new CreatureRepository();

        MoveRepository moveRepository = new MoveRepository();

        TypeAdvantageRepository typeAdvantageRepository = new TypeAdvantageRepository();

        // --------------------------------
        // LOAD CREATURES FROM DATABASE
        // --------------------------------

        List<Creature> creatures = repository.findAll();

        if (creatures.isEmpty()) {
            System.out.println("No creatures were found");
            scanner.close();
            return;
        }

        // --------------------------------
        // GAME INTRO
        // --------------------------------

        System.out.println("Welcome to RPSLS Battle!");

        System.out.println("===========================");
        System.out.println("        BATTLE TIME!");
        System.out.println("===========================");

        // -------------------------
        // CHOOSE FIGHTER
        // -------------------------

        System.out.println("\nChoose your creature!");

        for (int i = 0; i < creatures.size(); i++) {

            Creature creature = creatures.get(i);

            System.out.println((i + 1) + ". " + creature.getName() + " [" + creature.getType() + "]" + " HP: " + creature.getBaseHp());
        }

        System.out.print("\nEnter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        while (choice < 1 || choice > creatures.size()) {

            System.out.print("Invalid choice. Choose 1-" + creatures.size() + ": ");

            choice = scanner.nextInt();
            scanner.nextLine();
        }

        // --------------------------------
        // SELECT CREATURES
        // --------------------------------

        Creature playerCreature = creatures.get(choice - 1);

        Creature computerCreature = creatures.get(random.nextInt(creatures.size()));

        // --------------------------------
        // LOAD MOVES FROM DATABASE
        // --------------------------------

        List<Move> playerMoves = moveRepository.findMovesByCreatureId(playerCreature.getId());

        List<Move> computerMoves = moveRepository.findMovesByCreatureId(computerCreature.getId());

        if (playerMoves.isEmpty()) {
            System.out.println("Your " + playerCreature.getName() + " has no moves!");
            scanner.close();
            return;
        }
        if (computerMoves.isEmpty()) {
            System.out.println("Opponent " + computerCreature.getName() + " has no moves!");
            scanner.close();
            return;
        }

        // --------------------------------
        // BATTLE STATS
        // --------------------------------

        int playerHP = playerCreature.getBaseHp();
        int computerHP = computerCreature.getBaseHp();
        int playerAttackStat = playerCreature.getAttack();
        int playerDefenseStat = playerCreature.getDefense();
        int computerAttackStat = computerCreature.getAttack();
        int computerDefenseStat = computerCreature.getDefense();


        // --------------------------------
        // INTRODUCE FIGHTERS
        // --------------------------------

        System.out.println("\nYou chose " + playerCreature.getName() + "!");

        System.out.println("Type: " + playerCreature.getType());

        System.out.println("\nYour opponent chose " + computerCreature.getName() + "!");

        System.out.println("Type: " + computerCreature.getType());

        System.out.println("\nBattle Start!");

        // -------------------------
        // BATTLE LOOP
        // -------------------------

        while (playerHP > 0 && computerHP > 0) {

            System.out.println("\n----------------------------");

            System.out.println(playerCreature.getName() + " HP: " + playerHP + "/" + playerCreature.getBaseHp());
            System.out.println(computerCreature.getName() + " HP: " + computerHP + "/" + computerCreature.getBaseHp());

            System.out.println("----------------------------");

            // -------------------------
            // PLAYER ATTACK MENU
            // -------------------------

            System.out.println("\nChoose your attack:");

            for (int i = 0; i < playerMoves.size(); i++) {

                Move move = playerMoves.get(i);

                System.out.println((i + 1) + ". " + move.getName() + " | Damage: " + move.getDamage() + " | Accuracy: " + move.getAccuracy() + "%");
            }

            System.out.print("Move: ");

            int attackChoice = scanner.nextInt();
            scanner.nextLine();

            while (attackChoice < 1 || attackChoice > playerMoves.size()) {
                System.out.print("Invalid move. Choose 1-" + playerMoves.size() + ": ");

                attackChoice = scanner.nextInt();
                scanner.nextLine();
            }

            // --------------------------------
            // CHOOSE MOVES
            // --------------------------------

            Move playerMove = playerMoves.get(attackChoice - 1);

            Move computerMove = computerMoves.get(random.nextInt(computerMoves.size()));


            // =================================================
            // PLAYER TURN
            // =================================================

            System.out.println("\nYour " + playerCreature.getName() + " used " + playerMove.getName() + "!");

            // --------------------------------
            // PLAYER ATTACK MOVE
            // --------------------------------


            if (playerMove.getMoveCategory().equalsIgnoreCase("ATTACK")) {

                boolean playerHit = random.nextInt(100) < playerMove.getAccuracy();

                if (playerHit) {

                    boolean playerHasAdvantage = typeAdvantageRepository.hasAdvantage(playerMove.getType(), computerCreature.getType());

                    int playerDamage = playerMove.getDamage() + playerAttackStat - (computerDefenseStat / 2);

                    if (playerDamage < 1) {
                        playerDamage = 1;
                    }

                    if (playerHasAdvantage) {

                        playerDamage = (int) (playerDamage * 1.5);

                        System.out.println(
                                "It's super effective!"
                        );
                    }

                    computerHP -= playerDamage;

                    if (computerHP < 0) {
                        computerHP = 0;
                    }

                    System.out.println("Opponent's " + computerCreature.getName() + " took " + playerDamage + " damage!");

                } else {

                    System.out.println("Your " + playerCreature.getName() + "'s attack missed!");
                }


                // --------------------------------
                // PLAYER HEAL MOVE
                // --------------------------------

            } else if (
                    playerMove
                            .getMoveCategory()
                            .equalsIgnoreCase("HEAL")
            ) {

                int healAmount =
                        playerMove.getEffectValue();

                playerHP +=
                        healAmount;

                if (
                        playerHP >
                                playerCreature.getBaseHp()
                ) {

                    playerHP =
                            playerCreature.getBaseHp();
                }

                System.out.println("Your " + playerCreature.getName() + " recovered " + healAmount + " HP!");


                // --------------------------------
                // PLAYER DEFENSE MOVE
                // --------------------------------

            } else if (
                    playerMove
                            .getMoveCategory()
                            .equalsIgnoreCase("DEFENSE")
            ) {

                int defenseBoost =
                        playerMove.getEffectValue();

                playerDefenseStat +=
                        defenseBoost;

                System.out.println("Your " + playerCreature.getName() + "'s defense increased by " + defenseBoost + "!");


                // --------------------------------
                // PLAYER STATUS MOVE
                // --------------------------------

            } else if (
                    playerMove
                            .getMoveCategory()
                            .equalsIgnoreCase("STATUS")
            ) {

                if (playerMove.getDamage() > 0) {

                    boolean playerHit = random.nextInt(100) < playerMove.getAccuracy();

                    if (playerHit) {

                        boolean playerHasAdvantage = typeAdvantageRepository.hasAdvantage(playerMove.getType(), computerCreature.getType());

                        int playerDamage = playerMove.getDamage() + playerAttackStat - (computerDefenseStat / 2);

                        if (playerDamage < 1) {
                            playerDamage = 1;
                        }

                        if (playerHasAdvantage) {

                            playerDamage = (int) (playerDamage * 1.5);

                            System.out.println("It's super effective!");
                        }

                        computerHP -= playerDamage;

                        if (computerHP < 0) {
                            computerHP = 0;
                        }

                        System.out.println("Opponent's " + computerCreature.getName() + " took " + playerDamage + " damage!"
                        );

                    } else {

                        System.out.println("Your " + playerCreature.getName() + "'s move missed!");
                    }

                } else {

                    System.out.println("The status effect takes hold!");
                }
            }


            // --------------------------------
            // CHECK COMPUTER HP
            // --------------------------------

            if (computerHP <= 0) {
                break;
            }

            // =================================================
            // COMPUTER TURN
            // =================================================

            System.out.println("Opponent's " + computerCreature.getName() + " used " + computerMove.getName() + "!");


            // -------------------------------
            // COMPUTER ATTACK MOVE
            // --------------------------------

            if (computerMove.getMoveCategory().equalsIgnoreCase("ATTACK")) {

                boolean computerHit = random.nextInt(100) < computerMove.getAccuracy();

                if (computerHit) {

                    boolean computerHasAdvantage = typeAdvantageRepository.hasAdvantage(computerMove.getType(), playerCreature.getType());

                    int computerDamage = computerMove.getDamage() + computerAttackStat - (playerDefenseStat / 2);

                    if (computerDamage < 1) {
                        computerDamage = 1;
                    }

                    if (computerHasAdvantage) {

                        computerDamage = (int) (computerDamage * 1.5);

                        System.out.println(
                                "It's super effective!"
                        );
                    }

                    playerHP -= computerDamage;

                    if (playerHP < 0) {
                        playerHP = 0;
                    }

                    System.out.println("Your " + playerCreature.getName() + " took " + computerDamage + " damage!"
                    );

                } else {

                    System.out.println("Opponent's " + computerCreature.getName() + "'s attack missed!");
                }


                // --------------------------------
                // COMPUTER HEAL MOVE
                // --------------------------------

            } else if (computerMove.getMoveCategory().equalsIgnoreCase("HEAL")) {

                int healAmount = computerMove.getEffectValue();

                computerHP += healAmount;

                if (computerHP > computerCreature.getBaseHp()) {

                    computerHP = computerCreature.getBaseHp();
                }

                System.out.println("Opponent's " + computerCreature.getName() + " recovered " + healAmount + " HP!");


                // --------------------------------
                // COMPUTER DEFENSE MOVE
                // --------------------------------

            } else if (computerMove.getMoveCategory().equalsIgnoreCase("DEFENSE")) {

                int defenseBoost = computerMove.getEffectValue();

                computerDefenseStat += defenseBoost;

                System.out.println("Opponent's " + computerCreature.getName() + "'s defense increased by " + defenseBoost + "!");


                // --------------------------------
                // COMPUTER STATUS MOVE
                // --------------------------------

            } else if (computerMove.getMoveCategory().equalsIgnoreCase("STATUS")) {

                if (computerMove.getDamage() > 0) {

                    boolean computerHit = random.nextInt(100) < computerMove.getAccuracy();

                    if (computerHit) {

                        boolean computerHasAdvantage = typeAdvantageRepository.hasAdvantage(computerMove.getType(), playerCreature.getType());

                        int computerDamage = computerMove.getDamage() + computerAttackStat - (playerDefenseStat / 2);

                        if (computerDamage < 1) {
                            computerDamage = 1;
                        }

                        if (computerHasAdvantage) {

                            computerDamage = (int) (computerDamage * 1.5);

                            System.out.println("It's super effective!");
                        }

                        playerHP -= computerDamage;

                        if (playerHP < 0) {
                            playerHP = 0;
                        }

                        System.out.println("Your " + playerCreature.getName() + " took " + computerDamage + " damage!");

                    } else {

                        System.out.println("Opponent's " + computerCreature.getName() + "'s move missed!");
                    }

                } else {

                    System.out.println("The status effect takes hold!");
                }
            }
        }


        // -------------------------
        // RESULTS
        // -------------------------

        System.out.println("\n============================");

        if (playerHP > 0) {

            System.out.println("Opponent's " + computerCreature.getName() + " fainted!");

            System.out.println("Your " + playerCreature.getName() + " wins!");

            System.out.println(
                    "You won the battle!"
            );

        } else {

            System.out.println("Your " + playerCreature.getName() + " fainted!");

            System.out.println("Opponent's " + computerCreature.getName() + " wins!");

            System.out.println(
                    "You lost the battle!"
            );
        }

        System.out.println("============================");
        scanner.close();

    }
}