package com.mycompany.controleequipamentosti;

public class EquipamentoDescartado {
    private int fk_equipamento;
    private String tipo;
    private String modelo;
    private String motivo;
    private String data;
    private String usuario;
    
    public EquipamentoDescartado() {
        
        
    }

    public EquipamentoDescartado(int pk_equipamento, String tipo, String modelo, String motivo, String data, String usuario) {
        this.fk_equipamento = pk_equipamento;
        this.tipo = tipo;
        this.modelo = modelo;
        this.motivo = motivo;
        this.data = data;
        this.usuario = usuario;
    }
    
    public int getFk_equipamento() {
        return fk_equipamento;
    }

    public void setFk_equipamento(int fk_equipamento) {
        this.fk_equipamento = fk_equipamento;
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
}
