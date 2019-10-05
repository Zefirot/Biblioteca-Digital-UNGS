package biblioteca;

import java.util.ArrayList;

public class BDUNGS {
	private ArrayList<Estanteria> todasLasEstanterias; //todas las estanterias 
	private Conjunto<String> cojuntoISBN;//va a servir para contar rapido
	private Diccionario<String, Libro> todosLosLibros; //clave isbn, significado libro
	private Diccionario<Libro, Integer> catDeEjemplares;  //Contiene la cantidad de ejemplares
	
	BDUNGS(int catEstanterias, int tamaniodeEstanterias){
			if(catEstanterias<=0) {
				throw new RuntimeException("La cantidad de estanteria tiene que ser mayor a 0");
			}
			//no es necesario controlar tamaniodeEstanterias, si no se cumple el irep de estanteria se lanza una excepcion
			
			this.todasLasEstanterias= new ArrayList<Estanteria>();
			
			this.todosLosLibros= new Diccionario<String, Libro>();
			this.cojuntoISBN= new Conjunto<String>();

			this.catDeEjemplares = new Diccionario<Libro,Integer>();
			
			for(int i=0; i<catEstanterias;i++) {//agrego todas las estanterias a la lista
				Estanteria nueva= new Estanteria(tamaniodeEstanterias);//crea una estanteria nueva
				this.todasLasEstanterias.add(nueva);//la agrega
			}
			
			
		}
	
	void ingresarLibro(String isbn, String categoria, String titulo,  int ancho) {
		
		if(!existeElLibro(isbn)) {//si no existe el libro 
			Libro libroingresado= new Libro(isbn, categoria, titulo,ancho);//creo el libro
			this.cojuntoISBN.insertar(isbn);//lo agrego al conjuntodeisbn
			this.todosLosLibros.agregar(isbn, libroingresado);//agrego el libro nuevo al diccionario
			catDeEjemplares.agregar(libroingresado, 1);
		}else {
			Libro libroingresado= this.todosLosLibros.obtener(isbn);// libro "nuevo", busco el libro 
			//System.out.println(libroingresado);

			//Si el libro existe entonces lo "agrego de nuevo" y despues cambio el significado. El cual seria la cantidad de apariciones.
			catDeEjemplares.agregar(libroingresado, catDeEjemplares.obtener(libroingresado)+1);
			

			colocarLibroEnUnaEstanteriaDisponible(libroingresado); 
			//System.out.println("agregado sin problemas");
		}
	
	}
	void rotularEstante(String categoria, int numEstanteria) { //Si la estanteria esta vacia le asigna una rotulacion
		if(numEstanteria<=0 || numEstanteria>=this.todasLasEstanterias.size()) {
			throw new RuntimeException("No existe una estanteria con ese numero");
		}
		int pos=  numEstanteria - 1;//porque se maneja arraylist
		this.todasLasEstanterias.get(pos).rotular(categoria); //rotula la estanteria
		
	}
	
	void eliminarLibro(String isbn) {
		if(existeElLibro(isbn)) {
			Libro libro= this.todosLosLibros.obtener(isbn);
			//BuscarElLibro en CADA UNA de las Estanterias y eliminarlo
			buscarLibroYEliminarlo(libro);
			
		}
		else {
			throw new RuntimeException("No podes quitar un libro que no existe");
		}
		
	}
	//-------------FALTA HACER-----------
	
	
	//String librosDeUnaCat(Categoria) //devuelve un string con todos los libros y cat de ejemplares 
	//int espacioLibre(Estanteria)//devuelve si el espacio restante

	//void reAgruparEstanterias();
	
	//--------------------
	
	String librosDeUnaCat(String categoria){
		StringBuilder cantidadTotal = new StringBuilder();
		for(Libro elem : catDeEjemplares.keySet()){
			if(elem.getCategoria().equals(categoria)){
				cantidadTotal.append(elem.toString()+", Cantidad: "+ catDeEjemplares.obtener(elem)+" \n");
			}
		}
		return cantidadTotal.toString();
	}



	boolean existeElLibro(String isbn) {
		return  this.cojuntoISBN.pertenece(isbn); //retorna si el isbn pertenece al conjunto
	}
	void colocarLibroEnUnaEstanteriaDisponible(Libro libro) { 
		/*recibe un libro y lo agrega a una estanteria correspondiente, si no se 
		encuenta una estanteria no hace nada*/
		for(Estanteria elem: this.todasLasEstanterias) {//se puede hacer en otra funcion
			if(elem.rotulado().equals(libro.getCategoria()) && elem.espacioDisponible()>=libro.getAncho()) {//si tienen la misma cat y hay espacio disponible
				//System.out.println("Entro");
				elem.agregar(libro);
				return;
			}
			
		}
	}
	void buscarLibroYEliminarlo(Libro libro) {//le pasas un libro y lo elimina 
		for(Estanteria elem: this.todasLasEstanterias) {//tiene que recorrer todas las estanterias
			if(elem.pertenece(libro)) {//si el libro aparece en una
				elem.quitar(libro);//quito el libro de la estanteria
				return;//termina la funcion
				//cuando tenga cat de ejemplares, hay que verificar la cat para ver si lo eliminamos del conj o no
			}
		}
	}
	
	@Override
	public String toString() {//hacer piola, ahora solo imprimo solo las estanterias
		StringBuilder cadena= new StringBuilder("Biblioteca Ungs: ");
		Integer cont= 1; //para saber que estanteria
		for(Estanteria elem: this.todasLasEstanterias) {
			cadena.append("*Estanteria nro" + cont.toString() + " : " + elem.toString() + "***");
			cont++;
			}
		return cadena.toString();
	}
	
}
