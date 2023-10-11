package tp3.XML;

import org.w3c.dom.*;

/**
 * Classe abstracta que representa um Evento.
 *
 * @version 1.0
 * @author Docentes da Disciplina de Modelação e Programação, LEIM, Instituto Superior de Engenharia de Lisboa
 *
 */
public abstract class Evento {

    private String nome;

    public Evento(String nome) {this.nome = nome; }

    public String getNome() {return nome; }
    public abstract int getNumBilhetes();
    public abstract String[] getArtista();
    public abstract int numActuacoes(String artista);

    /**
     * Cria um novo Elemento contendo todas as informações deste Evento.
     * @param doc o documento que irá gerar o novo Elemento.
     * @return um Elemento que represnta o Evento na arvore XML
     */
    public abstract Element createElement(Document doc);

    /**
     * Retorna uma String contendo informações sobre o Evento.
     * Nota: Ver o ficheiro OutputPretendido.txt
     */
    public String toString() {
        return getNome() + " com " + getNumBilhetes() + " bilhetes e com os artistas " + java.util.Arrays.toString(getArtista());
    }

    /**
     * Escreve, para a consola, o prefixo seguido da String representativa do Evento.
     * @param prefix - Um prefixo para gerar a identação apropriada de acordo com a "profundidade".
     */
    public void print(String prefix) {
        System.out.println(prefix + toString());
    }

    /**
     * Constroi um novo Evento (Espetáculo ou Festival) a partir das informações existentes no nó
     * nNode que foi lido da arvore XML.
     * @param nNode o nó/elemento contendo a informação do Evento.
     */
    public static Evento build(Node nNode) {
        //começamos por criar um evento null
        Evento event = null;

        if (nNode.getNodeType() == Node.ELEMENT_NODE){

            /*se o nome do node for igual a Espetaculo então controi o Evento "event"
             * como um Espetaculo*/
            if (nNode.getNodeName().equals("Espetaculo")){
                event = Espetaculo.build(nNode);
                return event;
            }
            /*caso contrário se o nome do node for igual a Festival então controi
             * o Evento "event" como um Festival*/
            if (nNode.getNodeName().equals("Festival")){
                event = Festival.build(nNode);
                return event;
            }
        }
        /*se por acaso o nome do node não for nem Espetaculo nem evento
         * então não é contruído o evento e retorna null*/
        return event;
    }

}