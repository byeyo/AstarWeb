package per.map;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Edge implements Iterable<EdgeNode>{
	public static void main(String[] args) {
		boolean a = true;
		System.out.println(a^false);
	}
	final public boolean isHorizontal ;
	private LinkedList<EdgeNode> nodes ;
	private HashSet<EdgeNode> nodeSet;
	final public GridJointNode from;
	final public GridJointNode to;
	
	public Edge(GridJointNode from, GridJointNode to) {
		this.from = from;
		this.to = to;
		isHorizontal = (from.y == to.y);
		if((isHorizontal && from.x - to.x != -1)
		  ||(!isHorizontal && from.y - to.y != -1)){
			throw new RuntimeException("参数错误");
		}
		nodes = new LinkedList<>();
		nodeSet = new HashSet<>(15);
		/*EdgeNode fn = new EdgeNode(0, this);
		fn.setG(Double.MAX_VALUE);
		addNodes(fn);
		EdgeNode tn = new EdgeNode(1, this);
		tn.setG(Double.MAX_VALUE);
		addNodes(tn);*/
		
	}
	public boolean addNodes(EdgeNode node) {
		if(nodes.size() >= 1) {
			return true;
		}
		double relativeVal = node.relativeLocation();
	
		if(compare(relativeVal, 1 ) == 1 || compare(relativeVal, 0) == -1){
			throw new RuntimeException("坐标溢出 相对值为："+relativeVal);
		}
		if(examinDuplication(node)) {
			return true;
		}else {
			nodes.add(node);
			nodeSet.add(node);
			return false;
		}
		
	}
	public boolean isHorizontal() {
		return isHorizontal;
	}
	private boolean examinDuplication(EdgeNode readyToAddNode) {// 可以考虑优化
		return nodeSet.contains(readyToAddNode);
	}
	public int compare(double a, double b) {
		if(a - b > 0.01) {
			return 1;
		}
		if(a - b < -0.01) {
			return -1;
		}
		return 0;
	}
	public List<EdgeNode> getNodes() {
		return nodes;
	}
	@Override
	public boolean equals(Object o) {
		Edge otherEdge = (Edge)o;
		return this.from.equals(otherEdge.from)
			&&!(this.isHorizontal^otherEdge.isHorizontal);
	}
	@Override
	public int hashCode() {
		return Objects.hash(from,isHorizontal);
	}
	@Override
	public Iterator<EdgeNode> iterator() {
		return nodes.iterator();
	}
	@Override 
	public String toString() {
		return "("+from.x+", "+from.y+")"+" "+isHorizontal;
	}
}
