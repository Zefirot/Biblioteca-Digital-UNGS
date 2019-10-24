package biblioteca;

import java.util.ArrayList;

public class Estanteria {
	
	private String rotulo;
	private int tamanio;
	private int copiaTamanio;
	private int cantidaddelibros;
	private ArrayList<Libro> librosEnEstanteria; //ArrayList para guardar los libros
	
	public Estanteria(int tamanio){
		if(tamanio>0) {
			this.tamanio= tamanio;
			this.copiaTamanio = tamanio;
		}
		else {
			throw new RuntimeException("El tama�o de la estanteria tiene que ser >0");	
		}
		this.rotulo= "";//Cuando se crea una estanteria, no tiene rotulo.
		this.cantidaddelibros=0; //Se inicializa la cantidad a 0
		this.librosEnEstanteria= new ArrayList<Libro>(); //Se crea la arraylist de libros
		
	}
	
	
	public void agregar(Libro libro) {
		if(this.rotulo.equals("")) {//Si la estanteria no tiene rotulo
			throw new RuntimeException("La estanteria no esta rotulada");
		}
		if(this.rotulo.equals(libro.getCategoria())){ //Se compara que el rotulado sea igual a al categoria del libro
			this.librosEnEstanteria.add(libro);//Se agrega
			this.tamanio= tamanio - libro.getAncho();//Se resta el tamanio
			this.cantidaddelibros++;//Se aumenta la cantidad de libros
		}
		
	}
	
	public void quitar(Libro libro) {
		if(pertenece(libro)) {
			this.librosEnEstanteria.remove(libro); //Se remueve el libro ingresado del ArrayList
			this.tamanio+= libro.getAncho(); //Se le "agrega" espacio a la estanteria
			this.cantidaddelibros--; //Se resta la cantidad de libros
		}
		
	}
	
	public boolean estaVacia() {
		if(this.cantidaddelibros>0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void rotular(String rotulo) {
		if(estaVacia()) {//Solo si esta vacio se le puede asignar un rotulo
			this.rotulo= rotulo;
		}
		else {
			throw new RuntimeException("La estanteria ya esta rotulada y contiene libros");
		}
	}
	
	public boolean pertenece(Libro libro) {
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
		this.cantidaddelibros=0;
		this.librosEnEstanteria=quitarLibros;
		
	}
	
	
	public int espacioDisponible() {
		return this.tamanio;
	}
	public int cantidadDeLibros() {
		return this.cantidaddelibros;
	}
	public String rotulado() {
		return this.rotulo;
	}
	
	public ArrayList<Libro> getLibros(){
		return this.librosEnEstanteria;
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
