import java.awt.*;
import java.util.ArrayList;
public class P9 extends Upgrade {
    /*
    public int Robo;
    public int ExtraHP;
    public int ExtraDamage;
    public int Range;
    public int Speed;
     */

    P9() {
        Robo = 20;
        ExtraHP = 5;
        

        // refractor master
        Att = new Style() {
            @Override
            public void Pain(Entity Self, float Amount) {
                Self.Health -= 20;
                float dammageTaken = (Self.Hurt - Self.Health);
                ArrayList<Integer> nearby = new ArrayList<>();
                
                for (int x = 0; x < GamePanel.Units.size(); x++) {
                    if (Self == GamePanel.Units.get(x)) {
                        continue;
                    }

                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units.get(x).x - Self.x, 2) + Math.pow(GamePanel.Units.get(x).y - Self.y, 2));
                    if (TDis <= 140 + 10 * Self.Range) {
                        nearby.add(x);
                        
                    }
                }
                float dammageSpread = ((float) dammageTaken) / nearby.size();
                for (int i : nearby) {
                    GamePanel.Units.get(i).Dam(Self, -dammageSpread);
                }   
            }

            @Override
            public void WhenAttacked(Entity Self, Entity Enemy, int Range) {
            }

            @Override
            public void Attack(Entity Self, int[] TargetPos, float Range) {
            }
        };
        Att.Name = "refractor master";
        Att.Action = true;
    }

    @Override
    public void drawAttack(Graphics2D g2) {
        g2.setColor(Color.MAGENTA);
        g2.fillRect(0, GamePanel.player.NextY - (int) (25 * GamePanel.player.Range / 2), 1250, (int) (25 * GamePanel.player.Range));
        g2.fillRect(GamePanel.player.NextX - (int) (25 * GamePanel.player.Range / 2), 0, (int) (25 * GamePanel.player.Range), 1250);
    }
}