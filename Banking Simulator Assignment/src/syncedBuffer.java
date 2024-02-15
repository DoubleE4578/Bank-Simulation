/* Ethan Ellis
 * CNT 4714 – Spring 2024
 * Project 2 - Synchronized, Cooperating Threads Under Locking
 * Sunday February 11, 2024
 */


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class syncedBuffer {
	
	// Declare all variables:
	int balance;
	int count;
	int lastBankAudit;
	int lastTreasuryAudit;
	ReentrantLock accessLock;
	Condition lockCondition;
	
	
	// syncedBuffer class constructor; set variables:
	public syncedBuffer() {
		
		balance = 0;
		count = 0;
		lastBankAudit = 0;
		lastTreasuryAudit = 0;
		accessLock = new ReentrantLock();
		lockCondition = accessLock.newCondition();
	}
	
		
	// Method for depositing into balance:
	public void deposit(int value, int agentNum) throws InterruptedException {
		
		// Attempt to gain control of this object:
		try {
			
			// Lock this object temporarily:
			accessLock.lock();
			
			// Update the account balance with the new value and increment counts by 1:
			balance += value;
			count++;
			lastBankAudit++;
			lastTreasuryAudit++;
			
			System.out.println("Agent DT" + agentNum + " deposits $" + value + "\t\t\t\t\t\t\t" + "(+) Balance is $" + balance + "\t\t\t\t" + count);
			
			// Transactions over $350 are flagged:
			if (value > 350) {
				
				// Let the user know that this transaction has been flagged:
				System.out.println("\n* * * Flagged Transaction - Depositor Agent DT" + agentNum + " made a deposit in excess of $350.00 USD - See Flagged Transaction Log.\n");
				
				// Set up the format for the time:
				ZonedDateTime dateTime = ZonedDateTime.now();
				String format = "dd/MM/yyyy hh:mm:ssa z";
				DateTimeFormatter pattern = DateTimeFormatter.ofPattern(format);
				String time = dateTime.format(pattern);
				
				// Log the flagged transaction to the transactionsLog.csv file:
				try {
					
					File log = new File("transactionsLog.csv");
					FileWriter logFile = new FileWriter(log, true);
					logFile.write("Depositor Agent DT" + agentNum + " issued deposit of $" + value + " at: " + time + "\t\t\t\t Transaction Number: " + count + "\n");
					logFile.close();
				} // End of try
				
				catch (IOException e) {
					
					e.printStackTrace();
				} // End of catch
			} // End of if
			
			// Signal waiting threads that this object is now available. 
			lockCondition.signalAll();
			
		} // End of try
		
		finally {
			
			// Unlock this object:
			accessLock.unlock();
			
		} // End of Finally
	} // End of deposit
	
	
	// Method for withdrawing from balance:
	public void withdrawal(int value, int agentNum) {
		
		// Attempt to gain control of this object:
		try {
			
			// Lock this object temporarily:
			accessLock.lock();
			
			while (balance < value) {
				
				System.out.println("\t\t\t\tAgent WT" + agentNum + " withdraws $" + value + "\t\t\t(******) WITHDRAWAL BLOCKED - INSUFFICIENT FUNDS!!!");
				lockCondition.await();
			} // End of while
			
			// Update the account balance with the new value and increment counts by 1:
			balance -= value;
			count++;
			lastBankAudit++;
			lastTreasuryAudit++;
			
			System.out.println("\t\t\t\tAgent WT" + agentNum + " withdraws $" + value + "\t\t\t(-) Balance is: $" + balance + "\t\t\t\t" + count);
			
			// Transactions over $75 are flagged:
			if (value > 75) {
				
				// Let the user know that this transaction has been flagged:
				System.out.println("\n* * * Flagged Transaction - Withdrawal Agent WT" + agentNum + " made a withdrawal in excess of $75.00 USD - See Flagged Transaction Log.\n");
				
				// Set up the format for the time:
				ZonedDateTime dateTime = ZonedDateTime.now();
				String format = "dd/MM/yyyy hh:mm:ssa z";
				DateTimeFormatter pattern = DateTimeFormatter.ofPattern(format);
				String time = dateTime.format(pattern);
				
				// Log the flagged transaction to the transactionsLog.csv file:
				try {
					
					File log = new File("transactionsLog.csv");
					FileWriter logFile = new FileWriter(log, true);
					logFile.write("Withdrawal Agent WT" + agentNum + " issued withdrawal of $" + value + " at: " + time + "\t\t\t\t Transaction Number: " + count + "\n");
					logFile.close();
				} // End of try
				
				catch (IOException e) {
					
					e.printStackTrace();
				} // End of catch
			} // End of if
			
			// Signal waiting threads that this object is now available. 
			lockCondition.signalAll();
			
		} // End of try
		
		catch(InterruptedException exception) {
			
			exception.printStackTrace();
			
		} // End of catch
		
		finally {
			
			accessLock.unlock();
			
		} // End of finally
	} // End of withdrawal
	
	
	// Method for internal bank audits:
	public void bankAudit() {
		
		// Attempt to gain control of this object:
		try {
			
			// Lock this object temporarily:
			accessLock.lock();
			
			// Let the user know an audit has occured:
			System.out.println("\n********************************************************************************" + 
					"\n\n\t\tINTERNAL BANK AUDITOR FINDS CURRENT ACCOUNT BALANCE TO BE: $" + balance + 
					"\tNumber of transactions since last Internal audit is: " + lastBankAudit + 
					"\n\n********************************************************************************\n");
			
			// Reset the counter that tracks when the last audit was:
			lastBankAudit = 0;
			
			// Signal waiting threads that this object is now available:
			lockCondition.signalAll();
			
		} // End of try
		
		finally {
			
			accessLock.unlock();
			
		} // End of finally
	} // End of BankAudit
	
	
	// Method for treasury audits:
	public void treasuryAudit() {
		
		// Attempt to gain control of this object:
				try {
					
					// Lock this object temporarily:
					accessLock.lock();
					
					// Let the user know an audit has occured:
					System.out.println("\n********************************************************************************" + 
							"\n\n\t\tTREASURY DEPT AUDITOR FINDS CURRENT ACCOUNT BALANCE TO BE: $" + balance + 
							"\tNumber of transactions since last Treasury audit is: " + lastTreasuryAudit + 
							"\n\n********************************************************************************\n");
					
					// Reset the counter that tracks when the last audit was:
					lastTreasuryAudit = 0;
					
					// Signal waiting threads that this object is now available:
					lockCondition.signalAll();
					
				} // End of try
				
				finally {
					
					accessLock.unlock();
					
				} // End of finally
	} // End of treasuryAudit
} // End of syncedBuffer