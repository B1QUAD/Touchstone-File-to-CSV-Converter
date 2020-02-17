package touchstoneconverter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
/**
 *
 * @author David
 */
public class touchstoneconverter implements ActionListener {

    private static String file = "";
    private int x = 0;
    private JLabel label;
    private JLabel label2;
    private JLabel label3;
    private JFrame frame;
    private JPanel panel;
    private TextField tf1, tf2, tf3;

    public touchstoneconverter() {
        frame = new JFrame();
        panel = new JPanel();
        label = new JLabel("Enter file path (use forward slashes \"/\"):");
        label2 = new JLabel("Enter desired output unit (Ghz, Mhz, Khz, Hz):");
        label3 = new JLabel("");

        JButton button = new JButton("Convert Touchstone File");
        button.setBounds(10, 10, 20, 20);
        button.addActionListener(this);
		
		panel.setSize(10, 10);
		
        tf1 = new TextField();
        tf1.setBounds(50, 50, 150, 20);
		
		tf2 = new TextField();
		tf2.setBounds(50, 50, 150, 20);
		
		
		//tf2.setText("(Ghz, Mhz, Khz)");
		
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 125));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(label);
		panel.add(tf1);
        panel.add(label2);
		panel.add(tf2);
        panel.add(button);
        panel.add(label3);
        

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setTitle("Touchstone File Converter");
        frame.pack();
        frame.setVisible(true);

    }
    public static void main(String[] args) {
        new touchstoneconverter();
    }
    public void actionPerformed(ActionEvent e) {
		try{
			String path = tf1.getText();
			String conv = tf2.getText();
			converter con = new converter(path, conv);
			con.simpleConverter(path);
			label3.setText("Conversion Succesful");
		}
		catch(Exception E){
			label3.setText("Conversion Failed");
		}
		
    }
}