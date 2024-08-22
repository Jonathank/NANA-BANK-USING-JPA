package model;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import controllers.admin.MessageView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import views.AccountType;
import views.ViewFactory;

public class Model{

	private static Model model;
	private final ViewFactory viewfactory;
	//track account at login
	private AccountType loginAccountType = AccountType.CLIENT;
	
	//clients data section
	private Client client;
	private boolean clientLoginSuccessFlag;
	//admin data section
	private ClientView clientview;
	
	//constructor
	private Model() {
		this.viewfactory = new ViewFactory();
		//client data section
		this.setClientLoginSuccessFlag(false);
		this.client = new Client();
		//admin data section
		this.clientview = new ClientView();
	}
	
	public static synchronized Model getInstance() {
		if(model == null) {
			model = new Model();
		}
		return model;
	}
	
	
	public ViewFactory getViewFactory() {
		return viewfactory;
	}
	/**
	 * @return the clientview
	 */
	public ClientView getClientview() {
		return clientview;
	}

	public void setClientview(ClientView clientview) {
		this.clientview = clientview;
	}
	/**
	 * @return the loginAccountType
	 */
	public AccountType getLoginAccountType() {
		return loginAccountType;
	}

	/**
	 * @param loginAccountType the loginAccountType to set
	 */
	public void setLoginAccountType(AccountType loginAccountType) {
		this.loginAccountType = loginAccountType;
	}

	
	/*
	 * client data method section
	 * */
	/**
	 * @return the clientLoginSuccessFlag
	 */
	public boolean getClientLoginSuccessFlag() {
		return clientLoginSuccessFlag;
	}

	/**
	 * @param clientLoginSuccessFlag the clientLoginSuccessFlag to set
	 */
	public void setClientLoginSuccessFlag(boolean clientLoginSuccessFlag) {
		this.clientLoginSuccessFlag = clientLoginSuccessFlag;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	//generates random account numbers in admin create client
	public String generateAccountNumber(final String BANK_IDENTIFIER) { 
		  final int ACCOUNT_NUMBER_LENGTH = 10;
		  final int RANDOM_PART_LENGTH = ACCOUNT_NUMBER_LENGTH - BANK_IDENTIFIER.length(); // Remaining digits private
		  final Set<String> generatedAccountNumbers = new HashSet<>(); 
		  final SecureRandom random = new SecureRandom();
	  String accountNumber;
	  
	  do { // Generate a random number for the account 
		  StringBuilder sb = new StringBuilder(BANK_IDENTIFIER);
		  for (int i = 0; i < RANDOM_PART_LENGTH; i++) { 
			  sb.append(random.nextInt(10)); // Generates a digit from 0 to 9 
	  }
	  accountNumber = sb.toString(); 
	  } while(generatedAccountNumbers.contains(accountNumber)); // Ensure uniqueness
	  
	  // Add the newly generated account number to the set
	  generatedAccountNumbers.add(accountNumber);
	  return accountNumber; 
	  }
	  
	//validates clients credentials on login as client
public boolean validateCredentials(EntityManager em,String payeeAddress, String password) {  
	TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.payeeAddress = :payeeAddress AND c.password = :password", Client.class);
        query.setParameter("payeeAddress", payeeAddress);
        query.setParameter("password", password);

        try {
            Client user = query.getSingleResult();
            if(user.getPayeeAddress().equals(payeeAddress) && user.getPassword().equals(password)) {
            	return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        //	em.close();
        	//EntityMangerFactoryRepo.shutdown();
        }
        return false;
    }

//validates Admin credentials on login as client
public boolean validateAdminCredentials(EntityManager em,String username, String password) {  
	
	TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
      query.setParameter("username", username);
      query.setParameter("password", password);

      try {
          User user = query.getSingleResult();
          //String pass = getDecryptedValue(user.getPassword(),8);
          if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
          	return true;
          }
          
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
      //	em.close();
      	//EntityMangerFactoryRepo.shutdown();
      }
      return false;
  }

//get client payeeaddress
public String getClientPayeeAdress(EntityManager em,String payeeAddress) {
	String payee = "";
	TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.payeeAddress = :payeeAddress ", Client.class);
        query.setParameter("payeeAddress", payeeAddress);
       
        try {
            Client user = query.getSingleResult();
           payee = user.getPayeeAddress();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        //	em.close();
        	//EntityMangerFactoryRepo.shutdown();
        }
        return payee;
    }

	//show all clients in admin client on list view
public ObservableList<ClientView> loadDataFromDatabase(EntityManager em) {
    ObservableList<ClientView> clients = FXCollections.observableArrayList();
    try {
        TypedQuery<ClientView> query = em.createQuery("SELECT c FROM ClientView c", ClientView.class);
        List<ClientView> clientList = query.getResultList();
        
        Platform.runLater(() -> {
        	clients.addAll(clientList);
        });
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
    // em.close(); // Ensure the EntityManager is closed
    // EntityMangerFactoryRepo.shutdown();   
    }
    
    return clients;
}

//loads transaction data for view
public ObservableList<TransactionView> loadTransactionData(EntityManager em,String sender, String reciever) {
    ObservableList<TransactionView> transactions = FXCollections.observableArrayList();
    try {
    	TypedQuery<TransactionView> query = em.createQuery(
    	        "SELECT t FROM TransactionView t WHERE t.sender = :sender ORDER BY t.dateCreated DESC, t.timecreated DESC", 
    	        TransactionView.class);
    	    query.setParameter("sender", sender);
    	   // query.setMaxResults(2);
    	    
    	    TypedQuery<TransactionView> query1 = em.createQuery(
        	        "SELECT t FROM TransactionView t WHERE t.reciever = :reciever ORDER BY t.dateCreated DESC, t.timecreated DESC", 
        	        TransactionView.class);
        	     query1.setParameter("reciever", reciever);
        	   // query.setMaxResults(2);

        List<TransactionView> clientList = query.getResultList();
        List<TransactionView> clientList1 = query1.getResultList();
        Platform.runLater(() -> {
        	 transactions.addAll(clientList);
             transactions.addAll(clientList1);
        });
       
        
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
    // em.close(); // Ensure the EntityManager is closed
    // EntityMangerFactoryRepo.shutdown();   
    }
    
    return transactions;
}

public ObservableList<TransactionView> loadTransactionDataLimit(EntityManager em,String sender, String reciever) {
    ObservableList<TransactionView> transactions = FXCollections.observableArrayList();
    try {
    	TypedQuery<TransactionView> query = em.createQuery(
    	        "SELECT t FROM TransactionView t WHERE t.sender = :sender ORDER BY t.dateCreated DESC, t.timecreated DESC", 
    	        TransactionView.class);
    	    query.setParameter("sender", sender);
    	    query.setMaxResults(3);
    	    
    	    TypedQuery<TransactionView> query1 = em.createQuery(
        	        "SELECT t FROM TransactionView t WHERE t.reciever = :reciever ORDER BY t.dateCreated DESC, t.timecreated DESC", 
        	        TransactionView.class);
        	     query1.setParameter("reciever", reciever);
        	     query.setMaxResults(2);

        List<TransactionView> clientList = query.getResultList();
        List<TransactionView> clientList1 = query1.getResultList();
        
        transactions.addAll(clientList);
        transactions.addAll(clientList1);
        
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
    // em.close(); // Ensure the EntityManager is closed
    // EntityMangerFactoryRepo.shutdown();   
    }
    
    return transactions;
}

//returns client view object to set the table
public ObservableList<ClientView> LoadClientAccountDetails(EntityManager em, String payeeAddress) {
    ObservableList<ClientView> clients = FXCollections.observableArrayList();
    
    try {
       
        TypedQuery<ClientView> query = em.createQuery("SELECT c FROM ClientView c WHERE c.payeeAddress = :payeeAddress", ClientView.class);
        query.setParameter("payeeAddress", payeeAddress);
        
        List<ClientView> clientList = query.getResultList();
        clients.addAll(clientList);
        
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    return clients;
}

//generates payee address
	public String generatePayeeAddress(String fname, String lname) {
	    char firstInitial = Character.toLowerCase(fname.charAt(0));
	    char lastInitial = Character.toUpperCase(lname.charAt(0));
	    String restOfLastName = lname.substring(1).toLowerCase();
	    String num = Model.getInstance().AppendToPayee();
	    return "@" + firstInitial + lastInitial + restOfLastName +num;
	}

//append random generated number of 2 digits to payeeAddress in admin
public String AppendToPayee() { 
	  final String IDENTIFIER = "8";
	  final int NUMBER_LENGTH = 3;
	  final int RANDOM_PART_LENGTH = NUMBER_LENGTH - IDENTIFIER.length(); // Remaining digits private
	  final Set<String> generatedNumbers = new HashSet<>(); 
	  final SecureRandom random = new SecureRandom();
String Number;

do { // Generate a random number 
	  StringBuilder sb = new StringBuilder(IDENTIFIER);
	  for (int i = 0; i < RANDOM_PART_LENGTH; i++) { 
		  sb.append(random.nextInt(10)); // Generates a digit from 0 to 9 
}
Number = sb.toString(); 
} while(generatedNumbers.contains(Number)); // Ensure uniqueness

// Add the newly generated  number to the set
generatedNumbers.add(Number);
return Number; 
}

//validate client if they exist by their payee address for updates
public boolean CheckIfClientExists(EntityManager em,String payeeAddress) {
	 
	TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.payeeAddress = :payeeAddress ", Client.class);
        query.setParameter("payeeAddress", payeeAddress);
   
        try {
            Client user = query.getSingleResult();
            if(user.getPayeeAddress().equals(payeeAddress)) {
            	return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return false;
    }

//returns client object from database
public Client findClient( EntityManager em,String payeeAddress) {
   
    Client client = null; // Initialize to null
    try {
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.payeeAddress = :payeeAddress", Client.class);
        query.setParameter("payeeAddress", payeeAddress);
        
        client = query.getSingleResult();
    } catch (NoResultException e) {
        // Handle case where no client is found
        Logger.getLogger(getClass().getName()).log(Level.WARNING, "No client found for payeeAddress: " + payeeAddress, e);
    } catch (NonUniqueResultException e) {
        // Handle case where multiple clients are found
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Multiple clients found for payeeAddress: " + payeeAddress, e);
    } catch (Exception e) {
        // Handle other exceptions
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "An error occurred while fetching client.", e);
    }
    return client; // Returns null if not found
}



//returns Account object
public Account getAccountObject(EntityManager em,String payeeAddress, Class<? extends Account> accountType) {
   Account account = null;
    try {
      
        TypedQuery<Account> query = em.createQuery(
            "SELECT a FROM Account a WHERE a.client.payeeAddress = :payeeAddress AND TYPE(a) = :accountType", 
            Account.class);
        
        // Set the parameters for the query
        query.setParameter("payeeAddress", payeeAddress);
        query.setParameter("accountType", accountType); // Now using Class type

        // Execute the query and retrieve the result
        account = query.getSingleResult();

        em.clear();
    } catch (NoResultException e) {
        // Handle the case where no account is found
        System.out.println("No account found for payeeAddress: " + payeeAddress + " and accountType: " + accountType.getSimpleName());
    } catch (Exception e) {
        // Log any other exceptions that occur
        e.printStackTrace();
    }
    
    return account;
}

//return Account view object
public AccountView getAccountViewObject(EntityManager em,String payeeAddress, String AccountType) {
	AccountView accountview = null;
	    try {
	      
	        TypedQuery<AccountView> query = em.createQuery(
	            "SELECT a FROM AccountView a WHERE a.payeeAddress = :payeeAddress AND a.AccountType = :AccountType", 
	            AccountView.class);
	        
	        // Set the parameters for the query
	        query.setParameter("payeeAddress", payeeAddress);
	        query.setParameter("AccountType", AccountType); // Now using Class type

	        // Execute the query and retrieve the result
	        accountview = query.getSingleResult();

	        
	    } catch (NoResultException e) {
	        // Handle the case where no account is found
	        System.out.println("No account found for payeeAddress: " + payeeAddress + " and accountType: " + AccountType);
	    } catch (Exception e) {
	        // Log any other exceptions that occur
	        e.printStackTrace();
	    }
	    
	    return accountview;
	}

//returns balance basing on the account type paramenter
public BigDecimal getAccountBalance(EntityManager em, String payeeAddress, Class<? extends Account> accountType) {
  BigDecimal balance = null;
  try {
      // Create the JPQL query to select the balance based on payeeAddress and account type
      TypedQuery<BigDecimal> query = em.createQuery(
          "SELECT a.balance FROM Account a WHERE a.client.payeeAddress = :payeeAddress AND TYPE(a) = :accountType", 
          BigDecimal.class);
      
      // Set the parameters for the query
      query.setParameter("payeeAddress", payeeAddress);
      query.setParameter("accountType", accountType); // Now using Class type

      // Execute the query and retrieve the result
      balance = query.getSingleResult();
      

  } catch (NoResultException e) {
      // Handle the case where no account is found
      System.out.println("No account found for payeeAddress: " + payeeAddress + " and accountType: " + accountType.getSimpleName());
  } catch (Exception e) {
      // Log any other exceptions that occur
      e.printStackTrace();
  }
  
  // Print the retrieved balance
  System.out.println("Account balance: " + balance);
  return balance;
}

//update method for balance
public void updateBalance(EntityManager em, String payeeAddress, BigDecimal amount, Class<? extends Account> accountType) {
    em.getTransaction().begin();

    // Get the old balance
    BigDecimal oldBalance = getAccountBalance(em, payeeAddress, accountType);
    BigDecimal newBalance = oldBalance.add(amount);  // Correctly add BigDecimal values

    // Update the account balance
    Query query  = em.createQuery(
        "UPDATE Account a SET a.balance = :newBalance WHERE a.client.payeeAddress = :payeeAddress AND TYPE(a) = :accountType");
    
    query.setParameter("newBalance", newBalance);
    query.setParameter("payeeAddress", payeeAddress);
    query.setParameter("accountType", accountType);

    int updatedCount = query.executeUpdate();  // Get the number of updated rows

    if (updatedCount == 0) {
        throw new RuntimeException("No account updated, please check payee address and account type.");
    }

    em.getTransaction().commit();
    em.clear();
}

//reduce balance after sending
public void deductSenderBalance(EntityManager em, String payeeAddress, BigDecimal amount, Class<? extends Account> accountType) {
    em.getTransaction().begin();

    // Get the old balance
    BigDecimal oldBalance = getAccountBalance(em, payeeAddress, accountType);
    BigDecimal newBalance = oldBalance.subtract(amount); // Correctly add BigDecimal values

    // Update the account balance
    Query query  = em.createQuery(
        "UPDATE Account a SET a.balance = :newBalance WHERE a.client.payeeAddress = :payeeAddress AND TYPE(a) = :accountType");
    
    query.setParameter("newBalance", newBalance);
    query.setParameter("payeeAddress", payeeAddress);
    query.setParameter("accountType", accountType);

    int updatedCount = query.executeUpdate();  // Get the number of updated rows

    if (updatedCount == 0) {
        throw new RuntimeException("No account updated, please check payee address and account type.");
    }

    em.getTransaction().commit();
     em.clear();
}

public void deleteClient(EntityManager em, String payeeAddress) {
    em.getTransaction().begin();
    try {
        // Use Query for delete operation
        Query query = em.createQuery("DELETE FROM Client c WHERE c.payeeAddress = :payeeAddress");
        query.setParameter("payeeAddress", payeeAddress);
        int deletedCount = query.executeUpdate(); // This returns the number of entities deleted
        em.getTransaction().commit();
        System.out.println("Deleted " + deletedCount + " clients.");
    } catch (RuntimeException e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); // Rollback the transaction on error
        }
        throw e; // Re-throw the exception for further handling if needed
    }
}

//fetches messages to admin
public ObservableList<MessageView> loadMessageDataFromDatabase(EntityManager em) {
    ObservableList<MessageView> messages = FXCollections.observableArrayList();
    
    try {
        // Start a transaction if required
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }

        // Execute the query
        TypedQuery<MessageView> query = em.createQuery("SELECT m FROM MessageView m", MessageView.class);
        List<MessageView> messageList = query.getResultList();

        // Commit the transaction if it was started manually
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }

        // Update UI elements on the JavaFX Application Thread
        Platform.runLater(() -> messages.addAll(messageList));

    } catch (Exception e) {
        // Rollback transaction if it was started and an error occurs
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        e.printStackTrace();
    } 
    
    return messages;
}

public void updateStatus(EntityManager em, String payeeAddress,String MessageType) {
   
	em.getTransaction().begin();
try {
    String newstatus = "Viewed";
    // Update the account balance
    Query query  = em.createQuery(
        "UPDATE Message a SET a.status = :newstatus WHERE a.client.payeeAddress = :payeeAddress AND a.MessageType = :MessageType");
    
    query.setParameter("newstatus", newstatus);
    query.setParameter("payeeAddress", payeeAddress);
    query.setParameter("MessageType", MessageType);
 
    int updatedCount = query.executeUpdate();  // Get the number of updated rows

    if (updatedCount == 0) {
        throw new RuntimeException("No account updated, please check payee address and account type.");
    }
    em.getTransaction().commit();
} catch (Exception e) {
    // Rollback the transaction in case of an exception
    if (em.getTransaction() != null && em.getTransaction().isActive()) {
    	em.getTransaction().rollback();
    }
    throw new RuntimeException("Error updating status", e);
} finally {
    // Clear the EntityManager
    em.clear();
}
}
}