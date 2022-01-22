package gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Taskbar.State;

import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import worker.KhaosodService;
import worker.News;
import worker.OpenBrowser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.naming.OperationNotSupportedException;
import javax.print.DocFlavor.URL;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class Display extends JPanel {
	private JTable newsTable;
	private DefaultTableModel newsTableModel;
	private JComboBox<String> newsListPicker; 

	/**
	 * Create the panel.
	 */
	public Display() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel nPanel = new JPanel();
		FlowLayout fl_nPanel = (FlowLayout) nPanel.getLayout();
		fl_nPanel.setAlignment(FlowLayout.LEFT);
		add(nPanel, BorderLayout.NORTH);
		
		JButton btnLoadNews = new JButton("Load");
		btnLoadNews.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearData();
				int selection = newsListPicker.getSelectedIndex() + 1;
				showData(selection);
			}
		});
		nPanel.add(btnLoadNews);
		
		newsListPicker = new JComboBox<>();
		newsListPicker.setModel(new DefaultComboBoxModel<>(new String[] {"ข่าวด่วน", "ข่าวโควิด-19", "ข่าวเด่นออนไลน์", "บันเทิง", "กีฬา", "ดวง"}));
		nPanel.add(newsListPicker);
		
		JPanel sPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) sPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(sPanel, BorderLayout.SOUTH);
		
		JProgressBar pgBarUpdate = new JProgressBar();
		sPanel.add(pgBarUpdate);
		
		JLabel lbLastUpdateTime = new JLabel("Showing last update time");
		sPanel.add(lbLastUpdateTime);
		
		JPanel cPanel = new JPanel();
		add(cPanel, BorderLayout.CENTER);
		cPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane newsPane = new JScrollPane();
		cPanel.add(newsPane, BorderLayout.CENTER);
		
		newsTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ลำดับ", "หัวข้อข่าว", "เวลา", "link"
			}
			);
		newsTable = new JTable(newsTableModel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean editCellAt(int row, int column) {
				return false;
			}
			
			@Override
			public boolean editCellAt(int row, int column, EventObject e) {
				return false;
			}
		};
		newsTable.removeColumn(newsTable.getColumnModel().getColumn(3));
		newsPane.setViewportView(newsTable);
		newsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();

				if (clickCount == 2) {
					int option = JOptionPane.showConfirmDialog(newsPane, new JLabel("ต้องการเปิดดรายละเอียดูข่าวบนเว็บไซต์ ?"), "เปิดเว็บไซต์", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						try {
							int rowSelected = newsTable.getSelectedRow();
							String link = (String)newsTableModel.getValueAt(rowSelected, 3);
							OpenBrowser.open(link);
						} catch (OperationNotSupportedException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		newsTable.setFont(new Font("Arial", Font.PLAIN, 14));
		newsTable.setRowHeight(newsTable.getRowHeight() + 10);
	}
	
	private void showData(int selection) {
		List<News> list = KhaosodService.getNews(KhaosodService.getWebsiteNewsLink(selection));
		int order = 1;
		for (News news : list) {
			Object content = news.getNewsContent();
			Object time = news.getNewsTime();
			Object link = news.getNewsLink();
			newsTableModel.addRow(new Object[] {order, content, time, link});
			order++;
		}
	}
	
	private void clearData() {
		newsTableModel.setRowCount(0);
	}
	
	public static void main(String[] args) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		    java.net.URL iconURL = Display.class.getResource("./../icon/icon.png");
			JFrame f = new JFrame("JKhaosod");
			f.setSize(800, 600);
			f.setIconImage(new ImageIcon(iconURL).getImage());
			f.getContentPane().add(new Display());
			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
