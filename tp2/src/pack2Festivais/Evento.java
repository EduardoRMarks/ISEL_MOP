package pack2Festivais;

public abstract class Evento {

    private String nome;

    //Implementação do construtor
    public Evento(String nome){
        //validação do nome
        if(nome == null || nome.length() == 0){
            throw new IllegalArgumentException("O nome tem que ter pelo menos um caracter");
        }
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    /*Implementação de métodos abstratos, ou seja, vão buscar informação a uma classe que
    * desta herda, o que significa que vai buscar informação a um método com o mesmo nome*/
    public abstract int getNumBilhetes();
    public abstract String[] getArtistas();
    public abstract int numActuacoes(String artista);

    //implementação do metodo toString
    public String toString(){
        return getNome() + " com " + getNumBilhetes() + " bilhetes e com os artistas " + java.util.Arrays.toString(getArtistas());
    }

}
