public class P3 extends Upgrade {
    /*
    public int Robo;
    public int ExtraHP;
    public int ExtraMeleeDamage;
    public int ExtraRangeDamage;
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
                for (int x = 0; x < GamePanel.Units.length; x++) {
                    if (Self == GamePanel.Units[x]) {
                        continue;
                    }

                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units[x].x - Self.x, 2) + Math.pow(GamePanel.Units[x].y - Self.y, 2));
                    if (TDis <= 200 * Range) {
                        GamePanel.Units[x].Dam(Self, Self.Damage / 2f);
                        GamePanel.Units[x].StunEnemy(2);
                    }
                }
            }
        };
        Att.Name = "Stunnng Kick";
        Att.Action = true;
    }

    // Add Methods and / or Special Attacks
}
