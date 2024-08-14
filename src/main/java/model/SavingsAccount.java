package model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue("savings")
public class SavingsAccount extends Account{
	 
    // Withdrawal limit from the savings account
	@Column(name = "withdrawLimit", nullable = false, length = 150)
    private BigDecimal withdrawLimit;
	
	public SavingsAccount() {}
    public SavingsAccount(String accountNumber, BigDecimal balance, BigDecimal wLimit) {
        super(accountNumber, balance);
        this.withdrawLimit = wLimit;
     
    }

	/**
	 * @return the withdrawLimit
	 */
	public BigDecimal getWithdrawLimit() {
		return withdrawLimit;
	}

	/**
	 * @param withdrawLimit the withdrawLimit to set
	 */
	public void setWithdrawLimit(BigDecimal withdrawLimit) {
		this.withdrawLimit = withdrawLimit;
	}
}
