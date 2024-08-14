package com.mycompany.testecontrole;

public class Outros_Equipamentos extends Equipamento {
    private int fk_equipamento;
    private int pk_outros_equipamentos;
    private String descricao;
    
    public Outros_Equipamentos () {
        
    }

    public Outros_Equipamentos(int fk_equipamento, int pk_outros_equipamentos, String descricao, int pk_equipamento, String tipo, String modelo) {
        super(pk_equipamento, tipo, modelo);
        this.fk_equipamento = fk_equipamento;
        this.pk_outros_equipamentos = pk_outros_equipamentos;
        this.descricao = descricao;
    }
   
    public int getFk_equipamento() {
        return fk_equipamento;
    }

    public void setFk_equipamento(int fk_equipamento) {
        this.fk_equipamento = fk_equipamento;
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
