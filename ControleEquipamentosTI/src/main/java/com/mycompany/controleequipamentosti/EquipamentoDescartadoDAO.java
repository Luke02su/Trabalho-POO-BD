package com.mycompany.controleequipamentosti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoDescartadoDAO implements DescarteMetodos<EquipamentoDescartado>{

    // A conexão com o banco de dados
    private Connection connection; // Campo para armazenar a conexão com o banco de dados

    // Construtor da classe ComputadorDAO
    public EquipamentoDescartadoDAO() {
        // Inicializa a conexão com o banco de dados usando a ConnectionFactory
        this.connection = new ConnectionFactory().getConnection();
    }
    
    // Método para obter uma lista de todos os computadores no banco de dados
    @Override
    public List<EquipamentoDescartado> getLista() {
        try {
            // Cria uma lista para armazenar os computadores recuperados do banco de dados
            List<EquipamentoDescartado> descartados = new ArrayList<EquipamentoDescartado>();

            // Comando SQL para selecionar todos os registros da tabela computador, juntando com equipamento
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM log_equipamentos_descartados");
            
            // Executa a consulta e obtém o resultado
            ResultSet rs = stmt.executeQuery();

            // Itera sobre cada registro retornado pelo ResultSet
            while (rs.next()) {
                // Cria um novo objeto Computador e define seus atributos com base nos dados do banco de dados
                EquipamentoDescartado descartado = new EquipamentoDescartado();
                descartado.setFk_equipamento(rs.getInt("Fk_equipamento"));
                descartado.setTipo(rs.getString("tipo"));
                descartado.setModelo(rs.getString("modelo"));
                descartado.setMotivo(rs.getString("motivo"));
                descartado.setData(rs.getString("data"));
                descartado.setUsuario(rs.getString("usuario"));
                
                // Adiciona o computador à lista de computadores
                descartados.add(descartado);
            }
            rs.close(); // Fecha o ResultSet
            stmt.close(); // Fecha o PreparedStatement
            return descartados; // Retorna a lista de computadores
        } catch (SQLException e) {
            // Lança uma exceção em caso de erro na execução do SQL
            throw new RuntimeException(e);
        }
    }
    
    // Método para listar todos os computadores
    @Override
    public void listar() {
        System.out.println("---------- LISTA COMPLETA DE EQUIPAMENTOS DESCARTADOS ----------");
        // Obtém a lista de computadores do banco de dados
        List<EquipamentoDescartado> descartados = this.getLista();
        
        boolean encontrou = true;
        // Itera sobre cada computador e imprime seus detalhes
        for (EquipamentoDescartado d : descartados) {
            System.out.println("ID de equipamento: " + d.getFk_equipamento());
            System.out.println("Tipo: " + d.getTipo());
            System.out.println("Modelo: " + d.getModelo());
            System.out.println("Motivo: " + d.getMotivo());
            System.out.println("Data: " + d.getData());
            System.out.println("Usuário: " + d.getUsuario());
            System.out.println("--------------------------------------------------");
            encontrou = true;
        }
        if(!encontrou) {
            System.out.println("\nNão há nenhum equipamento descartado a ser mostrado.\n");
        } else if (!encontrou) {
            System.out.println("\nListagem completa de todos equipamentos descartados realizada com sucesso!\n");
        }
    } 
}
   