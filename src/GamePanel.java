import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    final int ogTS = 16;
    final int scale = 3;

    final int tSize = ogTS * scale; // Player Scale
    final int MSC = 16;
    final int MSR = 12;

    final int SWidth = (int) (4/3f * tSize * MSC); // 1024
    final int SHeight = (int) (4/3f * tSize * MSR); // 768

    int FPS = 60;

    Thread gameThread;
    KeyHandler KeyH = new KeyHandler();

    boolean PlayerTurn = true;
    int PlayerAction = 0;
    public static Player player = new Player(0, 0, 200);
    public static ArrayList<AI> Units; // Give Value

    JFrame ActionPage;
    JButton[] ActButtons;
    Level Generator = new Level();
    // Natural is 20x20 Grid
    // Better is a 18x14 Grid

    public GamePanel() {
        LoadLevel();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                if (KeyH.Action != 1) {
//                    return;
//                }
                // Handle the mouse click event
                int x = e.getX();
                int y = e.getY();

                player.SetPos(x, y);
                String Path = Generator.SimpleSearch(Convert(player), Convert(x , y)).toString();
                int[] Location = STI(new int[]{(1 + Generator.Width * player.x / SWidth), (1 + Generator.Height * player.y / SHeight)}, Path, player.Speed);
                System.out.println("End Pos : " + Location[0] + ", " + Location[1]);
                if (Location != player.GetPosition()) {
                    player.SetPos(Location[0], Location[1]);
                }
                // System.out.println("Mouse clicked at coordinates: (" + x + ", " + y + ")");
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // int x = e.getX();
                // int y = e.getY();
                // System.out.println("Mouse moved to: (" + x + ", " + y + ")");
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Handle mouse dragged events if needed
            }
        });

        this.setPreferredSize(new Dimension(SWidth, SHeight));
        this.setBackground(Color.green);
        this.addKeyListener(KeyH);
        this.setFocusable(true);
    }

    public void LoadLevel() {
        Generator.GenerateMap();
        player.AddBuffs();
        int S = (int) (Math.random() * 2) + 4;
        Units = new ArrayList<>();
        for (int x = 0; x < S; x++) {
            Units.add(new AI((int) (Math.random() * SWidth), (int) (Math.random() * SHeight), 100));
        }
    }

    public void startGT(JFrame Acts, JButton[] Butt) {
        for (int x = 0; x < 5; x++) {
            int finalX = x;
            if (x < player.Hand.size()) {
                Butt[x].setText(player.Hand.get(x).Att.Name);
                Butt[x].setVisible(true);
            } else {
                Butt[x].setVisible(false);
            }
            Butt[x].addActionListener(e -> {
                for (int y = 0; y < 5; y++) {
                    Butt[y].setBackground(y == finalX ? Color.yellow : Color.lightGray);
                }
                KeyH.Action = finalX + 1;
                System.out.println("Action " + KeyH.Action + "!");
            });
        }
        ActionPage = Acts;
        ActButtons = Butt;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInt = 1000000000f / FPS;
        double nextDraw = System.nanoTime() + drawInt;

        while (gameThread != null) {
            // 1,000,000,000 = 1 second
            // long currentTime = System.nanoTime();

            update();
            repaint();

            try {
                double remainingTime = nextDraw - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDraw += drawInt;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (player.Stun > 0) {
            KeyH.Action = 0;
        }
        for (int y = 0; y < 5; y++) {
            if (KeyH.Action == 0) {
                ActButtons[y].setBackground(Color.white);
            } else {
                ActButtons[y].setBackground(y + 1 == KeyH.Action ? Color.yellow : Color.lightGray);
            }
        }

        if (!PlayerTurn) {
            // Player Move
            player.Move();

            // Player Action
            try {
                System.out.println("" + player.Hand);
                player.Active = player.Hand.remove(PlayerAction - 1);
                player.Active.Att.Attack(player, player.GetPosition(), player.Range);
                player.RemoveHand();
                for (int x = 0; x < ActButtons.length; x++) {
                    if (x < player.Hand.size()) {
                        ActButtons[x].setText(player.Hand.get(x).Att.Name);
                        ActButtons[x].setVisible(true);
                    } else {
                        ActButtons[x].setVisible(false);
                    }
                }
            } catch (IndexOutOfBoundsException ignored) {
                player.ShuffleDeck();
                ignored.printStackTrace();
                //System.out.println("Nope, fuck you");
            }
            
            // Do Ally Move + Action
            //Geting cordants to move to
            int j;
            for (j = 0; j < GamePanel.Units.size(); j++){
                if (!Units.get(j).Good){
                    break;
                }
            }
            Level level = new Level();
            for(int i = 0; i < GamePanel.Units.size(); i++){
                if (Units.get(i).Good){
                    String starting = intToCords(i);
                    String enimie = intToCords(j);
                    String cordsL = "";
                    for(String x : level.SimpleSearch(starting, enimie)){
                        cordsL += ',' + x;
                    }
                    
                    //moving to found cordnats
                    int[] cords = cordsToInt(i, cordsL);
                    GamePanel.Units.get(i).x = cords[0];
                    GamePanel.Units.get(i).y = cords[1];
                    System.out.println("teammate moved to " + cords[0] + ", " + cords[1]);
                }
                
            } 
    
            // Do Enemy Move + Action
            //geting cordanats to move to
            for (j = 0; j < GamePanel.Units.size(); j++){
                if (!Units.get(j).Good){
                    break;
                }
            }
            for(int i = 0; i < GamePanel.Units.size(); i++){
                if (!(Units.get(i).Good)){
                    String starting = intToCords(i);
                    String enimie = intToCords(j);
                    String cordsL = "";

                    for(String x : level.SimpleSearch(starting, enimie)){
                        cordsL += x;
                        System.err.println("Our Search Results: "+x);
                    }
                    
                    if("".equals(cordsL)){continue;}
                    
                    //moving to found cordnats
                    int[] cords = cordsToInt(i, cordsL);
                    GamePanel.Units.get(i).x = cords[0];
                    GamePanel.Units.get(i).y = cords[1];
                    System.out.println("enemy moved to " + cords[0] + ", " + cords[1]);
                }
                
            } 


            for (int x = 0; x < Units.size(); x++) {
                if (Units.get(x).IfHurt()) {
                    Units.remove(x);
                    x--;
                }
            }
            player.IfHurt();
            PlayerTurn = true;
            return;
        }
        if (KeyH.FinishTurn) {
            KeyH.Action = 0;
            PlayerTurn = false;
            KeyH.FinishTurn = false;
            return;
        }
        PlayerAction = KeyH.Action;

        if (KeyH.up) {
            player.y -= player.Speed / 100;
        }
        if (KeyH.down) {
            player.y += player.Speed / 100;
        }
        if (KeyH.left) {
            player.x -= player.Speed / 100;
        }
        if (KeyH.right) {
            player.x += player.Speed / 100;
        }
    }

    public void GiveHand() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Where the player can move
        if (player.Stun == 0) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillOval(player.x - player.Speed, player.y - player.Speed, player.Speed * 2, player.Speed * 2);
        }

        // Map (AKA Walls)
        for (int x = 1; x < Generator.Map.size() - 1; x++) {
            for (int y = 1; y < Generator.Map.get(x).length - 1; y++) {
                if (Generator.Map.get(x)[y] == 1) {
                    g2.setColor(Color.BLACK);
                    g2.fillRect((x - 1) * SWidth / Generator.Width, (y - 1) * SHeight / Generator.Height, SWidth / Generator.Width, SHeight / Generator.Height);
                }
            }
        }

        try {
            player.Hand.get(PlayerAction - 1).drawAttack(g2);
        } catch (IndexOutOfBoundsException ignored) {}


        // Player
        g2.setColor(Color.white);
        g2.fillRect(player.x - player.CSize / 2, player.y - player.CSize / 2, player.CSize, player.CSize);

        // Blue Squares
        g2.setColor(Color.blue);
        for (int x = 0; x < Generator.Width; x++) {
            for (int y = 0; y < Generator.Height; y++) {
                g2.fillRect((SWidth * x) / Generator.Width + (SWidth / 4 / Generator.Width), (SHeight * y) / Generator.Height + (SHeight / 4 / Generator.Height), SWidth / 2 / Generator.Width, SHeight / 2 / Generator.Height);
            }
        }

        // All AI characters
        for (int x = 0; x < Units.size(); x++) {
            g2.setColor(Units.get(x).Good ? Color.green : Color.pink);
            g2.fillRect(Units.get(x).x - Units.get(x).CSize / 2, Units.get(x).y - Units.get(x).CSize / 2, Units.get(x).CSize, Units.get(x).CSize);

            if (Units.get(x).Health != Units.get(x).MaxHP + 1) {
                g2.setColor(Color.BLACK);
                g2.fillRect(Units.get(x).x - Units.get(x).CSize / 2, Units.get(x).y - Units.get(x).CSize / 2 - 10, Units.get(x).CSize, 10);

                g2.setColor(Color.red);
                g2.fillRect(Units.get(x).x - Units.get(x).CSize / 2, Units.get(x).y - Units.get(x).CSize / 2 - 10, (int) (Units.get(x).CSize * (Units.get(x).Health / Units.get(x).MaxHP)), 10);
            }
        }

        // Where the player will move to
        if (player.Allowed) {
            g2.setColor(Color.pink);
            g2.fillRect(player.NextX - tSize / 4, player.NextY - tSize / 4, tSize / 2, tSize / 2);
        }

        g2.dispose();
    }

    public String Convert(Entity Obj) {
        return Convert(Obj.x, Obj.y);
    }
    public String Convert (int x, int y) {
        return "" + (1 + Generator.Width * x / SWidth) + "," + (1 + Generator.Height * y / SHeight);
    }
    public int[] STI(int[] Start, String Path, float Speed) {
        Path = Path.substring(1, Path.length() - 1);
        if (Objects.equals(Path, "")) {
            return Start;
        }

        float[] Travel = new float[]{(float) SWidth / Generator.Width, (float) SHeight / Generator.Height};
        String[] Values = Path.split(",");
        int c = 0;
        System.out.println("Start Speed : " + Speed);
        while (Speed > 0 && c < Values.length - 2) {
            int Num;
            try {
                Num = Integer.parseInt(Values[c]);
            } catch (NumberFormatException ignored) {
                Num = Integer.parseInt(Values[c].substring(1));
            }
            System.out.println("Before : {" + Start[0] + ", " + Start[1] + "}\nAfter : {" + Num + ", " + Integer.parseInt(Values[c + 1]) + "}");
            Speed -= Math.abs(Start[0] - Num) * Travel[0] + Math.abs(Start[1] - Integer.parseInt(Values[c + 1])) * Travel[1];
            Start = new int[] {Num, Integer.parseInt(Values[c + 1])};
            c += 2;
            System.out.println("New Speed : " + Speed);
        }

        int Num;
        try {
            Num = Integer.parseInt(Values[c]);
        } catch (NumberFormatException ignored) {
            Num = Integer.parseInt(Values[c].substring(1));
        }
        return new int[] {(int) (Num * Travel[0]) - (SWidth / 2 / Generator.Width), (int) (Integer.parseInt(Values[c + 1]) * Travel[1]) - (SHeight / 2 / Generator.Height)};
    }
    public String intToCords(int index){
        String out = String.format("%d, %d", GamePanel.Units.get(index).x, GamePanel.Units.get(index).y);
        return out;
    }
    public int[] cordsToInt(int index, String cords){
        System.err.println("Splitting: "+cords);
        String[] parts = cords.split(", ");
        System.out.println(cords+"   "+parts[0]+" "+parts[1]);
                
        int[] cordsnum = {0, 0};
        cordsnum[0] = Integer.parseInt(parts[0]);
        cordsnum[1] = Integer.parseInt(parts[1]);

        return cordsnum;
    }
}
