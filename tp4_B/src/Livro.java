import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Classe que dever� suportar um livro
 */
public class Livro extends Nome {
    // pre�o do livro
    private float preco;

    //Categoria onde se encaixa o livro (com�dia, romance, etc)
    private Categoria[] categorias;

	/***
	 * Contrutor do Livro: entra titulo, pre�o e a categoria
	 */
    public Livro(String titulo, float preco, Categoria[] categorias){
		//valida��o do titulo feita na classe abstrata
		super(titulo);
        //o pre�o n�o pode ser negativo
        if (preco < 0){
            throw new IllegalArgumentException("O pre�o n�o pode ser negativo");
        }
        this.preco = preco;

        //cada livro tem de ter pelo menos uma categoria existente
        if (categorias == null || categorias.length == 0){
            throw new IllegalArgumentException("O livro tem que ter pelo menos uma categoria");
        }
        //cada livro n�o pode ter categorias null
        for (int i = 0; i < categorias.length; i++){
            if (categorias[i] == null){
                throw new IllegalArgumentException("O livro n�o pode conter categorias nulas");
            }
        }
        /*cada livro n�o pode tamb�m ter categorias repetidas, ou seja, um livro n�o pode ter
        * as categorias [HUMOR, A��O, HUMOR]*/
        if(haRepeticoes(categorias)){
            throw new IllegalArgumentException("O livro n�o pode ter categorias repetidas");
        }
        this.categorias = categorias;
    }

    /**
     * Devolve o pre�o do livro
     */
    public float getPreco() {
        return this.preco;
    }

    /**
     * Devolve uma c�pia do array de categorias do livro
     */
    //@Override
	public Categoria[] getCategorias() {
        //.clone faz uma copia dos autores
        return categorias.clone();
    }

	/**
	 * V� se n�o existem categorias repetidas
	 * @param categorias
	 * @return
	 */

    private boolean haRepeticoes(Categoria[] categorias) {
		/*Vamos comparar um elemento de uma string com o resto da string a partir do elemento
		 * seguinte, ou seja, numa string (A��O, BIBLIOGRAFIAS, FIC��O_CIENT�FICA) vamos comparar A��O
		 * com as duas categorias a seguir, BIBLIOGRAFIAS com a categoria a seguir e assim sucessivamente*/
		for (int i = 0; i < categorias.length; i++) {
			for (int j = i + 1; j < categorias.length; j++) {
				//se dois nomes forem iguais retorna true
				if (categorias[i].equals(categorias[j])) {
					return true;
				}
			}
		}
		//se n�o houverem nomes iguais retorna false
		return false;
	}

    /**
     * Devolve uma string com a informa��o do livro
     */
    public String toString() {
        //super.toString() chama o m�todo da classe Tipo, visto que Livro herda desta
        if (getCategorias().length > 1) {
            return "Livro \"" + super.toString() + "\", com valor de " + getPreco() + "� por livro e com categorias " + java.util.Arrays.toString(getCategorias());
        } else {
            return "Livro \"" + super.toString() + "\", com valor de " + getPreco() + "� por livro e com categoria " + java.util.Arrays.toString(getCategorias());
        }
    }


	public String print(String prefix){
		System.out.println(prefix + toString());
		return prefix;
	}

    /**
     * se for um Livro deve usar o metodo do "Tipo"
     */
    public boolean equals(Object l) {
        //Primeiro confirma se � um livro, e se n�o � nulo, e depois chama o equals da classe Obra
        if (l != null && l instanceof Livro){
            return super.equals(l);
        }
        //Se as condi��es n�o se verificarem, devolve false
        return false;
    }





	/**
	 * Constroi um novo Livro a partir de um n� contendo as infoma��es lidas do documento XML.
	 *
	 * @param nNode o n� associado ao Livro
	 * @return um novo Livro
	 */
	public static Livro build(Node nNode) {
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eLivro = (Element) nNode;

			String titulo = eLivro.getElementsByTagName("Titulo").item(0).getTextContent();
			float preco = Float.parseFloat(eLivro.getElementsByTagName("Preco").item(0).getTextContent());
			Element categorias = (Element) eLivro.getChildNodes();
			NodeList categoria = categorias.getElementsByTagName("Categoria");
			ArrayList<Categoria> c = new ArrayList<Categoria>();

			int len = categoria.getLength();

			for (int j = 0; j < len; j++) {
				String a = categorias.getElementsByTagName("Categoria").item(j).getTextContent();
				c.add(Categoria.valueOf(a));
			}
			Categoria[] cat = c.toArray(new Categoria[c.size()]);
			Livro livro = new Livro(titulo, preco, cat);
			return livro;
		}
		return null;
	}


	/**
	 * Cria um novo Elemento que ir� representar, no documento XML, o Livro associado ao Livro corrente.
	 *
	 * @param doc o Documento que ir� ser usado para gerar o novo Element.
	 */
	public Element createElement(Document doc) {

		//come�amos por criar o elemento Livro
		Element livro = doc.createElement("Livro");

		Element titulo = doc.createElement("Titulo");
		titulo.appendChild(doc.createTextNode(getTitulo()));
		livro.appendChild(titulo);

		Element preco = doc.createElement("Preco");
		preco.appendChild(doc.createTextNode(String.valueOf(getPreco())));
		livro.appendChild(preco);

		Element categorias = doc.createElement("Categorias");
		livro.appendChild(categorias);

		for (int i = 0; i < getCategorias().length; i++){
			Element categoria = doc.createElement("Categoria");
			categoria.appendChild(doc.createTextNode(String.valueOf(getCategorias()[i])));
			categorias.appendChild(categoria);
		}
		return livro;
	}



    /***
     * teste da classe Livro
     */
    /*public static void main(String[] args){
		try{
			File inputFile = new File("C:/Users/Eduardo/OneDrive - Instituto Superior de Engenharia de Lisboa/Desktop/LEIM/21-22SV/MP/Code/MoP/src/tp4/Biblioteca.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			XPath xpath = XPathFactory.newInstance().newXPath();
			String expression = "/Biblioteca/TotalLivros/*";
			NodeList nList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);

			Node nNode = nList.item(0);
			Livro livro = Livro.build(nNode);
			if (livro != null) livro.print("");

			Livro l3 = new Livro("Secret", 19.5f, new Categoria[]{Categoria.ACAO, Categoria.ROMANCE});

			if (livro instanceof Livro){
				Livro l = (Livro) livro;

				Document newDoc = dBuilder.newDocument();
				Element rootElement = newDoc.createElement("Eventos");
				rootElement.appendChild(l.createElement(newDoc));

				newDoc.appendChild(rootElement);

				FileOutputStream output = new FileOutputStream("C:/Users/Eduardo/OneDrive - Instituto Superior de Engenharia de Lisboa/Desktop/LEIM/21-22SV/MP/Code/MoP/src/tp4/yha.xml");
				writeXml(newDoc, output);
			}


		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writeXml (Document doc, OutputStream output) throws TransformerException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		// pretty print XML
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(output);

		transformer.transform(source, result);
	}*/

/*
        //contrutor
        Livro l1 = new Livro("1984", 15.5f, new Categoria[]{Categoria.FICCAO});
        Livro l2 = new Livro("Secret", 19.5f, new Categoria[]{Categoria.ACAO, Categoria.ROMANCE});

        //teste do toString()
        System.out.println(l1);
        System.out.println(l2);

        //teste ao equals
        System.out.println();
        System.out.println("Livro \"" + l1.getTitulo() + "\" igual ao livro \"" + l2.getTitulo() + "\"? --> " +  l1.equals(l2));

        Livro l3 = new Livro("Secret", 19.5f, new Categoria[]{Categoria.ACAO, Categoria.ROMANCE});
        System.out.println("Livro \"" + l2.getTitulo() + "\" igual ao livro \"" + l3.getTitulo() + "\"? --> " + l2.equals(l3));

        Livro l4 = new Livro("O Mercador de Livros", 17.2f, new Categoria[]{Categoria.ACAO});
        Livro l5 = new Livro("  O   Mercador    de Livros", 17.2f, new Categoria[]{Categoria.ACAO});
        System.out.println("Livro \"" + l4.getTitulo() + "\" igual a \"" + l5.getTitulo() + "\"? --> " + l4.equals(l5));

        //teste para ver se o livro faz parte de uma categoria
        System.out.println();
        System.out.println("O Livro \"" + l1.getTitulo() + "\" faz parte da Categoria A��o? " + l1.pertence(Categoria.ACAO));

        //teste dos erros
		System.out.println();
		try{
			//teste com nome inv�lido
			Livro l6 = new Livro("%", 17.2f, new Categoria[]{Categoria.ACAO});
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}

		try{
			//teste com pre�o inv�lido
			Livro l7 = new Livro("Um livro", -2.3f, new Categoria[]{Categoria.ACAO});
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}

		try{
			//teste sem categorias
			Livro l8 = new Livro("Um livro", 18.0f, new Categoria[]{});
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}

		try{
			//teste com categoria null
			Livro l8 = new Livro("Um livro", 18.0f, null);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}

		try{
			//teste com categorias repetidas
			Livro l9 = new Livro("Um livro", 18.0f, new Categoria[]{Categoria.ACAO, Categoria.ACAO});
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}

    }*/
}
