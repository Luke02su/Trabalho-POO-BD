package com.mycompany.controleequipamentosti;
import java.util.Scanner;

public class Menu {  

    public static void main(String[] args) {
        
        // Permitinfo entrada do usuário.
        Scanner scan = new Scanner(System.in);
        
        //Cruaando objetos.
        Computador computador = new Computador();
        Impressora impressora = new Impressora();
        Outros_Equipamentos outroEquip = new Outros_Equipamentos();
        Loja loja = new Loja();
        EnvioEquipamento envio = new EnvioEquipamento();
        Equipamento equipamento = new Computador(); //Precisa-se apenas de tipo e modelo em envio, poderia ser instanciado Impressora ou Outros Computadores.
        
        //Criando objetos dos DAOs.
        ComputadorDAO daoC = new ComputadorDAO();
        ImpressoraDAO daoI = new ImpressoraDAO();
        Outros_EquipamentosDAO daoO = new Outros_EquipamentosDAO();
        LojaDAO daoL = new LojaDAO();
        EnvioEquipamentoDAO daoE = new EnvioEquipamentoDAO();
        EquipamentoDescartadoDAO daoED = new EquipamentoDescartadoDAO();
        EnvioDescartadoEquipamentoDAO daoEED = new EnvioDescartadoEquipamentoDAO();
        
        int opcao = 0;
        
        // Laço de repetição que roda no mínimo 1 vez.
        do {
            System.out.println("------ MENU DE ENTIDADES ------");
            System.out.println("Escolha uma opção:");
            System.out.println("1. CRUD de Computador.");
            System.out.println("2. CRUD de Impressora.");
            System.out.println("3. CRUD de Equipamento Genérico.");
            System.out.println("4. CRUD de Loja.");
            System.out.println("5. CRUD de Envio de Equipamento.");
            System.out.println("6. Listar todos equipamentos descartados.");
            System.out.println("7. Listar todos envios de equipamentos descartados.");
            System.out.println("8. Sair do programa.");
            
            System.out.print("\nEscolha: ");
            opcao = (scan.nextInt());
            System.out.println("");
            
            // Switch para escolher.
            switch(opcao) {
                case 1: {
                    do {
                        System.out.println("------ MENU do CRUD de Computador ------");
                        System.out.println("Escolha uma opção:");
                        System.out.println("1. Criar dados para um computador.");
                        System.out.println("2. Ler dados de todos os computadores.");
                        System.out.println("3. Ler dados de um computador.");
                        System.out.println("4. Atualizar dados de um computador.");
                        System.out.println("5. Deletar dados de um computador.");
                        System.out.println("6. Retornar ao menu principal.");

                        System.out.print("\nEscolha: ");
                        opcao = scan.nextInt();
                        System.out.println("");

                        switch(opcao) {
                            case 1: {
                                System.out.println("Digite os dados do computador a serem cadastrados:\n");

                                scan.nextLine();
                                System.out.print("Digite o tipo: ");
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
                                System.out.print("Foi formatado? ");
                                String formatacao = (scan.nextLine());
                                computador.setFormatacao(formatacao);

                                System.out.print("Foi feita manutenção? ");
                                String manutencao = (scan.nextLine());
                                computador.setManutencao(manutencao);

                                daoC.adicionar(computador);
                                break;
                            } case 2: {
                                daoC.listar();
                                break;
                            } case 3: {
                                System.out.print("Digite o ID do computador a ser lido: ");
                                opcao = (scan.nextInt());
                                daoC.listarID(opcao);
                                break;
                            } case 4: {
                                System.out.print("Digite o ID do computador a ser atualizado (verifique se existe): ");
                                int id = scan.nextInt();

                                System.out.println("\n1. Atualizar tipo.");
                                System.out.println("2. Atualizar modelo.");
                                System.out.println("3. Atualizar´processador.");
                                System.out.println("4. Atualizar memoria.");
                                System.out.println("5. Atualizar Windows.");
                                System.out.println("6. Atualizar armazenamento.");
                                System.out.println("7. Atualizar status de formatado.");
                                System.out.println("8. Atualizar statis de revisado.");
                                System.out.println("9. Atualizar todos atributos.");

                                System.out.print("\nEscolha: ");
                                opcao = (scan.nextInt());
                                System.out.println("");

                                if (opcao == 1) {
                                    scan.nextLine();
                                    System.out.print("Digite o novo tipo de computador: ");
                                    String tipo = scan.nextLine();
                                    computador.setTipo(tipo);

                                    daoC.atualizarUmAtributo(computador, id);
                                } else if (opcao == 2) {
                                    scan.nextLine(); 
                                    System.out.print("Digite o novo modelo: ");
                                    String modelo = scan.nextLine();
                                    computador.setModelo(modelo);

                                    daoC.atualizarUmAtributo(computador, id);
                                } else if (opcao == 3) {
                                    System.out.print("Digite o novo processador: ");
                                    String processador = scan.nextLine();
                                    computador.setProcessador(processador);

                                    daoC.atualizarUmAtributo(computador, id);
                                } else if (opcao == 4) {
                                    scan.nextLine();
                                    System.out.print("Digite a nova memória RAM: ");
                                    String memoria = scan.nextLine();
                                    computador.setMemoria(memoria);

                                    daoC.atualizarUmAtributo(computador, id);
                                } else if (opcao == 5) {
                                    scan.nextLine();
                                    System.out.print("Digite a nova versão do Windows: ");
                                    String windows = scan.nextLine();
                                    computador.setWindows(windows);

                                    daoC.atualizarUmAtributo(computador, id);
                                } else if (opcao == 6) {
                                    scan.nextLine();
                                    System.out.print("Digite o novo tipo de armazenamento e seu tamanho: ");
                                    String armazenamento = scan.nextLine();
                                    computador.setArmazenamento(armazenamento);

                                    daoC.atualizarUmAtributo(computador, id);
                                } else if (opcao == 7) {
                                    scan.nextLine();    
                                    System.out.print("Foi formatado? ");
                                    String formatacao = scan.nextLine();
                                    computador.setFormatacao(formatacao);

                                    daoC.atualizarUmAtributo(computador, id);
                                } else if (opcao == 8) {
                                    scan.nextLine();
                                    System.out.print("Foi feita manutenção? ");
                                    String manutencao = scan.nextLine();
                                    computador.setManutencao(manutencao);

                                    daoC.atualizarUmAtributo(computador, id);
                                } else if (opcao == 9) {
                                    scan.nextLine();
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
                                } else {
                                    System.out.println("Opção inválida.");
                                }
                                break;
                            } case 5: {
                                System.out.print("Digite o ID do computador a ser deletado: ");
                                opcao = scan.nextInt();
                                daoC.deletar(opcao);
                                break;
                            } default: {
                                System.out.println("Opção inválida. Escolha novamente.");
                                break;
                            }
                        }
                    } while (opcao != 6);
                        opcao = 0; // Retornando valor para zero para que não adentre em nada.
                        break;
                    } case 2: {
                        do {
                        System.out.println("--- Menu do CRUD de Impressora ---");
                        System.out.println("Escolha uma opção:");
                        System.out.println("1. Criar dados para uma impressora.");
                        System.out.println("2. Ler dados de todas impressoras.");
                        System.out.println("3. Ler dados de uma impressora.");
                        System.out.println("4. Atualizar dados de uma impressora.");
                        System.out.println("5. Deletar dados de uma impressora.");
                         System.out.println("6. Retornar ao menu principal.");

                        System.out.print("\nEscolha: ");
                        opcao = (scan.nextInt());
                        System.out.println("");

                        switch(opcao) {
                            case 1: {
                            System.out.println("Digite os dados de impressora a serem cadastrados:\n");

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

                            daoI.adicionar(impressora);
                            break;
                        } case 2: {
                            daoI.listar();
                            break;
                        } case 3: {
                            System.out.print("Digite o ID da impressora a ser lida: ");
                            opcao = (scan.nextInt());
                            daoI.listarID(opcao);
                            break;
                        } case 4: {
                            
                            System.out.print("Digite o ID da impressora a ser atualizada (verifique se existe): ");
                            int id = scan.nextInt();
                            scan.nextLine(); 
                            
                            System.out.println("\n1. Atualizar tipo.");
                            System.out.println("2. Atualizar modelo.");
                            System.out.println("3. Atualizar revisão.");
                            System.out.println("4. Atualizar todos os atributos.");
                            
                            System.out.print("\nEscolha: ");
                            opcao = (scan.nextInt());
                            System.out.println("");
                            
                            if (opcao == 1) {
                                scan.nextLine();
                                System.out.print("Digite o novo tipo: ");
                                String tipo = scan.nextLine();
                                impressora.setTipo(tipo);
                                
                                daoI.atualizarUmAtributo(impressora, id);
                            } else if (opcao == 2) {
                                scan.nextLine();
                                System.out.print("Digite o novo modelo: ");
                                String modelo = scan.nextLine();
                                impressora.setModelo(modelo);
                                
                                daoI.atualizarUmAtributo(impressora, id);
                            } else if (opcao == 3) {
                                scan.nextLine();
                                System.out.print("Revisada? ");
                                String revisao = scan.nextLine();
                                impressora.setRevisao(revisao);
                                
                                daoI.atualizarUmAtributo(impressora, id);
                            } else if (opcao == 4) {
                                scan.nextLine();
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
                            } else {
                                System.out.println("Opção inválida.");
                            }
                            break;
                        } case 5: {
                            System.out.print("Digite o ID da impressora a ser deletada: ");
                            int id = scan.nextInt();
                            scan.nextLine();

                            daoI.deletar(id);
                            break;
                        } default: {
                            System.out.println("Digite uma opção válida. Tente novamente");
                            break;
                        }
                        }
                     } while (opcao != 6);
                        opcao = 0; // Retornando valor para zero para que não adentre em nada.
                        break;
                    } case 3: {
                        do {
                        System.out.println("--- Menu do CRUD de Equipamento Genérico ---");
                        System.out.println("Escolha uma opção:");
                        System.out.println("1. Criar dados para um equipamento genérico.");
                        System.out.println("2. Ler dados de todos equipamentos genéricos.");
                        System.out.println("3. Ler dados de um equipamento genérico.");
                        System.out.println("4. Atualizar dados de equipamento genérico.");
                        System.out.println("5. Deletar dados de equipamento genérico.");
                        System.out.println("6. Retornar ao menu principal.");

                        System.out.print("\nEscolha: ");
                        opcao = (scan.nextInt());
                        System.out.println("");

                            if(opcao == 1) {
                                System.out.println("Digite os dados de equipamento genérico a serem cadastrados: ");

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

                                daoO.adicionar(outroEquip);
                            } else if (opcao == 2) {
                                daoO.listar();
                            } else if (opcao == 3) {
                                System.out.print("Digite o ID do equipamento genérico a ser lido: ");
                                opcao = (scan.nextInt());
                                daoO.listarID(opcao);
                            } else if (opcao == 4) {

                            System.out.print("Digite o ID do equipamento genérico a ser atualizada (verifique se existe): ");
                            int id = scan.nextInt();
                            scan.nextLine();
                            
                            System.out.println("\n1. Atualizar tipo.");
                            System.out.println("2. Atualizar modelo.");
                            System.out.println("3. Atualizar descrição.");
                            System.out.println("4. Atualizar todos atributos.");
                            
                            System.out.print("\nEscolha: ");
                            opcao = (scan.nextInt());
                            System.out.println("");
                            
                            if (opcao == 1) {
                                scan.nextLine();
                                System.out.print("Digite o novo tipo: ");
                                String tipo = scan.nextLine();
                                outroEquip.setTipo(tipo);
                                
                                daoO.atualizarUmAtributo(outroEquip, id);
                            } else if (opcao == 2) {
                                scan.nextLine();
                                System.out.print("Digite o novo modelo: ");
                                String modelo = scan.nextLine();
                                outroEquip.setModelo(modelo);
                                
                                daoO.atualizarUmAtributo(outroEquip, id);
                            } else if (opcao == 3) {
                                scan.nextLine();
                                System.out.print("Digite uma nova descrição: ");
                                String descricao = scan.nextLine();
                                outroEquip.setDescricao(descricao);
                                
                                daoO.atualizarUmAtributo(outroEquip, id);
                            } else if (opcao == 4) {
                                scan.nextLine();

                                System.out.print("Digite o novo tipo: ");
                                String tipo = scan.nextLine();
                                outroEquip.setTipo(tipo);

                                System.out.print("Digite o novo modelo: ");
                                String modelo = scan.nextLine();
                                outroEquip.setModelo(modelo);

                                System.out.print("Digite uma nova descrição: ");
                                String descricao = scan.nextLine();
                                outroEquip.setDescricao(descricao);

                                daoO.atualizar(outroEquip, id);
                            }
                            opcao = 0;
                        } else if (opcao == 5) {
                            System.out.print("Digite o ID do equipamento genérico a ser deletado: ");
                            opcao = (scan.nextInt());
                            daoO.deletar(opcao);
                        }
                        } while (opcao != 6);
                        opcao = 0; // Retornando valor para zero para que não adentre em nada.
                        break;
                    } case 4: {
                        do {
                            System.out.println("--- Menu do CRUD de Loja ---");
                            System.out.println("Escolha uma opção:");
                            System.out.println("1. Criar dados para uma loja.");
                            System.out.println("2. Ler dados de todas lojas.");
                            System.out.println("3. Ler dados de uma loja.");
                            System.out.println("4. Atualizar dados de uma loja.");
                            System.out.println("5. Deletar dados de uma loja.");
                            System.out.println("6. Retornar ao menu principal.");

                            System.out.print("\nEscolha: ");
                            opcao = (scan.nextInt());
                            System.out.println("");

                            if (opcao == 1) {
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

                                System.out.print("Digite o ID de uma loja a ser atualizado: ");
                                int id = scan.nextInt();
                                scan.nextLine();

                                System.out.println("\n1. Atualizar CNPJ.");
                                System.out.println("2. Atualizar gerente.");
                                System.out.println("3. Atualizar cidade.");
                                System.out.println("4. Atualizar telefone.");
                                System.out.println("5. Atualizar todos atributos.");

                                System.out.print("\nEscolha: ");
                                opcao = (scan.nextInt());
                                System.out.println("");
                                
                                if (opcao == 1) {
                                    scan.nextLine();
                                    System.out.print("Digite o CNPJ: ");
                                    String cnpj = scan.nextLine();
                                    loja.setCnpj(cnpj);
                                        
                                    daoL.atualizarUmAtributo(loja, id);
                                } else if (opcao == 2) {
                                    scan.nextLine();
                                    System.out.print("Digite o nome do gerente: ");
                                    String gerente = scan.nextLine();
                                    loja.setGerente(gerente);
                                    
                                    daoL.atualizarUmAtributo(loja, id);
                                } else if (opcao == 3) {
                                    scan.nextLine();
                                    System.out.print("Digite a cidade: ");
                                    String cidade = scan.nextLine();
                                    loja.setCidade(cidade);
                                    
                                    daoL.atualizarUmAtributo(loja, id);
                                } else if (opcao == 4) {
                                    scan.nextLine();
                                    System.out.print("Digite o telefone: ");
                                    String telefone = scan.nextLine();
                                    loja.setTelefone(telefone);

                                    daoL.atualizarUmAtributo(loja, id);
                                
                                } else if (opcao == 5) {
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

                                    daoL.atualizar(loja, id);
                                 }
                            } else if (opcao == 5) {
                                System.out.print("Digite o ID de uma loja a ser deletado: ");
                                opcao = (scan.nextInt());
                                daoL.deletar(opcao);
                            }
                        } while (opcao != 6);
                        opcao = 0; // Retornando valor para zero para que não adentre em nada.
                        break;
                        } case 5: {
                            System.out.println("--- Menu do CRUD de Envio de Equipamento ---");
                            System.out.println("Escolha uma opção:");
                            System.out.println("1. Criar dados para um envio de equipamento.");
                            System.out.println("2. Ler dados de todos envios de equipamentos.");
                            System.out.println("3. Ler dados de um envio de equipamento.");
                            System.out.println("4. Atualizar dados de um envio de equipamento.");
                            System.out.println("5. Deletar dados de um envio de equipamento.");
                            System.out.println("6. Retornar ao menu principal.");

                            System.out.print("\nEscolha: ");
                            opcao = (scan.nextInt());
                            System.out.println("");

                            if (opcao == 1) {
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
                                System.out.print("Digite o ID do equipamento: ");
                                int fk_equipamento = scan.nextInt();

                                System.out.print("Digite o ID da loja: ");
                                int fk_loja = scan.nextInt();

                                daoE.listarID(fk_equipamento, fk_loja);
                            } else if (opcao == 4) {

                                System.out.print("Digite o ID de equipamento para envio a ser atualizado: ");
                                int fk_equipamento = scan.nextInt();

                                System.out.print("Digite o ID de loja para envio a ser atualizado: ");
                                int fk_loja = scan.nextInt();

                                scan.nextLine();

                                System.out.print("Digite uma nova data de envio: ");
                                String data = (scan.nextLine());
                                envio.setData_envio(data);

                                System.out.print("Digite uma nova observação: ");
                                String observacao = (scan.nextLine());
                                envio.setObservacao(observacao);

                                daoE.atualizar(envio, fk_equipamento, fk_loja);
                            } else if (opcao == 5) {
                                System.out.print("Digite o ID de equipamento para o envio ser deletado: ");
                                int fk_equipamento = scan.nextInt();

                                System.out.print("Digite o ID de loja para o envio ser deletado: ");
                                int fk_loja = scan.nextInt();

                                daoE.deletar(fk_equipamento, fk_loja);
                            }
                            break;
                    } case 6: {
                        daoED.listar();
                        break;
                    } case 7: {
                        daoEED.listar();
                        break;
                    } case 8: {
                        System.out.println("Você encerrou o programa.\n");
                        System.exit(0);
                    } default: {
                        System.out.println("Opção inválida. Digite uma opção válida.\n");
                        break;
                    }
                }   
            } while (opcao != 8);
        }
    }