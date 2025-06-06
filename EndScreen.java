import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class EndScreen implements Screen {

	final JankySoccer game;
    int winner;

	public EndScreen(final JankySoccer game) {
		this.game = game;
        winner = game.getWin();
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.BLACK);

		game.viewport.apply();
		game.spriteBatch.setProjectionMatrix(game.viewport.getCamera().combined);

		game.spriteBatch.begin();
		//draw text. Remember that x and y are in meters
        
		if(winner==1){
            game.font.draw(game.spriteBatch, "Player 1 WINS ", 1, 1.5f);
        }else if(winner==2){
            game.font.draw(game.spriteBatch, "Player 2 WINS ", 1, 1.5f);
        }else{
            game.font.draw(game.spriteBatch, "DRAW LADS", 1, 1.5f);
        }
        game.font.draw(game.spriteBatch, "FINAL SCORE: " + game.getScore(1) + " - " + game.getScore(2),1,2.5f);
        
		game.spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, true);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
		
	}

}