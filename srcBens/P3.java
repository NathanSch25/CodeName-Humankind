import java.awt.*;

public class P3 extends Upgrade {
    /*
    public int Robo;
    public int ExtraHP;
    public int ExtraDamage;
    public int Range;
    public int Speed;
     */
    P3() {
        // Punch Attack
        Att = new Style() {
            @Override
            public void Pain(Entity Self, float Amount) {}
            @Override
            public void WhenAttacked(Entity Self, Entity Enemy, int Range) {}
            @Override
            public void Attack(Entity Self, int[] TargetPos, float Range) {
                for (int x = 0; x < GamePanel.Units.size(); x++) {
                    if (Self == GamePanel.Units.get(x) || (Self != GamePanel.player && Self.Good == GamePanel.Units.get(x).Good)) {
                        continue;
                    }

                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units.get(x).x - Self.x, 2) + Math.pow(GamePanel.Units.get(x).y - Self.y, 2));
                    if (TDis <= 200 * Self.Range) {
                        GamePanel.Units.get(x).Dam(Self, Self.Damage / 2f);
                        GamePanel.Units.get(x).StunEnemy(2);
                    }
                }

                if (Self != GamePanel.player && !Self.Good) {
                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.player.x - TargetPos[0], 2) + Math.pow(GamePanel.player.y - TargetPos[1], 2));
                    if (TDis <= 200 * Self.Range) {
                        GamePanel.player.Dam(Self, Self.Damage / 2f);
                        GamePanel.player.StunEnemy(2);
                    }
                }

            }
        };
        Att.Name = "Stunning Kick";
        Att.Action = true;
    }
    @Override
    public void drawAttack(Graphics2D g2) {
        g2.setColor(Color.MAGENTA);
        g2.drawOval(GamePanel.player.NextX - (int) (200 * GamePanel.player.Range), GamePanel.player.NextY - (int) (200 * GamePanel.player.Range), (int) (2 * 200 * GamePanel.player.Range), (int) (2 * 200 * GamePanel.player.Range));
    }

    // Add Methods and / or Special Attacks
}
