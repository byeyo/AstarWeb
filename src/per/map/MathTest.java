package per.map;



public class MathTest {

	public static void main(String[] args) {
	/*	double x1 = 0.5d;
		double x2 = 3d;
		double y1 = 1d;
		double y2 = 3d;
		double x = 1;
		double y = getY(x1, y1, x2, y2,x);
		System.out.println("y = " + y);
		System.out.println("minLength= "+(getLength(x1, y1, x, y) + getLength(x2, y2, x, y)));*/
		int gridX = 1;
		int gridY = 1;
		double x1 = 1;
		double y1 = 1;
		double x2 = 1;
		double y2 = 2;
		System.out.println(isCrossGrid(gridX, gridY, x1, y1, x2, y2));
		System.out.println(round2(100.358));
	}
	public static double getY(double x1, double y1, double x2, double y2,double x) {
		return getX(y1,x1,y2,x2,x);
	}
	public static double getX(double x1, double y1, double x2, double y2,double y) {
		if(x1 > x2) {
			double t = x1;
			x1 = x2;
			x2 = t;
			t = y1;
			y1 = y2;
			y2 = t;
		}
		double a =  Math.pow(y2 - y, 2) - Math.pow(y1 - y, 2);
	//	System.out.println("a="+a);
		double b = 2* Math.pow(y1 - y, 2) *x2 - 2* Math.pow(y2 - y, 2) * x1;
	//	System.out.println("b="+b);
		double c = Math.pow(y2 - y, 2) * x1 *x1 - Math.pow(y1 - y, 2) * x2 * x2;
	//	System.out.println("c="+c);
		/*double a =  y2 * y2 - y1*y1;
		System.out.println("a="+a);
		double b = 2*y1 * y1 *x2 - 2* y2 * y2 * x1;
		System.out.println("b="+b);
		double c = y2 * y2 * x1 *x1 - y1 * y1 * x2 * x2;
		System.out.println("c="+c);*/
		/*double a = 1;
		double b = -7;
		double c = 12;*/
		double result = Double.MIN_VALUE;
		if(a == 0) {
			if(b == 0) { //即x1 == x2
				result = x1;
			}else {
				result = -c/b;
			}
			
		}else
		result =  (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
		//result = 0.66;
		//System.out.println("result= " + result);
//		System.out.println("验证：y="+(a*result * result + b*result + c));
//		System.out.println("result: "+result);
		return result;
	}
	public static double getLength(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2)+Math.pow(y2-y1, 2));
	}
	public static boolean isCrossGrid(int gridX,int gridY, double x1, double y1, double x2 , double y2) {
		if(doubleCompare(y2, y1) == 0 || doubleCompare(x2, x1) == 0){
			return false;
		}
		double k = (y2 - y1) / (x2 - x1);
		double b = y2 -k*x2;
		int count = 0;
 		for(int j = gridY ; j <= gridY + 1; j ++) {
 			for(int i = gridX; i <= gridX + 1; i ++) {
   				if(doubleCompare(j, y1) == 0&& doubleCompare(i, x1) == 0) {
					count = count - 2;
				}
 				if(doubleCompare(j, y2) == 0&& doubleCompare(i, x2) == 0) {
					count = count - 2;
				}
			}
		}
		double result = cross(k, b, gridX, true);
		if((doubleCompare(result, gridY) == 1 || doubleCompare(result, gridY) == 0)
				&& (doubleCompare(result, gridY + 1) == -1 || doubleCompare(result, gridY + 1) == 0)) {
			count ++;
		}
		result = cross(k, b, gridX + 1, true);
		if((doubleCompare(result, gridY) == 1 || doubleCompare(result, gridY) == 0)
				&& (doubleCompare(result, gridY + 1) == -1 || doubleCompare(result, gridY + 1) == 0)) {
			count ++;
		}
		result = cross(k, b, gridY, false);
		if((doubleCompare(result, gridX) == 1 || doubleCompare(result, gridX) == 0)
				&& (doubleCompare(result, gridX + 1) == -1 || doubleCompare(result, gridX + 1) == 0)){
			count ++;
		}
		result = cross(k, b, gridY + 1, false);
		if((doubleCompare(result, gridX) == 1 || doubleCompare(result, gridX) == 0)
				&& (doubleCompare(result, gridX + 1) == -1 || doubleCompare(result, gridX + 1) == 0)) {
			count ++;
		}
		if(count > 1) {
	//		System.err.println("有"+count+"个交点");
		}
		return count > 0;
	}
	private static double cross(double k, double b, double num, boolean isX) {
		if(isX) {
			return k * num + b;
		}
		return (num - b) / k;
	}
	public static int doubleCompare(double a , double b) {
		if(a - b > 0.01) {
			return 1;
		}
		if(a - b < -0.01) {
			return -1;
		}
		return 0;
	}
	public static double round2(double num) {
		return (double)Math.round(num * 100) / 100;
	}
}
