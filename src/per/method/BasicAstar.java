package per.method;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import per.map.BasicNode;
import per.map.BasicGridMap;
import per.map.NodeAble;

public class BasicAstar implements AStarAble {
	public static void main(String[] args) {
		BasicGridMap gridMap = new BasicGridMap(50, 50);
		BasicNode start = new BasicNode(0, 0);
		BasicNode destination = new BasicNode(49, 49);
		BasicAstar basicAstar = new BasicAstar(start, destination);
		for(int i =0 ; i <=3 ; i ++) {
			gridMap.setObstacle(3, i);
		}
		basicAstar.addOpenList(start);
		NodeAble curNode = null;
		boolean isPathFind = false;
		while((curNode = basicAstar.getAndRemoveMinEvalutionNode()) != null && !isPathFind) {
			basicAstar.addCloseList(curNode);
			//System.out.println(curNode.getPositionX()+","+curNode.getPositionY());
			for(NodeAble node : gridMap.getAdjNodes(curNode)) {
				//System.out.println("node:"+node.getPositionX()+","+node.getPositionY());
				Statistic.count ++;
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
		while(path != null) {
			System.out.println(path.getPositionX()+","+path.getPositionY());
			path = path.getFather();
		}
		System.out.println(Statistic.count);
		/*for(int i = 0 ; i < 5 ; i ++) {
			for(int j = 0 ; j < 5 ; j ++) {
				NodeAble node = gridMap.getNode(j, i);
				if(node == null) {
					continue;
				}
				System.out.print("("+node.getG()+", "+node.getH()+","+node.getF()+")  ");
			}
			System.out.println("");
		}*/
	}
	protected NodeAble start;
	protected NodeAble destination;
	protected PriorityQueue<NodeAble> openList = new PriorityQueue<>(new Comparator<NodeAble>() {
		@Override
		public int compare(NodeAble o1, NodeAble o2) {
			if(o1.getF() > o2.getF()) {
				return 1;
			}
			if(o1.getF() < o2.getF()) {
				return -1;
			}
			return 0;
		}
	});
	protected HashSet<NodeAble> isIncloseList = new HashSet<>();
	protected HashSet<NodeAble> isInopenList = new HashSet<>();
	public BasicAstar(NodeAble start, NodeAble destination) {
		this.start = start;
		this.destination = destination;
	}
	@Override
	public void addOpenList(NodeAble node) {
		//System.out.println(node+"加入了");
		if(isInCloseList(node)) {
			System.out.println("已存在");
		}
		openList.add(node);
		isInopenList.add(node);
		//System.out.println("top="+openList.peek());
	}

	@Override
	public void addCloseList(NodeAble node) {
		isIncloseList.add(node);
	}

	@Override
	public NodeAble getAndRemoveMinEvalutionNode() {
		NodeAble bestNode = openList.poll();
		isInopenList.remove(bestNode);
		return bestNode;
	}

	@Override
	public void updateNode(NodeAble node) {
		openList.remove(node);
		openList.add(node);
	}

	@Override
	public double computeNodeH(NodeAble node) {
		int x1 = (int)node.getPositionX();
		int x2 = (int)destination.getPositionX();
		int y1 = (int)node.getPositionY();
		int y2 = (int)destination.getPositionY();
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	@Override
	public double computeNodeG(NodeAble path, NodeAble node) {
		int x1 = (int)node.getPositionX();
		int x2 = (int)path.getPositionX();
		int y1 = (int)node.getPositionY();
		int y2 = (int)path.getPositionY();
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	@Override
	public boolean isInOpenList(NodeAble node) {
		return isInopenList.contains(node);
	}
	@Override
	public boolean isInCloseList(NodeAble node) {
		return isIncloseList.contains(node);
	}
	@Override
	public NodeAble getStart() {
		return start;
	}
	@Override
	public NodeAble getDestination() {
		// TODO Auto-generated method stub
		return destination;
	}
}
