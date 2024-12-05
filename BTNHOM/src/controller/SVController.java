package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.QLSV;

public class SVController  implements ActionListener{

	private QLSV qlsv;
	public SVController(QLSV qlsv) {
		 this.qlsv = qlsv;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
//		qlsv = new QLSV();
		String src = e.getActionCommand();
		if(src.equals("Thêm"))
			qlsv.ThemSinhVien();
		else if (src.equals("Xóa"))
			qlsv.XoaSinhVien();
		else 
			qlsv.SuaSinhVien();
	}
}
