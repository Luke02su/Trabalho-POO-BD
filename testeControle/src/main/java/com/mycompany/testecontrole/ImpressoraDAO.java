package com.mycompany.testecontrole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpressoraDAO {
    
// A conexão com o banco de dados
    private Connection connection; // Campo para armazenar a conexão com o banco de dados

    // Construtor da classe ContatoDAO
    public ImpressoraDAO() {
        // Inicializa a conexão com o banco de dados usando a ConnectionFactory
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adicionar(Impressora impressora) {
        // Comando SQL para inserir um novo contato na tabela contatos
        String sql1 = "INSERT INTO equipamento " + "(tipo, modelo)" + " VALUES (?, ?)";
        String sql2 = "INSERT INTO impressora " + "(fk_equipamento, revisao)" + " VALUES (?, ?)";

        try {
            // Cria um PreparedStatement para executar o comando SQL
            PreparedStatement stmt1 = connection.prepareStatement(sql1);
            PreparedStatement stmt2 = connection.prepareStatement(sql2);
            
            stmt1.setString(1, impressora.getTipo());
            stmt1.setString(2, impressora.getModelo());

            // Define os valores dos parâmetros na instrução SQL
            stmt2.setInt(1, impressora.getPk_equipamento());
            stmt2.setString(2, impressora.getRevisao()); // Email do contato
    
            // Executa o comando SQL
            // O método execute retorna true se a execução gerar um ResultSet (não é o caso aqui), caso contrário retorna false
            if (!stmt1.execute() && !stmt2.execute()) {
                System.out.println("Contato adicionado!"); // Mensagem de sucesso
            } else {
                System.out.println("Erro na gravação!"); // Mensagem de erro
            }
            stmt1.close(); // Fecha o PreparedStatement
            stmt2.close(); 
        } catch (SQLException e) {
            // Lança uma exceção em caso de erro na execução do SQL
            throw new RuntimeException(e);
        }
    }

    public List<Computador> getLista() {
        try {
            // Cria uma lista para armazenar os contatos recuperados do banco de dados
            List<Computador> contatos = new ArrayList<Computador>();

            // Comando SQL para selecionar todos os registros da tabela contatos
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM computador");
            
            // Executa a consulta e obtém o resultado
            ResultSet rs = stmt.executeQuery();

            // Itera sobre cada registro retornado pelo ResultSet
            while (rs.next()) {
                // Cria um novo objeto Contato e define seus atributos com base nos dados do banco de dados
                Computador computador = new Computador();
                Equipamento equipamento = new Computador();
                equipamento.setPk_equipamento(rs.getInt("Pk_equipamento"));
                computador.setModelo(rs.getString("modelo"));
                computador.setPk_computador(rs.getInt("pk_computador")); // ID do contato
                computador.setProcessador(rs.getString("processador")); // Nome do contato
                computador.setArmazenamento(rs.getString("armazenamento")); // Email do contato
                computador.setFormatacao(rs.getString("formatacao")); // Endereço do contato
                computador.setManutencao(rs.getString("manutencao")); // Endereço do contato

                // Adiciona o contato à lista de contatos
                contatos.add(computador);
            }
            rs.close(); // Fecha o ResultSet
            stmt.close(); // Fecha o PreparedStatement
            return contatos; // Retorna a lista de contatos
        } catch (SQLException e) {
            // Lança uma exceção em caso de erro na execução do SQL
            throw new RuntimeException(e);
        }
    }
    
    public void listar() {
        System.out.println("------------ Listagem ------------");
        // Obtém a lista de contatos do banco de dados
        List<Computador> computador = this.getLista();
        
        // Itera sobre cada contato e imprime seus detalhes
        for (Computador c : computador) {
            System.out.println("fk_equipamento: " + c.getPk_equipamento()); // Imprime o nome do contato
            System.out.println("pk_computador: " + c.getPk_computador()); // Imprime o email do contato
            System.out.println("processador: " + c.getProcessador()); // Imprime o endereço do contato
            System.out.println("memoria: " + c.getMemoria()); // Imprime o endereço do contato
            System.out.println("windows: " + c.getWindows());
            System.out.println("armazenamento " + c.getArmazenamento());
            System.out.println("formatacao " + c.getFormatacao());
            System.out.println("manutencao: " + c.getManutencao());
            System.out.println("----------------------------------");
        }
    }

}