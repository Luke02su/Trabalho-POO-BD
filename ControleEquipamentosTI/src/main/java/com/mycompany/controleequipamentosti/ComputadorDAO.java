package com.mycompany.controleequipamentosti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComputadorDAO implements EquipamentoLojaMetodos<Computador> {

    // A conexão com o banco de dados
    private Connection connection; // Campo para armazenar a conexão com o banco de dados

    // Construtor da classe ComputadorDAO
    public ComputadorDAO() {
        // Inicializa a conexão com o banco de dados usando a ConnectionFactory
        this.connection = new ConnectionFactory().getConnection();
    }
   
    // Método para adicionar um novo computador ao banco de dados
    public void adicionar(Computador computador) {
        // Comando SQL para inserir um novo equipamento
        String sql1 = "INSERT INTO equipamento (tipo, modelo) VALUES (?, ?)";
        // Comando SQL para inserir um novo computador, com fk_equipamento referenciando o equipamento inserido
        String sql2 = "INSERT INTO computador (fk_equipamento, processador, memoria, windows, armazenamento, formatacao, manutencao) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            // Começar a transação
            connection.setAutoCommit(false);

            // Preparar e executar a inserção do equipamento
            PreparedStatement stmt1 = connection.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt1.setString(1, computador.getTipo());
            stmt1.setString(2, computador.getModelo());
            stmt1.execute();

            // Recuperar a chave primária gerada para o equipamento
            ResultSet generatedKeys = stmt1.getGeneratedKeys();
            if (generatedKeys.next()) {
                int equipamentoId = generatedKeys.getInt(1); // Obtém o ID do equipamento gerado

                // Preparar e executar a inserção do computador usando o ID do equipamento
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

            System.out.println("\nComputador adicionado com sucesso!\n"); // Mensagem de sucesso
        } catch (SQLException e) {
            try {
                // Se houver um erro, reverter a transação
                connection.rollback();
                System.out.println("Erro na gravação de computador. Transação revertida."); // Mensagem de erro
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Erro ao reverter a transação.", rollbackEx);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                // Restaurar o estado de autocommit
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao restaurar o estado de autocommit.", ex);
            }
        }
    }

    // Método para obter uma lista de todos os computadores no banco de dados
    @Override
    public List<Computador> getLista() {
        try {
            // Cria uma lista para armazenar os computadores recuperados do banco de dados
            List<Computador> computadores = new ArrayList<Computador>();

            // Comando SQL para selecionar todos os registros da tabela computador, juntando com equipamento
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM computador INNER JOIN equipamento ON pk_equipamento = fk_equipamento");
            
            // Executa a consulta e obtém o resultado
            ResultSet rs = stmt.executeQuery();

            // Itera sobre cada registro retornado pelo ResultSet
            while (rs.next()) {
                // Cria um novo objeto Computador e define seus atributos com base nos dados do banco de dados
                Computador computador = new Computador();
                computador.setPk_equipamento(rs.getInt("Pk_equipamento"));
                computador.setTipo(rs.getString("tipo"));
                computador.setModelo(rs.getString("modelo"));
                computador.setPk_computador(rs.getInt("pk_computador")); // ID do computador
                computador.setProcessador(rs.getString("processador"));
                computador.setMemoria(rs.getString("memoria"));
                computador.setWindows(rs.getString("windows"));
                computador.setArmazenamento(rs.getString("armazenamento"));
                computador.setFormatacao(rs.getString("formatacao"));
                computador.setManutencao(rs.getString("manutencao"));

                // Adiciona o computador à lista de computadores
                computadores.add(computador);
            }
            rs.close(); // Fecha o ResultSet
            stmt.close(); // Fecha o PreparedStatement
            return computadores; // Retorna a lista de computadores
        } catch (SQLException e) {
            // Lança uma exceção em caso de erro na execução do SQL
            throw new RuntimeException(e);
        }
    }
    
    // Método para listar todos os computadores
    @Override
    public void listar() {
        System.out.println("\n---------- LISTA COMPLETA DE COMPUTADORES ----------");
        // Obtém a lista de computadores do banco de dados
        List<Computador> computador = this.getLista();
        
        // Itera sobre cada computador e imprime seus detalhes
        for (Computador c : computador) {
            System.out.println("ID de equipamento: " + c.getPk_equipamento());
            System.out.println("ID de computador: " + c.getPk_computador());
            System.out.println("Processador: " + c.getProcessador());
            System.out.println("Tipo: " + c.getTipo());
            System.out.println("Modelo: " + c.getModelo());
            System.out.println("Memória RAM: " + c.getMemoria());
            System.out.println("Versão do Windows: " + c.getWindows());
            System.out.println("Armazenamento " + c.getArmazenamento());
            System.out.println("Formatado? " + c.getFormatacao());
            System.out.println("M. preventiva e/ou M. corretiva: " + c.getManutencao());
            System.out.println("--------------------------------------------------");
        }
        System.out.println("\nListagem completa do todos computadores realizada com sucesso!\n");
    }
    
    // Método para listar um computador específico com base no ID
    @Override
    public void listarID(int id) {
        System.out.println("\n---------- LISTAGEM COMPLETA DE COMPUTADOR " + id + " ----------");
        // Obtém a lista de computadores do banco de dados
        List<Computador> computador = this.getLista();
        
        // Itera sobre cada computador e imprime seus detalhes se o ID corresponder
        boolean inserido = false;
        
        for (Computador c : computador) {
            if(id == c.getPk_computador()) {
                System.out.println("ID de equipamento: " + c.getPk_equipamento());
                System.out.println("ID de computador: " + c.getPk_computador());
                System.out.println("Processador: " + c.getProcessador());
                System.out.println("Tipo: " + c.getTipo());
                System.out.println("Modelo: " + c.getModelo());
                System.out.println("Memória RAM: " + c.getMemoria());
                System.out.println("Versão do Windows: " + c.getWindows());
                System.out.println("Armazenamento: " + c.getArmazenamento());
                System.out.println("Formatado? " + c.getFormatacao());
                System.out.println("M. preventiva e/ou M. corretiva: " + c.getManutencao());
                System.out.println("--------------------------------------------------");
                inserido = true;
                break;
            }
        }
        if (!inserido) {
            System.out.println("\nNenhum computador com o ID " + id + " foi encontrado na base de dados.\n");
        } else {
            System.out.println("\nListagem de computador com ID " + id + " realizada com sucesso!\n");
        }
    }
    
    // Método para atualizar um computador e o seu equipamento associado
    @Override
    public void atualizar(Computador computador, int id) {
        String sql1 = "UPDATE equipamento SET tipo = ?, modelo = ? WHERE pk_equipamento = ?";
        String sql2 = "UPDATE computador SET processador = ?, memoria = ?, windows = ?, armazenamento = ?, formatacao = ?, manutencao = ? WHERE pk_computador = ?";

        try {
            // Primeiro, selecione o pk_equipamento associado ao id do computador
            PreparedStatement stmt = this.connection.prepareStatement("SELECT fk_equipamento FROM computador WHERE pk_computador = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            int cont = 0;
            int cont2 = 0;

            if (rs.next()) {
                int pk_equipamento = rs.getInt("fk_equipamento");

                connection.setAutoCommit(false);

                // Atualizando os dados do equipamento
                PreparedStatement stmt1 = connection.prepareStatement(sql1);
                stmt1.setString(1, computador.getTipo());
                cont++;
                stmt1.setString(2, computador.getModelo());
                cont++;
                stmt1.setInt(3, pk_equipamento);

                stmt1.executeUpdate();
                System.out.println("\nColunas afetadas na tupla de equipamento " + pk_equipamento + ": " + cont);

                // Atualizando os dados do computador
                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                stmt2.setString(1, computador.getProcessador());
                cont2++;
                stmt2.setString(2, computador.getMemoria());
                cont2++;
                stmt2.setString(3, computador.getWindows());
                cont2++;
                stmt2.setString(4, computador.getArmazenamento());
                cont2++;
                stmt2.setString(5, computador.getFormatacao());
                cont2++;
                stmt2.setString(6, computador.getManutencao());
                cont2++;
                stmt2.setInt(7, id);

                stmt2.executeUpdate();
                System.out.println("Colunas afetadas na tupla de computador " + id + ": " + cont2 + "\n");

                connection.commit();
                stmt1.close();
                stmt2.close();
            } else {
                System.out.println("\nNenhum computador com ID " + id + " foi encontrado na base de dados.\n");
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            try {
                connection.rollback(); // Reverte a transação em caso de erro
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true); // Restaura o modo de commit automático
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
     // Método para atualizar um computador e o seu equipamento associado
    @Override
    public void atualizarUmAtributo(Computador computador, int id) {
        String sql1 = "UPDATE equipamento SET";
        String sql2 = "UPDATE computador SET";
        boolean updateEquipamento = false;
        boolean updateComputador = false;

        try {
            // Primeiro, selecione o pk_equipamento associado ao id do computador
            PreparedStatement stmt = this.connection.prepareStatement("SELECT fk_equipamento FROM computador WHERE pk_computador = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int pk_equipamento = rs.getInt("fk_equipamento");
                connection.setAutoCommit(false);

                // Construir a consulta SQL para atualizar o equipamento
                if (computador.getTipo() != null) {
                    sql1 += " tipo = ?,";
                    updateEquipamento = true;
                }
                if (computador.getModelo() != null) {
                    sql1 += " modelo = ?,";
                    updateEquipamento = true;
                }
                if (updateEquipamento) {
                    sql1 = sql1.endsWith(",") ? sql1.substring(0, sql1.length() - 1) : sql1;
                    sql1 += " WHERE pk_equipamento = ?";
                }

                // Construir a consulta SQL para atualizar o computador
                if (computador.getProcessador() != null) {
                    sql2 += " processador = ?,";
                    updateComputador = true;
                }
                if (computador.getMemoria() != null) {
                    sql2 += " memoria = ?,";
                    updateComputador = true;
                }
                if (computador.getWindows() != null) {
                    sql2 += " windows = ?,";
                    updateComputador = true;
                }
                if (computador.getArmazenamento() != null) {
                    sql2 += " armazenamento = ?,";
                    updateComputador = true;
                }
                if (computador.getFormatacao() != null) {
                    sql2 += " formatacao = ?,";
                    updateComputador = true;
                }
                if (computador.getManutencao() != null) {
                    sql2 += " manutencao = ?,";
                    updateComputador = true;
                }
                if (updateComputador) {
                    sql2 = sql2.endsWith(",") ? sql2.substring(0, sql2.length() - 1) : sql2;
                    sql2 += " WHERE pk_computador = ?";
                }

                // Atualizando os dados do equipamento, se necessário
                if (updateEquipamento) {
                    PreparedStatement stmt1 = connection.prepareStatement(sql1);
                    int index = 1;
                    if (computador.getTipo() != null) {
                        stmt1.setString(index++, computador.getTipo());
                    }
                    if (computador.getModelo() != null) {
                        stmt1.setString(index++, computador.getModelo());
                    }
                    stmt1.setInt(index++, pk_equipamento);

                    int rows1 = stmt1.executeUpdate();
                    System.out.println("\nColunas afetadas na tupla de equipamento: " + rows1 + "\n");
                    stmt1.close();
                }

                // Atualizando os dados do computador, se necessário
                if (updateComputador) {
                    PreparedStatement stmt2 = connection.prepareStatement(sql2);
                    int index = 1;
                    if (computador.getProcessador() != null) {
                        stmt2.setString(index++, computador.getProcessador());
                    }
                    if (computador.getMemoria() != null) {
                        stmt2.setString(index++, computador.getMemoria());
                    }
                    if (computador.getWindows() != null) {
                        stmt2.setString(index++, computador.getWindows());
                    }
                    if (computador.getArmazenamento() != null) {
                        stmt2.setString(index++, computador.getArmazenamento());
                    }
                    if (computador.getFormatacao() != null) {
                        stmt2.setString(index++, computador.getFormatacao());
                    }
                    if (computador.getManutencao() != null) {
                        stmt2.setString(index++, computador.getManutencao());
                    }
                    stmt2.setInt(index++, id);

                    int rows2 = stmt2.executeUpdate();
                    System.out.println("\nColunas afetadas na tupla de computador: " + rows2 + "\n");
                    stmt2.close();
                }

                connection.commit();
            } else {
                System.out.println("\nNenhum computador com ID " + id + " foi encontrado na base de dados.\n");
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            try {
                // Se houver um erro, reverter a transação
                connection.rollback();
                System.out.println("Erro na gravação de computador. Transação revertida."); // Mensagem de erro
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Erro ao reverter a transação.", rollbackEx);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                // Restaurar o estado de autocommit
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao restaurar o estado de autocommit.", ex);
            }
        }
    }
    
    // Método para deletar um computador e seu equipamento associado
    @Override
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

            // Define o valor do parâmetro ID nas instruções SQL
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
                System.out.println("\nLinhas afetadas em computador: " + rows1);

                // Executa a exclusão do equipamento
                int rows2 = stmt3.executeUpdate();
                System.out.println("Linhas afetadas em equipamento: " + rows2 + "\n");

                // Confirma a transação
                connection.commit();
            } else {
                System.out.println("Nenhum computador com ID " + id + " foi encontrado na base de dados.\n");
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