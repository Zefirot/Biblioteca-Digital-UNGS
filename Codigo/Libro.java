package biblioteca;

public class Libro {
	private String isbn; 
	private String nombre;
	private String categoria;
	private int ancho;
	
	
	Libro(String isbn, String categoria, String nombre, int ancho ){
		if(ancho>0) {
			this.ancho= ancho;
		}
		else {
			throw new RuntimeException("Ingrese un ancho valido");
		}
		if(isbn.length()>0) {
			this.isbn= isbn;
		}
		else {
			throw new RuntimeException("El ISBN no puede estar vacio");
		}
		
		if(categoria.length()>0) {
			this.categoria= categoria;
		}
		else {
			throw new RuntimeException("La categoria no puede estar vacia");
		}
		if(nombre.length()>0) {
			this.nombre= nombre;
		}
		else {
			throw new RuntimeException("El nombre no puede estar vacio");
		}

	}
	
	public String getIsbn() {
		return this.isbn;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getCategoria() {
		return this.categoria;
	}
	
	public int getAncho() {
		return this.ancho;
	}
	
	/* Ignora esto xdxd
	 * boolean equals(Libro libro){//tengo que hacerlo para determinar cuando un libro es igual a otro
	 
		boolean ret= false;
		if(this.isbn.equals(libro.getIsbn())) {//si tienen el mismo isbn
			ret= ret||true;//va a cambiar a true, sino va seguir en false
		}
		if(this.nombre.equals(libro.getNombre())) {//si tienen el mismo nombre
			ret= ret&&true;//si tiene el mismo isbn y nombre va a cambiar a true, sino va seguir en false
		}
		if(this.getCategoria().equals(libro.getCategoria())) {//si tienen la misma cat
			ret= ret&&true;// y si tenia el mismo nombre, va a valer true. sino arrastra el false
		}
		if(this.ancho==libro.getAncho()) {//si tienen el mismo ancho
			ret= ret&&true;//y tiene mismo name y cat, va a ser true, sino va a ser false
		}
		return ret;//retorna su  valoor
	}*/
	@Override
	public String toString() {
		String cadena= "ISBN: " + this.isbn + ", Categoria: " + this.categoria + ", Nombre: " + this.nombre;
		return cadena;
	}
	
}
