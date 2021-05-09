#include <stdio.h>
#include <stdlib.h>

#define max 20

//imprime array
void exibir(int array[], int tam);

//ordena o array tExecuta de acordo com o tempo de execução
void orderna(int array1[], int array2[], int tam);

//retorna o tempo médio de espera
float tempoEspera(int array1[], int array2[], int tam);

//retorna o tempo médio de execução
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

        for(i=0; i<tam; i++)
        {
            printf("Processo[%d]: ", i+1);
            scanf("%d", &tExecucao[i]);
            processo[i] = i+1;
        }

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