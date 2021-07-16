package fish_game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

//import Game.runGame;

class GameTest {
	
	Game g = new Game();

	@Test
	void testGame() { //Colocando peixes B no final
//		runGame(int M, int N, int X, int Y, int RA, int MA, int RB, int MB)
		String retorno = g.runGame(5, 5, 5, 5, 1, 5, 2, 5);
		String esperado = "Restaram 0 peixes do tipo A e 0 do tipo B.\n" + "A sua pontuação em número de iterações foi 19.\n";
		assertEquals(esperado, retorno);
	}
	
	
	
	@Test
	void testGame2() { //Peixe Morreu de fome
//		runGame(int M, int N, int X, int Y, int RA, int MA, int RB, int MB)
		String retorno = g.runGame(10, 10, 10, 1, 2, 10, 5, 10);
		String esperado = "Restaram 0 peixes do tipo A e 0 do tipo B.\n" + "A sua pontuação em número de iterações foi 64.\n";
		assertEquals(esperado, retorno);
	}
	

	@Test
	void testGame3() { //Resto do Cardume
//		runGame(int M, int N, int X, int Y, int RA, int MA, int RB, int MB)
		String retorno = g.runGame(10, 10, 1, 1, 1, 1, 1, 1);
		String esperado = "Restaram 2 peixes do tipo A e 0 do tipo B.\n" + "A sua pontuação em número de iterações foi 2.\n";
		assertEquals(esperado, retorno);
	}

}
