import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;

// Tarefas, ingresso, duração e prioridade

public class Prio {
public static void main(String[] args) {
	 
		 Scanner scan= new Scanner(System.in);
                 DecimalFormat df = new DecimalFormat("0.00");
                 
                 //variaveis
		 int N, entrada, tempoIng, execucao, idProcessoIng, qteprocessos;
                 int cont = 0;
                 
                 String formato, saida;
		 String ordemEscalo;
		 		 
	        //Solicita do user o número de processos a serem lidos e colocados na fila de prontos
		 System.out.println("Número de processos:");
		 N = scan.nextInt();
		 
		 System.out.println();
                 
                //repetição até o fim do N processos informado
		 while (N != 0) {
			 
			 cont++;
			
			ArrayList ingressos = new ArrayList();
			ArrayList duracoes = new ArrayList();
			ArrayList prioridades = new ArrayList();

                        
		 	// Leitura dos processos
			 for (int i = 0; i < N; i++) {
				 
                                //Lendo os valores recebidos e adiciona na lista de ingressos
				 System.out.println("Entrada do processo P" + (i+1) + ":");
				 entrada = scan.nextInt();
				 ingressos.add(entrada);
                                 
				// Lendo os valores e adiciona na lista de durações
				 System.out.println("Duração do P" + (i+1) + ":");
				 entrada = scan.nextInt();
				 duracoes.add(entrada);
				
                                // Lendo e adicionando na lista de prioridades
				 System.out.println("Prioridade do P" + (i+1) + ":");
			 	 entrada = scan.nextInt();
			 	 prioridades.add(entrada);
		 	 }
			 
                        //declarando vetores para armazenar N números inteiros
			 int[] temposIniciais = new int[N];
			 int[] temposFinais = new int[N];
			 
		 	// Cria cópia da lista de tempos de ingresso devido as mudanças
			ArrayList cpentradas = (ArrayList) ingressos.clone(); //cp: chegada do processo

			 ordemEscalo = "";
                         
                         ///tempo inicia do primeiro processo que ingressar
			 tempoIng = (int) ingressos.get(0);

			 qteprocessos = N;
			 
                         
			 while (qteprocessos > 0) {
				 // Percorrendo ingressos para descobrir processos que entram no tempo atual
				 
				ArrayList processos = new ArrayList();
				 
				 for (int i = 0; i < N; i++) {
					 if ((int) ingressos.get(i) != -1 && (int) ingressos.get(i) <= tempoIng) {
                                                // Adiciona na lista de processos
						 processos.add(i);
					 }
				 }

				 if (processos.isEmpty()) { //Retorna verdadeiro se a lista estiver vazia e falso caso contrário.
					 tempoIng++;
				 } else {
                                         //Indicando que o primeiro valor da lista é que possui menor prioridade
					 execucao = (int) processos.get(0);
					 
					 for (int i = 0; i < processos.size(); i++) {
						 
						 idProcessoIng = (int) processos.get(i);
						 
						 // Se a prioridade do processo atual for menor do que a menor prioridade já encontrada
                                                 //(quanto menor o valor numérico de Z, maior é a prioridade do processo.)
						 if ((int) prioridades.get(idProcessoIng) < (int) prioridades.get(execucao)) {
                                                         //Então modifica o processo que vai executar, ou seja, passa a ter mais prioridade de execução
                                                         // o processo de menor valor de prioridade
							 execucao = (int) processos.get(i);
						 }
					 }

					 temposIniciais[execucao] = tempoIng;

					 tempoIng += (int) duracoes.get(execucao);
                                         
                                         // Tempo que o processo finaliza a execução
					 temposFinais[execucao] = tempoIng;
					 ingressos.set(execucao, -1);

					 // Determina ordem de escalonamento
					 ordemEscalo += "P" + (execucao + 1) + " ";

					 qteprocessos--;
				 }
			 }

			 // Cálculo do tempo de escalonamento e tempo de espera
			 double tempoExecucao = 0, tempoEspera = 0;
			 
			 for (int i = 0; i < N; i++) {
				 tempoExecucao += temposFinais[i] - (int) cpentradas.get(i);
				 tempoEspera += temposIniciais[i] - (int) cpentradas.get(i);
			 }
			 
			 tempoExecucao = tempoExecucao / N;
			 tempoEspera = tempoEspera / N;
			 
			 // Saídas do conjunto de teste 
			 System.out.println("Teste n° " + cont);

			 formato = df.format(tempoExecucao);
			 saida = "Tempo médio de execução: " + formato + "s";
			 
			 System.out.println(saida);

			 formato = df.format(tempoEspera);
			 saida = "Tempo médio de espera: " + formato + "s";
			 
			 System.out.println(saida);

			 System.out.println("Ordem de escalonamento dos processos: " + ordemEscalo);
			 System.out.println();
                         
                         System.out.println("Digite qualquer valor para continuar ou 0 para finalizar:");
			 N = scan.nextInt();
		 }
	}
}
