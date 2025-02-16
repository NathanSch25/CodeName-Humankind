import java.util.ArrayList;

public class Player extends Entity {
    public int NextX;
    public int NextY;
    public boolean Allowed = false;
    ArrayList<Style> ba = new ArrayList<>();

    public Player(int a, int b, int c) {
        super(a, b, c);
        Name = "Player";

        NextX = a;
        NextY = b;
    }

    public void SetPos(int xPos, int yPos) {
        if (ValidPos(xPos, yPos)) {
            System.out.println("Position Set");
            NextX = xPos;
            NextY = yPos;
            Allowed = true;
        } else {
            System.out.println("Position Invalid");
        }
    }

    public boolean ValidPos(int xPos, int yPos) {
        return Math.sqrt(Math.pow(x - xPos, 2) + Math.pow(y - yPos, 2)) < Speed;
    }

    public void Move() {
        x = NextX;
        y = NextY;
        Allowed = false;
    }
}
