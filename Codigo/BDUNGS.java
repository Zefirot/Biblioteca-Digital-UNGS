package biblioteca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BDUNGS {
	private ArrayList<Estanteria> todasLasEstanterias; //Todas las estanterias 
	private Conjunto<String> conjuntoISBN;//Sirve para contar rapido los libros y saber si existen dentro de la biblioteca
	private Diccionario<Libro, Integer> catDeEjemplares;  //Contiene la cantidad de ejemplares
	//private Conjunto<Libro> conjuntoLibros;
	private int copiaDeTamanioEstanterias;
	
	
	public BDUNGS(int catEstanterias, int tamaniodeEstanterias){
		if(catEstanterias<=0) {
			throw new RuntimeException("La cantidad de estanteria tiene que ser mayor a 0");
		}
		//No es necesario controlar tamaniodeEstanterias, si no se cumple el irep de estanteria se lanza una excepcion

		this.todasLasEstanterias= new ArrayList<Estanteria>();

		this.conjuntoISBN= new Conjunto<String>();

		//this.conjuntoLibros= new Conjunto<Libro>();
		this.copiaDeTamanioEstanterias=tamaniodeEstanterias;
		
		this.catDeEjemplares = new Diccionario<Libro,Integer>();
		
		//Se crean todas las estanterias cuando se llama al constructor
		for(int i=0; i<catEstanterias;i++) {
			Estanteria nueva= new Estanteria(tamaniodeEstanterias);
			this.todasLasEstanterias.add(nueva);
		}
			
			
	}
	
	public void ingresarLibro(String isbn, String categoria, String titulo,  int ancho) {

		if(!existeElLibro(isbn)) {//Si no existe el libro 
			Libro libroingresado = new Libro(isbn,categoria,titulo,ancho);
			
			this.conjuntoISBN.insertar(isbn);//Lo se agrega al conjuntoISBN
			
			this.catDeEjemplares.agregar(libroingresado, 1);  //Se agrega el libro nuevo a la cantidad de ejemplares
			
			//this.conjuntoLibros.insertar(libroingresado); //Se agrega el libro nuevo al conjunto

			
			colocarLibroEnUnaEstanteriaDisponible(libroingresado);

		}else {
			Libro libroingresado = obtenerLibro(isbn);
			
			//Si el libro existe entonces lo "agrego de nuevo" y despues cambio el significado. El cual seria la cantidad de apariciones.
			catDeEjemplares.agregar(libroingresado, catDeEjemplares.obtener(libroingresado)+1);

			colocarLibroEnUnaEstanteriaDisponible(libroingresado); 
		}
	
	}
	
	public void rotularEstante(String categoria, int numEstanteria) { //Si la estanteria esta vacia le asigna una rotulacion
		if(numEstanteria<=0 || numEstanteria>this.todasLasEstanterias.size()) {
			throw new RuntimeException("No existe una estanteria con ese numero");
		}
		int pos=  numEstanteria - 1;// Se cambia el valor para simular que la cantidad de estanterias empieza desde 1
		
		this.todasLasEstanterias.get(pos).rotular(categoria); //Se rotula la estanteria
		
	}
	
	public void eliminarLibro(String isbn) {
		if(existeElLibro(isbn)) {
			Libro libro= obtenerLibro(isbn);
			//Se busca el libro en CADA UNA de las Estanterias y se elimina
			buscarLibroYEliminarlo(libro);
		}
		else {
			throw new RuntimeException("El isbn no corresponde a ningun libro ingresado en la biblioteca. Por ende, no puede ser eliminado");
		}
		
	}
	
	private Libro obtenerLibro(String isbn) {
		//Se recorre todas las Estanterias y se busca el libro
		/*for(Libro elem : this.conjuntoLibros.getConjunto()) {
			if(elem.getIsbn().equals(isbn)) {
				return elem;
			}
		}*/
		for(Libro elem: this.catDeEjemplares.keySet()) {
			if(elem.getIsbn().equals(isbn)) {
				return elem;
			}
		}
		throw new RuntimeException("No se encontro el libro solicitado");
	}
	
	
	public int reacomodarCategoria(String categoria) { 
		//Se obtiene todos los libros de la categoria ingresa
		//Seguido de eso se ordena el ArrayList de libros de mayor a menor
		ArrayList<Libro> cajaDeLibros = quickSortMayorAMenor(obtenerLibrosDeCategoria(categoria)); 
		int estantesLiberados=0;
		vaciarEstanterias(categoria);  //Se le pasa la categoria y vacia todos los estantes con esa categoria
		
		for(Estanteria estante : todasLasEstanterias) {
			for(int i=cajaDeLibros.size()-1; i>=0;i--) {
				//Se compara la categoria del libro con la estanteria y si hay espacio disponible para agregarlo
				if(cajaDeLibros.get(i).getCategoria().equals(estante.rotulado()) && cajaDeLibros.get(i).getAncho()<=estante.espacioDisponible()) {
					estante.agregar(cajaDeLibros.get(i)); //Se agrega al estante "vacio"
					cajaDeLibros.remove(i); //Se quita el libro de la caja de libros
				}
			}
		}
		
		//Despues de reacomodar todo de menor a mayor se comprueba la cantidad de estantes que quedaron libres
		for(Estanteria estante : todasLasEstanterias) {
			if(estante.rotulado().equals(categoria) && estante.espacioDisponible()==copiaDeTamanioEstanterias ) {
				estantesLiberados++;
			}
		}
		return estantesLiberados;
		
		
	}
	
	private ArrayList<Libro> obtenerLibrosDeCategoria(String categoria){
		ArrayList<Libro> libros = new ArrayList<Libro>();
		for(Estanteria elem: this.todasLasEstanterias) { 
			if(categoria.equals(elem.rotulado())) {//Se comprueba que el rotulado de la estanteria es igual al solicitado
				libros.addAll(elem.getLibros());//Se agregan todos los libros de esta estanteria al ArrayList de libros
			}
		}
		
		return libros;
		
	}
	
	private void vaciarEstanterias(String categoria) {

		Iterator iterator = todasLasEstanterias.iterator();

		while(iterator.hasNext()){
			Estanteria elem = (Estanteria)iterator.next();
			if(elem.rotulado().equals(categoria)){
				elem.vaciarEstanteria();
			}
		}

	}
	
	private ArrayList<Libro> quickSortMayorAMenor(ArrayList<Libro> libros){

		if (libros.isEmpty()) { 
			return libros; // Caso base de la recursividad
		}

		ArrayList<Libro> ordenado;  // Este ArrayList solo se crea para devolver el array ordenado al final
		ArrayList<Libro> mayor = new ArrayList<Libro>(); // ArrayList de los libros con mayor ancho
		ArrayList<Libro> menor = new ArrayList<Libro>(); // ArrayList de los libros con menor ancho
		Libro libroDeComparacion = libros.get(0);  // El primer libro de la lista va a ser usado como ejemplo de comparacion
		Libro libroNuevo=null;     // Se utiliza este libro para comparar y agregarlo a las listas

		for (int i=1;i<libros.size();i++){
			libroNuevo=libros.get(i);
			if (libroNuevo.getAncho()>libroDeComparacion.getAncho()) {  // Se compara el ancho de los 2 libros y se determina a que lista va a ir
				mayor.add(libroNuevo);
			}
			else {
				menor.add(libroNuevo);
			}
		}
		mayor=quickSortMayorAMenor(mayor);  // Se hace el llamado recursido con cada una de las listas
		menor=quickSortMayorAMenor(menor);  // Para que las ArrayList se vuelvan a ordenar y asi hasta que termine el ordenamiento

		mayor.add(libroDeComparacion);          // Agrego el libro que use de ejemplo a la pila de libros con mayor ancho
		mayor.addAll(menor);     // Uno las 2 listas y asi me queda una lista completa ordenada
		ordenado = mayor;           //Esta asignacion solo se hace para no tener que devolver la lista "Menor" y que queda mas claro.

		return ordenado;
	}
	
	
	
	public HashMap<String,Integer> verLibrosCategoria(String categoria){
		
		if(!existeEstanteriaDe(categoria)) {
			throw new RuntimeException("No existe la categoria ingresada");
		}
		
		HashMap<String,Integer> diccDeCategoria = new HashMap<String,Integer>(); //Se crea un nuevo HashMap para que el JUni lo acepte.
		for(Libro elem : catDeEjemplares.keySet()) {
			if(elem.getCategoria().equals(categoria)) {
				diccDeCategoria.put(elem.getIsbn(), catDeEjemplares.obtener(elem));  //Registro el nombre y la cantidad de veces que aparecen
			}
		}
		return diccDeCategoria;
		
	}
	
	private boolean existeEstanteriaDe(String categoria) {
		//Esta funcion solo se usa para saber si existe una estanteria con ese rotulado
		
		Iterator iterator = todasLasEstanterias.iterator();
		
		while(iterator.hasNext()) {
			Estanteria elem = (Estanteria)iterator.next();
			if(elem.rotulado().equals(categoria)) {
				return true;
			}
		}
		return false;
		
	}
	
	public int espacioLibre(int estanteria) {
		if(estanteria>todasLasEstanterias.size() || !estaRotulada(estanteria)) {
			throw new RuntimeException("El numero de estanteria ingresado no es valido");
		}
		int pos=estanteria-1;
		return todasLasEstanterias.get(pos).espacioDisponible();  //Se pasa la posicion y se devulve el espacio libre
	}

	private boolean estaRotulada(int estanteria) {
		//Esta funcion solo se utiliza para saber si una estanteria ya tiene un rotulado asignado
		int pos=estanteria-1;
		
		if(todasLasEstanterias.get(pos).rotulado().equals("")) {
			return false;
		}
		return true;
		
	}

	private boolean existeElLibro(String isbn) {
		return  this.conjuntoISBN.pertenece(isbn); //Retorna si el isbn pertenece al conjunto
	}
	
	private void colocarLibroEnUnaEstanteriaDisponible(Libro libro) { 
		/*recibe un libro y lo agrega a una estanteria correspondiente, si no se 
		encuenta una estanteria tira una excepcion*/
		for(Estanteria elem: this.todasLasEstanterias) {
			//si tienen la misma categoria y hay espacio disponible se guarda el libro
			if(elem.rotulado().equals(libro.getCategoria()) && elem.espacioDisponible()>=libro.getAncho()) {
				elem.agregar(libro);
				return;
			}
		}
		throw new RuntimeException("El libro ingresado no puede ser guardado por falta de estanterias rotuladas");
		
		
	}
	private void buscarLibroYEliminarlo(Libro libro) {//Se le pasa un libro y lo elimina 
		
		
		Iterator iterator = todasLasEstanterias.iterator();
		
		while(iterator.hasNext()) { //Se recorre todas las estanterias
			
			Estanteria elem = (Estanteria)iterator.next();
			
			while(elem.pertenece(libro)) {//Mientras el libro pertenezca a la estanteria
				elem.quitar(libro); //Se van a ir quitando de la estanteria todas las copias de ese libro, con el libro en si.
			}
			
		}
		
		//Se elimina de todas las estructuras de datos
		this.catDeEjemplares.quitar(libro);
		this.conjuntoISBN.eliminar(libro.getIsbn());
		//this.conjuntoLibros.eliminar(libro);
	}
	
	@Override
	public String toString() {
		StringBuilder cadena= new StringBuilder("Biblioteca Ungs: "+"\n");
		Integer cont= 1; //Esto se usa para saber el numero de estanteria
		
		for(Estanteria elem: todasLasEstanterias) {
			cadena.append("*Estanteria nro" + cont.toString() + " : " + elem.toString() +"\n");
			cont++;
		}
		return cadena.toString();
	}
	
}
