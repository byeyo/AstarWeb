package per.map;

import java.util.Objects;

public class BasicNode implements NodeAble {
	protected NodeAble father ;
	protected double h;
	protected double g;
	protected double f;
	protected int positionX;
	protected int positionY;
	public BasicNode(int positionX ,int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}
	@Override
	public NodeAble getFather() {
		return father;
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
		this.g = g;
		
	}

	@Override
	public void setH(double h) {
		this.h = h;
	}

	@Override
	public double getPositionX() {
		return positionX;
	}

	@Override
	public double getPositionY() {
		return positionY;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(positionX,positionY);
	}
	
	@Override 
	public boolean equals(Object obj) {
		NodeAble objNode = (NodeAble)obj;
		return objNode.getPositionX() == positionX && objNode.getPositionY() == positionY;
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
	@Override 
	public String toString() {
		return positionX+","+positionY+" g:"+g+" h:"+h+" f:"+f;
	}
}
