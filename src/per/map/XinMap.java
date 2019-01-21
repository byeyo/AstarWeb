package per.map;

import per.method.AStarBaseOnEdge;

public class XinMap extends FDMap {
	public static void main(String[] args) {
		int size = 15;
		XinMap map = new XinMap(size, size);
		EdgeNode start = new EdgeNode(0 ,map.getEdge(1, 1, false));
		EdgeNode destination = new EdgeNode(0, map.getEdge(12, 6, false));
		AStarBaseOnEdge astar = new AStarBaseOnEdge(start, destination);
		boolean[][] obstacle = new boolean[size][size];
		obstacle[1][2] = true;
		obstacle[2][2] = true;
		obstacle[3][2] = true;
		obstacle[1][2] = true;
	//	obstacle[2][2] = true;
		AstarRunner ar = new AstarRunner(map, astar, obstacle);
		for(NodeAble n :ar.getPath()) {
			System.out.println(n);
		}
	}
	public XinMap(int colSize, int rowSize) {
		super(colSize, rowSize);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void computeBestNode(Edge edgeEle,EdgeNode node) {
		EdgeNode result = null;
		EdgeNode fn = new EdgeNode(0, edgeEle);
		EdgeNode tn = new EdgeNode(1, edgeEle);
		Double resultG = null;
		Double resultF = null;
		
		fn.setH(getLength(fn, end));
		fn.setG(getLength(fn, node));
		fn.setF(fn.getH()+fn.getG());
		//edgeEle.addNodes(fn);
		
		tn.setH(getLength(tn, end));
		tn.setG(getLength(tn, node));
		tn.setF(tn.getH()+tn.getG());
		//edgeEle.addNodes(tn);
		
		double h2 = tn.getH();
		double h1 = fn.getH();
		double errF =  h1 - h2;
		double f1 = node.getG() + 1 + fn.getH();
		double f2 = node.getG() + 1.41 + tn.getH();
		double t = node.relativeLocation();
		//System.out.println("father: "+ node);
		if(node.whitchStatus() == 2) {
			if((upperOrEqual(h2, h1)) 
			    ||(upper(1, Math.max(1.41 * errF, Math.sqrt(1 + errF * errF))))){
				result = new EdgeNode(0, edgeEle);
				resultG = node.getG() + 1;
				resultF = resultG  + fn.getH();
			}
			else if((upperOrEqual(errF, 1))
                     ||(upper(1, errF)&&upperOrEqual(1.41 * errF, 1)&& upper(h1, h2))) {
				resultF = Math.min(f1, f2);
				if(MathTest.doubleCompare(f1, f2) == 1) {
					resultG = node.getG() + 1.41;
					result = new EdgeNode(1, edgeEle);
				}else {
					resultG = node.getG() + 1;
					result = new EdgeNode(0, edgeEle);
				}
			}
			else if(upper(h1, h2) && upper(1, 1.41 * errF)
            		&&(upperOrEqual(Math.sqrt(1 +errF * errF), 1))) {
            		resultF = node.getG() + fn.getH() + Math.sqrt(1 - errF * errF);
            		resultG = node.getG() + 1.0/(Math.sqrt(1 - errF * errF));
            		double rela = errF / Math.sqrt(1 - errF * errF);
            		result = new EdgeNode(rela, edgeEle);
            		//System.out.println(rela+"  A");
            }
		}else if(!edgeEle.isHorizontal ) {   //边为竖直
			double temp1 = Math.sqrt(1 + t*t)*errF;
			double temp2 = Math.sqrt(1 + errF * errF);
			if(upperOrEqual(h2, h1)||upper(1, Math.max(temp1, temp2))) {  //情形1
				resultG = node.getG() + t;
				resultF = resultG + fn.getH();
				result = new EdgeNode(0, edgeEle);
			}
			else if(upperOrEqual(errF, 1) || (upper(1,errF)&&upperOrEqual(temp1, 1)&& upper(h1, h2))){ //情形2
					resultG = node.getG() + t;
					f1 = resultG + fn.getH();
					resultG = node.getG() + Math.sqrt(1 + t * t);
					f2  = resultG + tn.getH();
					if(upperOrEqual(f2, f1)) {
						resultF = f1;
						resultG = node.getG() + t;
						result = new EdgeNode(0, edgeEle);
					}else {
						resultF = f2;
						result = new EdgeNode(1, edgeEle);
					}
			}else if(upper(h1, h2)&&(1 >temp1 && upperOrEqual(temp2, 1))) { //情形3
				double rela = t * errF / Math.sqrt(1 - errF * errF);
				resultF = node.getG() + fn.getH()+ t * Math.sqrt(1 - errF * errF);
				resultG = node.getG() + t / Math.sqrt(1 - errF * errF);
				result = new EdgeNode(rela, edgeEle);
				//System.out.println(rela+"  B");
			}
		}else if(edgeEle.isHorizontal) {
			double h3 = h1;
			errF = h2 - h3;
			double temp = 1- errF/Math.sqrt(1 - errF * errF);
			double temp2 = errF/Math.sqrt(1 - errF * errF);
			if(upperOrEqual(errF, 1)||(upper(1, errF) && upperOrEqual(errF, 0)&&upperOrEqual(t, temp))) {
				resultG = node.getG() + Math.sqrt(1 + (1 - t) * (1 - t));
				resultF = resultG + fn.getH();
				result = new EdgeNode(0, edgeEle);
			}else if((upperOrEqual(errF, 0) && upper(1, errF)&&upper(temp, t))
					||(upper(errF, -1)&&upper(0, errF)&&upper(t, -temp2))) {
				resultF = node.getG() + Math.sqrt(1 - errF * errF) + t * errF + tn.getH();
				resultG = node.getG() + 1/Math.sqrt(1 - errF * errF);
				double rela = temp2 + t;
			//	System.out.println(rela+"  C");
				result = new EdgeNode(rela, edgeEle);
			}else if(upperOrEqual(-1, errF) 
					||(upper(errF , -1)) && upper(0, errF)&& upperOrEqual(-errF/Math.sqrt(1- errF * errF), t)) {
				resultG = node.getG() + Math.sqrt(1 + t * t);
				resultF = resultG + tn.getH();
				result = new EdgeNode(1, edgeEle);
			}
		}
		
		result.setG(resultG);
		result.setH(resultF - resultG);
		result.setF(resultF);
		edgeEle.addNodes(result);
		//System.out.println("child: "+result);
	}
	private double getLength(NodeAble from, NodeAble to ) {
		double x1 = from.getPositionX();
		double y1 = from.getPositionY();
		double x2 = to.getPositionX();
		double y2 = to.getPositionY();
		return MathTest.getLength(x1, y1, x2, y2);
	}
	private boolean upperOrEqual(double num1, double num2) {
		return MathTest.doubleCompare(num1, num2) == 1 || MathTest.doubleCompare(num1, num2) == 0;
	}
	private boolean upper(double num1, double num2) {
		return MathTest.doubleCompare(num1, num2) == 1 ;
	}
}
