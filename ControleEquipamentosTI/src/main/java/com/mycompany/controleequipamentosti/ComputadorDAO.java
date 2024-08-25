package com.mycompany.controleequipamentosti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComputadorDAO implements CrudDAO<Computador> {

    // A conexão com o banco de dados
    private Connection connection; // Campo para armazenar a conexão com o banco de dados

    // Construtor da classe ContatoDAO
    public ComputadorDAO() {
        // Inicializa a conexão com o banco de dados usando a ConnectionFactory
        this.connection = new ConnectionFactory().getConnection();
    }
   
    public void adicionar(Computador computador) {
        // Comando SQL para inserir um novo equipamento e computador
        String sql1 = "INSERT INTO equipamento (tipo, modelo) VALUES (?, ?)";
        String sql2 = "INSERT INTO computador (fk_equipamento, processador, memoria, windows, armazenamento, formatacao, manutencao) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            // Começar a transação
            connection.setAutoCommit(false);

            // Inserir o equipamento
            PreparedStatement stmt1 = connection.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt1.setString(1, computador.getTipo());
            stmt1.setString(2, computador.getModelo());
            stmt1.execute();

            // Recuperar a chave primária gerada para o equipamento
            ResultSet generatedKeys = stmt1.getGeneratedKeys();
            if (generatedKeys.next()) {
                int equipamentoId = generatedKeys.getInt(1);

                // Inserir o computador com a chave estrangeira do equipamento
                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                stmt2.setInt(1, equipamentoId); // fk_equipamento
                stmt2.setString(2, computador.getProcessador());
                stmt2.setString(3, computador.getMemoria());
                stmt2.setString(4, computador.getWindows());
                stmt2.setString(5, computador.getArmazenamento());
                stmt2.setString(6, computador.getFormatacao());
                stmt2.setString(7, computador.getManutencao());

                stmt2.execute();
                stmt2.close();
            }

            // Commit da transação
            connection.commit();

            System.out.println("Computador adicionado com sucesso."); // Mensagem de sucesso
        } catch (SQLException e) {
            try {
                // Se houver um erro, reverter a transação
                connection.rollback();
                System.out.println("Erro na gravação de computador. Transação revertida."); // Mensagem de erro
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

    @Override
    public List<Computador> getLista() {
        try {
            // Cria uma lista para armazenar os contatos recuperados do banco de dados
            List<Computador> computadores = new ArrayList<Computador>();

            // Comando SQL para selecionar todos os registros da tabela contatos
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM computador INNER JOIN equipamento ON pk_equipamento = fk_equipamento");
            
            // Executa a consulta e obtém o resultado
            ResultSet rs = stmt.executeQuery();

            // Itera sobre cada registro retornado pelo ResultSet
            while (rs.next()) {
                // Cria um novo objeto Contato e define seus atributos com base nos dados do banco de dados
                Computador computador = new Computador();
                computador.setPk_equipamento(rs.getInt("Pk_equipamento"));
                computador.setTipo(rs.getString("tipo"));
                computador.setModelo(rs.getString("modelo"));
                computador.setPk_computador(rs.getInt("pk_computador")); // ID do contato
                computador.setProcessador(rs.getString("processador")); // Nome do contato
                computador.setMemoria(rs.getString("memoria")); // Nome do contato
                computador.setWindows(rs.getString("windows")); // Nome do contato
                computador.setArmazenamento(rs.getString("armazenamento")); // Email do contato
                computador.setFormatacao(rs.getString("formatacao")); // Endereço do contato
                computador.setManutencao(rs.getString("manutencao")); // Endereço do contato

                // Adiciona o contato à lista de contatos
                computadores.add(computador);
            }
            rs.close(); // Fecha o ResultSet
            stmt.close(); // Fecha o PreparedStatement
            return computadores; // Retorna a lista de contatos
        } catch (SQLException e) {
            // Lança uma exceção em caso de erro na execução do SQL
            throw new RuntimeException(e);
        }
    }
    
    public void listar() {
        System.out.println("------------ LISTA COMPLETA DE COMPUTADORES ------------");
        // Obtém a lista de contatos do banco de dados
        List<Computador> computador = this.getLista();
        
        // Itera sobre cada contato e imprime seus detalhes
        for (Computador c : computador) {
            System.out.println("ID de equipamento: " + c.getPk_equipamento()); // Imprime o nome do contato
            System.out.println("ID de computador: " + c.getPk_computador()); // Imprime o email do contato
            System.out.println("Processador: " + c.getProcessador()); // Imprime o endereço do contato
            System.out.println("Tipo: " + c.getTipo()); // Imprime o endereço do contato
            System.out.println("Modelo: " + c.getModelo()); // Imprime o endereço do contato
            System.out.println("Memória RAM: " + c.getMemoria()); // Imprime o endereço do contato
            System.out.println("Versão do Windows: " + c.getWindows());
            System.out.println("Armazenamento " + c.getArmazenamento());
            System.out.println("Formatado? " + c.getFormatacao());
            System.out.println("MP E/Ou MC: " + c.getManutencao());
            System.out.println("----------------------------------");
        }
    }
    
    public void listarID(int id) {
        System.out.println("------------ LISTAGEM COMPLETA DE COMPUTADOR " + id + "------------");
        // Obtém a lista de contatos do banco de dados
        List<Computador> computador = this.getLista();
        
        // Itera sobre cada contato e imprime seus detalhes
       boolean inserido = false;
        
        for (Computador c : computador) {
            if(id == c.getPk_computador()) {
                System.out.println("ID de equipamento: " + c.getPk_equipamento()); // Imprime o nome do contato
                System.out.println("ID de computador: " + c.getPk_computador()); // Imprime o email do contato
                System.out.println("Processador: " + c.getProcessador()); // Imprime o endereço do contato
                System.out.println("Tipo: " + c.getTipo());
                System.out.println("Modelo: " + c.getModelo()); // Imprime o endereço do contato
                System.out.println("Memória RAM: " + c.getMemoria()); // Imprime o endereço do contato
                System.out.println("Versão do Windows: " + c.getWindows());
                System.out.println("Armazenamento " + c.getArmazenamento());
                System.out.println("Formatado? " + c.getFormatacao());
                System.out.println("MP E/OU MC: " + c.getManutencao());
                System.out.println("----------------------------------");
                inserido = true;
                break;
            }
        }
        
        if (inserido == false) {
            System.out.println("Sinto muito! Este computador não existe.");
        }
    }
    
    public void atualizar(Computador computador, int id) {
        String sql1 = "UPDATE equipamento SET tipo = ?, modelo = ? WHERE pk_equipamento = ?";
        String sql2 = "UPDATE computador SET processador = ?, memoria = ?, windows = ?, armazenamento = ?, formatacao = ?, manutencao = ? WHERE pk_computador = ?";

        try {
            // Primeiro, selecione o pk_equipamento associado ao id do computador
            PreparedStatement stmt = this.connection.prepareStatement("SELECT fk_equipamento FROM computador WHERE pk_computador = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int pk_equipamento = rs.getInt("fk_equipamento");

                connection.setAutoCommit(false);

                // Atualizando os dados de equipamento
                PreparedStatement stmt1 = connection.prepareStatement(sql1);
                stmt1.setString(1, computador.getTipo());
                stmt1.setString(2, computador.getModelo());
                stmt1.setInt(3, pk_equipamento);

                int rows1 = stmt1.executeUpdate();
                System.out.println("Linhas afetadas em equipamento: " + rows1);

                // Atualizando os dados de computador
                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                stmt2.setString(1, computador.getProcessador());
                stmt2.setString(2, computador.getMemoria());
                stmt2.setString(3, computador.getWindows());
                stmt2.setString(4, computador.getArmazenamento());
                stmt2.setString(5, computador.getFormatacao());
                stmt2.setString(6, computador.getManutencao());
                stmt2.setInt(7, id);

                int rows2 = stmt2.executeUpdate();
                System.out.println("Linhas afetadas em computador: " + rows2);

                connection.commit();
                stmt1.close();
                stmt2.close();
            } else {
                System.out.println("Nenhum computador encontrado com o ID: " + id);
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