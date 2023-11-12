package org.kirillandrey.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.kirillandrey.config.DBConfig;
import org.kirillandrey.config.DBConst;

public class DateBaseHandler {
    private DBConfig dbConfig;
    private Connection dbConnection;

    public DateBaseHandler(DBConfig _dbConfig_) {
        this.dbConfig = new DBConfig().clone(_dbConfig_);
    }

    public DateBaseHandler() {
        this(new DBConfig());
    }

    public DateBaseHandler(DBConfig dbConfig, Connection dbConnection) {
        this.dbConfig = dbConfig;
        this.dbConnection = dbConnection;
    }

    public Connection dbGetConnection() throws ClassNotFoundException, SQLException {
        if (dbConnection != null && !dbConnection.isClosed()) {
            return dbConnection;
        }

        String connectionString = "jdbc:mysql://" + dbConfig.getdbHost() + ":" + dbConfig.getdbPort() + "/" + dbConfig.getdbName() + "?useSSL=false";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbConfig.getdbUser(), dbConfig.getdbPass());
        return dbConnection;
    }

    public String signUpUser(String firstName, String secondName, Long chatId) {
        if (!userInDB(chatId)) {
            String insert = "INSERT INTO " + dbConfig.getdbTableName() + "(" + DBConst.USER_CHATID + ","
                    + DBConst.USER_FIRSTNAME + "," + DBConst.USER_SECONDNAME +
                    "," + DBConst.USER_STATE + "," + DBConst.USER_SETTINGS + ")" + "VALUES(?,?,?,?,?)";

            try (Connection connection = dbGetConnection();
                 PreparedStatement prSt = connection.prepareStatement(insert)) {
                prSt.setString(1, chatId.toString());
                prSt.setString(2, firstName != null ? firstName : "?");
                prSt.setString(3, secondName != null ? secondName : "?");
                prSt.setString(4, "0");
                prSt.setString(5, "00000000000");

                prSt.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                return "0";
            }
            return "1";
        }
        return "2";
    }

    public boolean userInDB(Long chatId) {
        String selectQuery = "SELECT " + DBConst.USER_ID + " FROM " + dbConfig.getdbTableName() + " WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, String.valueOf(chatId));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getState(Long chatId) {
        String selectQuery = "SELECT " + DBConst.USER_STATE + " FROM " + dbConfig.getdbTableName() + " WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, chatId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(DBConst.USER_STATE);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setState(Long chatId, String newState) {
        String updateQuery = "UPDATE " + dbConfig.getdbTableName() + " SET " + DBConst.USER_STATE + "=? WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newState);
            preparedStatement.setString(2, chatId.toString());

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
