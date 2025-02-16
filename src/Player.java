import java.util.ArrayList;

public class Player extends Entity {
    public int NextX;
    public int NextY;
    public boolean Allowed = false;
    ArrayList<Upgrade> AllAtt = new ArrayList<>();
    ArrayList<Upgrade> Deck;
    ArrayList<Upgrade> Hand = new ArrayList<>();
    int HandSize = 3;

    public Player(int a, int b, int c) {
        super(a, b, c);
        Name = "Player";

        NextX = a;
        NextY = b;
        

        // Starting Cards
        /*
        AllAtt.add(new P1());
        AllAtt.add(new P1());
        AllAtt.add(new P1());
        AllAtt.add(new P2());
        AllAtt.add(new P2());
        AllAtt.add(new P3());
        AllAtt.add(new P4());
        */
        AllAtt.add(new P7());
        AllAtt.add(new P7());
        AllAtt.add(new P7());
        AllAtt.add(new P10());
        AllAtt.add(new P9());
        AllAtt.add(new P9());

        ShuffleDeck();
        Good = true;
    }

    public void AddBuffs() {
        // Load Base Stats
        Roboness = 0;
        MaxHP = 100;
        Damage = 10;
        Speed = 200;
        Range = 1;

        for (int x = 0; x < AllAtt.size(); x++) {
            Roboness += AllAtt.get(x).Robo;
            MaxHP += AllAtt.get(x).ExtraHP;
            Damage += AllAtt.get(x).ExtraDamage;
            Speed += AllAtt.get(x).Speed;
            Range += AllAtt.get(x).Range;
        }
        Health = MaxHP;
    }

    public void ShuffleDeck() {
        Hand = new ArrayList<>();
        Deck = (ArrayList<Upgrade>) AllAtt.clone();
        System.out.println("Shuffle Deck");
        DrawCards(Math.min(Deck.size(), HandSize));
    }

    public void RemoveHand() {
        Hand = new ArrayList<>();
        if (HandSize > Deck.size()) {
            ShuffleDeck();
        } else {
            DrawCards(HandSize);
        }
    }

    public void DrawCards(int Amount) {
        for (int x = 0; x < Amount; x++) {
            int R = (int) (Math.random() * Deck.size());
            if (!Deck.get(R).Att.Action) {
                Deck.remove(R);
                x--;
                continue;
            }
            Hand.add(Deck.get(R));
            Deck.remove(R);
        }
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
