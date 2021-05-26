# Trabalho 2 - parte 1
## Sobre este repositório
Reposistório destinado a disciplina Sistemas Operacionais, ministrado pelo **Prof. Dr. Rainer Xavier de Amorim**.
## O que será visto neste repositório 
1. Discentes
2. Problemas
3. Como executar o porjeto

### 1. Discentes
- Iano de Benedito Maciel, 21752128;
- Taynara Silva da Costa, 21751227;
- Adriano dos Santos Gomes, 21751229;
- Naelly Pereira Brito 21751167;

### 2. Problemas 
**Problema A - Algoritmo de escalonamento FCFS em C**
~~~c
#include <stdio.h>
#include <stdlib.h>

#define max 20

void exibir(int array[], int tam);
void cadastrarTempoExecucao(int array1[], int array2[], int tam);
float tempoEspera(int array1[], int array2[], int tam);
float tempoTurnaround(int array1[], int array2[], int array3[], int tam);

int main()
{
    int tExecucao[max], tEspera[max], tTurnaround[max];
    int processo[max];
    int i, tam, op;
    int cont = 0;

    while(-1 < cont)
    {
        printf("Infrome o tamanho do processos: ");
        scanf("%d", &tam);

        cadastrarTempoExecucao(tExecucao, processo, tam);
        printf("\n\nTeste %d\n", cont+1);
        printf("Tempo medio de espera: %0.2f\n", tempoEspera(tEspera, tExecucao, tam));
        printf("Tempo medio de turnaround: %0.2f\n", tempoTurnaround(tTurnaround, tExecucao, tEspera, tam));
        exibir(processo, tam);
        
        printf("\n\n1 - Testar novamente\n0 - Sair\nOP: ");
        scanf("%d", &op);
        
        if(op == 1)
        {
            cont++;
            system("clear");
        } 
        else if(op == 0)
            return 0;
    }
}

void cadastrarTempoExecucao(int array1[], int array2[], int tam)
{
    int i;

    for(i=0; i<tam; i++)
    {
        printf("Processo[%d]: ", i+1);
        scanf("%d", &array1[i]);
        array2[i] = i + 1;
    }
}

void exibir(int array[], int tam)
{
    int i;

    for(i=0; i<tam; i++)
    {
        printf("P%d\t", array[i]);
    }
}

float tempoEspera(int array1[], int array2[], int tam)
{
    int i, j;
    float total;

    array1[0] = 0;

    for(i=1; i<tam; i++)
    {
        array1[i] = 0;
        for(j=0; j<i; j++)
        {
            array1[i] += array2[j];
        }
    }
    for(i=0; i<tam; i++)
        total += array1[i];
    
   return (total/tam);
}

float tempoTurnaround(int array1[], int array2[], int array3[], int tam)
{
    int i;
    float total;

    for(i=0; i<tam; i++)
    {
        array1[i] = array2[i] + array3[i];
        total += array1[i];
    }
    return (total/tam);
}
~~~
**Problema B - Algoritmo de escalonamento SJF em C**
~~~C
#include <stdio.h>
#include <stdlib.h>

#define max 20

void exibir(int array[], int tam);
void cadastrarTempoExecucao(int array1[], int array2[], int tam);
void orderna(int array1[], int array2[], int tam);
float tempoEspera(int array1[], int array2[], int tam);
float tempoTurnaround(int array1[], int array2[], int array3[], int tam);

int main()
{
    int tExecucao[max], tEspera[max], tTurnaround[max];
    int processo[max];
    int tam, i, op, cont = 0;

    while(-1 < cont)
    {
        printf("Informe o tamanho do processo: ");
        scanf("%d", &tam);

        cadastrarTempoExecucao(tExecucao, processo, tam);

        orderna(tExecucao, processo, tam);

        printf("\n\nTeste %d\n", cont+1);
        printf("Tempo medio de espera: %0.2f\n", tempoEspera(tEspera, tExecucao, tam));
        printf("Tempo medio de Turnaround: %0.2f\n", tempoTurnaround(tTurnaround, tExecucao, tEspera, tam));
        exibir(processo, tam);

        printf("\n\n1 - Testar novamente\n0 - Sair\nOP: ");
        scanf("%d", &op);

        if(op == 1)
        {
            cont++;
            system("clear");
        }
        else if(op == 0)
            return 0;
    }
}

void exibir(int array[], int tam)
{
    int i;

    for(i=0; i<tam; i++)
    {
        printf("P%d\t", array[i]);
    }
}

void cadastrarTempoExecucao(int array1[], int array2[], int tam)
{
    int i;

    for(i=0; i<tam; i++)
    {
        printf("Processo[%d]: ", i+1);
        scanf("%d", &array1[i]);
        array2[i] = i + 1;
    }
}

void orderna(int array1[], int array2[], int tam)
{
    int i, j, aux, menor;

    for(i=0; i<tam; i++)
    {
        menor = i;
        for(j=i+1; j<tam; j++)
        {
            if(array1[j] < array1[menor])
                menor = j;
        }
        aux = array1[i];
        array1[i] = array1[menor];
        array1[menor] = aux;

        aux = array2[i];
        array2[i] = array2[menor];
        array2[menor] = aux;
    }
}

float tempoEspera(int array1[], int array2[], int tam)
{
    int i, j, total;

    array1[0] = 0;

    for(i=1; i<tam; i++)
    {
        array1[i] = 0;
        for(j=0; j<i; j++)
        {
            array1[i] += array2[j];
        }
        total += array1[i];  
    }
    return total/tam;
}

float tempoTurnaround(int array1[], int array2[], int array3[], int tam)
{
    int i, total;

    for(i=0; i<tam; i++)
    {
        array1[i] = array2[i] + array3[i];
        total += array1[i];
    }
    return total/tam;
}
~~~
**Problema C - Algoritmo de escalonamento RR em C++**
~~~c++
#include<stdio.h>
#include<stdlib.h>
#include<locale.h>

int processos[100][4], BO, quantum, agendador[1000],BA[100], teste=1;
FILE *file;

unsigned int tempo = 0;
typedef struct a
{
	unsigned int p;
	struct a * next;
}Q;

Q * q = NULL;


//OBTENÇÃO DO SISTEMA
void getSystem()
{
	int i;

	for(i=0; i<BO; i++ )
	{
		fscanf(file,"%d %d", &processos[i][0], &processos[i][1]);
		processos[i][2] = processos[i][1];
	}
}

//AQUI ACONTECE O PROCESSO DE EXECUÇÃO DA PERMANCIA
unsigned int executionRemained()
{
	int i;
	unsigned int x = 0;
	for(i=0; i<BO; i++)
	{
		if(processos[i][2] > 0)
		{
			x = 1;
		}
	}
	return x;
}
//AQUI ACONTECE O POCESSO DE ADICIONAR O ENFILEIRAMENTO
void addToQeue(int i)
{
	Q *n, *n1;
	n = (Q *)malloc(sizeof(Q));
	n->next = NULL;
	n->p = i;
	if(q == NULL)
	{
		
		q = n;
	}
	else
	{
		for(n1 = q ; n1->next!=NULL; n1=n1->next);
		n1 -> next = n;
	}
}
//AQUI ACONTECE O POCESSO DE ADICIONAR A CHEGADA DO ENFILEIRAMENTO
void addArrivedProcessesToQeue()
{
	int i;
	for(i=0; i<BO; i++)
	{
		if(processos[i][0] == tempo)
		{
			addToQeue(i);
		}
	}
}
//AQUI OBTEM O PROXIMO PROCESSO
unsigned int getNextProcess()
{
	Q *n;
	int x;
	if(q == NULL)
	{
		return -1;
	}
	else
	{
		x = q -> p;
		n = q;
		q = q -> next;
		free(n);
		return x;
	}
}
// CRONOGRAMA DO PROCESSO
void schedule()
{
	unsigned int np, toRun, q, i;
	q = 0;
	addArrivedProcessesToQeue();
	while(executionRemained())
	{
		np = getNextProcess();
		if(np == -1)
		{
			agendador[tempo] = -1;
			tempo++;
			addArrivedProcessesToQeue();
		}
		else
		{
			q = quantum;
			if(processos[np][2] < q)
			{
				q = processos[np][2];
			}
			for(i = q; i>0; i--)
			{
				agendador[tempo]=np;
				tempo++;
				processos[np][2]--;
				addArrivedProcessesToQeue();
			}
			if(processos[np][2] > 0)
			{
				addToQeue(np);
			}
			
			processos[np][3] = tempo;
		}
	}
}
// AQUI ACONTECE A IMPRESSÃO DOS AGENDAMENTOS
void printScheduling()
{
	int i, j, k;
	for(i=0; i<tempo; i++)
	{
		if (agendador[i]!=-1){
			printf("P%d " ,agendador[i]+1);
			
			j=1;k=i+1;
			while(agendador[k++]==agendador[i]){
				j++;
				if (j==quantum) break;
			}
			
			if (j>1)
				i+=j-1;
			
		}
	}
	printf ("\n\n");	
}
//TRABALHA A ESPERA DO TEMPO
void WatingTime()
{
	int i;
	unsigned int releaseTime, t;
	for(i=0; i<BO; i++)
	{
		
		for(t=tempo-1; agendador[t]!= i; t--);
		releaseTime = t+1;
		
		BA[i] = processos[i][3] - (processos[i][0] + processos[i][1]);
	}
	
	float AWT = 0.0;
	for(i=0; i<BO; i++)
	{
		AWT = AWT+BA[i];
	}
	AWT = AWT/BO;
	printf("TEMPO MEDIO REFERENTE A EXECUCAO: %f\n", AWT);
}
// AQUI A CONTECE A INVERSÃO DE MARCHA, CONHECIDO TAMBEM COMO O TURNAROUND :)
void turnaround()
{
	int i;
	float tmr = 0;
	for (i=0;i<BO; i++){
		tmr += processos[i][3] - processos[i][0];
	}
	
	tmr = tmr/BO;
	printf ("TEMPO MEDIO REFERENTE A ESPERA: %f\n", tmr);
}

void zeraProcessos()
{
	int i, j;
	
	//NESSA PARTE  ZERA OS PROCESSOS
	for (i=0; i<BO; i++){
		for (j=0;j<4; j++){
			processos[i][j] = 0;
		}
		BA[i]=0;
	}
	
	//NESSA PARTE ZERA A LINHA DO TEMPO
	for (i=0;i<tempo;i++)
		agendador[i] = 0;
	
	tempo = 0;
}

int main()
{
	//BIBLIOTECA PARA ACENTUACAO
	setlocale(LC_ALL,"portuguese");
	
	//  AQUI PEGA AS ENTRADAS ESPECCIFICADAS NO AQUIVO SAIDA, O MESMO ENCONTRA-SE NO FORMATO TXT
	file = fopen("saida.txt", "r");
	fscanf(file, "%d", &quantum);
	
	while(1){
		fscanf(file,"%d", &BO);
		if (BO == 0) break;
		getSystem(); // ENTRADA SUJEITA A ALTERAÇÕES
		schedule();
		printf ("Teste %d\n", teste++);
		WatingTime();
		turnaround();
		printScheduling();
		zeraProcessos();
	}
	return 0;
}
~~~
**Problema D - Algoritmo de escalonamento de PRIORIDADE em Java**
~~~java
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
~~~
**Threads**

### 3. Como executar o projeto

#### Requisitos:
+ Git
+ GCC ou outro compilador em C/C++
+ JDK
#### Clone o projeto utilizando o comando a baixo:
~~~git
git clone https://github.com/IanoMaciel/Sistemas_Operacionais_Trabalho2_parte1
~~~
#### Execute os arquivos :)
