package com.techchallenge;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tech_challenge";
        String user = "tech_user";
        String password = "passwd";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Conexão bem-sucedida!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
