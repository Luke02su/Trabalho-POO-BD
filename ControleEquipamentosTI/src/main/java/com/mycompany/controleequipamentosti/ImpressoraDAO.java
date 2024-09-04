package com.mycompany.controleequipamentosti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpressoraDAO implements EquipamentoLojaMetodos<Impressora> {
    
    // A conexão com o banco de dados
    private Connection connection; // Campo para armazenar a conexão com o banco de dados
    
    // Construtor da classe ImpressoraDAO
    public ImpressoraDAO() {
        // Inicializa a conexão com o banco de dados usando a ConnectionFactory
        this.connection = new ConnectionFactory().getConnection();
    }

    // Método para adicionar uma nova impressora ao banco de dados
    @Override
    public void adicionar(Impressora impressora) {
        String sql1 = "INSERT INTO equipamento (tipo, modelo) VALUES (?, ?)";
        String sql2 = "INSERT INTO impressora (fk_equipamento, revisao) VALUES (?, ?)";

        try {
            // Inicia a transação
            connection.setAutoCommit(false);

            // Insere dados na tabela equipamento
            PreparedStatement stmt1 = connection.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt1.setString(1, impressora.getTipo());
            stmt1.setString(2, impressora.getModelo());
            stmt1.executeUpdate();

            // Recupera a chave primária gerada para equipamento
            ResultSet generatedKeys = stmt1.getGeneratedKeys();
            if (generatedKeys.next()) {
                int equipamentoId = generatedKeys.getInt(1);

                // Define a chave primária no objeto impressora
                impressora.setPk_equipamento(equipamentoId);

                // Insere dados na tabela impressora usando a chave gerada
                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                stmt2.setInt(1, impressora.getPk_equipamento());
                stmt2.setString(2, impressora.getRevisao());
                stmt2.executeUpdate();

                stmt2.close();
            }

            // Confirma a transação
            connection.commit();
            System.out.println("\nImpressora adicionada com sucesso!\n"); // Mensagem de sucesso
            stmt1.close();
        } catch (SQLException e) {
            try {
                connection.rollback(); // Reverte a transação em caso de 
                System.out.println("Erro na gravação de impressora. Transação revertida.");
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Erro ao reverter a transação.", rollbackEx);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true); // Restaura o modo de commit automático
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao restaurar o estado de autocommit.", ex);
            }
        }
    }

    // Método para obter a lista de impressoras do banco de dados
    @Override
    public List<Impressora> getLista() {
        try {
            // Cria uma lista para armazenar as impressoras recuperadas do banco de dados
            List<Impressora> impressoras = new ArrayList<>();

            // Comando SQL para selecionar todos os registros da tabela impressora e equipamento
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM impressora INNER JOIN equipamento ON pk_equipamento = fk_equipamento");
            
            // Executa a consulta e obtém o resultado
            ResultSet rs = stmt.executeQuery();

            // Itera sobre cada registro retornado pelo ResultSet
            while (rs.next()) {
                // Cria um novo objeto Impressora e define seus atributos com base nos dados do banco de dados
                Impressora impressora = new Impressora();
                impressora.setPk_equipamento(rs.getInt("Pk_equipamento"));
                impressora.setTipo(rs.getString("tipo"));
                impressora.setModelo(rs.getString("modelo"));
                impressora.setPk_impressora(rs.getInt("pk_impressora")); // ID da impressora
                impressora.setRevisao(rs.getString("revisao"));

                // Adiciona a impressora à lista de impressoras
                impressoras.add(impressora);
            }
            rs.close(); // Fecha o ResultSet
            stmt.close(); // Fecha o PreparedStatement
            return impressoras; // Retorna a lista de impressoras
        } catch (SQLException e) {
            // Lança uma exceção em caso de erro na execução do SQL
            throw new RuntimeException(e);
        }
    }
    
    // Método para listar todas as impressoras
    @Override
    public void listar() {
        System.out.println("------------ LISTAS COMPLETAS DE IMPRESSORAS ------------");
        // Obtém a lista de impressoras do banco de dados
        List<Impressora> impressoras = this.getLista();
        
        // Itera sobre cada impressora e imprime seus detalhes
        for (Impressora i : impressoras) {
            System.out.println("ID de equipamento: " + i.getPk_equipamento());
            System.out.println("ID de impressora: " + i.getPk_impressora());
            System.out.println("Tipo: " + i.getTipo());
            System.out.println("Modelo: " + i.getModelo());
            System.out.println("Revisão: " + i.getRevisao());
            System.out.println("--------------------------------------------------");
        }
    }
    
    // Método para listar uma impressora específica pelo ID
    @Override
    public void listarID(int id) {
        System.out.println("------------ LISTA COMPLETA DE IMPRESSORA " + id + " ------------");
        // Obtém a lista de impressoras do banco de dados
        List<Impressora> impressoras = this.getLista();
        
        // Itera sobre cada impressora e imprime seus detalhes
        boolean inserido = false;
       
        for (Impressora i : impressoras) {
            if(id == i.getPk_impressora()) {
                System.out.println("ID de equipamento: " + i.getPk_equipamento());
                System.out.println("ID de impressora: " + i.getPk_impressora());
                System.out.println("Tipo: " + i.getTipo());
                System.out.println("Modelo: " + i.getModelo());
                System.out.println("Revisão: " + i.getRevisao()); 
                System.out.println("--------------------------------------------------");
                inserido = true;
                break;
            } 
        }
        if (!inserido) {
            System.out.println("\nNenhuma impressora com o ID " + id + " foi encontrada na base de dados.\n");
        } else {
            System.out.println("\nListagem de impressora com ID " + id + " realizada com sucesso!\n");
        }
    }
    
    // Método para atualizar os detalhes de uma impressora
    @Override
    public void atualizar(Impressora impressora, int id) {
        String sql1 = "UPDATE equipamento SET tipo = ?, modelo = ? WHERE pk_equipamento = ?";
        String sql2 = "UPDATE impressora SET revisao = ? WHERE pk_impressora = ?";
        int cont = 0;
        int cont2 = 0;

        try {
            // Primeiro, seleciona o fk_equipamento associado ao id da impressora
            PreparedStatement stmt = this.connection.prepareStatement("SELECT fk_equipamento FROM impressora WHERE pk_impressora = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int pk_equipamento = rs.getInt("fk_equipamento");

                connection.setAutoCommit(false);

                // Atualiza os dados na tabela equipamento
                PreparedStatement stmt1 = connection.prepareStatement(sql1);
                stmt1.setString(1, impressora.getTipo());
                cont++;
                stmt1.setString(2, impressora.getModelo());
                cont++;
                stmt1.setInt(3, pk_equipamento);

                stmt1.executeUpdate();
                System.out.println("\nColunas afetada na tupla de equipamento " +  pk_equipamento + ": " + cont);
                

                // Atualiza os dados na tabela impressora
                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                stmt2.setString(1, impressora.getRevisao());
                cont2++;
                stmt2.setInt(2, id);

                stmt2.executeUpdate();
                System.out.println("Colunas afetadas na tupla de impressora " +  id + ": " + cont2 + "\n");

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
    
    @Override
    public void atualizarUmAtributo(Impressora impressora, int id) {
            String sql1 = "UPDATE equipamento SET";
            String sql2 = "UPDATE impressora SET";
            boolean updateEquipamento = false;
            boolean updateImpressora = false;

        try {
            // Primeiro, selecione o pk_equipamento associado ao id do computador
            PreparedStatement stmt = this.connection.prepareStatement("SELECT fk_equipamento FROM impressora WHERE pk_impressora = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int pk_equipamento = rs.getInt("fk_equipamento");
                connection.setAutoCommit(false);

                // Construir a consulta SQL para atualizar o equipamento
                if (impressora.getTipo() != null) {
                    sql1 += " tipo = ?,";
                    updateEquipamento = true;
                }
                if (impressora.getModelo() != null) {
                    sql1 += " modelo = ?,";
                    updateEquipamento = true;
                }
                if (updateEquipamento) {
                    sql1 = sql1.endsWith(",") ? sql1.substring(0, sql1.length() - 1) : sql1;
                    sql1 += " WHERE pk_equipamento = ?";
                }

                // Construir a consulta SQL para atualizar o computador
                if (impressora.getRevisao() != null) {
                    sql2 += " revisao = ?,";
                    updateImpressora= true;
                }

                if (updateImpressora) {
                    sql2 = sql2.endsWith(",") ? sql2.substring(0, sql2.length() - 1) : sql2;
                    sql2 += " WHERE pk_impressora = ?";
                }

                // Atualizando os dados do equipamento, se necessário
                if (updateEquipamento) {
                    PreparedStatement stmt1 = connection.prepareStatement(sql1);
                    int index = 1;
                    if (impressora.getTipo() != null) {
                        stmt1.setString(index++, impressora.getTipo());
                    }
                    if (impressora.getModelo() != null) {
                        stmt1.setString(index++, impressora.getModelo());
                    }
                    stmt1.setInt(index++, pk_equipamento);

                    int rows1 = stmt1.executeUpdate();
                    System.out.println("Colunas afetadas na tupla de equipamento: " + rows1 + "\n");
                    stmt1.close();
                }

                // Atualizando os dados de impressora, se necessário
                if (updateImpressora) {
                    PreparedStatement stmt2 = connection.prepareStatement(sql2);
                    int index = 1;
                    if (impressora.getRevisao() != null) {
                        stmt2.setString(index++, impressora.getRevisao());
                    }
                    stmt2.setInt(index++, id);

                    int rows2 = stmt2.executeUpdate();
                    System.out.println("Colunas afetadas na tupla de impressora: " + rows2 + "\n");
                    stmt2.close();
                }

                connection.commit();
            } else {
                System.out.println("\nErro! Nenhuma impressora encontrada com o ID: " + id);
                System.out.println("");
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
    
    // Método para deletar uma impressora e seu equipamento associado
    @Override
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

                // Inicia a transação
                connection.setAutoCommit(false);

                // Executa a exclusão da impressora
                int rows1 = stmt1.executeUpdate();
                System.out.println("Linhas afetadas em impressora: " + rows1);

                // Executa a exclusão do equipamento
                int rows2 = stmt3.executeUpdate();
                System.out.println("Linhas afetadas em equipamento: " + rows2 + "\n");

                // Confirma a transação
                connection.commit();
            } else {
                System.out.println("Nenhuma impressora com ID " + id + " foi encontrada na base de dados.\n");
            }
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
