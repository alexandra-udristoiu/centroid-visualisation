
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Graph {
	private int n;
	private int rad;
	private LinkedList<Integer> v[];
	private int[] num, viz, nfather;
	private int radCentroid;
	private Graph centroid;
	private int numEdges;
	private int[] aEdges, bEdges;
	private int[] a, b;
	private int[] xnode, ynode;
	int[] x1Line, x2Line, y1Line, y2Line, xCircle, yCircle;
	public Graph(int n, int rad, int[] a, int[] b) {
		this.n = n;
		this.rad = rad;
		this.a = a;
		this.b = b;
		v = new LinkedList[101];
		num = new int[101];
		viz = new int[101];
		nfather = new int[101];
		for(int i = 1; i <= n; i++) {
			v[i] = new LinkedList();
		}
		for(int i = 1; i < n; i++) {
			v[ a[i] ].add(b[i]);
			v[ b[i] ].add(a[i]);
		}
	}
	public Graph() {
		// TODO Auto-generated constructor stub
	}
	public boolean isTree() {
		for(int i = 1; i <= n; i++) {
			viz[i] = 0;
		}
		boolean ok = dfsVerif(rad, 0);
		for(int i = 1; i <= n; i++) {
			if(viz[i] == 0) {
				ok = false;
			}
		}
		return ok;
	}
	private boolean dfsVerif(int node, int father) {
		viz[node] = 1;
		Iterator<Integer> it = v[node].listIterator();
		while(it.hasNext()) {
			int child = it.next();
			if(child != father) {
				if(viz[child] == 1) {
					return false;
				}
				else {
					boolean ok = dfsVerif(child, node);
					if(ok == false) {
						return false;
					}
				}
			}
		}
		return true;
	}
	public void calcCentroid() {
		for(int i = 1; i <= n; i++) {
			viz[i] = 0;
		}
		numEdges = 0;
		radCentroid = 0;
		aEdges = new int[101];
		bEdges = new int[101];
		decomp(rad, 0);
		centroid = new Graph(n, radCentroid, aEdges, bEdges);
	}
	private void decomp(int node, int lastCentroid) {
		dfsCountCen(node, lastCentroid);
		int centroid = findCentroid(node, lastCentroid, num[node]);
		viz[centroid] = 1;
		if(radCentroid == 0) {
			radCentroid = centroid;
		}
		else {
			numEdges++;
			aEdges[numEdges] = lastCentroid;
			bEdges[numEdges] = centroid;
		}
		Iterator<Integer> it = v[centroid].listIterator();
		while(it.hasNext()) {
			int son = it.next();
			if(viz[son] == 0) {
				decomp(son, centroid);
			}
		}
		
	}
	private int findCentroid(int node, int father, int total) {
		Iterator<Integer> it = v[node].listIterator();
		while(it.hasNext()) {
			int son = it.next();
			if(viz[son] == 1 || son == father) {
				continue;
			}
			if(num[son] > total / 2) {
				return findCentroid(son, node, num[son]);
			}
		}
		return node;
	}
	private void dfsCountCen(int node, int father) {
		num[node] = 1;
		Iterator<Integer> it = v[node].listIterator();
		while(it.hasNext()) {
			int son = it.next();
			if(viz[son] == 1 || son == father) {
				continue;
			}
			dfsCountCen(son, node);
			num[node] += num[son];
		}
	}
	public String writeEdgesCentroid() {
		return centroid.writeEdges();
	}
	private String writeEdges() {
		String text = new String();
		for(int i = 1; i < n; i++) {
			text += String.valueOf(a[i]) + " " + String.valueOf(b[i]) + "\n";
		}
		return text;
	}
	public int getN() {
		return n;
	}
	public Graph getCentroid() {
		return centroid;
	}
	public void calcVisualisation() {
		xnode = new int[101];
		ynode = new int[101];
		for(int i = 1; i <= n; i++) {
			num[i] = 0;
		}
		dfscount(rad, 0);
		dfsvis(rad, 0, 1, 0);
	}
	private void dfsvis(int node, int father, int level, int start) {
		ynode[node] = 60 * level - 20;
		if(num[father] - 1 == num[node]) {
			xnode[node] = xnode[father];
		}
		else {
			xnode[node] = (int) ( (start + num[node] * 1.0 / 2) * 50);
		}
		int prev = 0;
		Iterator<Integer> it = v[node].listIterator();
		while(it.hasNext()) {
			int son = it.next();
			if(son != father) {
				dfsvis(son, node, level + 1, prev + start);
				if(prev == 0) {
					prev++;
				}
				prev += num[son];
			}
		}
		
	}
	private void dfscount(int node, int father) {
		num[node] = 1;
		Iterator<Integer> it = v[node].listIterator();
		nfather[node] = father;
		while(it.hasNext()) {
			int son = it.next();
			if(son != father) {
				dfscount(son, node);
				num[node] += num[son];
			}
		}
		
	}
	public int getFather(int i) {
		return nfather[i];
	}
	public int getXnode(int i) {
		return xnode[i];
	}
	public int getYnode(int i) {
		return ynode[i];
	}

}
