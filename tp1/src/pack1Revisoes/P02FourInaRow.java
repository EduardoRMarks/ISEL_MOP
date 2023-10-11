package pack1Revisoes;

import java.util.Scanner;

public class P02FourInaRow {

    /**
     * Shows (prints) the board on the console
     *
     * @param board The board
     */
    private static void showboard(char[][] board) {
        //parte superior do tabuleiro
        System.out.println("+------------------+");

        for (int linha = 0; linha < board.length ; linha++) {
            //Por cada linha iniciada, é adicionada uma |
            System.out.print("|");
            //Ciclo fo para preencher cada linha
            for (int coluna = 0; coluna < board[linha].length; coluna++) {
                //se for o inicio do jogo/não haja nenhuma jogada na posição então mete um 0
                if (board[linha][coluna] == 0) System.out.print(" O ");
                //caso contrario na posição é posto a letra do jogador
                else System.out.print(" " + board[linha][coluna] + " ");
            }
            //no fim da linha é adicionado outra |
            System.out.println("|");
        }
        //parte inferior do tabuleiro
        System.out.println("+------------------+");

    }

    /**
     * Puts one piece for the received player. First asks the user to choose one
     * column, then validates it and repeat it until a valid column is chosen.
     * Finally, puts the player character on top of selected column.
     *
     * @param player   The player: 'A' or 'B'. Put this character on the board
     * @param board    The board
     * @param keyboard The keyboard Scanner
     * @return The column selected by the user.
     */
    private static int play(char player, char[][] board, Scanner keyboard) {
        //Variável para armazenar a coluna jogada
        int coluna;

        //ciclo para o jogador escolher a coluna e para validar a jogada
        //sai do ciclo se a jogada estiver entre 1 e 6 inclusive, e se a coluna escolhida não estiver cheia
        do {
            System.out.print("Choose a column (Player " + player + "): ");
            coluna = keyboard.nextInt();
        }
        while(1 > coluna || coluna > 6 || board[0][coluna-1] != 0); //usa-se o -1 pois a coluna 5 é na verdade a coluna 4 (012345)

        //ciclo para introduzir a jogada (começa por baixo)
        //introduz a jogada assim que encontrar o primeiro 0
        for (int linha = board.length-1; linha >= 0; linha--) {
            //é usado o break para ser adicionado só uma vez, caso contrario preenche a coluna toda
            if (board[linha][coluna-1] == 0) {
                board[linha][coluna-1] = player;
                break;
            }
        }

        //da return à coluna jogada
        return coluna;
    }

    /**
     * Checks if the player, with the character on top on the received column, won
     * the game or not. It will get the top move on that column, and check if there
     * are 4 pieces in a row, in relation to that piece and from the same player.
     * Returns true is yes, false is not.
     *
     * @param board The board
     * @param col   The last played column
     * @return True is that player won the game, or false if not.
     */
    private static boolean lastPlayerWon(char[][] board, int col) {
        //variavel para a linha onde a jogada foi feita
        int lin = 0;
        //usa-se o -1 pois a coluna 5 é na verdade a coluna 4 (012345)
        col -= 1;
        //variavel para contar se há 4 peças
        int nPecas = 1;

        //ciclo para saber qual a linha onde foi feita a jogada
        do{
            lin++;
        } while (board[lin][col] == 0);

        //variavel para saber quem é o jogador para depois se comparar
        int jogador = board[lin][col];

        /*Vamos então verificar se o jogador que meteu a peça ganhou o jogo
        * para tal temos que ver 4 direções sendo elas horizonal, vertical e
        * duas diagonais (\ e /)*/
//Horizontal
        /*Vamos começar por verificar a horizontal, ou seja, vamos olhar so para a esquerda e direita
        * usando o lugar onde foi feita a jogada como inicio*/

        //comecemos a contar para a direita
        int dir = 1, esq = 1;
        /*se a peça estiver dentro do tabuleiro e for ocupada pelo mesmo jogador, nPecas é incrementado
        * tal como a variável dir para ir verificar a proxima peça à direita*/
        while((col + dir <= 5) && (board[lin][col + esq] == jogador)) {
            nPecas++;
            dir++;
        }
        //fazendo agora para a esquerda
        /*se a peça estiver dentro do tabuleiro e for ocupada pelo mesmo jogador, nPecas é incrementado
         * tal como a variável esq para ir verificar a proxima peça à esquerda*/
        while(col - esq >= 0 && board[lin][col - esq] == jogador) {
            nPecas++;
            esq++;
        }
        //se o numero de peças for maior ou igual a 4, então o jogador ganha o jogo, caso contrário verificamos a próxima direção
        if (nPecas >= 4) return true;


//Vertical
        /*vamos agora verificar a vertical, ou seja, vamos começar na posição onde estamos e vamos descer
        * visto que para se ganhar na vertical, a ultima peça colocada é a peça "vencedora"*/

        //o numero de peças tem que voltar ao numero original
        nPecas = 1;
        //o processo é basicamente o mesmo usado para a vertical
        int baixo = 1;
        /*se a peça estiver dentro do tabuleiro e for ocupada pelo mesmo jogador, nPecas é incrementado
         * tal como a variável baixo para ir verificar a proxima peça a baixo*/
        while((lin + baixo) <= 6 && (board[lin + baixo][col] == jogador)) {
            nPecas++;
            baixo++;
        }
        //se o numero de peças for maior ou igual a 4, então o jogador ganha o jogo, caso contrário verificamos a próxima direção
        if (nPecas >= 4) return true;

//Diagonais
        /*por fim vamos tratar das duas diagonais que são o contrario uma da outra, para tal \ para a explicação
        * para a verificação teremos que começar na posição onde estamos e andar uma casa para o lado e uma para
        * cima/baixo usando o lugar onde a peça foi colocada como inicio*/

        //o numero de peças tem que voltar ao numero original
        nPecas = 1;
        //variavel 'paraCima' vai incrementar para o canto sup esquerdo e 'paraBaixo' vai apontar para o canto inf direito
        int paraBaixo = 1, paraCima = 1;
        //como não estamos a andar so numa direção vamos ter que mudar os valores tanto das linhas como das colunas
        while((lin + paraBaixo <= 6) && (col + paraBaixo < 6) && (board[lin + paraBaixo][col+paraBaixo] == jogador)){
            nPecas++;
            paraBaixo++;
        }
        while((lin - paraCima >= 0) && (col - paraCima >= 0) && (board[lin - paraCima][col - paraCima] == jogador)){
            nPecas++;
            paraCima++;
        }
        //se o numero de peças for maior ou igual a 4, então o jogador ganha o jogo, caso contrário verificamos a próxima direção
        if(nPecas >= 4) return true;

        //o numero de peças tem que voltar ao numero original
        nPecas = 1;
        //por fim vamos verificar a diagonal /
        //variavel 'paraCima' vai incrementar para o canto sup direito e 'paraBaixo' vai apontar para o canto inf esquerdo
        //não precisamos de criar novas variáveis, apenas reiniciar os valores
        paraBaixo = 1;
        paraCima = 1;
        //como não estamos a andar so numa direção vamos ter que mudar os valores tanto das linhas como das colunas
        while((lin + paraBaixo <= 6) && (col - paraBaixo >= 0) && (board[lin + paraBaixo][col - paraBaixo] == jogador)){
            nPecas++;
            paraBaixo++;
        }
        while((lin - paraCima >= 0) && (col + paraCima < 6) && (board[lin - paraCima][col + paraCima] == jogador)){
            nPecas++;
            paraCima++;
        }
        //se o numero de peças for maior ou igual a 4, então o jogador ganha o jogo
        if(nPecas >= 4) return true;

        //caso nenhuma das verificações se verifique retorna false e o jogo continua
        return false;
    }

    /**
     * Check if there are at least one free position on board.
     *
     * @param board The board
     * @return True if there is, at least, one free position on board
     */
    private static boolean existsFreePlaces(char[][] board) {
        //vai ver cada linha procurando em cada coluna uma posição vazia para a jogada poder ser realizada
        for (int linha = 0; linha < board.length; linha++) {
            for(int coluna = 0; coluna < board[linha].length; coluna++){
                if(board[linha][coluna] == 0) return true;
            }
        }
        /*caso não houver espaços vazios, retorna false
        * o que quer dizer que não dá para efetuar a jogada na coluna desejada*/
        return false;
    }

    /**
     * Main method - this method should not be changed
     */
    public static void main(String[] args) {
        final int NCOLs = 7;
        final int NROWS = 6;

        // program variables
        Scanner keyboard = new Scanner(System.in);
        char[][] board = new char[NCOLs][NROWS];
        char winner = ' ';

        // show empty board
        showboard(board);

        // game cycle
        do {
            int col = play('A', board, keyboard);
            showboard(board);
            if (lastPlayerWon(board, col)) {
                winner = 'A';
                break;
            }
            if (!existsFreePlaces(board))
                break;

            col = play('B', board, keyboard);
            showboard(board);
            if (lastPlayerWon(board, col)) {
                winner = 'B';
                break;
            }

        } while (existsFreePlaces(board));

        // show final result
        switch (winner) {
            case ' ':
                System.out.println("We have a draw....");
                break;
            case 'A':
                System.out.println("Winner: Player A. Congratulations...");
                break;
            case 'B':
                System.out.println("Winner: Player B. Congratulations...");
                break;
        }

        // close keyboard
        keyboard.close();
    }
}
