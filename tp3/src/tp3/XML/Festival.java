package tp3.XML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Classe que representa um Evento do tipo Festival.
 *
 * @version 1.0
 * @author Docentes da Disciplina de Modelação e Programação, LEIM, Instituto Superior de Engenharia de Lisboa
 *
 */
public class Festival extends Evento {

    private static final int MAXEVENTOS = 20;

    private Evento[] evento = new Evento[MAXEVENTOS];
    private int numEventos = 0;

    public Festival(String nome) {
        //validação do nome é feita na classe abstrata Evento
        super(nome);
    }

    /**
     * Devolve todos os bilhetes existentes no Festival (somando e devolvendo todos os bilhetes dos seus Eventos).
     *
     * @return o número de bilhetes existentes no Festival.
     */
    @Override
    public int getNumBilhetes() {
        //inicialização do numero de bilhetes
        int numBilhetes = 0;
        //vamos usar um ciclo for para correr todos os eventos e adicionar o seu numero de bilhetes
        for(int i = 0; i < numEventos; i++){
            numBilhetes += evento[i].getNumBilhetes();
        }
        //no fim do ciclo, retorna o numero total de bilhetes
        return numBilhetes;
    }


    /**
     * Retorna o número de actuaçõoes de um determinado artista.
     *
     * @Override
     */
    @Override
    public int numActuacoes(String artista) {
        //inicialização do numero de atuações
        int numAct = 0;
        //vamos usar um ciclo for para ver o numero de vezes que um artista atua
        for(int i = 0; i < numEventos; i++){
            if(evento[i] != null){
                //usamos recursividade para saber dentro do festival, cada evento
                numAct += evento[i].numActuacoes(artista);
            }
        }
        return numAct;
    }

    /**
     * Devolve uma string representativa do Festival.
     * Nota: Ver o ficheiro OutputPretendido/OutputPretendido.txt
     */
    public String toString() {
        String nFestival = "";
        for(int i = 0; i < numEventos; i++){
            if(evento[i] != null){
                nFestival += "\n " + evento[i].toString();
            }
        }
        /*retorna-se "Festival" mais o que foi previamente criado nas classes Espetaculo e Evento*/
        return "Festival " + super.toString();
    }

    /**
     * Devolve um array contendo todos, de forma não repetida, os nomes de todos os artistas quer irão
     * actuar no Festival.
     *
     * @return um array contendo os nomes dos artistas.
     */
    @Override
    public String[] getArtista() {
        //ne não houverem eventos então retornamos null
        if(numEventos == 0){
            return null;
        }
        //inicialização da nossa lista
        List<String> totalArtistas = new ArrayList<>();
        //ciclo for para percorrer cada evento
        for(int i = 0; i < numEventos; i++){
            //guardamos neste array os artistas deste evento
            String[] artistas = evento[i].getArtista();
            //ciclo for para adicionar os artistas à lista, caso ainda não estejam na lista
            for(int j  = 0; j < artistas.length; j++){
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

    /**
     * Retorna a profundidade maxima da "árvore" que contém Festivais dentro de Festivais. O próprio Festival não conta.
     *
     * @return a profundidade máxima.
     */
    public int getDeepFestival() {
        // A profundidade será sempre no mínimo 1
        int mProf = 0;
        //variável para realizar a contagem
        int prof = 0;
        //variavel aux para saber o indice do ultimo festival
        int ultFest = 0;
        //vamso descobrir o seu indice
        for(int i = evento.length - 1; i >= 0; i--){
            if(evento[i] instanceof tp3.XML.Festival){
                ultFest = i;
            }
        }

        for (int j = 0; j < evento.length; j++) {
            //se encontrarmos uma coleção
            if (evento[j] instanceof tp3.XML.Festival) {
                // Vamos buscar recursivamente a sua profundidade
                prof = ((tp3.XML.Festival) evento[j]).getDeepFestival() + 1;

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

    /**
     * Adiciona um novo Evento ao Festival, caso para nenhum dos artistas do novo evento se verifique que o seu número de atuações no
     * novo evento (a adicionar) supere em mais de duas o número de atuações no festival corrente.
     *
     * @param eventos
     * @return verdadeiro, se o novo Evento foi adicionado.
     */
    public boolean addEvento(Evento eventos) {
        //array para guardar os novos artistas
        String[] novosArtistas = eventos.getArtista();

        /*vamos verificar cada nome, vendo se o numero de atuações no evento não é duas
         * vezes maior do que no festival inteiro*/
        for(int i = 0; i < novosArtistas.length; i++){
            //variavel auxiliar para guardar quantas atuações tem o artista no evento
            int aux1 = eventos.numActuacoes(novosArtistas[i]);
            //variavel auxiliar para guardar quantas atuações tem o artista no festival
            int aux2 = numActuacoes(novosArtistas[i]);
            //se o numero de atuações no evento for duas vezes maior do que no festival
            if(aux1 - 2 > aux2){
                //retorna false e não adiciona o evento
                return false;
            }
        }

        //vamos então adicionar o evento
        for(int j = 0; j < MAXEVENTOS; j++){
            //adiciona na primeira posição que encontrar vazia
            if(evento[j] == null){
                evento[j] = eventos;
                break;
            }
        }
        numEventos++;
        return true;
    }

    private int getIndexOfEvento(String eventos) {
        /*se o evento na posição 0 for null significa que não há eventos no
         * festival, ou seja, não vale a pena procura o index do evento*/
        if(evento[0] == null) {
            return -1;
        }
        //ciclo for para encontrar a posição do evento no festival
        for(int i = 0; i < numEventos; i++){
            //se encontrar a obra na coleção retorna a posição i da obra
            if((evento[i].getNome()).equals(eventos)){
                return i;
            }
        }
        //caso não encontre o evento retorna -1
        return -1;
    }

    /**
     * Remove um evento em qualquer profundidade do Festival corrente.
     *
     * @param nomeEvento nome do Evento a remover.
     * @return verdadeiro, se o Evento foi removido.
     */
    public boolean delEvento(String nomeEvento) {
        //variavel para guardar a posição do evento a remover
        int posicao = getIndexOfEvento(nomeEvento);
        if(posicao != -1) {
            /*ciclo for para mudar a posição dos eventos depois do evento que foi removido uma
             * posição para a esquerda, para o festival não ficar com um lugar vazio*/
            for (int i = posicao; i < numEventos; i++) {
                if (i < MAXEVENTOS - 1) {
                    evento[i] = evento[i + 1];
                } else {
                    evento[i] = null;
                }
            }
            numEventos--;
            return true;
        }
        else return false;
    }

    /**
     * Imprime na consola informações sobre o Festival.
     * Nota: Ver o output pretendido em OutputPretendido/OutputPretendido.txt.
     */
    public void print(String prefix) {
        super.print(prefix);
        for (int i = 0; i < numEventos; i++){
            evento[i].print(" ");
        }

    }

    /**
     * Constroi um novo Festival a partir de um nó contendo as infomações lidas do documento XML.
     *
     * @param nNode o nó associado ao Festival
     * @return um novo Festival
     */
    public static Festival build(Node nNode) {
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eFestival = (Element) nNode;
            String nome = eFestival.getElementsByTagName("Nome").item(0).getTextContent();
            NodeList eEventos = eFestival.getElementsByTagName("Eventos").item(0).getChildNodes();
            System.out.println(eEventos);
            //criamos um Festival com o seu nome
            Festival festival = new Festival(nome);

            for (int i = 0; i < eEventos.getLength(); i++) {
                Node elemento = eEventos.item(i);
                //usamos o construtor do Evento usando o elemento lido
                Evento event = Evento.build(elemento);
                if (event != null) {
                    /*se o evento não for null adicionamos ao festival o evento, os artistas
                    * e o numero de bilhetes*/
                    festival.addEvento(event);
                    festival.getArtista();
                    festival.getNumBilhetes();
                }
            }
            //damos return do festival que acabamos de construir
            return festival;
        }
        //se o tipo de nó lido não for um ELEMENT_NODE então iremos retornar nulo
        return null;
    }

    /**
     * Cria um novo Elemento quer irá representar, no documento XML, o Festival associado ao Festival corrente.
     *
     * @param doc o Documento que irá ser usado para gerar o novo Element.
     */
    public Element createElement(Document doc) {
        //começamos por criar o elemento festival
        Element festival = doc.createElement("Festival");

        /*de seguida criamos o elemento nome. neste elemento vamos criar um nó de texto
        * onde iremos usar o getNome, damos append do getNome ao nome e repetimos o
        * append mas desta vez fazemos append do nome ao festival*/
        Element nome = doc.createElement("Nome");
        nome.appendChild(doc.createTextNode(getNome()));
        festival.appendChild(nome);

        //criamos o elemento eventos que também será um elemento filho do festival, tal como o nome
        Element eEventos = doc.createElement("Eventos");
        festival.appendChild(eEventos);

        /*ciclo for para adicionar eventos ao festival, estes eventos podem ser
        * espetaculos os outros festivais*/
        for (int i = 0; i < numEventos; i++){
            if (this.evento[i] != null){
                //se o evento i não for nulo vamos ver se o evento é um espetaculo ou um festival
                if (this.evento[i] instanceof Espetaculo){
                    //se for um espetaculo adicionamos o mesmo ao festival
                    Element eEspetaculo = ((Espetaculo)this.evento[i]).createElement(doc);
                    eEventos.appendChild(eEspetaculo);
                }
                else if (this.evento[i] instanceof Festival){
                    //se for um festival adicionamos o mesmo ao festival
                    Element eFestival = ((Festival)this.evento[i]).createElement(doc);
                    eEventos.appendChild(eFestival);
                }

            }
        }
        //por fim retornamos o festival acabado de criar
        return festival;
    }
    /**
     * Método main que gera no output o que está no ficheiro OutputPretendido/OutputPretendido.txt e cria um novo
     * documento XML/Eventos.xml, com a mesma estrutura que o documento OutputPretendido/Eventos.xml.
     * @param args
     */
    public static void main (String[]args){

        try {

            File inputFile = new File("C:/Users/Eduardo/OneDrive - Instituto Superior de Engenharia de Lisboa/Desktop/LEIM/21-22SV/MP/Code/MoP/src/tp3/XML/BaseDados.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            XPath xpath = XPathFactory.newInstance().newXPath();
            String expression = "/BaseDados/Eventos/*";
            NodeList nList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);

            Node nNode = nList.item(0);
            Evento evento = Evento.build(nNode);
            if (evento != null) evento.print("");

            Festival fNovo = new Festival("Bollywood Music Festival");

            Espetaculo e1_1 = new Espetaculo("Suna Hai", "Sines", 500);
            e1_1.addArtista("Suna Hai");
            fNovo.addEvento(e1_1);

            Espetaculo e1_2 = new Espetaculo("Rait Zara", "Sines", 400);
            e1_2.addArtista("Rait Zara");
            fNovo.addEvento(e1_2);

            if (evento instanceof Festival) {
                Festival festival = (Festival) evento;
                festival.addEvento(fNovo);

                // root elements
                Document newDoc = dBuilder.newDocument();
                Element rootElement = newDoc.createElement("Eventos");
                rootElement.appendChild(festival.createElement(newDoc));

                newDoc.appendChild(rootElement);

                FileOutputStream output = new FileOutputStream("C:/Users/Eduardo/OneDrive - Instituto Superior de Engenharia de Lisboa/Desktop/LEIM/21-22SV/MP/Code/MoP/src/tp3/XML/Eventos.xml");
                writeXml(newDoc, output);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Escreve, para o OutputStream, o documento doc.
     * @param doc o documento contendo os Elementos a gravar on ficheiro output
     * @param output o ficheiro de saída.
     */
    private static void writeXml (Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print XML
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }
}