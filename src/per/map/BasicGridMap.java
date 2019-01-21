package per.map;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BasicGridMap implements AstarMap{
	private NodeAble[][] gridMap ;
	private int colSize;
	private int rowSize;
	protected NodeAble destination;
	/**
	 * @param colSize
	 * @param rowSize
	 */
	public BasicGridMap(int colSize, int rowSize) {
		this.colSize = colSize;
		this.rowSize = rowSize;
		this.gridMap = new BasicNode[rowSize][colSize];
		for(int i = 0 ; i < rowSize ; i ++) {
			for(int j = 0 ; j < colSize ; j ++) {
				gridMap[i][j] = new BasicNode(j , i);
			}
		}
	}
	public NodeAble getNode(int x, int y) {
		if(x < 0 || x>= colSize || y < 0 | y >= rowSize) {
			return null;
		}
		return gridMap[y][x];
	}
	public List<NodeAble> getAdjNodes(NodeAble node){
		LinkedList<NodeAble> list = new LinkedList<>();
		int x = (int)node.getPositionX();
		int y = (int)node.getPositionY();
		boolean[] flag = new boolean[4];
		for(int i = y - 1 ; i <= y + 1 ; i ++) {
			for(int j = x -1 ; j <= x + 1 ; j ++) {
				if(j <0 || j >= colSize || i < 0 || i >= rowSize) {
					continue;
				}
				if(i == y && j == x)
				if(i -1 >= 0 && gridMap[i - 1][j] == null ) {
					if(j - 1 >= 0 && gridMap[i ][ j - 1] == null ) {
						flag[0] = true;
					}
					if(j + 1 < colSize && gridMap[i ][ j + 1] == null ) {
						flag[1] = true;			
					}
				}
				if(i == y && j == x)
				if(i + 1 < rowSize &&gridMap[i + 1 ][ j] == null ) {
					if(j-1 >=0 && gridMap[i ][ j - 1] == null ) {
						flag[2] = true;
					}
					if(j + 1 < colSize && gridMap[i ][ j + 1] == null ) {
						flag[3] = true;					
					}					
				}

			}
		}
		for(int i = y - 1 ; i <= y + 1 ; i ++) {
			for(int j = x -1 ; j <= x + 1 ; j ++) {
				if(j <0 || j >= colSize || i < 0 || i >= rowSize) {
					continue;
				}
				
				if(i == y && j == x) {
					continue;
				}
				if(i == y -1 && j == x - 1 && flag[0]) {
					continue;
				}
				if(i == y -1 && j == x + 1 && flag[1]) {
					continue;
				}
				if(i == y + 1 && j == x -1  && flag[2]) {
					continue;
				}
				if(i == y + 1 && j == x + 1  && flag[3]) {
					continue;
				}
				if(gridMap[i][j] != null)
				list.add(gridMap[i][j]);
			}
		}
		return list;
	}
	public void setObstacle(int x, int y) {
		gridMap[y][x] = null;
	}
	@Override
	public void setDestination(NodeAble node) {
		this.destination = node;
		
	}
	
	
}
