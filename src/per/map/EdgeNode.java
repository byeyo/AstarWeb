package per.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class EdgeNode implements NodeAble,Iterable<Edge> {
	private NodeAble father;
	private double g;
	private double h;
	private double f;
	private Edge onEdge;
	private Edge[] nbrsEdge ;
	private int status = -1 ;
	private double relativeLocation;
	private boolean hasComputeAdjNode = false;
	/**
	 * @param positionX
	 * @param positionY
	 */
	public EdgeNode(double relativeLocation , Edge edge) {
		this.onEdge = edge;
		if(edge.isHorizontal) {
			this.status = 0;	
		}
		else {
			this.status = 1;		
		}
		if(compareDouble(relativeLocation, 0) == 0 ){
			relativeLocation = 0;
			this.status = 2;
		}else if(compareDouble(relativeLocation, 1) == 0) {
			relativeLocation = 1;
			this.status = 2;
		}else {
			relativeLocation = MathTest.round2(relativeLocation);
		}
		this.relativeLocation = relativeLocation;
		
	}
	@Override
	public NodeAble getFather() {
		return this.father;
	}
	@Override
	public double getG() {
		return g;
	}
	@Override
	public double getH() {
		return h;
	}
	@Override
	public void setG(double g) {
		this.g = MathTest.round2(g);
	}
	@Override
	public void setH(double h) {
		this.h = MathTest.round2(h);
	}
	
	@Override
	public double getPositionX() {
		if(onEdge.isHorizontal) {
			return relativeLocation + onEdge.from.x ;
		}
		return onEdge.from.x;
	}
	@Override
	public double getPositionY() {
		if(!onEdge.isHorizontal) {
			return relativeLocation + onEdge.from.y;
		}
		return onEdge.from.y;
	}
	@Override
	public int hashCode() {
		return Objects.hash(getPositionX(),getPositionY());
	}
	@Override
	public boolean equals(Object obj) {
		EdgeNode gn = (EdgeNode)obj;
		return getPositionX() == gn.getPositionX() && getPositionY() == gn.getPositionY();
	}
	@Override
	public double getF() {
		return f;
	}
	@Override
	public void setF(double f) {
		this.f = f;
	}
	@Override
	public void setFather(NodeAble node) {
		this.father = node;
		
	}
	public static int compareDouble(double a, double b) {
		if(a - b > 0.01) {
			return 1;
		}
		if(a - b < -0.01) {
			return -1;
		}
		return 0;
	}
	@Override
	public Iterator<Edge> iterator() {
		if(nbrsEdge == null) {
			return null;
		}
		return new Iterator<Edge>() {
			int i = 0;
			public Edge next() {
				return nbrsEdge[i ++];
			}
			public boolean hasNext() {
				return i < nbrsEdge.length;
			}
		};
	}
	public void setAdjEdge(Edge[] edge) {
		nbrsEdge = edge;
	}
	public Edge onEdge() {
		return onEdge;
	}
	public int whitchStatus() {
		return status;
	}
	public double relativeLocation() {
		return relativeLocation;
	}
	public boolean hasComputeAdjNode() {
		return hasComputeAdjNode;
	}
	public void hasComputeAdjNode(boolean hasComputeAdjNode) {
		this.hasComputeAdjNode = hasComputeAdjNode;
	}
	@Override
	public String toString() {
		return "("+getPositionX()+", "+getPositionY()+")" + " h = "+ h+" g=" + g+" f="+f+" hash= "+ hashCode();
	}
	public int[] whenOnJoint() {
		if(status != 2) {
			return null;
		}
		int result[] = new int[] {onEdge.from.x , onEdge.from.y};
		if(onEdge.isHorizontal) {
			result[0] = (int)(onEdge.from.x + relativeLocation);
		}else {
			result[1] = (int)(onEdge.from.y + relativeLocation);
		}
		return result;
	}
}
