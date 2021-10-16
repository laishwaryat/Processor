package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	int LDResult;
	String ALURes;
	String REM;
	int DestAdd;
	boolean ISLoad;
	boolean ISALU;
	boolean IS_Cmp;
	boolean stall;
	boolean is_end;
	boolean isJmp;
	boolean isDiv;
	boolean MA_busy;
	public MA_RW_LatchType()
	{
		RW_enable = false;
		ISLoad=false;
		ISALU=false;
		IS_Cmp=false;
		stall=false;
		is_end=false;
		MA_busy=false;
		isJmp=false;
		
		isDiv=false;
	}

	public boolean isDiv() {
		return isDiv;
	}

	public void setDiv(boolean isDiv) {
		this.isDiv = isDiv;
	}

	

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	public int getLDResult() {
		return LDResult;
	}

	public void setLDResult(int lDResult) {
		LDResult = lDResult;
	}

	public String getALURes() {
		return ALURes;
	}

	public void setALURes(String aLURes) {
		ALURes = aLURes;
	}

	public int getDestAdd() {
		return DestAdd;
	}

	public void setDestAdd(int destAdd) {
		DestAdd = destAdd;
	}

	public boolean isISLoad() {
		return ISLoad;
	}

	public void setISLoad(boolean iSLoad) {
		ISLoad = iSLoad;
	}

	public boolean isISALU() {
		return ISALU;
	}

	public void setISALU(boolean iSALU) {
		ISALU = iSALU;
	}

	public String getREM() {
		return REM;
	}

	public void setREM(String rEM) {
		REM = rEM;
	}

	public boolean isIS_Cmp() {
		return IS_Cmp;
	}

	public void setIS_Cmp(boolean iS_Cmp) {
		IS_Cmp = iS_Cmp;
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

	

	public boolean isJmp() {
		return isJmp;
	}

	public void setJmp(boolean isJmp) {
		this.isJmp = isJmp;
	}

	public boolean isMA_busy() {
		return MA_busy;
	}

	public void setMA_busy(boolean mA_busy) {
		MA_busy = mA_busy;
	}

	


}
