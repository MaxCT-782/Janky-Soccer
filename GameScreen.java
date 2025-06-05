import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
	final JankySoccer game;
    
    // scoreboard mechanics
    double secondsLeft;
    int seconds;
    int score1;
    int score2;

	Texture backgroundTexture;
    Texture ballTexture;
    Texture player1Texture;
    Texture player2Texture;
    //Music music;
    // SpriteBatch spriteBatch;
    // FitViewport viewport;
    Sprite ballSprite;
    Sprite player1Sprite;
    Sprite player2Sprite;
    Vector2 touchPos;
    float dropTimer;
    Rectangle ballRectangle;
    Rectangle player1Rectangle;
    Rectangle player2Rectangle;
    float ballSpeedx;
    float ballSpeedY;
    Ball ball;
    Circle ballCircle;
    // public BitmapFont font;
    // public SpriteBatch batch;

	public GameScreen(final JankySoccer game) {
		this.game = game;

        backgroundTexture = new Texture("assets/field.jpeg");
        ballTexture = new Texture("assets/Soccer_Ball.png");
        player1Texture = new Texture("assets/soccer-player1.png");
        player2Texture = new Texture("assets/football-player.png");
        
        //music = Gdx.audio.newMusic(Gdx.files.internal("assets/music.mp3"));
        
        // spriteBatch = new SpriteBatch();
        // viewport = new FitViewport(8, 5);
        
        ballSprite = new Sprite(ballTexture);
        ballSprite.setPosition(3.87f,2.4f);
        ballSprite.setSize(0.25f, 0.25f);
        
        touchPos = new Vector2();
        
        player1Sprite = new Sprite(player1Texture);
        player1Sprite.setPosition(1,2);
        player1Sprite.setSize(0.9f, 0.9f);
        player2Sprite = new Sprite(player2Texture);
        player2Sprite.setPosition(6,2);
        player2Sprite.setSize(1, 1);
        
        ballRectangle = new Rectangle();
        player1Rectangle = new Rectangle();
        player2Rectangle = new Rectangle();
        ballCircle = new Circle();
        rect();

        ball = new Ball();

        // setting the timer
        secondsLeft = 90;
        // music.setLooping(true);
        // music.setVolume(.5f);
        // music.play();

        // font = new BitmapFont();
        // font.setUseIntegerPositions(false);
		// font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		// music.play();
	}

	@Override
	public void render(float delta) {
		input();
		logic();
		draw();
	}

	 private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            reset();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(player2Sprite.getX()<(7.5))
                player2Sprite.translateX(speed * delta);
			player2Rectangle.setPosition(player2Sprite.getX(),player2Rectangle.getY());
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if(player2Sprite.getX()>(-0.1))
                player2Sprite.translateX(-speed * delta);
			player2Rectangle.setPosition(player2Sprite.getX(),player2Rectangle.getY());
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(player2Sprite.getY()>(-0.1))
                player2Sprite.translateY(-speed * delta);
			player2Rectangle.setPosition(player2Sprite.getX(),player2Rectangle.getY());
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(player2Sprite.getY()<(4.1))
                player2Sprite.translateY(speed * delta);
			player2Rectangle.setPosition(player2Sprite.getX(),player2Rectangle.getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if(player1Sprite.getX()>(-0.4))
                player1Sprite.translateX(-speed * delta);
			player1Rectangle.setPosition(player1Sprite.getX(),player1Rectangle.getY());
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if(player1Sprite.getY()<(4.1))
                player1Sprite.translateY(speed * delta);
			player1Rectangle.setPosition(player1Sprite.getX(),player1Rectangle.getY());
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if(player1Sprite.getY()>(-0.1))
                player1Sprite.translateY(-speed * delta);
			player1Rectangle.setPosition(player1Sprite.getX(),player1Rectangle.getY());
		} 
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if(player1Sprite.getX()<(7.2))
                player1Sprite.translateX(speed * delta);
			player1Rectangle.setPosition(player1Sprite.getX(),player1Rectangle.getY());
        }

    }

	private void logic() {
		float worldWidth = game.viewport.getWorldWidth();
		float worldHeight = game.viewport.getWorldHeight();
		float delta = Gdx.graphics.getDeltaTime();
		float speed2 = 20f;
        /* bunch of variables for the triangle math
         the angles will be found using tangents
         the numbers 1 and 2 indicate which player the number is relevant to
         cx means center x, and cy means center y (taken from the center point of the sprites)
         b for bx and by mean ball x and ball y, taken the same way
         x/yInp (x/y input) 
        angles are the an
        */
        double angle;
        double angle2;
        double ballAngle;
        
        double cx1 = player1Sprite.getX();
        double cx2 = player2Sprite.getX();
        double cy1 = player1Sprite.getY();
        double cy2 = player2Sprite.getY();
        double bx = ballSprite.getX();
        double by = ballSprite.getY();
        double xInp;
        double yInp;
        
        // Timer stuff

        secondsLeft -= delta;
        seconds = (int)(secondsLeft);

        // seconds.tick(delta);
        // secondsLeft = seconds.getElapsed();
        // System.out.println(secondsLeft);

        
                
        // rectangle stuff
		rect();
        
        /* how the math works
        With the center points of each rectangle, a right triangle can be formed
        Using the arctan (opposite/adjacent) of the triangle lengths, the angle from center 1 to center 2 is found
        Using cos for x, and sin for y, the movement becomes more angled and contact specific
        */

        
        // player on player collision
		if(player1Rectangle.overlaps(player2Rectangle)){
			//System.out.println("Im overlapping it");


                xInp = cx1-cx2;
                yInp = cy1-cy2;
                angle = Math.atan(xInp/yInp);
                angle2 = angle + Math.PI;
                float x1 = speed2 * delta * (float)(Math.cos(angle));
                if(cx1>cx2){
                    player1Sprite.translateX(x1);
                    player2Sprite.translateX(-x1);
                }else{
                    player1Sprite.translateX(-x1);
                    player2Sprite.translateX(x1);
                }
                float y1 = speed2 * delta * (float)(Math.sin(angle));
                player1Sprite.translateY(y1);
                player2Sprite.translateY(-y1);
                // if(player1Sprite.getX()>player2Sprite.getX()){
                    
                //     player1Sprite.translateX(speed2 * delta);
                //     player2Sprite.translateX(-speed2 * delta);
                //     // rect();
                // }else{
                //     player1Sprite.translateX(-speed2 * delta);
                //     player2Sprite.translateX(speed2 * delta);
                //     // rect();
                // }
                // if(player1Sprite.getY()>player2Sprite.getY()){
                //     player1Sprite.translateY(speed2 * delta);
                //     player2Sprite.translateY(-speed2 * delta);
                //     // rect();
                // }else{
                //     player1Sprite.translateY(-speed2 * delta);
                //     player2Sprite.translateY(speed2 * delta);
                //     // rect();
                // }
		}
        // if player 1 hits the ball
        if(Intersector.overlaps(ballCircle, player1Rectangle)){
            ball.speedUp();
            xInp = cx1-bx;
            yInp = cy1-by;
            ballAngle = Math.atan(xInp/yInp);
            float x = ball.getSpeed() * delta * (float)(Math.cos(ballAngle));
            float y = ball.getSpeed() * delta * (float)(Math.sin(ballAngle));
            // if(cx1>bx){
            //     x= -x;
            // }
            if(xInp>0&&yInp>0){
                ballSpeedx = -x;
                ballSpeedY = -y;
                // ball.setX(false);
                // ball.setY(false);
            }else if(xInp>0&&yInp<0){
                ballSpeedx = -x;
                ballSpeedY = -y;
                // ball.setX(false);
                // ball.setY(true);
            }else if(xInp<0&&yInp>0){
                ballSpeedx = x;
                ballSpeedY = y;
                // ball.setX(true);
                // ball.setY(false);
            } else{
                ballSpeedx = x;
                ballSpeedY = y;
                // ball.setX(true);
                // ball.setY(true);
            }
            
            if(ballSpeedY>0){
                ball.setY(true);
            }else{
                ball.setY(false);
            }
            if(ballSpeedx>0){
                ball.setX(true);
            }else{
                ball.setX(false);
            }
            // if(player1Sprite.getX()>ballSprite.getX()){
            //     //ballSprite.translateX(-speed2 * delta);
            //     ball.setX(false);
            //     // rect();
            // }else if(player1Sprite.getX()<ballSprite.getX()){
            //     //ballSprite.translateX(speed2 * delta);
            //     ball.setX(true);
            //     // rect();
            // }
            // if(player1Sprite.getY()>ballSprite.getY()){
            //     //ballSprite.translateY(-speed2 * delta);
            //     ball.setY(false);
            //     // rect();
            // }else if(player1Sprite.getY()<ballSprite.getY()){
            //     //ballSprite.translateY(speed2 * delta);
            //     ball.setY(true);
            //     // rect();
            // }
        }
        // if player 2 hits the ball
        if(Intersector.overlaps(ballCircle, player2Rectangle)){
            ball.speedUp();
            xInp = cx2-bx;
            yInp = cy2-by;
            ballAngle = Math.atan(xInp/yInp);
            float x = ball.getSpeed() * delta * (float)(Math.cos(ballAngle));
            float y = ball.getSpeed() * delta * (float)(Math.sin(ballAngle));
            // if(x>0){
            //     ball.setX(true);
            // }else{
            //     ball.setX(false);
            // }
            if(xInp>0&&yInp>0){
                ballSpeedx = -x;
                ballSpeedY = -y;
                // ball.setX(false);
                // ball.setY(false);
            }else if(xInp>0&&yInp<0){
                ballSpeedx = -x;
                ballSpeedY = -y;
                // ball.setX(false);
                // ball.setY(true);
            }else if(xInp<0&&yInp>0){
                ballSpeedx = x;
                ballSpeedY = y;
                // ball.setX(true);
                // ball.setY(false);
            } else{
                ballSpeedx = x;
                ballSpeedY = y;
                // ball.setX(true);
                // ball.setY(true);
            }
            
            if(ballSpeedY>0){
                ball.setY(true);
            }else{
                ball.setY(false);
            }
            if(ballSpeedx>0){
                ball.setX(true);
            }else{
                ball.setX(false);
            }
            
            
            // if(player2Sprite.getX()>ballSprite.getX()){
            //     //ballSprite.translateX(-speed2 * delta);
            //     ball.setX(false);
            //     // rect();
            // }else if(player2Sprite.getX()<ballSprite.getX()){
            //     //ballSprite.translateX(speed2 * delta);
            //     ball.setX(true);
            //     // rect();
            // }
            // if(player2Sprite.getY()>ballSprite.getY()){
            //     //ballSprite.translateY(speed2 * delta);
            //     ball.setY(false);
            //     // rect();
            // }else if(player2Sprite.getY()<ballSprite.getY()){
            //     //ballSprite.translateY(speed2 * delta);
            //     ball.setY(true);
            //     // rect();
            // }
        }
        ballSprite.translateX(ballSpeedx * delta);
        ballSprite.translateY(ballSpeedY * delta);
        if(ball.getX()==true){
            if(ballSprite.getX()<7.8){
                // ballSprite.translateX(ballSpeedx * delta);
            }else{
                // ballSprite.translateX(-ballSpeedx * delta);
                ballSpeedx = -ballSpeedx;
                ball.setX(false);
                ball.speedDown();
            }
        }else{
            if(ballSprite.getX()>0.2){
                // ballSprite.translateX(ballSpeedx * delta);
            }else{
                // ballSprite.translateX(-ballSpeedx * delta);
                ballSpeedx = -ballSpeedx;
                ball.setX(true);
                ball.speedDown();
            }
        }
        if(ball.getY()==true){
            if(ballSprite.getY()<4.8){
                // ballSprite.translateY(ballSpeedY * delta);
            }else{
                // ballSprite.translateY(-ballSpeedY * delta);
                ballSpeedY = -ballSpeedY;
                ball.setY(false);
                ball.speedDown();
            }
        }else{
            if(ballSprite.getY()>0.2){
                // ballSprite.translateY(ballSpeedY * delta);
            }else{
                // ballSprite.translateY(-ballSpeedY * delta);
                ballSpeedY = -ballSpeedY;
                ball.setY(true);
                ball.speedDown();
            }
        }
        if(ballSprite.getX()>8||ballSprite.getX()<0||ballSprite.getY()<0||ballSprite.getY()>5){
        //     ballSprite.setPosition(3.87f,2.4f);
        //     ball.setSpeed(0);
        //     ballSpeedY=0;
        //     ballSpeedx=0;
        }
        // player1Rectangle.set(player1Sprite.getX(),player1Rectangle.getY(),player1Sprite.getWidth(),player1Sprite.getHeight());
		// player2Rectangle.set(player2Sprite.getX(),player2Rectangle.getY(),player2Sprite.getWidth(),player2Sprite.getHeight());
        // ballRectangle.set(ballSprite.getX(),ballSprite.getY(), ballSprite.getWidth(), ballSprite.getHeight());
	}

	private void draw() {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.spriteBatch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.spriteBatch.begin();

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        game.spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        ballSprite.draw(game.spriteBatch);
        player1Sprite.draw(game.spriteBatch);
        player2Sprite.draw(game.spriteBatch);

        game.spriteBatch.end();
    }

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, true);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		backgroundTexture.dispose();
	}
    public void rect(){
        player1Rectangle.set(player1Sprite.getX(),player1Sprite.getY(),0.2f,0.6f);//player1Sprite.getWidth(),player1Sprite.getHeight());
		player2Rectangle.set(player2Sprite.getX(),player2Sprite.getY(),0.2f,0.6f);//player2Sprite.getWidth(),player2Sprite.getHeight());
        ballCircle.set(ballSprite.getX(),ballSprite.getY(), .1f);
        ballRectangle.set(ballSprite.getX(),ballSprite.getY(),0.2f,0.2f);
    }
    public void reset(){
        ballSprite.setPosition(3.87f,2.4f);
        ball.setSpeed(0);
        player1Sprite.setPosition(1,2);
        player2Sprite.setPosition(6,2);
        ballSpeedY=0;
        ballSpeedx=0;
    }
}