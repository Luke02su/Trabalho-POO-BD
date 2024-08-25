package com.mycompany.controleequipamentosti;

public class Outros_Equipamentos extends Equipamento {
    private int pk_outros_equipamentos;
    private String descricao;
    
    public Outros_Equipamentos () {
        
    }

    public Outros_Equipamentos(int fk_equipamento, int pk_outros_equipamentos, String descricao, int pk_equipamento, String tipo, String modelo) {
        super(pk_equipamento, tipo, modelo);
        this.pk_outros_equipamentos = pk_outros_equipamentos;
        this.descricao = descricao;
    }

    public int getPk_outros_equipamentos() {
        return pk_outros_equipamentos;
    }

    public void setPk_outros_equipamentos(int pk_outros_equipamentos) {
        this.pk_outros_equipamentos = pk_outros_equipamentos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}