package biblioteca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Prueba {

	public static void main(String[] args) {
		BDUNGS bd = new BDUNGS(4, 10);
		bd.rotularEstante("Computacion", 1);
		bd.rotularEstante("Matematica", 2);
		bd.rotularEstante("Computacion", 3);
		bd.rotularEstante("Matematica", 4);
		bd.ingresarLibro("9789684443457", "Computacion", "Estructuras de datos", 4);
		bd.ingresarLibro("9788415552222", "Computacion", "Estructuras de datos en Java", 5);
		bd.ingresarLibro("9789684443457", "Computacion", "Estructuras de datos", 4);
		
		bd.ingresarLibro("9389557783457", "Matematica", "Analisis de Funciones", 4);
		bd.ingresarLibro("5421321846213", "Matematica", "Funciones Trigonometricas Inversas", 10);
		bd.ingresarLibro("5156465314456", "Matematica", "Las layes de Don Pedro", 6);
		
		
		bd.ingresarLibro("9789684443457", "Computacion", "Estructuras de datos", 4);
		bd.reacomodarCategoria("Computacion");
		
		
		System.out.println(bd.toString());
		
 
		
		
		
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
