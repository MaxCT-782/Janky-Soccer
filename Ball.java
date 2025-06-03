public class Ball {
    float speed;
    boolean y;
    boolean x;
    public Ball(){
        speed = 0;
    } 
    public void setSpeed(float f){
        speed = f;
    }
    public float getSpeed(){
        return speed;
    }
    public void speedUp(){
        speed++;
    }
    public void speedDown(){
        speed--;
    }
    public void setY(boolean b){
        y=b;
    }
    public boolean getY(){
        return y;
    }
    public void setX(boolean b){
        x=b;
    }
    public boolean getX(){
        return x;
    }
}
