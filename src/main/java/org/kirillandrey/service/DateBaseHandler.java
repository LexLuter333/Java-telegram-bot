package org.kirillandrey.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.kirillandrey.config.DBConfig;

import org.kirillandrey.config.DBConst;
public class DateBaseHandler {
    Connection dbConnection;
    DBConfig dbConfig = new DBConfig();

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

            prSt.setInt(4, 1);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean findUser(Long chatid) {
        String insert = "SELECT " + DBConst.USER_ID + " FROM " + DBConst.USER_TABLE + " WHERE " + DBConst.USER_CHATID + "=?";

        try {
            PreparedStatement prSt = dbGetConnection().prepareStatement(insert);
            prSt.setString(1, chatid.toString());
            ResultSet resultSet = prSt.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
