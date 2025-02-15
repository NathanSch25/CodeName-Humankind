import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GamePanel extends JPanel implements Runnable {
    final int ogTS = 16;
    final int scale = 3;

    final int tSize = ogTS * scale;
    final int MSC = 16;
    final int MSR = 12;

    final int SWidth = (int) (4/3f * tSize * MSC); // 1024
    final int SHeight = (int) (4/3f * tSize * MSR); // 768

    int FPS = 60;

    Thread gameThread;
    KeyHandler KeyH = new KeyHandler();

    boolean PlayerTurn = true;
    int PlayerAction = 0;
    public static Player player = new Player(100, 100, 200);
    public static AI[] Units;

    JFrame ActionPage;
    JButton[] ActButtons;
    Level Generator = new Level();
    // Natural is 20x20 Grid
    // Better is a 18x14 Grid

    public GamePanel() {
        Generator.GenerateMap();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (KeyH.Action != 1) {
                    return;
                }
                // Handle the mouse click event
                int x = e.getX();
                int y = e.getY();

                player.SetPos(x, y);
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

    public void startGT(JFrame Acts, JButton[] Butt) {
        for (int x = 0; x < 5; x++) {
            int finalX = x;
            Butt[x].addActionListener(e -> {
                for (int y = 0; y < 5; y++) {
                    Butt[y].setBackground(y == finalX ? Color.yellow : Color.lightGray);
                }
                KeyH.Action = finalX + 2;
                System.out.println("Action " + PlayerAction + "!");
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
        for (int y = 0; y < 5; y++) {
            if (KeyH.Action == 0) {
                ActButtons[y].setBackground(Color.white);
            } else {
                ActButtons[y].setBackground(y + 2 == KeyH.Action ? Color.yellow : Color.lightGray);
            }
        }

        if (!PlayerTurn) {
            player.Move();
            // Use "PlayerAction" Variable
            // Do Player Action
            // Do Ally Move + Action
            // Do Enemy Move + Action

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

        for (int x = 1; x < Generator.Map.size() - 1; x++) {
            for (int y = 1; y < Generator.Map.get(x).length - 1; y++) {
                if (Generator.Map.get(x)[y] == 1) {
                    g2.setColor(Color.BLACK);
                    g2.fillRect((x - 1) * SWidth / (Generator.Map.size() - 2), (y - 1) * SHeight / (Generator.Map.get(x).length - 2), SWidth / (Generator.Map.size() - 2), SHeight / (Generator.Map.get(x).length - 2));
                }
            }
        }

        // Where the player can move
        if (PlayerAction == 1) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillOval(player.x - player.Speed, player.y - player.Speed, player.Speed * 2, player.Speed * 2);
        }

        // Player
        g2.setColor(Color.white);
        g2.fillRect(player.x - tSize / 2, player.y - tSize / 2, tSize, tSize);

        if (player.Allowed) {
            g2.setColor(Color.pink);
            g2.fillRect(player.NextX - tSize / 4, player.NextY - tSize / 4, tSize / 2, tSize / 2);
        }

        g2.dispose();
    }
}
