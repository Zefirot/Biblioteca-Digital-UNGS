package biblioteca;

public class Libro {
	private String isbn; 
	private String nombre;
	private String categoria;
	private int ancho;
	
	
	Libro(String isbn, String categoria, String nombre, int ancho ){
		//Se controla que el libro cumpla con todos los requisitos
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
	
	@Override
	public String toString() {
		String cadena= "ISBN: " + this.isbn + ", Categoria: " + this.categoria + ", Nombre: " + this.nombre;
		return cadena;
	}
	
}
