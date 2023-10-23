package org.kirillandrey.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.kirillandrey.config.DBConfig;

import org.kirillandrey.config.DBConst;
public class DateBaseHandler {
    Connection dbConnection;
    DBConfig dbConfig = new DBConfig();

    public Connection dbGetConnection() throws ClassNotFoundException, SQLException {
        String ConnectionString = "jdbc:mysql//:" + dbConfig.getdbHost() + ":" + dbConfig.getdbPort() + "/" + dbConfig.getdbName();
        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(ConnectionString, dbConfig.getdbUser(), dbConfig.getdbPass());
        return dbConnection;
    }
    public void singUpUser(String firstname, String secondname, String username) throws SQLException {
        String insert = "INSERT INTO" + DBConst.USER_TABLE + "(" + DBConst.USER_NAME + ","
                + DBConst.USER_FIRSTNAME + "," + DBConst.USER_SECONDNAME +
                "," + DBConst.USER_STATE + ")" + "VALUES(?,?,?,?)";

        try {
            PreparedStatement prSt = dbGetConnection().prepareStatement(insert);

            prSt.setString(1, username);
            prSt.setString(2, firstname);
            prSt.setString(3, secondname);
            prSt.setInt(4, 1);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
