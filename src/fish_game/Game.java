package fish_game;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Game {
	
	public String runGame(int M, int N, int X, int Y, int RA, int MA, int RB, int MB){
		int stop=0; //Comando para parar.
		int iter=0; //Contador de iterações.
		int[][][] A=new int[M][N][4]; //Aquário.
		int i,j;
		for(i=0; i<X/N; ++i) //Coloca o cardume de peixes A no início do aquário.
			for(j=0; j<N; ++j){
				A[i][j][0]=0; //RA ou RB do peixe.
				A[i][j][1]=0; //MA ou MB do peixe.
				A[i][j][2]=1; //0 é sem peixe; 1 é peixe A; 2 é filhote do peixe A; -1 é peixe B; -2 é filhote do peixe B.
				A[i][j][3]=0; //0 está se movendo para cima, 1 para a direita, 2 para baixo ou 3 para a esquerda. Offset de -4 indica que o peixe já se moveu na iteração atual.
			}
		i=X/N;
		for(j=0; j<X%N; ++j){ //Resto do cardume.
			A[i][j][0]=0; //RA ou RB do peixe.
			A[i][j][1]=0; //MA ou MB do peixe.
			A[i][j][2]=1; //0 é sem peixe; 1 é peixe A; 2 é filhote do peixe A; -1 é peixe B; -2 é filhote do peixe B.
			A[i][j][3]=0; //0 está se movendo para cima, 1 para a direita, 2 para baixo ou 3 para a esquerda. Offset de -4 indica que o peixe já se moveu na iteração atual.
		}
		for(i=M-1; i>=M-Y/N; --i) //Coloca o cardume de peixes B no fim do aquário.
			for(j=N-1; j>=0; --j)
				A[i][j][2]=-1; //0 é sem peixe; 1 é peixe A; 2 é filhote do peixe A; -1 é peixe B; -2 é filhote do peixe B.
		i=M-Y/N-1;
		for(j=N-1; j>=N-Y%N; --j) //Resto do cardume.
			A[i][j][2]=-1; //0 é sem peixe; 1 é peixe A; 2 é filhote do peixe A; -1 é peixe B; -2 é filhote do peixe B. //Última modificação por: Rodolfo Favoretto Rissardi.
		while(!(stop==1||Y==0)){ //Executa o jogo até parar ou game over.
			System.out.println("Iteração " +iter+ "\n");
			print(A,M,N);
			for(i=0; i<M; ++i){
				for(j=0; j<N; ++j){
					if(A[i][j][2]==1&&A[i][j][3]>=0){ //Se for peixe do tipo A ainda não processado nesta iteração. (O caso A[i][j][2]==2 não precisa ser verificado porque o filhote cresce antes de se movimentar pela primeira vez, supostamente, na próxima iteração, mesmo se ele ainda for varrido neste laço.)
						int[] k=new int[3];
						k=busca(i,j,0,A,M,N); //Procura um espaço vazio ao redor da coordenada [i,j].
						if(k[0]!=-1){ //Se encontrou célula livre.
							if(A[i][j][0]==RA){ //Se atingiu RA, cria um filhote.
								A[k[0]][k[1]][0]=0;
								A[k[0]][k[1]][1]=0;
								A[k[0]][k[1]][2]=2;
								A[k[0]][k[1]][3]=0;
								A[i][j][0]=0; //Zera o RA.
								++X;
							}
							else{ //Move para a célula livre.
								A[k[0]][k[1]][0]=A[i][j][0]+1; //Transfere e incrementa o RA.
								A[k[0]][k[1]][1]=0; //Zera MA.
								A[k[0]][k[1]][2]=1; //Transfere o peixe.
								A[k[0]][k[1]][3]=k[2]-4; //Atualiza a direção de movimento.
								A[i][j][2]=0; //Espaço vazio (com lixo nas outras coordenadas).
							}
						}
						else{ //Se não encontrou célula livre.
							A[i][j][0]=0; //Zera o RA.
							if(A[i][j][1]++==MA){ //Incrementa o MA.
								A[i][j][2]=0; //Este peixe morreu de fome.
								--X;
							}
						}
					}
					else if(A[i][j][2]==-1&&A[i][j][3]>=0){ //Se for peixe do tipo B ainda não processado nesta iteração. (O caso A[i][j][2]==-2 não precisa ser verificado porque o filhote cresce antes de se movimentar pela primeira vez, supostamente, na próxima iteração, mesmo se ele ainda for varrido neste laço.) //Última modificação por: Rodolfo Favoretto Rissardi.
						int flag=0; //0 é alerta de peixe B ao redor; 2 é que se reproduziu.
						if(A[i][j][0]>=RB){ //Se atingiu o necessário para se reproduzir.
							int[] vaz; //Coordenadas de um espaço vazio.
							vaz=new int[]{-1,-1}; //Sinal caso não haja espaço vazio ao redor.
							for(int l=A[i][j][3]; l<4+A[i][j][3]; ++l){ //Testa os quatro lados.
								int m=l%4; //Testa primeiro a direção de movimento anterior do peixe.
								if(m==0&&i>0){ //Para cima.
									if(A[i-1][j][2]==0) //Vazio.
										vaz=new int[]{i-1, j};
									else if(A[i-1][j][2]==-1) //Tem peixe B.
										flag=1;
								}
								else if(m==1&&j<N-1){ //Para a direita.
									if(A[i][j+1][2]==0) //Vazio.
										vaz=new int[]{i, j+1};
									else if(A[i][j+1][2]==-1) //Tem peixe B.
										flag=1;
								}
								else if(m==2&&i<M-1){ //Para baixo.
									if(A[i+1][j][2]==0) //Vazio.
										vaz=new int[]{i+1, j};
									else if(A[i+1][j][2]==-1) //Tem peixe B.
										flag=1;
								}
								else if(m==3&&j>0) //Para a esquerda.
									if(A[i][j-1][2]==0) //Vazio.
										vaz=new int[]{i, j-1};
									else if(A[i][j-1][2]==-1) //Tem peixe B.
										flag=1;
							}
							if(flag==0&&vaz[0]!=-1){ //Reproduz-se.
								A[vaz[0]][vaz[1]][0]=0;
								A[vaz[0]][vaz[1]][1]=0;
								A[vaz[0]][vaz[1]][2]=-2;
								A[vaz[0]][vaz[1]][3]=0;
								A[i][j][0]-=RB; //Consome o RB (pois assume-se que ele seja cumulativo no caso do peixe não poder se reproduzir mas poder continuar comendo). Supõe-se que não incrementa o MB.
								++Y;
								flag=2;
							}
						}
						if(flag!=2){ //Supõe-se que se ele se reproduziu não vai se movimentar.
							int[] presa=new int[3];
							presa=busca(i,j,1,A,M,N); //Procura um peixe A para comer. Supõe-se que ele não come um filhote criado nesta mesma iteração.
							if(presa[0]!=-1){ //Se encontrou um peixe A, come ele e ocupa a sua posição.
								A[presa[0]][presa[1]][0]=A[i][j][0]+1; //Incrementa o RB.
								A[presa[0]][presa[1]][1]=0; //Zera o MB.
								A[presa[0]][presa[1]][2]=-1; //Transfere o peixe.
								A[presa[0]][presa[1]][3]=presa[2]-4; //Atualiza a direção de movimento.
								A[i][j][2]=0; //Espaço vazio (com lixo nas outras coordenadas).
								--X;
							}
							else{
								if(A[i][j][1]++==MB){ //Incrementa o MB.
									A[i][j][2]=0; //Este peixe morreu de fome.
									--Y;
								}
								else{ //O peixe ainda está vivo.
									int[] k=new int[3];
									k=busca(i,j,0,A,M,N); //Procura um espaço vazio ao redor da coordenada [i,j].
									if(k[0]!=-1){ //Se encontrou célula livre, move para a célula livre.
										A[k[0]][k[1]][0]=A[i][j][0]; //Transfere o RB.
										A[k[0]][k[1]][1]=A[i][j][1]; //Transfere o MB incrementado.
										A[k[0]][k[1]][2]=-1; //Transfere o peixe.
										A[k[0]][k[1]][3]=k[2]-4; //Atualiza a direção de movimento.
										A[i][j][2]=0; //Espaço vazio (com lixo nas outras coordenadas).
									}
								}
							}
						}
					}
				}
			}
			//Thread.sleep(2000); //Para executar as iterações do jogo sem o usuário. (Comente as próximas duas linhas.)
//			System.out.println("Digite 1 para terminar o jogo ou outro número para continuar: ");
//			stop=scan();
			iter++;
		}
		print(A,M,N);
		if(Y==0)
			System.out.println("GAME OVER!\n");
		String result = "Restaram " +X+ " peixes do tipo A e " +Y+ " do tipo B.\nA sua pontuação em número de iterações foi " +iter+ ".\n";
//		System.out.println(result);
		return result;
	}
	
	public static int[] busca(int i, int j, int k, int[][][] A, int M, int N){ //Procura um objeto do tipo k ao redor da coordenada [i,j].
		for(int l=A[i][j][3]; l<4+A[i][j][3]; ++l){ //Testa os quatro lados.
			int m=l%4; //Testa primeiro a direção de movimento anterior do peixe. //Última modificação por: Rodolfo Favoretto Rissardi.
			if(m==0&&i>0){ //Para cima.
				if(A[i-1][j][2]==k) //Encontrou.
					return new int[]{i-1, j, 0};
			}
			else if(m==1&&j<N-1){ //Para a direita.
				if(A[i][j+1][2]==k) //Encontrou.
					return new int[]{i, j+1, 1};
			}
			else if(m==2&&i<M-1){ //Para baixo.
				if(A[i+1][j][2]==k) //Encontrou.
					return new int[]{i+1, j, 2};
			}
			else if(m==3&&j>0) //Para a esquerda.
				if(A[i][j-1][2]==k) //Encontrou.
					return new int[]{i, j-1, 3};
		}
		return new int []{-1, -1, -1}; //Sinal de que não encontrou.
	}
	
	public static void print(int[][][] A, int M, int N){ //Mostra o aquário na tela e sincroniza os peixes. Para melhorar a experiência de visualização do usuário, os peixes filhotes são mostrados com letra minúscula uma vez.
		for(int j=-1; j<N+1; ++j)
			System.out.printf("_");
		for(int i=0; i<M; ++i){
			System.out.printf("\n|");
			for(int j=0; j<N; ++j){
				if(A[i][j][2]==0)
					System.out.printf(" "); //Espaço sem peixe.
				else if(A[i][j][2]==1){
					System.out.printf("A"); //Peixe do tipo A.
					if(A[i][j][3]<0)
						A[i][j][3]+=4; //Libera o peixe para processamento na próxima iteração.
				}
				else if(A[i][j][2]==2){
					System.out.printf("a"); //Filhote de peixe A.
					A[i][j][2]=1; //Filhote cresceu.
				}
				else if(A[i][j][2]==-1){
					System.out.printf("B"); //Peixe do tipo B.
					if(A[i][j][3]<0)
						A[i][j][3]+=4; //Libera o peixe para processamento na próxima iteração.
				}
				else if(A[i][j][2]==-2){
					System.out.printf("b"); //Filhote de peixe B.
					A[i][j][2]=-1; //Filhote cresceu.
				}
			}
			System.out.printf("|");
		}
		System.out.printf("\n");
		for(int j=-1; j<N+1; ++j)
			System.out.printf("-");
		System.out.printf("\n");
	}

}
