package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_Latch;
	//InsertNOP insertNop;
	static String type=null;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch,EX_MA_LatchType EX_MA_Latch,MA_RW_LatchType MA_RW_Latch,IF_EnableLatchType IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch=EX_MA_Latch;
		this.MA_RW_Latch=MA_RW_Latch;
		this.IF_Latch=IF_Latch;

	}
	
	
	String getType(String a) {
		if(a.equals("00000") || a.equals("00010") || a.equals("00100") || a.equals("00110") || a.equals("01000") || a.equals("01010") || a.equals("01100") || a.equals("01110") || a.equals("10000") || a.equals("10010") || a.equals("10100"))
		{
			type="R3";
		}
		else if(a.equals("11000") || a.equals("11101"))
		{
			type="RI";
		}
		else {
			type="R2I";
		}
			return type;
	}
	
	
	
	public String getOPCode(int num) {
		String a=containingProcessor.convertToBinary(num);
		String sub=a.substring(0,5);
		return sub;
	}
	public String getBRImm(int num) {
		String a=containingProcessor.convertToBinary(num);
		String sub=a.substring(15,32);
		return sub;	
	}
	public String getJmpImm(int num) {
		String a=containingProcessor.convertToBinary(num);
		String sub=a.substring(10,32);
		return sub;	
	}
	public String getOper1(int num) {
		String a=containingProcessor.convertToBinary(num);
		String sub=a.substring(5,10);
		return "0"+sub;	
	}
	public String getOper2(int num) {
		String a=containingProcessor.convertToBinary(num);
		String sub=a.substring(10,15);
		return "0"+sub;	
	}
	public String getRD(int num) {
		String a=containingProcessor.convertToBinary(num);
		String sub=a.substring(15,20);
		return "0"+sub;	
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
	
	public void performOF()
	{System.out.println("############_Operand Fetch_##################");
		int i=0,j=0;
		RegisterFile rs =Simulator.getRf();
		
		InsertNOP np=new InsertNOP(containingProcessor,OF_EX_Latch,EX_MA_Latch,MA_RW_Latch);
		if(IF_OF_Latch.isOF_enable()==false && OF_EX_Latch.isJmpEnd()==true) {
			i=1;
		}else {
			i=0;
		}
		
		if(IF_OF_Latch.isOF_enable()==false && OF_EX_Latch.isBrEnd()==true) {
			j=1;
		}else {
			j=0;
		}
		if(OF_EX_Latch.isMA1_busy()==true) {
			IF_OF_Latch.setMA1_busy(true);
		}
		if(OF_EX_Latch.isEX1_busy()==true) {
			IF_OF_Latch.setEX1_busy(true);;
		}
		
		if(IF_OF_Latch.isOF_enable()  && IF_OF_Latch.isStall()==false && Simulator.isSimulationComplete()==false && OF_EX_Latch.isJmpEnd()==false && OF_EX_Latch.isBrEnd()==false && OF_EX_Latch.isMA1_busy()==false && OF_EX_Latch.isEX1_busy()==false)
		{ 
			//TODO
			
			int ins=IF_OF_Latch.getInstruction();
			System.out.println("the ins being processed "+ins);
			String op=getOPCode(ins);
			System.out.println("opcode of the ins "+op);
			System.out.println("entered operand fetch");
			OF_EX_Latch.setOPCode(op);
			if(op.equals("00110") || op.equals("00111")) {
				System.out.println("entered the div if in operand fetch");
				OF_EX_Latch.setDiv(true);
			}
		
			if(op.equals("10110")) {	//Load
				
				if(np.checkIsStall(ins)==false) {
					System.out.println("checknop in load "+false);
					OF_EX_Latch.setStall(false);
				OF_EX_Latch.setIS_Load(true);	
				OF_EX_Latch.setOP2(convertInt(getBRImm(ins)));
				OF_EX_Latch.setOP1(rs.getValue(convertInt(getOper1(ins))));
				OF_EX_Latch.setRD(convertInt(getOper2(ins)));}
				else {
					System.out.println("checknop in load "+true);
					OF_EX_Latch.setStall(true);
					IF_OF_Latch.setStall(true);
				}
			
			}
			else if(op.equals("10111")) { //Store
				
				if(np.checkIsStall(ins)==false) {
					System.out.println("checknop in store "+false);
				OF_EX_Latch.setStall(false);
				OF_EX_Latch.setIS_Store(true);
				OF_EX_Latch.setMem(convertInt(getBRImm(ins))+rs.getValue(convertInt(getOper2(ins))));
			 	OF_EX_Latch.setOP1(rs.getValue(convertInt(getOper1(ins))));}
				else {
					System.out.println("checknop in store "+true);
				OF_EX_Latch.setStall(true);
				IF_OF_Latch.setStall(true);
				}
				
			}
			else if(op.equals("11001") || op.equals("11010") || op.equals("11011") || op.equals("11100") ) { // Beq Bne Blt Bgt
				
				if(np.checkIsStall(ins)==false) {
					System.out.println("checknop in cmp "+false);
					OF_EX_Latch.setStall(false);
				OF_EX_Latch.setIS_compare(true);
				OF_EX_Latch.setBranchTarget(getBRImm(ins));
				OF_EX_Latch.setOP1(rs.getValue(convertInt(getOper1(ins))));
				OF_EX_Latch.setOP2(rs.getValue(convertInt(getOper2(ins))));
				}
				else {
					System.out.println("checknop in cmp "+true);
					OF_EX_Latch.setStall(true);
					IF_OF_Latch.setStall(true);
				}
				
				
			}
			
			else if(op.equals("11000")) {
				
				OF_EX_Latch.setBranchTarget(getJmpImm(ins));
			
			}
			else if(getType(op).equals("R3")) {
				
				if(np.checkIsStall(ins)==false) {
					System.out.println("checknop in R3 "+false);
					OF_EX_Latch.setStall(false);
				OF_EX_Latch.setIS_ALU(true);
				OF_EX_Latch.setOP1(rs.getValue(convertInt(getOper1(ins))));
				OF_EX_Latch.setOP2(rs.getValue(convertInt(getOper2(ins))));
				OF_EX_Latch.setRD(convertInt(getRD(ins)));}
				else {
					System.out.println("checknop in R3 "+true);
					OF_EX_Latch.setStall(true);
					IF_OF_Latch.setStall(true);
					}
			}
			else if(op.equals("11101")) {	//End
				
			}
			
			else  {			//imm ALU
				
				if(np.checkIsStall(ins)==false) {
					System.out.println("checknop in immi "+false);
					OF_EX_Latch.setStall(false);
				OF_EX_Latch.setIS_ALU(true);
				OF_EX_Latch.setOP2(convertInt(getBRImm(ins)));
				OF_EX_Latch.setOP1(rs.getValue(convertInt(getOper1(ins))));
				OF_EX_Latch.setRD(convertInt(getOper2(ins)));
				}
				
				else {
					System.out.println("checknop in immi "+true);
					OF_EX_Latch.setStall(true);
					IF_OF_Latch.setStall(true);
				}
				
			}
			
			if(IF_OF_Latch.isIs_end()==true) {
				OF_EX_Latch.setIS_End(true);
			}
			
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
			
			
		}
		
		if(OF_EX_Latch.isMA1_busy()==false && OF_EX_Latch.isEX1_busy()==false) {
		if(i==0) {
			OF_EX_Latch.setJmpEnd(false);
			}
			if(j==0) {
			OF_EX_Latch.setBrEnd(false);
			}
		IF_OF_Latch.setIs_end(false);
		//IF_Latch.setIF_enable(true);
		IF_OF_Latch.setOF_enable(false);
		}
		OF_EX_Latch.setEX1_busy(false);
		OF_EX_Latch.setMA1_busy(false);
		
	}

}
