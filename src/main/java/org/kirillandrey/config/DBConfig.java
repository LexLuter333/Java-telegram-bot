package org.kirillandrey.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DBConfig {
    String dbHost;
    String dbPort;
    String dbUser;
    String dbPass;
    String dbName;

    public DBConfig() {
        Dotenv dotenv = Dotenv.load();
        this.dbHost = dotenv.get("dbHost");
        this.dbPort = dotenv.get("dbPort");
        this.dbUser = dotenv.get("dbUser");
        this.dbPass = dotenv.get("dbPass");
        this.dbName = dotenv.get("dbName");

    }
    public String getdbHost(){
        return this.dbHost;
    }
    public String getdbPort(){
        return this.dbPort;
    }
    public String getdbUser(){
        return this.dbUser;
    }
    public String getdbPass(){
        return this.dbPass;
    }
    public String getdbName(){
        return this.dbName;
    }
}
