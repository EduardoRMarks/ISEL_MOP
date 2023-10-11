package pack3Coleccoes;

import pack2Livros.Livro;

import java.util.Arrays;


/**
 * Classe Coleccao, deve conter a descrição de uma colecção, com título, os seus
 * livros, colecções e editores
 */
public class Coleccao {
    // número máximo de obras de uma colecção
    private static int MAXOBRAS = 20;

    // prefixo usual
    public static final String GENERALPREFIX = "  ";

    // título da colecção
    private String titulo;

    // Array de livros, em que estas encontram-se sempre nos menores índices e
    // pela ordem de registo
    private Livro[] livros = new Livro[MAXOBRAS];


    // deverá conter sempre o número de livros na colecção
    private int numLivros = 0;

    // array de colecções, estas devem ocupar sempre os menores índices
    private Coleccao[] coleccoes = new Coleccao[MAXOBRAS];

    // deverá conter sempre o número de colecções dentro da colecção
    private int numColeccoes = 0;

    // Editores, tem as mesmas condicionantes que array de autores na classe
    // livro
    private String[] editores;

    /**
     * Construtor; o título tem de ter pelo menos um caracter que não seja um
     * espaço (Character.isWhitespace); o array de editores devem ser pelo menos
     * um e têm as mesmas restrições que os autores dos livros;
     */
    public Coleccao(String titulo, String[] editores) {
        // titulo
        if (titulo == null || titulo.length() == 0)
            throw new IllegalArgumentException("O titulo tem de ter pelo menos um caracter");
        this.titulo = titulo;

        //ciclo for para ver se há pelo menos um caracter que não seja um espaço
        for(int i = 0; i < titulo.length(); i++){
            if(!Character.isWhitespace(titulo.charAt(i))){
                /*se encontrar um caracter que não seja espaço usa-se o break
                 * pois não precisa de continuar a procurar*/
                break;
            }
            //se estivermos no ultimo ciclo e ainda não houver uma letra então é lançada a excepção
            else if(i == titulo.length()-1){
                throw new IllegalArgumentException("O titulo tem de ter pelo menos um caracter que não seja um espaço");
            }
        }
        this.titulo = titulo;

        //o array de autores tem que ter pelo menos um autor
        if (editores == null || editores.length == 0)
            throw new IllegalArgumentException("O array de autores deve ter pelo menos um autor");
        /*os nomes dos editores têm que ser válidos, ou seja, só podem
         * conter letras e espaços*/
        if (Livro.validarNomes(editores) == false)
            throw new IllegalArgumentException("Um ou mais nomes do array de autores não é válido");
        //remove os espaços entre os nomes
        for (int i = 0; i < editores.length; i++){
            editores[i] = Livro.removeExtraSpaces(editores[i]);
        }
        //não podem haver editores repetidos no array de editores
        if(Livro.haRepeticoes(editores))
            throw new IllegalArgumentException("O array de autores contem autores repetidos");
        this.editores = editores;
    }

    /**
     *
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * Obtem o número total de páginas da colecção, páginas dos livros e das
     * colecções
     */
    public int getNumPaginas() {
        int nPaginas = 0;
        /*ciclo for para fazer a contagem do numero de paginas, somando sucessivamente
         * o numero de paginas tendo em conta o metodo da classe Livro (getNumPaginas)*/
        for(int i = 0; i < numLivros; i++){
            nPaginas += livros[i].getNumPaginas();
        }
        //depois fazemos o mesmo para a coleções
        for(int j = 0; j < numColeccoes; j++){
            nPaginas += coleccoes[j].getNumPaginas();
        }
        return nPaginas;
    }

    /**
     * As colecções com mais de 5000 páginas nos seus livros directos têm um
     * desconto de 20% nesses livros. As colecções em que o somatório de páginas
     * das suas subcolecções directas seja igual ou superior ao quádruplo do nº
     * de páginas da sua subcolecção directa com mais páginas deverão aplicar um
     * desconto de 10% sobre os preços das suas subcolecções
     */
    public float getPreco() {
        //pTotal é float pois o preco que é declarado é float
        float pTotal = 0;
        //variavel auxiliar para contar as paginas
        int pagImediatas = 0;
        /*começamos por ver contar as paginas da coleção, se tiver mais de 5000 paginas
        * é aplicado um desconto de 20% nesses livros*/
        for(int i = 0; i < numLivros; i++) {
            //adicionamos o numero de paginais ao total da coleção
            pagImediatas += livros[i].getNumPaginas();
            //adicionamos o preço do livro ao total da coleção
            pTotal += livros[i].getPreco();
        }
        //se o numero de paginas for maior que 5000
        if(pagImediatas > 5000){
            pTotal = pTotal * 0.8f;
        }
        //variaveis para contar as paginas da subcoleção e para guardar as paginas do livro maior
        int pagSubCol = 0, livroMaior = 0;
        //varialvel para o preço das subcoleçoes
        float pSubCol = 0;

        for(int j = 0; j < numColeccoes; j++) {
            //adicionamos o numero de paginais ao total da coleção
            pagSubCol += coleccoes[j].getNumPaginas();
            //adicionamos o preço do livro ao total da coleção
            pSubCol += coleccoes[j].getPreco();
            /*se o numero de paginas for maior do que qualquer outro livro então muda-se
            * a variavel livroMaior para o novo valor*/
            if(coleccoes[j].getNumPaginas() > livroMaior){
                livroMaior = coleccoes[j].getNumPaginas();
            }
        }
        //se o numero de paginas da coleção for 4 vezes maior que o maior livro o preço tem 10% de desconto
        if (pagSubCol >= livroMaior * 4) {
            pSubCol = pSubCol * 0.9f;
        }
        //no fim somamos o preço dos livros mais das subcoleçoes
        pTotal += pSubCol;
        return pTotal;
    }

    /**
     * Adiciona um livro à colecção se puder e este não seja null e a colecção
     * não ficar com livros iguais ao nível imediato da colecção. Deve utilzar o
     * método getIndexOfLivro e getIndexOfColeccao
     */
    public boolean addLivro(Livro livro) {
        //Começamos por ver se o livro é válido e se não existe já na coleção
        if((livro != null) && (getIndexOfLivro((livro.getTitulo())) == -1)){
            /*se o livro entrar no if coloca-se na primeira posição vazia da
             * coleção e incrementa-se a variável de contagem do numero de livros*/

            //se o numero de livros na coleção já está no seu máximo então não podemos adcionar
            if(numLivros == MAXOBRAS){
                return false;
            }
            //caso contrario podemos adicionar
            else {
                livros[numLivros] = livro;
                numLivros++;
                //retorna-se true pois foi adicionado o livro à coleção
                return true;
            }
        }
        //retorna false se o livro não for adicionado à coleção
        return false;
    }

    /**
     * Adiciona uma colecção à colecção se puder, esta não seja null e a
     * colecção não ficar com obras imediatas com títulos repetidos. Deve
     * utilizar o método getIndexOfLivro e getIndexOfColeccao
     */
    public boolean addColeccao(Coleccao col) {
        //Começamos por ver se a coleção é válido e se não existe já na subcoleção
        if((col != null) && (getIndexOfColeccao((col.getTitulo())) == -1)){
            /*se a coleçao entrar no if coloca-se na primeira posição vazia da
             * subcoleção e incrementa-se a variável de contagem do numero de coleção*/

            //se o numero de coleções já está no seu máximo então não podemos adicionar
            if(numColeccoes == MAXOBRAS){
                return false;
            }
            //caso contrario podemos adicionar
            else {
                coleccoes[numColeccoes] = col;
                numColeccoes++;
                //retorna-se true pois foi adicionado a coleção
                return true;
            }
        }
        //retorna false se o livro não for adicionado à coleção
        return false;
    }

    /**
     * Devolve o index no array de livros onde estiver o livro com o nome
     * pretendido. Devolve -1 caso não o encontre
     */
    private int getIndexOfLivro(String titulo) {
        /*se o livro na posição 0 for null significa que não há livros na
         * coleção, ou seja, não vale a pena procura o index do livro*/
        if(livros[0] == null){
            return -1;
        }
        //ciclo for para encontrar a posição do livro na coleção
        for(int i = 0; i < numLivros; i++){
            //se encontrar o livro na coleção retorna a posição i do livro
            if((livros[i].getTitulo()).equals(titulo)){
                return i;
            }
        }
        //caso não encontre o livro retorna -1
        return -1;
    }

    /**
     * Devolve o index no array de colecções onde estiver a colecção com o nome
     * pretendido. Devolve -1 caso não o encontre
     */
    private int getIndexOfColeccao(String titulo) {
        /*se a coleção na posição 0 for null significa que não há a coleção na
         * coleção de coleções, ou seja, não vale a pena procura o index da coleção*/
        if(coleccoes[0] == null){
            return -1;
        }
        //ciclo for para encontrar a posição da coleção na coleção de coleções
        for(int i = 0; i < numColeccoes; i++){
            //se encontrar a coleção na coleção de coleções retorna a posição i da coleção
            if((coleccoes[i].getTitulo()).equals(titulo)){
                return i;
            }
        }
        //caso não encontre a coleção retorna -1
        return -1;
    }

    /**
     * Remove do array o livro com o título igual ao título recebido. Devolve o
     * livro removido ou null caso não tenha encontrado o livro. Deve-se
     * utilizar o método getIndexOfLivro. Recorda-se que os livros devem ocupar
     * sempre os menores índices, ou seja, não pode haver nulls entre os livros
     */
    public Livro remLivro(String titulo) {
        //variavel para guardar a posição do livro a remover
        int posicao = getIndexOfLivro(titulo);
        if(posicao == -1){
            return null;
        }
        //variavel para retornar o livro que foi removido
        Livro livroRemovido = livros[posicao];
        /*ciclo for para mudar a posição dos livros depois do livro que foi removido uma
         * posição para a esquerda, para a coleção não ficar com um lugar vazio*/
        for(int i = posicao; i<numLivros; i++){
            /*copia o livro que esta na posição seguinte até ao penultimo livro, pois
             * a ultima posição ficara como null se a coleção estivesse cheia*/
            if(i<MAXOBRAS-1) {
                livros[i] = livros[i + 1];
            }
            else livros[i] = null;
        }
        //remove uma unidade no numero de livros
        numLivros--;

        return livroRemovido;
    }

    /**
     * Remove do array de colecções a colecção com o título igual ao título
     * recebido. Devolve a colecção removida ou null caso não tenha encontrado.
     * Deve-se utilizar o método getIndexOfColeccao. Recorda-se que as colecções
     * devem ocupar sempre os menores índices, ou seja, não pode haver nulls
     * entre elas
     */
    public Coleccao remColeccao(String titulo) {
        //variavel para guardar a posição da coleção a remover
        int posicao = getIndexOfColeccao(titulo);
        if(posicao == -1){
            return null;
        }
        //variavel para retornar o livro que foi removido
        Coleccao colecaoRemovida = coleccoes[posicao];
        /*ciclo for para mudar a posição das coleçoes depois da coleção que foi removido uma
         * posição para a esquerda, para a coleção não ficar com um lugar vazio*/
        for(int i = posicao; i < numColeccoes; i++){
            /*copia a coleção que esta na posição seguinte até à penultima coleção, pois
             * a ultima posição ficara como null se a coleção estivesse cheia*/
            if(i < MAXOBRAS-1) {
                coleccoes[i] = coleccoes[i + 1];
            }
            else coleccoes[i] = null;
        }
        //remove uma unidade no numero de coleções
        numColeccoes--;

        return colecaoRemovida;
    }

    /**
     * Devolve o nº de obras de uma pessoa. Cada colecção deve contabilizar-se
     * como uma obra para os editores.
     */
    public int getNumObrasFromPerson(String autorEditor) {
        //variavel para fazer a contagem de obras
        int nObras = 0;

        //primeiro tratamos dos livros individualmente
        //primeiro for serve para ver se o autor editou alguma coleção
        for(int i = 0; i < editores.length; i++){
            if(autorEditor.equals(editores[i])){
                nObras++;
            }
        }
        //este ciclo irá servir para ver quantas obras o autor editou
        for(int j = 0; j < numLivros; j++){
            //variavel auxiliar para saber quantos autores existem no livro
            int auxAutores = livros[j].getAutores().length;
            //ciclo para ver se o nome do autor conta no livro
            for(int k = 0; k < auxAutores; k++){
                //comparamos o nome k do livro com o autor recebido e se for igual adicionado uma obra
                if(autorEditor.equals(livros[j].getAutores()[k])){
                    nObras++;
                }
            }
        }

        //para cada coleçao vamos chamar o proprio método e adicionar o numero de obras
        for(int i = 0; i < numColeccoes; i++){
            nObras += coleccoes[i].getNumObrasFromPerson(autorEditor);
        }
        return nObras;
    }

    /**
     * Devolver um novo array (sem nulls) com os livros de que a pessoa recebida
     * é autor. Não deve conter repetições, para excluir as repetições devem
     * utilizar o método mergeWithoutRepetitions
     */
    public Livro[] getLivrosComoAutor(String autorNome) {
        /*criamos uma varialvel interira para fazermos a contagem do numero de
         * livros que o autor recebido faz parte*/
        int nLivros = 0;
        //vamos fazer dois ciclos
        //o primeiro ciclo serve para "escolher" cada livro da coleçao
        for(int i = 0; i < numLivros; i++){
            //o segundo ciclo serve para correr cada autor do livro previamente escolhido
            for(int j = 0; j < livros[i].getAutores().length; j++){
                //se o autor recebido fizer parte dos autores do livro, incrementa-se nLivros
                if(autorNome.equals(livros[i].getAutores()[j])){
                    nLivros++;
                    //fazemos um break pois caso encontre o autor nao precisa de continuar a procurar
                    break;
                }
            }
        }
        /*cria-se entao o array de livros dos quais o autor faz parte, com n posiçoes vazias
         * posiçoes essas que vao ser preenchidas no seguimento do codigo*/
        Livro stringLivros[] = new Livro[nLivros];
        //criamos uma variavel auxiliar para guardar o livro da posiçao correta do array
        int aux = 0;

        //para a adiçao dos livros no array de livros fazemos um array praticamete igual ao anterior
        //o primeiro ciclo serve para "escolher" cada livro da coleçao
        for(int i = 0; i < numLivros; i++) {
            //o segundo ciclo serve para correr cada autor do livro previamente escolhido
            for (int j = 0; j < livros[i].getAutores().length; j++) {
                //se o autor recebido fizer parte dos autores do livro, adiciona-se o livro ao array e incrementa-se aux
                if (autorNome.equals(livros[i].getAutores()[j])) {
                    stringLivros[aux] = livros[i];
                    aux++;
                    //fazemos um break pois caso encontre o autor nao precisa de continuar a procurar
                    break;
                }
            }
        }
        /*à semelhança do que foi feito no metodo anterior fazemos chamar o proprio metodo
        * com a diferença que iremos juntar com o mergeWithoutRepetitions*/
        for(int i = 0; i < numColeccoes; i++){
            stringLivros = mergeWithoutRepetitions(stringLivros, coleccoes[i].getLivrosComoAutor(autorNome));
        }
        return stringLivros;
    }

    /**
     * Deve devolver uma string compatível com os outputs desejados
     */
    public String toString() {
        //se num livros = 0, vamos escrever sobre coleçoes
        if(numLivros == 0){
            //criamos uma string para saber quantas coleções há, para ser escrito no singular ou no plurar
            String plurar = (numColeccoes == 1) ? " coleção" : " coleções";
            return "Coleção " + getTitulo() + ", editores " + Arrays.toString(editores)
                    + ", " + numColeccoes + plurar + ", " + getNumPaginas() + "p " + getPreco() + "€";
        }
        //caso contrario escrevemos sobre livros
        else {
            //criamos uma string para saber quantos livros há, para ser escrito no singular ou no plurar
            String plurar = (numLivros == 1) ? " livro" : " livros";
            return "Coleção " + getTitulo() + ", editores " + Arrays.toString(editores)
                    + ", " + numLivros + plurar + ", " + getNumPaginas() + "p " + getPreco() + "€";
        }
    }

    /**
     * Deve devolver um array, sem nulls, com todos os autores e editores
     * existentes na colecção. O resultado não deve conter repetições. Deve
     * utilizar o método mergeWithoutRepetitions
     */
    public String[] getAutoresEditores() {
        //se uma coleçao não tiver livros retorna os editores
        if(numLivros == 0){
            return editores;
        }
        //criamos um array que começa por acomodar o array de editores
        String[] AutoresEditores = editores;

        /*neste ciclo iremos fazer merge dos autores dos livros sem haver repetição, ou seja, se
         * houverem dois livros com o mesmo autor, o seu nome só irá aparecer uma vez no array final*/
        for(int i = 0; i < numLivros; i++){
            AutoresEditores = mergeWithoutRepetitions(AutoresEditores, livros[i].getAutores());
        }
        //fazemos o mesmo que foi feito no getLivrosComoAutor
        for(int i = 0; i < numColeccoes; i++){
            AutoresEditores = mergeWithoutRepetitions(AutoresEditores, coleccoes[i].getAutoresEditores());
        }
        return AutoresEditores;
    }

    /**
     * Método que recebendo dois arrays sem repetições devolve um novo array com
     * todos os elementos dos arrays recebidos mas sem repetições
     */
    private static String[] mergeWithoutRepetitions(String[] a1, String[] a2) {
        /*vamos começar por ver quantos nomes há repetidos nas duas string utilizando 2
         * ciclos for, para o array final ser a1 + a2 - repetidos*/
        int repetidos = 0;
        for(int i = 0; i < a1.length; i++){
            for(int j = 0; j < a2.length; j++){
                //se o nome de a1[i] e a2[j] forem igual entao a variavel repetidos é incrementada
                if(a1[i].equals(a2[j])){
                    repetidos++;
                }
            }
        }
        //criamos então a string que irá ter os dois arrays sem repetição
        String[] mwr = new String[a1.length + a2.length - repetidos];

        //variável para guardar a posição onde o nome é adicionado
        int n = 0;

        /*começamos por atribuir todos os nomes do a1 ao novo array visto que
         * as repetições vão ser retiradas de a2*/
        for(int k = 0; k < a1.length; k++){
            mwr[n] = a1[k];
            n++;
        }
        //por fim iremos juntar os nomes não repetidos de a2
        for(int l = 0; l < a2.length; l++) {
            //criamos um boolean auxiliar para saber se há valores iguais na comparação
            boolean igual = false;

            for(int m = 0; m < a1.length; m++){
                /*se o nome a2[l] for igual a um nome de a1[m]
                 * a varialvel igual torna-se true e há um break
                 * pois não precisamos de fazer mais comparações*/
                if(a2[l].equals(a1[m])){
                    igual = true;
                    break;
                }
            }
            /*se o ciclo de comparação acabar e o nome de a2 não for repetido então adiciona-se
             * ao array final e incrementa-se n*/
            if(!igual){
                mwr[n] = a2[l];
                n++;
            }
        }
        return mwr;
    }

    /**
     * Método idêntico ao método anterior mas agora com arrays de livros
     */
    private static Livro[] mergeWithoutRepetitions(Livro[] a1, Livro[] a2) {
        /*vamos começar por ver quantos nomes há repetidos nas duas string utilizando 2
         * ciclos for, para o array final ser a1 + a2 - repetidos*/
        int repetidos = 0;
        for(int i = 0; i < a1.length; i++){
            for(int j = 0; j < a2.length; j++){
                //se o nome de a1[i] e a2[j] forem igual entao a variavel repetidos é incrementada
                if(a1[i].equals(a2[j])){
                    repetidos++;
                }
            }
        }
        //criamos então a string que irá ter os dois arrays sem repetição
        Livro[] mwr = new Livro[a1.length + a2.length - repetidos];

        //variável para guardar a posição onde o nome é adicionado
        int n = 0;

        /*começamos por atribuir todos os nomes do a1 ao novo array visto que
         * as repetições vão ser retiradas de a2*/
        for(int k = 0; k < a1.length; k++){
            mwr[n] = a1[k];
            n++;
        }
        //por fim iremos juntar os nomes não repetidos de a2
        for(int l = 0; l < a2.length; l++) {
            //criamos um boolean auxiliar para saber se há valores iguais na comparação
            boolean igual = false;

            for(int m = 0; m < a1.length; m++){
                /*se o nome a2[l] for igual a um nome de a1[m]
                 * a varialvel igual torna-se true e há um break
                 * pois não precisamos de fazer mais comparações*/
                if(a2[l].equals(a1[m])){
                    igual = true;
                    break;
                }
            }
            /*se o ciclo de comparação acabar e o nome de a2 não for repetido então adiciona-se
             * ao array final e incrementa-se n*/
            if(!igual){
                mwr[n] = a2[l];
                n++;
            }
        }
        return mwr;
    }

    /**
     * Devolve true caso a colecção recebida tenha o mesmo título e a mesma
     * lista de editores. Para verificar verificar se os editores são os mesmos
     * devem utilizar o método mergeWithoutRepetitions
     */
    public boolean equals(Coleccao c) {
        //se a coleção recebida for null então retorna false
        if(c == null){
            return false;
        }

        String[] auxEditores = mergeWithoutRepetitions(editores, c.editores);
        return getTitulo().equals(c.getTitulo()) && auxEditores.length == editores.length && auxEditores.length == c.editores.length;
    }

    /**
     * Mostra uma colecção segundo os outputs desejados
     */
    public void print(String prefix) {
        //efetua o print da string recebida mais as informações recorrendo ao método toString
        System.out.println(prefix + toString());
        //prefixo e print de cada livro de uma coleção em cada linha
        for (int i = 0 ; i < numLivros ; i++){
            System.out.println(prefix + "  " + livros[i].toString());
        }
        //fazemos o mesmo para cada subcoleção
        for (int i = 0; i < numColeccoes; i++){
            coleccoes[i].print("  ");
        }

    }

    /**
     * main
     */
    public static void main(String[] args) {
        Livro l1 = new Livro("Viagem aos Himalaias", 340, 12.3f,
                new String[]{"João Mendonça", "Mário Andrade"});
        Livro l2 = new Livro("Viagem aos Pirinéus", 270, 11.5f,
                new String[]{"João Mendonça", "Júlio Pomar"});

        Coleccao c1 = new Coleccao("Primavera",
                new String[]{"João Mendonça", "Manuel Alfazema"});

        boolean res;

        res = c1.addLivro(l1);
        res = c1.addLivro(l2);
        System.out.println("c1 -> " + c1);
        c1.print("");
        System.out.println();

        // adicionar um livro com nome de outro já existente
        res = c1.addLivro(l2);
        System.out.println(
                "adição novamente de Viagem aos Pirinéus a c1 -> " + res);
        System.out.println("c1 -> " + c1);
        System.out.println();

        // Outra colecção
        Livro l21 = new Livro("Viagem aos Himalaias 2", 340, 12.3f,
                new String[]{"João Mendonça", "Mário Andrade"});
        Livro l22 = new Livro("Viagem aos Pirinéus 2", 270, 11.5f,
                new String[]{"João Mendonça", "Júlio Pomar"});

        Coleccao cx2 = new Coleccao("Outono",
                new String[]{"João Mendonça", "Manuel Antunes"});
        cx2.addLivro(l21);
        cx2.addLivro(l22);
        System.out.println("cx2 -> " + cx2);
        cx2.print("");
        System.out.println();

        // adicioná-la a c1
        c1.addColeccao(cx2);
        System.out.println("c1 após adição da colecção cx2 -> " + c1);
        c1.print("");
        System.out.println();

        // get editores autores
        String[] ae = c1.getAutoresEditores();
        System.out.println("Autores editores of c1 -> " + Arrays.toString(ae));
        System.out.println();

        // getNumObrasFromPerson
        String nome = "João Mendonça";
        int n = c1.getNumObrasFromPerson(nome);
        System.out.println("Nº de obras de " + nome + " -> " + n);
        System.out.println();

        // getLivrosComoAutor
        nome = "João Mendonça";
        Livro[] obras = c1.getLivrosComoAutor(nome);
        System.out
                .println("Livros de " + nome + " -> " + Arrays.toString(obras));
        System.out.println();

        // rem livro
        String nomeLivro = "Viagem aos Himalaias";
        Livro l = c1.remLivro(nomeLivro);
        System.out.println("Remoção de " + nomeLivro + " -> " + l);
        c1.print("");
        System.out.println();

        System.out.println("\n\n Testar funcionalidades \n\n");
        //lt = livro teste
        Livro lt1 = new Livro("Livro 1", 700, 17.3f, new String[] {"Eduardo Marques"});
        Livro lt2 = new Livro("Livro 2", 500, 14.8f, new String[] {"Eduardo Marques"});
        Livro lt3 = new Livro("Livro 3", 900, 20.0f, new String[] {"Eduardo Marques"});
        Livro lt4 = new Livro("Livro 1", 700, 17.3f, new String[] {"Diogo Romba"});
        Livro lt5 = new Livro("Livro 2", 500, 14.8f, new String[] {"Diogo Romba"});
        Livro lt6 = new Livro("Livro 3", 900, 20.0f, new String[] {"Diogo Romba"});
        //ct = coleçao teste
        Coleccao ct1 = new Coleccao("Obras1", new String[]{"Autor"});
        Coleccao ct2 = new Coleccao("Obras2", new String[]{"Autor"});
        Coleccao tudo = new Coleccao("Todas as Obras", new String[]{"Autor"});

        res = ct1.addLivro(lt1);
        res = ct1.addLivro(lt2);
        res = ct1.addLivro(lt6);

        //adicionamos 2 vezes o lt4 e só é adicionado uma vez
        res = ct2.addLivro(lt4);
        res = ct2.addLivro(lt5);
        res = ct2.addLivro(lt4);
        res = ct2.addLivro(lt3);

        tudo.addColeccao(ct1);
        tudo.addColeccao(ct2);
        tudo.print("");

        tudo.remColeccao("Obras1");
        System.out.println("Remoção de \"Obras1\" -> " + ct1 + "\n");
        tudo.print("");

    }
}