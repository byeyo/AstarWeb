package per.webPage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.map.AstarRunner;
import per.map.BasicGridMap;
import per.map.BasicNode;
import per.map.EdgeGridMap;
import per.map.EdgeNode;
import per.map.FDMap;
import per.map.MapStaticData;
import per.map.NodeAble;
import per.map.XinMap;
import per.method.AStarBaseOnEdge;
import per.method.BasicAstar;

/**
 * Servlet implementation class Main
 */
@WebServlet("/XinMain")
public class XinMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XinMain() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sizeX = MapStaticData.obs.length;
		int sizeY = MapStaticData.obs.length;
		XinMap map = new XinMap(sizeX, sizeY);
		EdgeNode start = new EdgeNode(0, map.getEdge(1, 1, false));
		EdgeNode destination = new EdgeNode(0, map.getEdge(12,  6, false));
		AStarBaseOnEdge astar = new AStarBaseOnEdge(start, destination);
		boolean[][] obstacle ;
		obstacle = MapStaticData.obs ;
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
	public boolean[][] generateObstacle(boolean[][] obstacle, double ratio){
		for(int i = 0 ; i < obstacle.length ; i ++) {
			for( int j = 0 ; j < obstacle[0].length; j ++) {
				if(Math.random() > ratio) {
					obstacle[i][j] = true;
				}
			}
		}
		return obstacle;
	}
	public String listToJSONstr(List<NodeAble> list) {
	    	if(list.size() == 1) {
	    		System.out.println("no result");
	    	}
	    	StringBuilder sb = new StringBuilder("[");
	    	for(NodeAble node : list) {
	    		double y = node .getPositionY() ;
	    		double x = node .getPositionX() ;
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
}
