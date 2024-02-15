/* Ethan Ellis
 * CNT 4714 – Spring 2024
 * Project 2 - Synchronized, Cooperating Threads Under Locking
 * Sunday February 11, 2024
 */


import java.util.Random;


public class InternalBank implements Runnable {
	
	// Declare all variables:
	Random generator = new Random();
	syncedBuffer internalAudit;
	
	
	// InternalBank class constructor; set variables:
	public InternalBank(syncedBuffer sharedBuffer) {
		
		internalAudit = sharedBuffer;
	}
	
	
	// Run the thread:
	public void run() {
		
		// Infinitely run the thread until the user terminates the program:
		while (true) {
			
			try {
				
				// Attempt to audit the account:
				internalAudit.bankAudit();
				
				// Sleep the thread for somewhere between 1 - 100 milliseconds:
				Thread.sleep(generator.nextInt(1000) + 1);
				
			} // End of try
			
			catch(InterruptedException exception) {
				
				exception.printStackTrace();
				
			} // End of catch
		} // End of while
	} // End of run
} // End of InternalBank