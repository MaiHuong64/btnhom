package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.userDAO;

public class DSLop {

	private ArrayList<Lop> list = new ArrayList<>();

	public ArrayList<Lop> getList() {
		return list;
	}

	public void setList(ArrayList<Lop> list) {
		this.list = list;
	}

	public void Themlop(Lop lop) {
		this.list.add(lop);
	}

	public void XoaLop(int index) {
		this.list.remove(index);
	}

	public void Sua(int index, Lop lop) {
		this.list.set(index, lop);
	}

	public Boolean KTTrung(String malop) {
		for (Lop lop : list)
			if (lop.getMalop().equals(malop))
				return true;
		return false;
	}

	public void loadFormLop() {
		Connection connection = userDAO.getConnection();
		if (connection != null) {
			String query = "Select *from LOP";
			try (PreparedStatement statement = connection.prepareStatement(query);
					ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					String malop = rs.getString("MALOP");
					String tenlop = rs.getString("TENLOP");
					int siso = rs.getInt("SISO");
					Lop lop = new Lop(malop, tenlop, siso);
					list.add(lop);
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
	}

	public void ThemData(Lop lop) {
		Connection connection = userDAO.getConnection();
		if (connection != null) {
			String query = "insert into LOP(MALOP, TENLOP, SISO) values (?,?,?)";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, lop.getMalop());
				statement.setString(2, lop.getTenlop());
				statement.setInt(3, lop.getSiso());
				statement.executeUpdate();
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void XoaData(String malop) {
		Connection connection = userDAO.getConnection();
		if (connection != null) {
			String query = "delete from LOP where MALOP = ?";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, malop);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void SuaData(Lop lop) {
		Connection connection = userDAO.getConnection();
		if (connection != null) {
			String query = "update LOP set TENLOP = ?, SISO = ? where MALOP = ?";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, lop.getTenlop());
				statement.setInt(2, lop.getSiso());
				statement.setString(3, lop.getMalop());
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
}
