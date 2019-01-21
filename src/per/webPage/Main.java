package per.webPage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.map.AstarRunner;
import per.map.EdgeGridMap;
import per.map.EdgeNode;
import per.map.MapStaticData;
import per.map.NodeAble;
import per.method.AStarBaseOnEdge;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sizeX = 50;
		int sizeY = 50;
		EdgeGridMap map = new EdgeGridMap(sizeX, sizeY);
		EdgeNode start = new EdgeNode(0, map.getEdge(1, 1, false));
		EdgeNode destination = new EdgeNode(0, map.getEdge(12,  6 , false));
		AStarBaseOnEdge astar = new AStarBaseOnEdge(start, destination);
		boolean[][] obstacle = new boolean[sizeX][sizeY];
		obstacle = generateObstacle(obstacle, 0.8,start,destination);
		obstacle[44][4] = true;
		/*obstacle[4][6] = true;
		obstacle[5][6] = true;
		obstacle[5][7] = true;*/
	//	obstacle = myObs(sizeX);
		obstacle = myObs(sizeY);
		
		MapStaticData.obs = obstacle  ;
	//	boolean[][] obstacle = new boolean[4][4];
		/*obstacle[1][2] = true;
		obstacle[2][2] = true;
		obstacle[3][2] = true;
		obstacle[1][1] = true;*/
		AstarRunner ar = new AstarRunner(map, astar, obstacle);
		String obsStr = listToJSONstr(obstacle);
		request.setAttribute("obsJsonStr", obsStr);
		String path = listToJSONstr(ar.getPath());
		request.setAttribute("pathJsonStr", path);
		request.setAttribute("mapSize", size(sizeX, sizeY));
	//	test();
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher("/display.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public boolean[][] generateObstacle(boolean[][] obstacle, double ratio, EdgeNode start, EdgeNode end){
		for(int i = 0 ; i < obstacle.length ; i ++) {
			for( int j = 0 ; j < obstacle[0].length; j ++) {
				if(Math.random() > ratio) {
					obstacle[i][j] = true;
				}
			}
		}
		obstacle[(int)start.getPositionX()][(int)start.getPositionY()] = false;
		obstacle[(int)end.getPositionX()][(int)end.getPositionY()] = false;
		return obstacle;
	}
	public boolean[][] myObs(int size){
		boolean[][] obs  = new boolean[size][size];
		/*for(int i = 6 ; i <=9 ; i ++) {
			obs[i][2]= true;
		}
		for(int i = 1 ; i <=6 ; i ++) {
			obs[i][6]= true;
		}
		for(int i =0 ; i <= 4 ; i ++) {
			obs[i][10]= true;
		}
		for(int i =3 ; i <= 5 ; i ++) {
			obs[6][i]= true;
		}
		for(int i =0 ; i <= 1 ; i ++) {
			obs[10][i]= true;
		}*/
		/*for(int i = 19 ; i <= 22 ; i ++) {
			obs[i][6]= true;
		}
		for(int i = 6 ; i <= 13 ; i ++) {
			obs[i][15]= true;
		}
		for(int i = 4 ; i <= 6 ; i ++) {
			obs[i][23]= true;
		}
		for(int i = 10 ; i <= 13 ; i ++) {
			obs[i][23]= true;
		}
		for(int i = 27 ; i <= 29 ; i ++) {
			obs[4][i]= true;
		}
		for(int i = 14 ; i <= 16 ; i ++) {
			obs[13][i]= true;
		}*/
		drawVertical(3, 5, 6, obs);
		obs[5][7] = true;
		return obs;
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
	public String listToJSONstr(List<NodeAble> list) {
	    	if(list.size() == 1) {
	    		System.out.println("no result");
	    	}
	    	StringBuilder sb = new StringBuilder("[");
	    	for(NodeAble node : list) {
	    		double y = node .getPositionY();
	    		double x = node .getPositionX();
	    		System.out.println("x= "+x+" y = "+y);
	    		sb.append("{");
	    		sb.append("\'x\':"+x+",");
	    		sb.append("\'y\':"+y+"}");
	    		sb.append(",");
	    	}
	    	sb.replace(sb.length() - 1, sb.length(), "]");
			return sb.toString();
	  }
	public String listToJSONstr(boolean[][] obstacles) {
    	
    	StringBuilder sb = new StringBuilder("[");
    	for(int j = 0 ; j < obstacles.length ; j ++) {
    		for(int i = 0 ; i < obstacles[0].length; i ++) {
    			if(obstacles[j][i]) {
    				sb.append("{");
    	    		sb.append("\'x\':"+i+",");
    	    		sb.append("\'y\':"+j+"}");
    	    		sb.append(",");
    			}
    		}
    	}
    	sb.replace(sb.length() - 1, sb.length(), "]");
		return sb.toString();
   }
	public String size(int x , int y) {
		return "{\'x\':"+x+",\'y\':"+y+"}";
	}
	public static void  test() {
		int size = 10;
		int startX = 1;
		int startY = 1;
		int endX = 4;
		int endY = 4;
		EdgeGridMap map1 = new EdgeGridMap(size, size);
		EdgeNode start1 = new EdgeNode(0, map1.getEdge(startX, startY, false));
		EdgeNode destination1 = new EdgeNode(0, map1.getEdge(9,9, false));
		AStarBaseOnEdge astar1 = new AStarBaseOnEdge(start1, destination1);
		boolean[][] obstacle = new boolean[size][size];
		obstacle = MapStaticData.obs;
	/*	obstacle[8][4] = true;
		obstacle[4][3] = true;
		obstacle[5][4] = true;
		obstacle[6][4] = true;
		obstacle[6][5] = true;
		obstacle[5][6] = true;
		obstacle[4][6] = true;
		obstacle[4][5] = true;*/
		/*obstacle[6][7] = true;
		obstacle[6][8] = true;*/
		  long time = System.currentTimeMillis();
			AstarRunner ar = new AstarRunner(map1, astar1, obstacle);
			List<NodeAble> l1 = ar.getPath();
			// basicTime +=System.currentTimeMillis() - time;
			//double r1 = Statistic.length(l1);
			for(NodeAble ne: l1) {
				System.out.println(ne);
			}
			/*avgBasic += r1;
			avgNew += r2;
			countExecutive ++;
			if(r1 >= r2) {
				countWin ++;
			}*/
	}
}
