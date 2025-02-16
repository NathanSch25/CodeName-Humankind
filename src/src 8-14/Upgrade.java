public class Upgrade {
    public int Robo;
    public int ExtraHP;
    public int ExtraMeleeDamage;
    public int ExtraRangeDamage;
    public int Range;
    public int Speed;
    Style Att = new Style() {
        @Override
        public void Pain(Entity Self, float Amount) {}
        @Override
        public void WhenAttacked(Entity Self, Entity Enemy, int Damage) {}
        @Override
        public void Attack(Entity Self, int[] TargetPos, float Range) {}
    };
    Upgrade() {}
}
