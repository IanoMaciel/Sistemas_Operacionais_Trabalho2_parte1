import java.util.Scanner;

/*2) Implemente um programa que inverta os valores das linhas de uma matriz 3x3 de números inteiros
 * e imprima a matriz resultante na tela. 
 * A inversão de cada linha da matriz deve ser realizada em paralelo por threads.
 */

public class Threads2 {

	public static void main(String[] args) {

		//definição da matriz 3 x 3
		final int M = 3;
		int[][] matriz = new int[M][M];

		//preenche a matriz com os valores
		matriz = preencherMatriz(matriz);

		//instancia e cria objeto com os atributos e metodos da classe Matriz, passando a matriz ja preenchida
		Matriz m = new Matriz(matriz);
		//instancia e cria objeto thread1 de matriz normal, passando a instancia m
		ThreadMatriz matrizNormal = new ThreadMatriz("Matriz Normal", m);
		//instancia e cria objeto thread2 de matriz com valores invertidos, passando a instancia m
		ThreadMatriz matrizInversa = new ThreadMatriz("Matriz Inversa", m);

		//as duas threads foram criadas e estao em execução seguindo as regras de negocios das classes
		try {
			//As threads esperam a execução de cada uma para que aja a sincronização
			matrizNormal.t.join();
			matrizInversa.t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}

	//metodo para preencher matriz
	//É NECESSARIO INFORMAR VALORES
	public static int[][] preencherMatriz(int[][] matriz) {

		//variaveis para tratamento de preencher a matriz
		int aux;
		Scanner scan = new Scanner(System.in);

		//percorre as linhas da matriz
		for(int linha = 0, l = 1; linha < matriz.length; linha++, l++) {	
			//percorre as colunas da matriz
			for(int coluna = 0, c = 1; coluna < matriz[linha].length; coluna++, c++) {

				//pede valores ao usuario
				System.out.print("Informe o valor da linha["+l+"] e coluna["+c+"] : ");
				//salva oque foi digitado
				aux = scan.nextInt();
				//e valor é enviado para matriz
				matriz[linha][coluna] = aux;
			}
		}

		//retorna a matriz preenchida
		return matriz;
	}	

	//classe que contem os metodos e atributos propostos a matriz
	//classe utilizada para regra de negocios
	public static class Matriz{

		//vez servira para receber estados de execução
		boolean vez;
		//cria matriz vazia
		int[][] matriz;

		//construtor da classe que recebe a matriz preenchida anteriormente
		public Matriz(int[][] matriz) {
			this.matriz = matriz;
		}

		//metodo para exibir a matriz
		synchronized void exibirMatriz(int[][] matriz, String nome) {
			
			//identifica se é matriz normal ou resultante
			System.out.println(nome);

			//percorre as linhas da matriz
			for(int linha = 0; linha < matriz.length; linha++) {
				//percorre as colunas da matriz
				for(int coluna = 0; coluna < matriz[linha].length; coluna++) {
					//exibi os valor de determinada linha e coluna
					System.out.print("  " + matriz[linha][coluna] + " ");
				}
				//pula linha
				System.out.println();
			}			
		}

		//metodo para inverter os valores da matriz
		synchronized void inverterMatriz(int[][] matriz) {

			//delimita as linhas a serem percorridas
			final int M = 3;
			//variavel auxiliar utilizado para guardar valores
			int aux;
			
			//percorre as linhas
			for(int linha = 0; linha < M; linha++) {
				//auxiliar recebe valor da linha em questao e primeira coluna
				aux = matriz[linha][0];
				//o valor da linha em questao e primeira coluna é substituido pelo novo valor da linha em questao e ultima coluna
				matriz[linha][0] = matriz[linha][2];
				//o valor da linha em questao e ultima coluna é substituido pelo valor do auxiliar (linha em questao e primeira coluna)
				matriz[linha][2] = aux;
			}			
		}

		//metodo da matriz normal
		synchronized void matrizNormal(boolean estaExecutando) {
			
			//NA PRIMEIRA EXECUÇÃO ESSE TRATAMENTO É IGNORADO
			//NA SEGUNDA EXECUÇÃO, É PASSADO PELO TRATAMENTO E ENCERRA A THREAD
			//OU SEJA, SENDO EXECUTADO APENAS DUAS VEZES
			
			//verifica se esta executando
			if(!estaExecutando) {
				//matriz normal sendo finalizada
				vez = true;
				//notifica a thread que estava esperando para retornar a sua execução
				notify();
				//termina a execução do metodo
				return;
			}

			//matriz normal sendo executada
			vez = true;
			
			//notifica a thread que estava esperando para retornar a sua execução
			notify();

			try {

				//enquanto matriz normal em execução
				while(vez) {
					//bloqueia a execução da outra thread temporariamente
					wait();
				}

			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}

		//metodo da matriz com valores invertidos
		synchronized void matrizInversa(boolean estaExecutando) {

			//NA PRIMEIRA EXECUÇÃO ESSE TRATAMENTO É IGNORADO
			//NA SEGUNDA EXECUÇÃO, É PASSADO PELO TRATAMENTO E ENCERRA A THREAD
			//OU SEJA, SENDO EXECUTADO APENAS DUAS VEZES
			
			//verifica se esta executando
			if(!estaExecutando) {
				//matriz inversa sendo finalizada
				vez = false;
				//notifica a thread que estava esperando para retornar a sua execução
				notify();
				//termina a execução do metodo
				return;
			}

			//matriz inversa sendo executada
			vez = false;
			//notifica a thread que estava esperando para retornar a sua execução
			notify();

			try {
				
				//enquanto matriz inversa em execução
				while(!vez) {
					//bloqueia a execução da outra thread temporariamente
					wait();
				}

			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}		
	}

	//classe que criara as threads e oque elas devem executar
	public static class ThreadMatriz implements Runnable{
		
		//instancia da classe Matriz
		Matriz m;
		//instancia de thread
		Thread t;
		//variavel para verificar se esta executando no momento (FLAG)
		boolean executando;

		//construtor da classe que identifica a thread e atribui os metodos e atributos da instancia da classe Matriz
		public ThreadMatriz(String nome, Matriz m) {
			this.m = m;
			//cria a thread em questao
			t = new Thread(this, nome);
			//identifica que a thread esta pronta para execução
			t.start();
		}

		//assim que thread for startada, sera executado as seguintes linhas
		@Override
		public void run() {

			//verifica qual a thread que esta sendo executada no momento
			if(t.getName().equalsIgnoreCase("Matriz Normal")) {

				//indica esta em execução
				executando = true;

				//enquanto em execução
				while(executando) {
					//vai exibir a matriz preenchida anteriomente
					m.exibirMatriz(m.matriz, "\n    Matriz\n");
					//identifica a thread que esta sendo executada. Ver a classe Matriz
					m.matrizNormal(true);
					//indica que a execução chegou ao fim
					executando = false;
				}

				//identifica que a thread esta finalizada. Ver a classe Matriz
				m.matrizNormal(false);

			}else {
				
				//inverte os valores da matriz. Ver a classe Matriz
				m.inverterMatriz(m.matriz);

				//indica esta em execução
				executando = true;

				//enquanto em execução
				while(executando) {	
					//vai exibir a matriz com valores invertidos
					m.exibirMatriz(m.matriz, "\n  Resultante\n");
					//identifica a thread que esta sendo executada. Ver a classe Matriz
					m.matrizInversa(true);
					//indica que a execução chegou ao fim
					executando = false;
				}

				//identifica que a thread esta finalizada. Ver a classe Matriz
				m.matrizInversa(false);				
			}			
		}		
	}
}
