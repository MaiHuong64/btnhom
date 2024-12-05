package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.userDAO;

public class DSSinhVien {

	private ArrayList<SinhVien> list = new ArrayList<>();

	public ArrayList<SinhVien> getList() {
		return list;
	}

	public void setList(ArrayList<SinhVien> list) {
		this.list = list;
	}

	public DSSinhVien() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DSSinhVien(ArrayList<SinhVien> list) {
		super();
		this.list = list;
	}

	public void ThemSV(SinhVien sv) {
		if (!list.contains(sv)) {
			list.add(sv);
//            System.out.println("Thêm sinh viên thành công");
			System.out.println(sv.toString());
		} else {
			System.out.println("Sinh viên đã tồn tại trong danh sách");
		}
	}

	public boolean KiemTraSV(String masv) {
		for (SinhVien sv : list)
			if (sv.getMaSV().equals(masv))
				return true;
		return false;
	}

	public void XoaSV(int index) {
		this.list.remove(index);
	}

	public void SuaSV(int index, SinhVien sv) {
		this.list.set(index, sv);
	}

	public void loadFromDatabase() {
		Connection connection = userDAO.getConnection();
		if (connection != null) {
			String query = "select *from SV";
			try (PreparedStatement stmt = connection.prepareStatement(query);
					ResultSet rs = stmt.executeQuery()) {

				while (rs.next()) {
					String maSV = rs.getString("MASV");
					String tenSV = rs.getString("TENSV");
					Boolean gioiTinh = rs.getBoolean("GIOITINH");
					String maLop = rs.getString("MALOP");
					SinhVien sv = new SinhVien(maSV, tenSV, gioiTinh, maLop);
					list.add(sv);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void ThemVaoDataBase(SinhVien sv) {
		Connection connection = userDAO.getConnection();
		if (connection != null) {
			// Truy vấn
			String querySV = "INSERT INTO SV(MASV, TENSV, GIOITINH, MALOP) VALUES(?, ?, ?, ?)";
			try (PreparedStatement statement = connection.prepareStatement(querySV)) {
				statement.setString(1, sv.getMaSV());
				statement.setString(2, sv.getTenSV());
				statement.setBoolean(3, sv.getGioiTinh());
				statement.setString(4, sv.getMaLop());
				// Update lại database
				statement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Lỗi khi thêm sinh viên: " + e.getMessage());
				e.printStackTrace();
			}
			String queryLop = "update LOP set SISO = SISO + 1 where MALOP = ?";
			try (PreparedStatement statementLop = connection.prepareStatement(queryLop)) {
				statementLop.setString(1, sv.getMaLop());
				statementLop.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Không thể cập nhật số lượng sinh viên");
				e.printStackTrace();
			}
		}
	}

	public void SuaData(SinhVien sv) {
		Connection connection = userDAO.getConnection();
		if (connection != null) {
			String query = "UPDATE SV SET TENSV = ?, GIOITINH = ?, MALOP = ? WHERE MASV = ?";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, sv.getTenSV());
				statement.setBoolean(2, sv.getGioiTinh());
				statement.setString(3, sv.getMaLop());
				statement.setString(4, sv.getMaSV());
				statement.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void XoaData(String masv) {
		Connection connection = userDAO.getConnection();
		if (connection != null) {
			String query = "DELETE FROM SV WHERE MASV = ?";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, masv);
				int row = statement.executeUpdate();
				if (row > 0) {
					System.out.println("Xóa sinh viên thành công");
				} else {
					System.out.println("Không tìm thấy sinh viên với MASV: " + masv);
				}
			} catch (SQLException e) {
				System.out.println("Lỗi khi xóa sinh viên: " + e.getMessage());
				e.printStackTrace();
			}
			String queryLop = "update LOP set SISO = SISO - 1 where MALOP = ?";
			try (PreparedStatement statementLop = connection.prepareStatement(queryLop)) {
				statementLop.setString(1, masv);
				statementLop.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Không thể cập nhật số lượng sinh viên");
				e.printStackTrace();
			}
		}
	}
}