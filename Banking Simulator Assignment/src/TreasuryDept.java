/* Ethan Ellis
 * CNT 4714 – Spring 2024
 * Project 2 - Synchronized, Cooperating Threads Under Locking
 * Sunday February 11, 2024
 */


import java.util.Random;


public class TreasuryDept implements Runnable {
	
	// Declare all variables:
	Random generator = new Random();
	syncedBuffer treasuryAuditor;
	
	
	// TreasuryDept class constructor; set variables:
	public TreasuryDept(syncedBuffer sharedBuffer) {
		
		treasuryAuditor = sharedBuffer;
	}
	
	
	// Run the thread:
	public void run() {
		
		// Infinitely run the thread until the user terminates the program:
		while (true) {
			
			try {
				
				// Attempt to audit the account:
				treasuryAuditor.treasuryAudit();
				
				// Sleep the thread for somewhere between 1 - 100 milliseconds:
				Thread.sleep(generator.nextInt(1000) + 1);
				
			} // End of try
			
			catch(InterruptedException exception) {
				
				exception.printStackTrace();
				
			} // End of catch
		} // End of while
	} // End of run
} // End of TreasuryDept