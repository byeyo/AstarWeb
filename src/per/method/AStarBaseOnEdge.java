package per.method;

import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

import per.map.EdgeNode;
import per.map.BasicGridMap;
import per.map.BasicNode;
import per.map.EdgeGridMap;
import per.map.NodeAble;

public class AStarBaseOnEdge extends BasicAstar {
	public AStarBaseOnEdge(NodeAble start, NodeAble destination) {
		super(start, destination);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		//BasicGridMap gridMap = new BasicGridMap(5, 5);
		EdgeGridMap map = new EdgeGridMap(5, 5);
		EdgeNode start = new EdgeNode(1, map.getEdge(3, 4, true));
		//BasicNode destination = new BasicNode(4, 4);
		EdgeNode destination = new EdgeNode(0, map.getEdge(1, 1, true));
		start.setG(0);

		AStarBaseOnEdge basicAstar = new AStarBaseOnEdge(start, destination);
		map.setDestination(destination);
		/*for(int i =1 ; i <=3 ; i ++) {
			map.setObstacle(2, i);
		}
		map.setObstacle(1, 1);*/
		basicAstar.addOpenList(start);
		NodeAble curNode = null;
		boolean isPathFind = false;
		while((curNode = basicAstar.getAndRemoveMinEvalutionNode()) != null && !isPathFind) {
			basicAstar.addCloseList(curNode);
			System.out.println(curNode);
			/*System.out.println(curNode.getPositionX()+","+curNode.getPositionY());
			if(curNode.getPositionX() == 1.5 && curNode.getPositionY() == 3) {
				System.out.println("debug");
				
			}*/
			
			for(NodeAble node : map.getAdjNodes(curNode)) {
				System.out.println(node);
			/*	if(curNode.getPositionX() == 1.5 && curNode.getPositionY() == 3) {
					System.out.println("debug");
				}
				if(node.getPositionX() == 1 && node.getPositionY() == 2 ) {
					System.out.println("debug");
					node.hashCode();
				}*/
			//	System.out.println("node:"+node.getPositionX()+","+node.getPositionY());
				if(basicAstar.isInCloseList(node)) {
					continue;
				}
 				else if(basicAstar.isInOpenList(node)) {
					double curToNextDist = basicAstar.computeNodeG(curNode, node);
					if(curToNextDist + curNode.getG() < node.getG()) {
						node.setFather(curNode);
						node.setG(curToNextDist + curNode.getG());
						node.setF(node.getG() + node.getH());
						basicAstar.updateNode(node);
					}
				}else {
					double h = basicAstar.computeNodeH(node);
					double g = basicAstar.computeNodeG(curNode, node) + curNode.getG();
					double f = g + h;
					node.setH(h);
					node.setG(g);
					node.setF(f); 
					node.setFather(curNode);
					basicAstar.addOpenList(node);
					if(destination.equals(node)) {
						System.out.println("OKKO");
						isPathFind = true;
						destination.setFather(node.getFather());
						break;
					}
				}
			}
			
		}
		NodeAble path =destination.getFather();
		System.out.println(destination.getPositionX()+","+destination.getPositionY());
		while(path != null) {
			System.out.println(path.getPositionX()+","+path.getPositionY());
			path = path.getFather();
		}
		System.out.println("count = "+Statistic.count);
	}
	
	@Override
	public double computeNodeG(NodeAble path, NodeAble node) {
		double x1 = node.getPositionX();
		double x2 = path.getPositionX();
		double y1 = node.getPositionY();
		double y2 = path.getPositionY();
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	@Override
	public double computeNodeH(NodeAble node) {
		double x1 = node.getPositionX();
		double x2 = destination.getPositionX();
		double y1 = node.getPositionY();
		double y2 = destination.getPositionY();
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
}
