package edu.illuminaty.websteganography.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import edu.illuminaty.websteganography.util.key.KeyGenerator;

public class KeyGeneratorFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1101213043579597115L;
	private JPanel contentPane;
	private JTextField webpageField;

	/**
	 * Create the frame.
	 */
	public KeyGeneratorFrame() {
		super("Key Generator");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 130);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow]", "[][grow]"));
		
		JLabel lblWebsite = new JLabel("Website:");
		contentPane.add(lblWebsite, "cell 0 0,alignx trailing");
		
		webpageField = new JTextField();
		contentPane.add(webpageField, "cell 1 0,growx");
		webpageField.setColumns(10);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 1 1,grow");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnGenerateKey = new JButton("Generate Key");
		btnGenerateKey.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				if(fc.showSaveDialog(KeyGeneratorFrame.this)==JFileChooser.APPROVE_OPTION){
					String webpage = webpageField.getText();
					if(!isUrlValid(webpage))
						webpage = "http://" + webpage;
					String key = KeyGenerator.generateKey(webpage);
					File file = fc.getSelectedFile();
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(file));
						bw.write(key);
						bw.flush();
						bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		panel.add(btnGenerateKey);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				KeyGeneratorFrame.this.dispose();				
			}
		});
		panel.add(btnCancel);
	}
	
	private boolean isUrlValid(String url){
		try{
			new URL(url);
			return true;
		}catch(MalformedURLException e){
			return false;
		}
	}
}
