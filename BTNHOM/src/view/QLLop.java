package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.LopController;
import model.DSLop;
import model.Lop;

public class QLLop extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnThem, btnXoa, btnSua;
	private JTable table;
	private DefaultTableModel model;
	private JLabel lbMalop, lbTenLop, lbSiso;
	private JTextField txtMalop, txtTenLop, txtSiso;

	private DSLop dsLop;

	public QLLop() {

		dsLop = new DSLop();
		model = new DefaultTableModel(new String[] { "Mã lớp", "Tên lớp", "Sỉ số" }, 0);
		table = new JTable(model);
		
		// Load du lieu tu sql len table
		loadData();
		
		this.setSize(800, 400);
		this.setTitle("Manager class");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		JPanel pnMain = new JPanel(new BorderLayout());
		JPanel pnInput = new JPanel();

		JPanel pnButton = new JPanel();
		
		btnThem = new JButton("Thêm");
		btnSua = new JButton("Sửa");
		btnXoa = new JButton("Xóa");

		lbMalop = new JLabel("Mã lớp: ");
		lbTenLop = new JLabel("Tên lớp: ");
		lbSiso = new JLabel("Sỉ số: ");

		txtMalop = new JTextField(10);
		txtTenLop = new JTextField(10);
		txtSiso = new JTextField(10);

		// Even khi lick chọn vào dòng trong table
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selected = table.getSelectedRow(); // Lấy dòng được chọn
				if (selected != -1) {
					// Set các giá trị trong dòng dược chọn của table vào lại text
					txtMalop.setText(model.getValueAt(selected, 0).toString());
					txtTenLop.setText(model.getValueAt(selected, 1).toString());
					txtSiso.setText(model.getValueAt(selected, 3).toString());
				}
			}
		});

		 //Even Listener
		ActionListener controller = new LopController(this);
		btnThem.addActionListener(controller);
		btnXoa.addActionListener(controller);
		btnSua.addActionListener(controller);

		pnInput.add(lbMalop);
		pnInput.add(txtMalop);
		pnInput.add(lbTenLop);
		pnInput.add(txtTenLop);
		pnInput.add(lbSiso);
		pnInput.add(txtSiso);
		pnButton.add(btnThem);
		pnButton.add(btnSua);
		pnButton.add(btnXoa);
		
		pnMain.add(pnInput, BorderLayout.WEST);
		pnMain.add(pnButton, BorderLayout.CENTER);
		pnMain.add(table, BorderLayout.SOUTH);
		
		pnMain.add(new JScrollPane(table));

		this.getContentPane().add(pnMain);
	}

	// Phuong thuc cho even
	public void Clear() {
		txtMalop.setText("");
		txtTenLop.setText("");
		txtSiso.setText("");
	}

	public void loadData() {
		dsLop.loadFormLop();
		model.setRowCount(0);
		for (Lop lop : dsLop.getList())
			model.addRow(new Object[] { lop.getMalop(), lop.getTenlop(), lop.getSiso() });
	}

	public void ThemLop() {
		String malop = txtMalop.getText();
		String tenlop = txtTenLop.getText();
		int siso = Integer.parseInt(txtSiso.getText());

		Lop lop = new Lop(malop, tenlop, siso);
		if (!dsLop.KTTrung(malop)) {
			model.addRow(new Object[] { malop, tenlop, siso });
			dsLop.Themlop(lop);
			// Them vao csdl
			dsLop.ThemData(lop);
			JOptionPane.showMessageDialog(null, "Thêm lớp thành công");
			Clear();
		} else
			JOptionPane.showMessageDialog(null, "Mã lớp đã tòn tại trong danh sách");
	}

	public void SuaLop() {
		int selected = table.getSelectedRow();
		if (selected != -1) {
			String malop = txtMalop.getText();
			String tenlop = txtTenLop.getText();
			int siso = Integer.parseInt(txtSiso.getText());
			model.setValueAt(model, selected, 0);
			model.setValueAt(tenlop, selected, 1);
			model.setValueAt(siso, selected, 2);

			Lop lop = new Lop(malop, tenlop, siso);
			dsLop.Sua(selected, lop);
			dsLop.SuaData(lop);
			JOptionPane.showMessageDialog(null, "Cập nhật thông tin lớp thành công");
			Clear();
		} else
			JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để sửa");
	}

	public void Xoa() {
		int selected = table.getSelectedRow();
		if (selected != -1) {
			String malop = model.getValueAt(selected, 0).toString();
			dsLop.XoaLop(selected);
			dsLop.XoaData(malop);
			JOptionPane.showMessageDialog(null, "Xóa lớp thành công");
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để xóa");
		}
	}

	public static void main(String[] args) {
		new QLLop().setVisible(true);
	}
}
