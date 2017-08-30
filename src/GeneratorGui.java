import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.Color;

public class GeneratorGui {

	private static JFrame frame;
	JFileChooser chooser;
	private JTextField reportCardHeader;
	private JButton generateButton;
	private JLabel lblEngradeCsv;
	private JTextField engradeFilePath;
	private JTextField reportCardDestPath;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeneratorGui window = new GeneratorGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GeneratorGui() {
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 555, 189);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		File f = new File("errors.txt");
		PrintStream errStream = null;
		File g = new File("log.txt");
		PrintStream logStream = null;
		try {
			errStream = new PrintStream(f);
			logStream = new PrintStream(g);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			showError(e1.getMessage());
		}
		
		System.setErr(errStream);
		System.setOut(logStream);
		
		JButton engradeUploadBtn = new JButton("Upload New");
		engradeUploadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser chooser = new JFileChooser();
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "*.CSV", "csv");
				    chooser.setFileFilter(filter);
				    int returnVal = chooser.showOpenDialog(null);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				       ExcelParser.setGradeSpreadsheetPath(chooser.getSelectedFile().getAbsolutePath());
				       engradeFilePath.setText(chooser.getSelectedFile().getAbsolutePath());
				    }
			}
		});
		
		JLabel lblNewLabel = new JLabel("Report Card Header Text");
		
		reportCardHeader = new JTextField(ExcelParser.getReportCardHeader());
		reportCardHeader.setColumns(10);
		
		generateButton = new JButton("Generate Grade Report");
		generateButton.setBackground(new Color(60, 179, 113));
		generateButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		generateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//custom title, no icon
			    ExcelParser.setReportCardHeader(reportCardHeader.getText());
			    try {
					ExcelParser.parse();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		lblEngradeCsv = new JLabel("Engrade CSV");
		
		engradeFilePath = new JTextField("No File Chosen");
		engradeFilePath.setEditable(false);
		engradeFilePath.setColumns(10);
		
		JLabel lblOutputFileLocation = new JLabel("Output File Location");
		reportCardDestPath = new JTextField(ExcelParser.getOutputDir());
		reportCardDestPath.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		reportCardDestPath.setEditable(false);
		reportCardDestPath.setColumns(10);
		
		JButton btnChangeDestination = new JButton("Change Destination");
		btnChangeDestination.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
					    "Please select a destination folder for your grade report",
					    "Select Destination",
					    JOptionPane.PLAIN_MESSAGE);
					JFileChooser chooser = new JFileChooser();
				    chooser.setCurrentDirectory(new java.io.File("."));
				    chooser.setDialogTitle("Choose Destination for Grade Report");
				    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				    chooser.setFileFilter(null);
				    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				      System.out.println("getSelectedFile() : " + chooser.getSelectedFile().getAbsolutePath());
				      ExcelParser.setOutputDir(chooser.getSelectedFile().getAbsolutePath());
				      reportCardDestPath.setText(ExcelParser.getOutputDir());
				    } else {
				      System.out.println("No Selection ");
				    }
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(173, Short.MAX_VALUE)
					.addComponent(generateButton)
					.addGap(120))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(reportCardHeader, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addComponent(lblOutputFileLocation)
									.addGap(18)
									.addComponent(reportCardDestPath, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblEngradeCsv, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(engradeFilePath)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(engradeUploadBtn, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnChangeDestination))))
					.addContainerGap(33, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(reportCardHeader, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(engradeUploadBtn)
						.addComponent(lblEngradeCsv)
						.addComponent(engradeFilePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOutputFileLocation)
						.addComponent(reportCardDestPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnChangeDestination))
					.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addComponent(generateButton)
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}

	public static void showComplete(String string) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(frame,
			    string,
			    "Done",
			    JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public static void showError(String string) {
		JOptionPane.showMessageDialog(frame,  string, "Error", JOptionPane.ERROR_MESSAGE);
		System.err.println(string);
	}
}
