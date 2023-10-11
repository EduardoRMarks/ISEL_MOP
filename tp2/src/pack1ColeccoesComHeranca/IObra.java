package pack1ColeccoesComHeranca;

public interface IObra {
    //Devolve o título da obra
    String getTitulo();

    //Devolve o número de páginas da obra
    int getNumPaginas();

    //Devolve o preço da obra
    float getPreco();

    //Devolve uma string com a informação da obra
    String toString();

    //Deve mostrar na consola a informação da obra (toString) precedida do prefixo recebido
    void print(String prefix);

    //O Object recebido é igual, se não for null, se for uma obra e se tiver o mesmo título que o título da obra corrente
    boolean equals(Object l);
}
