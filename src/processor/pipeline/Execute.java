package processor.pipeline;

import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.ExecutionCompleteEvent;
import generic.Simulator;
import processor.Clock;
import processor.Processor;

public class Execute implements Element{
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	RegisterFile rs;
	public static int numOfIns=0;
	public static int numOfCycles=0;
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	int convertInt (String s) {
		long dv = Long.parseLong(s, 2);
		//int dv= (int) i;
		if(s.charAt(0)=='1') {
			 dv=(int) (dv-Math.pow(2, s.length()));	
		}
		int i=(int) dv;
	    return i;
	}
	
	
	
	public void performEX()
	{
		numOfCycles=numOfCycles+1;
		System.out.println("############_Execute_##################");
		//TODO
		if(OF_EX_Latch.isEX_busy()==true) {
			System.out.println("EX is busy");
			OF_EX_Latch.setEX1_busy(true);
			return;
		}
		
		if(OF_EX_Latch.isIS_End()==true) {
			EX_MA_Latch.setIs_end(true);
		}
	if(EX_MA_Latch.isMA1_busy()==true) {
		OF_EX_Latch.setMA1_busy(true);
	}
		
		if(OF_EX_Latch.isEX_enable() && OF_EX_Latch.isStall()==false && Simulator.isSimulationComplete()==false && EX_MA_Latch.isMA1_busy()==false) {
			numOfIns=numOfIns+1;
			System.out.println("entered execute");
			
			
			if(OF_EX_Latch.IS_ALU==true) {
				System.out.println("entered ALU loop");
				
					EX_MA_Latch.setAlu(true);
						int op1=OF_EX_Latch.getOP1();
						
						System.out.println("value of op1 "+op1);
						int op2=OF_EX_Latch.getOP2();
						System.out.println("value of op2 "+op2);
						EX_MA_Latch.setDestReg(OF_EX_Latch.getRD());
						if(OF_EX_Latch.getOPCode().equals("00001") || OF_EX_Latch.getOPCode().equals("00000")) {
							int res=op1+op2;
							System.out.println("result "+res);
							String bin=containingProcessor.convertToBinary(res);
							EX_MA_Latch.setALUResult(bin);
							
						}
						else if(OF_EX_Latch.getOPCode().equals("00011") || OF_EX_Latch.getOPCode().equals("00010")) {
							int res=op1-op2;
							System.out.println("result "+res);
							String bin=containingProcessor.convertToBinary(res);
							EX_MA_Latch.setALUResult(bin);
							
						}
						else if(OF_EX_Latch.getOPCode().equals("00101") || OF_EX_Latch.getOPCode().equals("00100")) {
							Simulator.getEventQueue().addEvent (
									new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.multiplier_latency,
									this,
									this,
									op1,
									op2,
									OF_EX_Latch.getOPCode()
									));
							OF_EX_Latch.setEX_busy(true);
							OF_EX_Latch.setEX1_busy(true);
							System.out.println("EX is set to busy in mul");
							/*int res=op1*op2;
							System.out.println("result "+res);
							String bin=containingProcessor.convertToBinary(res);
							EX_MA_Latch.setALUResult(bin);*/
							
						}
						else if(OF_EX_Latch.getOPCode().equals("00111") || OF_EX_Latch.getOPCode().equals("00110")) {
							Simulator.getEventQueue().addEvent (
									new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.divider_latency,
									this,
									this,
									op1,
									op2,
									OF_EX_Latch.getOPCode()
									));
							OF_EX_Latch.setEX_busy(true);
							OF_EX_Latch.setEX1_busy(true);
							System.out.println("EX is set to busy in div");
							/*int res=op1/op2;
							int rem=op1%op2;
							EX_MA_Latch.setDiv(true);
							System.out.println("result "+res);
							System.out.println("rem "+rem);
							String bin=containingProcessor.convertToBinary(res);
							String binrem=containingProcessor.convertToBinary(rem);
							
							EX_MA_Latch.setALUResult(bin);
							
							
							EX_MA_Latch.setRem(binrem);*/
						}
						else if(OF_EX_Latch.getOPCode().equals("01001") || OF_EX_Latch.getOPCode().equals("01000")) {
							int res=op1 & op2;
							System.out.println("result "+res);
							String bin=containingProcessor.convertToBinary(res);
							EX_MA_Latch.setALUResult(bin);
							
						}
						else if(OF_EX_Latch.getOPCode().equals("01011") || OF_EX_Latch.getOPCode().equals("01010")) {
							int res=op1 | op2;
							System.out.println("result "+res);
							String bin=containingProcessor.convertToBinary(res);
							EX_MA_Latch.setALUResult(bin);
							
						}
						else if(OF_EX_Latch.getOPCode().equals("01101") || OF_EX_Latch.getOPCode().equals("01100")) {
							int res=op1 ^ op2;
							System.out.println("result "+res);
							String bin=containingProcessor.convertToBinary(res);
							EX_MA_Latch.setALUResult(bin);
													}
						else if(OF_EX_Latch.getOPCode().equals("01111") || OF_EX_Latch.getOPCode().equals("01110")) {
							int res;
							if(op1<op2) {
								res=1;
							}else {
								res=0;
							}
							System.out.println("result "+res);
							String bin=containingProcessor.convertToBinary(res);
							EX_MA_Latch.setALUResult(bin);
							
						}
						else if(OF_EX_Latch.getOPCode().equals("10001") || OF_EX_Latch.getOPCode().equals("10000")) {
							int res=op1 << op2;
							System.out.println("result "+res);
							String bin=containingProcessor.convertToBinary(res);
							EX_MA_Latch.setALUResult(bin);
							
						}
						else if(OF_EX_Latch.getOPCode().equals("10011") || OF_EX_Latch.getOPCode().equals("10010")) {
							int res=op1 >>> op2;
							System.out.println("result "+res);
							String bin=containingProcessor.convertToBinary(res);
							EX_MA_Latch.setALUResult(bin);
							
						}
						else if(OF_EX_Latch.getOPCode().equals("10101") || OF_EX_Latch.getOPCode().equals("10100")) {
							int res=op1 >> op2;
							System.out.println("result "+res);
							String bin=containingProcessor.convertToBinary(res);
							EX_MA_Latch.setALUResult(bin);
							
						}
					
								
				}
			
			if(OF_EX_Latch.IS_compare==true) {
				int op1=OF_EX_Latch.getOP1();
			
			System.out.println(op1+" cop1");
				int op2=OF_EX_Latch.getOP2();	
				
			System.out.println(op2+" cmpop2");
				if(OF_EX_Latch.getOPCode().equals("11001")) {
					EX_MA_Latch.setIS_Cmp(true);
					if(op1==op2) {
						EX_IF_Latch.setBranchTaken(true);
						OF_EX_Latch.setBrEnd(true);
						
						int x=convertInt(OF_EX_Latch.getBranchTarget());
						int y=containingProcessor.getRegisterFile().getProgramCounter();
						int z=x+y-2;
						System.out.println("is going to branch in beq"+z);
						EX_IF_Latch.setBranchPC(containingProcessor.convertToBinary(z));
					}
				}
				if(OF_EX_Latch.getOPCode().equals("11010")) {
					EX_MA_Latch.setIS_Cmp(true);
					if(op1!=op2) {
						EX_IF_Latch.setBranchTaken(true);
						OF_EX_Latch.setBrEnd(true);
						
						int x=convertInt(OF_EX_Latch.getBranchTarget());
						int y=containingProcessor.getRegisterFile().getProgramCounter();
						int z=x+y-2;
						System.out.println("is going to branch in bne"+z);
						EX_IF_Latch.setBranchPC(containingProcessor.convertToBinary(z));
					}
				}
			   if(OF_EX_Latch.getOPCode().equals("11011")) {
				   EX_MA_Latch.setIS_Cmp(true);
						if(op1<op2) {
							EX_IF_Latch.setBranchTaken(true);
							OF_EX_Latch.setBrEnd(true);
							
							int x=convertInt(OF_EX_Latch.getBranchTarget());
							int y=containingProcessor.getRegisterFile().getProgramCounter();
							int z=x+y-2;
						System.out.println("is going to branch in blt"+z);
							EX_IF_Latch.setBranchPC(containingProcessor.convertToBinary(z));
						}
			}
			   if(OF_EX_Latch.getOPCode().equals("11100")) {
				   EX_MA_Latch.setIS_Cmp(true);
							if(op1>op2){
								EX_IF_Latch.setBranchTaken(true);
								OF_EX_Latch.setBrEnd(true);
								
								int x=convertInt(OF_EX_Latch.getBranchTarget());
								int y=containingProcessor.getRegisterFile().getProgramCounter();
								int z=x+y-2;
								System.out.println("is going to branch in bgt"+z);
								EX_IF_Latch.setBranchPC(containingProcessor.convertToBinary(z));
							}
			}
				
			}
			if((OF_EX_Latch.getOPCode().equals("11000"))){	//JMP
				EX_IF_Latch.setBranchTaken(true);
				//EX_MA_Latch.setJmp(true);
				EX_MA_Latch.setIs_end(false);
				OF_EX_Latch.setIS_End(false);
				OF_EX_Latch.setJmpEnd(true);
				int x=convertInt(OF_EX_Latch.getBranchTarget());
				
				int y=containingProcessor.getRegisterFile().getProgramCounter();
				
				int z=x+y-2;
				System.out.println("is going to branch in jmp instruction "+z);
				EX_IF_Latch.setBranchPC(containingProcessor.convertToBinary(z));
				
			}
			if(OF_EX_Latch.getOPCode().equals("10110")) {	//Load
				EX_MA_Latch.setIS_Load(true);
				int op1=OF_EX_Latch.getOP1();
				
				int imm=OF_EX_Latch.getOP2();
				
				int res=op1+imm;
				System.out.println("from mem "+res);
				String bin=containingProcessor.convertToBinary(res);
				EX_MA_Latch.setALUResult(bin);
				EX_MA_Latch.setDestReg(OF_EX_Latch.getRD());
				System.out.println(OF_EX_Latch.getRD()+" in reg");
			}
			if(OF_EX_Latch.getOPCode().equals("10111")) {	//Store
				EX_MA_Latch.setIS_Store(true);
				int op1=OF_EX_Latch.getOP1();
				System.out.println("value stored is "+op1);
				
				int mem=OF_EX_Latch.getMem();
				System.out.println("in mem "+mem);
				
				String bin=containingProcessor.convertToBinary(mem);
				EX_MA_Latch.setALUResult(bin);
				EX_MA_Latch.setStoreReg(op1);
			}
			if(OF_EX_Latch.isIS_End()==true) {
			
				EX_IF_Latch.setIS_END(true);
				
				OF_EX_Latch.setEX_enable(false);
				EX_MA_Latch.setMA_enable(true);
				EX_IF_Latch.setIF_enable(true);
			}
			if(!OF_EX_Latch.getOPCode().equals("00111") && !OF_EX_Latch.getOPCode().equals("00110") && !OF_EX_Latch.getOPCode().equals("00101") && !OF_EX_Latch.getOPCode().equals("00100")) {
			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setMA_enable(true);
			EX_IF_Latch.setIF_enable(true);
			}
			
		}
		
		if(EX_MA_Latch.isMA1_busy()==false) {
		OF_EX_Latch.setIS_ALU(false);
		OF_EX_Latch.setIS_compare(false);
		OF_EX_Latch.setIS_End(false);
		OF_EX_Latch.setIS_Load(false);
		OF_EX_Latch.setIS_Store(false);
		OF_EX_Latch.setStall(false);
		OF_EX_Latch.setDiv(false);}
		EX_MA_Latch.setMA1_busy(false);
		
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		System.out.println("*@@@@*entered handle event of execute*@@@@*");
		ExecutionCompleteEvent e=(ExecutionCompleteEvent) event;
		String opcode=e.getOpcode();
		int op1=e.getOp1();
		int op2=e.getOp2();
		System.out.println("***** op1 "+op1);
		System.out.println("***** op2 "+op2);
		if(opcode.equals("00101") || opcode.equals("00100")) {
			int res=op1*op2;
			System.out.println("in handle event mul");
			String bin=containingProcessor.convertToBinary(res);
			EX_MA_Latch.setALUResult(bin);
			EX_MA_Latch.setAlu(true);
		}
		if(opcode.equals("00111") || opcode.equals("00110")) {
			int res=op1/op2;
			int rem=op1%op2;
			System.out.println("in handle event div");
			String bin=containingProcessor.convertToBinary(res);
			String binrem=containingProcessor.convertToBinary(rem);
			EX_MA_Latch.setALUResult(bin);
			EX_MA_Latch.setRem(binrem);	
			EX_MA_Latch.setAlu(true);
		}
		
		//OF_EX_Latch.setEX_enable(false);
		EX_MA_Latch.setMA_enable(true);
		EX_IF_Latch.setIF_enable(true);
		OF_EX_Latch.setEX_busy(false);
		OF_EX_Latch.setEX1_busy(false);
		
	}

}
