package pack1ColeccoesComHeranca;

public abstract class Obra implements IObra {

	private String titulo;

	/**
	 * Constructor
	 */
	public Obra(String titulo) {
		//validação do titulo
		if (titulo == null || titulo.length() == 0)
			throw new IllegalArgumentException("O titulo tem de ter pelo menos um caracter");
		//se recebido for válido, atribui o titulo ao livro atual
		this.titulo = titulo;
	}

	/**
	 * Devolve o título da obra
	 */
	@Override
	public String getTitulo() {
		return this.titulo;
	}

	/**
	 * Deve devolver true se o array conter apenas nomes válidos. Cada nome deve ser
	 * validado pelo método validarNome
	 */
	public static boolean validarNomes(String[] nomes) {
		//ciclo for para validar se os nomes são válidos
		for(int i = 0; i < nomes.length; i++){
			//se o nome não for válido retorna false, caso contrário avança para o nome seguinte
			if(!validarNome(nomes[i])){
				return false;
			}
		}
		//se todos os nomes forem válidos retorna true
		return true;
	}

	/**
	 * Um nome válido se não for null e conter pelo menos uma letra
	 * (Character.isLetter) e só conter letras e espaços (Character.isWhitespace)
	 */
	public static boolean validarNome(String nome) {
		/*no fim da validação, a variavel nLetra tem de ser pelo menos 1
		 * para o nome ser considerado válido*/
		int nLetra = 0;

		for(int i = 0; i < nome.length(); i++){
			//variavel auxiliar para guardar o caracter na posição i
			char aux = nome.charAt(i);
			//se o caracter não for uma letra nem um espeço retorna false
			if(!Character.isLetter(aux) && !Character.isWhitespace(aux)){
				return false;
			}
			//se o caracter for uma letra, incremente nLetra
			if(Character.isLetter(aux)){
				nLetra++;
			}
		}
		if(nLetra >= 1){
			return true;
		}
		else return false;
	}

	/**
	 * Recebe um nome já previamente validado, ou seja só com letras ou espaços.
	 * Deve devolver o mesmo nome mas sem espaços (utilizar trim e
	 * Character.isWhitespace) no início nem no fim e só com um espaço ' ' entre
	 * cada nome. Deve utilizar um StringBuilder para ir contendo o nome já
	 * corrigido
	 */
	public static String removeExtraSpaces(String nome) {
		//.trim() elimina espaços no inicio e no final de uma string, mas não elimina espaços no meio da string
		nome = nome.trim();

		//criação da StringBuilder
		StringBuilder sbName = new StringBuilder(nome);
		for(int i = 0; i < sbName.length(); i++){
			if(Character.isWhitespace(sbName.charAt(i)) && Character.isWhitespace(sbName.charAt(i+1))){
				/*se a posição onde estivermos for um espaço bem como a posição seguinte tambem
				 * for um espaço, então vamos retirar a posição onde estamos e vamos tirar uma
				 * unidade a i, pois pode haver mais do que um espaço, o que significa que se
				 * retirarmos o char seguinte em vez do char corrente, uma string com 3 espaços
				 * iria passar a ter 2 espaços em vez de 1*/
				sbName.deleteCharAt(i);
				i--;
			}
		}
		return sbName.toString();
	}

	/**
	 * Método que verifica se há elementos repetidos. O array recebido não contém
	 * nulls.
	 */
	public static boolean haRepeticoes(String[] elems) {
		/*Vamos comparar um elemento de uma string com o resto da string a partir do elemento
		 * seguinte, ou seja, numa string (Diogo, Eduardo, Marques, Romba) vamos comparar Diogo
		 * com os 3 nomes a seguir, Eduardo com os 2 nomes a seguir e assim sucessivamente*/
		for(int i = 0; i < elems.length; i++){
			for(int j = i + 1; j < elems.length; j++){
				//se dois nomes forem iguais retorna true
				if(elems[i].equals(elems[j])){
					return true;
				}
			}
		}
		//se não houverem nomes iguais retorna false
		return false;
	}

	/**
	 * Devolve uma string com a informação da obra (ver outputs desejados e método
	 * toString de Livro)
	 */
	@Override
	public String toString() {
		//Chamamos os metodos get para recebermos as informações desejadas
		return getTitulo() + ", " + getNumPaginas() + "p " + getPreco() + "€ ";
	}

	/**
	 * Deve mostrar na consola a informação da obra (toString) precedida do prefixo
	 * recebido
	 */
	@Override
	public void print(String prefix) {
		//efetua o print da string recebida mais as informações do livro recorrendo ao método toString
		System.out.println(prefix + toString());
	}

	/**
	 * O Object recebido é igual, se não for null, se for uma obra e se tiver o
	 * mesmo título que o título da obra corrente
	 */
	public boolean equals(Object l) {

		String tituloacomparar = "";

		//se l for null então retorna false
		if(l == null){
			return false;
		}
		//se o objecto l for um livro entra no 1º if
		if (l instanceof Livro){
			//se o titulo enviado for igual ao livro atual retorna true
			if(((Livro) l).getTitulo().equals(getTitulo())){
				return true;
			}
		}
		//se o objecto l for uma coleção entra no 2º if
		else if (l instanceof Coleccao){
			//se o titulo enviado for igual ao coleção atual retorna true
			if(((Coleccao) l).getTitulo().equals(getTitulo())){
				return true;
			}
		}
		//caso contrario retorna false
		return false;
	}
}