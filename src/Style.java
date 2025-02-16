public abstract class Style {
    // Range of 0 == Self
    // Also include all Enemies, Allies, and the Player into the methods

    public String Name;
    public boolean Action = false;
    public boolean Ranged = false;
    public abstract void Pain(Entity Self, float Amount);
    public abstract void WhenAttacked(Entity Self, Entity Enemy, int Damage);
    public abstract void Attack(Entity Self, int[] TargetPos, float Range);
}
