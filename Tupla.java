public class Tupla<T,S> {

    private T elemT;
    private S elemS;

    public Tupla(T elemT,S elemS){
        this.elemT=elemT;
        this.elemS=elemS;
    }


	public T getValor(){
        return elemT;
    }
    public S getNumero(){
        return elemS;
    }

    
}