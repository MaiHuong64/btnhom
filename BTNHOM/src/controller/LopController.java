package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.QLLop;

public class LopController implements ActionListener{

	private QLLop lop;
	
	public LopController(QLLop lop) {
		super();
		this.lop = lop;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String src = e.getActionCommand();
		if(src.equals("Thêm"))
			lop.ThemLop();
		else if (src.equals("Xóa"))
			lop.Xoa();
		else
			lop.SuaLop();
	}

}
