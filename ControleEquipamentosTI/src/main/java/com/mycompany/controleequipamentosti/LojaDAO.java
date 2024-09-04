package com.mycompany.controleequipamentosti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LojaDAO implements EquipamentoLojaMetodos<Loja> {
    
    // A conexão com o banco de dados
    private Connection connection; // Campo para armazenar a conexão com o banco de dados
    
    public LojaDAO() {
        // Inicializa a conexão com o banco de dados usando a ConnectionFactory
        this.connection = new ConnectionFactory().getConnection();
    }

    // Método para adicionar uma nova loja ao banco de dados
    @Override
    public void adicionar(Loja loja) {
        String sql = "INSERT INTO loja (cnpj, cidade, telefone) VALUES (?, ?, ?)";
        
        try {
            // Inicia a transação
            connection.setAutoCommit(false);

            // Prepara a instrução SQL para inserção
            PreparedStatement stmt1 = connection.prepareStatement(sql);
            stmt1.setString(1, loja.getCnpj());
            stmt1.setString(2, loja.getGerente());
            stmt1.setString(3, loja.getCidade());
            stmt1.setString(4, loja.getTelefone());
            stmt1.execute();

            // Confirma a transação
            connection.commit();
            System.out.println("Loja adicionada com sucesso!"); // Mensagem de sucesso
            stmt1.close();
        } catch (SQLException e) {
            try {
                // Desfaz a transação em caso de erro
                connection.rollback(); 
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Erro ao fazer rollback.", rollbackEx);
            }
            throw new RuntimeException("Erro ao adicionar loja.", e);
        } finally {
            try {
                // Redefine o auto-commit para true
                connection.setAutoCommit(true); 
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao redefinir auto-commit.", ex);
            }
        }
    }
    
    // Método para obter uma lista de todas as lojas
    @Override
    public List<Loja> getLista() {
        try {
            // Cria uma lista para armazenar as lojas
            List<Loja> lojas = new ArrayList<Loja>();

            // Comando SQL para selecionar todos os registros da tabela loja
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM loja");
            
            // Executa a consulta e obtém o resultado
            ResultSet rs = stmt.executeQuery();

            // Itera sobre cada registro retornado pelo ResultSet
            while (rs.next()) {
                // Cria um novo objeto Loja e define seus atributos com base nos dados do banco de dados
                Loja loja = new Loja();
                loja.setPk_loja(rs.getInt("Pk_loja"));
                loja.setCnpj(rs.getString("cnpj"));
                loja.setGerente(rs.getString("gerente"));
                loja.setCidade(rs.getString("cidade"));
                loja.setTelefone(rs.getString("telefone"));

                // Adiciona a loja à lista de lojas
                lojas.add(loja);
            }
            rs.close(); // Fecha o ResultSet
            stmt.close(); // Fecha o PreparedStatement
            return lojas; // Retorna a lista de lojas
        } catch (SQLException e) {
            // Lança uma exceção em caso de erro na execução do SQL
            throw new RuntimeException(e);
        }
    }
    
    // Método para listar todas as lojas
    @Override
    public void listar() {
        System.out.println("------------ LISTAS COMPLETAS DE LOJAS ------------");
        // Obtém a lista de lojas do banco de dados
        List<Loja> lojas = this.getLista();
        
        // Itera sobre cada loja e imprime seus detalhes
        for (Loja l : lojas) {
            System.out.println("ID de Loja " + l.getPk_loja()); // Imprime o ID da loja
            System.out.println("CNPJ: " + l.getCnpj());
            System.out.println("Gerente: " + l.getGerente());
            System.out.println("Cidade: " + l.getCidade());
            System.out.println("Telefone: " + l.getTelefone());
            System.out.println("----------------------------------");
        }
    }
    
    // Método para listar uma loja específica pelo ID
    @Override
    public void listarID(int id) {
        System.out.println("------------ LISTA COMPLETA DE LOJA " + id + " ------------");
        // Obtém a lista de lojas do banco de dados
        List<Loja> lojas = this.getLista();
        
        boolean inserido = false;
       
        // Itera sobre cada loja e imprime seus detalhes se o ID corresponder
        for (Loja l : lojas) {
            if(id == l.getPk_loja()) {
                System.out.println("ID de Loja " + l.getPk_loja()); // Imprime o ID da loja
                System.out.println("CNPJ: " + l.getCnpj());
                System.out.println("Gerente: " + l.getGerente());
                System.out.println("Cidade: " + l.getCidade());
                System.out.println("Telefone: " + l.getTelefone());
                System.out.println("----------------------------------");
                inserido = true;
                break;
            } 
        }
        if (!inserido) {
            System.out.println("\nNenhuma loja com o ID " + id + " foi encontrada na base de dados.\n");
        } else {
            System.out.println("\nListagem de loja com ID " + id + " realizada com sucesso!\n");
        }
    }
        
    // Método para atualizar os dados de uma loja
    @Override
    public void atualizar(Loja loja, int id) {
        String sql = "UPDATE loja SET cnpj = ?, gerente = ?, cidade = ?, telefone = ? WHERE pk_loja = ?";

        try {
            // Primeiro, seleciona o ID da loja associado ao id fornecido
            PreparedStatement stmt1 = this.connection.prepareStatement("SELECT pk_loja FROM loja WHERE pk_loja = ?");
            stmt1.setInt(1, id);
            ResultSet rs = stmt1.executeQuery();
            int cont = 0;

            if (rs.next()) {
                connection.setAutoCommit(false);

                // Atualiza os dados da loja
                PreparedStatement stmt2 = connection.prepareStatement(sql);
                stmt2.setString(1, loja.getCnpj());
                cont++;
                stmt2.setString(2, loja.getGerente());
                cont++;
                stmt2.setString(3, loja.getCidade());
                cont++;
                stmt2.setString(4, loja.getTelefone());
                cont++;
                stmt2.setInt(5, id);

                stmt2.executeUpdate();
                System.out.println("Linhas afetadas em loja: " + cont);

                // Confirma a transação
                connection.commit();
                
                stmt1.close();
                stmt2.close();
            } else {
                System.out.println("Nenhuma loja encontrada com o ID: " + id);
            }
            rs.close();
        } catch (SQLException e) {
            try {
                // Desfaz a transação em caso de erro
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                // Redefine o auto-commit para true
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void atualizarUmAtributo(Loja loja, int id) {
        String sql = "UPDATE loja SET";
        boolean updateLoja = false;
        int cont = 0;

        try {
            // Primeiro, selecione o pk_equipamento associado ao id do computador
            PreparedStatement stmt1 = this.connection.prepareStatement("SELECT pk_loja FROM loja WHERE pk_loja = ?");
            stmt1.setInt(1, id);
            ResultSet rs = stmt1.executeQuery();

            if (rs.next()) {
                connection.setAutoCommit(false);
                if (loja.getCnpj() != null) {
                    sql += " cnpj = ?,";
                    updateLoja = true;
                    cont++;
                }
                
                if (loja.getGerente() != null) {
                    sql += " gerente = ?,";
                    updateLoja = true;
                    cont++;
                }
                
                if (loja.getCidade() != null) {
                    sql += " cidade = ?,";
                    updateLoja = true;
                    cont++;
                }
                
                if (loja.getTelefone()!= null) {
                    sql += " telefone = ?,";
                    updateLoja = true;
                    cont++;
                }

                if (updateLoja) {
                    sql = sql.endsWith(",") ? sql.substring(0, sql.length() - 1) : sql;
                    sql += " WHERE pk_loja = ?";
                }

                // Atualizando os dados do computador, se necessário
                if (updateLoja) {
                    PreparedStatement stmt2 = connection.prepareStatement(sql);
                    int index = 1;
                    if (loja.getCnpj() != null) {
                        stmt2.setString(index++, loja.getCnpj());
                    }
                    if (loja.getGerente() != null) {
                        stmt2.setString(index++, loja.getGerente());
                    }
                    if (loja.getCidade() != null) {
                        stmt2.setString(index++, loja.getCidade());
                    }
                    if (loja.getTelefone() != null) {
                        stmt2.setString(index++, loja.getTelefone());
                    }
                    
                    stmt2.setInt(index++, id);

                    stmt2.executeUpdate();
                    System.out.println("Colunas afetadas na tupla de loja: " + cont);
                    stmt2.close();
                }

                connection.commit();
            } else {
                System.out.println("\nErro! Nenhuma loja encontrada com o ID: " + id);
                System.out.println("");
            }

            rs.close();
            stmt1.close();

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
        
    // Método para deletar uma loja pelo ID
    @Override
    public void deletar(int id) {
        
        PreparedStatement stmt = null;
        
        try {
            // Prepara a instrução SQL para exclusão
            stmt = this.connection.prepareStatement("DELETE FROM loja WHERE pk_loja = ?");
            stmt.setInt(1, id);
            
            // Inicia a transação
            connection.setAutoCommit(false);

            // Executa a exclusão da loja
            int rows1 = stmt.executeUpdate();
            if (rows1 == 1) {
                System.out.println("Linhas afetadas em loja: " + rows1);
                connection.commit();
            } else {
                System.out.println("Nenhuma loja encontrada com ID " + id);
            }

            System.out.println("");
        } catch (SQLException e) {
            try {
                // Desfaz a transação em caso de erro
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.setAutoCommit(true); // Restaura o modo de commit automático
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
