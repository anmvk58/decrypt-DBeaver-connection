package com.anmv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.NoSuchPaddingException;

import com.anmv.entity.ConnectionDBeaver;
import com.anmv.service.DecryptDBeaver;
import com.anmv.service.ReadConnection;

import org.json.JSONObject;

/**
 * Project to retrival password from DBeaver Community version
 * Author: anmv
 * CreateDate: 2024-09-26
 */
public class App {
    public static void main(String[] args) throws InvalidKeyException, InvalidAlgorithmParameterException,
            NoSuchPaddingException, NoSuchAlgorithmException, IOException {

        final String basePath = "C:\\Users\\anmv2\\AppData\\Roaming\\DBeaverData\\workspace6\\General\\.dbeaver";

        final String credentialsPath = String.format("%s\\credentials-config.json", basePath);
        final String dataSourcePath = String.format("%s\\data-sources.json", basePath);

        ReadConnection readConnection = new ReadConnection();

        // Read all connection from dataSource file:
        List<ConnectionDBeaver> lConnectionDBeavers = readConnection.getListConnection(dataSourcePath);

        // Decrypt credentials to Raw String
        String contentDecrypt = DecryptDBeaver.decrypt(Files.readAllBytes(Paths.get(credentialsPath)));

        for (ConnectionDBeaver cnn : lConnectionDBeavers) {
            contentDecrypt = contentDecrypt.replace(cnn.getName(), cnn.getHost());
        }

        // Write result to Json file
        System.out.println(contentDecrypt);
        JSONObject jsonObject = new JSONObject(contentDecrypt);

        // Ghi JSONObject ra file
        String filePath = "decrypt_result.json";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jsonObject.toString(4));
            System.out.println("Result successfully create: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
