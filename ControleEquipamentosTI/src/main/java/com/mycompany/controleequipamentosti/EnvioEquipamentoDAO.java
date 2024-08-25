package com.mycompany.controleequipamentosti;

import java.time.LocalDate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnvioEquipamentoDAO implements CrudDAO<EnvioEquipamento> {
    // A conexão com o banco de dados
    private Connection connection; // Campo para armazenar a conexão com o banco de dados
    
    public EnvioEquipamentoDAO() {
        // Inicializa a conexão com o banco de dados usando a ConnectionFactory
        this.connection = new ConnectionFactory().getConnection();
    }
    
        public void adicionar(EnvioEquipamento envio) {
        // Comando SQL para inserir um novo equipamento e computador
        String sql = "INSERT INTO envio_equipamento (fk_equipamento, fk_loja, data_envio, observacao) VALUES (?, ?, ?, ?)";

        try {
            // Começar a transação
            connection.setAutoCommit(false);
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, envio.getEquipamento().getPk_equipamento()); // fk_equipamento
            stmt.setInt(2, envio.getLoja().getPk_loja());
            stmt.setString(3, envio.getData_envio());
            stmt.setString(4, envio.getObservacao());
            stmt.execute();

            connection.commit();
            System.out.println("Envio de equipamento adicionado com sucesso."); // Mensagem de sucesso
            stmt.close();
        } catch (SQLException e) {
            try {
                // Se houver um erro, reverter a transação
                connection.rollback();
                System.out.println("Erro na gravação de envio de computador. Transação revertida."); // Mensagem de erro
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Erro ao reverter a transação", rollbackEx);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                // Restaurar o estado de autocommit
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao restaurar o estado de autocommit", ex);
            }
        }
    }

    public List<EnvioEquipamento> getLista() {
        try {
            // Cria uma lista para armazenar os contatos recuperados do banco de dados
            List<EnvioEquipamento> envios = new ArrayList<EnvioEquipamento>();

            // Comando SQL para selecionar todos os registros da tabela contatos
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM view_equipamento_envio_detalhado");
            
            // Executa a consulta e obtém o resultado
            ResultSet rs = stmt.executeQuery();

            // Itera sobre cada registro retornado pelo ResultSet
            while (rs.next()) {
                // Cria um novo objeto Contato e define seus atributos com base nos dados do banco de dados
                EnvioEquipamento envio = new EnvioEquipamento();
                Equipamento equipamento = new Computador(); // commo e abstract, usei objeto de pc
                Loja loja = new Loja(); 

                envio.setEquipamento(equipamento);
                envio.getEquipamento().setPk_equipamento(rs.getInt("fk_equipamento"));
                envio.getEquipamento().setTipo(rs.getString("tipo"));
                envio.getEquipamento().setModelo(rs.getString("modelo"));
                envio.setLoja(loja);
                envio.getLoja().setPk_loja(rs.getInt("fk_loja"));
                envio.getLoja().setGerente(rs.getString("gerente"));
                envio.setData_envio(rs.getString("data_envio"));
                envio.setObservacao(rs.getString("observacao"));

                // Adiciona o contato à lista de contatos
                envios.add(envio);
            }
            rs.close(); // Fecha o ResultSet
            stmt.close(); // Fecha o PreparedStatement
            return envios; // Retorna a lista de contatos
        } catch (SQLException e) {
            // Lança uma exceção em caso de erro na execução do SQL
            throw new RuntimeException(e);
        }
    }
    
        public void listar() {
        System.out.println("------------ LISTAS COMPLETAS DE ENVIO DE EQUIPAMENTO ------------");
        // Obtém a lista de contatos do banco de dados
        List<EnvioEquipamento> envio = this.getLista();
        
        // Itera sobre cada contato e imprime seus detalhes
        for (EnvioEquipamento eq : envio) {
            System.out.println("ID do equipamento: " + eq.getEquipamento().getPk_equipamento()); // Imprime o email do contato
            System.out.println("Tipo: " + eq.getEquipamento().getTipo());
            System.out.println("Modelo: " + eq.getEquipamento().getModelo());
            System.out.println("ID da loja :" + eq.getLoja().getPk_loja());
            System.out.println("Gerente: " + eq.getLoja().getGerente());
            System.out.println("Data de envio:" + eq.getData_envio());
            System.out.println("Observação: " + eq.getObservacao());
            System.out.println("----------------------------------");
        }
    }
        
    
    public void listarID(int id) {
        System.out.println("------------ LISTA COMPLETA DE ENVIO DE EQUIPAMENTO " + id + "------------");
        // Obtém a lista de contatos do banco de dados
        List<EnvioEquipamento> envio = this.getLista();
        
        // Itera sobre cada contato e imprime seus detalhes
        
        boolean inserido = false;
       
        for (EnvioEquipamento eq : envio) {
            if(id == eq.getPk_envio()) {
                System.out.println("ID do equipamento: " + eq.getEquipamento().getPk_equipamento()); // Imprime o email do contato
                System.out.println("Tipo: " + eq.getEquipamento().getTipo());
                System.out.println("Modelo: " + eq.getEquipamento().getModelo());
                System.out.println("ID da loja:" + eq.getLoja().getPk_loja());
                System.out.println("Gerente: " + eq.getLoja().getGerente());
                System.out.println("Data de envio:" + eq.getData_envio());
                System.out.println("Observação: " + eq.getObservacao());
                System.out.println("----------------------------------");
                inserido = true;
                break;
            } 
        }
        if (inserido == false) {
            System.out.println("Sinto muito! Este equipamento genérico não existe.");
        }
    }
    
    public void atualizar(EnvioEquipamento envio, int idEquipamento, int idLoja) {
        String sql = "UPDATE envio_equipamento SET data_envio = ?, observacao = ? WHERE fk_equipamento = ? AND fk_loja = ?";

        try {
            // Primeiro, selecione o pk_equipamento associado ao id do computador
            PreparedStatement stmt1 = this.connection.prepareStatement("SELECT fk_equipamento, fk_loja FROM envio_equipamento WHERE fk_equipamento = ? AND fk_loja = ?");
            stmt1.setInt(1, idEquipamento);
            stmt1.setInt(2, idLoja);
            ResultSet rs = stmt1.executeQuery();

            if (rs.next()) {
                int fk_equipamento = rs.getInt("fk_equipamento");
                int fk_loja = rs.getInt("fk_loja");

                connection.setAutoCommit(false);

                // Atualizando os dados de equipamento
                PreparedStatement stmt2 = connection.prepareStatement(sql);
                stmt2.setString(1, envio.getData_envio());
                stmt2.setString(2, envio.getObservacao());
                stmt2.setInt(3, fk_equipamento);
                stmt2.setInt(4, fk_loja);
                
                int rows1 = stmt2.executeUpdate();
                System.out.println("Linhas afetadas em envio de equipamento: " + rows1);

                connection.commit();
                stmt2.close();
            } else {
                System.out.println("Dados não atualizados! Nenhum envio de equipamento encontrado com o ID de equipamento " + idEquipamento + "e ID de loja" + idLoja);
            }

            rs.close();
            stmt1.close();
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
            stmt1 = connection.prepareStatement("DELETE FROM computador WHERE pk_computador = ?");
            stmt2 = connection.prepareStatement("SELECT fk_equipamento FROM computador WHERE pk_computador = ?");
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
                System.out.println("Linhas afetadas em computador: " + rows1);

                // Executa a exclusão do equipamento
                int rows2 = stmt3.executeUpdate();
                System.out.println("Linhas afetadas em equipamento: " + rows2);

                // Confirma a transação
                connection.commit();
            } else {
                System.out.println("Nenhum equipamento encontrado para o computador com ID: " + id);
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
