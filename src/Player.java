public class Player {
    public int x = 100;
    public int y = 100;
    public int Speed = 4;

    public int NextX;
    public int NextY;


    public Player(int a, int b, int c) {
        x = a;
        y = b;
        Speed = c;

        NextX = a;
        NextY = b;
    }

    public boolean ValidPos() {
        return Math.sqrt(Math.pow(x - NextX, 2) + Math.pow(y - NextY, 2)) < Speed;
    }
}
