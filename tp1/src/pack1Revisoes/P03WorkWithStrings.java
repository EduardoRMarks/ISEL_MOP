package pack1Revisoes;

public class P03WorkWithStrings {

    /**
     * Main, método de arranque da execução
     */
    public static void main(String[] args) {

        test_compareStrings(null, null); // result = 0
        test_compareStrings(null, ""); // result = -1
        test_compareStrings("", null); // result = 1
        test_compareStrings("a", ""); // result = 1
        test_compareStrings("", "a"); // result = -1
        test_compareStrings("a", "a"); // result = 0
        test_compareStrings("b", "a"); // result = 1
        test_compareStrings("a", "b"); // result = -1
        test_compareStrings("aa", "a"); // result = 2
        test_compareStrings("a", "aa"); // result = -2
        test_compareStrings("aa", "aa"); // result = 0
        test_compareStrings("ab", "aa"); // result = 2
        test_compareStrings("ab", "ab"); // result = 0
        test_compareStrings("abc", "abc"); // result = 0
        test_compareStrings("abc", "abd"); // result = -3

        test_compareStrings("Bom", "Bo"); // result = 3
    }

    /**
     * Este método recebe duas Strings s1 e s2 e procede à sua comparação,
     * devolvendo um valor positivo se s1 for maior que s2, negativo se ao
     * contrário e 0 se iguais. A comparação deve ser feita primeiro em termos
     * lexicográficos caracter a caracter começando pelos caracteres de menor
     * peso ou em segundo lugar em termos de número de caracteres. Se diferentes
     * deve devolver o índice +1/-1 do caractere que faz a diferença. Ex.
     * s1="Bom", s2="Dia", deve devolver -1; s1="Boa", s2="Bom", deve devolver
     * -3; s1="Bom", s2="Bo", deve devolver 3. Uma String a null é considerada
     * menor que uma string não null.
     *
     * @param s1 string a comparar
     * @param s2 string a comparar
     * @return o resultado da comparação
     */
    private static int compareStrings(String s1, String s2) {
        //se forem ambos null o resultado é 0
        if(s1 == null && s2 == null) {
            return 0;
        }
        //se s1 for null e s2 não null, o resultado é -1
        if(s1 == null && s2 != null) {
            return -1;
        }
        //se s2 for null e s1 não null, o resultado é 1
        if(s1 != null && s2 == null) {
            return 1;
        }
        //se ambas forem vazias o resultado é 0
        if(s1.equals("") && s2.equals("")) {
            return 0;
        }
        //se s1 for vazio mas s2 não o resultado é -1
        if(s1.equals("") && s2.length() > 0) {
            return -1;
        }
        //se s2 for vazio mas s1 não o resultado é 1
        if(s2.equals("") && s1.length() > 0) {
            return 1;
        }

        //caso as strings não cumpram nenhum dos critérios anteriores vamos comparar primeiramente pelo tamanho
        //guardamos o tamanho de cada string nas variaveis len1 e len2
        int len1 = s1.length();
        int len2 = s2.length();
        //vê qual a maior string, retiramos uma unidade aos len1 e len2 para caso forem do mesmo tamanho
        //depois ser mais simples fazer a comparação
        int maior = --len1 < --len2 ? len2: len1;
        //variavel diferença para saber qual a string maior
        int dif = len1 - len2;

        //se a diferença for positiva significa que a primeira string é maior
        if (dif > 0){
            return maior + dif;
        }
        //caso contrário é a segunda string que é maior
        else if (dif < 0){
            return -maior + dif;
        }

        //entra neste ultimo else caso o tamanho das strings sejam iguais
        else{
            //variavel auxiliar para no return dizer a posição certa
            int aux = len1+1;

            //ciclo for para comparar os char das duas palavras, começando pelo caracter de menor peso
            //no ex da palavra "ola"(012) o caracter de menor peso é o 2, ou seja, o "a"
            for (int i = len1; i >= 0 ; i--) {

                //veriaveis que guardam o valor ASCII do caracter
                int char1 = s1.charAt(i);
                int char2 = s2.charAt(i);

                //se o caracter com valor mais elevado for da primeira palavra então return do aux
                if (char1 > char2){
                    return aux;
                }
                //se o caracter com valor mais elevado for da primeira palavra então return do -aux
                else if (char1 < char2){
                    return -aux;
                }
                //se os caracteres forem iguais e se ainda houverem letras na palavra (i>0)
                //retira uma unidade à variável aux e retorna ao incio do ciclo for
                else if ((char1 == char2) &&  (i > 0)){
                    aux--;
                }
            }
        }
        //caso as palavras forem iguais, o return é 0
        return 0;
    }

    /**
     * Auxiliary method that call compareStrings with two strings
     */
    private static void test_compareStrings(String s1, String s2) {
        try {
            System.out.print("compareStrings (" + s1 + ", " + s2 + ") = ");
            int res = compareStrings(s1, s2);
            System.out.println(res);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
