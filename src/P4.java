public class P4 extends Upgrade {
    /*
    public int Robo;
    public int ExtraHP;
    public int ExtraDamage;
    public int Range;
    public int Speed;
     */
    P4() {
        // Punch Attack
        Att = new Style() {
            @Override
            public void Pain(Entity Self, float Amount) {
                if (Amount < 20) {
                    Self.Heal(Self, 20);
                } else {
                    Self.StunEnemy(1);
                }
            }
            @Override
            public void WhenAttacked(Entity Self, Entity Enemy, int Range) {}
            @Override
            public void Attack(Entity Self, int[] TargetPos, float Range) {}
        };
        Att.Name = "Reboot";
        Att.Action = true;
    }

    // Add Methods and / or Special Attacks
}
