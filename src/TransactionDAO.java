import javax.swing.tree.TreeNode;

public interface TransactionDAO {
    public void addTransaction (Transaction trans);
    public Transaction getTransaction (String accountNum); 
}