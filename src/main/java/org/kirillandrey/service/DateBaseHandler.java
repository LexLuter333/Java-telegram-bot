package org.kirillandrey.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.kirillandrey.config.DBConfig;

import org.kirillandrey.config.DBConst;

public class DateBaseHandler {
    private Connection dbConnection;
    private DBConfig dbConfig;
    public DateBaseHandler(DBConfig dbConfig) {
        this.dbConfig = dbConfig;
    }
    public DateBaseHandler() {
        this.dbConfig = new DBConfig();
    }
    public DateBaseHandler(DBConfig dbConfig, Connection dbConnection) {
        this.dbConfig = dbConfig;
        this.dbConnection = dbConnection;
    }

    public Connection dbGetConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbConfig.getdbHost() + ":" + dbConfig.getdbPort() + "/" + dbConfig.getdbName();
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbConfig.getdbUser(), dbConfig.getdbPass());
        return dbConnection;
    }

    public void singUpUser(String firstname, String secondname, Long chatid) throws SQLException {
        String insert = "INSERT INTO " + DBConst.USER_TABLE + "(" + DBConst.USER_CHATID + ","
                + DBConst.USER_FIRSTNAME + "," + DBConst.USER_SECONDNAME +
                "," + DBConst.USER_STATE + ")" + "VALUES(?,?,?,?)";

        try {
            PreparedStatement prSt = dbGetConnection().prepareStatement(insert);

            prSt.setString(1, chatid.toString());

            if (firstname != null){
                prSt.setString(2, firstname);
            } else {
                prSt.setString(2, "?");
            }

            if (secondname != null) {
                prSt.setString(3, secondname);
            } else {
                prSt.setString(3, "?");
            }

            prSt.setString(4, "0");
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean userIsDB(Long chatid) {
        String selectQuery = "SELECT " + DBConst.USER_ID + " FROM " + DBConst.USER_TABLE + " WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, String.valueOf(chatid));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String getState(Long chatid) {
        String selectQuery = "SELECT " + DBConst.USER_STATE + " FROM " + DBConst.USER_TABLE + " WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, chatid.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String state = resultSet.getString(DBConst.USER_STATE);

                    return resultSet.getString(DBConst.USER_STATE);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean setState(Long chatid, String newState) {
        String updateQuery = "UPDATE " + DBConst.USER_TABLE + " SET " + DBConst.USER_STATE + "=? WHERE " + DBConst.USER_CHATID + "=?";

        try (Connection connection = dbGetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newState);
            preparedStatement.setString(2, chatid.toString());

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}
