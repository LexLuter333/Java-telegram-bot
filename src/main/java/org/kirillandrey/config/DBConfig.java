package org.kirillandrey.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DBConfig {
    private String dbHost;
    private String dbPort;
    private String dbUser;
    private String dbPass;
    private String dbName;
    private String dbTableName;
    private String dbNotificTableName;

    public DBConfig() {
        Dotenv dotenv = Dotenv.load();
        this.dbHost = dotenv.get("dbHost");
        this.dbPort = dotenv.get("dbPort");
        this.dbUser = dotenv.get("dbUser");
        this.dbPass = dotenv.get("dbPass");
        this.dbName = dotenv.get("dbName");
        this.dbTableName = dotenv.get("dbTableName");
        this.dbNotificTableName = dotenv.get("dbNotificTableName");
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
    public String getdbNotificTableName() {
        return this.dbNotificTableName;
    }

    public void setdbNotificTable(String dbNotificTable) {
        this.dbNotificTableName = dbNotificTable;
    }
    public DBConfig clone(DBConfig original) {
        DBConfig cloned = new DBConfig();
        cloned.setdbHost(original.getdbHost());
        cloned.setdbPort(original.getdbPort());
        cloned.setdbUser(original.getdbUser());
        cloned.setdbPass(original.getdbPass());
        cloned.setdbName(original.getdbName());
        cloned.setdbTableName(original.getdbTableName());
        cloned.setdbNotificTable(original.getdbNotificTableName());
        return cloned;
    }
}
