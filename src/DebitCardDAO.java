public interface DebitCardDAO {
    public void addCard (DebitCard card);
    public void withdraw (DebitCard card, float amount);
    public void transfer (DebitCard card, Account acc, float amount);
    public float getSpending (DebitCard card);
}