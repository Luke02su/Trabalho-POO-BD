package com.mycompany.controleequipamentosti;

import java.util.List;

// Interface genérica para as operações CRUD
public interface EquipamentoLojaMetodos<X> {

    // Método para adicionar um objeto ao banco de dados
    void adicionar(X objeto);

    // Método para obter uma lista de todos os objetos do banco de dados
    List<X> getLista();

    // Método para listar todos os objetos no console
    void listar();

    // Método para listar um objeto específico pelo seu ID
    void listarID(int id);
    
    // Método para atualizar todos os atributos de um objeto no banco de dados
    void atualizar(X objeto, int id);
    
    // Método para atualizar um atributo específico por vez de um objeto no banco de dados
    void atualizarUmAtributo(X objeto, int id);

    // Método para deletar um objeto do banco de dados pelo seu ID
    void deletar(int id);
}
