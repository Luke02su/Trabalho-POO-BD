
package com.mycompany.controleequipamentosti;

public abstract class Equipamento {
    private int pk_equipamento;
    private String categoria;
    private String tipo;
    private String modelo;
    
    public Equipamento() {
        
    }

    public Equipamento(int pk_equipamento, String tipo, String modelo) {
        this.pk_equipamento = pk_equipamento;
        this.tipo = tipo;
        this.modelo = modelo;
    }

    public int getPk_equipamento() {
        return pk_equipamento;
    }

    public void setPk_equipamento(int pk_equipamento) {
        this.pk_equipamento = pk_equipamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public final String getCategoria() {
        return categoria;
    }

    public final void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
