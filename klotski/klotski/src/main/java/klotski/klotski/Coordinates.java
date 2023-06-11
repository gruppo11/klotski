package klotski.klotski;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void incX(int inc){
        x += inc;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incY(int inc){
        y += inc;
    }

    @Override
    public boolean equals(Object obj){
        if(!obj.getClass().equals(this.getClass()))
            return false;

        return ((Coordinates) obj).getX() == x && ((Coordinates) obj).getY() == y;
    }
}
