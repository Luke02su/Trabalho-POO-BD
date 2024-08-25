package com.mycompany.controleequipamentosti;

public class Loja {
    
    private int pk_loja;
    private String cnpj;
    private String gerente;
    private String cidade;
    private String telefone;
    
    public Loja () {
        
    }

    public Loja(int pk_loja, String cnpj, String gerente, String cidade, String telefone) {
        this.pk_loja = pk_loja;
        this.cnpj = cnpj;
        this.gerente = gerente;
        this.cidade = cidade;
        this.telefone = telefone;
    }
    
  
    public int getPk_loja() {
        return pk_loja;
    }

    public void setPk_loja(int pk_loja) {
        this.pk_loja = pk_loja;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }
    
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
