package model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type") // Use a more descriptive name
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "account_number", nullable = false, length = 150)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance; // Use BigDecimal for monetary values
    
    @Column(name = "DateCreated", nullable = false, length = 250)
	private LocalDate DateCreated;

    @ManyToOne // Many accounts can belong to one client
    @JoinColumn(name = "payeeAddress", insertable = true, updatable = true) // Set to false if using payeeAddress from Client // This should match your DB column
    private Client client;
    
    protected Account() {}

    public Account(String accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.DateCreated = LocalDate.now();
    }
    
 // Getters and setters...

    
    public void setClient(Client client) {
        this.client = client;
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
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}


	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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

    
    
    
    
}
