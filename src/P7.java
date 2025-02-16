import java.awt.*;

public class P7 extends Upgrade {
    /*
    public int Robo;
    public int ExtraHP;
    public int ExtraDamage;
    public int Range;
    public int Speed;
     */
    P7() {
        Robo = 10;
        Range = .2f;
        Speed = 25;

        // Punch Attack
        Att = new Style() {
            @Override
            public void Pain(Entity Self, float Amount) {}
            @Override
            public void WhenAttacked(Entity Self, Entity Enemy, int Range) {}
            @Override
            public void Attack(Entity Self, int[] TargetPos, float Range) {
                Entity Closest = null;
                float Dis = -1;
                for (int x = 0; x < GamePanel.Units.size(); x++) {
                    if (Self == GamePanel.Units.get(x)) {
                        continue;
                    }

                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units.get(x).x - TargetPos[0], 2) + Math.pow(GamePanel.Units.get(x).y - TargetPos[1], 2));
                    if (TDis <= 300 * Range) {
                        if (Dis < TDis || Dis == -1) {
                            Closest = GamePanel.Units.get(x);
                            Dis = TDis;
                        }
                    }
                }

                if (Closest != null) {
                    Closest.Dam(Self, Self.Damage * 1.5f);
                }
            }
        };
        Att.Name = "Spear Throw";
        Att.Action = true;
    }
    @Override
    public void drawAttack(Graphics2D g2) {
        Entity Closest = null;
        float Dis = -1;
        for (int x = 0; x < GamePanel.Units.size(); x++) {
            float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units.get(x).x - GamePanel.player.NextX, 2) + Math.pow(GamePanel.Units.get(x).y - GamePanel.player.NextY, 2));
            if (TDis <= 300 * GamePanel.player.Range) {
                if (Dis < TDis || Dis == -1) {
                    Closest = GamePanel.Units.get(x);
                    Dis = TDis;
                }
            }
        }

        g2.setColor(Color.MAGENTA);
        if (Closest != null) {
            g2.drawLine(GamePanel.player.NextX, GamePanel.player.NextY, Closest.x, Closest.y);
        }
        g2.drawOval(GamePanel.player.NextX - (int) (300 * GamePanel.player.Range), GamePanel.player.NextY - (int) (300 * GamePanel.player.Range), (int) (2 * 300 * GamePanel.player.Range), (int) (2 * 300 * GamePanel.player.Range));
    }


}
