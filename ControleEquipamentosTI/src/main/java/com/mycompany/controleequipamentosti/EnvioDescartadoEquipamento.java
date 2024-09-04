package com.mycompany.controleequipamentosti;

public class EnvioDescartadoEquipamento {
    private int pk_descarte;
    private int fk_equipamento;
    private int fk_loja;
    private String motivo;
    private String data;
    private String usuario;
    
    public EnvioDescartadoEquipamento() {
        
    }

    public int getPk_descarte() {
        return pk_descarte;
    }

    public void setPk_descarte(int pk_descarte) {
        this.pk_descarte = pk_descarte;
    }

    public int getFk_equipamento() {
        return fk_equipamento;
    }

    public void setFk_equipamento(int fk_equipamento) {
        this.fk_equipamento = fk_equipamento;
    }

    public int getFk_loja() {
        return fk_loja;
    }

    public void setFk_loja(int fk_loja) {
        this.fk_loja = fk_loja;
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
