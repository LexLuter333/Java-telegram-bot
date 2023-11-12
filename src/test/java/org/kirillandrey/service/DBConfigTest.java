package org.kirillandrey.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.kirillandrey.config.DBConfig;

public class DBConfigTest extends DBConfig {
    String dbHost;
    String dbPort;
    String dbUser;
    String dbPass;
    String dbName;
    String dbTableName;

    public DBConfigTest() {
        Dotenv dotenv = Dotenv.load();
        this.dbHost = dotenv.get("dbHostTest");
        this.dbPort = dotenv.get("dbPortTest");
        this.dbUser = dotenv.get("dbUserTest");
        this.dbPass = dotenv.get("dbPassTest");
        this.dbName = dotenv.get("dbNameTest");
        this.dbTableName = dotenv.get("dbTableNameTest");
    }
    public String getdbHost() {
        return this.dbHost;
    }

    public void setdbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getdbPort() {
        return this.dbPort;
    }

    public void setdbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getdbUser() {
        return this.dbUser;
    }

    public void setdbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getdbPass() {
        return this.dbPass;
    }

    public void setdbPass(String dbPass) {
        this.dbPass = dbPass;
    }

    public String getdbName() {
        return this.dbName;
    }

    public void setdbName(String dbName) {
        this.dbName = dbName;
    }

    public String getdbTableName() {
        return this.dbTableName;
    }

    public void setdbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
    }
    public DBConfig clone(DBConfig original) {
        DBConfig cloned = new DBConfig();
        this.setdbHost(original.getdbHost());
        cloned.setdbPort(original.getdbPort());
        cloned.setdbUser(original.getdbUser());
        cloned.setdbPass(original.getdbPass());
        cloned.setdbName(original.getdbName());
        cloned.setdbTableName(original.getdbTableName());
        return cloned;
    }
}
