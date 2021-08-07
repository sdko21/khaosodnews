import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.time.*;
import java.util.Calendar;
import java.util.WeakHashMap;

public class KhaosodBreaking extends JFrame {

	private JTabbedPane newsTabs;
	private JPanel contentPane;
	private JLabel todayLabel;
	private JTable normalNewsTable;
	private JTable covidNewsTable;
	private JButton btnFetchNews;
	private JButton btnShowNews;

	private JProgressBar loadingNews;
	private DefaultTableModel normalNewsModel;
	private DefaultTableModel covidNewsModel;
	private String[] columnName;
	private Object[] newsList;
	private Object[] newsTime;
	private Object[] newsLink;
	private Khaosod khaosod;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					KhaosodBreaking frame = new KhaosodBreaking();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JButton getBtnFetchNews() {
		return btnFetchNews;
	}

	public JButton getBtnShowNews() {
		return btnShowNews;
	}

	public JProgressBar getLoadingNews() {
		return loadingNews;
	}

	public void setNewsList(Object[] newsList) {
		this.newsList = newsList;
	}

	public void setNewsLink(Object[] newsLink) {
		this.newsLink = newsLink;
	}

	public void setNewsTime(Object[] newsTime) {
		this.newsTime = newsTime;
	}

	public KhaosodBreaking() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 700);
		setTitle("สรุปหัวข้อข่าวจาก ข่าวสด");
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel mainPane = new JPanel();
		contentPane.add(mainPane, BorderLayout.CENTER);
		mainPane.setLayout(new BorderLayout(0, 0));

		newsTabs = new JTabbedPane();
		mainPane.add(newsTabs, BorderLayout.CENTER);

		JPanel northPane = new JPanel();
		mainPane.add(northPane, BorderLayout.NORTH);
		northPane.setLayout(new BorderLayout(0, 0));

		JPanel buttonAndTimePane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonAndTimePane.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		northPane.add(buttonAndTimePane, BorderLayout.NORTH);

		btnFetchNews = new JButton("ดึงข้อมูล");
		buttonAndTimePane.add(btnFetchNews);

		btnShowNews = new JButton("แสดง");
		btnShowNews.setEnabled(false);
		buttonAndTimePane.add(btnShowNews);

		todayLabel = new JLabel("ยังไม่ได้ดึงข้อมูล");
		buttonAndTimePane.add(todayLabel);

		JPanel normalNewsPane = new JPanel();
		normalNewsPane.setLayout(new BorderLayout(0, 0));
		newsTabs.addTab("ข่าวทั่วไป", normalNewsPane);

		JScrollPane normalNewsScrollPane = new JScrollPane();
		normalNewsPane.add(normalNewsScrollPane);

		columnName = new String[] { "ลำดับ", "หัวข้อข่าว", "เมื่อเวลา" };
		normalNewsModel = new DefaultTableModel(columnName, 0);
		normalNewsTable = new JTable(normalNewsModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		normalNewsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		normalNewsTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		normalNewsTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		normalNewsTable.getColumnModel().getColumn(1).setPreferredWidth(600);
		normalNewsTable.getColumnModel().getColumn(2)
				.setPreferredWidth(normalNewsTable.getColumnModel().getColumn(2).getPreferredWidth() + 50);
		((DefaultTableCellRenderer) normalNewsTable.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);
		normalNewsScrollPane.setViewportView(normalNewsTable);

		JPanel covidNewsPane = new JPanel();
		covidNewsPane.setLayout(new BorderLayout());
		newsTabs.addTab("ข่าวโควิด-19", covidNewsPane);

		JScrollPane covidNewsScrollPane = new JScrollPane();
		covidNewsPane.add(covidNewsScrollPane);

		covidNewsModel = new DefaultTableModel(columnName, 0);
		covidNewsTable = new JTable(covidNewsModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		covidNewsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		covidNewsTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		covidNewsTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		covidNewsTable.getColumnModel().getColumn(1).setPreferredWidth(600);
		covidNewsTable.getColumnModel().getColumn(2)
				.setPreferredWidth(covidNewsTable.getColumnModel().getColumn(2).getPreferredWidth() + 50);
		((DefaultTableCellRenderer) covidNewsTable.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);
		covidNewsScrollPane.setViewportView(covidNewsTable);

		JPanel southPane = new JPanel();
		mainPane.add(southPane, BorderLayout.SOUTH);
		southPane.setLayout(new BorderLayout(0, 0));

		loadingNews = new JProgressBar(JProgressBar.HORIZONTAL, 0, 26);
		loadingNews.setStringPainted(true);
		loadingNews.setVisible(false);
		southPane.add(loadingNews, BorderLayout.NORTH);

		btnFetchNews.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnFetchNews.setEnabled(false);
				clearData();
				fetchData();
			}
		});

		btnShowNews.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showData();
			}
		});

		normalNewsTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});

		normalNewsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTable tmpTable = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = tmpTable.rowAtPoint(point);
				int buttonClick = e.getButton();

				if (buttonClick == MouseEvent.BUTTON1) {
					if (e.getClickCount() == 2 && tmpTable.getSelectedRow() != -1) {
						int option = JOptionPane.showConfirmDialog(null, "ต้องการเปิดเว็บไซต์ เพื่อดูข่าว ?",
								"ต้องการเปิดเว็บไซต์", JOptionPane.YES_NO_OPTION);
						if (option == JOptionPane.YES_OPTION) {
							openWebsite(row);
						}
					}
				}
				// BUTTON1 is left click.
				// BUTTON3 is right click.
			}
		});
	}

	public void clearData() {
		int totalRow = normalNewsModel.getRowCount() - 1;

		while (totalRow != -1) {
			normalNewsModel.removeRow(totalRow--);
		}

		loadingNews.setValue(0);
	}

	public void openWebsite(int row) {
		OpenBrowser.open(newsLink[row].toString());
	}

	public void showData() {
		clearData();
		System.out.println("Fill data : " + newsList.length);
		for (int i = 0; i < newsList.length; i++) {
			Object[] data = { i + 1, newsList[i], newsTime[i] };
			normalNewsModel.addRow(data);
		}
	}

	public void fetchData() {
		btnShowNews.setEnabled(false);

		khaosod = new Khaosod();
		khaosod.setFrame(this);
		khaosod.start();

		loadingNews.setVisible(true);
		System.out.println("Loading again.");
//		newsList = khaosod.getNews();
//		newsTime = khaosod.getTime();
//		newsLink = khaosod.getLink();

		LocalDateTime now = LocalDateTime.now();
		todayLabel.setText("ข้อมูลอัพเดทเมื่อเวลา " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond()
				+ " วันที่ " + now.getDayOfMonth() + " " + now.getMonth() + " " + (now.getYear() + 543));
//		for (int i = 0; i < newsList.length; i++) {
//			Object[] data = { i + 1, newsList[i], newsTime[i] };
//			normalNewsModel.addRow(data);
//		}
//		LocalDateTime now = LocalDateTime.now();
//		todayLabel.setText("ข้อมูลอัพเดทเมื่อเวลา " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond()
//				+ " วันที่ " + now.getDayOfMonth() + " " + now.getMonth() + " " + (now.getYear() + 543));
//		khaosod = null;
//		System.out.println("Fetech complete : " + newsList.length);
//		loadingNews.setVisible(false);
	}
}