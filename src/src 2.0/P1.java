public class P1 extends Upgrade {
    /*
    public int Robo;
    public int ExtraHP;
    public int ExtraMeleeDamage;
    public int ExtraRangeDamage;
    public int Range;
    public int Speed;
     */
    P1() {
        // Punch Attack
        Att = new Style() {
            @Override
            public void Defend(Entity Self, int[] TargetPos, int Range) {}
            @Override
            public void WhenAttacked(Entity Self, int[] TargetPos, int Range) {}
            @Override
            public void Attack(Entity Self, int[] TargetPos, int Range) {
                Entity Closest = null;
                float Dis = -1;
                for (int x = 0; x < GamePanel.Units.length; x++) {
                    if (Self == GamePanel.Units[x]) {
                        continue;
                    }

                    float TDis = (float) Math.sqrt(Math.pow(GamePanel.Units[x].x - TargetPos[0], 2) + Math.pow(GamePanel.Units[x].y - TargetPos[1], 2));
                    if (TDis <= Range) {
                        if (Dis > TDis) {
                            Closest = GamePanel.Units[x];
                            Dis = TDis;
                        }
                    }
                }

                if (Closest != null) {
                    Closest.Health -= Self.Damage;
                    System.out.println("" + Self.Name + " hit " + Closest.Name + "!");
                } else {
                    System.out.println("No Enemy in Range of " + Self.Name);
                }
            }
        };
        Att.Name = "Punch";
        Att.Action = true;
    }

    // Add Methods and / or Special Attacks
}
