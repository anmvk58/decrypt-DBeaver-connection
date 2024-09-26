package com.anmv.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.anmv.entity.ConnectionDBeaver;

import org.json.JSONObject;

public class ReadConnection {
    
    public List<ConnectionDBeaver> getListConnection(String dataSourcePath){

        List<ConnectionDBeaver> lConnectionDBeavers = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(dataSourcePath)));

            JSONObject jsonObject = new JSONObject(content);
            JSONObject listConnection = jsonObject.getJSONObject("connections");
            Set<String> listIdConnection = listConnection.keySet();

            // convert all json data to ConnectionDBeaver entities
            for (String key : listIdConnection) {
                String host = listConnection.getJSONObject(key).getJSONObject("configuration").getString("host");
                String dbType = listConnection.getJSONObject(key).getString("provider");
                ConnectionDBeaver cnnTemp = new ConnectionDBeaver(key, host, dbType);
                lConnectionDBeavers.add(cnnTemp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lConnectionDBeavers;
    }
}
