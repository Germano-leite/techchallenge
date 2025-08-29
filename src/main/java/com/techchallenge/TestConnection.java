package com.techchallenge;

import java.sql.Connection;
import java.sql.DriverManager;

//Classe teste. Não faz parte do projeto.
//Testa a conexão com o banco de dados PostgreSQL
public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tech_challenge";
        String user = "postgres";
        String password = "passwd";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("##  Conexao bem-sucedida!  ##");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
