import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class JankySoccer extends Game{ 
    SpriteBatch spriteBatch;
    FitViewport viewport;
    public BitmapFont font;
    public int p1Score;
    public int p2Score;

    @Override
    public void create() {
        
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        p1Score = 0;
        p2Score = 0;

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
    public void score(int p){
        if(p==1){
            p1Score++;
        }else{
            p2Score++;
        }
    }
    public int getWin(){
        if(p1Score>p2Score){
            return 1;
        }else if(p2Score>p1Score){
            return 2;
        }
        return 0;
    }
    public int getScore(int p){
        if(p==1){
            return p1Score;
        }else if(p==2){
            return p2Score;
        }
        return 0;
    }

}