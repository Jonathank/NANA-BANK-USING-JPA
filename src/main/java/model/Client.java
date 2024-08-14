package model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Clients")
public class Client {
    @Id
    @Column(name = "payeeAddress", nullable = false, length = 150)
    private String payeeAddress;

    @Column(name = "firstname", nullable = false, length = 150)
    private String fname;

    @Column(name = "lastname", nullable = false, length = 150)
    private String lname;

    @Column(name = "gender", nullable = false, length = 150)
    private String gender;

    @Column(name = "DateOfBirth", nullable = false, length = 150)
    private String dateOfBirth;

    @Column(name = "password", nullable = false, length = 150)
    private String password;

    @Column(name = "DateCreated", nullable = false)
    private String dateCreated;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Account> accounts = new HashSet<>();
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> transaction = new HashSet<>();
    
    public Client(String fname, String lname, String gender, String dateOfBirth,  String password) {
      
    	if (fname == null || fname.isEmpty() || lname == null || lname.isEmpty()) {
            throw new IllegalArgumentException("First name and last name cannot be null or empty.");
        }
        
        if (fname.length() < 1 || lname.length() < 1) {
            throw new IllegalArgumentException("First name and last name must have at least one character.");
        }
    	this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.dateCreated = LocalDate.now().toString();
		this.payeeAddress = Model.getInstance().generatePayeeAddress(fname, lname);
    }

    public Client() {}

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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
