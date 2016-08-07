package capture;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import utilities.SessionUtils;
import api.BoxApi;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UnitFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtMaHocPhan;
	private JButton btnChoose;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnitFrame frame = new UnitFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UnitFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		initGui();
		buttonHandle();
	}
	
	private void initGui() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtMaHocPhan = new JTextField();
		txtMaHocPhan.setBounds(128, 68, 215, 20);
		contentPane.add(txtMaHocPhan);
		txtMaHocPhan.setColumns(10);

		btnChoose = new JButton("Ch\u1ECDn");
		btnChoose.setBounds(188, 120, 89, 23);
		contentPane.add(btnChoose);
	}

	private void buttonHandle() {
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtMaHocPhan.getText().equals("")) {
					System.out.println("Khong the bo trong truong ma hoc phan");
					return;
				} else {
					BoxApi api = BoxApi.getInstance();
					SessionUtils.unitFolderId = api.createFolder(api.getRootFolder(), txtMaHocPhan.getText()).getID();
					System.out.println("Open webcam");
					GrabberShow gs = new GrabberShow();
					Thread th = new Thread(gs);
					th.start();
				}
			}
		});
	}
}
