package tp3.XML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Classe que representa um Evento do tipo Espetaculo.
 *
 * @version 1.0
 * @author Docentes da Disciplina de Modelação e Programação, LEIM, Instituto Superior de Engenharia de Lisboa
 *
 */
public class Espetaculo extends Evento {

    private static final int MAXARTISTAS = 10;
    private String[] artistas = new String[MAXARTISTAS];
    private int nArtistas = 0;
    private int numBilhetes;
    private String localidade;

    /**
     * Constroi um novo Espetaculo
     * @param nome nome do Espetaculo
     * @param localidade a localidade do Espetaculo
     * @param numBilhetes o número de bilhetes disponíveis
     */
    public Espetaculo(String nome, String localidade, int numBilhetes) {
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

    /**
     * Informa se um determinado artista irá actuar no Espetaculo.
     * @return 1, se actuar e 0 caso contrário.
     * @Override
     */
    public int numActuacoes(String artista) {
        List<String> nomesArtistas = new ArrayList<String>(Arrays.asList(artistas));
        if ( nomesArtistas.indexOf(artista)== -1){
            return 0;
        }
        return 1;
    }

    /**
     * Permite adicionar un novo artista ao Espetaculo se o artista ainda
     * não tem actuações e se o número máximo de artistas ainda não foi ultrapassado.
     * @param artista representa o novo artista
     * @return verdadeiro, caso o artista tenha sido adicionado e falso caso contrário.
     */
    public boolean addArtista(String artista) {
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

    /**
     * Devolve o número de bilhetes.
     * @Override
     */
    public int getNumBilhetes() {
        return this.numBilhetes;
    }

    /**
     * Devolve uma cópia dos artistas que actuam no Espetaculo.
     * @Override
     */
    public String[] getArtista() {
        return this.artistas;
    }

    /**
     * Devolve a localidade do Espetaculo
     * @return a localidade.
     */
    public String getLocalidade() {
        return this.localidade;
    }

    /**
     * Devolve uma String a representar o Espetaculo.
     * Nota: Ver o ficheiro OutputPretendido.txt
     * @Override
     */
    public String toString() {
        /*super.toString() vai buscar o toString criada na classe abstrata Evento e adicionamos
         * ao toStrign a localidade onde o Evento vai decorrer*/
        return super.toString() + " em " + getLocalidade();
    }

    /**
     * Constroi um novo Evento a partir do objecto Node passado como parâmetro.
     * @param nNode
     * @return um novo Evento
     */
    public static Evento build(Node nNode) {
        if (nNode.getNodeType() == Node.ELEMENT_NODE){
            Element eSpec = (Element) nNode;
            String codESpetaculo = eSpec.getAttribute("codEspetaculo");
            int numBilhetes = Integer.parseInt(eSpec.getAttribute("numBilhetes"));
            String nome = eSpec.getElementsByTagName("Nome").item(0).getTextContent();
            String localidade = eSpec.getElementsByTagName("Localidade").item(0).getTextContent();

            Element artistas = (Element) eSpec.getChildNodes();

            //criamos um Espetaculo com todos os parametros
            Espetaculo esp = new Espetaculo(nome, localidade, numBilhetes);

            //vai buscar os artistas ao node
            NodeList artista = artistas.getElementsByTagName("Artista");
            int len = artista.getLength();

            //ciclo for para adicionar os artistas ao espetaculo
            for (int i = 0; i < len; i++){
                String artist = artistas.getElementsByTagName("Artista").item(i).getTextContent();
                esp.addArtista(artist);
            }

            return esp;
        }
        //se o tipo de nó lido não for um ELEMENT_NODE então iremos retornar nulo
        return null;
    }

    /**
     *  Constroi um novo Element a partir do Espectaculo actual.
     *  @param doc - o documento que irá gerar o novo Element
     */
    public Element createElement(Document doc) {
        //começamos por criar o elemento espetaculo
        Element espetaculo = doc.createElement("Espetaculo");

        /*de seguida criamos o elemento nome. neste elemento vamos criar um nó de texto
         * onde iremos usar o getNome, damos append do getNome ao nome e repetimos o
         * append mas desta vez fazemos append do nome ao espetaculo*/
        Element nome = doc.createElement("Nome");
        nome.appendChild(doc.createTextNode(getNome()));
        espetaculo.appendChild(nome);

        /*repetimos o que foi realizado a cima para os artistas, localidade
        * e para o numero de bilhestes*/
        Element eArtistas = doc.createElement("Artistas");
        //neste caso estamos a criar um elemento self closing/empty
        espetaculo.appendChild(eArtistas);

        Element localidade = doc.createElement("Localidade");
        localidade.appendChild(doc.createTextNode(getLocalidade()));
        espetaculo.appendChild(localidade);

        espetaculo.setAttribute("numBilhetes" , Integer.toString(getNumBilhetes()));

        /*por fim vamos tratar do elemento "Artistas" que até agora é um elemento self
        * closing/empty, para tal começamos por criar um array de Strings chamado o metodo
        * getArtista para termos acesso ao artistas */
        String[] artistasTotal = getArtista();
        //vamos um ciclo for para adicionar os artistas 1 a 1
        for (int i = 0; i < nArtistas; i++){
            if (artistasTotal[i] != null){
                /*se o artista i não for null então criamos um elemento "Artista" e damos append
                * do do mesmo para o elemento*/
                Element eArtista = doc.createElement("Artista");
                eArtista.appendChild(doc.createTextNode(artistasTotal[i]));
                /*por fim damos append do elemento "Artista" ao elemento "Artistas" e deste modo
                * deixamos de ter o elemento "Artistas" self closing/empty*/
                eArtistas.appendChild(eArtista);
            }
        }
        //feito tudo isto, damos return do espetaculo em questão
        return espetaculo;
    }


}