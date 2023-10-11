import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.io.File;

public abstract class StockGUI {

	public static void verStock(){
		try {
			File inputFile = new File("..\\ISEL_MOP\\tp4_B\\src\\Biblioteca.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			XPath xpath = XPathFactory.newInstance().newXPath();
			String expression = "/Biblioteca/TotalLivros/*";
			NodeList nList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);

			Node nNode = nList.item(0);
			ConjLivros nome = ConjLivros.build(nNode);
			if (nome != null) JOptionPane.showMessageDialog(new Frame(), nome.print(""));

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
