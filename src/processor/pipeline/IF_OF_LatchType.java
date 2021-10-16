package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	int instruction;
	boolean stall;
	boolean is_end;
	boolean OF_busy;
	boolean MA1_busy;
	boolean EX1_busy;
	public IF_OF_LatchType()
	{
		OF_enable = false;
		stall=false;
		OF_busy=false;
		MA1_busy=false;
		EX1_busy=false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

	public boolean isStall() {
		return stall;
	}

	public void setStall(boolean stall) {
		this.stall = stall;
	}

	public boolean isIs_end() {
		return is_end;
	}

	public void setIs_end(boolean is_end) {
		this.is_end = is_end;
	}

	public boolean isOF_busy() {
		return OF_busy;
	}

	public void setOF_busy(boolean oF_busy) {
		OF_busy = oF_busy;
	}

	public boolean isMA1_busy() {
		return MA1_busy;
	}

	public void setMA1_busy(boolean mA1_busy) {
		MA1_busy = mA1_busy;
	}

	public boolean isEX1_busy() {
		return EX1_busy;
	}

	public void setEX1_busy(boolean eX1_busy) {
		EX1_busy = eX1_busy;
	}

	
}
