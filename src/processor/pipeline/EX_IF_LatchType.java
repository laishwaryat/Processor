package processor.pipeline;

public class EX_IF_LatchType {
	
	boolean IF_enable;
	boolean isBranchTaken;
	String BranchPC;
	boolean IS_END;
	boolean Cmp_BrTaken;
	
	public EX_IF_LatchType() {
	IF_enable=false;
	isBranchTaken=false;
		IS_END=false;
		Cmp_BrTaken=false;
	}
	public boolean isIF_enable() {
		return IF_enable;
	}
	public void setIF_enable(boolean iF_enable) {
		IF_enable = iF_enable;
	}
	public boolean isBranchTaken() {
		return isBranchTaken;
	}
	public void setBranchTaken(boolean isBranchTaken) {
		this.isBranchTaken = isBranchTaken;
	}
	public String getBranchPC() {
		return BranchPC;
	}
	public void setBranchPC(String branchPC) {
		BranchPC = branchPC;
	}
	
	public boolean isIS_END() {
		return IS_END;
	}
	public void setIS_END(boolean iS_END) {
		IS_END = iS_END;
	}
	public boolean isCmp_BrTaken() {
		return Cmp_BrTaken;
	}
	public void setCmp_BrTaken(boolean cmp_BrTaken) {
		Cmp_BrTaken = cmp_BrTaken;
	}
	
	
}
