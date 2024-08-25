package com.mycompany.controleequipamentosti;

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

    public void adicionar(Loja loja) {
        String sql = "INSERT INTO loja (cnpj, cidade, telefone) VALUES (?, ?, ?)";
        
        try {
            // Start the transaction
            connection.setAutoCommit(false);

            // Insert into equipamento
            PreparedStatement stmt1 = connection.prepareStatement(sql);
            stmt1.setString(1, loja.getCnpj());
            stmt1.setString(2, loja.getGerente());
            stmt1.setString(3, loja.getCidade());
            stmt1.setString(4, loja.getTelefone());
            stmt1.execute();

            // Commit the transaction
            connection.commit();
            System.out.println("Loja adicionada com sucesso!"); // Success message
            stmt1.close();
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback in case of error
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Erro ao fazer rollback!", rollbackEx);
            }
            throw new RuntimeException("Erro ao adicionar loja!", e);
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao redefinir auto-commit!", ex);
            }
        }
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
                loja.setGerente(rs.getString("gerente"));
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
            System.out.println("Gerente: " + l.getGerente());
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
                System.out.println("Gerente: " + l.getGerente());
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
        
    public void atualizar(Loja loja, int id) {
        String sql = "UPDATE loja SET cnpj = ?, gerente = ?, cidade = ?, telefone = ? WHERE pk_loja = ?";

        try {
            // Primeiro, selecione o pk_equipamento associado ao id do computador
            PreparedStatement stmt1 = this.connection.prepareStatement("SELECT pk_loja FROM loja WHERE pk_loja = ?");
            stmt1.setInt(1, id);
            ResultSet rs = stmt1.executeQuery();

            if (rs.next()) {
                connection.setAutoCommit(false);

                // Atualizando os dados de equipamento
                PreparedStatement stmt2 = connection.prepareStatement(sql);
                stmt2.setString(1, loja.getCnpj());
                stmt2.setString(2, loja.getGerente());
                stmt2.setString(3, loja.getCidade());
                stmt2.setString(4, loja.getTelefone());
                stmt2.setInt(5, id);

                int rows1 = stmt2.executeUpdate();
                System.out.println("Linhas afetadas em loja: " + rows1);

                connection.commit();
                
                stmt1.close();
                stmt2.close();
            } else {
                System.out.println("Nenhuma loja encontrada com o ID: " + id);
            }
            rs.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
        
    public void deletar(int id) {
  
        try {
            // Inicializa as instruções SQL
            PreparedStatement stmt = this.connection.prepareStatement("DELETE FROM loja WHERE pk_loja = ?");
            stmt.setInt(1, id);
            
             // Começa a transação
            connection.setAutoCommit(false);

            // Executa a exclusão do computador
            int rows1 = stmt.executeUpdate();
            if (rows1 > 0) {
                System.out.println("Linhas afetadas em loja: " + rows1);
                connection.commit();
            } else {
                System.out.println("Nenhuma loja encontrada com ID " + id);
            }

            // Confirma a transação
            connection.commit();
            
            System.out.println("");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}