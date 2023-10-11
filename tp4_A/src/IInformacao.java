public interface IInformacao {
	//Devolve o t�tulo da obra
	String getTitulo();

	//Devolve uma string com a informa��o da obra
	String toString();

	//Deve mostrar na consola a informa��o da obra (toString) precedida do prefixo recebido
	String print(String prefix);

	//O Object recebido � igual, se n�o for null, se for um livro e se tiver o mesmo t�tulo que o t�tulo do livro corrente
	boolean equals(Object o);
}
