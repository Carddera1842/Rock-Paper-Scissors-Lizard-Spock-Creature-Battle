package org.example.service;

import org.example.model.BattleCreature;
import org.example.model.Move;
import org.example.repository.TypeAdvantageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerAIService {

    private final Random random;
    private final TypeAdvantageRepository typeAdvantageRepository;

    public ComputerAIService() {

        this.random = new Random();

        this.typeAdvantageRepository =
                new TypeAdvantageRepository();
    }


    public Move chooseMove(
            BattleCreature computer,
            BattleCreature player,
            List<Move> computerMoves
    ) {

        List<Move> healMoves =
                new ArrayList<>();

        List<Move> attackMoves =
                new ArrayList<>();

        List<Move> statusMoves =
                new ArrayList<>();

        List<Move> advantageousMoves =
                new ArrayList<>();

        List<Move> knockoutMoves =
                new ArrayList<>();


        // =====================================================
        // ANALYZE AVAILABLE MOVES
        // =====================================================

        for (Move move : computerMoves) {

            String category =
                    move.getMoveCategory()
                            .toUpperCase();


            // --------------------------------
            // HEAL MOVES
            // --------------------------------

            if (category.equals("HEAL")) {

                healMoves.add(move);
            }


            // --------------------------------
            // DAMAGE MOVES
            // --------------------------------

            if (
                    category.equals("ATTACK") ||
                            (
                                    category.equals("STATUS") &&
                                            move.getDamage() > 0
                            )
            ) {

                attackMoves.add(move);

                boolean hasAdvantage =
                        typeAdvantageRepository
                                .hasAdvantage(
                                        move.getType(),
                                        player
                                                .getCreature()
                                                .getType()
                                );

                if (hasAdvantage) {
                    advantageousMoves.add(move);
                }


                // --------------------------------
                // CHECK FOR POSSIBLE KNOCKOUT
                // --------------------------------

                int estimatedDamage =
                        calculateEstimatedDamage(
                                computer,
                                player,
                                move,
                                hasAdvantage
                        );

                if (
                        estimatedDamage >=
                                player.getCurrentHp()
                ) {

                    knockoutMoves.add(move);
                }
            }


            // --------------------------------
            // NON-DAMAGE STATUS / DEFENSE
            // --------------------------------

            if (
                    category.equals("DEFENSE") ||
                            (
                                    category.equals("STATUS") &&
                                            move.getDamage() == 0
                            )
            ) {

                statusMoves.add(move);
            }
        }


        // =====================================================
        // PRIORITY 1:
        // IF WE CAN WIN, ATTACK
        // =====================================================

        if (!knockoutMoves.isEmpty()) {

            return chooseMostAccurateMove(
                    knockoutMoves
            );
        }


        // =====================================================
        // PRIORITY 2:
        // HEAL WHEN IN DANGER
        // =====================================================

        double hpPercent =
                (double) computer.getCurrentHp()
                        / computer
                        .getCreature()
                        .getBaseHp();

        if (
                hpPercent <= 0.35 &&
                        !healMoves.isEmpty()
        ) {

            // Don't waste healing if we're
            // barely missing any health.
            int missingHp =
                    computer
                            .getCreature()
                            .getBaseHp()
                            - computer.getCurrentHp();

            Move healMove =
                    healMoves.get(
                            random.nextInt(
                                    healMoves.size()
                            )
                    );

            if (
                    missingHp >=
                            healMove.getEffectValue() / 2
            ) {

                // 70% chance to heal while low
                if (random.nextInt(100) < 70) {

                    return healMove;
                }
            }
        }


        // =====================================================
        // PRIORITY 3:
        // FAVOR SUPER-EFFECTIVE ATTACKS
        // =====================================================

        if (!advantageousMoves.isEmpty()) {

            if (random.nextInt(100) < 70) {

                return chooseBestDamageMove(
                        advantageousMoves,
                        computer,
                        player
                );
            }
        }


        // =====================================================
        // PRIORITY 4:
        // SOMETIMES USE BUFFS / DEBUFFS
        // =====================================================

        if (!statusMoves.isEmpty()) {

            if (random.nextInt(100) < 20) {

                return statusMoves.get(
                        random.nextInt(
                                statusMoves.size()
                        )
                );
            }
        }


        // =====================================================
        // PRIORITY 5:
        // FAVOR A STRONG ATTACK
        // =====================================================

        if (!attackMoves.isEmpty()) {

            // 70% of the time choose the best
            // estimated damage move.
            if (random.nextInt(100) < 70) {

                return chooseBestDamageMove(
                        attackMoves,
                        computer,
                        player
                );
            }

            // Keep some randomness.
            return attackMoves.get(
                    random.nextInt(
                            attackMoves.size()
                    )
            );
        }


        // =====================================================
        // FALLBACK
        // =====================================================

        return computerMoves.get(
                random.nextInt(
                        computerMoves.size()
                )
        );
    }


    // =====================================================
    // ESTIMATE DAMAGE
    // =====================================================

    private int calculateEstimatedDamage(
            BattleCreature attacker,
            BattleCreature defender,
            Move move,
            boolean hasAdvantage
    ) {

        int damage =
                move.getDamage()
                        + attacker.getCurrentAttack()
                        - (
                        defender.getCurrentDefense()
                                / 2
                );

        if (damage < 1) {
            damage = 1;
        }

        if (hasAdvantage) {

            damage =
                    (int) (
                            damage * 1.5
                    );
        }

        return damage;
    }


    // =====================================================
    // CHOOSE MOST ACCURATE MOVE
    // =====================================================

    private Move chooseMostAccurateMove(
            List<Move> moves
    ) {

        Move bestMove =
                moves.get(0);

        for (Move move : moves) {

            if (
                    move.getAccuracy() >
                            bestMove.getAccuracy()
            ) {

                bestMove = move;
            }

            // Same accuracy?
            // Prefer more damage.
            else if (
                    move.getAccuracy() ==
                            bestMove.getAccuracy()
                            &&
                            move.getDamage() >
                                    bestMove.getDamage()
            ) {

                bestMove = move;
            }
        }

        return bestMove;
    }


    // =====================================================
    // CHOOSE BEST DAMAGE MOVE
    // =====================================================

    private Move chooseBestDamageMove(
            List<Move> moves,
            BattleCreature attacker,
            BattleCreature defender
    ) {

        Move bestMove =
                moves.get(0);

        int bestDamage = -1;

        for (Move move : moves) {

            boolean hasAdvantage =
                    typeAdvantageRepository
                            .hasAdvantage(
                                    move.getType(),
                                    defender
                                            .getCreature()
                                            .getType()
                            );

            int damage =
                    calculateEstimatedDamage(
                            attacker,
                            defender,
                            move,
                            hasAdvantage
                    );

            if (damage > bestDamage) {

                bestDamage = damage;
                bestMove = move;
            }

            // If damage is identical,
            // favor better accuracy.
            else if (
                    damage == bestDamage &&
                            move.getAccuracy() >
                                    bestMove.getAccuracy()
            ) {

                bestMove = move;
            }
        }

        return bestMove;
    }
}