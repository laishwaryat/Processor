package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	boolean IS_Cmp;
	String ALUResult;
	boolean IS_Load;
	boolean IS_Store;
	int storeReg;
	int DestReg;
	String Rem="00";
	boolean is_end;
	boolean stall;
	boolean isJmp;
	boolean isAlu;
	boolean isDiv;
	boolean MA_busy;
	boolean MA1_busy;
	public String getRem() {
		return Rem;
	}

	public void setRem(String rem) {
		Rem = rem;
	}

	public EX_MA_LatchType()
	{
		MA_enable = false;
		IS_Load=false;
		IS_Store=false;
		IS_Cmp=false;
		stall=false;
		is_end=false;
		isJmp=false;
		isAlu=false;
		isDiv=false;
		MA_busy=false;
		MA1_busy=false;
	}

	public boolean isDiv() {
		return isDiv;
	}

	public void setDiv(boolean isDiv) {
		this.isDiv = isDiv;
	}

	
	
	public boolean isMA_enable() {
		return MA_enable;
	}

	public boolean isIS_Cmp() {
		return IS_Cmp;
	}

	public void setIS_Cmp(boolean iS_Cmp) {
		IS_Cmp = iS_Cmp;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

	public String getALUResult() {
		return ALUResult;
	}

	public void setALUResult(String aLUResult) {
		ALUResult = aLUResult;
	}

	public boolean isIS_Load() {
		return IS_Load;
	}

	public void setIS_Load(boolean iS_Load) {
		IS_Load = iS_Load;
	}

	public boolean isIS_Store() {
		return IS_Store;
	}

	public void setIS_Store(boolean iS_Store) {
		IS_Store = iS_Store;
	}

	

	public int getStoreReg() {
		return storeReg;
	}

	public void setStoreReg(int storeReg) {
		this.storeReg = storeReg;
	}

	public int getDestReg() {
		return DestReg;
	}

	public void setDestReg(int destReg) {
		DestReg = destReg;
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

	public boolean isAlu() {
		return isAlu;
	}

	public void setAlu(boolean isAlu) {
		this.isAlu = isAlu;
	}

	public boolean isMA_busy() {
		return MA_busy;
	}

	public void setMA_busy(boolean mA_busy) {
		MA_busy = mA_busy;
	}

	public boolean isMA1_busy() {
		return MA1_busy;
	}

	public void setMA1_busy(boolean mA1_busy) {
		MA1_busy = mA1_busy;
	}

	

}
