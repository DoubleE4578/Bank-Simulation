/* Ethan Ellis
 * CNT 4714 – Spring 2024
 * Project 2 - Synchronized, Cooperating Threads Under Locking
 * Sunday February 11, 2024
 */


import java.util.Random;


public class withdrawalAgent implements Runnable {
	
	// Declare all variables:
	Random generator = new Random();
	syncedBuffer withdrawalMoney;
	int withdrawalAmount;
	int withdrawalNum;
	
	
	// withdrawalAgent class constructor; set variables:
	public withdrawalAgent(syncedBuffer sharedBuffer, int withdrawalID) {
		
		withdrawalMoney = sharedBuffer;
		withdrawalNum = withdrawalID;
	}
	
	
	// Run the thread:
	public void run() {
		
		// Infinitely run the thread until the user terminates the program:
		while (true) {
			
			try {
				
				// Randomly generate the amount of money to be between $1 - $99:
				withdrawalAmount = generator.nextInt(99) + 1;
				
				// Attempt to withdrawal money from the account:
				withdrawalMoney.withdrawal(withdrawalAmount, withdrawalNum);
				
				// Sleep the thread for somewhere between 1 - 25 milliseconds:
				Thread.sleep(generator.nextInt(250) + 1);
				
			} // End of try
			
			catch(InterruptedException exception) {
				
				exception.printStackTrace();
				
			} // End of catch
		} // End of while
	} // End of run
} // End of withdrawalAgent