package org.example.repository;

import org.example.database.DatabaseConnection;
import org.example.model.Move;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class MoveRepository {

    public List<Move> findMovesByCreatureId(int creatureId) {

        List<Move> moves = new ArrayList<>();

        String sql = """
                SELECT
                    m.id,
                    m.name,
                    ct.name AS type,
                    m.damage,
                    m.accuracy,
                    m.description,
                    m.move_category,
                    m.effect_value,
                    m.effect_type
                FROM moves m
                JOIN creature_types ct
                    ON m.type_id = ct.id
                JOIN creature_moves cm
                    ON m.id = cm.move_id
                WHERE cm.creature_id = ?
                ORDER BY m.id
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, creatureId);

            try (ResultSet results = statement.executeQuery()) {

                while (results.next()) {

                    Move move = new Move(
                            results.getInt("id"),
                            results.getString("name"),
                            results.getString("type"),
                            results.getInt("damage"),
                            results.getInt("accuracy"),
                            results.getString("description"),
                            results.getString("move_category"),
                            results.getInt("effect_value"),
                            results.getString("effect_type")
                    );

                    moves.add(move);
                }
            }

        } catch (SQLException e) {
            System.out.println("Could not load moves.");
            System.out.println(e.getMessage());
        }

        return moves;
    }
}