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

    public String signUpUser(Long chatId) {
        if (!userInDB(chatId)) {
            String insert = "INSERT INTO " + dbConfig.getdbTableName() + "(" + DBConst.USER_CHATID +
                    "," + DBConst.USER_STATE + "," + DBConst.USER_SETTINGS + ")" + "VALUES(?,?,?)";

            try (Connection connection = dbGetConnection();
                 PreparedStatement prSt = connection.prepareStatement(insert)) {
                prSt.setString(1, chatId.toString());
                prSt.setString(2, "0");
                prSt.setString(3, new SettingJson().toJson());

                prSt.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                return "0";
            } catch (Exception e) {
                throw new RuntimeException(e);
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
    public boolean setSettings(Long chatId, SettingJson settings) {
        String updateQuery = "UPDATE " + dbConfig.getdbTableName() + " SET " + DBConst.USER_SETTINGS + "=? WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, settings.toJson());
            preparedStatement.setString(2, chatId.toString());

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SettingJson getSettings(Long chatId) {
        String selectQuery = "SELECT " + DBConst.USER_SETTINGS + " FROM " + dbConfig.getdbTableName() + " WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, chatId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String settingsJson = resultSet.getString(DBConst.USER_SETTINGS);
                    return SettingJson.fromJson(settingsJson);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
