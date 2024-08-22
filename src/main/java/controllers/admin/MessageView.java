package controllers.admin;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messageview")
public class MessageView {

	@Id
	private long id;
	private String payeeAddress; 
	private String messages; 
	private String MessageType;
	private String dateCreated;
	private String status;
	
	public MessageView() {}
	
	public MessageView(String msg) {
		this.messages = msg;
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
	 * @return the messages
	 */
	public String getMessages() {
		return messages;
	}
	/**
	 * @param messages the messages to set
	 */
	public void setMessages(String messages) {
		this.messages = messages;
	}
	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return MessageType;
	}
	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		MessageType = messageType;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
