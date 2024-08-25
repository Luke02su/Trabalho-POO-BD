package com.mycompany.controleequipamentosti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpressoraDAO {
    
// A conexão com o banco de dados
    private Connection connection; // Campo para armazenar a conexão com o banco de dados
    
    public ImpressoraDAO() {
        // Inicializa a conexão com o banco de dados usando a ConnectionFactory
        this.connection = new ConnectionFactory().getConnection();
    }

    // Construtor da classe ContatoDAO
    public void adicionar(Impressora impressora) {
        String sql1 = "INSERT INTO equipamento (tipo, modelo) VALUES (?, ?)";
        String sql2 = "INSERT INTO impressora (fk_equipamento, revisao) VALUES (?, ?)";

        try {
            // Start the transaction
            connection.setAutoCommit(false);

            // Insert into equipamento
            PreparedStatement stmt1 = connection.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt1.setString(1, impressora.getTipo());
            stmt1.setString(2, impressora.getModelo());
            stmt1.executeUpdate();

            // Retrieve the generated primary key for equipamento
            ResultSet generatedKeys = stmt1.getGeneratedKeys();
            if (generatedKeys.next()) {
                int equipamentoId = generatedKeys.getInt(1);

                // Set the primary key in the impressora object
                impressora.setPk_equipamento(equipamentoId);

                // Insert into impressora using the generated key
                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                stmt2.setInt(1, impressora.getPk_equipamento());
                stmt2.setString(2, impressora.getRevisao());
                stmt2.executeUpdate();

                stmt2.close();
            }

            // Commit the transaction
            connection.commit();
            System.out.println("Impressora adicionada com sucesso!"); // Success message
            stmt1.close();
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback in case of error
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Erro ao fazer rollback!", rollbackEx);
            }
            throw new RuntimeException("Erro ao adicionar impressora!", e);
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao redefinir auto-commit!", ex);
            }
        }
    }


    public List<Impressora> getLista() {
        try {
            // Cria uma lista para armazenar os contatos recuperados do banco de dados
            List<Impressora> impressoras = new ArrayList<Impressora>();

            // Comando SQL para selecionar todos os registros da tabela contatos
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM impressora INNER JOIN equipamento ON pk_equipamento = fk_equipamento");
            
            // Executa a consulta e obtém o resultado
            ResultSet rs = stmt.executeQuery();

            // Itera sobre cada registro retornado pelo ResultSet
            while (rs.next()) {
                // Cria um novo objeto Contato e define seus atributos com base nos dados do banco de dados
                Impressora impressora = new Impressora();
                impressora.setPk_equipamento(rs.getInt("Pk_equipamento"));
                impressora.setTipo(rs.getString("tipo"));
                impressora.setModelo(rs.getString("modelo"));
                impressora.setPk_impressora(rs.getInt("pk_impressora")); // ID do contato
                impressora.setRevisao(rs.getString("revisao"));

                // Adiciona o contato à lista de contatos
                impressoras.add(impressora);
            }
            rs.close(); // Fecha o ResultSet
            stmt.close(); // Fecha o PreparedStatement
            return impressoras; // Retorna a lista de contatos
        } catch (SQLException e) {
            // Lança uma exceção em caso de erro na execução do SQL
            throw new RuntimeException(e);
        }
    }
    
    public void listar() {
        System.out.println("------------ LISTAS COMPLETAS DE IMPRESSORAS------------");
        // Obtém a lista de contatos do banco de dados
        List<Impressora> impressora = this.getLista();
        
        // Itera sobre cada contato e imprime seus detalhes
        for (Impressora i : impressora) {
            System.out.println("ID de equipamento: " + i.getPk_equipamento()); // Imprime o nome do contato
            System.out.println("ID de impressora " + i.getPk_impressora()); // Imprime o email do contato
            System.out.println("Tipo: " + i.getTipo());
            System.out.println("Modelo: " + i.getModelo());
            System.out.println("Revisada? " + i.getRevisao());
            System.out.println("----------------------------------");
        }
    }
    
        public void listarID(int id) {
        System.out.println("------------ LISTA COMPLETA DE IMPRESSORA " + id + "------------");
        // Obtém a lista de contatos do banco de dados
        List<Impressora> impressora = this.getLista();
        
        // Itera sobre cada contato e imprime seus detalhes
        
        boolean inserido = false;
       
        for (Impressora i : impressora) {
            if(id == i.getPk_impressora()) {
                System.out.println("ID de equipamento: " + i.getPk_equipamento()); // Imprime o nome do contato
                System.out.println("ID de computador: " + i.getPk_impressora()); // Imprime o email do contato
                System.out.println("Tipo: " + i.getTipo());
                System.out.println("Modelo: " + i.getModelo()); // Imprime o endereço do cont
                System.out.println("Revisada? " + i.getRevisao()); 
                System.out.println("----------------------------------");
                inserido = true;
                break;
            } 
        }
        if (inserido == false) {
            System.out.println("Sinto muito! Este computador não existe.");
        }
    }
        
        public void atualizar(Impressora impressora, int id) {
        String sql1 = "UPDATE equipamento SET tipo = ?, modelo = ? WHERE pk_equipamento = ?";
        String sql2 = "UPDATE impressora SET revisao = ? WHERE pk_impressora = ?";

        try {
            // Primeiro, selecione o pk_equipamento associado ao id do computador
            PreparedStatement stmt = this.connection.prepareStatement("SELECT fk_equipamento FROM impressora WHERE pk_impressora = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int pk_equipamento = rs.getInt("fk_equipamento");

                connection.setAutoCommit(false);

                // Atualizando os dados de equipamento
                PreparedStatement stmt1 = connection.prepareStatement(sql1);
                stmt1.setString(1, impressora.getTipo());
                stmt1.setString(2, impressora.getModelo());
                stmt1.setInt(3, pk_equipamento);

                int rows1 = stmt1.executeUpdate();
                System.out.println("Linhas afetadas em equipamento: " + rows1);

                // Atualizando os dados de computador
                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                stmt2.setString(1, impressora.getRevisao());
                stmt2.setInt(2, id);

                int rows2 = stmt2.executeUpdate();
                System.out.println("Linhas afetadas em impressora: " + rows2 + "\n");

                connection.commit();
                stmt1.close();
                stmt2.close();
            } else {
                System.out.println("Dados não atualizados! Nenhuma impressora encontrada com o ID: " + id);
            }

            rs.close();
            stmt.close();

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
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        ResultSet rs = null;

        try {
            // Inicializa as instruções SQL
            stmt1 = connection.prepareStatement("DELETE FROM impressora WHERE pk_impressora = ?");
            stmt2 = connection.prepareStatement("SELECT fk_equipamento FROM impressora WHERE pk_impressora = ?");
            stmt3 = connection.prepareStatement("DELETE FROM equipamento WHERE pk_equipamento = ?");

            // Define o valor do parâmetro ID na instrução SQL
            stmt1.setInt(1, id);
            stmt2.setInt(1, id);

            // Executa a consulta para obter o fk_equipamento
            rs = stmt2.executeQuery();

            if (rs.next()) { // Verifica se há um resultado
                int pk_equipamento = rs.getInt("fk_equipamento");

                // Define o parâmetro para a exclusão do equipamento
                stmt3.setInt(1, pk_equipamento);

                // Começa a transação
                connection.setAutoCommit(false);

                // Executa a exclusão do computador
                int rows1 = stmt1.executeUpdate();
                System.out.println("Linhas afetadas em impressora: " + rows1);

                // Executa a exclusão do equipamento
                int rows2 = stmt3.executeUpdate();
                System.out.println("Linhas afetadas em equipamento: " + rows2 + "\n");

                // Confirma a transação
                connection.commit();
            } else {
                System.out.println("Nenhum equipamento encontrado para a impressora com ID: " + id);
            }
            System.out.println("");
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Reverte a transação em caso de erro
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            // Libera os recursos
            try {
                if (rs != null) rs.close();
                if (stmt1 != null) stmt1.close();
                if (stmt2 != null) stmt2.close();
                if (stmt3 != null) stmt3.close();
                if (connection != null) connection.setAutoCommit(true); // Restaura o modo de commit automático
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}