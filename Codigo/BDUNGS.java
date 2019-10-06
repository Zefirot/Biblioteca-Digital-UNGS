package biblioteca;

import java.util.ArrayList;
import java.util.HashMap;

public class BDUNGS {
	private ArrayList<Estanteria> todasLasEstanterias; //todas las estanterias 
	private Conjunto<String> cojuntoISBN;//va a servir para contar rapido
	//private Diccionario<String, Libro> todosLosLibros; //clave isbn, significado libro
	private Diccionario<Libro, Integer> catDeEjemplares;  //Contiene la cantidad de ejemplares
	private ArrayList<Libro> todosLosLibros;  //Se guardan todos los libros de todas las estanterias en este array
	
	private int copiaDeTamanioEstanterias;
	
	
	public BDUNGS(int catEstanterias, int tamaniodeEstanterias){
		if(catEstanterias<=0) {
			throw new RuntimeException("La cantidad de estanteria tiene que ser mayor a 0");
		}
		//no es necesario controlar tamaniodeEstanterias, si no se cumple el irep de estanteria se lanza una excepcion

		this.todasLasEstanterias= new ArrayList<Estanteria>();

		this.cojuntoISBN= new Conjunto<String>();

		todosLosLibros= new ArrayList<Libro>();
		
		this.copiaDeTamanioEstanterias=tamaniodeEstanterias;
		
		this.catDeEjemplares = new Diccionario<Libro,Integer>();
		for(int i=0; i<catEstanterias;i++) {//agrego todas las estanterias a la lista
			Estanteria nueva= new Estanteria(tamaniodeEstanterias);//crea una estanteria nueva
			this.todasLasEstanterias.add(nueva);//la agrega
		}
			
			
	}
	
	public void ingresarLibro(String isbn, String categoria, String titulo,  int ancho) {

		if(!existeElLibro(isbn)) {//si no existe el libro 
			Libro libroingresado = new Libro(isbn,categoria,titulo,ancho);
			
			this.cojuntoISBN.insertar(isbn);//lo agrego al conjuntodeisbn
			
			this.catDeEjemplares.agregar(libroingresado, 1);
			
			todosLosLibros.add(libroingresado); //Agrego el libro nuevo a todos los libros
			
			colocarLibroEnUnaEstanteriaDisponible(libroingresado);

		}else {
			Libro libroingresado = obtenerLibro(isbn);
			//System.out.println(libroingresado);
			//Si el libro existe entonces lo "agrego de nuevo" y despues cambio el significado. El cual seria la cantidad de apariciones.
			catDeEjemplares.agregar(libroingresado, catDeEjemplares.obtener(libroingresado)+1);
			
			todosLosLibros.add(libroingresado); //Agrego el libro.

			colocarLibroEnUnaEstanteriaDisponible(libroingresado); 
		}
	
	}
	public void rotularEstante(String categoria, int numEstanteria) { //Si la estanteria esta vacia le asigna una rotulacion
		if(numEstanteria<=0 || numEstanteria>this.todasLasEstanterias.size()) {
			throw new RuntimeException("No existe una estanteria con ese numero");
		}
		int pos=  numEstanteria - 1;//porque se maneja arraylist
		this.todasLasEstanterias.get(pos).rotular(categoria); //rotula la estanteria
		
	}
	
	public void eliminarLibro(String isbn) {
		if(existeElLibro(isbn)) {
			Libro libro= obtenerLibro(isbn);  //O(n) el anterior tambien era O(n)
			//BuscarElLibro en CADA UNA de las Estanterias y eliminarlo
			buscarLibroYEliminarlo(libro);
		}
		else {
			throw new RuntimeException("No podes quitar un libro que no existe");
		}
		
	}
	
	private Libro obtenerLibro(String isbn) {
		for(Libro elem : todosLosLibros) {
			if(elem.getIsbn().equals(isbn)) {
				return elem;
			}
		}
		throw new RuntimeException("No se encontro el libro");
	}
	//-------------Completo-----------
	
	
	//String librosDeUnaCat(Categoria) //devuelve un string con todos los libros y cat de ejemplares. COMPLETADO
	//int espacioLibre(Estanteria)//devuelve si el espacio restante 

	//void reAgruparEstanterias();
	
	//--------------------
	
	
	public int reacomodarCategoria(String categoria) {  //CAMBIAR. LA CONCHA DE SU MADRE
		ArrayList<Libro> cajaDeLibros = ordenarDeMayorAMenor(obtenerLibrosDeCategoria(categoria));  //Obtengo el Orden de Mayor a Menor. O(n2)
		int estantesLiberados=0;
		vaciarEstanterias(categoria);  //Vacia las estanterias O(n);
		
		for(Estanteria estante : todasLasEstanterias) {
			for(int i=cajaDeLibros.size()-1; i>=0;i--) {  //lo recorro asi porque al borrar datos puedo alterar la iteracion
				//Se compara la categoria del libro con la estanteria y si hay espacio disponible para agregarlo
				if(cajaDeLibros.get(i).getCategoria().equals(estante.rotulado()) && cajaDeLibros.get(i).getAncho()<=estante.espacioDisponible()) {
					estante.agregar(cajaDeLibros.get(i));
					cajaDeLibros.remove(i); //Se agrega y quita el libro de la caja
				}
			}
		}
		
		//Despues de reacomodar todo de menor a mayor se comprueba con este For la cantidad de estantes que quedaron libres.
		for(Estanteria estante : todasLasEstanterias) {
			if(estante.rotulado().equals(categoria) && estante.espacioDisponible()==copiaDeTamanioEstanterias ) {
				estantesLiberados++;
			}
		}
		return estantesLiberados;
		
		
	}
	
	private ArrayList<Libro> obtenerLibrosDeCategoria(String categoria){
		ArrayList<Libro> libros = new ArrayList<Libro>();
		
		for(Libro elem : todosLosLibros) {
			if(elem.getCategoria().equals(categoria)) {
				libros.add(elem);
			}
		}
		return libros;
		
		
	}
	
	private void vaciarEstanterias(String categoria) {
		for(Estanteria elem : todasLasEstanterias) {
			if(elem.rotulado().equals(categoria)) {
				elem.vaciarEstanteria();
			}
		}
	}
	
	//IMPORTANTE 2. El sistema de Ordenamiento puede ser mejorado con quicksort o margesort.
	private ArrayList<Libro> ordenarDeMayorAMenor(ArrayList<Libro> libros){
		ArrayList<Libro> librosDesordenados = (ArrayList<Libro>) libros.clone();
		ArrayList<Libro> librosOrdenados = new ArrayList<Libro>();
		for(int i=librosDesordenados.size(); i>0; i--) {  //Se itera la lista para obtener todos los mayores en orden
			Libro mayor = obtenerMayor(librosDesordenados);
			librosDesordenados.remove(mayor);
			librosOrdenados.add(mayor);  //Se agrega a la lista de ordenados y se quita de la lista que se le pase
		}
		
		return librosOrdenados;
		
	}
	
	private Libro obtenerMayor(ArrayList<Libro> lista) {  //Se busca el mayor de toda la lista que recibe
		Libro menor=null;
		int tamanio = 0;
		for(Libro elem : lista) {
			if (elem.getAncho()>=tamanio) {
				menor=elem;
				tamanio=elem.getAncho();
			}
		}
		return menor;
		
	}
	
	public HashMap<String,Integer> verLibrosCategoria(String categoria){
		
		if(!existeEstanteriaDe(categoria)) {
			throw new RuntimeException("No existe la categoria ingresada");
		}
		
		HashMap<String,Integer> diccDeCategoria = new HashMap<String,Integer>(); //Se crea un nuevo HashMap para que el JUni verga lo acepte :c
		for(Libro elem : catDeEjemplares.keySet()) {
			if(elem.getCategoria().equals(categoria)) {
				diccDeCategoria.put(elem.getIsbn(), catDeEjemplares.obtener(elem));  //Registro el nombre y la cantidad de veces que aparecen
			}
		}
		return diccDeCategoria;
		
	}
	
	private boolean existeEstanteriaDe(String categoria) {
		for(Estanteria elem : todasLasEstanterias) {
			if(elem.rotulado().equals(categoria)) {
				return true;
			}
		}
		return false;
	}
	
	public int espacioLibre(int estanteria) {
		if(!estaRotulada(estanteria)) {
			throw new RuntimeException("La estanteria ingresada no esta rotulada");
		}
		int pos=estanteria-1;
		return todasLasEstanterias.get(pos).espacioDisponible();  //Paso la posicion al array y despues devuelvo el espacio libre de la estanteria
	}

	private boolean estaRotulada(int estanteria) {
		int pos=estanteria-1;
		
		if(todasLasEstanterias.get(pos).rotulado().equals("")) {
			return false;
		}
		return true;
		
	}

	private boolean existeElLibro(String isbn) {
		return  this.cojuntoISBN.pertenece(isbn); //retorna si el isbn pertenece al conjunto
	}
	
	private void colocarLibroEnUnaEstanteriaDisponible(Libro libro) { 
		/*recibe un libro y lo agrega a una estanteria correspondiente, si no se 
		encuenta una estanteria no hace nada*/
		for(Estanteria elem: this.todasLasEstanterias) {//se puede hacer en otra funcion
			if(elem.rotulado().equals(libro.getCategoria()) && elem.espacioDisponible()>=libro.getAncho()) {//si tienen la misma cat y hay espacio disponible
				//System.out.println("Entro");
				elem.agregar(libro);
				return;
			}
		}
		throw new RuntimeException("El libro ingresado no puede ser guardado por falta de estanterias rotuladas");
		
		
	}
	private void buscarLibroYEliminarlo(Libro libro) {//le pasas un libro y lo elimina 
		for(Estanteria elem: this.todasLasEstanterias) {//tiene que recorrer todas las estanterias
			if(elem.pertenece(libro)) {//si el libro aparece en una
				elem.quitar(libro);//quito el libro de la estanteria
				
				catDeEjemplares.quitar(libro);
				todosLosLibros.remove(libro);  //EXPERIMENTAL.
				
				return;//termina la funcion
				//cuando tenga cat de ejemplares, hay que verificar la cat para ver si lo eliminamos del conj o no
			}
		}
	}
	
	@Override
	public String toString() {//hacer piola, ahora solo imprimo solo las estanterias
		StringBuilder cadena= new StringBuilder("Biblioteca Ungs: "+"\n");
		Integer cont= 1; //para saber que estanteria
		for(Estanteria elem: this.todasLasEstanterias) {
			cadena.append("*Estanteria nro" + cont.toString() + " : " + elem.toString() +"\n");
			cont++;
			}
		return cadena.toString();
	}
	
}
