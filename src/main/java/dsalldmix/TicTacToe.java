class TicTacToe {

    int n;
    int [] rows;
    int [] cols;
    int diag;
    int antiDiag;
    public TicTacToe(int n) {
     this.n = n;
     this.rows = new int[n];
     this.cols = new int[n];
     this.diag = 0;
     this.antiDiag = 0; 
    }

    
    public int move(int row, int col, int player) {
         int val = (player == 1)?1:-1;
         rows[row] += val;
         cols[col] += val;

         if(row == col){
            diag+=val;
         }
         if((row+col) == n-1){
            antiDiag+=val;
         }

         if(Math.abs(rows[row]) == n ||
            Math.abs(cols[col]) == n ||
            Math.abs(diag) == n ||
            Math.abs(antiDiag) == n){
                return player;
            }
        return 0;
    }
}

/**
 * Your TicTacToe object will be instantiated and called as such:
 * TicTacToe obj = new TicTacToe(n);
 * int param_1 = obj.move(row,col,player);
 */
