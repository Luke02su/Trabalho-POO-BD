package com.mycompany.controleequipamentosti;

public class EnvioEquipamento {
    private int pk_envio;
    private Equipamento equipamento;
    private Loja loja;
    private String data_envio;
    private String observacao;

    public int getPk_envio() {
        return pk_envio;
    }

    public void setPk_envio(int pk_envio) {
        this.pk_envio = pk_envio;
    }
    
    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public String getData_envio() {
        return data_envio;
    }

    public void setData_envio(String data_envio) {
        this.data_envio = data_envio;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
