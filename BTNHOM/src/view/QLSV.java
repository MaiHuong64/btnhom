package view;

import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.SVController;
import model.DSSinhVien;
import model.SinhVien;

public class QLSV extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnThem, btnXoa, btnSua;
	private JTable table;
	private DefaultTableModel model;
	private JLabel lbMasv, lbTensv, lbMalop, lbGioitinh;
	private JTextField txtMaSV, txtTenSV, txtMalop;
	private JRadioButton radNam, radNu;

	private DSSinhVien dssv;

	public QLSV() {

		dssv = new DSSinhVien();
		model = new DefaultTableModel(new String[] { "MaSV", "TenSV", "GioiTinh", "Malop" }, 0);
		table = new JTable(model);
		loadData();
		this.setSize(600, 400);
		this.setTitle("Manager Students");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		JPanel pnMain = new JPanel();

		btnThem = new JButton("Thêm");
		btnSua = new JButton("Sửa");
		btnXoa = new JButton("Xóa");

		lbMasv = new JLabel("Mã sinh viên: ");
		lbTensv = new JLabel("Tên sinh viên: ");
		lbMalop = new JLabel("Mã lớp: ");
		lbGioitinh = new JLabel("Giới tính: ");

		txtMalop = new JTextField(10);
		txtMaSV = new JTextField(10);
		txtTenSV = new JTextField(10);

		radNam = new JRadioButton("Nam");
		radNu = new JRadioButton("Nữ");

		// Gr rad lại cho dễ chinh vị trí
		ButtonGroup group = new ButtonGroup();
		group.add(radNam);
		group.add(radNu);

		// Thêm even cho các buttons

		// Even khi lick chọn vào dòng trong table
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selected = table.getSelectedRow(); // Lấy dòng được chọn
				if (selected != -1) {
					// Set các giá trị trong dòng dược chọn của table vào lại text
					txtMaSV.setText(model.getValueAt(selected, 0).toString());
					txtTenSV.setText(model.getValueAt(selected, 1).toString());
					String gt = model.getValueAt(selected, 2).toString();
					if (gt.equals("Nam"))
						radNam.setSelected(true);
					else
						radNu.setSelected(true);
					txtMalop.setText(model.getValueAt(selected, 3).toString());
				}
			}
		});

		// Even Listener
		ActionListener controller = new SVController(this);
		btnThem.addActionListener(controller);
		btnXoa.addActionListener(controller);
		btnSua.addActionListener(controller);

		pnMain.add(lbMasv);
		pnMain.add(txtMaSV);
		pnMain.add(lbTensv);
		pnMain.add(txtTenSV);
		pnMain.add(lbMalop);
		pnMain.add(txtMalop);
		pnMain.add(lbGioitinh);
		pnMain.add(radNam);
		pnMain.add(radNu);
		pnMain.add(btnThem);
		pnMain.add(btnSua);
		pnMain.add(btnXoa);
		pnMain.add(new JScrollPane(table));

		this.getContentPane().add(pnMain);
	}

	public void loadData() {
		dssv.loadFromDatabase();
		model.setRowCount(0);
		for (SinhVien sv : dssv.getList())
			model.addRow(new Object[] { sv.getMaSV(), sv.getTenSV(), sv.getGioiTinh() ? "Nam" : "Nữ", sv.getMaLop() });
	}

	public void Clear() {
		txtMaSV.setText("");
		txtTenSV.setText("");
		txtMalop.setText("");
		radNam.setSelected(false);
		radNu.setSelected(false);
	}

	public void ThemSinhVien() {
		String masv = txtMaSV.getText();
		String tensv = txtTenSV.getText();
		String malop = txtMalop.getText();
		boolean gioitinh = radNam.isSelected();

		if (masv.isEmpty() || tensv.isEmpty() || malop.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin", null, JOptionPane.WARNING_MESSAGE);
		} else {
			SinhVien sv = new SinhVien(masv, tensv, gioitinh, malop);

			if (!dssv.KiemTraSV(masv)) {
				model.addRow(new Object[] { masv, tensv, gioitinh ? "Nam" : "Nu", malop });
				dssv.ThemSV(sv);
				dssv.ThemVaoDataBase(sv);
				Clear();
			} else {
				JOptionPane.showMessageDialog(null, "Sinh viên đã tồn tại trong danh sách");
				Clear();
			}
		}
	}

	public void SuaSinhVien() {
		int selected = table.getSelectedRow();
		if (selected != -1) {
			String masv = txtMaSV.getText();
			String tensv = txtTenSV.getText();
			String malop = txtMalop.getText();
			Boolean gioitinh = radNam.isSelected();

			model.setValueAt(masv, selected, 0);
			model.setValueAt(tensv, selected, 1);
			model.setValueAt(gioitinh ? "Nam" : "Nữ", selected, 2);
			model.setValueAt(malop, selected, 3);

			SinhVien sv = new SinhVien(masv, tensv, gioitinh, malop);
			dssv.SuaSV(selected, sv);
			dssv.SuaData(sv);
			JOptionPane.showMessageDialog(null, "Cập nhật sinh viên thành công");
			Clear();
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để sửa");
		}
	}

	public void XoaSinhVien() {
		int selected = table.getSelectedRow();
		if (selected != -1) {
			String masv = (String) model.getValueAt(selected, 0);
			model.removeRow(selected);
			dssv.XoaSV(selected);
			dssv.XoaData(masv);
			JOptionPane.showMessageDialog(null, "Xóa sinh viên thành công");
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để xóa");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new QLSV().setVisible(true));
	}
}
