import java.awt.*;

public class P8 extends Upgrade {
    /*
    public int Robo;
    public int ExtraHP;
    public int ExtraDamage;
    public int Range;
    public int Speed;
     */

    P8() {
        Robo = 25;
        ExtraHP = 20;
        ExtraDamage = 20;

        // laser beam
        Att = new Style() {
            @Override
            public void Pain(Entity Self, float Amount) {
            }

            @Override
            public void WhenAttacked(Entity Self, Entity Enemy, int Range) {
            }

            @Override
            public void Attack(Entity Self, int[] TargetPos, float Range) {
                for (int x = 0; x < GamePanel.Units.size(); x++) {
                    if (Self == GamePanel.Units.get(x) || (Self != GamePanel.player && Self.Good == GamePanel.Units.get(x).Good)) {
                        continue;
                    }

                    if (Math.abs(TargetPos[0] - GamePanel.Units.get(x).x) < 25 * Self.Range || Math.abs(TargetPos[1] - GamePanel.Units.get(x).y) < 25 * Self.Range) {
                        GamePanel.Units.get(x).Dam(Self, Self.Damage * 1.2f);
                    }
                }

                if (Self != GamePanel.player && !Self.Good) {
                    if (Math.abs(TargetPos[0] - GamePanel.player.x) < 25 * Self.Range || Math.abs(TargetPos[1] - GamePanel.player.y) < 25 * Self.Range) {
                        GamePanel.player.Dam(Self, Self.Damage * 1.2f);
                    }
                }
            }
        };
        Att.Name = "Laser Beam";
        Att.Action = true;
    }

    @Override
    public void drawAttack(Graphics2D g2) {
        g2.setColor(Color.MAGENTA);
        g2.fillRect(0, GamePanel.player.NextY - (int) (25 * GamePanel.player.Range / 2), 1250, (int) (25 * GamePanel.player.Range));
        g2.fillRect(GamePanel.player.NextX - (int) (25 * GamePanel.player.Range / 2), 0, (int) (25 * GamePanel.player.Range), 1250);
    }
}