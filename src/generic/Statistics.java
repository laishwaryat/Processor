package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	static int numberOfInstructions;
	static long numberOfCycles;
	static  int wrongPath;
	 static int numOfStall;

	public static void printStatistics(String statFile)
	{
		try
		{
			
			PrintWriter writer = new PrintWriter(statFile);
			
			//writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			//writer.println("Number of times instructions on  wrong branch path entered pipeline taken = " + wrongPath);
			//writer.println("Number of stalls = " + numOfStall);
			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics
	
	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public static void setNumberOfCycles(long numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}

	public static void setWrongPath(int wrongPath) {
		// TODO Auto-generated method stub
		Statistics.wrongPath=wrongPath;
	}

	public static void setNumberOfStalls(int numOfStall) {
		// TODO Auto-generated method stub
		Statistics.numOfStall=numOfStall;
		
	}
}
