package model;

public class SinhVien {

	private String maSV, tenSV;
	private Boolean gioiTinh;
	private String maLop;
	public SinhVien() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SinhVien(String maSV, String tenSV, Boolean gioiTinh, String maLop) {
		super();
		this.maSV = maSV;
		this.tenSV = tenSV;
		this.gioiTinh = gioiTinh;
		this.maLop = maLop;
	}
	public String getMaSV() {
		return maSV;
	}
	public void setMaSV(String maSV) {
		this.maSV = maSV;
	}
	public String getTenSV() {
		return tenSV;
	}
	public void setTenSV(String tenSV) {
		this.tenSV = tenSV;
	}
	public Boolean getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(Boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getMaLop() {
		return maLop;
	}
	public void setMaLop(String maLop) {
		this.maLop = maLop;
	}
	@Override
	public String toString() {
		return "SinhVien [maSV=" + maSV + ", tenSV=" + tenSV + ", gioiTinh=" + gioiTinh + ", maLop=" + maLop + "]";
	}
	
	
}
