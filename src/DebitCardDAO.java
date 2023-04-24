public interface DebitCardDAO {
    public DebitCard addCard (DebitCard card);
    public Transaction withdraw (DebitCard db, float amount, TransactionDAO tdao);
    public Transaction transfer (DebitCard db, Account acc2, float amount, TransactionDAO tdao);
}