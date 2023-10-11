import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdicionarLivroGUI extends ConjLivros {

	private static ImageIcon icon = new ImageIcon("C:/Users/Eduardo/OneDrive - Instituto Superior de Engenharia de Lisboa/Desktop/LEIM/21-22SV/MP/Code/MoP/src/tp4/imagens/icon.png");

	/**
	 * Metodo construtor do adicionar livro
	 */
	public AdicionarLivroGUI(String titulo, float p, Categoria[] categorias){
		super(titulo);
	}

	/**
	 * Metodo que vai ser chamado pela classe Biblioteca para
	 * efetuar a adi��o de um livro
	 */

	public static void AdicaoGUI(){

		Categoria[] cats = Categoria.values();

		JFrame janelaAdicao = new JFrame("Adicionar");
		janelaAdicao.setSize(512,512);
		janelaAdicao.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JLabel ja = new JLabel(new ImageIcon("..\\ISEL_MOP\\tp4_B\\imagens\\fundo3.png"));
		janelaAdicao.setContentPane(ja);
		janelaAdicao.setIconImage(icon.getImage());
		//abre a janela a meio do ecr�
		janelaAdicao.setLocationRelativeTo(null);
		//n�o deixa mexer no tamanho da janela
		janelaAdicao.setResizable(false);

		JPanel infad = new JPanel(new BorderLayout());
		infad.setBounds(98,30,300,90);
		infad.setOpaque(false);

		//Layout com gride para titulo do livro, pre�o e categoria
		JPanel panel = new JPanel(null);
		panel.setSize(512, 512);
		panel.setOpaque(false);

		JLabel labelAdd = new JLabel("Adi��o de um livro");
		labelAdd.setFont(new Font("Agency FB", Font.BOLD, 40));
		labelAdd.setBounds(20, 130, 512, 25);
		labelAdd.setForeground(new Color(255, 200, 50));
		labelAdd.setHorizontalAlignment(JLabel.CENTER);
		labelAdd.setVerticalAlignment(JLabel.CENTER);
		infad.add(labelAdd, BorderLayout.NORTH);


		JLabel titulo =  new JLabel("Insira o t�tulo do Livro");
		titulo.setFont(new Font("Arial", Font.BOLD, 20));
		titulo.setBounds(20, 130, 512, 25);
		titulo.setForeground(new Color(255,255,255));
		panel.add(titulo);
		JTextField textTitulo =  new JTextField(50);
		textTitulo.setBounds(20, 160, 165, 25);
		panel.add(textTitulo);

		JLabel preco =  new JLabel("Insira o pre�o do Livro");
		preco.setFont(new Font("Arial", Font.BOLD, 20));
		preco.setBounds(20, 190, 512, 25);
		preco.setForeground(new Color(255,255,255));
		panel.add(preco);
		JTextField textPreco =  new JTextField(50);
		textPreco.setBounds(20, 220, 165, 25);
		panel.add(textPreco);

		JLabel categoria1 =  new JLabel("Insira as categorias do");
		categoria1.setFont(new Font("Arial", Font.BOLD, 20));
		categoria1.setBounds(20, 250, 512, 25);
		categoria1.setForeground(new Color(255,255,255));
		panel.add(categoria1);
		JLabel categoria2 =  new JLabel("livro (sem espa�os");
		categoria2.setFont(new Font("Arial", Font.BOLD, 20));
		categoria2.setBounds(20, 270, 512, 25);
		categoria2.setForeground(new Color(255,255,255));
		panel.add(categoria2);
		JLabel categoria3 =  new JLabel("e at� 8 categorias)");
		categoria3.setFont(new Font("Arial", Font.BOLD, 20));
		categoria3.setBounds(20, 290, 512, 25);
		categoria3.setForeground(new Color(255,255,255));
		panel.add(categoria3);
		JTextField textCategorias =  new JTextField(50);
		textCategorias.setBounds(20, 320, 165, 25);
		panel.add(textCategorias);

		JButton adicionar =  new JButton("Adicionar");
		adicionar.setBounds(20, 360, 100, 25);

		adicionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String titulo = textTitulo.getText();
				String preco = textPreco.getText();
				String categ = textCategorias.getText();
				//se um dos espa�os estiver vazio, n�o adiciona nada
				if (titulo.equals("") || preco.equals("") || categ.equals("")){
					return;
				}
				else {
					textTitulo.setText("");
					textPreco.setText("");
					textCategorias.setText("");

					if(!isTitulo(titulo)){
						JOptionPane.showMessageDialog(new Frame(), "O t�tulo � inv�lido");
					}

					if(!isNumber(preco)){
						JOptionPane.showMessageDialog(new Frame(), "O pre�o s� pode conter n�meros");
					}
					float pAux = Float.parseFloat(preco);
					System.out.println(pAux);

					if(categ.length() > 8){
						JOptionPane.showMessageDialog(new Frame(), "Escolheu mais do que 8 categorias para o livro");
					}

					int aux = categ.length();
					Categoria[] numCat = new Categoria[categ.length()];

					for(int i = 0; i < categ.length(); i++){
						int c = Integer.parseInt(String.valueOf(categ.charAt(i)));
						if (c <= 0 || c > 8){
							JOptionPane.showMessageDialog(new Frame(), "Tem pelo menos uma categoria inv�lida");
							break;
						}
						numCat[i] = cats[c-1];
					}
					ConjLivros.adSub(titulo, pAux, numCat, "adicionar");
				}
			}
		});




		adicionar.setBackground(new Color(255, 200, 50));
		adicionar.setForeground(new Color(0, 100, 150));
		panel.add(adicionar);

		/***
		 * meter as categorias na janela para o utilizador saber quais s�o os n�meros a usar
		 */

		JLabel categorias =  new JLabel("Lista de categorias");
		categorias.setFont(new Font("Arial", Font.BOLD, 20));
		categorias.setBounds(300, 130, 512, 25);
		categorias.setForeground(new Color(229, 106, 16));
		panel.add(categorias);

		JLabel ACAO =  new JLabel("1- A��o");
		ACAO.setFont(new Font("Arial", Font.BOLD, 20));
		ACAO.setBounds(300, 160, 512, 25);
		ACAO.setForeground(new Color(229, 106, 16));
		panel.add(ACAO);
		JLabel BIBLIOGRAFIAS =  new JLabel("2- Bibliografias");
		BIBLIOGRAFIAS.setFont(new Font("Arial", Font.BOLD, 20));
		BIBLIOGRAFIAS.setBounds(300, 190, 512, 25);
		BIBLIOGRAFIAS.setForeground(new Color(229, 106, 16));
		panel.add(BIBLIOGRAFIAS);
		JLabel FICCAO =  new JLabel("3- Fic��o");
		FICCAO.setFont(new Font("Arial", Font.BOLD, 20));
		FICCAO.setBounds(300, 220, 512, 25);
		FICCAO.setForeground(new Color(229, 106, 16));
		panel.add(FICCAO);
		JLabel FICCAO_CIENTIFICA =  new JLabel("4- Fic��o Cient�fica");
		FICCAO_CIENTIFICA.setFont(new Font("Arial", Font.BOLD, 20));
		FICCAO_CIENTIFICA.setBounds(300, 250, 512, 25);
		FICCAO_CIENTIFICA.setForeground(new Color(229, 106, 16));
		panel.add(FICCAO_CIENTIFICA);
		JLabel HUMOR =  new JLabel("5- Humor");
		HUMOR.setFont(new Font("Arial", Font.BOLD, 20));
		HUMOR.setBounds(300, 280, 512, 25);
		HUMOR.setForeground(new Color(229, 106, 16));
		panel.add(HUMOR);
		JLabel POESIA =  new JLabel("6- Poesia");
		POESIA.setFont(new Font("Arial", Font.BOLD, 20));
		POESIA.setBounds(300, 310, 512, 25);
		POESIA.setForeground(new Color(229, 106, 16));
		panel.add(POESIA);
		JLabel REVISTAS =  new JLabel("7- Revistas");
		REVISTAS.setFont(new Font("Arial", Font.BOLD, 20));
		REVISTAS.setBounds(300, 340, 512, 25);
		REVISTAS.setForeground(new Color(229, 106, 16));
		panel.add(REVISTAS);
		JLabel ROMANCE =  new JLabel("8- Romance");
		ROMANCE.setFont(new Font("Arial", Font.BOLD, 20));
		ROMANCE.setBounds(300, 370, 512, 25);
		ROMANCE.setForeground(new Color(229, 106, 16));
		panel.add(ROMANCE);


		janelaAdicao.add(infad);
		janelaAdicao.add(panel);
		janelaAdicao.setVisible(true);
	}


	public static boolean isTitulo(String titulo){
		int aux = 0;
		for (int i = 0; i < titulo.length(); i++){
			if(!Character.isDigit(titulo.charAt(i)) && !Character.isLetter(titulo.charAt(i)) && !Character.isWhitespace(titulo.charAt(i))){
				return false;
			}
			if(Character.isDigit(titulo.charAt(i)) || Character.isLetter(titulo.charAt(i))){
				aux++;
			}
		}
		if(aux > 0){
			return true;
		}
		else return false;
	}

	public static boolean isNumber(String num){
		for(int i = 0; i < num.length(); i++){
			if(!Character.isDigit(num.charAt(i))){
				return false;
			}
		}
		return true;
	}
}
