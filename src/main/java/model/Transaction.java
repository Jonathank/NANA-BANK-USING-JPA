package model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
   
	@Column(name = "reciever", nullable = false, length = 150)
	private  String reciever;
	@Column(name = "amount", nullable = false, length = 150)
	private  Double amount;
	@Column(name = "dateCreated", nullable = false, length = 255)
	private  LocalDate dateCreated;
	@Column(name = "message", nullable = false, length = 255)
	private  String message;
	@Column(name = "timecreated", nullable = false, length = 255)
	private  LocalTime timecreated;
	@ManyToOne 
    @JoinColumn(name = "sender", insertable = true, updatable = true) // Set to false if using payeeAddress from Client // This should match your DB column
    private Client client;
	

	/**
	 * @param sender
	 * @param reciever
	 * @param amount
	 * @param date
	 * @param message
	 */
	public Transaction(String reciever, Double amount, String message) {
		
		this.reciever = reciever;
		this.amount = amount;
		this.dateCreated = LocalDate.now();
		this.message = message;
		this.timecreated = LocalTime.now();
	}
	
	

	public Transaction() {}
	
	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}
	/**
	 * @return the reciever
	 */
	public String getReciever() {
		return reciever;
	}
	/**
	 * @param reciever the reciever to set
	 */
	public void setReciever(String reciever) {
		this.reciever = reciever;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return dateCreated;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.dateCreated = date;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @return the timecreated
	 */
	public LocalTime getTimecreated() {
		return timecreated;
	}

	/**
	 * @param timecreated the timecreated to set
	 */
	public void setTimecreated(LocalTime timecreated) {
		this.timecreated = timecreated;
	}
}
