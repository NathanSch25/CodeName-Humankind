public class P2 extends Upgrade {
    /*
    public int Robo;
    public int ExtraHP;
    public int ExtraMeleeDamage;
    public int ExtraRangeDamage;
    public int Range;
    public int Speed;
     */
    P2() {
        // Absorb
        Att = new Style() {
            @Override
            public void Pain(Entity Self, float Amount) {
                for (int x = 0; x < GamePanel.Units.length; x++) {
                    if (Self == GamePanel.Units[x]) {
                        continue;
                    }

                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units[x].x - Self.x, 2) + Math.pow(GamePanel.Units[x].y - Self.y, 2));
                    if (TDis <= 140 + 10 * Range) {
                        GamePanel.Units[x].Dam(Self, Math.max((Self.Hurt - Self.Health) * (Self.Damage / 10f), 0));
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

    // Add Methods and / or Special Attacks
}
