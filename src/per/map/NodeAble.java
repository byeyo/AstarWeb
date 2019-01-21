package per.map;

public interface NodeAble  {
	
	public NodeAble getFather();
	public double getG();
	public double getH();
	public double getF();
	public void setF(double f);
	public void setG(double g);
	public void setH(double h);
	public void setFather(NodeAble node);
	public double getPositionX();
	public double getPositionY();
	
}
