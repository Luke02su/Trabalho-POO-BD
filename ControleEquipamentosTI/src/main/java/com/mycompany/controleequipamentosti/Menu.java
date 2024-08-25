package com.mycompany.controleequipamentosti;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
  
        ComputadorDAO daoC = new ComputadorDAO();
        ImpressoraDAO daoI = new ImpressoraDAO();
        Outros_EquipamentosDAO daoO = new Outros_EquipamentosDAO();
        LojaDAO daoL = new LojaDAO();
        EnvioEquipamentoDAO daoE = new EnvioEquipamentoDAO();
        
        int opcao = 0;
        
        do {
            System.out.println("--- MENU DE ENTIDADES ---");
            System.out.println("Escolha uma opção:");
            System.out.println("1. CRUD de Computador.");
            System.out.println("2. CRUD de Impressora.");
            System.out.println("3. CRUD de Outro Equipamento.");
            System.out.println("4. CRUD de Loja.");
            System.out.println("5. CRUD de Envio de Equipamento.");
            System.out.println("6. Sair do programa.");
            
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
                        daoC.listarID(opcao);
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
                } case 4: {
                    
                    System.out.println("--- Menu do CRUD de Loja ---");
                    System.out.println("Escolha uma opção:");
                    System.out.println("1. Criar dados para uma loja.");
                    System.out.println("2. Ler dados de todas lojas.");
                    System.out.println("3. Ler dados de uma loja.");
                    System.out.println("4. Atualizar dados de uma loja.");
                    System.out.println("5. Deletar dados de uma loja.");
                    
                    System.out.print("\nEscolha: ");
                    opcao = (scan.nextInt());
                    System.out.println("");
                    
                    if (opcao == 1) {
                        Loja loja = new Loja();
                        
                        scan.nextLine();
                        
                        System.out.print("Digite o CNPJ: ");
                        String cnpj = scan.nextLine();
                        loja.setCnpj(cnpj);
                        
                        System.out.print("Digite o nome do gerente: ");
                        String gerente = scan.nextLine();
                        loja.setGerente(gerente);

                        System.out.print("Digite a cidade: ");
                        String cidade = scan.nextLine();
                        loja.setCidade(cidade);

                        System.out.print("Digite o telefone: ");
                        String telefone = scan.nextLine();
                        loja.setTelefone(telefone);
                        
                        daoL.adicionar(loja);
                    } else if (opcao == 2) {
                        daoL.listar();
                    } else if (opcao == 3) {
                        System.out.print("Digite o ID de uma loja a ser lido: ");
                        opcao = (scan.nextInt());
                        daoL.listarID(opcao);
                    } else if (opcao == 4) {
                        
                        Loja loja = new Loja();
                        
                        System.out.print("Digite o ID de uma loja a ser atualizado: ");
                        opcao = (scan.nextInt());
                        
                        scan.nextLine();
                        
                        System.out.print("Digite o CNPJ: ");
                        String cnpj = scan.nextLine();
                        loja.setCnpj(cnpj);
                        
                        System.out.print("Digite o nome do gerente: ");
                        String gerente = scan.nextLine();
                        loja.setGerente(gerente);

                        System.out.print("Digite a cidade: ");
                        String cidade = scan.nextLine();
                        loja.setCidade(cidade);

                        System.out.print("Digite o telefone: ");
                        String telefone = scan.nextLine();
                        loja.setTelefone(telefone);
                        
                        daoL.atualizar(loja, opcao);
                    } else if (opcao == 5) {
                        System.out.print("Digite o ID de uma loja a ser deletado: ");
                        opcao = (scan.nextInt());
                        daoL.deletar(opcao);
                    }
                    break;
                } case 5: {
                    System.out.println("--- Menu do CRUD de Envio de Equipamento ---");
                    System.out.println("Escolha uma opção:");
                    System.out.println("1. Criar dados para um envio de equipamento.");
                    System.out.println("2. Ler dados de todos envios de equipamentos.");
                    System.out.println("3. Ler dados de um envio de equipamento.");
                    System.out.println("4. Atualizar dados de um envio de equipamento.");
                    System.out.println("5. Deletar dados de um envio de equipamento.");
                    
                    System.out.print("\nEscolha: ");
                    opcao = (scan.nextInt());
                    System.out.println("");
                    
                    if (opcao == 1) {
                        
                        EnvioEquipamento envio = new EnvioEquipamento();
                        Equipamento equipamento = new Computador();
                        Loja loja = new Loja();
                        
                        scan.nextLine();
                        
                        System.out.println("Digite os dados de envio de equipamento a serem cadastrados: ");
                        
                        System.out.print("Digite o ID do equipamento: ");
                        int pk_equipamento = (scan.nextInt());
                        equipamento.setPk_equipamento(pk_equipamento);
                        envio.setEquipamento(equipamento);
                        
                        System.out.print("Digite o ID da loja: ");
                        int pk_loja = (scan.nextInt());
                        loja.setPk_loja(pk_loja);
                        envio.setLoja(loja);
                        
                        scan.nextLine();
                        
                        System.out.print("Data de envio: ");
                        String data = (scan.nextLine());
                        envio.setData_envio(data);
                        
                        System.out.print("Observação: ");
                        String observacao = (scan.nextLine());
                        envio.setObservacao(observacao);

                        daoE.adicionar(envio);
                        
                    } else if (opcao == 2) {
                        daoE.listar();
                    } else if (opcao == 3) {
                        System.out.print("Digite o ID do envio de equipamento: ");
                        opcao = (scan.nextInt());
                        daoE.listarID(opcao);
                    } else if (opcao == 4) {
                        EnvioEquipamento envio = new EnvioEquipamento();
                        Equipamento equipamento = new Computador();
                        Loja loja = new Loja();
                        
                        
                        System.out.print("Digite o ID de equipamento para envio a ser atualizado: ");
                        int fk_equipamento = scan.nextInt();
                        
                        System.out.print("Digite o ID de loja para envio a ser atualizado: ");
                        int fk_loja = scan.nextInt();
                        
                        scan.nextLine(); // Limpa o buffer
                        
                        System.out.print("Digite uma nova data de envio: ");
                        String data = (scan.nextLine());
                        envio.setData_envio(data);
                        
                        System.out.print("Digite uma nova observação: ");
                        String observacao = (scan.nextLine());
                        envio.setObservacao(observacao);
                        
       
                        daoE.atualizar(envio, fk_equipamento, fk_loja);
                    } else if (opcao == 5) {
                        
                    }
                
                    break;
                } case 6: {
                        System.out.println("");
                        break;
                    } default: {
                        System.out.println("Opção inválida. Digite uma opção válida.\n");
                        System.exit(0);
                    }
                } 
        } while (opcao != 6);
    }
}