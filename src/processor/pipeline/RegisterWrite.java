package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	public static int i=0;

	int convertInt (String s) {
		long dv = Long.parseLong(s, 2);
		
		if(s.charAt(0)=='1') {
			 dv=(int) (dv-Math.pow(2, s.length()));	
		}
		int i=(int) dv;
	    return i;
	}
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	
	public void performRW()
	{
		System.out.println("############_Register write_##################");
		RegisterFile rf=Simulator.getRf();
		
		
		System.out.println("is simulation complete "+Simulator.isSimulationComplete());
		
		if(MA_RW_Latch.isRW_enable() && Simulator.isSimulationComplete()==false)
		{
			
			i=i+1;
			//TODO
			System.out.println("entered register write");
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			if(MA_RW_Latch.isISLoad()==true) {
				int ld=MA_RW_Latch.getLDResult();
				//System.out.println(ld);
				//int l=convertInt(ld);
				int d=MA_RW_Latch.getDestAdd();
				//int d=convertInt(dst);
				System.out.println("Load ins");
			System.out.println("dest"+" "+d);
			System.out.println("value in dest "+ld);
				//System.out.println(d);
				rf.setValue(d,ld);
				
			}
			if(MA_RW_Latch.isISALU()==true && MA_RW_Latch.IS_Cmp==false){
				String alu=MA_RW_Latch.getALURes();
				//System.out.println(alu);
			//***	System.out.println("beforeConvert");
				int l=(int)convertInt(alu);
			//***	System.out.println("couldConvert");
				int d=MA_RW_Latch.getDestAdd();
				String rem=MA_RW_Latch.getREM();
				int r=convertInt(rem);
				//int d=convertInt(dst);
			System.out.println("dest"+" "+d);
			System.out.println("value in dest "+l);
			System.out.println("value stored in 31 reg "+r);
				rf.setValue(31,r);
				rf.setValue(d,l);
			}
			
			if(MA_RW_Latch.isIs_end()==true) {
				Simulator.setSimulationComplete(true);	
				System.out.println("in RW stage sim complete is true");
				}
		
			IF_EnableLatch.setIF_enable(true);
			MA_RW_Latch.setRW_enable(false);
			
		}
		
		MA_RW_Latch.setISALU(false);
		MA_RW_Latch.setISLoad(false);
		MA_RW_Latch.setIS_Cmp(false);
		MA_RW_Latch.setIs_end(false);
		IF_EnableLatch.setIF_enable(true);
		MA_RW_Latch.setDiv(false);
		
		System.out.println("IF_enable "+IF_EnableLatch.isIF_enable());
		if(containingProcessor.getRegisterFile().getProgramCounter()==65536) {
			Simulator.setSimulationComplete(true);
		}
	}

}
