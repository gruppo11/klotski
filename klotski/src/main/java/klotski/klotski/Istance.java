package klotski.klotski;

public class Istance {
    private final Istance prev;
    private final String board;

    public Istance(Istance prev, String board){
        this.prev = prev;
        this.board = board;
    }

    public Istance getPrevIstance() {return prev; }
    public String getBoard(){
        return board;
    }
}
