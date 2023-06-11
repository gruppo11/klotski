package klotski.klotski;

import java.util.Comparator;
import java.util.Vector;

public class Solver {
    private String board;
    private int conf;
    private Vector<String> sol;
    private String target;
    private final Vector<Istance> queue;
    private final Vector<Integer> visited;
    private Istance tail;
    private final Vector<String> sequence;
    private int index;
    private boolean isInverse;
    private boolean solved;
    private boolean added;
    public Solver(Board board){
        this.board = board.toString();
        this.conf = board.getConf();
        this.sol = board.getBestSolutionSequence();
        this.queue = new Vector<>();
        this.visited = new Vector<>();
        this.tail = null;
        this.sequence = new Vector<>();
        reset();
        queue.add(new Istance(null, board.toString()));
        visited.add(board.toString().hashCode());
    }
    //metodo che resetta tutti i vari oggetti necessari alla risoluzione
    private void reset(){
        this.target = "";
        this.queue.clear();
        this.visited.clear();
        this.index = 0;
        this.tail = null;
        this.sequence.clear();
        this.isInverse = false;
        this.solved = false;
        this.added = false;
    }
    //metodo che ricevuta una board fornisce la mossa migliore
    public Board getNextMove(Board board){
        //se è presente una soluzione ma la board in entrata non corrisponde resetta tutto
        if(solved && !board.toString().equals(this.board)) {
            solved = false;
        }
        //se non è presente una soluzione
        if(!solved){
            this.board = board.toString();
            this.sol = board.getBestSolutionSequence();
            this.conf = board.getConf();

            reset();

            queue.add(new Istance(null, board.toString()));
            visited.add(board.toString().hashCode());
            double bestDist = 1000000;
            //trova la configurazione della sequenza migliore più vicina alla board in entrata
            for(int i = 0; i < sol.size(); i++) {
                double dist = hammingDist(board.toString(), sol.get(i));
                if (dist <= bestDist) {
                    isInverse = false;
                    bestDist = dist;
                    index = i;
                }

                dist = hammingDist(board.toInverseString(), sol.get(i));
                if (dist <= bestDist) {
                    isInverse = true;
                    bestDist = dist;
                    index = i;
                }

            }
            //se la board in entrata è contenuta nella migliore sequenza ritorna la board successiva
            if(bestDist == 0) {
                solved = true;
            }else {
                //se non è contenuta setta il target e chiama il metodo solve
                if (isInverse)
                    target = Board.strToBoard(sol.get(index), conf).toInverseString();
                else
                    target = sol.get(index);

                solved = solve();
            }
        }
        //se è presente una soluzione
        if(solved){
            //se la sequenza di configurazioni non è stata popolata
            if(tail != null) {
                while (tail.getPrevIstance() != null) {
                    sequence.insertElementAt(tail.getBoard(), 0);
                    tail = tail.getPrevIstance();
                }
            }
            //toglie la prima configurazione della sequenza che è uguale a quella in entrata se la soluzione non era già stata calcolata
            if(!sequence.isEmpty() && sequence.get(0).equals(this.board))
                sequence.remove(0);
            //se non sono state aggiunte le mosse della sequenza migliore
            if(!added) {
                for (int i = index + 1; i < sol.size(); i++)
                    if(isInverse)
                        sequence.add(Board.strToBoard(sol.get(i), conf).toInverseString());
                    else
                        sequence.add(sol.get(i));
                added = true;
            }
            //ritorna il risultato dopo aver aggiornato la board del solver
            this.board = sequence.remove(0);
            return Board.strToBoard(this.board, conf);
        }else{
            return null;
        }
    }

    private int hammingDist(String str1, String str2)
    {
        int count = 0;
        for(int i = 0; i < str1.length(); i++)
            if (str1.charAt(i) != str2.charAt(i))
                count++;

        return count;
    }
    //metodo ricorsivo che cerca la sequenza per la soluzione
    private boolean solve(){
        //prende la prima board nella queue
        Istance istance = queue.remove(0);
        Board base = Board.strToBoard(istance.getBoard(), conf);
        //prova ogni mossa possibile
        for(String move : base.getPossibleMoves()){
            Board tmpBoard = new Board(base);
            tmpBoard.evaluate(move.charAt(0) - 48, move.charAt(1));

                //se la nuova configurazione e la sua versione specchiata non sono già state visitate
                if (!visited.contains(tmpBoard.toString().hashCode()) && !visited.contains(tmpBoard.toInverseString().hashCode())) {
                    //se ha trovato la soluzione ritorna true
                    if (hammingDist(tmpBoard.toString(), target) == 0) {
                        tail = new Istance(istance, tmpBoard.toString());
                        return true;
                    } else {
                        //se non è ancora arrivato alla soluzione aggiunge alla queue e alla lista di configurazioni visitate la nuova board
                        queue.add(new Istance(istance, tmpBoard.toString()));
                        visited.add(tmpBoard.toString().hashCode());
                    }
                }
        }
        //ordina le board in base alla somiglianza con la soluzione
        queue.sort(Comparator.comparing((Istance ist) -> hammingDist(ist.getBoard(), target)));
        //reitera il processo
        return solve();
    }
}
