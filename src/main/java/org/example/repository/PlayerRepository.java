package org.example.repository;

import org.example.database.DatabaseConnection;
import org.example.model.Player;

import java.sql.*;

public class PlayerRepository {

    // =====================================================
    // CREATE PLAYER
    // =====================================================

    public Player create(String username) {

        String sql =
                "INSERT INTO players (username) VALUES (?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setString(1, username);

            statement.executeUpdate();

            ResultSet generatedKeys =
                    statement.getGeneratedKeys();

            if (generatedKeys.next()) {

                int id =
                        generatedKeys.getInt(1);

                return new Player(
                        id,
                        username
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error creating player: " +
                            e.getMessage()
            );
        }

        return null;
    }


    // =====================================================
    // FIND PLAYER BY USERNAME
    // =====================================================

    public Player findByUsername(String username) {

        String sql =
                "SELECT id, username " +
                        "FROM players " +
                        "WHERE username = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    username
            );

            ResultSet results =
                    statement.executeQuery();

            if (results.next()) {

                return new Player(
                        results.getInt("id"),
                        results.getString("username")
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding player: " +
                            e.getMessage()
            );
        }

        return null;
    }


    // =====================================================
    // CHECK IF USERNAME EXISTS
    // =====================================================

    public boolean existsByUsername(String username) {

        return findByUsername(username) != null;
    }
}