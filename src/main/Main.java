package main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import configuration.Configuration;
import generic.Statistics;
import processor.Processor;
import processor.memorysystem.MainMemory;
import processor.pipeline.RegisterFile;
import generic.Misc;
import generic.Simulator;

/*
descending.out            =          -878251457
evenorodd.out             =            286481597 
fibonacci.out                =          -432822385
palindrome.out             =           1384542853 
prime.out                     =           1746953974
add.asm						=			719511628
data.asm					=			1479805189
jmp.asm						=			-448587852
 */
/*
 prime.out = -136430165
 palindrome.out = 1665293250
 fibonacci.out = -1224106554
 even-odd.out = 190621344
 descending.out =-864847383
  
  */


public class Main {
	private static Scanner in;
	
	public static void main(String[] args) throws IOException {
		
		in = new Scanner(System.in);
		//String s0 = in.nextLine();
		String s1 = in.nextLine();
		String s2 = in.nextLine();
		
		
		
		
		/*if(args.length != 3)
		{
			Misc.printErrorAndExit("usage: java -jar <path-to-jar-file> <path-to-config-file> <path-to-stat-file> <path-to-object-file>\n");
		}*/
		
		Configuration.parseConfiguratioFile("config.xml");
		
		Processor processor = new Processor();
		
		Simulator.setupSimulation(s2, processor);
		Simulator.simulate();
		
		processor.printState(0, 30); // ((0, 0) refers to the range of main memory addresses we wish to print. this is an empty set.
		
		Statistics.printStatistics(s1);
		
		System.out.println("Hash of the Processor State = "+getHashCode(processor.getRegisterFile(), processor.getMainMemory()));
	}
	
	static int getHashCode(RegisterFile registerState, MainMemory memoryState) {
		ArrayList<Integer> hash = new ArrayList<Integer>();
		
		
		for(int i=0;i<32;i++) {
			hash.add(registerState.getValue(i));
		}
		
		for(int i=0;i<65536;i++) {
			hash.add(memoryState.getWord(i));
		}
		
		return hash.hashCode();
	}

}
