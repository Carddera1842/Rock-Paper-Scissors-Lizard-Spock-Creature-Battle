package org.example;

import org.example.model.Creature;
import org.example.repository.CreatureRepository;
import org.example.model.Move;
import org.example.repository.MoveRepository;
import org.example.model.BattleCreature;
import org.example.service.BattleService;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // --------------------------------
        // REPOSITORIES/SERVICES
        // --------------------------------

        CreatureRepository repository = new CreatureRepository();

        MoveRepository moveRepository = new MoveRepository();

        BattleService battleService = new BattleService();

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

            System.out.println((i + 1) + ". " + creature.getName() + " [" + creature.getType() + "]" + " | HP: " + creature.getBaseHp() + " | ATK: " + creature.getAttack() + " | DEF: " + creature.getDefense() + " | SPD: " + creature.getSpeed());
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

        BattleCreature player = new BattleCreature(playerCreature);

        BattleCreature computer = new BattleCreature(computerCreature);


        // --------------------------------
        // INTRODUCE FIGHTERS
        // --------------------------------

        System.out.println("\nYou chose " + playerCreature.getName() + "!");

        System.out.println("Type: " + playerCreature.getType());

        System.out.println("\nYour opponent chose " + computerCreature.getName() + "!");

        System.out.println("Type: " + computerCreature.getType());

        System.out.println("\nBattle Start!");

        // --------------------------------
        // DISPLAY SPEED ADVANTAGE
        // --------------------------------

        if (
                playerCreature.getSpeed() >
                        computerCreature.getSpeed()
        ) {

            System.out.println(
                    "\nYour " +
                            playerCreature.getName() +
                            " is faster!"
            );

        } else if (
                computerCreature.getSpeed() >
                        playerCreature.getSpeed()
        ) {

            System.out.println(
                    "\nOpponent's " +
                            computerCreature.getName() +
                            " is faster!"
            );

        } else {

            System.out.println(
                    "\nBoth creatures have the same speed!"
            );
        }

        // -------------------------
        // BATTLE LOOP
        // -------------------------

        while (!player.isFainted() && !computer.isFainted()) {

            System.out.println("\n----------------------------");

            System.out.println(playerCreature.getName() + " HP: " + player.getCurrentHp()+ "/" + playerCreature.getBaseHp());
            System.out.println(computerCreature.getName() + " HP: " + computer.getCurrentHp() + "/" + computerCreature.getBaseHp());

            System.out.println("----------------------------");

            // -------------------------
            // PLAYER ATTACK MENU
            // -------------------------

            System.out.println("\nChoose your move:");

            for (int i = 0; i < playerMoves.size(); i++) {

                Move move = playerMoves.get(i);

                System.out.println((i + 1) + ". " + move.getName() + " | Damage: " + move.getDamage() + " | Accuracy: " + move.getAccuracy() + "%");
            }

            System.out.print("Move: ");

            int moveChoice =
                    scanner.nextInt();

            scanner.nextLine();

            while (
                    moveChoice < 1 ||
                            moveChoice >
                                    playerMoves.size()
            ) {

                System.out.print(
                        "Invalid move. Choose 1-" +
                                playerMoves.size() +
                                ": "
                );

                moveChoice =
                        scanner.nextInt();

                scanner.nextLine();
            }


            // --------------------------------
            // CHOOSE MOVES
            // --------------------------------

            Move playerMove = playerMoves.get(moveChoice - 1);

            Move computerMove = computerMoves.get(random.nextInt(computerMoves.size()));

            // --------------------------------
            // DETERMINE TURN ORDER
            // --------------------------------

            boolean playerGoesFirst;

            if (
                    playerCreature.getSpeed() >
                            computerCreature.getSpeed()
            ) {

                playerGoesFirst = true;

            } else if (
                    computerCreature.getSpeed() >
                            playerCreature.getSpeed()
            ) {

                playerGoesFirst = false;

            } else {

                // Same speed
                playerGoesFirst =
                        random.nextBoolean();
            }



            // =================================================
            // EXECUTE TURNS
            // =================================================

            if (playerGoesFirst) {

                // -------------------------
                // PLAYER FIRST
                // -------------------------

                battleService.executeMove(
                        player,
                        computer,
                        playerMove,
                        true
                );

                if (!computer.isFainted()) {

                    battleService.executeMove(
                            computer,
                            player,
                            computerMove,
                            false
                    );
                }

            } else {

                // -------------------------
                // COMPUTER FIRST
                // -------------------------

                battleService.executeMove(
                        computer,
                        player,
                        computerMove,
                        false
                );

                if (!player.isFainted()) {

                    battleService.executeMove(
                            player,
                            computer,
                            playerMove,
                            true
                    );
                }
            }


            // --------------------------------
            // END-OF-ROUND POISON
            // --------------------------------

            if (!player.isFainted()) {

                if (player.isPoisoned()) {

                    player.applyPoisonDamage();

                    System.out.println(
                            "\nYour " +
                                    playerCreature.getName() +
                                    " took " +
                                    player.getPoisonDamage() +
                                    " poison damage!"
                    );
                }
            }

            if (!computer.isFainted()) {

                if (computer.isPoisoned()) {

                    computer.applyPoisonDamage();

                    System.out.println(
                            "Opponent's " +
                                    computerCreature.getName() +
                                    " took " +
                                    computer.getPoisonDamage() +
                                    " poison damage!"
                    );
                }
            }
        }


        // -------------------------
        // RESULTS
        // -------------------------

        System.out.println("\n============================");

        if (!player.isFainted()) {

            System.out.println("Opponent's " + computerCreature.getName() + " fainted!");

            System.out.println("Your " + playerCreature.getName() + " wins!");

            System.out.println("You won the battle!");

        } else {

            System.out.println("Your " + playerCreature.getName() + " fainted!");

            System.out.println("Opponent's " + computerCreature.getName() + " wins!");

            System.out.println("You lost the battle!");
        }

        System.out.println("============================");
        scanner.close();

    }
}