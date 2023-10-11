package pack1Revisoes;

import java.util.Scanner;

public class P01CheckPrime {

    /**
     * Main, método de arranque da execução
     */
    public static void main(String[] args) {

        System.out.println("Programa para ver se um número é primo");

        int n;
        Scanner keyboard = new Scanner(System.in);

        //do while para verificar se o numero introduzido é válido (>=0)
        do {
            System.out.println("Introduza um número inteiro: ");
            n = keyboard.nextInt();
            if(n < 0){
                System.out.println("O número introduzido é inválido");
            }
        }while (n < 0);

        //se o numero for maior que 0 verifica se é primo
        if(n > 0){
            if(isPrime(n)) {
                System.out.println("O número " + n + " não é um número primo");
            }
            else {
                System.out.println("O número " + n + " é um número primo");
            }
        }
        //se o numero for 0 acaba o programa
        else System.out.println("Fim do programa");
    }

    public static boolean isPrime(int number) {

        //variavel auxiliar para servir de divisor
        int aux = 1;
        do{
            /*iteramos aux antes da primeira verificação pois qualquer numero
            * é divisivel por 1, o que causaria um problema, iria sempre dar resto 0*/
            aux++;

            //caso o numero introduzido for divisivel por qualquer aux, retorna true e o numero não é primo
            if(number % aux == 0 || number == 1){
                return true;
            }
         /*ciclo while até ao numero que vamos verificar
         * ex: para verificar se o nº7 fazemos 7/2; 7/3 ... 7/6
         * e não fazemos 7/7 porque iriamos encontrar o mesmo problema do 7/1*/
        } while(aux < number - 1);

        //caso o numero nunca entre no if do ciclo anterior, retorna false e o numero é primo
        return false;
    }
}
