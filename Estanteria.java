import java.util.HashMap;

public class Estanteria {

    private int espacio;
    private String rotulado;
    private static int todosLosLibros;
    private int librosEstanteria; 
    //Libros: Codigo, clase de estanteria, nombres, cm.
    private HashMap<String,Tupla<String,Integer>> libros = new HashMap<String,Tupla<String,Integer>>();



    public Estanteria(int espacioPredetermiando, String rotulado){
        this.espacio=espacioPredetermiando;
        this.rotulado=rotulado;
        Tupla<String,String> li = new Tupla<String,String>("hola","10");
    }


    public void agregar(String clave, String nombre, int cm){
        Tupla<String,Integer> libro = new Tupla<String,Integer>(nombre,libros.getOrDefault(libros.get(nombre).getNumero()+1, 1));
        libros.put(clave,libro);
        espacio=espacio-cm;
    }


    public int getEspacio(){
        return espacio;
    }
    public String getRotulado(){
        return rotulado;
    }


}