package org.example.repository;

import org.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeAdvantageRepository {

    public boolean hasAdvantage(
            String attackerType,
            String defenderType
    ) {

        String sql = """
                SELECT COUNT(*) AS match_count
                FROM type_advantages ta
                JOIN creature_types attacker
                    ON ta.attacker_type_id = attacker.id
                JOIN creature_types defender
                    ON ta.defender_type_id = defender.id
                WHERE attacker.name = ?
                  AND defender.name = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, attackerType);
            statement.setString(2, defenderType);

            try (ResultSet results = statement.executeQuery()) {

                if (results.next()) {
                    return results.getInt("match_count") > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Could not check type advantage."
            );
            System.out.println(e.getMessage());
        }

        return false;
    }
}