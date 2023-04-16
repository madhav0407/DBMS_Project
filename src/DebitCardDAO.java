public interface DebitCardDAO {
    public boolean addCard (DebitCard card);
    public boolean withdraw (DebitCard card, float amount);
    public boolean transfer (DebitCard card, Account acc, float amount);
    public float getSpending (DebitCard card); 
}