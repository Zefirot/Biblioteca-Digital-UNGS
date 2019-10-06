package biblioteca;

import java.util.ArrayList;

public class Estanteria {
	
	private String rotulo;
	private int tamanio;
	private int copiaTamanio;
	private int cantidaddelibros;
	private ArrayList<Libro> librosEnEstanteria; //guardo los libros
	
	public Estanteria(int tamanio){
		if(tamanio>0) {
			this.tamanio= tamanio;
			this.copiaTamanio = tamanio;
		}
		else {
			throw new RuntimeException("El tama�o de la estanteria tiene que ser >0");	
		}
		this.rotulo= "";//cuando se crea una estanteria, no tiene rotulo
		this.cantidaddelibros=0; //inicializo la cantidad 0
		this.librosEnEstanteria= new ArrayList<Libro>(); //creo la arraylist de libros
		
	}
	
	
	void agregar(Libro libro) {
		if(this.rotulo.equals("")) {//si la estanteria no tiene rotulo
			throw new RuntimeException("La estanteria no esta rotulada");
		}
		if(this.rotulo.equals(libro.getCategoria())){ //si el rotulo es igual a la categoria del libro
			this.librosEnEstanteria.add(libro);//lo agrego
			this.tamanio= tamanio - libro.getAncho();//le resto tama�o
			this.cantidaddelibros++;//aumento la cat de libros
		}
		
	}
	
	void quitar(Libro libro) {
		if(pertenece(libro)) {
			this.librosEnEstanteria.remove(libro);
			this.tamanio+= libro.getAncho();
			this.cantidaddelibros--;
		}
		
	}
	
	boolean estaVacia() {
		if(this.cantidaddelibros>0) {
			return false;
		}
		else {
			return true;
		}
	}
	void rotular(String rotulo) { //va a cambiar el rotulo
		if(estaVacia()) {//solo si esta vacio se le puede asignar un rotulo
			this.rotulo= rotulo;
		}
		else {
			throw new RuntimeException("La estanteria ya esta rotulada y contiene libros");
		}
	}
	
	boolean pertenece(Libro libro) {
		for(Libro elem: librosEnEstanteria) {
			if(elem.equals(libro)) {
				return true;
			}
		}
		return false;
	}
	
	
	public void vaciarEstanteria() {  //Se resetea la estanteria.
		ArrayList<Libro> quitarLibros = new ArrayList<Libro>();
		this.tamanio=copiaTamanio;
		this.librosEnEstanteria=quitarLibros;
	}
	
	//IMPORTANTE 3. Esta funcion debe de ser borrada y sustituida por "vaciarEstanteria".
	/*public ArrayList<Libro> obtenerLibros(){
		ArrayList<Libro> asd = new ArrayList<Libro>();
		ArrayList<Libro> copiaDeLibros = new ArrayList<Libro>();  //Creo una copia de los libros
		for(Libro elem : librosEnEstanteria) {
			copiaDeLibros.add(elem);  //Copio todos los libros que esten en la estanteria
		}
		this.librosEnEstanteria=asd;  //Borro todos los libros
		tamanio=copiaTamanio;  //Reseteo el tamano de la estanteria
		return copiaDeLibros;
	}*/
	
	
	int espacioDisponible() {
		return this.tamanio;
	}
	int cantidadDeLibros() {
		return this.cantidaddelibros;
	}
	String rotulado() {
		return this.rotulo;
	}
	
	@Override
	public String toString() {
		StringBuilder cadena= new StringBuilder("Libros en la Estanteria: ");
		for(Libro elem: librosEnEstanteria) {
			cadena.append("{ ");
			cadena.append(elem.toString());
			cadena.append(" } ");
		}
		return cadena.toString();
	}

}
