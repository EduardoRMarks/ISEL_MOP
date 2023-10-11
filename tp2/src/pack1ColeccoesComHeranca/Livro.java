package pack1ColeccoesComHeranca;

/**
 * Classe que deverá suportar um livro
 */
public class Livro extends Obra {

	// número de páginas
	private int numPaginas;

	// preço do livro
	private float preco;

	// array de autores, este array não deve ter nulls
	private String[] autores;

	/**
	 * Deve criar um novo livro com os dados recebidos. O número de páginas não
	 * pode ser menor que 1. O preço não pode ser negativo. O array de autores
	 * não deve conter nem nulls e deve conter pelo menos um autor válido. Não
	 * pode haver repetições dos nomes dos autores, considera-se os nomes sem os
	 * espaços extra (ver removeExtraSpaces). Este método deve utilizar os
	 * métodos auxiliares existentes. Em caso de nome inválido deve lançar uma
	 * excepção de IllegalArgumentException com a indicação do erro ocorrido
	 */
	public Livro(String titulo, int numPaginas, float preco, String[] autores) {
		super(titulo);
		//nº de paginas não pode ser menor ou igual a 1
		if(numPaginas < 0)
			throw new IllegalArgumentException("O nº de páginas não pode ser negativo");
		else if(numPaginas == 0)
			throw new IllegalArgumentException("O nº de páginas não pode ser nulo");
		this.numPaginas = numPaginas;

		//preço tem que ser maior ou igual a zero
		if(preco < 0)
			throw new IllegalArgumentException("O preço não pode ser negativo");
		this.preco = preco;

		//o array de autores tem que ter pelo menos um autor
		if (autores == null || autores.length == 0)
			throw new IllegalArgumentException("O array de autores deve ter pelo menos um autor");
		/*os nomes dos autores têm que ser válidos, ou seja, só podem
		 * conter letras e espaços*/
		if (validarNomes(autores) == false)
			throw new IllegalArgumentException("Um ou mais nomes do array de autores não é válido");
		//remove os espaços entre os nomes
		for (int i = 0; i < autores.length; i++){
			autores[i] = removeExtraSpaces(autores[i]);
		}
		//não podem haver autores repetidos no array de autores
		if(haRepeticoes(autores))
			throw new IllegalArgumentException("O array de autores contem autores repetidos");
		this.autores = autores;
	}

	/**
	 * Devolve o número de páginas do livro
	 */
	public int getNumPaginas() {
		return this.numPaginas;
	}

	/**
	 * Devolve o preço do livro
	 */
	public float getPreco() {
		return this.preco;
	}

	/**
	 * Devolve true se o autor recebido existe como autor do livro. O nome
	 * recebido não contém espaços extra.
	 */
	public boolean contemAutor(String autorNome) {
		//se o nome recebido for null devolve false
		if(autorNome == null){
			return false;
		}
		/*À semelhança do que foi feito no metodo haRepeticoes vamos comparar o nome recebido com
		 * todos os autores do livro*/
		for(int i = 0; i < autores.length; i++){
			//se o nome recebido fizer parte do array autores então retorna true
			if(autorNome.equals(autores[i])){
				return true;
			}
		}
		//se não fizer parte retorna false
		return false;
	}

	/**
	 * Devolve uma cópia do array de autores do livro
	 */
	public String[] getAutores() {
		//.clone faz uma copia dos autores
		return autores.clone();
	}

	/**
	 * Devolve uma string com a informação do livro (ver outputs desejados)
	 */
	public String toString() {
		//super.toString() chama o método da classe Obra, visto que Livro herda desta
		return super.toString() + ", autores " + java.util.Arrays.toString(getAutores());
	}

	/**
	 * Iguais se equais no contexto de obra e se o objecto recebido for um Livro.
	 * Deve utilizar o método equals de Obra
	 */
	public boolean equals(Object l) {
		//Primeiro confirma se é um livro, e se não é nulo, e depois chama o equals da classe Obra
		if (l != null && l instanceof Livro){
			return super.equals(l);
		}
		//Se as condições não se verificarem, devolve false
		return false;
	}

	/**
	 * main
	 */
	public static void main(String[] args) {

		// constructor e toString
		Livro l = new Livro("Viagem aos Himalaias", 340, 12.3f,
				new String[] { "João Mendonça", "Mário Andrade" });
		System.out.println("Livro -> " + l);
		l.print("");
		l.print("-> ");
		System.out.println();

		// contém autor
		String autorNome = "Mário Andrade";
		System.out.println("Livro com o autor " + autorNome + "? -> "
				+ l.contemAutor(autorNome));
		autorNome = "Mário Zambujal";
		System.out.println("Livro com o autor " + autorNome + "? -> "
				+ l.contemAutor(autorNome));
		System.out.println();

		// equals
		System.out.println("Livro: " + l);
		System.out.println("equals Livro: " + l);
		System.out.println(" -> " + l.equals(l));

		Livro l2 = new Livro("Viagem aos Himalaias", 100, 10.3f,
				new String[] { "Vitor Záspara" });
		System.out.println("Livro: " + l);
		System.out.println("equals Livro: " + l2);
		System.out.println(" -> " + l.equals(l2));
		System.out.println();

		// testes que dão excepção - mostra-se a excepção

		// livro lx1
		System.out.println("Livro lx1: ");
		try {
			Livro lx1 = new Livro("Viagem aos Himalaias", -1, 12.3f,
					new String[] { "João Mendonça", "Mário Andrade" });
			System.out.println("Livro lx1: " + lx1);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		System.out.println();

		// livro lx2
		System.out.println("Livro lx2: ");
		try {
			Livro lx2 = new Livro("Viagem aos Himalaias", 200, -12.3f,
					new String[] { "João Mendonça", "Mário Andrade" });
			System.out.println("Livro lx2: " + lx2);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		System.out.println();

		// livro lx3
		System.out.println("Livro lx3: ");
		try {
			Livro lx3 = new Livro(null, 200, -12.3f,
					new String[] { "João Mendonça", "Mário Andrade" });
			System.out.println("Livro lx3: " + lx3);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		System.out.println();

		// livro lx4
		System.out.println("Livro lx4: ");
		try {
			Livro lx4 = new Livro("Viagem aos Himalaias", 200, 12.3f,
					new String[] { "João Mendonça", "Mário Andrade",
							"João Mendonça" });
			System.out.println("Livro lx4: " + lx4);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
	}
}
