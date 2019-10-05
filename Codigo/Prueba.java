package biblioteca;

import java.util.ArrayList;

public class Prueba {

	public static void main(String[] args) {
		BDUNGS bd = new BDUNGS(4, 10);
		bd.rotularEstante("Computacion", 1);
		bd.rotularEstante("Matematica", 2);
		bd.ingresarLibro("9789684443457", "Computacion", "Estructuras de datos", 5);
		bd.ingresarLibro("9789684443457", "Computacion", "Estructuras de datos", 5);
		bd.ingresarLibro("9788415552222", "Computacion", "Estructuras de datos en Java", 7);
		bd.ingresarLibro("9389557783457", "Matematica", "Analisis de Funciones", 4);
		//bd.eliminarLibro("9389557783457");
		//System.out.println(bd);
		
		System.out.println(bd.librosDeUnaCat("Computacion"));
 

		//bd.eliminarLibro("9389557783457");
		//bd.rotularEstante("An�lisis Matem�tico", 2);
		//System.out.println(bd);

		//Prueba de que si se agrega dos veces un mismo elem, se elimina el primero
		//esto va a pasar si hay dos ejemplares en una misma estanteria
		/*Libro libro= new Libro("9789684443457", "Computacion", "Estructuras de datos", 5);
		


		Estanteria est= new Estanteria(20);
		est.rotular("Computacion");
		est.agregar(libro);
		est.agregar(libro);
		System.out.println(est.toString());
		est.quitar(libro);
		System.out.println(est.toString());*/
	}

}
