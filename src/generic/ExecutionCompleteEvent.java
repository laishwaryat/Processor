package generic;

public class ExecutionCompleteEvent extends Event {
	int op1;
	int op2;
	String opcode;
	
	public ExecutionCompleteEvent(long eventTime, Element requestingElement, Element processingElement,int op1,int op2,String opcode)
	{
		super(eventTime, EventType.ExecutionComplete, requestingElement, processingElement);
		this.op1=op1;
		this.op2=op2;
		this.opcode=opcode;
	}

	public int getOp1() {
		return op1;
	}

	public void setOp1(int op1) {
		this.op1 = op1;
	}

	public int getOp2() {
		return op2;
	}

	public void setOp2(int op2) {
		this.op2 = op2;
	}

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	



}