import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Table extends JFrame {
	
	Table(String stationState, ArrayList<String[]> table) {
		String[][] stationTable = new String[1][1];
		
		String[] label1 = {"指定駅に関する情報"};
		String[] label2 = {"路線名", "運行状態", "遅延時間", "遅延区間", "遅延原因"};
		stationTable[0][0] = stationState;
		String[][] routeTable = table.toArray(new String[table.size()][]);
		
		DefaultTableModel tm1 = new DefaultTableModel(stationTable, label1);
		DefaultTableModel tm2 = new DefaultTableModel(routeTable, label2);
		JTable tb1 = new JTable(tm1);
		JTable tb2 = new JTable(tm2);
		tb1.setRowHeight(20);
		tb2.setRowHeight(20);
		for(int i = 0; i < 5; i++){
			TableColumn col = tb2.getColumnModel().getColumn(i);
			switch (i) {
			case 0:
				col.setMinWidth(100);
				col.setMaxWidth(150);
				break;
			case 1:
				col.setMinWidth(150);
				col.setMaxWidth(200);
				break;
			case 2:
				col.setMinWidth(100);
				col.setMaxWidth(100);
				break;
			case 3:
				col.setMinWidth(200);
				col.setMaxWidth(200);
				break;
			case 4:
				col.setMinWidth(100);
				col.setMaxWidth(150);
				break;
			default:
				break;
			}
		}
		
		JScrollPane sp1 = new JScrollPane(tb1);
		JScrollPane sp2 = new JScrollPane(tb2);
		sp1.setPreferredSize(new Dimension(800, 40));
		sp2.setPreferredSize(new Dimension(800, (table.size() + 1) * 20));
		getContentPane().add(sp1);
		getContentPane().add(sp2);
		getContentPane().setLayout(new FlowLayout());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("遅延情報");
		setSize(820, (table.size() + 1) * 20 + 80);
		setVisible(true);
	}
}