package model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactionview")
public class TransactionView {
	@Id
	private long id;
	@Column(name = "sender", nullable = false, length = 150)
	private String sender;

	@Column(name = "reciever", nullable = false, length = 150)
	private String reciever;

	@Column(name = "Message", nullable = false, length = 150)
	private String Message;

	@Column(name = "dateCreated", nullable = false, length = 150)
	private String dateCreated;
	@Column(name = "timecreated", nullable = false, length = 150)
	private String timecreated;
	
	@Column(name = "Amount", nullable = false, length = 250)
	private BigDecimal Amount;

	 
	public TransactionView() {}


	/**
	 * @param id
	 * @param sender
	 * @param reciever
	 * @param message
	 * @param dateCreated
	 * @param amount
	 */
	public TransactionView(String sender, String reciever, String message, String dateCreated,BigDecimal amount) {
		this.sender = sender;
		this.reciever = reciever;
		Message = message;
		this.dateCreated = dateCreated;
		Amount = amount;
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
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}


	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
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
	 * @return the message
	 */
	public String getMessage() {
		return Message;
	}


	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		Message = message;
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


	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return Amount;
	}


	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		Amount = amount;
	}
	/**
	 * @return the timecreated
	 */
	public String getTimecreated() {
		return timecreated;
	}


	/**
	 * @param timecreated the timecreated to set
	 */
	public void setTimecreated(String timecreated) {
		this.timecreated = timecreated;
	}

	
}
