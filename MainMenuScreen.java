import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

	final JankySoccer game;

	public MainMenuScreen(final JankySoccer game) {
		this.game = game;
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
		game.font.draw(game.spriteBatch, "Welcome to Janky Soccer!!! ", 1, 1.5f);
		game.font.draw(game.spriteBatch, "Tap anywhere to begin!", 1, 1);
		game.spriteBatch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
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
