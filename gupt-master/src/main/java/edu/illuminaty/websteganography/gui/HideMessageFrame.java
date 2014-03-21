package edu.illuminaty.websteganography.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
import edu.illuminaty.websteganography.hider.Hider;
import edu.illuminaty.websteganography.hider.PDFHider;

public class HideMessageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4496670412702737672L;
	private JPanel contentPane;
	private JTextField pathKeyField;
	private File keyFile;
	private File covertextFile;
	private JTextField pathCovertextField;

	/**
	 * Create the frame.
	 */
	public HideMessageFrame() {
		super("Hider");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][]", "[][][grow][]"));
		
		JLabel lblKey = new JLabel("Key:");
		contentPane.add(lblKey, "cell 0 0,alignx left");
		
		pathKeyField = new JTextField();
		contentPane.add(pathKeyField, "cell 1 0,growx");
		pathKeyField.setColumns(10);
		
		JButton btnLoadKey = new JButton("Load Key");
		btnLoadKey.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				if(fc.showOpenDialog(HideMessageFrame.this)==JFileChooser.APPROVE_OPTION){
					keyFile = fc.getSelectedFile();
					pathKeyField.setText(keyFile.getAbsolutePath());
				}
				
			}
		});
		contentPane.add(btnLoadKey, "cell 2 0,growx");
		
		JLabel lblCovertext = new JLabel("Covertext:");
		contentPane.add(lblCovertext, "cell 0 1,alignx trailing");
		
		pathCovertextField = new JTextField();
		contentPane.add(pathCovertextField, "cell 1 1,growx");
		pathCovertextField.setColumns(10);
		
		JButton btnLoadCovertext = new JButton("Load Covertext");
		btnLoadCovertext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("PDF-Files", "pdf"));
				if(fc.showOpenDialog(HideMessageFrame.this)==JFileChooser.APPROVE_OPTION){
					covertextFile = fc.getSelectedFile();
					pathCovertextField.setText(covertextFile.getAbsolutePath());
				}
				
			}
		});
		contentPane.add(btnLoadCovertext, "cell 2 1");
		
		JLabel lblMessage = new JLabel("Message:");
		contentPane.add(lblMessage, "cell 0 2,alignx left,aligny top");
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 1 2,grow");
		
		final JTextArea messageArea = new JTextArea();
		scrollPane.setViewportView(messageArea);
		messageArea.setWrapStyleWord(true);
		messageArea.setLineWrap(true);
		
		JButton btnHide = new JButton("Hide");
		btnHide.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				if(fc.showSaveDialog(HideMessageFrame.this)==JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					String fileName = file.getAbsolutePath();
					if(!fileName.endsWith(".pdf")){
						file = new File(fileName+".pdf");
					}
					try {
						if(keyFile==null || pathKeyField.getText()!=keyFile.getAbsolutePath())
							keyFile = new File(pathKeyField.getText());
						if(covertextFile==null || pathCovertextField.getText()!=covertextFile.getAbsolutePath()){
							String path = pathCovertextField.getText();
							if(path==null||path.isEmpty())
								covertextFile = null;
							else
								covertextFile = new File(path);
						}
						BufferedReader br = new BufferedReader(new FileReader(keyFile));
						Hider hider = new PDFHider(br.readLine(), covertextFile);
						br.close();
						hider.hide(messageArea.getText(), file);
						JOptionPane.showMessageDialog(HideMessageFrame.this, "Message was successfully hidden.");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(HideMessageFrame.this, "An error occured: "+e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		contentPane.add(btnHide, "flowx,cell 1 3,alignx center");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HideMessageFrame.this.dispose();
			}
		});
		contentPane.add(btnCancel, "cell 1 3,alignx center");
	}

}
