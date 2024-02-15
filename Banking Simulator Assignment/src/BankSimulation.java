/* Ethan Ellis
 * CNT 4714 – Spring 2024
 * Project 2 - Synchronized, Cooperating Threads Under Locking
 * Sunday February 11, 2024
 */


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.FileOutputStream;


public class BankSimulation {

	public static void main(String[] args) throws FileNotFoundException {
		
		System.out.println("Simulation begins");
		
		// Redirect console output to an output file:
		PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
		System.setOut(out);
		
		// Declare 17 new threads:
		ExecutorService application = Executors.newFixedThreadPool(17);
		
		// Declare the shared buffer for all threads:
		syncedBuffer sharedBuffer = new syncedBuffer();
		
		// Let user know the simulation has begun:
		System.out.println("* * * SIMULATION BEGINS...\n");
		System.out.println("Deposit Agents\t\t\tWithdrawal Agents\t\t\tBalance\t\t\t\t\t\tTransaction Number\n");
		System.out.println("---------------\t\t\t------------------\t\t\t--------\t\t\t\t\t-------------------\t\n");
		
		
		// Declare and activate all 17 threads:
		// Withdrawal Agent threads:
		withdrawalAgent withdrawal0 = new withdrawalAgent(sharedBuffer, 0);
		withdrawalAgent withdrawal1 = new withdrawalAgent(sharedBuffer, 1);
		withdrawalAgent withdrawal2 = new withdrawalAgent(sharedBuffer, 2);
		withdrawalAgent withdrawal3 = new withdrawalAgent(sharedBuffer, 3);
		withdrawalAgent withdrawal4 = new withdrawalAgent(sharedBuffer, 4);
		withdrawalAgent withdrawal5 = new withdrawalAgent(sharedBuffer, 5);
		withdrawalAgent withdrawal6 = new withdrawalAgent(sharedBuffer, 6);
		withdrawalAgent withdrawal7 = new withdrawalAgent(sharedBuffer, 7);
		withdrawalAgent withdrawal8 = new withdrawalAgent(sharedBuffer, 8);
		withdrawalAgent withdrawal9 = new withdrawalAgent(sharedBuffer, 9);
		
		// Depositor Agent threads:
		depositorAgent depositor0 = new depositorAgent(sharedBuffer, 0);
		depositorAgent depositor1 = new depositorAgent(sharedBuffer, 1);
		depositorAgent depositor2 = new depositorAgent(sharedBuffer, 2);
		depositorAgent depositor3 = new depositorAgent(sharedBuffer, 3);
		depositorAgent depositor4 = new depositorAgent(sharedBuffer, 4);
		
		// Auditor Agent threads:
		InternalBank auditor1 = new InternalBank(sharedBuffer);
		TreasuryDept auditor2 = new TreasuryDept(sharedBuffer);
		
		
		// Execute all threads:
		// Withdrawal Agent threads:
		application.execute(withdrawal0);
		application.execute(withdrawal1);
		application.execute(withdrawal2);
		application.execute(withdrawal3);
		application.execute(withdrawal4);
		application.execute(withdrawal5);
		application.execute(withdrawal6);
		application.execute(withdrawal7);
		application.execute(withdrawal8);
		application.execute(withdrawal9);
		
		// Depositor Agent threads:
		application.execute(depositor0);
		application.execute(depositor1);
		application.execute(depositor2);
		application.execute(depositor3);
		application.execute(depositor4);
		
		// Auditor Agent threads:
		application.execute(auditor1);
		application.execute(auditor2);
		
	} // End of main
} // End of BankSimulation