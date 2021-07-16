package fish_game;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class tf {
    
    int M=1;
    int N=1;
    int X=1;
    int Y=1;
    int RA=1;
    int MA=1;
    int RB=1;
    int MB=1;


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void tf01(){
		Game game = new Game();
		String retorno = game.runGame(M, N, X, Y, RA, MA, RB, MB);
        assertNotNull(retorno);
	}

    @Test (expected = NegativeArraySizeException.class)
    public void tf02(){
        M = -1;
		Game game = new Game();
		String retorno = game.runGame(M, N, X, Y, RA, MA, RB, MB);
	}

    @Test
    public void tf03(){
        M = 2;
        X = -1;
		Game game = new Game();
		String retorno = game.runGame(M, N, X, Y, RA, MA, RB, MB);
        assertNull(retorno);
	}
}
