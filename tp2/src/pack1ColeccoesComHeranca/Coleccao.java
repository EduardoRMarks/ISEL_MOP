package pack1ColeccoesComHeranca;

import java.util.Arrays;

/**
 * Classe Coleccao, deve conter a descrição de uma colecção, com título, os seus
 * livros, colecções e editores. Deve utilizar herança para guardar os livros e
 * as colecções num só array
 */
public class Coleccao extends Obra {
	// prefixo a colocar no início de cada print mais interno que o corrente
	public static final String GENERALPREFIX = "  ";

	// número máximo de obras de uma colecção
	private static int MAXOBRAS = 20;

	// Array de obras, de Livros ou Coleccçõe, em que estas devem encontrar-se
	// sempre nos menores índices e pela ordem de registo
	private IObra[] obras = new IObra[MAXOBRAS];

	// deverá conter sempre o número de obras na colecção
	private int numObras = 0;

	// Editores, tem as mesmas condicionantes que array de autores na classe
	// livro
	private String[] editores;

	/**
	 * Construtor; o título deve ser guardado e validado na clase obra; o array de
	 * editores devem ser pelo menos um e tem as mesmas restrições que os autores
	 * dos livros;
	 */
	public Coleccao(String titulo, String[] editores) {
		// titulo
		super(titulo);

		//o array de autores tem que ter pelo menos um autor
		if (editores == null || editores.length == 0)
			throw new IllegalArgumentException("O array de autores deve ter pelo menos um autor");
		/*os nomes dos editores têm que ser válidos, ou seja, só podem
		 * conter letras e espaços*/
		if (Livro.validarNomes(editores) == false)
			throw new IllegalArgumentException("Um ou mais nomes do array de autores não é válido");
		//remove os espaços entre os nomes
		for (int i = 0; i < editores.length; i++){
			editores[i] = Livro.removeExtraSpaces(editores[i]);
		}
		//não podem haver editores repetidos no array de editores
		if(Livro.haRepeticoes(editores))
			throw new IllegalArgumentException("O array de autores contem autores repetidos");
		this.editores = editores;
	}

	/**
	 * Obtem o número total de páginas da colecção, páginas dos livros e das
	 * colecções
	 */
	public int getNumPaginas() {
		int nPaginas = 0;
		/*ciclo for para fazer a contagem do numero de paginas, somando sucessivamente
		 * o numero de paginas tendo em conta o metodo da classe Livro (getNumPaginas)*/
		for(int i = 0; i < numObras; i++){
			nPaginas += obras[i].getNumPaginas();
		}
		return nPaginas;
	}

	/**
	 * As colecções com mais de 5000 páginas nos seus livros directos têm um
	 * desconto de 20% nesses livros. As colecções em que o somatório de páginas das
	 * suas subcolecções directas seja igual ou superior ao quádruplo do nº de
	 * páginas da sua subcolecção directa com mais páginas deverão aplicar um
	 * desconto de 10% sobre os preços das suas subcolecções
	 */

	public float getPreco() {
		//pTotal é float pois o preco que é declarado é float
		float pTotal = 0;
		//PrecoLivros e PrecoSubcolecoes são float pois o preco que é declarado é float
		float PrecoLivros = 0;
		float PrecoSubcolecoes = 0;
		//paginas são declaradas em int
		int PaginasSubcolecoes = 0;
		int PaginasLivrosDiretos = 0;
		int maiorNumerodePaginas = 0;

		for (int i = 0 ; i < numObras ; i++) {
			// No caso dos livros, queremos apenas os somatório das páginas e dos preços
			if (obras[i] instanceof Livro) {
				//adicionamos o numero de paginais ao total da coleção
				PaginasLivrosDiretos += obras[i].getNumPaginas();
				//adicionamos o preço do livro ao total da coleção
				PrecoLivros += obras[i].getPreco();
			}
			// No caso das coleções, queremos ainda guardar o tamanho da maior coleção
			if (obras[i] instanceof Coleccao) {
				//adicionamos o numero de paginais ao total da coleção
				PaginasSubcolecoes += obras[i].getNumPaginas();
				//adicionamos o preço do livro ao total da coleção
				PrecoSubcolecoes += obras[i].getPreco();
				/*se o numero de paginas for maior do que qualquer outro livro então muda-se
				 * a variavel livroMaior para o novo valor*/
				if (obras[i].getNumPaginas() >= maiorNumerodePaginas) {
					maiorNumerodePaginas = obras[i].getNumPaginas();
				}
			}
		}

		//se o numero de paginas da coleção for maior que 5000, o preço tem 20% de desconto
		if (PaginasLivrosDiretos > 5000){
			PrecoLivros *= 0.8f;
		}
		//se o numero de paginas da coleção for 4 vezes maior que o maior livro o preço tem 10% de desconto
		if (PaginasSubcolecoes >= maiorNumerodePaginas * 4){
			PrecoSubcolecoes = PrecoSubcolecoes * 0.9f;
		}
		pTotal = PrecoLivros + PrecoSubcolecoes;
		return (pTotal);

	}

	/**
	 * Adiciona uma obra à colecção se puder, se esta não for null e a colecção não
	 * ficar com obras com iguais no seu nível imediato. Deve utilizar o método
	 * getIndexOfLivro e getIndexOfColeccao
	 */
	public boolean addObra(IObra obra) {
		//Começamos por ver se a obra é válida e se não existe já na coleção
		if((obra != null) && (getIndexOfObra((obra.getTitulo())) == -1)){
			/*se a obra entrar no if coloca-se na primeira posição vazia da
			 * coleção e incrementa-se a variável de contagem do numero de obras*/

			//se o numero de obras na coleção já está no seu máximo então não podemos adcionar
			if(numObras == MAXOBRAS){
				return false;
			}
			//caso contrario podemos adicionar
			else {
				obras[numObras] = obra;
				numObras++;
				//retorna-se true pois foi adicionado a obra à coleção
				return true;
			}
		}
		//retorna false se a obra não for adicionado à coleção
		return false;
	}

	/**
	 * Devolve o index no array de obras onde estiver a obra com o nome pretendido.
	 * Devolve -1 caso não o encontre
	 */
	private int getIndexOfObra(String titulo) {
		/*se a obra na posição 0 for null significa que não há obras na
		 * coleção, ou seja, não vale a pena procura o index da obra*/
		if(obras[0] == null){
			return -1;
		}
		//ciclo for para encontrar a posição da obra na coleção
		for(int i = 0; i < numObras; i++){
			//se encontrar a obra na coleção retorna a posição i da obra
			if((obras[i].getTitulo()).equals(titulo)){
				return i;
			}
		}
		//caso não encontre a obra retorna -1
		return -1;
	}

	/**
	 * Remove do array a obra com o título igual ao título recebido. Devolve a obra
	 * removida ou null caso não tenha encontrado a obra. Deve-se utilizar o método
	 * getIndexOfLivro. Recorda-se que as obras ocupam sempre os menores índices, ou
	 * seja, não pode haver nulls entre elas.
	 */
	public IObra remObra(String titulo) {
		//variavel para guardar a posição da obra a remover
		int posicao = getIndexOfObra(titulo);
		if(posicao == -1){
			return null;
		}
		//variavel para retornar a obra que foi removida
		IObra ObraRemovida = obras[posicao];
		/*ciclo for para mudar a posição das obras depois da obra que foi removida uma
		 * posição para a esquerda, para a coleção não ficar com um lugar vazio*/
		for(int i = posicao; i < numObras; i++){
			/*copia a obra que esta na posição seguinte até ao penultimo livro, pois
			 * a ultima posição ficara como null se a coleção estivesse cheia*/
			if(i < MAXOBRAS-1) {
				obras[i] = obras[i + 1];
			}
			else obras[i] = null;
		}
		//remove uma unidade no numero de obras
		numObras--;

		return ObraRemovida;
	}

	/**
	 * Remove todas as obras (livros ou colecções) dentro da obra corrente, que
	 * tenham um título igual ou título recebido. Devolve true se removeu pelo menos
	 * uma obra, ou false caso não tenha trealizado qualquer remoção. Deve utilizar
	 * os métodos remObra e remAllObra.
	 */
	public boolean remAllObra(String titulo) {
		if (getIndexOfObra(titulo) != -1) {
			remObra(titulo);
			//vamos remover todas as obras recorrendo a recursividade
			remAllObra(titulo);
			return true;
		}
		/*não irá entrar no if quando já não houver obras para remover, ou caso nunca tenha havido obras para
		* a remoção ser feita, nesse caso retorna false*/
		return false;
	}

	/**
	 * Devolve o nº de obras de uma pessoa. Cada colecção deve contabilizar-se como
	 * uma obra para os editores.
	 */
	public int getNumObrasFromPerson(String autorEditor) {
		//variavel para fazer a contagem de obras
		int nObras = 0;

		//primeiro tratamos dos livros individualmente
		//primeiro for serve para ver se o autor editou alguma coleção
		for(int i = 0; i < editores.length; i++){
			if(autorEditor.equals(editores[i])){
				nObras++;
			}
		}
		//este ciclo irá servir para ver quantas obras o autor editou
		for(int j = 0; j < numObras; j++){
			if (obras[j] instanceof Livro) {
				//variavel auxiliar para saber quantos autores existem no livro
				int auxAutores = ((Livro) obras[j]).getAutores().length;
				//ciclo para ver se o nome do autor conta no livro
				for(int k = 0; k < auxAutores; k++) {
					//comparamos o nome k da obra com o autor recebido e se for igual adicionado uma obra
					if (autorEditor.equals(((Livro) obras[j]).getAutores()[k])) {
						nObras++;
					}
				}
			}
			/*se for uma subcoleção, guarda-se como tal e usamos recursividade para saber o número
			* de obras, adicionado à variável nObras*/
			if (obras[j] instanceof Coleccao) {
				Coleccao colAux = (Coleccao) obras[j];
				nObras += colAux.getNumObrasFromPerson((autorEditor));
			}
		}
		//no retorna-se o nº de obras
		return nObras;
	}

	/**
	 * Deve devolver um novo array, sem repetições, com os livros de que o autor
	 * recebido é autor. O array devolvido não deve conter repetições, para excluir
	 * as repetições devem utilizar o método mergeWithoutRepetitions
	 */
	public Livro[] getLivrosComoAutor(String autorNome) {
		/*criamos uma varialvel interira para fazermos a contagem do numero de
		 * livros que o autor recebido faz parte*/
		int nLivros = 0;
		//vamos fazer dois ciclos
		//o primeiro ciclo serve para "escolher" cada livro da coleçao
		for(int i = 0; i < numObras; i++){
			//if para garantir que é um livro
			if(obras[i] instanceof Livro) {
				Livro livroAux = (Livro) obras[i];
				//obtemos assim o tamanho da lista de autores
				int numAutores = livroAux.getAutores().length;
				//o segundo ciclo serve para correr cada autor do livro previamente escolhido
				for (int j = 0; j < numAutores; j++) {
					//se o autor recebido fizer parte dos autores do livro, incrementa-se nLivros
					if (autorNome.equals(livroAux.getAutores()[j])){
						nLivros++;
						//fazemos um break pois caso encontre o autor nao precisa de continuar a procurar
						break;
					}
				}
			}
		}
		/*cria-se entao o array de livros dos quais o autor faz parte, com n posiçoes vazias
		 * posiçoes essas que vao ser preenchidas no seguimento do codigo*/
		Livro[] stringLivros = new Livro[nLivros];
		//criamos uma variavel auxiliar para guardar o livro da posiçao correta do array
		int aux = 0;

		//para a adiçao dos livros no array de livros fazemos um array praticamete igual ao anterior
		//o primeiro ciclo serve para "escolher" cada livro da coleçao
		for(int i = 0; i < numObras; i++) {
			//preenchemos assim o array com os livros
			if(obras[i] instanceof Livro) {
				Livro livroAux = (Livro) obras[i];
				//obtemos assim o tamanho da lista de autores
				int numAutores = livroAux.getAutores().length;
				//o segundo ciclo serve para correr cada autor do livro previamente escolhido
				for (int j = 0; j < numAutores; j++) {
					//se o autor recebido fizer parte dos autores do livro, adiciona-se o livro ao array e incrementa-se aux
					if (autorNome.equals(livroAux.getAutores()[j])) {
						stringLivros[aux] = livroAux;
						aux++;
						//fazemos um break pois caso encontre o autor nao precisa de continuar a procurar
						break;
					}
				}
			}
		}
		/*à semelhança do que foi feito no metodo anterior fazemos chamar o proprio metodo
		 * com a diferença que iremos juntar com o mergeWithoutRepetitions*/
		for(int i = 0; i < numObras; i++){
			//com este if temos a certeza que estamos a trabalhar com uma subcoleção
			if(obras[i] instanceof Coleccao) {
				Coleccao colAux = (Coleccao) obras[i];
				stringLivros = mergeWithoutRepetitions(stringLivros, colAux.getLivrosComoAutor(autorNome));
			}
		}
		return stringLivros;
	}

	/**
	 * Deve devolver um array, sem nulls, com todos os autores e editores existentes
	 * na colecção. O resultado não deve conter repetições. Deve utilizar o método
	 * mergeWithoutRepetitions
	 */
	public String[] getAutoresEditores() {
		//se uma coleçao não tiver livros retorna os editores
		if(getNumLivros() == 0){
			return editores;
		}
		//criamos um array que começa por acomodar o array de editores
		String[] AutoresEditores = editores;

		/*neste ciclo iremos fazer merge dos autores das obras sem haver repetição, ou seja, se
		 * houverem dois livros com o mesmo autor, o seu nome só irá aparecer uma vez no array final*/
		for(int i = 0; i < numObras; i++){
			//se for um livro guarda-se como tal e acrescentam-se os seus autores ao array
			if(obras[i] instanceof Livro) {
				Livro livroAux = (Livro) obras[i];
				AutoresEditores = mergeWithoutRepetitions(AutoresEditores, livroAux.getAutores());
			}
			//se for uma subcoleção utilizamos recursividade para tratar de cada livro da subcoleção
			if(obras[i] instanceof Coleccao) {
				Coleccao colAux = (Coleccao) obras[i];
				AutoresEditores = mergeWithoutRepetitions(AutoresEditores, colAux.getAutoresEditores());
			}
		}
		//fazemos o mesmo que foi feito no getLivrosComoAutor

		return AutoresEditores;
	}

	/**
	 * Método que recebendo dois arrays sem repetições devolve um novo array com
	 * todos os elementos dos arrays recebidos mas sem repetições
	 */
	private static String[] mergeWithoutRepetitions(String[] a1, String[] a2) {
		/*vamos começar por ver quantos nomes há repetidos nas duas string utilizando 2
		 * ciclos for, para o array final ser a1 + a2 - repetidos*/
		int repetidos = 0;
		for(int i = 0; i < a1.length; i++){
			for(int j = 0; j < a2.length; j++){
				//se o nome de a1[i] e a2[j] forem igual entao a variavel repetidos é incrementada
				if(a1[i].equals(a2[j])){
					repetidos++;
				}
			}
		}
		//criamos então a string que irá ter os dois arrays sem repetição
		String[] mwr = new String[a1.length + a2.length - repetidos];

		//variável para guardar a posição onde o nome é adicionado
		int n = 0;

		/*começamos por atribuir todos os nomes do a1 ao novo array visto que
		 * as repetições vão ser retiradas de a2*/
		for(int k = 0; k < a1.length; k++){
			mwr[n] = a1[k];
			n++;
		}
		//por fim iremos juntar os nomes não repetidos de a2
		for(int l = 0; l < a2.length; l++) {
			//criamos um boolean auxiliar para saber se há valores iguais na comparação
			boolean igual = false;

			for(int m = 0; m < a1.length; m++){
				/*se o nome a2[l] for igual a um nome de a1[m]
				 * a varialvel igual torna-se true e há um break
				 * pois não precisamos de fazer mais comparações*/
				if(a2[l].equals(a1[m])){
					igual = true;
					break;
				}
			}
			/*se o ciclo de comparação acabar e o nome de a2 não for repetido então adiciona-se
			 * ao array final e incrementa-se n*/
			if(!igual){
				mwr[n] = a2[l];
				n++;
			}
		}
		return mwr;
	}

	/**
	 * Método idêntico ao método anterior mas agora com arrays de livros
	 */
	private static Livro[] mergeWithoutRepetitions(Livro[] a1, Livro[] a2) {
		/*vamos começar por ver quantos nomes há repetidos nas duas string utilizando 2
		 * ciclos for, para o array final ser a1 + a2 - repetidos*/
		int repetidos = 0;
		for(int i = 0; i < a1.length; i++){
			for(int j = 0; j < a2.length; j++){
				//se o nome de a1[i] e a2[j] forem igual entao a variavel repetidos é incrementada
				if(a1[i].equals(a2[j])){
					repetidos++;
				}
			}
		}
		//criamos então a string que irá ter os dois arrays sem repetição
		Livro[] mwr = new Livro[a1.length + a2.length - repetidos];

		//variável para guardar a posição onde o nome é adicionado
		int n = 0;

		/*começamos por atribuir todos os nomes do a1 ao novo array visto que
		 * as repetições vão ser retiradas de a2*/
		for(int k = 0; k < a1.length; k++){
			mwr[n] = a1[k];
			n++;
		}
		//por fim iremos juntar os nomes não repetidos de a2
		for(int l = 0; l < a2.length; l++) {
			//criamos um boolean auxiliar para saber se há valores iguais na comparação
			boolean igual = false;

			for(int m = 0; m < a1.length; m++){
				/*se o nome a2[l] for igual a um nome de a1[m]
				 * a varialvel igual torna-se true e há um break
				 * pois não precisamos de fazer mais comparações*/
				if(a2[l].equals(a1[m])){
					igual = true;
					break;
				}
			}
			/*se o ciclo de comparação acabar e o nome de a2 não for repetido então adiciona-se
			 * ao array final e incrementa-se n*/
			if(!igual){
				mwr[n] = a2[l];
				n++;
			}
		}
		return mwr;
	}

	/**
	 * Devolve o nº de livros dentro da colecção
	 */
	public int getNumLivros() {
		//variável para realizar a contagem
		int numLivros = 0;
		for(int i = 0; i < numObras; i++){
			//se a obra em questão for um livro, incrementa-se a contagem
			if(obras[i] instanceof Livro){
				numLivros++;
			}
			//caso seja uma subcoleção, usamos recursividade para incrementar a contagem
			else{
				numLivros += ((Coleccao) obras[i]).getNumLivros();
			}
		}
		return numLivros;
	}

	/**
	 * Devolve o nº de colecções dentro da colecção
	 */
	public int getNumColeccoes() {
		//variável para realizar a contagem
		int numColeccoes = 0;
		for (int i = 0; i < numObras ; i++) {
			//if para so adicionarmos subcoleções
			if (obras[i] instanceof Coleccao) {
				numColeccoes++;
				((Coleccao) obras[i]).getNumColeccoes();
			}
		}
		return numColeccoes;
	}

	/**
	 * Devolve a profundidada de máxima de uma colecção em termos de coleccões
	 * dentro de coleccções: uma colecção c1 com uma colecção c2 dentro, c1 deve
	 * devolver 2 e c2 deve devolver 1, independentemente do número do conteúdo de
	 * cada uma.
	 */
	public int getProfundidade() {
		// A profundidade será sempre no mínimo 1
		int mProf = 1;
		//variável para realizar a contagem
		int prof = 1;

		for (int i = 0 ; i < numObras ; i++) {
			//se encontrarmos uma coleção
			if (obras[i] instanceof Coleccao) {
				// Vamos buscar recursivamente a sua profundidade
				prof = ((Coleccao) obras[i]).getProfundidade() + 1;
				
				//se a profundidade atual for a maior até a este ponto
				if (prof >= mProf) {
					//guardamos como a nova maior
					mProf = prof;
				}
				
				/* Quando se chega à última subcoleção, retorna-se a maior,
				 * caso contrário, retorna-se a contagem atual*/
				if (i == numObras-1) {
					return mProf;
				}
				else {
					return prof;
				}
			}
		}
		//quando há uma subColeção sem subcoleções, retorna 1
		return prof;
	}

	/**
	 * Duas colecções são iguais se tiverem o mesmo título e a mesma lista de
	 * editores. Deve utilizar o equals da classe Obra. Para verificar verificar se
	 * os editores são os mesmos devem utilizar o método mergeWithoutRepetitions
	 */
	public boolean equals(Object c) {
		if(c == null && !(c instanceof Coleccao)){
			return false;
		}
		Coleccao col = (Coleccao) c;
		String[] auxEditores = mergeWithoutRepetitions(editores, col.editores);

		/* Se titulo for igual (equals de obra) e se todos os arrays de editores tratados
		 * tiverem a mesma dimensão (ou seja, são iguais), irá retornar true*/
		return super.equals(c) && auxEditores.length == editores.length && auxEditores.length == col.editores.length;
	}

	/**
	 * Deve devolver uma string compatível com os outputs desejados
	 */
	public String toString() {
		return super.toString() + ", editores " + Arrays.toString(editores) + ", com " + getNumLivros() + " livros, com "
				+ getNumColeccoes() + " coleções e com profundidade máxima de " +getProfundidade();
	}

	/**
	 * Mostra uma colecção segundo os outputs desejados. Deve utilizar o método
	 * print da classe Obra.
	 */
	public void print(String prefix) {
		//efetua o print da string recebida mais as informações recorrendo ao método toString
		System.out.println(prefix + toString());
		//prefixo e print de cada livro de uma coleção em cada linha
		for (int i = 0 ; i < numObras ; i++){
			obras[i].print(prefix + GENERALPREFIX);
		}
	}

	/**
	 * main
	 */
	public static void main(String[] args) {
		Livro l1 = new Livro("Viagem aos Himalaias", 340, 12.3f, new String[] { "João Mendonça", "Mário Andrade" });
		Livro l2 = new Livro("Viagem aos Pirinéus", 270, 11.5f, new String[] { "João Mendonça", "Júlio Pomar" });

		Coleccao c1 = new Coleccao("Primavera", new String[] { "João Mendonça", "Manuel Alfazema" });

		boolean res;

		res = c1.addObra(l1);
		res = c1.addObra(l2);
		System.out.println("c1 -> " + c1);
		c1.print("");
		System.out.println();

		// adicionar um livro com nome de outro já existente
		res = c1.addObra(l2);
		System.out.println("adição novamente de Viagem aos Pirinéus a c1 -> " + res);
		System.out.println("c1 -> " + c1);
		System.out.println();

		// Outra colecção
		Livro l21 = new Livro("Viagem aos Himalaias 2", 340, 12.3f, new String[] { "João Mendonça", "Mário Andrade" });
		Livro l22 = new Livro("Viagem aos Pirinéus 2", 270, 11.5f, new String[] { "João Mendonça", "Júlio Pomar" });

		Coleccao cx2 = new Coleccao("Outono", new String[] { "João Mendonça", "Manuel Antunes" });
		cx2.addObra(l21);
		cx2.addObra(l22);
		System.out.println("cx2 -> " + cx2);
		cx2.print("");
		System.out.println();

		// adicioná-la a c1
		c1.addObra(cx2);
		System.out.println("c1 após adição da colecção cx2 -> " + c1);
		c1.print("");
		System.out.println();

		// get editores autores
		String[] ae = c1.getAutoresEditores();
		System.out.println("Autores editores of c1 -> " + Arrays.toString(ae));
		System.out.println();

		// getNumObrasFromPerson
		String nome = "João Mendonça";
		int n = c1.getNumObrasFromPerson(nome);
		System.out.println("Nº de obras de " + nome + " -> " + n);
		System.out.println();

		// getLivrosComoAutor
		nome = "João Mendonça";
		Livro[] livros = c1.getLivrosComoAutor(nome);
		System.out.println("Livros de " + nome + " -> " + Arrays.toString(livros));
		System.out.println();
		System.out.println();

		// testes aos métodos getNumLivros, getNumColeccoes e getProfundidade
		c1.print("");
		System.out.println("Nº de livros na colecção " + c1.getTitulo() + " -> " + c1.getNumLivros());

		System.out.println("Nº de colecções dentro da colecção " + c1.getTitulo() + " -> " + c1.getNumColeccoes());

		System.out.println("Profundidade da colecção " + c1.getTitulo() + " -> " + c1.getProfundidade());
		System.out.println("Profundidade da colecção " + cx2.getTitulo() + " -> " + cx2.getProfundidade());
		System.out.println();

		// rem livro
		String nomeLivro = "Viagem aos Himalaias";
		IObra l = c1.remObra(nomeLivro);
		System.out.println("Remoção de " + nomeLivro + " -> " + l);
		c1.print("");

	}
}