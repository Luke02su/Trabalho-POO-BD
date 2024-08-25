package com.mycompany.controleequipamentosti;

public class Computador extends Equipamento {
    
    private int pk_computador;
    private String processador;
    private String memoria;
    private String windows;
    private String armazenamento;
    private String formatacao;
    private String manutencao;
    
    public Computador() {
        
    }

    public Computador(int pk_equipamento, String tipo, String modelo, int pk_computador, String processador, String memoria, String windows, String armazenamento, String formatacao, String manutencao) {
        super(pk_equipamento, tipo, modelo);
        this.pk_computador = pk_computador;
        this.processador = processador;
        this.memoria = memoria;
        this.windows = windows;
        this.armazenamento = armazenamento;
        this.formatacao = formatacao;
        this.manutencao = manutencao;
    } 

    public int getPk_computador() {
        return pk_computador;
    }

    public void setPk_computador(int pk_computador) {
        this.pk_computador = pk_computador;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public String getWindows() {
        return windows;
    }

    public void setWindows(String windows) {
        this.windows = windows;
    }

    public String getArmazenamento() {
        return armazenamento;
    }

    public void setArmazenamento(String armazenamento) {
        this.armazenamento = armazenamento;
    }

    public String getFormatacao() {
        return formatacao;
    }

    public void setFormatacao(String formatacao) {
        this.formatacao = formatacao;
    }

    public String getManutencao() {
        return manutencao;
    }

    public void setManutencao(String manutencao) {
        this.manutencao = manutencao;
    }
    
    

    
}