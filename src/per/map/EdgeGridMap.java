package per.map;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import per.method.AStarBaseOnEdge;
import per.method.Statistic;



public class EdgeGridMap  implements AstarMap{
	public static void main(String[] args) {
		/*EdgeGridMap egMap = new EdgeGridMap(3, 3);
		int x1 = 1;
		int y1 = 1;
		Edge edge1 = egMap.getEdge(x1, y1, false);
		int x2 = 0;
		int y2 = 1;
		Edge edge2 = egMap.getEdge(x2, y2,false);
		int x3 = 3;
		int y3 = 2;
		Edge edge3 = egMap.getEdge(x3, y3,false);
		boolean resultConnect = egMap.isOnOneGrid(edge1, edge2);
		boolean resultConnect1 = egMap.isOnOneGrid(edge2, edge1);
		System.out.println(resultConnect+","+resultConnect1);
		EdgeNode node1 = new EdgeNode(0.5, edge1);
		EdgeNode node2 = new EdgeNode(1, edge2);
		EdgeNode node3 = new EdgeNode(1, edge3);
		egMap.end = node3;
		System.out.println("测试（"+node1.getPositionX()+","+node1.getPositionY()+") -> "+
		"("+node2.getPositionX()+", "+node2.getPositionY()+")");
		System.out.println(egMap.canBeConnected(node1, node2));
		System.out.println(egMap.canBeConnected(node2, node1));
		//egMap.setObstacle(0,0);
		System.out.println(egMap.canBeConnected(node1, node2));
		System.out.println(egMap.canBeConnected(node2, node1));
		
		Iterator<Edge> it = egMap.adjAroundEdge(node1);
		while(it.hasNext()) {
			Edge e = it.next();
			System.out.print(e.from.x +" ," + e.from.y);
			for(EdgeNode en : e) {
				System.out.print(" "+en.relativeLocation());;
			}
			if(e.isHorizontal) {
				System.out.println(" H");
			}else {
				System.out.println(" V");
			}
		}*/
		EdgeGridMap egMap = new EdgeGridMap(3, 3);
		
	}
	private static Edge[] edge6 = new Edge[6];
	private static Edge[] edge8 = new Edge[8];
	boolean isObstacle[][];
	GridJointNode[][] jointMap ;
	private final int jointRowSize;
	private final int jointColSize;
	protected NodeAble end;
	public EdgeGridMap(int colSize, int rowSize) {
		this.jointRowSize = rowSize + 1;
		this.jointColSize = colSize + 1;
		isObstacle = new boolean[rowSize][colSize];
		jointMap = new GridJointNode[jointRowSize][jointColSize];
		for(int j = 0 ; j < rowSize + 1 ; j ++) {
			for(int i = 0 ; i < colSize + 1 ; i ++) {
				jointMap[j][i] = new GridJointNode(i, j);
			}                                                                                                                                                                                                              
		}
		for(int j = 0 ; j < rowSize + 1 ; j ++) {//0,1
			for(int i = 0 ; i < colSize + 1 ; i ++) {
				if(i < colSize) {
					jointMap[j][i].setRightEdge(new Edge(jointMap[j][i], jointMap[j][i + 1]));
				}
				if(j < rowSize) {
					jointMap[j][i].setUpEdge(new Edge(jointMap[j][i], jointMap[j + 1][i]));
				}
			}                                                                                                                                                                                                              
		}
		
	}
	public Edge getEdge(int x , int y, boolean isHorizontal) {
		if(x < 0 || x >= jointColSize || y < 0 || y >= jointRowSize) {
			return null;
		}
		if(isHorizontal) {
			return jointMap[y][x].rightEdge();
		}
		return jointMap[y][x].upEdge();
	}

	public void setObstacle(int x, int y) {
		isObstacle[y][x] = true;
	}
	
	private boolean isObstacle(int x, int y) {
		if(x < 0 || x >= jointColSize  - 1 || y < 0 || y >= jointRowSize - 1) {
			return true;
		}
		return isObstacle[y][x];
	}
	
	public boolean canBeConnected(EdgeNode node,EdgeNode toNode) {
	
			int[] temp = getGrid(node,toNode);
			if(temp == null) {
				return false;
			}
			if(node.whitchStatus() == 2 && toNode.whitchStatus() == 2) {
				int x1 = node.whenOnJoint()[0];
				int y1 = node.whenOnJoint()[1];
				int x2 = toNode.whenOnJoint()[0];
				int y2 = toNode.whenOnJoint()[1];		
				if(x1 == x2) {	
					/*if(!isObstacle(x1, Math.min(y1, y2))){
						return true;
					}*/
					if(isObstacle(x1 , Math.min(y1, y2)) &&  (isObstacle(x1 - 1, Math.min(y1, y2) ))){
						return false;
					}
					if(isObstacle(x1 -1 , y1) && isObstacle(x1, y2)
					   ||isObstacle(x1  , y1) && isObstacle(x1 - 1, y2)) {
						return false;
					}
					return true;
				 }
				if(y1 == y2) {	
					/*if(!isObstacle(Math.min(x1, x2) , y1 - 1)){
						return true;
					}*/
					if(isObstacle(Math.min(x1, x2) , y1 ) &&  (isObstacle(Math.min(x1, x2) , y1 - 1))){
						return false;
					}
					return true;
				 }	
				temp[0] = Math.min(x1, x2);
				temp[1] = Math.min(y1, y2);
			}
			
		//	System.out.println(isObstacle(temp[0], temp[1]));
			return !isObstacle(temp[0], temp[1]);
		
		//todo根据两个点来判断障碍的位置
		
	}
	
	private boolean isOnOneGridBasedOneEdge(Edge edge ,Edge otherEdge) {
		int x1 = (int)edge.from.x;
		int y1 = (int)edge.from.y;
		int x2 = (int)otherEdge.from.x;
		int y2 = (int)otherEdge.from.y;
		if(edge.isHorizontal() && otherEdge.isHorizontal()) {
			return Math.abs(y1 - y2) == 1 
					&& x1 == x2 ;
		}
		if(!edge.isHorizontal() && !otherEdge.isHorizontal()) {
			return Math.abs(x1 - x2) == 1 
					&& y1 == y2;
		}
		
		if(edge.isHorizontal() && !otherEdge.isHorizontal()) {
			if(x1 == x2 && y1 == y2) {
				return true;
			}
			else if(x1 - x2 == -1
				&&y1 == y2) {
				return true;
			}else if(x1 - x2 == -1
					&& y1 - y2 == 1) {
				return true;
			}else if(x1 == x2
					&& y1 - y2 == 1) {
				return true;
			}
		}
		
		
		return false;
	}
	public Iterator<Edge> adjAroundEdge(EdgeNode node){
		
		if(node.hasComputeAdjNode()) {  //周围结点已经计算过了则不再计算
			return node.iterator();
		}
		
		node.hasComputeAdjNode(true);
		return iniAdj(node);
		
	}
	public List<NodeAble> getAdjNodes(NodeAble nodeAble){
			LinkedList result = new LinkedList<>();
			EdgeNode node = (EdgeNode)nodeAble;
			Iterator<Edge> it = null;
			if(!node.hasComputeAdjNode()) {
				node.hasComputeAdjNode(true);
				iniAdj(node);
			}
			it = node.iterator();
			if(it == null) {
				return result;
			}
			while(it.hasNext()) {
				Edge e = it.next();
				if(e == null) {
					continue;
				}
				for(EdgeNode adjNode : e.getNodes()) {
					if(canBeConnected(node, adjNode))
					result.add(adjNode);
				}
			};
			return result;
		}
	private Iterator<Edge> iniAdj(EdgeNode node){
 		Edge edge = node.onEdge();
		int status = node.whitchStatus();
		int x = edge.from.x;
		int y = edge.from.y;
		LinkedList<Edge> result = new LinkedList<>();
		Edge[] aroundEdge = null;
		
		if(status == 0) {
			aroundEdge = new Edge[6];
			aroundEdge[0] = getEdge(x , y + 1, true);
			aroundEdge[1] = getEdge(x + 1 , y , false);
			aroundEdge[2] = getEdge(x + 1 , y - 1 ,  false);
			aroundEdge[3] = getEdge(x, y - 1, true);
			aroundEdge[4] = getEdge(x, y - 1, false);
			aroundEdge[5] = getEdge(x, y , false);
		}else if(status == 1) {
			aroundEdge = new Edge[6];
			aroundEdge[0] = getEdge(x - 1 , y + 1, true);
			aroundEdge[1] = getEdge(x , y + 1 , true);
			aroundEdge[2] = getEdge(x + 1, y  ,  false);
			aroundEdge[3] = getEdge(x , y  , true);
			aroundEdge[4] = getEdge(x - 1, y , true);
			aroundEdge[5] = getEdge(x - 1, y , false);
		}else if(status == 2) {
			if(isObstacle((int)node.getPositionX(),(int) node.getPositionY())
					&& isObstacle((int)node.getPositionX() - 1,(int) node.getPositionY() - 1)){
					return null;
				}
			if(isObstacle((int)node.getPositionX() - 1,(int) node.getPositionY())
					&& isObstacle((int)node.getPositionX() ,(int) node.getPositionY() - 1)){
					return null;
				}
			aroundEdge = new Edge[8];
			if(EdgeNode.compareDouble(node.relativeLocation(),1.0) == 0) {
				if(edge.isHorizontal) {
					x = x + 1;
				}
				else {
					y = y + 1;
				}
			}else{
				if(edge.isHorizontal) {
				}
				else {
				}
			}
			
			aroundEdge[0] = getEdge(x - 1 , y + 1 , true);
			aroundEdge[1] = getEdge(x , y + 1  , true);
			aroundEdge[2] = getEdge(x + 1 , y  ,  false);
			aroundEdge[3] = getEdge(x + 1 , y - 1  ,  false);
			aroundEdge[4] = getEdge(x , y - 1 ,  true);
			aroundEdge[5] = getEdge(x - 1 , y - 1  ,  true);
			aroundEdge[6] = getEdge(x - 1 , y - 1 ,  false);
			aroundEdge[7] = getEdge(x - 1 , y , false);
			
		}else {
			throw new RuntimeException("EdgeNode处于未知状态");
		}
		
		double x1 = node.getPositionX();
		double y1 = node.getPositionY();
		double x2 = end.getPositionX();
		double y2 = end.getPositionY();
		int count = 0;
		for(Edge edgeEle : aroundEdge) {
			if(count == 1) {
				//System.out.println("");
			}
			if(edgeEle != null) {
				result.add(edgeEle);
				computeBestNode(edgeEle,node);
				/*double h = -1,g = -1;
 				EdgeNode newNode;
				if(edgeEle.isHorizontal()) {
					double xAbsolution = MathTest.getX(x1, y1, x2, y2, edgeEle.from.y);
					xAbsolution = getAbsolute('x', x1, y1, x2, y2, edgeEle);
					xAbsolution = amendMaxAndMin(xAbsolution, edgeEle.from.x, edgeEle.to.x);
					newNode = new EdgeNode(xAbsolution - edgeEle.from.x, edgeEle);
					h = MathTest.getLength(xAbsolution, edgeEle.from.y, x2, y2);
					g = MathTest.getLength(xAbsolution, edgeEle.from.y, x1, y1);
				}else {
					double yAbsolution = MathTest.getY(x1, y1, x2, y2, edgeEle.from.x);
					yAbsolution = getAbsolute('y', x1, y1, x2, y2, edgeEle);
					yAbsolution = amendMaxAndMin(yAbsolution, edgeEle.from.y, edgeEle.to.y);
					newNode = new EdgeNode(yAbsolution - edgeEle.from.y, edgeEle);
					h = MathTest.getLength(edgeEle.from.x, yAbsolution, x2, y2);
					g = MathTest.getLength(edgeEle.from.x, yAbsolution, x1, y1);					
				}
				//System.out.println("edge"+count +":");
				
				if(canBeConnected(node,newNode )) {
					if(edgeEle.addNodes(newNode)){
					   continue;
					}
					newNode.setH(h);
					newNode.setG(g);
					newNode.setF(g + h);
					newNode.setFather(node);
					//System.out.println("TT  "+newNode.relativeLocation());
				}
				
			*/}
			count ++;
		}
		node.setAdjEdge(aroundEdge);
		return result.iterator();
	}
	protected void computeBestNode(Edge edgeEle,EdgeNode node) {
		double x1 = node.getPositionX();
		double y1 = node.getPositionY();
		double x2 = end.getPositionX();
		double y2 = end.getPositionY();
		
		if(edgeEle != null) {
			double h = -1,g = -1;
				EdgeNode newNode;
			if(edgeEle.isHorizontal()) {
				double xAbsolution /*= MathTest.getX(x1, y1, x2, y2, edgeEle.from.y)*/;
				xAbsolution = getAbsolute('x', x1, y1, x2, y2, edgeEle);
				xAbsolution = amendMaxAndMin(xAbsolution, edgeEle.from.x, edgeEle.to.x);
				newNode = new EdgeNode(xAbsolution - edgeEle.from.x, edgeEle);
				h = MathTest.getLength(xAbsolution, edgeEle.from.y, x2, y2);
				g = MathTest.getLength(xAbsolution, edgeEle.from.y, x1, y1);
			}else {
				double yAbsolution /*= MathTest.getY(x1, y1, x2, y2, edgeEle.from.x)*/;
				yAbsolution = getAbsolute('y', x1, y1, x2, y2, edgeEle);
				yAbsolution = amendMaxAndMin(yAbsolution, edgeEle.from.y, edgeEle.to.y);
				newNode = new EdgeNode(yAbsolution - edgeEle.from.y, edgeEle);
				h = MathTest.getLength(edgeEle.from.x, yAbsolution, x2, y2);
				g = MathTest.getLength(edgeEle.from.x, yAbsolution, x1, y1);					
			}
			//System.out.println("edge"+count +":");
			
			if(canBeConnected(node,newNode )) {
				if(edgeEle.addNodes(newNode)){
				   
				}else {
					newNode.setH(h);
					newNode.setG(g);
					newNode.setF(g + h);
					newNode.setFather(node);
				}
				
				//System.out.println("TT  "+newNode.relativeLocation());
			}
			
		}
	}

	public LinkedList<EdgeNode> getAroundEdgeNode(EdgeNode node){
		if(!node.hasComputeAdjNode()) {
			
		}
		return null;
	}
	public boolean isOnOneGrid(Edge edge ,Edge otherEdge) {
		if(edge == null || otherEdge == null) {
			return false;
		}
		return edge.equals(otherEdge)
				||isOnOneGridBasedOneEdge(edge, otherEdge) 
				|| isOnOneGridBasedOneEdge(otherEdge, edge);
	}
	private double amendMaxAndMin(double val, double min , double max) {
		if(val > max) {
			return max;
		}
		if(val < min) {
			return min;
		}
		return val;
	}
	private int[] getGrid(Edge edge1,Edge edge2) {
		int x1 = edge1.from.x;
		int y1 = edge1.from.y;
		int x2 = edge2.from.x;
		int y2 = edge2.from.y;
		if(isOnOneGrid(edge1, edge2)) {
			return new int[] {Math.min(x1, x2),Math.min(y1,y2)};
		}
		return null;
	}
	
	private int[] coreToNodeGetGrid(EdgeNode core,EdgeNode otherNode) {
		if(core.whitchStatus() != 2) {
			return null;
		}
		int x = -1;
		int y = -1;
		if(core.onEdge().isHorizontal) {
			if(MathTest.doubleCompare(core.relativeLocation(), 1) == 0) {
				x = core.onEdge().from.x;
				y = core.onEdge().from.y;
				if(isOnOneGrid(getEdge(x, y, true), otherNode.onEdge())) {
					return getGrid(getEdge(x, y, true), otherNode.onEdge());
				}
				x = x + 1;
				if(isOnOneGrid(getEdge(x, y, true), otherNode.onEdge())) {
					return getGrid(getEdge(x, y, true), otherNode.onEdge());
				}
			}
			else {
				x = core.onEdge().from.x ;
				y = core.onEdge().from.y;
				if(isOnOneGrid(getEdge(x, y, true), otherNode.onEdge())) {
					return getGrid(getEdge(x, y, true), otherNode.onEdge());
				}
				x = x - 1;
				if(isOnOneGrid(getEdge(x, y, true), otherNode.onEdge())) {
					return getGrid(getEdge(x, y, true), otherNode.onEdge());
				}
			}
		}else {
			if(MathTest.doubleCompare(core.relativeLocation(), 1) == 0) {
				x = core.onEdge().from.x;
				y = core.onEdge().from.y;
				if(isOnOneGrid(getEdge(x, y, false), otherNode.onEdge())) {
					return getGrid(getEdge(x, y, false), otherNode.onEdge());
				}
				y = y + 1;
				if(isOnOneGrid(getEdge(x, y, false), otherNode.onEdge())) {
					return getGrid(getEdge(x, y, false), otherNode.onEdge());
				}
			} 
			else{
				x = core.onEdge().from.x;
				y = core.onEdge().from.y;
				if(isOnOneGrid(getEdge(x, y, false), otherNode.onEdge())) {
					return getGrid(getEdge(x, y, false), otherNode.onEdge());
				}
				y = y - 1;
				if(isOnOneGrid(getEdge(x, y, false), otherNode.onEdge())) {
					return getGrid(getEdge(x, y, false), otherNode.onEdge());
				}
			}
		}
		return null;

	}
	private int[] getGrid(EdgeNode node1,EdgeNode toNode) {
		if(node1.whitchStatus() == 2) {
			return coreToNodeGetGrid(node1, toNode);
		}
		if(toNode.whitchStatus() == 2) {
			return coreToNodeGetGrid(toNode, node1);
		}
		return getGrid(node1.onEdge(),toNode.onEdge());
	}
	@Override
	public void setDestination(NodeAble end) {
		this.end = end;
		
	}
	protected double getAbsolute(char xOrY,double x1,double y1,double x2, double y2 , Edge edgeEle) {
		
		if(xOrY == 'x') {
			return MathTest.getX(x1, y1, x2, y2, edgeEle.from.y);
		}else if(xOrY == 'y') {
			return MathTest.getY(x1, y1, x2, y2, edgeEle.from.x);
		}
		throw new RuntimeException();
	}
	private Edge[] getEdge6() {
		return edge6;
	}
	private Edge[] getEdge8() {
		return edge6;
	}
	
}
