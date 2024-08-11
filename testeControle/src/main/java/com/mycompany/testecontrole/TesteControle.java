package com.mycompany.testecontrole;
import java.util.Scanner;

public class TesteControle {

    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        int opcao = 0;
        ComputadorDAO daoC = new ComputadorDAO();
        ImpressoraDAO daoI = new ImpressoraDAO();
        
        do {
            System.out.println("--- MENU DE OPÇÕES ---");
            System.out.println("Escolha uma opção: ");
            System.out.println("1. CRUD de Computador.");
            System.out.println("2. CRUD de Impressora.");
            
            System.out.print("\nEscolha: ");
            opcao = (scan.nextInt());
            System.out.println("");
            
            switch(opcao) {
                case 1: {
                    System.out.println("--- MENU do CRUD de Computador ---");
                    System.out.println("1. Criar dados para um computador.");
                    System.out.println("2. Ler dados de todos os computadores.");
                    System.out.println("3. Ler dados de um computador.");
                    System.out.println("4. Atualizar dados de um computador.");
                    System.out.println("5. Atualizar dados de um computador.");
                    
                    System.out.print("\nEscolha: ");
                    opcao = (scan.nextInt());
                    System.out.println("");
                    
                    if (opcao == 1) {
                        System.out.print("Digite os dados do computador a serem cadastrados: ");
                        
                        System.out.print("Digite a chave estrangeira de equipamento: ");
                        int id_equipamento = (scan.nextInt());
                        scan.nextLine();
                        
                        Computador computador = new Computador();
                        computador.setPk_equipamento(id_equipamento);
                        
                        System.out.print("Digite o tipo de computador: ");
                        String tipo = (scan.nextLine());
                       
                        computador.setTipo(tipo);
                        System.out.print("Digite o modelo: ");
                        String modelo = (scan.nextLine());
                       
                        
                        computador.setModelo(modelo);
                        System.out.print("Digite o processador: ");
                        String processador = (scan.nextLine());
                        
                        
                        computador.setProcessador(processador);
                        System.out.print("Digite a memória RAM: ");
                        String memoria = (scan.nextLine());
                        
                        computador.setMemoria(memoria);
                        System.out.print("Digite a versão do Windows: ");
                        String windows = (scan.nextLine());
                        
                        computador.setWindows(windows);
                        System.out.print("Digite o tipo de armazenamento e seu tamanho: ");
                        String armazenamento = (scan.nextLine());
                        
                        computador.setArmazenamento(armazenamento);
                        System.out.print("Foi formatado? : ");
                        String formatacao = (scan.nextLine());
                        computador.setFormatacao(formatacao);
                        
                        System.out.print("Foi feito manutenção?");
                        String manutencao = (scan.nextLine());
                        computador.setManutencao(manutencao);
                        
                        daoC.adicionar(computador);
                    } else if (opcao == 2) {
                        daoC.listar();
                    } else if (opcao == 3) {
                        System.out.print("Digite o ID do computador a ser lido: ");
                        opcao = (scan.nextInt());
                        daoC.listarIDComputador(opcao);
                    } else if (opcao == 4) {
                            System.out.print("Digite o ID do computador a ser atualizado: ");
                            int id = scan.nextInt();
                            scan.nextLine(); // Limpa o buffer
                            
                            Computador computador = new Computador();

                            // Solicita novos dados para o computador
                            System.out.print("Digite o novo tipo de computador: ");
                            String tipo = scan.nextLine();
                            computador.setTipo(tipo);

                            System.out.print("Digite o novo modelo: ");
                            String modelo = scan.nextLine();
                            computador.setModelo(modelo);

                            System.out.print("Digite o novo processador: ");
                            String processador = scan.nextLine();
                            computador.setProcessador(processador);

                            System.out.print("Digite a nova memória RAM: ");
                            String memoria = scan.nextLine();
                            computador.setMemoria(memoria);

                            System.out.print("Digite a nova versão do Windows: ");
                            String windows = scan.nextLine();
                            computador.setWindows(windows);

                            System.out.print("Digite o novo tipo de armazenamento e seu tamanho: ");
                            String armazenamento = scan.nextLine();
                            computador.setArmazenamento(armazenamento);

                            System.out.print("Foi formatado? ");
                            String formatacao = scan.nextLine();
                            computador.setFormatacao(formatacao);

                            System.out.print("Foi feita manutenção? ");
                            String manutencao = scan.nextLine();
                            computador.setManutencao(manutencao);
                        
                        daoC.atualizar(computador, id);
                    } else if (opcao == 5) {
                        System.out.print("Digite o ID do computador a ser deletado: ");
                        opcao = (scan.nextInt());
                        daoC.deletar(opcao);
                    } else {
                        System.out.println("Opção inválida. Escolha novamente.");
                    }
                    return;
                }
                case 2: {
                    
                    System.out.println("--- MENU do CRUD de Impressora ---");
                    System.out.println("1. Criar dados para uma impressora.");
                    System.out.println("2. Ler dados de todas impressoras.");
                    System.out.println("3. Ler dados de uma impressora.");
                    System.out.println("4. Atualizar dados de uma impressora.");
                    System.out.println("5. Atualizar dados de uma impressora.");
                    
                    System.out.print("\nEscolha: ");
                    opcao = (scan.nextInt());
                    System.out.println("");
                    
                    if(opcao == 1) {
                        System.out.println("Digite os dados de impressora a serem cadastrados: ");
                        
                        System.out.print("Digite a chave estrangeira de impressora: ");
                        int id_equipamento = scan.nextInt();
                        scan.nextLine(); // Consumir a nova linha

                        // Supondo que Impressora tenha um construtor padrão
                        Impressora impressora = new Impressora();

                        impressora.setPk_equipamento(id_equipamento);

                        System.out.print("Digite o tipo: ");
                        String tipo = scan.nextLine();
                        impressora.setTipo(tipo);

                        System.out.print("Digite o modelo: ");
                        String modelo = scan.nextLine();
                        impressora.setModelo(modelo);

                        System.out.print("Foi revisada?: ");
                        String revisao = scan.nextLine();
                        impressora.setRevisao(revisao);

                        // Chama o método adicionar
                        daoI.adicionar(impressora);

                    }
                }
            }
        }while (opcao != 3);
    }
}
