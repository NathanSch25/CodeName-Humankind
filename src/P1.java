import java.awt.*;

public class P1 extends Upgrade {
    /*
    public int Robo;
    public int ExtraHP;
    public int ExtraDamage;
    public int Range;
    public int Speed;
     */
    P1() {
        // Punch Attack
        Att = new Style() {
            @Override
            public void Pain(Entity Self, float Amount) {}
            @Override
            public void WhenAttacked(Entity Self, Entity Enemy, int Damage) {}
            @Override
            public void Attack(Entity Self, int[] TargetPos, float Range) {
                Entity Closest = null;
                float Dis = -1;
                for (int x = 0; x < GamePanel.Units.size(); x++) {
                    if (Self == GamePanel.Units.get(x) || (Self != GamePanel.player && Self.Good == GamePanel.Units.get(x).Good)) {
                        continue;
                    }

                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units.get(x).x - TargetPos[0], 2) + Math.pow(GamePanel.Units.get(x).y - TargetPos[1], 2));
                    if (TDis <= 100 * Self.Range) {
                        if (Dis > TDis || Dis == -1) {
                            Closest = GamePanel.Units.get(x);
                            Dis = TDis;
                        }
                    }
                }

                if (Self != GamePanel.player && !Self.Good) {
                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.player.x - TargetPos[0], 2) + Math.pow(GamePanel.player.y - TargetPos[1], 2));
                    if (TDis <= 100 * Self.Range) {
                        if (Dis > TDis || Dis == -1) {
                            Closest = GamePanel.player;
                        }
                    }
                }

                if (Closest != null) {
                    Closest.Dam(Self);
                } else {
                    System.out.println("No Enemy in Range of " + Self.Name);
                }
            }
        };
        Att.Name = "Punch";
        Att.Action = true;
    }

    @Override
    public void drawAttack(Graphics2D g2) {
        Entity Closest = null;
        float Dis = -1;
        for (int x = 0; x < GamePanel.Units.size(); x++) {
            float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units.get(x).x - GamePanel.player.NextX, 2) + Math.pow(GamePanel.Units.get(x).y - GamePanel.player.NextY, 2));
            if (TDis <= 100 * GamePanel.player.Range) {
                if (Dis > TDis || Dis == -1) {
                    Closest = GamePanel.Units.get(x);
                    Dis = TDis;
                }
            }
        }

        g2.setColor(Color.MAGENTA);
        if (Closest != null) {
            g2.drawLine(GamePanel.player.NextX, GamePanel.player.NextY, Closest.x, Closest.y);
        }
        g2.drawOval(GamePanel.player.NextX - (int) (100 * GamePanel.player.Range), GamePanel.player.NextY - (int) (100 * GamePanel.player.Range), (int) (2 * 100 * GamePanel.player.Range), (int) (2 * 100 * GamePanel.player.Range));
    }

    // Add Methods and / or Special Attacks
}
