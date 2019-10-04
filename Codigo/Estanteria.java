import java.util.HashMap;

public class Estanteria {

    private int espacio;
    private String rotulado;
    
    //private static int todosLosLibros;
    private int librosEstanteria; 

    private HashMap<String,Tupla<String,Integer>> libros = new HashMap<String,Tupla<String,Integer>>();



    public Estanteria(int espacioPredetermiando){
        this.espacio=espacioPredetermiando;
    }


    public void agregar(String clave, String nombre, int cm){
        if(!pertenece(clave)){
            Tupla<String,Integer> libro = new Tupla<String,Integer>(nombre,1);
            libros.put(clave,libro);
            espacio=espacio-cm;
        }else{
            Tupla<String,Integer> libro = new Tupla<String,Integer>(nombre,libros.get(clave).getNumero()+1);
            libros.put(clave,libro);
            
            espacio=espacio-cm;
        }
        
    }
    
    public void agregar(String ibns, String rotulado, String nombre, int cm){
        this.rotulado = rotulado;
        Tupla<String, Integer> libro = new Tupla<String,Integer>(nombre,1);
        libros.put(ibns,libro);
        espacio = espacio-cm;
        librosEstanteria++;
    }


    private boolean pertenece(String clave){
        for(String key : libros.keySet()){
            if(key.equals(clave)){
                return true;
            }
        }
        return false;
    }

    public int espacioLibre(){
        return espacio;
    }
    public String getRotulado(){
        return rotulado;
    }


}