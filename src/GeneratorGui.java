import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Panel;
import java.awt.Label;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

public class GeneratorGui {

	private JFrame frame;
	JFileChooser chooser;
	private JTextField reportCardHeader;
	private JButton generateButton;
	private JLabel lblEngradeCsv;
	private JTextField engradeFilePath;
	private JPanel advancedSettingsPanel;
	private JLabel lblAdvancedSettings;
	private JLabel lblCoursehoursDocument;
	private JTextField courseHoursPathLabel;
	private JButton courseHoursPathUpload;
	private JLabel lblGpasForA;
	private JTextField typeAgpaPathLabel;
	private JButton typeAgpaPathUpload;
	private JTextField nonAgpaPathLabel;
	private JLabel lblGpasForAll;
	private JButton nonAgpaPathUpload;
	private JButton btnAdvancedSettings;
	
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
		frame.setBounds(100, 100, 555, 331);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
			    } else {
			      System.out.println("No Selection ");
			    }
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
		
		advancedSettingsPanel = new JPanel();
		advancedSettingsPanel.setVisible(false);
		
		btnAdvancedSettings = new JButton("Advanced Settings");
		btnAdvancedSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				advancedSettingsPanel.setVisible(!advancedSettingsPanel.isVisible());
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(reportCardHeader, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblEngradeCsv, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(engradeFilePath)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(engradeUploadBtn, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(33, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(171, Short.MAX_VALUE)
					.addComponent(generateButton)
					.addGap(122))
				.addComponent(advancedSettingsPanel, GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(389, Short.MAX_VALUE)
					.addComponent(btnAdvancedSettings)
					.addContainerGap())
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
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(generateButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAdvancedSettings)
					.addGap(12)
					.addComponent(advancedSettingsPanel, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
		);
		advancedSettingsPanel.setLayout(null);
		
		lblAdvancedSettings = new JLabel("Advanced Settings");
		lblAdvancedSettings.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblAdvancedSettings.setBounds(209, 6, 155, 16);
		advancedSettingsPanel.add(lblAdvancedSettings);
		
		lblCoursehoursDocument = new JLabel("Course:Hours Document");
		lblCoursehoursDocument.setBounds(6, 33, 155, 16);
		advancedSettingsPanel.add(lblCoursehoursDocument);
		
		courseHoursPathLabel = new JTextField(ExcelParser.getCourseHoursPath());
		courseHoursPathLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		courseHoursPathLabel.setEditable(false);
		courseHoursPathLabel.setBounds(6, 53, 155, 26);
		advancedSettingsPanel.add(courseHoursPathLabel);
		courseHoursPathLabel.setColumns(10);
		
		courseHoursPathUpload = new JButton("Upload New");
		courseHoursPathUpload.setBounds(44, 85, 117, 29);
		advancedSettingsPanel.add(courseHoursPathUpload);
		courseHoursPathUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser chooser = new JFileChooser();
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "*.CSV", "csv");
				    chooser.setFileFilter(filter);
				    int returnVal = chooser.showOpenDialog(null);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				       ExcelParser.setCourseHourPath(chooser.getSelectedFile().getAbsolutePath());
				       courseHoursPathLabel.setText(chooser.getSelectedFile().getAbsolutePath());
				    }
			}
		});
		
		lblGpasForA = new JLabel("GPAs for A Courses");
		lblGpasForA.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGpasForA.setBounds(196, 34, 155, 16);
		advancedSettingsPanel.add(lblGpasForA);
		
		typeAgpaPathLabel = new JTextField();
		typeAgpaPathLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		typeAgpaPathLabel.setEditable(false);
		typeAgpaPathLabel.setColumns(10);
		typeAgpaPathLabel.setBounds(196, 53, 155, 26);
		typeAgpaPathLabel.setText(ExcelParser.getTypeAGradeGPAPath());
		advancedSettingsPanel.add(typeAgpaPathLabel);
		
		typeAgpaPathUpload = new JButton("Upload New");
		typeAgpaPathUpload.setBounds(231, 85, 117, 29);
		typeAgpaPathUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser chooser = new JFileChooser();
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "*.CSV", "csv");
				    chooser.setFileFilter(filter);
				    int returnVal = chooser.showOpenDialog(null);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				       ExcelParser.setTypeAGradeGPAPath(chooser.getSelectedFile().getAbsolutePath());
				       typeAgpaPathLabel.setText(chooser.getSelectedFile().getAbsolutePath());
				    }
			}
		});
		advancedSettingsPanel.add(typeAgpaPathUpload);
		
		nonAgpaPathLabel = new JTextField();
		nonAgpaPathLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		nonAgpaPathLabel.setEditable(false);
		nonAgpaPathLabel.setColumns(10);
		nonAgpaPathLabel.setBounds(373, 53, 168, 26);
		nonAgpaPathLabel.setText(ExcelParser.getGradeGPAPath());
		advancedSettingsPanel.add(nonAgpaPathLabel);
		
		lblGpasForAll = new JLabel("GPAs for All Other Courses");
		lblGpasForAll.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGpasForAll.setBounds(363, 33, 178, 16);

		advancedSettingsPanel.add(lblGpasForAll);
		
		nonAgpaPathUpload = new JButton("Upload New");
		nonAgpaPathUpload.setBounds(432, 85, 117, 29);
		nonAgpaPathUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser chooser = new JFileChooser();
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "*.CSV", "csv");
				    chooser.setFileFilter(filter);
				    int returnVal = chooser.showOpenDialog(null);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				       ExcelParser.setGradeGPAPath(chooser.getSelectedFile().getAbsolutePath());
				       nonAgpaPathLabel.setText(chooser.getSelectedFile().getAbsolutePath());
				    }
			}
		});
		advancedSettingsPanel.add(nonAgpaPathUpload);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(174, 34, 12, 98);
		advancedSettingsPanel.add(verticalStrut);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setBounds(352, 34, 12, 98);
		advancedSettingsPanel.add(verticalStrut_1);
		frame.getContentPane().setLayout(groupLayout);
	}
}
