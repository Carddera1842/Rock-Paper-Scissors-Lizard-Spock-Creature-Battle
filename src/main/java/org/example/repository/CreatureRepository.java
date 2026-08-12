package org.example.repository;

import org.example.database.DatabaseConnection;
import org.example.model.Creature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class CreatureRepository {

    public List<Creature> findAll() {

        List<Creature> creatures = new ArrayList<>();

        String sql = """
                SELECT
                    c.id,
                    c.name,
                    ct.name AS type,
                    c.base_hp,
                    c.attack,
                    c.defense,
                    c.speed,
                    c.description
                FROM creatures c
                JOIN creature_types ct
                    ON c.type_id = ct.id
                ORDER BY c.id
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet results =
                        statement.executeQuery()
        ) {

            while (results.next()) {

                Creature creature = new Creature(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getString("type"),
                        results.getInt("base_hp"),
                        results.getInt("attack"),
                        results.getInt("defense"),
                        results.getInt("speed"),
                        results.getString("description")
                );

                creatures.add(creature);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Could not load creatures."
            );
            System.out.println(e.getMessage());
        }

        return creatures;
    }
}