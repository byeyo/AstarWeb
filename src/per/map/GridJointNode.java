package per.map;

import java.util.Objects;

public class GridJointNode {
	final public int x;
	final public int y;
	private Edge rightEdge;
	private Edge upEdge;
	public GridJointNode(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void setRightEdge(Edge rightEdge) {
		this.rightEdge = rightEdge;
	}
	public void setUpEdge(Edge upEdge) {
		this.upEdge = upEdge;
	}
	public Edge rightEdge() {
		return rightEdge;
	}
	public Edge upEdge() {
		return upEdge;
	}
	@Override
	public boolean equals(Object obj) {
		GridJointNode node = (GridJointNode) obj;
		return x == node.x && y == node.y;
	}
	@Override
	public int hashCode() {
		return Objects.hash(x,y);
	}
}
