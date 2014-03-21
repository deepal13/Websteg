package edu.illuminaty.websteganography.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class StartFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3578282104138493326L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		StartFrame frame = new StartFrame();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 150, 130);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JButton btnHideMessage = new JButton("Hide Message");
		btnHideMessage.setAlignmentX(CENTER_ALIGNMENT);
		btnHideMessage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new HideMessageFrame().setVisible(true);
			}
		});
		contentPane.add(btnHideMessage);
		
		JButton btnExtractMessage = new JButton("Extract Message");
		btnExtractMessage.setAlignmentX(CENTER_ALIGNMENT);
		btnExtractMessage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ExtractMessageFrame().setVisible(true);
			}
		});
		contentPane.add(btnExtractMessage);
		
		JButton btnKeyGenerator = new JButton("Key Generator");
		btnKeyGenerator.setAlignmentX(CENTER_ALIGNMENT);
		btnKeyGenerator.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new KeyGeneratorFrame().setVisible(true);
			}
		});
		contentPane.add(btnKeyGenerator);
	}

}
