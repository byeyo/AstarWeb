package per.map;

import java.util.List;

public interface AstarMap {
	public List<NodeAble> getAdjNodes(NodeAble node);
	public void setObstacle(int x, int y);
	public void setDestination(NodeAble node);
}
