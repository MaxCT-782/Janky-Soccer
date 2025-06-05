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
        speed+=3f;
    }
    public void speedDown(){
        speed-=2f;
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
