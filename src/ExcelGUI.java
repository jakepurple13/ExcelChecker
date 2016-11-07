import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JLabel;


public class ExcelGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExcelGUI frame = new ExcelGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ExcelGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblNewLabel = new JLabel("New label");
		sl_contentPane.putConstraint(SpringLayout.EAST, lblNewLabel, -359, SpringLayout.EAST, contentPane);
		contentPane.add(lblNewLabel);
		
		JButton btnRealTimeFile = new JButton("Real Time File Chooser");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 6, SpringLayout.SOUTH, btnRealTimeFile);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 10, SpringLayout.WEST, btnRealTimeFile);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnRealTimeFile, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnRealTimeFile, 10, SpringLayout.WEST, contentPane);
		contentPane.add(btnRealTimeFile);
		
		JButton btnOutputFile = new JButton("Output File");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnOutputFile, 0, SpringLayout.WEST, lblNewLabel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnOutputFile, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnOutputFile);
		
		JButton btnBegin = new JButton("Begin");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnBegin, 0, SpringLayout.SOUTH, btnOutputFile);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnBegin, -10, SpringLayout.EAST, contentPane);
		contentPane.add(btnBegin);
		
		
		btnRealTimeFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Excel Images", "xlsx");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(contentPane);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       System.out.println("You chose to open this file: " +
			            chooser.getSelectedFile().getName());
			    }
			}
		});
		
		
		
		
	}
}
