package model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accountview")
public class AccountView {

	@Id
	private long id;
	@Column(name = "payeeAddress", nullable = false, length = 150)
	private String payeeAddress;

	@Column(name = "Account_Type", nullable = false, length = 150)
	private String AccountType;
	
	@Column(name = "Account_Number", nullable = false, length = 150)
	private String AccountNumber;
	
	@Column(name = "withdrawLimit", nullable = false, length = 150)
	private BigDecimal withdrawLimit;
	
	@Column(name = "balance", nullable = false, length = 250)
	private BigDecimal balance;
	
	@Column(name = "transaction_limit", nullable = false, length = 250)
	private long transaction_limit;

	@Column(name = "DateCreated", nullable = false, length = 250)
	private LocalDate DateCreated;
	
	public AccountView() {}

	/**
	 * @param id
	 * @param payeeAddress
	 * @param accountType
	 * @param accountNumber
	 * @param withdrawLimit
	 * @param balance
	 * @param transaction_limit
	 */
	public AccountView(String payeeAddress, String accountType, String accountNumber, BigDecimal withdrawLimit,
			BigDecimal balance, long transaction_limit) {
		this.payeeAddress = payeeAddress;
		AccountType = accountType;
		AccountNumber = accountNumber;
		this.withdrawLimit = withdrawLimit;
		this.balance = balance;
		this.transaction_limit = transaction_limit;
	}
	
	

	/**
	 * @return the dateCreated
	 */
	public LocalDate getDateCreated() {
		return DateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(LocalDate dateCreated) {
		DateCreated = dateCreated;
	}

	/**
	 * @return the payeeAddress
	 */
	public String getPayeeAddress() {
		return payeeAddress;
	}

	/**
	 * @param payeeAddress the payeeAddress to set
	 */
	public void setPayeeAddress(String payeeAddress) {
		this.payeeAddress = payeeAddress;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return AccountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		AccountType = accountType;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return AccountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
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

	/**
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return the transaction_limit
	 */
	public long getTransaction_limit() {
		return transaction_limit;
	}

	/**
	 * @param transaction_limit the transaction_limit to set
	 */
	public void setTransaction_limit(long transaction_limit) {
		this.transaction_limit = transaction_limit;
	}
	

}
