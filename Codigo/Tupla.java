package biblioteca;

public class Tupla <T1, T2> {
	private T1 var1;
	private T2 var2;
	
	public Tupla(T1 x, T2 y){
		this.var1= x;
		this.var2= y;
		
	}
	public T1 getVar1() {
		return this.var1;
	}
	public T2 getVar2() {
		return this.var2;
	}
	public void setVar1(T1 x) {
		this.var1= x;
	}
	public void setVar2(T2 y) {
		this.var2= y;
	}
	
	@Override
	public String toString() {
		return "["+ var1.toString() + "," + var2.toString() + "]";
	}
}
