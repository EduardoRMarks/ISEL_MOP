import java.util.Scanner;

public class AdicionarLivro extends ConjLivros{

	/**
	 * Metodo construtor do adicionar livro
	 */
	public AdicionarLivro(String titulo, float p, Categoria[] categorias){
		super(titulo);
	}

	/**
	 * Metodo que vai ser chamado pela classe Biblioteca para
	 * efetuar a adi��o de um livro
	 */
	public static void Adicao(){
		Categoria[] categorias = Categoria.values();

		System.out.println("Adicionar um livro");
		//inicializa��o de vari�veis
		String titulo;
		float preco;
		int cat;
		int quantidade;
		int c = 0;

		Scanner keyboard = new Scanner(System.in);

		System.out.println("Introduza o nome do livro: ");
		titulo = keyboard.nextLine();

		//ciclo do-while, so sai quando o pre�o � maior que 0
		do {
			System.out.println("Introduza o pre�o do livro: ");
			preco = keyboard.nextFloat();
			if(preco <= 0){
				System.out.println("O pre�o introduzido n�o pode ser negativo");
			}
		}while (preco <= 0);

		//ciclo do-while, so sai quando o numero de categorias estiver entre 1 e 8
		do {
			System.out.println("Introduza o n�mero de Categorias do livro: ");
			quantidade = keyboard.nextInt();
			if(quantidade <= 0 || quantidade >= 8){
				System.out.println("O n�mero de categorias n�o pode ser menor que 1 nem maior que 7");
			}
		}while (quantidade <= 0 || quantidade >= 8);

		//cria��o de um array de categorias com o numero anterior
		Categoria[] numCat = new Categoria[quantidade];
		//aux vai iterar negativamente para serem adicionadas as x categorias
		int aux = quantidade;

		//ciclo do-while para adicionar as categorias
		do {
			System.out.println("Introduza a(s) Categoria(s) do livro: 1)ACAO, 2)BIBLIOGRAFIAS, 3)FICCAO, 4)FICCAO_CIENTIFICA, 5)HUMOR, 6)POESIA, 7)REVISTAS, 8)ROMANCE ou 10 para terminar a adi��o");
			cat = keyboard.nextInt();
			if(cat <= 0 || cat >= 10){

				System.out.println("Escolha um n�mero dos enunciados");
			}
			if(1 <= cat && cat <= 8){
				numCat[c] = categorias[cat-1];
				c++;
				aux--;
			}

		}while (aux != 0);
		//chamada do metodo da classe ConjLivros para, neste caso, adicionar o livro
		ConjLivros.adSub(titulo, preco, numCat, "adicionar");
	}

	/*public static void main(String[] args){
		Categoria[] categorias = Categoria.values();

		System.out.println("Adicionar um livro");
		String titulo;
		float preco;
		int cat;
		int quantidade;
		int c = 0;


		ArrayList<Categoria> array = new ArrayList<Categoria>();

		Scanner keyboard = new Scanner(System.in);

		System.out.println("Introduza o nome do livro: ");
		titulo = keyboard.nextLine();

		do {
			System.out.println("Introduza o pre�o do livro: ");
			preco = keyboard.nextFloat();
			if(preco <= 0){
				System.out.println("O pre�o introduzido n�o pode ser negativo");
			}
		}while (preco <= 0);

		do {
			System.out.println("Introduza o n�mero de Categorias do livro: ");
			quantidade = keyboard.nextInt();
			if(quantidade <= 0 || quantidade >= 8){
				System.out.println("O n�mero de categorias n�o pode ser menor que 1 nem maior que 7");
			}
		}while (quantidade <= 0);

		Categoria[] numCat = new Categoria[quantidade];
		int aux = quantidade;

		do {
			System.out.println("Introduza a(s) Categoria(s) do livro: 1)ACAO, 2)BIBLIOGRAFIAS, 3)FICCAO, 4)FICCAO_CIENTIFICA, 5)HUMOR, 6)POESIA, 7)REVISTAS, 8)ROMANCE ou 10 para terminar a adi��o");
			cat = keyboard.nextInt();
			if(cat <= 0 || cat >= 10){

				System.out.println("Escolha um n�mero dos enunciados");
			}
			if(1 <= cat && cat <= 8){
				numCat[c] = categorias[cat-1];
				System.out.println(c);
				c++;
				aux--;
			}

		}while (aux != 0);
		ConjLivros.adSub(titulo, preco, numCat, "adicionar");
	}*/
}
