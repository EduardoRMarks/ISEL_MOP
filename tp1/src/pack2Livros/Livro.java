package pack2Livros;

import java.util.Arrays;

/**
 * Classe que deverá suportar um livro
 */
public class Livro {

    // Título do livro
    private String titulo;

    // número de páginas
    private int numPaginas;

    // preço do livro
    private float preco;

    // array de autores, este array não deve ter nulls
    private String[] autores;

    /**
     * Deve criar um novo livro com os dados recebidos. O título não deve ser
     * null nem vazio. O número de páginas não pode ser menor que 1. O preço não
     * pode ser negativo. O array de autores não deve conter nem nulls e deve
     * conter pelo menos um autor válido. Não pode haver repetições dos nomes
     * dos autores, considera-se os nomes sem os espaços extra (ver
     * removeExtraSpaces). Este método deve utilizar os métodos auxiliares
     * existentes. Em caso de nome inválido deve lançar uma excepção de
     * IllegalArgumentException com a indicação do erro ocorrido
     */
    public Livro(String titulo, int numPaginas, float preco, String[] autores) {

        // título
        if (titulo == null || titulo.length() == 0)
            throw new IllegalArgumentException("O titulo tem de ter pelo menos um caracter");
        //se recebido for válido, atribui o titulo ao livro atual
        this.titulo = titulo;

        //nº de paginas não pode ser menor ou igual a 1
        if(numPaginas <= 1)
            throw new IllegalArgumentException("O lívro tem que ter mais do que uma página");
        this.numPaginas = numPaginas;

        //preço tem que ser maior ou igual a zero
        if(preco < 0)
            throw new IllegalArgumentException("O preço não pode ser negativo");
        this.preco = preco;

        //o array de autores tem que ter pelo menos um autor
        if (autores == null || autores.length == 0)
            throw new IllegalArgumentException("O array de autores deve ter pelo menos um autor");
        /*os nomes dos autores têm que ser válidos, ou seja, só podem
        * conter letras e espaços*/
        if (validarNomes(autores) == false)
            throw new IllegalArgumentException("Um ou mais nomes do array de autores não é válido");
        //remove os espaços entre os nomes
        for (int i = 0; i < autores.length; i++){
            autores[i] = removeExtraSpaces(autores[i]);
        }
        //não podem haver autores repetidos no array de autores
        if(haRepeticoes(autores))
            throw new IllegalArgumentException("O array de autores contem autores repetidos");
        this.autores = autores;
    }

    /**
     * Devolve o título do livro
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * Devolve o número de páginas do livro
     */
    public int getNumPaginas() {
        return this.numPaginas;
    }

    /**
     * Devolve o preço do livro
     */
    public float getPreco() {
        return this.preco;
    }

    /**
     * Devolve uma cópia do array de autores do livro
     */
    public String[] getAutores() {
        //.clone faz uma copia dos autores
        return autores.clone();
    }

    /**
     * Deve devolver true se o array conter apenas nomes válidos. Um nome é
     * válido se conter pelo menos uma letra (Character.isLetter) e só conter
     * letras e espaços (Character.isWhitespace). Deve chamar validarNome.
     */
    public static boolean validarNomes(String[] nomes) {
        //ciclo for para validar se os nomes são válidos
        for(int i = 0; i < nomes.length; i++){
            //se o nome não for válido retorna false, caso contrário avança para o nome seguinte
            if(!validarNome(nomes[i])){
                return false;
            }
        }
        //se todos os nomes forem válidos retorna true
        return true;
    }

    /**
     * Um nome válido se não for null e não conter pelo menos uma letra
     * (Character.isLetter) e só conter letras e espaços
     * (Character.isWhitespace)
     */
    public static boolean validarNome(String nome) {
        /*no fim da validação, a variavel nLetra tem de ser pelo menos 1
        * para o nome ser considerado válido*/
        int nLetra = 0;

        for(int i = 0; i < nome.length(); i++){
            //variavel auxiliar para guardar o caracter na posição i
            char aux = nome.charAt(i);
            //se o caracter não for uma letra nem um espeço retorna false
            if(!Character.isLetter(aux) && !Character.isWhitespace(aux)){
                return false;
            }
            //se o caracter for uma letra, incremente nLetra
            if(Character.isLetter(aux)){
                nLetra++;
            }
        }
        if(nLetra >= 1){
            return true;
        }
        else return false;
    }

    /**
     * Recebe um nome já previamente validado, ou seja só com letras ou espaços.
     * Deve devolver o mesmo nome mas sem espaços (utilizar trim e
     * Character.isWhitespace) no início nem no fim e só com um espaço ' ' entre
     * cada nome. Deve utilizar um StringBuilder para ir contendo o nome já
     * corrigido
     */
    public static String removeExtraSpaces(String nome) {
        //.trim() elimina espaços no inicio e no final de uma string, mas não elimina espaços no meio da string
        nome = nome.trim();

        //criação da StringBuilder
        StringBuilder sbName = new StringBuilder(nome);
        for(int i = 0; i < sbName.length(); i++){
            if(Character.isWhitespace(sbName.charAt(i)) && Character.isWhitespace(sbName.charAt(i+1))){
                /*se a posição onde estivermos for um espaço bem como a posição seguinte tambem
                * for um espaço, então vamos retirar a posição onde estamos e vamos tirar uma
                * unidade a i, pois pode haver mais do que um espaço, o que significa que se
                * retirarmos o char seguinte em vez do char corrente, uma string com 3 espaços
                * iria passar a ter 2 espaços em vez de 1*/
                sbName.deleteCharAt(i);
                i--;
            }
        }
        return sbName.toString();
    }

    /**
     * Método que verifica se há elementos repetidos. O array recebido não
     * contém nulls.
     */
    public static boolean haRepeticoes(String[] elems) {
        /*Vamos comparar um elemento de uma string com o resto da string a partir do elemento
        * seguinte, ou seja, numa string (Diogo, Eduardo, Marques, Romba) vamos comparar Diogo
        * com os 3 nomes a seguir, Eduardo com os 2 nomes a seguir e assim sucessivamente*/
        for(int i = 0; i < elems.length; i++){
            for(int j = i + 1; j < elems.length; j++){
                //se dois nomes forem iguais retorna true
                if(elems[i].equals(elems[j])){
                    return true;
                }
            }
        }
        //se não houverem nomes iguais retorna false
        return false;
    }

    /**
     * Devolve true se o autor recebido existe como autor do livro. O nome
     * recebido não contém espaços extra.
     */
    public boolean contemAutor(String autorNome) {
        //se o nome recebido for null devolve false
        if(autorNome == null){
            return false;
        }
        /*À semelhança do que foi feito no metodo haRepeticoes vamos comparar o nome recebido com
        * todos os autores do livro*/
        for(int i = 0; i < autores.length; i++){
            //se o nome recebido fizer parte do array autores então retorna true
            if(autorNome.equals(autores[i])){
                return true;
            }
        }
        //se não fizer parte retorna false
        return false;
    }

    /**
     * Devolve uma string com a informação do livro (ver outputs desejados)
     */
    public String toString() {
        //Chamamos os metodos get para recebermos as informações desejadas
        return getTitulo() + ", " + getNumPaginas() + "p " + getPreco() + "€ " + Arrays.toString(getAutores());
    }

    /**
     * Deve mostrar na consola a informação do livro precedida do prefixo
     */
    public void print(String prefix) {
        //efetua o print da string recebida mais as informações do livro recorrendo ao método toString
        System.out.println(prefix + toString());
    }

    /**
     * O Livro recebido é igual se tiver o mesmo título que o título do livro
     * corrente
     */
    public boolean equals(Livro l) {
        //se l for null então retorna false
        if(l == null){
            return false;
        }
        //se o titulo enviado for igual ao livro atual retorna true
        if(l.getTitulo().equals(getTitulo())){
            return true;
        }
        //caso contrario retorna false
        return false;
    }

    /**
     * main
     */
    public static void main(String[] args) {

        // constructor e toString
        Livro l = new Livro("Viagem aos Himalaias", 340, 12.3f, new String[]{"João Mendonça", "Mário Andrade"});
        System.out.println("Livro -> " + l);
        l.print("");
        l.print("-> ");
        System.out.println();

        // contém autor
        String autorNome = "Mário Andrade";
        System.out.println("Livro com o autor " + autorNome + "? -> " + l.contemAutor(autorNome));
        autorNome = "Mário Zambujal";
        System.out.println("Livro com o autor " + autorNome + "? -> " + l.contemAutor(autorNome));
        System.out.println();

        // equals
        System.out.println("Livro: " + l);
        System.out.println("equals Livro: " + l);
        System.out.println(" -> " + l.equals(l));

        Livro l2 = new Livro("Viagem aos Himalaias", 100, 10.3f, new String[]{"Vitor Záspara"});
        System.out.println("Livro: " + l);
        System.out.println("equals Livro: " + l2);
        System.out.println(" -> " + l.equals(l2));
        System.out.println();

        // testes que dão excepção - mostra-se a excepção

        // livro lx1
        System.out.println("Livro lx1: ");
        try {
            Livro lx1 = new Livro("Viagem aos Himalaias", -1, 12.3f, new String[]{"João Mendonça", "Mário Andrade"});
            System.out.println("Livro lx1: " + lx1);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        System.out.println();

        // livro lx2
        System.out.println("Livro lx2: ");
        try {
            Livro lx2 = new Livro("Viagem aos Himalaias", 200, -12.3f,
                    new String[]{"João Mendonça", "Mário Andrade"});
            System.out.println("Livro lx2: " + lx2);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        System.out.println();

        // livro lx3
        System.out.println("Livro lx3: ");
        try {
            Livro lx3 = new Livro(null, 200, -12.3f, new String[]{"João Mendonça", "Mário Andrade"});
            System.out.println("Livro lx3: " + lx3);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        System.out.println();

        // livro lx4
        System.out.println("Livro lx4: ");
        try {
            Livro lx4 = new Livro("Viagem aos Himalaias", 200, 12.3f,
                    new String[]{"João Mendonça", "Mário Andrade", "João Mendonça"});
            System.out.println("Livro lx4: " + lx4);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }
}

