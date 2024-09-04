package com.mycompany.controleequipamentosti;

import java.util.List;

// Interface genérica para as operações READ
public interface DescarteMetodos<X> {

    // Método para obter uma lista de todos os objetos do banco de dados.
    List<X> getLista();

    // Método para listar todos os objetos da lista.
    void listar();
}
