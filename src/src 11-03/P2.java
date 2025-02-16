import java.awt.*;

public class P2 extends Upgrade {
    /*
    public int Robo;
    public int ExtraHP;
    public int ExtraDamage;
    public int Range;
    public int Speed;
     */
    P2() {
        // Absorb
        Att = new Style() {
            @Override
            public void Pain(Entity Self, float Amount) {
                for (int x = 0; x < GamePanel.Units.size(); x++) {
                    if (Self == GamePanel.Units.get(x)) {
                        continue;
                    }

                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units.get(x).x - Self.x, 2) + Math.pow(GamePanel.Units.get(x).y - Self.y, 2));
                    if (TDis <= 140 + 10 * Range) {
                        GamePanel.Units.get(x).Dam(Self, Math.max((Self.Hurt - Self.Health) * (Self.Damage / 10f), 0));
                    }
                }
            }
            @Override
            public void WhenAttacked(Entity Self, Entity Enemy, int Damage) {
                Self.Health += Damage * .75f;
            }
            @Override
            public void Attack(Entity Self, int[] TargetPos, float Range) {}
        };
        Att.Name = "Absorb";
        Att.Action = true;
    }

    @Override
    public void drawAttack(Graphics2D g2) {
        g2.setColor(Color.MAGENTA);
        g2.drawOval(GamePanel.player.NextX - (int) (140 + 10 * GamePanel.player.Range), GamePanel.player.NextY - (int) (140 + 10 * GamePanel.player.Range), (int) (2 * 140 + 10 * GamePanel.player.Range), (int) (2 * 140 + 10 * GamePanel.player.Range));
    }

    // Add Methods and / or Special Attacks
}
