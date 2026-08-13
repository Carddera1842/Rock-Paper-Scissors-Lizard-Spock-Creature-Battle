package org.example.service;

import org.example.model.BattleCreature;
import org.example.model.Move;
import org.example.repository.TypeAdvantageRepository;

import java.util.Random;

public class BattleService {

    private final Random random;
    private final TypeAdvantageRepository typeAdvantageRepository;

    public BattleService() {

        this.random =
                new Random();

        this.typeAdvantageRepository =
                new TypeAdvantageRepository();
    }


    // =====================================================
    // EXECUTE MOVE
    // =====================================================

    public void executeMove(
            BattleCreature attacker,
            BattleCreature defender,
            Move move,
            boolean playerTurn
    ) {

        String attackerPrefix = playerTurn ? "Your " : "Opponent's ";

        String defenderPrefix = playerTurn ? "Opponent's " : "Your ";

        System.out.println("\n" + attackerPrefix + attacker.getCreature().getName() + " used " + move.getName() + "!");

        String category = move.getMoveCategory().toUpperCase();

        switch (category) {

            case "ATTACK":

                executeAttack(
                        attacker,
                        defender,
                        move,
                        attackerPrefix,
                        defenderPrefix
                );

                break;


            case "HEAL":

                executeHeal(
                        attacker,
                        move,
                        attackerPrefix
                );

                break;


            case "DEFENSE":

                executeDefense(
                        attacker,
                        move,
                        attackerPrefix
                );

                break;


            case "STATUS":

                executeStatus(
                        attacker,
                        defender,
                        move,
                        attackerPrefix,
                        defenderPrefix
                );

                break;


            default:

                System.out.println("Unknown move category: " + category);
        }
    }


    // =====================================================
    // ATTACK
    // =====================================================

    private void executeAttack(
            BattleCreature attacker,
            BattleCreature defender,
            Move move,
            String attackerPrefix,
            String defenderPrefix
    ) {

        boolean hit = checkAccuracy(move);

        if (!hit) {

            System.out.println(attackerPrefix + attacker.getCreature().getName() + "'s attack missed!");

            return;
        }

        boolean hasAdvantage = typeAdvantageRepository.hasAdvantage(move.getType(), defender.getCreature().getType());

        int damage = calculateDamage(attacker, defender, move, hasAdvantage);

        if (hasAdvantage) {

            System.out.println("It's super effective!");
        }

        defender.takeDamage(damage);

        System.out.println(defenderPrefix + defender.getCreature().getName() + " took " + damage + " damage!");
    }


    // =====================================================
    // HEAL
    // =====================================================

    private void executeHeal(
            BattleCreature attacker,
            Move move,
            String attackerPrefix
    ) {

        int beforeHp = attacker.getCurrentHp();

        attacker.heal(move.getEffectValue());

        int actualHeal = attacker.getCurrentHp() - beforeHp;

        System.out.println(attackerPrefix + attacker.getCreature().getName() + " recovered " + actualHeal + " HP!");
    }


    // =====================================================
    // DEFENSE
    // =====================================================

    private void executeDefense(
            BattleCreature attacker,
            Move move,
            String attackerPrefix
    ) {

        int defenseBoost = move.getEffectValue();

        attacker.increaseDefense(defenseBoost);

        System.out.println(attackerPrefix + attacker.getCreature().getName() + "'s defense increased by " + defenseBoost + "!");
    }


    // =====================================================
// STATUS
// =====================================================

    private void executeStatus(
            BattleCreature attacker,
            BattleCreature defender,
            Move move,
            String attackerPrefix,
            String defenderPrefix
    ) {

        boolean hit = checkAccuracy(move);

        if (!hit) {

            System.out.println(attackerPrefix + attacker.getCreature().getName() + "'s move missed!");

            return;
        }


        // --------------------------------
        // STATUS MOVE WITH DAMAGE
        // --------------------------------

        if (move.getDamage() > 0) {

            boolean hasAdvantage = typeAdvantageRepository.hasAdvantage(move.getType(), defender.getCreature().getType());

            int damage = calculateDamage(attacker, defender, move, hasAdvantage);

            if (hasAdvantage) {
                System.out.println("It's super effective!");
            }

            defender.takeDamage(damage);

            System.out.println(defenderPrefix + defender.getCreature().getName() + " took " + damage + " damage!");

            // Don't apply a status effect
            // if the attack already caused a faint.
            if (defender.isFainted()) {
                return;
            }
        }


        // --------------------------------
        // APPLY STATUS EFFECT
        // --------------------------------

        String effectType = move.getEffectType();

        if (effectType == null || effectType.isBlank()) {

            System.out.println("No status effect is configured for " + move.getName() + ".");

            return;
        }

        switch (effectType.toUpperCase()) {

            // --------------------------------
            // POISON
            // --------------------------------

            case "POISON":

                defender.applyPoison(move.getEffectValue());

                System.out.println(defenderPrefix + defender.getCreature().getName() + " was poisoned!");

                break;


            // --------------------------------
            // ATTACK UP
            // --------------------------------

            case "ATTACK_UP":

                attacker.increaseAttack(move.getEffectValue());

                System.out.println(attackerPrefix + attacker.getCreature().getName() + "'s attack increased by " + move.getEffectValue() + "!");

                break;


            // --------------------------------
            // ATTACK DOWN
            // --------------------------------

            case "ATTACK_DOWN":

                defender.decreaseAttack(move.getEffectValue());

                System.out.println(defenderPrefix + defender.getCreature().getName() + "'s attack decreased by " + move.getEffectValue() + "!");

                break;


            // --------------------------------
            // DEFENSE UP
            // --------------------------------

            case "DEFENSE_UP":

                attacker.increaseDefense(move.getEffectValue());

                System.out.println(attackerPrefix + attacker.getCreature().getName() + "'s defense increased by " + move.getEffectValue() + "!");

                break;


            // --------------------------------
            // DEFENSE DOWN
            // --------------------------------

            case "DEFENSE_DOWN":

                defender.decreaseDefense(move.getEffectValue());

                System.out.println(defenderPrefix + defender.getCreature().getName() + "'s defense decreased by " + move.getEffectValue() + "!");

                break;


            // --------------------------------
            // UNKNOWN EFFECT
            // --------------------------------

            default:

                System.out.println("Unknown status effect: " + effectType);
        }
    }


    // =====================================================
    // ACCURACY
    // =====================================================

    private boolean checkAccuracy(Move move) {

        return random.nextInt(100) < move.getAccuracy();
    }


    // =====================================================
    // DAMAGE CALCULATION
    // =====================================================

    private int calculateDamage(
            BattleCreature attacker,
            BattleCreature defender,
            Move move,
            boolean hasAdvantage
    ) {

        int damage = move.getDamage() + (attacker.getCurrentAttack() / 2) - (defender.getCurrentDefense() / 3);

        if (damage < 1) {
            damage = 1;
        }

        if (hasAdvantage) {

            damage = (int) (damage * 1.25);
        }

        return damage;
    }
}