/*1) Escreva um programa em C (ou Java) composto por duas threads:
 * a primeira deve contar e exibir na tela todos os n�meros entre 1 a 500 (de forma crescente); 
 * a segunda deve contar e exibir na tela todos os n�meros entre 500 a 1 (de forma decrescente). 
 * As duas threads devem ser executadas em paralelo.
 */

public class Threads1 {

	public static void main(String[] args) {

		//instancia e cria objeto com os atributos e metodos da classe CrescenteDecrescente
		CrescenteDecrescente cd = new CrescenteDecrescente();
		//instancia e cria objeto thread1 de contagem crescente, passando a instancia cd
		ThreadCrescenteDecrescente crescente = new ThreadCrescenteDecrescente("Thread #1", cd);
		//instancia e cria objeto thread2 de contagem decrescente, passando a instancia cd
		ThreadCrescenteDecrescente decrescente = new ThreadCrescenteDecrescente("Thread #2", cd);
		
		//as duas threads foram criadas e estao em execu��o seguindo as regras de negocios das classes
		try {
			//As threads esperam a execu��o de cada uma para que aja a sincroniza��o
			crescente.t.join();
			decrescente.t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}

	//classe que contem os metodos de contagem crescente e decrescente
	//classe utilizada para regra de negocios
	public static class CrescenteDecrescente{

		//vez servira para receber estados de execu��o
		boolean vez;

		//metodo de contagem crescente
		synchronized void crescente(boolean estaExecutando, int contador) {

			//verifica se esta executando
			if(!estaExecutando) {
				//contador crescente finalizado
				vez = true;
				//notifica a thread que estava esperando para retornar a sua execu��o
				notify();
				//termina a execu��o do metodo
				return;
			}

			//identifica a thread e sua contagem
			System.out.print("Thread #1 - " + contador + "	");
			
			//contador crescente em execu��o 1, 2, 3...
			vez = true;
			
			//notifica a thread que estava esperando para retornar a sua execu��o
			notify();

			try {

				//enquanto crescente em execu��o
				while(vez) {
					//bloqueia a execu��o da outra thread temporariamente
					wait();
				}

			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		 //metodo de contagem decrescente
		synchronized void decrescente(boolean estaExecutando, int contador) {

			//verifica se esta executando
			if(!estaExecutando) {
				//contador descrescente finalizado
				vez = false;
				//notifica a thread que estava esperando para retornar a sua execu��o
				notify();
				//termina a execu��o do metodo
				return;
			}

			//identifica a thread e sua contagem
			System.out.println("Thread #2 - " + contador);
			
			//contador decrescente em execu��o 500, 499, 498...
			vez = false;
			
			//notifica a thread que estava esperando para retornar a sua execu��o
			notify();

			try {
				
				//enquanto decrescente em execu��o
				while(!vez) {
					//bloqueia a execu��o da outra thread temporariamente
					wait();
				}

			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}		
	}

	//classe que criara as threads e oque elas devem executar
	public static class ThreadCrescenteDecrescente implements Runnable{
		
		//instancia da classe CrescenteDecrescente
		CrescenteDecrescente cd;
		//instancia de thread
		Thread t;

		//variaveis que ser�o usadas para a contagem dos numeros
		final int MAX = 500;
		final int MIN = 1;
		int contador1 = MIN;
		int contador2 = MAX;
		
		//construtor da classe que identifica a thread e atribui os metodos e atributos da instancia da classe CrescenteDecrescente
		public ThreadCrescenteDecrescente(String nome, CrescenteDecrescente cd) {
			this.cd = cd;
			//cria a thread em questao
			t = new Thread(this, nome);
			//identifica que a thread esta pronta para execu��o
			t.start();
		}

		//assim que thread for startada, sera executado as seguintes linhas
		@Override
		public void run() {
		
			//verifica qual a thread que esta sendo executada no momento
			if(t.getName().equalsIgnoreCase("Thread #1")) {
				
				//faz a contagem crescente de 1 a 500
				while(contador1 <= MAX) {	
					//identifica que esta sendo executada e qual a contagem atual
					cd.crescente(true, contador1);
					//incrementa a contagem
					contador1++;
				}
				//identifica que a contagem esta finalizada ou em espera. Ver a classe CrescenteDecrescente
				cd.crescente(false, contador1);
				
			}else {
				
				//faz a contagem decrescente de 500 a 1
				while(contador2 >= MIN) {	
					//identifica que esta sendo executada e qual a contagem atual
					cd.decrescente(true, contador2);
					//desincrementa a contagem
					contador2--;
				}
				
				//identifica que a contagem esta finalizada ou em espera. Ver a classe CrescenteDecrescente
				cd.decrescente(false, contador2);				
			}			
		}		
	}
}
