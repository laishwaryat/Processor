package processor.pipeline;

import processor.Processor;

public class InsertNOP {
	  Processor containingProcessor;
	  OF_EX_LatchType OF_EX_Latch;
	  EX_MA_LatchType EX_MA_Latch;
	 MA_RW_LatchType MA_RW_Latch;
public static int numOfStall=0;
	
	

	public InsertNOP(Processor containingProcessor,OF_EX_LatchType OF_EX_Latch,EX_MA_LatchType EX_MA_Latch,MA_RW_LatchType MA_RW_Latch) {
		super();
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch=OF_EX_Latch;
		this.EX_MA_Latch=EX_MA_Latch;
		this.MA_RW_Latch=MA_RW_Latch;
	}

	static String getType(String op) {
		if(op.equals("00000") || op.equals("00010") || op.equals("00100") || op.equals("00110") || op.equals("01000") || op.equals("01010") || op.equals("01100") || op.equals("01110") || op.equals("10000") || op.equals("10010") || op.equals("10100"))
		{
			return "R3";
		}
		else if(op.equals("10110")){
		return "RILoad";	
		}else if(op.equals("11101")){
			return "RIEnd";
			}
		else if(op.equals("11001") || op.equals("11010") || op.equals("11011") || op.equals("11100") || op.equals("10111")){
			return "R2IcmpSt";
		}
		else return "R2I";
	}
	
	
	
	
	/* String getDest(int i) {
		String s1=getOPCode(i);
		String type=getType(s1);
		
		if(type.equals("R3")) {
			return (getRD(i));
		}
		else if(type.equals("R2I")) {
			return (getOper2(i));
		}
		return "nothing";
	}*/
	public   String getOPCode(int num) {
		String a=containingProcessor.convertToBinary(num);
		//System.out.println("converted to binary");
		String sub=a.substring(0,5);
		return sub;
	}
	public   String getBRImm(int num) {
		String a=containingProcessor.convertToBinary(num);
		String sub=a.substring(15,32);
		return sub;	
	}
	public   String getOper1(int num) {
		String a=containingProcessor.convertToBinary(num);
		String sub=a.substring(5,10);
		return "0"+sub;	
	}
	public   String getOper2(int num) {
		String a=containingProcessor.convertToBinary(num);
		String sub=a.substring(10,15);
		return "0"+sub;	
	}
	public  String getRD(int num) {
		String a=containingProcessor.convertToBinary(num);
		String sub=a.substring(15,20);
		return sub;	
	}
	static int convertInt (String s) {
		long dv = Long.parseLong(s, 2);
		
		if(s.charAt(0)=='1') {
			 dv=(int) (dv-Math.pow(2, s.length()));	
		}
		int i=(int) dv;
	    return i;
	}
	
	public  boolean checkIsStall(int ins) {
		
		String op=getOPCode(ins);
		
		if(getType(op).equals("RILoad")) {
			
			System.out.println("entered RILoady in InsertNOP");
			
			if((OF_EX_Latch.getRD()==convertInt(getOper1(ins)) && OF_EX_Latch.isIS_ALU()==true) || (EX_MA_Latch.getDestReg()==convertInt(getOper1(ins)) && EX_MA_Latch.isAlu()==true) || (MA_RW_Latch.getDestAdd()==convertInt(getOper1(ins)) && MA_RW_Latch.isISALU()==true)) {
				return true;
			}else if((OF_EX_Latch.getRD()==convertInt(getOper1(ins)) && OF_EX_Latch.isIS_Load()==true) || (EX_MA_Latch.getDestReg()==convertInt(getOper1(ins)) && EX_MA_Latch.isIS_Load()==true) || (MA_RW_Latch.getDestAdd()==convertInt(getOper1(ins)) && MA_RW_Latch.isISLoad()==true)) {
				return true;
			}
			else if((convertInt(getOper1(ins))==31 && OF_EX_Latch.isDiv()==true) || (convertInt(getOper1(ins))==31 && EX_MA_Latch.isDiv()==true) || (convertInt(getOper1(ins))==31 && MA_RW_Latch.isDiv()==true)) {
				return true;
			}
			
		}
		else if(getType(op).equals("R3") || getType(op).equals("R2IcmpSt")) {
			System.out.println("getOper2 "+getOper2(ins));
			System.out.println("oper2 "+convertInt(getOper2(ins)));
			System.out.println("isDiv in OF_EX_latch "+OF_EX_Latch.isDiv());
			System.out.println("entered R3 or R2IcmpSt in InsertNOP");
			if(((OF_EX_Latch.getRD()==convertInt(getOper1(ins)) || OF_EX_Latch.getRD()==convertInt(getOper2(ins))) && (OF_EX_Latch.isIS_ALU()==true || OF_EX_Latch.isIS_Load()==true)) || ((EX_MA_Latch.getDestReg()==convertInt(getOper1(ins)) || EX_MA_Latch.getDestReg()==convertInt(getOper2(ins))) && (EX_MA_Latch.isAlu()==true || EX_MA_Latch.isIS_Load()==true)) || ((MA_RW_Latch.getDestAdd()==convertInt(getOper1(ins)) || MA_RW_Latch.getDestAdd()==convertInt(getOper2(ins))) && (MA_RW_Latch.isISALU()==true || MA_RW_Latch.isISLoad()==true))){
				return true;
			}else if(((convertInt(getOper1(ins))==31 || convertInt(getOper2(ins))==31 ) && OF_EX_Latch.isDiv()==true) || ((convertInt(getOper1(ins))==31 || convertInt(getOper2(ins))==31 ) && EX_MA_Latch.isDiv()==true) || ((convertInt(getOper1(ins))==31 || convertInt(getOper2(ins))==31 ) && MA_RW_Latch.isDiv()==true)) {
				return true;
			}
		}
		else if(getType(op).equals("R2I")) {
			System.out.println("entered R2I in InsertNOP");
			System.out.println("oper1 in insertNOP "+convertInt(getOper1(ins)));
			if((OF_EX_Latch.getRD()==convertInt(getOper1(ins)) && (OF_EX_Latch.isIS_ALU()==true || OF_EX_Latch.isIS_Load()==true)) || (EX_MA_Latch.getDestReg()==convertInt(getOper1(ins)) && (EX_MA_Latch.isAlu()==true || EX_MA_Latch.isIS_Load()==true)) || (MA_RW_Latch.getDestAdd()==convertInt(getOper1(ins)) && (MA_RW_Latch.isISALU()==true || MA_RW_Latch.isISLoad()==true))) {
				return true;
		}else if((convertInt(getOper1(ins))==31 && OF_EX_Latch.isDiv()==true) || (convertInt(getOper1(ins))==31 && EX_MA_Latch.isDiv()==true) || (convertInt(getOper1(ins))==31 && MA_RW_Latch.isDiv()==true)) {
			return true;
		}
		}
		
		return false;
		
	}
	

}
