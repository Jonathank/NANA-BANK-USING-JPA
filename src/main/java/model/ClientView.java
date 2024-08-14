package model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ClientView")
public class ClientView {

	@Id
	private long id;
	@Column(name = "payeeAddress", nullable = false, length = 150)
	private String payeeAddress;

	@Column(name = "firstname", nullable = false, length = 150)
	private String fname;

	@Column(name = "lastname", nullable = false, length = 150)
	private String lname;
	@Column(name = "Account_Type", nullable = false, length = 150)
	private String AccountType;
	@Column(name = "Account_Number", nullable = false, length = 150)
	private String AccountNumber;
	@Column(name = "dateCreated", nullable = false, length = 150)
	private String dateCreated;
	@Column(name = "balance", nullable = false, length = 250)
	private BigDecimal balance;

	 
	public ClientView() {}
	/**
	 * @param payeeAddress
	 * @param fname
	 * @param lname
	 * @param accountType
	 * @param accountNumber
	 * @param dateCreated
	 */
	public ClientView(String payeeAddress, String fname, String lname, String accountType, String accountNumber,
			String dateCreated) {
		this.payeeAddress = payeeAddress;
		this.fname = fname;
		this.lname = lname;
		AccountType = accountType;
		AccountNumber = accountNumber;
		this.dateCreated = dateCreated;
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
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
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
	 * @return the dateCreated
	 */
	public String getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

}
