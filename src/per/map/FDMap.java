package per.map;

import java.util.List;

import per.method.AStarBaseOnEdge;
import per.method.BasicAstar;
import per.method.Statistic;

public class FDMap extends EdgeGridMap {
	
	public FDMap(int colSize, int rowSize) {
		super(colSize, rowSize);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void computeBestNode(Edge edgeEle,EdgeNode node) {
		double x1 = node.getPositionX();
		double y1 = node.getPositionY();
		double x2 = end.getPositionX();
		double y2 = end.getPositionY();
		EdgeNode sb = new EdgeNode(0, edgeEle);
		sb.setFather(node);
		sb.setG(MathTest.getLength(x1, y1, sb.getPositionX(), sb.getPositionY()));
		sb.setH(MathTest.getLength(x1, y1, end.getPositionX(), end.getPositionY()));
		sb.setF(sb.getG()+sb.getH());
		if(canBeConnected(node, sb)) {
			edgeEle.addNodes(sb);
		}
		EdgeNode sa = new EdgeNode(1, edgeEle);
		sa.setFather(node);
		sa.setG(MathTest.getLength(x1, y1, sa.getPositionX(), sa.getPositionY()));
		sa.setH(MathTest.getLength(x1, y1, end.getPositionX(), end.getPositionY()));
		sa.setF(sa.getG()+sa.getH());
		if(canBeConnected(node, sa)) {
			edgeEle.addNodes(sa);
		}
		EdgeNode s = node;
		double result[] = computeCost(s, sa, sb);
		if(result == null) {
			return;
		}
		EdgeNode resultNode = new EdgeNode(result[1], edgeEle);
		
		if(!canBeConnected(node, resultNode)) {
			//System.out.println("s");
			return;
		}
		
		resultNode.setH(result[0]);
		resultNode.setG(MathTest.getLength(resultNode.getPositionX(), resultNode.getPositionY(), x2, y2));
		resultNode.setF(resultNode.getH()+resultNode.getG());
		resultNode.setFather(node);
		edgeEle.addNodes(resultNode);
		
	}
	public double[] computeCost(EdgeNode s,EdgeNode sa,EdgeNode sb) {
		EdgeNode s1 = sb;
		EdgeNode s2 = sa;
		Double vs = null;
		Double y = null;
		double b=  1;
		double c = 1;
		if(!canBeConnected(s, s1)) {
			b = Double.MAX_VALUE;
		}
		if(!canBeConnected(s, s2)) {
			c = Double.MAX_VALUE;
		}
		if(!isDig(s2, sa)) {
			s1 = sa;
			s2 = sb;
		}
		if(Math.min(c,b) == Double.MAX_VALUE) {
			return null;
		}
		else if(s1.getF() < s2.getF() || MathTest.doubleCompare(s1.getF(), s2.getF()) == 0) {
			vs = 1 + s1.getF();
			y = s1.relativeLocation();
		} else {
			double f = s1.getF() - s2.getF();
			if(MathTest.doubleCompare(f, b) == 0 || MathTest.doubleCompare(f, b) == -1 ) {
				if(MathTest.doubleCompare(f, c) == 1 || MathTest.doubleCompare(f, c) == 0 ) {
					vs = 1.41 + s2.getF();
					y = s2.relativeLocation();
				}else {
					y = Math.min(f/Math.sqrt(1 - f * f), 1);
					vs = Math.sqrt(1 + y * y)+f * (1 - y) + s2.getF();
				}
				  
			}else {
				vs = 1.41 + s2.getF();
				y= s2.relativeLocation();
			}
			
		}
		return new double[] {vs,y};
	}
	private boolean isDig(NodeAble s, NodeAble sa) {
		double x1 = s.getPositionX();
		double y1 = s.getPositionY();
		double x2 = s.getPositionX();
		double y2 = s.getPositionY();
		if(MathTest.doubleCompare((x1-x2) * (x1-x2) + (y1-y2) * (y1-y2),2) == 0) {
			return true;
		}
		return false;
	}
}

