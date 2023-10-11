package pack2Festivais;

public class Espetaculo extends Evento {

    //Numero maximo de artistas
    private static int MAXARTISTAS = 10;
    //artistas: String
    private String[] artistas = new String[MAXARTISTAS];

    //nArtistas: int
    //Tera sempre o n�mero de lugares preenchidos
    private int nArtistas = 0;
    //numBilhetes: int
    private int numBilhetes;
    //localidade: String
    private String localidade;

    //Implementação do construtor
    public Espetaculo(String nome, String localidade, int numBilhetes){
        //validação do nome é feita na classe abstrata Evento
        super(nome);
        //validação da String localidadae
        if(localidade == null || localidade.length() == 0){
            throw new IllegalArgumentException("A localidade tem que ter pelo menos um caracter");
        }
        this.localidade = localidade;
        //validação do numero de bilhetes
        if(numBilhetes < 0){
            throw new IllegalArgumentException("O numero de bilhetes não pode ser negativo");
        }
        this.numBilhetes = numBilhetes;
    }

    //metodo que devolve o numero de bilhetes
    public int getNumBilhetes(){
        return this.numBilhetes;
    }

    //metodo que devolve o numero de atuações de x artista
    public int numActuacoes(String artista){
        //se o array estiver vazio devolve retorna 0
        if(nArtistas == 0){
            return 0;
        }
        /*caso contrario vamos correr o array de artistas e comparar os artistas um a um
        * até encontrarmos o artista introduzido, se encontrar retorna 1*/
        for(int i = 0; i < nArtistas; i++){
            if(artista.equals(artistas[i])){
                return 1;
            }
        }
        //se o artista nao for encontrado no array, retornamos 0;
        return 0;
    }

    //metodo que adiciona um artista num espetaculo caso ainda não esteja
    public boolean addArtista(String artista){
        /*so podemos adicionar o artista se o nome não for nulo e se
        * o tal artista ainda não fizer parte do espetaculo em questão*/
        if(artista != null && numActuacoes(artista) == 0){
            /*tambem so podemos adicioar o artista se o numero de artistas
            * total do evento for menor que o numero maximo*/
            if(nArtistas < MAXARTISTAS){
                //se o artista for adicionado "mete-se" o artista na ultima posição do array
                artistas[nArtistas] = artista;
                //incrementa-se o numero de artistas
                nArtistas++;
                //e retorna-se true
                return true;
            }
            //se numero de artistas for igual retorna-se false
            else return false;
        }
        /*se o nome do artista for nulo ou se já existir no array do evento
        * não adiciona e retorna false*/
        else return false;
    }

    //metodo que devolve um array de artistas registados de x evento
    public String[] getArtistas(){
        //se o array de artistas estiver vazio, retorna null
        if(nArtistas == 0){
            return null;
        }
        //criação de um array de strings com o tamanho dos artistas registados
        String[] artistasRegis = new String[nArtistas];
        //ciclo for para a introdução dos artistas registados no array
        for(int i = 0; i < nArtistas; i++){
            //copia-se cada artista para o novo array
            artistasRegis[i] = artistas[i];
        }
        //no fim do ciclo for retorna-se o array de strings com os nomes dos artistas
        return artistasRegis;
    }

    public String toString(){
        /*super.toString() vai buscar o toString criada na classe abstrata Evento e adicionamos
        * ao toStrign a localidade onde o Evento vai decorrer*/
        return super.toString() + " em " + localidade;
    }

    public static void main(String[] args){
        // constructor e toString, com o exemplo do enunciado
        Espetaculo e1 = new Espetaculo("NoitadaAzul","Lisboa",2000);
        e1.addArtista("Joana");
        e1.addArtista("Artur");
        System.out.println("Exemplo do Enunciado -> " + e1);

        //segundo exemplo
        Espetaculo e2 = new Espetaculo("Segundo exemplo","Lisboa",8600);
        e2.addArtista("The National");
        e2.addArtista("Liam Gallagher");
        System.out.println("Outro espetáculo -> " + e2);

        System.out.println("\nNúmero de atuações de um artista");
        System.out.println("    Nº de atuações de The National -> " + e2.numActuacoes("The National"));
        System.out.println("    Nº de atuações de Liam Gallagher -> " + e2.numActuacoes("Liam Gallagher"));

        System.out.println("\nAdição de mais um artista: " + e2.addArtista("The Black Mamba"));
        System.out.println("Tentativa de adição de um artista que já existe: " + e2.addArtista("Liam Gallagher"));

        //testes com erros
        //erro quando o nome do espetáculo não é válido
        try { Espetaculo erro1 = new Espetaculo("","Lisboa",8600); }
        catch(IllegalArgumentException erro1) { erro1.printStackTrace(); }

        //erro quando a localidade do espetáculo não é válida
        try { Espetaculo erro2 = new Espetaculo("Segundo exemplo","",8600); }
        catch (IllegalArgumentException erro2) { erro2.printStackTrace(); }

        //erro quando o número de bilhetes do espetáculo não é válido
        try { Espetaculo erro3 = new Espetaculo("Segundo exemplo","Lisboa",-8600); }
        catch(IllegalArgumentException erro3) { erro3.printStackTrace(); }

    }
}
