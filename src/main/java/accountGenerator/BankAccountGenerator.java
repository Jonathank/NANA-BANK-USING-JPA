package accountGenerator;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class BankAccountGenerator {
	  
	  public static String generateAccountNumber(final String BANK_IDENTIFIER) { 
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
	  
	 
}
