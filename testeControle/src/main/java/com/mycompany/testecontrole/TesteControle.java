package com.mycompany.testecontrole;
import java.util.Scanner;

public class TesteControle {

    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
  
        ComputadorDAO daoC = new ComputadorDAO();
        ImpressoraDAO daoI = new ImpressoraDAO();
        Outros_EquipamentosDAO daoO = new Outros_EquipamentosDAO();
        
        int opcao = 0;
        
        do {
            System.out.println("--- MENU DE ENTIDADES ---");
            System.out.println("Escolha uma opção:");
            System.out.println("1. CRUD de Computador.");
            System.out.println("2. CRUD de Impressora.");
            System.out.println("3. CRUD de Outro Equipamento.");
            System.out.println("4. CRUD de Loja.");
            System.out.println("5. CRUD de Envio de Equipamento.");
            
            System.out.print("\nEscolha: ");
            opcao = (scan.nextInt());
            System.out.println("");
            
            switch(opcao) {
                case 1: {
                    System.out.println("--- MENU do CRUD de Computador ---");
                    System.out.println("Escolha uma opção:");
                    System.out.println("1. Criar dados para um computador.");
                    System.out.println("2. Ler dados de todos os computadores.");
                    System.out.println("3. Ler dados de um computador.");
                    System.out.println("4. Atualizar dados de um computador.");
                    System.out.println("5. Deletar dados de um computador.");
                    
                    System.out.print("\nEscolha: ");
                    opcao = (scan.nextInt());
                    System.out.println("");
                    
                    if (opcao == 1) {
                        System.out.println("Digite os dados do computador a serem cadastrados: ");
                        
                        Computador computador = new Computador();
                        
                        scan.nextLine();
                        
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
                break;
                }
                case 2: {
                    
                    System.out.println("--- Menu do CRUD de Impressora ---");
                    System.out.println("Escolha uma opção:");
                    System.out.println("1. Criar dados para uma impressora.");
                    System.out.println("2. Ler dados de todas impressoras.");
                    System.out.println("3. Ler dados de uma impressora.");
                    System.out.println("4. Atualizar dados de uma impressora.");
                    System.out.println("5. Deletar dados de uma impressora.");
                    
                    System.out.print("\nEscolha: ");
                    opcao = (scan.nextInt());
                    System.out.println("");
                    
                    if(opcao == 1) {
                        System.out.println("Digite os dados de impressora a serem cadastrados: ");
                        
                        // Supondo que Impressora tenha um construtor padrão
                        Impressora impressora = new Impressora();
                        
                        scan.nextLine();

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

                    } else if (opcao == 2) {
                        daoI.listar();
                    } else if (opcao == 3) {
                        System.out.print("Digite o ID do computador a ser lido: ");
                        opcao = (scan.nextInt());
                        daoI.listarID(opcao);
                    } else if (opcao == 4) {
                                             
                            System.out.print("Digite o ID da impressora a ser atualizada: ");
                            int id = scan.nextInt();
                            scan.nextLine(); // Limpa o buffer
                            
                            Impressora impressora = new Impressora();

                            // Solicita novos dados para o computador
                            System.out.print("Digite o novo tipo: ");
                            String tipo = scan.nextLine();
                            impressora.setTipo(tipo);

                            System.out.print("Digite o novo modelo: ");
                            String modelo = scan.nextLine();
                            impressora.setModelo(modelo);

                            System.out.print("Revisada? ");
                            String revisao = scan.nextLine();
                            impressora.setRevisao(revisao);
                            
                        daoI.atualizar(impressora, id);
                    } else if (opcao == 5) {
                        System.out.print("Digite o ID da impressora a ser atualizada: ");
                        int id = scan.nextInt();
                        scan.nextLine(); // Limpa o buffer
                            
                        daoI.deletar(id);
                    }
                    break;
                } case 3: {
                    
                    System.out.println("--- Menu do CRUD de Equipamento Genérico ---");
                    System.out.println("Escolha uma opção:");
                    System.out.println("1. Criar dados para um equipamento genérico.");
                    System.out.println("2. Ler dados de todos equipamentos genéricos.");
                    System.out.println("3. Ler dados de um equipamento genérico.");
                    System.out.println("4. Atualizar dados de equipamento genérico.");
                    System.out.println("5. Deletar dados de equipamento genérico.");
                    
                    System.out.print("\nEscolha: ");
                    opcao = (scan.nextInt());
                    System.out.println("");
                    
                    
                if(opcao == 1) {
                        System.out.println("Digite os dados de impressora a serem cadastrados: ");
                        
                        // Supondo que Impressora tenha um construtor padrão
                        Outros_Equipamentos outroEquip = new Outros_Equipamentos();
                        
                        scan.nextLine();

                        System.out.print("Digite o tipo: ");
                        String tipo = scan.nextLine();
                        outroEquip.setTipo(tipo);

                        System.out.print("Digite o modelo: ");
                        String modelo = scan.nextLine();
                        outroEquip.setModelo(modelo);

                        System.out.print("Descrição: ");
                        String descricao = scan.nextLine();
                        outroEquip.setDescricao(descricao);

                        // Chama o método adicionar
                        daoO.adicionar(outroEquip);
                        
                    } else if (opcao == 2) {
                        daoO.listar();
                        
                    } else if (opcao == 3) {
                        System.out.print("Digite o ID do equipamento genérico a ser lido: ");
                        opcao = (scan.nextInt());
                        daoO.listarID(opcao);
                        
                    } else if (opcao == 4) {
                        
                            Outros_Equipamentos outroEquip = new Outros_Equipamentos();
                            
                            scan.nextLine();
                        
                            System.out.print("Digite o ID da impressora a ser atualizada: ");
                            int id = scan.nextInt();
                            scan.nextLine(); // Limpa o buffer
                            
                            System.out.print("Digite o novo tipo: ");
                            String tipo = scan.nextLine();
                            outroEquip.setTipo(tipo);

                            System.out.print("Digite o novo modelo: ");
                            String modelo = scan.nextLine();
                            outroEquip.setModelo(modelo);

                            System.out.print("Revisada? ");
                            String descricao = scan.nextLine();
                            outroEquip.setDescricao(descricao);
                            
                            daoO.atualizar(outroEquip, id);
                            
                    } else if (opcao == 5) {
                        System.out.print("Digite o ID do equipamento genérico a ser deletado: ");
                        opcao = (scan.nextInt());
                        daoO.deletar(opcao);
                    }
                    break;
                } default: {
                    System.out.println("Opção inválida. Digite uma opção válida.\n");
                }
            } 
        } while (opcao != 3);
    }
}
