import java.util.Scanner;

public class Biblioteca {
	//vari�vel da password para puder usar a app
	private static String PASSWORD = "A45977";
	//vari�vel trabalhador, � o unico que pode adicionar ou vender livros
	private static String TRABALHADOR = "Eduardo";

	/**
	 * Mostra o stock se a op��o for selecionada
	 */
	public static boolean stock() {
		System.out.println("Consulta de Stock");
		Stock.stock();
		return true;
	}

	/**
	 * M�todo para chamar o m�todo para adicionar um livro e para confirmar se quem est� a
	 * adicionar o livro tem permiss�o para tal
	 */
	public static boolean adicionar() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Adicionar um livro ao stock");
		System.out.println("Insira o nome do empregado que est� a adiconar o livro");
		String nome = keyboard.nextLine();

		//v� se o nome do empregado � v�lido
		if (!nome.equalsIgnoreCase(TRABALHADOR)) {
			System.out.println("Esta pessoa n�o trabalha aqui, logo n�o pode adicionar um livro");
			return false;
		} else {
			AdicionarLivro.Adicao();
			System.out.println("\nLivro adicionado com sucesso");
			return true;
		}
	}

	/**
	 * M�todo para chamar o m�todo para vender um livro e para confirmar se quem est� a
	 * vender o livro tem permiss�o para tal
	 */
	public static boolean vender() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Vender um livro");
		System.out.println("Insira o nome do empregado que est� a vender o livro");
		String nome = keyboard.nextLine();

		//v� se o nome do empregado � v�lido
		if (!nome.equalsIgnoreCase(TRABALHADOR)) {
			System.out.println("Esta pessoa n�o trabalha aqui, logo n�o pode adicionar um livro");
			return false;
		}

		System.out.println("Insira o nome do Cliente");
		String nome2 = keyboard.nextLine();

		//valida se o nome do cliente � v�lido
		if (!validarNome(nome2)) {
			System.out.println("O nome do cliente � inv�lido");
			return false;
		} else {
			VendaLivro.Venda();
			System.out.println("\nLivro vendido com sucesso");
			return true;
		}
	}

	/**
	 * M�todo para validar um nome
	 *
	 * @param nome
	 * @return true/false
	 */
	public static boolean validarNome(String nome) {
		/*no fim da valida��o, a variavel nLetra tem de ser pelo menos 1
		 * para o nome ser considerado v�lido*/
		int nLetra = 0;

		for (int i = 0; i < nome.length(); i++) {
			//variavel auxiliar para guardar o caracter na posi��o i
			char aux = nome.charAt(i);
			//se o caracter n�o for uma letra nem um espe�o retorna false
			if (!Character.isLetter(aux) && !Character.isWhitespace(aux)) {
				return false;
			}
			//se o caracter for uma letra, incremente nLetra
			if (Character.isLetter(aux)) {
				nLetra++;
			}
		}
		if (nLetra >= 1) {
			return true;
		} else return false;
	}


	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		boolean sair = false;
		// AUTENTICA��O
		for (int tentativa = 0; tentativa < 3; tentativa++) {
			System.out.print("Introduza o c�digo de acesso, por favor: ");
			String codigo = keyboard.nextLine();
			if (codigo.equals(PASSWORD)) {
				break;
			} else {
				if (tentativa == 2) {
					System.out.println("\nAtingiu o n�mero m�ximo de tentativas, tente de novo mais tarde.");
					System.exit(0);
				}
				System.out.println("\nC�digo incorreto! (Tentativa " + (tentativa + 1) + "/3)");
				System.out.println("Tente outra vez.");
			}
		}

		System.out.println("\nBem Vindo!\n");
		do {
			System.out.println("Selecione o que pretende fazer das op��es seguintes:");
			System.out.println("a - Consultar stock");
			System.out.println("b - Adicionar um Livro");
			System.out.println("c - Vender um livro");
			System.out.println("f - Terminar");
			System.out.print("Op��o: ");
			String opcao = keyboard.nextLine().trim();
			if (opcao.length() != 1) {
				System.out.println("Op��o n�o reconhecida, tente de novo");
			} else {
				char c = opcao.charAt(0);
				switch (c) {
					case 'a':
						stock();
						break;
					case 'b':
						adicionar();
						break;
					case 'c':
						vender();
						break;
					case 'f':
						sair = true;
						break;
					default:
						System.out.println("Op��o n�o consta das op��es v�lidas");
				}
			}
			System.out.println("");
		} while (!sair);

		System.out.println("At� � pr�xima");

	}
}

