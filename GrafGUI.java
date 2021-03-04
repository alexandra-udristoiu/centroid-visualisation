import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GrafGUI{
	private static Graph grafInitial;
	private static JTextArea textFinal;
	private static TreeVisualisation tree1, tree2;
	public static boolean parsare(String text) {
		int sizeText = text.length();
		int x = 0, n = 0, nr = 0;
		int[] a = new int[105];
		int []b = new int[105];
		for(int i = 0; i <= sizeText; i++) {
			char ch;
			if(i < sizeText) {
				 ch = text.charAt(i);
			}
			else {
				 ch = 0;
			}
			if(ch >= '0' && ch <= '9') {
				x = x * 10 + ch - '0';
			}
			else {
				if(x != 0) {
					if(n == 0) {
						n = x;
						if(n > 100) {
							textFinal.setText("The number of nodes is too big!");
							return false;
						}
					}
					else {
						nr++;
						if(nr > 2 * (n - 1) ) {
							textFinal.setText("Too many edges!");
							return false;
						}
						if( !(x >= 1 && x <= n) ) {
							textFinal.setText("Invalid edge!");
							return false;
						}
						if(nr % 2 == 1) {
							a[nr / 2 + 1] = x;
						}
						else {
							b[nr / 2] = x;
						}
					}
				}
				x = 0;
			}
		}
		if(nr < 2 * (n - 1) ) {
			textFinal.setText("Edges missing!");
			return false;
		}
		grafInitial = new Graph(n, 1, a, b);
		return true;
		
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("Graph Visualiser for Centroid Decomposition");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(1000, 1000);
	    frame.setLayout(new BorderLayout());
	    JTextArea textInitial = new JTextArea();
	    JScrollPane scrollInitial = new JScrollPane(textInitial);
	    scrollInitial.setVerticalScrollBarPolicy(
	                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollInitial.setBounds(100, 100, 300, 350);
	    JButton button = new JButton("Submit");
	    button.setBounds(200, 370, 100, 20);
	    textFinal = new JTextArea();
	    JScrollPane scrollFinal = new JScrollPane(textFinal);
	    scrollFinal.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollFinal.setBounds(100, 400, 300, 350);
	    textFinal.setEditable(false);
	    tree1 = new TreeVisualisation();
	    tree2 = new TreeVisualisation();
	    button.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e) {
	    		String text = textInitial.getText();
	    		boolean ok = parsare(text);
	    		if(ok == true) {
	    			if(grafInitial.isTree() == false) {
	    				textFinal.setText("The graph is not a tree");
	    			}
	    			else {
	    				grafInitial.calcCentroid();
	    				tree1.setModel(grafInitial);
	    				tree1.repaint();
	    				
	    				tree2.setModel(grafInitial.getCentroid());
	    				tree2.repaint();
	    				
	    				String textCentroid = grafInitial.writeEdgesCentroid();
	    				textFinal.setText(textCentroid);
	    			}
	    		}
	    	}

	    });
	    scrollInitial.setPreferredSize(new Dimension(200, 250) );
	    scrollFinal.setPreferredSize(new Dimension(200, 250) );
	    tree1.setPreferredSize(new Dimension(600, 500) );
	    tree2.setPreferredSize(new Dimension(600, 500) );
	    JPanel left = new JPanel();
	    left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
	    left.add(scrollInitial);
	    left.add(button);
	    left.add(scrollFinal);
	    JPanel right = new JPanel();
	    right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
	    right.add(tree1);
	    right.add(tree2);
	    frame.add(left, BorderLayout.WEST);
	    frame.add(right, BorderLayout.EAST);
	    frame.pack();
	    //frame.setLayout(null);
	    frame.setVisible(true);
	    
	    
	}

}
