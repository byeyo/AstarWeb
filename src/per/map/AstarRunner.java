package per.map;

import java.util.LinkedList;
import java.util.List;

import per.method.AStarAble;
import per.method.AStarBaseOnEdge;
import per.method.BasicAstar;
import per.method.Statistic;

public class AstarRunner {
	static int count = 100;
	static int temp = count;
	static int size ;
	static int err = 130;
	static int errX = 0;
	static int errY = 0 ;
	static double ratio = 0.8;
	int countCurNode = 0;
	public static void main(String[] args) {
		
		double ratioParam[] = {1,0.9,0.8};
		long time = 0;
		int sizeParam[] = {200,400,600};
		int failCount = 0;
		AstarRunner ar = null;
		for(int i = 0 ; i < sizeParam.length ; i ++) {
			for(int j = 0 ; j < ratioParam.length ; j ++) {
				size = sizeParam[i];
				ratio = ratioParam[j];
				List l1 ;
				int countWin = 0;
				int countExecutive = 0;
				long basicTime =0;
				long newTime = 0;
				long xinTime = 0;
				double avgBasic = 0;
				double avgNew = 0;
				double avgXin = 0;
				double avgNodeBasic = 0;
				double avgNodeNew = 0;
				double avgNodeXin = 0;
				while(temp-- > 0) {
					int startX = getRanDomNode(size);
					int startY = getRanDomNode(size);
					int endX = getRanDomNode(size);
					int endY =getRanDomNode(size);
				//	System.out.println(startX+" ,"+startY);
				//	System.out.println("->"+endX+" ,"+ endY);
					BasicGridMap map = new BasicGridMap(size, size);
					BasicNode start = new BasicNode(startX, startY);
					BasicNode destination = new BasicNode(endX, endY);
					BasicAstar astar = new BasicAstar(start, destination);
					boolean[][] obstacle = new boolean[size][size];
					obstacle = AstarRunner.generateObstacle(obstacle, ratio, start, destination);
					ar = new AstarRunner(map, astar, obstacle);
					time = System.currentTimeMillis();
					l1 = ar.getPath();
					long BasictimeTemp = System.currentTimeMillis() - time;
					double avgBasicTemp = Statistic.length(l1);
					int avgNodeBasicTemp = ar.getCountCurNode();
					if(avgBasicTemp == 0 ) {
				    	 continue;
				    }
					
					EdgeGridMap map1 = new EdgeGridMap(size, size);
					EdgeNode start1 = new EdgeNode(0, map1.getEdge(startX, startY, false));
					EdgeNode destination1 = new EdgeNode(0, map1.getEdge(endX,endY, false));
					AStarBaseOnEdge astar1 = new AStarBaseOnEdge(start1, destination1);
					ar = new AstarRunner(map1, astar1, obstacle);
					time = System.currentTimeMillis();
					l1 = ar.getPath();
					long newtimeTemp = System.currentTimeMillis() - time;
					double avgNewTemp = Statistic.length(l1);
					int avgNodeNewTemp = ar.getCountCurNode();
					 if(avgNewTemp == 0 ) {
				    	 continue;
				    }
					
					XinMap map2 = new XinMap(size, size);
					EdgeNode start2 = new EdgeNode(0, map2.getEdge(startX, startY, false));
					EdgeNode destination2 = new EdgeNode(0, map1.getEdge(endX,endY, false));
					AStarBaseOnEdge astar2 = new AStarBaseOnEdge(start2, destination2);
					ar = new AstarRunner(map2, astar2, obstacle);
					time = System.currentTimeMillis();
					l1 = ar.getPath();
					long xinTimeTemp = System.currentTimeMillis() - time;
					double avgXinTemp = Statistic.length(l1);
					int avgNodeXinTemp = ar.getCountCurNode();
					/*obstacle[1][2] = true;
					obstacle[2][2] = true;
					obstacle[3][2] = true;
					obstacle[1][2] = true;*/
				    if( avgXinTemp == 0) {
				    	 continue;
				    }
				      countExecutive ++;
				      
				      avgBasic += avgBasicTemp;
					  basicTime += BasictimeTemp;
					  avgNodeBasic += avgNodeBasicTemp;
					  
					  avgNew += avgNewTemp;
					  newTime += newtimeTemp;
					  avgNodeNew += avgNodeNewTemp;
					  
					  avgXin += avgXinTemp;
					  xinTime += xinTimeTemp;
					  avgNodeXin += avgNodeXinTemp;
						
					}
					avgBasic /= countExecutive;
					avgNew /= countExecutive;
					avgNodeBasic /= countExecutive;
					avgNodeNew /= countExecutive;
					System.out.println("size :"+size+" "+"ratio: "+ratio);
					System.out.println("countWin"+countWin+" max :"+countExecutive);
					System.out.println("BasicTime:"+basicTime/countExecutive+"  newTime: "+ newTime/countExecutive
							+" xinTime: "+ xinTime / countExecutive);
					System.out.println("BasicNode:"+avgNodeBasic+"  newNode: "+avgNodeNew+" xinNode: "+ avgNodeXin/countExecutive);
					System.out.println(avgBasic);
					System.out.println(avgNew);
					System.out.println(avgXin /= countExecutive);
					System.out.println("fail:"+(count - countExecutive));
					temp = count;
			}
			
		}
		
	}
	private AstarMap map;
	private AStarAble astar;
	private NodeAble start ;
	private NodeAble destination;
	public AstarRunner(AstarMap map , AStarAble astar,boolean[][] obstacle) {
		this.map = map;
		this.astar = astar;
		this.start = astar.getStart();
		this.destination = astar.getDestination();
		this.map.setDestination(destination);
		for(int i = 0 ; i < obstacle.length; i ++) {
			for(int j = 0 ; j < obstacle[0].length ; j ++) {
				if(obstacle[i][j]) {
					this.map.setObstacle(j, i);
				}
			}
		}
		this.astar.addOpenList(start);
	}
	public List<NodeAble> getPath(){
	
		NodeAble curNode = null;
		boolean isPathFind = false;
		while((curNode = astar.getAndRemoveMinEvalutionNode()) != null && !isPathFind) {
			astar.addCloseList(curNode);
		//	System.out.println(curNode);
			countCurNode ++;
			for(NodeAble node : map.getAdjNodes(curNode)) {
				if(destination.equals(node)) {
					//System.out.println("OKKO");
					isPathFind = true;
					destination.setFather(curNode);
					break;
				}
				if(astar.isInCloseList(node)) {
					continue;
				}
 				else if(astar.isInOpenList(node)) {
					double curToNextDist = astar.computeNodeG(curNode, node);
					if(curToNextDist + curNode.getG() < node.getG()) {
						node.setFather(curNode);
						node.setG(curToNextDist + curNode.getG());
						double h = MathTest.getLength(node.getPositionX(), node.getPositionY(),
								        destination.getPositionX(), destination.getPositionY());//边上的两点并未初始化
						node.setF(node.getG() + h);
						astar.updateNode(node);
					}
				}else {
					double h = astar.computeNodeH(node);
					double g = astar.computeNodeG(curNode, node) + curNode.getG();
					double f = g + h;
					node.setH(h);
					node.setG(g);
					node.setF(f); 
					node.setFather(curNode);
					astar.addOpenList(node);
					
				}
			}
			
		}
		NodeAble path =destination.getFather();
		LinkedList<NodeAble> result = new LinkedList<>();
		result.add(destination);
	//	System.out.println(destination.getPositionX()+","+destination.getPositionY());
		while(path != null) {
			result.add(path);
	//		System.out.println(path.getPositionX()+","+path.getPositionY());
			path = path.getFather();
		}
	//	System.out.println("curNodes= "+countCurNode + map.getClass().getName());
		
		return result;
	}
	public static boolean[][] generateObstacle(boolean[][] obstacle, double ratio, NodeAble start, NodeAble end){
		for(int i = 0 ; i < obstacle.length ; i ++) {
			for( int j = 0 ; j < obstacle[0].length; j ++) {
				if(Math.random() > ratio) {
					obstacle[i][j] = true;
				}
			}
		}
		obstacle[(int)start.getPositionY()][(int)start.getPositionX()] = false;
		obstacle[(int)end.getPositionY()][(int)end.getPositionX()] = false;
		/*for(int i = 0 ; i < obstacle.length ; i ++) {
			for( int j = 0 ; j < obstacle[0].length; j ++) {
				System.out.print(obstacle[i][j]+" ");
			}System.out.println("");
		}*/
		return obstacle;
	}
	public static int getRanDomNode(int size) {
		int x = (int)(Math.random()*size - 1);
		if(x <=1 || x >= size - 1) {
			return getRanDomNode(size);
		}
		return  x;
	}
	public static boolean [][] drawVertical(int start, int end ,int x, boolean[][] obs) {
		for(int i = start ; i <= end ; i ++) {
			obs[i][x] = true;
		}
		return obs;
	}
	public static boolean[][] drawHorizontal(int start, int end ,int y, boolean[][] obs){
		for(int i = start ; i <= end ; i ++) {
			obs[y][i] = true;
		}
		return obs;
	}
	public int getCountCurNode() {
		return countCurNode;
	}
}
