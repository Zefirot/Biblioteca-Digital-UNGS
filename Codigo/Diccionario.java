package biblioteca;

import java.util.ArrayList;
import java.util.HashSet;

public class Diccionario<Clave, Significado> {
	private ArrayList< Tupla<Clave,Significado> > datos;
	private HashSet<Clave> claves;
	public Diccionario () {//constructor
		this.datos= new ArrayList< Tupla <Clave,Significado> >();
		this.claves = new HashSet<Clave>();
	}
	
	public void agregar(Clave clave, Significado significado) {
		if(!pertenece(clave)) {
			Tupla<Clave,Significado> tuplanueva= new Tupla<Clave,Significado>(clave,significado);
			datos.add(tuplanueva);
			claves.add(clave); //Agrego la clave al conjunto.
		}
		else {
			for(int i=0; i<datos.size(); i++) { //recorro el array de lista
				if(clave.equals(datos.get(i).getVar1())) {//si el string de n es igual al de datos[i](variable1). asumo que son iguales
					datos.get(i).setVar2(significado); //asi que sobre escribo el significado (la variable 2)
				}
			}
		}
	}
	public Significado obtener(Clave n) {
		//System.out.println(claves.size());
		for(Tupla<Clave,Significado> elem: datos) {
			if(elem.getVar1().equals(n)) {
				return elem.getVar2();
			}
		}
		throw new RuntimeException("No existe Significado asociado con esa Clave");
	}
	public Significado obtenerOPredeterminado(Clave n, Significado s) {
		for(Tupla<Clave,Significado> elem: datos) {
			if(elem.getVar1().equals(n)) {
				return elem.getVar2();
			}
		}
		return s;
	}
	
	public void quitar(Clave elemento) {
		if (!pertenece(elemento)) {
			throw new RuntimeException("El elemento ingresado no puede ser quitado porque no esta registrado");
		}
		
		for(Tupla<Clave,Significado> elem : datos) {
			if(elem.getVar1().equals(elemento)) {
				datos.remove(elem);
				claves.remove(elem.getVar1());
				return;
			}
		}
	}
	
	public boolean pertenece(Clave n) {
		boolean ret= false;
		for(int i=0; i<datos.size();i++) {
			if(n.equals(datos.get(i).getVar1())) {//si tienen la misma clave
				ret= ret||true;
			}
				
		}
		return ret;
	}

	public HashSet<Clave> keySet(){
		return claves; //Devuelvo el conjunto de claves para asi poder iterar en un for
	}

	@Override
	public String toString() {
		return datos.toString();
	}
}
