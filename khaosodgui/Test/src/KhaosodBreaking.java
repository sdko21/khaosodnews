import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.time.*;
import java.util.Calendar;

public class KhaosodBreaking extends JFrame {

	private JPanel contentPane;
	private JTable newsTable;
	private DefaultTableModel newsModel;
	private String[] columnName;
	private Object[] newsLink;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KhaosodBreaking frame = new KhaosodBreaking();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public KhaosodBreaking() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 700);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel mainPane = new JPanel();
		contentPane.add(mainPane, BorderLayout.CENTER);
		mainPane.setLayout(new BorderLayout(0, 0));

		JPanel northPane = new JPanel();
		mainPane.add(northPane, BorderLayout.NORTH);
		northPane.setLayout(new BorderLayout(0, 0));

		JPanel buttonAndTimePane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonAndTimePane.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		northPane.add(buttonAndTimePane, BorderLayout.NORTH);

		JButton btnFetchNews = new JButton("ดึงข้อมูล");
		buttonAndTimePane.add(btnFetchNews);

		JButton btnClear = new JButton("ล้าง");
		buttonAndTimePane.add(btnClear);

		JLabel todayLabel = new JLabel("ยังไม่ได้ดึงข้อมูล");
		buttonAndTimePane.add(todayLabel);

		JPanel centerPane = new JPanel();
		mainPane.add(centerPane, BorderLayout.CENTER);
		centerPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		centerPane.add(scrollPane);

		columnName = new String[] { "ลำดับ", "หัวข้อข่าว" };
		newsModel = new DefaultTableModel(columnName, 0);
		newsTable = new JTable(newsModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		newsTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		newsTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		newsTable.getColumnModel().getColumn(1).setPreferredWidth(660);
		((DefaultTableCellRenderer) newsTable.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);
		scrollPane.setViewportView(newsTable);

		JPanel southPane = new JPanel();
		mainPane.add(southPane, BorderLayout.SOUTH);
		southPane.setLayout(new BorderLayout(0, 0));

		JProgressBar progressBar = new JProgressBar();
		southPane.add(progressBar, BorderLayout.NORTH);

		btnFetchNews.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearData();
				fetchData();
			}

			public void fetchData() {
				Khaosod news = new Khaosod();
				Object[] newsList = news.getNews();
				newsLink = news.getLink();

				for (int i = 0; i < newsList.length; i++) {
					Object[] data = { i + 1, newsList[i] };
					newsModel.addRow(data);
				}
				LocalDateTime now = LocalDateTime.now();
				todayLabel.setText("ข้อมูลอัพเดทเมื่อเวลา " + now.getHour() + ":" + now.getMinute() + ":"
						+ now.getSecond() + " วันที่ " + now.getDayOfMonth() + " " + now.getMonth() + " "
						+ (now.getYear() + 543));

			}
		});

		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearData();
			}
		});

		newsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTable tmpTable = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = tmpTable.rowAtPoint(point);
				int buttonClick = e.getButton();

				if (buttonClick == MouseEvent.BUTTON1) {
					if (e.getClickCount() == 2 && tmpTable.getSelectedRow() != -1) {
						int option = JOptionPane.showConfirmDialog(null, "ต้องการเปิดเว็บไซต์ เพื่อดูข่าว ?", "ต้องการเปิดเว็บไซต์", JOptionPane.YES_NO_OPTION);
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
		int totalRow = newsModel.getRowCount() - 1;
		while (totalRow != -1) {
			newsModel.removeRow(totalRow--);
		}
	}

	public void openWebsite(int row) {
		try {
			Runtime run = Runtime.getRuntime();
			run.exec("open " + newsLink[row]);
		} catch (Exception ex) {

		}
	}
}