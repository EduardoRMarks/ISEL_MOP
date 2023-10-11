import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;

/***
 * Esta classe ir� servir para mostrar o stock dispon�vel da biblioteca,
 * � chamada pela classe Biblioteca
 */

abstract class Stock {

	/*public static ConjLivros build(Node nNode) {
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element tlivros = (Element) nNode;

			Element livros = (Element) tlivros.getChildNodes();
			NodeList eLivros = livros.getElementsByTagName("Livro");

			ConjLivros conjLivros = new ConjLivros("Stock");

			for (int i = 0; i < eLivros.getLength(); i++){
				Node livro = eLivros.item(i);
				Livro l = Livro.build(livro);
				conjLivros.addSubLivro(l, "adicionar");
				l.print(l.getTitulo());

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
	}*/

	public static void stock(){
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
			ConjLivros nome = ConjLivros.build(nNode);
			if (nome != null) nome.print("");

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
