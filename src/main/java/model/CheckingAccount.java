package model;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("checking") // This value is what is stored in the discriminator column
public class CheckingAccount extends Account {
    @Column(name = "transaction_limit", nullable = false)
    private Integer transactionLimit;
    
    public CheckingAccount() {}

    public CheckingAccount(String accountNumber, BigDecimal balance, int limit) {
        super(accountNumber, balance);
        this.transactionLimit = limit;
    }

    public Integer getTransactionLimit() {
        return transactionLimit;
    }

	/**
	 * @param transactionLimit the transactionLimit to set
	 */
	public void setTransactionLimit(Integer transactionLimit) {
		this.transactionLimit = transactionLimit;
	}

   
    
}
