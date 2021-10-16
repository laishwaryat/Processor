package processor.pipeline;

import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Simulator;
import processor.Clock;
import processor.Processor;
import processor.memorysystem.MainMemory;

public class MemoryAccess implements Element{
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;

	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	int convertInt (String s) {
		long dv = Long.parseLong(s, 2);
		
		if(s.charAt(0)=='1') {
			 dv=(int) (dv-Math.pow(2, s.length()));	
		}
		int i=(int) dv;
	    return i;
	}
	
	public void performMA()
	{
		System.out.println("############_Memory Access_##################");
		
		if (EX_MA_Latch.isMA_busy()==true)
		{	EX_MA_Latch.setMA1_busy(true);
			System.out.println("MA is busy");
		return ;
		}	
		
		MainMemory m=Simulator.getMm();
		//TODO
		
		
		
		if(EX_MA_Latch.isDiv()==true) {
			MA_RW_Latch.setDiv(true);
		}
		 
		if(EX_MA_Latch.isMA_enable() && Simulator.isSimulationComplete()==false )
		{
		
		
			System.out.println("entered memory Access");
			
			
			if(EX_MA_Latch.isIS_Load()==true) {
				System.out.println("entered Load loop");
				MA_RW_Latch.setISLoad(true);
				String alu=EX_MA_Latch.getALUResult();
				int memaddr=convertInt(alu);
				MA_RW_Latch.setDestAdd(EX_MA_Latch.getDestReg());
				Simulator.getEventQueue().addEvent (
						new MemoryReadEvent (
						Clock.getCurrentTime()+Configuration.mainMemoryLatency,
						this,
						containingProcessor.getMainMemory(),
						memaddr));
				EX_MA_Latch.setMA_busy(true);
				EX_MA_Latch.setMA1_busy(true);
				//MA_RW_Latch.setMA_busy(true);
				System.out.println("MA_Load is set to busy");
				/*int i=m.getWord(memaddr);
				
				System.out.println(i+" value stored in memory");
				//String ld=containingProcessor.convertToBinary(i);
				MA_RW_Latch.setLDResult(i);
				System.out.println(EX_MA_Latch.getDestReg()+" is dest reg");
				MA_RW_Latch.setDestAdd(EX_MA_Latch.getDestReg());
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);*/
			}
			
			//**********************@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@********************************
			else if(EX_MA_Latch.IS_Store==true) {
				String alu=EX_MA_Latch.getALUResult();
				
					int i=convertInt(alu);
					int op1=EX_MA_Latch.getStoreReg();
					Simulator.getEventQueue().addEvent (
							new MemoryWriteEvent (
							Clock.getCurrentTime()+Configuration.mainMemoryLatency,
							this,
							containingProcessor.getMainMemory(),
							i,op1)); 
					System.out.println("MA_Store is set to busy");
				//	m.setWord(i,op1);
				System.out.println("memory addr "+i);
				System.out.println("value stored in mem "+op1);
				
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);
			
			}
			
			//******************************@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*************************************
			else if(EX_MA_Latch.isAlu()==true){
				MA_RW_Latch.setISALU(true);
				MA_RW_Latch.setALURes(EX_MA_Latch.getALUResult());
				
				MA_RW_Latch.setDestAdd(EX_MA_Latch.getDestReg());
				
				MA_RW_Latch.setREM(EX_MA_Latch.getRem());
				
				MA_RW_Latch.setIS_Cmp(EX_MA_Latch.isIS_Cmp());
				
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);
			}
			if(EX_MA_Latch.isIs_end()==true) {
				MA_RW_Latch.setIs_end(true);
				
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);
			}
		
		}
			
		EX_MA_Latch.setIS_Load(false);
		EX_MA_Latch.setIS_Store(false);
		EX_MA_Latch.setIS_Cmp(false);
		EX_MA_Latch.setIs_end(false);
		EX_MA_Latch.setAlu(false);
		EX_MA_Latch.setDiv(false);
			
	}
	@Override
	public void handleEvent(Event e) {
		MemoryResponseEvent event = (MemoryResponseEvent) e ;
		System.out.println("in Handle event MA: value of event "+event.getValue());
	
		MA_RW_Latch.setLDResult(event.getValue());
		
		//System.out.println("dest reg no "+EX_MA_Latch.getDestReg());
		//EX_MA_Latch.setMA_enable(false);
		MA_RW_Latch.setRW_enable(true);
		EX_MA_Latch.setMA_busy(false);
		MA_RW_Latch.setISLoad(true);
		MA_RW_Latch.setMA_busy(false);

		
	}

}
