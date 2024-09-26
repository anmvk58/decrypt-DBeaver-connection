package com.anmv.entity;

public class ConnectionDBeaver {

    private String name;
    private String host;
    private String dbType;


    public ConnectionDBeaver(String name, String host, String dbType) {
        this.name = name;
        this.host = host;
        this.dbType = dbType;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getDbType() {
        return dbType;
    }
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Override
    public String toString() {
        return "ConnectionDBeaver [dbType=" + dbType + ", host=" + host + ", name=" + name + "]";
    }
    
}
