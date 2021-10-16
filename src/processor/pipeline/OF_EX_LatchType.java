package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	boolean IS_Load;
	boolean IS_Store;
	boolean IS_Imm;
	boolean IS_Jmp;
	boolean IS_End;
	boolean IS_ALU;
	boolean IS_compare;
	boolean isDiv;
	boolean MA1_busy;
	boolean EX_busy;
	boolean EX1_busy;
	String OPCode;
	int Mem;
	int OP1;
	int OP2;
	int RD;
	String BranchTarget;
	boolean stall;
	
	boolean jmpEnd;
	boolean brEnd;

	public int getMem() {
		return Mem;
	}

	public void setMem(int mem) {
		Mem = mem;
	}

	public int getOP1() {
		return OP1;
	}

	public void setOP1(int oP1) {
		OP1 = oP1;
	}

	public int getOP2() {
		return OP2;
	}

	public void setOP2(int oP2) {
		OP2 = oP2;
	}

	public void setRD(int rD) {
		RD = rD;
	}

	public String getBranchTarget() {
		return BranchTarget;
	}

	public void setBranchTarget(String branchTarget) {
		BranchTarget = branchTarget;
	}

	public OF_EX_LatchType()
	{
		EX_enable = false;
		IS_Load=false;
		IS_Store=false;
		IS_Imm=false;
		IS_Jmp=false;
		IS_End=false;
		IS_ALU=false;
		IS_compare=false;
		stall=false;
		MA1_busy=false;
		jmpEnd=false;
		brEnd = false;
		isDiv=false;
		EX_busy=false;
		EX1_busy=false;
	}

	
	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}


	public void setIS_Load(boolean iS_Load) {
		IS_Load = iS_Load;
	}


	public void setIS_Store(boolean iS_Store) {
		IS_Store = iS_Store;
	}


	public void setIS_Imm(boolean iS_Imm) {
		IS_Imm = iS_Imm;
	}



	public void setIS_Jmp(boolean iS_Jmp) {
		IS_Jmp = iS_Jmp;
	}


	
	public boolean isIS_Load() {
		return IS_Load;
	}


	public boolean isIS_Store() {
		return IS_Store;
	}


	public boolean isIS_Imm() {
		return IS_Imm;
	}

	public boolean isIS_Jmp() {
		return IS_Jmp;
	}


	

	public boolean isIS_End() {
		return IS_End;
	}

	public void setIS_End(boolean iS_End) {
		IS_End = iS_End;
	}

	public boolean isIS_ALU() {
		return IS_ALU;
	}

	public void setIS_ALU(boolean iS_ALU) {
		IS_ALU = iS_ALU;
	}

	

	public int getRD() {
		return RD;
	}

	public boolean isIS_compare() {
		return IS_compare;
	}

	public void setIS_compare(boolean iS_compare) {
		IS_compare = iS_compare;
	}

	public String getOPCode() {
		return OPCode;
	}

	public void setOPCode(String oPCode) {
		OPCode = oPCode;
	}

	

	public boolean isStall() {
		return stall;
	}

	public void setStall(boolean stall) {
		this.stall = stall;
	}

	

	public boolean isJmpEnd() {
		return jmpEnd;
	}

	public void setJmpEnd(boolean jmpEnd) {
		this.jmpEnd = jmpEnd;
	}

	public boolean isDiv() {
		return isDiv;
	}

	public void setDiv(boolean isDiv) {
		this.isDiv = isDiv;
	}

	public boolean isBrEnd() {
		return brEnd;
	}

	public void setBrEnd(boolean brEnd) {
		this.brEnd = brEnd;
	}

	public boolean isMA1_busy() {
		return MA1_busy;
	}

	public void setMA1_busy(boolean mA1_busy) {
		MA1_busy = mA1_busy;
	}

	public boolean isEX_busy() {
		return EX_busy;
	}

	public void setEX_busy(boolean eX_busy) {
		EX_busy = eX_busy;
	}

	public boolean isEX1_busy() {
		return EX1_busy;
	}

	public void setEX1_busy(boolean eX1_busy) {
		EX1_busy = eX1_busy;
	}


}
