import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class TreeVisualisation extends JPanel {
	private Graph model;
	
	public TreeVisualisation(Graph model) {
		super();
		this.model = model;
	}

	public TreeVisualisation() {
		model = null;
	}

	public void setModel(Graph model) {
		this.model = model;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		if(model != null) {
			model.calcVisualisation();
			int n = model.getN();
			for(int i = 1; i <= n; i++) {
				int x = model.getXnode(i);
				int y = model.getYnode(i);
				g2.drawOval(x, y, 35, 35);
				g2.drawString(String.valueOf(i), x, y);
			}
			for(int i = 1; i <= n; i++) {
				int father = model.getFather(i);
				if(father == 0) {
					continue;
				}
				int x = model.getXnode(i);
				int y = model.getYnode(i);
				int xfather = model.getXnode(father);
				int yfather = model.getYnode(father);
				g2.drawLine(x + 17, y, xfather + 17, yfather + 35);
			}
		}
    }
}
