#include <stdio.h>
#include <stdlib.h>

#define max 20

void exibir(int array[], int tam);
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

        for(i=0; i<tam; i++)
        {
            printf("Processo[%d]: ", i+1);
            scanf("%d", &tExecucao[i]);
            processo[i] = i+1;
        }

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