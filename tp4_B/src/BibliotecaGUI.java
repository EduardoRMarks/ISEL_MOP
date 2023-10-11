import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class BibliotecaGUI extends JFrame {
	private static ImageIcon icon = new ImageIcon("C:/Users/Eduardo/OneDrive - Instituto Superior de Engenharia de Lisboa/Desktop/LEIM/21-22SV/MP/Code/MoP/src/tp4/imagens/icon.png");

	//vari�vel da password para puder usar a app
	private static String PASSWORD = "A45977";
	//vari�vel trabalhador, � o unico que pode adicionar ou vender livros
	private static String TRABALHADOR = "Eduardo";
	//variavel para as tentativas
	private int tentativas = 0;

	public void logInGUI(){

		JFrame logInGUI = new JFrame("Bem vindo");
		logInGUI.setSize(512,512);
		logInGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel bibpanel = new JPanel(null);
		//.setOpaque(false) serve para n�o tapar a JFrame pois vai ter o mesmo tamanho
		bibpanel.setOpaque(false);
		bibpanel.setSize(512,512);

		JLabel login = new JLabel(new ImageIcon("..\\ISEL_MOP\\tp4_B\\imagens\\fundo.png"));
		logInGUI.setContentPane(login);
		logInGUI.setIconImage(icon.getImage());
		//abre a janela a meio do ecr�
		logInGUI.setLocationRelativeTo(null);
		//n�o deixa mexer no tamanho da janela
		logInGUI.setResizable(false);

		JLabel text = new JLabel("Inicie a sess�o para aceder � aplica��o");
		text.setBounds(133, 155, 512, 25);
		text.setForeground(new Color(220, 220, 220));
		bibpanel.add(text);

		JLabel ut = new JLabel("Utilizador ");
		ut.setBounds(133, 195, 512, 25);
		ut.setForeground(new Color(255, 200, 50));
		JTextField textUt = new JTextField(25);
		textUt.setBounds(194, 195, 165, 25);
		bibpanel.add(ut);
		bibpanel.add(textUt);

		JLabel pw = new JLabel("Password ");
		pw.setBounds(133, 235, 512, 25);
		pw.setForeground(new Color(255, 200, 50));
		JPasswordField textPw = new JPasswordField(25);
		textPw.setBounds(194, 235, 165, 25);
		bibpanel.add(pw);
		bibpanel.add(textPw);

		JLabel errado = new JLabel("");
		errado.setBounds(133, 305, 512, 25);
		errado.setForeground(new Color(150, 22, 12));
		bibpanel.add(errado);

		JButton entrar =  new JButton("Entrar");
		entrar.setBounds(289, 275, 70, 25);
		entrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String utilizador = textUt.getText();
				String password = textPw.getText();
				//if caso seja carregado no bot�o e uma das duas variaveis estejam vazias
				if (utilizador.equals("") || password.equals("")){
					return;
				}
				else if(utilizador.equals(TRABALHADOR) && password.equals(PASSWORD)){
					logInGUI.dispose();
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							menu();
						}
					});
				}
				else {
					textUt.setText("");
					textPw.setText("");
					if(tentativas < 2){
						errado.setText("Incorreto, tente de novo  (" + (tentativas + 1) + "/3)");
						tentativas++;
					}
					else {
						errado.setText("Tentativais excedidas");
						entrar.setEnabled(false);
					}
				}
			}
		});
		entrar.setBackground(new Color(255, 200, 50));
		entrar.setForeground(new Color(0, 100, 150));
		bibpanel.add(entrar);

		logInGUI.add(bibpanel);
		logInGUI.setVisible(true);
	}

	public void menu(){
		JFrame menuT = new JFrame("Bem vindo");
		menuT.setSize(512,512);
		menuT.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel menu = new JLabel(new ImageIcon("..\\ISEL_MOP\\tp4_B\\imagens\\fundo2.png"));
		menuT.setContentPane(menu);
		menuT.setIconImage(icon.getImage());
		//abre a janela a meio do ecr�
		menuT.setLocationRelativeTo(null);
		//n�o deixa mexer no tamanho da janela
		menuT.setResizable(false);
		setLayout(null);

		JPanel inf = new JPanel(new BorderLayout());
		inf.setBounds(98,10,300,90);
		inf.setOpaque(false);


		//Layout com gride 3 bot�es
		GridLayout grid = new GridLayout(3,1,0,20);
		JPanel panel = new JPanel(grid);
		panel.setBounds(98,160,300,260);
		panel.setOpaque(false);

		JLabel mb = new JLabel("");

		Timer tempo = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mb.setText("Menu da Biblioteca");
			}
		});
		tempo.start();
		mb.setFont(new Font("Agency FB", Font.BOLD, 40));
		mb.setForeground(new Color(255, 200, 50));
		mb.setHorizontalAlignment(JLabel.CENTER);
		mb.setVerticalAlignment(JLabel.CENTER);
		inf.add(mb, BorderLayout.NORTH);

		//vai ser feito agora o print da data atual
		JLabel data = new JLabel(LocalDate.now().toString());
		data.setFont(new Font("Agency FB", Font.BOLD, 40));
		data.setForeground(new Color(255, 200, 50));
		data.setHorizontalAlignment(JLabel.CENTER);
		data.setVerticalAlignment(JLabel.CENTER);
		inf.add(data, BorderLayout.CENTER);

		//cria��o dos bot�es do menu
		JButton BStock = new JButton("Stock");
		JButton BAdicionar = new JButton("Adicionar");
		JButton BVender = new JButton("Vender");

		//este arrayList foi criado para adicionar todos os bot�es ao JPanel com a grid
		ArrayList<JButton> BTotal =  new ArrayList<JButton>();
		BTotal.add(BStock);
		BTotal.add(BAdicionar);
		BTotal.add(BVender);

		LineBorder bordas = new LineBorder(new Color(0,162,232),4);
		Font letra = new Font("Agency FB", Font.BOLD, 40);

		for(JButton Baux : BTotal){
			Baux.setBackground(new Color(37, 153, 122));
			Baux.setForeground(new Color(255, 200, 50));
			Baux.setBorder(bordas);
			Baux.setFont(letra);
			panel.add(Baux);
		}


		BStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						StockGUI.verStock();
					}
				});
			}
		});
		BAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						AdicionarLivroGUI.AdicaoGUI();
					}
				});
			}
		});
		BVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						VendaLivroGUI.VendaGUI();
					}
				});
			}
		});

		menuT.add(inf);
		menuT.add(panel, SwingConstants.CENTER);

		menuT.setVisible(true);
	}

	public static void main(String[] args) {
		BibliotecaGUI b = new BibliotecaGUI();
		b.logInGUI();
		//b.menu();
	}
}
