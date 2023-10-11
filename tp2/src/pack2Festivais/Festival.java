package pack2Festivais;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Festival extends Evento {

    /*Cada festival devera ter uma capacidade para um maximo de 20 eventos,
    * que podem ser Espetaculos ou Festivais*/
    private static int MAXEVENTOS = 20;

    // eventos: é um array
    private Evento[] eventos = new Evento[MAXEVENTOS];
    // numEventos: int
    private int numEventos = 0;

    //Implementação do construtor
    public Festival(String nome){
        //validação do nome é feita na classe abstrata Evento
        super(nome);
    }

    //metodo que devolve o numero de bilhetes do total de evententos de um festival
    public int getNumBilhetes(){
        //inicialização do numero de bilhetes
        int numBilhetes = 0;
        //vamos usar um ciclo for para correr todos os eventos e adicionar o seu numero de bilhetes
        //numEventos!!!!!!!!!!!
        for(int i = 0; i < numEventos; i++){
            numBilhetes += eventos[i].getNumBilhetes();
        }
        //no fim do ciclo, retorna o numero total de bilhetes
        return numBilhetes;
    }

    //metodo que devolve o numero de atuações de um artista recebido
    public int numActuacoes(String artista){
        //inicialização do numero de atuações
        int numAct = 0;
        //vamos usar um ciclo for para ver o numero de vezes que um artista atua
        for(int i = 0; i < numEventos; i++){
            if(eventos[i] != null){
                //usamos recursividade para saber dentro do festival, cada evento
                numAct += eventos[i].numActuacoes(artista);
            }
        }
        return numAct;
    }

    public String toString(){
        String nFestival = "";
        for(int i = 0; i < numEventos; i++){
            if(eventos[i] != null){
                nFestival += "\n " + eventos[i].toString();
            }
        }
        /*retorna-se "Festival" mais o que foi previamente criado nas classes Espetaculo e Evento*/
        return "Festival " + super.toString();
    }

    /*metodo para devolver um array de artistas registados e com essa dimensao, vamos usar a sugestao
    * de utilizar uma List<String> bem como o contains e toArray()*/
    public String[] getArtistas(){
        //ne não houverem eventos então retornamos null
        if(numEventos == 0){
            return null;
        }
        //inicialização da nossa lista
        List<String> totalArtistas = new ArrayList<>();
        //ciclo for para percorrer cada evento
        for(int i = 0; i < numEventos; i++){
            //guardamos neste array os artistas deste evento
            String[] artistas = eventos[i].getArtistas();
            //ciclo for para adicionar os artistas à lista, caso ainda não estejam na lista
            for(int j  = 0; j < artistas.length; j++) {
                //if para ver se o artista não existe na lista
                if (!(totalArtistas.contains(artistas[j]))) {
                    //se não existir adiciona-se o artista à lista
                    totalArtistas.add(artistas[j]);
                }
            }
        }
        //por fim retorna-se a lista tornando-a num array
        return totalArtistas.toArray(new String[0]);
    }

    private int getDeepFestival() {
        // A profundidade será sempre no mínimo 1
        int mProf = 0;
        //variável para realizar a contagem
        int prof = 0;
        //variavel aux para saber o indice do ultimo festival
        int ultFest = 0;
        //vaoso descobrir o seu indice
        for(int i = eventos.length - 1; i >= 0; i--){
            if(eventos[i] instanceof Festival){
                ultFest = i;
            }
        }

        for (int j = 0; j < eventos.length; j++) {
            //se encontrarmos uma coleção
            if (eventos[j] instanceof Festival) {
                // Vamos buscar recursivamente a sua profundidade
                prof = ((Festival) eventos[j]).getDeepFestival() + 1;

                //se a profundidade atual for a maior até a este ponto
                if (prof >= mProf) {
                    //guardamos como a nova maior
                    mProf = prof;
                }

                /* Quando se chega à última subcoleção, retorna-se a maior,
                 * caso contrário, retorna-se a contagem atual*/
                if (j == ultFest) {
                    return mProf;
                } else {
                    return prof;
                }
            }
        }
        //só chega a este ponto se um festival não tiver subfestivais, ou seja, profundidade 0
        return 0;
    }

    //metodo para adicionar um evento
    public boolean addEvento(Evento evento){
        //array para guardar os novos artistas
        String[] novosArtistas = evento.getArtistas();

        /*vamos verificar cada nome, vendo se o numero de atuações no evento não é duas
        * vezes maior do que no festival inteiro*/
        for(int i = 0; i < novosArtistas.length; i++){
            //variavel auxiliar para guardar quantas atuações tem o artista no evento
            //int aux1 = evento.numActuacoes(novosArtistas[i]);
            //variavel auxiliar para guardar quantas atuações tem o artista no festival
            //int aux2 = numActuacoes(novosArtistas[i]);
            //se o numero de atuações no evento for duas vezes maior do que no festival
            if(evento.numActuacoes(novosArtistas[i]) - 2 > numActuacoes(novosArtistas[i])){
                //retorna false e não adiciona o evento
                return false;
            }
        }

        //vamos então adicionar o evento
        for(int j = 0; j < MAXEVENTOS; j++){
            //adiciona na primeira posição que encontrar vazia
            if(eventos[j] == null){
                eventos[j] = evento;
                break;
            }
        }
        numEventos++;
        return true;
    }

    private int getIndexOfEvento(Evento evento) {
        /*se o evento na posição 0 for null significa que não há eventos no
         * festival, ou seja, não vale a pena procura o index do evento*/
        if(eventos[0] == null) {
            return -1;
        }
        //ciclo for para encontrar a posição do evento no festival
        for(int i = 0; i < numEventos; i++){
            //se encontrar a obra na coleção retorna a posição i da obra
            if((eventos[i].getNome()).equals(evento.getNome())){
                return i;
            }
        }
        //caso não encontre o evento retorna -1
        return -1;
    }

    //metodo para eliminar um evento
    public boolean delEvento(Evento nomeEvento){
        //variavel para guardar a posição do evento a remover
        int posicao = getIndexOfEvento(nomeEvento);
        if(posicao != -1) {
            /*ciclo for para mudar a posição dos eventos depois do evento que foi removido uma
             * posição para a esquerda, para o festival não ficar com um lugar vazio*/
            for (int i = posicao; i < numEventos; i++) {
                if (i < MAXEVENTOS - 1) {
                    eventos[i] = eventos[i + 1];
                } else {
                    eventos[i] = null;
                }
            }
            numEventos--;
            return true;
        }
        else return false;
    }


    public static void main(String[] args) {

        //criação dos espetáculos e adição de artistas
        Espetaculo pMundo1 = new Espetaculo("Palco Mundo","Lisboa",8600);
        pMundo1.addArtista("The National");
        pMundo1.addArtista("Liam Gallagher");
        System.out.println("Espetáculo principal 1 -> " + pMundo1);

        Espetaculo galpMusic1 = new Espetaculo("Rock Your Street","Lisboa",3100);
        galpMusic1.addArtista("The Black Mamba");
        galpMusic1.addArtista("Izal");
        galpMusic1.addArtista("Ego Kill Talent");
        System.out.println("Espetáculo secundário 1 -> " + galpMusic1);

        Espetaculo pMundo2 = new Espetaculo("Palco Mundo","Lisboa",7500);
        pMundo2.addArtista("Black Eyed Peas");
        pMundo2.addArtista("Ivete Sangalo");
        //tentativa de adicionar o mesmo artista duas vezes
        pMundo2.addArtista("Ivete Sangalo");
        System.out.println("\nEspetáculo principal 2 -> " + pMundo2);

        Espetaculo galpMusic2 = new Espetaculo("Rock Your Street","Lisboa",2600);
        galpMusic2.addArtista("Bárbara Tinoco");
        galpMusic2.addArtista("Deejay Kamala");
        galpMusic2.addArtista("Miss Caffeina");
        System.out.println("Espetáculo secundário 2 -> " + galpMusic2 + "\n");

        //criação dos festivais e adição dos eventos
        Festival dia1 = new Festival("\"Primeiro dia\"");
        Festival dia2 = new Festival("\"Segundo dia\"");

        dia1.addEvento(pMundo1);
        dia1.addEvento(galpMusic1);
        dia2.addEvento(pMundo2);
        dia2.addEvento(galpMusic2);

        System.out.println("\nDia 1: " +dia1);
        System.out.println("Dia 2: " +dia2);

        //criação do festival principal e adição dos dias
        Festival rockinRio = new Festival("\"Rock in Rio\"");
        rockinRio.addEvento(dia1);
        rockinRio.addEvento(dia2);

        Festival festivais = new Festival("\"Festivais deste ano\"");
        festivais.addEvento(rockinRio);

        System.out.println("\nNome do Festival: " + rockinRio.getNome());
        System.out.println("Número de bilhetes: " + rockinRio.getNumBilhetes());
        System.out.println("Artistas do festival " + rockinRio.getNome() + " -> " + Arrays.toString(rockinRio.getArtistas()));

        System.out.println("\nProfundidade " + dia1.getNome() + " : (deve ser 0) -> " + dia1.getDeepFestival());
        System.out.println("Profundidade " + rockinRio.getNome() + " : (deve ser 1) -> " + rockinRio.getDeepFestival());
        System.out.println("Profundidade " + festivais.getNome() + " : (deve ser 2) -> " + festivais.getDeepFestival());

        System.out.println("\nNúmero de atuações de \"Liam Gallagher\" -> " + rockinRio.numActuacoes("Liam Gallagher"));

        System.out.println("\nRemoção do primeiro dia -> " + rockinRio.delEvento(dia1));
        System.out.println("Tentativa de emoção do primeiro dia novamente -> " + rockinRio.delEvento(dia1));
        System.out.println(rockinRio);

        System.out.println("\nNúmero de atuações de \"Liam Gallagher\" -> " + rockinRio.numActuacoes("Liam Gallagher"));
    }


}
