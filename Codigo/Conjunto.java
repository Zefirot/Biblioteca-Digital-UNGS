package biblioteca;

import java.util.ArrayList;
import java.util.Iterator;

public class Conjunto<T> {
	private ArrayList<T> conj;
	private int indice;

	// ¿¿ SETTERS Y GETTER ??

	Conjunto() {
		this.conj = new ArrayList<T>();
		this.indice = 0;
	}

	/**
	 * O(n) por el Orden del método pertenece.
	 * @param elem
	 */
	void insertar(T elem) {
		if (!this.pertenece(elem))
			this.conj.add(elem);
	}

	/**
	 * O(n)
	 * @param elem
	 * @return
	 */
	boolean pertenece(T elem) {
		return this.conj.contains(elem);
	}

	/**
	 * O(n)
	 * @param elem
	 */
	void eliminar(T elem) {
		this.conj.remove(elem);
	}

	/**
	 * O(1) porque es ArrayList.
	 * @return
	 */
	int tamanio() {
		return this.conj.size();
	}

	T dameUno() {
		if (indice >= this.tamanio()) {
			indice = 0;
		}
		return this.conj.get(indice++);
	}

	@Override
	public String toString() {
		return "Conjunto [" + conj + "]";
	}

	// /////////// UNION & INTERSECCION �Destructivos!
	
	void union(Conjunto<T> otroConjunto) {
		for(int i = 0; i < otroConjunto.tamanio(); i++) {
			this.insertar(otroConjunto.dameUno());
		}	
	}

	void interseccion(Conjunto<T> otroConjunto) {
		Iterator<T> it = this.conj.iterator();
		while (it.hasNext()) {
			if ( ! otroConjunto.pertenece(it.next())) {
				it.remove();
			}
		}		
	}
	
	void interseccionConWhile(Conjunto<T> otroConjunto) {
		int pos=0;
		while( pos < this.tamanio() ) { // n
			T aux = this.conj.get(pos); // 1  -> ArrayList
			if ( ! otroConjunto.pertenece( aux )) { // m
				this.eliminar(aux); // n
			} else {
				pos++; // 1
			}
		}
	}
}