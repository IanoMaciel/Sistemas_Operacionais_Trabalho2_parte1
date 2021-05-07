#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

typedef struct Dados
{
    int tExecucao[20]; //tempo de execução
    int tEspera[20]; //tempo de espera
    int tTurnaround[20]; //tempo de turnaround
    float tmpEspera; //tempo médio de espera
    float tmpTurn; //tempo médio de turnaround
}t_dados;

 
 int main()
{
    t_dados dados;
    dados.tmpEspera = 0;
    dados.tmpTurn = 0;
    int i, j, tam, op;
    int cont = 0;


    while(cont > -1)
    {
        printf("Infrome o tamanho do processos: ");
        scanf("%d", &tam);

        for(i=0; i<tam; i++)
        {
            printf("Processo[%d]: ", i+1);
            scanf("%d", &dados.tExecucao[i]);
        }

        dados.tEspera[0] = 0;
        for(i=1; i<tam; i++)
        {
            dados.tEspera[i] = 0;
            for(j=0; j<i; j++)
                dados.tEspera[i] += dados.tExecucao[j];
        }
        
        for(i=0; i<tam; i++)
        {
            dados.tTurnaround[i] = dados.tExecucao[i] + dados.tEspera[i];
            dados.tmpEspera += dados.tEspera[i];
            dados.tmpTurn += dados.tTurnaround[i];
        }
        dados.tmpEspera /= tam;
        dados.tmpTurn /= tam;

        printf("\n\nTeste %d", cont+1);
        printf("\nTempo medio de espera: %0.2f", dados.tmpEspera);
        printf("\nTempo medio de turnaround: %0.2f\n", dados.tmpTurn);
        for(i=0; i<tam; i++)
            printf("P%d\t", i+1);

        printf("\n\n1 - Testar novamente\n0 - Sair\nOP: ");
        scanf("%d", &op);
        if(op == 1)
        {
            cont++;
            system("clear");
        } else if(op == 0)
            return 0;
    }
}