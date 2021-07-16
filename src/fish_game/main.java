package fish_game;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class main {
	public static void main(String args[]){
		System.out.println("Bem vindo ao jogo do aquário!\nPara começar, você define o tamanho MxN do aquário.\nDigite o valor de M: ");
		int M=scan(); //Lê o número inteiro.
		while(M<1){
			System.out.println("Digite um número positivo para M: ");
			M=scan();
		}
		System.out.println("Digite o valor de N: ");
		int N=scan();
		while(N<1){
			System.out.println("Digite um número positivo para N: ");
			N=scan();
		}
		System.out.println("Digite a quantidade de peixes do tipo A que comem plâncton que haverá no aquário: ");
		int X=scan();
		while(X>M*N||X<0){
			System.out.println("A quantidade de peixes deve estar entre 0 e " +M*N+ ". Insira novo valor: ");
			X=scan();
		}
		System.out.println("Digite a quantidade de peixes do tipo B que comem os do tipo A que haverá no aquário: ");
		int Y=scan();
		while(Y>M*N-X||Y<0){
			System.out.println("A quantidade de peixes deve estar entre 0 e " +(M*N-X)+ ". Insira novo valor: ");
			Y=scan();
		}
		System.out.println("Digite o número de movimentos RA de um peixe A para ele se reproduzir: ");
		int RA=scan();
		while(RA<1){
			System.out.println("Digite um número positivo para RA: ");
			RA=scan();
		}
		System.out.println("Digite o limite MA que um peixe do tipo A pode ficar parado sem morrer de fome: ");
		int MA=scan();
		while(MA<1){
			System.out.println("Digite um número positivo para MA: ");
			MA=scan();
		}
		System.out.println("Digite o número de refeições RB de um peixe B para ele se reproduzir: ");
		int RB=scan();
		while(RB<1){
			System.out.println("Digite um número positivo para RB: ");
			RB=scan();
		}
		System.out.println("Digite o limite MB que um peixe do tipo B pode ficar sem comer para não morrer de fome: ");
		int MB=scan();
		while(MB<1){
			System.out.println("Digite um número positivo para MB: "); //Última modificação por: Rodolfo Favoretto Rissardi, 10/7/2021.
			MB=scan();
		}
		
		Game g = new Game();
		String g_result = g.runGame(M, N, X, Y, RA, MA, RB, MB);
		System.out.println(g_result);
		
	}
	
	public static int scan() //Lê entrada e verifica se é número inteiro.
	{
		int r=0;
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); //Objeto BufferedReader.
		try{
			r=Integer.parseInt(br.readLine()); //Valida se for um inteiro (e pula a catch) e prossegue, caso contrário vai para catch.
			return r;
		}
		catch(Exception e){
			System.out.println("Digite um número inteiro: "); //Caso não teha sido um inteiro.
			r=scan(); //Chama esta função para o usuário tentar novamente.
			return r;
		}
	}
}
