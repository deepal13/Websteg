package edu.illuminaty.websteganography.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;
import edu.illuminaty.websteganography.extractor.Extractor;
import edu.illuminaty.websteganography.extractor.PDFExtractor;

public class ExtractMessageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9029524078375878557L;
	private JPanel contentPane;
	private JTextField pathMessageField;
	private JTextField pathKeyField;
	private JButton btnLoadMessage;
	private JButton btnLoadKey;
	private JPanel panel;
	private JButton btnExtract;
	private JButton btnCancel;
	private File messageFile;
	private File keyFile;
	private JTextArea extractedMessageArea;
	private JButton btnSave;
	private JLabel lblExtractedMessage;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public ExtractMessageFrame() {
		super("Extractor");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][]", "[][][grow][]"));
		
		JLabel lblMessage = new JLabel("Message:");
		contentPane.add(lblMessage, "cell 0 0,alignx left");
		
		pathMessageField = new JTextField();
		contentPane.add(pathMessageField, "cell 1 0,growx");
		pathMessageField.setColumns(10);
		
		btnLoadMessage = new JButton("Load Message");
		btnLoadMessage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("PDF-Files", "pdf"));
				if(fc.showOpenDialog(ExtractMessageFrame.this)==JFileChooser.APPROVE_OPTION){
					messageFile = fc.getSelectedFile();
					pathMessageField.setText(messageFile.getAbsolutePath());
				}
				
			}
		});
		contentPane.add(btnLoadMessage, "cell 2 0");
		
		JLabel lblKey = new JLabel("Key:");
		contentPane.add(lblKey, "cell 0 1,alignx left");
		
		pathKeyField = new JTextField();
		contentPane.add(pathKeyField, "cell 1 1,growx");
		pathKeyField.setColumns(10);
		
		btnLoadKey = new JButton("Load Key");
		btnLoadKey.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				if(fc.showOpenDialog(ExtractMessageFrame.this)==JFileChooser.APPROVE_OPTION){
					keyFile = fc.getSelectedFile();
					pathKeyField.setText(keyFile.getAbsolutePath());
				}
				
			}
		});
		contentPane.add(btnLoadKey, "cell 2 1,growx");
		
		lblExtractedMessage = new JLabel("Extracted Message:");
		contentPane.add(lblExtractedMessage, "cell 0 2,aligny top");
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "flowy,cell 1 2,grow");
		
		extractedMessageArea = new JTextArea();
		scrollPane.setViewportView(extractedMessageArea);
		extractedMessageArea.setWrapStyleWord(true);
		extractedMessageArea.setLineWrap(true);
		extractedMessageArea.setEditable(false);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				if(fc.showSaveDialog(ExtractMessageFrame.this)==JFileChooser.APPROVE_OPTION){
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(fc.getSelectedFile()));
						bw.write(extractedMessageArea.getText());
						bw.flush();
						bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		contentPane.add(btnSave, "cell 2 2,growx,aligny top");
		
		panel = new JPanel();
		contentPane.add(panel, "cell 1 3,grow");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnExtract = new JButton("Extract");
		btnExtract.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(keyFile==null || pathKeyField.getText()!=keyFile.getAbsolutePath())
						keyFile = new File(pathKeyField.getText());
					if(messageFile==null || pathMessageField.getText()!=messageFile.getAbsolutePath())
						messageFile = new File(pathMessageField.getText());
					BufferedReader br = new BufferedReader(new FileReader(keyFile));
					Extractor extr = new PDFExtractor(br.readLine());
					br.close();
					extractedMessageArea.setText(extr.extract(messageFile));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(ExtractMessageFrame.this, "An error occured: "+e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(btnExtract);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ExtractMessageFrame.this.dispose();
			}
		});
		panel.add(btnCancel);
	}

}
