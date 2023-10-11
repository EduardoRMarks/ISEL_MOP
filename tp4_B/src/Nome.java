import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

abstract class Nome implements IInformacao {
	//String com o nome do livro ou do conjunto
	private String titulo;

	/**
	 * Construtor do Nome: o seu nome pode ser constituido por letras, espa�os ou n�meros
	 * por�m tem de ter pelo menos uma letra ou n�mero*/
	public Nome(String titulo){
		/*Come�amos por validar o titulo do livro/conjunto, por�m em vez de o validar no construtor,
		 *  irei criar um m�todo espec�fico para o c�digo ficar mais organizado*/
		if(!validarTitulo(titulo)){
			//se o titulo n�o for v�lido, envia a mensagem seguinte
			throw new IllegalArgumentException("O t�tulo introduzido n�o � v�lido");
		}
		//se for v�lido, instanciamos este livro/conjunto com este titulo
		this.titulo = aparar(titulo);//aparamos primeiro o titulo para retirar espe�os a mais
	}
	/**
	 * Devolve o t�tulo da obra
	 */
	public String getTitulo() {
		return titulo;
	}

	//public abstract float getPreco();
	//public abstract Categoria[] getCategorias();

	/**
	 * validarTitulo(): metodo para validar titulo como o nome indica. O titulo s� ser� v�lido
	 * se conter apenas letras, espa�os ou numeros, mas tem de conter pelo menos uma letra ou numero,
	 * ou seja, o titulo "    " n�o ser� v�lido, mas "   Q    " j� ir� ser v�lido
	 */

	private boolean validarTitulo(String titulo) {
		//come�amos por ver se o titulo � null ou igual a 0
		if(titulo == null || titulo.length() == 0){
			return false;
		}

		/*no fim da valida��o, a variavel nCaract tem de ser pelo menos 1
		 * para o nome ser considerado v�lido*/
		int nCaract = 0;

		for(int i = 0; i < titulo.length(); i++){
			//variavel auxiliar para guardar o caracter na posi��o i
			char aux = titulo.charAt(i);
			//se o caracter n�o for uma letra nem um espe�o nem um numero retorna false
			if(!Character.isWhitespace(aux) && !Character.isLetter(aux) && !Character.isDigit(aux)){
				return false;
			}
			//se o caracter for uma letra ou numero vai iterar nCaract
			if (Character.isLetter(aux) || Character.isDigit(aux)){
				nCaract++;
			}
		}
		//se houver pelo menos um caracter v�lido ent�o retorna true
		if (nCaract >= 1){
			return true;
		}
		//caso contr�rio retorna false
		else return false;
	}
	/***
	 * aparar(): metodo para "aparar" o titulo, ou seja, vai retirar espa�os a mais que se encontrem antes e
	 * depois do titulo bem como espa�os a mais que se possam encontrar dentro do titulo.
	 * ex: "  Os   Lus�adas  " --> "Os Lus�adas"
	 */
	private String aparar(String titulo) {
		//.trim() elimina espa�os no inicio e no final de uma string, mas n�o elimina espa�os no meio da string
		titulo = titulo.trim();

		//cria��o da StringBuilder
		StringBuilder sbTitulo = new StringBuilder(titulo);
		for(int i = 0; i < sbTitulo.length(); i++){
			if(Character.isWhitespace(sbTitulo.charAt(i)) && Character.isWhitespace(sbTitulo.charAt(i+1))){
				/*se a posi��o onde estivermos for um espa�o bem como a posi��o seguinte tambem
				 * for um espa�o, ent�o vamos retirar a posi��o onde estamos e vamos tirar uma
				 * unidade a i, pois pode haver mais do que um espa�o, o que significa que se
				 * retirarmos o char seguinte em vez do char corrente, uma string com 3 espa�os
				 * iria passar a ter 2 espa�os em vez de 1*/
				sbTitulo.deleteCharAt(i);
				i--;
			}
		}
		return sbTitulo.toString();
	}

	/**
	 * Cria um novo Elemento contendo todas as informa��es deste Evento.
	 * @param doc o documento que ir� gerar o novo Elemento.
	 * @return um Elemento que represnta o Evento na arvore XML
	 */
	public abstract Element createElement(Document doc);

	/**
	 * Devolve uma string com a informa��o da obra (ver outputs desejados e m�todo
	 * toString de Livro)
	 */
	@Override
	public String toString() {
		//Chamamos o metodo get para recebermos a informa��o desejada
		return getTitulo();
	}

	public String print(String prefix){
		System.out.println(prefix + toString());
		return prefix;
	}

	/**
	 * O Object recebido � igual, se n�o for null, se for uma obra e se tiver o
	 * mesmo t�tulo que o t�tulo da obra corrente
	 */
	@Override
	public boolean equals(Object t) {
		//se t for null ent�o retorna false
		if(t == null){
			return false;
		}
		//se o objecto t for um livro entra no 1� if
		if (t instanceof Livro){
			Livro l = (Livro) t;
			//se o titulo enviado for igual ao livro atual retorna true
			if(getTitulo().equalsIgnoreCase(l.getTitulo())){
				return true;
			}
		}
		if (t instanceof ConjLivros){
			ConjLivros l = (ConjLivros) t;
			//se o titulo enviado for igual ao livro atual retorna true
			if(getTitulo().equalsIgnoreCase(l.getTitulo())){
				return true;
			}
		}
		//caso contrario retorna false
		return false;
	}

	public static Nome build(Node nNode){
		//come�amos por criar um evento null
		Nome nome = null;

		if (nNode.getNodeType() == Node.ELEMENT_NODE){

			/*se o nome do node for igual a Livro ent�o constroi o Nome "nome"
			 * como um livro*/
			if (nNode.getNodeName().equals("Livro")){
				nome = Livro.build(nNode);
				return nome;
			}
			/*caso contr�rio se o nome do node for igual a Livros ent�o constroi
			 * o Nome "nome" como um Conjunto de Livros*/
			if (nNode.getNodeName().equals("Livros")){
				nome = ConjLivros.build(nNode);
				return nome;
			}
		}
		/*se por acaso o nome do node n�o for nem Espetaculo nem evento
		 * ent�o n�o � contru�do o evento e retorna null*/
		return null;
	}
}
