package com.mycompany.controleequipamentosti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnvioDescartadoEquipamentoDAO {

    // A conexão com o banco de dados
    private Connection connection; // Campo para armazenar a conexão com o banco de dados

    // Construtor da classe ComputadorDAO
    public EnvioDescartadoEquipamentoDAO() {
        // Inicializa a conexão com o banco de dados usando a ConnectionFactory
        this.connection = new ConnectionFactory().getConnection();
    }
    
        // Método para obter uma lista de todos os computadores no banco de dados
    public List<EnvioDescartadoEquipamento> getLista() {
        try {
            // Cria uma lista para armazenar os computadores recuperados do banco de dados
            List<EnvioDescartadoEquipamento> enviosDescartados = new ArrayList<EnvioDescartadoEquipamento>();

            // Comando SQL para selecionar todos os registros da tabela computador, juntando com equipamento
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM log_envios_descartados_equipamentos");
            
            // Executa a consulta e obtém o resultado
            ResultSet rs = stmt.executeQuery();

            // Itera sobre cada registro retornado pelo ResultSet
            while (rs.next()) {
                EnvioDescartadoEquipamento descartado = new EnvioDescartadoEquipamento();
                descartado.setPk_descarte(rs.getInt("pk_descarte"));
                descartado.setFk_equipamento(rs.getInt("fk_equipamento"));
                descartado.setFk_equipamento(rs.getInt("fk_equipamento"));
                descartado.setFk_loja(rs.getInt("fk_loja"));
                descartado.setMotivo(rs.getString("motivo"));
                descartado.setData(rs.getString("data"));
                descartado.setUsuario(rs.getString("usuario"));
                
                // Adiciona o computador à lista de envios descartados
                enviosDescartados.add(descartado);
            }
            rs.close(); // Fecha o ResultSet
            stmt.close(); // Fecha o PreparedStatement
            return enviosDescartados; // Retorna a lista de computadores
        } catch (SQLException e) {
            // Lança uma exceção em caso de erro na execução do SQL
            throw new RuntimeException(e);
        }
    }
    
    // Método para listar todos os computadores
    public void listar() {
        System.out.println("---------- LISTA COMPLETA DE ENVIOS DESCARTADOS DE EQUIPAMENTOS ----------");
        // Obtém a lista de computadores do banco de dados
        List<EnvioDescartadoEquipamento> descartados = this.getLista();
        
        boolean encontrou = false;
        // Itera sobre cada computador e imprime seus detalhes
        for (EnvioDescartadoEquipamento d : descartados) {
            System.out.println("ID de descarte: " + d.getPk_descarte());
            System.out.println("ID de equipamento: " + d.getFk_equipamento());
            System.out.println("ID de loja: " + d.getFk_loja());
            System.out.println("Motivo: " + d.getMotivo());
            System.out.println("Data: " + d.getData());
            System.out.println("Usuário: " + d.getUsuario());
            System.out.println("--------------------------------------------------");
            encontrou = true;
        }
        if (encontrou) {
            System.out.println("\nListagem completa de todos envios descartados de equipamentos realizados com sucesso!\n");
        } else if (!encontrou) {
            System.out.println("\nNão há nenhum envio descartado de equipamento a ser mostrado.\n");
        }
    }
}
   