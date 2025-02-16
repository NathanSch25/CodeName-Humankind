import java.awt.*;

public class P10 extends Upgrade {
    public int ExtraHP;
    // public int Robo;
    // public int ExtraDamage;
    // public int Range;
    // public int Speed;

    P10() {
        // Robo = 25;
        ExtraHP = -100;
        // ExtraDamage = 20;

        // Kamakasi
        Att = new Style() {
            @Override
            public boolean TakeOver(Entity Self) {
                return ((Self.Hurt - Self.Health) < 20);
            }

            @Override
            public void Pain(Entity Self, float Amount) {
            }

            @Override
            public void WhenAttacked(Entity Self, Entity Enemy, int Range) {
            }

            @Override
            public void Attack(Entity Self, int[] TargetPos, float Range) {
                for (int x = 0; x < GamePanel.Units.size(); x++) {
                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units.get(x).x - Self.x, 2) + Math.pow(GamePanel.Units.get(x).y - Self.y, 2));
                    if (TDis <= 300 * Range) {
                        GamePanel.Units.get(x).Dam(Self, 150);
                    }
                }
            }
        };
        Att.Name = "Kamakasi";
        Att.Action = true;
    }

    @Override
    public void drawAttack(Graphics2D g2) {
        g2.setColor(Color.RED);
        // g2.fillRect(0, GamePanel.player.NextY - (int) (500 * GamePanel.player.Range / 2), 500, (int) (25 * GamePanel.player.Range));
        g2.fillRoundRect(GamePanel.player.NextX - 400, GamePanel.player.NextY - 400, 800, 800, -100, -100);
    }
}