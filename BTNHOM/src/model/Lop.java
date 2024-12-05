package model;

public class Lop {

	private String malop, tenlop;
	private int siso;
	public String getMalop() {
		return malop;
	}
	public void setMalop(String malop) {
		this.malop = malop;
	}
	public String getTenlop() {
		return tenlop;
	}
	public void setTenlop(String tenlop) {
		this.tenlop = tenlop;
	}
	public int getSiso() {
		return siso;
	}
	public void setSiso(int siso) {
		this.siso = siso;
	}
	public Lop() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Lop(String malop, String tenlop, int siso) {
		super();
		this.malop = malop;
		this.tenlop = tenlop;
		this.siso = siso;
	}
	
	
}
