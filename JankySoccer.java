import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class JankySoccer extends Game{ 
    SpriteBatch spriteBatch;
    FitViewport viewport;
    public BitmapFont font;

    @Override
    public void create() {
        
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        font = new BitmapFont();
        font.setUseIntegerPositions(false);
		font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        super.render();
    }
}