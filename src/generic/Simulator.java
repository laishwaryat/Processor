package generic;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import processor.Clock;
import processor.Processor;
import processor.memorysystem.MainMemory;
import processor.pipeline.InstructionFetch;
import processor.pipeline.RegisterFile;
import processor.pipeline.RegisterWrite;

public class Simulator {
		static MainMemory mm;
	static Processor processor;
	static boolean simulationComplete;
	static RegisterFile rf;
	public static int count=0;
	static int numins=0;
	static EventQueue eventQueue;
	
	public Simulator() {
		eventQueue = new EventQueue();
	}
	
	public static void setupSimulation(String assemblyProgramFile, Processor p) throws IOException
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile) throws IOException
	{
		
		mm= new MainMemory();
		rf=new RegisterFile();
		int numi=0;
		File file = new File(assemblyProgramFile);
		FileInputStream fin = new FileInputStream(file);
		BufferedInputStream bin = new BufferedInputStream(fin);
		@SuppressWarnings("resource")
		DataInputStream din = new DataInputStream(bin);

		int count = (int) (file.length() / 4);
		for (int i = 0; i < count; i++) {
			if(i==0) {
				numi=din.readInt();
			}else {
			 mm.setWord(i-1, din.readInt());}
		}
		
		////System.out.println(mm.getWord(1)+" from sim");
		processor.setMainMemory(mm);
		processor.setRegisterFile(rf);
	System.out.println(processor.getMainMemory().getContentsAsString(0, 30));
		processor.getRegisterFile().setProgramCounter(numi);
		rf.setValue(0,0);
		rf.setValue(1,65535);
		rf.setValue(2,65535);
		
		
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
	}
	
	
	
	public static MainMemory getMm() {
		return mm;
	}

	public static void setMm(MainMemory mm) {
		Simulator.mm = mm;
	}

	public static RegisterFile getRf() {
		return rf;
	}

	public static void setRf(RegisterFile rf) {
		Simulator.rf = rf;
	}

	public static void simulate()
	{			eventQueue = new EventQueue();
		while(simulationComplete == false)
			{	
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			count=count+1;
			processor.getRWUnit().performRW();
			
			processor.getMAUnit().performMA();
			
			processor.getEXUnit().performEX();
			eventQueue.processEvents();
			processor.getOFUnit().performOF();
			
			processor.getIFUnit().performIF();
			
			Clock.incrementClock();
		}
		//Statistics.setNumberOfInstructions(Execute.numOfIns-1);
		Statistics.setNumberOfCycles(Clock.getCurrentTime());
		System.out.println(Clock.getCurrentTime());
		
		//System.out.println(RegisterWrite.i+" numof ins final");
		// TODO
		// set statistics
		
		}
	
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}

	public static EventQueue getEventQueue() {
		return eventQueue;
	}

	public static boolean isSimulationComplete() {
		return simulationComplete;
	}

	
	
}
