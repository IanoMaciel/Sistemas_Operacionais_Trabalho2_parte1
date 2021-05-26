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
void getSystem(){
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
