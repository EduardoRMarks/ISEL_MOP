import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ConjLivros extends Nome {
	//array de livros num Conjunto
	private ArrayList<Livro> livros = new ArrayList<Livro>();
	//private ArrayList<ConjLivros> conjLivros = new ArrayList<ConjLivros>();
	private int numLivros = 0;
	private String addSub;


	/**
	 * Contrutor do Conjunto de livros: entra o nome
	 */
	public ConjLivros(String nome) {
		super(nome);
	}

	/**
	 * Obtem o n�mero total de livros do Conjunto
	 */
	public int getNumLivros(){
		if(livros.isEmpty()){
			return 0;
		}
		else {
			numLivros = livros.size();
			return numLivros;
		}
	}

	/**
	 * Obtem o pre�o total do Conjunto de livros, somando o pre�o de cada livro
	 */
	public float getPreco(){
		//se o Conjunto n�o tiver livros retornamos o pre�o 0
		if(livros.isEmpty()){
			return 0;
		}
		//vari�vel para fazera contagem do pre�o total
		float precoTotal = 0;
		/*este ciclo � chamado for-each loop, que vai percorrer cada elemento do Livro
		 * e vai somando os pre�os, substitui o for(int i = 0; i < livros.size(); i++)*/
		for(Livro livro : livros){
			precoTotal += livro.getPreco();
		}
		return precoTotal;
	}
	/**
	 * Devolve uma c�pia do array de categorias do livro
	 */
	//@Override
	public Categoria[] getCategorias() {
		if(numLivros == 0){
			return null;
		}
		ArrayList<Categoria> totalCategorias =  new ArrayList<Categoria>();
		for (int i = 0; i < numLivros; i++){
			Categoria[] cat = livros.get(i).getCategorias();
			for (int j = 0; j < cat.length; j++){
				if (!(totalCategorias.contains(cat[j]))){
					totalCategorias.add(cat[j]);
				}
			}
		}
		return totalCategorias.toArray(new Categoria[0]);
	}

	/***
	 * Metodo para adicionar um livro ao Conjunto de livros
	 */
	public boolean addSubLivro(Livro l, String addSub){
		//se o t�tulo do livro for null ou a quantidade do livro x for 0, ou menor que 0, ent�o n�o adiciona
		if(l == null) {
			return false;
		}
		this.addSub = addSub;

		//se a string addSub for igual a adicionar ent�o vai adicionar o livro
		if (addSub.equals("Adicionar") || addSub.equals("adicionar")){
			livros.add(l);
			return true;
		}
		//se a string addSub for igual a cender ent�o vai remover o livro caso haja no stock
		if (addSub.equals("Vender") || addSub.equals("vender")){
			//vamos ver se existe o livro no stock
			if (Collections.frequency(livros, l) == 0){
				//se n�o houver, n�o remove
				System.out.println("O livro \"" + l.getTitulo() + "\" n�o existe na loja, logo n�o d� para vender");
				return false;
			}
			//se houver, remove
			else {
				livros.remove(l);
				return true;
			}
		}
		return false;
	}

	/**
	 * Devolve uma string com a informa��o do Conjunto
	 */
	public String toString() {
		//super.toString() chama o m�todo da classe Tipo, visto que Conjunto herda desta
		if (getNumLivros() > 1 || getNumLivros() == 0) {
			return "Conjunto \"" + super.toString() + "\" com " + getNumLivros() + " livros e com pre�o final de " + getPreco() + "�";
		} else {
			return "Conjunto \"" + super.toString() + "\" com " + getNumLivros() + " livro e com pre�o final de " + getPreco() + "�";
		}
	}

	/***
	 * Devolve a informa��o do Conjunto (Livros e quantidades do mesmo)
	 * @return
	 */
	public String print(String prefix){
		//efetua o print da string recebida mais as informa��es recorrendo ao m�todo toString
		System.out.println(prefix + toString());

		ArrayList<String> totalLivros =  new ArrayList<String>();

		if(!livros.isEmpty()) {
			/*para mostrar a informa��o de cada livro do conjunto utilizamos o seguinte ciclo for com recurso
			 * ao m�todo todosOsLivros que foi criado com este proposito*/
			for (int i = 0; i < livros.size(); i++) {
				Livro x = livros.get(i);
				x.print("   " + prefix);
				totalLivros.add("\n" + x);
			}
		}
		return String.valueOf(totalLivros);
	}

	/**
	 * se for uma Conjunto deve usar o metodo do "Tipo"
	 */
	public boolean equals(Object c) {
		//Primeiro confirma se � um Conjunto, e se n�o � nulo, e depois chama o equals da classe Tipo
		if (c != null && c instanceof ConjLivros){
			return super.equals(c);
		}
		//Se as condi��es n�o se verificarem, devolve false
		return false;
	}


	/**
	 * Constroi um novo Conjunto de Livros a partir de um n� contendo as infoma��es lidas do documento XML.
	 *
	 * @param nNode o n� associado aos Livros
	 * @return um novo Conjunto de Livros
	 */
	public static ConjLivros build(Node nNode) {
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element tlivros = (Element) nNode;

			Element livros = (Element) tlivros.getChildNodes();
			NodeList eLivros = livros.getElementsByTagName("Livro");

			ConjLivros conjLivros = new ConjLivros("Stock");

			for (int i = 0; i < eLivros.getLength(); i++){
				Node livro = eLivros.item(i);
				Livro l = Livro.build(livro);
				conjLivros.addSubLivro(l, "adicionar");

				Nome nome = Nome.build(livro);
				if(nome != null){
					conjLivros.getTitulo();
					conjLivros.getPreco();
					conjLivros.getCategorias();
				}
			}
			return conjLivros;
		}
		return null;
	}

	/**
	 * Cria um novo Elemento quer ir� representar, no documento XML, os Livros associados aos Livros correntes.
	 *
	 * @param doc o Documento que ir� ser usado para gerar o novo Element.
	 */
	public Element createElement(Document doc) {


		Element totalLivros = doc.createElement("TotalLivros");

		//criamos o elemento eventos que tamb�m ser� um elemento filho do festival, tal como o nome
		Element eLivros = doc.createElement("Livros");
		totalLivros.appendChild(eLivros);

		for(int i = 0; i < numLivros; i++){
			if(this.livros.get(i) != null){
				if (this.livros.get(i) instanceof Livro) {
					Element eLivro = (Element) this.livros.get(i).createElement(doc);
					eLivros.appendChild((Element) eLivro);
				}
			}
		}
		return totalLivros;
	}

	public static Object adSub (String titulo, float p, Categoria[] categorias, String decisao){
		try {
			File inputFile = new File("C:/Users/Eduardo/OneDrive - Instituto Superior de Engenharia de Lisboa/Desktop/LEIM/21-22SV/MP/Code/MoP/src/tp4_A/Biblioteca.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			XPath xpath = XPathFactory.newInstance().newXPath();
			String expression = "/Biblioteca/TotalLivros/*";
			NodeList nList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);

			Node nNode = nList.item(0);
			Nome nome = Nome.build(nNode);
			if (nome != null);

			Livro l = new Livro(titulo, p, categorias);
			System.out.println(l);

			if (nome instanceof ConjLivros){
				ConjLivros conj = (ConjLivros) nome;
				conj.addSubLivro(l, decisao);
				System.out.println(conj);
				Document newDoc = dBuilder.newDocument();


				Element rootElement = newDoc.createElement("Biblioteca");
				rootElement.appendChild(conj.createElement(newDoc));


				newDoc.appendChild(rootElement);

				FileOutputStream output = new FileOutputStream("C:/Users/Eduardo/OneDrive - Instituto Superior de Engenharia de Lisboa/Desktop/LEIM/21-22SV/MP/Code/MoP/src/tp4_A/Biblioteca.xml");
				writeXml(newDoc, output);
				conj.print("");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*public static void main (String[]args){

		try {
			File inputFile = new File("C:/Users/Eduardo/OneDrive - Instituto Superior de Engenharia de Lisboa/Desktop/LEIM/21-22SV/MP/Code/MoP/src/tp4/Biblioteca.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			XPath xpath = XPathFactory.newInstance().newXPath();
			String expression = "/Biblioteca/TotalLivros/*";
			NodeList nList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);

			Node nNode = nList.item(0);
			Nome nome = Nome.build(nNode);
			if (nome != null) ;

			Livro l = new Livro("1984", 15.5f, new Categoria[]{Categoria.FICCAO});

			if (nome instanceof ConjLivros){
				ConjLivros conj = (ConjLivros) nome;
				conj.addSubLivro(l, "adicionar");
				System.out.println(conj);
				//conj.addSubLivro(novo, "adicionar");
				Document newDoc = dBuilder.newDocument();

				/*DOMImplementation domImpl = newDoc.getImplementation();
				DocumentType doctype = domImpl.createDocumentType("doctype", "teste", "Biblioteca.dtd");
				newDoc.appendChild(doctype);*/

				/*Element rootElement = newDoc.createElement("Biblioteca");
				rootElement.appendChild(conj.createElement(newDoc));


				newDoc.appendChild(rootElement);

				FileOutputStream output = new FileOutputStream("C:/Users/Eduardo/OneDrive - Instituto Superior de Engenharia de Lisboa/Desktop/LEIM/21-22SV/MP/Code/MoP/src/tp4/Biblioteca.xml");
				writeXml(newDoc, output);
				conj.print("");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

	}*/
	/**
	 * Escreve, para o OutputStream, o documento doc.
	 * @param doc o documento contendo os Elementos a gravar on ficheiro output
	 * @param output o ficheiro de sa�da.
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
