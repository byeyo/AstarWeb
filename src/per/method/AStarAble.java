package per.method;

import java.util.Iterator;
import java.util.LinkedList;

import per.map.NodeAble;

public interface AStarAble {
	public void addOpenList(NodeAble node);
	public void addCloseList(NodeAble node);
	/**   
	 * @Title: getMinEvalutionNode   
	 * @Description: 得到评价值最小的点并删除  
	 * @param: @return      
	 * @return: NodeAble      
	 * @throws   
	 */
	public NodeAble getAndRemoveMinEvalutionNode();
	public void updateNode(NodeAble node);
	public double computeNodeH(NodeAble node);
	/**
	 * @param node ss
	 * @param path sdasd
	 */
	public double computeNodeG(NodeAble path, NodeAble node);
	public boolean isInOpenList(NodeAble node);
	public boolean isInCloseList(NodeAble node);
	public NodeAble getStart();
	public NodeAble getDestination();
}
