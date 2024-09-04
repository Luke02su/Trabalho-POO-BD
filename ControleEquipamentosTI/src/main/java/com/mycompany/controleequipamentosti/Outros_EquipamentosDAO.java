package com.mycompany.controleequipamentosti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Outros_EquipamentosDAO implements EquipamentoLojaMetodos<Outros_Equipamentos> {
    
    // A conexão com o banco de dados
    private Connection connection; // Campo para armazenar a conexão com o banco de dados
    
    public Outros_EquipamentosDAO() {
        // Inicializa a conexão com o banco de dados usando a ConnectionFactory
        this.connection = new ConnectionFactory().getConnection();
    }

    // Adiciona um novo registro na tabela de equipamentos e na tabela de outros equipamentos
    @Override
    public void adicionar(Outros_Equipamentos outros) {
        String sql1 = "INSERT INTO equipamento (tipo, modelo) VALUES (?, ?)";
        String sql2 = "INSERT INTO outros_equipamentos (fk_equipamento, descricao) VALUES (?, ?)";

        try {
            // Inicia a transação
            connection.setAutoCommit(false);

            // Insere na tabela equipamento
            PreparedStatement stmt1 = connection.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt1.setString(1, outros.getTipo());
            stmt1.setString(2, outros.getModelo());
            stmt1.executeUpdate();

            // Recupera a chave primária gerada para o equipamento
            ResultSet generatedKeys = stmt1.getGeneratedKeys();
            if (generatedKeys.next()) {
                int equipamentoid = generatedKeys.getInt(1);

                // Define a chave primária no objeto outros
                outros.setPk_equipamento(equipamentoid);

                // Insere na tabela outros_equipamentos usando a chave gerada
                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                stmt2.setInt(1, outros.getPk_equipamento());
                stmt2.setString(2, outros.getDescricao());
                stmt2.executeUpdate();

                stmt2.close();
            }

            // Comita a transação
            connection.commit();
            System.out.println("\nEquipamento adicionado com sucesso!\n"); // Mensagem de sucesso
            stmt1.close();
        } catch (SQLException e) {
            try {
                connection.rollback(); // Reverte em caso de erro
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Erro ao fazer rollback.", rollbackEx);
            }
            throw new RuntimeException("Erro ao adicionar outro equipamento.", e);
        } finally {
            try {
                connection.setAutoCommit(true); // Restaura o auto-commit para true
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao redefinir auto-commit.", ex);
            }
        }
    }

    // Recupera uma lista de todos os outros equipamentos
    @Override
    public List<Outros_Equipamentos> getLista() {
        try {
            // Cria uma lista para armazenar os equipamentos recuperados do banco de dados
            List<Outros_Equipamentos> outros_equip = new ArrayList<Outros_Equipamentos>();

            // Comando SQL para selecionar todos os registros da tabela outros_equipamentos com join na tabela equipamento
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM outros_equipamentos INNER JOIN equipamento ON pk_equipamento = fk_equipamento");
            
            // Executa a consulta e obtém o resultado
            ResultSet rs = stmt.executeQuery();

            // Itera sobre cada registro retornado pelo ResultSet
            while (rs.next()) {
                // Cria um novo objeto Outros_Equipamentos e define seus atributos com base nos dados do banco de dados
                Outros_Equipamentos outros = new Outros_Equipamentos();
                outros.setPk_equipamento(rs.getInt("Pk_equipamento"));
                outros.setTipo(rs.getString("tipo"));
                outros.setModelo(rs.getString("modelo"));
                outros.setPk_outros_equipamentos(rs.getInt("pk_outros_equipamentos")); // ID do outro equipamento
                outros.setDescricao(rs.getString("descricao"));

                // Adiciona o equipamento à lista de equipamentos
                outros_equip.add(outros);
            }
            rs.close(); // Fecha o ResultSet
            stmt.close(); // Fecha o PreparedStatement
            return outros_equip; // Retorna a lista de equipamentos
        } catch (SQLException e) {
            // Lança uma exceção em caso de erro na execução do SQL
            throw new RuntimeException(e);
        }
    }
    
    // Lista todos os outros equipamentos com detalhes
    public void listar() {
        System.out.println("------------ LISTAS COMPLETAS DE OUTROS EQUIPAMENTOS ------------");
        // Obtém a lista de outros equipamentos do banco de dados
        List<Outros_Equipamentos> outros_equip = this.getLista();
        
        // Itera sobre cada equipamento e imprime seus detalhes
        for (Outros_Equipamentos o : outros_equip) {
            System.out.println("ID de equipamento: " + o.getPk_equipamento()); // Imprime o ID do equipamento
            System.out.println("ID de equipamento genérico: " + o.getPk_outros_equipamentos()); // Imprime o ID do outro equipamento
            System.out.println("Tipo: " + o.getTipo());
            System.out.println("Modelo: " + o.getModelo());
            System.out.println("Descrição: " + o.getDescricao());
            System.out.println("----------------------------------");
        }
    }
    
    // Lista um equipamento específico baseado no ID
    @Override
    public void listarID(int id) {
        System.out.println("------------ LISTA COMPLETA DE OUTRO EQUIPAMENTO " + id + " ------------");
        // Obtém a lista de outros equipamentos do banco de dados
        List<Outros_Equipamentos> outros_equip = this.getLista();
        
        // Itera sobre cada equipamento e imprime seus detalhes
        boolean inserido = false;
       
        for (Outros_Equipamentos o : outros_equip) {
            if(id == o.getPk_outros_equipamentos()) {
                System.out.println("ID de equipamento: " + o.getPk_equipamento()); // Imprime o ID do equipamento
                System.out.println("ID de equipamento genérico: " + o.getPk_outros_equipamentos()); // Imprime o ID do outro equipamento
                System.out.println("Tipo: " + o.getTipo());
                System.out.println("Modelo: " + o.getModelo());
                System.out.println("Descrição: " + o.getDescricao());
                System.out.println("--------------------------------------------------");
                inserido = true;
                break;
            } 
        }
        if (!inserido) {
            System.out.println("\nSinto muito! Este equipamento genérico não existe.\n");
        }
    }
    
    @Override
    // Atualiza os dados de um equipamento e de um outro equipamento
    public void atualizar(Outros_Equipamentos outros_equip, int id) {
        String sql1 = "UPDATE equipamento SET tipo = ?, modelo = ? WHERE pk_equipamento = ?";
        String sql2 = "UPDATE outros_equipamentos SET descricao = ? WHERE pk_outros_equipamentos = ?";
        int cont = 0;
        int cont2 = 0;

        try {
            // Primeiro, seleciona o fk_equipamento associado ao id do outro equipamento
            PreparedStatement stmt = this.connection.prepareStatement("SELECT fk_equipamento FROM outros_equipamentos WHERE pk_outros_equipamentos = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int fk_equipamento = rs.getInt("fk_equipamento");

                connection.setAutoCommit(false);

                // Atualiza os dados do equipamento
                PreparedStatement stmt1 = connection.prepareStatement(sql1);
                stmt1.setString(1, outros_equip.getTipo());
                cont++;
                stmt1.setString(2, outros_equip.getModelo());
                cont++;
                stmt1.setInt(3, fk_equipamento);

                int rows1 = stmt1.executeUpdate();
                System.out.println("\nColunas afetadas em equipamento: " + cont);

                // Atualiza os dados do outro equipamento
                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                stmt2.setString(1, outros_equip.getDescricao());
                cont2++;
                stmt2.setInt(2, id);

                stmt2.executeUpdate();
                System.out.println("Colunas da linha afetadas em outros equipamentos: " + cont2 + "\n");

                connection.commit();
                
                stmt1.close();
                stmt2.close();
            } else {
                System.out.println("\nNenhum equipamento genérico com ID " + id + " foi encontrado na base de dados.\n");
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
    
   public void atualizarUmAtributo(Outros_Equipamentos outros_equip, int id) {
            String sql1 = "UPDATE equipamento SET";
            String sql2 = "UPDATE outros_equipamentos SET";
            boolean updateEquipamento = false;
            boolean updateOutro = false;
            int cont = 0;
            int cont2 = 0;

        try {

            // Primeiro, selecione o pk_equipamento associado ao id de outros equipamentos
            PreparedStatement stmt = this.connection.prepareStatement("SELECT fk_equipamento FROM outros_equipamentos WHERE pk_outros_equipamentos = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int pk_equipamento = rs.getInt("fk_equipamento");
                connection.setAutoCommit(false);

                // Construir a consulta SQL para atualizar o equipamento
                if (outros_equip.getTipo() != null) {
                    sql1 += " tipo = ?,";
                    updateEquipamento = true;
                }
                if (outros_equip.getModelo() != null) {
                    sql1 += " modelo = ?,";
                    updateEquipamento = true;
                }
                if (updateEquipamento) {
                    sql1 = sql1.endsWith(",") ? sql1.substring(0, sql1.length() - 1) : sql2; // Remove vírgula extra se houver
                    sql1 += " WHERE pk_equipamento = ?";
                }

                // Construir a consulta SQL para atualizar outro equipamento
                if (outros_equip.getDescricao() != null) {
                    sql2 += " descricao = ?,";
                    updateOutro= true;
                }

                if (updateOutro) {
                    sql2 = sql2.endsWith(",") ? sql2.substring(0, sql2.length() - 1) : sql2; // Remove vírgula extra se houver
                    sql2 += " WHERE pk_outros_equipamentos = ?";
                }

                // Atualizando os dados do equipamento, se necessário
                if (updateEquipamento) {
                    PreparedStatement stmt1 = connection.prepareStatement(sql1);
                    int index = 1;
                    if (outros_equip.getTipo() != null) {
                        stmt1.setString(index++, outros_equip.getTipo());
                    }
                    if (outros_equip.getModelo() != null) {
                        stmt1.setString(index++, outros_equip.getModelo());
                    }
                    stmt1.setInt(index++, pk_equipamento);

                    int rows1 = stmt1.executeUpdate();
                    System.out.println("Coluna da linha afetada em equipamento " + id + ": " + rows1 + "\n");
                    stmt1.close();
                }

                // Atualizando os dados de outro equipamento, se necessário
                if (updateOutro) {
                    PreparedStatement stmt2 = connection.prepareStatement(sql2);
                    int index = 1;
                    if (outros_equip.getDescricao() != null) {
                        stmt2.setString(index++, outros_equip.getDescricao());
                    }
                    stmt2.setInt(index++, id);

                    int rows2 = stmt2.executeUpdate();
                    System.out.println("Colunas afetadas em equipamento genérico " + id + ": " + rows2 + "\n");
                    stmt2.close();
                }

                connection.commit();
            } else {
                System.out.println("\nNenhum equipamento genérico com ID " + id + " foi encontrado na base de dados.\n");
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
        
    // Deleta um equipamento e o seu correspondente na tabela de equipamentos
    @Override
    public void deletar(int id) {
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        ResultSet rs = null;

        try {
            // Inicializa as instruções SQL
            stmt1 = connection.prepareStatement("DELETE FROM outros_equipamentos WHERE pk_outros_equipamentos = ?");
            stmt2 = connection.prepareStatement("SELECT fk_equipamento FROM outros_equipamentos WHERE pk_outros_equipamentos = ?");
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

                // Executa a exclusão do outro equipamento
                int rows1 = stmt1.executeUpdate();
                System.out.println("Linhas afetadas em outros equipamentos: " + rows1);

                // Executa a exclusão do equipamento
                int rows2 = stmt3.executeUpdate();
                System.out.println("Linhas afetadas em equipamentos: " + rows2 + "\n");

                // Confirma a transação
                connection.commit();
                System.out.println("\nEquipamento genérico com ID: " + id + " foi deletado da base de dados.\n");
            } else {
                System.out.println("\nNenhum equipamento genérico com o ID: " + id + " foi encontrado na base de dados.\n");
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
