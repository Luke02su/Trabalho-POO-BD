package com.mycompany.testecontrole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LojaDAO {
    
// A conexão com o banco de dados
    private Connection connection; // Campo para armazenar a conexão com o banco de dados
    
    public LojaDAO() {
        // Inicializa a conexão com o banco de dados usando a ConnectionFactory
        this.connection = new ConnectionFactory().getConnection();
    }
    
        public List<Loja> getLista() {
        try {
            // Cria uma lista para armazenar os contatos recuperados do banco de dados
            List<Loja> lojas = new ArrayList<Loja>();

            // Comando SQL para selecionar todos os registros da tabela contatos
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM loja");
            
            // Executa a consulta e obtém o resultado
            ResultSet rs = stmt.executeQuery();

            // Itera sobre cada registro retornado pelo ResultSet
            while (rs.next()) {
                // Cria um novo objeto Contato e define seus atributos com base nos dados do banco de dados
                Loja loja = new Loja();
                loja.setPk_loja(rs.getInt("Pk_loja"));
                loja.setCnpj(rs.getString("cnpj"));
                loja.setCidade(rs.getString("cidade"));
                loja.setTelefone(rs.getString("telefone"));

                // Adiciona o contato à lista de contatos
                lojas.add(loja);
            }
            rs.close(); // Fecha o ResultSet
            stmt.close(); // Fecha o PreparedStatement
            return lojas; // Retorna a lista de contatos
        } catch (SQLException e) {
            // Lança uma exceção em caso de erro na execução do SQL
            throw new RuntimeException(e);
        }
    }
    
    public void listar() {
        System.out.println("------------ LISTAS COMPLETAS DE IMPRESSORAS------------");
        // Obtém a lista de contatos do banco de dados
        List<Loja> loja = this.getLista();
        
        // Itera sobre cada contato e imprime seus detalhes
        for (Loja l : loja) {
            System.out.println("ID de Loja " + l.getPk_loja()); // Imprime o email do contato
            System.out.println("CNPJ: " + l.getCnpj());
            System.out.println("Cidade: " + l.getCidade());
            System.out.println("Telefone: " + l.getTelefone());
            System.out.println("----------------------------------");
        }
    }
    
    public void listarID(int id) {
        System.out.println("------------ LISTA COMPLETA DE IMPRESSORA " + id + "------------");
        // Obtém a lista de contatos do banco de dados
        List<Loja> loja = this.getLista();
        
        // Itera sobre cada contato e imprime seus detalhes
        
        boolean inserido = false;
       
        for (Loja l : loja) {
            if(id == l.getPk_loja()) {
                System.out.println("ID de Loja " + l.getPk_loja()); // Imprime o email do contato
                System.out.println("CNPJ: " + l.getCnpj());
                System.out.println("Cidade: " + l.getCidade());
                System.out.println("Telefone: " + l.getTelefone());
                System.out.println("----------------------------------");
                inserido = true;
                break;
            } 
        }
        if (inserido == false) {
            System.out.println("Sinto muito! Este computador não existe.");
        }
    }
}