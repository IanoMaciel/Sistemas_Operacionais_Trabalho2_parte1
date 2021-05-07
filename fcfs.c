#include<stdio.h>

typedef struct Dados
{
    int tExecucao[20]; //tempo de execução
    int tEspera[20]; //tempo de espera
    int tTurnaround[20]; //tempo de turnaround
    float tmp; //tempo médio de espera
    float tmt; //tempo médio de turnaround
}t_dados;

 
 int main()
{
    t_dados dados;
    int i, j, tam;
    
    printf("Informe o tamanho dos processos: ");
    scanf("%d", &tam);

    for(i=0; i<tam; i++)
    {
        printf("Processo[%d]: ", i+1);
        scanf("%d", &dados.tExecucao[i]);
    }

    //Calculando o tempo de espera
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
        dados.tmp += dados.tEspera[i];
        dados.tmt += dados.tExecucao[i];
    }
    dados.tmp /= i;
    dados.tmt /= i;

    printf("\nTempo medio de espera: %0.2f", dados.tmp);
    printf("\nTempo medio de turnaround: %0.2f", dados.tmt);

    /*
    int n,bt[20],wt[20],tat[20],avwt=0,avtat=0,i,j;
    printf("Enter total number of processes(maximum 20): ");
    scanf("%d",&n);
 
    printf("Enter Process Burst Timen\n");
    for(i=0;i<n;i++)
    {
        printf("P[%d]:",i+1);
        scanf("%d",&bt[i]);
    }
 
    wt[0]=0;   
 
    for(i=1;i<n;i++)
    {
        wt[i]=0;
        for(j=0;j<i;j++)
            wt[i]+=bt[j];
    }
 
    printf("\nProcess\tBurst\tTimetWaiting\tTimetTurnaround\tTime\n");
 
    for(i=0;i<n;i++)
    {
        tat[i]=bt[i]+wt[i];
        avwt+=wt[i];
        avtat+=tat[i];
        printf("P[%d]\t%d\t%d\t%d\n",i+1,bt[i],wt[i],tat[i]);
    }
    
    avwt/=i; // tempo médio de espera
    avtat/=i; // tempo médio de turnaround

    printf("\nAverage Waiting Time:%d",avwt);
    printf("\nAverage Turnaround Time:%d",avtat);
 
    return 0;
    */
}