package per.method;

import java.util.List;

import per.map.NodeAble;

public class Statistic {
	public static long startTime;
	public static long count;
	public static double length(List<NodeAble> list) {
		double sum = 0;
		int count = 0;
		double x = 0;
		double y = 0;;
		for(NodeAble node : list) {
			if(count ++ == 0) {
				x = node.getPositionX();
				y = node.getPositionY();
				continue;
			}
			double x1 = node.getPositionX();
			double y1 = node.getPositionY();
			sum += Math.sqrt(Math.abs(x1 - x) * Math.abs(x1 - x)+Math.abs(y1 - y)* Math.abs(y1 - y));
			x = x1;
			y = y1;
		}
		return sum;
	}
}
