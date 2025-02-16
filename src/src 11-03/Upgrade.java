import java.awt.*;

public class Upgrade {
    public int Robo = 0;
    public int ExtraHP = 0;
    public int ExtraDamage = 0;
    public float Range = 0;
    public int Speed = 0;
    Style Att = new Style() {
        @Override
        public void Pain(Entity Self, float Amount) {}
        @Override
        public void WhenAttacked(Entity Self, Entity Enemy, int Damage) {}
        @Override
        public void Attack(Entity Self, int[] TargetPos, float Range) {}
    };
    Upgrade() {}

    public void drawAttack(Graphics2D g2) {}
}
