package org.example.repository;

import org.example.database.DatabaseConnection;
import org.example.model.Creature;
import org.example.model.PlayerCreature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class PlayerCreatureRepository {

    // =====================================================
    // ADD CREATURE TO PLAYER
    // =====================================================

    public void addCreatureToPlayer(
            int playerId,
            int creatureId
    ) {

        String sql = """
                INSERT INTO player_creatures
                    (player_id, creature_id)
                VALUES (?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, playerId);
            statement.setInt(2, creatureId);

            statement.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Error adding creature to player: " +
                            e.getMessage()
            );
        }
    }


    // =====================================================
    // FIND ALL CREATURES OWNED BY PLAYER
    // =====================================================

    public List<PlayerCreature> findByPlayerId(
            int playerId
    ) {

        List<PlayerCreature> playerCreatures =
                new ArrayList<>();

        String sql = """
                SELECT
                    pc.id AS player_creature_id,
                    pc.player_id,
                    pc.level,
                    pc.experience,
                    pc.wins,
                    pc.losses,

                    c.id AS creature_id,
                    c.name,
                    c.base_hp,
                    c.attack,
                    c.defense,
                    c.speed,
                    c.description,

                    ct.name AS type

                FROM player_creatures pc

                JOIN creatures c
                    ON pc.creature_id = c.id

                JOIN creature_types ct
                    ON c.type_id = ct.id

                WHERE pc.player_id = ?

                ORDER BY pc.id
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    playerId
            );

            try (
                    ResultSet results =
                            statement.executeQuery()
            ) {

                while (results.next()) {

                    Creature creature =
                            new Creature(
                                    results.getInt(
                                            "creature_id"
                                    ),
                                    results.getString(
                                            "name"
                                    ),
                                    results.getString(
                                            "type"
                                    ),
                                    results.getInt(
                                            "base_hp"
                                    ),
                                    results.getInt(
                                            "attack"
                                    ),
                                    results.getInt(
                                            "defense"
                                    ),
                                    results.getInt(
                                            "speed"
                                    ),
                                    results.getString(
                                            "description"
                                    )
                            );

                    PlayerCreature playerCreature =
                            new PlayerCreature(
                                    results.getInt(
                                            "player_creature_id"
                                    ),
                                    results.getInt(
                                            "player_id"
                                    ),
                                    creature,
                                    results.getInt(
                                            "level"
                                    ),
                                    results.getInt(
                                            "experience"
                                    ),
                                    results.getInt(
                                            "wins"
                                    ),
                                    results.getInt(
                                            "losses"
                                    )
                            );

                    playerCreatures.add(
                            playerCreature
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading player creatures: " +
                            e.getMessage()
            );
        }

        return playerCreatures;
    }


    // =====================================================
    // UPDATE PLAYER CREATURE PROGRESS
    // =====================================================

    public void updateProgress(
            PlayerCreature playerCreature
    ) {

        String sql = """
                UPDATE player_creatures
                SET
                    level = ?,
                    experience = ?,
                    wins = ?,
                    losses = ?
                WHERE id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    playerCreature.getLevel()
            );

            statement.setInt(
                    2,
                    playerCreature.getExperience()
            );

            statement.setInt(
                    3,
                    playerCreature.getWins()
            );

            statement.setInt(
                    4,
                    playerCreature.getLosses()
            );

            statement.setInt(
                    5,
                    playerCreature.getId()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Error updating creature progress: " +
                            e.getMessage()
            );
        }
    }
}