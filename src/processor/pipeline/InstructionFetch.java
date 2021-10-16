package processor.pipeline;

import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.Simulator;
import processor.Clock;
import processor.Processor;

public class InstructionFetch implements Element {

	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	int i = 0;
	public static int wrongPath=0;
	public static int numOfStalls=0;
	//public static int numOfIns;

	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch,
			IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch) {
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	int convertInt(String s) {
		long dv = Long.parseLong(s, 2);
		
		if (s.charAt(0) == '1') {
			dv = (int) (dv - Math.pow(2, s.length()));
		}
		int i = (int) dv;
		return i;
	}

	public void performIF() {
		
		System.out.println("############_Instruction fetch_##################");
		if (IF_EnableLatch.isIF_busy())
		{
			System.out.println("IF is busy");
		return ;
		}
		
		if (IF_EnableLatch.isIF_enable() && Simulator.isSimulationComplete() == false && IF_OF_Latch.isIs_end()==false && IF_OF_Latch.isMA1_busy()==false && IF_OF_Latch.isEX1_busy()==false) {
			
			i = i + 1;
			
			System.out.println("entered instruction fetch");
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();

		if (IF_OF_Latch.isStall()==true) {
				numOfStalls=numOfStalls+1;
				currentPC = currentPC - 1;
				
			}
			if (currentPC == 65536) {
				Simulator.setSimulationComplete(true);
			}
			System.out.println(EX_IF_Latch.isBranchTaken() + " is branch taken");
			if (EX_IF_Latch.isBranchTaken() == true && IF_OF_Latch.isStall()==false ) {
				wrongPath=wrongPath+1;
				
				int i = convertInt(EX_IF_Latch.getBranchPC());
				currentPC = i;
				containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			}
			else {
				
				containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			}
			System.out.println("currentPC " + currentPC);
			if(IF_OF_Latch.isStall()==false) {
			Simulator.getEventQueue().addEvent (
					new MemoryReadEvent (
					Clock.getCurrentTime()+Configuration.mainMemoryLatency,
					this,
					containingProcessor.getMainMemory(),
					currentPC));
			
			IF_EnableLatch.setIF_busy(true) ;}
			else {
				int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
				if (newInstruction == -402653184) {
					IF_OF_Latch.setIs_end(true);
				}
				IF_OF_Latch.setInstruction(newInstruction);
				EX_IF_Latch.setIF_enable(false);
				IF_EnableLatch.setIF_enable(false);
				IF_OF_Latch.setOF_enable(true);
				
			}
			
			
		}
		if(IF_OF_Latch.isMA1_busy()==false && IF_OF_Latch.isEX1_busy()==false) {
		EX_IF_Latch.setBranchTaken(false);
		EX_IF_Latch.setCmp_BrTaken(false);
		IF_OF_Latch.setStall(false);}
		IF_OF_Latch.setMA1_busy(false);
		IF_OF_Latch.setEX1_busy(false);
	}

	@Override
	public void handleEvent(Event e) {
		// TODO Auto-generated method stub
		if(IF_OF_Latch.isOF_busy())
		{
		e.setEventTime(Clock.getCurrentTime()+1) ;
		Simulator.getEventQueue().addEvent(e) ;
		}
		else
		{
		MemoryResponseEvent event = (MemoryResponseEvent) e ;
		System.out.println("in Handle event IF: value of event "+event.getValue());
		if (event.getValue() == -402653184) {
			IF_OF_Latch.setIs_end(true);
		}
		IF_OF_Latch.setInstruction(event.getValue());
		//IF_EnableLatch.setIF_enable(false);
		IF_OF_Latch.setOF_enable(true);
		EX_IF_Latch.setIF_enable(false);
		IF_EnableLatch.setIF_busy(false);
		
	}
	}
}
