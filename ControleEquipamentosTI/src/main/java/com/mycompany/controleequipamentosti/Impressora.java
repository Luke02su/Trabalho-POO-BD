package com.mycompany.controleequipamentosti;

public class Impressora extends Equipamento {
    private int pk_impressora;
    private String revisao;
    
    public Impressora() {
        
    }

    public Impressora(int pk_equipamento, String tipo, String modelo, int pk_impressora, String revisao) {
        super(pk_equipamento, tipo, modelo);
        this.pk_impressora = pk_impressora;
        this.revisao = revisao;
    }
 
    public int getPk_impressora() {
        return pk_impressora;
    }

    public void setPk_impressora(int pk_impressora) {
        this.pk_impressora = pk_impressora;
    }

    public String getRevisao() {
        return revisao;
    }

    public void setRevisao(String revisao) {
        this.revisao = revisao;
    }
}
