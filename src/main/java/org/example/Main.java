package org.example;

import org.example.model.Creature;
import org.example.model.Move;
import org.example.model.BattleCreature;
import org.example.model.Player;
import org.example.model.PlayerCreature;

import org.example.repository.CreatureRepository;
import org.example.repository.MoveRepository;
import org.example.repository.PlayerRepository;
import org.example.repository.PlayerCreatureRepository;

import org.example.service.BattleService;
import org.example.service.ComputerAIService;

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

        ComputerAIService computerAIService = new ComputerAIService();

        PlayerRepository playerRepository = new PlayerRepository();

        PlayerCreatureRepository playerCreatureRepository = new PlayerCreatureRepository();

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

        // =====================================================
        // PLAYER MENU
        // =====================================================

        System.out.println("\n1. New Player");
        System.out.println("2. Load Player");
        System.out.println("3. Exit");

        System.out.print("\nChoose an option: ");

        int menuChoice = scanner.nextInt();
        scanner.nextLine();

        while (menuChoice < 1 || menuChoice > 3) {

            System.out.print("Invalid choice. Choose 1, 2, or 3: ");

            menuChoice = scanner.nextInt();
            scanner.nextLine();
        }

        if (menuChoice == 3) {

            System.out.println("Thanks for playing!");

            scanner.close();
            return;
        }

        Player currentPlayer;


        // =====================================================
        // NEW PLAYER
        // =====================================================

        if (menuChoice == 1) {

            System.out.print("\nChoose a username: ");

            String username = scanner.nextLine().trim();

            while (username.isBlank() || playerRepository.existsByUsername(username)) {

                if (username.isBlank()) {

                    System.out.println("Username cannot be blank.");

                } else {

                    System.out.println("That username already exists.");
                }

                System.out.print("Choose another username: ");

                username = scanner.nextLine().trim();
            }

            currentPlayer = playerRepository.create(username);

            if (currentPlayer == null) {

                System.out.println("Could not create player.");

                scanner.close();
                return;
            }

            System.out.println("\nWelcome, " + currentPlayer.getUsername() + "!");


            // =================================================
            // CHOOSE STARTER
            // =================================================

            System.out.println("\nChoose your starter creature!\n");

            for (int i = 0; i < creatures.size(); i++) {

                Creature creature = creatures.get(i);

                System.out.println((i + 1) + ". " + creature.getName() + " [" + creature.getType() + "]");
            }

            System.out.print("\nStarter: ");

            int starterChoice = scanner.nextInt();

            scanner.nextLine();

            while (starterChoice < 1 || starterChoice > creatures.size()) {

                System.out.print("Invalid choice. Choose 1-" + creatures.size() + ": ");

                starterChoice = scanner.nextInt();

                scanner.nextLine();
            }

            Creature starter = creatures.get(starterChoice - 1);

            playerCreatureRepository.addCreatureToPlayer(currentPlayer.getId(), starter.getId());

            System.out.println("\n" + starter.getName() + " is now your first creature!");


        // =====================================================
        // LOAD PLAYER
        // =====================================================

        } else {

            System.out.print("\nEnter your username: ");

            String username = scanner.nextLine().trim();

            currentPlayer = playerRepository.findByUsername(username);

            if (currentPlayer == null) {

                System.out.println("Player not found.");

                scanner.close();
                return;
            }

            System.out.println("\nWelcome back, " + currentPlayer.getUsername() + "!");
        }

        // =====================================================
        // LOAD PLAYER'S CREATURES
        // =====================================================

        List<PlayerCreature> playerCreatures = playerCreatureRepository.findByPlayerId(currentPlayer.getId());

        if (playerCreatures.isEmpty()) {

            System.out.println("You don't own any creatures!");

            scanner.close();
            return;
        }


        // =====================================================
// CHOOSE PLAYER CREATURE
// =====================================================

        PlayerCreature selectedPlayerCreature;


// --------------------------------
// ONLY ONE CREATURE
// --------------------------------

        if (playerCreatures.size() == 1) {

            selectedPlayerCreature =
                    playerCreatures.get(0);

            System.out.println(
                    "\n" +
                            selectedPlayerCreature
                                    .getCreature()
                                    .getName() +
                            " will battle for you!"
            );


// --------------------------------
// MULTIPLE CREATURES
// --------------------------------

        } else {

            System.out.println(
                    "\nYour Creatures:\n"
            );

            for (int i = 0; i < playerCreatures.size(); i++) {

                PlayerCreature owned =
                        playerCreatures.get(i);

                Creature creature =
                        owned.getCreature();

                System.out.println(
                        (i + 1) +
                                ". " +
                                creature.getName() +
                                " [" +
                                creature.getType() +
                                "]" +
                                " | Level: " +
                                owned.getLevel() +
                                " | XP: " +
                                owned.getExperience() +
                                " | W: " +
                                owned.getWins() +
                                " | L: " +
                                owned.getLosses()
                );
            }

            System.out.print(
                    "\nChoose your creature: "
            );

            int creatureChoice =
                    scanner.nextInt();

            scanner.nextLine();

            while (
                    creatureChoice < 1 ||
                            creatureChoice > playerCreatures.size()
            ) {

                System.out.print(
                        "Invalid choice. Choose 1-" +
                                playerCreatures.size() +
                                ": "
                );

                creatureChoice =
                        scanner.nextInt();

                scanner.nextLine();
            }

            selectedPlayerCreature =
                    playerCreatures.get(
                            creatureChoice - 1
                    );
        }


        // =====================================================
        // GET BASE CREATURE
        // =====================================================

        Creature playerCreature =
                selectedPlayerCreature.getCreature();


        // =====================================================
        // SELECT COMPUTER CREATURE
        // =====================================================

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

        BattleCreature player = new BattleCreature(playerCreature, selectedPlayerCreature.getLevel());

        BattleCreature computer = new BattleCreature(computerCreature, 1);


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

            Move computerMove = computerAIService.chooseMove(computer, player, computerMoves);

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


        // =====================================================
// RESULTS / SAVE PROGRESS
// =====================================================

        System.out.println("\n============================");

        if (!player.isFainted()) {

            System.out.println(
                    "Opponent's " +
                            computerCreature.getName() +
                            " fainted!"
            );

            System.out.println(
                    "Your " +
                            playerCreature.getName() +
                            " wins!"
            );

            System.out.println("You won the battle!");

            int xpEarned = 50;

            selectedPlayerCreature.addWin();
            selectedPlayerCreature.addExperience(xpEarned);

            int levelsGained =
                    selectedPlayerCreature.checkForLevelUps();

            if (levelsGained > 0) {

                System.out.println(
                        playerCreature.getName() +
                                " reached Level " +
                                selectedPlayerCreature.getLevel() +
                                "!"
                );
            }

            playerCreatureRepository.updateProgress(
                    selectedPlayerCreature
            );

            System.out.println(
                    "\n" +
                            playerCreature.getName() +
                            " gained " +
                            xpEarned +
                            " XP!"
            );

        } else {

            System.out.println(
                    "Your " +
                            playerCreature.getName() +
                            " fainted!"
            );

            System.out.println(
                    "Opponent's " +
                            computerCreature.getName() +
                            " wins!"
            );

            System.out.println("You lost the battle!");

            int xpEarned = 20;

            selectedPlayerCreature.addLoss();
            selectedPlayerCreature.addExperience(xpEarned);

            playerCreatureRepository.updateProgress(
                    selectedPlayerCreature
            );

            System.out.println(
                    "\n" +
                            playerCreature.getName() +
                            " gained " +
                            xpEarned +
                            " XP!"
            );
        }

        System.out.println("============================");
        scanner.close();

    }
}