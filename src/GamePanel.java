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

    final int SWidth = (int) (4/3f * tSize * MSC);
    final int SHeight = (int) (4/3f * tSize * MSR);

    int FPS = 60;

    Thread gameThread;
    KeyHandler KeyH = new KeyHandler();

    boolean PlayerTurn = true;
    Player player = new Player(100, 100, 4);

    public GamePanel() {

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle the mouse click event
                int x = e.getX();
                int y = e.getY();
                System.out.println("Mouse clicked at coordinates: (" + x + ", " + y + ")");
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println("Mouse moved to: (" + x + ", " + y + ")");
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

    public void startGT() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInt = 1000000000f / FPS;
        double nextdraw = System.nanoTime() + drawInt;


        while (gameThread != null) {
            // 1,000,000,000 = 1 second
            // long currentTime = System.nanoTime();

            update();
            repaint();

            try {
                double remainingTime = nextdraw - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextdraw += drawInt;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (PlayerTurn) {

        }

        if (KeyH.up) {
            player.y -= player.Speed;
        }
        if (KeyH.down) {
            player.y += player.Speed;
        }
        if (KeyH.left) {
            player.x -= player.Speed;
        }
        if (KeyH.right) {
            player.x += player.Speed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        g2.fillRect(player.x, player.y, tSize, tSize);

        g2.dispose();
    }
}
