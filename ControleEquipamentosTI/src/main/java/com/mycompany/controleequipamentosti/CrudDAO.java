package com.mycompany.controleequipamentosti;

import java.util.List;

// Interface genérica para as operações CRUD
public interface CrudDAO<X> {

    // Método para adicionar um objeto ao banco de dados
    void adicionar(X objeto);

    // Método para obter uma lista de todos os objetos do banco de dados
    List<X> getLista();

    // Método para listar todos os objetos no console
    void listar();

    // Método para listar um objeto específico pelo seu ID
    void listarID(int id);

    // Método para atualizar um objeto no banco de dados

    default void atualizar(X objeto, int id) {
        System.out.println("Método diferente em EnvioEquipamentoDAO, em que se´passa três parâmetros. Por favor, implemente esse método nas demias classes DAO.");
    }

    // Método para deletar um objeto do banco de dados pelo seu ID
    void deletar(int id);
}
