/* Ethan Ellis
 * CNT 4714 – Spring 2024
 * Project 2 - Synchronized, Cooperating Threads Under Locking
 * Sunday February 11, 2024
 */


import java.util.Random;


public class depositorAgent implements Runnable {
	
	// Declare all variables:
	Random generator = new Random();
	syncedBuffer depositMoney;
	int depositAmount;
	int depositorNum;
	
	
	// depositorAgent class constructor; set variables:
	public depositorAgent(syncedBuffer sharedBuffer, int depositorID) {
		
		depositMoney = sharedBuffer;
		depositorNum = depositorID;
	}
	
	
	// Run the thread:
	public void run() {
		
		// Infinitely run the thread until the user terminates the program:
		while (true) {
			
			try {
				
				// Randomly generate the amount of money to be between $1 - $500:
				depositAmount = generator.nextInt(500) + 1;
				
				// Attempt to deposit the money into the account:
				depositMoney.deposit(depositAmount, depositorNum);
				
				// Sleep the thread for somewhere between 1 - 50 milliseconds:
				Thread.sleep(generator.nextInt(750) + 1);
				
			} // End of try
			
			catch(InterruptedException exception) {
				
				exception.printStackTrace();
				
			} // End of catch
		} // End of while
	} // End of run
} // End of depositorAgent